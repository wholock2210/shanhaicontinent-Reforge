package hua.huase.shanhaicontinent.world.biomesource;

import com.google.common.collect.Sets;
import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import hua.huase.shanhaicontinent.SHMainBus;
import net.minecraft.core.*;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.resources.RegistryOps;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.biome.*;
import net.minecraft.world.level.levelgen.DensityFunction;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

import javax.annotation.Nullable;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Stream;

public class SHBiomeSource extends BiomeSource {
   public static final Codec<SHBiomeSource> CODEC = RecordCodecBuilder.create((p_255555_) -> {
      return p_255555_.group(RegistryOps.retrieveElement(Biomes.THE_VOID),
              RegistryOps.retrieveElement(Biomes.PLAINS),
              RegistryOps.retrieveElement(Biomes.END_MIDLANDS),
              RegistryOps.retrieveElement(Biomes.SMALL_END_ISLANDS),
              RegistryOps.retrieveElement(Biomes.END_BARRENS)
      ).apply(p_255555_, p_255555_.stable(SHBiomeSource::new));
   });
   private final Holder<Biome> end;
   private final Holder<Biome> highlands;
   private final Holder<Biome> midlands;
   private final Holder<Biome> islands;
   private final Holder<Biome> barrens;

   public static SHBiomeSource create(HolderGetter<Biome> p_256561_) {
      RegistryOps.retrieveElement(Biomes.THE_VOID);
      return new SHBiomeSource(p_256561_.getOrThrow(Biomes.THE_VOID), p_256561_.getOrThrow(Biomes.PLAINS), p_256561_.getOrThrow(Biomes.END_MIDLANDS), p_256561_.getOrThrow(Biomes.SMALL_END_ISLANDS), p_256561_.getOrThrow(Biomes.END_BARRENS));
   }

   private SHBiomeSource(Holder<Biome> p_220678_, Holder<Biome> p_220679_, Holder<Biome> p_220680_, Holder<Biome> p_220681_, Holder<Biome> p_220682_) {
      this.end = p_220678_;
      this.highlands = p_220679_;
      this.midlands = p_220680_;
      this.islands = p_220681_;
      this.barrens = p_220682_;
   }

   protected Stream<Holder<Biome>> collectPossibleBiomes() {
      return Stream.of(this.end, this.highlands, this.midlands, this.islands, this.barrens);
   }

   protected Codec<? extends BiomeSource> codec() {
      return CODEC;
   }

   public Holder<Biome> getNoiseBiome(int p_204292_, int p_204293_, int p_204294_, Climate.Sampler p_204295_) {
      if(p_204292_>200){
         return this.end;
      }if(p_204292_>400){
         return this.highlands;
      }if(p_204292_>600){
         return this.midlands;
      }if(p_204292_>800){
         return this.islands;
      }



      return this.midlands;
   }

   public static final DeferredRegister<Codec<? extends BiomeSource>> REGISTER = DeferredRegister.create(Registries.BIOME_SOURCE, SHMainBus.MOD_ID);
   public static final RegistryObject<Codec<SHBiomeSource>> SARCON = REGISTER.register("sh_biomesource", () -> SHBiomeSource.CODEC);



}