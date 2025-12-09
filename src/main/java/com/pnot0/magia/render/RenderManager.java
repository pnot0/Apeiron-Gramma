package com.pnot0.magia.render;

import java.io.IOException;

import com.mojang.logging.LogUtils;
import com.pnot0.magia.Magia;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.PostChain;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.server.packs.resources.SimplePreparableReloadListener;
import net.minecraft.util.profiling.ProfilerFiller;
import net.minecraftforge.client.event.RegisterClientReloadListenersEvent;
import net.minecraftforge.fml.ModList;

public class RenderManager {
	public static final RenderManager instance = new RenderManager();
	
	private static boolean hasIris = false;
	private static boolean shaderpackActive = false;
	
	private static final ResourceLocation EXPLOSION_SHADER = ResourceLocation.fromNamespaceAndPath(Magia.MODID, "shaders/post/magia_explosion.json");
	
	private static PostChain explosionChain;
	
	public static void registerReloadListener(RegisterClientReloadListenersEvent event) {
		Magia.LOGGER.info("RenderManager.registerReloadListener");
    	event.registerReloadListener(new SimplePreparableReloadListener<Void>() {
			@Override
			protected Void prepare(ResourceManager resourceManager, ProfilerFiller profiler) {return null;}
			
			@Override
			protected void apply(Void object, ResourceManager resourceManager, ProfilerFiller profiler) {
				if(RenderManager.isShaderpackActive()) {
					//close the chain
				}else {
					reloadChain(resourceManager);
				}
			}
    	});
	}
	
	public static void reloadChain(ResourceManager resouceManager) {
		if(isShaderpackActive()) {
			//close chain
			return;
		}
		
		Minecraft minecraft = Minecraft.getInstance();
		try {
			explosionChain = new PostChain(minecraft.getTextureManager(), resouceManager, minecraft.getMainRenderTarget(), EXPLOSION_SHADER);
		}catch(IOException exception) {
			LogUtils.getLogger().info("failed to load postchain: ", exception);
		}
	}
	
	public static void checkIrisLoaded() {
		hasIris = ModList.get().isLoaded("oculus") || ModList.get().isLoaded("iris");
	}
	
	private static boolean isShaderpackActive() {
		if (!hasIris) return false;
		return shaderpackActive;
	}
	
	private static boolean checkIrisState() {
		if(!hasIris) return false;
		boolean now = queryIrisShaderpack();
		boolean changed = (now != shaderpackActive);
		shaderpackActive = now;
		return changed;
	}
	
	private static boolean queryIrisShaderpack() {
		if(!hasIris) return false;
		try {
			Class<?> irisAPIClass = Class.forName("net.irisshaders.iris.api.v0.IrisApi");
			Object iris = irisAPIClass.getMethod("getInstance").invoke(null);
			Object result = irisAPIClass.getMethod("isShaderPackInUse").invoke(iris);
			return result instanceof Boolean && (Boolean) result;
		}catch(Throwable t) {
			return false;
		}
	}
}
