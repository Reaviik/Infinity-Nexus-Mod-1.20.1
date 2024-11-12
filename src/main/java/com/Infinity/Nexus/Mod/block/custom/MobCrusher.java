package com.Infinity.Nexus.Mod.block.custom;

import com.Infinity.Nexus.Mod.block.ModBlocksAdditions;
import com.Infinity.Nexus.Mod.block.custom.common.CommonUpgrades;
import com.Infinity.Nexus.Mod.block.entity.MobCrusherBlockEntity;
import com.Infinity.Nexus.Mod.block.entity.ModBlockEntities;
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
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

/**
 * Represents the Mob Crusher block, a machine that automatically kills mobs in its working area
 * and collects their drops. Supports upgrades, direction-based placement, and machine level indication.
 */
public class MobCrusher extends BaseEntityBlock {
    /** Block state properties for machine state and orientation */
    public static final IntegerProperty LIT = IntegerProperty.create("lit", 0, 17);
    public static final DirectionProperty FACING = BlockStateProperties.HORIZONTAL_FACING;
    public static final VoxelShape SHAPE = Block.box(0, 0, 0, 16, 16, 16);

    /**
     * Creates a new Mob Crusher block with the specified properties
     * 
     * @param pProperties Block properties
     */
    public MobCrusher(Properties pProperties) {
        super(pProperties);
    }

    /**
     * @return The collision and render shape of the block
     */
    @Override
    public VoxelShape getShape(BlockState pState, BlockGetter pLevel, BlockPos pPos, CollisionContext pContext) {
        return SHAPE;
    }

    /**
     * Determines the block state when placed, considering player facing direction
     */
    public BlockState getStateForPlacement(BlockPlaceContext pContext) {
        return this.defaultBlockState().setValue(FACING, pContext.getHorizontalDirection().getOpposite());
    }

    /**
     * Handles block rotation
     */
    @Override
    public BlockState rotate(BlockState pState, Rotation pRotation) {
        return pState.setValue(FACING, pRotation.rotate(pState.getValue(FACING)));
    }

    /**
     * Handles block mirroring
     */
    @Override
    public BlockState mirror(BlockState pState, Mirror pMirror) {
        return pState.rotate(pMirror.getRotation(pState.getValue(FACING)));
    }

    /**
     * Defines block states used by this block
     */
    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> pBuilder) {
        pBuilder.add(FACING, LIT);
    }

    /**
     * @return The render type for the block
     */
    @Override
    public RenderShape getRenderShape(BlockState pState) {
        return RenderShape.MODEL;
    }

    /**
     * Handles block removal, ensuring proper item drops
     */
    @Override
    public void onRemove(BlockState pState, Level pLevel, BlockPos pPos, BlockState pNewState, boolean pIsMoving) {
        if (pState.getBlock() != pNewState.getBlock()) {
            BlockEntity blockEntity = pLevel.getBlockEntity(pPos);
            if (blockEntity instanceof MobCrusherBlockEntity) {
                ((MobCrusherBlockEntity) blockEntity).drops();
            }
        }
        super.onRemove(pState, pLevel, pPos, pNewState, pIsMoving);
    }

    /**
     * Handles player interaction with the block, managing upgrades and GUI access
     */
    @Override
    public @NotNull InteractionResult use(@NotNull BlockState pState, Level pLevel, @NotNull BlockPos pPos, 
            @NotNull Player pPlayer, @NotNull InteractionHand pHand, @NotNull BlockHitResult pHit) {
        pLevel.setBlock(pPos, ModBlocksAdditions.MOB_CRUSHER.get().defaultBlockState(), 1);
        CommonUpgrades.setUpgrades(pLevel, pPos, pPlayer);
        return InteractionResult.sidedSuccess(pLevel.isClientSide());
    }

    /**
     * Creates a new block entity for this block
     */
    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pPos, BlockState pState) {
        return new MobCrusherBlockEntity(pPos, pState);
    }

    /**
     * Provides the ticker for block entity updates
     */
    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState state, 
            BlockEntityType<T> type) {
        if (level.isClientSide()) {
            return createTickerHelper(type, ModBlockEntities.MOBCRUSHER_BE.get(),
                (level1, pos, state1, blockEntity) -> blockEntity.clientTick(level1, pos, state1));
        }
        return createTickerHelper(type, ModBlockEntities.MOBCRUSHER_BE.get(),
            (level1, pos, state1, blockEntity) -> blockEntity.tick(level1, pos, state1));
    }

    /**
     * Adds tooltip information to the block item
     */
    @Override
    public void appendHoverText(ItemStack stack, @Nullable BlockGetter level, List<Component> components, 
            TooltipFlag flag) {
        if (Screen.hasShiftDown()) {
            components.add(Component.translatable("item.infinity_nexus.mod_crusher_description"));
        } else {
            components.add(Component.translatable("tooltip.infinity_nexus.pressShift"));
        }
        super.appendHoverText(stack, level, components, flag);
    }
}