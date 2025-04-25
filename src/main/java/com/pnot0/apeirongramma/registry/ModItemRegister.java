package com.pnot0.apeirongramma.registry;

import com.pnot0.apeirongramma.ApeironGramma;
import com.pnot0.apeirongramma.item.ChangeGuiPresetItem;
import com.pnot0.apeirongramma.item.EyeItem;
import com.pnot0.apeirongramma.item.GuiAdvanceItem;
import com.pnot0.apeirongramma.item.GuiReturnItem;
import com.pnot0.apeirongramma.item.GuiSummonItem;
import com.pnot0.apeirongramma.item.SacredKnife;

import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModItemRegister {
    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(ApeironGramma.MODID);
    
    public static final DeferredItem<BlockItem> EXAMPLE_BLOCK_ITEM = ITEMS.registerSimpleBlockItem("example_block", ApeironGramma.EXAMPLE_BLOCK);
    
    public static final DeferredItem<Item> EXAMPLE_ITEM = ITEMS.registerSimpleItem(
    		"example_item",
    		new Item.Properties()
    			.food(new FoodProperties.Builder()
					.alwaysEdible()
					.nutrition(1)
					.saturationModifier(2f)
					.build())
    			);
    
    public static final DeferredItem<Item> GUI_SUMMON = ITEMS.registerItem(
    	    "gui_summon",
    	    GuiSummonItem::new,
    	    new Item.Properties()
    	);
    
    public static final DeferredItem<Item> ADVANCE_GUI = ITEMS.registerItem(
    		"advance_gui",
    		GuiAdvanceItem::new,
    		new Item.Properties()
		);
    
    public static final DeferredItem<Item> RETURN_GUI = ITEMS.registerItem(
    		"return_gui",
    		GuiReturnItem::new,
    		new Item.Properties()
		);
    
    public static final DeferredItem<Item> SACRED_KNIFE = ITEMS.registerItem(
    		"sacred_knife",
    		SacredKnife::new,
    		new Item.Properties()
    	);
    
    public static final DeferredItem<Item> CHANGE_GUI = ITEMS.registerItem(
    		"change_gui",
    		ChangeGuiPresetItem::new,
    		new Item.Properties()
		);
    
    public static final DeferredItem<Item> EYE_ITEM = ITEMS.registerItem(
    		"eye_item",
    		EyeItem::new,
    		new Item.Properties().stacksTo(1)
    	);
    
    public static void register(IEventBus eventBus) {
    	ITEMS.register(eventBus);
    }
}
