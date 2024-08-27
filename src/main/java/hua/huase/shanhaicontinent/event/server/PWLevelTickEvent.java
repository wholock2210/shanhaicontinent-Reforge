package hua.huase.shanhaicontinent.event.server;

import com.google.common.collect.Lists;
import hua.huase.shanhaicontinent.SHMainBus;
import hua.huase.shanhaicontinent.capability.AttrubuteAPI;
import hua.huase.shanhaicontinent.entity.hunhe.HunheEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ChunkHolder;
import net.minecraft.server.level.ServerChunkCache;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.NaturalSpawner;
import net.minecraft.world.level.chunk.ChunkSource;
import net.minecraft.world.level.chunk.LevelChunk;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.Collections;
import java.util.List;

import static hua.huase.shanhaicontinent.SHMainBus.random;

//@Mod.EventBusSubscriber(modid = SHMainBus.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class PWLevelTickEvent {
//    @SubscribeEvent
    public static void levelTickEvent(TickEvent.LevelTickEvent event){

        /*
        if(event.phase == TickEvent.Phase.END){
//            获取可刷新区块
            ServerChunkCache chunkSource =(ServerChunkCache) event.level.getChunkSource();
            int naturalSpawnChunkCount = chunkSource.distanceManager.getNaturalSpawnChunkCount();
            List<ChunkAndHolder> list = Lists.newArrayListWithCapacity(naturalSpawnChunkCount);
            Iterable<ChunkHolder> chunks = chunkSource.chunkMap.getChunks();
            for (ChunkHolder chunkholder : chunks) {
                LevelChunk levelchunk = chunkholder.getTickingChunk();
                if (levelchunk != null) {
                    list.add(new ChunkAndHolder(levelchunk, chunkholder));
                }
            }
//洗牌
            boolean flag2 = event.level.getGameRules().getBoolean(GameRules.RULE_DOMOBSPAWNING);
            Collections.shuffle(list);




            for(ChunkAndHolder serverchunkcache$chunkandholder : list) {
                LevelChunk levelchunk1 = serverchunkcache$chunkandholder.chunk;
//                刷怪
                ChunkPos pos = levelchunk1.getPos();
                int height = levelchunk1.getHeight();
                BlockPos blockPos = new BlockPos(pos.x, height, pos.z);
                HunheEntity.createHunhe(10,event.level,blockPos);
            }



        }
*/

    }

    static record ChunkAndHolder(LevelChunk chunk, ChunkHolder holder) {
    }

}
