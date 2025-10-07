package com.pnot0.magia.gui;

import java.awt.Color;
import java.util.logging.Logger;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.logging.LogUtils;
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
	private static final int WIDTH = 300;
	private static final int HEIGHT  = 300;
	
	private static float rotationAngle = 0;
	
	private static final int SIDES = 3;
	private static final int ANGLE_SEPARATION = 360 / SIDES;
	
	@Override
	public void render(ForgeGui gui, GuiGraphics graphics, float tick, int screenWidth, int screenHeight) {
		if(minecraft.player.getMainHandItem().getItem() == ItemRegistry.SOCKET_ITEM.get()) {
						
	        float xPos = (screenWidth / -1.7f) + (screenWidth / 2f);
	        float yPos = xPos;
	        
	        float centerPos = (xPos + (xPos + (float) WIDTH)) / 2f;
	        
	        //LogUtils.getLogger().info("xpos: " + Float.toString(xPos));
	        //LogUtils.getLogger().info("end xpos: " + Float.toString(xPos + (float) WIDTH));
	        //LogUtils.getLogger().info("average/center pos: " + Float.toString(centerPos));
	        
	        rotationAngle = (rotationAngle + (minecraft.getDeltaFrameTime() * 20f)) % 360;
	        
	        LogUtils.getLogger().info(Float.toString(rotationAngle));
	        
	        for (int s = 0; s < SIDES; s++) {
	        	double separationRadians = Math.toRadians(ANGLE_SEPARATION * s) + Math.toRadians(rotationAngle);
	        	int xPosRotation = (int) (centerPos + (WIDTH/4) * Math.cos(separationRadians));
	        	int yPosRotation = (int) (centerPos + (WIDTH/4) * Math.sin(separationRadians));
	        	
	        	graphics.fill(xPosRotation, yPosRotation, xPosRotation+10, yPosRotation+10, -65536);
	        }
	        
	        //graphics.fill((int)(centerPos - WIDTH/4), (int)(centerPos - HEIGHT/4), WIDTH/2, HEIGHT/2, -65536);
	        
			PoseStack poseStack = graphics.pose();
	        poseStack.pushPose();
	        poseStack.translate(xPos, yPos, 0);
	        
	        //TODO keep a constant overlay scaling
			graphics.blit(TEXTURE, 0, 0, 0, 0, WIDTH, HEIGHT, WIDTH, HEIGHT);
				
        	poseStack.popPose();
        	
        	//poseStack.pushPose();
        	
			//poseStack.translate(centerPos - WIDTH/4, centerPos - HEIGHT/4, 0);
						
			//graphics.blit(TEXTURE, 0, 0, 0, 0, WIDTH/2, HEIGHT/2, WIDTH/2, HEIGHT/2);
			//graphics.renderItem(ItemRegistry.TEST_SPELLSCHOOL.get().getDefaultInstance(), (int) centerPos - 16, (int) centerPos - 16);
			
			//poseStack.popPose();
		}
	}
}
