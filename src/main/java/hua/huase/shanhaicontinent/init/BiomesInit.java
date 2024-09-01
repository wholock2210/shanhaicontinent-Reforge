package hua.huase.shanhaicontinent.init;

import hua.huase.shanhaicontinent.SHMainBus;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class BiomesInit {



    public static final ResourceKey<Biome> TEXT_BIOMES = register("text_biomes");


    private static ResourceKey<Biome> register(String string) {

        return ResourceKey.create(Registries.BIOME, new ResourceLocation(SHMainBus.MOD_ID,string));
    }
}
