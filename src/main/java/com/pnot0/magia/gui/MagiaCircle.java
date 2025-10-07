package com.pnot0.magia.gui;

import java.awt.Color;
import java.util.logging.Logger;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.logging.LogUtils;
import com.mojang.math.Axis;
import com.pnot0.magia.Magia;
import com.pnot0.magia.inventory.SocketData;
import com.pnot0.magia.item.ItemRegistry;
import com.pnot0.magia.item.SocketItem;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraftforge.client.gui.overlay.ForgeGui;
import net.minecraftforge.client.gui.overlay.IGuiOverlay;
import net.minecraftforge.registries.ForgeRegistries;

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
			
			//TODO make this better later in refactoring
			SocketData data = SocketItem.getData(minecraft.player.getMainHandItem());
			
	        float xPos = (screenWidth / -1.7f) + (screenWidth / 2f);
	        float yPos = xPos;
	        
	        float centerPos = (xPos + (xPos + (float) WIDTH)) / 2f;
	        
	        //LogUtils.getLogger().info("xpos: " + Float.toString(xPos));
	        //LogUtils.getLogger().info("end xpos: " + Float.toString(xPos + (float) WIDTH));
	        //LogUtils.getLogger().info("average/center pos: " + Float.toString(centerPos));
	        
	        rotationAngle = (rotationAngle + (minecraft.getDeltaFrameTime() * 2f)) % 360;
	        
	        //LogUtils.getLogger().info(Float.toString(rotationAngle));
	        
	        int sqrSize = 10;
	        
	        graphics.fill((int) centerPos - sqrSize / 2, (int) centerPos - sqrSize / 2, (int)centerPos+sqrSize, (int)centerPos+sqrSize, Color.RED.getRGB());
	        
	        for (int s = 0; s < SIDES; s++) {
	        	if(data.getStackInSlot(s).getItem() != Items.AIR) {
	        		double separationRadians = Math.toRadians(ANGLE_SEPARATION * s) + Math.toRadians(- rotationAngle - 90);
		        	int xPosRotation = (int) ((centerPos - sqrSize/2) + (WIDTH/4) * Math.cos(separationRadians));
		        	int yPosRotation = (int) ((centerPos - sqrSize/2) + (WIDTH/4) * Math.sin(separationRadians));
		        	
		        	int[] color = {Color.RED.getRGB(), Color.GREEN.getRGB(), Color.BLUE.getRGB()};

		        	//how to ARGB with bitshifts
		        	//int color = 0;
		        	//color |= 255 << 24;
		        	//color |= 255 << 16;
		        	//color |= 255 << 8;
		        	//color |= 255;
		        		        	
		        	graphics.fill(xPosRotation, yPosRotation, xPosRotation+sqrSize, yPosRotation+sqrSize, color[s]);
	        	}	        	
	        }
	        
			PoseStack poseStack = graphics.pose();
	        poseStack.pushPose();
	        poseStack.translate(xPos + WIDTH / 2f, yPos + HEIGHT / 2f, 0);
	        
	        poseStack.mulPose(Axis.ZP.rotationDegrees(- rotationAngle));
	        
	        //TODO keep a constant overlay scaling
			graphics.blit(TEXTURE, - WIDTH / 2, - HEIGHT / 2, 0, 0, WIDTH, HEIGHT, WIDTH, HEIGHT);
				
        	poseStack.popPose();
        	
		}
	}
}
