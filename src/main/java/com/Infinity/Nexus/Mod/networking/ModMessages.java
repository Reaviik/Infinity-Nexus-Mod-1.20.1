package com.Infinity.Nexus.Mod.networking;

import com.Infinity.Nexus.Mod.InfinityNexusMod;
import com.Infinity.Nexus.Mod.networking.packet.EnergySyncS2CPacket;
import com.Infinity.Nexus.Mod.networking.packet.ToggleAreaC2SPacket;
import com.Infinity.Nexus.Mod.networking.packet.AreaVisibilityS2CPacket;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkDirection;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.PacketDistributor;
import net.minecraftforge.network.simple.SimpleChannel;

/**
 * Handles network message registration and packet sending for the Infinity Nexus mod.
 * This class manages all network communications between client and server.
 */
public class ModMessages {
    /** The main network channel instance used for packet transmission */
    private static SimpleChannel INSTANCE;
    
    /** Counter for generating unique packet IDs */
    private static int packetId = 0;
    
    /** The protocol version used for network communications */
    private static final String PROTOCOL_VERSION = "1.0";
    
    /**
     * Generates and returns the next available packet ID
     * @return The next sequential packet ID
     */
    private static int id() {
        return packetId++;
    }

    /**
     * Registers all network packets and sets up the network channel.
     * Must be called during mod initialization.
     */
    public static void register() {
        SimpleChannel net = NetworkRegistry.ChannelBuilder
                .named(new ResourceLocation(InfinityNexusMod.MOD_ID, "messages"))
                .networkProtocolVersion(() -> PROTOCOL_VERSION)
                .clientAcceptedVersions(s -> s.equals(PROTOCOL_VERSION))
                .serverAcceptedVersions(s -> s.equals(PROTOCOL_VERSION))
                .simpleChannel();
        INSTANCE = net;

        net.messageBuilder(EnergySyncS2CPacket.class, id(), NetworkDirection.PLAY_TO_CLIENT)
                .decoder(EnergySyncS2CPacket::new)
                .encoder(EnergySyncS2CPacket::toBytes)
                .consumerMainThread(EnergySyncS2CPacket::handle)
                .add();

        net.messageBuilder(ToggleAreaC2SPacket.class, id(), NetworkDirection.PLAY_TO_SERVER)
                .decoder(ToggleAreaC2SPacket::new)
                .encoder(ToggleAreaC2SPacket::toBytes)
                .consumerMainThread(ToggleAreaC2SPacket::handle)
                .add();

        net.messageBuilder(AreaVisibilityS2CPacket.class, id(), NetworkDirection.PLAY_TO_CLIENT)
                .decoder(AreaVisibilityS2CPacket::new)
                .encoder(AreaVisibilityS2CPacket::toBytes)
                .consumerMainThread(AreaVisibilityS2CPacket::handle)
                .add();
    }

    /**
     * Sends a message from the client to the server
     * 
     * @param message The message to send
     * @param <MSG> The type of the message
     */
    public static <MSG> void sendToServer(MSG message) {
        INSTANCE.sendToServer(message);
    }

    /**
     * Sends a message from the server to a specific player
     * 
     * @param message The message to send
     * @param player The target player
     * @param <MSG> The type of the message
     */
    public static <MSG> void sendToPlayer(MSG message, ServerPlayer player) {
        INSTANCE.send(PacketDistributor.PLAYER.with(() -> player), message);
    }

    /**
     * Broadcasts a message from the server to all connected clients
     * 
     * @param message The message to broadcast
     * @param <MSG> The type of the message
     */
    public static <MSG> void sendToClients(MSG message) {
        INSTANCE.send(PacketDistributor.ALL.noArg(), message);
    }
}