package com.pnot0.magia.gui;

import java.util.UUID;

import com.pnot0.magia.Magia;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;

public class SocketContainer extends AbstractContainerMenu{

	private final IItemHandler handler;
	private final UUID uuid;
	
	public static SocketContainer fromNetwork(final int windowId, final Inventory inventory, FriendlyByteBuf data) {
		UUID uuid = data.readUUID();
		return new SocketContainer(windowId, inventory, uuid, new ItemStackHandler(3));
	}

	public SocketContainer(final int windowId, final Inventory inventory, UUID uuid, IItemHandler handler) {
		super(Magia.SOCKET_CONTAINER.get(), windowId);
		
		this.uuid = uuid;
		this.handler = handler;
		
		addPlayerSlots(inventory);
		addSocketSlots();
	}
	
	@Override
	public ItemStack quickMoveStack(Player pPlayer, int pIndex) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean stillValid(Player pPlayer) {
		// TODO Auto-generated method stub
		return false;
	}
	
	private void addPlayerSlots(Inventory inventory) {
		//TODO add player inventory
	}
	
	private void addSocketSlots() {
		//TODO add magia slots
	}

}
