package hua.huase.shanhaicontinent.advance;

import com.google.gson.JsonObject;
import hua.huase.shanhaicontinent.SHMainBus;
import net.minecraft.advancements.critereon.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.GsonHelper;

import javax.annotation.Nullable;

public class XishouhunhuanTrigger extends SimpleCriterionTrigger<XishouhunhuanTrigger.TriggerInstance> {
    static final ResourceLocation ID = new ResourceLocation(SHMainBus.MOD_ID,"xishouhunhuan");

    public ResourceLocation getId() {
        return ID;
    }

    public XishouhunhuanTrigger.TriggerInstance createInstance(JsonObject p_19762_, ContextAwarePredicate p_286295_, DeserializationContext p_19764_) {
        int dengji = p_19762_.has("nianxian") ? GsonHelper.getAsInt(p_19762_, "nianxian"): -1;
        return new XishouhunhuanTrigger.TriggerInstance(p_286295_, dengji);
    }

    public void trigger(ServerPlayer p_19758_, int nianxian) {
        this.trigger(p_19758_, (p_19768_) -> {
            return p_19768_.matches(nianxian);
        });
    }

    public static class TriggerInstance extends AbstractCriterionTriggerInstance {
        @Nullable
        private final int nianxian;

        public TriggerInstance(ContextAwarePredicate p_286423_, @Nullable int p_286666_) {
            super(XishouhunhuanTrigger.ID, p_286423_);
            this.nianxian = p_286666_;
        }

        public static XishouhunhuanTrigger.TriggerInstance createInstance(int dengji) {
            return new XishouhunhuanTrigger.TriggerInstance(ContextAwarePredicate.ANY, dengji);
        }


        public boolean matches(int nianxian) {
            if (this.nianxian >= nianxian) {
                return false;
            } else {
                return true;
            }
        }

        public JsonObject serializeToJson(SerializationContext p_19781_) {
            JsonObject jsonobject = super.serializeToJson(p_19781_);
            jsonobject.addProperty("nianxian", this.nianxian);
            return jsonobject;
        }
    }
}