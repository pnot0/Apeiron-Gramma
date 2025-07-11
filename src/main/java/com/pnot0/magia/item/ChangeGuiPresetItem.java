package com.pnot0.magia.item;

import com.pnot0.magia.screens.MagicCircleCurrentPresets;

import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;

public class ChangeGuiPresetItem extends Item{

	private static boolean changePresetKey = false;
	
	public ChangeGuiPresetItem(Properties properties) {
		super(properties);
	}
	
	@Override
	public InteractionResult use(Level level, Player player, InteractionHand hand) {
		if (level.isClientSide) {
			changePresetKey = !changePresetKey;
			if(changePresetKey) {
				MagicCircleCurrentPresets.INSTANCE.setPreset("textures/gui/3sided_circle.png", 512, 3);
			}else {
				MagicCircleCurrentPresets.INSTANCE.setPreset("textures/gui/5sided_circle.png", 512, 5);
			}
        }
        return InteractionResult.SUCCESS;
	}
}
