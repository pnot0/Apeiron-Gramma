package com.pnot0.magia.gui;

import java.util.UUID;

import com.mojang.logging.LogUtils;
import com.pnot0.magia.Magia;
import com.pnot0.magia.inventory.SocketContainerSlot;
import com.pnot0.magia.item.ItemRegistry;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ClickType;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import net.minecraftforge.items.SlotItemHandler;

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
		
		this.addSlot(new SocketContainerSlot(this.handler, 0, 65, 36));
		this.addSlot(new SocketContainerSlot(this.handler, 1, 80, 10));
		this.addSlot(new SocketContainerSlot(this.handler, 2, 95, 36));
	}
	
    // CREDIT GOES TO: diesieben07 | https://github.com/diesieben07/SevenCommons
    // must assign a slot number to each of the slots used by the GUI.
    // For this container, we can see both the tile inventory's slots as well as the player inventory slots and the hotbar.
    // Each time we add a Slot to the container, it automatically increases the slotIndex, which means
    //  0 - 8 = hotbar slots (which will map to the InventoryPlayer slot numbers 0 - 8)
    //  9 - 35 = player inventory slots (which map to the InventoryPlayer slot numbers 9 - 35)
    //  36 - 44 = TileInventory slots, which map to our TileEntity slot numbers 0 - 8)
    private static final int HOTBAR_SLOT_COUNT = 9;
    private static final int PLAYER_INVENTORY_ROW_COUNT = 3;
    private static final int PLAYER_INVENTORY_COLUMN_COUNT = 9;
    private static final int PLAYER_INVENTORY_SLOT_COUNT = PLAYER_INVENTORY_COLUMN_COUNT * PLAYER_INVENTORY_ROW_COUNT;
    private static final int VANILLA_SLOT_COUNT = HOTBAR_SLOT_COUNT + PLAYER_INVENTORY_SLOT_COUNT;
    private static final int VANILLA_FIRST_SLOT_INDEX = 0;
    private static final int TE_INVENTORY_FIRST_SLOT_INDEX = VANILLA_FIRST_SLOT_INDEX + VANILLA_SLOT_COUNT;

    // THIS YOU HAVE TO DEFINE!
    private static final int TE_INVENTORY_SLOT_COUNT = 3;  // must be the number of slots you have!
    @Override
    public ItemStack quickMoveStack(Player playerIn, int pIndex) {
        Slot sourceSlot = slots.get(pIndex);
        if (sourceSlot == null || !sourceSlot.hasItem()) return ItemStack.EMPTY;  //EMPTY_ITEM
        ItemStack sourceStack = sourceSlot.getItem();
        ItemStack copyOfSourceStack = sourceStack.copy();

        // Check if the slot clicked is one of the vanilla container slots
        if (pIndex < VANILLA_FIRST_SLOT_INDEX + VANILLA_SLOT_COUNT) {
            // This is a vanilla container slot so merge the stack into the tile inventory
            if (!moveItemStackTo(sourceStack, TE_INVENTORY_FIRST_SLOT_INDEX, TE_INVENTORY_FIRST_SLOT_INDEX
                    + TE_INVENTORY_SLOT_COUNT, false)) {
                return ItemStack.EMPTY;  // EMPTY_ITEM
            }
        } else if (pIndex < TE_INVENTORY_FIRST_SLOT_INDEX + TE_INVENTORY_SLOT_COUNT) {
            // This is a TE slot so merge the stack into the players inventory
            if (!moveItemStackTo(sourceStack, VANILLA_FIRST_SLOT_INDEX, VANILLA_FIRST_SLOT_INDEX + VANILLA_SLOT_COUNT, false)) {
                return ItemStack.EMPTY;
            }
        } else {
            System.out.println("Invalid slotIndex:" + pIndex);
            return ItemStack.EMPTY;
        }
        // If stack size == 0 (the entire stack was moved) set slot contents to null
        if (sourceStack.getCount() == 0) {
            sourceSlot.set(ItemStack.EMPTY);
        } else {
            sourceSlot.setChanged();
        }
        sourceSlot.onTake(playerIn, sourceStack);
        return copyOfSourceStack;
    }

	@Override
	public boolean stillValid(Player pPlayer) {
		return true;
	}
	
	@Override
	public void clicked(int slot, int dragType, ClickType clickType, Player player) {
		/*
		if(slot>=0) {
			LogUtils.getLogger().info("slot: " + Integer.toString(slot));
			LogUtils.getLogger().info(getSlot(slot).getItem().toString());
			LogUtils.getLogger().info("click type: " + ClickType.SWAP.toString());
		}
		
		*/
		
		//Prevent moving socket item
		if(slot >= 0 && getSlot(slot).getItem() == player.getMainHandItem())
			return;
		
		if (clickType == ClickType.SWAP)
            return;
		
        if (slot >= 0) {
        	getSlot(slot).container.setChanged();
        }
        
        super.clicked(slot, dragType, clickType, player);
        }
	
	private void addPlayerSlots(Inventory playerInventory) {
		for (int i = 0; i < 3; ++i) {
            for (int l = 0; l < 9; ++l) {
                this.addSlot(new Slot(playerInventory, l + i * 9 + 9, 8 + (l * 18), 68 + (i * 18)));
            }
        }

        for (int k = 0; k < 9; ++k) {
            this.addSlot(new Slot(playerInventory, k, 8 + (k * 18), 126));
        }
	}

}
