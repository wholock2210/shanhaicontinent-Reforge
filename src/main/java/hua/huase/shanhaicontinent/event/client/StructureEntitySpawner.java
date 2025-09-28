package hua.huase.shanhaicontinent.event.client;

import hua.huase.shanhaicontinent.init.ModConfig;
import hua.huase.shanhaicontinent.register.ModEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.level.StructureManager;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.structure.BoundingBox;
import net.minecraft.world.level.levelgen.structure.Structure;
import net.minecraft.world.level.levelgen.structure.StructureStart;
import net.minecraft.world.phys.AABB;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.RegistryObject;

import java.util.*;

@Mod.EventBusSubscriber
public class StructureEntitySpawner {
    private static final ResourceLocation STRUCTURE_ID = new ResourceLocation("shanhaicontinent", "coldhoteye");
    private static final int MAX_ENTITIES = 3;
    private static int tickCounter = 0;
    private static final Random random = new Random();

    private static final List<RegistryObject<? extends EntityType<? extends Monster>>> ENTITY_TYPES = List.of(
            ModEntities.CHRYSANTHEMUM_TRUNCATUM,
            ModEntities.LOVERED,
            ModEntities.QILUOTULIP
    );

    @SubscribeEvent
    public static void onServerTick(TickEvent.ServerTickEvent event) {
        if (event.phase != TickEvent.Phase.END) return;

        tickCounter++;
        if (tickCounter < ModConfig.tickcoldhot.get()) return;
        tickCounter = 0;

        for (ServerLevel level : event.getServer().getAllLevels()) {
            Set<BlockPos> processedStructures = new HashSet<>();

            for (ServerPlayer player : level.players()) {
                BlockPos playerPos = player.blockPosition();
                StructureManager structureManager = level.structureManager();

                StructureStart foundStructureStart = null;

                outer:
                for (int dx = -10; dx <= 10; dx++) {
                    for (int dz = -10; dz <= 10; dz++) {
                        BlockPos checkPos = playerPos.offset(dx * 16, 0, dz * 16);
                        Optional<? extends Structure> structureOpt = level.registryAccess()
                                .registryOrThrow(Registries.STRUCTURE)
                                .getOptional(STRUCTURE_ID);

                        if (structureOpt.isEmpty()) continue;

                        Structure structure = structureOpt.get();
                        StructureStart structureStart = structureManager.getStructureAt(checkPos, structure);

                        if (structureStart != null && structureStart.isValid()) {
                            foundStructureStart = structureStart;
                            break outer;
                        }
                    }
                }

                if (foundStructureStart == null) continue;

                BoundingBox box = foundStructureStart.getBoundingBox();
                BlockPos structureKey = new BlockPos(box.minX() >> 4, 0, box.minZ() >> 4);
                if (processedStructures.contains(structureKey)) continue;
                processedStructures.add(structureKey);

                if (playerPos.distSqr(new BlockPos(box.getCenter())) > 100 * 100) continue;
                if (random.nextFloat() > 0.6f) continue;

                RegistryObject<? extends EntityType<? extends Monster>> selectedEntity = ENTITY_TYPES.get(random.nextInt(ENTITY_TYPES.size()));
                EntityType<? extends Monster> entityType = selectedEntity.get();

                int count = level.getEntities(entityType,
                        new AABB(box.minX(), box.minY(), box.minZ(), box.maxX(), box.maxY(), box.maxZ()).inflate(32),
                        e -> true).size();

                if (count >= MAX_ENTITIES) continue;
                if (count == 1 && random.nextFloat() > 0.7f) continue;
                if (count == 2 && random.nextFloat() > 0.5f) continue;

                BlockPos spawnPos = findValidSpawnPosition(level, box);
                if (spawnPos != null) {
                    Monster mob = entityType.create(level);
                    if (mob != null) {
                        mob.setPos(spawnPos.getX() + 0.5, spawnPos.getY() + 1, spawnPos.getZ() + 0.5);
                        level.addFreshEntity(mob);
                    }
                }
            }
        }
    }

    private static BlockPos findValidSpawnPosition(ServerLevel level, BoundingBox box) {
        for (int i = 0; i < 50; i++) {
            int x = random.nextInt(box.maxX() - box.minX() + 1) + box.minX();
            int z = random.nextInt(box.maxZ() - box.minZ() + 1) + box.minZ();
            int y = level.getHeight(Heightmap.Types.WORLD_SURFACE, x, z) - 1;

            if (y > 100) continue;

            BlockPos groundPos = new BlockPos(x, y, z);
            if (level.getBlockState(groundPos).is(Blocks.GRASS_BLOCK)) {
                boolean spaceClear = true;
                for (int dy = 1; dy <= 4; dy++) {
                    if (!level.getBlockState(groundPos.above(dy)).isAir()) {
                        spaceClear = false;
                        break;
                    }
                }
                if (spaceClear) return groundPos;
            }
        }
        return null;
    }
}