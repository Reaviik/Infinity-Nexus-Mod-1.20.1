package com.Infinity.Nexus.Mod.item.custom;

import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class Orb extends Item {
    public Orb(Properties pProperties) {
        super(pProperties);
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> components, TooltipFlag flag) {
        components.add(Component.translatable("tooltip.infinity_nexus.orb_stage").append(Component.literal(stack.getOrCreateTag().getInt("stage") >= 1 ? " " + stack.getOrCreateTag().getInt("stage") : "0")));
        super.appendHoverText(stack, level, components, flag);
    }
}
