package com.Infinity.Nexus.Mod.block.custom;

import com.Infinity.Nexus.Mod.block.custom.common.CommonUpgrades;
import com.Infinity.Nexus.Mod.block.entity.ModBlockEntities;
import com.Infinity.Nexus.Mod.block.entity.TankBlockEntity;
import com.Infinity.Nexus.Mod.config.ConfigUtils;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraftforge.fluids.FluidStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class Tank extends BaseEntityBlock {
    public static IntegerProperty LIT = IntegerProperty.create("lit", 0, 1);
    public static final DirectionProperty FACING = BlockStateProperties.HORIZONTAL_FACING;
    public static final VoxelShape SHAPE = Block.box(2, 0, 2, 14, 16, 14);

    public Tank(Properties pProperties) {
        super(pProperties);
    }


    @Override
    public VoxelShape getShape(BlockState pState, BlockGetter pLevel, BlockPos pPos, CollisionContext pContext) {
            return SHAPE;
    }

    public BlockState getStateForPlacement(BlockPlaceContext pContext) {
        return this.defaultBlockState().setValue(FACING, pContext.getHorizontalDirection().getOpposite());
    }
    @Override
    public BlockState rotate(BlockState pState, Rotation pRotation) {
        return pState.setValue(FACING, pRotation.rotate(pState.getValue(FACING)));
    }

    @Override
    public BlockState mirror(BlockState pState, Mirror pMirror) {
        return pState.rotate(pMirror.getRotation(pState.getValue(FACING)));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> pBuilder) {
        pBuilder.add(FACING, LIT);
    }
    @Override
    public RenderShape getRenderShape(BlockState pState) {
        return RenderShape.MODEL;
    }

    @Override
    public void onRemove(BlockState pState, Level pLevel, BlockPos pPos, BlockState pNewState, boolean pIsMoving) {
        if (pState.getBlock() != pNewState.getBlock()) {
            BlockEntity blockEntity = pLevel.getBlockEntity(pPos);
            if (blockEntity instanceof TankBlockEntity) {
                ((TankBlockEntity) blockEntity).drops();
            }
        }

        super.onRemove(pState, pLevel, pPos, pNewState, pIsMoving);
    }

    @Override
    public @NotNull InteractionResult use(@NotNull BlockState pState, Level pLevel, @NotNull BlockPos pPos, @NotNull Player pPlayer, @NotNull InteractionHand pHand, @NotNull BlockHitResult pHit) {
        CommonUpgrades.setUpgrades(pLevel, pPos, pPlayer);
        return InteractionResult.sidedSuccess(pLevel.isClientSide());
    }
     @Override
     public void setPlacedBy(Level pLevel, BlockPos pPos, BlockState pState, @Nullable LivingEntity pPlacer, ItemStack pStack) {
         FluidStack stack = FluidStack.loadFluidStackFromNBT(pStack.getTagElement("Fluid"));
         TankBlockEntity entity =  (TankBlockEntity) pLevel.getBlockEntity(pPos);
         if(entity != null && !pLevel.isClientSide()) {
             entity.fillFluidFromNBT(stack);
         }
         super.setPlacedBy(pLevel, pPos, pState, pPlacer, pStack);
     }
    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pPos, BlockState pState) {
        return new TankBlockEntity(pPos, pState);
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level pLevel, BlockState pState, BlockEntityType<T> pBlockEntityType) {
        if(pLevel.isClientSide()) {
            return null;
        }
        return createTickerHelper(pBlockEntityType, ModBlockEntities.TANK_BE.get(),
                (pLevel1, pPos, pState1, pBlockEntity) -> pBlockEntity.tick(pLevel1, pPos, pState1));
    }

    @Override
    public void appendHoverText(ItemStack pStack, @Nullable BlockGetter pLevel, List<Component> pTooltip, TooltipFlag pFlag) {
        FluidStack stack = FluidStack.loadFluidStackFromNBT(pStack.getTagElement("Fluid"));
        if(!stack.equals(FluidStack.EMPTY)) {
            boolean endless = stack.getAmount() >= ConfigUtils.tank_capacity;
            pTooltip.add(Component.literal(stack.getDisplayName().getString() + (endless ? " (" + Component.translatable("tooltip.infinity_nexus_mod.tank_endless").getString() + ")" : " " + stack.getAmount() + "mB/128000mB")));
        }else{
            pTooltip.add(Component.translatable("tooltip.infinity_nexus_mod.tank_empty"));
        }if(ConfigUtils.tank_can_endless) {
            if (Screen.hasShiftDown()) {
                pTooltip.add(Component.translatable("item.infinity_nexus.tank_description"));
            } else {
                pTooltip.add(Component.translatable("tooltip.infinity_nexus.pressShift"));
            }
        }
        super.appendHoverText(pStack, pLevel, pTooltip, pFlag);
    }
}