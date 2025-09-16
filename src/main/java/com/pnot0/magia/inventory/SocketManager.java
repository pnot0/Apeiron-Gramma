package com.pnot0.magia.inventory;

import java.util.HashMap;
import java.util.UUID;

import org.stringtemplate.v4.compiler.STParser.compoundElement_return;

import com.pnot0.magia.Magia;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.saveddata.SavedData;
import net.minecraftforge.fml.util.thread.SidedThreadGroups;
import net.minecraftforge.server.ServerLifecycleHooks;

public class SocketManager extends SavedData{

	private static final String SAVE_NAME = Magia.MODID + "_socket_data";
	
	private static final HashMap<UUID, SocketData> mappedData = new HashMap<>();
	
	public static final SocketManager blankClient = new SocketManager();
	
	public HashMap<UUID, SocketData> getMap() {return mappedData;}
	
	public static SocketManager get() {
		if(Thread.currentThread().getThreadGroup() == SidedThreadGroups.SERVER)
			return ServerLifecycleHooks.getCurrentServer()
					.getLevel(Level.OVERWORLD).getDataStorage()
					.computeIfAbsent(SocketManager::load, SocketManager::new, SAVE_NAME);
		else
			return blankClient;
	}
	
	public SocketData getOrCreateSocket(UUID uuid) {
		return mappedData.computeIfAbsent(uuid, id -> {
			setDirty();
			return new SocketData(id);
		});
	}
	
	public static SocketManager load(CompoundTag tag) {
		if(tag.contains("Sockets")) {
			ListTag list = tag.getList("Sockets", Tag.TAG_COMPOUND);
			list.forEach(
					(socketNBT) -> SocketData.fromNBT((CompoundTag) socketNBT).ifPresent(
									(socket) -> mappedData.put(socket.getUUID(), socket)
							)
					);
		}
		return new SocketManager();
	}
	
	@Override
	public CompoundTag save(CompoundTag tag) {
		ListTag sockets = new ListTag();
		mappedData.forEach((uuid, socketData) -> sockets.add(socketData.toNBT()));
		tag.put("Sockets", sockets);
		return tag;
	}

}
