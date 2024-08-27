package hua.huase.shanhaicontinent.init;

import hua.huase.shanhaicontinent.advance.*;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.advancements.CriterionTrigger;
import net.minecraft.advancements.critereon.ChangeDimensionTrigger;


public class AdvenceInit {

    public static final ChangeDengjiTrigger changedengjitrigger = CriteriaTriggers.register(new ChangeDengjiTrigger());
    public static final XishouHunheTrigger xishouhunhetrigger = CriteriaTriggers.register(new XishouHunheTrigger());
    public static final MenghuiwanguTrigger menghuiwangutrigger = CriteriaTriggers.register(new MenghuiwanguTrigger());
    public static final XishouhunhuanTrigger xishouhunhuantrigger = CriteriaTriggers.register(new XishouhunhuanTrigger());
    public static final JuexingwuhunTrigger juexingwuhuntrigger = CriteriaTriggers.register(new JuexingwuhunTrigger());
//
//    public static void register(){
//        System.out.println("Advence invalid scussed");
//    }
}
