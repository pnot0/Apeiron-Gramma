package com.example.examplemod.item;

import com.example.examplemod.screens.PlayerScreen;

import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;

public class GuiSummonItem extends Item{

	public GuiSummonItem(Properties properties) {
		super(properties);
	}
	
	@Override
	public InteractionResult use(Level level, Player player, InteractionHand hand) {
		if (level.isClientSide) {
            PlayerScreen.overlayVisible = !PlayerScreen.overlayVisible;
        }
        return InteractionResult.SUCCESS;
	}
}
