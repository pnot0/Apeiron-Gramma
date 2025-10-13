package com.pnot0.magia.data;

import java.util.HashMap;

import com.pnot0.magia.Magia;
import com.pnot0.magia.inventory.SocketData;
import com.pnot0.magia.inventory.SocketManager;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.saveddata.SavedData;
import net.minecraftforge.fml.util.thread.SidedThreadGroups;
import net.minecraftforge.server.ServerLifecycleHooks;

public class SpellSchoolManager extends SavedData{

	private static final String SAVE_NAME = Magia.MODID + "_spellschool_data";
	
	private static final HashMap<String, SpellSchoolData> mappedData = new HashMap<>();
	
	public static final SpellSchoolManager blankClient = new SpellSchoolManager();
	
	public HashMap<String, SpellSchoolData> getMap() {return mappedData;}
	
	public static SpellSchoolManager get(){
		if(Thread.currentThread().getThreadGroup() == SidedThreadGroups.SERVER)
			return ServerLifecycleHooks.getCurrentServer()
					.getLevel(Level.OVERWORLD).getDataStorage()
					.computeIfAbsent(SpellSchoolManager::load, SpellSchoolManager::new, SAVE_NAME);
		else
			return blankClient;
	}
	
	public SpellSchoolData getOrCreateSpellSchool(String itemKey, String texturePath) {
		return mappedData.computeIfAbsent(itemKey, id -> {
			setDirty();
			return new SpellSchoolData(id, texturePath);
		});
	}
	
	public static SpellSchoolManager load(CompoundTag tag) {
		if(tag.contains("SpellSchools")) {
			ListTag list = tag.getList("SpellSchools", Tag.TAG_COMPOUND);
			list.forEach(
					(spellSchoolNBT) -> SpellSchoolData.fromNBT((CompoundTag) spellSchoolNBT).ifPresent(
									(spellSchool) -> mappedData.put(spellSchool.getItemKey(), spellSchool)
							)
					);
		}
		return new SpellSchoolManager();
	}
	
	@Override
	public CompoundTag save(CompoundTag tag) {
		ListTag spellSchools = new ListTag();
		mappedData.forEach((itemKey, spellSchoolData) -> spellSchools.add(spellSchoolData.toNBT()));
		tag.put("SpellSchools", spellSchools);
		return tag;
	}

}
