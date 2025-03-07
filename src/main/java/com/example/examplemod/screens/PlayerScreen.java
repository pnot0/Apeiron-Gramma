package com.example.examplemod.screens;

import com.example.examplemod.ExampleMod;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;

import net.minecraft.client.DeltaTracker;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.LayeredDraw;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;

public class PlayerScreen{
	public static boolean overlayVisible = false;
	public static final LayeredDraw.Layer OVERLAY_LAYER = PlayerScreen::renderOverlay;
	private static final ResourceLocation TEXTURE = ExampleMod.prefix("textures/gui/example_container.png");
	private static final Minecraft minecraft = Minecraft.getInstance();
	private static float rotationAngle = 0;
	
	public static void renderOverlay(GuiGraphics guiGraphics, DeltaTracker deltaTracker) {
		if (!overlayVisible) return;
		rotationAngle += deltaTracker.getGameTimeDeltaTicks() * 3;
		int offsetLeft = -80;
		int yOffset = minecraft.getWindow().getGuiScaledHeight() - 380;
		renderRotatedTexture(guiGraphics, TEXTURE, offsetLeft, yOffset, rotationAngle, 256, 256);
	}
	
	private static void renderRotatedTexture(GuiGraphics guiGraphics, ResourceLocation texture, int x, int y, float rotationDegrees, int width, int height) {
		PoseStack poseStack = guiGraphics.pose();
	    
	    poseStack.pushPose();
	    poseStack.translate(
	        x + width / 2f, 
	        y + height / 2f, 
	        0
	    );
	    
	    poseStack.mulPose(Axis.ZP.rotationDegrees(rotationDegrees));
	    
	    guiGraphics.blit(
	        RenderType::guiTextured, texture,
	        -width / 2,
	        -height / 2,
	        0, 0,
	        width, height,
	        width, height, 0xFF00F1F1
	        
	    );
	    
	    poseStack.popPose();
	}
}
