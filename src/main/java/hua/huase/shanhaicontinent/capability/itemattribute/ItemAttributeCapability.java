package hua.huase.shanhaicontinent.capability.itemattribute;

import hua.huase.shanhaicontinent.capability.CapabilityAttributeBase;
import net.minecraft.nbt.CompoundTag;
import net.minecraftforge.common.util.INBTSerializable;

import static hua.huase.shanhaicontinent.SHMainBus.random;

public class ItemAttributeCapability extends CapabilityAttributeBase implements INBTSerializable<CompoundTag> {
//    年限

    private int nianxian;

    public ItemAttributeCapability(){
        super();
    }


    public void toUpdateNianxian(int nianxian){
        this.nianxian = nianxian;

        if(nianxian<100){
            float g = (10 + (float) nianxian / 5 + random.nextInt(10))*0.1f;
            float l= 1 + nianxian / 100f ;

            super.setWugong(g);
            super.setWufang(g/3);
            super.setWuchuan(g/3);
            super.setZhenshang(g/10);
            super.setMaxshengming(10*g);
            super.setShengming(10*g);
            super.setBaojilv(l);
            super.setBaojishanghai(5*l);
            super.setKangbao(3*l);
            super.setXixue((l/4));
            super.setMingzhong(l);
            super.setShanbi(l);

        }else if(nianxian<1000){

            float g = (50 + (float) nianxian / 10 + random.nextInt(50))*0.1f;
            float l= 2 + nianxian / 1000f ;

            super.setWugong(g);
            super.setWufang(g/3);
            super.setWuchuan(g/3);
            super.setZhenshang(g/10);
            super.setMaxshengming(10*g);
            super.setShengming(10*g);
            super.setBaojilv(l);
            super.setBaojishanghai(5*l);
            super.setKangbao(3*l);
            super.setXixue((l/4));
            super.setMingzhong(l);
            super.setShanbi(l);

        }else if(nianxian<10000){

            float g = (250 + (float) nianxian / 20 + random.nextInt(250))*0.1f;
            float l= 3 + nianxian / 10000f ;

            super.setWugong(g);
            super.setWufang(g/3);
            super.setWuchuan(g/3);
            super.setZhenshang(g/10);
            super.setMaxshengming(10*g);
            super.setShengming(10*g);
            super.setBaojilv(l);
            super.setBaojishanghai(5*l);
            super.setKangbao(3*l);
            super.setXixue((l/4));
            super.setMingzhong(l);
            super.setShanbi(l);
        }else if(nianxian<100000){
            float g = (750 + (float) nianxian / 67 + random.nextInt(750))*0.1f;
            float l= 4 + nianxian / 100000f ;

            super.setWugong(g);
            super.setWufang(g/3);
            super.setWuchuan(g/3);
            super.setZhenshang(g/10);
            super.setMaxshengming(10*g);
            super.setShengming(10*g);
            super.setBaojilv(l);
            super.setBaojishanghai(5*l);
            super.setKangbao(3*l);
            super.setXixue((l/4));
            super.setMingzhong(l);
            super.setShanbi(l);
        }else if(nianxian<1000000){

            float g = (3750 + (float) nianxian / 133 + random.nextInt(3750))*0.1f;
            float l= 5 + nianxian / 1000000f ;

            super.setWugong(g);
            super.setWufang(g/3);
            super.setWuchuan(g/3);
            super.setZhenshang(g/10);
            super.setMaxshengming(10*g);
            super.setShengming(10*g);
            super.setBaojilv(l);
            super.setBaojishanghai(5*l);
            super.setKangbao(3*l);
            super.setXixue((l/4));
            super.setMingzhong(l);
            super.setShanbi(l);
        } else if(nianxian<10000000){

            float g = (7750 + (float) nianxian / 700 + random.nextInt(13750))*0.1f;
            float l= 7 + nianxian / 10000000f ;

            super.setWugong(g);
            super.setWufang(g/4);
            super.setWuchuan(g/4);
            super.setZhenshang(g/13);
            super.setMaxshengming(13*g);
            super.setShengming(13*g);
            super.setBaojilv(l);
            super.setBaojishanghai(6*l);
            super.setKangbao(4*l);
            super.setXixue((l/5));
            super.setMingzhong(l);
            super.setShanbi(l);
        }else if(nianxian>=10000000){

            float g = (7750 + (float) nianxian / 700 + random.nextInt(17750))*0.1f;
            float l= 8 + nianxian / 10000000f ;

            super.setWugong(g);
            super.setWufang(g/4);
            super.setWuchuan(g/4);
            super.setZhenshang(g/13);
            super.setMaxshengming(13*g);
            super.setShengming(13*g);
            super.setBaojilv(l);
            super.setBaojishanghai(6*l);
            super.setKangbao(4*l);
            super.setXixue((l/5));
            super.setMingzhong(l);
            super.setShanbi(l);
        }
    }


    @Override
    public CompoundTag serializeNBT() {

        CompoundTag nbt = super.serializeNBT();
        nbt.putInt("nianxian",nianxian);
        return nbt;
    }

    @Override
    public void deserializeNBT(CompoundTag nbt) {
        if(nbt == null)return;
        super.deserializeNBT(nbt);
        this.nianxian=nbt.getInt("nianxian");

    }

    public int getNianxian() {
        return nianxian;
    }

    public void setNianxian(int nianxian) {
        this.nianxian = nianxian;
    }
}
