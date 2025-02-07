package com.Infinity.Nexus.Mod.block.entity.wrappedHandlerMap;

import com.Infinity.Nexus.Core.items.ModItems;
import com.Infinity.Nexus.Core.utils.ModUtils;
import net.minecraft.core.Direction;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import org.jetbrains.annotations.NotNull;

public class TankHandler {

    public static boolean extract(int slot, Direction direction) {
        return slot == 1;
    }
    public static boolean insert(int slot, @NotNull ItemStack stack) {
        return  switch (slot) {
            case 0 -> stack.getCapability(ForgeCapabilities.FLUID_HANDLER_ITEM).isPresent();
            case 2 -> ModUtils.isUpgrade(stack);
            default -> false;
        };
    }
}
