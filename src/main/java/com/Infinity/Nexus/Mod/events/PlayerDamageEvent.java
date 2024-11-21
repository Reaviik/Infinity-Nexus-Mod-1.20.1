package com.Infinity.Nexus.Mod.events;

import com.Infinity.Nexus.Mod.InfinityNexusMod;
import com.Infinity.Nexus.Mod.item.ModItemsAdditions;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = InfinityNexusMod.MOD_ID)
public class PlayerDamageEvent {

    @SubscribeEvent
    public static void onPlayerHurt(LivingHurtEvent event) {
        if (event.getEntity() instanceof Player player) {
            try {
                if(hasFullImperialArmor(player)){
                    if (event.getSource().getEntity() instanceof Player enemy) {
                        ItemStack weapon = enemy.getMainHandItem();
                        if (!isCorrectWeapon(weapon)) {
                            enemy.sendSystemMessage(Component.literal(InfinityNexusMod.message + "§cYou cannot attack other players with full Imperial Armor with this weapon!"));
                            enemy.setSwimming(true);
                            enemy.playNotifySound(SoundEvents.GENERIC_EXPLODE, SoundSource.PLAYERS, 1.0F, 1.0F);
                            event.setCanceled(true);
                        } else {
                            float damage = event.getAmount();
                            float newDamage = (damage / 100) * 10;
                            event.setAmount(newDamage);
                        }
                    }else{
                        if(event.getSource().getEntity() instanceof Mob mob){
                            ServerPlayer target = (ServerPlayer) event.getEntity();
                            target.attack(mob);
                        }
                        event.setCanceled(true);
                    }
                }else if(hasFullCarbonArmor(player)) {
                    float damage = event.getAmount();
                    float hurtDamage = damage * 2;

                    for(int i = 0; i < 4; i++){
                        int durability = player.getInventory().getArmor(i).getDamageValue();
                        if(durability >= 1){
                            ItemStack  part = player.getInventory().getArmor(i);
                            part.hurt((int) hurtDamage, player.level().random, null);
                        }
                    }
                    event.setAmount(damage/2);

                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
    private static boolean isCorrectWeapon(ItemStack weapon) {
        return     weapon.getItem() == ModItemsAdditions.IMPERIAL_INFINITY_SWORD.get()
                || weapon.getItem() == ModItemsAdditions.IMPERIAL_INFINITY_3D_SWORD.get()
                || weapon.getItem() == ModItemsAdditions.IMPERIAL_INFINITY_AXE.get()
                || weapon.getItem() == ModItemsAdditions.IMPERIAL_INFINITY_PAXEL.get()
                || weapon.getItem() == ModItemsAdditions.IMPERIAL_INFINITY_HAMMER.get()
                || weapon.getItem() == ModItemsAdditions.IMPERIAL_INFINITY_PICKAXE.get()
                || weapon.getItem() == ModItemsAdditions.IMPERIAL_INFINITY_SHOVEL.get()
                || weapon.getItem() == ModItemsAdditions.IMPERIAL_INFINITY_BOW.get()
                || weapon.getItem() == ModItemsAdditions.IMPERIAL_INFINITY_HOE.get();
    }
    private static boolean hasFullImperialArmor(Player player) {
        Item boots = player.getInventory().getArmor(0).getItem();
        Item leggings = player.getInventory().getArmor(1).getItem();
        Item breastplate = player.getInventory().getArmor(2).getItem();
        Item helmet = player.getInventory().getArmor(3).getItem();
        boolean armor =
                boots == ModItemsAdditions.IMPERIAL_INFINITY_BOOTS.get()
                        && leggings == ModItemsAdditions.IMPERIAL_INFINITY_LEGGINGS.get()
                        && breastplate == ModItemsAdditions.IMPERIAL_INFINITY_CHESTPLATE.get()
                        && helmet == ModItemsAdditions.IMPERIAL_INFINITY_HELMET.get();
        return armor;
    }
    private static boolean hasFullCarbonArmor(Player player) {
        Item boots = player.getInventory().getArmor(0).getItem();
        Item leggings = player.getInventory().getArmor(1).getItem();
        Item breastplate = player.getInventory().getArmor(2).getItem();
        Item helmet = player.getInventory().getArmor(3).getItem();
        boolean armor =
                boots == ModItemsAdditions.CARBON_BOOTS.get()
                        && leggings == ModItemsAdditions.CARBON_LEGGINGS.get()
                        && breastplate == ModItemsAdditions.CARBON_CHESTPLATE.get()
                        && helmet == ModItemsAdditions.CARBON_HELMET.get();
        return armor;
    }
}