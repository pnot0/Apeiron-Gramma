package com.pnot0.magia.registry.client;

import com.pnot0.magia.Magia;
import com.pnot0.magia.screens.EmptySocket;
import com.pnot0.magia.screens.MagicCircle;

import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.RegisterGuiLayersEvent;
import net.neoforged.neoforge.client.gui.VanillaGuiLayers;

@EventBusSubscriber(modid = Magia.MODID, bus = EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ClientRegistering {
    @SubscribeEvent
    public static void registerOverlays(RegisterGuiLayersEvent event) {
    	event.registerAbove(VanillaGuiLayers.CROSSHAIR, Magia.prefix("circle_gui"), MagicCircle.INSTANCE::render);
        event.registerAboveAll(Magia.prefix("empty_socket_hud"), EmptySocket.INSTANCE::render);
    }
}