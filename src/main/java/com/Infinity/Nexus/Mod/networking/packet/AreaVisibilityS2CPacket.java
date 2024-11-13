package com.Infinity.Nexus.Mod.networking.packet;

import com.Infinity.Nexus.Mod.block.entity.MobCrusherBlockEntity;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

/**
 * Network packet sent from server to client to synchronize the working area visibility state
 * of a Mob Crusher block entity. This ensures all clients see the same visual state.
 */
public class AreaVisibilityS2CPacket {
    private final BlockPos pos;
    private final boolean showArea;

    /**
     * Creates a new area visibility packet
     *
     * @param pos The position of the Mob Crusher block
     * @param showArea The visibility state to synchronize
     */
    public AreaVisibilityS2CPacket(BlockPos pos, boolean showArea) {
        this.pos = pos;
        this.showArea = showArea;
    }

    /**
     * Deserializes packet data from the network buffer
     *
     * @param buf The network buffer containing serialized packet data
     */
    public AreaVisibilityS2CPacket(FriendlyByteBuf buf) {
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
     * Processes the packet on the client side, updating the block entity's area visibility state
     *
     * @param supplier The network context supplier
     * @return true if the packet was handled successfully
     */
    public boolean handle(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() -> {
            if (Minecraft.getInstance().level != null) {
                if (Minecraft.getInstance().level.getBlockEntity(pos) instanceof MobCrusherBlockEntity blockEntity) {
                    blockEntity.setShowArea(showArea);
                }
            }
        });
        return true;
    }
}