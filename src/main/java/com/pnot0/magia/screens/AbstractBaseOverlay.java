package com.pnot0.magia.screens;

import net.minecraft.client.DeltaTracker;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.resources.ResourceLocation;

public abstract class AbstractBaseOverlay {
    protected static final Minecraft minecraft = Minecraft.getInstance();
    public boolean overlayVisible = false;
    
    public abstract void render(GuiGraphics guiGraphics, DeltaTracker deltaTracker);
}