package com.Infinity.Nexus.Mod.block.custom;

import com.Infinity.Nexus.Mod.block.entity.ModBlockEntities;
import com.Infinity.Nexus.Mod.block.entity.TranslocatorBlockEntity;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
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
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.stream.Stream;

public class Translocator extends BaseEntityBlock {
    public static IntegerProperty LIT = IntegerProperty.create("lit", 0, 6);
    public static final DirectionProperty FACING = BlockStateProperties.FACING;
    protected static final VoxelShape CEILING_AABB  = Stream.of(
            Shapes.box(0.25, 0.9375, 0.25, 0.75, 1, 0.75),
            Shapes.box(0.375, 0.5625, 0.375, 0.625, 0.9375, 0.625),
            Shapes.box(0.46875, 0.3125, 0.46875, 0.53125, 0.5625, 0.53125),
            Shapes.box(0.4375, 0.21875, 0.4375, 0.5625, 0.34375, 0.5625),
            Shapes.box(0.453125, 0.234375, 0.453125, 0.546875, 0.328125, 0.546875)
    ).reduce(Shapes.empty(), (a, b) -> Shapes.join(a, b, BooleanOp.OR));
    protected static final VoxelShape FLOOR_AABB = Stream.of(
            Shapes.box(0.25, 0, 0.25, 0.75, 0.0625, 0.75),
            Shapes.box(0.375, 0.0625, 0.375, 0.625, 0.4375, 0.625),
            Shapes.box(0.46875, 0.4375, 0.46875, 0.53125, 0.6875, 0.53125),
            Shapes.box(0.4375, 0.65625, 0.4375, 0.5625, 0.78125, 0.5625),
            Shapes.box(0.453125, 0.671875, 0.453125, 0.546875, 0.765625, 0.546875)
    ).reduce(Shapes.empty(), (a, b) -> Shapes.join(a, b, BooleanOp.OR));
    protected static final VoxelShape NORTH_AABB =  Stream.of(
            Shapes.box(0.25, 0.25, 0.9375, 0.75, 0.75, 1),
            Shapes.box(0.375, 0.375, 0.5625, 0.625, 0.625, 0.9375),
            Shapes.box(0.46875, 0.46875, 0.3125, 0.53125, 0.53125, 0.5625),
            Shapes.box(0.4375, 0.4375, 0.21875, 0.5625, 0.5625, 0.34375),
            Shapes.box(0.453125, 0.453125, 0.234375, 0.546875, 0.546875, 0.328125)
    ).reduce(Shapes.empty(), (a, b) -> Shapes.join(a, b, BooleanOp.OR));
    protected static final VoxelShape SOUTH_AABB = Stream.of(
            Shapes.box(0.25, 0.25, 0, 0.75, 0.75, 0.0625),
            Shapes.box(0.375, 0.375, 0.0625, 0.625, 0.625, 0.4375),
            Shapes.box(0.46875, 0.46875, 0.4375, 0.53125, 0.53125, 0.6875),
            Shapes.box(0.4375, 0.4375, 0.65625, 0.5625, 0.5625, 0.78125),
            Shapes.box(0.453125, 0.453125, 0.671875, 0.546875, 0.546875, 0.765625)
    ).reduce(Shapes.empty(), (a, b) -> Shapes.join(a, b, BooleanOp.OR));
    protected static final VoxelShape WEST_AABB = Stream.of(
            Shapes.box(0.9375, 0.25, 0.25, 1, 0.75, 0.75),
            Shapes.box(0.5625, 0.375, 0.375, 0.9375, 0.625, 0.625),
            Shapes.box(0.3125, 0.46875, 0.46875, 0.5625, 0.53125, 0.53125),
            Shapes.box(0.21875, 0.4375, 0.4375, 0.34375, 0.5625, 0.5625),
            Shapes.box(0.234375, 0.453125, 0.453125, 0.328125, 0.546875, 0.546875)
    ).reduce(Shapes.empty(), (a, b) -> Shapes.join(a, b, BooleanOp.OR));
    protected static final VoxelShape EAST_AABB = Stream.of(
            Shapes.box(0, 0.25, 0.25, 0.0625, 0.75, 0.75),
            Shapes.box(0.0625, 0.375, 0.375, 0.4375, 0.625, 0.625),
            Shapes.box(0.4375, 0.46875, 0.46875, 0.6875, 0.53125, 0.53125),
            Shapes.box(0.65625, 0.4375, 0.4375, 0.78125, 0.5625, 0.5625),
            Shapes.box(0.671875, 0.453125, 0.453125, 0.765625, 0.546875, 0.546875)
    ).reduce(Shapes.empty(), (a, b) -> Shapes.join(a, b, BooleanOp.OR));

    public Translocator(Properties pProperties) {
        super(pProperties);
    }

    public BlockState getStateForPlacement(BlockPlaceContext pContext) {
        return this.defaultBlockState().setValue(FACING, pContext.getClickedFace());
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
    public VoxelShape getShape(BlockState pState, BlockGetter pLevel, BlockPos pPos, CollisionContext pContext) {
        return switch (pState.getValue(FACING)) {
            case DOWN -> CEILING_AABB;
            case NORTH -> NORTH_AABB;
            case SOUTH -> SOUTH_AABB;
            case WEST -> WEST_AABB;
            case EAST -> EAST_AABB;
            default -> FLOOR_AABB;
        };
    }

    @Override
    public RenderShape getRenderShape(BlockState pState) {
        return RenderShape.MODEL;
    }

    @Override
    public void onRemove(BlockState pState, Level pLevel, BlockPos pPos, BlockState pNewState, boolean pIsMoving) {
        if (pState.getBlock() != pNewState.getBlock()) {
            BlockEntity blockEntity = pLevel.getBlockEntity(pPos);
            if (blockEntity instanceof TranslocatorBlockEntity) {
                ((TranslocatorBlockEntity) blockEntity).drops();
            }
        }
        super.onRemove(pState, pLevel, pPos, pNewState, pIsMoving);
    }

    @Override
    public @NotNull InteractionResult use(@NotNull BlockState pState, Level pLevel, @NotNull BlockPos pPos, @NotNull Player pPlayer, @NotNull InteractionHand pHand, @NotNull BlockHitResult pHit) {
        if (!pLevel.isClientSide()) {
            BlockEntity blockEntity = pLevel.getBlockEntity(pPos);
            if (blockEntity instanceof TranslocatorBlockEntity translocator) {
                translocator.toggleMode(pPlayer.getItemInHand(pHand).copy(), pPlayer, pPlayer.isShiftKeyDown(), pPos);
            }
        }
        return InteractionResult.sidedSuccess(pLevel.isClientSide());
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pPos, BlockState pState) {
        return new TranslocatorBlockEntity(pPos, pState);
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level pLevel, BlockState pState, BlockEntityType<T> pBlockEntityType) {
        if(pLevel.isClientSide()) {
            return null;
        }

        return createTickerHelper(pBlockEntityType, ModBlockEntities.TRASLOCATOR_BE.get(),
                (pLevel1, pPos, pState1, pBlockEntity) -> pBlockEntity.tick(pLevel1, pPos, pState1));
    }
    @Override
    public void appendHoverText(ItemStack stack, @Nullable BlockGetter level, List<Component> components, TooltipFlag flag) {
        if (Screen.hasShiftDown()) {
            components.add(Component.translatable("item.infinity_nexus.translocator_description"));
        } else {
            components.add(Component.translatable("tooltip.infinity_nexus.pressShift"));
        }
        super.appendHoverText(stack, level, components, flag);
    }
}