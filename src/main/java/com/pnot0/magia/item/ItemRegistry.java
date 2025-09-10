package com.pnot0.magia.item;

import com.pnot0.magia.Magia;

import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.Item;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ItemRegistry {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, Magia.MODID);
	
    //public static final RegistryObject<Item> EXAMPLE_BLOCK_ITEM = ITEMS.register("example_block", () -> new BlockItem(EXAMPLE_BLOCK.get(), new Item.Properties()));

    public static void register(IEventBus eventBus) {
		ITEMS.register(eventBus);
    }
    
    public static final RegistryObject<Item> EXAMPLE_ITEM = ITEMS.register("food_item", () -> new Item(new Item.Properties().food(new FoodProperties.Builder()
            .alwaysEat().nutrition(1).saturationMod(2f).build())));

    public static final RegistryObject<Item> SOCKET_ITEM = ITEMS.register("socket_item", () -> new Item(new SocketItem.Properties().stacksTo(1)));
    
}
