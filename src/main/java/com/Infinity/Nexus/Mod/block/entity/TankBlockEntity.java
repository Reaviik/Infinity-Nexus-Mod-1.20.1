package com.Infinity.Nexus.Mod.block.entity;

import com.Infinity.Nexus.Core.block.entity.WrappedHandler;
import com.Infinity.Nexus.Core.items.ModItems;
import com.Infinity.Nexus.Core.utils.ModUtils;
import com.Infinity.Nexus.Mod.block.custom.Tank;
import com.Infinity.Nexus.Mod.block.entity.wrappedHandlerMap.TankHandler;
import com.Infinity.Nexus.Mod.config.ConfigUtils;
import com.Infinity.Nexus.Mod.screen.tank.TankMenu;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.Connection;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.Containers;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.item.BucketItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.IFluidTank;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.fluids.capability.IFluidHandlerItem;
import net.minecraftforge.fluids.capability.templates.FluidTank;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Map;

public class TankBlockEntity extends BlockEntity implements MenuProvider {
    private final ItemStackHandler itemHandler = new ItemStackHandler(3) {
        @Override
        protected void onContentsChanged(int slot) {
            setChanged();
        }

        @Override
        public boolean isItemValid(int slot, @NotNull ItemStack stack) {
            return switch (slot) {
                case 0,1 -> !ModUtils.isUpgrade(stack) && !ModUtils.isComponent(stack);
                case 2 -> ModUtils.isUpgrade(stack);
                default -> false;
            };
        }
    };

    private static final int FLUID_SLOT = 0;
    private static final int OUTPUT_FLUID_SLOT = 1;
    private static final int FLUID_CAPACITY = ConfigUtils.tank_capacity;

    private final FluidTank FLUID_STORAGE = createFluidStorage();

    private FluidTank createFluidStorage() {
        return new FluidTank(FLUID_CAPACITY) {
            @Override
            public void onContentsChanged() {
                setChanged();
                if(!level.isClientSide()) {
                    level.sendBlockUpdated(getBlockPos(), getBlockState(), getBlockState(), 3);
                }
            }

            @Override
            public boolean isFluidValid(FluidStack stack) {
                return true;
            }
        };
    }

    private LazyOptional<IItemHandler> lazyItemHandler = LazyOptional.empty();
    private LazyOptional<IFluidHandler> lazyFluidHandler = LazyOptional.empty();

    private final Map<Direction, LazyOptional<WrappedHandler>> directionWrappedHandlerMap =
            Map.of(
                    Direction.UP, LazyOptional.of(() -> new WrappedHandler(itemHandler, (i) -> TankHandler.extract(i, Direction.UP), TankHandler::insert)),
                    Direction.DOWN, LazyOptional.of(() -> new WrappedHandler(itemHandler, (i) -> TankHandler.extract(i, Direction.DOWN), TankHandler::insert)),
                    Direction.NORTH, LazyOptional.of(() -> new WrappedHandler(itemHandler, (i) -> TankHandler.extract(i, Direction.NORTH), TankHandler::insert)),
                    Direction.SOUTH, LazyOptional.of(() -> new WrappedHandler(itemHandler, (i) -> TankHandler.extract(i, Direction.SOUTH), TankHandler::insert)),
                    Direction.EAST, LazyOptional.of(() -> new WrappedHandler(itemHandler, (i) -> TankHandler.extract(i, Direction.EAST), TankHandler::insert)),
                    Direction.WEST, LazyOptional.of(() -> new WrappedHandler(itemHandler, (i) -> TankHandler.extract(i, Direction.WEST), TankHandler::insert)));

    protected final ContainerData data;
    private int endless = 0;

    public TankBlockEntity(BlockPos pPos, BlockState pBlockState) {
        super(ModBlockEntities.TANK_BE.get(), pPos, pBlockState);
        this.data = new ContainerData() {
            @Override
            public int get(int pIndex) {
                return switch (pIndex) {
                    case 0 -> TankBlockEntity.this.endless;
                    case 1 -> FLUID_STORAGE.getFluidAmount();
                    default -> 0;
                };
            }

            @Override
            public void set(int pIndex, int pValue) {
                switch (pIndex) {
                    case 0 -> TankBlockEntity.this.endless = pValue;
                }
            }

            @Override
            public int getCount() {
                return 2;
            }
        };
    }

    @Override
    public @NotNull <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {

        if (cap == ForgeCapabilities.FLUID_HANDLER) {
            return lazyFluidHandler.cast();
        }
        if (cap == ForgeCapabilities.ITEM_HANDLER) {
            if (side == null) {
                return lazyItemHandler.cast();
            }

            if (directionWrappedHandlerMap.containsKey(side)) {
                Direction localDir = this.getBlockState().getValue(Tank.FACING);

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
        lazyFluidHandler = LazyOptional.of(() -> FLUID_STORAGE);

    }

    @Override
    public void invalidateCaps() {
        super.invalidateCaps();
        lazyItemHandler.invalidate();
        lazyFluidHandler.invalidate();
    }

    public void drops() {
        SimpleContainer inventory = new SimpleContainer(itemHandler.getSlots());
        if(!FLUID_STORAGE.getFluid().isEmpty()) {
            ItemStack itemStack = new ItemStack(this.getBlockState().getBlock().asItem());
            itemStack.addTagElement("Fluid", FLUID_STORAGE.getFluid().writeToNBT(new CompoundTag()));

            ItemEntity itemTank = new ItemEntity(this.level, this.worldPosition.getX(), this.worldPosition.getY(), this.worldPosition.getZ(), itemStack);
            this.level.addFreshEntity(itemTank);
        }else{
            ItemEntity itemTank = new ItemEntity(this.level, this.worldPosition.getX(), this.worldPosition.getY(), this.worldPosition.getZ(), new ItemStack(this.getBlockState().getBlock().asItem()));
            this.level.addFreshEntity(itemTank);
        }
        for (int i = 0; i < itemHandler.getSlots(); i++) {
            inventory.setItem(i, itemHandler.getStackInSlot(i));
        }
        Containers.dropContents(this.level, this.worldPosition, inventory);
    }

    @Override
    public Component getDisplayName() {
        String endless = this.endless == 1 ? Component.translatable("tooltip.infinity_nexus_mod.tank_endless").getString() : "";
        return Component.translatable("block.infinity_nexus_mod.tank").append(": ").append(FLUID_STORAGE.getFluid().getDisplayName()).append(" ").append(endless);
    }

    @Nullable
    @Override
    public AbstractContainerMenu createMenu(int pContainerId, Inventory pPlayerInventory, Player pPlayer) {
        return new TankMenu(pContainerId, pPlayerInventory, this, this.data);
    }
    public static long getFluidCapacity() {
        return FLUID_CAPACITY;
    }


    @Override
    protected void saveAdditional(CompoundTag pTag) {
        pTag.put("inventory", itemHandler.serializeNBT());
        pTag.putInt("tank.progress", endless);
        pTag = FLUID_STORAGE.writeToNBT(pTag);

        super.saveAdditional(pTag);
    }

    @Override
    public void load(CompoundTag pTag) {
        super.load(pTag);
        itemHandler.deserializeNBT(pTag.getCompound("inventory"));
        endless = pTag.getInt("tank.progress");
        FLUID_STORAGE.readFromNBT(pTag);
    }

    public FluidStack getFluid() {
        return FLUID_STORAGE.getFluid();
    }
    public void tick(Level pLevel, BlockPos pPos, BlockState pState) {
        try {
            if (pLevel.isClientSide) {
                return;
            }
            if(endless == 1) {
                if(pState.getValue(Tank.LIT) != 1){
                    pLevel.setBlock(pPos, pState.setValue(Tank.LIT, 1), 3);
                }
            }
            fillUpOnFluid();
            ejectFluid();
            verifyEndless();
            setChanged(pLevel, pPos, pState);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private void ejectFluid() {
        if(itemHandler.getStackInSlot(2).is(ModItems.PUSHER_UPGRADE.get())) {
            if (!FLUID_STORAGE.getFluid().isEmpty()) {
                BlockEntity blockEntity = level.getBlockEntity(worldPosition.below());
                if (blockEntity != null) {
                    blockEntity.getCapability(ForgeCapabilities.FLUID_HANDLER, Direction.UP).ifPresent(iFluidHandler -> {
                        FluidStack stack = FLUID_STORAGE.getFluid();
                        FluidStack tankFluid = iFluidHandler.getFluidInTank(0);

                        int freeSpace = iFluidHandler.getTankCapacity(0) - tankFluid.getAmount();
                        if (stack.getFluid() == tankFluid.getFluid() || tankFluid.isEmpty()) {
                            int transferAmount = Math.min(stack.getAmount(), Math.min(freeSpace, 1000));
                            if (transferAmount > 0) {
                                FluidStack newFluid = new FluidStack(stack.getFluid(), transferAmount);
                                int filledAmount = iFluidHandler.fill(newFluid, IFluidHandler.FluidAction.EXECUTE);
                                if (filledAmount > 0) {
                                    FLUID_STORAGE.drain(filledAmount, IFluidHandler.FluidAction.EXECUTE);
                                }
                            }
                        }
                    });
                }
            }
        }
    }


    private void verifyEndless() {
        if(ConfigUtils.tank_can_endless) {
            if (endless == 1) {
                FLUID_STORAGE.fill(new FluidStack(FLUID_STORAGE.getFluid().getFluid(), FLUID_STORAGE.getCapacity()), IFluidHandler.FluidAction.EXECUTE);
            }else if(!FLUID_STORAGE.getFluid().equals(FluidStack.EMPTY) && FLUID_STORAGE.getFluid().getAmount() >= ConfigUtils.tank_capacity){
                    endless = 1;
            }
        }
    }


    private void fillUpOnFluid() {
        if(hasFluidSourceInSlot(FLUID_SLOT)) {
            transferItemFluidToTank(FLUID_SLOT);
        }
    }
    private void transferItemFluidToTank(int fluidInputSlot) {
        this.itemHandler.getStackInSlot(fluidInputSlot).getCapability(ForgeCapabilities.FLUID_HANDLER_ITEM).ifPresent(iFluidHandlerItem -> {
            //TODO
            if(iFluidHandlerItem.getContainer().getItem() instanceof BucketItem) {
                if(this.FLUID_STORAGE.getSpace() >= 1000){
                    FluidStack stack = iFluidHandlerItem.drain(1000, IFluidHandler.FluidAction.EXECUTE);
                    this.FLUID_STORAGE.fill(new FluidStack(stack.getFluid(), stack.getAmount()), IFluidHandler.FluidAction.EXECUTE);
                    this.itemHandler.extractItem(FLUID_SLOT, 1, false);
                    this.itemHandler.setStackInSlot(FLUID_SLOT, Items.BUCKET.getDefaultInstance());
                    removeContainer(iFluidHandlerItem.getContainer(), 0);
                }
            }else if(this.FLUID_STORAGE.getSpace() >= 10 || this.FLUID_STORAGE.getSpace() >= iFluidHandlerItem.getContainer().getCount()) {
                int drainAmount = Math.min(this.FLUID_STORAGE.getSpace(), 10);
                FluidStack stack = iFluidHandlerItem.drain(drainAmount, IFluidHandler.FluidAction.SIMULATE);
                iFluidHandlerItem.drain(stack, IFluidHandler.FluidAction.EXECUTE);
                this.FLUID_STORAGE.fill(new FluidStack(stack.getFluid(), stack.getAmount()), IFluidHandler.FluidAction.EXECUTE);
                removeContainer(iFluidHandlerItem.getContainer(), iFluidHandlerItem.getFluidInTank(0).getAmount());
            }
        });
    }

    private void removeContainer(ItemStack container, int fluid) {
        if(fluid <= 0 || container.getItem() instanceof BucketItem) {
            if(itemHandler.getStackInSlot(OUTPUT_FLUID_SLOT).isEmpty()) {
                this.itemHandler.extractItem(FLUID_SLOT, 1, false);
                this.itemHandler.setStackInSlot(OUTPUT_FLUID_SLOT, container);
            }
        }

    }

    private boolean hasFluidSourceInSlot(int fluidInputSlot) {
        return this.itemHandler.getStackInSlot(fluidInputSlot).getCount() > 0 &&
                this.itemHandler.getStackInSlot(fluidInputSlot).getCapability(ForgeCapabilities.FLUID_HANDLER_ITEM).isPresent();
    }

    public void fillFluidFromNBT(FluidStack stack) {
        if(!stack.isEmpty()) {
            FLUID_STORAGE.fill(stack, IFluidHandler.FluidAction.EXECUTE);
        }
    }
    public static int getInputSlot() {
        return FLUID_SLOT;
    }

    public static int getOutputSlot() {
        return OUTPUT_FLUID_SLOT;
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

    public @NotNull FluidStack getRenderStack() {
        return FLUID_STORAGE.getFluid();
    }

    public IFluidTank getFluidTank() {
        return FLUID_STORAGE;
    }
}