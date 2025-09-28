package hua.huase.shanhaicontinent.event.client;

import hua.huase.shanhaicontinent.SHMainBus;
import hua.huase.shanhaicontinent.capability.itemattribute.ItemAttributeCapabilityProvider;
import hua.huase.shanhaicontinent.capability.monsterattribute.MonsterAttributeCapability;
import hua.huase.shanhaicontinent.capability.monsterattribute.MonsterAttributeCapabilityProvider;
import hua.huase.shanhaicontinent.entity.hunhuan.HunhuanEntity;
import hua.huase.shanhaicontinent.init.EntityInit;
import hua.huase.shanhaicontinent.register.ModEntities;
import hua.huase.shanhaicontinent.register.ModItems;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.RegistryObject;

import java.util.HashMap;
import java.util.Map;

@Mod.EventBusSubscriber(modid = SHMainBus.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class XCLivingDeathEvent {

    private static final Map<RegistryObject<? extends EntityType<?>>, RegistryObject<? extends Item>> ENTITY_TO_ITEM_MAP = new HashMap<>();
    private static final double DROP_CHANCE = 0.03;

    public static void init() {
        ENTITY_TO_ITEM_MAP.put(ModEntities.CHRYSANTHEMUM_TRUNCATUM, ModItems.CHRYSANTHEMUM_TRUNCATUM);
        ENTITY_TO_ITEM_MAP.put(ModEntities.LOVERED, ModItems.LOVERED);
        ENTITY_TO_ITEM_MAP.put(ModEntities.QILUOTULIP, ModItems.QILUOTULIP);
    }

    @SubscribeEvent
    public static void onLivingDeath(LivingDeathEvent event) {
        LivingEntity entity = event.getEntity();
        if (entity == null || entity.level().isClientSide) return;
        if (!(event.getSource().getEntity() instanceof Player)) return;
        EntityType<?> deadType = entity.getType();
        RegistryObject<? extends Item> itemToDrop = null;
        for (Map.Entry<RegistryObject<? extends EntityType<?>>, RegistryObject<? extends Item>> entry : ENTITY_TO_ITEM_MAP.entrySet()) {
            if (entry.getKey().get() == deadType) {
                itemToDrop = entry.getValue();
                break;
            }
        }
        if (itemToDrop == null) return;
        if (entity.getRandom().nextDouble() > DROP_CHANCE) return;
        ItemStack stack = new ItemStack(itemToDrop.get());
        entity.getCapability(MonsterAttributeCapabilityProvider.CAPABILITY).ifPresent(monsterCap -> {
            stack.getCapability(ItemAttributeCapabilityProvider.CAPABILITY).ifPresent(itemCap -> {
                CompoundTag originalTag = monsterCap.serializeNBT();
                CompoundTag reducedTag = originalTag.copy();
                for (String key : originalTag.getAllKeys()) {
                    if (originalTag.getTagType(key) == Tag.TAG_INT) {
                        if ("nianxian".equals(key)) {
                            reducedTag.putInt(key, originalTag.getInt(key));
                        } else {
                            int reducedValue = Math.max(1, (int)(originalTag.getInt(key) * 0.25f));
                            reducedTag.putInt(key, reducedValue);
                        }
                    }
                }
                itemCap.deserializeNBT(reducedTag);
                stack.getOrCreateTag().put("itemattribute", reducedTag);
            });
        });
        ItemEntity itemEntity = new ItemEntity(entity.level(), entity.getX(), entity.getY(), entity.getZ(), stack);
        itemEntity.setExtendedLifetime();
        entity.level().addFreshEntity(itemEntity);
        entity.getCapability(MonsterAttributeCapabilityProvider.CAPABILITY).ifPresent(cap -> {
            addHunhuanEntity(cap, entity.level(), entity.getOnPos());
        });
    }

    private static void addHunhuanEntity(MonsterAttributeCapability cap, Level level, BlockPos pos) {
        if (level == null || pos == null) return;

        HunhuanEntity entity = new HunhuanEntity(EntityInit.HUNHUAN.get(), level);
        entity.getCapability(MonsterAttributeCapabilityProvider.CAPABILITY).ifPresent(entityCap -> {
            entityCap.deserializeNBT(cap.serializeNBT());
            entity.setPos(pos.getCenter().add(0, 1, 0));
            level.addFreshEntity(entity);
        });
    }
}