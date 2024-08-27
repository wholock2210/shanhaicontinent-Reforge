package hua.huase.shanhaicontinent.advance;

import com.google.gson.JsonObject;
import hua.huase.shanhaicontinent.SHMainBus;
import net.minecraft.advancements.critereon.AbstractCriterionTriggerInstance;
import net.minecraft.advancements.critereon.ContextAwarePredicate;
import net.minecraft.advancements.critereon.DeserializationContext;
import net.minecraft.advancements.critereon.SimpleCriterionTrigger;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;

public class MenghuiwanguTrigger extends SimpleCriterionTrigger<MenghuiwanguTrigger.TriggerInstance> {
    static final ResourceLocation ID = new ResourceLocation(SHMainBus.MOD_ID,"menghuiwangu");

    public ResourceLocation getId() {
        return ID;
    }

    public MenghuiwanguTrigger.TriggerInstance createInstance(JsonObject p_286276_, ContextAwarePredicate p_286282_, DeserializationContext p_286851_) {
        return new MenghuiwanguTrigger.TriggerInstance(p_286282_);
    }

    public void trigger(ServerPlayer p_160388_) {
        this.trigger(p_160388_, (p_160394_) -> {
            return true;
        });
    }

    public static class TriggerInstance extends AbstractCriterionTriggerInstance {
        public TriggerInstance(ContextAwarePredicate p_286351_) {
            super(MenghuiwanguTrigger.ID, p_286351_);
        }

        public static MenghuiwanguTrigger.TriggerInstance createInstance() {
            return new MenghuiwanguTrigger.TriggerInstance(ContextAwarePredicate.ANY);
        }

    }
}