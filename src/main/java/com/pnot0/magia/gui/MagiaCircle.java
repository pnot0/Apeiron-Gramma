package com.pnot0.magia.gui;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import com.pnot0.magia.Magia;
import com.pnot0.magia.data.SpellSchoolData;
import com.pnot0.magia.inventory.SocketData;
import com.pnot0.magia.item.SocketItem;
import com.pnot0.magia.item.SpellSchoolItem;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Items;

public class MagiaCircle{
	private final Minecraft minecraft = Minecraft.getInstance();
	
	private GuiGraphics graphics;
	private int screenWidth;
	private int screenHeight;
	
	private ResourceLocation TEXTURE;
	private int textureWidth;
	private int textureHeight;
	
	private int sides;
	private int angleSeparation;
	
	private boolean overlayAdvance = false;
	private boolean overlayReturn = false;
	
	private int rotationAngle = 0;
	private int rotationClamp = 0;
	
	public void advanceSelection() {
		this.overlayAdvance = true;
		this.rotationClamp = (rotationClamp + angleSeparation) % 360;
	}
	
	public void returnSelection() {
		this.overlayReturn = true;
		this.rotationClamp = treatAngle(rotationClamp - angleSeparation);
	}
	
	private int angleDistance(int curAngle, int targetAngle) {
		int dif = Math.abs(curAngle - targetAngle);
		if(dif > 180) return 360 - dif;
		else return dif;
	}
	
	private int treatAngle(int angle) {
		if(angle<0) return 360 - Math.abs(angle);
		else return angle;
	}
	
	private void rotateSelection() {
		int increment = 360 / 36;
		if (overlayAdvance) {
			if(angleDistance((int) rotationAngle, rotationClamp) == 0) {
				overlayAdvance = false;
			}else {
				rotationAngle = (rotationAngle + increment) % 360;
			}
		}
		if(overlayReturn) {
			if(angleDistance((int) rotationAngle, rotationClamp) == 0) {
				overlayReturn = false;
			}else {
				rotationAngle = treatAngle(rotationAngle - increment);
			}
		}
	}
	
	public MagiaCircle(
			String textureLocation, int textureWidth, int textureHeight,
			int sides
		) {
		this.TEXTURE = ResourceLocation.fromNamespaceAndPath(Magia.MODID, textureLocation);
		this.textureWidth = textureWidth;
		this.textureHeight = textureHeight;
		this.sides = sides;
		this.angleSeparation = 360 / sides;
	}
	
	public void render(GuiGraphics graphics, int screenWidth, int screenHeight) {
		//TODO holy shit doc this math im getting lost
		//TODO make a dedicated magiacircle logic class, instead of calling and putting everything in the render pipeline
		
		SocketData socketData = SocketItem.getData(minecraft.player.getMainHandItem());
		
        float xPos = (screenWidth / -1.7f) + (screenWidth / 2f);
        float yPos = xPos;
        
        float centerPos = ((float) xPos + (xPos + (float) textureWidth)) / 2f;
        rotateSelection();
        int textureSize = 32;
        
        for (int s = 0; s < sides; s++) {
        	if(socketData.getStackInSlot(s).getItem() != Items.AIR) {
        		SpellSchoolData spellSchoolData = SpellSchoolItem.getData(socketData.getStackInSlot(s));
        		
        		ResourceLocation spellSchoolTexture = 
        				ResourceLocation.fromNamespaceAndPath(Magia.MODID, spellSchoolData.getHandler().getTexturePath());
        		
        		// get the current angle interval, sum it to the negative rotationAngle rotated 90 degrees, convert all to radians
        		double separationRadians = Math.toRadians((angleSeparation * s) + (-rotationAngle - 90));
	        	
        		// first part: (centerPos - textureSize/2) is position of the points in cartesian plane
        		// second part: ((WIDTH+(textureSize/4f))/4f) + 2 is the radius of the circle/points
        		// third part: Math sin or cos (separationRadius) get the points in the unit circle using sine and cosine
        		
        		float xPosRotation = (float) ((centerPos - textureSize/2f) + ((textureWidth+4+(textureSize/4f))/4f) * Math.cos(separationRadians));
	        	float yPosRotation = (float) ((centerPos - textureSize/2f) + ((textureWidth+4+(textureSize/4f))/4f) * Math.sin(separationRadians));
	        	
	        	PoseStack poseStack = graphics.pose();
	        	poseStack.pushPose();
	        	poseStack.translate(xPosRotation, yPosRotation, 0);
	        	
	        	graphics.blit(spellSchoolTexture, 0, 0, 0, 0, 32, 32, 32, 32);
	        	
	        	poseStack.popPose();
	        	
        	}	        	
        }
        
		PoseStack poseStack = graphics.pose();
        poseStack.pushPose();
        poseStack.translate(xPos + textureWidth / 2f, yPos + textureHeight / 2f, 0);
        
        poseStack.mulPose(Axis.ZP.rotationDegrees(-rotationAngle));
        
        //TODO keep a constant overlay scaling
		graphics.blit(TEXTURE, - textureWidth / 2, - textureHeight / 2, 0, 0, textureWidth, textureHeight, textureWidth, textureHeight);
			
    	poseStack.popPose();
	}
	
}