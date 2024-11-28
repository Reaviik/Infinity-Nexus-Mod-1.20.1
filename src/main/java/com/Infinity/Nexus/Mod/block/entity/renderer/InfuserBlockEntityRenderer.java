package com.Infinity.Nexus.Mod.block.entity.renderer;

import com.Infinity.Nexus.Mod.block.custom.Infuser;
import com.Infinity.Nexus.Mod.block.entity.InfuserBlockEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.core.BlockPos;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LightLayer;

public class InfuserBlockEntityRenderer implements BlockEntityRenderer<InfuserBlockEntity> {

    public InfuserBlockEntityRenderer(BlockEntityRendererProvider.Context context) {
    }

    @Override
    public void render(InfuserBlockEntity pBlockEntity, float pPartialTick, PoseStack pPoseStack, MultiBufferSource pBufferSource,
                       int pPackedLight, int pPackedOverlay) {


        ItemStack itemStack = pBlockEntity.getRenderStack();

        if(itemStack != ItemStack.EMPTY) {
            ItemRenderer itemRenderer = Minecraft.getInstance().getItemRenderer();
            pPoseStack.pushPose();
            pPoseStack.translate(0.5, 1.15, 0.5);
            pPoseStack.scale(0.5f, 0.5f, 0.5f);
            pPoseStack.mulPose(Axis.YN.rotationDegrees(pBlockEntity.getBlockState().getValue(Infuser.FACING).toYRot()));
            pPoseStack.mulPose(Axis.YN.rotationDegrees(180.0F));

            itemRenderer.renderStatic(itemStack, ItemDisplayContext.FIXED, getLightLevel(pBlockEntity.getLevel(),
                    pBlockEntity.getBlockPos()), OverlayTexture.NO_OVERLAY, pPoseStack, pBufferSource, pBlockEntity.getLevel(), 0);
            pPoseStack.popPose();
        }
    }
    private int getLightLevel(Level pLevel, BlockPos pPos) {
        int blight = pLevel.getBrightness(LightLayer.BLOCK, pPos);
        int slight = pLevel.getBrightness(LightLayer.SKY, pPos);

        return LightTexture.pack(blight, slight);
    }
}
