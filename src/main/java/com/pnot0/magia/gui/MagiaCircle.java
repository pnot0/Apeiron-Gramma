package com.pnot0.magia.gui;

import com.mojang.blaze3d.vertex.PoseStack;
import com.pnot0.magia.Magia;
import com.pnot0.magia.item.ItemRegistry;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.client.gui.overlay.ForgeGui;
import net.minecraftforge.client.gui.overlay.IGuiOverlay;

public class MagiaCircle implements IGuiOverlay{
	
	protected static final Minecraft minecraft = Minecraft.getInstance();
	private static final ResourceLocation TEXTURE = ResourceLocation.fromNamespaceAndPath(Magia.MODID, "textures/gui/overlay.png");
	private int guiSize = 0;
	
	@Override
	public void render(ForgeGui gui, GuiGraphics graphics, float tick, int screenWidth, int screenHeight) {
		if(minecraft.player.getMainHandItem().getItem() == ItemRegistry.SOCKET_ITEM.get()) {
						
	        float xPos = (float) (screenWidth / -1.7);
	        float yPos = xPos;
			
			PoseStack poseStack = graphics.pose();
	        poseStack.pushPose();
	        poseStack.translate(xPos + screenWidth / 2f, yPos + screenWidth / 2f, 0);
	        
	        //TODO keep a constant overlay scaling
			graphics.blit(TEXTURE, 0, 0, 0, 0, 300, 300, 300, 300);
			
        	poseStack.popPose();
		}
	}
}
