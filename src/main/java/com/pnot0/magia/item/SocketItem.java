 package com.pnot0.magia.item;

import java.util.UUID;

import org.jetbrains.annotations.Nullable;

import com.pnot0.magia.data.SocketData;
import com.pnot0.magia.data.SocketManager;
import com.pnot0.magia.inventory.SocketContainer;

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
	
	public SocketItem(SocketsEnum socketTier) {
		super(new Item.Properties().stacksTo(1));
		this.socketTier = socketTier;
	}
	
	private final SocketsEnum socketTier;
	
	public static SocketsEnum getSocketTier(ItemStack itemStack) {
		if(!itemStack.isEmpty() && itemStack.getItem() instanceof SocketItem) return ((SocketItem) itemStack.getItem()).socketTier;
		else return SocketsEnum.TRIANGLE;
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
		return SocketManager.get().getOrCreateSocket(uuid, ((SocketItem) itemStack.getItem()).socketTier);
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
			
			//LogUtils.getLogger().info("how many slots: " +data.getSlots());
			//LogUtils.getLogger().info("itemstack at 1st slot to string: " +data.getStackInSlot(0).toString());
			//LogUtils.getLogger().info("item at 1st slot to string: " +data.getStackInSlot(0).getItem().toString());
			
			UUID uuid = data.getUUID();
						
			NetworkHooks.openScreen(
				((ServerPlayer) player), 
					new SimpleMenuProvider((windowId, playerInventory, playerEntity) -> 
						new SocketContainer(
								windowId,
								playerInventory,
								uuid,
								data.getSocketTier(),
								data.getHandler()
							), 
						itemStack.getHoverName()
					),
				(buffer -> buffer.writeUUID(uuid).writeInt(data.getSocketTier().ordinal()))
			);
		}
		
		return InteractionResultHolder.consume(player.getItemInHand(hand));

	}	

}
