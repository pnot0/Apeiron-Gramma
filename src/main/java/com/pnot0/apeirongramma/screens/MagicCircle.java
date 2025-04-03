package com.pnot0.apeirongramma.screens;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import com.pnot0.apeirongramma.ApeironGramma;

import net.minecraft.client.DeltaTracker;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;

public class MagicCircle extends AbstractBaseOverlay implements PresetObserver{
	public static final MagicCircle INSTANCE = new MagicCircle();
    protected ResourceLocation TEXTURE;
    protected int guiSize = 0;
    protected int amountOfEdges = 0;
    protected int anglePerEdge = 0;
    
    protected float rotationAngle = 0;
    protected int rotationClamp = 0;
    public boolean overlayAdvance = false;
    public boolean overlayReturn = false;

    @Override
    public void render(GuiGraphics guiGraphics, DeltaTracker deltaTracker) {
        if (!overlayVisible) return;
    	MagicCircleCurrentPresets.INSTANCE.addObserver(this);
        int width = (guiSize / (int) minecraft.getWindow().getGuiScale()) - minecraft.getWindow().getGuiScaledWidth();
        int height = width;
        float xPos = (float) (width / -1.7);
        float yPos = xPos;
        if (TEXTURE == null) return;
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

	@Override
	public void update(String newTextureLocation, int newGuiSize, int newEdges) {
		this.TEXTURE = ApeironGramma.prefix(newTextureLocation);
		this.guiSize = newGuiSize;
		this.amountOfEdges = newEdges;
		this.anglePerEdge = 360/newEdges;
		this.rotationAngle = 0;
		this.rotationClamp = 0;
	}
}