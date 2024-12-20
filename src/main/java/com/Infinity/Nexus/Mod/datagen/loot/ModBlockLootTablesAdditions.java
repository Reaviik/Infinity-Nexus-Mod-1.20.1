package com.Infinity.Nexus.Mod.datagen.loot;

import com.Infinity.Nexus.Mod.block.ModBlocksAdditions;
import com.Infinity.Nexus.Mod.block.ModBlocksProgression;
import com.Infinity.Nexus.Mod.item.ModItemsAdditions;
import net.minecraft.data.loot.BlockLootSubProvider;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.registries.RegistryObject;

import java.util.Set;
import java.util.stream.Stream;

public class ModBlockLootTablesAdditions extends BlockLootSubProvider {


    public ModBlockLootTablesAdditions(){
        super(Set.of(), FeatureFlags.REGISTRY.allFlags());
    }

    @Override
    protected void generate() {
        this.dropSelf(ModBlocksAdditions.INFINIUM_STELLARUM_BLOCK.get());
        this.dropSelf(ModBlocksAdditions.INFINITY_BLOCK.get());
        this.dropSelf(ModBlocksAdditions.RAW_INFINITY_BLOCK.get());
        this.dropSelf(ModBlocksAdditions.INFINITY_ORE.get());
        this.dropSelf(ModBlocksAdditions.DEEPSLATE_INFINITY_ORE.get());

        this.dropSelf(ModBlocksAdditions.LEAD_BLOCK.get());
        this.dropSelf(ModBlocksAdditions.RAW_LEAD_BLOCK.get());
        this.dropSelf(ModBlocksAdditions.LEAD_ORE.get());
        this.dropSelf(ModBlocksAdditions.DEEPSLATE_LEAD_ORE.get());

        this.dropSelf(ModBlocksAdditions.ALUMINUM_BLOCK.get());
        this.dropSelf(ModBlocksAdditions.RAW_ALUMINUM_BLOCK.get());
        this.dropSelf(ModBlocksAdditions.ALUMINUM_ORE.get());
        this.dropSelf(ModBlocksAdditions.DEEPSLATE_ALUMINUM_ORE.get());

        this.dropSelf(ModBlocksAdditions.TIN_BLOCK.get());
        this.dropSelf(ModBlocksAdditions.RAW_TIN_BLOCK.get());
        this.dropSelf(ModBlocksAdditions.TIN_ORE.get());
        this.dropSelf(ModBlocksAdditions.DEEPSLATE_TIN_ORE.get());

        this.dropSelf(ModBlocksAdditions.NICKEL_BLOCK.get());
        this.dropSelf(ModBlocksAdditions.RAW_NICKEL_BLOCK.get());
        this.dropSelf(ModBlocksAdditions.NICKEL_ORE.get());
        this.dropSelf(ModBlocksAdditions.DEEPSLATE_NICKEL_ORE.get());

        this.dropSelf(ModBlocksAdditions.ZINC_BLOCK.get());
        this.dropSelf(ModBlocksAdditions.RAW_ZINC_BLOCK.get());
        this.dropSelf(ModBlocksAdditions.ZINC_ORE.get());
        this.dropSelf(ModBlocksAdditions.DEEPSLATE_ZINC_ORE.get());

        this.dropSelf(ModBlocksAdditions.SILVER_BLOCK.get());
        this.dropSelf(ModBlocksAdditions.RAW_SILVER_BLOCK.get());
        this.dropSelf(ModBlocksAdditions.SILVER_ORE.get());
        this.dropSelf(ModBlocksAdditions.DEEPSLATE_SILVER_ORE.get());

        this.dropSelf(ModBlocksAdditions.URANIUM_BLOCK.get());
        this.dropSelf(ModBlocksAdditions.RAW_URANIUM_BLOCK.get());
        this.dropSelf(ModBlocksAdditions.URANIUM_ORE.get());
        this.dropSelf(ModBlocksAdditions.DEEPSLATE_ALUMINUM_ORE.get());

        this.dropSelf(ModBlocksAdditions.BRASS_BLOCK.get());
        this.dropSelf(ModBlocksAdditions.BRONZE_BLOCK.get());
        this.dropSelf(ModBlocksAdditions.STEEL_BLOCK.get());

        this.dropSelf(ModBlocksAdditions.EXPLORAR_PORTAL_FRAME.get());
        this.dropSelf(ModBlocksAdditions.EXPLORAR_PORTAL.get());
        this.dropSelf(ModBlocksAdditions.VOXEL_BLOCK.get());
        this.dropSelf(ModBlocksAdditions.ASPHALT.get());

        this.dropSelf(ModBlocksAdditions.MOB_CRUSHER.get());
        this.dropSelf(ModBlocksAdditions.PRESS.get());
        this.dropSelf(ModBlocksAdditions.CRUSHER.get());
        this.dropSelf(ModBlocksAdditions.ASSEMBLY.get());
        this.dropSelf(ModBlocksAdditions.FACTORY.get());
        this.dropSelf(ModBlocksAdditions.INFUSER.get());
        this.dropSelf(ModBlocksAdditions.SQUEEZER.get());
        this.dropSelf(ModBlocksAdditions.SMELTERY.get());
        this.dropSelf(ModBlocksAdditions.GENERATOR.get());
        this.dropSelf(ModBlocksAdditions.FERMENTATION_BARREL.get());
        this.dropSelf(ModBlocksAdditions.RECYCLER.get());
        this.dropSelf(ModBlocksAdditions.MATTER_CONDENSER.get());
        this.dropSelf(ModBlocksAdditions.PLACER.get());
        this.dropSelf(ModBlocksAdditions.SOLAR.get());
        this.dropSelf(ModBlocksAdditions.DISPLAY.get());
        this.dropSelf(ModBlocksAdditions.DEPOT.get());
        this.dropSelf(ModBlocksAdditions.DEPOT_STONE.get());

        this.dropSelf(ModBlocksAdditions.TECH_PEDESTAL.get());
        this.dropSelf(ModBlocksAdditions.EXPLORATION_PEDESTAL.get());
        this.dropSelf(ModBlocksAdditions.RESOURCE_PEDESTAL.get());
        this.dropSelf(ModBlocksAdditions.MAGIC_PEDESTAL.get());
        this.dropSelf(ModBlocksAdditions.CREATIVITY_PEDESTAL.get());
        this.dropSelf(ModBlocksAdditions.DECOR_PEDESTAL.get());

        this.dropSelf(ModBlocksAdditions.CATWALK.get());
        this.dropSelf(ModBlocksAdditions.CATWALK_2.get());
        this.dropSelf(ModBlocksAdditions.CATWALK_3.get());
        this.dropSelf(ModBlocksAdditions.CATWALK_4.get());
        this.dropSelf(ModBlocksAdditions.CATWALK_5.get());
        this.dropSelf(ModBlocksAdditions.CATWALK_6.get());
        this.dropSelf(ModBlocksAdditions.CATWALK_7.get());
        this.dropSelf(ModBlocksAdditions.CATWALK_8.get());
        this.dropSelf(ModBlocksAdditions.CATWALK_9.get());
        this.dropSelf(ModBlocksAdditions.CATWALK_10.get());
        this.dropSelf(ModBlocksAdditions.CATWALK_11.get());
        this.dropSelf(ModBlocksAdditions.CATWALK_12.get());
        this.dropSelf(ModBlocksAdditions.CATWALK_13.get());
        this.dropSelf(ModBlocksAdditions.CATWALK_14.get());
        this.dropSelf(ModBlocksAdditions.CATWALK_15.get());
        this.dropSelf(ModBlocksAdditions.CATWALK_16.get());
        this.dropSelf(ModBlocksAdditions.CATWALK_17.get());
        this.dropSelf(ModBlocksAdditions.CATWALK_18.get());

        this.dropSelf(ModBlocksProgression.SILVER_MACHINE_CASING.get());
        this.dropSelf(ModBlocksProgression.TIN_MACHINE_CASING.get());
        this.dropSelf(ModBlocksProgression.NICKEL_MACHINE_CASING.get());
        this.dropSelf(ModBlocksProgression.BRONZE_MACHINE_CASING.get());
        this.dropSelf(ModBlocksProgression.BRASS_MACHINE_CASING.get());
        this.dropSelf(ModBlocksProgression.LEAD_MACHINE_CASING.get());
        this.dropSelf(ModBlocksProgression.ALUMINUM_MACHINE_CASING.get());
        this.dropSelf(ModBlocksProgression.PLASTIC_MACHINE_CASING.get());
        this.dropSelf(ModBlocksProgression.GLASS_MACHINE_CASING.get());
        this.dropSelf(ModBlocksProgression.STEEL_MACHINE_CASING.get());
        this.dropSelf(ModBlocksProgression.INDUSTRIAL_MACHINE_CASING.get());
        this.dropSelf(ModBlocksProgression.INFINITY_MACHINE_CASING.get());


        this.add(ModBlocksAdditions.INFINITY_ORE.get(), block -> createOreDrop( ModBlocksAdditions.INFINITY_ORE.get(), ModItemsAdditions.RAW_INFINITY.get()));
        this.add(ModBlocksAdditions.DEEPSLATE_INFINITY_ORE.get(), block -> createOreDrop( ModBlocksAdditions.DEEPSLATE_INFINITY_ORE.get(), ModItemsAdditions.RAW_INFINITY.get()));

        this.add(ModBlocksAdditions.LEAD_ORE.get(), block -> createOreDrop( ModBlocksAdditions.LEAD_ORE.get(), ModItemsAdditions.RAW_LEAD.get()));
        this.add(ModBlocksAdditions.DEEPSLATE_LEAD_ORE.get(), block -> createOreDrop( ModBlocksAdditions.DEEPSLATE_LEAD_ORE.get(), ModItemsAdditions.RAW_LEAD.get()));

        this.add(ModBlocksAdditions.ALUMINUM_ORE.get(), block -> createOreDrop( ModBlocksAdditions.ALUMINUM_ORE.get(), ModItemsAdditions.RAW_ALUMINUM.get()));
        this.add(ModBlocksAdditions.DEEPSLATE_ALUMINUM_ORE.get(), block -> createOreDrop( ModBlocksAdditions.DEEPSLATE_ALUMINUM_ORE.get(), ModItemsAdditions.RAW_ALUMINUM.get()));

        this.add(ModBlocksAdditions.TIN_ORE.get(), block -> createOreDrop( ModBlocksAdditions.TIN_ORE.get(), ModItemsAdditions.RAW_TIN.get()));
        this.add(ModBlocksAdditions.DEEPSLATE_TIN_ORE.get(), block -> createOreDrop( ModBlocksAdditions.DEEPSLATE_TIN_ORE.get(), ModItemsAdditions.RAW_TIN.get()));

        this.add(ModBlocksAdditions.ZINC_ORE.get(), block -> createOreDrop( ModBlocksAdditions.ZINC_ORE.get(), ModItemsAdditions.RAW_ZINC.get()));
        this.add(ModBlocksAdditions.DEEPSLATE_ZINC_ORE.get(), block -> createOreDrop( ModBlocksAdditions.DEEPSLATE_ZINC_ORE.get(), ModItemsAdditions.RAW_ZINC.get()));

        this.add(ModBlocksAdditions.NICKEL_ORE.get(), block -> createOreDrop( ModBlocksAdditions.NICKEL_ORE.get(), ModItemsAdditions.RAW_NICKEL.get()));
        this.add(ModBlocksAdditions.DEEPSLATE_NICKEL_ORE.get(), block -> createOreDrop( ModBlocksAdditions.DEEPSLATE_NICKEL_ORE.get(), ModItemsAdditions.RAW_NICKEL.get()));

        this.add(ModBlocksAdditions.SILVER_ORE.get(), block -> createOreDrop( ModBlocksAdditions.SILVER_ORE.get(), ModItemsAdditions.RAW_SILVER.get()));
        this.add(ModBlocksAdditions.DEEPSLATE_SILVER_ORE.get(), block -> createOreDrop( ModBlocksAdditions.DEEPSLATE_SILVER_ORE.get(), ModItemsAdditions.RAW_SILVER.get()));

        this.add(ModBlocksAdditions.URANIUM_ORE.get(), block -> createOreDrop( ModBlocksAdditions.URANIUM_ORE.get(), ModItemsAdditions.RAW_URANIUM.get()));
        this.add(ModBlocksAdditions.DEEPSLATE_URANIUM_ORE.get(), block -> createOreDrop( ModBlocksAdditions.DEEPSLATE_URANIUM_ORE.get(), ModItemsAdditions.RAW_URANIUM.get()));

        this.dropSelf(ModBlocksAdditions.ENTITY_CENTRALIZER.get());
        this.dropSelf(ModBlocksAdditions.ENTITY_DISPLAY.get());


    }

    @Override
    protected Iterable<Block> getKnownBlocks() {
        Stream<Block> additions = ModBlocksAdditions.BLOCKS.getEntries().stream().map(RegistryObject::get);
        Stream<Block> progression = ModBlocksProgression.BLOCKS.getEntries().stream().map(RegistryObject::get);


        return Stream.concat(additions, progression)::iterator;
    }
}
