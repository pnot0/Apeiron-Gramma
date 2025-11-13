package com.pnot0.magia.item;

import com.mojang.logging.LogUtils;

import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;

public class GungnirItem extends Item{

	public GungnirItem() {
		super(new Item.Properties().stacksTo(1).fireResistant());
	}
	
	@Override
	public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
		
		//copy the algorithm for povhitresult, without blockreach limit, accounting for chunk limits of course
		//TODO: why is explosion only damaging but not destroying blocks?
		
		float f = player.getXRot();
		float f1 = player.getYRot();
		Vec3 vec3 = player.getEyePosition();
		float f2 = Mth.cos(-f1 * ((float)Math.PI / 180F) - (float)Math.PI);
		float f3 = Mth.sin(-f1 * ((float)Math.PI / 180F) - (float)Math.PI);
		float f4 = -Mth.cos(-f * ((float)Math.PI / 180F));
		float f5 = Mth.sin(-f * ((float)Math.PI / 180F));
		float f6 = f3 * f4;
		float f7 = f2 * f4;
		double d0 = player.getAttributeValue(net.minecraftforge.common.ForgeMod.BLOCK_REACH.get()) + 320;
		Vec3 vec31 = vec3.add((double)f6 * d0, (double)f5 * d0, (double)f7 * d0);
		BlockHitResult hitResult = level.clip(new ClipContext(vec3, vec31, ClipContext.Block.OUTLINE, ClipContext.Fluid.NONE, player));
		
		LogUtils.getLogger().info(hitResult.getBlockPos().toShortString());
		
		Explosion exp = new Explosion(
				level,
				player,
				(double) hitResult.getBlockPos().getX()-1,
				(double) hitResult.getBlockPos().getY()-1,
				(double) hitResult.getBlockPos().getZ()-1,
				15f,
				true,
				Explosion.BlockInteraction.DESTROY
				);
		
		exp.explode();
		
		return super.use(level, player, hand);
	}

}
