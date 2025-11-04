package com.pnot0.magia.player;

import com.pnot0.magia.Magia;
import com.pnot0.magia.gui.MagiaCircle;
import com.pnot0.magia.gui.MagiaOverlay;

import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
import net.minecraft.core.NonNullList;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = Magia.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE, value = Dist.CLIENT)
public class ClientInput {
	
    @SubscribeEvent
    public static void onKeyInput(InputEvent.Key event) {
        if(Minecraft.getInstance().screen == null) {
        	if(KeyMaps.ADVANCE_SS_KEY.isDown() && !KeyMaps.RETURN_SS_KEY.isDown()) {
            	//Magia.LOGGER.info("logging key press: " + KeyMaps.ADVANCE_SS_KEY.getName());
            	//float rotationAngle = MagiaCircle.instance.getAngle();
            	//testing rotation
            	MagiaOverlay.instance.advanceSelection();
            }
            if(KeyMaps.RETURN_SS_KEY.isDown() && !KeyMaps.ADVANCE_SS_KEY.isDown()) {
            	MagiaOverlay.instance.returnSelection();
            }
        }

    }
}
