package hua.huase.shanhaicontinent.advance;

import com.google.gson.JsonObject;
import hua.huase.shanhaicontinent.SHMainBus;
import net.minecraft.advancements.critereon.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.GsonHelper;

import javax.annotation.Nullable;

public class ChangeDengjiTrigger extends SimpleCriterionTrigger<ChangeDengjiTrigger.TriggerInstance> {
    static final ResourceLocation ID = new ResourceLocation(SHMainBus.MOD_ID,"changed_dengji");

    public ResourceLocation getId() {
        return ID;
    }

    public ChangeDengjiTrigger.TriggerInstance createInstance(JsonObject p_19762_, ContextAwarePredicate p_286295_, DeserializationContext p_19764_) {
        int dengji = p_19762_.has("dengji") ? GsonHelper.getAsInt(p_19762_, "dengji"): -1;
        return new ChangeDengjiTrigger.TriggerInstance(p_286295_, dengji);
    }

    public void trigger(ServerPlayer p_19758_, int dengji) {
        this.trigger(p_19758_, (p_19768_) -> {
            return p_19768_.matches(dengji);
        });
    }

    public static class TriggerInstance extends AbstractCriterionTriggerInstance {
        @Nullable
        private final int dengji;

        public TriggerInstance(ContextAwarePredicate p_286423_, @Nullable int p_286666_) {
            super(ChangeDengjiTrigger.ID, p_286423_);
            this.dengji = p_286666_;
        }

        public static ChangeDengjiTrigger.TriggerInstance createInstance(int dengji) {
            return new ChangeDengjiTrigger.TriggerInstance(ContextAwarePredicate.ANY, dengji);
        }


        public boolean matches(int dengji) {
            if (this.dengji != dengji) {
                return false;
            } else {
                return true;
            }
        }

        public JsonObject serializeToJson(SerializationContext p_19781_) {
            JsonObject jsonobject = super.serializeToJson(p_19781_);
            jsonobject.addProperty("dengji", this.dengji);
            return jsonobject;
        }
    }
}