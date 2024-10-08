package com.Infinity.Nexus.Mod.block.custom;

import com.Infinity.Nexus.Mod.item.ModItemsAdditions;
import com.Infinity.Nexus.Mod.worldgen.dimension.ModDimensions;
import com.Infinity.Nexus.Mod.worldgen.portal.PortalTeleport;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;

public class PortalBlock extends Block {
    public PortalBlock(Properties pProperties) {
        super(pProperties);
    }

    @Override
    public InteractionResult use(BlockState pState, Level pLevel, BlockPos pPos, Player pPlayer, InteractionHand pHand, BlockHitResult pHit) {
        if (pPlayer.canChangeDimensions() && pPlayer.getMainHandItem().getItem() == ModItemsAdditions.PORTAL_ACTIVATOR.get()) {
            handlePortal(pPlayer, pPos);
            return InteractionResult.SUCCESS;
        } else {
            return InteractionResult.CONSUME;
        }
    }

    private void handlePortal(Entity player, BlockPos pPos) {
        if (player.level() instanceof ServerLevel serverlevel && player instanceof ServerPlayer serverPlayer) {
            MinecraftServer minecraftserver = serverlevel.getServer();
            ResourceKey<Level> resourcekey = player.level().dimension() == ModDimensions.EXPLORAR_LEVEL_KEY?
                    Level.OVERWORLD : ModDimensions.EXPLORAR_LEVEL_KEY;

            ServerLevel portalDimension = minecraftserver.getLevel(resourcekey);
            if (portalDimension != null && !player.isPassenger()) {
                if(resourcekey == ModDimensions.EXPLORAR_LEVEL_KEY) {
                    serverPlayer.addEffect(new MobEffectInstance(MobEffects.SLOW_FALLING, 1000, 1, false, false));
                    player.changeDimension(portalDimension, new PortalTeleport(pPos, true));
                    player.teleportTo(player.getX(), player.getY()+300, player.getZ());
                } else {
                    player.changeDimension(portalDimension, new PortalTeleport(pPos, false));
                    player.teleportTo(player.getX(), player.getY()+300, player.getZ());
                }
            }
        }
    }
    public void animateTick(BlockState pState, Level pLevel, BlockPos pPos, RandomSource pRandom) {
        double d0 = (double)pPos.getX() + pRandom.nextDouble();
        double d1 = (double)pPos.getY() + 1D;
        double d2 = (double)pPos.getZ() + pRandom.nextDouble();
        pLevel.addParticle(ParticleTypes.END_ROD, d0, d1, d2, 0.0D, 0.03D, 0.0D);
    }

}
