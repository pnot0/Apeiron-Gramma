package com.pnot0.magia.inventory;

import java.util.Optional;
import java.util.UUID;

import com.pnot0.magia.item.SocketsEnum;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.IItemHandler;

public class SocketData {
	private final UUID uuid;
	private SocketsEnum socketTier;
	private final SocketItemHandler inventory;
    private final LazyOptional<IItemHandler> optional;
	
    public LazyOptional<IItemHandler> getOptional() {return this.optional;}

    public IItemHandler getHandler() {return this.inventory;}
    
    public SocketData(UUID uuid, SocketsEnum socketTier) {
    	this.uuid = uuid;
    	this.socketTier = socketTier;
    	
    	this.inventory = new SocketItemHandler(socketTier.slots);
    	this.optional = LazyOptional.of(() -> this.inventory);
    }
    
    public SocketData(UUID uuid, CompoundTag tag) {
    	this.uuid = uuid;
    	this.socketTier = SocketsEnum.values()[Math.min(tag.getInt("SocketTier"), SocketsEnum.PENTAGRAM.ordinal())];
    	
    	this.inventory = new SocketItemHandler(socketTier.slots);
    	
    	if(tag.getCompound("Inventory").contains("Size")) {
    		if(tag.getCompound("Inventory").getInt("Size") != socketTier.slots)
    			tag.getCompound("Inventory").putInt("Size", socketTier.slots);
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
    
    public SocketsEnum getSocketTier() {
    	return this.socketTier;
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
    	tag.putInt("SocketTier", this.socketTier.ordinal());
    	tag.put("Inventory", this.inventory.serializeNBT());
    	
    	return tag;
    }
}
