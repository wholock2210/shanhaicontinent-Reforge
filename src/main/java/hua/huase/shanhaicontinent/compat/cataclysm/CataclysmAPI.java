package hua.huase.shanhaicontinent.compat.cataclysm;

import com.github.L_Ender.cataclysm.entity.AnimationMonster.BossMonsters.*;
import com.github.L_Ender.cataclysm.entity.AnimationMonster.BossMonsters.The_Leviathan.The_Leviathan_Entity;
import com.github.L_Ender.cataclysm.entity.AnimationMonster.Endermaptera_Entity;
import com.github.L_Ender.cataclysm.entity.AnimationMonster.The_Watcher_Entity;
import com.github.L_Ender.cataclysm.entity.Deepling.*;
import com.github.L_Ender.cataclysm.entity.InternalAnimationMonster.*;
import com.github.L_Ender.cataclysm.entity.InternalAnimationMonster.Draugar.Aptrgangr_Entity;
import com.github.L_Ender.cataclysm.entity.InternalAnimationMonster.Draugar.Draugr_Entity;
import com.github.L_Ender.cataclysm.entity.InternalAnimationMonster.Draugar.Elite_Draugr_Entity;
import com.github.L_Ender.cataclysm.entity.InternalAnimationMonster.Draugar.Royal_Draugr_Entity;
import com.github.L_Ender.cataclysm.entity.InternalAnimationMonster.IABossMonsters.Ancient_Remnant.Ancient_Remnant_Entity;
import com.github.L_Ender.cataclysm.entity.InternalAnimationMonster.IABossMonsters.Maledictus.Maledictus_Entity;
import com.github.L_Ender.cataclysm.entity.InternalAnimationMonster.IABossMonsters.NewNetherite_Monstrosity.Netherite_Monstrosity_Entity;
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


public class CataclysmAPI {
    public static int getNianxian(Entity entity, RandomSource random){
        //末影傀儡
        if (entity instanceof Ender_Golem_Entity) {
            return 100000 + random.nextInt(150000);
            //赤炎益魂
        }else if (entity instanceof Ignited_Revenant_Entity) {
            return 60000 + random.nextInt(100000);
            //珊瑚傀儡
        }else if (entity instanceof Coral_Golem_Entity) {
            return 100000 + random.nextInt(500000);
            //紫水晶巨蟹
        }else if (entity instanceof Amethyst_Crab_Entity) {
            return 100000 + random.nextInt(500000);
            //徘徊者
        }else if (entity instanceof The_Prowler_Entity) {
            return 100000 + random.nextInt(500000);
            //珊瑚巨像
        }else if (entity instanceof Coralssus_Entity) {
            return 160000 + random.nextInt(500000);
            //骸骨斩首者
        }else if (entity instanceof Kobolediator_Entity) {
            return 180000 + random.nextInt(100000);
            //瓦吉特
        }else if (entity instanceof Wadjet_Entity) {
            return 150000 + random.nextInt(500000);
            //冥行武弁
        }else if (entity instanceof Aptrgangr_Entity) {
            return 180000 + random.nextInt(100000);
            //末影守卫
        }else if (entity instanceof Ender_Guardian_Entity) {
            return 30000 + random.nextInt(1500000);
            //阎魔
        }else if (entity instanceof Ignis_Entity) {
            return 450000 + random.nextInt(1000000);
            //下届合金巨兽
        }else if (entity instanceof Netherite_Monstrosity_Entity) {
            return 500000 + random.nextInt(1000000);
            //先驱者
        }else if (entity instanceof The_Harbinger_Entity) {
            return 390000 + random.nextInt(1200000);
            //利维坦
        }else if (entity instanceof The_Leviathan_Entity) {
            return 200000 + random.nextInt(1000000);
            //远古移魂
        }else if (entity instanceof Ancient_Remnant_Entity) {
            return 200000 + random.nextInt(1200000);
            //咒翼灵骸
        }else if (entity instanceof Maledictus_Entity) {
            return 200000 + random.nextInt(1000000);
            //末影甲虫
        }else if (entity instanceof Endermaptera_Entity) {
            return 60000 + random.nextInt(150000);
            //渊灵
        }else if (entity instanceof Deepling_Entity) {
            return 1000 + random.nextInt(300000);
            //渊灵蛮兵
        }else if (entity instanceof Deepling_Brute_Entity) {
            return 80000 + random.nextInt(650000);
            //渊灵垂钓者
        }else if (entity instanceof Deepling_Angler_Entity) {
            return 1000 + random.nextInt(300000);
            //渊灵祭祀
        }else if (entity instanceof Deepling_Priest_Entity) {
            return 1000 + random.nextInt(450000);
            //蓑鲉
        }else if (entity instanceof Lionfish_Entity) {
            return 100 + random.nextInt(120000);
            //渊灵术士
        }else if (entity instanceof Deepling_Warlock_Entity) {
            return 1000 + random.nextInt(450000);
            //观测着
        }else if (entity instanceof The_Watcher_Entity) {
            return 50000 + random.nextInt(250000);
            //骷髅狗头人
        }else if (entity instanceof Kobolediator_Entity) {
            return 1000 + random.nextInt(250000);
            //炽焰狂魂
        }else if (entity instanceof Ignited_Berserker_Entity) {
            return 80000 + random.nextInt(650000);
            //再行魂师
        }else if (entity instanceof Draugr_Entity) {
            return 30000 + random.nextInt(2800000);
            //皇家魂师
        }else if (entity instanceof Royal_Draugr_Entity) {
            return 50000 + random.nextInt(300000);
            //精英魂师
        }else if (entity instanceof Elite_Draugr_Entity) {
            return 30000 + random.nextInt(320000);
            //上古益魂
        }else if (entity instanceof Ancient_Ancient_Remnant_Entity) {
            return 4000000 + random.nextInt(1000000);
        }else  if (entity instanceof EnderDragon) {
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
