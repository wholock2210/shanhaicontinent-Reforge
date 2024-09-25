package hua.huase.shanhaicontinent.capability.monsterattribute;

import hua.huase.shanhaicontinent.SHMainBus;
import hua.huase.shanhaicontinent.compat.sophisticatedbackpacks.SophisticatedbackpacksAPI;
import hua.huase.shanhaicontinent.compat.twilightforest.TwilightforestAPI;
import net.minecraft.resources.ResourceKey;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.monster.Enemy;
import net.minecraft.world.level.Level;

public interface MonsterCapabilityAPI {
    public static MonsterAttributeCapability genMonsterCapability(Entity entity){
        RandomSource random = entity.level().random;
        int index = 0;
        if(SHMainBus.twilightforest_compat){
            index = TwilightforestAPI.getNianxian(entity,random);
        } else if (true) {
            index = genLevel(entity,random);
        }
//        index=genLevel(entity);

        return new MonsterAttributeCapability(index);
    }

    public static int genLevel(Entity entity, RandomSource random){

        int index = 0;

        if(entity instanceof Mob && entity instanceof Enemy){
            ResourceKey<Level> dimension = entity.level().dimension();
            if(dimension == Level.OVERWORLD){
//                1,331
                int i = random.nextInt(3) + 1;
                index = random.nextInt((int) Math.pow(14,i));

            }else if(dimension == Level.NETHER){
//                160,000+100
                int i = random.nextInt(4) + 1;
                index = random.nextInt((int) Math.pow(20,i))+100;
            }else if(dimension == Level.END){
//             531,441+100
                int i = random.nextInt(6) + 1;
                index = Math.min(random.nextInt((int) Math.pow(9,i)),1000000)+100;
            }else {
//             531,441+100
                int i = SHMainBus.random.nextInt(6) + 1;
                index = Math.min(SHMainBus.random.nextInt((int) Math.pow(9,i)),1000000)+100;
            }


            if(((Mob) entity).getMaxHealth() >60){
                if(SHMainBus.twilightforest_compat){
                    index = SophisticatedbackpacksAPI.getNianxian(entity,random,index);
                }else {
                    index = (int) (1+1000000*Math.log10(((Mob) entity).getMaxHealth()));
                }
            }
        }else {
            index = random.nextInt(10+1);
        }
        return index;
    }
    public static int genjvli(Entity entity){

//        entity.level().getSharedSpawnPos()
        int index = 0;
        double x = entity.getX();
        double z = entity.getZ();
        double sqrt = Math.sqrt(x * x + z * z);
        if(sqrt>30000){
            index = SHMainBus.random.nextInt((int) (1536000+sqrt*10));
            if(index>=1000000) index =1000000;
        }else if(sqrt>26000){
            index = SHMainBus.random.nextInt(768000);
        }else if(sqrt>22000){
            index = SHMainBus.random.nextInt(384000);
        }else if(sqrt>18000){
            index = SHMainBus.random.nextInt(192000);
        }else if(sqrt>15000){
            index = SHMainBus.random.nextInt(96000);
        }else if(sqrt>12000){
            index = SHMainBus.random.nextInt(48000);
        }else if(sqrt>10000){
            index = SHMainBus.random.nextInt(24000);
        }else if(sqrt>8000){
            index = SHMainBus.random.nextInt(12000);
        }else if(sqrt>6000){
            index = SHMainBus.random.nextInt(6000);
        }else if(sqrt>4000){
            index = SHMainBus.random.nextInt(3000);
        }else if(sqrt>3000){
            index = SHMainBus.random.nextInt(1000);
        }else if(sqrt>2000){
            index = SHMainBus.random.nextInt(500);
        }else if (sqrt>1000){
            index = SHMainBus.random.nextInt(200);
        }else {
            index = SHMainBus.random.nextInt(120);
        }
        return index;
    }
}
