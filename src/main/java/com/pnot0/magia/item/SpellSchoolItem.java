package com.pnot0.magia.item;

import org.jetbrains.annotations.Nullable;

import com.mojang.logging.LogUtils;
import com.pnot0.magia.data.SpellSchoolData;
import com.pnot0.magia.data.SpellSchoolManager;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.capabilities.ICapabilityProvider;

public class SpellSchoolItem extends Item {
		
	private String TEXTURE;
	
	public SpellSchoolItem(String TEXTURE) {
		super(new Item.Properties().stacksTo(1));
		this.TEXTURE = TEXTURE;
	}
	
	public static SpellSchoolData getData(ItemStack itemStack) {
		if(!(itemStack.getItem() instanceof SpellSchoolItem))
			return null;
		String itemKey;
		CompoundTag tag = itemStack.getOrCreateTag();
		if(!tag.contains("ITEMKEY")) {
			itemKey = itemStack.getDescriptionId();
			tag.putString("ITEMKEY", itemKey);
		}else {
			itemKey = tag.getString("ITEMKEY");
		}
		return SpellSchoolManager.get().getOrCreateSpellSchool(itemKey, ((SpellSchoolItem) itemStack.getItem()).TEXTURE);
	}
	
	@Override
	public @Nullable ICapabilityProvider initCapabilities(ItemStack itemStack, @Nullable CompoundTag tag) {
		getData(itemStack);
		
		return super.initCapabilities(itemStack, tag);
	}
	
}
