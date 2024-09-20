package hua.huase.shanhaicontinent.world.lootables;

import com.google.common.collect.Sets;
import hua.huase.shanhaicontinent.SHMainBus;
import net.minecraft.resources.ResourceLocation;

import java.util.Collections;
import java.util.Set;

public class SHLootTables {

    private static final Set<ResourceLocation> LOCATIONS = Sets.newHashSet();
    private static final Set<ResourceLocation> IMMUTABLE_LOCATIONS = Collections.unmodifiableSet(LOCATIONS);
    public static final ResourceLocation gufengxiaowu0 = register("chests/gufengxiaowu0");

    private static ResourceLocation register(String pId) {
        return register(new ResourceLocation(SHMainBus.MOD_ID,pId));
    }

    private static ResourceLocation register(ResourceLocation pId) {
        if (LOCATIONS.add(pId)) {
            return pId;
        } else {
            throw new IllegalArgumentException(pId + " is already a registered built-in loot table");
        }
    }

    public static Set<ResourceLocation> all() {
        return IMMUTABLE_LOCATIONS;
    }
}
