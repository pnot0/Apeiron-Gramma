package com.pnot0.apeirongramma.item;

import com.pnot0.apeirongramma.EyeContainer;
import com.pnot0.apeirongramma.menus.EyeContainerMenu;

import net.minecraft.core.component.DataComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.SimpleMenuProvider;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuConstructor;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.ItemContainerContents;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.items.ItemStackHandler;
import net.minecraft.server.level.ServerPlayer;

public class EyeItem extends Item{
	private int slots;
	public final EyeContainer container = new EyeContainer(new ItemStack(this));
	/*
	public final ItemStackHandler inventory = new ItemStackHandler(slots) {
		@Override
		protected int getStackLimit(int slot, ItemStack stack) {
			return 1;
		};
		@Override
		protected void onContentsChanged(int slot) {
			super.onContentsChanged(slot);
		};
	};
	*/
	public EyeItem(Properties properties) {
		this(properties, 1);
	}
	
	public EyeItem(Properties properties, int slots) {
		super(properties);
		this.slots = slots;
	}
	
	@Override
	public InteractionResult use(Level level, Player player, InteractionHand hand) {
		// TODO Auto-generated method stub
		
		if(player.isCrouching() && !level.isClientSide()) {
			((ServerPlayer) player).openMenu(new SimpleMenuProvider((MenuConstructor) this, Component.literal("Eye Menu")));
		}
		return InteractionResult.SUCCESS;
	}
}
