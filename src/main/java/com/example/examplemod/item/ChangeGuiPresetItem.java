package com.example.examplemod.item;

import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;

public class ChangeGuiPresetItem extends Item{

	private static int changePresetKey = 0;
	
	public ChangeGuiPresetItem(Properties properties) {
		super(properties);
	}
	
	@Override
	public InteractionResult use(Level level, Player player, InteractionHand hand) {
		if (level.isClientSide) {
			changePresetKey = (changePresetKey + 1) % 2;

        }
        return InteractionResult.SUCCESS;
	}
}
