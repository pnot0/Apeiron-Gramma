package com.pnot0.magia;

import javax.annotation.Nullable;

import com.pnot0.magia.menus.EyeContainerMenu;

import net.minecraft.core.component.DataComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.ItemContainerContents;
import net.neoforged.neoforge.items.ItemStackHandler;

public class EyeContainer extends ItemStackHandler implements MenuProvider{

	private final ItemStack stack;
	
	public EyeContainer(ItemStack stack) {
		super(3);
		this.stack = stack;
		
		ItemContainerContents contents = stack.getOrDefault(DataComponents.CONTAINER, ItemContainerContents.EMPTY);
		contents.copyInto(this.stacks);
	}
	
	@Override
	public int getStackLimit(int slot, ItemStack stack) {
		return 1;
	};
	
	@Override
	public void onContentsChanged(int slot) {
		super.onContentsChanged(slot);
		this.stack.set(DataComponents.CONTAINER, ItemContainerContents.fromItems(this.stacks));
	}

	@Nullable
	@Override
	public AbstractContainerMenu createMenu(int containerId, Inventory playerInventory, Player player) {
		return new EyeContainerMenu(containerId, playerInventory);
	}

	@Override
	public Component getDisplayName() {
		// TODO Auto-generated method stub
		return null;
	}
	
}
