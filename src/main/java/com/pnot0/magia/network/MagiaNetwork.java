package com.pnot0.magia.network;

import com.pnot0.magia.Magia;

import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.network.NetworkDirection;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.simple.SimpleChannel;

public class MagiaNetwork {
	private static final String PROTOCOL_VERSION = "1";
	
	private static int ID = 0;
	
	public static final SimpleChannel CHANNEL = NetworkRegistry.newSimpleChannel(
			ResourceLocation.fromNamespaceAndPath(Magia.MODID, "main"),
			() -> PROTOCOL_VERSION,
			PROTOCOL_VERSION::equals, 
			PROTOCOL_VERSION::equals
			);
	
	public static void init() {
		CHANNEL.messageBuilder(ClientMessage.class, ID++, NetworkDirection.PLAY_TO_SERVER)
		.encoder(ClientMessage::encode)
		.decoder(ClientMessage::decode)
		.consumerMainThread(ClientMessage::handle)
		.add();
		
		CHANNEL.messageBuilder(ServerMessageBroadcast.class, ID++, NetworkDirection.PLAY_TO_CLIENT)
		.encoder(ServerMessageBroadcast::encode)
		.decoder(ServerMessageBroadcast::decode)
		.consumerMainThread(ServerMessageBroadcast::handle)
		.add();
	}
}
