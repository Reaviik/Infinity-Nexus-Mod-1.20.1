package com.Infinity.Nexus.Mod.block.entity;

import com.Infinity.Nexus.Core.block.entity.WrappedHandler;
import com.Infinity.Nexus.Core.items.ModItems;
import com.Infinity.Nexus.Core.utils.ModUtils;
import com.Infinity.Nexus.Mod.block.custom.Compactor;
import com.Infinity.Nexus.Mod.block.custom.Tank;
import com.Infinity.Nexus.Mod.block.entity.wrappedHandlerMap.TankHandler;
import com.Infinity.Nexus.Mod.config.ConfigUtils;
import com.Infinity.Nexus.Mod.recipe.CompactorRecipes;
import com.Infinity.Nexus.Mod.recipe.PressRecipes;
import com.Infinity.Nexus.Mod.screen.tank.TankMenu;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.Connection;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.Containers;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.item.BucketItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.IFluidTank;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.fluids.capability.templates.FluidTank;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public class CompactorBlockEntity extends BlockEntity {
    private boolean redstone = false;
    private int progress = 0;
    private int maxProgress = 100;

    public CompactorBlockEntity(BlockPos pPos, BlockState pBlockState) {
        super(ModBlockEntities.COMPACTOR_BE.get(), pPos, pBlockState);
    }

    public void tick(Level pLevel, BlockPos pPos, BlockState pState) {
        if (pLevel.isClientSide()) {
            return;
        }
        boolean isPowered = isRedstonePowered(pPos);
        if(progress < maxProgress){
            progress++;
        }else {
            progress = 0;
            verifySpace(pLevel, pPos);
        }

        if (isPowered != redstone) {
            redstone = isPowered;
            if (isPowered && verifySpace(pLevel, pPos)) {
                craft(pLevel, pPos);
            }
        }

        setChanged();
    }

    private void craft(Level pLevel, BlockPos pPos) {
        Optional<CompactorRecipes> recipe = getCurrentRecipe();
        if(!recipe.isEmpty()) {
            AABB aabb = new AABB(pPos.above(4).east(2).south(2), pPos.above().west().north());
            List<ItemEntity> entities = level.getEntitiesOfClass(ItemEntity.class, aabb);
            ServerLevel level = (ServerLevel) getLevel();
            if (!entities.isEmpty()) {
                entities.get(0).remove(Entity.RemovalReason.DISCARDED);
                ItemStack result = recipe.get().getResultItem(pLevel.registryAccess());
                ItemEntity output = new ItemEntity(level, pPos.getX(), pPos.getY() + 2, pPos.getZ(), result.copy());
                level.addFreshEntity(output);
                removeBlocks();
            }
        }
    }
    private boolean  verifySpace(Level pLevel, BlockPos pPos) {
        boolean free = true;
        for (int x = -1; x <= 1; x++) {
            for (int z = -1; z <= 1; z++) {
                if (x == 0 && z == 0) continue;
                BlockPos adjacentPos = pPos.offset(x, 0, z);
                BlockState adjacentState = pLevel.getBlockState(adjacentPos);
                if (adjacentState.getBlock() != Blocks.BARRIER) {
                    free = false;
                }
            }
        }
        if(free){
            pLevel.setBlock(pPos, this.getBlockState().setValue(Compactor.LIT, 0), 3);
        }else{
            pLevel.setBlock(pPos, this.getBlockState().setValue(Compactor.LIT, 1), 3);
        }
        return free;
    }
    private void removeBlocks() {
        int centerX = this.worldPosition.getX();
        int centerY = this.worldPosition.getY() + 2;
        int centerZ = this.worldPosition.getZ();
        BlockPos center = new BlockPos(centerX, centerY, centerZ);
        for (int x = -1; x <= 1; x++) {
            for (int y = 1; y >= -1; y--) {
                for (int z = -1; z <= 1; z++) {
                    BlockPos pos = new BlockPos(centerX + x, centerY + y, centerZ + z);
                    if(!center.equals(pos)) {
                        level.destroyBlock(pos, false);
                    }
                }
            }
        }
    }



    private Optional<CompactorRecipes> getCurrentRecipe() {
        SimpleContainer inventory = new SimpleContainer(27);
        int centerX = this.worldPosition.getX();
        int centerY = this.worldPosition.getY() + 2;
        int centerZ = this.worldPosition.getZ();
        int index = 0;
        for (int y = -1; y <= 1; y++) {
            for (int z = -1; z <= 1; z++) {
                for (int x = -1; x <= 1; x++) {
                    BlockPos pos = new BlockPos(centerX + x, centerY + y, centerZ + z);

                    if (x == 0 && y == 0 && z == 0) {
                        AABB aabb = new AABB(pos.below().east(2).south(2), pos.above().north().west());
                        List<ItemEntity> entities = level.getEntitiesOfClass(ItemEntity.class, aabb);
                        if (!entities.isEmpty()) {
                            ItemStack centerItem = entities.get(0).getItem();
                            if (centerItem.getCount() == 1) {
                                inventory.setItem(index++, centerItem);
                            }
                        }
                    } else {
                        BlockState state = this.level.getBlockState(pos);
                        ItemStack item = new ItemStack(state.getBlock().asItem());
                        inventory.setItem(index++, item);
                    }
                }
            }
        }
        return this.level.getRecipeManager().getRecipeFor(CompactorRecipes.Type.INSTANCE, inventory, this.level);
    }



    private boolean isRedstonePowered(BlockPos pPos) {
        return this.level.hasNeighborSignal(pPos.north()) || this.level.hasNeighborSignal(pPos.south()) || this.level.hasNeighborSignal(pPos.west()) || this.level.hasNeighborSignal(pPos.east());
    }
    @Nullable
    @Override
    public Packet<ClientGamePacketListener> getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }

    @Override
    public CompoundTag getUpdateTag() {
        return saveWithFullMetadata();
    }

    @Override
    public void onDataPacket(Connection net, ClientboundBlockEntityDataPacket pkt) {
        super.onDataPacket(net, pkt);
    }

    public void onRemove() {
        //TODO break adjacent blocks
    }
    public void onPlace() {
        //TODO place adjacent blocks
    }
}