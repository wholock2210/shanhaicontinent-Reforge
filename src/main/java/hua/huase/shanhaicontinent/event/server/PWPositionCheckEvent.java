package hua.huase.shanhaicontinent.event.server;

import hua.huase.shanhaicontinent.SHMainBus;
import hua.huase.shanhaicontinent.entity.hunhe.HunheEntity;
import hua.huase.shanhaicontinent.init.EntityInit;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.biome.MobSpawnSettings;
import net.minecraftforge.event.entity.living.MobSpawnEvent;
import net.minecraftforge.event.level.LevelEvent;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.List;

import static hua.huase.shanhaicontinent.SHMainBus.random;

@Mod.EventBusSubscriber(modid = SHMainBus.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class PWPositionCheckEvent {
    @SubscribeEvent
    public static void onPositionCheck(MobSpawnEvent.PositionCheck event){
        if(random.nextInt(10)!=0)return;
        int i = random.nextInt(5);
        HunheEntity.createHunhe(i+1, event.getLevel().getLevel(), new BlockPos((int) event.getX(), (int) event.getY(), (int) event.getZ()));


    }


}
