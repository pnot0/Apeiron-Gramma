package com.pnot0.magia.data;

import java.util.Optional;

import net.minecraft.nbt.CompoundTag;
import net.minecraftforge.common.util.LazyOptional;

public class SpellSchoolData {
	private final String ITEMKEY;
	private final SpellSchoolHandler spellSchool;
	private final LazyOptional<ISpellSchoolHandler> optional;
	
	public LazyOptional<ISpellSchoolHandler> getOptional(){return this.optional;}
	
	public SpellSchoolHandler getHandler() {return this.spellSchool;}
	
	public SpellSchoolData(String ITEMKEY, String texturePath) {
		this.ITEMKEY = ITEMKEY;
		this.spellSchool = new SpellSchoolHandler(texturePath);
    	this.optional = LazyOptional.of(() -> this.spellSchool);
	}
	
	public SpellSchoolData(String ITEMKEY, CompoundTag tag) {
		this.ITEMKEY = ITEMKEY;
		this.spellSchool = new SpellSchoolHandler(tag.getString(ITEMKEY));
		this.spellSchool.deserializeNBT(tag.getCompound("SpellSchool"));
		this.optional = LazyOptional.of(() -> this.spellSchool);
	}
	
	public String getItemKey() {
		return this.ITEMKEY;
	}
	
	public static Optional<SpellSchoolData> fromNBT(CompoundTag tag){
		if(tag.contains("ITEMKEY")) {
			String ITEMKEY  = tag.getString("ITEMKEY");
			return Optional.of(new SpellSchoolData(tag.getString(ITEMKEY), tag));
		}
		return Optional.empty();
	}
	
	public CompoundTag toNBT() {
		CompoundTag tag = new CompoundTag();
		tag.putString("ITEMKEY", ITEMKEY);
		tag.put("SpellSchool", this.spellSchool.serializeNBT());
	
		return tag;
	}
}
