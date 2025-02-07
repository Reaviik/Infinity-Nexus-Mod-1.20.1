package com.Infinity.Nexus.Mod.tab;

import com.Infinity.Nexus.Mod.InfinityNexusMod;
import com.Infinity.Nexus.Mod.block.ModBlocksAdditions;
import com.Infinity.Nexus.Mod.item.ModItemsAdditions;
import com.Infinity.Nexus.Mod.item.ModItemsProgression;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

public class ModTabAdditions {
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS =
            DeferredRegister.create(Registries.CREATIVE_MODE_TAB, InfinityNexusMod.MOD_ID);
    public static final RegistryObject<CreativeModeTab> INFINITY_TAB_ADDITIONS = CREATIVE_MODE_TABS.register("infinity_nexus_mod_addition",
                                                            //Tab Icon
            () -> CreativeModeTab.builder().icon(() -> new ItemStack(ModBlocksAdditions.ASSEMBLY.get()))
                    .title(Component.translatable("itemGroup.infinity_nexus_mod_addition"))
                    .displayItems((pParameters, pOutput) -> {
                        //-------------------------//-------------------------//
                        //machines
                        pOutput.accept(new ItemStack(ModBlocksAdditions.CRUSHER.get()));
                        pOutput.accept(new ItemStack(ModBlocksAdditions.PRESS.get()));
                        pOutput.accept(new ItemStack(ModBlocksAdditions.ASSEMBLY.get()));
                        pOutput.accept(new ItemStack(ModBlocksAdditions.SQUEEZER.get()));
                        pOutput.accept(new ItemStack(ModBlocksAdditions.SMELTERY.get()));
                        pOutput.accept(new ItemStack(ModBlocksAdditions.RECYCLER.get()));
                        pOutput.accept(new ItemStack(ModBlocksAdditions.GENERATOR.get()));
                        pOutput.accept(new ItemStack(ModBlocksAdditions.MATTER_CONDENSER.get()));
                        pOutput.accept(new ItemStack(ModBlocksAdditions.MOB_CRUSHER.get()));
                        pOutput.accept(new ItemStack(ModBlocksAdditions.PLACER.get()));
                        pOutput.accept(new ItemStack(ModBlocksAdditions.INFUSER.get()));
                        pOutput.accept(new ItemStack(ModBlocksAdditions.SOLAR.get()));
                        pOutput.accept(new ItemStack(ModBlocksAdditions.FERMENTATION_BARREL.get()));
                        pOutput.accept(new ItemStack(ModBlocksAdditions.FACTORY.get()));
                        pOutput.accept(new ItemStack(ModBlocksAdditions.DISPLAY.get()));
                        pOutput.accept(new ItemStack(ModBlocksAdditions.ENTITY_DISPLAY.get()));
                        pOutput.accept(new ItemStack(ModBlocksAdditions.ENTITY_CENTRALIZER.get()));
                        pOutput.accept(new ItemStack(ModBlocksAdditions.TANK.get()));
                        pOutput.accept(new ItemStack(ModBlocksAdditions.DEPOT.get()));
                        pOutput.accept(new ItemStack(ModBlocksAdditions.DEPOT_STONE.get()));
                        //-------------------------//-------------------------//
                        //Pedestals
                        pOutput.accept(new ItemStack(ModBlocksAdditions.TECH_PEDESTAL.get()));
                        pOutput.accept(new ItemStack(ModBlocksAdditions.EXPLORATION_PEDESTAL.get()));
                        pOutput.accept(new ItemStack(ModBlocksAdditions.RESOURCE_PEDESTAL.get()));
                        pOutput.accept(new ItemStack(ModBlocksAdditions.MAGIC_PEDESTAL.get()));
                        pOutput.accept(new ItemStack(ModBlocksAdditions.CREATIVITY_PEDESTAL.get()));
                        pOutput.accept(new ItemStack(ModBlocksAdditions.DECOR_PEDESTAL.get()));
                        //-------------------------//-------------------------//
                        //Solar
                        pOutput.accept(new ItemStack(ModItemsAdditions.SOLAR_PANE.get()));
                        pOutput.accept(new ItemStack(ModItemsAdditions.SOLAR_PANE_ADVANCED.get()));
                        pOutput.accept(new ItemStack(ModItemsAdditions.SOLAR_PANE_ULTIMATE.get()));
                        pOutput.accept(new ItemStack(ModItemsAdditions.SOLAR_PANE_QUANTUM.get()));
                        pOutput.accept(new ItemStack(ModItemsAdditions.SOLAR_PANE_PHOTONIC.get()));
                        //-------------------------//-------------------------//
                        //infinity tools
                        pOutput.accept(new ItemStack(ModItemsAdditions.INFINITY_SWORD.get()));
                        pOutput.accept(new ItemStack(ModItemsAdditions.INFINITY_PAXEL.get()));
                        pOutput.accept(new ItemStack(ModItemsAdditions.INFINITY_HAMMER.get()));
                        pOutput.accept(new ItemStack(ModItemsAdditions.INFINITY_PICKAXE.get()));
                        pOutput.accept(new ItemStack(ModItemsAdditions.INFINITY_AXE.get()));
                        pOutput.accept(new ItemStack(ModItemsAdditions.INFINITY_SHOVEL.get()));
                        pOutput.accept(new ItemStack(ModItemsAdditions.INFINITY_HOE.get()));
                        pOutput.accept(new ItemStack(ModItemsAdditions.INFINITY_BOW.get()));
                        pOutput.accept(new ItemStack(ModItemsAdditions.INFINITY_3D_SWORD.get()));
                        //infinity armor
                        pOutput.accept(new ItemStack(ModItemsAdditions.INFINITY_HELMET.get()));
                        pOutput.accept(new ItemStack(ModItemsAdditions.INFINITY_CHESTPLATE.get()));
                        pOutput.accept(new ItemStack(ModItemsAdditions.INFINITY_LEGGINGS.get()));
                        pOutput.accept(new ItemStack(ModItemsAdditions.INFINITY_BOOTS.get()));
                        //imperial tools
                        pOutput.accept(new ItemStack(ModItemsAdditions.IMPERIAL_INFINITY_SWORD.get()));
                        pOutput.accept(new ItemStack(ModItemsAdditions.IMPERIAL_INFINITY_PAXEL.get()));
                        pOutput.accept(new ItemStack(ModItemsAdditions.IMPERIAL_INFINITY_HAMMER.get()));
                        pOutput.accept(new ItemStack(ModItemsAdditions.IMPERIAL_INFINITY_PICKAXE.get()));
                        pOutput.accept(new ItemStack(ModItemsAdditions.IMPERIAL_INFINITY_AXE.get()));
                        pOutput.accept(new ItemStack(ModItemsAdditions.IMPERIAL_INFINITY_SHOVEL.get()));
                        pOutput.accept(new ItemStack(ModItemsAdditions.IMPERIAL_INFINITY_HOE.get()));
                        pOutput.accept(new ItemStack(ModItemsAdditions.IMPERIAL_INFINITY_BOW.get()));
                        pOutput.accept(new ItemStack(ModItemsAdditions.IMPERIAL_INFINITY_3D_SWORD.get()));
                        //imperial armor
                        pOutput.accept(new ItemStack(ModItemsAdditions.IMPERIAL_INFINITY_HELMET.get()));
                        pOutput.accept(new ItemStack(ModItemsAdditions.IMPERIAL_INFINITY_CHESTPLATE.get()));
                        pOutput.accept(new ItemStack(ModItemsAdditions.IMPERIAL_INFINITY_LEGGINGS.get()));
                        pOutput.accept(new ItemStack(ModItemsAdditions.IMPERIAL_INFINITY_BOOTS.get()));
                        //carbon armor
                        pOutput.accept(new ItemStack(ModItemsAdditions.CARBON_HELMET.get()));
                        pOutput.accept(new ItemStack(ModItemsAdditions.CARBON_CHESTPLATE.get()));
                        pOutput.accept(new ItemStack(ModItemsAdditions.CARBON_LEGGINGS.get()));
                        pOutput.accept(new ItemStack(ModItemsAdditions.CARBON_BOOTS.get()));
                        //-------------------------//-------------------------//
                        //Catwalk
                        pOutput.accept(new ItemStack(ModBlocksAdditions.CATWALK.get()));
                        pOutput.accept(new ItemStack(ModBlocksAdditions.CATWALK_2.get()));
                        pOutput.accept(new ItemStack(ModBlocksAdditions.CATWALK_3.get()));
                        pOutput.accept(new ItemStack(ModBlocksAdditions.CATWALK_4.get()));
                        pOutput.accept(new ItemStack(ModBlocksAdditions.CATWALK_5.get()));
                        pOutput.accept(new ItemStack(ModBlocksAdditions.CATWALK_6.get()));
                        pOutput.accept(new ItemStack(ModBlocksAdditions.CATWALK_7.get()));
                        pOutput.accept(new ItemStack(ModBlocksAdditions.CATWALK_8.get()));
                        pOutput.accept(new ItemStack(ModBlocksAdditions.CATWALK_9.get()));
                        pOutput.accept(new ItemStack(ModBlocksAdditions.CATWALK_10.get()));
                        pOutput.accept(new ItemStack(ModBlocksAdditions.CATWALK_11.get()));
                        pOutput.accept(new ItemStack(ModBlocksAdditions.CATWALK_12.get()));
                        pOutput.accept(new ItemStack(ModBlocksAdditions.CATWALK_13.get()));
                        pOutput.accept(new ItemStack(ModBlocksAdditions.CATWALK_16.get()));
                        pOutput.accept(new ItemStack(ModBlocksAdditions.CATWALK_17.get()));
                        pOutput.accept(new ItemStack(ModBlocksAdditions.CATWALK_18.get()));
                        pOutput.accept(new ItemStack(ModBlocksAdditions.CATWALK_14.get()));
                        pOutput.accept(new ItemStack(ModBlocksAdditions.CATWALK_15.get()));
                        //-------------------------//-------------------------//
                        //ingots
                        pOutput.accept(new ItemStack(ModItemsAdditions.SILVER_INGOT.get()));
                        pOutput.accept(new ItemStack(ModItemsAdditions.TIN_INGOT.get()));
                        pOutput.accept(new ItemStack(ModItemsAdditions.LEAD_INGOT.get()));
                        pOutput.accept(new ItemStack(ModItemsAdditions.NICKEL_INGOT.get()));
                        pOutput.accept(new ItemStack(ModItemsAdditions.ZINC_INGOT.get()));
                        pOutput.accept(new ItemStack(ModItemsAdditions.BRASS_INGOT.get()));
                        pOutput.accept(new ItemStack(ModItemsAdditions.BRONZE_INGOT.get()));
                        pOutput.accept(new ItemStack(ModItemsAdditions.STEEL_INGOT.get()));
                        pOutput.accept(new ItemStack(ModItemsAdditions.GRAPHITE_INGOT.get()));
                        pOutput.accept(new ItemStack(ModItemsAdditions.ALUMINUM_INGOT.get()));
                        pOutput.accept(new ItemStack(ModItemsAdditions.URANIUM_INGOT.get()));
                        pOutput.accept(new ItemStack(ModItemsAdditions.INFINITY_INGOT.get()));
                        pOutput.accept(new ItemStack(ModItemsAdditions.INFINIUM_STELLARUM_INGOT.get()));
                        pOutput.accept(new ItemStack(ModItemsAdditions.IRIDIUM_INGOT.get()));
                        //nuggets
                        pOutput.accept(new ItemStack(ModItemsAdditions.COPPER_NUGGET.get()));
                        pOutput.accept(new ItemStack(ModItemsAdditions.SILVER_NUGGET.get()));
                        pOutput.accept(new ItemStack(ModItemsAdditions.TIN_NUGGET.get()));
                        pOutput.accept(new ItemStack(ModItemsAdditions.LEAD_NUGGET.get()));
                        pOutput.accept(new ItemStack(ModItemsAdditions.NICKEL_NUGGET.get()));
                        pOutput.accept(new ItemStack(ModItemsAdditions.ZINC_NUGGET.get()));
                        pOutput.accept(new ItemStack(ModItemsAdditions.BRASS_NUGGET.get()));
                        pOutput.accept(new ItemStack(ModItemsAdditions.BRONZE_NUGGET.get()));
                        pOutput.accept(new ItemStack(ModItemsAdditions.STEEL_NUGGET.get()));
                        pOutput.accept(new ItemStack(ModItemsAdditions.ALUMINUM_NUGGET.get()));
                        pOutput.accept(new ItemStack(ModItemsAdditions.URANIUM_NUGGET.get()));
                        pOutput.accept(new ItemStack(ModItemsAdditions.INFINITY_NUGGET.get()));
                        //dust
                        pOutput.accept(new ItemStack(ModItemsAdditions.COPPER_DUST.get()));
                        pOutput.accept(new ItemStack(ModItemsAdditions.IRON_DUST.get()));
                        pOutput.accept(new ItemStack(ModItemsAdditions.GOLD_DUST.get()));
                        pOutput.accept(new ItemStack(ModItemsAdditions.SILVER_DUST.get()));
                        pOutput.accept(new ItemStack(ModItemsAdditions.TIN_DUST.get()));
                        pOutput.accept(new ItemStack(ModItemsAdditions.LEAD_DUST.get()));
                        pOutput.accept(new ItemStack(ModItemsAdditions.NICKEL_DUST.get()));
                        pOutput.accept(new ItemStack(ModItemsAdditions.ZINC_DUST.get()));
                        pOutput.accept(new ItemStack(ModItemsAdditions.BRASS_DUST.get()));
                        pOutput.accept(new ItemStack(ModItemsAdditions.BRONZE_DUST.get()));
                        pOutput.accept(new ItemStack(ModItemsAdditions.STEEL_DUST.get()));
                        pOutput.accept(new ItemStack(ModItemsAdditions.GRAPHITE_DUST.get()));
                        pOutput.accept(new ItemStack(ModItemsAdditions.ALUMINUM_DUST.get()));
                        pOutput.accept(new ItemStack(ModItemsAdditions.URANIUM_DUST.get()));
                        pOutput.accept(new ItemStack(ModItemsAdditions.INFINITY_DUST.get()));
                        //blocks
                        pOutput.accept(new ItemStack(ModBlocksAdditions.SILVER_BLOCK.get()));
                        pOutput.accept(new ItemStack(ModBlocksAdditions.TIN_BLOCK.get()));
                        pOutput.accept(new ItemStack(ModBlocksAdditions.LEAD_BLOCK.get()));
                        pOutput.accept(new ItemStack(ModBlocksAdditions.NICKEL_BLOCK.get()));
                        pOutput.accept(new ItemStack(ModBlocksAdditions.ZINC_BLOCK.get()));
                        pOutput.accept(new ItemStack(ModBlocksAdditions.BRASS_BLOCK.get()));
                        pOutput.accept(new ItemStack(ModBlocksAdditions.BRONZE_BLOCK.get()));
                        pOutput.accept(new ItemStack(ModBlocksAdditions.STEEL_BLOCK.get()));
                        pOutput.accept(new ItemStack(ModBlocksAdditions.ALUMINUM_BLOCK.get()));
                        pOutput.accept(new ItemStack(ModBlocksAdditions.URANIUM_BLOCK.get()));
                        pOutput.accept(new ItemStack(ModBlocksAdditions.INFINITY_BLOCK.get()));
                        pOutput.accept(new ItemStack(ModBlocksAdditions.INFINIUM_STELLARUM_BLOCK.get()));
                        //raw
                        pOutput.accept(new ItemStack(ModBlocksAdditions.RAW_SILVER_BLOCK.get()));
                        pOutput.accept(new ItemStack(ModBlocksAdditions.RAW_TIN_BLOCK.get()));
                        pOutput.accept(new ItemStack(ModBlocksAdditions.RAW_LEAD_BLOCK.get()));
                        pOutput.accept(new ItemStack(ModBlocksAdditions.RAW_NICKEL_BLOCK.get()));
                        pOutput.accept(new ItemStack(ModBlocksAdditions.RAW_ZINC_BLOCK.get()));
                        pOutput.accept(new ItemStack(ModBlocksAdditions.RAW_ALUMINUM_BLOCK.get()));
                        pOutput.accept(new ItemStack(ModBlocksAdditions.RAW_URANIUM_BLOCK.get()));
                        pOutput.accept(new ItemStack(ModBlocksAdditions.RAW_INFINITY_BLOCK.get()));

                        pOutput.accept(new ItemStack(ModItemsAdditions.RAW_SILVER.get()));
                        pOutput.accept(new ItemStack(ModItemsAdditions.RAW_TIN.get()));
                        pOutput.accept(new ItemStack(ModItemsAdditions.RAW_LEAD.get()));
                        pOutput.accept(new ItemStack(ModItemsAdditions.RAW_NICKEL.get()));
                        pOutput.accept(new ItemStack(ModItemsAdditions.RAW_ZINC.get()));
                        pOutput.accept(new ItemStack(ModItemsAdditions.RAW_ALUMINUM.get()));
                        pOutput.accept(new ItemStack(ModItemsAdditions.RAW_URANIUM.get()));
                        pOutput.accept(new ItemStack(ModItemsAdditions.RAW_INFINITY.get()));

                        //ores
                        pOutput.accept(new ItemStack(ModBlocksAdditions.SILVER_ORE.get()));
                        pOutput.accept(new ItemStack(ModBlocksAdditions.TIN_ORE.get()));
                        pOutput.accept(new ItemStack(ModBlocksAdditions.LEAD_ORE.get()));
                        pOutput.accept(new ItemStack(ModBlocksAdditions.NICKEL_ORE.get()));
                        pOutput.accept(new ItemStack(ModBlocksAdditions.ZINC_ORE.get()));
                        pOutput.accept(new ItemStack(ModBlocksAdditions.ALUMINUM_ORE.get()));
                        pOutput.accept(new ItemStack(ModBlocksAdditions.URANIUM_ORE.get()));
                        pOutput.accept(new ItemStack(ModBlocksAdditions.INFINITY_ORE.get()));
                        //deepslates ores
                        pOutput.accept(new ItemStack(ModBlocksAdditions.DEEPSLATE_SILVER_ORE.get()));
                        pOutput.accept(new ItemStack(ModBlocksAdditions.DEEPSLATE_TIN_ORE.get()));
                        pOutput.accept(new ItemStack(ModBlocksAdditions.DEEPSLATE_LEAD_ORE.get()));
                        pOutput.accept(new ItemStack(ModBlocksAdditions.DEEPSLATE_NICKEL_ORE.get()));
                        pOutput.accept(new ItemStack(ModBlocksAdditions.DEEPSLATE_ZINC_ORE.get()));
                        pOutput.accept(new ItemStack(ModBlocksAdditions.DEEPSLATE_ALUMINUM_ORE.get()));
                        pOutput.accept(new ItemStack(ModBlocksAdditions.DEEPSLATE_URANIUM_ORE.get()));
                        pOutput.accept(new ItemStack(ModBlocksAdditions.DEEPSLATE_INFINITY_ORE.get()));

                        pOutput.accept(new ItemStack(ModItemsAdditions.INFINITY_SINGULARITY.get()));
                        pOutput.accept(new ItemStack(ModItemsAdditions.PORTAL_ACTIVATOR.get()));
                        pOutput.accept(new ItemStack(ModBlocksAdditions.ASPHALT.get()));
                        pOutput.accept(new ItemStack(ModItemsAdditions.ITEM_DISLOCATOR.get()));
                        pOutput.accept(new ItemStack(ModBlocksAdditions.EXPLORAR_PORTAL_FRAME.get()));

                        pOutput.accept(new ItemStack(ModItemsAdditions.BUCKET_LUBRICANT.get()));
                        pOutput.accept(new ItemStack(ModItemsAdditions.BUCKET_POTATO_JUICE.get()));
                        pOutput.accept(new ItemStack(ModItemsAdditions.ALCOHOL_BOTTLE.get()));
                        pOutput.accept(new ItemStack(ModItemsAdditions.WINE_BOTTLE.get()));
                        pOutput.accept(new ItemStack(ModItemsAdditions.VINEGAR_BOTTLE.get()));
                        pOutput.accept(new ItemStack(ModItemsAdditions.SUGARCANE_JUICE_BOTTLE.get()));
                        pOutput.accept(new ItemStack(ModItemsAdditions.STARCH.get()));
                        pOutput.accept(new ItemStack(ModItemsAdditions.STRAINER.get()));
                        pOutput.accept(new ItemStack(ModItemsAdditions.PLASTIC_GOO.get()));
                        pOutput.accept(new ItemStack(ModItemsAdditions.GLYCERIN.get()));
                        pOutput.accept(new ItemStack(ModItemsAdditions.SLICED_APPLE.get()));
                        pOutput.accept(new ItemStack(ModItemsAdditions.KNIFE.get()));

                        pOutput.accept(new ItemStack(ModItemsAdditions.BASIC_CIRCUIT.get()));
                        pOutput.accept(new ItemStack(ModItemsAdditions.ADVANCED_CIRCUIT.get()));

                        pOutput.accept(new ItemStack(ModItemsAdditions.TERRAIN_MARKER.get()));
                        pOutput.accept(new ItemStack(ModItemsAdditions.HAMMER_RANGE_UPGRADE.get()));

                        pOutput.accept(new ItemStack(ModItemsAdditions.ASGREON_SPAWN_EGG.get()));
                        pOutput.accept(new ItemStack(ModItemsAdditions.FLARON_SPAWN_EGG.get()));

                        pOutput.accept(new ItemStack(ModItemsAdditions.STAR_FRAGMENT.get()));
                        pOutput.accept(new ItemStack(ModItemsProgression.RESIDUAL_MATTER.get()));
                        pOutput.accept(new ItemStack(ModItemsProgression.SOLAR_CORE.get()));
                        pOutput.accept(new ItemStack(ModItemsProgression.ADVANCED_SOLAR_CORE.get()));
                        pOutput.accept(new ItemStack(ModItemsProgression.QUANTUM_SOLAR_CORE.get()));
                        pOutput.accept(new ItemStack(ModItemsProgression.BIO_MASS.get()));
                        pOutput.accept(new ItemStack(ModItemsProgression.UNSTABLE_MATTER.get()));
                        pOutput.accept(new ItemStack(ModItemsProgression.STABLE_MATTER.get()));
                        pOutput.accept(new ItemStack(ModItemsProgression.IRIDIUM.get()));
                        pOutput.accept(new ItemStack(ModItemsProgression.PLASTIC.get()));
                        pOutput.accept(new ItemStack(ModItemsProgression.CARBON_PLATE.get()));

                        //-------------------------//-------------------------//
                    })
                    .build());
    public static void register(IEventBus eventBus) {
        CREATIVE_MODE_TABS.register(eventBus);
    }
}
