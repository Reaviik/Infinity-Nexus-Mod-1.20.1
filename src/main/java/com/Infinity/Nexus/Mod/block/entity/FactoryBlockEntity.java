package com.Infinity.Nexus.Mod.block.entity;

import com.Infinity.Nexus.Core.block.entity.WrappedHandler;
import com.Infinity.Nexus.Core.block.entity.common.SetMachineLevel;
import com.Infinity.Nexus.Core.block.entity.common.SetUpgradeLevel;
import com.Infinity.Nexus.Core.utils.ModEnergyStorage;
import com.Infinity.Nexus.Core.utils.ModUtils;
import com.Infinity.Nexus.Mod.block.custom.Factory;
import com.Infinity.Nexus.Mod.block.entity.wrappedHandlerMap.FactoryHandler;
import com.Infinity.Nexus.Mod.config.ConfigUtils;
import com.Infinity.Nexus.Mod.recipe.FactoryRecipes;
import com.Infinity.Nexus.Mod.screen.factory.FactoryMenu;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.Connection;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.Containers;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.energy.IEnergyStorage;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Map;
import java.util.Optional;

public class FactoryBlockEntity extends BlockEntity implements MenuProvider {
    private final ItemStackHandler itemHandler = new ItemStackHandler(22) {
        @Override
        protected void onContentsChanged(int slot) {
            setChanged();
            assert level != null;
            if(!level.isClientSide()){
                level.sendBlockUpdated(getBlockPos(), getBlockState(), getBlockState(), 3);
            }
        }

        @Override
        public boolean isItemValid(int slot, @NotNull ItemStack stack) {
            return switch (slot) {
                case 0,1,2,3,4,5,6,7,8,9,10,11,12,13,14,15 -> !ModUtils.isUpgrade(stack) && !ModUtils.isComponent(stack);
                case 16 -> false;
                case 17,18,19,20 -> ModUtils.isUpgrade(stack);
                case 21 -> ModUtils.isComponent(stack);

                default -> super.isItemValid(slot, stack);
            };
        }

        //By gsoldera
        @Override
        public int getSlotLimit(int slot) {
            if (slot >= 0 && slot <= 7) {
                return 1;
            }
            return super.getSlotLimit(slot);
        }

        @Override
        @NotNull
        public ItemStack insertItem(int slot, @NotNull ItemStack stack, boolean simulate) {
            if (slot < 0 || slot > 15) {
                return super.insertItem(slot, stack, simulate);
            }

            if (!isItemValid(slot, stack)) {
                return stack;
            }

            if (simulate) {
                ItemStack existing = getStackInSlot(slot);
                if (existing.isEmpty()) {
                    return ItemStack.EMPTY;
                }
                return stack;
            }

            // Procura o primeiro slot vazio
            for (int i = 0; i <= 15; i++) {
                ItemStack existingStack = getStackInSlot(i);
                if (existingStack.isEmpty()) {
                    ItemStack singleItem = stack.copy();
                    singleItem.setCount(1);
                    setStackInSlot(i, singleItem);

                    ItemStack remainder = stack.copy();
                    remainder.shrink(1);
                    return remainder;
                }
            }

            return stack;
        }

        @Override
        protected int getStackLimit(int slot, @NotNull ItemStack stack) {
            if (slot >= 0 && slot <= 15) {
                return 1;
            }
            return super.getStackLimit(slot, stack);
        }
    };

    private static final int INPUT_SLOT = 15;
    private static final int OUTPUT_SLOT = 16;
    private static final int[] UPGRADE_SLOTS = {17, 18, 19, 20};
    private static final int COMPONENT_SLOT = 21;
    private static final int EnergyStorageCapacity = 60000;

    private final ModEnergyStorage ENERGY_STORAGE = createEnergyStorage();

    public static int getComponentSlot() {
        return COMPONENT_SLOT;
    }


    private ModEnergyStorage createEnergyStorage() {
        return new ModEnergyStorage(EnergyStorageCapacity, 640) {
            @Override
            public void onEnergyChanged() {
                setChanged();
                getLevel().sendBlockUpdated(getBlockPos(), getBlockState(), getBlockState(), 3);
            }
        };
    }
    public ItemStack getRenderStack(int slot){
            return itemHandler.getStackInSlot(slot).isEmpty() ? ItemStack.EMPTY : itemHandler.getStackInSlot(slot);
    }

    private static final int ENERGY_REQ = 32;

    private LazyOptional<IItemHandler> lazyItemHandler = LazyOptional.empty();
    private LazyOptional<IEnergyStorage> lazyEnergyStorage = LazyOptional.empty();

    private final Map<Direction, LazyOptional<WrappedHandler>> directionWrappedHandlerMap =
            Map.of(
                    Direction.UP, LazyOptional.of(() -> new WrappedHandler(itemHandler, (i) -> FactoryHandler.extract(i, Direction.UP), (i, s) -> FactoryHandler.insert(i,s) && (itemHandler.getStackInSlot(i).isEmpty()))),
                    Direction.DOWN, LazyOptional.of(() -> new WrappedHandler(itemHandler, (i) -> FactoryHandler.extract(i, Direction.DOWN), (i, s) -> FactoryHandler.insert(i,s) && (itemHandler.getStackInSlot(i).isEmpty()))),
                    Direction.NORTH, LazyOptional.of(() -> new WrappedHandler(itemHandler, (i) -> FactoryHandler.extract(i, Direction.NORTH), (i, s) -> FactoryHandler.insert(i,s) && (itemHandler.getStackInSlot(i).isEmpty()))),
                    Direction.SOUTH, LazyOptional.of(() -> new WrappedHandler(itemHandler, (i) -> FactoryHandler.extract(i, Direction.SOUTH), (i, s) -> FactoryHandler.insert(i,s) && (itemHandler.getStackInSlot(i).isEmpty()))),
                    Direction.EAST, LazyOptional.of(() -> new WrappedHandler(itemHandler, (i) -> FactoryHandler.extract(i, Direction.EAST), (i, s) -> FactoryHandler.insert(i,s) && (itemHandler.getStackInSlot(i).isEmpty()))),
                    Direction.WEST, LazyOptional.of(() -> new WrappedHandler(itemHandler, (i) -> FactoryHandler.extract(i, Direction.WEST), (i, s) -> FactoryHandler.insert(i,s) && (itemHandler.getStackInSlot(i).isEmpty()))));

    protected final ContainerData data;
    private int progress = 0;
    public int maxProgress = 0;
    public ItemStack recipeOutput = ItemStack.EMPTY;

    public FactoryBlockEntity(BlockPos pPos, BlockState pBlockState) {
        super(ModBlockEntities.FACTORY_BE.get(), pPos, pBlockState);
        this.data = new ContainerData() {
            @Override
            public int get(int pIndex) {
                return switch (pIndex) {
                    case 0 -> FactoryBlockEntity.this.progress;
                    case 1 -> FactoryBlockEntity.this.maxProgress;
                    default -> 0;
                };
            }

            @Override
            public void set(int pIndex, int pValue) {
                switch (pIndex) {
                    case 0 -> FactoryBlockEntity.this.progress = pValue;
                    case 1 -> FactoryBlockEntity.this.maxProgress = pValue;
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

        if (cap == ForgeCapabilities.ENERGY) {
            return lazyEnergyStorage.cast();
        }
        if (cap == ForgeCapabilities.ITEM_HANDLER) {
            if (side == null) {
                return lazyItemHandler.cast();
            }

            if (directionWrappedHandlerMap.containsKey(side)) {
                Direction localDir = this.getBlockState().getValue(Factory.FACING);

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
        lazyEnergyStorage = LazyOptional.of(() -> ENERGY_STORAGE);
    }

    @Override
    public void invalidateCaps() {
        super.invalidateCaps();
        lazyItemHandler.invalidate();
        lazyEnergyStorage.invalidate();
    }
    @Override
    protected void saveAdditional(CompoundTag pTag) {
        pTag.put("inventory", itemHandler.serializeNBT());
        pTag.putInt("factory.progress", progress);
        pTag.putInt("factory.maxProgress", maxProgress);
        pTag.putInt("factory.energy", ENERGY_STORAGE.getEnergyStored());
        pTag.put("recipeOutput", recipeOutput.serializeNBT());

        super.saveAdditional(pTag);
    }

    @Override
    public void load(CompoundTag pTag) {
        super.load(pTag);
        itemHandler.deserializeNBT(pTag.getCompound("inventory"));
        progress = pTag.getInt("factory.progress");
        maxProgress = pTag.getInt("factory.maxProgress");
        ENERGY_STORAGE.setEnergy(pTag.getInt("factory.energy"));
        recipeOutput = ItemStack.of(pTag.getCompound("recipeOutput"));
    }
    public ItemStack getResultItem(){
        return this.recipeOutput;
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
        return Component.translatable("block.infinity_nexus_mod.factory").append(" LV "+ getMachineLevel());
    }


    @Nullable
    @Override
    public AbstractContainerMenu createMenu(int pContainerId, Inventory pPlayerInventory, Player pPlayer) {
        return new FactoryMenu(pContainerId, pPlayerInventory, this, this.data);
    }

    public IEnergyStorage getEnergyStorage() {
        return ENERGY_STORAGE;
    }

    public void setEnergyLevel(int energy) {
        this.ENERGY_STORAGE.setEnergy(energy);
    }
    public int getCurrentProgress() {
        return this.progress;
    }
    public int getMaxProgress() {
        return maxProgress;
    }


    public void tick(Level pLevel, BlockPos pPos, BlockState pState) {
        if (pLevel.isClientSide) {
            return;
        }
        int machineLevel = getMachineLevel()-1 <= 0 ? 0 : getMachineLevel()-1; ;
        pLevel.setBlock(pPos, pState.setValue(Factory.LIT, machineLevel), 3);

        if (isRedstonePowered(pPos)) {
            return;
        }

        if (!hasRecipe()) {
            resetProgress();
            return;
        }

        setMaxProgress(machineLevel);
        if (!hasEnoughEnergy()) {
            return;
        }
        pLevel.setBlock(pPos, pState.setValue(Factory.LIT, machineLevel+9), 3);
        increaseCraftingProgress();
        extractEnergy(this);
        setChanged(pLevel, pPos, pState);

        if (hasProgressFinished()) {
            craftItem();
            ModUtils.ejectItemsWhePusher(pPos.above(),UPGRADE_SLOTS, new int[]{OUTPUT_SLOT}, itemHandler, pLevel);
            resetProgress();
        }
    }

    private void extractEnergy(FactoryBlockEntity factoryBlockEntity) {
        int energy = getCurrentRecipe().get().getEnergy();
        int machineLevel = getMachineLevel() + 1;
        int maxProgress = factoryBlockEntity.maxProgress;
        int speed = ModUtils.getSpeed(itemHandler, UPGRADE_SLOTS) + 1;
        int strength = (ModUtils.getStrength(itemHandler, UPGRADE_SLOTS) * 10);

        int var1 = ((energy + (machineLevel * 20)) / maxProgress) * (speed + machineLevel);
        int var2 = Math.multiplyExact(strength, var1 / 100);

        int extractEnergy = var1 - var2;

        factoryBlockEntity.ENERGY_STORAGE.extractEnergy(Math.max(extractEnergy, 1), false);
    }


    private boolean hasEnoughEnergy() {
        return ENERGY_STORAGE.getEnergyStored() >= ENERGY_REQ;
    }

    private void resetProgress() {
        progress = 0;
    }

    private void craftItem() {
        Optional<FactoryRecipes> recipe = getCurrentRecipe();
        ItemStack result = recipe.get().getResultItem(null);
        int[] amountInput = recipe.get().getAmountInput();


        for (int i = 0; i < amountInput.length; i++) {
            int amount = amountInput[i];
            for (int slot = 0; slot <= INPUT_SLOT; slot++) {
                ItemStack item = this.itemHandler.getStackInSlot(slot);
                if (!item.isEmpty() && recipe.get().getIngredients().get(i).test(item) && amount > 0) {
                    int extracted = this.itemHandler.extractItem(slot, amount, false).getCount();
                    amount -= extracted;
                }
            }
        }
        ItemStack component = this.itemHandler.getStackInSlot(COMPONENT_SLOT);

        ModUtils.useComponent(component, level, this.getBlockPos());

        this.itemHandler.setStackInSlot(OUTPUT_SLOT, new ItemStack(result.getItem(),
                this.itemHandler.getStackInSlot(OUTPUT_SLOT).getCount() + result.getCount()));

        if(ModUtils.getMuffler(itemHandler, UPGRADE_SLOTS) < 1){
            level.playSound(null, this.getBlockPos(), SoundEvents.RESPAWN_ANCHOR_AMBIENT, SoundSource.BLOCKS, 0.3f, 1.0f);
        }
    }

    private boolean hasRecipe() {
        Optional<FactoryRecipes> recipe = getCurrentRecipe();

        if (recipe.isEmpty()) {
            this.recipeOutput = ItemStack.EMPTY;
            return false;
        }

        ItemStack result = recipe.get().getResultItem(getLevel().registryAccess());
        this.recipeOutput = result.copy();

        return canInsertAmountIntoOutputSlot(result.getCount()) && canInsertItemIntoOutputSlot(result.getItem());
    }

    private Optional<FactoryRecipes> getCurrentRecipe() {
        SimpleContainer inventory = new SimpleContainer(itemHandler.getSlots());
        for (int i = 0; i < itemHandler.getSlots(); i++) {
            inventory.setItem(i, itemHandler.getStackInSlot(i));
        }
        return this.level.getRecipeManager().getRecipeFor(FactoryRecipes.Type.INSTANCE, inventory, this.level);
    }
    private int getMachineLevel(){
        return ModUtils.getComponentLevel(this.itemHandler.getStackInSlot(COMPONENT_SLOT));
    }
    private boolean canInsertItemIntoOutputSlot(Item item) {
        return this.itemHandler.getStackInSlot(OUTPUT_SLOT).isEmpty() || this.itemHandler.getStackInSlot(OUTPUT_SLOT).is(item);
    }

    private boolean canInsertAmountIntoOutputSlot(int count) {
        return this.itemHandler.getStackInSlot(OUTPUT_SLOT).getCount() + count <= this.itemHandler.getStackInSlot(OUTPUT_SLOT).getMaxStackSize();
    }

    private boolean isRedstonePowered(BlockPos pPos) {
        return this.level.hasNeighborSignal(pPos);
    }

    private boolean hasProgressFinished() {
        return progress >= maxProgress;
    }

    private void increaseCraftingProgress() {
        progress ++;
    }
    private void setMaxProgress(int machineLevel) {
        int duration = getCurrentRecipe().get().getDuration(); //130
        int halfDuration = duration / 2;
        int speedReduction = halfDuration / 16;
        int speed = ModUtils.getSpeed(itemHandler, UPGRADE_SLOTS); //16

        int reducedDuration = speed * speedReduction;
        int reducedLevel = machineLevel * (halfDuration / 8);
        duration = duration - reducedDuration - reducedLevel;

        maxProgress = Math.max(duration, ConfigUtils.assembler_minimum_tick);
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

    public void setUpgradeLevel(ItemStack itemStack, Player player) {
        SetUpgradeLevel.setUpgradeLevel(itemStack, player, this, UPGRADE_SLOTS, this.itemHandler);
    }
}