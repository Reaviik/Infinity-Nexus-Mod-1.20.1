package com.Infinity.Nexus.Mod.item.custom;

import com.Infinity.Nexus.Mod.item.ModItemsAdditions;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.DiggerItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.Vanishable;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;

import java.util.ArrayList;
import java.util.List;

public class HammerItem extends DiggerItem implements Vanishable {
    public HammerItem(Tier pTier, float pAttackDamageModifier, float pAttackSpeedModifier, Properties pProperties) {
        super(pAttackDamageModifier, pAttackSpeedModifier, pTier, BlockTags.MINEABLE_WITH_PICKAXE, pProperties);
    }
    public static List<BlockPos> getBlocksToBeDestroyed(int range, BlockPos initalBlockPos, ServerPlayer player) {
        List<BlockPos> positions = new ArrayList<>();

        BlockHitResult traceResult = player.level().clip(new ClipContext(player.getEyePosition(1f),
                (player.getEyePosition(1f).add(player.getViewVector(1f).scale(6f))),
                ClipContext.Block.COLLIDER, ClipContext.Fluid.NONE, player));
        if(traceResult.getType() == HitResult.Type.MISS) {
            return positions;
        }

        if(traceResult.getDirection() == Direction.DOWN || traceResult.getDirection() == Direction.UP) {
            for(int x = -range; x <= range; x++) {
                for(int y = -range; y <= range; y++) {
                    positions.add(new BlockPos(initalBlockPos.getX() + x, initalBlockPos.getY(), initalBlockPos.getZ() + y));
                }
            }
        }

        if(traceResult.getDirection() == Direction.NORTH || traceResult.getDirection() == Direction.SOUTH) {
            for(int x = -range; x <= range; x++) {
                for(int y = -range; y <= range; y++) {
                    positions.add(new BlockPos(initalBlockPos.getX() + x, initalBlockPos.getY() + y, initalBlockPos.getZ()));
                }
            }
        }

        if(traceResult.getDirection() == Direction.EAST || traceResult.getDirection() == Direction.WEST) {
            for(int x = -range; x <= range; x++) {
                for(int y = -range; y <= range; y++) {
                    positions.add(new BlockPos(initalBlockPos.getX(), initalBlockPos.getY() + y, initalBlockPos.getZ() + x));
                }
            }
        }


        return positions;
    }
    @Override
    public InteractionResult onItemUseFirst(ItemStack stack, UseOnContext context) {
        if (context.getLevel().isClientSide()) {
            return super.onItemUseFirst(stack, context);
        }

        Player player = context.getPlayer();
        if (player == null) {
            return super.onItemUseFirst(stack, context);
        }

        int maxUses = 6;
        int currentUses = stack.getOrCreateTag().getInt("range");

        if(player.getOffhandItem().getItem() == ModItemsAdditions.HAMMER_RANGE_UPGRADE.get()) {
            if (currentUses < maxUses) {
                int range = currentUses + 1;
                player.getOffhandItem().shrink(1);
                stack.getOrCreateTag().putInt("range", range);

                int increasedArea = (range * 2) + (player.getMainHandItem().getItem() == ModItemsAdditions.INFINITY_HAMMER.get() ? 3 : 5);
                player.sendSystemMessage(Component.literal("§e[§cINM§e]§b: Área aumentada para §f" + increasedArea + "x" + increasedArea));
            } else {
                player.sendSystemMessage(Component.literal("§e[§cINM§e]§b: Você atingiu o máximo de usos para este item."));
            }
        } else if (player.getOffhandItem().getItem() == ModItemsAdditions.ADVANCED_CIRCUIT.get()) {
            if (currentUses > 0) {
                int range = currentUses - 1;
                int decreasedArea = (range * 2) + (player.getMainHandItem().getItem() == ModItemsAdditions.INFINITY_HAMMER.get() ? 3 : 5);
                player.getOffhandItem().shrink(1);
                context.getLevel().addFreshEntity(new ItemEntity(context.getLevel(), context.getClickedPos().getX(), context.getClickedPos().getY(), context.getClickedPos().getZ(), ModItemsAdditions.HAMMER_RANGE_UPGRADE.get().getDefaultInstance()));
                player.sendSystemMessage(Component.literal("§e[§cINM§e]§b: Área diminuida para §f" + decreasedArea + "x" + decreasedArea));
                stack.getOrCreateTag().putInt("range", range);
            }else{
                player.sendSystemMessage(Component.literal("§e[§cINM§e]§b: Nao ha nenhum upgrade para remover."));
            }
        }


        return super.onItemUseFirst(stack, context);
    }

    @Override
    public boolean isEnchantable(ItemStack pStack) {
        return true;
    }
}
