package com.Infinity.Nexus.Mod.recipe;

import com.Infinity.Nexus.Mod.InfinityNexusMod;
import com.Infinity.Nexus.Mod.block.entity.InfuserBlockEntity;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import net.minecraft.core.NonNullList;
import net.minecraft.core.RegistryAccess;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;

public class InfuserRecipes implements Recipe<SimpleContainer> {
    private final NonNullList<Ingredient> inputItems;
    private final int inputCount;
    private final ItemStack output;
    private final ResourceLocation id;
    private final int[] pedestals;

    public InfuserRecipes(NonNullList<Ingredient> inputItems, int inputCount, ItemStack output, ResourceLocation id, int[] pedestals) {
        this.inputItems = inputItems;
        this.inputCount = inputCount;
        this.output = output;
        this.id = id;
        this.pedestals = pedestals;
    }


    @Override
    public boolean matches(SimpleContainer pContainer, Level pLevel) {
        if(pLevel.isClientSide()) {
            return false;
        }
        int inputSlot = InfuserBlockEntity.getInputSlot();
        ItemStack stack = pContainer.getItem(inputSlot);
        return (inputItems.get(0).test(stack) && pContainer.getItem(0).getCount() >= inputCount);
    }

    @Override
    public NonNullList<Ingredient> getIngredients() {
        return inputItems;
    }

    @Override
    public @NotNull ItemStack assemble(@NotNull SimpleContainer pContainer, RegistryAccess pRegistryAccess) {
        return output.copy();
    }
    public int[] getPedestals() {
        return pedestals;
    }

    @Override
    public boolean canCraftInDimensions(int pWidth, int pHeight) {
        return true;
    }

    @Override
    public @NotNull ItemStack getResultItem(RegistryAccess pRegistryAccess) {
        return output.copy();
    }

    @Override
    public ResourceLocation getId() {
        return id;
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return Serializer.INSTANCE;
    }

    @Override
    public RecipeType<?> getType() {
        return Type.INSTANCE;
    }

    public int getInputCount() {
        return inputCount;
    }

    public static class Type implements RecipeType<InfuserRecipes> {
        public static final Type INSTANCE = new Type();
        public static final String ID = "infuser";
    }

    public static class Serializer implements RecipeSerializer<InfuserRecipes> {
        public static final Serializer INSTANCE = new Serializer();
        public static final ResourceLocation ID = new ResourceLocation(InfinityNexusMod.MOD_ID, "infuser");

        @Override
        public InfuserRecipes fromJson(ResourceLocation pRecipeId, JsonObject pSerializedRecipe) {
            ItemStack output = ShapedRecipe.itemStackFromJson(GsonHelper.getAsJsonObject(pSerializedRecipe, "output"));
            JsonArray ingredients = GsonHelper.getAsJsonArray(pSerializedRecipe, "ingredients");


            int inputCount = 1;
            if(ingredients.get(0).getAsJsonObject().get("count") != null){
                inputCount = ingredients.get(0).getAsJsonObject().get("count").getAsInt();
            }

            NonNullList<Ingredient> inputs = NonNullList.withSize(1, Ingredient.EMPTY);
            inputs.set(0, Ingredient.fromJson(ingredients.get(0)));

            JsonArray pedestalArray = GsonHelper.getAsJsonArray(pSerializedRecipe, "pedestals");
            int[] pedestals = new int[pedestalArray.size()];

            for (int i = 0; i < pedestalArray.size(); i++) {
                pedestals[i] = pedestalArray.get(i).getAsInt();
            }
            return new InfuserRecipes(inputs, inputCount, output, pRecipeId, pedestals);
        }

        @Override
        public @Nullable InfuserRecipes fromNetwork(ResourceLocation pRecipeId, FriendlyByteBuf pBuffer) {
            //1
            NonNullList<Ingredient> inputs = NonNullList.withSize(pBuffer.readInt(), Ingredient.EMPTY);

            for(int i = 0; i < inputs.size(); i++) {
                //2
                inputs.set(i, Ingredient.fromNetwork(pBuffer));
            }
            //3
            int inputCount = pBuffer.readInt();
            ItemStack output = pBuffer.readItem();
            int pedestalCount = pBuffer.readInt();
            int[] pedestals = new int[pedestalCount];

            for (int i = 0; i < pedestalCount; i++) {
                pedestals[i] = pBuffer.readInt();
            }
            return new InfuserRecipes(inputs, inputCount, output, pRecipeId, pedestals);
        }

        @Override
        public void toNetwork(FriendlyByteBuf pBuffer, InfuserRecipes pRecipe) {
            //1
            pBuffer.writeInt(pRecipe.getIngredients().size());

            for (Ingredient ing : pRecipe.getIngredients()) {
                //2
                ing.toNetwork(pBuffer);
            }
            //3
            pBuffer.writeInt(pRecipe.inputCount);
            pBuffer.writeItemStack(pRecipe.getResultItem(null), false);
            pBuffer.writeInt(pRecipe.getPedestals().length);

            for (int pedestal : pRecipe.getPedestals()) {
                pBuffer.writeInt(pedestal);
            }
        }
    }
}