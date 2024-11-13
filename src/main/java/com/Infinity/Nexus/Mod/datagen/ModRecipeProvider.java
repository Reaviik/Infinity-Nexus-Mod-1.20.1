package com.Infinity.Nexus.Mod.datagen;

import com.Infinity.Nexus.Core.items.ModItems;
import com.Infinity.Nexus.Miner.block.ModBlocksMiner;
import com.Infinity.Nexus.Miner.item.ModItemsMiner;
import com.Infinity.Nexus.Mod.InfinityNexusMod;
import com.Infinity.Nexus.Mod.block.ModBlocksAdditions;
import com.Infinity.Nexus.Mod.block.ModBlocksProgression;
import com.Infinity.Nexus.Mod.item.ModItemsAdditions;
import com.Infinity.Nexus.Mod.item.ModItemsProgression;
import net.minecraft.advancements.critereon.ItemPredicate;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.*;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.AbstractCookingRecipe;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.common.crafting.conditions.IConditionBuilder;

import java.util.List;
import java.util.function.Consumer;

public class ModRecipeProvider extends RecipeProvider implements IConditionBuilder {
    private final List<ItemLike> LEAD_SMELTING = List.of(ModItemsAdditions.RAW_LEAD.get(), ModBlocksAdditions.LEAD_ORE.get(), ModBlocksAdditions.DEEPSLATE_LEAD_ORE.get(), ModItemsAdditions.LEAD_DUST.get());
    private final List<ItemLike> TIN_SMELTING = List.of(ModItemsAdditions.RAW_TIN.get(), ModBlocksAdditions.TIN_ORE.get(), ModBlocksAdditions.DEEPSLATE_TIN_ORE.get(), ModItemsAdditions.TIN_DUST.get());
    private final List<ItemLike> ALUMINUM_SMELTING = List.of(ModItemsAdditions.RAW_ALUMINUM.get(), ModBlocksAdditions.ALUMINUM_ORE.get(), ModBlocksAdditions.DEEPSLATE_ALUMINUM_ORE.get(), ModItemsAdditions.ALUMINUM_DUST.get());
    private final List<ItemLike> NICKEL_SMELTING = List.of(ModItemsAdditions.RAW_NICKEL.get(), ModBlocksAdditions.NICKEL_ORE.get(), ModBlocksAdditions.DEEPSLATE_NICKEL_ORE.get(), ModItemsAdditions.NICKEL_DUST.get());
    private final List<ItemLike> ZINC_SMELTING = List.of(ModItemsAdditions.RAW_ZINC.get(), ModBlocksAdditions.ZINC_ORE.get(), ModBlocksAdditions.DEEPSLATE_ZINC_ORE.get(), ModItemsAdditions.ZINC_DUST.get());
    private final List<ItemLike> SILVER_SMELTING = List.of(ModItemsAdditions.RAW_SILVER.get(), ModBlocksAdditions.SILVER_ORE.get(), ModBlocksAdditions.DEEPSLATE_SILVER_ORE.get(), ModItemsAdditions.SILVER_DUST.get());
    private final List<ItemLike> URANIUM_SMELTING = List.of(ModItemsAdditions.RAW_URANIUM.get(), ModBlocksAdditions.URANIUM_ORE.get(), ModBlocksAdditions.DEEPSLATE_URANIUM_ORE.get(), ModItemsAdditions.URANIUM_DUST.get());

    public ModRecipeProvider(PackOutput pOutput) {
        super(pOutput);
    }

    @Override
    protected void buildRecipes(Consumer<FinishedRecipe> pWriter) {
        //infinium stellarum
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModBlocksAdditions.INFINIUM_STELLARUM_BLOCK.get()).pattern("###").pattern("###").pattern("###").define('#', ModItemsAdditions.INFINIUM_STELLARUM_INGOT.get()).unlockedBy("has_infinium_stellarum_ingot", inventoryTrigger(ItemPredicate.Builder.item().of(ModItemsAdditions.INFINIUM_STELLARUM_INGOT.get()).build())).save(pWriter, "infinium_stellarum_block_from_ingot");
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModBlocksAdditions.INFINITY_BLOCK.get()).pattern("###").pattern("###").pattern("###").define('#', ModItemsAdditions.INFINITY_INGOT.get()).unlockedBy("has_infinity_ingot", inventoryTrigger(ItemPredicate.Builder.item().of(ModItemsAdditions.INFINITY_INGOT.get()).build())).save(pWriter, "infinity_block_from_ingot");
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModBlocksAdditions.LEAD_BLOCK.get()).pattern("###").pattern("###").pattern("###").define('#', ModItemsAdditions.LEAD_INGOT.get()).unlockedBy("has_lead_ingot", inventoryTrigger(ItemPredicate.Builder.item().of(ModItemsAdditions.LEAD_INGOT.get()).build())).save(pWriter, "lead_block_from_ingot");
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModBlocksAdditions.ALUMINUM_BLOCK.get()).pattern("###").pattern("###").pattern("###").define('#', ModItemsAdditions.ALUMINUM_INGOT.get()).unlockedBy("has_aluminum_ingot", inventoryTrigger(ItemPredicate.Builder.item().of(ModItemsAdditions.ALUMINUM_INGOT.get()).build())).save(pWriter, "aluminum_block_from_ingot");
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModBlocksAdditions.NICKEL_BLOCK.get()).pattern("###").pattern("###").pattern("###").define('#', ModItemsAdditions.NICKEL_INGOT.get()).unlockedBy("has_nickel_ingot", inventoryTrigger(ItemPredicate.Builder.item().of(ModItemsAdditions.NICKEL_INGOT.get()).build())).save(pWriter, "nickel_block_from_ingot");
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModBlocksAdditions.ZINC_BLOCK.get()).pattern("###").pattern("###").pattern("###").define('#', ModItemsAdditions.ZINC_INGOT.get()).unlockedBy("has_zinc_ingot", inventoryTrigger(ItemPredicate.Builder.item().of(ModItemsAdditions.ZINC_INGOT.get()).build())).save(pWriter, "zinc_block_from_ingot");
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModBlocksAdditions.SILVER_BLOCK.get()).pattern("###").pattern("###").pattern("###").define('#', ModItemsAdditions.SILVER_INGOT.get()).unlockedBy("has_silver_ingot", inventoryTrigger(ItemPredicate.Builder.item().of(ModItemsAdditions.SILVER_INGOT.get()).build())).save(pWriter, "silver_block_from_ingot");
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModBlocksAdditions.URANIUM_BLOCK.get()).pattern("###").pattern("###").pattern("###").define('#', ModItemsAdditions.URANIUM_INGOT.get()).unlockedBy("has_uranium_ingot", inventoryTrigger(ItemPredicate.Builder.item().of(ModItemsAdditions.URANIUM_INGOT.get()).build())).save(pWriter, "uranium_block_from_ingot");
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModBlocksAdditions.TIN_BLOCK.get()).pattern("###").pattern("###").pattern("###").define('#', ModItemsAdditions.TIN_INGOT.get()).unlockedBy("has_tin_ingot", inventoryTrigger(ItemPredicate.Builder.item().of(ModItemsAdditions.TIN_INGOT.get()).build())).save(pWriter, "tin_block_from_ingot");
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModBlocksAdditions.BRASS_BLOCK.get()).pattern("###").pattern("###").pattern("###").define('#', ModItemsAdditions.BRASS_INGOT.get()).unlockedBy("has_brass_ingot", inventoryTrigger(ItemPredicate.Builder.item().of(ModItemsAdditions.BRASS_INGOT.get()).build())).save(pWriter, "brass_block_from_ingot");
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModBlocksAdditions.STEEL_BLOCK.get()).pattern("###").pattern("###").pattern("###").define('#', ModItemsAdditions.STEEL_INGOT.get()).unlockedBy("has_steel_ingot", inventoryTrigger(ItemPredicate.Builder.item().of(ModItemsAdditions.STEEL_INGOT.get()).build())).save(pWriter, "steel_block_from_ingot");
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModBlocksAdditions.BRONZE_BLOCK.get()).pattern("###").pattern("###").pattern("###").define('#', ModItemsAdditions.BRONZE_INGOT.get()).unlockedBy("has_bronze_ingot", inventoryTrigger(ItemPredicate.Builder.item().of(ModItemsAdditions.BRONZE_INGOT.get()).build())).save(pWriter, "bronze_block_from_ingot");

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModBlocksAdditions.EXPLORAR_PORTAL_FRAME.get()).pattern("A#A").pattern("#B#").pattern("A#A").define('#', Items.GOLD_INGOT).define('A', Blocks.WHITE_CONCRETE).define('B', ModItemsAdditions.INFINITY_DUST.get()).unlockedBy("has_infinity_dust", inventoryTrigger(ItemPredicate.Builder.item().of(ModItemsAdditions.INFINITY_DUST.get()).build())).save(pWriter, "explorar_portal_frame");
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModBlocksAdditions.ASPHALT.get(), 8).pattern("A#A").pattern("#B#").pattern("A#A").define('#', Blocks.GRAVEL).define('A', Blocks.SAND).define('B', Items.GRAY_DYE).unlockedBy("has_gray_dye", inventoryTrigger(ItemPredicate.Builder.item().of(Items.GRAY_DYE).build())).save(pWriter, "asphalt");
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItemsAdditions.PORTAL_ACTIVATOR.get()).pattern(" # ").pattern("#A#").pattern(" # ").define('#', ModItemsAdditions.INFINITY_DUST.get()).define('A', Items.ENDER_PEARL).unlockedBy("has_infinity_dust", inventoryTrigger(ItemPredicate.Builder.item().of(ModItemsAdditions.INFINITY_DUST.get()).build())).save(pWriter, "catalyst");
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItemsProgression.RAW_ROD_CLAY_MODEL.get()).pattern("  #").pattern(" A ").pattern("#  ").define('#', Items.CLAY_BALL).define('A', Items.IRON_NUGGET).unlockedBy("has_clay_ball", inventoryTrigger(ItemPredicate.Builder.item().of(Items.CLAY_BALL).build())).save(pWriter, "raw_clay_rod");
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItemsProgression.RAW_SCREW_CLAY_MODEL.get()).pattern("#").pattern("A").pattern(" ").define('#', Items.CLAY_BALL).define('A', Items.IRON_NUGGET).unlockedBy("has_clay_ball", inventoryTrigger(ItemPredicate.Builder.item().of(Items.CLAY_BALL).build())).save(pWriter, "raw_clay_screw");
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItemsProgression.RAW_SHEET_CLAY_MODEL.get()).pattern(" # ").pattern("#A#").pattern(" # ").define('#', Items.CLAY_BALL).define('A', Items.IRON_NUGGET).unlockedBy("has_clay_ball", inventoryTrigger(ItemPredicate.Builder.item().of(Items.CLAY_BALL).build())).save(pWriter, "raw_clay_sheet");
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItemsProgression.RAW_WIRE_CLAY_MODEL.get()).pattern("#").pattern("A").pattern("#").define('#', Items.CLAY_BALL).define('A', Items.IRON_NUGGET).unlockedBy("has_clay_ball", inventoryTrigger(ItemPredicate.Builder.item().of(Items.CLAY_BALL).build())).save(pWriter, "raw_clay_wire");

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModBlocksAdditions.CRUSHER.get()).pattern("ABA").pattern("CDC").pattern(" E ").define('A', Items.FLINT).define('B', Blocks.COPPER_BLOCK).define('C', Blocks.COBBLESTONE).define('D', ModBlocksMiner.WOOD_STRUCTURE.get()).define('E', ModItemsAdditions.BASIC_CIRCUIT.get()).unlockedBy("has_wood_casings", inventoryTrigger(ItemPredicate.Builder.item().of(ModBlocksMiner.WOOD_STRUCTURE.get()).build())).save(pWriter, "crusher");
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModBlocksAdditions.PLACER.get()).pattern("ABA").pattern("CDC").pattern(" E ").define('A', Items.DISPENSER).define('B', Blocks.IRON_BARS).define('C', Items.COPPER_INGOT).define('D', ModBlocksMiner.WOOD_STRUCTURE.get()).define('E', ModItemsAdditions.BASIC_CIRCUIT.get()).unlockedBy("has_wood_casings", inventoryTrigger(ItemPredicate.Builder.item().of(ModBlocksMiner.WOOD_STRUCTURE.get()).build())).save(pWriter, "placer");
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModBlocksAdditions.MOB_CRUSHER.get()).pattern("ABA").pattern("CDC").pattern(" E ").define('A', Items.DIAMOND_SWORD).define('B', Blocks.IRON_BLOCK).define('C', Blocks.COPPER_BLOCK).define('D', ModBlocksMiner.COPPER_STRUCTURE.get()).define('E', ModItemsAdditions.BASIC_CIRCUIT.get()).unlockedBy("has_wood_casings", inventoryTrigger(ItemPredicate.Builder.item().of(ModBlocksMiner.COPPER_STRUCTURE.get()).build())).save(pWriter, "mob_crusher");
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModBlocksAdditions.GENERATOR.get()).pattern("ABA").pattern("CDC").pattern(" E ").define('A', Blocks.COBBLESTONE).define('B', Blocks.COPPER_BLOCK).define('C', Blocks.COBBLESTONE).define('D', ModBlocksMiner.WOOD_STRUCTURE.get()).define('E', ModItemsAdditions.BASIC_CIRCUIT.get()).unlockedBy("has_wood_casings", inventoryTrigger(ItemPredicate.Builder.item().of(ModBlocksMiner.WOOD_STRUCTURE.get()).build())).save(pWriter, "generator");
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModBlocksAdditions.SQUEEZER.get()).pattern("ABA").pattern("CDC").pattern(" E ").define('A', Items.BUCKET).define('B', Blocks.COPPER_BLOCK).define('C', Blocks.COBBLESTONE).define('D', ModBlocksMiner.WOOD_STRUCTURE.get()).define('E', ModItemsAdditions.BASIC_CIRCUIT.get()).unlockedBy("has_wood_casings", inventoryTrigger(ItemPredicate.Builder.item().of(ModBlocksMiner.WOOD_STRUCTURE.get()).build())).save(pWriter, "squeezer");
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModBlocksAdditions.ASSEMBLY.get()).pattern("ABA").pattern("CDC").pattern(" E ").define('A', Blocks.CRAFTING_TABLE).define('B', Blocks.COPPER_BLOCK).define('C', Blocks.COBBLESTONE).define('D', ModBlocksMiner.WOOD_STRUCTURE.get()).define('E', ModItemsAdditions.BASIC_CIRCUIT.get()).unlockedBy("has_wood_casings", inventoryTrigger(ItemPredicate.Builder.item().of(ModBlocksMiner.WOOD_STRUCTURE.get()).build())).save(pWriter, "assembly");
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModBlocksAdditions.PRESS.get()).pattern("ABA").pattern("CDC").pattern(" E ").define('A', Blocks.ANVIL).define('B', Blocks.COPPER_BLOCK).define('C', Blocks.COBBLESTONE).define('D', ModBlocksMiner.WOOD_STRUCTURE.get()).define('E', ModItemsAdditions.BASIC_CIRCUIT.get()).unlockedBy("has_wood_casings", inventoryTrigger(ItemPredicate.Builder.item().of(ModBlocksMiner.WOOD_STRUCTURE.get()).build())).save(pWriter, "press");
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModBlocksAdditions.SMELTERY.get()).pattern("ABA").pattern("CDC").pattern(" E ").define('A', Blocks.BLAST_FURNACE).define('B', Blocks.COPPER_BLOCK).define('C', Blocks.MAGMA_BLOCK).define('D', ModBlocksMiner.STONE_STRUCTURE.get()).define('E', ModItemsAdditions.BASIC_CIRCUIT.get()).unlockedBy("has_iron_casings", inventoryTrigger(ItemPredicate.Builder.item().of(ModBlocksMiner.STONE_STRUCTURE.get()).build())).save(pWriter, "smeltery");
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModBlocksAdditions.FERMENTATION_BARREL.get()).pattern("AB ").pattern("CDC").define('A', Blocks.OAK_BUTTON).define('B', Blocks.BARREL).define('C', Items.STICK).define('D', ModBlocksMiner.WOOD_STRUCTURE.get()).unlockedBy("has_barrel", inventoryTrigger(ItemPredicate.Builder.item().of(Blocks.BARREL).build())).save(pWriter, "fermentation_barrel");

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItemsAdditions.BASIC_CIRCUIT.get()).pattern("ABA").pattern("BCB").pattern("ABA").define('A', Items.REDSTONE).define('B', Items.COPPER_INGOT).define('C', Blocks.HEAVY_WEIGHTED_PRESSURE_PLATE).unlockedBy("has_redstone", inventoryTrigger(ItemPredicate.Builder.item().of(Items.REDSTONE).build())).save(pWriter, "basic_circuit");
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItemsAdditions.ADVANCED_CIRCUIT.get()).pattern("ABA").pattern("BCB").pattern("ABA").define('A', ModItemsAdditions.BRASS_INGOT.get()).define('B', Items.REDSTONE).define('C', Blocks.LIGHT_WEIGHTED_PRESSURE_PLATE).unlockedBy("has_brass_ingot", inventoryTrigger(ItemPredicate.Builder.item().of(Items.REDSTONE).build())).save(pWriter, "advanced_circuit");
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItemsAdditions.ITEM_DISLOCATOR.get()).pattern("A A").pattern("B B").pattern("BBB").define('A', ModItemsAdditions.INFINITY_NUGGET.get()).define('B', Items.IRON_INGOT).unlockedBy("has_redstone", inventoryTrigger(ItemPredicate.Builder.item().of(Items.REDSTONE).build())).save(pWriter, "item_dislocator");

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItemsAdditions.INFINITY_HELMET.get()).pattern("AAA").pattern("ABA").define('A', ModItemsAdditions.INFINITY_INGOT.get()).define('B', ModItemsAdditions.CARBON_HELMET.get()).unlockedBy("has_infinity_ingot", inventoryTrigger(ItemPredicate.Builder.item().of(ModItemsAdditions.INFINITY_INGOT.get()).build())).save(pWriter, "infinity_helmet");
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItemsAdditions.INFINITY_CHESTPLATE.get()).pattern("ABA").pattern("AAA").pattern("AAA").define('A', ModItemsAdditions.INFINITY_INGOT.get()).define('B', ModItemsAdditions.CARBON_CHESTPLATE.get()).unlockedBy("has_infinity_ingot", inventoryTrigger(ItemPredicate.Builder.item().of(ModItemsAdditions.INFINITY_INGOT.get()).build())).save(pWriter, "infinity_chestplate");
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItemsAdditions.INFINITY_LEGGINGS.get()).pattern("AAA").pattern("ABA").pattern("A A").define('A', ModItemsAdditions.INFINITY_INGOT.get()).define('B', ModItemsAdditions.CARBON_LEGGINGS.get()).unlockedBy("has_infinity_ingot", inventoryTrigger(ItemPredicate.Builder.item().of(ModItemsAdditions.INFINITY_INGOT.get()).build())).save(pWriter, "infinity_leggings");
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItemsAdditions.INFINITY_BOOTS.get()).pattern("A A").pattern("ABA").define('A', ModItemsAdditions.INFINITY_INGOT.get()).define('B', ModItemsAdditions.CARBON_LEGGINGS.get()).unlockedBy("has_infinity_ingot", inventoryTrigger(ItemPredicate.Builder.item().of(ModItemsAdditions.INFINITY_INGOT.get()).build())).save(pWriter, "infinity_boots");ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItemsAdditions.INFINITY_SWORD.get()).pattern("A").pattern("A").pattern("B").define('A', ModItemsAdditions.INFINITY_INGOT.get()).define('B', Items.STICK).unlockedBy("has_infinity_ingot", inventoryTrigger(ItemPredicate.Builder.item().of(ModItemsAdditions.INFINITY_INGOT.get()).build())).save(pWriter, "infinity_sword");

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItemsAdditions.INFINITY_BOW.get()).pattern(" AB").pattern("A B").pattern(" AB").define('A', ModItemsAdditions.INFINITY_INGOT.get()).define('B', Items.STRING).unlockedBy("has_infinity_ingot", inventoryTrigger(ItemPredicate.Builder.item().of(ModItemsAdditions.INFINITY_INGOT.get()).build())).save(pWriter, "infinity_bow");
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItemsAdditions.INFINITY_PICKAXE.get()).pattern("AAA").pattern(" B ").pattern(" B ").define('A', ModItemsAdditions.INFINITY_INGOT.get()).define('B', Items.STICK).unlockedBy("has_infinity_ingot", inventoryTrigger(ItemPredicate.Builder.item().of(ModItemsAdditions.INFINITY_INGOT.get()).build())).save(pWriter, "infinity_pickaxe");
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItemsAdditions.INFINITY_HAMMER.get()).pattern("AAA").pattern("ABA").pattern(" B ").define('A', ModItemsAdditions.INFINITY_INGOT.get()).define('B', Items.STICK).unlockedBy("has_infinity_block", inventoryTrigger(ItemPredicate.Builder.item().of(ModBlocksAdditions.INFINITY_BLOCK.get()).build())).save(pWriter, "infinity_hammer");
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItemsAdditions.IMPERIAL_INFINITY_HAMMER.get()).pattern("AAA").pattern(" B ").pattern(" B ").define('A', ModBlocksAdditions.INFINITY_BLOCK.get()).define('B', Items.STICK).unlockedBy("has_infinity_block", inventoryTrigger(ItemPredicate.Builder.item().of(ModBlocksAdditions.INFINITY_BLOCK.get()).build())).save(pWriter, "imperial_infinity_hammer");
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItemsAdditions.INFINITY_AXE.get()).pattern("AA ").pattern("AB ").pattern(" B ").define('A', ModItemsAdditions.INFINITY_INGOT.get()).define('B', Items.STICK).unlockedBy("has_infinity_ingot", inventoryTrigger(ItemPredicate.Builder.item().of(ModItemsAdditions.INFINITY_INGOT.get()).build())).save(pWriter, "infinity_axe");
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItemsAdditions.INFINITY_SHOVEL.get()).pattern("A").pattern("B").pattern("B").define('A', ModItemsAdditions.INFINITY_INGOT.get()).define('B', Items.STICK).unlockedBy("has_infinity_ingot", inventoryTrigger(ItemPredicate.Builder.item().of(ModItemsAdditions.INFINITY_INGOT.get()).build())).save(pWriter, "infinity_shovel");
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItemsAdditions.INFINITY_HOE.get()).pattern("AA").pattern(" B").pattern(" B").define('A', ModItemsAdditions.INFINITY_INGOT.get()).define('B', Items.STICK).unlockedBy("has_infinity_ingot", inventoryTrigger(ItemPredicate.Builder.item().of(ModItemsAdditions.INFINITY_INGOT.get()).build())).save(pWriter, "infinity_hoe");
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItemsAdditions.INFINITY_PAXEL.get()).pattern("ABC").pattern(" D ").pattern(" E ").define('A', ModItemsAdditions.INFINITY_AXE.get()).define('B', ModItemsAdditions.INFINITY_SWORD.get()).define('C', ModItemsAdditions.INFINITY_PICKAXE.get()).define('D', ModItemsAdditions.INFINITY_SHOVEL.get()).define('E', ModItemsAdditions.INFINITY_HOE.get()).unlockedBy("has_infinity_axe", inventoryTrigger(ItemPredicate.Builder.item().of(ModItemsAdditions.INFINITY_AXE.get()).build())).save(pWriter, "infinity_paxel");

        //Imperial
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItemsAdditions.IMPERIAL_INFINITY_SWORD.get()).pattern("A").pattern("B").pattern("C").define('A', ModItemsAdditions.INFINITY_SINGULARITY.get()).define('B', ModItemsAdditions.INFINITY_SWORD.get()).define('C', Items.STICK).unlockedBy("has_infinity_sword", inventoryTrigger(ItemPredicate.Builder.item().of(ModItemsAdditions.INFINITY_SWORD.get()).build())).save(pWriter, "imperial_infinity_sword");
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItemsAdditions.IMPERIAL_INFINITY_BOW.get()).pattern(" AB").pattern("C B").pattern(" AB").define('A', ModItemsAdditions.INFINITY_SINGULARITY.get()).define('B', Items.STICK).define('C', ModItemsAdditions.INFINITY_BOW.get()).unlockedBy("has_infinity_bow", inventoryTrigger(ItemPredicate.Builder.item().of(ModItemsAdditions.INFINITY_BOW.get()).build())).save(pWriter, "imperial_infinity_bow");
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItemsAdditions.IMPERIAL_INFINITY_PICKAXE.get()).pattern("BBB").pattern(" A ").pattern(" C ").define('A', ModItemsAdditions.INFINITY_PICKAXE.get()).define('B', ModItemsAdditions.INFINITY_SINGULARITY.get()).define('C', Items.STICK).unlockedBy("has_infinity_pickaxe", inventoryTrigger(ItemPredicate.Builder.item().of(ModItemsAdditions.INFINITY_PICKAXE.get()).build())).save(pWriter, "imperial_infinity_pickaxe");
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItemsAdditions.IMPERIAL_INFINITY_SHOVEL.get()).pattern("B").pattern("A").pattern("C").define('A', ModItemsAdditions.INFINITY_SHOVEL.get()).define('B', ModItemsAdditions.INFINITY_SINGULARITY.get()).define('C', Items.STICK).unlockedBy("has_infinity_shovel", inventoryTrigger(ItemPredicate.Builder.item().of(ModItemsAdditions.INFINITY_SHOVEL.get()).build())).save(pWriter, "imperial_infinity_shovel");
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItemsAdditions.IMPERIAL_INFINITY_HOE.get()).pattern("B").pattern("A").pattern("C").define('A', ModItemsAdditions.INFINITY_HOE.get()).define('B', ModItemsAdditions.INFINITY_SINGULARITY.get()).define('C', Items.STICK).unlockedBy("has_infinity_hoe", inventoryTrigger(ItemPredicate.Builder.item().of(ModItemsAdditions.INFINITY_HOE.get()).build())).save(pWriter, "imperial_infinity_hoe");
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItemsAdditions.IMPERIAL_INFINITY_AXE.get()).pattern("BB ").pattern("BA ").pattern(" C ").define('A', ModItemsAdditions.INFINITY_AXE.get()).define('B', ModItemsAdditions.INFINITY_SINGULARITY.get()).define('C', Items.STICK).unlockedBy("has_infinity_axe", inventoryTrigger(ItemPredicate.Builder.item().of(ModItemsAdditions.INFINITY_AXE.get()).build())).save(pWriter, "imperial_infinity_axe");
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItemsAdditions.IMPERIAL_INFINITY_PAXEL.get()).pattern("ABC").pattern(" D ").pattern(" E ").define('A', ModItemsAdditions.IMPERIAL_INFINITY_AXE.get()).define('B', ModItemsAdditions.IMPERIAL_INFINITY_SWORD.get()).define('C', ModItemsAdditions.IMPERIAL_INFINITY_PICKAXE.get()).define('D', ModItemsAdditions.IMPERIAL_INFINITY_SHOVEL.get()).define('E', ModItemsAdditions.IMPERIAL_INFINITY_HOE.get()).unlockedBy("has_imperial_infinity_axe", inventoryTrigger(ItemPredicate.Builder.item().of(ModItemsAdditions.IMPERIAL_INFINITY_AXE.get()).build())).save(pWriter, "imperial_infinity_paxel");
        //Star
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, Items.NETHER_STAR).pattern(" A ").pattern("AAA").pattern(" A ").define('A', ModItemsAdditions.STAR_FRAGMENT.get()).unlockedBy("has_star_fragment", inventoryTrigger(ItemPredicate.Builder.item().of(ModItemsAdditions.STAR_FRAGMENT.get()).build())).save(pWriter, "star");
        //Solar
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModBlocksAdditions.SOLAR.get()).pattern("AAA").pattern("ABA").pattern("CCC").define('A', ModItemsProgression.COPPER_WIRE.get()).define('B', Items.CAULDRON).define('C', Items.IRON_INGOT).unlockedBy("has_solar", inventoryTrigger(ItemPredicate.Builder.item().of(ModBlocksAdditions.SOLAR.get()).build())).save(pWriter, "solar");
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItemsAdditions.SOLAR_PANE.get()).pattern("AAA").pattern("BCB").define('A', Items.AMETHYST_SHARD).define('B', ModItemsAdditions.BASIC_CIRCUIT.get()).define('C', ModItemsProgression.COPPER_WIRE.get()).unlockedBy("has_solar_pane", inventoryTrigger(ItemPredicate.Builder.item().of(ModItemsAdditions.SOLAR_PANE.get()).build())).save(pWriter, "solar_pane");
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItemsAdditions.SOLAR_PANE_ADVANCED.get()).pattern("AAA").pattern("ABA").pattern("AAA").define('A', ModItemsAdditions.SOLAR_PANE.get()).define('B', ModItemsAdditions.ADVANCED_CIRCUIT.get()).unlockedBy("has_solar_pane_advanced", inventoryTrigger(ItemPredicate.Builder.item().of(ModItemsAdditions.SOLAR_PANE_ADVANCED.get()).build())).save(pWriter, "solar_pane_advanced");
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItemsAdditions.SOLAR_PANE_ULTIMATE.get()).pattern("AAA").pattern("ABA").pattern("AAA").define('A', ModItemsAdditions.SOLAR_PANE_ADVANCED.get()).define('B', ModItemsProgression.SOLAR_CORE.get()).unlockedBy("has_solar_pane_ultimate", inventoryTrigger(ItemPredicate.Builder.item().of(ModItemsAdditions.SOLAR_PANE_ULTIMATE.get()).build())).save(pWriter, "solar_pane_ultimate");
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItemsAdditions.SOLAR_PANE_QUANTUM.get()).pattern("AAA").pattern("ABA").pattern("AAA").define('A', ModItemsAdditions.SOLAR_PANE_ULTIMATE.get()).define('B', ModItemsProgression.ADVANCED_SOLAR_CORE.get()).unlockedBy("has_solar_pane_quantum", inventoryTrigger(ItemPredicate.Builder.item().of(ModItemsAdditions.SOLAR_PANE_QUANTUM.get()).build())).save(pWriter, "solar_pane_quantum");
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItemsAdditions.SOLAR_PANE_PHOTONIC.get()).pattern("AAA").pattern("ABA").pattern("AAA").define('A', ModItemsAdditions.SOLAR_PANE_QUANTUM.get()).define('B', ModItemsProgression.QUANTUM_SOLAR_CORE.get()).unlockedBy("has_solar_pane_photonic", inventoryTrigger(ItemPredicate.Builder.item().of(ModItemsAdditions.SOLAR_PANE_PHOTONIC.get()).build())).save(pWriter, "solar_pane_photonic");
        //TODO crystals >>
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItemsProgression.SOLAR_CORE.get()).pattern("AAA").pattern("BCB").pattern("EDE").define('A', ModItemsMiner.DEMETRIUM_CRYSTAL.get()).define('B', ModItemsAdditions.ADVANCED_CIRCUIT.get()).define('C', ModItems.LOGIC_COMPONENT.get()).define('D', ModItemsProgression.STABLE_MATTER.get()).define('E', ModItemsAdditions.INFINITY_NUGGET.get()).unlockedBy("has_solar_core", inventoryTrigger(ItemPredicate.Builder.item().of(ModItemsProgression.SOLAR_CORE.get()).build())).save(pWriter, "solar_core");
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItemsProgression.ADVANCED_SOLAR_CORE.get()).pattern("AAA").pattern("BCB").pattern("EDE").define('A', ModItemsMiner.DARIUM_CRYSTAL.get()).define('B', ModItemsProgression.SOLAR_CORE.get()).define('C', ModItems.REFINED_COMPONENT.get()).define('D', ModItemsAdditions.INFINIUM_STELLARUM_INGOT.get()).define('E', ModItemsProgression.UNSTABLE_MATTER.get()).unlockedBy("has_advanced_solar_core", inventoryTrigger(ItemPredicate.Builder.item().of(ModItemsProgression.ADVANCED_SOLAR_CORE.get()).build())).save(pWriter, "advanced_solar_core");
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItemsProgression.QUANTUM_SOLAR_CORE.get()).pattern("AAA").pattern("BCB").pattern("EDE").define('A', ModItemsMiner.TERMURIUM_CRYSTAL.get()).define('B', ModItemsProgression.ADVANCED_SOLAR_CORE.get()).define('C', ModItems.INFINITY_COMPONENT.get()).define('D', ModItemsProgression.IRIDIUM.get()).define('E', ModItemsProgression.STABLE_MATTER.get()).unlockedBy("has_quantum_solar_core", inventoryTrigger(ItemPredicate.Builder.item().of(ModItemsProgression.QUANTUM_SOLAR_CORE.get()).build())).save(pWriter, "quantum_solar_core");

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItemsAdditions.STRAINER.get()).pattern("AAA").pattern("BBB").pattern("AAA").define('A', Items.STICK).define('B', Items.STRING).unlockedBy("has_strainer", inventoryTrigger(ItemPredicate.Builder.item().of(ModItemsAdditions.STRAINER.get()).build())).save(pWriter, "strainer");
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItemsProgression.STABLE_MATTER.get()).pattern("AAA").pattern("A A").pattern("AAA").define('A', ModItemsProgression.UNSTABLE_MATTER.get()).unlockedBy("has_unstable_matter", inventoryTrigger(ItemPredicate.Builder.item().of(ModItemsProgression.STABLE_MATTER.get()).build())).save(pWriter, "stable_matter");
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItemsProgression.IRIDIUM.get()).pattern("AAA").pattern("AAA").pattern("AAA").define('A', ModItemsProgression.STABLE_MATTER.get()).unlockedBy("has_stable_matter", inventoryTrigger(ItemPredicate.Builder.item().of(ModItemsProgression.IRIDIUM.get()).build())).save(pWriter, "iridium");
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModBlocksAdditions.RECYCLER.get()).pattern("AAA").pattern("BCB").pattern("DED").define('A', Blocks.POINTED_DRIPSTONE).define('B', Blocks.GRINDSTONE).define('C', ModBlocksProgression.STEEL_MACHINE_CASING.get()).define('D', Items.IRON_INGOT).define('E', ModItemsAdditions.ADVANCED_CIRCUIT.get()).unlockedBy("has_recycler", inventoryTrigger(ItemPredicate.Builder.item().of(ModBlocksAdditions.RECYCLER.get()).build())).save(pWriter, "recycler");

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModBlocksAdditions.MATTER_CONDENSER.get()).pattern("ABA").pattern("ACA").pattern("DED").define('A', Blocks.SMOOTH_STONE).define('B', Blocks.TINTED_GLASS).define('C', Items.GLASS_BOTTLE).define('D', ModItemsAdditions.STEEL_INGOT.get()).define('E', ModBlocksProgression.STEEL_MACHINE_CASING.get()).unlockedBy("has_matter_condenser", inventoryTrigger(ItemPredicate.Builder.item().of(ModBlocksAdditions.MATTER_CONDENSER.get()).build())).save(pWriter, "matter_condenser");
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModBlocksAdditions.FACTORY.get()).pattern("ABA").pattern("CDC").pattern("EEE").define('A', Blocks.QUARTZ_STAIRS).define('B', Items.GLOW_ITEM_FRAME).define('C', Blocks.CRAFTING_TABLE).define('D', Blocks.RESPAWN_ANCHOR).define('E', Blocks.POLISHED_BASALT).unlockedBy("has_factory", inventoryTrigger(ItemPredicate.Builder.item().of(ModBlocksAdditions.FACTORY.get()).build())).save(pWriter, "factory");
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModBlocksAdditions.DISPLAY.get()).pattern("A").pattern("B").define('A', ModItemsProgression.CARBON_PLATE.get()).define('B', Blocks.POLISHED_ANDESITE_SLAB).unlockedBy("has_display", inventoryTrigger(ItemPredicate.Builder.item().of(ModBlocksAdditions.DISPLAY.get()).build())).save(pWriter, "display");
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModBlocksAdditions.ENTITY_CENTRALIZER.get()).pattern(" A ").pattern("BCD").define('A', Blocks.HOPPER).define('B', Items.IRON_SWORD).define('C', Items.ENDER_PEARL).define('D', Items.IRON_AXE).unlockedBy("has_entity_centralizer", inventoryTrigger(ItemPredicate.Builder.item().of(ModBlocksAdditions.ENTITY_CENTRALIZER.get()).build())).save(pWriter, "entity_centralizer");
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModBlocksAdditions.ENTITY_DISPLAY.get())
                .pattern(" A ")
                .pattern("BCB")
                .define('A', ModItemsProgression.CARBON_PLATE.get())
                .define('B', Items.SPRUCE_LOG)
                .define('C', Items.ENDER_PEARL)
                .unlockedBy("has_entity_centralizer", inventoryTrigger(ItemPredicate.Builder.item().of(ModBlocksAdditions.ENTITY_CENTRALIZER.get()).build()))
                .save(pWriter, "entity_display");


        //infinium stellarum
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ModItemsAdditions.INFINIUM_STELLARUM_INGOT.get(), 9).requires(ModBlocksAdditions.INFINIUM_STELLARUM_BLOCK.get()).unlockedBy("has_infinium_stellarum_ingot_from_block", inventoryTrigger(ItemPredicate.Builder.item().of(ModBlocksAdditions.INFINIUM_STELLARUM_BLOCK.get()).build())).save(pWriter);
        //infinity
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ModItemsAdditions.INFINITY_INGOT.get(), 9).requires(ModBlocksAdditions.INFINITY_BLOCK.get()).unlockedBy("has_infinity_ingot_from_block", inventoryTrigger(ItemPredicate.Builder.item().of(ModBlocksAdditions.INFINITY_BLOCK.get()).build())).save(pWriter);
        //lead
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ModItemsAdditions.LEAD_INGOT.get(), 9).requires(ModBlocksAdditions.LEAD_BLOCK.get()).unlockedBy("has_lead_ingot_from_block", inventoryTrigger(ItemPredicate.Builder.item().of(ModBlocksAdditions.LEAD_BLOCK.get()).build())).save(pWriter);
        //aluminum
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ModItemsAdditions.ALUMINUM_INGOT.get(), 9).requires(ModBlocksAdditions.ALUMINUM_BLOCK.get()).unlockedBy("has_raw_infinity_from_block", inventoryTrigger(ItemPredicate.Builder.item().of(ModBlocksAdditions.ALUMINUM_BLOCK.get()).build())).save(pWriter);
        //tin
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ModItemsAdditions.TIN_INGOT.get(), 9).requires(ModBlocksAdditions.TIN_BLOCK.get()).unlockedBy("has_tin_ingot_from_block", inventoryTrigger(ItemPredicate.Builder.item().of(ModBlocksAdditions.TIN_BLOCK.get()).build())).save(pWriter);
        //nickel
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ModItemsAdditions.NICKEL_INGOT.get(), 9).requires(ModBlocksAdditions.NICKEL_BLOCK.get()).unlockedBy("has_nickel_ingot_from_block", inventoryTrigger(ItemPredicate.Builder.item().of(ModBlocksAdditions.NICKEL_BLOCK.get()).build())).save(pWriter);
        //zinc
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ModItemsAdditions.ZINC_INGOT.get(), 9).requires(ModBlocksAdditions.ZINC_BLOCK.get()).unlockedBy("has_zinc_ingot_from_block", inventoryTrigger(ItemPredicate.Builder.item().of(ModBlocksAdditions.ZINC_BLOCK.get()).build())).save(pWriter);
        //silver
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ModItemsAdditions.SILVER_INGOT.get(), 9).requires(ModBlocksAdditions.SILVER_BLOCK.get()).unlockedBy("has_silver_ingot_from_block", inventoryTrigger(ItemPredicate.Builder.item().of(ModBlocksAdditions.SILVER_BLOCK.get()).build())).save(pWriter);
        //brass
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ModItemsAdditions.BRASS_INGOT.get(), 9).requires(ModBlocksAdditions.BRASS_BLOCK.get()).unlockedBy("has_brass_ingot_from_block", inventoryTrigger(ItemPredicate.Builder.item().of(ModBlocksAdditions.BRASS_BLOCK.get()).build())).save(pWriter);
        //bronze
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ModItemsAdditions.BRONZE_INGOT.get(), 9).requires(ModBlocksAdditions.BRONZE_BLOCK.get()).unlockedBy("has_bronze_ingot_from_block", inventoryTrigger(ItemPredicate.Builder.item().of(ModBlocksAdditions.BRONZE_BLOCK.get()).build())).save(pWriter);
        //steel
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ModItemsAdditions.STEEL_INGOT.get(), 9).requires(ModBlocksAdditions.STEEL_BLOCK.get()).unlockedBy("has_steel_ingot_from_block", inventoryTrigger(ItemPredicate.Builder.item().of(ModBlocksAdditions.STEEL_BLOCK.get()).build())).save(pWriter);
        //uranium
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ModItemsAdditions.URANIUM_INGOT.get(), 9).requires(ModBlocksAdditions.URANIUM_BLOCK.get()).unlockedBy("has_uranium_ingot_from_block", inventoryTrigger(ItemPredicate.Builder.item().of(ModBlocksAdditions.URANIUM_BLOCK.get()).build())).save(pWriter);
        //infinity
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ModItemsAdditions.INFINITY_NUGGET.get(), 9).requires(ModItemsAdditions.INFINITY_INGOT.get()).unlockedBy("has_infinity_ingot", inventoryTrigger(ItemPredicate.Builder.item().of(ModItemsAdditions.INFINITY_INGOT.get()).build())).save(pWriter);
        //lead
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ModItemsAdditions.LEAD_NUGGET.get(), 9).requires(ModItemsAdditions.LEAD_INGOT.get()).unlockedBy("has_lead_ingot", inventoryTrigger(ItemPredicate.Builder.item().of(ModItemsAdditions.LEAD_INGOT.get()).build())).save(pWriter);
        //aluminum
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ModItemsAdditions.ALUMINUM_NUGGET.get(), 9).requires(ModItemsAdditions.ALUMINUM_INGOT.get()).unlockedBy("has_aluminum_ingot", inventoryTrigger(ItemPredicate.Builder.item().of(ModItemsAdditions.ALUMINUM_INGOT.get()).build())).save(pWriter);
        //tin
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ModItemsAdditions.TIN_NUGGET.get(), 9).requires(ModItemsAdditions.TIN_INGOT.get()).unlockedBy("has_tin_ingot", inventoryTrigger(ItemPredicate.Builder.item().of(ModItemsAdditions.TIN_INGOT.get()).build())).save(pWriter);
        //nickel
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ModItemsAdditions.NICKEL_NUGGET.get(), 9).requires(ModItemsAdditions.NICKEL_INGOT.get()).unlockedBy("has_nickel_ingot", inventoryTrigger(ItemPredicate.Builder.item().of(ModItemsAdditions.NICKEL_INGOT.get()).build())).save(pWriter);
        //zinc
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ModItemsAdditions.ZINC_NUGGET.get(), 9).requires(ModItemsAdditions.ZINC_INGOT.get()).unlockedBy("has_zinc_ingot", inventoryTrigger(ItemPredicate.Builder.item().of(ModItemsAdditions.ZINC_INGOT.get()).build())).save(pWriter);
        //bronze
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ModItemsAdditions.BRONZE_NUGGET.get(), 9).requires(ModItemsAdditions.BRONZE_INGOT.get()).unlockedBy("has_bronze_ingot", inventoryTrigger(ItemPredicate.Builder.item().of(ModItemsAdditions.BRONZE_INGOT.get()).build())).save(pWriter);
        //steel
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ModItemsAdditions.STEEL_NUGGET.get(), 9).requires(ModItemsAdditions.STEEL_INGOT.get()).unlockedBy("has_steel_ingot", inventoryTrigger(ItemPredicate.Builder.item().of(ModItemsAdditions.STEEL_INGOT.get()).build())).save(pWriter);
        //uranium
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ModItemsAdditions.URANIUM_NUGGET.get(), 9).requires(ModItemsAdditions.URANIUM_INGOT.get()).unlockedBy("has_uranium_ingot", inventoryTrigger(ItemPredicate.Builder.item().of(ModItemsAdditions.URANIUM_INGOT.get()).build())).save(pWriter);
        //silver
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ModItemsAdditions.SILVER_NUGGET.get(), 9).requires(ModItemsAdditions.SILVER_INGOT.get()).unlockedBy("has_silver_ingot", inventoryTrigger(ItemPredicate.Builder.item().of(ModItemsAdditions.SILVER_INGOT.get()).build())).save(pWriter);
        //brass
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ModItemsAdditions.BRASS_NUGGET.get(), 9).requires(ModItemsAdditions.BRASS_INGOT.get()).unlockedBy("has_brass_ingot", inventoryTrigger(ItemPredicate.Builder.item().of(ModItemsAdditions.BRASS_INGOT.get()).build())).save(pWriter);
        //copper
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ModItemsAdditions.COPPER_NUGGET.get(), 9)
                        .requires(Items.COPPER_INGOT).unlockedBy("has_copper_ingot", inventoryTrigger(ItemPredicate.Builder.item()
                        .of(Items.COPPER_INGOT).build())).save(pWriter);

        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ModItemsAdditions.GLYCERIN.get(), 1)
                        .requires(ModItemsAdditions.ALCOHOL_BOTTLE.get())
                        .requires(Items.SUGAR)
                        .requires(Items.HONEY_BOTTLE)
                        .unlockedBy("has_alcohol", inventoryTrigger(ItemPredicate.Builder.item()
                        .of(ModItemsAdditions.ALCOHOL_BOTTLE.get()).build())).save(pWriter);

        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ModItemsAdditions.PLASTIC_GOO.get(), 8)
                        .requires(ModItemsAdditions.GLYCERIN.get())
                        .requires(ModItemsAdditions.VINEGAR_BOTTLE.get())
                        .requires(ModItemsAdditions.STARCH.get(), 4)
                        .requires(Items.POTION)
                        .unlockedBy("has_glycerin", inventoryTrigger(ItemPredicate.Builder.item()
                        .of(ModItemsAdditions.GLYCERIN.get()).build())).save(pWriter);

        nineBlockStorageRecipes(pWriter, RecipeCategory.MISC, ModItemsAdditions.RAW_INFINITY.get(), RecipeCategory.MISC, ModBlocksAdditions.RAW_INFINITY_BLOCK.get());
        nineBlockStorageRecipes(pWriter, RecipeCategory.MISC, ModItemsAdditions.RAW_LEAD.get(), RecipeCategory.MISC, ModBlocksAdditions.RAW_LEAD_BLOCK.get());
        nineBlockStorageRecipes(pWriter, RecipeCategory.MISC, ModItemsAdditions.RAW_ALUMINUM.get(), RecipeCategory.MISC, ModBlocksAdditions.RAW_ALUMINUM_BLOCK.get());
        nineBlockStorageRecipes(pWriter, RecipeCategory.MISC, ModItemsAdditions.RAW_TIN.get(), RecipeCategory.MISC, ModBlocksAdditions.RAW_TIN_BLOCK.get());
        nineBlockStorageRecipes(pWriter, RecipeCategory.MISC, ModItemsAdditions.RAW_NICKEL.get(), RecipeCategory.MISC, ModBlocksAdditions.RAW_NICKEL_BLOCK.get());
        nineBlockStorageRecipes(pWriter, RecipeCategory.MISC, ModItemsAdditions.RAW_ZINC.get(), RecipeCategory.MISC, ModBlocksAdditions.RAW_ZINC_BLOCK.get());
        nineBlockStorageRecipes(pWriter, RecipeCategory.MISC, ModItemsAdditions.RAW_URANIUM.get(), RecipeCategory.MISC, ModBlocksAdditions.RAW_URANIUM_BLOCK.get());
        nineBlockStorageRecipes(pWriter, RecipeCategory.MISC, ModItemsAdditions.RAW_SILVER.get(), RecipeCategory.MISC, ModBlocksAdditions.RAW_SILVER_BLOCK.get());



        nineBlockStorageRecipes(pWriter, RecipeCategory.MISC, ModItemsAdditions.INFINITY_NUGGET.get(), RecipeCategory.MISC, ModItemsAdditions.INFINITY_INGOT.get());
        nineBlockStorageRecipes(pWriter, RecipeCategory.MISC, ModItemsAdditions.LEAD_NUGGET.get(), RecipeCategory.MISC, ModItemsAdditions.LEAD_INGOT.get());
        nineBlockStorageRecipes(pWriter, RecipeCategory.MISC, ModItemsAdditions.ALUMINUM_NUGGET.get(), RecipeCategory.MISC, ModItemsAdditions.ALUMINUM_INGOT.get());
        nineBlockStorageRecipes(pWriter, RecipeCategory.MISC, ModItemsAdditions.TIN_NUGGET.get(), RecipeCategory.MISC, ModItemsAdditions.TIN_INGOT.get());
        nineBlockStorageRecipes(pWriter, RecipeCategory.MISC, ModItemsAdditions.NICKEL_NUGGET.get(), RecipeCategory.MISC, ModItemsAdditions.NICKEL_INGOT.get());
        nineBlockStorageRecipes(pWriter, RecipeCategory.MISC, ModItemsAdditions.ZINC_NUGGET.get(), RecipeCategory.MISC, ModItemsAdditions.ZINC_INGOT.get());
        nineBlockStorageRecipes(pWriter, RecipeCategory.MISC, ModItemsAdditions.URANIUM_NUGGET.get(), RecipeCategory.MISC, ModItemsAdditions.URANIUM_INGOT.get());
        nineBlockStorageRecipes(pWriter, RecipeCategory.MISC, ModItemsAdditions.SILVER_NUGGET.get(), RecipeCategory.MISC, ModItemsAdditions.SILVER_INGOT.get());
        nineBlockStorageRecipes(pWriter, RecipeCategory.MISC, ModItemsAdditions.BRASS_NUGGET.get(), RecipeCategory.MISC, ModItemsAdditions.BRASS_INGOT.get());
        nineBlockStorageRecipes(pWriter, RecipeCategory.MISC, ModItemsAdditions.STEEL_NUGGET.get(), RecipeCategory.MISC, ModItemsAdditions.STEEL_INGOT.get());
        nineBlockStorageRecipes(pWriter, RecipeCategory.MISC, ModItemsAdditions.BRONZE_NUGGET.get(), RecipeCategory.MISC, ModItemsAdditions.BRONZE_INGOT.get());

        oreSmelting(pWriter, LEAD_SMELTING, RecipeCategory.MISC, ModItemsAdditions.LEAD_INGOT.get(), 0.25f, 200, "lead_ingot");
        oreSmelting(pWriter, ALUMINUM_SMELTING, RecipeCategory.MISC, ModItemsAdditions.ALUMINUM_INGOT.get(), 0.25f, 200, "aluminum_ingot");
        oreSmelting(pWriter, TIN_SMELTING, RecipeCategory.MISC, ModItemsAdditions.TIN_INGOT.get(), 0.25f, 200, "tin_ingot");
        oreSmelting(pWriter, NICKEL_SMELTING, RecipeCategory.MISC, ModItemsAdditions.NICKEL_INGOT.get(), 0.25f, 200, "nickel_ingot");
        oreSmelting(pWriter, ZINC_SMELTING, RecipeCategory.MISC, ModItemsAdditions.ZINC_INGOT.get(), 0.25f, 200, "zinc_ingot");
        oreSmelting(pWriter, URANIUM_SMELTING, RecipeCategory.MISC, ModItemsAdditions.URANIUM_INGOT.get(), 0.25f, 200, "uranium_ingot");
        oreSmelting(pWriter, SILVER_SMELTING, RecipeCategory.MISC, ModItemsAdditions.SILVER_INGOT.get(), 0.25f, 200, "silver_ingot");
        oreSmelting(pWriter, List.of(Items.COAL), RecipeCategory.MISC, ModItemsAdditions.GRAPHITE_INGOT.get(), 0.25f, 200, "graphite_ingot");


        oreBlasting(pWriter, LEAD_SMELTING, RecipeCategory.MISC, ModItemsAdditions.LEAD_INGOT.get(), 0.25f, 100, "lead_ingot");
        oreBlasting(pWriter, ALUMINUM_SMELTING, RecipeCategory.MISC, ModItemsAdditions.ALUMINUM_INGOT.get(), 0.25f, 100, "aluminum_ingot");
        oreBlasting(pWriter, TIN_SMELTING, RecipeCategory.MISC, ModItemsAdditions.TIN_INGOT.get(), 0.25f, 100, "tin_ingot");
        oreBlasting(pWriter, NICKEL_SMELTING, RecipeCategory.MISC, ModItemsAdditions.NICKEL_INGOT.get(), 0.25f, 100, "nickel_ingot");
        oreBlasting(pWriter, ZINC_SMELTING, RecipeCategory.MISC, ModItemsAdditions.ZINC_INGOT.get(), 0.25f, 100, "zinc_ingot");
        oreBlasting(pWriter, URANIUM_SMELTING, RecipeCategory.MISC, ModItemsAdditions.URANIUM_INGOT.get(), 0.25f, 100, "uranium_ingot");
        oreBlasting(pWriter, SILVER_SMELTING, RecipeCategory.MISC, ModItemsAdditions.SILVER_INGOT.get(), 0.25f, 100, "silver_ingot");
        oreBlasting(pWriter, List.of(ModItemsAdditions.BRASS_DUST.get()), RecipeCategory.MISC, ModItemsAdditions.BRASS_INGOT.get(), 0.25f, 100, "brass_ingot");
        oreBlasting(pWriter, List.of(ModItemsAdditions.STEEL_DUST.get()), RecipeCategory.MISC, ModItemsAdditions.STEEL_INGOT.get(), 0.25f, 100, "steel_ingot");

        oreBlasting(pWriter, List.of(ModItemsAdditions.PLASTIC_GOO.get()), RecipeCategory.MISC, ModItemsProgression.PLASTIC.get(), 0.15f, 10, "plastic");

        simpleCookingRecipe(pWriter,"campfire_cooking", RecipeSerializer.SMOKING_RECIPE,5, ModItemsProgression.RAW_ROD_CLAY_MODEL.get(), ModItemsProgression.ROD_CLAY_MODEL.get(), 0.25f);
        simpleCookingRecipe(pWriter,"campfire_cooking",RecipeSerializer.SMOKING_RECIPE,5, ModItemsProgression.RAW_SCREW_CLAY_MODEL.get(), ModItemsProgression.SCREW_CLAY_MODEL.get(), 0.25f);
        simpleCookingRecipe(pWriter,"campfire_cooking",RecipeSerializer.SMOKING_RECIPE,5, ModItemsProgression.RAW_SHEET_CLAY_MODEL.get(), ModItemsProgression.SHEET_CLAY_MODEL.get(), 0.25f);
        simpleCookingRecipe(pWriter,"campfire_cooking",RecipeSerializer.SMOKING_RECIPE,5, ModItemsProgression.RAW_WIRE_CLAY_MODEL.get(), ModItemsProgression.WIRE_CLAY_MODEL.get(), 0.25f);

    }





    protected static void oreSmelting(Consumer<FinishedRecipe> pFinishedRecipeConsumer, List<ItemLike> pIngredients, RecipeCategory pCategory, ItemLike pResult,
                                      float pExperience, int pCookingTIme, String pGroup) {
        oreCooking(pFinishedRecipeConsumer, RecipeSerializer.SMELTING_RECIPE, pIngredients, pCategory, pResult,
                pExperience, pCookingTIme, pGroup, "_from_smelting");
    }

    protected static void oreBlasting(Consumer<FinishedRecipe> pFinishedRecipeConsumer, List<ItemLike> pIngredients, RecipeCategory pCategory, ItemLike pResult,
                                      float pExperience, int pCookingTime, String pGroup) {
        oreCooking(pFinishedRecipeConsumer, RecipeSerializer.BLASTING_RECIPE, pIngredients, pCategory, pResult,
                pExperience, pCookingTime, pGroup, "_from_blasting");
    }

    protected static void oreCooking(Consumer<FinishedRecipe> pFinishedRecipeConsumer, RecipeSerializer<? extends AbstractCookingRecipe> pCookingSerializer,
                                     List<ItemLike> pIngredients, RecipeCategory pCategory, ItemLike pResult, float pExperience, int pCookingTime, String pGroup, String pRecipeName) {
        for(ItemLike itemlike : pIngredients) {
            SimpleCookingRecipeBuilder.generic(Ingredient.of(itemlike), pCategory, pResult, pExperience, pCookingTime,
                            pCookingSerializer).group(pGroup).unlockedBy(getHasName(itemlike), has(itemlike))
                    .save(pFinishedRecipeConsumer, InfinityNexusMod.MOD_ID + ":" + getItemName(pResult) + pRecipeName + "_" + getItemName(itemlike));
        }
    }

}