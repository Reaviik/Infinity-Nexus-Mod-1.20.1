package com.Infinity.Nexus.Mod.flight;

import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.Vec3;

public class FlightManager {
    //Parse to mod config
    private static final long DOUBLE_PRESS_TIME = 500;
    private static final long TOGGLE_COOLDOWN = 500;

    private static long lastPressTime = 0;
    private static long lastToggleTime = 0;
    private static boolean isFlying = false;

    public static void handleFlightActivation(Player player) {
        long currentTime = System.currentTimeMillis();
        if (currentTime - lastToggleTime < TOGGLE_COOLDOWN) {
            return;
        }

        if (currentTime - lastPressTime <= DOUBLE_PRESS_TIME) {
            toggleFlight(player);
            lastPressTime = 0;
        } else {
            lastPressTime = currentTime;
        }
    }
    private static void toggleFlight(Player player) {
        isFlying = !isFlying;
        lastToggleTime = System.currentTimeMillis();

        if (isFlying) {
            player.getAbilities().flying = true;
            player.getAbilities().mayfly = true;
        } else {
            player.getAbilities().flying = false;
            player.getAbilities().mayfly = false;
            player.fallDistance = 0F;
        }
        player.onUpdateAbilities();
    }
}