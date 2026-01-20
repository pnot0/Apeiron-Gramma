package com.pnot0.magia.network;

import java.nio.charset.Charset;
import java.util.function.Supplier;

import com.mojang.logging.LogUtils;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;
import net.minecraftforge.network.PacketDistributor;

public record ClientMessage(String message) {
	public static void encode(ClientMessage packet, FriendlyByteBuf buf) {
		buf.writeInt(packet.message.length());
		buf.writeCharSequence(packet.message, Charset.defaultCharset());
	}
	
	public static ClientMessage decode(FriendlyByteBuf buf) {
		int charLength = buf.readInt();
		return new ClientMessage(buf.readCharSequence(charLength, Charset.defaultCharset()).toString());
	}
	
	public static void handle(ClientMessage packet, Supplier<NetworkEvent.Context> context) {
		NetworkEvent.Context ctx = context.get();
		ctx.enqueueWork(() -> {
			LogUtils.getLogger().info("CLIENT: " + packet.message);
			
			MagiaNetwork.CHANNEL.send(PacketDistributor.ALL.noArg(), new ServerMessageBroadcast(packet.message));
		});
		ctx.setPacketHandled(true);
	}
}

