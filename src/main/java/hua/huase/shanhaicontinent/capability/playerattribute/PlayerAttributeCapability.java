package hua.huase.shanhaicontinent.capability.playerattribute;

import hua.huase.shanhaicontinent.capability.CapabilityAttributeBase;
import hua.huase.shanhaicontinent.capability.monsterattribute.MonsterAttributeCapability;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraftforge.common.util.INBTSerializable;
import net.minecraftforge.items.ItemStackHandler;

import java.util.*;

public class PlayerAttributeCapability extends CapabilityAttributeBase implements INBTSerializable<CompoundTag> {
//    经验  最大经验
//    精神力 最大精神力
//    等级 魂环快关
//    突破成功概率
//    武魂      魂环
//    物品蓸

    private float jingyan;
    private float maxjingyan;
    private float jingshenli;
    private float maxjingshenli;
    private int dengji;
    private int hunhuankuaiguan;
    private float tupochenggonggailv;
    private Map<String, List<MonsterAttributeCapability>> monsterCapabilityLists = new HashMap<>();
    private List<String> wuhunListsname = new ArrayList<>();;
    ItemStackHandler boneslot = new ItemStackHandler(7);

    public static HashSet<String> wuhunListsnameall= new HashSet<>();

    static {
//        wuhunListsnameall.add("jingubang");
//        wuhunListsnameall.add("huang");
//        wuhunListsnameall.add("haotianchui");
        wuhunListsnameall.add(WuHunName.jingubang);
        wuhunListsnameall.add(WuHunName.huang);
        wuhunListsnameall.add(WuHunName.haotianchui);
    }


    public PlayerAttributeCapability(){
        super();
        this.jingyan = 0;
        this.maxjingyan = 20;
        this.jingshenli = 20;
        this.maxjingshenli = 20;
        this.dengji = 0;
        this.hunhuankuaiguan = 0;
        this.tupochenggonggailv = 3.0f;



    }
    public PlayerAttributeCapability(int jingyan, int maxjingyan, int jingshenli, int dengji, int hunhuankuaiguan, float tupochenggonggailv) {
        this.jingyan = jingyan;
        this.maxjingyan = maxjingyan;
        this.jingshenli = jingshenli;
        this.dengji = dengji;
        this.hunhuankuaiguan = hunhuankuaiguan;
        this.tupochenggonggailv = tupochenggonggailv;
    }



    @Override
    public CompoundTag serializeNBT() {

        CompoundTag nbt = super.serializeNBT();
        nbt.putFloat("jingyan",jingyan);
        nbt.putFloat("maxjingyan",maxjingyan);
        nbt.putFloat("jingshenli",jingshenli);
        nbt.putFloat("tupochenggonggailv",tupochenggonggailv);
        nbt.putFloat("maxjingshenli",maxjingshenli);
        nbt.putInt("dengji",dengji);
        nbt.putInt("hunhuankuaiguan",hunhuankuaiguan);
        for (Map.Entry<String, List<MonsterAttributeCapability>> stringListEntry : monsterCapabilityLists.entrySet()) {
            nbt.putBoolean("iswuhun"+stringListEntry.getKey(),true);
            int nameindex = 0;
            for (MonsterAttributeCapability monsterAttributeCapability : stringListEntry.getValue()) {
                CompoundTag compoundTag = monsterAttributeCapability.serializeNBT();
                nbt.put(stringListEntry.getKey()+nameindex,compoundTag);
                nameindex++;
            }
        }
        nbt.put("boneslot", boneslot.serializeNBT());
        return nbt;
    }

    @Override
    public void deserializeNBT(CompoundTag nbt) {
        super.deserializeNBT(nbt);
        this.jingyan=nbt.getFloat("jingyan");
        this.maxjingyan=nbt.getFloat("maxjingyan");
        this.jingshenli=nbt.getFloat("jingshenli");
        this.tupochenggonggailv=nbt.getFloat("tupochenggonggailv");
        this.maxjingshenli=nbt.getFloat("maxjingshenli");
        this.dengji=nbt.getInt("dengji");
        this.hunhuankuaiguan=nbt.getInt("hunhuankuaiguan");
        this.wuhunListsname.clear();
        this.monsterCapabilityLists.clear();
        for (String s : wuhunListsnameall) {
            int nameindex = 0;
            Tag tag = nbt.get(s + nameindex);
            ArrayList<MonsterAttributeCapability> list = new ArrayList<>();
            while (tag != null){
                MonsterAttributeCapability monsterAttributeCapability = new MonsterAttributeCapability();
                monsterAttributeCapability.deserializeNBT((CompoundTag) tag);
                list.add(monsterAttributeCapability);
                nameindex++;
                tag = nbt.get(s + nameindex);
            }
            if(nbt.getBoolean("iswuhun"+s)){
                this.monsterCapabilityLists.put(s,list);

                this.wuhunListsname.add(s);
            }
        }
        if(nbt.get("boneslot")!=null){
            this.boneslot.deserializeNBT((CompoundTag) nbt.get("boneslot"));
        }

    }


    public float getJingyan() {
        return jingyan;
    }

    public void setJingyan(float jingyan) {
        this.jingyan = jingyan;
    }

    public float getMaxjingyan() {
        return maxjingyan;
    }

    public void setMaxjingyan(int maxjingyan) {
        this.maxjingyan = maxjingyan;
    }

    public float getJingshenli() {
        return jingshenli;
    }

    public void setJingshenli(float jingshenli) {
        this.jingshenli = jingshenli;
    }

    public float getMaxjingshenli() {
        return maxjingshenli;
    }

    public void setMaxjingshenli(float maxjingshenli) {
        this.maxjingshenli = maxjingshenli;
    }

    public int getDengji() {
        return dengji;
    }

    public void setDengji(int dengji) {
        this.dengji = dengji;
    }
    public float getTupochenggonggailv() {
        return tupochenggonggailv;
    }

    public void setTupochenggonggailv(float tupochenggonggailv) {
        this.tupochenggonggailv = tupochenggonggailv;
    }

    public int getHunhuankuaiguan() {
        return hunhuankuaiguan;
    }
    public ItemStackHandler getBoneslot() {
        return boneslot;
    }

    public void setHunhuankuaiguan(int hunhuankuaiguan) {
        this.hunhuankuaiguan = hunhuankuaiguan;
    }

    public List<String> getWuhunListsname() {
        return wuhunListsname;
    }

    public void setWuhunListsname(List<String> wuhunListsname) {
        this.wuhunListsname = wuhunListsname;
    }


    public List<MonsterAttributeCapability> getWuhunList() {
        if(wuhunListsname.size()-1<hunhuankuaiguan||hunhuankuaiguan<0)return null;

        return monsterCapabilityLists.get(wuhunListsname.get(hunhuankuaiguan));
    }

    public String getWuhunName() {
        if(wuhunListsname.size()-1<hunhuankuaiguan||hunhuankuaiguan<0)return null;

        return wuhunListsname.get(hunhuankuaiguan);
    }


    public Map<String, List<MonsterAttributeCapability>> getMonsterCapabilityLists() {
        return monsterCapabilityLists;
    }

    public void setMonsterCapabilityLists(Map<String, List<MonsterAttributeCapability>> monsterCapabilityLists) {
        this.monsterCapabilityLists = monsterCapabilityLists;
    }
}
