package com.example.examplemod.screens;

import com.example.examplemod.ExampleMod;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.DeltaTracker;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;

public class MagicCircle extends AbstractBaseOverlay {
	public static final MagicCircle INSTANCE = new MagicCircle();
    protected final ResourceLocation TEXTURE = ExampleMod.prefix(MagicCircleCurrentPresets.textureLocation);
    protected final int guiSize = MagicCircleCurrentPresets.guiSize;
    protected final int amountOfEdges = MagicCircleCurrentPresets.edges;
    protected final int anglePerEdge = 360 / amountOfEdges;
    
    protected float rotationAngle = 0;
    protected int rotationClamp = 0;
    public boolean overlayAdvance = false;
    public boolean overlayReturn = false;

    @Override
    public void render(GuiGraphics guiGraphics, DeltaTracker deltaTracker) {
        if (!overlayVisible) return;

        int width = (guiSize / (int) minecraft.getWindow().getGuiScale()) - minecraft.getWindow().getGuiScaledWidth();
        int height = width;
        float xPos = (float) (width / -1.7);
        float yPos = xPos;
    	
        updateRotation(deltaTracker);
        renderRotatedTexture(guiGraphics, TEXTURE, xPos, yPos, width, height, 0x8FFFFFFF);
    }

    protected void updateRotation(DeltaTracker deltaTracker) {
        if (overlayAdvance) {
            if (rotationAngle <= rotationClamp + anglePerEdge) {
                rotationAngle += deltaTracker.getGameTimeDeltaTicks() * 20f;
            } else {
                overlayAdvance = false;
                rotationClamp += anglePerEdge;
            }
        }
        if (overlayReturn) {
            if (rotationAngle >= rotationClamp - anglePerEdge) {
                rotationAngle -= deltaTracker.getGameTimeDeltaTicks() * 20f;
            } else {
                overlayReturn = false;
                rotationClamp -= anglePerEdge;
            }
        }
    }

    protected void renderRotatedTexture(GuiGraphics guiGraphics, ResourceLocation texture, float x, float y, int width, int height, int color) {

        PoseStack poseStack = guiGraphics.pose();
        poseStack.pushPose();
        poseStack.translate(x + width / 2f, y + height / 2f, 0);
        poseStack.mulPose(Axis.ZP.rotationDegrees(rotationAngle));
        //for some reason blit is crashing the game
        guiGraphics.blit(
            RenderType::guiTextured, texture,
            -width / 2,
            -height / 2,
            0, 0,
            width, height,
            width, height,
            color
        );
        poseStack.popPose();
    }
}