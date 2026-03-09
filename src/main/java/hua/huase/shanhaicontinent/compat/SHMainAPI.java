package hua.huase.shanhaicontinent.compat;

import hua.huase.shanhaicontinent.capability.monsterattribute.MonsterCapabilityAPI;
import hua.huase.shanhaicontinent.entity.shenjieentity.*;
import hua.huase.shanhaicontinent.entity.shenjieentity.severentity.demonking.DemonKingEntity;
import hua.huase.shanhaicontinent.entity.shenjieentity.severentity.evileyetyrant.EvileyetyrantEntity;
import hua.huase.shanhaicontinent.entity.shenjieentity.severentity.frostprison.FrostPrisonEntity;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.animal.IronGolem;
import net.minecraft.world.entity.boss.enderdragon.EnderDragon;
import net.minecraft.world.entity.boss.wither.WitherBoss;
import net.minecraft.world.entity.monster.warden.Warden;

public class SHMainAPI {
    public static int getNianxian(Entity entity, RandomSource random) {
        //末影龙
        if (entity instanceof EnderDragon) {
            return 60000 + random.nextInt(1500000);
            //凋零
        } else if (entity instanceof WitherBoss) {
            return 10000 + random.nextInt(1100000);
            //魔物
        } else if (entity instanceof MoWuEntity) {
            return 50000 + random.nextInt(990000);
            //天罚审判者
        } else if (entity instanceof TianFaEntity) {
            return 10000000 + random.nextInt(6000000);
            //仙草
        }else if (entity instanceof XiancaoEntity){
            return 10 +random.nextInt(1100000);
            //相思断肠红
        }else if (entity instanceof LoveRedEntity){
            return 10 +random.nextInt(1100000);
            //绮罗郁金香
        }else if (entity instanceof QiluotulipEntity) {
            return 10 + random.nextInt(1100000);
            //监守者
        }else if (entity instanceof Warden){
            return 100000 + random.nextInt(800000);
            //斜眼暴君
        }else if (entity instanceof EvileyetyrantEntity){
            return 100000 + random.nextInt(600000);
            //魔皇
        }else if (entity instanceof DemonKingEntity){
            return 1000000 + random.nextInt(8000000);
            //霜狱
        }else if (entity instanceof FrostPrisonEntity){
            return 10000000 + random.nextInt(8000000);
            //铁傀儡
        }else if (entity instanceof IronGolem) {
            return 9000 + random.nextInt(100000);
        } else {
            return MonsterCapabilityAPI.genLevel(entity, random);
        }
    }
}
