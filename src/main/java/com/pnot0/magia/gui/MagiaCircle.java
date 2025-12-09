package com.pnot0.magia.gui;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.logging.LogUtils;
import com.mojang.math.Axis;
import com.pnot0.magia.Magia;
import com.pnot0.magia.data.SocketData;
import com.pnot0.magia.data.SpellSchoolData;
import com.pnot0.magia.item.SocketItem;
import com.pnot0.magia.item.SpellSchoolItem;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.resources.ResourceLocation;

public class MagiaCircle{
	private final Minecraft minecraft = Minecraft.getInstance();
	
	private int angleSeparation = 0;
	private int increment = 0;
	private int rotationAngle = 0;
	private int rotationClamp = 0;
	private int sides = 0;
	
	private boolean overlayAdvance = false;
	private boolean overlayReturn = false;
	
	public void advanceSelection() {
		this.overlayReturn = false;
		this.overlayAdvance = true;
		
		this.rotationClamp = normalizeAngle(rotationClamp + angleSeparation);
	}
	
	public void returnSelection() {
		this.overlayReturn = true;
		this.overlayAdvance = false;
		
		this.rotationClamp = normalizeAngle(rotationClamp - angleSeparation);
	}
	
	private int angleDistance(int curAngle, int targetAngle) {
		int dif = Math.abs(curAngle - targetAngle);
		if(dif > 180) return 360 - dif; //max angle separation of 2 points in a circle
		else return dif;
	}
	
	private int normalizeAngle(int angle) {
		if(angle<0) return 360 - Math.abs(angle);
		else if(angle>360) return angle % 360;
		else return angle;
	}
	
	private void rotateSelection() {
		if(overlayAdvance || overlayReturn) {
			if(angleDistance(rotationAngle, rotationClamp) == 0) {
				overlayAdvance = false;
				overlayReturn = false;
			}else {
				if(overlayAdvance) rotationAngle = normalizeAngle(rotationAngle + increment);
				if(overlayReturn) rotationAngle = normalizeAngle(rotationAngle - increment);
			}
		}
	}
	
	public void renderOverlay(GuiGraphics graphics, int screenWidth, int screenHeight, ResourceLocation overlayTexture, int textureWidth, int sides, int increment) {		
		SocketData socketData = SocketItem.getData(minecraft.player.getMainHandItem());

		if (this.sides != 0 && this.sides != sides) {
			this.rotationAngle = 0;
			this.rotationClamp = 0;
		}
		
		this.sides = sides;
		this.angleSeparation = 360 / sides;
		this.increment = increment;
		
        float xPos = (screenWidth / -1.7f) + (screenWidth / 2f);
        float yPos = xPos;
        
        float centerPos = ((float) xPos + (xPos + (float) textureWidth)) / 2f;
        rotateSelection();
        int textureSize = 32;
        
        for (int s = 0; s < sides; s++) {
        	if(socketData.getStackInSlot(s).getItem() instanceof SpellSchoolItem) {
        		SpellSchoolData spellSchoolData = SpellSchoolItem.getData(socketData.getStackInSlot(s));
        		
        		ResourceLocation spellSchoolTexture = 
        				ResourceLocation.fromNamespaceAndPath(Magia.MODID, spellSchoolData.getHandler().getTexturePath());
        		
        		double separationRadians = Math.toRadians(((angleSeparation * s) + 90) - rotationAngle);
	        	
        		// first part: (centerPos - textureSize/2) is position of the points in cartesian plane
        		// second part: ((WIDTH+(textureSize/4f))/4f) + 2 is the radius of the circle/points
        		// third part: Math sin or cos (separationRadius) get the points in the unit circle using sine and cosine
        		
        		//i dont know why yPos has to be flipped, or xpos also, this formula needs some work
        		
        		float xPosRotation = (float) ((centerPos - textureSize/2f) + ((textureWidth+4+(textureSize/4f))/4f) * Math.cos(separationRadians));
	        	float yPosRotation = (float) ((centerPos - textureSize/2f) + ((textureWidth+4+(textureSize/4f))/4f) * Math.sin(separationRadians+Math.toRadians(180)));
	        	
	        	PoseStack poseStack = graphics.pose();
	        	poseStack.pushPose();
	        	poseStack.translate(xPosRotation, yPosRotation, 0);
	        	
	        	graphics.blit(spellSchoolTexture, 0, 0, 0, 0, 32, 32, 32, 32);
	        	
	        	poseStack.popPose();
	        	
        	}	        	
        }
        
		PoseStack poseStack = graphics.pose();
        poseStack.pushPose();
        poseStack.translate(xPos + textureWidth / 2f, yPos + textureWidth / 2f, 0);
        
        poseStack.mulPose(Axis.ZP.rotationDegrees(rotationAngle));
        
        //TODO keep a constant overlay scaling
		graphics.blit(overlayTexture, - textureWidth / 2, - textureWidth / 2, 0, 0, textureWidth, textureWidth, textureWidth, textureWidth);
			
    	poseStack.popPose();
	}
	
}