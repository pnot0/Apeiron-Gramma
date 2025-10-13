package com.pnot0.magia.inventory;

import org.jetbrains.annotations.NotNull;

import com.pnot0.magia.item.SpellSchoolItem;

import net.minecraft.world.item.ItemStack;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;

public class SocketContainerSlot extends SlotItemHandler{

	public SocketContainerSlot(IItemHandler itemHandler, int index, int xPosition, int yPosition) {
		super(itemHandler, index, xPosition, yPosition);
	}
	
	@Override
	public boolean mayPlace(@NotNull ItemStack stack) {
		if(stack.getItem() instanceof SpellSchoolItem)
			return super.mayPlace(stack);
		else
			return false;
	}

}
