package com.pnot0.magia.gui;

import com.mojang.logging.LogUtils;
import com.pnot0.magia.item.ItemRegistry;
import com.pnot0.magia.item.SocketItem;
import com.pnot0.magia.item.SocketsEnum;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.client.gui.overlay.ForgeGui;
import net.minecraftforge.client.gui.overlay.IGuiOverlay;

public class MagiaOverlay implements IGuiOverlay{
	public static final MagiaOverlay instance = new MagiaOverlay();
	private static final Minecraft minecraft = Minecraft.getInstance();
	
	private MagiaCircle overlay;
	
	public MagiaOverlay() {
		this.overlay = new MagiaCircle();
	}
	
	public void advanceSelection() {
		if(shouldRender())
			overlay.advanceSelection();
	}
	
	public void returnSelection() {
		if(shouldRender())
			overlay.returnSelection();
	}
	
	private boolean shouldRender() {
		return minecraft.player.getMainHandItem().getItem() instanceof SocketItem;
	}
	
	@Override
	public void render(ForgeGui gui, GuiGraphics graphics, float tick, int screenWidth, int screenHeight) {
		if(shouldRender()) {
			SocketsEnum socketTier = SocketItem.getSocketTier(minecraft.player.getMainHandItem());
			overlay.renderOverlay(graphics, screenWidth, screenHeight, socketTier.overlayTexture, socketTier.overlayWidth, socketTier.slots);
		}
	}

}
