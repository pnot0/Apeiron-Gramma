package com.pnot0.magia.gui;

import com.pnot0.magia.Magia;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;

public class SocketGUI extends AbstractContainerScreen<SocketContainer>{

	private final ResourceLocation GUI;
	
	public SocketGUI(SocketContainer container, Inventory playerInventory, Component name) {
		super(container, playerInventory, name);
		
		this.GUI = ResourceLocation.fromNamespaceAndPath(Magia.MODID, "textures/gui/socketmenu_gui.png");
		this.imageWidth = 176;
		this.imageHeight = 150;
		
	}
	
	@Override
	protected void init() {
		super.init();
	}
	
	@Override
	protected void renderBg(GuiGraphics graphics, float tick, int x, int y) {
		graphics.blit(this.GUI, this.leftPos, this.topPos, 0, 0, this.imageWidth, this.imageHeight, this.imageWidth, this.imageHeight);
	}
	
	public void render(GuiGraphics graphics, int x, int y, float tick) {
		this.renderBackground(graphics);
		super.render(graphics, x, y, tick);
	}
}
