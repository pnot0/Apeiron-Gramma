package com.pnot0.apeirongramma.client.registry;

import com.pnot0.apeirongramma.ApeironGramma;
import com.pnot0.apeirongramma.screens.EmptySocket;
import com.pnot0.apeirongramma.screens.MagicCircle;

import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.RegisterGuiLayersEvent;
import net.neoforged.neoforge.client.gui.VanillaGuiLayers;

@EventBusSubscriber(modid = ApeironGramma.MODID, bus = EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ClientRegistering {
    @SubscribeEvent
    public static void registerOverlays(RegisterGuiLayersEvent event) {
    	event.registerAbove(VanillaGuiLayers.CROSSHAIR, ApeironGramma.prefix("circle_gui"), MagicCircle.INSTANCE::render);
        event.registerAboveAll(ApeironGramma.prefix("empty_socket_hud"), EmptySocket.INSTANCE::render);
    }
}