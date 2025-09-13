package com.pnot0.magia.item;

import java.util.UUID;

import org.openjdk.nashorn.internal.runtime.options.LoggingOption.LoggerInfo;

import com.mojang.logging.LogUtils;
import com.pnot0.magia.Magia;
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
		LogUtils.getLogger().info("attempt getOrCreateSocket");
		return SocketManager.get().getOrCreateSocket(uuid);
	}
	
	@Override
	public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
		LogUtils.getLogger().info("attempt to use item");

		ItemStack itemStack = player.getItemInHand(hand);
		if(!level.isClientSide() && itemStack.getItem() instanceof SocketItem) {
			SocketData data = SocketItem.getData(itemStack);
			
			UUID uuid = data.getUUID();
			
			LogUtils.getLogger().info("attempt to open screen");
			NetworkHooks.openScreen(
					((ServerPlayer) player), 
					new SimpleMenuProvider((windowId, playerInventory, playerEntity) -> 
					new SocketContainer(windowId, playerInventory, uuid, data.getHandler()), 
					itemStack.getHoverName()), (buffer -> buffer.writeUUID(uuid)));
		}
		
		return InteractionResultHolder.consume(player.getItemInHand(hand));

	}	

}
