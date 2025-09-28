package hua.huase.shanhaicontinent.potion;

import net.minecraft.world.effect.MobEffect;

import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeMap;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.player.Abilities;

public class FrostBindingEffect extends MobEffect {
    public FrostBindingEffect() {
        super(MobEffectCategory.HARMFUL, 0x66a3ff);
    }
    @Override
    public void applyEffectTick(LivingEntity entity, int amplifier) {
        if (entity instanceof Player player) {
            AttributeInstance movement = player.getAttribute(Attributes.MOVEMENT_SPEED);
            if (movement != null) {
                movement.setBaseValue(0);
            }

            AttributeInstance jump = player.getAttribute(Attributes.JUMP_STRENGTH);
            if (jump != null) {
                jump.setBaseValue(0);
            }

            player.setDeltaMovement(0, Math.min(player.getDeltaMovement().y, 0), 0);
            player.setSprinting(false);

            player.onUpdateAbilities();
        }
    }

    @Override
    public void removeAttributeModifiers(LivingEntity entity, AttributeMap attributes, int amplifier) {
        super.removeAttributeModifiers(entity, attributes, amplifier);

        if (entity instanceof Player player) {
            AttributeInstance movement = player.getAttribute(Attributes.MOVEMENT_SPEED);
            if (movement != null) {
                movement.setBaseValue(0.1);
            }
            AttributeInstance jump = player.getAttribute(Attributes.JUMP_STRENGTH);
            if (jump != null) {
                jump.setBaseValue(0.42);
            }
            player.onUpdateAbilities();
        }
    }

    @Override
    public boolean isDurationEffectTick(int duration, int amplifier) {
        return true; // 每tick都执行
    }
}