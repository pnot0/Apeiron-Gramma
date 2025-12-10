package com.pnot0.magia.item;

import com.pnot0.magia.spells.MagicExplosion;

import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.ProjectileUtil;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
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
		if(!level.isClientSide) {
			MagicExplosion magicExp = new MagicExplosion(level, null, null, pos.getX(), pos.getY(), pos.getZ(), explosionRadius);
			magicExp.blockExplosion(0.7f, 0.8f, 0.08f, 0.8f, true);
			magicExp.entityExplosion(0f, false, 8f);
			//TODO shaders for explosion
		}
		
		return InteractionResultHolder.consume(player.getItemInHand(hand));
	}
	
	
}
