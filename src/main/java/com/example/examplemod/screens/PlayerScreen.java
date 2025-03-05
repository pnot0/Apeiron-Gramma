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
		int offsetLeft = 10;
		int yOffset = minecraft.getWindow().getGuiScaledHeight() - 250;
		renderRotatedTexture(guiGraphics, TEXTURE, offsetLeft, yOffset, rotationAngle, 128, 128);
	}
	
	private static void renderRotatedTexture(GuiGraphics guiGraphics, ResourceLocation texture, int x, int y, float rotationDegrees, int width, int height) {
		PoseStack poseStack = guiGraphics.pose();
	    
	    poseStack.pushPose();
	    // 1. Move pivot point to center of texture
	    poseStack.translate(
	        x + width / 2f, 
	        y + height / 2f, 
	        0
	    );
	    
	    // 2. Apply rotation (convert degrees to radians)
	    poseStack.mulPose(Axis.ZP.rotationDegrees(rotationDegrees));
	    
	    // 3. Render texture with offset to account for pivot
	    guiGraphics.blit(
	        RenderType::guiTextured, texture,
	        -width / 2,  // Adjusted X
	        -height / 2, // Adjusted Y
	        0, 0,        // Texture UV start
	        width, height,
	        width, height // Texture dimensions
	    );
	    
	    poseStack.popPose();
	}
}
