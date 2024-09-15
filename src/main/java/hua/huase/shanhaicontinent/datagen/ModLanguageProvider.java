package hua.huase.shanhaicontinent.datagen;

import hua.huase.shanhaicontinent.init.BlockInit;
import hua.huase.shanhaicontinent.init.EntityInit;
import hua.huase.shanhaicontinent.init.ItemInit;
import hua.huase.shanhaicontinent.init.SHModMobEffectsinit;
import net.minecraft.data.PackOutput;
import net.minecraftforge.common.data.LanguageProvider;

public class ModLanguageProvider extends LanguageProvider {
    public ModLanguageProvider(PackOutput output, String modid, String locale) {
        super(output, modid, locale);
    }

    @Override
    protected void addTranslations() {
// In LanguageProvider#addTranslations
//        this.addBlock(BlockInit.text_block, "Example Block");
        this.add("套装效果", "套装效果:");
        this.add("生命", "生命：%s/%s");
        this.add("最大生命", "最大生命：+%s");
        this.add("获得生命", "生命：+%s");
        this.add("物攻", "物攻：+%s");
        this.add("物防", "物防：+%s");
        this.add("爆伤", "爆伤：+%s");
        this.add("爆率", "爆率：+%s");
        this.add("真伤", "真伤：+%s");
        this.add("物穿", "物穿：+%s");
        this.add("抗暴", "抗暴：+%s");
        this.add("吸血", "吸血：+%s");
        this.add("回复", "回复：+%s");
        this.add("命中", "命中：+%s");
        this.add("闪避", "闪避：+%s");
        this.add("经验", "经验：%s/%s");
        this.add("获得经验", "经验：+%s");
        this.add("等级", "等级：%s");
        this.add("精神力", "精神力：%s/%s");
        this.add("获得精神力", "精神力：+%s");
        this.add("获得最大精神力", "最大精神力：+%s");
        this.add("体力", "体力：%s/%s");

        this.add("突破成功率", "突破成功率：+%s");
        this.add("能量", "能量：%s/%s");

        this.add("能量耗尽", "能量耗尽");
        this.add("能量已满", "能量已满");


        this.add("技能开关", "技能开关");
        this.add("武魂开关", "武魂开关");
        this.add("属性面板", "属性面板");
        this.add("右键<魂核>收集能量；右键<耕地>消耗能量，转化<魂土>", "右键<魂核>收集能量；右键<耕地>消耗能量，转化<魂土>");



        this.add("player join world", "§2-%s-§f来到了山海大陆———-§e山海大陆：§b宇宙之初，混沌演化，造化之气，造化万物，山川海势，万物而生");
        this.add("精神力不足", "精神力不足");
        this.add("吸收经验成功", "§2吸收成功,经验：§e+%s");
        this.add("突破成功", "§6成功进阶到§e%s级");
        this.add("不能突破", "§c无法突破：请先吸收魂环");
        this.add("已经满级", "§c无法突破：天地压制");
        this.add("突破失败", "§5突破失败，下次突破概率+5");

        this.add("未开启或觉醒武魂", "§c未开启或觉醒武魂");
        this.add("成功觉醒武魂", "§c成功觉醒武魂§c%s");
        this.add("成功吸收魂环", "§c成功吸收%s年魂环");
        this.add("等级不够", "§c等级不够或魂环已满，无法吸收魂环");
        this.add("正在吸收魂环", "§c正在吸收魂环，精神力-%s");


        this.add("服用后觉醒武魂", "§2服用后觉醒%s武魂");
//        this.add("jingubang",   "§4破天神棍");
//        this.add("huang",       "§e武魂-荒");
//        this.add("haotianchui", "§e浩天圣锤");


        this.add("已达到服用上限", "§4已达到服用上限");


        this.add("武魂已开启", "<<§c%s>> §a武魂已开启");
        this.add("武魂已关闭", "§a武魂已关闭");
        this.add("拥有者", "拥有者:%s");
        this.add("年限", "年限:%s");
        this.add("武魂技能", "技能：%s+%s年");


        this.add("block.tutorialmod.gem_polishing_station", "§6造化炉配方");
        this.add("creativetab.shanhaicontient_tab", "§7山海大陆");




        this.add(BlockInit.SAPPHIRE_BLOCK   .get(),"测试方块");
        this.add(BlockInit.SOUL_BLOCK       .get(),"§a魂土");
        this.add(BlockInit.POT_BLOCK        .get(),"§e造化炉");
        this.add(BlockInit.baisuilan        .get(),"§1百岁栏");
        this.add(BlockInit.fengxinzi        .get(),"§a风信子");
        this.add(BlockInit.hanxiaohua       .get(),"§b含笑花");
        this.add(BlockInit.hehuan           .get(),"§3合欢");
        this.add(BlockInit.heshouwu         .get(),"§c何首乌");
        this.add(BlockInit.qiuhaitang       .get(),"§4秋海棠");
        this.add(BlockInit.shancha          .get(),"§d山茶");
        this.add(BlockInit.wangyoucao       .get(),"§f忘忧草");
        this.add(BlockInit.xiwu             .get(),"§f夕雾");
        this.add(BlockInit.xunyicao         .get(),"§f薰衣草");
        this.add(BlockInit.yueguanghua      .get(),"§f月光花");
        this.add(BlockInit.bianhua          .get(),"§2彼岸花");
        this.add(BlockInit.zhushamei        .get(),"§f朱莎玫");

//物品


        this.add(ItemInit.wanfajieshao       .get(),"§1山海大陆玩法介绍");




        this.add(ItemInit.baisuilan_fruit       .get(),"§1百岁栏");
        this.add(ItemInit.fengxinzi_fruit       .get(),"§a风信子");
        this.add(ItemInit.hanxiaohua_fruit      .get(),"§b含笑花");
        this.add(ItemInit.hehuan_fruit          .get(),"§3合欢");
        this.add(ItemInit.heshouwu_fruit        .get(),"§c何首乌");
        this.add(ItemInit.qiuhaitang_fruit      .get(),"§4秋海棠");
        this.add(ItemInit.shancha_fruit         .get(),"§d山茶");
        this.add(ItemInit.wangyoucao_fruit      .get(),"§f忘忧草");
        this.add(ItemInit.xiwu_fruit            .get(),"§f夕雾");
        this.add(ItemInit.xunyicao_fruit        .get(),"§f薰衣草");
        this.add(ItemInit.yueguanghua_fruit     .get(),"§f月光花");
        this.add(ItemInit.zhushamei_fruit       .get(),"§f朱莎玫");
        this.add(ItemInit.bianhua_fruit         .get(),"§2彼岸花");

        this.add(ItemInit.baisuilan_seed        .get(),"§1百岁栏 *** 种子");
        this.add(ItemInit.fengxinzi_seed        .get(),"§a风信子 *** 种子");
        this.add(ItemInit.hanxiaohua_seed       .get(),"§b含笑花 *** 种子");
        this.add(ItemInit.hehuan_seed           .get(),"§3合欢 *** 种子");
        this.add(ItemInit.heshouwu_seed         .get(),"§c何首乌 *** 种子");
        this.add(ItemInit.qiuhaitang_seed       .get(),"§4秋海棠 *** 种子");
        this.add(ItemInit.shancha_seed          .get(),"§d山茶 *** 种子");
        this.add(ItemInit.wangyoucao_seed       .get(),"§f忘忧草 *** 种子");
        this.add(ItemInit.xiwu_seed             .get(),"§f夕雾 *** 种子");
        this.add(ItemInit.xunyicao_seed         .get(),"§f薰衣草 *** 种子");
        this.add(ItemInit.yueguanghua_seed      .get(),"§f月光花 *** 种子");
        this.add(ItemInit.zhushamei_seed        .get(),"§f朱莎玫 *** 种子");
        this.add(ItemInit.bianhua_seed          .get(),"§2彼岸花 *** 种子");



        this.add(SHModMobEffectsinit.jineng_jgb_6 	     .get(),"§b《金箍真身》");
        this.add(SHModMobEffectsinit.jineng_jgb_7 	     .get(),"§b《斗战圣域》");
        this.add(SHModMobEffectsinit.jineng_jgb_8 	     .get(),"§b《斗战胜佛体》");
        this.add(SHModMobEffectsinit.jineng_huang_6      .get(),"§b《轮回宝术》");
        this.add(SHModMobEffectsinit.jineng_huang_8      .get(),"§b《不知名至尊术》");
        this.add(SHModMobEffectsinit.jineng_htsc_2       .get(),"§b《浩天护盾》");
        this.add(SHModMobEffectsinit.jineng_htsc_5       .get(),"§b《浩天九绝》");
        this.add(SHModMobEffectsinit.jineng_htsc_6       .get(),"§b《落天雷锤》");
        this.add(SHModMobEffectsinit.zhiwu_bianhua       .get(),"§d梦回万古");


        this.add(ItemInit.TEXTITEM.get(),"测试物品");
        this.add(ItemInit.hunyeping.get(),"§a<魂液瓶> ");

        this.add(ItemInit.jineng_jgb_0.get(),"§e器武魂：§4破天神棍 §f------ §e《极品器武魂》");
        this.add(ItemInit.jineng_jgb_1.get(),"§e魂技：§b《分身化影》 §f------ 武魂：§e《破天神棍》");
        this.add(ItemInit.jineng_jgb_2.get(),"§e魂技：§b《禁锢咒》 §f------ 武魂：§e《破天神棍》");
        this.add(ItemInit.jineng_jgb_3.get(),"§e魂技：§b《定海》 §f------ 武魂：§e《破天神棍》");
        this.add(ItemInit.jineng_jgb_4.get(),"§e魂技：§b《金斗云》 §f------ 武魂：§e《破天神棍》");
        this.add(ItemInit.jineng_jgb_5.get(),"§e魂技：§b《九龙禅音》 §f------ 武魂：§e《破天神棍》");
        this.add(ItemInit.jineng_jgb_6.get(),"§e魂技：§b《金箍真身》 §f------ 武魂：§e《破天神棍》");
        this.add(ItemInit.jineng_jgb_7.get(),"§e魂技：§b《斗战圣域》 §f------ 武魂：§e《破天神棍》");
        this.add(ItemInit.jineng_jgb_8.get(),"§e魂技：§b《斗战胜佛体》 §f------ 武魂：§e《破天神棍》");
        this.add(ItemInit.jineng_huang_0.get(),"§e武魂-荒： §f------ §e《极品武魂》");
        this.add(ItemInit.jineng_huang_1.get(),"§e魂技：§b《鲲鹏宝术》 §f------ 武魂：§e《荒》");
        this.add(ItemInit.jineng_huang_2.get(),"§e魂技：§b《狻猊神雷》 §f------ 武魂：§e《荒》");
        this.add(ItemInit.jineng_huang_3.get(),"§e魂技：§b《柳神法》 §f------ 武魂：§e《荒》");
        this.add(ItemInit.jineng_huang_4.get(),"§e魂技：§b《草灭剑诀》 §f------ 武魂：§e《荒》");
        this.add(ItemInit.jineng_huang_5.get(),"§e魂技：§b《六道轮回天功》 §f------ 武魂：§e《荒》");
        this.add(ItemInit.jineng_huang_6.get(),"§e魂技：§b《轮回宝术》 §f------ 武魂：§e《荒》");
        this.add(ItemInit.jineng_huang_7.get(),"§e魂技：§b《抱剑杀》 §f------ 武魂：§e《荒》");
        this.add(ItemInit.jineng_huang_8.get(),"§e魂技：§b《不知名至尊术》 §f------ 武魂：§e《荒》");
        this.add(ItemInit.jineng_htsc_0.get(),"§e器武魂-§e浩天圣锤： §f------ §e《极品武魂》");
        this.add(ItemInit.jineng_htsc_1.get(),"§e魂技：§b《浩天震》 §f------ 武魂：§e《浩天圣锤》");
        this.add(ItemInit.jineng_htsc_2.get(),"§e魂技：§b《浩天护盾》 §f------ 武魂：§e《浩天圣锤》");
        this.add(ItemInit.jineng_htsc_3.get(),"§e魂技：§b《神秘之锤》 §f------ 武魂：§e《浩天圣锤》");
        this.add(ItemInit.jineng_htsc_4.get(),"§e魂技：§b《天地无极》 §f------ 武魂：§e《浩天圣锤》");
        this.add(ItemInit.jineng_htsc_5.get(),"§e魂技：§b《浩天九绝》 §f------ 武魂：§e《浩天圣锤》");
        this.add(ItemInit.jineng_htsc_6.get(),"§e魂技：§b《浩天护体》 §f------ 武魂：§e《浩天圣锤》");
        this.add(ItemInit.jineng_htsc_7.get(),"§e魂技：§b《凌天一击》 §f------ 武魂：§e《浩天圣锤》");
        this.add(ItemInit.jineng_htsc_8.get(),"§e魂技：§b《落天雷锤》 §f------ 武魂：§e《浩天圣锤》");
        this.add(ItemInit.guoshi_haotianchui    .get(),"§4<浩天圣锤> §f------ §c《武魂果实》");
        this.add(ItemInit.guoshi_huang          .get(),"§4<荒> §f------ §c《武魂果实》");
        this.add(ItemInit.guoshi_jingubang      .get(),"§4<破天神棍> §f------ §c《武魂果实》");
        this.add(ItemInit.danyao_qihundan       .get(),"§a气魂丹*Ⅰ阶");
        this.add(ItemInit.danyao_jvlingdan      .get(),"§b聚灵丹*Ⅱ阶");
        this.add(ItemInit.danyao_xvanyuandan    .get(),"§c玄元丹*Ⅲ阶");
        this.add(ItemInit.danyao_yanghundan     .get(),"§d养魂丹*Ⅳ阶");
        this.add(ItemInit.danyao_lingbidan      .get(),"§e凝碧丹*Ⅴ阶");
        this.add(ItemInit.danyao_haoyuan        .get(),"§7昊元丹*Ⅵ阶");
        this.add(ItemInit.danyao_xihundan       .get(),"§6涤魂丹*Ⅶ阶");
        this.add(ItemInit.danyao_huangjidan     .get(),"§5皇极丹*Ⅷ阶");
        this.add(ItemInit.danyao_lushendan      .get(),"§4怒神丹*Ⅸ阶");
        this.add(ItemInit.danyao_jiuhua         .get(),"§1《§b九花玉露丸§1》");
        this.add(ItemInit.danfang_qihundan      .get(),"§a丹方：§f《§a气魂丹§f》*Ⅰ阶");
        this.add(ItemInit.danfang_jvlingdan     .get(),"§b丹方：§f《§b聚灵丹§f》*Ⅱ阶");
        this.add(ItemInit.danfang_xvanyuandan   .get(),"§c丹方：§f《§c玄元丹§f》*Ⅲ阶");
        this.add(ItemInit.danfang_yanghundan    .get(),"§d丹方：§f《§d养魂丹§f》*Ⅳ阶");
        this.add(ItemInit.danfang_lingbidan     .get(),"§e丹方：§f《§e凝碧丹§f》*Ⅴ阶");
        this.add(ItemInit.danfang_haoyuan       .get(),"§7丹方：§f《§7昊元丹§f》*Ⅵ阶");
        this.add(ItemInit.danfang_xihundan      .get(),"§6丹方：§f《§6涤魂丹§f》*Ⅶ阶");
        this.add(ItemInit.danfang_huangjidan    .get(),"§5丹方：§f《§5皇极丹§f》*Ⅷ阶");
        this.add(ItemInit.danfang_lushendan     .get(),"§4丹方：§f《§4怒神丹§f》*Ⅸ阶");
        this.add(ItemInit.danfang_jiuhua        .get(),"§4丹方：§1《§b九花玉露丸§1》***");
        this.add(ItemInit.headbone              .get(),"§c头骨 §f§k****** §e《上古神秘骨》");
        this.add(ItemInit.exoskeletonsoulbone.get(),"§c外附骨 §f§k****** §e《上古神秘骨》");
        this.add(ItemInit.lefthandbone          .get(),"§c左臂骨 §f§k****** §e《上古神秘骨》");
        this.add(ItemInit.righthandbone         .get(),"§c右臂骨 §f§k****** §e《上古神秘骨》");
        this.add(ItemInit.trunkbone             .get(),"§c躯干骨 §f§k****** §e《上古神秘骨》");
        this.add(ItemInit.leftlegbone           .get(),"§c左腿骨 §f§k****** §e《上古神秘骨》");
        this.add(ItemInit.rightlegbone          .get(),"§c右腿骨 §f§k****** §e《上古神秘骨》");
        this.add(ItemInit.hunmin_spanw_egg      .get(),"生成魂民");


        this.add(EntityInit.hunmin.get(),"§a魂民");
        this.add(EntityInit.HUNHE.get(),"§9魂核");
        this.add(EntityInit.HUNHUAN.get(),"§9魂环");



    }
}
