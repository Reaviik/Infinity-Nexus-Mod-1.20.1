package com.Infinity.Nexus.Mod.events;

import com.Infinity.Nexus.Mod.InfinityNexusMod;
import com.Infinity.Nexus.Mod.block.entity.ModBlockEntities;
import com.Infinity.Nexus.Mod.block.entity.renderer.DisplayBlockEntityRenderer;
import com.Infinity.Nexus.Mod.block.entity.renderer.FactoryBlockEntityRenderer;
import com.Infinity.Nexus.Mod.block.entity.renderer.PlacerBlockEntityRenderer;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = InfinityNexusMod.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ModEventClientBusEvents {

    @SubscribeEvent
    public static void registerBER(EntityRenderersEvent.RegisterRenderers event) {
        event.registerBlockEntityRenderer(ModBlockEntities.FACTORY_BE.get(),
                FactoryBlockEntityRenderer::new);

        event.registerBlockEntityRenderer(ModBlockEntities.DISPLAY_BE.get(),
                DisplayBlockEntityRenderer::new);

        event.registerBlockEntityRenderer(ModBlockEntities.PLACER_BE.get(),
                PlacerBlockEntityRenderer::new);
    }
}
