package com.pnot0.magia.item;

import com.pnot0.magia.Magia;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.Item;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ItemRegistry {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, Magia.MODID);
	
    public static void register(IEventBus eventBus) {
		ITEMS.register(eventBus);
    }
    
    public static final RegistryObject<Item> TEST_SPELLSCHOOL = ITEMS.register("test_spellschool", () -> new SpellSchoolItem(
    		"textures/spellschool/test_spellschool.png"));
    
    public static final RegistryObject<Item> COMBAT_SPELLSCHOOL = ITEMS.register("combat_spellschool", () -> new SpellSchoolItem(
    		"textures/spellschool/combat_spellschool.png"));
    
    public static final RegistryObject<Item> TRIANGLE_SOCKET = ITEMS.register("triangle_socket", () -> new SocketItem(SocketsEnum.TRIANGLE));
    public static final RegistryObject<Item> PENTAGRAM_SOCKET = ITEMS.register("pentagram_socket", () -> new SocketItem(SocketsEnum.PENTAGRAM));
    
    //possible registry names
    //TRIANGLE_SOCKET
    //PENTAGRAM_SOCKET
    //OCTAGRAM_SOCKET
}
