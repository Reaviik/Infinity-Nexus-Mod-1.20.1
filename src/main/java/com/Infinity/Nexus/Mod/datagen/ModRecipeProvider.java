package com.Infinity.Nexus.Mod.datagen;

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
    private final List<ItemLike> SILVER_SMELTING = List.of(ModItemsAdditions.RAW_SILVER.get(), ModBlocksAdditions.SILVER_ORE.get(), ModBlocksAdditions.DEEPSLATE_SILVER_ORE.get(), ModItemsAdditions.SILVER_DUST.get());
    private final List<ItemLike> URANIUM_SMELTING = List.of(ModItemsAdditions.RAW_URANIUM.get(), ModBlocksAdditions.URANIUM_ORE.get(), ModBlocksAdditions.DEEPSLATE_URANIUM_ORE.get(), ModItemsAdditions.URANIUM_DUST.get());

    public ModRecipeProvider(PackOutput pOutput) {
        super(pOutput);
    }

    @Override
    protected void buildRecipes(Consumer<FinishedRecipe> pWriter) {
        //infinity
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModBlocksAdditions.INFINITY_BLOCK.get()).pattern("###").pattern("###").pattern("###").define('#', ModItemsAdditions.INFINITY_INGOT.get()).unlockedBy("has_infinity_ingot", inventoryTrigger(ItemPredicate.Builder.item().of(ModItemsAdditions.INFINITY_INGOT.get()).build())).save(pWriter, "infinity_block_from_ingot");
        //lead
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModBlocksAdditions.LEAD_BLOCK.get()).pattern("###").pattern("###").pattern("###").define('#', ModItemsAdditions.LEAD_INGOT.get()).unlockedBy("has_lead_ingot", inventoryTrigger(ItemPredicate.Builder.item().of(ModItemsAdditions.LEAD_INGOT.get()).build())).save(pWriter, "lead_block_from_ingot");
        //aluminum
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModBlocksAdditions.ALUMINUM_BLOCK.get()).pattern("###").pattern("###").pattern("###").define('#', ModItemsAdditions.ALUMINUM_INGOT.get()).unlockedBy("has_aluminum_ingot", inventoryTrigger(ItemPredicate.Builder.item().of(ModItemsAdditions.ALUMINUM_INGOT.get()).build())).save(pWriter, "aluminum_block_from_ingot");
        //nickel
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModBlocksAdditions.NICKEL_BLOCK.get()).pattern("###").pattern("###").pattern("###").define('#', ModItemsAdditions.NICKEL_INGOT.get()).unlockedBy("has_nickel_ingot", inventoryTrigger(ItemPredicate.Builder.item().of(ModItemsAdditions.NICKEL_INGOT.get()).build())).save(pWriter, "nickel_block_from_ingot");
        //silver
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModBlocksAdditions.SILVER_BLOCK.get()).pattern("###").pattern("###").pattern("###").define('#', ModItemsAdditions.SILVER_INGOT.get()).unlockedBy("has_silver_ingot", inventoryTrigger(ItemPredicate.Builder.item().of(ModItemsAdditions.SILVER_INGOT.get()).build())).save(pWriter, "silver_block_from_ingot");
        //uranium
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModBlocksAdditions.URANIUM_BLOCK.get()).pattern("###").pattern("###").pattern("###").define('#', ModItemsAdditions.URANIUM_INGOT.get()).unlockedBy("has_uranium_ingot", inventoryTrigger(ItemPredicate.Builder.item().of(ModItemsAdditions.URANIUM_INGOT.get()).build())).save(pWriter, "uranium_block_from_ingot");
        //tin
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModBlocksAdditions.TIN_BLOCK.get()).pattern("###").pattern("###").pattern("###").define('#', ModItemsAdditions.TIN_INGOT.get()).unlockedBy("has_tin_ingot", inventoryTrigger(ItemPredicate.Builder.item().of(ModItemsAdditions.TIN_INGOT.get()).build())).save(pWriter, "tin_block_from_ingot");
        //brass
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModBlocksAdditions.BRASS_BLOCK.get()).pattern("###").pattern("###").pattern("###").define('#', ModItemsAdditions.BRASS_INGOT.get()).unlockedBy("has_brass_ingot", inventoryTrigger(ItemPredicate.Builder.item().of(ModItemsAdditions.BRASS_INGOT.get()).build())).save(pWriter, "brass_block_from_ingot");
        //steel
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModBlocksAdditions.STEEL_BLOCK.get()).pattern("###").pattern("###").pattern("###").define('#', ModItemsAdditions.STEEL_INGOT.get()).unlockedBy("has_steel_ingot", inventoryTrigger(ItemPredicate.Builder.item().of(ModItemsAdditions.STEEL_INGOT.get()).build())).save(pWriter, "steel_block_from_ingot");
        //bronze
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModBlocksAdditions.BRONZE_BLOCK.get()).pattern("###").pattern("###").pattern("###").define('#', ModItemsAdditions.BRONZE_INGOT.get()).unlockedBy("has_bronze_ingot", inventoryTrigger(ItemPredicate.Builder.item().of(ModItemsAdditions.BRONZE_INGOT.get()).build())).save(pWriter, "bronze_block_from_ingot");



        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModBlocksAdditions.EXPLORAR_PORTAL_FRAME.get()).pattern("A#A").pattern("#B#").pattern("A#A").define('#', Items.GOLD_INGOT).define('A', Blocks.WHITE_CONCRETE).define('B', ModItemsAdditions.INFINITY_DUST.get()).unlockedBy("has_infinity_dust", inventoryTrigger(ItemPredicate.Builder.item().of(ModItemsAdditions.INFINITY_DUST.get()).build())).save(pWriter, "explorar_portal_frame");
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModBlocksAdditions.ASPHALT.get()).pattern("A#A").pattern("#B#").pattern("A#A").define('#', Blocks.GRAVEL).define('A', Blocks.SAND).define('B', Items.GRAY_DYE).unlockedBy("has_gray_dye", inventoryTrigger(ItemPredicate.Builder.item().of(Items.GRAY_DYE).build())).save(pWriter, "asphalt");
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItemsAdditions.PORTAL_ACTIVATOR.get()).pattern(" # ").pattern("#A#").pattern(" # ").define('#', ModItemsAdditions.INFINITY_DUST.get()).define('A', Items.ENDER_PEARL).unlockedBy("has_infinity_dust", inventoryTrigger(ItemPredicate.Builder.item().of(ModItemsAdditions.INFINITY_DUST.get()).build())).save(pWriter, "catalyst");
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItemsProgression.RAW_ROD_CLAY_MODEL.get()).pattern("  #").pattern(" A ").pattern("#  ").define('#', Items.CLAY_BALL).define('A', Items.IRON_NUGGET).unlockedBy("has_clay_ball", inventoryTrigger(ItemPredicate.Builder.item().of(Items.CLAY_BALL).build())).save(pWriter, "raw_clay_rod");
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItemsProgression.RAW_SCREW_CLAY_MODEL.get()).pattern("#").pattern("A").pattern(" ").define('#', Items.CLAY_BALL).define('A', Items.IRON_NUGGET).unlockedBy("has_clay_ball", inventoryTrigger(ItemPredicate.Builder.item().of(Items.CLAY_BALL).build())).save(pWriter, "raw_clay_screw");
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItemsProgression.RAW_SHEET_CLAY_MODEL.get()).pattern(" # ").pattern("#A#").pattern(" # ").define('#', Items.CLAY_BALL).define('A', Items.IRON_NUGGET).unlockedBy("has_clay_ball", inventoryTrigger(ItemPredicate.Builder.item().of(Items.CLAY_BALL).build())).save(pWriter, "raw_clay_sheet");
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItemsProgression.RAW_WIRE_CLAY_MODEL.get()).pattern("#").pattern("A").pattern("#").define('#', Items.CLAY_BALL).define('A', Items.IRON_NUGGET).unlockedBy("has_clay_ball", inventoryTrigger(ItemPredicate.Builder.item().of(Items.CLAY_BALL).build())).save(pWriter, "raw_clay_wire");

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModBlocksAdditions.CRUSHER.get())
                .pattern("ABA")
                .pattern("CDC")
                .pattern(" E ")
                .define('A', Items.FLINT)
                .define('B', Blocks.COPPER_BLOCK)
                .define('C', Blocks.COBBLESTONE)
                .define('D', ModBlocksProgression.WOOD_MACHINE_CASING.get())
                .define('E', ModItemsAdditions.BASIC_COMPONENT.get())
                .unlockedBy("has_wood_casings", inventoryTrigger(ItemPredicate.Builder.item().of(ModBlocksProgression.WOOD_MACHINE_CASING.get()).build()))
                .save(pWriter, "crusher");

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModBlocksAdditions.ASSEMBLY.get())
                .pattern("ABA")
                .pattern("CDC")
                .pattern(" E ")
                .define('A', Blocks.CRAFTING_TABLE)
                .define('B', Blocks.COPPER_BLOCK)
                .define('C', Blocks.COBBLESTONE)
                .define('D', ModBlocksProgression.WOOD_MACHINE_CASING.get())
                .define('E', ModItemsAdditions.BASIC_COMPONENT.get())
                .unlockedBy("has_wood_casings", inventoryTrigger(ItemPredicate.Builder.item().of(ModBlocksProgression.WOOD_MACHINE_CASING.get()).build()))
                .save(pWriter, "assembly");

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModBlocksAdditions.PRESS.get())
                .pattern("ABA")
                .pattern("CDC")
                .pattern(" E ")
                .define('A', Blocks.ANVIL)
                .define('B', Blocks.COPPER_BLOCK)
                .define('C', Blocks.COBBLESTONE)
                .define('D', ModBlocksProgression.WOOD_MACHINE_CASING.get())
                .define('E', ModItemsAdditions.BASIC_COMPONENT.get())
                .unlockedBy("has_wood_casings", inventoryTrigger(ItemPredicate.Builder.item().of(ModBlocksProgression.WOOD_MACHINE_CASING.get()).build()))
                .save(pWriter, "press");

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItemsAdditions.REDSTONE_COMPONENT.get())
                .pattern("ABA")
                .pattern("CDC")
                .pattern("ABA")
                .define('A', Items.IRON_INGOT)
                .define('B', Items.COPPER_INGOT)
                .define('C', Items.REDSTONE)
                .define('D', Blocks.HEAVY_WEIGHTED_PRESSURE_PLATE)
                .unlockedBy("has_redstone_component", inventoryTrigger(ItemPredicate.Builder.item().of(Items.REDSTONE).build()))
                .save(pWriter, "redstone_component");

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModBlocksAdditions.FERMENTATION_BARREL.get())
                .pattern("AB ")
                .pattern("CDC")
                .define('A', Blocks.OAK_BUTTON)
                .define('B', Blocks.BARREL)
                .define('C', Items.STICK)
                .define('D', ModBlocksProgression.WOOD_MACHINE_CASING.get())
                .unlockedBy("has_barrel", inventoryTrigger(ItemPredicate.Builder.item().of(Blocks.BARREL).build()))
                .save(pWriter, "fermentation_barrel");


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
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ModItemsAdditions.INFINITY_NUGGET.get(), 9)
                        .requires(ModItemsAdditions.INFINITY_INGOT.get()).unlockedBy("has_infinity_ingot", inventoryTrigger(ItemPredicate.Builder.item()
                        .of(ModItemsAdditions.INFINITY_INGOT.get()).build())).save(pWriter);
        //lead
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ModItemsAdditions.LEAD_NUGGET.get(), 9)
                        .requires(ModItemsAdditions.LEAD_INGOT.get()).unlockedBy("has_lead_ingot", inventoryTrigger(ItemPredicate.Builder.item()
                        .of(ModItemsAdditions.LEAD_INGOT.get()).build())).save(pWriter);
       //aluminum
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ModItemsAdditions.ALUMINUM_NUGGET.get(), 9)
                        .requires(ModItemsAdditions.ALUMINUM_INGOT.get()).unlockedBy("has_aluminum_ingot", inventoryTrigger(ItemPredicate.Builder.item()
                        .of(ModItemsAdditions.ALUMINUM_INGOT.get()).build())).save(pWriter);
        //tin
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ModItemsAdditions.TIN_NUGGET.get(), 9)
                        .requires(ModItemsAdditions.TIN_INGOT.get()).unlockedBy("has_tin_ingot", inventoryTrigger(ItemPredicate.Builder.item()
                        .of(ModItemsAdditions.TIN_INGOT.get()).build())).save(pWriter);
        //nickel
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ModItemsAdditions.NICKEL_NUGGET.get(), 9)
                        .requires(ModItemsAdditions.NICKEL_INGOT.get()).unlockedBy("has_nickel_ingot", inventoryTrigger(ItemPredicate.Builder.item()
                        .of(ModItemsAdditions.NICKEL_INGOT.get()).build())).save(pWriter);
        //bronze
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ModItemsAdditions.BRONZE_NUGGET.get(), 9)
                        .requires(ModItemsAdditions.BRONZE_INGOT.get()).unlockedBy("has_bronze_ingot", inventoryTrigger(ItemPredicate.Builder.item()
                        .of(ModItemsAdditions.BRONZE_INGOT.get()).build())).save(pWriter);
        //steel
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ModItemsAdditions.STEEL_NUGGET.get(), 9)
                        .requires(ModItemsAdditions.STEEL_INGOT.get()).unlockedBy("has_steel_ingot", inventoryTrigger(ItemPredicate.Builder.item()
                        .of(ModItemsAdditions.STEEL_INGOT.get()).build())).save(pWriter);
        //uranium
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ModItemsAdditions.URANIUM_NUGGET.get(), 9)
                        .requires(ModItemsAdditions.URANIUM_INGOT.get()).unlockedBy("has_uranium_ingot", inventoryTrigger(ItemPredicate.Builder.item()
                        .of(ModItemsAdditions.URANIUM_INGOT.get()).build())).save(pWriter);
        //silver
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ModItemsAdditions.SILVER_NUGGET.get(), 9)
                        .requires(ModItemsAdditions.SILVER_INGOT.get()).unlockedBy("has_silver_ingot", inventoryTrigger(ItemPredicate.Builder.item()
                        .of(ModItemsAdditions.SILVER_INGOT.get()).build())).save(pWriter);
        //brass
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ModItemsAdditions.BRASS_NUGGET.get(), 9)
                        .requires(ModItemsAdditions.BRASS_INGOT.get()).unlockedBy("has_brass_ingot", inventoryTrigger(ItemPredicate.Builder.item()
                        .of(ModItemsAdditions.BRASS_INGOT.get()).build())).save(pWriter);

        nineBlockStorageRecipes(pWriter, RecipeCategory.MISC, ModItemsAdditions.RAW_INFINITY.get(), RecipeCategory.MISC, ModBlocksAdditions.RAW_INFINITY_BLOCK.get());
        nineBlockStorageRecipes(pWriter, RecipeCategory.MISC, ModItemsAdditions.RAW_LEAD.get(), RecipeCategory.MISC, ModBlocksAdditions.RAW_LEAD_BLOCK.get());
        nineBlockStorageRecipes(pWriter, RecipeCategory.MISC, ModItemsAdditions.RAW_ALUMINUM.get(), RecipeCategory.MISC, ModBlocksAdditions.RAW_ALUMINUM_BLOCK.get());
        nineBlockStorageRecipes(pWriter, RecipeCategory.MISC, ModItemsAdditions.RAW_TIN.get(), RecipeCategory.MISC, ModBlocksAdditions.RAW_TIN_BLOCK.get());
        nineBlockStorageRecipes(pWriter, RecipeCategory.MISC, ModItemsAdditions.RAW_NICKEL.get(), RecipeCategory.MISC, ModBlocksAdditions.RAW_NICKEL_BLOCK.get());
        nineBlockStorageRecipes(pWriter, RecipeCategory.MISC, ModItemsAdditions.RAW_URANIUM.get(), RecipeCategory.MISC, ModBlocksAdditions.RAW_URANIUM_BLOCK.get());
        nineBlockStorageRecipes(pWriter, RecipeCategory.MISC, ModItemsAdditions.RAW_SILVER.get(), RecipeCategory.MISC, ModBlocksAdditions.RAW_SILVER_BLOCK.get());



        nineBlockStorageRecipes(pWriter, RecipeCategory.MISC, ModItemsAdditions.INFINITY_NUGGET.get(), RecipeCategory.MISC, ModItemsAdditions.INFINITY_INGOT.get());
        nineBlockStorageRecipes(pWriter, RecipeCategory.MISC, ModItemsAdditions.LEAD_NUGGET.get(), RecipeCategory.MISC, ModItemsAdditions.LEAD_INGOT.get());
        nineBlockStorageRecipes(pWriter, RecipeCategory.MISC, ModItemsAdditions.ALUMINUM_NUGGET.get(), RecipeCategory.MISC, ModItemsAdditions.ALUMINUM_INGOT.get());
        nineBlockStorageRecipes(pWriter, RecipeCategory.MISC, ModItemsAdditions.TIN_NUGGET.get(), RecipeCategory.MISC, ModItemsAdditions.TIN_INGOT.get());
        nineBlockStorageRecipes(pWriter, RecipeCategory.MISC, ModItemsAdditions.NICKEL_NUGGET.get(), RecipeCategory.MISC, ModItemsAdditions.NICKEL_INGOT.get());
        nineBlockStorageRecipes(pWriter, RecipeCategory.MISC, ModItemsAdditions.URANIUM_NUGGET.get(), RecipeCategory.MISC, ModItemsAdditions.URANIUM_INGOT.get());
        nineBlockStorageRecipes(pWriter, RecipeCategory.MISC, ModItemsAdditions.SILVER_NUGGET.get(), RecipeCategory.MISC, ModItemsAdditions.SILVER_INGOT.get());
        nineBlockStorageRecipes(pWriter, RecipeCategory.MISC, ModItemsAdditions.BRASS_NUGGET.get(), RecipeCategory.MISC, ModItemsAdditions.BRASS_INGOT.get());
        nineBlockStorageRecipes(pWriter, RecipeCategory.MISC, ModItemsAdditions.STEEL_NUGGET.get(), RecipeCategory.MISC, ModItemsAdditions.STEEL_INGOT.get());
        nineBlockStorageRecipes(pWriter, RecipeCategory.MISC, ModItemsAdditions.BRONZE_INGOT.get(), RecipeCategory.MISC, ModItemsAdditions.BRONZE_NUGGET.get());

        oreSmelting(pWriter, LEAD_SMELTING, RecipeCategory.MISC, ModItemsAdditions.LEAD_INGOT.get(), 0.25f, 200, "lead_ingot");
        oreSmelting(pWriter, ALUMINUM_SMELTING, RecipeCategory.MISC, ModItemsAdditions.ALUMINUM_INGOT.get(), 0.25f, 200, "aluminum_ingot");
        oreSmelting(pWriter, TIN_SMELTING, RecipeCategory.MISC, ModItemsAdditions.TIN_INGOT.get(), 0.25f, 200, "tin_ingot");
        oreSmelting(pWriter, NICKEL_SMELTING, RecipeCategory.MISC, ModItemsAdditions.NICKEL_INGOT.get(), 0.25f, 200, "nickel_ingot");
        oreSmelting(pWriter, URANIUM_SMELTING, RecipeCategory.MISC, ModItemsAdditions.URANIUM_INGOT.get(), 0.25f, 200, "uranium_ingot");
        oreSmelting(pWriter, SILVER_SMELTING, RecipeCategory.MISC, ModItemsAdditions.SILVER_INGOT.get(), 0.25f, 200, "silver_ingot");


        oreBlasting(pWriter, LEAD_SMELTING, RecipeCategory.MISC, ModItemsAdditions.LEAD_INGOT.get(), 0.25f, 100, "lead_ingot");
        oreBlasting(pWriter, ALUMINUM_SMELTING, RecipeCategory.MISC, ModItemsAdditions.ALUMINUM_INGOT.get(), 0.25f, 100, "aluminum_ingot");
        oreBlasting(pWriter, TIN_SMELTING, RecipeCategory.MISC, ModItemsAdditions.TIN_INGOT.get(), 0.25f, 100, "tin_ingot");
        oreBlasting(pWriter, NICKEL_SMELTING, RecipeCategory.MISC, ModItemsAdditions.NICKEL_INGOT.get(), 0.25f, 100, "nickel_ingot");
        oreBlasting(pWriter, URANIUM_SMELTING, RecipeCategory.MISC, ModItemsAdditions.URANIUM_INGOT.get(), 0.25f, 100, "uranium_ingot");
        oreBlasting(pWriter, SILVER_SMELTING, RecipeCategory.MISC, ModItemsAdditions.SILVER_INGOT.get(), 0.25f, 100, "silver_ingot");

        simpleCookingRecipe(pWriter,"smoking", RecipeSerializer.SMOKING_RECIPE,5, ModItemsProgression.RAW_ROD_CLAY_MODEL.get(), ModItemsProgression.ROD_CLAY_MODEL.get(), 0.25f);
        simpleCookingRecipe(pWriter,"smoking",RecipeSerializer.SMOKING_RECIPE,5, ModItemsProgression.RAW_SCREW_CLAY_MODEL.get(), ModItemsProgression.SCREW_CLAY_MODEL.get(), 0.25f);
        simpleCookingRecipe(pWriter,"smoking",RecipeSerializer.SMOKING_RECIPE,5, ModItemsProgression.RAW_SHEET_CLAY_MODEL.get(), ModItemsProgression.SHEET_CLAY_MODEL.get(), 0.25f);
        simpleCookingRecipe(pWriter,"smoking",RecipeSerializer.SMOKING_RECIPE,5, ModItemsProgression.RAW_WIRE_CLAY_MODEL.get(), ModItemsProgression.WIRE_CLAY_MODEL.get(), 0.25f);

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
            SimpleCookingRecipeBuilder.generic(Ingredient.of(itemlike), pCategory, pResult, pExperience, pCookingTime, pCookingSerializer).group(pGroup)
                    .unlockedBy(getHasName(itemlike), has(itemlike))
                    .save(pFinishedRecipeConsumer,  InfinityNexusMod.MOD_ID + ":" +(pResult) + pRecipeName + "_" + getItemName(itemlike));
        }

    }
}