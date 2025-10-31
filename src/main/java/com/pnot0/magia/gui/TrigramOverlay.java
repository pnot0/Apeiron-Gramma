package com.pnot0.magia.gui;

import com.mojang.logging.LogUtils;
import com.pnot0.magia.item.ItemRegistry;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraftforge.client.gui.overlay.ForgeGui;
import net.minecraftforge.client.gui.overlay.IGuiOverlay;

public class TrigramOverlay implements IGuiOverlay{
	public static final TrigramOverlay instance = new TrigramOverlay();
	private final Minecraft minecraft = Minecraft.getInstance();
	
	private static final String textureLocation = "textures/gui/overlay.png";
	private static final int WIDTH = 300;
	private static final int SIDES = 3;
	private MagiaCircle circleOverlay;
	
	public TrigramOverlay() {
		this.circleOverlay = new MagiaCircle(
				textureLocation, WIDTH, WIDTH, SIDES
			);
	}
	
	//TODO abstract class for these methods
	public void advanceSelection() {
		if(shouldRender())
			circleOverlay.advanceSelection();
	}
	
	public void returnSelection() {
		if(shouldRender())
			circleOverlay.returnSelection();
	}
	
	private boolean shouldRender() {
		return Minecraft.getInstance().player.getMainHandItem().getItem() == ItemRegistry.SOCKET_ITEM.get();
	}
	
	@Override
	public void render(ForgeGui gui, GuiGraphics graphics, float tick, int screenWidth, int screenHeight) {
		if(shouldRender())
			circleOverlay.render(graphics, screenWidth, screenHeight);
	}

}
