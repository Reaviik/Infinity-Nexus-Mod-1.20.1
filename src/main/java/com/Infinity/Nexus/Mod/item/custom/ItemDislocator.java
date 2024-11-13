package com.Infinity.Nexus.Mod.item.custom;

import net.minecraft.client.gui.screens.Screen;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class ItemDislocator extends Item {


    public ItemDislocator(Properties pProperties) {
        super(pProperties);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand ihand) {
        if (!level.isClientSide() && !Screen.hasShiftDown()) {
            CompoundTag itemNBT = player.getMainHandItem().getOrCreateTag();
            player.sendSystemMessage(Component.translatable(itemNBT.getBoolean("onofre")
                    ? "chat.infinity_nexus_mod.item_dislocator_off"
                    : "chat.infinity_nexus_mod.item_dislocator_on"));
            itemNBT.putBoolean("onofre", !itemNBT.getBoolean("onofre"));
        }
        return super.use(level, player, ihand);
    }

    @Override
    public void inventoryTick(@NotNull ItemStack stack, @NotNull Level level, @NotNull Entity entity, int slot, boolean selected) {
        if (!level.isClientSide() && entity instanceof Player player && stack.getOrCreateTag().getBoolean("onofre")) {
            try {
                List<ItemEntity> entities = level.getEntitiesOfClass(ItemEntity.class, new AABB(entity.getX() + 5, entity.getY() + 5, entity.getZ() + 5, entity.getX() - 5, entity.getY() - 5, entity.getZ() - 5));
                for(ItemEntity item: entities){
                    if (item.isAlive() && !item.hasPickUpDelay()) {
                        item.playerTouch(player);
                    }
                }
            }catch (Exception e){
                System.out.println("\nInfinity Item Dislocator\n"+e+"\n\n");
            }
        }
    }


    @Override
    public void appendHoverText(ItemStack pStack, @Nullable Level pLevel, List<Component> components, TooltipFlag pIsAdvanced) {
        super.appendHoverText(pStack, pLevel, components, pIsAdvanced);
        if (Screen.hasShiftDown()) {
            components.add(Component.translatable("tooltip.infinity_nexus_mod.item_dislocator"));
        } else {
            components.add(Component.translatable("tooltip.infinity_nexus.pressShift"));
            components.add(Component.literal(pStack.getOrCreateTag().getBoolean("onofre") ? "§aON" : "§cOFF"));
        }
    }

}
