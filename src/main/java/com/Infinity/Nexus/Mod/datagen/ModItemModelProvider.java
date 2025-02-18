package com.Infinity.Nexus.Mod.datagen;

import com.Infinity.Nexus.Mod.InfinityNexusMod;
import com.Infinity.Nexus.Mod.item.ModItemsAdditions;
import com.Infinity.Nexus.Mod.item.ModItemsProgression;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraftforge.client.model.generators.ItemModelBuilder;
import net.minecraftforge.client.model.generators.ItemModelProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.RegistryObject;

public class ModItemModelProvider extends ItemModelProvider {
    public ModItemModelProvider(PackOutput output,ExistingFileHelper existingFileHelper) {
        super(output, InfinityNexusMod.MOD_ID, existingFileHelper);
    }

    @Override
    protected void registerModels() {
        simpleItem(ModItemsAdditions.INFINITY_INGOT);
        simpleItem(ModItemsAdditions.LEAD_INGOT);
        simpleItem(ModItemsAdditions.ALUMINUM_INGOT);
        simpleItem(ModItemsAdditions.NICKEL_INGOT);
        simpleItem(ModItemsAdditions.ZINC_INGOT);
        simpleItem(ModItemsAdditions.SILVER_INGOT);
        simpleItem(ModItemsAdditions.TIN_INGOT);
        simpleItem(ModItemsAdditions.INVAR_INGOT);
        simpleItem(ModItemsAdditions.URANIUM_INGOT);
        simpleItem(ModItemsAdditions.BRASS_INGOT);
        simpleItem(ModItemsAdditions.BRONZE_INGOT);
        simpleItem(ModItemsAdditions.STEEL_INGOT);
        simpleItem(ModItemsAdditions.GRAPHITE_INGOT);
        simpleItem(ModItemsAdditions.IRIDIUM_INGOT);

        simpleItem(ModItemsAdditions.INFINITY_NUGGET);
        simpleItem(ModItemsAdditions.COPPER_NUGGET);
        simpleItem(ModItemsAdditions.LEAD_NUGGET);
        simpleItem(ModItemsAdditions.ALUMINUM_NUGGET);
        simpleItem(ModItemsAdditions.NICKEL_NUGGET);
        simpleItem(ModItemsAdditions.ZINC_NUGGET);
        simpleItem(ModItemsAdditions.SILVER_NUGGET);
        simpleItem(ModItemsAdditions.TIN_NUGGET);
        simpleItem(ModItemsAdditions.INVAR_NUGGET);
        simpleItem(ModItemsAdditions.URANIUM_NUGGET);
        simpleItem(ModItemsAdditions.BRASS_NUGGET);
        simpleItem(ModItemsAdditions.BRONZE_NUGGET);
        simpleItem(ModItemsAdditions.STEEL_NUGGET);

        simpleItem(ModItemsAdditions.INFINIUM_STELLARUM_INGOT);
        simpleItem(ModItemsAdditions.INFINITY_DUST);
        simpleItem(ModItemsAdditions.IRON_DUST);
        simpleItem(ModItemsAdditions.COPPER_DUST);
        simpleItem(ModItemsAdditions.GOLD_DUST);
        simpleItem(ModItemsAdditions.LEAD_DUST);
        simpleItem(ModItemsAdditions.ALUMINUM_DUST);
        simpleItem(ModItemsAdditions.NICKEL_DUST);
        simpleItem(ModItemsAdditions.ZINC_DUST);
        simpleItem(ModItemsAdditions.SILVER_DUST);
        simpleItem(ModItemsAdditions.TIN_DUST);
        simpleItem(ModItemsAdditions.INVAR_DUST);
        simpleItem(ModItemsAdditions.URANIUM_DUST);
        simpleItem(ModItemsAdditions.BRASS_DUST);
        simpleItem(ModItemsAdditions.BRONZE_DUST);
        simpleItem(ModItemsAdditions.STEEL_DUST);
        simpleItem(ModItemsAdditions.GRAPHITE_DUST);
        simpleItem(ModItemsAdditions.DIAMOND_DUST);

        simpleItem(ModItemsAdditions.RAW_INFINITY);
        simpleItem(ModItemsAdditions.RAW_LEAD);
        simpleItem(ModItemsAdditions.RAW_ALUMINUM);
        simpleItem(ModItemsAdditions.RAW_NICKEL);
        simpleItem(ModItemsAdditions.RAW_ZINC);
        simpleItem(ModItemsAdditions.RAW_SILVER);
        simpleItem(ModItemsAdditions.RAW_TIN);
        simpleItem(ModItemsAdditions.RAW_URANIUM);
        simpleItem(ModItemsAdditions.RAW_ALUMINUM);
        simpleItem(ModItemsAdditions.RAW_NICKEL);
        simpleItem(ModItemsAdditions.RAW_SILVER);
        simpleItem(ModItemsAdditions.RAW_URANIUM);

        simpleItem(ModItemsAdditions.INFINITY_SINGULARITY);
        simpleItem(ModItemsAdditions.ITEM_DISLOCATOR);
        simpleItem(ModItemsAdditions.PORTAL_ACTIVATOR);
        simpletools(ModItemsAdditions.INFINITY_SWORD);
        simpletools(ModItemsAdditions.INFINITY_HAMMER);
        simpletools(ModItemsAdditions.INFINITY_PAXEL);
        simpletools(ModItemsAdditions.INFINITY_PICKAXE);
        simpletools(ModItemsAdditions.INFINITY_AXE);
        simpletools(ModItemsAdditions.INFINITY_SHOVEL);
        simpletools(ModItemsAdditions.INFINITY_HOE);
        simpleItem(ModItemsAdditions.INFINITY_HELMET);
        simpleItem(ModItemsAdditions.INFINITY_CHESTPLATE);
        simpleItem(ModItemsAdditions.INFINITY_LEGGINGS);
        simpleItem(ModItemsAdditions.INFINITY_BOOTS);
        simpleItem(ModItemsAdditions.CARBON_HELMET);
        simpleItem(ModItemsAdditions.CARBON_CHESTPLATE);
        simpleItem(ModItemsAdditions.CARBON_LEGGINGS);
        simpleItem(ModItemsAdditions.CARBON_BOOTS);
        simpletools(ModItemsAdditions.CARBON_SWORD);
        simpletools(ModItemsAdditions.CARBON_PICKAXE);
        simpletools(ModItemsAdditions.CARBON_AXE);
        simpletools(ModItemsAdditions.CARBON_SHOVEL);
        simpletools(ModItemsAdditions.CARBON_HOE);
        simpletools(ModItemsAdditions.IMPERIAL_INFINITY_SWORD);
        simpletools(ModItemsAdditions.IMPERIAL_INFINITY_HAMMER);
        simpletools(ModItemsAdditions.IMPERIAL_INFINITY_PAXEL);
        simpletools(ModItemsAdditions.IMPERIAL_INFINITY_PICKAXE);
        simpletools(ModItemsAdditions.IMPERIAL_INFINITY_AXE);
        simpletools(ModItemsAdditions.IMPERIAL_INFINITY_SHOVEL);
        simpletools(ModItemsAdditions.IMPERIAL_INFINITY_HOE);
        simpleItem(ModItemsAdditions.IMPERIAL_INFINITY_HELMET);
        simpleItem(ModItemsAdditions.IMPERIAL_INFINITY_CHESTPLATE);
        simpleItem(ModItemsAdditions.IMPERIAL_INFINITY_LEGGINGS);
        simpleItem(ModItemsAdditions.IMPERIAL_INFINITY_BOOTS);

        simpleItem(ModItemsAdditions.BASIC_CIRCUIT);
        simpleItem(ModItemsAdditions.ADVANCED_CIRCUIT);
        simpleItem(ModItemsAdditions.STARCH);
        simpleItem(ModItemsAdditions.STRAINER);
        simpleItem(ModItemsAdditions.PLASTIC_GOO);
        simpleItem(ModItemsAdditions.GLYCERIN);
        simpleItem(ModItemsAdditions.SLICED_APPLE);
        simpletools(ModItemsAdditions.KNIFE);
        simpletools(ModItemsAdditions.TRANSLOCATOR_LINK);

        simpleItem(ModItemsAdditions.BUCKET_LUBRICANT);
        simpleItem(ModItemsAdditions.BUCKET_ETHANOL);
        simpleItem(ModItemsAdditions.BUCKET_OIL);
        simpleItem(ModItemsAdditions.BUCKET_VINEGAR);
        simpleItem(ModItemsAdditions.BUCKET_SUGARCANE_JUICE);
        simpleItem(ModItemsAdditions.BUCKET_WINE);
        simpleItem(ModItemsAdditions.BUCKET_EXPERIENCE);
        simpleItem(ModItemsAdditions.BUCKET_STARLIQUID);
        simpleItem(ModItemsAdditions.BUCKET_POTATO_JUICE);

        simpleItem(ModItemsAdditions.ALCOHOL_BOTTLE);
        simpleItem(ModItemsAdditions.VINEGAR_BOTTLE);
        simpleItem(ModItemsAdditions.SUGARCANE_JUICE_BOTTLE);
        simpleItem(ModItemsAdditions.WINE_BOTTLE);

        simpleItem(ModItemsProgression.GOLD_ROD_CAST);
        simpleItem(ModItemsProgression.GOLD_SCREW_CAST);
        simpleItem(ModItemsProgression.GOLD_SHEET_CAST);
        simpleItem(ModItemsProgression.GOLD_WIRE_CAST);
        simpleItem(ModItemsProgression.GOLD_INGOT_CAST);
        simpleItem(ModItemsProgression.INFINITY_MESH_CAST);
        simpleItem(ModItemsProgression.RAW_ROD_CLAY_MODEL);
        simpleItem(ModItemsProgression.RAW_SCREW_CLAY_MODEL);
        simpleItem(ModItemsProgression.RAW_SHEET_CLAY_MODEL);
        simpleItem(ModItemsProgression.RAW_WIRE_CLAY_MODEL);
        simpleItem(ModItemsProgression.ROD_CLAY_MODEL);
        simpleItem(ModItemsProgression.SCREW_CLAY_MODEL);
        simpleItem(ModItemsProgression.SHEET_CLAY_MODEL);
        simpleItem(ModItemsProgression.WIRE_CLAY_MODEL);

        simpleItem(ModItemsProgression.COPPER_WIRE);
        simpleItem(ModItemsProgression.LEAD_WIRE);
        simpleItem(ModItemsProgression.TIN_WIRE);
        simpleItem(ModItemsProgression.IRON_WIRE);
        simpleItem(ModItemsProgression.GOLD_WIRE);
        simpleItem(ModItemsProgression.SILVER_WIRE);
        simpleItem(ModItemsProgression.NICKEL_WIRE);
        simpleItem(ModItemsProgression.ZINC_WIRE);
        simpleItem(ModItemsProgression.BRASS_WIRE);
        simpleItem(ModItemsProgression.BRONZE_WIRE);
        simpleItem(ModItemsProgression.STEEL_WIRE);
        simpleItem(ModItemsProgression.ALUMINUM_WIRE);
        simpleItem(ModItemsProgression.INDUSTRIAL_WIRE);
        simpleItem(ModItemsProgression.INFINITY_WIRE);

        simpleItem(ModItemsProgression.COPPER_SCREW);
        simpleItem(ModItemsProgression.LEAD_SCREW);
        simpleItem(ModItemsProgression.TIN_SCREW);
        simpleItem(ModItemsProgression.IRON_SCREW);
        simpleItem(ModItemsProgression.GOLD_SCREW);
        simpleItem(ModItemsProgression.SILVER_SCREW);
        simpleItem(ModItemsProgression.NICKEL_SCREW);
        simpleItem(ModItemsProgression.ZINC_SCREW);
        simpleItem(ModItemsProgression.BRASS_SCREW);
        simpleItem(ModItemsProgression.BRONZE_SCREW);
        simpleItem(ModItemsProgression.STEEL_SCREW);
        simpleItem(ModItemsProgression.ALUMINUM_SCREW);
        simpleItem(ModItemsProgression.INDUSTRIAL_SCREW);
        simpleItem(ModItemsProgression.INFINITY_SCREW);

        simpleItem(ModItemsProgression.COPPER_ROD);
        simpleItem(ModItemsProgression.LEAD_ROD);
        simpleItem(ModItemsProgression.TIN_ROD);
        simpleItem(ModItemsProgression.IRON_ROD);
        simpleItem(ModItemsProgression.GOLD_ROD);
        simpleItem(ModItemsProgression.SILVER_ROD);
        simpleItem(ModItemsProgression.NICKEL_ROD);
        simpleItem(ModItemsProgression.ZINC_ROD);
        simpleItem(ModItemsProgression.BRASS_ROD);
        simpleItem(ModItemsProgression.BRONZE_ROD);
        simpleItem(ModItemsProgression.STEEL_ROD);
        simpleItem(ModItemsProgression.ALUMINUM_ROD);
        simpleItem(ModItemsProgression.INDUSTRIAL_ROD);
        simpleItem(ModItemsProgression.INFINITY_ROD);

        simpleItem(ModItemsProgression.COPPER_SHEET);
        simpleItem(ModItemsProgression.LEAD_SHEET);
        simpleItem(ModItemsProgression.TIN_SHEET);
        simpleItem(ModItemsProgression.IRON_SHEET);
        simpleItem(ModItemsProgression.GOLD_SHEET);
        simpleItem(ModItemsProgression.SILVER_SHEET);
        simpleItem(ModItemsProgression.NICKEL_SHEET);
        simpleItem(ModItemsProgression.ZINC_SHEET);
        simpleItem(ModItemsProgression.BRASS_SHEET);
        simpleItem(ModItemsProgression.BRONZE_SHEET);
        simpleItem(ModItemsProgression.STEEL_SHEET);
        simpleItem(ModItemsProgression.ALUMINUM_SHEET);
        simpleItem(ModItemsProgression.INDUSTRIAL_SHEET);
        simpleItem(ModItemsProgression.INFINITY_SHEET);
        simpleItem(ModItemsProgression.IRIDIUM_MESH);
        simpleItem(ModItemsProgression.CARBON_PLATE);

        simpleItem(ModItemsProgression.BIO_MASS);
        simpleItem(ModItemsProgression.SOLAR_CORE);
        simpleItem(ModItemsProgression.ADVANCED_SOLAR_CORE);
        simpleItem(ModItemsProgression.QUANTUM_SOLAR_CORE);
        simpleItem(ModItemsProgression.RESIDUAL_MATTER);
        simpleItem(ModItemsProgression.UNSTABLE_MATTER);
        simpleItem(ModItemsProgression.STABLE_MATTER);
        simpleItem(ModItemsProgression.IRIDIUM);
        simpleItem(ModItemsProgression.PLASTIC);

        simpleItem(ModItemsAdditions.TERRAIN_MARKER);
        simpleItem(ModItemsAdditions.HAMMER_RANGE_UPGRADE);
        simpleItem(ModItemsAdditions.STAR_FRAGMENT);

        withExistingParent(ModItemsAdditions.ASGREON_SPAWN_EGG.getId().getPath(), mcLoc("item/template_spawn_egg"));
        withExistingParent(ModItemsAdditions.FLARON_SPAWN_EGG.getId().getPath(), mcLoc("item/template_spawn_egg"));

    }
    private ItemModelBuilder simpleItem(RegistryObject<Item> item){
        return withExistingParent(item.getId().getPath(),
                new ResourceLocation("item/generated")).texture("layer0",
                new ResourceLocation(InfinityNexusMod.MOD_ID, "item/" + item.getId().getPath()));
    }
    private ItemModelBuilder simpletools(RegistryObject<Item> item){
        return withExistingParent(item.getId().getPath(),
                new ResourceLocation("item/handheld")).texture("layer0",
                new ResourceLocation(InfinityNexusMod.MOD_ID, "item/" + item.getId().getPath()));
    }
}
