package hua.huase.shanhaicontinent.advance;

import com.google.gson.JsonObject;
import hua.huase.shanhaicontinent.SHMainBus;
import net.minecraft.advancements.Criterion;
import net.minecraft.advancements.critereon.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;

public class XishouHunheTrigger extends SimpleCriterionTrigger<XishouHunheTrigger.TriggerInstance> {
    static final ResourceLocation ID = new ResourceLocation(SHMainBus.MOD_ID,"xishouhunhe");

    public ResourceLocation getId() {
        return ID;
    }

    public XishouHunheTrigger.TriggerInstance createInstance(JsonObject p_286276_, ContextAwarePredicate p_286282_, DeserializationContext p_286851_) {
        return new XishouHunheTrigger.TriggerInstance(p_286282_);
    }

    public void trigger(ServerPlayer p_160388_) {
        this.trigger(p_160388_, (p_160394_) -> {
            return true;
        });
    }

    public static class TriggerInstance extends AbstractCriterionTriggerInstance {
        public TriggerInstance(ContextAwarePredicate p_286351_) {
            super(XishouHunheTrigger.ID, p_286351_);
        }

        public static XishouHunheTrigger.TriggerInstance createInstance() {
            return new XishouHunheTrigger.TriggerInstance(ContextAwarePredicate.ANY);
        }

    }
}