package com.Infinity.Nexus.Mod.block.entity;

import com.Infinity.Nexus.Core.block.entity.WrappedHandler;
import com.Infinity.Nexus.Core.block.entity.common.SetMachineLevel;
import com.Infinity.Nexus.Core.block.entity.common.SetUpgradeLevel;
import com.Infinity.Nexus.Core.utils.ModEnergyStorage;
import com.Infinity.Nexus.Core.utils.ModUtils;
import com.Infinity.Nexus.Mod.block.custom.Press;
import com.Infinity.Nexus.Mod.block.entity.wrappedHandlerMap.PressHandler;
import com.Infinity.Nexus.Mod.config.ConfigUtils;
import com.Infinity.Nexus.Mod.recipe.PressRecipes;
import com.Infinity.Nexus.Mod.screen.press.PressMenu;
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

public class PressBlockEntity extends BlockEntity implements MenuProvider {
    private final ItemStackHandler itemHandler = new ItemStackHandler(8) {
        @Override
        protected void onContentsChanged(int slot) {
            setChanged();
        }

        @Override
        public boolean isItemValid(int slot, @NotNull ItemStack stack) {
            return switch (slot) {
                case 0,1 -> !ModUtils.isUpgrade(stack) && !ModUtils.isComponent(stack);
                case 2 -> false;
                case 3,4,5,6 -> ModUtils.isUpgrade(stack);
                case 7 -> ModUtils.isComponent(stack);
                default -> super.isItemValid(slot, stack);
            };
        }
    };

    private static final int INPUT_SLOT = 0;
    private static final int OUTPUT_SLOT = 2;
    private static final int[] UPGRADE_SLOTS = {3, 4, 5, 6};
    private static final int COMPONENT_SLOT = 7;
    private static final int capacity = 60000;
    private static final int maxTransfer = 640;

    private final ModEnergyStorage ENERGY_STORAGE = createEnergyStorage();


    private ModEnergyStorage createEnergyStorage() {
        return new ModEnergyStorage(capacity, maxTransfer) {
            @Override
            public void onEnergyChanged() {
                setChanged();
                getLevel().sendBlockUpdated(getBlockPos(), getBlockState(), getBlockState(), 3);
            }
        };
    }

    private LazyOptional<IItemHandler> lazyItemHandler = LazyOptional.empty();
    private LazyOptional<IEnergyStorage> lazyEnergyStorage = LazyOptional.empty();

    private final Map<Direction, LazyOptional<WrappedHandler>> directionWrappedHandlerMap =
            Map.of(
                    Direction.UP, LazyOptional.of(() -> new WrappedHandler(itemHandler, (i) -> PressHandler.extract(i, Direction.UP), PressHandler::insert)),
                    Direction.DOWN, LazyOptional.of(() -> new WrappedHandler(itemHandler, (i) -> PressHandler.extract(i, Direction.DOWN), PressHandler::insert)),
                    Direction.NORTH, LazyOptional.of(() -> new WrappedHandler(itemHandler, (i) -> PressHandler.extract(i, Direction.NORTH), PressHandler::insert)),
                    Direction.SOUTH, LazyOptional.of(() -> new WrappedHandler(itemHandler, (i) -> PressHandler.extract(i, Direction.SOUTH), PressHandler::insert)),
                    Direction.EAST, LazyOptional.of(() -> new WrappedHandler(itemHandler, (i) -> PressHandler.extract(i, Direction.EAST), PressHandler::insert)),
                    Direction.WEST, LazyOptional.of(() -> new WrappedHandler(itemHandler, (i) -> PressHandler.extract(i, Direction.WEST), PressHandler::insert)));

    protected final ContainerData data;
    private int progress = 0;
    private int maxProgress;
    private ItemStack recipeOutput = ItemStack.EMPTY;

    public PressBlockEntity(BlockPos pPos, BlockState pBlockState) {
        super(ModBlockEntities.PRESS_BE.get(), pPos, pBlockState);
        this.data = new ContainerData() {
            @Override
            public int get(int pIndex) {
                return switch (pIndex) {
                    case 0 -> PressBlockEntity.this.progress;
                    case 1 -> PressBlockEntity.this.maxProgress;
                    default -> 0;
                };
            }

            @Override
            public void set(int pIndex, int pValue) {
                switch (pIndex) {
                    case 0 -> PressBlockEntity.this.progress = pValue;
                    case 1 -> PressBlockEntity.this.maxProgress = pValue;
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
                Direction localDir = this.getBlockState().getValue(Press.FACING);

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

    public void drops() {
        SimpleContainer inventory = new SimpleContainer(itemHandler.getSlots());
        for (int i = 0; i < itemHandler.getSlots(); i++) {
            inventory.setItem(i, itemHandler.getStackInSlot(i));
        }
        Containers.dropContents(this.level, this.worldPosition, inventory);
    }

    @Override
    public Component getDisplayName() {
        return Component.translatable("block.infinity_nexus_mod.press").append(" LV "+ getMachineLevel());
    }
    public static int getComponentSlot() {
        return COMPONENT_SLOT;
    }

    @Nullable
    @Override
    public AbstractContainerMenu createMenu(int pContainerId, Inventory pPlayerInventory, Player pPlayer) {
        return new PressMenu(pContainerId, pPlayerInventory, this, this.data);
    }
    public IEnergyStorage getEnergyStorage() {
        return ENERGY_STORAGE;
    }
    public void setEnergyLevel(int energy) {
        this.ENERGY_STORAGE.setEnergy(energy);
    }

    @Override
    protected void saveAdditional(CompoundTag pTag) {
        pTag.put("inventory", itemHandler.serializeNBT());
        pTag.putInt("press.progress", progress);
        pTag.putInt("press.energy", ENERGY_STORAGE.getEnergyStored());
        pTag.put("recipeOutput", recipeOutput.serializeNBT());

        super.saveAdditional(pTag);
    }

    @Override
    public void load(CompoundTag pTag) {
        super.load(pTag);
        itemHandler.deserializeNBT(pTag.getCompound("inventory"));
        progress = pTag.getInt("press.progress");
        ENERGY_STORAGE.setEnergy(pTag.getInt("press.energy"));
        recipeOutput = ItemStack.of(pTag.getCompound("recipeOutput"));
    }
    public ItemStack getResultItem(){
        return this.recipeOutput;
    }

    public void tick(Level pLevel, BlockPos pPos, BlockState pState) {
        if (pLevel.isClientSide) {
            return;
        }

        int machineLevel = getMachineLevel()-1 <= 0 ? 0 : getMachineLevel()-1;
        pLevel.setBlock(pPos, pState.setValue(Press.LIT, machineLevel), 3);
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
        pLevel.setBlock(pPos, pState.setValue(Press.LIT, machineLevel+9), 3);
        increaseCraftingProgress();
        extractEnergy(this);
        setChanged(pLevel, pPos, pState);


        if (hasProgressFinished()) {
            craftItem();
            ModUtils.ejectItemsWhePusher(pPos.above(),UPGRADE_SLOTS, new int[]{OUTPUT_SLOT}, itemHandler, pLevel);
            resetProgress();
        }
    }

    private void extractEnergy(PressBlockEntity pressBlockEntity) {
        int energy = getCurrentRecipe().get().getEnergy();
        int machineLevel = getMachineLevel() + 1;
        int maxProgress = pressBlockEntity.maxProgress;
        int speed = ModUtils.getSpeed(itemHandler, UPGRADE_SLOTS) + 1;
        int strength = (ModUtils.getStrength(itemHandler, UPGRADE_SLOTS) * 10);

        int var1 = ((energy + (machineLevel * 20)) / maxProgress) * (speed + machineLevel);
        int var2 = Math.multiplyExact(strength, var1 / 100);

        int extractEnergy = var1 - var2;

        pressBlockEntity.ENERGY_STORAGE.extractEnergy(Math.max(extractEnergy, 1), false);
    }
    private boolean hasEnoughEnergy() {
        return ENERGY_STORAGE.getEnergyStored() >= ((getCurrentRecipe().get().getEnergy() + (getMachineLevel()*20)) / maxProgress);
    }

    private void resetProgress() {
        progress = 0;
    }

    private void craftItem() {
        Optional<PressRecipes> recipe = getCurrentRecipe();
        ItemStack result = recipe.get().getResultItem(null);

        this.itemHandler.extractItem(INPUT_SLOT, recipe.get().getInputCount(), false);
        ItemStack component = this.itemHandler.getStackInSlot(COMPONENT_SLOT);

        ModUtils.useComponent(component, level, this.getBlockPos());

        this.itemHandler.setStackInSlot(OUTPUT_SLOT, new ItemStack(result.getItem(),
                this.itemHandler.getStackInSlot(OUTPUT_SLOT).getCount() + result.getCount()));
        if(ModUtils.getMuffler(itemHandler, UPGRADE_SLOTS) < 1){
            level.playSound(null, this.getBlockPos(), SoundEvents.ANVIL_FALL, SoundSource.BLOCKS, 0.3f, 1.0f);
        }
    }

    private boolean hasRecipe() {
        Optional<PressRecipes> recipe = getCurrentRecipe();

        if (recipe.isEmpty()) {
            this.recipeOutput = ItemStack.EMPTY;
            return false;
        }

        ItemStack result = recipe.get().getResultItem(getLevel().registryAccess());
        this.recipeOutput = result.copy();

        return canInsertAmountIntoOutputSlot(result.getCount()) && canInsertItemIntoOutputSlot(result.getItem());
    }

    private int getMachineLevel(){
        return ModUtils.getComponentLevel(this.itemHandler.getStackInSlot(COMPONENT_SLOT));
    }
    private Optional<PressRecipes> getCurrentRecipe() {
        SimpleContainer inventory = new SimpleContainer(itemHandler.getSlots());
        for (int i = 0; i < itemHandler.getSlots(); i++) {
            inventory.setItem(i, itemHandler.getStackInSlot(i));
        }
        return this.level.getRecipeManager().getRecipeFor(PressRecipes.Type.INSTANCE, inventory, this.level);
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
    public void setMachineLevel(ItemStack itemStack, Player player) {
        SetMachineLevel.setMachineLevel(itemStack, player, this, COMPONENT_SLOT, this.itemHandler);
    }
    public void setUpgradeLevel(ItemStack itemStack, Player player) {
        SetUpgradeLevel.setUpgradeLevel(itemStack, player, this, UPGRADE_SLOTS, this.itemHandler);
    }

}