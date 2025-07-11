package com.pnot0.magia.item;

import com.pnot0.magia.screens.MagicCircle;

import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;

public class GuiReturnItem extends Item{

	public GuiReturnItem(Properties properties) {
		super(properties);
	}

	@Override
	public InteractionResult use(Level level, Player player, InteractionHand hand) {
		if (level.isClientSide) {
			if(!MagicCircle.INSTANCE.overlayAdvance) {
				MagicCircle.INSTANCE.overlayReturn = true;
			}
        }
        return InteractionResult.SUCCESS;
	}
}
