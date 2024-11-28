package com.Infinity.Nexus.Mod.block.entity.pedestals;


import com.Infinity.Nexus.Mod.block.custom.pedestals.BasePedestal;
import com.Infinity.Nexus.Mod.block.custom.pedestals.CreativityPedestal;
import com.Infinity.Nexus.Mod.block.entity.ModBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import software.bernie.geckolib.animatable.GeoBlockEntity;
import software.bernie.geckolib.core.animatable.GeoAnimatable;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animation.*;
import software.bernie.geckolib.core.object.PlayState;
import software.bernie.geckolib.util.GeckoLibUtil;

public class CreativityPedestalBlockEntity extends BasePedestalBlockEntity{
    public CreativityPedestalBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.CREATIVITY_PEDESTAL_BE.get(), pos, state);
    }
}