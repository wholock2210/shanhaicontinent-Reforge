package hua.huase.shanhaicontinent.register;


import hua.huase.shanhaicontinent.SHMainBus;
import hua.huase.shanhaicontinent.entity.shenjieentity.*;
import hua.huase.shanhaicontinent.entity.shenjieentity.severentity.demonking.DemonKingEntity;
import hua.huase.shanhaicontinent.entity.shenjieentity.severentity.evileyetyrant.EvileyetyrantEntity;
import hua.huase.shanhaicontinent.entity.shenjieentity.severentity.frostprison.FrostPrisonEntity;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class ModEntities {
    public static final DeferredRegister<EntityType<?>> REGISTRY = DeferredRegister.create(ForgeRegistries.ENTITY_TYPES, SHMainBus.MOD_ID);
    public static final RegistryObject<EntityType<MoWuEntity>> MOWU = register("mowu",
            EntityType.Builder.<MoWuEntity>of(MoWuEntity::new, MobCategory.MONSTER).setShouldReceiveVelocityUpdates(true).setTrackingRange(64).setUpdateInterval(3).setCustomClientFactory(MoWuEntity::new)
                    .sized(0.6f, 1.95f));

    public static final RegistryObject<EntityType<TianFaEntity>> TIANFA = register("tianfa",
            EntityType.Builder.<TianFaEntity>of(TianFaEntity::new, MobCategory.MONSTER).setShouldReceiveVelocityUpdates(true).setTrackingRange(64).setUpdateInterval(3).setCustomClientFactory(TianFaEntity::new).fireImmune().sized(0.6f, 1.95f));

    public static final RegistryObject<EntityType<XiancaoEntity>> CHRYSANTHEMUM_TRUNCATUM = register("chrysanthemum_truncatum",
            EntityType.Builder.<XiancaoEntity>of(XiancaoEntity::new, MobCategory.MONSTER).setShouldReceiveVelocityUpdates(true).setTrackingRange(64).setUpdateInterval(3).setCustomClientFactory(XiancaoEntity::new).fireImmune().sized(0.6f, 1.8f));

    public static final RegistryObject<EntityType<LoveRedEntity>> LOVERED = register("lovered",
            EntityType.Builder.<LoveRedEntity>of(LoveRedEntity::new, MobCategory.MONSTER).setShouldReceiveVelocityUpdates(true).setTrackingRange(64).setUpdateInterval(3).setCustomClientFactory(LoveRedEntity::new).fireImmune().sized(0.6f, 1.8f));

    public static final RegistryObject<EntityType<QiluotulipEntity>> QILUOTULIP = register("qiluotulip",
            EntityType.Builder.<QiluotulipEntity>of(QiluotulipEntity::new, MobCategory.MONSTER).setShouldReceiveVelocityUpdates(true).setTrackingRange(64).setUpdateInterval(3).setCustomClientFactory(QiluotulipEntity::new).fireImmune().sized(0.6f, 1.8f));

 public static final RegistryObject<EntityType<EvileyetyrantEntity>> EVILEYETYRANT = register("evileyetyrant",
            EntityType.Builder.<EvileyetyrantEntity>of(EvileyetyrantEntity::new, MobCategory.MONSTER).setShouldReceiveVelocityUpdates(true).setTrackingRange(64).setUpdateInterval(3).setCustomClientFactory(EvileyetyrantEntity::new));

    public static final RegistryObject<EntityType<DemonKingEntity>> DEMON_KING = register("demon_king",
            EntityType.Builder.<DemonKingEntity>of(DemonKingEntity::new, MobCategory.MONSTER).setShouldReceiveVelocityUpdates(true).setTrackingRange(64).setUpdateInterval(3).setCustomClientFactory(DemonKingEntity::new));

    public static final RegistryObject<EntityType<FrostPrisonEntity>> FROST_PRISON = register("frost_prison",
            EntityType.Builder.<FrostPrisonEntity>of(FrostPrisonEntity::new, MobCategory.MONSTER).setShouldReceiveVelocityUpdates(true).setTrackingRange(64).setUpdateInterval(3).setCustomClientFactory(FrostPrisonEntity::new));


    private static <T extends Entity> RegistryObject<EntityType<T>> register(String registryname, EntityType.Builder<T> entityTypeBuilder) {
        return REGISTRY.register(registryname, () -> (EntityType<T>) entityTypeBuilder.build(registryname));
    }

    @SubscribeEvent
    public static void init(FMLCommonSetupEvent event) {
        event.enqueueWork(() -> {
            MoWuEntity.init();
            TianFaEntity.init();
            XiancaoEntity.init();
            LoveRedEntity.init();
            QiluotulipEntity.init();
            EvileyetyrantEntity.init();
            DemonKingEntity.init();
            FrostPrisonEntity.init();
        });
    }

    @SubscribeEvent
    public static void registerAttributes(EntityAttributeCreationEvent event) {
        event.put(MOWU.get(), MoWuEntity.createAttributes().build());
        event.put(TIANFA.get(), TianFaEntity.createAttributes().build());
        event.put(CHRYSANTHEMUM_TRUNCATUM.get(),XiancaoEntity.createAttributes().build());
        event.put(LOVERED.get(),LoveRedEntity.createAttributes().build());
        event.put(QILUOTULIP.get(),QiluotulipEntity.createAttributes().build());
        event.put(EVILEYETYRANT.get(),EvileyetyrantEntity.createAttributes().build());
        event.put(DEMON_KING.get(),DemonKingEntity.createAttributes().build());
        event.put(FROST_PRISON.get(),FrostPrisonEntity.createAttributes().build());
    }
}
