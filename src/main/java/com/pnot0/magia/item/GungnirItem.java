package com.pnot0.magia.item;

import com.pnot0.magia.network.ClientMessage;
import com.pnot0.magia.network.MagiaNetwork;
import com.pnot0.magia.spells.MagicExplosion;

import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
public class GungnirItem extends Item{

	public GungnirItem() {
		super(new Item.Properties().stacksTo(1).fireResistant());
	}
	
	@Override
	public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
		//either a range calculate by max server render distance or one defined in config
		float range = 128;
		float explosionRadius = 8f;
		
		BlockPos pos = MagicExplosion.findBlockPos(level, player, range);
		
		//good for regular explosion values: 0.6f, 0.6f, 0.3f, 0.3f
		//define in config a blacklist of blocks that a strong explosion will not destroy, ex: bedrock, end portal, etc...
		if(!level.isClientSide) {
			MagicExplosion magicExp = new MagicExplosion(level, null, null, pos.getX(), pos.getY(), pos.getZ(), explosionRadius);
			magicExp.blockExplosion(0.7f, 0.8f, 0.08f, 0.8f, true);
			magicExp.entityExplosion(player, 1f, 6f);
			//TODO shaders for explosion
			MagiaNetwork.CHANNEL.sendToServer(new ClientMessage("exploded"));
		}
		
		return InteractionResultHolder.consume(player.getItemInHand(hand));
	}
	
	
}
