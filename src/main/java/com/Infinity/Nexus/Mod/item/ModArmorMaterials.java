package com.Infinity.Nexus.Mod.item;

import com.Infinity.Nexus.Mod.InfinityNexusMod;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.crafting.Ingredient;

import java.util.function.Supplier;

public enum ModArmorMaterials implements ArmorMaterial {

    IMPERIAL("imperial_infinity", -1,
            new int[]{0,0,0,0}, 80, SoundEvents.ARMOR_EQUIP_NETHERITE, 0f, 0f, () -> {
        return Ingredient.of(ModItemsAdditions.INFINITY_INGOT.get());
    }),
    INFINITY("infinity", -1,
            new int[]{15,25,20,15},
            0, SoundEvents.ARMOR_EQUIP_DIAMOND, 0f, 0f, () ->{
        return Ingredient.of(ModItemsAdditions.INFINITY_INGOT.get());
    }),
    CARBON("carbon", 150000,
            new int[]{0,0,0,0}, 80, SoundEvents.ARMOR_EQUIP_NETHERITE, 0f, 0f, () -> {
        return Ingredient.of(ModItemsProgression.CARBON_PLATE.get());
    });

    //Boots, Leggings, Chestplate, Helmet

    private static final int[] HEALTH_PER_SLOT = new int[]{13,15,20,12};
    private final String name;
    private final int durabilityMultiplier;
    private final int[] slotProtections;
    private final int enchantmentValue;
    private final SoundEvent sound;
    private final float toughness;
    private final float knockbackResistance;
    private final Supplier<Ingredient> repairIngredient;

    ModArmorMaterials(String name, int durabilityMultiplier, int[] slotProtections, int enchantmentValue,
                      SoundEvent sound, float toughness, float knockbackResistance, Supplier<Ingredient> repairIngredient) {
        this.name = name;
        this.durabilityMultiplier = durabilityMultiplier;
        this.slotProtections = slotProtections;
        this.enchantmentValue = enchantmentValue;
        this.sound = sound;
        this.toughness = toughness;
        this.knockbackResistance = knockbackResistance;
        this.repairIngredient = repairIngredient;
    }

    public int getDurabilityForType(ArmorItem.Type ptype) {
        return HEALTH_PER_SLOT[ptype.ordinal()] * this.durabilityMultiplier;
    }

    public int getDefenseForType(ArmorItem.Type ptype) {
        return this.slotProtections[ptype.ordinal()];
    }

    public int getEnchantmentValue() {
        return this.enchantmentValue;
    }

    public SoundEvent getEquipSound() {
        return this.sound;
    }
    public Ingredient getRepairIngredient() {
        return this.repairIngredient.get();
    }

    public String getName() {
        return InfinityNexusMod.MOD_ID + ":" + this.name;
    }

    public float getToughness() {
        return this.toughness;
    }

    public float getKnockbackResistance() {
        return this.knockbackResistance;
    }
}