package com.Infinity.Nexus.Mod.item.custom;

import com.Infinity.Nexus.Mod.InfinityNexusMod;
import com.Infinity.Nexus.Mod.block.entity.TranslocatorBlockEntity;
import com.Infinity.Nexus.Mod.utils.ModUtilsMachines;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import org.jetbrains.annotations.Nullable;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class TranslocatorLink extends Item {
    public TranslocatorLink(Properties pProperties) {
        super(pProperties);
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> components, TooltipFlag flag) {
        int[] coord = stack.getOrCreateTag().getIntArray("cords");
        components.add(Component.translatable("tooltip.infinity_nexus.cord").append(Component.literal(" " + Arrays.toString(coord))));
        super.appendHoverText(stack, level, components, flag);
    }

    @Override
    public InteractionResult onItemUseFirst(ItemStack stack, UseOnContext context) {
        if(!context.getLevel().isClientSide()){
            BlockEntity blockEntity = context.getLevel().getBlockEntity(context.getClickedPos());
            if (blockEntity instanceof TranslocatorBlockEntity translocator) {
                BlockPos pos = context.getClickedPos();
                int[] cord = {pos.getX(), pos.getY(), pos.getZ()};
                if(!Objects.requireNonNull(context.getPlayer()).isShiftKeyDown()) {
                    int[] cords = context.getPlayer().getMainHandItem().getOrCreateTag().getIntArray("cords");
                    translocator.setCords(cords, context.getPlayer(), cords);
                }else {
                    stack.getOrCreateTag().putIntArray("cords", cord);
                    context.getPlayer().sendSystemMessage(Component.literal(InfinityNexusMod.message).append(Component.translatable("tooltip.infinity_nexus.translocator_copy")));
                }
            }else{
                if(Objects.requireNonNull(context.getPlayer()).isShiftKeyDown()) {
                    context.getPlayer().getMainHandItem().getOrCreateTag().remove("cords");
                    context.getPlayer().sendSystemMessage(Component.literal(InfinityNexusMod.message).append(Component.translatable("tooltip.infinity_nexus.translocator_clear")));
                }

            }
        }
        return super.onItemUseFirst(stack, context);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level pLevel, Player pPlayer, InteractionHand pUsedHand) {
        //if(!pLevel.isClientSide() && pPlayer.isShiftKeyDown()) {
        //    pPlayer.getMainHandItem().getOrCreateTag().remove("cords");
        //    pPlayer.sendSystemMessage(Component.literal("Coordenadas removidas"));
        //}
        return super.use(pLevel, pPlayer, pUsedHand);
    }
}
