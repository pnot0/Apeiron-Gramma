package com.example.examplemod.screens;

import com.example.examplemod.ExampleMod;
import com.mojang.blaze3d.vertex.PoseStack;

import net.minecraft.client.DeltaTracker;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;

public class EmptySocket extends AbstractBaseOverlay {
	public static final EmptySocket INSTANCE = new EmptySocket();
    private static final ResourceLocation TEXTURE = ExampleMod.prefix("textures/gui/empty_socket.png");
    
    @Override
    public void render(GuiGraphics guiGraphics, DeltaTracker deltaTracker) {
        if (!overlayVisible) return;
        
        int width = minecraft.getWindow().getGuiScaledWidth();
        int height = width + minecraft.getWindow().getScreenHeight();
        float xPos = (float) (width / -2.5f);
        float yPos = xPos;
        
        renderTexture(guiGraphics, TEXTURE, xPos, yPos, width, height, 0xFFFFFFFF);
    }

    public void renderTexture(GuiGraphics guiGraphics, ResourceLocation texture, float x, float y, int width, int height, int color) {
        PoseStack poseStack = guiGraphics.pose();
        poseStack.pushPose();
        poseStack.translate(x + width / 2f, y + height / 2f, 0);
        guiGraphics.blit(
            RenderType::guiTextured, texture,
            -width / 2,
            -height / 2,
            0, 0,
            width, height,
            width, height, color
        );
        poseStack.popPose();
    }
}
