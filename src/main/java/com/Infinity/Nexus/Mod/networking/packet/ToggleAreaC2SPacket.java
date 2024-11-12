package com.Infinity.Nexus.Mod.networking.packet;

import com.Infinity.Nexus.Mod.block.entity.MobCrusherBlockEntity;
import com.Infinity.Nexus.Mod.networking.ModMessages;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

/**
 * Network packet sent from client to server to toggle the working area display of a Mob Crusher.
 * Handles the synchronization of area visibility state between client and server.
 */
public class ToggleAreaC2SPacket {
    private final BlockPos pos;
    private final boolean showArea;

    /**
     * Creates a new toggle area packet
     * 
     * @param pos The position of the Mob Crusher block
     * @param showArea The desired visibility state of the area
     */
    public ToggleAreaC2SPacket(BlockPos pos, boolean showArea) {
        this.pos = pos;
        this.showArea = showArea;
    }

    /**
     * Deserializes packet data from the network buffer
     * 
     * @param buf The network buffer containing serialized packet data
     */
    public ToggleAreaC2SPacket(FriendlyByteBuf buf) {
        this.pos = buf.readBlockPos();
        this.showArea = buf.readBoolean();
    }

    /**
     * Serializes packet data to the network buffer
     * 
     * @param buf The network buffer to write the packet data to
     */
    public void toBytes(FriendlyByteBuf buf) {
        buf.writeBlockPos(pos);
        buf.writeBoolean(showArea);
    }

    /**
     * Processes the packet on the server side and broadcasts the state change to nearby players
     * 
     * @param supplier The network context supplier
     * @return true if the packet was handled successfully
     */
    public boolean handle(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() -> {
            ServerPlayer player = context.getSender();
            if (player == null) return;

            ServerLevel level = player.serverLevel();
            if (level.getBlockEntity(pos) instanceof MobCrusherBlockEntity blockEntity) {
                blockEntity.setShowArea(showArea);
                blockEntity.setChanged();
                
                for (ServerPlayer nearbyPlayer : level.players()) {
                    if (nearbyPlayer.distanceToSqr(pos.getX(), pos.getY(), pos.getZ()) < 64 * 64) {
                        ModMessages.sendToPlayer(new AreaVisibilityS2CPacket(pos, showArea), nearbyPlayer);
                    }
                }
                
                level.sendBlockUpdated(pos, blockEntity.getBlockState(), blockEntity.getBlockState(), 3);
            }
        });
        return true;
    }
}
