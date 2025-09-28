package hua.huase.shanhaicontinent.world.biome;

import hua.huase.shanhaicontinent.SHMainBus;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.biome.Biome;

public class ModBiomes {
    public static final ResourceKey<Biome> TEST_BIOME = ResourceKey.create(Registries.BIOME,
            new ResourceLocation(SHMainBus.MOD_ID,"test_biome"));
    public static void boostrap(BootstapContext<Biome> context){
        context.register(TEST_BIOME,testBiome(context));
    }
    public static Biome testBiome(BootstapContext<Biome> context){
        return null;
    }
}
