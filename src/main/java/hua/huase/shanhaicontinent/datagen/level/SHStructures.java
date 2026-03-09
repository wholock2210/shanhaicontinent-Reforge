package hua.huase.shanhaicontinent.datagen.level;

import hua.huase.shanhaicontinent.SHMainBus;
import hua.huase.shanhaicontinent.init.EntityInit;
import hua.huase.shanhaicontinent.world.structure.gufengxiaowu.GufengxiaowuStructure;
import hua.huase.shanhaicontinent.world.structure.jvxiange.JvxiangeStructure;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.random.WeightedRandomList;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.level.biome.MobSpawnSettings;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.structure.*;
import net.minecraft.world.level.levelgen.structure.placement.RandomSpreadStructurePlacement;
import net.minecraft.world.level.levelgen.structure.placement.RandomSpreadType;

import java.util.Map;

public class SHStructures {

    public static final ResourceKey<Structure> gufengxiaowu01 = registerKey("gufengxiaowu01");
    public static final ResourceKey<StructureSet> gufengxiaowu01_set = registerSetKey("gufengxiaowu01");

    public static final ResourceKey<Structure> jvxiange = registerKey("jvxiange");
    public static final ResourceKey<StructureSet> jvxiange_set = registerSetKey("jvxiange");


    public static ResourceKey<Structure> registerKey(String name) {
        return ResourceKey.create(Registries.STRUCTURE, new ResourceLocation(SHMainBus.MOD_ID, name));
    }

    public static  ResourceKey<StructureSet> registerSetKey(String name) {
        return ResourceKey.create(Registries.STRUCTURE_SET, new ResourceLocation(SHMainBus.MOD_ID, name));
    }


    public static void bootstrapSTRUCTURE(BootstapContext<Structure> context) {
        context.register(gufengxiaowu01, new GufengxiaowuStructure(new Structure.StructureSettings(
                        context.lookup(Registries.BIOME).getOrThrow(SHBiomeTagGenerator.SH_GUFENGXIAOWU),
                        Map.of(MobCategory.MONSTER, new StructureSpawnOverride(
                                StructureSpawnOverride.BoundingBoxType.STRUCTURE,
                                WeightedRandomList.create(new MobSpawnSettings.SpawnerData(EntityInit.hunmin.get(), 0, 1, 2))),
                                MobCategory.UNDERGROUND_WATER_CREATURE,
                                new StructureSpawnOverride(StructureSpawnOverride.BoundingBoxType.STRUCTURE, MobSpawnSettings.EMPTY_MOB_LIST),
                                MobCategory.AXOLOTLS,
                                new StructureSpawnOverride(StructureSpawnOverride.BoundingBoxType.STRUCTURE, MobSpawnSettings.EMPTY_MOB_LIST)
                        ),
                        GenerationStep.Decoration.SURFACE_STRUCTURES,
                        TerrainAdjustment.BEARD_BOX
                    )
                )
        );
        context.register(jvxiange, new JvxiangeStructure(new Structure.StructureSettings(
                        context.lookup(Registries.BIOME).getOrThrow(SHBiomeTagGenerator.SH_GUFENGXIAOWU),
                        Map.of(MobCategory.MONSTER, new StructureSpawnOverride(
                                StructureSpawnOverride.BoundingBoxType.STRUCTURE,
                                WeightedRandomList.create(new MobSpawnSettings.SpawnerData(EntityInit.hunmin.get(), 1, 1, 2))),
                                MobCategory.UNDERGROUND_WATER_CREATURE,
                                new StructureSpawnOverride(StructureSpawnOverride.BoundingBoxType.STRUCTURE, MobSpawnSettings.EMPTY_MOB_LIST),
                                MobCategory.AXOLOTLS,
                                new StructureSpawnOverride(StructureSpawnOverride.BoundingBoxType.STRUCTURE, MobSpawnSettings.EMPTY_MOB_LIST)
                        ),
                        GenerationStep.Decoration.RAW_GENERATION,
                        TerrainAdjustment.NONE
                    )
                )
        );
    }

    public static <T> void bootstrapSTRUCTURE_SET(BootstapContext<StructureSet> tBootstapContext) {

        HolderGetter<Structure> holdergetter = tBootstapContext.lookup(Registries.STRUCTURE);
        tBootstapContext.register(gufengxiaowu01_set, new StructureSet(holdergetter.getOrThrow(gufengxiaowu01), new RandomSpreadStructurePlacement(20, 4, RandomSpreadType.TRIANGULAR, 428149644)));
        tBootstapContext.register(jvxiange_set, new StructureSet(holdergetter.getOrThrow(jvxiange), new RandomSpreadStructurePlacement(34, 8, RandomSpreadType.TRIANGULAR, 4281496)));

    }
}
