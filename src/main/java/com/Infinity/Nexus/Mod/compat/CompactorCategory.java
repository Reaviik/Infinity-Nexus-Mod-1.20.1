package com.Infinity.Nexus.Mod.compat;

import com.Infinity.Nexus.Mod.InfinityNexusMod;
import com.Infinity.Nexus.Mod.block.ModBlocksAdditions;
import com.Infinity.Nexus.Mod.recipe.CompactorRecipes;
import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.gui.ingredient.IRecipeSlotsView;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeIngredientRole;
import mezz.jei.api.recipe.RecipeType;
import mezz.jei.api.recipe.category.IRecipeCategory;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.core.NonNullList;
import net.minecraft.core.RegistryAccess;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;

import java.util.List;

public class CompactorCategory implements IRecipeCategory<CompactorRecipes> {

    public static final ResourceLocation UID = new ResourceLocation(InfinityNexusMod.MOD_ID, "compacting");
    public static final ResourceLocation TEXTURE = new ResourceLocation(InfinityNexusMod.MOD_ID, "textures/gui/jei/compactor_gui.png");

    public static final RecipeType<CompactorRecipes> COMPACTOR_TYPE = new RecipeType<>(UID, CompactorRecipes.class);

    private final IDrawable background;
    private final IDrawable icon;

    public CompactorCategory(IGuiHelper helper) {
        this.background = helper.createDrawable(TEXTURE, 0, 0, 176, 95);
        this.icon = helper.createDrawableIngredient(VanillaTypes.ITEM_STACK, new ItemStack(ModBlocksAdditions.COMPACTOR.get()));
    }

    @Override
    public RecipeType<CompactorRecipes> getRecipeType() {
        return COMPACTOR_TYPE;
    }

    @Override
    public Component getTitle() {
        return Component.translatable("block.infinity_nexus_mod.compactor");
    }

    @Override
    public IDrawable getBackground() {
        return this.background;
    }

    @Override
    public void draw(CompactorRecipes recipe, IRecipeSlotsView recipeSlotsView, GuiGraphics guiGraphics, double mouseX, double mouseY) {
        Minecraft minecraft = Minecraft.getInstance();
        guiGraphics.drawString(minecraft.font, "§3Layer: 1", 8, 84, 0xFFFFFF, false);
        guiGraphics.drawString(minecraft.font, "§bLayer: 2", 62, 84, 0xFFFFFF, false);
        guiGraphics.drawString(minecraft.font, "§aLayer: 3", 118, 84, 0xFFFFFF, false);
    }

    @Override
    public IDrawable getIcon() {
        return this.icon;
    }

    //@Override
    //public void setRecipe(IRecipeLayoutBuilder builder, CompactorRecipes recipe, IFocusGroup focuses) {
    //    NonNullList<Ingredient> inputs = recipe.getIngredients();
    //    int[] positionX = {6, 62, 118}; // Posições X das grades
    //    int positionY = 11; // Posição Y inicial
    //    int increment = 18; // Distância entre linhas
//
    //    for (int grid = 0; grid < 3; grid++) { // Loop para as 3 grades
    //        for (int row = 0; row < 3; row++) { // Loop para as linhas
    //            for (int col = 0; col < 3; col++) { // Loop para as colunas
    //                int index = grid * 9 + row * 3 + col; // Índice correto para cada ingrediente
    //                if (index < inputs.size()) { // Evita erro se houver menos ingredientes
    //                    builder.addSlot(RecipeIngredientRole.INPUT, positionX[grid] + (col * increment), positionY + (row * increment))
    //                            .addIngredients(inputs.get(index));
    //                }
    //            }
    //        }
    //    }
    //}
    @Override
    public void setRecipe(IRecipeLayoutBuilder builder, CompactorRecipes recipe, IFocusGroup focuses) {
        NonNullList<Ingredient> inputs = recipe.getIngredients();
        int[] positionX = {11, 63, 115};
        int positionY = 6;
        int increment = 17;
        inputs.get(13).getItems()[0].resetHoverName();
        String name = inputs.get(13).getItems()[0].getDisplayName().getString();
        inputs.get(13).getItems()[0].setHoverName(Component.literal(name + " Este item deve ser dropado no centro!"));

        for (int grid = 0; grid < 3; grid++) {
            for (int row = 0; row < 3; row++) {
                for (int col = 0; col < 3; col++) {
                    int index = grid * 9 + row * 3 + col;
                    if (index < inputs.size()) {
                        builder.addSlot(RecipeIngredientRole.INPUT, positionX[grid] + (col * increment), positionY + (row * increment)).addIngredients(inputs.get(index));
                    }
                }
            }
        }
        builder.addSlot(RecipeIngredientRole.OUTPUT, 80, 59).addItemStack(recipe.getResultItem(null));
    }
}
