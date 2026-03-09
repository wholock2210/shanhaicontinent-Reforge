package hua.huase.shanhaicontinent.event.server;

import hua.huase.shanhaicontinent.init.EntityInit;
import net.minecraft.world.level.biome.MobSpawnSettings;
import net.minecraftforge.event.level.LevelEvent;

import java.util.List;

//@Mod.EventBusSubscriber(modid = SHMainBus.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class PWPotentialSpawnsEvent {
//    @SubscribeEvent
    public static void levelTickEvent(LevelEvent.PotentialSpawns event){
        List<MobSpawnSettings.SpawnerData> spawnerDataList = event.getSpawnerDataList();
        if(spawnerDataList.isEmpty())return;
        MobSpawnSettings.SpawnerData spawnerData = new MobSpawnSettings.SpawnerData(EntityInit.HUNHE.get(), 10, 1, 5);
        event.addSpawnerData(spawnerData);

    }
}
