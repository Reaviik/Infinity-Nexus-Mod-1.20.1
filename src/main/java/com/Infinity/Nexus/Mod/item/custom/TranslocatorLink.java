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
import net.minecraft.world.inventory.ClickAction;
import net.minecraft.world.inventory.Slot;
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
        int[] cords = stack.getOrCreateTag().getIntArray("cords");
        boolean multiples = stack.getOrCreateTag().getBoolean("multiples");
        components.add(Component.translatable("tooltip.infinity_nexus.translocator_link_type").append(Component.literal(" " + (multiples ? "Multiples" : "Single"))));
        components.add(Component.translatable("tooltip.infinity_nexus.translocator_link_cord"));

        if (cords.length > 0) {
            for (int i = 0; i < cords.length; i += 3) {
                if (i + 2 < cords.length) {
                    components.add(Component.literal("X: " + cords[i] + " Y: " + cords[i+1] + " Z: " + cords[i+2]));
                }
            }
        }

        super.appendHoverText(stack, level, components, flag);
    }


    @Override
    public InteractionResult onItemUseFirst(ItemStack stack, UseOnContext context) {
        if (!context.getLevel().isClientSide()) {
            BlockEntity blockEntity = context.getLevel().getBlockEntity(context.getClickedPos());
            Player player = context.getPlayer();
            BlockPos pos = context.getClickedPos();
            int[] newCord = {pos.getX(), pos.getY(), pos.getZ()};

            if (blockEntity instanceof TranslocatorBlockEntity translocator) {
                if (!Objects.requireNonNull(player).isShiftKeyDown()) {
                    int[] cords = stack.getOrCreateTag().getIntArray("cords");
                    translocator.setCords(cords, player);
                } else {
                    int[] existingCords = stack.getOrCreateTag().getIntArray("cords");

                    boolean alreadyExists = false;
                    for (int i = 0; i <= existingCords.length - 3; i += 3) {
                        if (existingCords[i] == newCord[0] &&
                                existingCords[i + 1] == newCord[1] &&
                                existingCords[i + 2] == newCord[2]) {
                            alreadyExists = true;
                            break;
                        }
                    }

                    if (alreadyExists) {
                        player.sendSystemMessage(Component.literal(InfinityNexusMod.message)
                                .append(Component.translatable("tooltip.infinity_nexus.translocator_duplicate")));
                    } else if (existingCords.length >= 9 * 3) {
                        player.sendSystemMessage(Component.literal(InfinityNexusMod.message)
                                .append(Component.translatable("tooltip.infinity_nexus.translocator_full").append(Component.literal(" " + existingCords.length / 3))));
                    } else {
                        int[] updatedCords = Arrays.copyOf(existingCords, existingCords.length + 3);
                        System.arraycopy(newCord, 0, updatedCords, existingCords.length, 3);

                        stack.getOrCreateTag().putIntArray("cords", updatedCords);
                        player.sendSystemMessage(Component.literal(InfinityNexusMod.message)
                                .append(Component.translatable("tooltip.infinity_nexus.translocator_added",
                                        newCord[0], newCord[1], newCord[2])));
                    }
                }
            } else {
                if (Objects.requireNonNull(player).isShiftKeyDown()) {
                    int[] cords = stack.getOrCreateTag().getIntArray("cords");
                    if (cords.length != 0) {
                        stack.getOrCreateTag().remove("cords");
                        player.sendSystemMessage(Component.literal(InfinityNexusMod.message)
                                .append(Component.translatable("tooltip.infinity_nexus.translocator_clear")));
                    }else{
                        boolean multiples = stack.getOrCreateTag().getBoolean("multiples");
                        stack.getOrCreateTag().putBoolean("multiples", !multiples);
                        player.sendSystemMessage(Component.literal(InfinityNexusMod.message)
                                .append(Component.translatable("tooltip.infinity_nexus.translocator_link_type")
                                        .append(Component.literal(" " + (!multiples ? "Multiples" : "Single")))));
                    }
                }
            }
        }
        return super.onItemUseFirst(stack, context);
    }
}
