package com.Infinity.Nexus.Mod.block.entity;

import com.Infinity.Nexus.Core.block.entity.WrappedHandler;
import com.Infinity.Nexus.Core.utils.ModUtils;
import com.Infinity.Nexus.Mod.block.custom.FermentationBarrel;
import com.Infinity.Nexus.Mod.item.ModItemsAdditions;
import com.Infinity.Nexus.Mod.recipe.FermentationBarrelRecipes;
import com.Infinity.Nexus.Mod.screen.fermentation.FermentationBarrelMenu;
import net.minecraft.client.Minecraft;
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
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.item.BucketItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.MyceliumBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.fluids.capability.templates.FluidTank;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Map;
import java.util.Optional;

public class FermentationBarrelBlockEntity extends BlockEntity implements MenuProvider {
    private final ItemStackHandler itemHandler = new ItemStackHandler(4) {
        @Override
        protected void onContentsChanged(int slot) {
            setChanged();
        }

        @Override
        public boolean isItemValid(int slot, @NotNull ItemStack stack) {
            return switch (slot) {
                case 0 -> stack.getCapability(ForgeCapabilities.FLUID_HANDLER_ITEM).isPresent();
                case 2 -> true;
                case 1,3 -> false;
                default -> super.isItemValid(slot, stack);
            };
        }
    };

    private final FluidTank inputFluidHandler = new FluidTank(INPUT_FLUID_CAPACITY) {
        @Override
        public void onContentsChanged() {
            setChanged();
            if (!level.isClientSide()) {
                level.sendBlockUpdated(getBlockPos(), getBlockState(), getBlockState(), 3);
            }
        }

        @Override
        public boolean isFluidValid(FluidStack stack) {
            return true;
        }
    };
    private static final int INPUT_FLUID_ITEM = 0;
    private static final int OUTPUT_FLUID_ITEM = 1;
    private static final int INPUT_SLOT = 2;
    private static final int OUTPUT_SLOT = 3;
    private final FluidTank FLUID_STORAGE_INPUT = inputFluidHandler;
    private static final int INPUT_FLUID_CAPACITY = 8000;
    private static final int OUTPUT_FLUID_CAPACITY = 8000;
    private LazyOptional<IItemHandler> lazyItemHandler = LazyOptional.empty();
    private LazyOptional<IFluidHandler> lazyFluidHandler = LazyOptional.empty();


    private final Map<Direction, LazyOptional<WrappedHandler>> directionWrappedHandlerMap =
            Map.of(
                    Direction.UP, LazyOptional.of(() -> new WrappedHandler(itemHandler, (i) -> i == OUTPUT_SLOT || i == OUTPUT_FLUID_ITEM, (i, s) -> i == INPUT_SLOT  || i == INPUT_FLUID_ITEM  && !(ModUtils.isComponent(s) || ModUtils.isUpgrade(s)))),
                    Direction.DOWN, LazyOptional.of(() -> new WrappedHandler(itemHandler, (i) -> i == OUTPUT_SLOT || i == OUTPUT_FLUID_ITEM, (i, s) -> i == INPUT_SLOT  || i == INPUT_FLUID_ITEM  && !(ModUtils.isComponent(s) || ModUtils.isUpgrade(s)))),
                    Direction.NORTH, LazyOptional.of(() -> new WrappedHandler(itemHandler, (i) -> i == OUTPUT_SLOT || i == OUTPUT_FLUID_ITEM, (i, s) -> i == INPUT_SLOT  || i == INPUT_FLUID_ITEM  && !(ModUtils.isComponent(s) || ModUtils.isUpgrade(s)))),
                    Direction.SOUTH, LazyOptional.of(() -> new WrappedHandler(itemHandler, (i) -> i == OUTPUT_SLOT || i == OUTPUT_FLUID_ITEM, (i, s) -> i == INPUT_SLOT  || i == INPUT_FLUID_ITEM  && !(ModUtils.isComponent(s) || ModUtils.isUpgrade(s)))),
                    Direction.EAST, LazyOptional.of(() -> new WrappedHandler(itemHandler, (i) -> i == OUTPUT_SLOT || i == OUTPUT_FLUID_ITEM, (i, s) -> i == INPUT_SLOT  || i == INPUT_FLUID_ITEM  && !(ModUtils.isComponent(s) || ModUtils.isUpgrade(s)))),
                    Direction.WEST, LazyOptional.of(() -> new WrappedHandler(itemHandler, (i) -> i == OUTPUT_SLOT || i == OUTPUT_FLUID_ITEM, (i, s) -> i == INPUT_SLOT  || i == INPUT_FLUID_ITEM  && !(ModUtils.isComponent(s) || ModUtils.isUpgrade(s)))));

    protected final ContainerData data;
    private int progress = 0;
    private int maxProgress = 0;
    private double nextTarget = maxProgress * 0.025;
    public ItemStack recipeOutput = ItemStack.EMPTY;


    public FermentationBarrelBlockEntity(BlockPos pPos, BlockState pBlockState) {
        super(ModBlockEntities.FERMENTATION_BE.get(), pPos, pBlockState);
        this.data = new ContainerData() {
            @Override
            public int get(int pIndex) {
                return switch (pIndex) {
                    case 0 -> FermentationBarrelBlockEntity.this.progress;
                    case 1 -> FermentationBarrelBlockEntity.this.maxProgress;
                    default -> 0;
                };
            }

            @Override
            public void set(int pIndex, int pValue) {
                switch (pIndex) {
                    case 0 -> FermentationBarrelBlockEntity.this.progress = pValue;
                    case 1 -> FermentationBarrelBlockEntity.this.maxProgress = pValue;
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
                Direction localDir = this.getBlockState().getValue(FermentationBarrel.FACING);

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
        lazyFluidHandler = LazyOptional.of(() -> FLUID_STORAGE_INPUT);
    }

    @Override
    public void invalidateCaps() {
        super.invalidateCaps();
        lazyItemHandler.invalidate();
        lazyFluidHandler.invalidate();
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
        return Component.translatable("block.infinity_nexus_mod.fermentation_barrel");
    }

    @Nullable
    @Override
    public AbstractContainerMenu createMenu(int pContainerId, Inventory pPlayerInventory, Player pPlayer) {
        return new FermentationBarrelMenu(pContainerId, pPlayerInventory, this, this.data);
    }

    public static long getInputFluidCapacity() {
        return INPUT_FLUID_CAPACITY;
    }
    public static long getOutputFluidCapacity() {
        return OUTPUT_FLUID_CAPACITY;
    }

    @Override
    protected void saveAdditional(CompoundTag pTag) {
        pTag.put("inventory", itemHandler.serializeNBT());
        pTag.putInt("fermentation_barrel.progress", progress);
        pTag.putInt("fermentation_barrel.max_progress", maxProgress);
        pTag.put("recipeOutput", recipeOutput.serializeNBT());
        pTag = FLUID_STORAGE_INPUT.writeToNBT(pTag);
        super.saveAdditional(pTag);
    }

    @Override
    public void load(CompoundTag pTag) {
        super.load(pTag);
        itemHandler.deserializeNBT(pTag.getCompound("inventory"));
        progress = pTag.getInt("fermentation_barrel.progress");
        maxProgress = pTag.getInt("fermentation_barrel.max_progress");
        recipeOutput = ItemStack.of(pTag.getCompound("recipeOutput"));
        FLUID_STORAGE_INPUT.readFromNBT(pTag);
    }
    public ItemStack getResultItem(){
        return this.recipeOutput;
    }
    public FluidStack getFluidInInputTank() {
        return FLUID_STORAGE_INPUT.getFluid();
    }
    public void tick(Level pLevel, BlockPos pPos, BlockState pState) {

        if (pLevel.isClientSide) {
            return;
        }
        fillUpOnFluid();
        if (isRedstonePowered(pPos)) {
            return;
        }
        if (!hasRecipe()) {
            resetProgress();
            return;
        }

        setMaxProgress();
        increaseCraftingProgress(pPos);
        setChanged(pLevel, pPos, pState);

        if (hasProgressFinished()) {
            craftItem();
            resetProgress();
        }
    }
    private void fillUpOnFluid() {
        if(hasFluidSourceInSlot(INPUT_FLUID_ITEM)) {
            transferItemFluidToTank(INPUT_FLUID_ITEM);
        }
    }
    private void transferItemFluidToTank(int fluidInputSlot) {
        this.itemHandler.getStackInSlot(fluidInputSlot).getCapability(ForgeCapabilities.FLUID_HANDLER_ITEM).ifPresent(iFluidHandlerItem -> {
            //TODO
            if(iFluidHandlerItem.getContainer().getItem() instanceof BucketItem) {
                if(this.FLUID_STORAGE_INPUT.getSpace() >= 1000){
                    FluidStack stack = iFluidHandlerItem.drain(1000, IFluidHandler.FluidAction.EXECUTE);
                    this.FLUID_STORAGE_INPUT.fill(new FluidStack(stack.getFluid(), stack.getAmount()), IFluidHandler.FluidAction.EXECUTE);
                    this.itemHandler.extractItem(INPUT_FLUID_ITEM, 1, false);
                    this.itemHandler.setStackInSlot(INPUT_FLUID_ITEM, Items.BUCKET.getDefaultInstance());
                    removeContainer(iFluidHandlerItem.getContainer(), 0);
                }
            }else if(this.FLUID_STORAGE_INPUT.getSpace() >= 10 || this.FLUID_STORAGE_INPUT.getSpace() >= iFluidHandlerItem.getContainer().getCount()) {
                int drainAmount = Math.min(this.FLUID_STORAGE_INPUT.getSpace(), 10);
                FluidStack stack = iFluidHandlerItem.drain(drainAmount, IFluidHandler.FluidAction.SIMULATE);
                iFluidHandlerItem.drain(stack, IFluidHandler.FluidAction.EXECUTE);
                this.FLUID_STORAGE_INPUT.fill(new FluidStack(stack.getFluid(), stack.getAmount()), IFluidHandler.FluidAction.EXECUTE);
                removeContainer(iFluidHandlerItem.getContainer(), iFluidHandlerItem.getFluidInTank(0).getAmount());
            }
        });
    }

    private void removeContainer(ItemStack container, int fluid) {
        if(fluid <= 0 || container.getItem() instanceof BucketItem) {
            if(itemHandler.getStackInSlot(OUTPUT_FLUID_ITEM).isEmpty()) {
                this.itemHandler.extractItem(INPUT_FLUID_ITEM, 1, false);
                this.itemHandler.setStackInSlot(OUTPUT_FLUID_ITEM, container);
            }
        }

    }

    private boolean hasFluidSourceInSlot(int fluidInputSlot) {
        return this.itemHandler.getStackInSlot(fluidInputSlot).getCount() > 0 &&
                this.itemHandler.getStackInSlot(fluidInputSlot).getCapability(ForgeCapabilities.FLUID_HANDLER_ITEM).isPresent();
    }
    private void setMaxProgress() {
        int time = getCurrentRecipe().get().getDuration();

        if(this.level.getBlockState(getBlockPos().below()).getBlock() instanceof MyceliumBlock){
            time = time / 2;
        }
        if(itemHandler.getStackInSlot(INPUT_SLOT).getItem() == ModItemsAdditions.INFINITY_INGOT.get()){
            time = time * itemHandler.getStackInSlot(INPUT_SLOT).getCount();
        }
        maxProgress = time;
    }


    private void resetProgress() {
        progress = 0;
    }

    private void craftItem() {
        Optional<FermentationBarrelRecipes> recipe = getCurrentRecipe();
        boolean isFermentationRecipe = recipe.get().getRecipeType();
        int recipeFluidInputAmount = recipe.get().getInputFluidStack().getAmount();
        int recipeInputCount = recipe.get().getInputCount();
        int recipeResultCount = recipe.get().getResultItem(null).getCount();
        Item stack = recipe.get().getResultItem(null).getItem();

        if(!isFermentationRecipe) {
            int inputSlot = itemHandler.getStackInSlot(INPUT_SLOT).getCount();
            if (itemHandler.getStackInSlot(INPUT_SLOT).getItem().equals(ModItemsAdditions.INFINITY_INGOT.get())) {
                for (int i = 0; i < inputSlot; i++) {
                    if (inputFluidHandler.getFluidAmount() >= recipeFluidInputAmount && itemHandler.getStackInSlot(INPUT_SLOT).getCount() > 0) {
                        this.FLUID_STORAGE_INPUT.drain(recipeFluidInputAmount, IFluidHandler.FluidAction.EXECUTE);

                        itemHandler.setStackInSlot(OUTPUT_SLOT, new ItemStack(stack, recipeResultCount + itemHandler.getStackInSlot(OUTPUT_SLOT).getCount()));
                        itemHandler.getStackInSlot(INPUT_SLOT).shrink(recipeInputCount);
                    } else {
                        break;
                    }
                }
            } else {
                this.FLUID_STORAGE_INPUT.drain(recipeFluidInputAmount, IFluidHandler.FluidAction.EXECUTE);

                itemHandler.setStackInSlot(OUTPUT_SLOT, new ItemStack(stack, recipeResultCount + itemHandler.getStackInSlot(OUTPUT_SLOT).getCount()));
            }
        }else{
            int inputCount = recipe.get().getInputCount();
            int fluidInputCount = recipe.get().getInputFluidStack().getAmount();
            int recipeFluidOutputAmount = recipe.get().getOutputFluidStack().getAmount();
            FluidStack recipeFluidOutput = recipe.get().getOutputFluidStack();
            recipeFluidOutput.setAmount(recipeFluidOutputAmount);
            while (itemHandler.getStackInSlot(INPUT_SLOT).getCount() >= inputCount && this.FLUID_STORAGE_INPUT.getFluidAmount() >= fluidInputCount) {
                this.FLUID_STORAGE_INPUT.drain(recipeFluidInputAmount, IFluidHandler.FluidAction.EXECUTE);
                this.FLUID_STORAGE_INPUT.fill(recipeFluidOutput, IFluidHandler.FluidAction.EXECUTE);

                itemHandler.setStackInSlot(OUTPUT_SLOT, new ItemStack(stack, recipeResultCount + itemHandler.getStackInSlot(OUTPUT_SLOT).getCount()));
                itemHandler.getStackInSlot(INPUT_SLOT).shrink(recipeInputCount);

            }
        }

        level.playSound(null, this.getBlockPos(), SoundEvents.BREWING_STAND_BREW, SoundSource.BLOCKS, 1.0f, 1.0f);
    }

    private boolean hasRecipe() {
        Optional<FermentationBarrelRecipes> recipe = getCurrentRecipe();
        if (recipe.isEmpty()) {
            this.recipeOutput = ItemStack.EMPTY;
            return false;
        }
        ItemStack result = recipe.get().getResultItem(getLevel().registryAccess());
        this.recipeOutput = result.copy();
        return canInsertAmountIntoOutputSlot(result.getCount()) && canInsertItemIntoOutputSlot(result.getItem()) && hasFluidInTank();
    }

    private boolean hasFluidInTank() {
        return this.FLUID_STORAGE_INPUT.getFluid().getAmount() >= getCurrentRecipe().get().getInputFluidStack().getAmount()
                && this.FLUID_STORAGE_INPUT.getFluid().getFluid() == getCurrentRecipe().get().getInputFluidStack().getFluid();
    }

    private boolean canInsertItemIntoOutputSlot(Item item) {
        return this.itemHandler.getStackInSlot(OUTPUT_SLOT).isEmpty() || this.itemHandler.getStackInSlot(OUTPUT_SLOT).is(item);
    }

    private boolean canInsertAmountIntoOutputSlot(int count) {
        return this.itemHandler.getStackInSlot(OUTPUT_SLOT).getCount() + count <= this.itemHandler.getStackInSlot(OUTPUT_SLOT).getMaxStackSize();
    }
    private Optional<FermentationBarrelRecipes> getCurrentRecipe() {
        SimpleContainer inventory = new SimpleContainer(itemHandler.getSlots());
        for (int i = 0; i < itemHandler.getSlots(); i++) {
            inventory.setItem(i, itemHandler.getStackInSlot(i));
        }
        return this.level.getRecipeManager().getRecipeFor(FermentationBarrelRecipes.Type.INSTANCE, inventory, this.level);
    }

    private boolean isRedstonePowered(BlockPos pPos) {
        return this.level.hasNeighborSignal(pPos);
    }

    private boolean hasProgressFinished() {
        return progress >= maxProgress;
    }
    private void increaseCraftingProgress(BlockPos pPos) {
        if (progress >= nextTarget) {
        double x = pPos.getX() + 0.5;
        double y = pPos.getY() + 1;
        double z = pPos.getZ() + 0.5;
        var level = (ServerLevel) this.getLevel();
        level.sendParticles(ParticleTypes.CAMPFIRE_COSY_SMOKE, x, y + 0.1, z, 1, 0, 0, 0, 0.01D);
        nextTarget += maxProgress * 0.025;
    }
        progress ++;
    }

    public static int getInputSlot() {
        return INPUT_SLOT;
    }

    public static int getOutputSlot() {
        return OUTPUT_SLOT;
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