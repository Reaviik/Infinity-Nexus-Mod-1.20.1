package com.Infinity.Nexus.Mod.block.entity;

import com.Infinity.Nexus.Core.items.ModItems;
import com.Infinity.Nexus.Mod.InfinityNexusMod;
import com.Infinity.Nexus.Mod.block.custom.Translocator;
import com.Infinity.Nexus.Mod.config.ConfigUtils;
import com.Infinity.Nexus.Mod.utils.ModUtilsMachines;
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
import net.minecraft.world.Containers;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.items.ItemStackHandler;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class TranslocatorBlockEntity extends BlockEntity {
    private final ItemStackHandler itemHandler = new ItemStackHandler(2) {
        @Override
        protected void onContentsChanged(int slot) {
            setChanged();
        }

        @Override
        public boolean isItemValid(int slot, @NotNull ItemStack stack) {
            return switch (slot) {
                //TODO FIX
                case 0 -> false;
                case 1 -> stack.getItem() == ModItems.PUSHER_UPGRADE.get();
                default -> super.isItemValid(slot, stack);
            };
        }
    };
    private static final int INPUT_SLOT = 0;
    private static final int UPGRADE_SLOT = 1;

    private int progress = 0;
    private int maxProgress = ConfigUtils.translocator_delay;
    private int mode = 0;

    protected final ContainerData data;
    public TranslocatorBlockEntity(BlockPos pPos, BlockState pBlockState) {
        super(ModBlockEntities.TRASLOCATOR_BE.get(), pPos, pBlockState);
        this.data = new ContainerData() {
            @Override
            public int get(int pIndex) {
                return switch (pIndex) {
                    case 0 -> TranslocatorBlockEntity.this.progress;
                    case 1 -> TranslocatorBlockEntity.this.maxProgress;
                    case 2 -> TranslocatorBlockEntity.this.mode;
                    default -> 0;
                };
            }
            @Override
            public void set(int pIndex, int pValue) {
                switch (pIndex) {
                    case 0 -> TranslocatorBlockEntity.this.progress = pValue;
                    case 1 -> TranslocatorBlockEntity.this.maxProgress = pValue;
                    case 2 -> TranslocatorBlockEntity.this.mode = pValue;
                }
            }

            @Override
            public int getCount() {
                return 3;
            }
        };
    }



    @Override
    public void onLoad() {
        super.onLoad();
    }

    @Override
    public void invalidateCaps() {
        super.invalidateCaps();
    }

    public static int getInputSlot() {
        return INPUT_SLOT;
    }

    public static int getUpgradeSlot() {
        return UPGRADE_SLOT;
    }

    public void drops() {
        SimpleContainer inventory = new SimpleContainer(itemHandler.getSlots());
        for (int i = 0; i < itemHandler.getSlots(); i++) {
            inventory.setItem(i, itemHandler.getStackInSlot(i));
        }
        Containers.dropContents(this.level, this.worldPosition, inventory);
    }

    @Override
    protected void saveAdditional(CompoundTag pTag) {
        pTag.put("inventory", itemHandler.serializeNBT());
        pTag.putInt("translocator.progress", progress);
        pTag.putInt("translocator.max_progress", maxProgress);
        pTag.putInt("translocator.mode", mode);

        super.saveAdditional(pTag);
    }

    @Override
    public void load(CompoundTag pTag) {
        super.load(pTag);
        itemHandler.deserializeNBT(pTag.getCompound("inventory"));
        progress = pTag.getInt("translocator.progress");
        maxProgress = pTag.getInt("translocator.max_progress");
        mode = pTag.getInt("translocator.mode");
    }

    public void tick(Level pLevel, BlockPos pPos, BlockState pState) {
        if (pLevel.isClientSide()) {
            return;
        }
        if (mode == 0 && !isInputSlotEmpty()) {
            if(hasProgressFinished()) {
                depositItem(pLevel, pPos);
                resetProgress();
            }else{
                increaseProgress();
            }
        } else {
            if(canSend()) {
                if (!isInputSlotEmpty()) {
                    if(hasProgressFinished()) {
                        resetProgress();
                        sendItem(pLevel);
                    }
                    increaseProgress();
                } else {
                    pullItem(pLevel, pPos, pState);
                }
            }
        }
        updateLit(pLevel, pState);
        setChanged(pLevel, pPos, pState);
    }

    private void pullItem(Level pLevel, BlockPos pPos, BlockState pState) {
        BlockEntity entity = getInventoryPos(pLevel, pPos, pState);
        if (entity != null) {
            entity.getCapability(ForgeCapabilities.ITEM_HANDLER, Direction.UP).ifPresent(handler -> {
                for (int i = 0; i < handler.getSlots(); i++) {
                    ItemStack stackInSlot = handler.getStackInSlot(i);
                    if (!stackInSlot.isEmpty()) {
                        if (!handler.extractItem(i, 1, true).isEmpty()) {
                            if(hasProgressFinished()) {
                                this.itemHandler.setStackInSlot(INPUT_SLOT, stackInSlot.copy());
                                handler.extractItem(i, stackInSlot.getCount(), false);
                                break;
                            }else{
                                increaseProgress();
                            }
                        }
                    }
                }
            });
        }
    }

    private void depositItem(Level level, BlockPos pos) {
        BlockEntity entity = getInventoryPos(level, pos, getBlockState());
        if (entity == null) return;

        entity.getCapability(ForgeCapabilities.ITEM_HANDLER, Direction.DOWN).ifPresent(handler -> {
            ItemStack stackToDeposit = itemHandler.getStackInSlot(INPUT_SLOT);
            if (!stackToDeposit.isEmpty()) {
                for (int i = 0; i < handler.getSlots(); i++) {
                    ItemStack slotStack = handler.getStackInSlot(i);
                    if (slotStack.isEmpty() || slotStack.getItem() == stackToDeposit.getItem()) {
                        int spaceAvailable = handler.getSlotLimit(i) - slotStack.getCount();
                        int toInsert = Math.min(stackToDeposit.getCount(), spaceAvailable);
                        boolean canInsertAmount = (toInsert + slotStack.getCount()) <= handler.getStackInSlot(i).getMaxStackSize();

                        if (toInsert > 0 && canInsertAmount) {
                            ItemStack stackToInsert = stackToDeposit.split(toInsert);
                            handler.insertItem(i, stackToInsert, false);
                            itemHandler.setStackInSlot(INPUT_SLOT, stackToDeposit);
                        }
                        if (stackToDeposit.isEmpty()) {
                            break;
                        }
                    }
                }
            }
        });
    }


    private void sendItem(Level level) {
        BlockPos targetPos = getDestination();
        BlockEntity entity = level.getBlockEntity(targetPos);
        if (entity instanceof TranslocatorBlockEntity translocator) {
            ItemStack stackToSend = this.itemHandler.getStackInSlot(INPUT_SLOT);
            if (stackToSend != null && !stackToSend.isEmpty()) {
                if (translocator.receiveItem(stackToSend)) {
                    this.itemHandler.setStackInSlot(INPUT_SLOT, ItemStack.EMPTY);
                    ModUtilsMachines.sendParticlePath((ServerLevel) this.getLevel(), ParticleTypes.ELECTRIC_SPARK, worldPosition, getDestination(), 0.5D, 0.5D, 0.5D);
                }
            }
        }
    }

    public boolean receiveItem(ItemStack stack) {
        if (!stack.isEmpty() && isInputSlotEmpty()) {
            this.itemHandler.setStackInSlot(INPUT_SLOT, stack);
            if(canSend()){
                progress = ConfigUtils.translocator_skip_progress;
            }
            setChanged();
            return true;
        } else {
            return false;
        }
    }


    private BlockEntity getInventoryPos(Level pLevel, BlockPos pPos, BlockState pState) {
        Direction direction = pState.getValue(Translocator.FACING);
        return pLevel.getBlockEntity(pPos.relative(direction.getOpposite()));
    }
    private boolean canLink(BlockPos pos) {
        return (int) Math.sqrt(this.getBlockPos().distSqr(pos)) < ConfigUtils.translocator_range_limit && this.getLevel().isLoaded(pos);
    }
    private void resetProgress() {
        progress = 0;
        maxProgress = ConfigUtils.translocator_delay;
    }

    private void increaseProgress() {
        if (progress < maxProgress) {
            progress++;
        }
    }

    private boolean hasProgressFinished() {
        return progress >= maxProgress;
    }

    private BlockPos getDestination() {
        int[] cords = getCords();
        if(cords.length == 3 && canLink(new BlockPos(cords[0], cords[1], cords[2]))){
            return new BlockPos(cords[0], cords[1], cords[2]);
        }
        return null;
    }

    private int[] getCords() {
        if(!this.itemHandler.getStackInSlot(UPGRADE_SLOT).isEmpty()){
            return this.itemHandler.getStackInSlot(UPGRADE_SLOT).getOrCreateTag().getIntArray("cords");
        }
        return new int[0];
    }

    private boolean canSend(){
        return getCords().length == 3;
    }


    private boolean isInputSlotEmpty() {
        return this.itemHandler.getStackInSlot(INPUT_SLOT).isEmpty();
    }

    private void updateLit(Level level, BlockState state) {
        int lit = 6;
        int step = maxProgress / 5;

        if (mode == 1) {
            if (canSend()) {
                lit = (progress >= 4 * step) ? 5 : (progress >= 3 * step) ? 4 : (progress >= 2 * step) ? 3 : (progress >= step) ? 2 : 1;
            }
        } else if (mode == 0) {
            if (!isInputSlotEmpty()) {
                lit = (progress <= step) ? 5 : (progress <= 2 * step) ? 4 : (progress <= 3 * step) ? 3 : (progress <= 4 * step) ? 2 : 6;
            }
        }

        setLit(level, state, lit);
    }


    private void setLit(Level level, BlockState state, int lit) {
        if(state.getValue(Translocator.LIT) != lit) {
            level.setBlock(this.getBlockPos(), this.getBlockState().setValue(Translocator.LIT, lit), 3);
        }
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
    public void toggleMode(ItemStack stack, Player player, boolean shift, BlockPos pos) {
        if (stack.is(ModItems.PUSHER_UPGRADE.get())) {
            if (!this.itemHandler.getStackInSlot(UPGRADE_SLOT).isEmpty()) {
                ItemEntity entity = new ItemEntity(level, player.getX(), player.getY(), player.getZ(), this.itemHandler.getStackInSlot(UPGRADE_SLOT));
                level.addFreshEntity(entity);
                this.itemHandler.setStackInSlot(UPGRADE_SLOT, ItemStack.EMPTY);
                mode = 0;
                player.sendSystemMessage(Component.literal(InfinityNexusMod.message).append(Component.translatable("tooltip.infinity_nexus.translocator_receive")));
            } else {
                this.itemHandler.setStackInSlot(UPGRADE_SLOT, stack);
                mode = 1;
                player.getMainHandItem().shrink(1);
                player.sendSystemMessage(Component.literal(InfinityNexusMod.message).append(Component.translatable("tooltip.infinity_nexus.translocator_send")));
            }
        }
    }

    public void setCords(int[] cords, Player player, int[] cord) {
        if(itemHandler.getStackInSlot(UPGRADE_SLOT).is(ModItems.PUSHER_UPGRADE.get())){
            this.itemHandler.getStackInSlot(UPGRADE_SLOT).getOrCreateTag().putIntArray("cords", cords);
            ModUtilsMachines.sendParticlePath((ServerLevel) this.getLevel(), ParticleTypes.SCRAPE, worldPosition, new BlockPos(cords[0], cords[1], cords[2]), 0.5D, 0.2D, 0.5D);
            player.sendSystemMessage(Component.literal(InfinityNexusMod.message).append(Component.translatable("tooltip.infinity_nexus.translocator_link")));
            player.getMainHandItem().getOrCreateTag().putIntArray("cords", cord);
        }else{
            player.sendSystemMessage(Component.literal(InfinityNexusMod.message).append(Component.translatable("tooltip.infinity_nexus.translocator_link_fail")));
        }
    }
}