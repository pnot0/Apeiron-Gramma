package com.pnot0.magia.client;

import com.pnot0.magia.Magia;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RenderLevelStageEvent;
import net.minecraftforge.client.event.ScreenEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = Magia.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE, value = Dist.CLIENT)
public class ClientEvents {	
	@SubscribeEvent
    public static void onScreenRender(ScreenEvent.Render.Post event) {
    	
	}
    
	@SubscribeEvent
	public static void onRenderStage(RenderLevelStageEvent event) {
		
	}
	
    //add registerReloadListeners
    //what is rendering related will be dedicated to a rendering class, separating from the client event class
}
