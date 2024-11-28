package com.Infinity.Nexus.Mod.datagen;

import com.Infinity.Nexus.Mod.InfinityNexusMod;
import com.Infinity.Nexus.Mod.item.ModItemsAdditions;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.DisplayInfo;
import net.minecraft.advancements.FrameType;
import net.minecraft.advancements.critereon.InventoryChangeTrigger;
import net.minecraft.core.HolderLookup;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.common.data.ForgeAdvancementProvider;

import java.util.function.Consumer;

public class ModAdvancementProvider implements ForgeAdvancementProvider.AdvancementGenerator {
    @Override
    public void generate(HolderLookup.Provider registries, Consumer<Advancement> saver, ExistingFileHelper existingFileHelper) {
        Advancement rootAdvancement = Advancement.Builder.advancement()
                .display(new DisplayInfo(new ItemStack(ModItemsAdditions.INFINITY_SINGULARITY.get()),
                        Component.literal("Infinity Nexus Mod"), Component.literal("The start of Infinity!"),
                        new ResourceLocation(InfinityNexusMod.MOD_ID, "textures/block/infinity_singularity.png"), FrameType.TASK,
                        true, true, false))
                .addCriterion("has_raw_infinity", InventoryChangeTrigger.TriggerInstance.hasItems(ModItemsAdditions.RAW_INFINITY.get()))
                .save(saver, new ResourceLocation(InfinityNexusMod.MOD_ID, "infinity_nexus_mod"), existingFileHelper);

    }
}
