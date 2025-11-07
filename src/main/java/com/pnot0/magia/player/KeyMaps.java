package com.pnot0.magia.player;

import com.mojang.blaze3d.platform.InputConstants;
import com.pnot0.magia.Magia;
import net.minecraft.client.KeyMapping;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RegisterKeyMappingsEvent;
import net.minecraftforge.client.settings.KeyConflictContext;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = Magia.MODID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class KeyMaps {
	
	public static final KeyMapping ADVANCE_SS_KEY = new KeyMapping("key.magia.advance_spellschool.desc", KeyConflictContext.IN_GAME, InputConstants.Type.KEYSYM, InputConstants.KEY_Z, "key.magia.category");
	public static final KeyMapping RETURN_SS_KEY = new KeyMapping("key.magia.return_spellschool.desc", KeyConflictContext.IN_GAME, InputConstants.Type.KEYSYM, InputConstants.KEY_X, "key.magia.category");
	
	@SubscribeEvent
    public static void registerKeyMaps(RegisterKeyMappingsEvent event) {
		Magia.LOGGER.info("KeyMaps.registerKeyMaps");
		event.register(ADVANCE_SS_KEY);
		event.register(RETURN_SS_KEY);
    }
}
