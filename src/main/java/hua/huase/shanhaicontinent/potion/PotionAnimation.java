package hua.huase.shanhaicontinent.potion;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.client.event.RenderPlayerEvent;

import java.util.Map;

public interface PotionAnimation {

     void renderPlayer(RenderPlayerEvent.Post event);
}
