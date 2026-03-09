package hua.huase.shanhaicontinent.compat.twilightforest;

import hua.huase.shanhaicontinent.capability.monsterattribute.MonsterCapabilityAPI;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import twilightforest.entity.boss.*;
import twilightforest.entity.monster.*;

public class TwilightforestAPI {
    public static int getNianxian(Entity entity, RandomSource random) {
        //娜迦
        if (entity instanceof Naga) {
            return 500000 + random.nextInt(20000);
            //巫妖
        } else if (entity instanceof Lich) {
            return 700000 + random.nextInt(20000);
            //巫妖的仆人 死灵书
        } else if (entity instanceof LichMinion || entity instanceof DeathTome) {
            return 3000 + random.nextInt(3000);
            //米诺菇
        } else if (entity instanceof Minoshroom) {
            return 700000 + random.nextInt(200000);
            //米诺陶 迷宫史莱姆、粘液甲虫、喷火甲虫、夹虫、
        } else if (entity instanceof Minotaur || entity instanceof MazeSlime || entity instanceof SlimeBeetle || entity instanceof FireBeetle || entity instanceof PinchBeetle) {
            return 6000 + random.nextInt(6000);
            //九头蛇本体
        } else if (entity instanceof Hydra) {
            return 1000000 + random.nextInt(300);
            //幻影骑士
        } else if (entity instanceof KnightPhantom) {
            return 700000 + random.nextInt(200000);
            //红帽哥布林、红帽地精、哥布林、链锤哥布林、寄居蟹、哥布林骑士(上身)、哥布林骑士(下身)
        } else if (entity instanceof Redcap || entity instanceof RedcapSapper || entity instanceof Kobold || entity instanceof BlockChainGoblin || entity instanceof HelmetCrab || entity instanceof UpperGoblinKnight || entity instanceof LowerGoblinKnight) {
            return 12000 + random.nextInt(10000);
            //暮色恶魂
        } else if (entity instanceof UrGhast) {
            return 1300000 + random.nextInt(400000);
            //高塔铁傀儡 高塔螟 (Towerwood Borer)高塔幽灵 (Carminite Ghastguard)寄生虫 (Carminite Broodling)
        } else if (entity instanceof CarminiteGolem || entity instanceof TowerwoodBorer || entity instanceof CarminiteGhastguard || entity instanceof TowerBroodling) {
            return 24000 + random.nextInt(10000);
            //雪怪首领
        } else if (entity instanceof AlphaYeti) {
            return 700000 + random.nextInt(200000);
            //雪怪
        } else if (entity instanceof Yeti) {
            return 24000 + random.nextInt(10000);
            //冰雪女王
        } else if (entity instanceof SnowQueen) {
            return 1500000 + random.nextInt(100000);
            //冰雪守卫 (Snow Guardian)
        } else if (entity instanceof SnowGuardian) {
            return 60000 + random.nextInt(50000);
            //洞穴巨魔 (Cave Troll)
        } else if (entity instanceof Troll) {
            return 700000 + random.nextInt(200000);
            //巨人矿工 (Giant Miner)武装巨人 (Armored Giant)
        } else if (entity instanceof GiantMiner || entity instanceof ArmoredGiant) {
            return 50000 + random.nextInt(1100000);
            //信徒【未实现】 (Adherent [NYI])信徒【未实现】 (Adherent [NYI])
        } else if (entity instanceof Adherent) {
            return 1000000 + random.nextInt(500000);
        }else if (entity instanceof LoyalZombie) {
            return 9000 + random.nextInt(30000);
        } else {
            return MonsterCapabilityAPI.genLevel(entity, random);
        }
    }
}