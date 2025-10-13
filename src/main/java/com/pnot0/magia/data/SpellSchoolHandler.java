package com.pnot0.magia.data;

import net.minecraft.nbt.CompoundTag;
import net.minecraftforge.common.util.INBTSerializable;

public class SpellSchoolHandler implements ISpellSchoolHandler, INBTSerializable<CompoundTag>{

	private String TEXTURE_PATH;
	
	public SpellSchoolHandler(String texturePath){
		this.TEXTURE_PATH = texturePath;
	}
	
	@Override
	public String getTexturePath() {
		return this.TEXTURE_PATH;
	}

	@Override
	public CompoundTag serializeNBT() {
        CompoundTag tag = new CompoundTag();
        tag.putString("TexturePath", this.TEXTURE_PATH);
		return tag;
	}

	@Override
	public void deserializeNBT(CompoundTag tag) {
		this.TEXTURE_PATH = tag.getString("TexturePath");
	}

}
