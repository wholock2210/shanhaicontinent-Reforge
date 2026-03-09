package hua.huase.shanhaicontinent.compat.sophisticatedbackpacks;

import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;

public class SophisticatedbackpacksAPI {

    public static int getNianxian(Entity entity, RandomSource random, int index) {
        if(entity.getTags().contains("spawnedWithBackpack")){
            return (int) (index*1.5);
        }else {
            return index;
        }
    }
}
