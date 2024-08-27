package hua.huase.shanhaicontinent.capability.monsterattribute;

import hua.huase.shanhaicontinent.capability.CapabilityAttributeBase;
import net.minecraft.nbt.CompoundTag;
import net.minecraftforge.common.util.INBTSerializable;

import static hua.huase.shanhaicontinent.SHMainBus.random;

public class MonsterAttributeCapability extends CapabilityAttributeBase implements INBTSerializable<CompoundTag> {
//    年限

    private int nianxian;

    public MonsterAttributeCapability(){
        this.nianxian = 0;
    }
    public MonsterAttributeCapability(int nianxian) {
        super();
        this.nianxian = nianxian;


        if(nianxian<100){
            float g = 10 + (float) nianxian / 5 + random.nextInt(10);
            float l= 10 + (float) nianxian / 100 * 10 ;

            super.setWugong(g);
            super.setWufang(g/3);
            super.setWuchuan(g/15);
            super.setZhenshang(g/10);
            super.setMaxshengming(10*g);
            super.setShengming(10*g);
            super.setBaojilv(l);
            super.setBaojishanghai(5*l);
            super.setKangbao(3*l);
            super.setXixue((l/4));
            super.setMingzhong(l);
            super.setShanbi(l);
            super.setShengminghuifu(0);

        }else if(nianxian<1000){

            float g = 50 + (float) nianxian / 10 + random.nextInt(50);
            float l= 20 + (float) nianxian / 1000 * 10 ;

            super.setWugong(g);
            super.setWufang(g/3);
            super.setWuchuan(g/15);
            super.setZhenshang(g/10);
            super.setMaxshengming(20*g);
            super.setShengming(10*g);
            super.setBaojilv(l);
            super.setBaojishanghai(5*l);
            super.setKangbao(3*l);
            super.setXixue((l/4));
            super.setMingzhong(l);
            super.setShanbi(l);
            super.setShengminghuifu(0);

        }else if(nianxian<10000){

            float g = 250 + (float) nianxian / 20 + random.nextInt(250);
            float l= 30 + (float) nianxian / 10000 * 10 ;

            super.setWugong(g);
            super.setWufang(g/3);
            super.setWuchuan(g/15);
            super.setZhenshang(g/10);
            super.setMaxshengming(30*g);
            super.setShengming(10*g);
            super.setBaojilv(l);
            super.setBaojishanghai(5*l);
            super.setKangbao(3*l);
            super.setXixue((l/4));
            super.setMingzhong(l);
            super.setShanbi(l);
            super.setShengminghuifu(0);
        }else if(nianxian<100000){
            float g = 750 + (float) nianxian / 67 + random.nextInt(750);
            float l= 40 + (float) nianxian / 100000 * 10 ;

            super.setWugong(g);
            super.setWufang(g/3);
            super.setWuchuan(g/15);
            super.setZhenshang(g/10);
            super.setMaxshengming(40*g);
            super.setShengming(10*g);
            super.setBaojilv(l);
            super.setBaojishanghai(5*l);
            super.setKangbao(3*l);
            super.setXixue((l/4));
            super.setMingzhong(l);
            super.setShanbi(l);
            super.setShengminghuifu(0);
        }else if(nianxian<1000000){

            float g = 3750 + (float) nianxian / 133 + random.nextInt(3750);
            float l= 50 + (float) nianxian / 1000000 * 10 ;

            super.setWugong(g);
            super.setWufang(g/3);
            super.setWuchuan(g/15);
            super.setZhenshang(g/10);
            super.setMaxshengming(50*g);
            super.setShengming(10*g);
            super.setBaojilv(l);
            super.setBaojishanghai(5*l);
            super.setKangbao(3*l);
            super.setXixue((l/4));
            super.setMingzhong(l);
            super.setShanbi(l);
            super.setShengminghuifu(0);
        }else if(nianxian>=1000000){
            float g = 18750 + (float) nianxian / 300 + random.nextInt(18750);
            float l= 80 ;

            super.setWugong(g);
            super.setWufang(g/3);
            super.setWuchuan(g/15);
            super.setZhenshang(g/10);
            super.setMaxshengming(60*g);
            super.setShengming(10*g);
            super.setBaojilv(l);
            super.setBaojishanghai(5*l);
            super.setKangbao(3*l);
            super.setXixue((l/4));
            super.setMingzhong(l);
            super.setShanbi(l);
            super.setShengminghuifu(0);
        }

    }



    @Override
    public CompoundTag serializeNBT() {

        CompoundTag nbt = super.serializeNBT();
        nbt.putInt("nianxian", nianxian);
        return nbt;
    }

    @Override
    public void deserializeNBT(CompoundTag nbt) {
        super.deserializeNBT(nbt);
        this.nianxian =nbt.getInt("nianxian");

    }


    public int getNianxian() {
        return nianxian;
    }

    public void setNianxian(int nianxian) {
        this.nianxian = nianxian;
    }
}
