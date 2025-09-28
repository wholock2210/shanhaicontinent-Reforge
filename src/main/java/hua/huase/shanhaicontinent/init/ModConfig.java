package hua.huase.shanhaicontinent.init;

import net.minecraftforge.common.ForgeConfigSpec;

public class ModConfig {
    // 原有配置项...
    public static final ForgeConfigSpec.BooleanValue canBreakBlocks;
    public static final ForgeConfigSpec.BooleanValue danyaoBalance;
    public static final ForgeConfigSpec.BooleanValue enablebalance;
    public static final ForgeConfigSpec.BooleanValue hungupingheng;
    public static final ForgeConfigSpec.BooleanValue jingyanbalance;
    public static final ForgeConfigSpec.BooleanValue slowGrowth;
    public static final ForgeConfigSpec.IntValue tickcoldhot;

    // 新增爆率配置项
    public static final ForgeConfigSpec.DoubleValue baseDropChance; // 基础爆率(hungupingheng=false时使用)
    public static final ForgeConfigSpec.DoubleValue dropChanceTier1; // 10-99年
    public static final ForgeConfigSpec.DoubleValue dropChanceTier2; // 100-999年
    public static final ForgeConfigSpec.DoubleValue dropChanceTier3; // 1000-9999年
    public static final ForgeConfigSpec.DoubleValue dropChanceTier4; // 10000-99999年
    public static final ForgeConfigSpec.DoubleValue dropChanceTier5; // 100000-999999年
    public static final ForgeConfigSpec.DoubleValue dropChanceTier6; // 1000000-9999999年
    public static final ForgeConfigSpec.DoubleValue dropChanceTier7; // 10000000+年

    public static final ForgeConfigSpec.BooleanValue ENABLE_HUNHUAN_PROBABILITY;
    public static final ForgeConfigSpec.DoubleValue TIER1_PROB;
    public static final ForgeConfigSpec.DoubleValue TIER2_PROB;
    public static final ForgeConfigSpec.DoubleValue TIER3_PROB;
    public static final ForgeConfigSpec.DoubleValue TIER4_PROB;
    public static final ForgeConfigSpec.DoubleValue TIER5_PROB;
    public static final ForgeConfigSpec.DoubleValue TIER6_PROB;
    public static final ForgeConfigSpec.DoubleValue TIER7_PROB;

    static {
        ForgeConfigSpec.Builder builder = new ForgeConfigSpec.Builder();

        // 原有配置项...
        canBreakBlocks = builder.comment("是否允许技能破坏方块(默认为false：不允许破坏方块。开启后还是蹲下释放技能时破坏地形)")
                .define("canBreakBlocks", false);
        danyaoBalance = builder.comment("是否开启丹药平衡(默认为true：削弱丹药属性。false时保持原属性)")
                .define("danyaoBalance", true);
        enablebalance = builder.comment("是否开启魂核数值平衡(默认为true：削弱魂核属性。false时保持原属性)")
                .define("hunhe_balance", true);
        hungupingheng = builder.comment("是否开启魂骨爆率平衡(默认为true：增加魂骨爆率。false时保持原爆率)")
                .define("hungu_balance", true);
        jingyanbalance = builder.comment("是否开启最大经验数值平衡(默认为true：增加最大经验值。false时保持原属性)")
                .define("jingyan_balance", true);
        slowGrowth = builder.comment("是否开启草药种子生长平衡(默认为true：开启。false时保持原生长速率)")
                .define("slow_growth", true);
        tickcoldhot = builder.comment("冰火两仪眼刷仙草频率，单位为tick（20 tick = 1 秒）")
                .defineInRange("tickcoldhot", 36000, 100, Integer.MAX_VALUE);

        // 是否启用概率生成
        ENABLE_HUNHUAN_PROBABILITY = builder
                .comment("是否启用基于年限的魂环概率生成")
                .define("enableHunhuanProbability", true);

        // 各阶段概率配置
        TIER1_PROB = builder
                .comment("10年内魂环生成概率")
                .defineInRange("hunhuanTier1Prob", 0.6, 0.0, 1.0);
        TIER2_PROB = builder
                .comment("100年内魂环生成概率")
                .defineInRange("hunhuanTier2Prob", 0.5, 0.0, 1.0);
        TIER3_PROB = builder
                .comment("1000年内魂环生成概率")
                .defineInRange("hunhuanTier3Prob", 0.4, 0.0, 1.0);
        TIER4_PROB = builder
                .comment("10000年内魂环生成概率")
                .defineInRange("hunhuanTier4Prob", 0.35, 0.0, 1.0);
        TIER5_PROB = builder
                .comment("100000年内魂环生成概率")
                .defineInRange("hunhuanTier5Prob", 0.3, 0.0, 1.0);
        TIER6_PROB = builder
                .comment("1000000年内魂环生成概率")
                .defineInRange("hunhuanTier6Prob", 0.32, 0.0, 1.0);
        TIER7_PROB = builder
                .comment("10000000年以上魂环生成概率")
                .defineInRange("hunhuanTier7Prob", 0.70, 0.0, 1.0);


        // 新增爆率配置项
        builder.push("魂骨爆率配置").comment("这些配置只在魂骨平衡为true时生效(本功能供开发测试用，私自修改导致存档问题不予解决！)");
        baseDropChance = builder.comment("基础爆率(当魂骨平衡为false时使用)")
                .defineInRange("baseDropChance", 1.0/16, 0.0, 1.0);
        dropChanceTier1 = builder.comment("10-99年魂兽的魂骨爆率")
                .defineInRange("tier1", 0.001, 0.0, 1.0);
        dropChanceTier2 = builder.comment("100-999年魂兽的魂骨爆率")
                .defineInRange("tier2", 0.002, 0.0, 1.0);
        dropChanceTier3 = builder.comment("1000-9999年魂兽的魂骨爆率")
                .defineInRange("tier3", 0.004, 0.0, 1.0);
        dropChanceTier4 = builder.comment("10000-99999年魂兽的魂骨爆率")
                .defineInRange("tier4", 0.006, 0.0, 1.0);
        dropChanceTier5 = builder.comment("100000-999999年魂兽的魂骨爆率")
                .defineInRange("tier5", 0.01, 0.0, 1.0);
        dropChanceTier6 = builder.comment("1000000-9999999年魂兽的魂骨爆率")
                .defineInRange("tier6", 0.01, 0.0, 1.0);
        dropChanceTier7 = builder.comment("10000000年魂兽的魂骨爆率")
                .defineInRange("tier7", 0.10, 0.0, 1.0);
        builder.pop();
        CONFIG = builder.build();
    }

    public static final ForgeConfigSpec CONFIG;
}