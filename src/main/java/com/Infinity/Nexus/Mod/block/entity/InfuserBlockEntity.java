package com.Infinity.Nexus.Mod.block.entity;

import com.Infinity.Nexus.Core.block.entity.WrappedHandler;
import com.Infinity.Nexus.Core.utils.ModUtils;
import com.Infinity.Nexus.Mod.block.custom.Infuser;
import com.Infinity.Nexus.Mod.block.custom.pedestals.*;
import com.Infinity.Nexus.Mod.block.entity.pedestals.*;
import com.Infinity.Nexus.Mod.block.entity.wrappedHandlerMap.InfuserHandler;
import com.Infinity.Nexus.Mod.recipe.InfuserRecipes;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.Connection;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.Containers;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LightningBolt;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public class InfuserBlockEntity extends BlockEntity{
    private final ItemStackHandler itemHandler = new ItemStackHandler(2) {
        @Override
        protected void onContentsChanged(int slot) {
            setChanged();
        }

        @Override
        public boolean isItemValid(int slot, @NotNull ItemStack stack) {
            return switch (slot) {
                case 0 -> true;
                case 1 -> false;
                default -> super.isItemValid(slot, stack);
            };
        }
    };

    //Slots
    private static final int INPUT_SLOT = 0;
    private static final int OUTPUT_SLOT = 1;
    //Inventory
    private LazyOptional<IItemHandler> lazyItemHandler = LazyOptional.empty();
    protected final ContainerData data;
    //Misc
    private int progress = 0;
    public int maxProgress = 200;
    public ItemStack recipeOutput = ItemStack.EMPTY;

    public ItemStack getRenderStack(){
        return this.itemHandler.getStackInSlot(INPUT_SLOT).isEmpty() ? ItemStack.EMPTY : this.itemHandler.getStackInSlot(INPUT_SLOT);
    }
    private final Map<Direction, LazyOptional<WrappedHandler>> directionWrappedHandlerMap =
            Map.of(
                    Direction.UP, LazyOptional.of(() -> new WrappedHandler(itemHandler, (i) -> InfuserHandler.extract(i, Direction.UP), (i, s) -> InfuserHandler.insert(i,s) && (itemHandler.getStackInSlot(i).isEmpty()))),
                    Direction.DOWN, LazyOptional.of(() -> new WrappedHandler(itemHandler, (i) -> InfuserHandler.extract(i, Direction.DOWN), (i, s) -> InfuserHandler.insert(i,s) && (itemHandler.getStackInSlot(i).isEmpty()))),
                    Direction.NORTH, LazyOptional.of(() -> new WrappedHandler(itemHandler, (i) -> InfuserHandler.extract(i, Direction.NORTH), (i, s) -> InfuserHandler.insert(i,s) && (itemHandler.getStackInSlot(i).isEmpty()))),
                    Direction.SOUTH, LazyOptional.of(() -> new WrappedHandler(itemHandler, (i) -> InfuserHandler.extract(i, Direction.SOUTH), (i, s) -> InfuserHandler.insert(i,s) && (itemHandler.getStackInSlot(i).isEmpty()))),
                    Direction.EAST, LazyOptional.of(() -> new WrappedHandler(itemHandler, (i) -> InfuserHandler.extract(i, Direction.EAST), (i, s) -> InfuserHandler.insert(i,s) && (itemHandler.getStackInSlot(i).isEmpty()))),
                    Direction.WEST, LazyOptional.of(() -> new WrappedHandler(itemHandler, (i) -> InfuserHandler.extract(i, Direction.WEST), (i, s) -> InfuserHandler.insert(i,s) && (itemHandler.getStackInSlot(i).isEmpty()))));


    public InfuserBlockEntity(BlockPos pPos, BlockState pBlockState) {
        super(ModBlockEntities.INFUSER_BE.get(), pPos, pBlockState);
        this.data = new ContainerData() {
            @Override
            public int get(int pIndex) {
                return switch (pIndex) {
                    case 0 -> InfuserBlockEntity.this.progress;
                    case 1 -> InfuserBlockEntity.this.maxProgress;
                    default -> 0;
                };
            }
            @Override
            public void set(int pIndex, int pValue) {
                switch (pIndex) {
                    case 0 -> InfuserBlockEntity.this.progress = pValue;
                    case 1 -> InfuserBlockEntity.this.maxProgress = pValue;
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
        if (cap == ForgeCapabilities.ITEM_HANDLER) {
            if (side == null) {
                return lazyItemHandler.cast();
            }

            if (directionWrappedHandlerMap.containsKey(side)) {
                Direction localDir = this.getBlockState().getValue(Infuser.FACING);

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
    @Override
    protected void saveAdditional(CompoundTag pTag) {
        pTag.put("inventory", itemHandler.serializeNBT());
        pTag.putInt("infuser.progress", progress);

        super.saveAdditional(pTag);
    }

    @Override
    public void load(CompoundTag pTag) {
        super.load(pTag);
        itemHandler.deserializeNBT(pTag.getCompound("inventory"));
        progress = pTag.getInt("infuser.progress");
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
        this.stopPedestalAnimation(this.worldPosition);
    }

    public void tick(Level pLevel, BlockPos pPos, BlockState pState) {
        if (pLevel.isClientSide) {
            return;
        }
        if(itemHandler.getStackInSlot(0).isEmpty()){
            if(progress > 0) {
                stopPedestalAnimation(pPos);
                resetProgress();
            }
            if(level.getBlockState(pPos).getValue(Infuser.LIT) != 3){
                this.getLevel().setBlock(pPos, pState.setValue(Infuser.LIT, 3), 3);
            }
            return;
        }

        if (!hasRecipe(pPos)) {
            if(progress > 0) {
                stopPedestalAnimation(pPos);
            }
            resetProgress();
            return;
        }
        int[] pedestals = this.getCurrentRecipe().get().getPedestals();
        managePedestals(pLevel, pPos, pState, pedestals);

        increaseCraftingProgress();
        setChanged(pLevel, pPos, pState);

        if (hasProgressFinished()) {
            craftItem();
            startPedestalAnimation(pPos, false, pedestals);
            ModUtils.ejectItemsWhePusher(pPos.above(),new int[]{INPUT_SLOT}, new int[]{OUTPUT_SLOT}, itemHandler, pLevel);
            this.getLevel().setBlock(pPos, pState.setValue(Infuser.LIT, 0), 3);
            resetProgress();
        }
    }

    private void managePedestals(Level pLevel, BlockPos pPos, BlockState pState, int[] pedestals) {
        if(progress == 0){
            startPedestalAnimation(pPos, false, pedestals);
            pLevel.setBlock(pPos, pState.setValue(Infuser.LIT, 1), 3);
        }
        if(progress == 1){
            startPedestalAnimation(pPos, true, pedestals);
        }
        if(progress == 180){
            summonLightning(pPos);
        }
    }

    private void startPedestalAnimation(BlockPos pos, boolean work, int[] pedestals) {

        Map<Integer, BlockPos> pedestalPositions = Map.of(
                1, pos.west(2).south(1),
                2, pos.west(2).north(1),
                3, pos.north(2),
                4, pos.east(2).north(1),
                5, pos.east(2).south(1),
                6, pos.south(2)
        );

        for (int pedestalIndex : pedestals) {
            BlockPos pedestalPos = pedestalPositions.get(pedestalIndex);
            if (pedestalPos != null) {
                level.setBlock(
                        pedestalPos,
                        this.level.getBlockState(pedestalPos).setValue(BasePedestal.WORK, work),
                        3
                );
            }
        }
    }
    private void stopPedestalAnimation(BlockPos pos) {
        List<BlockPos> pedestalPositions = List.of(
                pos.north(2),
                pos.south(2),
                pos.east(2).north(1),
                pos.east(2).south(1),
                pos.west(2).south(1),
                pos.west(2).north(1)
        );

        pedestalPositions.forEach(pedestalPos -> {
            try {
                level.setBlock(pedestalPos, level.getBlockState(pedestalPos).setValue(BasePedestal.WORK, false), 3);
            } catch (Exception ignored) {
            }
        });
    }



    private boolean hasProgressFinished() {
        return progress >= maxProgress;
    }

    private void increaseCraftingProgress() {
        progress ++;
    }

    private void resetProgress() {
        progress = 0;
    }

    private void craftItem() {
        Optional<InfuserRecipes> recipe = getCurrentRecipe();
        ItemStack result = recipe.get().getResultItem(null);

        this.itemHandler.extractItem(0, recipe.get().getInputCount(), false);
        ItemStack newStack = new ItemStack(result.getItem(), this.itemHandler.getStackInSlot(OUTPUT_SLOT).getCount() + result.getCount());
        if(recipeOutput.hasTag()){
            newStack.setTag(result.getTag());
        }
        this.itemHandler.setStackInSlot(OUTPUT_SLOT, newStack);

        level.playSound(null, this.getBlockPos(), SoundEvents.FIREWORK_ROCKET_LARGE_BLAST, SoundSource.BLOCKS, 1f, 1.0f);

    }
    private void summonLightning(BlockPos pos) {
        if (!level.isClientSide()) {
            LightningBolt lightning = EntityType.LIGHTNING_BOLT.create(level);
            if (lightning != null && !isRedstonePowered(pos)) {
                lightning.moveTo(pos.getX() + 0.5, pos.getY()+1, pos.getZ() + 0.5);
                lightning.setVisualOnly(true);
                lightning.playSound(SoundEvents.AMETHYST_BLOCK_BREAK, 1.0f, 1.0f);
                level.addFreshEntity(lightning);
            }
        }
    }
    private boolean hasRecipe(BlockPos pos) {
        Optional<InfuserRecipes> recipe = getCurrentRecipe();

        if (recipe.isEmpty()) {
            recipeOutput = ItemStack.EMPTY;
            return false;
        }

        ItemStack result = recipe.get().getResultItem(getLevel().registryAccess());
        recipeOutput = result.copy();
        if(!itemHandler.getStackInSlot(OUTPUT_SLOT).isEmpty() && recipeOutput != itemHandler.getStackInSlot(OUTPUT_SLOT)){
            return false;
        }

        return canInsertAmountIntoOutputSlot(result.getCount())
                && canInsertItemIntoOutputSlot(result.getItem())
                && hasPedestals(pos, recipe.get().getPedestals());
    }


    private boolean hasPedestals(BlockPos pos, int[] pedestals) {
        // Mapear os índices para as posições relativas dos pedestais
        Map<Integer, BlockPos> pedestalPositions = Map.of(
                1, pos.west(2).south(1),  // Tech Pedestal
                2, pos.west(2).north(1),  // Resource Pedestal
                3, pos.north(2),          // Magic Pedestal
                4, pos.east(2).north(1),  // Decor Pedestal
                5, pos.east(2).south(1),  // Creativity Pedestal
                6, pos.south(2)           // Exploration Pedestal
        );

        // Verificar os pedestais necessários na receita
        for (int pedestalIndex : pedestals) {
            BlockPos pedestalPos = pedestalPositions.get(pedestalIndex);
            if (pedestalPos == null) {
                return false; // Posição inválida para o pedestal
            }

            boolean isValid = switch (pedestalIndex) {
                case 1 -> this.level.getBlockEntity(pedestalPos) instanceof TechPedestalBlockEntity;
                case 2 -> this.level.getBlockEntity(pedestalPos) instanceof ResourcePedestalBlockEntity;
                case 3 -> this.level.getBlockEntity(pedestalPos) instanceof MagicPedestalBlockEntity;
                case 4 -> this.level.getBlockEntity(pedestalPos) instanceof DecorPedestalBlockEntity;
                case 5 -> this.level.getBlockEntity(pedestalPos) instanceof CreativityPedestalBlockEntity;
                case 6 -> this.level.getBlockEntity(pedestalPos) instanceof ExplorationPedestalBlockEntity;
                default -> false;
            };

            if (!isValid) {
                return false; // Um dos pedestais necessários não está presente
            }
        }

        return true; // Todos os pedestais necessários estão presentes
    }


    private Optional<InfuserRecipes> getCurrentRecipe() {
        SimpleContainer inventory = new SimpleContainer(itemHandler.getSlots());
        for (int i = 0; i < itemHandler.getSlots(); i++) {
            inventory.setItem(i, itemHandler.getStackInSlot(i));
        }
        return this.level.getRecipeManager().getRecipeFor(InfuserRecipes.Type.INSTANCE, inventory, this.level);
    }

    private boolean canInsertItemIntoOutputSlot(Item item) {
        return this.itemHandler.getStackInSlot(OUTPUT_SLOT).isEmpty() ||
                (this.itemHandler.getStackInSlot(OUTPUT_SLOT).is(item) && this.itemHandler.getStackInSlot(OUTPUT_SLOT).getTag().isEmpty());
    }

    private boolean canInsertAmountIntoOutputSlot(int count) {
        return this.itemHandler.getStackInSlot(OUTPUT_SLOT).getCount() + count <= this.itemHandler.getStackInSlot(OUTPUT_SLOT).getMaxStackSize();
    }

    private boolean isRedstonePowered(BlockPos pPos) {
        return this.level.hasNeighborSignal(pPos);
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

    public void removeStack(ItemStack copy, InfuserBlockEntity blockEntity, Player player, int slot) {
        level.addFreshEntity(new ItemEntity(level, player.getX(), player.getY(), player.getZ(), itemHandler.getStackInSlot(slot).copy()));
        this.itemHandler.setStackInSlot(slot, ItemStack.EMPTY);
    }

    public void addStack(ItemStack copy, InfuserBlockEntity blockEntity, Player player, int slot) {
        if(this.itemHandler.getStackInSlot(0).isEmpty()) {
            this.itemHandler.setStackInSlot(0, copy);
            player.getMainHandItem().setCount(0);
        }
    }
}