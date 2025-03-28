package com.example.examplemod.client.registry;

import com.example.examplemod.ExampleMod;
import com.example.examplemod.screens.EmptySocket;
import com.example.examplemod.screens.MagicCircle;

import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.RegisterGuiLayersEvent;
import net.neoforged.neoforge.client.gui.VanillaGuiLayers;

@EventBusSubscriber(modid = ExampleMod.MODID, bus = EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ClientRegistering {
    @SubscribeEvent
    public static void registerOverlays(RegisterGuiLayersEvent event) {
    	event.registerAbove(VanillaGuiLayers.CROSSHAIR, ExampleMod.prefix("circle_gui"), MagicCircle.INSTANCE::render);
        event.registerAboveAll(ExampleMod.prefix("empty_socket_hud"), EmptySocket.INSTANCE::render);
    }
}