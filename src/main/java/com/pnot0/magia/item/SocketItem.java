 package com.pnot0.magia.item;

import java.util.UUID;

import org.jetbrains.annotations.Nullable;

import com.mojang.logging.LogUtils;
import com.pnot0.magia.gui.SocketContainer;
import com.pnot0.magia.inventory.SocketData;
import com.pnot0.magia.inventory.SocketManager;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.SimpleMenuProvider;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.network.NetworkHooks;

public class SocketItem extends Item{
	
	public SocketItem(Properties properties) {
		super(properties);
	}

	public static SocketData getData(ItemStack itemStack) {
		if(!(itemStack.getItem() instanceof SocketItem)) 
			return null;
		UUID uuid;
		CompoundTag tag = itemStack.getOrCreateTag();
		if(!tag.contains("UUID")) {
			uuid = UUID.randomUUID();
			tag.putUUID("UUID", uuid);
		}else {
			uuid = tag.getUUID("UUID");
		}
		return SocketManager.get().getOrCreateSocket(uuid);
	}
	
	@Override
	public @Nullable ICapabilityProvider initCapabilities(ItemStack itemStack, @Nullable CompoundTag nbt) {
		getData(itemStack);
		return super.initCapabilities(itemStack, nbt);
	}
	
	@Override
	public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
		ItemStack itemStack = player.getItemInHand(hand);
		if(!level.isClientSide() && itemStack.getItem() instanceof SocketItem) {
			SocketData data = SocketItem.getData(itemStack);
			
			LogUtils.getLogger().info("how many slots: " +data.getSlots());
			LogUtils.getLogger().info("itemstack at 1st slot to string: " +data.getStackInSlot(0).toString());
			LogUtils.getLogger().info("item at 1st slot to string: " +data.getStackInSlot(0).getItem().toString());
			
			UUID uuid = data.getUUID();
						
			NetworkHooks.openScreen(
					((ServerPlayer) player), 
					new SimpleMenuProvider((windowId, playerInventory, playerEntity) -> 
					new SocketContainer(windowId, playerInventory, uuid, data.getHandler()), 
					itemStack.getHoverName()), (buffer -> buffer.writeUUID(uuid)));
		}
		
		return InteractionResultHolder.consume(player.getItemInHand(hand));

	}	

}
