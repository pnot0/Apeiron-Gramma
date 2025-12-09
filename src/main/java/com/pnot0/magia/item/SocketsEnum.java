package com.pnot0.magia.item;

import com.pnot0.magia.Magia;
import com.pnot0.magia.registries.ItemRegistry;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;
import net.minecraftforge.registries.RegistryObject;

public enum SocketsEnum {
	TRIANGLE("Triangle_Socket", Rarity.COMMON, 3, "triangle_socketmenu_inventory.png", "triangle_socket_overlay.png", 176, 200, 300, ItemRegistry.TRIANGLE_SOCKET, 10),
	PENTAGRAM("Pentagram_Socket", Rarity.UNCOMMON, 5, "pentagram_socketmenu_inventory.png", "pentagram_socket_overlay.png", 176, 200, 300, ItemRegistry.PENTAGRAM_SOCKET, 9);
	
	public final String name;
	public final Rarity rarity;
	public final int slots;
	public final ResourceLocation guiTexture, overlayTexture;
	public final int guiWidth, guiHeight, overlayWidth;
	public final RegistryObject<Item> item;
	public final int increment;
	
	SocketsEnum(
			String name, Rarity rarity, int slots, String guiTextureLocation, String overlayTextureLocation,
			int guiWidth, int guiHeight, int overlayWidth, RegistryObject<Item> item, int increment
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
		this.increment = increment;
	}
}
