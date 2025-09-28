package hua.huase.shanhaicontinent.advance;

import com.google.gson.JsonObject;
import hua.huase.shanhaicontinent.SHMainBus;
import net.minecraft.advancements.critereon.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.GsonHelper;

public class WeaponStrengthenTrigger extends SimpleCriterionTrigger<WeaponStrengthenTrigger.TriggerInstance> {
    static final ResourceLocation ID = new ResourceLocation(SHMainBus.MOD_ID, "weapon_strengthen");

    @Override
    public ResourceLocation getId() {
        return ID;
    }

    @Override
    public TriggerInstance createInstance(JsonObject json, ContextAwarePredicate predicate, DeserializationContext context) {
        int level = GsonHelper.getAsInt(json, "level", -1);
        return new TriggerInstance(predicate, level);
    }

    public void trigger(ServerPlayer player, int currentLevel) {
        this.trigger(player, instance -> instance.matches(currentLevel));
    }

    public static class TriggerInstance extends AbstractCriterionTriggerInstance {
        private final int requiredLevel;

        public TriggerInstance(ContextAwarePredicate predicate, int requiredLevel) {
            super(ID, predicate);
            this.requiredLevel = requiredLevel;
        }

        public boolean matches(int currentLevel) {
            return currentLevel >= this.requiredLevel;
        }

        @Override
        public JsonObject serializeToJson(SerializationContext context) {
            JsonObject json = super.serializeToJson(context);
            json.addProperty("level", this.requiredLevel);
            return json;
        }
    }
}
