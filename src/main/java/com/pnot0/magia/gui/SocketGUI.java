package com.pnot0.magia.gui;

import com.pnot0.magia.Magia;
import com.pnot0.magia.inventory.SocketContainer;
import com.pnot0.magia.item.SocketsEnum;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;

public class SocketGUI extends AbstractContainerScreen<SocketContainer>{

	private final ResourceLocation GUI;
	
	public SocketGUI(SocketContainer container, Inventory playerInventory, Component name) {
		super(container, playerInventory, name);
		
		SocketsEnum socketTier = container.getSocketTier();
		this.GUI = socketTier.guiTexture;
		this.imageWidth = socketTier.guiWidth;
		this.imageHeight = socketTier.guiHeight;
	}
	
	@Override
	protected void init() {
		super.init();
		this.inventoryLabelY = 10000;
		this.titleLabelY = -10;
	}
	
	@Override
	protected void renderBg(GuiGraphics graphics, float tick, int x, int y) {
		graphics.blit(this.GUI, this.leftPos, this.topPos, 0, 0, this.imageWidth, this.imageHeight, this.imageWidth, this.imageHeight);
	}
	
	@Override
	protected void renderLabels(GuiGraphics graphics, int x, int y) {
		graphics.drawString(font, this.title.getString(), 7, 6, 0x404040, false);
	}
	
	public void render(GuiGraphics graphics, int x, int y, float tick) {
		this.renderBackground(graphics);
		super.render(graphics, x, y, tick);
		this.renderTooltip(graphics, x, y);
	}
}
