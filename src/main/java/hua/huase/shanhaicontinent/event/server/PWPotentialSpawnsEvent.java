package hua.huase.shanhaicontinent.event.server;

import hua.huase.shanhaicontinent.SHMainBus;
import hua.huase.shanhaicontinent.init.EntityInit;
import net.minecraft.world.level.biome.MobSpawnSettings;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.level.LevelEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.List;
import java.util.Random;

import static hua.huase.shanhaicontinent.SHMainBus.random;

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
