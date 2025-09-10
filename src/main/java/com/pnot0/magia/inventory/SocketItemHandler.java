package com.pnot0.magia.inventory;

import net.minecraftforge.items.ItemStackHandler;

public class SocketItemHandler extends ItemStackHandler{
	public SocketItemHandler(int size) {
		super(size);
	}
	
	@Override
	protected void onContentsChanged(int slot) {
		SocketManager.get().setDirty();
	}
}
