package com.Infinity.Nexus.Mod.config;

import net.minecraft.world.item.Item;

import java.util.List;

public class ConfigUtils {
    //Cria as Variaveis de Configuração
    //ASSEMBLER
    public static int assembler_energy_storage_capacity;
    public static int assembler_energy_transfer_rate;
    public static int assembler_energy_request;
    public static int assembler_fluid_storage_capacity;
    public static int assembler_minimum_tick;
    //CRUSHER
    public static int crusher_energy_storage_capacity;
    public static int crusher_energy_transfer_rate;
    public static int crusher_energy_request;
    public static int crusher_minimum_tick;
    //FACTORY
    public static int factory_energy_storage_capacity;
    public static int factory_energy_transfer_rate;
    public static int factory_energy_request;
    public static int factory_minimum_tick;
    //GENERATOR
    public static int generator_energy_storage_capacity;
    public static int generator_energy_transfer_rate;
    //MATTER CONDENSER
    public static int matter_condenser_energy_storage_capacity;
    public static int matter_condenser_energy_transfer_rate;
    public static int matter_condenser_energy_request;
    public static int matter_condenser_maximum_catalyst_storage;
    //MINER
    public static int miner_energy_storage_capacity;
    public static int miner_energy_transfer_rate;
    public static int miner_fluid_storage_capacity;
    public static int miner_tier_crystal_chance;
    public static int miner_old_tier_crystal_chance;
    public static int miner_minimum_tick;
    //MOB CRUSHER
    public static int mob_crusher_energy_storage_capacity;
    public static int mob_crusher_energy_transfer_rate;
    public static int mob_crusher_energy_request;
    public static int mob_crusher_fluid_storage_capacity;
    public static int mob_crusher_work_speed;
    //PRESS
    public static int press_energy_storage_capacity;
    public static int press_energy_transfer_rate;
    public static int press_minimum_tick;
    //RECYCLER
    public static int recycler_energy_storage_capacity;
    public static int recycler_energy_transfer_rate;
    public static int recycler_energy_request;
    public static int recycler_minimum_tick;
    //SMELTERY
    public static int smelter_energy_storage_capacity;
    public static int smelter_energy_transfer_rate;
    public static int smelter_energy_request;
    public static int smelter_minimum_tick;
    //SOLAR
    public static int solar_energy_storage_capacity;
    public static int solar_energy_transfer_rate;
    //SQUEEZER
    public static int squeezer_energy_storage_capacity;
    public static int squeezer_energy_transfer_rate;
    public static int squeezer_fluid_storage_capacity;
    public static int squeezer_minimum_tick;
    //DISPLAY
    public static double display_rotation_speed_multiplier;
    //INFINITY ARMOR
    public static boolean infinity_armor_can_fly;
    public static boolean infinity_armor_need_fuel;
    public static Item infinity_armor_fuel;
    public static int infinity_armor_fuel_time;
    //IMPERIAL INFINITY ARMOR
    public static boolean imperial_infinity_armor_can_fly;

    public static List<String> list_of_non_placeable_blocks;

    //public static ItemStack getStrengthUpgrade() {
    //    return new ItemStack(Objects.requireNonNull(ForgeRegistries.ITEMS.getValue(new ResourceLocation(AssemblerConfig.list_of_upgrades.get(1)))));
    //}

}