package com.pnot0.apeirongramma.registry;

import java.util.function.Supplier;

import com.pnot0.apeirongramma.ApeironGramma;
import com.pnot0.apeirongramma.menus.EyeContainerMenu;

import net.minecraft.core.registries.Registries;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.common.extensions.IMenuTypeExtension;
import net.neoforged.neoforge.network.IContainerFactory;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

public class ModMenuRegister {
	public static final DeferredRegister<MenuType<?>> MENUS = DeferredRegister.create(Registries.MENU, ApeironGramma.MODID);
    
    public static final Supplier<MenuType<EyeContainerMenu>> EYE_MENU = MENUS.register("my_menu", () -> new MenuType<>(EyeContainerMenu::new, FeatureFlags.DEFAULT_FLAGS));
	
	private static <T extends AbstractContainerMenu>DeferredHolder<MenuType<?>, MenuType<T>> registerMenuType(String name, IContainerFactory<T> factory){
		return MENUS.register(name, () -> IMenuTypeExtension.create(factory));
	}
	 
    public static void register(IEventBus eventBus) {
		MENUS.register(eventBus);
    }
}
