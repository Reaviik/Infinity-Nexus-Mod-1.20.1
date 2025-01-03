package com.Infinity.Nexus.Mod.block.entity;

import com.Infinity.Nexus.Mod.utils.ModUtilsMachines;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.Connection;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class DepotStoneBlockEntity extends DepotBlockEntityBase {
    AABB aabb = new AABB(worldPosition.offset(-1, 0, -1), worldPosition.offset(+2, 1, +2));
    public DepotStoneBlockEntity(BlockPos pPos, BlockState pBlockState) {
        super(ModBlockEntities.DEPOT_STONE_BE.get(), pPos, pBlockState);
    }
    @Override
    public Component getDisplayName() {
        return Component.translatable("block.infinity_nexus_mod.depot_stone");
    }

    @Override
    protected void place() {
        if (!hasProgressFinished()) {
            return;
        }

        ItemStack stack = this.itemHandler.getStackInSlot(0);
        if (!stack.isEmpty()) {
            for (int dx = -1; dx <= 1; dx++) {
                for (int dz = -1; dz <= 1; dz++) {
                    BlockPos targetPos = worldPosition.offset(dx, 0, dz);
                    if (canPlaceHere(targetPos) && targetPos != worldPosition) {
                        ItemStack stack2 = stack.copy();
                        stack2.setCount(1);

                        ItemEntity entity = new ItemEntity(
                                level,
                                targetPos.getX() + 0.5,
                                targetPos.getY() + 0.5,
                                targetPos.getZ() + 0.5,
                                stack2
                        );
                        entity.setDeltaMovement(Vec3.ZERO);
                        entity.setUnlimitedLifetime();
                        entity.setPickUpDelay(10);
                        level.addFreshEntity(entity);
                        ModUtilsMachines.sendParticlePath((ServerLevel) this.getLevel(),ParticleTypes.SCRAPE, worldPosition, targetPos, 0.5D, 0.2D, 0.5D);

                        this.itemHandler.getStackInSlot(0).shrink(1);
                        return;
                    }
                }
            }
        } else {
            hasEntities(worldPosition);
        }
    }

    protected boolean canPlaceHere(BlockPos pPos) {
        return hasEntity(pPos);
    }
    private boolean hasEntities(BlockPos pPos) {
        List<ItemEntity> entities = level.getEntitiesOfClass(ItemEntity.class, aabb);
        if (!entities.isEmpty()) {
            entities.forEach(entity -> {
                entity.setPickUpDelay(20);
            });
        }
        return entities.size() < 8;
    }


    @Override
    protected boolean canPlace(BlockPos pPos) {
        return hasEntities(pPos);
    }
}