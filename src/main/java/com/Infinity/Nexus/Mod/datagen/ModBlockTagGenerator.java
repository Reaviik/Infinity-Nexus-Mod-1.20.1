package com.Infinity.Nexus.Mod.datagen;

import com.Infinity.Nexus.Mod.InfinityNexusMod;
import com.Infinity.Nexus.Mod.block.ModBlocksAdditions;
import com.Infinity.Nexus.Mod.block.ModBlocksProgression;
import com.Infinity.Nexus.Mod.utils.ModTags;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.tags.BlockTags;
import net.minecraftforge.common.data.BlockTagsProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;

public class ModBlockTagGenerator extends BlockTagsProvider {
    public ModBlockTagGenerator(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, @Nullable ExistingFileHelper existingFileHelper) {
        super(output, lookupProvider, InfinityNexusMod.MOD_ID, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.Provider pProvider) {

        this.tag(BlockTags.MINEABLE_WITH_PICKAXE)
                //Silver
                .add(ModBlocksAdditions.SILVER_ORE.get())
                .add(ModBlocksAdditions.DEEPSLATE_SILVER_ORE.get())
                .add(ModBlocksAdditions.RAW_SILVER_BLOCK.get())
                .add(ModBlocksAdditions.SILVER_BLOCK.get())

                //TIN
                .add(ModBlocksAdditions.TIN_ORE.get())
                .add(ModBlocksAdditions.DEEPSLATE_TIN_ORE.get())
                .add(ModBlocksAdditions.RAW_TIN_BLOCK.get())
                .add(ModBlocksAdditions.TIN_BLOCK.get())

                //Lead
                .add(ModBlocksAdditions.LEAD_ORE.get())
                .add(ModBlocksAdditions.DEEPSLATE_LEAD_ORE.get())
                .add(ModBlocksAdditions.RAW_LEAD_BLOCK.get())
                .add(ModBlocksAdditions.LEAD_BLOCK.get())

                //Nickel
                .add(ModBlocksAdditions.NICKEL_ORE.get())
                .add(ModBlocksAdditions.DEEPSLATE_NICKEL_ORE.get())
                .add(ModBlocksAdditions.RAW_NICKEL_BLOCK.get())
                .add(ModBlocksAdditions.NICKEL_BLOCK.get())

                //Zinc
                .add(ModBlocksAdditions.ZINC_ORE.get())
                .add(ModBlocksAdditions.DEEPSLATE_ZINC_ORE.get())
                .add(ModBlocksAdditions.RAW_ZINC_BLOCK.get())
                .add(ModBlocksAdditions.ZINC_BLOCK.get())

                //Aluminum
                .add(ModBlocksAdditions.ALUMINUM_ORE.get())
                .add(ModBlocksAdditions.DEEPSLATE_ALUMINUM_ORE.get())
                .add(ModBlocksAdditions.RAW_ALUMINUM_BLOCK.get())
                .add(ModBlocksAdditions.ALUMINUM_BLOCK.get())

                //Uranium
                .add(ModBlocksAdditions.URANIUM_ORE.get())
                .add(ModBlocksAdditions.DEEPSLATE_URANIUM_ORE.get())
                .add(ModBlocksAdditions.RAW_URANIUM_BLOCK.get())
                .add(ModBlocksAdditions.URANIUM_BLOCK.get())

                //Infinity
                .add(ModBlocksAdditions.INFINITY_ORE.get())
                .add(ModBlocksAdditions.DEEPSLATE_INFINITY_ORE.get())
                .add(ModBlocksAdditions.RAW_INFINITY_BLOCK.get())
                .add(ModBlocksAdditions.INFINITY_BLOCK.get())
                .add(ModBlocksAdditions.INFINIUM_STELLARUM_BLOCK.get())

                .add(ModBlocksAdditions.ASPHALT.get())
                .add(ModBlocksAdditions.EXPLORAR_PORTAL_FRAME.get())

                .add(ModBlocksAdditions.MOB_CRUSHER.get())
                .add(ModBlocksAdditions.CRUSHER.get())
                .add(ModBlocksAdditions.PRESS.get())
                .add(ModBlocksAdditions.ASSEMBLY.get())
                .add(ModBlocksAdditions.SQUEEZER.get())
                .add(ModBlocksAdditions.SMELTERY.get())
                .add(ModBlocksAdditions.GENERATOR.get())
                .add(ModBlocksAdditions.MATTER_CONDENSER.get())
                .add(ModBlocksAdditions.RECYCLER.get())
                .add(ModBlocksAdditions.DISPLAY.get())
                .add(ModBlocksAdditions.SOLAR.get())
                .add(ModBlocksAdditions.FERMENTATION_BARREL.get())

                .add(ModBlocksProgression.SILVER_MACHINE_CASING.get())
                .add(ModBlocksProgression.TIN_MACHINE_CASING.get())
                .add(ModBlocksProgression.LEAD_MACHINE_CASING.get())
                .add(ModBlocksProgression.NICKEL_MACHINE_CASING.get())
                .add(ModBlocksProgression.BRASS_MACHINE_CASING.get())
                .add(ModBlocksProgression.BRONZE_MACHINE_CASING.get())
                .add(ModBlocksProgression.ALUMINUM_MACHINE_CASING.get())
                .add(ModBlocksProgression.PLASTIC_MACHINE_CASING.get())
                .add(ModBlocksProgression.GLASS_MACHINE_CASING.get())
                .add(ModBlocksProgression.STEEL_MACHINE_CASING.get())
                .add(ModBlocksProgression.INDUSTRIAL_MACHINE_CASING.get())
                .add(ModBlocksProgression.INFINITY_MACHINE_CASING.get());


        this.tag(BlockTags.NEEDS_STONE_TOOL)

                .add(ModBlocksAdditions.ASPHALT.get())
                .add(ModBlocksAdditions.EXPLORAR_PORTAL_FRAME.get())

                .add(ModBlocksAdditions.MOB_CRUSHER.get())
                .add(ModBlocksAdditions.CRUSHER.get())
                .add(ModBlocksAdditions.PRESS.get())
                .add(ModBlocksAdditions.ASSEMBLY.get())
                .add(ModBlocksAdditions.SQUEEZER.get())
                .add(ModBlocksAdditions.SMELTERY.get())
                .add(ModBlocksAdditions.GENERATOR.get())
                .add(ModBlocksAdditions.FERMENTATION_BARREL.get())

                .add(ModBlocksProgression.SILVER_MACHINE_CASING.get())
                .add(ModBlocksProgression.TIN_MACHINE_CASING.get())
                .add(ModBlocksProgression.LEAD_MACHINE_CASING.get())
                .add(ModBlocksProgression.NICKEL_MACHINE_CASING.get())
                .add(ModBlocksProgression.BRASS_MACHINE_CASING.get())
                .add(ModBlocksProgression.BRONZE_MACHINE_CASING.get())
                .add(ModBlocksProgression.ALUMINUM_MACHINE_CASING.get())
                .add(ModBlocksProgression.PLASTIC_MACHINE_CASING.get())
                .add(ModBlocksProgression.GLASS_MACHINE_CASING.get())
                .add(ModBlocksProgression.STEEL_MACHINE_CASING.get())
                .add(ModBlocksProgression.INDUSTRIAL_MACHINE_CASING.get())
                .add(ModBlocksProgression.INFINITY_MACHINE_CASING.get());

        this.tag(BlockTags.NEEDS_IRON_TOOL)

                //Silver
                .add(ModBlocksAdditions.SILVER_ORE.get())
                .add(ModBlocksAdditions.DEEPSLATE_SILVER_ORE.get())
                .add(ModBlocksAdditions.RAW_SILVER_BLOCK.get())
                .add(ModBlocksAdditions.SILVER_BLOCK.get())

                //TIN
                .add(ModBlocksAdditions.TIN_ORE.get())
                .add(ModBlocksAdditions.DEEPSLATE_TIN_ORE.get())
                .add(ModBlocksAdditions.RAW_TIN_BLOCK.get())
                .add(ModBlocksAdditions.TIN_BLOCK.get())

                //Lead
                .add(ModBlocksAdditions.LEAD_ORE.get())
                .add(ModBlocksAdditions.DEEPSLATE_LEAD_ORE.get())
                .add(ModBlocksAdditions.RAW_LEAD_BLOCK.get())
                .add(ModBlocksAdditions.LEAD_BLOCK.get())

                //Nickel
                .add(ModBlocksAdditions.NICKEL_ORE.get())
                .add(ModBlocksAdditions.DEEPSLATE_NICKEL_ORE.get())
                .add(ModBlocksAdditions.RAW_NICKEL_BLOCK.get())
                .add(ModBlocksAdditions.NICKEL_BLOCK.get())

                //Zinc
                .add(ModBlocksAdditions.ZINC_ORE.get())
                .add(ModBlocksAdditions.DEEPSLATE_ZINC_ORE.get())
                .add(ModBlocksAdditions.RAW_ZINC_BLOCK.get())
                .add(ModBlocksAdditions.ZINC_BLOCK.get())

                //Aluminum
                .add(ModBlocksAdditions.ALUMINUM_ORE.get())
                .add(ModBlocksAdditions.DEEPSLATE_ALUMINUM_ORE.get())
                .add(ModBlocksAdditions.RAW_ALUMINUM_BLOCK.get())
                .add(ModBlocksAdditions.ALUMINUM_BLOCK.get())

                //Uranium
                .add(ModBlocksAdditions.URANIUM_ORE.get())
                .add(ModBlocksAdditions.DEEPSLATE_URANIUM_ORE.get())
                .add(ModBlocksAdditions.RAW_URANIUM_BLOCK.get())
                .add(ModBlocksAdditions.URANIUM_BLOCK.get())

                //Infinity
                .add(ModBlocksAdditions.INFINITY_ORE.get())
                .add(ModBlocksAdditions.DEEPSLATE_INFINITY_ORE.get())
                .add(ModBlocksAdditions.RAW_INFINITY_BLOCK.get())
                .add(ModBlocksAdditions.INFINITY_BLOCK.get())
                .add(ModBlocksAdditions.INFINIUM_STELLARUM_BLOCK.get());


        this.tag(BlockTags.NEEDS_DIAMOND_TOOL)

                //Infinity
                .add(ModBlocksAdditions.INFINITY_ORE.get())
                .add(ModBlocksAdditions.DEEPSLATE_INFINITY_ORE.get())
                .add(ModBlocksAdditions.RAW_INFINITY_BLOCK.get())
                .add(ModBlocksAdditions.INFINITY_BLOCK.get())
                .add(ModBlocksAdditions.INFINIUM_STELLARUM_BLOCK.get());

        this.tag(ModTags.Blocks.FORGE_ORES_IN_GROUND_STONE)
                .add(ModBlocksAdditions.SILVER_ORE.get())
                .add(ModBlocksAdditions.TIN_ORE.get())
                .add(ModBlocksAdditions.LEAD_ORE.get())
                .add(ModBlocksAdditions.NICKEL_ORE.get())
                .add(ModBlocksAdditions.ZINC_ORE.get())
                .add(ModBlocksAdditions.ALUMINUM_ORE.get())
                .add(ModBlocksAdditions.URANIUM_ORE.get())
                .add(ModBlocksAdditions.INFINITY_ORE.get());

        this.tag(ModTags.Blocks.FORGE_ORES_IN_GROUND_DEEPSLATE)
                .add(ModBlocksAdditions.DEEPSLATE_SILVER_ORE.get())
                .add(ModBlocksAdditions.DEEPSLATE_TIN_ORE.get())
                .add(ModBlocksAdditions.DEEPSLATE_LEAD_ORE.get())
                .add(ModBlocksAdditions.DEEPSLATE_NICKEL_ORE.get())
                .add(ModBlocksAdditions.DEEPSLATE_ZINC_ORE.get())
                .add(ModBlocksAdditions.DEEPSLATE_ALUMINUM_ORE.get())
                .add(ModBlocksAdditions.DEEPSLATE_URANIUM_ORE.get())
                .add(ModBlocksAdditions.DEEPSLATE_INFINITY_ORE.get());

        this.tag(ModTags.Blocks.FORGE_ORES)
                .add(ModBlocksAdditions.SILVER_ORE.get())
                .add(ModBlocksAdditions.TIN_ORE.get())
                .add(ModBlocksAdditions.LEAD_ORE.get())
                .add(ModBlocksAdditions.NICKEL_ORE.get())
                .add(ModBlocksAdditions.ZINC_ORE.get())
                .add(ModBlocksAdditions.ALUMINUM_ORE.get())
                .add(ModBlocksAdditions.URANIUM_ORE.get())
                .add(ModBlocksAdditions.INFINITY_ORE.get())
                .add(ModBlocksAdditions.DEEPSLATE_SILVER_ORE.get())
                .add(ModBlocksAdditions.DEEPSLATE_TIN_ORE.get())
                .add(ModBlocksAdditions.DEEPSLATE_LEAD_ORE.get())
                .add(ModBlocksAdditions.DEEPSLATE_NICKEL_ORE.get())
                .add(ModBlocksAdditions.DEEPSLATE_ZINC_ORE.get())
                .add(ModBlocksAdditions.DEEPSLATE_ALUMINUM_ORE.get())
                .add(ModBlocksAdditions.DEEPSLATE_URANIUM_ORE.get())
                .add(ModBlocksAdditions.DEEPSLATE_INFINITY_ORE.get());

        this.tag(ModTags.Blocks.FORGE_ORES_SINGULAR)
                .add(ModBlocksAdditions.SILVER_ORE.get())
                .add(ModBlocksAdditions.TIN_ORE.get())
                .add(ModBlocksAdditions.LEAD_ORE.get())
                .add(ModBlocksAdditions.NICKEL_ORE.get())
                .add(ModBlocksAdditions.ZINC_ORE.get())
                .add(ModBlocksAdditions.ALUMINUM_ORE.get())
                .add(ModBlocksAdditions.URANIUM_ORE.get())
                .add(ModBlocksAdditions.INFINITY_ORE.get())
                .add(ModBlocksAdditions.DEEPSLATE_SILVER_ORE.get())
                .add(ModBlocksAdditions.DEEPSLATE_TIN_ORE.get())
                .add(ModBlocksAdditions.DEEPSLATE_LEAD_ORE.get())
                .add(ModBlocksAdditions.DEEPSLATE_NICKEL_ORE.get())
                .add(ModBlocksAdditions.DEEPSLATE_ZINC_ORE.get())
                .add(ModBlocksAdditions.DEEPSLATE_ALUMINUM_ORE.get())
                .add(ModBlocksAdditions.DEEPSLATE_URANIUM_ORE.get())
                .add(ModBlocksAdditions.DEEPSLATE_INFINITY_ORE.get());

        this.tag(ModTags.Blocks.FORGE_STORAGE_BLOCKS)
                .add(ModBlocksAdditions.RAW_SILVER_BLOCK.get())
                .add(ModBlocksAdditions.RAW_TIN_BLOCK.get())
                .add(ModBlocksAdditions.RAW_LEAD_BLOCK.get())
                .add(ModBlocksAdditions.RAW_NICKEL_BLOCK.get())
                .add(ModBlocksAdditions.RAW_ZINC_BLOCK.get())
                .add(ModBlocksAdditions.RAW_ALUMINUM_BLOCK.get())
                .add(ModBlocksAdditions.RAW_URANIUM_BLOCK.get())
                .add(ModBlocksAdditions.RAW_INFINITY_BLOCK.get())

                .add(ModBlocksAdditions.SILVER_BLOCK.get())
                .add(ModBlocksAdditions.TIN_BLOCK.get())
                .add(ModBlocksAdditions.LEAD_BLOCK.get())
                .add(ModBlocksAdditions.NICKEL_BLOCK.get())
                .add(ModBlocksAdditions.ZINC_BLOCK.get())
                .add(ModBlocksAdditions.ALUMINUM_BLOCK.get())
                .add(ModBlocksAdditions.URANIUM_BLOCK.get())
                .add(ModBlocksAdditions.INFINITY_BLOCK.get())
                .add(ModBlocksAdditions.INFINIUM_STELLARUM_BLOCK.get())
                .add(ModBlocksAdditions.BRASS_BLOCK.get())
                .add(ModBlocksAdditions.BRONZE_BLOCK.get())
                .add(ModBlocksAdditions.STEEL_BLOCK.get());


    }

    @Override
    public String getName() {
        return "Block Tags";
    }
}
