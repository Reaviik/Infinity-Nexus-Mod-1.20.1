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
import com.Infinity.Nexus.Mod.utils.ModUtilsMachines;
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
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicBoolean;

public class MobCrusherBlockEntity extends BlockEntity implements MenuProvider {
    private static final int[] OUTPUT_SLOT = {0,1,2,3,4,5,6,7,8};
    private static final int[] UPGRADE_SLOTS = {9,10,11,12};
    private static final int COMPONENT_SLOT = 13;
    private static final int SWORD_SLOT = 14;
    private static final int LINK_SLOT = 15;
    private static final int FUEL_SLOT = 16;

    protected final ContainerData data;

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
    private final FluidTank FLUID_STORAGE = createFluidStorage();
    private static final int FluidStorageCapacity = 10000;

    private LazyOptional<IFluidHandler> lazyFluidHandler = LazyOptional.empty();
    private LazyOptional<IItemHandler> lazyItemHandler = LazyOptional.empty();
    private LazyOptional<IEnergyStorage> lazyEnergyStorage = LazyOptional.empty();

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


    private final Map<Direction, LazyOptional<WrappedHandler>> directionWrappedHandlerMap = Map.of(
            Direction.UP, LazyOptional.of(() -> new WrappedHandler(itemHandler, (i) -> MobCrusherHandler.extract(i, Direction.UP), MobCrusherHandler::insert)),
            Direction.DOWN, LazyOptional.of(() -> new WrappedHandler(itemHandler, (i) -> MobCrusherHandler.extract(i, Direction.DOWN), MobCrusherHandler::insert)),
            Direction.NORTH, LazyOptional.of(() -> new WrappedHandler(itemHandler, (i) -> MobCrusherHandler.extract(i, Direction.NORTH), MobCrusherHandler::insert)),
            Direction.SOUTH, LazyOptional.of(() -> new WrappedHandler(itemHandler, (i) -> MobCrusherHandler.extract(i, Direction.SOUTH), MobCrusherHandler::insert)),
            Direction.EAST, LazyOptional.of(() -> new WrappedHandler(itemHandler, (i) -> MobCrusherHandler.extract(i, Direction.EAST), MobCrusherHandler::insert)),
            Direction.WEST, LazyOptional.of(() -> new WrappedHandler(itemHandler, (i) -> MobCrusherHandler.extract(i, Direction.WEST), MobCrusherHandler::insert)));

    private final ModEnergyStorage ENERGY_STORAGE = new ModEnergyStorage(ENERGY_CAPACITY, ENERGY_TRANSFER) {
        @Override
        public void onEnergyChanged() {
            setChanged();
            getLevel().sendBlockUpdated(getBlockPos(), getBlockState(), getBlockState(), 4);
        }
    };

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

    @Override
    public void onLoad() {
        super.onLoad();
        lazyItemHandler = LazyOptional.of(() -> itemHandler);
        lazyEnergyStorage = LazyOptional.of(() -> ENERGY_STORAGE);
        lazyFluidHandler = LazyOptional.of(() -> FLUID_STORAGE);
    }


    @Override
    public void invalidateCaps() {
        super.invalidateCaps();
        lazyItemHandler.invalidate();
        lazyEnergyStorage.invalidate();
        lazyFluidHandler.invalidate();
    }

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


    public static int getComponentSlot() {
        return COMPONENT_SLOT;
    }

    public void drops() {
        SimpleContainer inventory = new SimpleContainer(itemHandler.getSlots());
        for (int i = 0; i < itemHandler.getSlots(); i++) {
            inventory.setItem(i, itemHandler.getStackInSlot(i));
        }
        assert this.level != null;
        Containers.dropContents(this.level, this.worldPosition, inventory);
    }

    @Override
    public Component getDisplayName() {
        return Component.translatable("block.infinity_nexus_mod.mob_crusher").append(" LV "+ getMachineLevel());
    }

    @Nullable
    @Override
    public AbstractContainerMenu createMenu(int pContainerId, @NotNull Inventory pPlayerInventory, Player pPlayer) {
        return new MobCrusherMenu(pContainerId, pPlayerInventory, this, this.data);
    }

    public IEnergyStorage getEnergyStorage() {
        return ENERGY_STORAGE;
    }

    public static long getFluidCapacity() {
        return FluidStorageCapacity;
    }

    public FluidStack getFluid() {
        return this.FLUID_STORAGE.getFluid();
    }

    public void setEnergyLevel(int energy) {
        this.ENERGY_STORAGE.setEnergy(energy);
    }


    public int getHasRedstoneSignal() { return data.get(2); }
    public int getStillCrafting() { return data.get(3); }
    public int getHasSlotFree() { return data.get(4); }
    public int getHasComponent() { return data.get(5); }
    public int getHasEnoughEnergy() { return data.get(6); }
    public int getHasRecipe() { return data.get(7); }

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
            this.data.set(3, 0);
        } else {
            this.data.set(7, 0);
        }
    }

    private boolean hasMobInside(int machinelevel, BlockPos pPos, Level pLevel) {
        machinelevel = machinelevel + 1;
        List<Mob> mobs = new ArrayList<>(pLevel.getEntitiesOfClass(Mob.class, new AABB(pPos.offset( machinelevel * -1, 1,  machinelevel * -1), pPos.offset(+machinelevel, 3,+machinelevel))));
        return !mobs.isEmpty();
    }

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

    private boolean hasEnoughEnergy() {
        return ENERGY_STORAGE.getEnergyStored() >= ENERGY_REQ;
    }

    private void resetProgress() {
        progress = 0;
    }

    public int getMachineLevel(){
        if(ModUtils.isComponent(this.itemHandler.getStackInSlot(COMPONENT_SLOT))){
            this.data.set(5, ModUtils.getComponentLevel(this.itemHandler.getStackInSlot(COMPONENT_SLOT)));
        }else{
            this.data.set(5, 0);
        }
        return ModUtils.getComponentLevel(this.itemHandler.getStackInSlot(COMPONENT_SLOT));
    }

    private boolean isRedstonePowered(BlockPos pPos) {
        return this.level.hasNeighborSignal(pPos);
    }

    private boolean hasProgressFinished() {
        return progress >= maxProgress;
    }

    private void increaseCraftingProgress() {
        progress++;
    }

    private void setMaxProgress() {
        maxProgress = 20;
    }

    @Nullable
    @Override
    public Packet<ClientGamePacketListener> getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }

    @Override
    public @NotNull CompoundTag getUpdateTag() {
        return saveWithFullMetadata();
    }

    @Override
    public void onDataPacket(Connection net, ClientboundBlockEntityDataPacket pkt) {
        super.onDataPacket(net, pkt);
        if (level != null && level.isClientSide()) {
            level.sendBlockUpdated(getBlockPos(), getBlockState(), getBlockState(), 3);
        }
    }
    private  void execute(Mob mob, BlockPos pPos, int machineLevel) {

        //ItemStack component = this.itemHandler.getStackInSlot(COMPONENT_SLOT);

        //ModUtils.useComponent(component, level, this.getBlockPos());
        IFFakePlayer player = ModUtilsMachines.getFakePlayer((ServerLevel) level);
        player.setItemInHand(InteractionHand.MAIN_HAND, this.itemHandler.getStackInSlot(SWORD_SLOT));
        ServerPlayer randomPlayer = ((ServerLevel) this.level).getRandomPlayer();
        DamageSource source = player.damageSources().playerAttack((randomPlayer != null) && machineLevel >= 7 ? randomPlayer : player);
        LootTable table = Objects.requireNonNull(this.level.getServer()).getLootData().getLootTable(mob.getLootTable());
        LootParams.Builder context = new LootParams.Builder((ServerLevel) this.level)
                .withParameter(LootContextParams.THIS_ENTITY, mob)
                .withParameter(LootContextParams.DAMAGE_SOURCE, source)
                .withParameter(LootContextParams.ORIGIN, new Vec3(pPos.getX(), pPos.getY(), pPos.getZ()))
                .withParameter(LootContextParams.KILLER_ENTITY, player)
                .withParameter(LootContextParams.LAST_DAMAGE_PLAYER, player)
                .withOptionalParameter(LootContextParams.DIRECT_KILLER_ENTITY, player);
        table.getRandomItems(context.create(LootContextParamSets.ENTITY)).forEach(stack ->{
            insertItemOnInventory(stack);
            for(int loot = 0; loot < machineLevel; loot++) {
                int rand = RandomSource.create().nextInt(10);
                if (rand == 0) {
                    insertItemOnInventory(stack);
                }
            }
        });

        List<ItemEntity> extra = new ArrayList<>();
        try {
            if (mob.captureDrops() == null) mob.captureDrops(new ArrayList<>());
            ObfuscationReflectionHelper.findMethod(Mob.class, "m_7472_", DamageSource.class, int.class, boolean.class).invoke(mob, source, 0, true);
            if (mob.captureDrops() != null) {
                extra.addAll(mob.captureDrops());
            }
        } catch (IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
        ForgeHooks.onLivingDrops(mob, source, extra, 3, true);
        player.attack(mob);
        extra.forEach(itemEntity -> {
            insertItemOnInventory(itemEntity.getItem());
            itemEntity.remove(Entity.RemovalReason.KILLED);
        });
        mob.setHealth(0);
        insertExpense(mob.getExperienceReward());
    }


    private void insertExpense(int experienceReward) {
        FluidStack fluidStack = new FluidStack(ModFluids.EXPERIENCE_SOURCE.get(), experienceReward);
        this.FLUID_STORAGE.fill(fluidStack, IFluidHandler.FluidAction.EXECUTE);
    }
    public void verifyMobs(Level pLevel, BlockPos pPos, int machinelevel) {
        try {
            machinelevel = machinelevel + 1;
            List<Mob> entities = new ArrayList<>(pLevel.getEntitiesOfClass(Mob.class, new AABB(pPos.offset( machinelevel * -1, 0,  machinelevel * -1), pPos.offset(+machinelevel,3,+machinelevel))));
            this.data.set(4,0);
            if (!entities.isEmpty()) {
                boolean hasFreeSlots = hasFreeSlots();
                if(!hasFreeSlots && entities.size() > 30) {
                    if(hasProgressFinished()){
                        insertItemOnInventory(ItemStack.EMPTY);
                    }
                    entities.forEach(Entity::discard);
                    notifyOwner();
                }else if(hasFreeSlots){
                    this.data.set(4,1);
                    for (Mob entity : entities) {
                        if (entity != null) {
                            if (entity.isAlive()) {
                                execute(entity, pPos, machinelevel);
                            }
                        }
                    }
                }
            }
        }catch (Exception e){
            System.out.println("§f[INM§f]§4: Failed to kill mobs in: " + pPos);
            e.printStackTrace();
        }
    }
    private void insertItemOnInventory(ItemStack itemStack) {
        try {
            if (itemHandler.getStackInSlot(LINK_SLOT).is(ModItems.LINKING_TOOL.get())) {
                ItemStack linkingTool = itemHandler.getStackInSlot(LINK_SLOT).copy();
                AtomicBoolean success = new AtomicBoolean(false);
                String name = linkingTool.getDisplayName().getString();
                this.data.set(8, 0);
                this.data.set(9, 0);
                this.data.set(10, 0);
                if (linkingTool.hasCustomHoverName()) {
                    String[] parts = name.substring(1, name.length() - 1).split(",");
                    int xl = 0;
                    int yl = 0;
                    int zl = 0;
                    String facel = "up";

                    for (String part : parts) {
                        String[] keyValue = part.split("=");
                        String key = keyValue[0].trim();
                        String value = keyValue[1].trim();

                        if (key.equals("x")) {
                            xl = Integer.parseInt(value);
                            this.data.set(8, xl);
                        } else if (key.equals("y")) {
                            yl = Integer.parseInt(value);
                            this.data.set(9, yl);
                        } else if (key.equals("z")) {
                            zl = Integer.parseInt(value);
                            this.data.set(10, zl);
                        } else if (key.equals("face")) {
                            facel = value;
                        }
                    }
                    BlockEntity blockEntity = this.level.getBlockEntity(new BlockPos(xl, yl, zl));
                    BlockPos targetPos = new BlockPos(xl, yl, zl);
                    if (blockEntity.getBlockPos().equals(this.getBlockPos())) {
                        level.addFreshEntity(new ItemEntity(level, xl, yl + 1, zl, itemHandler.getStackInSlot(LINK_SLOT).copy()));
                        itemHandler.extractItem(LINK_SLOT, 1, false);
                    }
                    if(!itemHandler.getStackInSlot(OUTPUT_SLOT[7]).isEmpty()) {
                        if (blockEntity != null && canLink(blockEntity)) {
                            blockEntity.getCapability(ForgeCapabilities.ITEM_HANDLER, ModUtilsMiner.getLinkedSide(facel)).ifPresent(iItemHandler -> {
                                for (int slot = 0; slot < iItemHandler.getSlots(); slot++) {
                                    if (ModUtils.canPlaceItemInContainer(itemStack.copy(), slot, iItemHandler) && iItemHandler.isItemValid(slot, itemStack.copy())) {
                                        iItemHandler.insertItem(slot, itemStack.copy(), false);
                                        ModUtilsMachines.sendParticlePath((ServerLevel) this.getLevel(), worldPosition.above(), targetPos, 0.5D, 0.2D, 0.5D);
                                        success.set(true);
                                        break;
                                    }
                                }

                                for (int slot = 0; slot < iItemHandler.getSlots(); slot++) {
                                    for (int outputSlot : OUTPUT_SLOT) {
                                        if (!itemHandler.getStackInSlot(outputSlot).isEmpty() && iItemHandler.isItemValid(slot, itemStack.copy()) && ModUtils.canPlaceItemInContainer(itemHandler.getStackInSlot(outputSlot).copy(), slot, iItemHandler)) {
                                            iItemHandler.insertItem(slot, itemHandler.getStackInSlot(outputSlot).copy(), false);
                                            itemHandler.extractItem(outputSlot, itemHandler.getStackInSlot(outputSlot).getCount(), false);
                                            success.set(true);
                                            break;
                                        }
                                    }
                                }
                            });
                        }else{
                            ModUtils.ejectItemsWhePusher(worldPosition.above(),UPGRADE_SLOTS, OUTPUT_SLOT, itemHandler, level);
                        }
                    }
                }
                if (!success.get()) {
                    insertItemOnSelfInventory(itemStack);
                }
            } else {
                insertItemOnSelfInventory(itemStack);
            }

        } catch (Exception e) {
            System.out.println("§f[INM§f]§c: Failed to insert item in: " + this.getBlockPos());
        }
    }

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

    private boolean canLink(BlockEntity blockEntity) {
        return (int) Math.sqrt(this.getBlockPos().distSqr(blockEntity.getBlockPos())) < 100;
    }

    public String getHasLink() {
        if (this.data.get(8) != 0 || this.data.get(9) != 0 || this.data.get(10) != 0) {
            return "X: " + this.data.get(8) + ", Y: " + this.data.get(9) + ", Z: " + this.data.get(10);
        }
        return "[Unlinked]";
    }

    public ItemStack getLikedBlock() {
        return new ItemStack(level.getBlockState(new BlockPos(
                this.data.get(8),
                this.data.get(9),
                this.data.get(10))).getBlock().asItem());
    }

    public void setMachineLevel(ItemStack itemStack, Player player) {
        SetMachineLevel.setMachineLevel(itemStack, player, this, COMPONENT_SLOT, this.itemHandler);
    }

    public void setUpgradeLevel(ItemStack itemStack, Player player) {
        SetUpgradeLevel.setUpgradeLevel(itemStack, player, this, UPGRADE_SLOTS, this.itemHandler);
    }
    private boolean showArea = false;

    public void setShowArea(boolean show) {
        this.showArea = show;
        this.setChanged();
        if (level != null && !level.isClientSide()) {
            level.sendBlockUpdated(getBlockPos(), getBlockState(), getBlockState(), 3);
        }
    }

    public boolean shouldShowArea() {
        return this.showArea;
    }

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

    private boolean isEdgePosition(int x, int y, int z, int range) {
        if (Math.abs(x) == range && Math.abs(z) == range) {
            return true;
        }
        if (y == 0 || y == 2) {
            return Math.abs(x) == range || Math.abs(z) == range;
        }
        return false;
    }

    public void clientTick(Level level, BlockPos pos, BlockState state) {
        if (!level.isClientSide()) {
            return;
        }

        if (this.showArea) {
            renderAreaPreview(level, pos);
        }
    }


    private boolean hasFreeSlots() {
        for (int slot : OUTPUT_SLOT) {
            if (itemHandler.getStackInSlot(slot).isEmpty()) {
                return true;
            }
        }
        return false;
    }

    private void notifyOwner() {
    }


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