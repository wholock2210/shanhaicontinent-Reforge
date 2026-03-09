package hua.huase.shanhaicontinent.advance;

import com.google.gson.JsonObject;
import hua.huase.shanhaicontinent.SHMainBus;
import net.minecraft.advancements.critereon.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.GsonHelper;

import javax.annotation.Nullable;

public class JuexingwuhunTrigger extends SimpleCriterionTrigger<JuexingwuhunTrigger.TriggerInstance> {
    static final ResourceLocation ID = new ResourceLocation(SHMainBus.MOD_ID,"juexingwuhun");

    public ResourceLocation getId() {
        return ID;
    }

    public JuexingwuhunTrigger.TriggerInstance createInstance(JsonObject p_19762_, ContextAwarePredicate p_286295_, DeserializationContext p_19764_) {
        int num = p_19762_.has("num") ? GsonHelper.getAsInt(p_19762_, "num"): -1;
        return new JuexingwuhunTrigger.TriggerInstance(p_286295_, num);
    }

    public void trigger(ServerPlayer p_19758_, int num) {
        this.trigger(p_19758_, (p_19768_) -> {
            return p_19768_.matches(num);
        });
    }

    public static class TriggerInstance extends AbstractCriterionTriggerInstance {
        @Nullable
        private final int num;

        public TriggerInstance(ContextAwarePredicate p_286423_, @Nullable int p_286666_) {
            super(JuexingwuhunTrigger.ID, p_286423_);
            this.num = p_286666_;
        }

        public static JuexingwuhunTrigger.TriggerInstance createInstance(int num) {
            return new JuexingwuhunTrigger.TriggerInstance(ContextAwarePredicate.ANY, num);
        }


        public boolean matches(int num) {
            if (this.num > num) {
                return false;
            } else {
                return true;
            }
        }

        public JsonObject serializeToJson(SerializationContext p_19781_) {
            JsonObject jsonobject = super.serializeToJson(p_19781_);
            jsonobject.addProperty("num", this.num);
            return jsonobject;
        }
    }
}