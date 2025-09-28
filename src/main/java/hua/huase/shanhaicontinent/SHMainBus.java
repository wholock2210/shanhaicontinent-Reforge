package hua.huase.shanhaicontinent;

import hua.huase.shanhaicontinent.block.Zzhaohuantaientity;
import hua.huase.shanhaicontinent.block.shenjie.renderer.ZhaohuantaiEnitityrebderer;
import hua.huase.shanhaicontinent.command.SHCommand;
import hua.huase.shanhaicontinent.entity.client.LoveRedRenderer;
import hua.huase.shanhaicontinent.entity.client.QiluotulipRenderer;
import hua.huase.shanhaicontinent.entity.shenjieentity.*;
import hua.huase.shanhaicontinent.event.client.StructureEntitySpawner;
import hua.huase.shanhaicontinent.event.client.XCLivingDeathEvent;
import hua.huase.shanhaicontinent.init.*;
import hua.huase.shanhaicontinent.init.ModelBlockEntitiesinit;
import hua.huase.shanhaicontinent.capability.CapabilityRegistryHandler;
import hua.huase.shanhaicontinent.item.HunyePing;
import hua.huase.shanhaicontinent.item.shenjie.DecompositionItem;
import hua.huase.shanhaicontinent.network.NetworkHandler;
import hua.huase.shanhaicontinent.init.ModRecipesInit;
import hua.huase.shanhaicontinent.register.ModBlock;
import hua.huase.shanhaicontinent.register.ModBlockEntities;
import hua.huase.shanhaicontinent.register.ModEntities;
import hua.huase.shanhaicontinent.register.ModItems;
import hua.huase.shanhaicontinent.screen.ModMenuTypes;
import hua.huase.shanhaicontinent.tab.ShenJieModTab;
import hua.huase.shanhaicontinent.world.biome.ModTerrablender;
import hua.huase.shanhaicontinent.world.biome.suface.ModsufaceRules;
import hua.huase.shanhaicontinent.world.structure.SHStructureTypes;
import net.minecraft.client.renderer.entity.EntityRenderers;
import net.minecraft.core.registries.Registries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.damagesource.DamageType;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.attributes.RangedAttribute;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.util.thread.SidedThreadGroups;
import net.minecraftforge.network.NetworkEvent;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.simple.SimpleChannel;
import terrablender.api.SurfaceRuleManager;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.*;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.function.BiConsumer;
import java.util.function.Function;
import java.util.function.Supplier;

// The value here should match an entry in the META-INF/mods.toml file
@Mod(SHMainBus.MOD_ID)
@Mod.EventBusSubscriber(modid = SHMainBus.MOD_ID)
public class SHMainBus {
    public static final String MOD_ID = "shanhaicontinent";
    public static final Random random = new Random();

    //    暮色深林
    public static boolean twilightforest_compat;
    //    暮色深林
    public static boolean sophisticated_backpacks;
    //    灾变
    public static boolean LEndersCataclysm;
    //  原版生物BOSS

    public static final ResourceLocation HUNHUAN = new ResourceLocation(SHMainBus.MOD_ID, "textures/gui/hunhuan.png");
    public static final ResourceLocation TEXT = new ResourceLocation(SHMainBus.MOD_ID, "textures/entity/hunhe.png");

    //寒极冰泉&炽火阳泉 DEBUFF
    public static final ResourceKey<DamageType> COLDICE_DAMAGE = ResourceKey.create(Registries.DAMAGE_TYPE, new ResourceLocation("shanhaicontinent", "coldice"));
    public static final ResourceKey<DamageType> HOT_DAMAGE =
            ResourceKey.create(Registries.DAMAGE_TYPE, new ResourceLocation("shanhaicontinent", "hot_yangquan"));

//    protected static final SimpleChannel channel = NetworkRegistry.newSimpleChannel(new ResourceLocation("ep","sync"),()->"340",(s) -> true, (s) -> true);

    static {
        ModLoadingContext.get().registerConfig(net.minecraftforge.fml.config.ModConfig.Type.COMMON, ModConfig.CONFIG);
    }

    public SHMainBus() throws IOException {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();

        //丹药平衡
        //指令
        MinecraftForge.EVENT_BUS.addListener(this::registerCommands);

        BlockInit.register(modEventBus);
        ItemInit.register(modEventBus);
        EntityInit.register(modEventBus);
        ModelBlockEntitiesinit.register(modEventBus);
        ShenJieModTab.TABS.register(modEventBus);
        ModMenuTypes.register(modEventBus);
        ModRecipesInit.register(modEventBus);
        CreativeModTabsInit.register(modEventBus);
        SHModMobEffectsinit.register(modEventBus);
        ModBlock.Blocks.register(modEventBus);
        ModItems.Items.register(modEventBus);
        ShanhaicontinentModMenus.REGISTER.register(modEventBus);
        ModBlockEntities.BLOCK_ENTITIES.register(modEventBus);
        ModEntities.REGISTRY.register(modEventBus);
        ModSounds.SOUND_EVENTS.register(modEventBus);

        ModTerrablender.registerBioms();

        XCLivingDeathEvent.init();

        ShanhaicontinentModFluids.REGISTRY.register(modEventBus);
        ShanhaicontinentModFluidTypes.REGISTRY.register(modEventBus);

//        world
        SHStructureTypes.STRUCTURE_TYPES.register(modEventBus);

        //仙草事件主注册
        MinecraftForge.EVENT_BUS.register(StructureEntitySpawner.class);

//        触发器
        new AdvenceInit();

        MinecraftForge.EVENT_BUS.register(this);
//        modEventBus.addListener(this::addCreative);
        modEventBus.addListener(CapabilityRegistryHandler::registerCaps);

        NetworkHandler.register();

        changeAttributesIO();

        modEventBus.addListener(this::commonSetup);
//联动
        twilightforest_compat = ModList.get().isLoaded("twilightforest");
//联动
        sophisticated_backpacks = ModList.get().isLoaded("sophisticatedbackpacks");

        LEndersCataclysm = ModList.get().isLoaded("cataclysm");
    }


    public void registerCommands(RegisterCommandsEvent event) {
        SHCommand.register(event.getDispatcher());
    }

    private void commonSetup(final FMLCommonSetupEvent event) {
        event.enqueueWork(() -> {
            SurfaceRuleManager.addSurfaceRules(SurfaceRuleManager.RuleCategory.OVERWORLD,MOD_ID, ModsufaceRules.makeRules());
        });
    }


    public static void changeAttributesIO() {

        try {
            Field privateField = RangedAttribute.class.getDeclaredFields()[1];
            privateField.setAccessible(true);
            privateField.set(Attributes.MAX_HEALTH, Float.MAX_VALUE);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }

    }

    @SubscribeEvent
    public void onPlayerClone(PlayerEvent.Clone event) {
        if (!event.isWasDeath()) {
            return;
        }
        Player original = event.getOriginal();
        Player newPlayer = event.getEntity();
        if (original.getPersistentData().contains("JinengmianbanItems")) {
            CompoundTag itemsTag = original.getPersistentData().getCompound("JinengmianbanItems");
            newPlayer.getPersistentData().put("JinengmianbanItems", itemsTag);
        }
    }

    @Mod.EventBusSubscriber(modid = SHMainBus.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE)
    public class EventHandler {
        @SubscribeEvent
        public static void onPlayerTick(TickEvent.PlayerTickEvent event) {
            HunyePing.onPlayerTick(event);
            DecompositionItem.onPlayerTick(event);
        }
    }

    private static final Collection<AbstractMap.SimpleEntry<Runnable, Integer>> workQueue = new ConcurrentLinkedQueue<>();

    public static void queueServerWork(int tick, Runnable action) {
        if (Thread.currentThread().getThreadGroup() == SidedThreadGroups.SERVER)
            workQueue.add(new AbstractMap.SimpleEntry<>(action, tick));
    }

    private static final String PROTOCOL_VERSION = "1";
    public static final SimpleChannel PACKET_HANDLER = NetworkRegistry.newSimpleChannel(new ResourceLocation(MOD_ID, MOD_ID), () -> PROTOCOL_VERSION, PROTOCOL_VERSION::equals, PROTOCOL_VERSION::equals);
    private static int messageID = 0;

    public static <T> void addNetworkMessage(Class<T> messageType, BiConsumer<T, FriendlyByteBuf> encoder, Function<FriendlyByteBuf, T> decoder, BiConsumer<T, Supplier<NetworkEvent.Context>> messageConsumer) {
        PACKET_HANDLER.registerMessage(messageID, messageType, encoder, decoder, messageConsumer);
        messageID++;
    }

    @Mod.EventBusSubscriber(modid = MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    public static class ClientEventHandler {
        @SubscribeEvent
        public static void onClientSetup(FMLClientSetupEvent event) {
            EntityRenderers.register(ModEntities.CHRYSANTHEMUM_TRUNCATUM.get(), XiancaoRenderer::new);
            EntityRenderers.register(ModEntities.LOVERED.get(), LoveRedRenderer::new);
            EntityRenderers.register(ModEntities.QILUOTULIP.get(), QiluotulipRenderer::new);
        }
        @SubscribeEvent
        public static void registerLayerDefinitions(EntityRenderersEvent.RegisterLayerDefinitions event) {
            event.registerLayerDefinition(CustomModel.LAYER_LOCATION, CustomModel::createBodyLayer);
            event.registerLayerDefinition(LoveRedModel.LAYER_LOCATION,LoveRedModel::createBodyLayer);
            event.registerLayerDefinition(QiluotulipModel.LAYER_LOCATION,QiluotulipModel::createBodyLayer);
        }
        @SubscribeEvent
        public static void onRegisterRenderers(EntityRenderersEvent.RegisterRenderers event) {
            event.registerBlockEntityRenderer((BlockEntityType<Zzhaohuantaientity>) ModBlockEntities.ZHAOHUANTAI.get(),
                    ZhaohuantaiEnitityrebderer::new);
        }
    }

        @SubscribeEvent
        public void tick(TickEvent.ServerTickEvent event) {
            if (event.phase == TickEvent.Phase.END) {
                List<AbstractMap.SimpleEntry<Runnable, Integer>> actions = new ArrayList<>();
                workQueue.forEach(work -> {
                    work.setValue(work.getValue() - 1);
                    if (work.getValue() == 0)
                        actions.add(work);
                });
                actions.forEach(e -> e.getKey().run());
                workQueue.removeAll(actions);
            }
        }

    }
