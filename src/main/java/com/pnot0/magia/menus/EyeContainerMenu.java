package com.pnot0.magia.menus;

import com.pnot0.magia.EyeContainer;
import com.pnot0.magia.item.EyeItem;
import com.pnot0.magia.registry.ModMenuRegister;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.inventory.SimpleContainerData;
import net.minecraft.world.item.ItemStack;

public class EyeContainerMenu extends AbstractContainerMenu{
	
	public EyeContainerMenu(int containerId, Inventory inv) {
		this(containerId, inv, new SimpleContainerData(3));
	}
	
	public EyeContainerMenu(int containerId, Inventory inv, ContainerData dataMultiple) {
		super(ModMenuRegister.EYE_MENU.get(), containerId);
		checkContainerDataCount(dataMultiple, 3);
		this.addDataSlots(dataMultiple);
		
		
	}

	@Override
	public ItemStack quickMoveStack(Player player, int index) {
		return null;
	}

	@Override
	public boolean stillValid(Player player) {
		return false;
	}
	
}
