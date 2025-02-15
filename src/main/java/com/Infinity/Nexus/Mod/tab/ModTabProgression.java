package com.Infinity.Nexus.Mod.tab;

import com.Infinity.Nexus.Mod.InfinityNexusMod;
import com.Infinity.Nexus.Mod.block.ModBlocksAdditions;
import com.Infinity.Nexus.Mod.block.ModBlocksProgression;
import com.Infinity.Nexus.Mod.item.ModItemsProgression;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

public class ModTabProgression {
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS =
            DeferredRegister.create(Registries.CREATIVE_MODE_TAB, InfinityNexusMod.MOD_ID);
    public static final RegistryObject<CreativeModeTab> INFINITY_TAB_ADDITIONS = CREATIVE_MODE_TABS.register("infinity_nexus_mod_progression",
                                                            //Tab Icon
            () -> CreativeModeTab.builder().icon(() -> new ItemStack(ModBlocksProgression.INFINITY_MACHINE_CASING.get()))
                    .title(Component.translatable("itemGroup.infinity_nexus_mod_addition"))
                    .displayItems((pParameters, pOutput) -> {
                        //-------------------------//-------------------------//

                        pOutput.accept(new ItemStack(ModBlocksProgression.SILVER_MACHINE_CASING.get()));
                        pOutput.accept(new ItemStack(ModBlocksProgression.TIN_MACHINE_CASING.get()));
                        pOutput.accept(new ItemStack(ModBlocksProgression.LEAD_MACHINE_CASING.get()));
                        pOutput.accept(new ItemStack(ModBlocksProgression.NICKEL_MACHINE_CASING.get()));
                        pOutput.accept(new ItemStack(ModBlocksProgression.BRASS_MACHINE_CASING.get()));
                        pOutput.accept(new ItemStack(ModBlocksProgression.BRONZE_MACHINE_CASING.get()));
                        pOutput.accept(new ItemStack(ModBlocksProgression.ALUMINUM_MACHINE_CASING.get()));
                        pOutput.accept(new ItemStack(ModBlocksProgression.PLASTIC_MACHINE_CASING.get()));
                        pOutput.accept(new ItemStack(ModBlocksProgression.GLASS_MACHINE_CASING.get()));
                        pOutput.accept(new ItemStack(ModBlocksProgression.STEEL_MACHINE_CASING.get()));
                        pOutput.accept(new ItemStack(ModBlocksProgression.INDUSTRIAL_MACHINE_CASING.get()));
                        pOutput.accept(new ItemStack(ModBlocksProgression.INFINITY_MACHINE_CASING.get()));

                        pOutput.accept(new ItemStack(ModItemsProgression.GOLD_WIRE_CAST.get()));
                        pOutput.accept(new ItemStack(ModItemsProgression.GOLD_SCREW_CAST.get()));
                        pOutput.accept(new ItemStack(ModItemsProgression.GOLD_SHEET_CAST.get()));
                        pOutput.accept(new ItemStack(ModItemsProgression.GOLD_ROD_CAST.get()));
                        pOutput.accept(new ItemStack(ModItemsProgression.GOLD_INGOT_CAST.get()));
                        pOutput.accept(new ItemStack(ModItemsProgression.INFINITY_MESH_CAST.get()));

                        pOutput.accept(new ItemStack(ModItemsProgression.RAW_WIRE_CLAY_MODEL.get()));
                        pOutput.accept(new ItemStack(ModItemsProgression.RAW_SCREW_CLAY_MODEL.get()));
                        pOutput.accept(new ItemStack(ModItemsProgression.RAW_SHEET_CLAY_MODEL.get()));
                        pOutput.accept(new ItemStack(ModItemsProgression.RAW_ROD_CLAY_MODEL.get()));
                        pOutput.accept(new ItemStack(ModItemsProgression.WIRE_CLAY_MODEL.get()));
                        pOutput.accept(new ItemStack(ModItemsProgression.SCREW_CLAY_MODEL.get()));
                        pOutput.accept(new ItemStack(ModItemsProgression.SHEET_CLAY_MODEL.get()));
                        pOutput.accept(new ItemStack(ModItemsProgression.ROD_CLAY_MODEL.get()));

                        pOutput.accept(new ItemStack(ModItemsProgression.COPPER_WIRE.get()));
                        pOutput.accept(new ItemStack(ModItemsProgression.LEAD_WIRE.get()));
                        pOutput.accept(new ItemStack(ModItemsProgression.IRON_WIRE.get()));
                        pOutput.accept(new ItemStack(ModItemsProgression.TIN_WIRE.get()));
                        pOutput.accept(new ItemStack(ModItemsProgression.IRON_WIRE.get()));
                        pOutput.accept(new ItemStack(ModItemsProgression.GOLD_WIRE.get()));
                        pOutput.accept(new ItemStack(ModItemsProgression.SILVER_WIRE.get()));
                        pOutput.accept(new ItemStack(ModItemsProgression.NICKEL_WIRE.get()));
                        pOutput.accept(new ItemStack(ModItemsProgression.BRASS_WIRE.get()));
                        pOutput.accept(new ItemStack(ModItemsProgression.BRONZE_WIRE.get()));
                        pOutput.accept(new ItemStack(ModItemsProgression.STEEL_WIRE.get()));
                        pOutput.accept(new ItemStack(ModItemsProgression.ALUMINUM_WIRE.get()));
                        pOutput.accept(new ItemStack(ModItemsProgression.INDUSTRIAL_WIRE.get()));
                        pOutput.accept(new ItemStack(ModItemsProgression.INFINITY_WIRE.get()));

                        pOutput.accept(new ItemStack(ModItemsProgression.COPPER_SCREW.get()));
                        pOutput.accept(new ItemStack(ModItemsProgression.LEAD_SCREW.get()));
                        pOutput.accept(new ItemStack(ModItemsProgression.TIN_SCREW.get()));
                        pOutput.accept(new ItemStack(ModItemsProgression.IRON_SCREW.get()));
                        pOutput.accept(new ItemStack(ModItemsProgression.GOLD_SCREW.get()));
                        pOutput.accept(new ItemStack(ModItemsProgression.SILVER_SCREW.get()));
                        pOutput.accept(new ItemStack(ModItemsProgression.NICKEL_SCREW.get()));
                        pOutput.accept(new ItemStack(ModItemsProgression.BRASS_SCREW.get()));
                        pOutput.accept(new ItemStack(ModItemsProgression.BRONZE_SCREW.get()));
                        pOutput.accept(new ItemStack(ModItemsProgression.STEEL_SCREW.get()));
                        pOutput.accept(new ItemStack(ModItemsProgression.ALUMINUM_SCREW.get()));
                        pOutput.accept(new ItemStack(ModItemsProgression.INDUSTRIAL_SCREW.get()));
                        pOutput.accept(new ItemStack(ModItemsProgression.INFINITY_SCREW.get()));

                        pOutput.accept(new ItemStack(ModItemsProgression.COPPER_ROD.get()));
                        pOutput.accept(new ItemStack(ModItemsProgression.LEAD_ROD.get()));
                        pOutput.accept(new ItemStack(ModItemsProgression.TIN_ROD.get()));
                        pOutput.accept(new ItemStack(ModItemsProgression.IRON_ROD.get()));
                        pOutput.accept(new ItemStack(ModItemsProgression.GOLD_ROD.get()));
                        pOutput.accept(new ItemStack(ModItemsProgression.SILVER_ROD.get()));
                        pOutput.accept(new ItemStack(ModItemsProgression.NICKEL_ROD.get()));
                        pOutput.accept(new ItemStack(ModItemsProgression.BRASS_ROD.get()));
                        pOutput.accept(new ItemStack(ModItemsProgression.BRONZE_ROD.get()));
                        pOutput.accept(new ItemStack(ModItemsProgression.STEEL_ROD.get()));
                        pOutput.accept(new ItemStack(ModItemsProgression.ALUMINUM_ROD.get()));
                        pOutput.accept(new ItemStack(ModItemsProgression.INDUSTRIAL_ROD.get()));
                        pOutput.accept(new ItemStack(ModItemsProgression.INFINITY_ROD.get()));

                        pOutput.accept(new ItemStack(ModItemsProgression.COPPER_SHEET.get()));
                        pOutput.accept(new ItemStack(ModItemsProgression.LEAD_SHEET.get()));
                        pOutput.accept(new ItemStack(ModItemsProgression.TIN_SHEET.get()));
                        pOutput.accept(new ItemStack(ModItemsProgression.IRON_SHEET.get()));
                        pOutput.accept(new ItemStack(ModItemsProgression.GOLD_SHEET.get()));
                        pOutput.accept(new ItemStack(ModItemsProgression.SILVER_SHEET.get()));
                        pOutput.accept(new ItemStack(ModItemsProgression.NICKEL_SHEET.get()));
                        pOutput.accept(new ItemStack(ModItemsProgression.BRASS_SHEET.get()));
                        pOutput.accept(new ItemStack(ModItemsProgression.BRONZE_SHEET.get()));
                        pOutput.accept(new ItemStack(ModItemsProgression.STEEL_SHEET.get()));
                        pOutput.accept(new ItemStack(ModItemsProgression.ALUMINUM_SHEET.get()));
                        pOutput.accept(new ItemStack(ModItemsProgression.INDUSTRIAL_SHEET.get()));
                        pOutput.accept(new ItemStack(ModItemsProgression.INFINITY_SHEET.get()));
                        pOutput.accept(new ItemStack(ModItemsProgression.IRIDIUM_MESH.get()));

                        pOutput.accept(new ItemStack(ModItemsProgression.VOXEL_TOP.get()));
                        pOutput.accept(new ItemStack(ModItemsProgression.VOXEL_DOW.get()));
                        pOutput.accept(new ItemStack(ModItemsProgression.VOXEL_NORTH.get()));
                        pOutput.accept(new ItemStack(ModItemsProgression.VOXEL_SOUTH.get()));
                        pOutput.accept(new ItemStack(ModItemsProgression.VOXEL_WEST.get()));
                        pOutput.accept(new ItemStack(ModItemsProgression.VOXEL_EAST.get()));
                        pOutput.accept(new ItemStack(ModBlocksAdditions.VOXEL_BLOCK.get()));


                        //-------------------------//-------------------------//
                    })
                    .build());
    public static void register(IEventBus eventBus) {
        CREATIVE_MODE_TABS.register(eventBus);
    }
}
