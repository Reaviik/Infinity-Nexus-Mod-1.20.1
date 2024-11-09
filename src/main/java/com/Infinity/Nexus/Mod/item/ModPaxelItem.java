package com.Infinity.Nexus.Mod.item;

import com.Infinity.Nexus.Mod.utils.ModTags;
import net.minecraft.core.Direction;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.*;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.CampfireBlock;
import net.minecraft.world.level.block.RotatedPillarBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.ToolAction;
import net.minecraftforge.common.ToolActions;
import org.jetbrains.annotations.NotNull;

import java.util.Map;
import java.util.function.Function;

public class ModPaxelItem extends DiggerItem implements Vanishable {

    protected static final Map<Block, Block> BLOCK_STRIPPING_MAP = Axe.getStrippables();
    protected static final Map<Block, BlockState> SHOVEL_LOOKUP = Shovel.getFlattenables();

    public ModPaxelItem(Tier tier, float damage, float attackSpeed, Function<Properties, Properties> properties) {
        super(damage, attackSpeed, tier, ModTags.Blocks.PAXEL_MINEABLE, properties.apply(new Properties()
                .defaultDurability((int) (tier.getUses() * 1.5))
        ));
    }

    @Override
    public float getDestroySpeed(ItemStack stack, BlockState state) {
        if (state.is(BlockTags.MINEABLE_WITH_PICKAXE) ||
                state.is(BlockTags.MINEABLE_WITH_AXE) ||
                state.is(BlockTags.MINEABLE_WITH_SHOVEL) ||
                state.is(ModTags.Blocks.PAXEL_MINEABLE)) {
            return speed;
        }
        return 1.0F;
    }

    @Override
    public boolean canPerformAction(ItemStack stack, ToolAction action) {
        return ToolActions.DEFAULT_AXE_ACTIONS.contains(action)
                || ToolActions.DEFAULT_PICKAXE_ACTIONS.contains(action)
                || ToolActions.DEFAULT_SHOVEL_ACTIONS.contains(action)
                || ToolActions.DEFAULT_HOE_ACTIONS.contains(action);
    }

    @Override
    public boolean isCorrectToolForDrops(@NotNull ItemStack stack, BlockState state) {
        return state.is(BlockTags.MINEABLE_WITH_PICKAXE)
                || state.is(BlockTags.MINEABLE_WITH_AXE)
                || state.is(BlockTags.MINEABLE_WITH_SHOVEL)
                || state.is(ModTags.Blocks.PAXEL_MINEABLE);
    }

    @Override
    public @NotNull InteractionResult useOn(UseOnContext context) {
        var level = context.getLevel();
        var pos = context.getClickedPos();
        var player = context.getPlayer();
        var blockState = level.getBlockState(pos);
        BlockState resultToSet = null;

        var strippedResult = BLOCK_STRIPPING_MAP.get(blockState.getBlock());
        if (strippedResult != null) {
            level.playSound(player, pos, SoundEvents.AXE_STRIP, SoundSource.BLOCKS, 1.0F, 1.0F);
            resultToSet = strippedResult.defaultBlockState().setValue(RotatedPillarBlock.AXIS, blockState.getValue(RotatedPillarBlock.AXIS));
        }
        else if (context.getClickedFace() != Direction.DOWN) {
            var foundResult = SHOVEL_LOOKUP.get(blockState.getBlock());
            if (foundResult != null && level.getBlockState(pos.above()).isAir()) {
                level.playSound(player, pos, SoundEvents.SHOVEL_FLATTEN, SoundSource.BLOCKS, 1.0F, 1.0F);
                resultToSet = foundResult;
            }
            else if (blockState.getBlock() instanceof CampfireBlock && blockState.getValue(CampfireBlock.LIT)) {
                if (!level.isClientSide()) {
                    level.levelEvent(null, 1009, pos, 0);
                }
                resultToSet = blockState.setValue(CampfireBlock.LIT, false);
            }
        }

        if (resultToSet != null) {
            if (!level.isClientSide()) {
                level.setBlock(pos, resultToSet, 11);
                if (player != null) {
                    context.getItemInHand().hurtAndBreak(1, player, (entity) -> {
                        entity.broadcastBreakEvent(context.getHand());
                    });
                }
            }
            return InteractionResult.SUCCESS;
        }

        return InteractionResult.PASS;
    }

    private static final class Axe extends AxeItem {
        public static Map<Block, Block> getStrippables() {
            return AxeItem.STRIPPABLES;
        }

        private Axe(Tier tier, float attackDamage, float attackSpeed, Properties properties) {
            super(tier, attackDamage, attackSpeed, properties);
        }
    }

    private static final class Shovel extends ShovelItem {
        public static Map<Block, BlockState> getFlattenables() {
            return ShovelItem.FLATTENABLES;
        }

        private Shovel(Tier tier, float attackDamage, float attackSpeed, Properties properties) {
            super(tier, attackDamage, attackSpeed, properties);
        }
    }
}