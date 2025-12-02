package com.pnot0.magia.item;

import java.util.List;

import com.mojang.logging.LogUtils;
import com.pnot0.magia.spells.MagicExplosion;

import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageSources;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.Explosion.BlockInteraction;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.Level.ExplosionInteraction;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
public class GungnirItem extends Item{

	public GungnirItem() {
		super(new Item.Properties().stacksTo(1).fireResistant());
	}
	
	@Override
	public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
		float range = 32;
		
		Vec3 look = player.getLookAngle();
		Vec3 start = new Vec3(player.getX(), player.getY() + player.getEyeHeight(), player.getZ());
		Vec3 end = new Vec3(player.getX() + look.x * range, player.getY() + player.getEyeHeight() + look.y * range, player.getZ() + look.z * range);
		
		ClipContext ctx = new ClipContext(start, end, ClipContext.Block.COLLIDER, ClipContext.Fluid.NONE, player);
		BlockHitResult hitResult = level.clip(ctx);
		BlockPos pos = hitResult.getBlockPos();
		
		//good for regular explosion values: 0.6f, 0.6f, 0.3f, 0.3f
		if(!level.isClientSide) {
			float explosionRadius = 8f;
			MagicExplosion magicExp = new MagicExplosion(level, null, null, pos.getX(), pos.getY(), pos.getZ(), explosionRadius);
			magicExp.blockExplosion(0.7f, 0.8f, 0.08f, 0.8f, true);
			magicExp.entityExplosion(0.04f, 8f);
			//TODO shaders for explosion
		}
		
		return InteractionResultHolder.consume(player.getItemInHand(hand));
	}
}
