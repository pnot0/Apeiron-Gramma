package com.pnot0.magia.spells;

import java.util.List;
import java.util.Optional;

import javax.annotation.Nullable;

import com.mojang.logging.LogUtils;

import net.minecraft.core.BlockPos;
import net.minecraft.util.Mth;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.EntityBasedExplosionDamageCalculator;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.ExplosionDamageCalculator;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.Explosion.BlockInteraction;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.phys.AABB;

public class MagicExplosion extends Explosion{

	public final Level level;
	public final double posX, posY, posZ;
	public final float radius;
	public final ExplosionDamageCalculator damageCalculator;
	public final DamageSource damageSource;
	
	public MagicExplosion(Level level, @Nullable Entity explodingEntity, @Nullable DamageSource damageSource,
			double x, double y, double z,
			float radius) {
		super(level, explodingEntity, damageSource, null, x, y, z, radius, false, BlockInteraction.KEEP);
		this.level = level;
		this.posX = x;
		this.posY = y;
		this.posZ = z;
		this.radius = radius;
		this.damageSource = damageSource == null ? level.damageSources().explosion(this) : damageSource;
		damageCalculator = explodingEntity == null ? new ExplosionDamageCalculator() : new EntityBasedExplosionDamageCalculator(explodingEntity);
	}
	
	public void blockExplosion(float xzStrength, float yStrength, float resistance, float randomVec, boolean strongExplosion) {
		for(int offX = (int) -radius; offX <= radius; offX++) {
			for(int offY = (int) -radius; offY <= radius; offY++) {
				for(int offZ = (int) -radius; offZ <= radius; offZ++) {
					double distance = Math.sqrt(offX * offX + offY * offY + offZ * offZ);
					
					if((int)distance == radius || (offX == -radius || offX == radius || offY == -radius || offY == radius || offZ == -radius || offZ == radius)) {
						
						double xStep = offX / distance;
						double yStep = offY / distance;
						double zStep = offZ / distance;
						
						float vecLength = radius * (0.5f + (float)Math.random() * 0.6f * randomVec);
						
						double blockX = posX;
						double blockY = posY;
						double blockZ = posZ;
						
						for(float vecStep = 0; vecStep < vecLength; vecStep += 0.3d * 1.5f - 0.225f) {
							blockX += xStep * 0.3d * xzStrength;
							blockY += yStep * 0.3d * yStrength;
							blockZ += zStep * 0.3d * xzStrength;
							BlockPos pos = new BlockPos((int)blockX, (int)blockY, (int)blockZ);
							if(!level.isInWorldBounds(pos)) {
								break;
							}
							BlockState blockState = level.getBlockState(pos);
							FluidState fluidState = level.getFluidState(pos);
							if(!strongExplosion && fluidState.isEmpty()) {
								Optional<Float> explosionResistance = damageCalculator.getBlockExplosionResistance(this, level, pos, blockState, fluidState);
								if(explosionResistance.isPresent()) {
									vecLength -= (explosionResistance.get() + 0.3f) * 0.3f * resistance;
								}
								if(vecLength > 0 && damageCalculator.shouldBlockExplode(this, level, pos, blockState, vecLength) && !blockState.isAir()) {
									level.getBlockState(pos).getBlock().onBlockExploded(level.getBlockState(pos), level, pos, this);
								}
							}
							else {
								level.getBlockState(pos).getBlock().onBlockExploded(level.getBlockState(pos), level, pos, this);
							}
						}
					}
				}
			}
		}
	}
	
	public void entityExplosion(float knockback) {
		List<Entity> entities = level.getEntities(getDirectSourceEntity(), new AABB(posX - radius * 2, posY - radius * 2, posZ - radius * 2, posX + radius * 2, posY + radius * 2, posZ + radius * 2));
		//TODO add knockback and improve the algorithm
		entities.forEach(e -> {
			e.hurt(damageSource, 100f);
		});
	}
}
