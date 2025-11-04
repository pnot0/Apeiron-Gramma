package com.pnot0.magia.item;

import com.pnot0.magia.Magia;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;
import net.minecraftforge.registries.RegistryObject;

public enum SocketsEnum {
	TRIANGLE("Triangle", Rarity.COMMON, 3, "triangle_socketmenu_gui.png", "triangle_socket_overlay.png", 176, 150, 300, ItemRegistry.TRIANGLE_SOCKET);
	
	public final String name;
	public final Rarity rarity;
	public final int slots;
	public final ResourceLocation guiTexture, overlayTexture;
	public final int guiWidth, guiHeight, overlayWidth;
	public final RegistryObject<Item> item;
	
	SocketsEnum(
			String name, Rarity rarity, int slots, String guiTextureLocation, String overlayTextureLocation,
			int guiWidth, int guiHeight, int overlayWidth, RegistryObject<Item> item
		){
		this.name = name;
		this.rarity = rarity;
		this.slots = slots;
		this.guiTexture = ResourceLocation.fromNamespaceAndPath(Magia.MODID, "textures/gui/" + guiTextureLocation);
		this.overlayTexture = ResourceLocation.fromNamespaceAndPath(Magia.MODID, "textures/overlay/" + overlayTextureLocation);
		this.guiWidth = guiWidth;
		this.guiHeight = guiHeight;
		this.overlayWidth = overlayWidth;
		this.item = item;
	}
}
