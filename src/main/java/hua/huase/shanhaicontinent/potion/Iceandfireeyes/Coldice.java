package hua.huase.shanhaicontinent.potion.Iceandfireeyes;

import hua.huase.shanhaicontinent.SHMainBus;
import hua.huase.shanhaicontinent.capability.playerattribute.PlayerAttrubuteAPI;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.damagesource.DamageSource;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.effect.*;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.ItemStack;

import java.util.UUID;
public class Coldice extends MobEffect {
    private static final Random RANDOM = new Random();
    private static final UUID MOVEMENT_SPEED_UUID = UUID.fromString("1a1b1c1d-2e2f-3a3b-4c4d-5e5f6a6b7c7d");

    public Coldice() {
        super(MobEffectCategory.HARMFUL, 0x99ccff);
        // 添加移动速度减益
        this.addAttributeModifier(Attributes.MOVEMENT_SPEED,
                MOVEMENT_SPEED_UUID.toString(),
                -0.45,
                AttributeModifier.Operation.MULTIPLY_TOTAL);
    }

    @Override
    public List<ItemStack> getCurativeItems() {
        return new ArrayList<>(); // 返回空列表表示无法被常规方法治愈
    }

    @Override
    public void applyEffectTick(LivingEntity entity, int amplifier) {
        // 动态获取DamageSource
        DamageSource damageSource = new DamageSource(
                entity.level().registryAccess()
                        .registryOrThrow(Registries.DAMAGE_TYPE)
                        .getHolderOrThrow(SHMainBus.COLDICE_DAMAGE)
        );

        if (entity instanceof Player player) {
            // 对玩家的处理：百分比扣血
            float maxHealth = PlayerAttrubuteAPI.getMaxshengming(player);
            if (maxHealth <= 0) return;

            float percent = 0.1f + RANDOM.nextFloat() * 0.2f;
            player.hurt(damageSource, maxHealth * percent);
        } else {
            // 对非玩家实体的处理：直接杀死
            entity.hurt(entity.damageSources().genericKill(), Float.MAX_VALUE);
            // 或者使用虚空伤害（两种方式任选其一）
//             entity.hurt(entity.damageSources().outOfWorld(), Float.MAX_VALUE);
        }

        // 对所有实体添加缓慢效果（叠加）
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
        return duration % 20 == 0; // 每20刻（1秒）触发一次
    }
}