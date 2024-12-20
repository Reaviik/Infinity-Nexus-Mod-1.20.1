package com.Infinity.Nexus.Mod.block.entity;

import com.Infinity.Nexus.Core.block.entity.WrappedHandler;
import com.Infinity.Nexus.Core.block.entity.common.SetMachineLevel;
import com.Infinity.Nexus.Core.block.entity.common.SetUpgradeLevel;
import com.Infinity.Nexus.Core.fakePlayer.IFFakePlayer;
import com.Infinity.Nexus.Core.items.ModItems;
import com.Infinity.Nexus.Core.utils.ModEnergyStorage;
import com.Infinity.Nexus.Core.utils.ModUtils;
import com.Infinity.Nexus.Miner.utils.ModUtilsMiner;
import com.Infinity.Nexus.Mod.block.custom.MobCrusher;
import com.Infinity.Nexus.Mod.block.entity.wrappedHandlerMap.MobCrusherHandler;
import com.Infinity.Nexus.Mod.fluid.ModFluids;
import com.Infinity.Nexus.Mod.screen.mobcrusher.MobCrusherMenu;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.Connection;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.RandomSource;
import net.minecraft.world.Containers;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.loot.LootParams;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.common.ForgeHooks;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.energy.IEnergyStorage;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.fluids.capability.templates.FluidTank;
import net.minecraftforge.fml.util.ObfuscationReflectionHelper;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.enchantment.Enchantments;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * BlockEntity for the Mob Crusher machine.
 * This machine automatically kills mobs in its working area and collects their drops.
 * Features:
 * - Upgradeable with components and upgrade items
 * - Energy storage and consumption
 * - Fluid storage for experience
 * - Configurable working area based on machine level
 * - Automated item collection and sorting
 */
public class MobCrusherBlockEntity extends BlockEntity implements MenuProvider {
    private static final int[] OUTPUT_SLOT = {0,1,2,3,4,5,6,7,8};
    private static final int[] UPGRADE_SLOTS = {9,10,11,12};
    private static final int COMPONENT_SLOT = 13;
    private static final int SWORD_SLOT = 14;
    private static final int LINK_SLOT = 15;
    private static final int FUEL_SLOT = 16;

    protected final ContainerData data;

    private int itemProcessingTicks = 0;
    private int progress = 0;
    private int maxProgress = 120;
    private int hasRedstoneSignal = 0;
    private int stillCrafting = 0;
    private int hasSlotFree = 0;
    private int hasComponent = 0;
    private int hasEnoughEnergy = 0;
    private int hasRecipe = 0;
    private int linkx = 0;
    private int linky = 0;
    private int linkz = 0;
    private int linkFace = 0;

    private static final int ENERGY_CAPACITY = 60000;
    private static final int ENERGY_TRANSFER = 640;
    private static final int ENERGY_REQ = 32;
    private static final int PROCESSING_INTERVAL = 100;
    private final FluidTank FLUID_STORAGE = createFluidStorage();
    private static final int FluidStorageCapacity = 10000;

    private LazyOptional<IFluidHandler> lazyFluidHandler = LazyOptional.empty();
    private LazyOptional<IItemHandler> lazyItemHandler = LazyOptional.empty();
    private LazyOptional<IEnergyStorage> lazyEnergyStorage = LazyOptional.empty();

    /**
     * Main inventory handler for the machine.
     * Handles item validation and storage for all slots.
     */
    private final ItemStackHandler itemHandler = new ItemStackHandler(17) {
        @Override
        protected void onContentsChanged(int slot) {
            setChanged();
        }

        @Override
        public boolean isItemValid(int slot, @NotNull ItemStack stack) {
            return switch (slot) {
                case 0,1,2,3,4,5,6,7,8 -> !ModUtils.isUpgrade(stack) && !ModUtils.isComponent(stack);
                case 9,10,11,12 -> ModUtils.isUpgrade(stack);
                case 13 -> ModUtils.isComponent(stack);
                case 14 -> stack.getItem() instanceof SwordItem;
                case 15 -> stack.is(ModItems.LINKING_TOOL.get().asItem());
                case 16 -> ForgeHooks.getBurnTime(stack, null) > 0;
                default -> super.isItemValid(slot, stack);
            };
        }
    };

    /**
     * Mapping of side-specific inventory handlers.
     * Controls how items can be inserted/extracted from different block sides.
     */
    private final Map<Direction, LazyOptional<WrappedHandler>> directionWrappedHandlerMap = Map.of(
            Direction.UP, LazyOptional.of(() -> new WrappedHandler(itemHandler, (i) -> MobCrusherHandler.extract(i, Direction.UP), MobCrusherHandler::insert)),
            Direction.DOWN, LazyOptional.of(() -> new WrappedHandler(itemHandler, (i) -> MobCrusherHandler.extract(i, Direction.DOWN), MobCrusherHandler::insert)),
            Direction.NORTH, LazyOptional.of(() -> new WrappedHandler(itemHandler, (i) -> MobCrusherHandler.extract(i, Direction.NORTH), MobCrusherHandler::insert)),
            Direction.SOUTH, LazyOptional.of(() -> new WrappedHandler(itemHandler, (i) -> MobCrusherHandler.extract(i, Direction.SOUTH), MobCrusherHandler::insert)),
            Direction.EAST, LazyOptional.of(() -> new WrappedHandler(itemHandler, (i) -> MobCrusherHandler.extract(i, Direction.EAST), MobCrusherHandler::insert)),
            Direction.WEST, LazyOptional.of(() -> new WrappedHandler(itemHandler, (i) -> MobCrusherHandler.extract(i, Direction.WEST), MobCrusherHandler::insert)));

    /**
     * Energy storage handler for the machine.
     * Handles energy storage and transfer capabilities.
     */
    private final ModEnergyStorage ENERGY_STORAGE = new ModEnergyStorage(ENERGY_CAPACITY, ENERGY_TRANSFER) {
        @Override
        public void onEnergyChanged() {
            setChanged();
            getLevel().sendBlockUpdated(getBlockPos(), getBlockState(), getBlockState(), 4);
        }
    };

    /**
     * Creates and configures the fluid storage tank for the machine.
     * @return Configured FluidTank instance
     */
    private FluidTank createFluidStorage() {
        return new FluidTank(FluidStorageCapacity) {
            @Override
            protected void onContentsChanged() {
                setChanged();
                if (!getLevel().isClientSide) {
                    getLevel().sendBlockUpdated(getBlockPos(), getBlockState(), getBlockState(), 3);
                }
            }

            @Override
            public boolean isFluidValid(FluidStack stack) {
                return true;
            }
        };
    }

    /**
     * Constructor for the Mob Crusher BlockEntity.
     * Initializes the container data handler for synchronization.
     */
    public MobCrusherBlockEntity(BlockPos pPos, BlockState pBlockState) {
        super(ModBlockEntities.MOBCRUSHER_BE.get(), pPos, pBlockState);
        this.data = new ContainerData() {
            @Override
            public int get(int pIndex) {
                return switch (pIndex) {
                    case 0 -> MobCrusherBlockEntity.this.progress;
                    case 1 -> MobCrusherBlockEntity.this.maxProgress;
                    case 2 -> MobCrusherBlockEntity.this.hasRedstoneSignal;
                    case 3 -> MobCrusherBlockEntity.this.stillCrafting;
                    case 4 -> MobCrusherBlockEntity.this.hasSlotFree;
                    case 5 -> MobCrusherBlockEntity.this.hasComponent;
                    case 6 -> MobCrusherBlockEntity.this.hasEnoughEnergy;
                    case 7 -> MobCrusherBlockEntity.this.hasRecipe;
                    case 8 -> MobCrusherBlockEntity.this.linkx;
                    case 9 -> MobCrusherBlockEntity.this.linky;
                    case 10 -> MobCrusherBlockEntity.this.linkz;
                    case 11 -> MobCrusherBlockEntity.this.linkFace;
                    default -> 0;
                };
            }

            @Override
            public void set(int pIndex, int pValue) {
                switch (pIndex) {
                    case 0 -> MobCrusherBlockEntity.this.progress = pValue;
                    case 1 -> MobCrusherBlockEntity.this.maxProgress = pValue;
                    case 2 -> MobCrusherBlockEntity.this.hasRedstoneSignal = pValue;
                    case 3 -> MobCrusherBlockEntity.this.stillCrafting = pValue;
                    case 4 -> MobCrusherBlockEntity.this.hasSlotFree = pValue;
                    case 5 -> MobCrusherBlockEntity.this.hasComponent = pValue;
                    case 6 -> MobCrusherBlockEntity.this.hasEnoughEnergy = pValue;
                    case 7 -> MobCrusherBlockEntity.this.hasRecipe = pValue;
                    case 8 -> MobCrusherBlockEntity.this.linkx = pValue;
                    case 9 -> MobCrusherBlockEntity.this.linky = pValue;
                    case 10 -> MobCrusherBlockEntity.this.linkz = pValue;
                    case 11 -> MobCrusherBlockEntity.this.linkFace = pValue;
                }
            }

            @Override
            public int getCount() {
                return 12;
            }
        };
    }

    /**
     * Handles capability requests for the block entity.
     * Supports energy, fluid, and item handling capabilities.
     */
    @Override
    public @NotNull <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
        if (cap == ForgeCapabilities.ENERGY) {
            return lazyEnergyStorage.cast();
        }
        if (cap == ForgeCapabilities.FLUID_HANDLER) {
            return lazyFluidHandler.cast();
        }
        if (cap == ForgeCapabilities.ITEM_HANDLER) {
            if (side == null) {
                return lazyItemHandler.cast();
            }
            if (directionWrappedHandlerMap.containsKey(side)) {
                Direction localDir = this.getBlockState().getValue(MobCrusher.FACING);
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

    /**
     * Initializes capabilities when the block entity loads.
     */
    @Override
    public void onLoad() {
        super.onLoad();
        lazyItemHandler = LazyOptional.of(() -> itemHandler);
        lazyEnergyStorage = LazyOptional.of(() -> ENERGY_STORAGE);
        lazyFluidHandler = LazyOptional.of(() -> FLUID_STORAGE);
    }

    /**
     * Invalidates capabilities when the block entity is removed.
     */
    @Override
    public void invalidateCaps() {
        super.invalidateCaps();
        lazyItemHandler.invalidate();
        lazyEnergyStorage.invalidate();
        lazyFluidHandler.invalidate();
    }

    /**
     * Saves the block entity's data to NBT.
     */
    @Override
    protected void saveAdditional(CompoundTag pTag) {
        pTag.put("inventory", itemHandler.serializeNBT());
        pTag.putInt("mobCrusher.progress", progress);
        pTag.putInt("mobCrusher.energy", ENERGY_STORAGE.getEnergyStored());
        pTag = FLUID_STORAGE.writeToNBT(pTag);
        pTag.putInt("mobCrusher.hasRedstoneSignal", getHasRedstoneSignal());
        pTag.putInt("mobCrusher.stillCrafting", getStillCrafting());
        pTag.putInt("mobCrusher.hasSlotFree", getHasSlotFree());
        pTag.putInt("mobCrusher.hasComponent", getHasComponent());
        pTag.putInt("mobCrusher.hasEnoughEnergy", getHasEnoughEnergy());
        pTag.putInt("mobCrusher.hasRecipe", getHasRecipe());
        pTag.putInt("miner.linkx", data.get(8));
        pTag.putInt("miner.linky", data.get(9));
        pTag.putInt("miner.linkz", data.get(10));
        pTag.putInt("miner.linkFace", data.get(11));
        pTag.putBoolean("mobCrusher.showArea", showArea);
        super.saveAdditional(pTag);
    }

    /**
     * Loads the block entity's data from NBT.
     */
    @Override
    public void load(CompoundTag pTag) {
        super.load(pTag);
        itemHandler.deserializeNBT(pTag.getCompound("inventory"));
        progress = pTag.getInt("mobCrusher.progress");
        ENERGY_STORAGE.setEnergy(pTag.getInt("mobCrusher.energy"));
        FLUID_STORAGE.readFromNBT(pTag);
        hasRedstoneSignal = pTag.getInt("mobCrusher.hasRedstoneSignal");
        stillCrafting = pTag.getInt("mobCrusher.stillCrafting");
        hasSlotFree = pTag.getInt("mobCrusher.hasSlotFree");
        hasComponent = pTag.getInt("mobCrusher.hasComponent");
        hasEnoughEnergy = pTag.getInt("mobCrusher.hasEnoughEnergy");
        hasRecipe = pTag.getInt("mobCrusher.hasRecipe");
        linkx = pTag.getInt("miner.linkx");
        linky = pTag.getInt("miner.linky");
        linkz = pTag.getInt("miner.linkz");
        linkFace = pTag.getInt("miner.linkFace");
        showArea = pTag.getBoolean("mobCrusher.showArea");
    }

    /**
     * Gets the component slot index.
     * @return The index of the component slot
     */
    public static int getComponentSlot() {
        return COMPONENT_SLOT;
    }

    /**
     * Drops all items from the machine's inventory when broken.
     */
    public void drops() {
        SimpleContainer inventory = new SimpleContainer(itemHandler.getSlots());
        for (int i = 0; i < itemHandler.getSlots(); i++) {
            inventory.setItem(i, itemHandler.getStackInSlot(i));
        }
        assert this.level != null;
        Containers.dropContents(this.level, this.worldPosition, inventory);
    }

    /**
     * Gets the display name for the machine's GUI.
     * @return Component containing the machine's name and level
     */
    @Override
    public Component getDisplayName() {
        return Component.translatable("block.infinity_nexus_mod.mob_crusher").append(" LV "+ getMachineLevel());
    }

    /**
     * Creates the container menu for the machine's GUI.
     */
    @Nullable
    @Override
    public AbstractContainerMenu createMenu(int pContainerId, @NotNull Inventory pPlayerInventory, Player pPlayer) {
        return new MobCrusherMenu(pContainerId, pPlayerInventory, this, this.data);
    }

    /**
     * Gets the energy storage capability.
     * @return The machine's energy storage
     */
    public IEnergyStorage getEnergyStorage() {
        return ENERGY_STORAGE;
    }

    /**
     * Gets the fluid storage capacity.
     * @return Maximum fluid storage capacity
     */
    public static long getFluidCapacity() {
        return FluidStorageCapacity;
    }

    /**
     * Gets the current fluid stored in the machine.
     * @return Current fluid stack
     */
    public FluidStack getFluid() {
        return this.FLUID_STORAGE.getFluid();
    }

    /**
     * Sets the energy level of the machine.
     * @param energy New energy level
     */
    public void setEnergyLevel(int energy) {
        this.ENERGY_STORAGE.setEnergy(energy);
    }

    /**
     * Gets various machine states from the data array.
     */
    public int getHasRedstoneSignal() { return data.get(2); }
    public int getStillCrafting() { return data.get(3); }
    public int getHasSlotFree() { return data.get(4); }
    public int getHasComponent() { return data.get(5); }
    public int getHasEnoughEnergy() { return data.get(6); }
    public int getHasRecipe() { return data.get(7); }

    /**
     * Main tick function for the machine's operation.
     * Handles mob detection, killing, and loot collection.
     */
    public void tick(Level level, BlockPos pos, BlockState state) {
        if (level.isClientSide()) {
            return;
        }

        renderAreaPreview(level, pos);

        int machineLevel = getMachineLevel()-1 <= 0 ? 0 : getMachineLevel()-1;
        state = state.setValue(MobCrusher.LIT, machineLevel);

        if (isRedstonePowered(pos)) {
            this.data.set(2, 1);
            return;
        }
        this.data.set(2, 0);

        setMaxProgress();
        if (!hasEnoughEnergy()) {
            verifySolidFuel();
            this.data.set(6, 0);
            return;
        }
        this.data.set(6, 1);

        if(hasMobInside(machineLevel, pos, level)) {
            this.data.set(7, 1);
            increaseCraftingProgress();

            if (hasProgressFinished()) {
                this.data.set(3, 1);
                level.setBlock(pos, state.setValue(MobCrusher.LIT, machineLevel+9), 3);
                verifyMobs(level, pos, machineLevel);
                extractEnergy(this);
                setChanged(level, pos, state);
                resetProgress();
            }
        } else {
            this.data.set(7, 0);
        }
        this.data.set(3, 0);

        itemProcessingTicks++;
        if (itemProcessingTicks >= PROCESSING_INTERVAL) {
            processStoredItems(pos, level);
            itemProcessingTicks = 0;
        }
    }

    /**
     * Attempts to eject items to the appropriate destination.
     * If a linking tool is present, tries linked destination first, then falls back to above block.
     * If no linking tool is present, tries above block directly.
     */
    private void tryEjectItems(BlockPos pos, Level level) {
        boolean itemsNeedHandling = false;

        if (!itemHandler.getStackInSlot(LINK_SLOT).isEmpty()) {
            for (int outputSlot : OUTPUT_SLOT) {
                ItemStack stackToTransfer = itemHandler.getStackInSlot(outputSlot);
                if (!stackToTransfer.isEmpty()) {
                    ItemStack beforeTransfer = stackToTransfer.copy();
                    handleLinkedInsertion(stackToTransfer);

                    if (!stackToTransfer.isEmpty() && stackToTransfer.getCount() == beforeTransfer.getCount()) {
                        itemsNeedHandling = true;
                    }
                }
            }
        } else {
            itemsNeedHandling = true;
        }

        if (itemsNeedHandling) {
            tryEjectUpward(pos, level);
        }
    }

    /**
     * Attempts to eject items to the block above.
     * If items can't be transferred, they remain in the machine.
     */
    private void tryEjectUpward(BlockPos pos, Level level) {
        BlockEntity targetEntity = level.getBlockEntity(pos.above());
        if (targetEntity == null) {
            return;
        }

        targetEntity.getCapability(ForgeCapabilities.ITEM_HANDLER).ifPresent(targetHandler -> {
            boolean hasValidSlots = false;
            for (int outputSlot : OUTPUT_SLOT) {
                ItemStack stackToTransfer = itemHandler.getStackInSlot(outputSlot);
                if (!stackToTransfer.isEmpty()) {
                    for (int targetSlot = 0; targetSlot < targetHandler.getSlots(); targetSlot++) {
                        if (canInsertItem(stackToTransfer, targetSlot, targetHandler)) {
                            hasValidSlots = true;
                            break;
                        }
                    }
                    if (hasValidSlots) break;
                }
            }

            if (hasValidSlots) {
                for (int outputSlot : OUTPUT_SLOT) {
                    ItemStack stackToTransfer = itemHandler.getStackInSlot(outputSlot);
                    if (!stackToTransfer.isEmpty()) {
                        ItemStack remaining = transferItemToInventory(stackToTransfer.copy(), targetHandler);
                        itemHandler.setStackInSlot(outputSlot, remaining);
                    }
                }
            }
        });
    }

    /**
     * Checks if an item can be inserted into a specific slot.
     */
    private boolean canInsertItem(ItemStack stack, int slot, IItemHandler handler) {
        if (stack.isEmpty()) {
            return false;
        }

        ItemStack existingStack = handler.getStackInSlot(slot);
        if (existingStack.isEmpty()) {
            return handler.isItemValid(slot, stack);
        }

        if (!existingStack.is(stack.getItem())) {
            return false;
        }

        int maxStackSize = Math.min(handler.getSlotLimit(slot), stack.getMaxStackSize());
        return existingStack.getCount() + stack.getCount() <= maxStackSize;
    }

    /**
     * Attempts to transfer an ItemStack to an inventory.
     */
    private ItemStack transferItemToInventory(ItemStack stack, IItemHandler targetHandler) {
        ItemStack remaining = stack.copy();

        for (int slot = 0; slot < targetHandler.getSlots() && !remaining.isEmpty(); slot++) {
            if (canInsertItem(remaining, slot, targetHandler)) {
                remaining = targetHandler.insertItem(slot, remaining, false);
            }
        }

        return remaining;
    }

    /**
     * Checks if there are mobs within the machine's working area.
     */
    private boolean hasMobInside(int machinelevel, BlockPos pPos, Level pLevel) {
        machinelevel = machinelevel + 1;
        List<Mob> mobs = new ArrayList<>(pLevel.getEntitiesOfClass(Mob.class,
                new AABB(pPos.offset(machinelevel * -1, 1, machinelevel * -1),
                        pPos.offset(+machinelevel, 3,+machinelevel))));
        return !mobs.isEmpty();
    }

    /**
     * Calculates and extracts energy based on machine level and upgrades.
     */
    private void extractEnergy(MobCrusherBlockEntity mobCrusherBlockEntity) {
        int machineLevel = getMachineLevel() + 1;
        int maxProgress = mobCrusherBlockEntity.maxProgress;
        int speed = ModUtils.getSpeed(itemHandler, UPGRADE_SLOTS) + 1;
        int strength = (ModUtils.getStrength(itemHandler, UPGRADE_SLOTS) * 10);

        int var1 = (((machineLevel * 20)) / maxProgress) * (speed + machineLevel);
        int var2 = Math.multiplyExact(strength, var1 / 100);

        int extractEnergy = var1 - var2;

        mobCrusherBlockEntity.ENERGY_STORAGE.extractEnergy(Math.max(extractEnergy, 1), false);
    }

    /**
     * Utility methods for machine operation
     */
    private boolean hasEnoughEnergy() {
        return ENERGY_STORAGE.getEnergyStored() >= ENERGY_REQ;
    }

    private void resetProgress() {
        progress = 0;
    }

    /**
     * Gets the current machine level based on installed component.
     */
    public int getMachineLevel() {
        if(ModUtils.isComponent(this.itemHandler.getStackInSlot(COMPONENT_SLOT))) {
            this.data.set(5, ModUtils.getComponentLevel(this.itemHandler.getStackInSlot(COMPONENT_SLOT)));
        } else {
            this.data.set(5, 0);
        }
        return ModUtils.getComponentLevel(this.itemHandler.getStackInSlot(COMPONENT_SLOT));
    }

    /**
     * Checks if the block is receiving a redstone signal.
     */
    private boolean isRedstonePowered(BlockPos pPos) {
        return this.level.hasNeighborSignal(pPos);
    }

    /**
     * Checks if the current operation has completed.
     */
    private boolean hasProgressFinished() {
        return progress >= maxProgress;
    }

    /**
     * Increments the crafting progress counter.
     */
    private void increaseCraftingProgress() {
        progress++;
    }

    /**
     * Sets the maximum progress time for operations.
     */
    private void setMaxProgress() {
        maxProgress = 20;
    }

    /**
     * Creates a packet for syncing block entity data to the client.
     */
    @Nullable
    @Override
    public Packet<ClientGamePacketListener> getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }

    /**
     * Gets the update tag for initial chunk data synchronization.
     */
    @Override
    public @NotNull CompoundTag getUpdateTag() {
        return saveWithFullMetadata();
    }

    /**
     * Handles incoming data packets from the server.
     */
    @Override
    public void onDataPacket(Connection net, ClientboundBlockEntityDataPacket pkt) {
        super.onDataPacket(net, pkt);
        if (level != null && level.isClientSide()) {
            level.sendBlockUpdated(getBlockPos(), getBlockState(), getBlockState(), 3);
        }
    }

    /**
     * Executes the mob killing operation and processes drops.
     * @param entity The target entity to be processed
     */
    private List<ItemStack> execute(Entity entity) {
        if (!(level instanceof ServerLevel) || !(entity instanceof Mob mob)) {
            return new ArrayList<>();
        }
        ServerLevel serverLevel = (ServerLevel) level;
        List<ItemStack> allDrops = new ArrayList<>();

        ItemStack sword = this.itemHandler.getStackInSlot(SWORD_SLOT);
        int machineLevel = getMachineLevel();

        Player player = new IFFakePlayer(serverLevel);
        player.setItemInHand(InteractionHand.MAIN_HAND, sword);
        ServerPlayer randomPlayer = serverLevel.getRandomPlayer();

        int fireAspectLevel = sword.getEnchantmentLevel(Enchantments.FIRE_ASPECT);
        if (fireAspectLevel > 0) {
            mob.setSecondsOnFire(fireAspectLevel * 4);
        }

        DamageSource source = player.damageSources().playerAttack((randomPlayer != null) && machineLevel >= 7 ? randomPlayer : player);

        int lootingLevel = sword.getEnchantmentLevel(Enchantments.MOB_LOOTING);

        LootParams.Builder context = new LootParams.Builder(serverLevel)
                .withParameter(LootContextParams.THIS_ENTITY, mob)
                .withParameter(LootContextParams.DAMAGE_SOURCE, source)
                .withParameter(LootContextParams.ORIGIN, new Vec3(entity.getX(), entity.getY(), entity.getZ()))
                .withParameter(LootContextParams.KILLER_ENTITY, player)
                .withParameter(LootContextParams.LAST_DAMAGE_PLAYER, player)
                .withOptionalParameter(LootContextParams.DIRECT_KILLER_ENTITY, player)
                .withParameter(LootContextParams.TOOL, sword)
                .withLuck((float)lootingLevel);

        LootTable table = Objects.requireNonNull(this.level.getServer()).getLootData().getLootTable(mob.getLootTable());
        table.getRandomItems(context.create(LootContextParamSets.ENTITY)).forEach(stack -> {
            allDrops.add(stack.copy());
            for(int loot = 0; loot < machineLevel; loot++) {
                int rand = RandomSource.create().nextInt(10);
                if (rand == 0) {
                    allDrops.add(stack.copy());
                }
            }
        });

        List<ItemEntity> extra = new ArrayList<>();
        try {
            if (mob.captureDrops() == null) mob.captureDrops(new ArrayList<>());
            ObfuscationReflectionHelper.findMethod(Mob.class, "m_7472_", DamageSource.class, int.class, boolean.class)
                    .invoke(mob, source, lootingLevel, true);
            if (mob.captureDrops() != null) {
                extra.addAll(mob.captureDrops());
            }
        } catch (IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }

        ForgeHooks.onLivingDrops(mob, source, extra, 3 + lootingLevel, true);
        player.attack(mob);

        extra.forEach(itemEntity -> {
            allDrops.add(itemEntity.getItem().copy());
            itemEntity.remove(Entity.RemovalReason.KILLED);
        });

        mob.setHealth(0);

        int baseXP = mob.getExperienceReward();
        insertExpense(baseXP);

        return allDrops;
    }

    /**
     * Stores experience points as fluid in the machine's tank.
     * @param experienceReward Amount of experience to store
     */
    private void insertExpense(int experienceReward) {
        FluidStack fluidStack = new FluidStack(ModFluids.EXPERIENCE_SOURCE.get(), experienceReward);
        this.FLUID_STORAGE.fill(fluidStack, IFluidHandler.FluidAction.EXECUTE);
    }

    /**
     * Processes all mobs within the machine's working area.
     */
    public void verifyMobs(Level pLevel, BlockPos pPos, int machinelevel) {
        try {
            machinelevel = machinelevel + 1;
            List<Mob> entities = new ArrayList<>(pLevel.getEntitiesOfClass(Mob.class,
                    new AABB(pPos.offset(machinelevel * -1, 0, machinelevel * -1),
                            pPos.offset(+machinelevel, 3, +machinelevel))));

            if (!entities.isEmpty()) {
                boolean hasFreeSlots = hasFreeSlots();
                if(!hasFreeSlots && entities.size() > 30) {
                    entities.forEach(Entity::discard);
                    notifyOwner();
                    return;
                }

                for (Mob entity : entities) {
                    if (entity != null && entity.isAlive()) {
                        List<ItemStack> drops = execute(entity);
                        for (ItemStack drop : drops) {
                            insertItemOnSelfInventory(drop.copy());
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Processes stored items, attempting to send them to linked containers or upward.
     */
    private void processStoredItems(BlockPos pos, Level level) {
        for (int outputSlot : OUTPUT_SLOT) {
            ItemStack stackToProcess = itemHandler.getStackInSlot(outputSlot);
            if (!stackToProcess.isEmpty()) {
                ItemStack remaining = stackToProcess.copy();
                boolean itemMoved = false;

                if (hasLinkingTool()) {
                    ItemStack beforeTransfer = remaining.copy();
                    handleLinkedInsertion(remaining);
                    if (remaining.isEmpty() || remaining.getCount() != beforeTransfer.getCount()) {
                        itemHandler.setStackInSlot(outputSlot, remaining);
                        itemMoved = true;
                    }
                }

                if (!itemMoved && !remaining.isEmpty()) {
                    BlockEntity targetEntity = level.getBlockEntity(pos.above());
                    if (targetEntity != null) {
                        targetEntity.getCapability(ForgeCapabilities.ITEM_HANDLER).ifPresent(handler -> {
                            ItemStack afterTransfer = transferItemToInventory(remaining.copy(), handler);
                            if (afterTransfer.isEmpty() || afterTransfer.getCount() != remaining.getCount()) {
                                itemHandler.setStackInSlot(outputSlot, afterTransfer);
                            }
                        });
                    }
                }
            }
        }
    }

    /**
     * Resets the link coordinates in the data array.
     */
    private void resetLinkCoordinates() {
        this.data.set(8, 0);
        this.data.set(9, 0);
        this.data.set(10, 0);
    }

    /**
     * Parses link coordinates from the tool's display name.
     * @param name The tool's display name containing coordinate data
     * @return LinkCoordinates object or null if parsing fails
     */
    private LinkCoordinates parseLinkCoordinates(String name) {
        try {
            String[] parts = name.substring(1, name.length() - 1).split(",");
            int x = 0, y = 0, z = 0;
            String face = "up";

            for (String part : parts) {
                String[] keyValue = part.split("=");
                String key = keyValue[0].trim();
                String value = keyValue[1].trim();

                switch (key) {
                    case "x" -> {
                        x = Integer.parseInt(value);
                        this.data.set(8, x);
                    }
                    case "y" -> {
                        y = Integer.parseInt(value);
                        this.data.set(9, y);
                    }
                    case "z" -> {
                        z = Integer.parseInt(value);
                        this.data.set(10, z);
                    }
                    case "face" -> face = value;
                }
            }
            return new LinkCoordinates(x, y, z, face);
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * Handles interaction with linked container for item insertion.
     * @param targetEntity The linked block entity
     * @param coords The link coordinates
     * @param itemStack The item stack to insert
     */
    private void handleLinkedContainer(BlockEntity targetEntity, LinkCoordinates coords, ItemStack itemStack) {
        if (targetEntity == null || !canLink(targetEntity)) {
            return;
        }

        if (targetEntity.getBlockPos().equals(this.getBlockPos())) {
            handleSelfLink();
            return;
        }

        if (!itemHandler.getStackInSlot(OUTPUT_SLOT[7]).isEmpty()) {
            AtomicBoolean success = new AtomicBoolean(false);
            attemptItemTransfer(targetEntity, coords, itemStack, success);
        }
    }

    /**
     * Handles the case when the machine is linked to itself.
     */
    private void handleSelfLink() {
        level.addFreshEntity(new ItemEntity(level,
                data.get(8),
                data.get(9) + 1,
                data.get(10),
                itemHandler.getStackInSlot(LINK_SLOT).copy()));
        itemHandler.extractItem(LINK_SLOT, 1, false);
    }

    /**
     * Attempts to transfer items to the linked container.
     * @param targetEntity The target block entity
     * @param coords The link coordinates
     * @param itemStack The item stack to transfer
     * @param success Reference to track transfer success
     */
    private void attemptItemTransfer(BlockEntity targetEntity, LinkCoordinates coords, ItemStack itemStack, AtomicBoolean success) {
        targetEntity.getCapability(ForgeCapabilities.ITEM_HANDLER, ModUtilsMiner.getLinkedSide(coords.face())).ifPresent(handler -> {
            if (tryInsertNewItem(handler, itemStack.copy())) {
                success.set(true);
                return;
            }
            transferExistingItems(handler, success);
        });
    }

    /**
     * Attempts to insert a new item into the target handler.
     * @param handler The target item handler
     * @param itemStack The item stack to insert
     * @return true if insertion was successful
     */
    private boolean tryInsertNewItem(IItemHandler handler, ItemStack itemStack) {
        for (int slot = 0; slot < handler.getSlots(); slot++) {
            if (ModUtils.canPlaceItemInContainer(itemStack, slot, handler) &&
                    handler.isItemValid(slot, itemStack)) {
                handler.insertItem(slot, itemStack, false);
                return true;
            }
        }
        return false;
    }

    /**
     * Transfers existing items from output slots to the target handler.
     * @param handler The target item handler
     * @param success Reference to track transfer success
     */
    private void transferExistingItems(IItemHandler handler, AtomicBoolean success) {
        for (int slot = 0; slot < handler.getSlots(); slot++) {
            for (int outputSlot : OUTPUT_SLOT) {
                ItemStack outputStack = itemHandler.getStackInSlot(outputSlot);
                if (!outputStack.isEmpty() &&
                        handler.isItemValid(slot, outputStack) &&
                        ModUtils.canPlaceItemInContainer(outputStack.copy(), slot, handler)) {
                    handler.insertItem(slot, outputStack.copy(), false);
                    itemHandler.extractItem(outputSlot, outputStack.getCount(), false);
                    success.set(true);
                    break;
                }
            }
        }
    }

    /**
     * Inserts an item stack into the machine's own inventory.
     * @param itemStack The item stack to insert
     */
    private void insertItemOnSelfInventory(ItemStack itemStack) {
        if (itemStack.isEmpty()) {
            return;
        }

        for (int slot : OUTPUT_SLOT) {
            ItemStack existingStack = this.itemHandler.getStackInSlot(slot);
            if (!existingStack.isEmpty() &&
                    ItemStack.isSameItemSameTags(existingStack, itemStack) &&
                    existingStack.getCount() < existingStack.getMaxStackSize()) {

                int spaceAvailable = existingStack.getMaxStackSize() - existingStack.getCount();
                int amountToAdd = Math.min(spaceAvailable, itemStack.getCount());

                existingStack.grow(amountToAdd);
                itemStack.shrink(amountToAdd);

                if (itemStack.isEmpty()) {
                    return;
                }
            }
        }

        for (int slot : OUTPUT_SLOT) {
            if (this.itemHandler.getStackInSlot(slot).isEmpty()) {
                this.itemHandler.insertItem(slot, itemStack.copy(), false);
                return;
            }
        }
    }

    /**
     * Checks if a block entity is within linking range.
     * @param blockEntity The target block entity
     * @return true if the block entity is within range
     */
    private boolean canLink(BlockEntity blockEntity) {
        return (int) Math.sqrt(this.getBlockPos().distSqr(blockEntity.getBlockPos())) < 100;
    }

    /**
     * Record class for storing link coordinates and face.
     */
    private record LinkCoordinates(int x, int y, int z, String face) {
        public BlockPos toBlockPos() {
            return new BlockPos(x, y, z);
        }
    }

    /**
     * Gets the current link status as a string.
     * @return String representation of the current link coordinates or "[Unlinked]"
     */
    public String getHasLink() {
        if (this.data.get(8) != 0 || this.data.get(9) != 0 || this.data.get(10) != 0) {
            return "X: " + this.data.get(8) + ", Y: " + this.data.get(9) + ", Z: " + this.data.get(10);
        }
        return "[Unlinked]";
    }

    /**
     * Gets the ItemStack of the linked block.
     * @return ItemStack representing the linked block
     */
    public ItemStack getLikedBlock() {
        return new ItemStack(level.getBlockState(new BlockPos(
                this.data.get(8),
                this.data.get(9),
                this.data.get(10))).getBlock().asItem());
    }

    /**
     * Sets the machine level based on the provided component item.
     * @param itemStack The component item stack
     * @param player The player performing the action
     */
    public void setMachineLevel(ItemStack itemStack, Player player) {
        SetMachineLevel.setMachineLevel(itemStack, player, this, COMPONENT_SLOT, this.itemHandler);
    }

    /**
     * Sets the upgrade level based on the provided upgrade item.
     * @param itemStack The upgrade item stack
     * @param player The player performing the action
     */
    public void setUpgradeLevel(ItemStack itemStack, Player player) {
        SetUpgradeLevel.setUpgradeLevel(itemStack, player, this, UPGRADE_SLOTS, this.itemHandler);
    }

    /** Flag to control area preview rendering */
    private boolean showArea = false;

    /**
     * Controls the visibility of the working area preview.
     * @param show Whether to show or hide the preview
     */
    public void setShowArea(boolean show) {
        this.showArea = show;
        this.setChanged();
        if (level != null && !level.isClientSide()) {
            level.sendBlockUpdated(getBlockPos(), getBlockState(), getBlockState(), 3);
        }
    }

    /**
     * Gets the current state of area preview visibility.
     * @return true if area preview is enabled
     */
    public boolean shouldShowArea() {
        return this.showArea;
    }

    /**
     * Renders the working area preview using particles.
     * Only renders when showArea is true and on client side.
     * @param level The current level
     * @param pos The block position
     */
    private void renderAreaPreview(Level level, BlockPos pos) {
        if (!showArea || !level.isClientSide()) {
            return;
        }

        int machineLevel = getMachineLevel();
        if (machineLevel <= 0) {
            return;
        }

        int range = machineLevel;
        BlockPos start = pos.above();

        renderCubeEdges(level, start, range);
    }

    /**
     * Renders the edges of the working area cube using particles.
     * @param level The current level
     * @param start The starting position
     * @param range The range of the working area
     */
    private void renderCubeEdges(Level level, BlockPos start, int range) {
        for (int y = 0; y <= 2; y++) {
            for (int x = -range; x <= range; x++) {
                for (int z = -range; z <= range; z++) {
                    if (isEdgePosition(x, y, z, range)) {
                        spawnEdgeParticle(level, start, x, y, z);
                    }
                }
            }
        }
    }

    /**
     * Spawns a particle at the specified edge position.
     * @param level The current level
     * @param start The starting position
     * @param x The x offset
     * @param y The y offset
     * @param z The z offset
     */
    private void spawnEdgeParticle(Level level, BlockPos start, int x, int y, int z) {
        if (level.random.nextFloat() < 0.5f) {
            double particleX = start.getX() + x + 0.5;
            double particleY = start.getY() + y;
            double particleZ = start.getZ() + z + 0.5;

            level.addParticle(
                    ParticleTypes.END_ROD,
                    particleX,
                    particleY,
                    particleZ,
                    0, 0.01, 0
            );
        }
    }

    /**
     * Checks if a position represents an edge of the working area cube.
     * @param x The x coordinate
     * @param y The y coordinate
     * @param z The z coordinate
     * @param range The range of the working area
     * @return true if the position is on an edge
     */
    private boolean isEdgePosition(int x, int y, int z, int range) {
        if (Math.abs(x) == range && Math.abs(z) == range) {
            return true;
        }
        if (y == 0 || y == 2) {
            return Math.abs(x) == range || Math.abs(z) == range;
        }
        return false;
    }

    /**
     * Handles client-side tick updates, primarily for particle effects.
     * @param level The current level
     * @param pos The block position
     * @param state The current block state
     */
    public void clientTick(Level level, BlockPos pos, BlockState state) {
        if (!level.isClientSide()) {
            return;
        }

        if (this.showArea) {
            renderAreaPreview(level, pos);
        }
    }

    /**
     * Verifica se há slots livres no inventário.
     */
    private boolean hasFreeSlots() {
        for (int slot : OUTPUT_SLOT) {
            if (itemHandler.getStackInSlot(slot).isEmpty()) {
                return true;
            }
        }
        return false;
    }

    /**
     * Checks if a linking tool is installed.
     * @return true if a linking tool is installed
     */
    private boolean hasLinkingTool() {
        return !itemHandler.getStackInSlot(LINK_SLOT).isEmpty();
    }

    /**
     * Notify the owner of the machine.
     */
    private void notifyOwner() {
    }

    /**
     * Checks and processes solid fuel.
     */
    private void verifySolidFuel(){
        ItemStack slotItem = itemHandler.getStackInSlot(FUEL_SLOT);
        int burnTime = ForgeHooks.getBurnTime(slotItem, null);
        if(burnTime > 1){
            while(itemHandler.getStackInSlot(FUEL_SLOT).getCount() > 0 && this.getEnergyStorage().getEnergyStored() + burnTime < this.getEnergyStorage().getMaxEnergyStored()){
                this.getEnergyStorage().receiveEnergy(burnTime, false);
                itemHandler.extractItem(FUEL_SLOT, 1, false);
            }
        }
    }

    /**
     * Processes item insertion into a linked container.
     */
    private void handleLinkedInsertion(ItemStack stack) {
        if (stack.isEmpty()) return;

        BlockEntity targetEntity = level.getBlockEntity(new BlockPos(
                this.data.get(8),
                this.data.get(9),
                this.data.get(10)
        ));

        if (targetEntity == null) return;

        targetEntity.getCapability(ForgeCapabilities.ITEM_HANDLER).ifPresent(handler -> {
            for (int slot = 0; slot < handler.getSlots(); slot++) {
                if (handler.isItemValid(slot, stack)) {
                    ItemStack remaining = handler.insertItem(slot, stack.copy(), false);
                    stack.setCount(remaining.getCount());
                    if (stack.isEmpty()) break;
                }
            }
        });
    }
}