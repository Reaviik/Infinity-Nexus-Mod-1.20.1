package com.Infinity.Nexus.Mod.block.entity;

import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.level.block.state.BlockState;

public class DepotBlockEntity extends DepotBlockEntityBase{
    public DepotBlockEntity(BlockPos pPos, BlockState pBlockState) {
        super(ModBlockEntities.DEPOT_BE.get(), pPos, pBlockState);
    }

    @Override
    public Component getDisplayName() {
        return Component.translatable("block.infinity_nexus_mod.depot");
    }
}