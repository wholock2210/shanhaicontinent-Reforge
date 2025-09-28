package hua.huase.shanhaicontinent.world.biome;

import hua.huase.shanhaicontinent.SHMainBus;
import net.minecraft.resources.ResourceLocation;
import terrablender.api.Regions;

public class ModTerrablender {
    public static void registerBioms(){
        Regions.register(new ModOverworldRegion(new ResourceLocation(SHMainBus.MOD_ID,"overworld"), 5));
    }
}
