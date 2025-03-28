package com.Infinity.Nexus.Mod.events;

import com.Infinity.Nexus.Mod.InfinityNexusMod;
import com.Infinity.Nexus.Mod.command.Infuser;
import com.Infinity.Nexus.Mod.command.Teste;
import com.Infinity.Nexus.Mod.item.ModItemsAdditions;
import com.Infinity.Nexus.Mod.item.custom.HammerItem;
import com.Infinity.Nexus.Mod.item.custom.ImperialInfinityArmorItem;
import com.Infinity.Nexus.Mod.item.custom.InfinityArmorItem;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ClickAction;
import net.minecraft.world.inventory.InventoryMenu;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.client.event.ContainerScreenEvent;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.event.entity.item.ItemTossEvent;
import net.minecraftforge.event.entity.living.LivingEquipmentChangeEvent;
import net.minecraftforge.event.entity.player.PlayerContainerEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.event.level.BlockEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.server.command.ConfigCommand;

import java.awt.event.ContainerEvent;
import java.util.HashSet;
import java.util.Set;

@Mod.EventBusSubscriber(modid = InfinityNexusMod.MOD_ID)
public class ModEvents {
    private static final Set<BlockPos> HARVESTED_BLOCKS = new HashSet<>();

    @SubscribeEvent
    public static void onCommandRegister(final RegisterCommandsEvent event) {
        new Teste(event.getDispatcher());
        new Infuser(event.getDispatcher());
        ConfigCommand.register(event.getDispatcher());
    }

    @SubscribeEvent
    public static void onHammerUsage(final BlockEvent.BreakEvent event) {
        final Player player = event.getPlayer();
        final ItemStack mainHandItem = player.getMainHandItem();

        if (!(mainHandItem.getItem() instanceof HammerItem hammer) || !(player instanceof ServerPlayer serverPlayer)) {
            return;
        }

        final BlockPos initialBlockPos = event.getPos();
        if (HARVESTED_BLOCKS.contains(initialBlockPos)) {
            return;
        }

        processHammerBreak(mainHandItem, hammer, initialBlockPos, serverPlayer);
    }

    private static void processHammerBreak(ItemStack mainHandItem, HammerItem hammer, BlockPos initialBlockPos, ServerPlayer serverPlayer) {
        final int baseRange = mainHandItem.getOrCreateTag().getInt("range");
        final int effectiveRange = mainHandItem.getItem() == ModItemsAdditions.IMPERIAL_INFINITY_HAMMER.get() ? baseRange + 2 : baseRange + 1;

        for (BlockPos pos : HammerItem.getBlocksToBeDestroyed(effectiveRange, initialBlockPos, serverPlayer)) {
            if (pos == initialBlockPos || !hammer.isCorrectToolForDrops(mainHandItem, serverPlayer.level().getBlockState(pos))) {
                continue;
            }

            HARVESTED_BLOCKS.add(pos);
            serverPlayer.gameMode.destroyBlock(pos);
            double x = pos.getX()+0.5D;
            double y = pos.getY()+0.5D;
            double z = pos.getZ()+0.5D;
            serverPlayer.serverLevel().sendParticles(ParticleTypes.SCULK_SOUL, x, y, z, 4, 0, 0, 0, 0);
            HARVESTED_BLOCKS.remove(pos);
        }
    }
    @SubscribeEvent
    public static void onArmorChange(final LivingEquipmentChangeEvent event) {
        if (event.getEntity() instanceof Player player && !player.level().isClientSide()
                && event.getSlot().getType() == EquipmentSlot.Type.ARMOR) {
            checkArmorAndDisableFlight(player);
        }
    }

    @SubscribeEvent
    public static void onItemToss(final ItemTossEvent event) {
        final Player player = event.getPlayer();
        final Item tossedItem = event.getEntity().getItem().getItem();

        if (!player.level().isClientSide() && (tossedItem instanceof ImperialInfinityArmorItem
                || tossedItem instanceof InfinityArmorItem)) {
            checkArmorAndDisableFlight(player);
        }
    }

    private static void checkArmorAndDisableFlight(final Player player) {
        //if (!hasFullSuitOfArmorOn(player)) {
        //    player.getAbilities().flying = false;
        //    player.getAbilities().mayfly = false;
        //    player.onUpdateAbilities();
        //}
    }
    public static boolean hasFullSuitOfArmorOn(final Player player) {
        final Item boots = player.getInventory().getArmor(0).getItem();
        final Item leggings = player.getInventory().getArmor(1).getItem();
        final Item breastplate = player.getInventory().getArmor(2).getItem();
        final Item helmet = player.getInventory().getArmor(3).getItem();
        final ItemStack chestpiece = player.getInventory().getArmor(2);

        return hasFullInfinitySet(boots, leggings, breastplate, helmet, chestpiece) ||
                hasFullImperialSet(boots, leggings, breastplate, helmet);
    }

    private static boolean hasFullInfinitySet(Item boots, Item leggings, Item breastplate, Item helmet, ItemStack fuel) {
        return boots == ModItemsAdditions.INFINITY_BOOTS.get()
                && leggings == ModItemsAdditions.INFINITY_LEGGINGS.get()
                && breastplate == ModItemsAdditions.INFINITY_CHESTPLATE.get()
                && helmet == ModItemsAdditions.INFINITY_HELMET.get()
                && fuel.getOrCreateTag().getInt("Fuel") > 1;
    }

    private static boolean hasFullImperialSet(Item boots, Item leggings, Item breastplate, Item helmet) {
        return boots == ModItemsAdditions.IMPERIAL_INFINITY_BOOTS.get()
                && leggings == ModItemsAdditions.IMPERIAL_INFINITY_LEGGINGS.get()
                && breastplate == ModItemsAdditions.IMPERIAL_INFINITY_CHESTPLATE.get()
                && helmet == ModItemsAdditions.IMPERIAL_INFINITY_HELMET.get();
    }
}