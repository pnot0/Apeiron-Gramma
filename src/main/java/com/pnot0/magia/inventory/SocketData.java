package com.pnot0.magia.inventory;

import java.util.Optional;
import java.util.UUID;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.IItemHandler;

public class SocketData {
	private final UUID uuid;
	private final SocketItemHandler inventory;
    private final LazyOptional<IItemHandler> optional;
	
    public LazyOptional<IItemHandler> getOptional() {return this.optional;}

    public IItemHandler getHandler() {return this.inventory;}
    
    public SocketData(UUID uuid) {
    	this.uuid = uuid;
    	this.inventory = new SocketItemHandler(3);
    	this.optional = LazyOptional.of(() -> this.inventory);
    }
    
    public SocketData(UUID uuid, CompoundTag tag) {
    	this.uuid = uuid;
    	this.inventory = new SocketItemHandler(3);
    	if(tag.getCompound("Inventory").contains("Size")) {
    		if(tag.getCompound("Inventory").getInt("Size") != 3)
    			tag.getCompound("Inventory").putInt("Size", 3);
    	}
    	this.inventory.deserializeNBT(tag.getCompound("Inventory"));
		this.optional = LazyOptional.of(()-> this.inventory);
    }
    
    public ItemStack getStackInSlot(int slot) {
    	return this.inventory.getStackInSlot(slot);
    }
    
    public int getSlots() {
    	return this.inventory.getSlots();
    }
    
    public UUID	getUUID() {
    	return this.uuid;
    }
    
    public static Optional<SocketData> fromNBT(CompoundTag tag){
    	if(tag.contains("UUID")) {
    		UUID uuid = tag.getUUID("UUID");
    		return Optional.of(new SocketData(uuid, tag));
    	}
    	return Optional.empty();
    }
    
    public CompoundTag toNBT() {
    	CompoundTag tag = new CompoundTag();
    	
    	tag.putUUID("UUID", this.uuid);
    	tag.put("Inventory", this.inventory.serializeNBT());
    	
    	return tag;
    }
}
