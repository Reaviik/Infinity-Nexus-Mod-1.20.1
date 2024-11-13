package com.Infinity.Nexus.Mod.block.entity;

import com.Infinity.Nexus.Core.block.entity.WrappedHandler;
import com.Infinity.Nexus.Core.block.entity.common.SetMachineLevel;
import com.Infinity.Nexus.Core.fakePlayer.IFFakePlayer;
import com.Infinity.Nexus.Core.utils.ModUtils;
import com.Infinity.Nexus.Mod.block.custom.Placer;
import com.Infinity.Nexus.Mod.block.entity.wrappedHandlerMap.PlacerHandler;
import com.Infinity.Nexus.Mod.config.ConfigUtils;
import com.Infinity.Nexus.Mod.screen.placer.PlacerMenu;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.Connection;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.Containers;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import net.minecraftforge.registries.ForgeRegistries;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Map;

public class PlacerBlockEntity extends BlockEntity implements MenuProvider {
    private final ItemStackHandler itemHandler = new ItemStackHandler(2) {
        @Override
        protected void onContentsChanged(int slot) {
            setChanged();
        }

        @Override
        public boolean isItemValid(int slot, @NotNull ItemStack stack) {
            return switch (slot) {
                case 0 -> !ModUtils.isUpgrade(stack) && !ModUtils.isComponent(stack);
                case 1 -> ModUtils.isComponent(stack);
                default -> super.isItemValid(slot, stack);
            };
        }
    };
    private static final int INPUT_SLOT = 0;
    private static final int COMPONENT_SLOT = 1;

    private int progress = 0;
    private int maxProgress = 20;
    private int blocked = 0;

    private LazyOptional<IItemHandler> lazyItemHandler = LazyOptional.empty();

    private final Map<Direction, LazyOptional<WrappedHandler>> directionWrappedHandlerMap =
            Map.of( Direction.UP, LazyOptional.of(() -> new WrappedHandler(itemHandler, (i) -> PlacerHandler.extract(i, Direction.UP), PlacerHandler::insert)),
                    Direction.DOWN, LazyOptional.of(() -> new WrappedHandler(itemHandler, (i) -> PlacerHandler.extract(i, Direction.DOWN), PlacerHandler::insert)),
                    Direction.NORTH, LazyOptional.of(() -> new WrappedHandler(itemHandler, (i) -> PlacerHandler.extract(i, Direction.NORTH), PlacerHandler::insert)),
                    Direction.SOUTH, LazyOptional.of(() -> new WrappedHandler(itemHandler, (i) -> PlacerHandler.extract(i, Direction.SOUTH), PlacerHandler::insert)),
                    Direction.EAST, LazyOptional.of(() -> new WrappedHandler(itemHandler, (i) -> PlacerHandler.extract(i, Direction.EAST), PlacerHandler::insert)),
                    Direction.WEST, LazyOptional.of(() -> new WrappedHandler(itemHandler, (i) -> PlacerHandler.extract(i, Direction.WEST), PlacerHandler::insert)));

    protected final ContainerData data;
    public PlacerBlockEntity(BlockPos pPos, BlockState pBlockState) {
        super(ModBlockEntities.PLACER_BE.get(), pPos, pBlockState);
        this.data = new ContainerData() {
            @Override
            public int get(int pIndex) {
                return switch (pIndex) {
                    case 0 -> PlacerBlockEntity.this.progress;
                    case 1 -> PlacerBlockEntity.this.maxProgress;
                    case 2 -> PlacerBlockEntity.this.blocked;
                    default -> 0;
                };
            }

            @Override
            public void set(int pIndex, int pValue) {
                switch (pIndex) {
                    case 0 -> PlacerBlockEntity.this.progress = pValue;
                    case 1 -> PlacerBlockEntity.this.maxProgress = pValue;
                    case 2 -> PlacerBlockEntity.this.blocked = pValue;
                }
            }

            @Override
            public int getCount() {
                return 3;
            }
        };
    }


    @Override
    public @NotNull <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {

        if (cap == ForgeCapabilities.ITEM_HANDLER) {
            if (side == null) {
                return lazyItemHandler.cast();
            }

            if (directionWrappedHandlerMap.containsKey(side)) {
                Direction localDir = this.getBlockState().getValue(Placer.FACING);

                if (side == Direction.UP || side == Direction.DOWN) {
                    return directionWrappedHandlerMap.get(side).cast();
                }

                return switch (localDir) {
                    default -> directionWrappedHandlerMap.get(side.getOpposite()).cast();
                    case EAST -> directionWrappedHandlerMap.get(side.getClockWise()).cast();
                    case SOUTH -> directionWrappedHandlerMap.get(side).cast();
                    case WEST -> directionWrappedHandlerMap.get(side.getCounterClockWise()).cast();
                };
            }
        }


        return super.getCapability(cap, side);
    }

    @Override
    public void onLoad() {
        super.onLoad();
        lazyItemHandler = LazyOptional.of(() -> itemHandler);
    }

    @Override
    public void invalidateCaps() {
        super.invalidateCaps();
        lazyItemHandler.invalidate();
    }

    public static int getComponentSlot() {
        return COMPONENT_SLOT;
    }

    public void drops() {
        SimpleContainer inventory = new SimpleContainer(itemHandler.getSlots());
        for (int i = 0; i < itemHandler.getSlots(); i++) {
            inventory.setItem(i, itemHandler.getStackInSlot(i));
        }
        Containers.dropContents(this.level, this.worldPosition, inventory);
    }

    @Override
    public Component getDisplayName() {
        return Component.translatable("block.infinity_nexus_mod.placer");
    }
    @Nullable
    @Override
    public AbstractContainerMenu createMenu(int pContainerId, Inventory pPlayerInventory, Player pPlayer) {
        return new PlacerMenu(pContainerId, pPlayerInventory, this, this.data);
    }
    @Override
    protected void saveAdditional(CompoundTag pTag) {
        pTag.put("inventory", itemHandler.serializeNBT());
        pTag.putInt("placer.progress", progress);
        pTag.putInt("placer.max_progress", maxProgress);
        pTag.putInt("placer.blocked", blocked);

        super.saveAdditional(pTag);
    }

    @Override
    public void load(CompoundTag pTag) {
        super.load(pTag);
        itemHandler.deserializeNBT(pTag.getCompound("inventory"));
        progress = pTag.getInt("placer.progress");
        maxProgress = pTag.getInt("placer.max_progress");
        blocked = pTag.getInt("placer.blocked");
    }


    public ItemStack getRenderStack(int slot){
        return itemHandler.getStackInSlot(slot).isEmpty() ? ItemStack.EMPTY : itemHandler.getStackInSlot(slot).getItem().getDefaultInstance();
    }
    public void tick(Level pLevel, BlockPos pPos, BlockState pState) {
        if (pLevel.isClientSide()) {
            return;
        }

        int machineLevel = getMachineLevel()-1 <= 0 ? 0 : getMachineLevel()-1;
        if(pState.getValue(Placer.LIT) != machineLevel){
            pLevel.setBlock(pPos, pState.setValue(Placer.LIT, machineLevel), 3);
        }

        if (isRedstonePowered(pPos)) {
            return;
        }
        increaseProgress();
        if (!hasProgressFinished()) {
            return;
        }
        resetProgress();
        if (!hasRecipe(pPos)) {
            data.set(2, 1);
            return;
        }
        data.set(2, 0);
        craft(pLevel, pPos, pState , machineLevel);
        setChanged(pLevel, pPos, pState);
    }

    private void resetProgress() {
        progress = 0;
        maxProgress = 20;
    }

    private void increaseProgress() {
        if (progress < maxProgress) {
            progress++;
        }
    }

    private boolean hasProgressFinished() {
        return progress >= maxProgress;
    }

    private void craft(Level pLevel, BlockPos pPos, BlockState pState, int machineLevel) {
        if(pLevel instanceof ServerLevel level) {
            Direction direction = pState.getValue(Placer.FACING);
            BlockPos placePos = getPlacePos(pPos, direction);
            if(!hasEntity(placePos)) {
                ItemStack stack = itemHandler.getStackInSlot(INPUT_SLOT).copy();
                IFFakePlayer player = new IFFakePlayer(level);
                player.placeBlock(this.level, placePos, stack, direction);
                itemHandler.extractItem(INPUT_SLOT, 1, false);
            }
        }
    }

    private boolean hasEntity(BlockPos pPos) {
        return !level.getEntitiesOfClass(Player.class, new AABB(pPos)).isEmpty();

    }
    private boolean canPlace(ItemStack stack) {
        return !ConfigUtils.list_of_non_placeable_blocks.stream()
                .map(structure -> ForgeRegistries.ITEMS.getValue(new ResourceLocation(structure)))
                .anyMatch(structureItem -> structureItem == stack.getItem());
    }

    private boolean isFree(BlockPos pPos) {
        return level.getBlockState(pPos) == Blocks.AIR.defaultBlockState();
    }

    private BlockPos getPlacePos(BlockPos pPos, Direction pDirection) {
        return switch (pDirection) {
            case DOWN -> pPos.below();
            case UP -> pPos.above();
            case NORTH -> pPos.north();
            case SOUTH -> pPos.south();
            case WEST -> pPos.west();
            case EAST -> pPos.east();
        };
    }


    private boolean hasRecipe(BlockPos pPos) {
        ItemStack stack = itemHandler.getStackInSlot(INPUT_SLOT);
        return itemHandler.getStackInSlot(COMPONENT_SLOT).getCount() > 0
                && stack.getItem() instanceof BlockItem
                && isFree(getPlacePos(pPos, this.getBlockState().getValue(Placer.FACING)))
                && !(stack.hasTag())
                && canPlace(stack);
    }

    private boolean isRedstonePowered(BlockPos pPos) {
        return this.level.hasNeighborSignal(pPos);
    }
    private int getMachineLevel(){
        try {
            return ModUtils.getComponentLevel(this.itemHandler.getStackInSlot(COMPONENT_SLOT));
        }catch (Exception e) {
            return 0;
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
    public void setMachineLevel(ItemStack itemStack, Player player) {
        SetMachineLevel.setMachineLevel(itemStack, player, this, COMPONENT_SLOT, this.itemHandler);
    }
}