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
        canBreakBlocks = builder.comment("Cho phép kỹ năng phá hủy khối (mặc định là false: không cho phép phá hủy khối. Ngay cả khi được bật, kỹ năng vẫn sẽ phá hủy địa hình khi cúi người để sử dụng).")
                .define("canBreakBlocks", false);
        danyaoBalance = builder.comment("Bật tính năng cân bằng thuốc (mặc định là true: làm suy yếu thuộc tính của thuốc. False giữ nguyên thuộc tính ban đầu).")
                .define("danyaoBalance", true);
        enablebalance = builder.comment("Bật tính năng cân bằng chỉ số Lõi Linh Hồn (mặc định là true: làm suy yếu các thuộc tính của Lõi Linh Hồn. False giữ nguyên các thuộc tính ban đầu).")
                .define("hunhe_balance", true);
        hungupingheng = builder.comment("Bật tính năng cân bằng tỷ lệ rơi Xương Linh Hồn (mặc định là true: tăng tỷ lệ rơi Xương Linh Hồn. False giữ nguyên tỷ lệ rơi ban đầu).")
                .define("hungu_balance", true);
        jingyanbalance = builder.comment("Cho phép cân bằng giá trị kinh nghiệm tối đa (mặc định là true: tăng giá trị kinh nghiệm tối đa. False giữ nguyên thuộc tính ban đầu).")
                .define("jingyan_balance", true);
        slowGrowth = builder.comment("Bật hoặc tắt chế độ cân bằng tốc độ tăng trưởng hạt giống thảo mộc (mặc định là true: bật. False: duy trì tốc độ tăng trưởng ban đầu).")
                .define("slow_growth", true);
        tickcoldhot = builder.comment("Tần suất mà Nhãn Âm Dương Băng Dương tìm kiếm thảo dược bất tử được đo bằng tích tắc (20 tích tắc = 1 giây).")
                .defineInRange("tickcoldhot", 36000, 100, Integer.MAX_VALUE);

        // 是否启用概率生成
        ENABLE_HUNHUAN_PROBABILITY = builder
                .comment("Cho phép tạo xác suất nhận Nhẫn Linh Hồn dựa trên độ tuổi?")
                .define("enableHunhuanProbability", true);

        // 各阶段概率配置
        TIER1_PROB = builder
                .comment("Xác suất tạo ra Nhẫn Linh Hồn trong vòng 10 năm")
                .defineInRange("hunhuanTier1Prob", 0.6, 0.0, 1.0);
        TIER2_PROB = builder
                .comment("Xác suất tạo ra Nhẫn Linh Hồn trong vòng 100 năm")
                .defineInRange("hunhuanTier2Prob", 0.5, 0.0, 1.0);
        TIER3_PROB = builder
                .comment("Xác suất tạo ra Nhẫn Linh Hồn trong vòng 1000 năm")
                .defineInRange("hunhuanTier3Prob", 0.4, 0.0, 1.0);
        TIER4_PROB = builder
                .comment("Xác suất tạo ra Nhẫn Linh Hồn trong vòng 10.000 năm")
                .defineInRange("hunhuanTier4Prob", 0.35, 0.0, 1.0);
        TIER5_PROB = builder
                .comment("Xác suất tạo ra Nhẫn Linh Hồn trong vòng 100.000 năm")
                .defineInRange("hunhuanTier5Prob", 0.3, 0.0, 1.0);
        TIER6_PROB = builder
                .comment("Xác suất tạo ra Nhẫn Linh Hồn trong vòng 1.000.000 năm")
                .defineInRange("hunhuanTier6Prob", 0.32, 0.0, 1.0);
        TIER7_PROB = builder
                .comment("Xác suất tạo ra một Chiếc Nhẫn Linh Hồn có tuổi thọ từ 10.000.000 năm trở lên.")
                .defineInRange("hunhuanTier7Prob", 0.70, 0.0, 1.0);


        // 新增爆率配置项
        builder.push("魂骨爆率配置").comment("Các cấu hình này chỉ có hiệu lực khi Soul Bone Balance được đặt thành true (tính năng này chỉ dành cho mục đích phát triển và thử nghiệm; bất kỳ sự cố nào về tệp lưu do sửa đổi trái phép sẽ không được giải quyết!).");
        baseDropChance = builder.comment("Tỷ lệ rơi đồ cơ bản (được sử dụng khi Soul Bone Balance là false)")
                .defineInRange("baseDropChance", 1.0/16, 0.0, 1.0);
        dropChanceTier1 = builder.comment("Tỷ lệ rơi Xương Linh Hồn Quái Thú từ 10 đến 99 năm")
                .defineInRange("tier1", 0.1, 0.0, 1.0);
        dropChanceTier2 = builder.comment("Tỷ lệ rơi Xương Linh Hồn của Linh Thú từ 100 đến 999 năm")
                .defineInRange("tier2", 0.2, 0.0, 1.0);
        dropChanceTier3 = builder.comment("Tỷ lệ rơi Xương Linh Hồn của Linh Thú trong các năm 1000-9999")
                .defineInRange("tier3", 0.3, 0.0, 1.0);
        dropChanceTier4 = builder.comment("Tỷ lệ rơi Xương Linh Hồn của Linh Thú trong khoảng thời gian 10000-99999 năm.")
                .defineInRange("tier4", 0.4, 0.0, 1.0);
        dropChanceTier5 = builder.comment("Tỷ lệ rơi Xương Linh Hồn của Linh Thú trong khoảng thời gian 100.000-999.999 năm")
                .defineInRange("tier5", 0.5, 0.0, 1.0);
        dropChanceTier6 = builder.comment("Tỷ lệ rơi Xương Linh Hồn của Linh Thú trong các năm 1.000.000-9.999.999")
                .defineInRange("tier6", 0.6, 0.0, 1.0);
        dropChanceTier7 = builder.comment("Tỷ lệ rơi xương linh hồn từ quái thú linh hồn 10.000.000 năm tuổi")
                .defineInRange("tier7", 0.7, 0.0, 1.0);
        builder.pop();
        CONFIG = builder.build();
    }

    public static final ForgeConfigSpec CONFIG;
}