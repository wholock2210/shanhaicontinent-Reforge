package hua.huase.shanhaicontinent.capability.monsterattribute;

import hua.huase.shanhaicontinent.capability.CapabilityAttributeBase;
import net.minecraft.nbt.CompoundTag;
import net.minecraftforge.common.util.INBTSerializable;

import static hua.huase.shanhaicontinent.SHMainBus.random;

public class MonsterAttributeCapability extends CapabilityAttributeBase implements INBTSerializable<CompoundTag> {
//    年限

    private int nianxian;

    private boolean shenci;

    private int zidingyi;

    public MonsterAttributeCapability() {
        this.nianxian = 0;
        this.zidingyi = 0;
    }

    public static MonsterAttributeCapability createWithNianxian(int nianxian) {
        return new MonsterAttributeCapability(nianxian); // 直接使用原有构造函数
    }

    public MonsterAttributeCapability(int nianxian) {
        super();
        this.nianxian = nianxian;

        if (nianxian < 100) {
            float g = 2 + (float) nianxian / 10  + random.nextInt(10) ;
            float l = 10 + (float) nianxian / 20;

            super.setWugong(g);
            super.setWufang(g / 3);
            super.setWuchuan(g / 5);
            super.setZhenshang(g / 10);
            super.setMaxshengming(10 * g);
            super.setShengming(10 * g);
            super.setBaojilv(l);
            super.setBaojishanghai(5 * l);
            super.setKangbao(3 * l);
            super.setXixue((l / 4));
            super.setMingzhong(l);
            super.setShanbi(l);
            super.setShengminghuifu(0);

        } else if (nianxian < 1000) {

            float g = 50 + (float) nianxian / 10  + random.nextInt(50) ;
            float l = 20 + (float) nianxian / 1000 * 10;

            super.setWugong(g * 2);
            super.setWufang(g / 3);
            super.setWuchuan(g / 5);
            super.setZhenshang(g / 10);
            super.setMaxshengming(20 * g);
            super.setShengming(10 * g);
            super.setBaojilv(l);
            super.setBaojishanghai(5 * l);
            super.setKangbao(3 * l);
            super.setXixue((l / 4));
            super.setMingzhong(l);
            super.setShanbi(l);
            super.setShengminghuifu(0);

        } else if (nianxian < 10000) {

            float g = 250 + (float) nianxian / 20  + random.nextInt(250) ;
            float l = 30 + (float) nianxian / 10000 * 10;

            super.setWugong(g * 2);
            super.setWufang(g / 3);
            super.setWuchuan(g / 5);
            super.setZhenshang(g / 10);
            super.setMaxshengming(30 * g);
            super.setShengming(10 * g);
            super.setBaojilv(l);
            super.setBaojishanghai(5 * l);
            super.setKangbao(3 * l);
            super.setXixue((l / 4));
            super.setMingzhong(l);
            super.setShanbi(l);
            super.setShengminghuifu(0);
        } else if (nianxian < 100000) {
            float g = 750 + (float) nianxian / 67  + random.nextInt(750) ;
            float l = 40 + (float) nianxian / 100000 * 10;

            super.setWugong(g * 3);
            super.setWufang(g / 4);
            super.setWuchuan(g / 5);
            super.setZhenshang(g / 10);
            super.setMaxshengming(40 * g);
            super.setShengming(10 * g);
            super.setBaojilv(l);
            super.setBaojishanghai(5 * l);
            super.setKangbao(3 * l);
            super.setXixue((l / 4));
            super.setMingzhong(l);
            super.setShanbi(l);
            super.setShengminghuifu(0);
        } else if (nianxian < 1000000) {

            float g = 3750 + (float) nianxian / 133  + random.nextInt(3750) ;
            float l = 50 + (float) nianxian / 1000000 * 10;

            super.setWugong(g * 5);
            super.setWufang(g / 5);
            super.setWuchuan(g / 5);
            super.setZhenshang(g / 10);
            super.setMaxshengming(50 * g);
            super.setShengming(10 * g);
            super.setBaojilv(l);
            super.setBaojishanghai(5 * l);
            super.setKangbao(3 * l);
            super.setXixue((l / 4));
            super.setMingzhong(l);
            super.setShanbi(l);
            super.setShengminghuifu(0);
        } else if (nianxian < 10000000) {

            float g = 13750 + (float) nianxian / 681  + random.nextInt(13750) ;
            float l = 100 + (float) nianxian / 10000000 * 10;

            super.setWugong(g * 8);
            super.setWufang(g / 7);
            super.setWuchuan(g / 6);
            super.setZhenshang(g / 12);
            super.setMaxshengming(100 * g);
            super.setShengming(15 * g);
            super.setBaojilv(l);
            super.setBaojishanghai(6 * l);
            super.setKangbao(4 * l);
            super.setXixue((l / 5));
            super.setMingzhong(l);
            super.setShanbi(l);
            super.setShengminghuifu(0);
        } else if (nianxian >= 10000000) {
            float g = 23750 + (float) nianxian / 788  + random.nextInt(23750) ;
            float l = 100 + (float) nianxian / 10000000 * 10;

            super.setWugong(g * 8);
            super.setWufang(g / 7);
            super.setWuchuan(g / 6);
            super.setZhenshang(g / 12);
            super.setMaxshengming(100 * g);
            super.setShengming(15 * g);
            super.setBaojilv(l);
            super.setBaojishanghai(6 * l);
            super.setKangbao(4 * l);
            super.setXixue((l / 5));
            super.setMingzhong(l);
            super.setShanbi(l);
            super.setShengminghuifu(0);
        }
    }

    @Override
    public CompoundTag serializeNBT() {

        CompoundTag nbt = super.serializeNBT();
        nbt.putInt("nianxian", nianxian);
        nbt.putBoolean("shenci", shenci);
        nbt.putInt("zidingyi", zidingyi);
        return nbt;
    }

    @Override
    public void deserializeNBT(CompoundTag nbt) {
        super.deserializeNBT(nbt);
        this.nianxian = nbt.getInt("nianxian");
        this.shenci = nbt.getBoolean("shenci");
        this.zidingyi = nbt.getInt("zidingyi");

    }


    public int getNianxian() {
        return nianxian;
    }

    public void setNianxian(int nianxian) {
        this.nianxian = nianxian;
    }

    public int getzidingyi() {
        return zidingyi;
    }
    public void setzidingyi(int zidingyi) {
        this.zidingyi = zidingyi;
    }

    public boolean isShenci() {
        return shenci;
    }

    public void setShenci(boolean shenci) {
        this.shenci = shenci;
    }
}
