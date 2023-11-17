package com.Infinity.Nexus.Mod.screen.fermenting;

import com.Infinity.Nexus.Mod.InfinityNexusMod;
import com.Infinity.Nexus.Mod.block.entity.FermentingBarrelBlockEntity;
import com.Infinity.Nexus.Mod.screen.renderer.FluidTankRenderer;
import com.Infinity.Nexus.Mod.screen.renderer.InfoArea;
import com.Infinity.Nexus.Mod.utils.MouseUtil;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.item.TooltipFlag;
import net.minecraftforge.fluids.FluidStack;

import java.util.Optional;

public class FermentingBarrelScreen extends AbstractContainerScreen<FermentingBarrelMenu> {
    private static final ResourceLocation TEXTURE =
            new ResourceLocation(InfinityNexusMod.MOD_ID, "textures/gui/fermentation_barrel_gui.png");

    private FluidTankRenderer fluidRenderer;
    private FluidTankRenderer fluidRendererOut;

    public FermentingBarrelScreen(FermentingBarrelMenu pMenu, Inventory pPlayerInventory, Component pTitle) {
        super(pMenu, pPlayerInventory, pTitle);
    }
    @Override
    protected void init() {
        super.init();
        this.inventoryLabelY = 10000;
        this.titleLabelY = 10000;
        assignFluidTank();
    }
    private void assignFluidTank() {
        fluidRenderer = new FluidTankRenderer(FermentingBarrelBlockEntity.getInputFluidCapacity(), true, 16, 62);
        fluidRendererOut = new FluidTankRenderer(FermentingBarrelBlockEntity.getOutputFluidCapacity(), true, 16, 62);
    }
    @Override
    protected void renderLabels(GuiGraphics pGuiGraphics, int pMouseX, int pMouseY) {
        int x = (width - imageWidth) / 2;
        int y = (height - imageHeight) / 2;
        pGuiGraphics.drawString(this.font,this.playerInventoryTitle,8,74,0XFFFFFF);
        pGuiGraphics.drawString(this.font,this.title,8,-9,0XFFFFFF);

        renderFluidAreaTooltips(pGuiGraphics,pMouseX,pMouseY, x, y, menu.blockEntity.getFluidInInputTank(), 62,6, fluidRenderer);
        renderFluidAreaTooltips(pGuiGraphics,pMouseX,pMouseY, x, y, menu.blockEntity.getFluidInOutputTank(), 98,6, fluidRendererOut);

        InfoArea.draw(pGuiGraphics);
        super.renderLabels(pGuiGraphics, pMouseX, pMouseY);
    }



    @Override
    protected void renderBg(GuiGraphics guiGraphics, float pPartialTick, int pMouseX, int pMouseY) {
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.setShaderTexture(0, TEXTURE);
        int x = (width - imageWidth) / 2;
        int y = (height - imageHeight) / 2;

        guiGraphics.blit(TEXTURE, x + 2, y-14, 2, 167, 174, 64);
        guiGraphics.blit(TEXTURE, x, y, 0, 0, imageWidth, imageHeight);


        renderProgressArrow(guiGraphics, x, y);

        fluidRenderer.render(guiGraphics, x+62, y+6, menu.blockEntity.getFluidInInputTank());
        fluidRendererOut.render(guiGraphics, x+98, y+6, menu.blockEntity.getFluidInOutputTank());
    }

    private void renderProgressArrow(GuiGraphics guiGraphics, int x, int y) {
        if(menu.isCrafting()) {
            guiGraphics.blit(TEXTURE, x + 81, y + 29, 176, 0, 16, menu.getScaledProgress());

        }
    }

    @Override
    public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float delta) {
        renderBackground(guiGraphics);
        super.render(guiGraphics, mouseX, mouseY, delta);
        renderTooltip(guiGraphics, mouseX, mouseY);
    }
    private void renderFluidAreaTooltips(GuiGraphics guiGraphics, int pMouseX, int pMouseY, int x, int y,
                                         FluidStack stack, int offsetX, int offsetY, FluidTankRenderer renderer) {
        if(isMouseAboveArea(pMouseX, pMouseY, x, y, offsetX, offsetY, renderer)) {
            guiGraphics.renderTooltip(this.font, renderer.getTooltip(stack, TooltipFlag.Default.NORMAL),
                    Optional.empty(), pMouseX - x, pMouseY - y);
        }
    }
    private boolean isMouseAboveArea(int pMouseX, int pMouseY, int x, int y, int offsetX, int offsetY, FluidTankRenderer renderer) {
        return MouseUtil.isMouseOver(pMouseX, pMouseY, x + offsetX, y + offsetY, renderer.getWidth(), renderer.getHeight());
    }
    private boolean isMouseAboveArea(int pMouseX, int pMouseY, int x, int y, int offsetX, int offsetY, int width, int height) {
        return MouseUtil.isMouseOver(pMouseX, pMouseY, x + offsetX, y + offsetY, width, height);
    }
}