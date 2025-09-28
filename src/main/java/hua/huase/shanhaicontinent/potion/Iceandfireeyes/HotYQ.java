package hua.huase.shanhaicontinent.potion.Iceandfireeyes;

import hua.huase.shanhaicontinent.SHMainBus;
import hua.huase.shanhaicontinent.capability.playerattribute.PlayerAttrubuteAPI;
import net.minecraft.world.effect.MobEffect;

import net.minecraft.core.registries.Registries;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.*;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.ItemStack;

import java.util.UUID;
public class HotYQ extends MobEffect {
    private static final Random RANDOM = new Random();
    private static final UUID MOVEMENT_SPEED_UUID = UUID.fromString("9a8b7c6d-5e4f-3a2b-1c0d-9e8f7a6b5c4d");

    public HotYQ() {
        super(MobEffectCategory.HARMFUL, 0xFF4500);
        this.addAttributeModifier(Attributes.MOVEMENT_SPEED,
                MOVEMENT_SPEED_UUID.toString(),
                -0.10,
                AttributeModifier.Operation.MULTIPLY_TOTAL);
    }

    @Override
    public List<ItemStack> getCurativeItems() {
        return new ArrayList<>();
    }

    @Override
    public void applyEffectTick(LivingEntity entity, int amplifier) {
        DamageSource damageSource = new DamageSource(
                entity.level().registryAccess()
                        .registryOrThrow(Registries.DAMAGE_TYPE)
                        .getHolderOrThrow(SHMainBus.HOT_DAMAGE)
        );

        if (entity instanceof Player player) {
            float maxHealth = PlayerAttrubuteAPI.getMaxshengming(player);
            if (maxHealth > 0) {
                float percent = 0.2f + RANDOM.nextFloat() * 0.2f;
                player.hurt(damageSource, maxHealth * percent);
            }
        } else {
            entity.hurt(entity.damageSources().genericKill(), Float.MAX_VALUE);
        }
        if (!entity.fireImmune()) {
            entity.setSecondsOnFire(2);
        }
        entity.addEffect(new MobEffectInstance(
                MobEffects.MOVEMENT_SLOWDOWN,
                40, // 2秒持续时间
                amplifier, // 与当前效果等级相同
                false, // 不显示粒子
                false // 不显示图标
        ));
    }

    @Override
    public boolean isDurationEffectTick(int duration, int amplifier) {
        return duration % 20 == 0; // 每秒触发一次
    }
}