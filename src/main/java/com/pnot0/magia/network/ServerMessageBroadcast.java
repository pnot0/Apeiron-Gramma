package com.pnot0.magia.network;

import java.nio.charset.Charset;
import java.util.function.Supplier;

import com.mojang.logging.LogUtils;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;

public record ServerMessageBroadcast(String message) {
	public static void encode(ServerMessageBroadcast packet, FriendlyByteBuf buf) {
		buf.writeInt(packet.message.length());
		buf.writeCharSequence(packet.message, Charset.defaultCharset());
	}
	
	public static ServerMessageBroadcast decode(FriendlyByteBuf buf) {
		int charLength = buf.readInt();
		return new ServerMessageBroadcast(buf.readCharSequence(charLength, Charset.defaultCharset()).toString());
	}
	
	public static void handle(ServerMessageBroadcast packet, Supplier<NetworkEvent.Context> context) {
		NetworkEvent.Context ctx = context.get();
		ctx.enqueueWork(() -> {
			LogUtils.getLogger().info("SERVER: " + packet.message);
		});
		ctx.setPacketHandled(true);
	}
}
