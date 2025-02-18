package com.Infinity.Nexus.Mod.block.custom;

import com.Infinity.Nexus.Mod.block.custom.common.CommonUpgrades;
import com.Infinity.Nexus.Mod.block.entity.DisplayBlockEntity;
import com.Infinity.Nexus.Mod.block.entity.InfuserBlockEntity;
import com.Infinity.Nexus.Mod.block.entity.ModBlockEntities;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
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
import net.minecraftforge.network.NetworkHooks;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class Infuser extends BaseEntityBlock {
    public static IntegerProperty LIT = IntegerProperty.create("lit", 0, 17);
    public static final DirectionProperty FACING = BlockStateProperties.HORIZONTAL_FACING;
    public static final VoxelShape SHAPE = Block.box(0, 0, 0, 16, 15, 16);

    public Infuser(Properties pProperties) {
        super(pProperties);
    }

    @Override
    public VoxelShape getShape(BlockState pState, BlockGetter pLevel, BlockPos pPos, CollisionContext pContext) {
        return SHAPE;
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
            if (blockEntity instanceof InfuserBlockEntity) {
                ((InfuserBlockEntity) blockEntity).drops();
            }
        }
        super.onRemove(pState, pLevel, pPos, pNewState, pIsMoving);
    }
    @Override
    public @NotNull InteractionResult use(@NotNull BlockState pState, Level pLevel, @NotNull BlockPos pPos, @NotNull Player pPlayer, @NotNull InteractionHand pHand, @NotNull BlockHitResult pHit) {
        if (!pLevel.isClientSide()) {
            InfuserBlockEntity  blockEntity = (InfuserBlockEntity) pLevel.getBlockEntity(pPos);
            if(pPlayer.getMainHandItem().isEmpty()){
                if(pPlayer.isShiftKeyDown()){
                    blockEntity.removeStack(pPlayer.getMainHandItem().copy(), blockEntity, pPlayer, 0);
                }else{
                    blockEntity.removeStack(pPlayer.getMainHandItem().copy(), blockEntity, pPlayer, 1);
                }
            }else{
                if(pPlayer.isShiftKeyDown()){
                    blockEntity.addStack(pPlayer.getMainHandItem().copy(), blockEntity, pPlayer, 0);
                }else{
                    blockEntity.addStack(pPlayer.getMainHandItem().copy(), blockEntity, pPlayer, 1);
                }
            }
            return InteractionResult.SUCCESS;
        }
        return InteractionResult.sidedSuccess(pLevel.isClientSide());
    }
    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pPos, BlockState pState) {
        return new InfuserBlockEntity(pPos, pState);
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level pLevel, BlockState pState, BlockEntityType<T> pBlockEntityType) {
        if(pLevel.isClientSide()) {
            return null;
        }

        return createTickerHelper(pBlockEntityType, ModBlockEntities.INFUSER_BE.get(),
                (pLevel1, pPos, pState1, pBlockEntity) -> pBlockEntity.tick(pLevel1, pPos, pState1));
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable BlockGetter level, List<Component> components,
                                TooltipFlag flag) {
        if (Screen.hasShiftDown()) {
            components.add(Component.literal("§bInfuser Build Example"));
            components.add(Component.literal("§4§n[]§f = §4").append(Component.translatable("block.infinity_nexus_mod.infuser")));
            components.add(Component.literal("§5[]§f = ").append(Component.translatable("block.infinity_nexus_mod.magic_pedestal")));
            components.add(Component.literal("§e[]§f = ").append(Component.translatable("block.infinity_nexus_mod.resource_pedestal")));
            components.add(Component.literal("§8[]§f = ").append(Component.translatable("block.infinity_nexus_mod.decor_pedestal")));
            components.add(Component.literal("§3[]§f = ").append(Component.translatable("block.infinity_nexus_mod.tech_pedestal")));
            components.add(Component.literal("§6[]§f = ").append(Component.translatable("block.infinity_nexus_mod.creativity_pedestal")));
            components.add(Component.literal("§2[]§f = ").append(Component.translatable("block.infinity_nexus_mod.exploration_pedestal")));

            components.add(Component.literal("§f[][]§5[]§f[][]"));
            components.add(Component.literal("§e[]§f[][][]§8[]"));
            components.add(Component.literal("§f[][]§4[]§f[][]"));
            components.add(Component.literal("§3[]§f[][][]§6[]"));
            components.add(Component.literal("§f[][]§2[]§f[][]"));
        } else {
            components.add(Component.translatable("tooltip.infinity_nexus.pressShift"));
        }
        super.appendHoverText(stack, level, components, flag);
    }
}