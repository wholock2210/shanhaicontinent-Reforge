package hua.huase.shanhaicontinent.capability.monsterattribute;

import hua.huase.shanhaicontinent.SHMainBus;
import hua.huase.shanhaicontinent.capability.itemattribute.ItemAttributeCapability;
import hua.huase.shanhaicontinent.capability.itemattribute.ItemAttributeCapabilityProvider;
import hua.huase.shanhaicontinent.capability.playerattribute.PlayerAttributeCapability;
import hua.huase.shanhaicontinent.capability.playerattribute.PlayerAttributeCapabilityProvider;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.monster.Enemy;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.alchemy.Potion;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.ItemStackHandler;

public interface MonsterCapabilityAPI {
    public static MonsterAttributeCapability genMonsterCapability(Entity entity){
        int index = 0;
        if(entity instanceof Mob && entity instanceof Enemy){
            ResourceKey<Level> dimension = entity.level().dimension();
            if(dimension == Level.OVERWORLD){
                index = genOverworld(entity);

                if(((Mob) entity).getMaxHealth() >60){
                    index = (int) (10*index*Math.log10(((Mob) entity).getMaxHealth()));
                }

            }else {
                index = SHMainBus.random.nextInt(1500+1);

                if(((Mob) entity).getMaxHealth() >60){
                    index = (int) (1+1000000*Math.log10(((Mob) entity).getMaxHealth()));
                }

            }
        }else {
             index = SHMainBus.random.nextInt(100+1);
        }
        return new MonsterAttributeCapability(index);
    }

    private static int genOverworld(Entity entity){

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
