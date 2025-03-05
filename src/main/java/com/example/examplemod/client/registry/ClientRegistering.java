package com.example.examplemod.client.registry;

import com.example.examplemod.ExampleMod;
import com.example.examplemod.screens.PlayerScreen;

import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.RegisterGuiLayersEvent;
import net.neoforged.neoforge.client.gui.VanillaGuiLayers;

@EventBusSubscriber(modid = ExampleMod.MODID, bus = EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ClientRegistering {
    
    @SubscribeEvent
    public static void registerOverlays(RegisterGuiLayersEvent event) {
        event.registerAbove(
            VanillaGuiLayers.CROSSHAIR, 
            ExampleMod.prefix("example_hud"), 
            PlayerScreen.OVERLAY_LAYER
        );
    }
}