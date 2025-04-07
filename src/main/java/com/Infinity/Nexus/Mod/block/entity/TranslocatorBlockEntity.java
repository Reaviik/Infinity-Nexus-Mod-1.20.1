package com.Infinity.Nexus.Mod.block.entity;

import com.Infinity.Nexus.Mod.config.ConfigUtils;
import com.Infinity.Nexus.Mod.utils.ModUtilsMachines;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.Connection;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import org.jetbrains.annotations.Nullable;

public class TranslocatorBlockEntity extends TranslocatorBlockEntityBase {


    public TranslocatorBlockEntity(BlockPos pPos, BlockState pBlockState) {
        super(pPos, pBlockState);
    }

    @Override
    public void tick(Level pLevel, BlockPos pPos, BlockState pState) {
        if (pLevel.isClientSide()) {
            return;
        }
        if (mode == 0 && !isInputSlotEmpty()) {
            if(hasProgressFinished()) {
                depositItem(pLevel, pPos);
            }else{
                increaseProgress();
            }
        } else {
            if(canSend()) {
                if (!isInputSlotEmpty()) {
                    if(hasProgressFinished()) {
                        resetProgress();
                        sendItem(pLevel);
                        upgradeStep();
                    }
                    increaseProgress();
                } else {
                    if(hasProgressFinished() && isInputSlotEmpty()) {
                        resetProgress();
                        pullItem(pLevel, pPos, pState);
                    }
                    increaseProgress();
                }
            }
        }
        updateLit(pLevel, pState);
        setChanged(pLevel, pPos, pState);
    }

    private void pullItem(Level pLevel, BlockPos pPos, BlockState pState) {
        BlockEntity entity = getInventoryPos(pLevel, pPos, pState);
        if (entity == null) return;

        entity.getCapability(ForgeCapabilities.ITEM_HANDLER, Direction.UP).ifPresent(handler -> {
            if (filter != null && filter.length > 0) {
                boolean itemPulled = false;
                int startIdx = filterIndex;

                for (int i = 0; i < filter.length; i++) {
                    int currentIdx = (startIdx + i) % filter.length;
                    String targetItem = filter[currentIdx];

                    for (int slot = 0; slot < handler.getSlots(); slot++) {
                        ItemStack stackInSlot = handler.getStackInSlot(slot);
                        if (stackInSlot.isEmpty()) continue;

                        String itemName = stackInSlot.getItem().builtInRegistryHolder().key().location().toString();
                        if (!itemName.equals(targetItem)) continue;

                        ItemStack currentStack = this.itemHandler.getStackInSlot(INPUT_SLOT);
                        int maxStackSize = Math.min(stackInSlot.getMaxStackSize(), this.itemHandler.getSlotLimit(INPUT_SLOT));

                        if (currentStack.isEmpty()) {
                            int amountToPull = Math.min(stackInSlot.getCount(), maxStackSize);
                            ItemStack extracted = handler.extractItem(slot, amountToPull, false);
                            if (!extracted.isEmpty()) {
                                this.itemHandler.setStackInSlot(INPUT_SLOT, extracted);
                                resetProgress();
                                itemPulled = true;
                                filterIndex = (currentIdx + 1) % filter.length; // Atualiza o índice para o próximo item
                                setChanged();
                                break;
                            }
                        } else if (ItemStack.isSameItemSameTags(currentStack, stackInSlot)) {
                            int spaceAvailable = maxStackSize - currentStack.getCount();
                            if (spaceAvailable > 0) {
                                int amountToPull = Math.min(stackInSlot.getCount(), spaceAvailable);
                                ItemStack extracted = handler.extractItem(slot, amountToPull, false);
                                if (!extracted.isEmpty()) {
                                    currentStack.grow(extracted.getCount());
                                    this.itemHandler.setStackInSlot(INPUT_SLOT, currentStack);
                                    resetProgress();
                                    itemPulled = true;
                                    filterIndex = (currentIdx + 1) % filter.length;
                                    setChanged();
                                    break;
                                }
                            }
                        }

                        if (itemPulled) break;
                    }
                    if (itemPulled) break;
                }
            } else {
                for (int slot = 0; slot < handler.getSlots(); slot++) {
                    ItemStack stackInSlot = handler.getStackInSlot(slot);
                    if (stackInSlot.isEmpty() || !isFiltered(stackInSlot)) continue;

                    ItemStack currentStack = this.itemHandler.getStackInSlot(INPUT_SLOT);
                    int maxStackSize = Math.min(stackInSlot.getMaxStackSize(), this.itemHandler.getSlotLimit(INPUT_SLOT));

                    if (currentStack.isEmpty()) {
                        int amountToPull = Math.min(stackInSlot.getCount(), maxStackSize);
                        ItemStack extracted = handler.extractItem(slot, amountToPull, false);
                        if (!extracted.isEmpty()) {
                            this.itemHandler.setStackInSlot(INPUT_SLOT, extracted);
                            resetProgress();
                            break;
                        }
                    } else if (ItemStack.isSameItemSameTags(currentStack, stackInSlot)) {
                        int spaceAvailable = maxStackSize - currentStack.getCount();
                        if (spaceAvailable > 0) {
                            int amountToPull = Math.min(stackInSlot.getCount(), spaceAvailable);
                            ItemStack extracted = handler.extractItem(slot, amountToPull, false);
                            if (!extracted.isEmpty()) {
                                currentStack.grow(extracted.getCount());
                                this.itemHandler.setStackInSlot(INPUT_SLOT, currentStack);
                                resetProgress();
                                break;
                            }
                        }
                    }
                }
            }
        });
    }


    private void depositItem(Level level, BlockPos pos) {
        BlockEntity entity = getInventoryPos(level, pos, getBlockState());
        if (entity == null) return;

        entity.getCapability(ForgeCapabilities.ITEM_HANDLER, Direction.DOWN).ifPresent(handler -> {
            ItemStack stackToDeposit = itemHandler.getStackInSlot(INPUT_SLOT);
            if (stackToDeposit.isEmpty()) return;

            for (int i = 0; i < handler.getSlots(); i++) {
                ItemStack slotStack = handler.getStackInSlot(i);
                if ((slotStack.isEmpty() || ItemStack.isSameItemSameTags(slotStack, stackToDeposit))
                        && handler.isItemValid(i, stackToDeposit)) {

                    int maxStackSize = Math.min(handler.getSlotLimit(i), stackToDeposit.getMaxStackSize());
                    int spaceAvailable = maxStackSize - slotStack.getCount();
                    int toInsert = Math.min(stackToDeposit.getCount(), spaceAvailable);

                    if (toInsert > 0) {
                        ItemStack stackToInsert = stackToDeposit.split(toInsert);
                        ItemStack leftover = handler.insertItem(i, stackToInsert, false);
                        if (!leftover.isEmpty()) {
                            stackToDeposit.grow(leftover.getCount());
                        }
                        itemHandler.setStackInSlot(INPUT_SLOT, stackToDeposit);
                        resetProgress();
                    }
                    if (stackToDeposit.isEmpty()) {
                        break;
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
                    ModUtilsMachines.sendParticlePath((ServerLevel) this.getLevel(), ParticleTypes.ELECTRIC_SPARK, worldPosition, targetPos, 0.5D, 0.5D, 0.5D);
                }else{
                    depositItem(level, worldPosition);
                    progress = maxProgress - 5;
                }
            }
        }
    }

    public boolean receiveItem(ItemStack stack) {
        if (stack.isEmpty() || !isInputSlotEmpty() || !isFiltered(stack)) {
            return false;
        }

        ItemStack currentStack = this.itemHandler.getStackInSlot(INPUT_SLOT);

        if (currentStack.isEmpty()) {
            int stackLimit = Math.min(stack.getMaxStackSize(), this.itemHandler.getSlotLimit(INPUT_SLOT));
            ItemStack toInsert = stack.copy();
            toInsert.setCount(Math.min(toInsert.getCount(), stackLimit));
            this.itemHandler.setStackInSlot(INPUT_SLOT, toInsert);
        } else if (ItemStack.isSameItemSameTags(currentStack, stack)) {
            int maxStackSize = Math.min(currentStack.getMaxStackSize(), this.itemHandler.getSlotLimit(INPUT_SLOT));
            int newCount = currentStack.getCount() + stack.getCount();

            if (newCount <= maxStackSize) {
                currentStack.grow(stack.getCount());
            } else {
                int remaining = maxStackSize - currentStack.getCount();
                currentStack.grow(remaining);
            }
            this.itemHandler.setStackInSlot(INPUT_SLOT, currentStack);
        } else {
            return false;
        }

        if (canSend()) {
            progress = ConfigUtils.translocator_skip_progress;
        }

        setChanged();
        return true;
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
}