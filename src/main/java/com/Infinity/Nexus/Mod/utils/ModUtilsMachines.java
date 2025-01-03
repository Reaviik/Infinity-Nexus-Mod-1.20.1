package com.Infinity.Nexus.Mod.utils;

import com.Infinity.Nexus.Core.fakePlayer.IFFakePlayer;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.server.level.ServerLevel;

public class ModUtilsMachines {
    private static IFFakePlayer fakePlayer = null;

    public static void sendParticlePath(ServerLevel serverLevel, SimpleParticleType particleType, BlockPos posStart, BlockPos targetPos, double x, double y, double z) {
        double distance = posStart.distManhattan(targetPos);
        int steps = (int) (distance * 5);

        double stepX = (targetPos.getX() - posStart.getX()) / (double) steps;
        double stepY = (targetPos.getY() - posStart.getY()) / (double) steps;
        double stepZ = (targetPos.getZ() - posStart.getZ()) / (double) steps;

        for (int i = 0; i <= steps; i++) {
            double x1 = posStart.getX() + stepX * i;
            double y1 = posStart.getY() + stepY * i;
            double z1 = posStart.getZ() + stepZ * i;
            serverLevel.sendParticles(particleType, x1 + x, y1 + y, z1 + z, 1, 0, 0, 0, 0.01D);
        }
    }

    public static IFFakePlayer getFakePlayer(ServerLevel level) {
        if (fakePlayer == null) {
            fakePlayer = new IFFakePlayer(level);
        }
        return fakePlayer;
    }
}
