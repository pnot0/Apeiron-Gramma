package com.pnot0.magia;

import org.slf4j.Logger;

import com.mojang.logging.LogUtils;
import com.pnot0.magia.gui.MagiaOverlay;
import com.pnot0.magia.gui.SocketGUI;
import com.pnot0.magia.inventory.SocketContainer;
import com.pnot0.magia.network.MagiaNetwork;
import com.pnot0.magia.registries.ItemRegistry;
import com.pnot0.magia.render.RenderManager;

import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RegisterClientReloadListenersEvent;
import net.minecraftforge.client.event.RegisterGuiOverlaysEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.extensions.IForgeMenuType;
import net.minecraftforge.event.BuildCreativeModeTabContentsEvent;
import net.minecraftforge.event.server.ServerStartingEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

@Mod(Magia.MODID)
public class Magia
{
    public static final String MODID = "magia";
    public static final Logger LOGGER = LogUtils.getLogger();
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, MODID);
    
    private static final DeferredRegister<MenuType<?>> CONTAINERS = DeferredRegister.create(ForgeRegistries.MENU_TYPES, MODID);
    public static final RegistryObject<MenuType<SocketContainer>> SOCKET_CONTAINER = CONTAINERS.register("socket_container", () -> IForgeMenuType.create(SocketContainer::fromNetwork));
    
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, MODID);
    
    //TODO dedicated creative registry 
    public static final RegistryObject<CreativeModeTab> EXAMPLE_TAB = CREATIVE_MODE_TABS.register("magia", () -> CreativeModeTab.builder()
            .withTabsBefore(CreativeModeTabs.COMBAT)
            .icon(() -> ItemRegistry.COMBAT_SPELLSCHOOL.get().getDefaultInstance())
            .displayItems((parameters, output) -> {
                output.accept(ItemRegistry.TRIANGLE_SOCKET.get());
                output.accept(ItemRegistry.PENTAGRAM_SOCKET.get());
                output.accept(ItemRegistry.TEST_SPELLSCHOOL.get());
                output.accept(ItemRegistry.COMBAT_SPELLSCHOOL.get());
                output.accept(ItemRegistry.MOVEMENT_SPELLSCHOOL.get());
            }).build());
    
    public Magia(FMLJavaModLoadingContext context){
        IEventBus modEventBus = context.getModEventBus();

        BLOCKS.register(modEventBus);
        CONTAINERS.register(modEventBus);
        ItemRegistry.register(modEventBus);
        CREATIVE_MODE_TABS.register(modEventBus);

        MinecraftForge.EVENT_BUS.register(this);

        modEventBus.addListener(this::addCreative);
        modEventBus.addListener(this::networkInit);

    }

    private void addCreative(BuildCreativeModeTabContentsEvent event){
        if (event.getTabKey() == CreativeModeTabs.BUILDING_BLOCKS)
            event.accept(ItemRegistry.TRIANGLE_SOCKET);
    }

	private void networkInit(FMLCommonSetupEvent event) {
		event.enqueueWork(MagiaNetwork::init);
	}
    
    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event){
        LOGGER.info("HELLO from server starting");
    }

    @Mod.EventBusSubscriber(modid = MODID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    public static class ClientModEvents{
    	
        @SubscribeEvent
        public static void onClientSetup(FMLClientSetupEvent event){            
            MenuScreens.register(SOCKET_CONTAINER.get(), SocketGUI::new);
            RenderManager.checkIrisLoaded();
         }
        
        @SubscribeEvent
        public static void registerOverlays(RegisterGuiOverlaysEvent event) {
        	event.registerAboveAll(MODID + "_overlay", MagiaOverlay.instance);
        }
        
        @SubscribeEvent
        public static void onRegisterReloadListeners(RegisterClientReloadListenersEvent event) {
        	RenderManager.registerReloadListener(event);
        }
    }
}


