package com.Infinity.Nexus.Mod.recipe;

import com.Infinity.Nexus.Mod.InfinityNexusMod;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModRecipes {

    public static final DeferredRegister<RecipeSerializer<?>> SERIALIZER =
            DeferredRegister.create(ForgeRegistries.RECIPE_SERIALIZERS, InfinityNexusMod.MOD_ID);
    public static final RegistryObject<RecipeSerializer<CrusherRecipes>> CRUSHER_SERIALIZER = SERIALIZER.register("crushing", () -> CrusherRecipes.Serializer.INSTANCE);
    public static final RegistryObject<RecipeSerializer<PressRecipes>> PRESS_SERIALIZER = SERIALIZER.register("pressing", () -> PressRecipes.Serializer.INSTANCE);
    public static final RegistryObject<RecipeSerializer<AssemblerRecipes>> ASSEMBLY_SERIALIZER = SERIALIZER.register("assembler", () -> AssemblerRecipes.Serializer.INSTANCE);
    public static final RegistryObject<RecipeSerializer<FactoryRecipes>> FACTORY_SERIALIZER = SERIALIZER.register("factory", () -> FactoryRecipes.Serializer.INSTANCE);
    public static final RegistryObject<RecipeSerializer<SqueezerRecipes>> SQUEEZER_SERIALIZER = SERIALIZER.register("squeezing", () -> SqueezerRecipes.Serializer.INSTANCE);
    public static final RegistryObject<RecipeSerializer<SmelteryRecipes>> SMELTRERY_SERIALIZER = SERIALIZER.register("melting", () -> SmelteryRecipes.Serializer.INSTANCE);
    public static final RegistryObject<RecipeSerializer<FermentationBarrelRecipes>> FERMENTATION_BARREL_SERIALIZER = SERIALIZER.register("fermentation", () -> FermentationBarrelRecipes.Serializer.INSTANCE);
    public static final RegistryObject<RecipeSerializer<MatterCondenserRecipes>> MATTER_CONDENSER_SERIALIZER = SERIALIZER.register("matter_condenser", () -> MatterCondenserRecipes.Serializer.INSTANCE);
    public static final RegistryObject<RecipeSerializer<InfuserRecipes>> INFUSER_SERIALIZER = SERIALIZER.register("infuser", () -> InfuserRecipes.Serializer.INSTANCE);
    public static final RegistryObject<RecipeSerializer<CompactorRecipes>> COMPACTOR_SERIALIZER = SERIALIZER.register("compacting", () -> CompactorRecipes.Serializer.INSTANCE);

    public static void register(IEventBus eventBus) {
        SERIALIZER.register(eventBus);
    }
}
