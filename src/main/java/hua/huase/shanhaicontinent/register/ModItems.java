package hua.huase.shanhaicontinent.register;

import hua.huase.shanhaicontinent.SHMainBus;
import hua.huase.shanhaicontinent.init.ModSounds;
import hua.huase.shanhaicontinent.item.BoneItem;
import hua.huase.shanhaicontinent.item.DanYaoItem;
import hua.huase.shanhaicontinent.item.shenjie.*;
import hua.huase.shanhaicontinent.item.shenjie.severitem.GodGiveHunhuan;
import hua.huase.shanhaicontinent.item.shenjie.severitem.HunhuanItem;
import hua.huase.shanhaicontinent.item.shenjie.severitem.StrengtheningSucceedItem;
import hua.huase.shanhaicontinent.item.shenjie.shenqi.Molian;
import hua.huase.shanhaicontinent.item.shenjie.shenqi.ShanChaJi;
import hua.huase.shanhaicontinent.item.shenjie.shenqi.TianShiChangJian;
import hua.huase.shanhaicontinent.item.shenjie.shenqi.XiuLuoSword;
import hua.huase.shanhaicontinent.item.shenjie.wupin.*;
import net.minecraft.ChatFormatting;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.*;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.ForgeSpawnEggItem;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

public class ModItems {

    public static final DeferredRegister<Item> Items = DeferredRegister.create(ForgeRegistries.ITEMS, SHMainBus.MOD_ID);
    public static final RegistryObject<Item> SILVER_INGOT = Items.register("silver_ingot", () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> SHENLAN_BLOCK = Items.register("shenlan_block", () -> new BlockItem(ModBlock.SHENLAN_BLOCK.get(), new Item.Properties()));
    public static final RegistryObject<Item> DENGXIANTAI_BLOCK = Items.register("dengxiantai_block", () -> new BlockItem(ModBlock.DENGXIANTAI_BLOCK.get(), new Item.Properties().stacksTo(1)));
    public static final RegistryObject<Item> GRASSSJ_BLOCK = Items.register("grasssj_block", () -> new BlockItem(ModBlock.GRASSSJ_BLOCK.get(), new Item.Properties()));
    public static final RegistryObject<Item> XUANBING_BLOCK = Items.register("xuanbing_block", () -> new BlockItem(ModBlock.XUANBING_BLOCK.get(), new Item.Properties()));
    public static final RegistryObject<Item> SHENJIEMU_BLOCK = Items.register("shenjiemu_block",()-> new BlockItem(ModBlock.SHENJIEMU_BLOCK.get(), new Item.Properties()));
    public static final RegistryObject<Item> SHENJIEMUSHUYE_BLOCK = Items.register("shenjiemushuye_block", ()-> new BlockItem(ModBlock.SHENJIEMUSHUYE_BLOCK.get(), new Item.Properties()));
    public static final RegistryObject<Item> SHENJIESHUSHUMIAO_SAPLING = Items.register("shenjieshushumiao_sapling", ()-> new BlockItem(ModBlock.SHENJIESHUSHUMIAO_SAPLING.get(), new Item.Properties().stacksTo(16)));
    public static final RegistryObject<Item> artifact_workbench = Items.register("artifact_workbench", ()-> new BlockItem(ModBlock.artifact_workbench.get(), new Item.Properties()));
    public static final RegistryObject<Item> zixiaoxianjin = Items.register("zixiaoxianjin", ()-> new BlockItem(ModBlock.zixiaoxianjin.get(), new Item.Properties()));

    public static final RegistryObject<Item> LINGPAI_HAISHEN = Items.register("lingpai_haishen", ()-> new HaiShen(Tiers.NETHERITE, 7, -0.4F, new Item.Properties()));
    public static final RegistryObject<Item> LINGPAI_TIANSHISHEN = Items.register("lingpai_tianshishen", ()-> new TianShiShen(Tiers.NETHERITE, 7, -0.4F, new Item.Properties()));
    public static final RegistryObject<Item> LINGPAI_XIULUOSHEN = Items.register("lingpai_xiuluoshen", ()-> new XiuLuoShen(Tiers.NETHERITE, 7, -0.4F, new Item.Properties()));
    public static final RegistryObject<Item> LINGPAI_LUOCHASHEN = Items.register("lingpai_luochashen", ()-> new luochashen(Tiers.NETHERITE, 7, -0.4F, new Item.Properties()));

    public static final RegistryObject<Item> XUANBING_STONE = Items.register("xuanbing_stone", ()-> new Item(new Item.Properties()){
        public void appendHoverText(@NotNull ItemStack itemStack, @Nullable Level level, @NotNull List<Component> list, @NotNull TooltipFlag tooltipFlag) {
            list.add(Component.literal("铸造神器的必须品之一").withStyle(ChatFormatting.GRAY));
        }
    });

    public static final RegistryObject<Item> XUANBINGBLOCK = Items.register("xuanbingblock", ()-> new BlockItem(ModBlock.XUANBINGBLOCK.get(), new Item.Properties()));

    public static final RegistryObject<Item> pojingdan = Items.register("pojingdan", ()-> new PoJingDan(new Item.Properties()));
    public static final RegistryObject<Item> cs_dengji = Items.register("cs_dengji", ()-> new CheShiDengjiOne(new Item.Properties()));
    public static final RegistryObject<Item> cs_dengjijia = Items.register("cs_dengjijia", ()-> new DanYaoItem(new Item.Properties().food(new FoodProperties.Builder().alwaysEat().build())).setJingshenlibaifenbi(50));
    public static final RegistryObject<Item> cs_dengjijian = Items.register("cs_dengjijian", ()-> new DanYaoItem(new Item.Properties().food(new FoodProperties.Builder().alwaysEat().build())).setJingshenlibaifenbi(100));
    public static final RegistryObject<Item> cs_lingpai = Items.register("cs_lingpai", ()-> new HuoDeLingPaI(new Item.Properties()));
    public static final RegistryObject<Item> cs_shengwuscq = Items.register("cs_shengwuscq", ()-> new ceshidongwu(new Item.Properties()));
    public static final RegistryObject<Item> cs_shenqihuoqu = Items.register("cs_shenqihuoqu", ()-> new GiveSworld(new Item.Properties()));
    public static final RegistryObject<Item> cs_jiadengji = Items.register("cs_jiadengji", ()-> new dengjijiayi(new Item.Properties()));
    public static final RegistryObject<Item> cs_jinengmianban = Items.register("cs_jinengmianban", ()-> new csjinengman(new Item.Properties()));
    public static final RegistryObject<Item> CS_DENGJIYI = Items.register("cs_dengjiyi", ()-> new Lveverhou(new Item.Properties()));
    public static final RegistryObject<Item> CS_HEALTH = Items.register("cs_health", ()-> new Healthitem(new Item.Properties()));

    public static final RegistryObject<Item> god_eye = Items.register("god_eye", ()-> new godeye(new Item.Properties()));

    public static final RegistryObject<Item> SHENJIE_CSM = Items.register("shenjie_csm", ()-> new BlockItem(ModBlock.SHENJIE_CSM.get(), new Item.Properties()));

    public static final RegistryObject<Item> HAISHEN_SANCHAJI = Items.register("haishen_sanchaji", ShanChaJi::new);
    public static final RegistryObject<Item> XIULUOSHEN_JIAN = Items.register("xiuluoshen_jian", XiuLuoSword::new);
    public static final RegistryObject<Item> TIANSHISHEN_JIAN = Items.register("tianshishen_jian", TianShiChangJian::new);
    public static final RegistryObject<Item> LUOCHASHEN_MOLIAN = Items.register("luochashen_molian", Molian::new);

    public static final RegistryObject<Item> JIANYING_BLOCK = Items.register("jianying_block", ()-> new BlockItem(ModBlock.JIANYING_BLOCK.get(), new Item.Properties()));
    public static final RegistryObject<Item> JIANYING_QINAG = Items.register("jianying_qiang", ()-> new BlockItem(ModBlock.JIANYING_QIANG.get(), new Item.Properties()));
    public static final RegistryObject<Item> JIANYING_LOUTI = Items.register("jianying_louti", ()-> new BlockItem(ModBlock.JIANYING_LOUTI.get(), new Item.Properties()));

    public static final RegistryObject<Item> ZHAOHUANSHI = Items.register("zhaohuanshi", ()-> new Item(new Item.Properties().stacksTo(1)){
        @Override
        public boolean isFoil(@NotNull ItemStack pStack) {
            return true;
        }
        public void appendHoverText(@NotNull ItemStack itemStack, @Nullable Level level, @NotNull List<Component> list, @NotNull TooltipFlag tooltipFlag) {
            list.add(Component.translatable("召唤石"));
            list.add(Component.translatable("召唤石1"));
            list.add(Component.translatable("召唤石2"));
        }
    });

    public static final RegistryObject<Item> ZHAOHUANTAI_BLOCK = Items.register("zhaohuantai_block", ()-> new BlockItem(ModBlock.ZHAOHUANTAI_BLOCK.get(), new Item.Properties().stacksTo(1)){});
    public static final RegistryObject<Item> CIXUE_ORE = Items.register("cixue_ore", ()-> new BlockItem(ModBlock.CIXUE_ORE.get(),new Item.Properties()));
    public static final RegistryObject<Item> MOWU_XUENANG = Items.register("mowu_xuenang", () -> new Item(new Item.Properties()));

    public static final RegistryObject<Item> MOWU_JINGHUA = Items.register("mowu_jinghua", () -> new Item(new Item.Properties()){
        @Override
        public boolean isFoil(@NotNull ItemStack pStack) {
            return true;
        }
        public void appendHoverText(@NotNull ItemStack itemStack, @Nullable Level level, @NotNull List<Component> list, @NotNull TooltipFlag tooltipFlag) {
            list.add(Component.literal("铸造神器的必须品之一").withStyle(ChatFormatting.GRAY));
        }
    });

    public static final RegistryObject<Item> danfang_zixiao      = Items.register("danfang_zixiao", () -> new Item(new Item.Properties()){
        public void appendHoverText(@NotNull ItemStack itemStack, @Nullable Level level, @NotNull List<Component> list, @NotNull TooltipFlag tooltipFlag) {
            list.add(Component.literal("击杀《天罚审判者》有80%概率掉落").withStyle(ChatFormatting.GRAY));
            list.add(Component.literal("魂民可掉落/魂民小屋箱子有概率可生成").withStyle(ChatFormatting.GRAY));
        }
    });

    public static final RegistryObject<Item> danfang_fengming      = Items.register("danfang_fengming", () -> new Item(new Item.Properties()){
        public void appendHoverText(@NotNull ItemStack itemStack, @Nullable Level level, @NotNull List<Component> list, @NotNull TooltipFlag tooltipFlag) {
            list.add(Component.literal("击杀《天罚审判者》有80%概率掉落").withStyle(ChatFormatting.GRAY));
            list.add(Component.literal("魂民可掉落/魂民小屋箱子有概率可生成").withStyle(ChatFormatting.GRAY));
        }
    });

    public static final RegistryObject<Item> danfang_yaochen     = Items.register("danfang_yaochen", () -> new Item(new Item.Properties()){
        public void appendHoverText(@NotNull ItemStack itemStack, @Nullable Level level, @NotNull List<Component> list, @NotNull TooltipFlag tooltipFlag) {
            list.add(Component.literal("Dùng chín viên Đơn phương Cửu hoa vũ lộ để hợp thành").withStyle(ChatFormatting.GRAY));
        }
    });
    public static final RegistryObject<Item> HANJING_GLASS      = Items.register("hanjing_glass", () -> new BlockItem(ModBlock.HANJING_GLASS.get(), new Item.Properties()){
        public void appendHoverText(@NotNull ItemStack itemStack, @Nullable Level level, @NotNull List<Component> list, @NotNull TooltipFlag tooltipFlag) {
            list.add(Component.translatable("寒晶玻璃1").withStyle(ChatFormatting.GRAY));
            list.add(Component.translatable("寒晶玻璃2").withStyle(ChatFormatting.GRAY));
        }
    });

    public static final RegistryObject<Item> SYNTHETIC_DREAM = Items.register("synthetic_dream", () -> new RecordItem(13,ModSounds.SYNTHETICDREAM,new Item.Properties().stacksTo(1),4040){
        @Override
        public void appendHoverText(@NotNull ItemStack itemStack, @org.jetbrains.annotations.Nullable Level pLevel, @NotNull List<Component> list, @NotNull TooltipFlag pFlag) {
            list.add(Component.translatable("dream1").withStyle(ChatFormatting.GRAY));
        }
    });

    public static final RegistryObject<Item> SYNTHETIC_DREAM_MUSIC = Items.register("synthetic_dream_music", () -> new RecordItem(13,ModSounds.SYNTHETICDREAM_MUSIC,new Item.Properties().stacksTo(1),4040){
        @Override
        public void appendHoverText(@NotNull ItemStack itemStack, @org.jetbrains.annotations.Nullable Level pLevel, @NotNull List<Component> list, @NotNull TooltipFlag pFlag) {
            list.add(Component.translatable("dream2").withStyle(ChatFormatting.GRAY));
        }
    });

    public static final RegistryObject<Item> haiyang_suipian      = Items.register("haiyang_suipian", () -> new Item(new Item.Properties()){
        public void appendHoverText(@NotNull ItemStack itemStack, @Nullable Level level, @NotNull List<Component> list, @NotNull TooltipFlag tooltipFlag) {
            list.add(Component.literal("Thẻ bài phụ sẽ rơi ra khi bạn tiêu diệt quái vật của kẻ thù.").withStyle(ChatFormatting.GRAY));
            list.add(Component.literal("Vật liệu bắt buộc để luyện Thần khí Thủy thần").withStyle(ChatFormatting.GRAY));
        }
    });

    public static final RegistryObject<Item> haiyang_zhixin      = Items.register("haiyang_zhixin", () -> new Item(new Item.Properties()){
        public void appendHoverText(@NotNull ItemStack itemStack, @Nullable Level level, @NotNull List<Component> list, @NotNull TooltipFlag tooltipFlag) {
            list.add(Component.literal("Một trong những nguyên liệu thiết yếu để rèn nên vũ khí thần thánh.").withStyle(ChatFormatting.GRAY));
        }
    });

    public static final RegistryObject<Item> zixiaoxianjin_ingot = Items.register("zixiaoxianjin_ingot", () -> new Item(new Item.Properties()));

    public static final RegistryObject<Item> tianshi_suipian      = Items.register("tianshi_suipian", () -> new Item(new Item.Properties()){
        public void appendHoverText(@NotNull ItemStack itemStack, @Nullable Level level, @NotNull List<Component> list, @NotNull TooltipFlag tooltipFlag) {
            list.add(Component.literal("Thẻ bài phụ sẽ rơi ra khi bạn tiêu diệt quái vật của kẻ thù.").withStyle(ChatFormatting.GRAY));
            list.add(Component.literal("Vật liệu cần để luyện Thần khí Thiên sứ").withStyle(ChatFormatting.GRAY));
        }
    });

    public static final RegistryObject<Item> tianshi_zhiguan      = Items.register("tianshi_zhiguan", () -> new Item(new Item.Properties()){
        public void appendHoverText(@NotNull ItemStack itemStack, @Nullable Level level, @NotNull List<Component> list, @NotNull TooltipFlag tooltipFlag) {
            list.add(Component.literal("Một trong những nguyên liệu thiết yếu để rèn nên vũ khí thần thánh.").withStyle(ChatFormatting.GRAY));
        }
    });

    public static final RegistryObject<Item> luocha_suipian      = Items.register("luocha_suipian", () -> new Item(new Item.Properties()){
        public void appendHoverText(@NotNull ItemStack itemStack, @Nullable Level level, @NotNull List<Component> list, @NotNull TooltipFlag tooltipFlag) {
            list.add(Component.literal("Thẻ bài phụ sẽ rơi ra khi bạn tiêu diệt quái vật của kẻ thù.").withStyle(ChatFormatting.GRAY));
            list.add(Component.literal("Vật liệu cần để luyện Thần khí La Sát").withStyle(ChatFormatting.GRAY));
        }
    });

    public static final RegistryObject<Item> luocha_zhitong      = Items.register("luocha_zhitong", () -> new Item(new Item.Properties()){
        public void appendHoverText(@NotNull ItemStack itemStack, @Nullable Level level, @NotNull List<Component> list, @NotNull TooltipFlag tooltipFlag) {
            list.add(Component.literal("Một trong những nguyên liệu thiết yếu để rèn nên vũ khí thần thánh.").withStyle(ChatFormatting.GRAY));
        }
    });

    public static final RegistryObject<Item> xiuluo_suipian      = Items.register("xiuluo_suipian", () -> new Item(new Item.Properties()){
        public void appendHoverText(@NotNull ItemStack itemStack, @Nullable Level level, @NotNull List<Component> list, @NotNull TooltipFlag tooltipFlag) {
            list.add(Component.literal("Thẻ bài phụ sẽ rơi ra khi bạn tiêu diệt quái vật của kẻ thù.").withStyle(ChatFormatting.GRAY));
            list.add(Component.literal("Vật liệu cần để luyện Thần khí Tu La").withStyle(ChatFormatting.GRAY));
        }
    });

    public static final RegistryObject<Item> xiuluo_zhiyin      = Items.register("xiuluo_zhiyin", () -> new Item(new Item.Properties()){
        public void appendHoverText(@NotNull ItemStack itemStack, @Nullable Level level, @NotNull List<Component> list, @NotNull TooltipFlag tooltipFlag) {
            list.add(Component.literal("Một trong những nguyên liệu thiết yếu để rèn nên vũ khí thần thánh.").withStyle(ChatFormatting.GRAY));
        }
    });

    public static final RegistryObject<Item> shenqi_eye      = Items.register("shenqi_eye", () -> new ShenEye(new Item.Properties()){
        public void appendHoverText(@NotNull ItemStack itemStack, @Nullable Level level, @NotNull List<Component> list, @NotNull TooltipFlag tooltipFlag) {
            list.add(Component.literal("Nhấp phải tìm xưởng luyện thần mà Thần vương để lại").withStyle(ChatFormatting.GRAY));
        }
    });

    public static final RegistryObject<Item> hanjing_suipian      = Items.register("hanjing_suipian", () -> new Item(new Item.Properties()){
        public void appendHoverText(@NotNull ItemStack itemStack, @Nullable Level level, @NotNull List<Component> list, @NotNull TooltipFlag tooltipFlag) {
            list.add(Component.literal("Ẩn chứa năng lượng hùng mạnh, nhưng cực kì phổ biến").withStyle(ChatFormatting.GRAY));
            list.add(Component.literal("Có thể dùng làm vật thay thế cho nguyên liệu Kim thạch băng tinh").withStyle(ChatFormatting.GRAY));
        }
    });

    public static final RegistryObject<Item> hungu_fenjie      = Items.register("hungu_fenjie", () -> new BlockItem(ModBlock.HUNGU_FENJIE.get(), new Item.Properties()){
        public void appendHoverText(@NotNull ItemStack itemStack, @Nullable Level level, @NotNull List<Component> list, @NotNull TooltipFlag tooltipFlag) {
            list.add(Component.translatable("Phân hủy xương linh hồn 1").withStyle(ChatFormatting.GRAY));
        }
    });

    public static final RegistryObject<Item> hungu_jinghua      = Items.register("hungu_jinghua", () -> new Hungufen(new Item.Properties()));

    public static final RegistryObject<Item> shencihunhuan_baiwan = Items.register("shencihunhuan_baiwan", () -> new GodGiveHunhuan(new Item.Properties(), 5201314){
        public void appendHoverText(@NotNull ItemStack itemStack, @Nullable Level level, @NotNull List<Component> list, @NotNull TooltipFlag tooltipFlag) {
            list.add(Component.literal("Hồn vòng Thần ban - Trăm vạn năm").withStyle(ChatFormatting.GRAY));
            list.add(Component.literal("hấp phải đặt xuống đất, ngồi lên giống hồn vòng bình thường để hấp thụ").withStyle(ChatFormatting.GRAY));
                                                                                                               list.add(Component.literal("Hồn vòng Thần ban không tiêu hao linh lực hồn vòng").withStyle(ChatFormatting.GRAY));
            list.add(Component.literal("Nhưng sẽ tiêu hao linh lực khi mở võ hồn").withStyle(ChatFormatting.GRAY));
            list.add(Component.literal("Vì vậy trước khi hấp thụ hãy chuẩn bị vài đan dược để tránh không có linh lực duy trì võ hồn dẫn đến hấp thụ thất bại").withStyle(ChatFormatting.GRAY));
            list.add(Component.literal("Hồn vòng Thần ban có những gia tăng đặc biệt không giống hồn vòng bình thường").withStyle(ChatFormatting.GRAY));
            list.add(Component.literal("Chú ý! Hồn vòng Thần ban có thể bị phân giải! Đừng cố gắng phân giải nó, mọi thiệt hại sau khi phân giải không chịu trách nhiệm!").withStyle(ChatFormatting.DARK_RED));
            list.add(Component.literal("Chú ý! Hồn vòng Thần ban sẽ tự động phân giải sau một thời gian nhất định, hãy hấp thụ càng sớm càng tốt!").withStyle(ChatFormatting.DARK_RED));
            list.add(Component.literal("Cách nhận: mua gói quà (chỉ trên máy chủ)").withStyle(ChatFormatting.GRAY));
        }});

    public static final RegistryObject<Item> shencihunhuan_qianwan = Items.register("shencihunhuan_qianwan", () -> new GodGiveHunhuan(new Item.Properties(), 15201314){
        public void appendHoverText(@NotNull ItemStack itemStack, @Nullable Level level, @NotNull List<Component> list, @NotNull TooltipFlag tooltipFlag) {
            list.add(Component.literal("Hồn vòng Thần ban - Thập triệu năm").withStyle(ChatFormatting.GRAY));
            list.add(Component.literal("Nhấp phải đặt xuống đất, ngồi lên giống hồn vòng bình thường để hấp thụ").withStyle(ChatFormatting.GRAY));
            list.add(Component.literal("Hồn vòng Thần ban không tiêu hao linh lực hồn vòng").withStyle(ChatFormatting.GRAY));
            list.add(Component.literal("Nhưng sẽ tiêu hao linh lực khi mở võ hồn").withStyle(ChatFormatting.GRAY));
            list.add(Component.literal("Vì vậy trước khi hấp thụ hãy chuẩn bị vài đan dược để tránh không có linh lực duy trì võ hồn dẫn đến hấp thụ thất bại").withStyle(ChatFormatting.GRAY));
            list.add(Component.literal("Hồn vòng Thần ban có những gia tăng đặc biệt không giống hồn vòng bình thường").withStyle(ChatFormatting.GRAY));
            list.add(Component.literal("Chú ý! Hồn vòng Thần ban có thể bị phân giải! Đừng cố gắng phân giải nó, mọi thiệt hại sau khi phân giải không chịu trách nhiệm!").withStyle(ChatFormatting.DARK_RED));
            list.add(Component.literal("Chú ý! Hồn vòng Thần ban sẽ tự động phân giải sau một thời gian nhất định, hãy hấp thụ càng sớm càng tốt!").withStyle(ChatFormatting.DARK_RED));
            list.add(Component.literal("Cách nhận: mua gói quà (chỉ trên máy chủ)").withStyle(ChatFormatting.GRAY));
        }
    });

    public static final RegistryObject<Item> COLDICE_BUCKET      = Items.register("coldice_bucket", ColdiceItem::new);

    public static final RegistryObject<Item> HOTYANGQUAN_BUCKET = Items.register("hotyangquan_bucket", HotyangquanItem::new);

    public static final RegistryObject<Item> ICECOLD_BUCKET      = Items.register("icecold_bucket", () -> new Item(new Item.Properties().stacksTo(1)){
        public void appendHoverText(@NotNull ItemStack itemStack, @Nullable Level level, @NotNull List<Component> list, @NotNull TooltipFlag tooltipFlag) {
            list.add(Component.literal("Thùng bị băng hóa bởi Tuyền băng Cực hàn").withStyle(ChatFormatting.GRAY));
            list.add(Component.literal("Nấu trong lò cao để giải băng").withStyle(ChatFormatting.GRAY));
        }
    });

    public static final RegistryObject<Item> HOT_BUCKET      = Items.register("hot_bucket", () -> new Item(new Item.Properties().stacksTo(1)){
        public void appendHoverText(@NotNull ItemStack itemStack, @Nullable Level level, @NotNull List<Component> list, @NotNull TooltipFlag tooltipFlag) {
            list.add(Component.literal("Thùng bị Lưuẩn Thủy nóng rực làm rò rỉ").withStyle(ChatFormatting.GRAY));
            list.add(Component.literal("Nấu trong lò cao để lấy hạt sắt").withStyle(ChatFormatting.GRAY));
        }
    });

    public static final RegistryObject<Item> FERTILE_DIRT      = Items.register("fertile_dirt", () -> new BlockItem(ModBlock.FERTILE_DIRT.get(), new Item.Properties()));

    public static final RegistryObject<Item> sunset_log      = Items.register("sunset_log", () -> new BlockItem(ModBlock.sunset_log.get(), new Item.Properties()));
    public static final RegistryObject<Item> sunset_leave      = Items.register("sunset_leave", () -> new BlockItem(ModBlock.sunset_leave.get(), new Item.Properties()));
    public static final RegistryObject<Item> sunset_sapling      = Items.register("sunset_sapling", () -> new BlockItem(ModBlock.sunset_sapling.get(), new Item.Properties()));

    public static final RegistryObject<Item> BLACKSOIL      = Items.register("blacksoil", () -> new BlockItem(ModBlock.BLACKSOIL.get(), new Item.Properties()));

    public static final RegistryObject<Item> COLDHOTEYE_BOOK      = Items.register("coldhoteye_book", () -> new ColdHotEYEBook(new Item.Properties().stacksTo(1)));

    public static final RegistryObject<Item> CHRYSANTHEMUM_TRUNCATUM_EGG = Items.register("chrysanthemum_truncatum_egg", () -> new ForgeSpawnEggItem(ModEntities.CHRYSANTHEMUM_TRUNCATUM, -6163430, -1, new Item.Properties()));
    public static final RegistryObject<Item> LOVERED_EGG = Items.register("lovered_egg", () -> new ForgeSpawnEggItem(ModEntities.LOVERED, -65536, -13108, new Item.Properties()));
    public static final RegistryObject<Item> QILUOTULIP_EGG = Items.register("qiluotulip_egg", () -> new ForgeSpawnEggItem(ModEntities.QILUOTULIP,  -10027264, -10027162,  new Item.Properties()));
    public static final RegistryObject<Item> EVILEYETYRANT_SPAWN_EGG = Items.register("evileyetyrant_spawn_egg", () -> new ForgeSpawnEggItem(ModEntities.EVILEYETYRANT, -3355648, -3407872, new Item.Properties()));
    public static final RegistryObject<Item> TIANFA_SPAWN_EGG = Items.register("tianfa_spawn_egg", () -> new ForgeSpawnEggItem(ModEntities.TIANFA,-10092391, -3407668, new Item.Properties()));
    public static final RegistryObject<Item> MOWU_SPAWN_EGG = Items.register("mowu_spawn_egg", () -> new ForgeSpawnEggItem(ModEntities.MOWU, -3355393, -3342388, new Item.Properties()));
    public static final RegistryObject<Item> DEMON_KING_SPAWN_EGG = Items.register("demon_king_spawn_egg", () -> new ForgeSpawnEggItem(ModEntities.DEMON_KING, -10555393, -3341887, new Item.Properties()));
    public static final RegistryObject<Item> FROST_PRISON_SPAWN_EGG = Items.register("frost_prison_spawn_egg", () -> new ForgeSpawnEggItem(ModEntities.FROST_PRISON, -11907162, -16776961, new Item.Properties()));


    public static ArrayList<RegistryObject<Item>> shengwuegg = new ArrayList<>();
    static {
        shengwuegg.add(CHRYSANTHEMUM_TRUNCATUM_EGG);
        shengwuegg.add(LOVERED_EGG);
        shengwuegg.add(QILUOTULIP_EGG);
        shengwuegg.add(TIANFA_SPAWN_EGG);
        shengwuegg.add(MOWU_SPAWN_EGG);
        shengwuegg.add(EVILEYETYRANT_SPAWN_EGG);
        shengwuegg.add(DEMON_KING_SPAWN_EGG);
        shengwuegg.add(FROST_PRISON_SPAWN_EGG);
    }

    public static final RegistryObject<Item> CHRYSANTHEMUM_TRUNCATUM = Items.register("chrysanthemum_truncatum", () -> new BoneItem(new Item.Properties()).setArramIndex(7));
    public static final RegistryObject<Item> LOVERED = Items.register("lovered", () -> new BoneItem(new Item.Properties()).setArramIndex(7));
    public static final RegistryObject<Item> QILUOTULIP = Items.register("qiluotulip", () -> new BoneItem(new Item.Properties()).setArramIndex(7));

    public static ArrayList<RegistryObject<Item>> xiancaolist = new ArrayList<>();
    static {
        xiancaolist.add(CHRYSANTHEMUM_TRUNCATUM);
        xiancaolist.add(LOVERED);
        xiancaolist.add(QILUOTULIP);
    }

    public static final RegistryObject<Item> ZIXIAOXIANJIN_BLOCK      = Items.register("zixiaoxianjin_block", () -> new BlockItem(ModBlock.ZIXIAOXIANJIN_BLOCK.get(), new Item.Properties()));

    public static final RegistryObject<Item> REINCARNATION_CAPSULE      = Items.register("reincarnation_capsule", () -> new ReincarnationCapsuleItem(new Item.Properties().stacksTo(1)){
        public void appendHoverText(@NotNull ItemStack itemStack, @Nullable Level level, @NotNull List<Component> list, @NotNull TooltipFlag tooltipFlag) {
            list.add(Component.literal("Hồi sinh tán, ăn vào sẽ hồi sinh sau 10 giây").withStyle(ChatFormatting.GRAY));
            list.add(Component.literal("Trong 10 giây mà chết có thể ngăn hồi sinh!").withStyle(ChatFormatting.GRAY));
            list.add(Component.literal("Không thể hợp thành, đồ chỉ có ở máy chủ.").withStyle(ChatFormatting.GRAY));
        }
    });

    public static final RegistryObject<Item> HUNHUAN_STORAGE_ONE = Items.register("hunhuan_storage_one",
            () -> new HunHuanStorageItem(new Item.Properties().stacksTo(1).fireResistant(), 1, 6666));

    public static final RegistryObject<Item> HUNHUAN_STORAGE_TWO = Items.register("hunhuan_storage_two",
            () -> new HunHuanStorageItem(new Item.Properties().stacksTo(1).fireResistant(), 1, 50000));

    public static final RegistryObject<Item> HUNHUAN_STORAGE_THREE = Items.register("hunhuan_storage_three",
            () -> new HunHuanStorageItem(new Item.Properties().stacksTo(1).fireResistant(), 1, 900000));

    public static final RegistryObject<Item> HUNHUAN_STORAGE_FOUR = Items.register("hunhuan_storage_four",
            () -> new HunHuanStorageItem(new Item.Properties().stacksTo(1).fireResistant(), 1, 3000000));

    public static final RegistryObject<Item> HUNHUAN_STORAGE_FIVE = Items.register("hunhuan_storage_five",
            () -> new HunHuanStorageItem(new Item.Properties().stacksTo(1).fireResistant(), 1, 20000000));

    public static ArrayList<RegistryObject<Item>> hunhuanstorage = new ArrayList<>();
    static {
        hunhuanstorage.add(HUNHUAN_STORAGE_ONE);
        hunhuanstorage.add(HUNHUAN_STORAGE_TWO);
        hunhuanstorage.add(HUNHUAN_STORAGE_THREE);
        hunhuanstorage.add(HUNHUAN_STORAGE_FOUR);
        hunhuanstorage.add(HUNHUAN_STORAGE_FIVE);
    }

    public static final RegistryObject<Item> SPACE_RING = Items.register("space_ring", () -> new Item(new Item.Properties().stacksTo(1)){
                public void appendHoverText(@NotNull ItemStack itemStack, @Nullable Level level, @NotNull List<Component> list, @NotNull TooltipFlag tooltipFlag) {
                    list.add(Component.literal("Nhẫn làm từ Thạch không gian").withStyle(ChatFormatting.GRAY));
                    list.add(Component.literal("Nhưng bản thân không có tác dụng thực tế").withStyle(ChatFormatting.GRAY));
                    list.add(Component.literal("Có thể dùng để chế tạo một loại Hồn dẫn khí lưu trữ không gian").withStyle(ChatFormatting.GRAY));
                }
            });

    public static final RegistryObject<Item> SHIWAN_CHALLENGE_TICKET = Items.register("shiwan_challenge_ticket", () -> new Item(new Item.Properties().stacksTo(16)){
        public void appendHoverText(@NotNull ItemStack itemStack, @Nullable Level level, @NotNull List<Component> list, @NotNull TooltipFlag tooltipFlag) {
            list.add(Component.literal("Phiếu thử thách sinh vật thập vạn năm").withStyle(ChatFormatting.GRAY));
            list.add(Component.literal("Tỉ lệ rớt hồn cốt của sinh vật thập vạn năm *13 lần").withStyle(ChatFormatting.GRAY));
            list.add(Component.literal("Rơi ra loại Hồn vòng thập vạn năm (chưa thực hiện)").withStyle(ChatFormatting.GRAY));
            list.add(Component.literal("Các vật phẩm của máy chủ (không thể nhận được theo cách thông thường)）").withStyle(ChatFormatting.GRAY));
        }
    });

    public static final RegistryObject<Item> BAIWAN_CHALLENGE_TICKET = Items.register("baiwan_challenge_ticket", () -> new Item(new Item.Properties().stacksTo(16)){
        public void appendHoverText(@NotNull ItemStack itemStack, @Nullable Level level, @NotNull List<Component> list, @NotNull TooltipFlag tooltipFlag) {
            list.add(Component.literal("Phiếu thử thách sinh vật triệu năm").withStyle(ChatFormatting.GRAY));
            list.add(Component.literal("Tỉ lệ rớt hồn cốt của sinh vật triệu năm *10 lần").withStyle(ChatFormatting.GRAY));
            list.add(Component.literal("Rơi ra loại Hồn vòng triệu năm (chưa thực hiện)").withStyle(ChatFormatting.GRAY));
            list.add(Component.literal("Các mục phía máy chủ (không thể lấy được theo cách thông thường)").withStyle(ChatFormatting.GRAY));
        }
    });

    public static final RegistryObject<Item> QIANWAN_CHALLENGE_TICKET = Items.register("qianwan_challenge_ticket", () -> new Item(new Item.Properties().stacksTo(16)){
        public void appendHoverText(@NotNull ItemStack itemStack, @Nullable Level level, @NotNull List<Component> list, @NotNull TooltipFlag tooltipFlag) {
            list.add(Component.literal("Phiếu thử thách sinh vật thập triệu năm").withStyle(ChatFormatting.GRAY));
            list.add(Component.literal("Tỉ lệ rớt hồn cốt của sinh vật thập triệu năm *2 lần").withStyle(ChatFormatting.GRAY));
            list.add(Component.literal("Rơi ra loại Hồn vòng thập triệu năm (chưa thực hiện)").withStyle(ChatFormatting.GRAY));
            list.add(Component.literal("Các mục phía máy chủ (không thể lấy được theo cách thông thường)").withStyle(ChatFormatting.GRAY));
        }
    });

    public static final RegistryObject<Item> SPECIAL_CHALLENGE_TICKET = Items.register("special_challenge_ticket", () -> new Item(new Item.Properties().stacksTo(16)){
        public void appendHoverText(@NotNull ItemStack itemStack, @Nullable Level level, @NotNull List<Component> list, @NotNull TooltipFlag tooltipFlag) {
            list.add(Component.literal("Phiếu thử thách sinh vật thời hạn đặc biệt").withStyle(ChatFormatting.GRAY));
            list.add(Component.literal("Tỉ lệ rớt hồn cốt của sinh vật thử thách *2 lần").withStyle(ChatFormatting.GRAY));
            list.add(Component.literal("Rơi ra loại Hồn vòng đặc biệt (chưa thực hiện)").withStyle(ChatFormatting.GRAY));
            list.add(Component.literal("Các mục phía máy chủ (không thể lấy được theo cách thông thường)").withStyle(ChatFormatting.GRAY));
        }
    });

    public static ArrayList<RegistryObject<Item>> challengeticket = new ArrayList<>();
    static {
        challengeticket.add(SHIWAN_CHALLENGE_TICKET);
        challengeticket.add(BAIWAN_CHALLENGE_TICKET);
        challengeticket.add(QIANWAN_CHALLENGE_TICKET);
        challengeticket.add(SPECIAL_CHALLENGE_TICKET);
    }

    public static final RegistryObject<Item> HUNHUAN_SHIWAN = Items.register("hunhuan_shiwan", () -> new HunhuanItem(new Item.Properties().stacksTo(1)){
        public void appendHoverText(@NotNull ItemStack itemStack, @Nullable Level level, @NotNull List<Component> list, @NotNull TooltipFlag tooltipFlag) {
            if (itemStack.hasTag() && itemStack.getTag().contains("monster_data")) {
                CompoundTag monsterData = itemStack.getTag().getCompound("monster_data");
                if (monsterData.contains("nianxian")) {
                    int nianxian = monsterData.getInt("nianxian");
                    list.add(Component.literal("Tuổi hiện tại của Vòng Linh Hồn: " + nianxian + "Năm")
                            .withStyle(ChatFormatting.GRAY));
                }
            }
            list.add(Component.literal("Nhẫn Linh Hồn nhận được bằng cách tiêu diệt một sinh vật 100.000 tuổi trong hầm ngục.").withStyle(ChatFormatting.GRAY));
            list.add(Component.literal("Nhấp chuột phải để đặt xuống đất và hấp thụ.").withStyle(ChatFormatting.GRAY));
            list.add(Component.literal("Hãy hấp thụ nó càng sớm càng tốt sau khi đặt xuống, nếu không nó sẽ biến thành lõi linh hồn.").withStyle(ChatFormatting.GRAY));
            list.add(Component.literal("Các mục phía máy chủ (không thể lấy được theo cách thông thường)").withStyle(ChatFormatting.GRAY));
        }
    });

    public static final RegistryObject<Item> HUNHUAN_BAIWAN = Items.register("hunhuan_baiwan", () -> new HunhuanItem(new Item.Properties().stacksTo(1)){
        public void appendHoverText(@NotNull ItemStack itemStack, @Nullable Level level, @NotNull List<Component> list, @NotNull TooltipFlag tooltipFlag) {
            if (itemStack.hasTag() && itemStack.getTag().contains("monster_data")) {
                CompoundTag monsterData = itemStack.getTag().getCompound("monster_data");
                if (monsterData.contains("nianxian")) {
                    int nianxian = monsterData.getInt("nianxian");
                    list.add(Component.literal("Tuổi hiện tại của Vòng Linh Hồn: " + nianxian + "Năm")
                            .withStyle(ChatFormatting.GRAY));
                }
            }
            list.add(Component.literal("Những chiếc nhẫn linh hồn thu được bằng cách tiêu diệt những sinh vật triệu năm tuổi trong hầm ngục.").withStyle(ChatFormatting.GRAY));
            list.add(Component.literal("Nhấp chuột phải để đặt xuống đất và hấp thụ.").withStyle(ChatFormatting.GRAY));
            list.add(Component.literal("Hãy hấp thụ nó càng sớm càng tốt sau khi đặt xuống, nếu không nó sẽ biến thành lõi linh hồn.").withStyle(ChatFormatting.GRAY));
            list.add(Component.literal("Các mục phía máy chủ (không thể lấy được theo cách thông thường)").withStyle(ChatFormatting.GRAY));
        }
    });

    public static final RegistryObject<Item> HUNHUAN_QIANWAN = Items.register("hunhuan_qianwan", () -> new HunhuanItem(new Item.Properties().stacksTo(1)){
        public void appendHoverText(@NotNull ItemStack itemStack, @Nullable Level level, @NotNull List<Component> list, @NotNull TooltipFlag tooltipFlag) {
            if (itemStack.hasTag() && itemStack.getTag().contains("monster_data")) {
                CompoundTag monsterData = itemStack.getTag().getCompound("monster_data");
                if (monsterData.contains("nianxian")) {
                    int nianxian = monsterData.getInt("nianxian");
                    list.add(Component.literal("Tuổi hiện tại của Vòng Linh Hồn: " + nianxian + "Năm")
                            .withStyle(ChatFormatting.GRAY));
                }
            }
            list.add(Component.literal("Những chiếc nhẫn linh hồn thu được bằng cách tiêu diệt những sinh vật đã tồn tại hàng triệu năm trong hầm ngục.").withStyle(ChatFormatting.GRAY));
            list.add(Component.literal("Nhấp chuột phải để đặt xuống đất và hấp thụ.").withStyle(ChatFormatting.GRAY));
            list.add(Component.literal("Hãy hấp thụ nó càng sớm càng tốt sau khi đặt xuống, nếu không nó sẽ biến thành lõi linh hồn.").withStyle(ChatFormatting.GRAY));
            list.add(Component.literal("Các mục phía máy chủ (không thể lấy được theo cách thông thường)").withStyle(ChatFormatting.GRAY));
        }
    });

    public static final RegistryObject<Item> HUNHUAN_SPECIAL = Items.register("hunhuan_special", () -> new HunhuanItem(new Item.Properties().stacksTo(1)){
        public void appendHoverText(@NotNull ItemStack itemStack, @Nullable Level level, @NotNull List<Component> list, @NotNull TooltipFlag tooltipFlag) {
            if (itemStack.hasTag() && itemStack.getTag().contains("monster_data")) {
                CompoundTag monsterData = itemStack.getTag().getCompound("monster_data");
                if (monsterData.contains("nianxian")) {
                    int nianxian = monsterData.getInt("nianxian");
                    list.add(Component.literal("Tuổi hiện tại của Vòng Linh Hồn: " + nianxian + "Năm")
                            .withStyle(ChatFormatting.GRAY));
                }
            }
            list.add(Component.literal("Nhẫn linh hồn nhận được bằng cách đánh bại những sinh vật đặc biệt trong hầm ngục.").withStyle(ChatFormatting.GRAY));
            list.add(Component.literal("Nhấp chuột phải để đặt xuống đất và hấp thụ.").withStyle(ChatFormatting.GRAY));
            list.add(Component.literal("Hãy hấp thụ nó càng sớm càng tốt sau khi đặt xuống, nếu không nó sẽ biến thành lõi linh hồn.").withStyle(ChatFormatting.GRAY));
            list.add(Component.literal("Các mục phía máy chủ (không thể lấy được theo cách thông thường)").withStyle(ChatFormatting.GRAY));
        }
    });

    public static ArrayList<RegistryObject<Item>> hunhuanitem = new ArrayList<>();
    static {
        hunhuanitem.add(HUNHUAN_SHIWAN);
        hunhuanitem.add(HUNHUAN_BAIWAN);
        hunhuanitem.add(HUNHUAN_QIANWAN);
        hunhuanitem.add(HUNHUAN_SPECIAL);
    }

    public static final RegistryObject<Item> STRENGTHENING_TABLE      = Items.register("strengthening_table", () -> new BlockItem(ModBlock.STRENGTHENING_TABLE.get(), new Item.Properties()));


    public static ArrayList<RegistryObject<Item>> shenqilist = new ArrayList<>();
    static {
        shenqilist.add(HAISHEN_SANCHAJI);
        shenqilist.add(XIULUOSHEN_JIAN);
        shenqilist.add(TIANSHISHEN_JIAN);
        shenqilist.add(LUOCHASHEN_MOLIAN);
    }

    public static final RegistryObject<Item> STRENGTHENING_STONE      = Items.register("strengthening_stone", () -> new Item(new Item.Properties().fireResistant()){
        public void appendHoverText(@NotNull ItemStack itemStack, @Nullable Level level, @NotNull List<Component> list, @NotNull TooltipFlag tooltipFlag) {
            list.add(Component.literal("Những yếu tố cần thiết để nâng tầm hiện vật").withStyle(ChatFormatting.GRAY));
        }
    });

    public static final RegistryObject<Item> STRENGTHENING_PROTECT      = Items.register("strengthening_protect", () -> new Item(new Item.Properties().fireResistant()){
        public void appendHoverText(@NotNull ItemStack itemStack, @Nullable Level level, @NotNull List<Component> list, @NotNull TooltipFlag tooltipFlag) {
            list.add(Component.literal("Thể tích bảo vệ được tăng cường").withStyle(ChatFormatting.GRAY));
            list.add(Component.literal("Quá trình nâng cấp thất bại, một thẻ đã bị trừ, mức độ bảo vệ 1.").withStyle(ChatFormatting.GRAY));
            list.add(Component.literal("Việc nâng cấp thành công cũng sẽ trừ đi một thẻ.").withStyle(ChatFormatting.GRAY));
        }
    });

    public static final RegistryObject<Item> STRENGTHENING_SUCCEED      = Items.register("strengthening_succeed", () -> new StrengtheningSucceedItem(new Item.Properties().fireResistant()){
        public void appendHoverText(@NotNull ItemStack itemStack, @Nullable Level level, @NotNull List<Component> list, @NotNull TooltipFlag tooltipFlag) {
            list.add(Component.literal("Cuộn giấy tăng tỷ lệ thành công").withStyle(ChatFormatting.GRAY));
            list.add(Component.literal("Tăng tỷ lệ thành công của quá trình cải tiến").withStyle(ChatFormatting.GRAY));
            list.add(Component.literal("Dù việc nâng cấp thành công hay thất bại, nó cũng sẽ tiêu tốn một thẻ.").withStyle(ChatFormatting.GRAY));
        }
    });

    public static final RegistryObject<Item> STRENGTHENING_DOUBLE      = Items.register("strengthening_double", () -> new Item(new Item.Properties().fireResistant()){
        public void appendHoverText(@NotNull ItemStack itemStack, @Nullable Level level, @NotNull List<Component> list, @NotNull TooltipFlag tooltipFlag) {
            list.add(Component.literal("Cuộn đôi được cải tiến").withStyle(ChatFormatting.GRAY));
            list.add(Component.literal("Việc nâng cấp thành công sẽ tăng cấp độ lên một.").withStyle(ChatFormatting.GRAY));
            list.add(Component.literal("Dù việc nâng cấp thành công hay thất bại, nó cũng sẽ tiêu tốn một thẻ.").withStyle(ChatFormatting.GRAY));
        }
    });

    public static ArrayList<RegistryObject<Item>> qianghuasanjintao = new ArrayList<>();
    static {
        qianghuasanjintao.add(STRENGTHENING_STONE);
        qianghuasanjintao.add(STRENGTHENING_PROTECT);
        qianghuasanjintao.add(STRENGTHENING_SUCCEED);
        qianghuasanjintao.add(STRENGTHENING_DOUBLE);
    }

    public static final RegistryObject<Item> HEART_OF_ICE      = Items.register("heart_of_ice", () -> new Item(new Item.Properties().fireResistant().stacksTo(1)){
        public void appendHoverText(@NotNull ItemStack itemStack, @Nullable Level level, @NotNull List<Component> list, @NotNull TooltipFlag tooltipFlag) {
            list.add(Component.literal("Một vị thần băng giá sa ngã đã tự tạo ra vật kỷ niệm cho riêng mình.").withStyle(ChatFormatting.GRAY));
            list.add(Component.literal("Sau khi đặt nó vào Bàn thờ Âm phủ cổ đại, hãy cúi xuống và nhấp chuột phải để triệu hồi Ngục tù Băng giá.").withStyle(ChatFormatting.GRAY));
        }
    });

    public static final RegistryObject<Item> TYRANT_HEART      = Items.register("tyrant_heart", () -> new Item(new Item.Properties().fireResistant().stacksTo(1)){
        public void appendHoverText(@NotNull ItemStack itemStack, @Nullable Level level, @NotNull List<Component> list, @NotNull TooltipFlag tooltipFlag) {
            list.add(Component.literal("Trái tim rơi ra từ Kẻ bạo chúa Mắt Ác.").withStyle(ChatFormatting.GRAY));
            list.add(Component.literal("Nó có thể được sử dụng như một phương tiện để tổng hợp một loại token nhất định.").withStyle(ChatFormatting.GRAY));
        }
    });

    public static final RegistryObject<Item> DECOMPOSITION_GOSSIP      = Items.register("decomposition_gossip", () -> new Item(new Item.Properties().fireResistant().stacksTo(1)){
        public void appendHoverText(@NotNull ItemStack itemStack, @Nullable Level level, @NotNull List<Component> list, @NotNull TooltipFlag tooltipFlag) {
            list.add(Component.literal("Được sử dụng để tổng hợp các vật phẩm phân hủy Vòng Linh Hồn loại tầm xa.").withStyle(ChatFormatting.GRAY));
        }
    });

    public static final RegistryObject<Item> FIRST_DECOMPOSITION_GOSSIP      = Items.register("first_decomposition_gossip", () -> new DecompositionItem(new Item.Properties().fireResistant().stacksTo(1),1));
    public static final RegistryObject<Item> TWO_DECOMPOSITION_GOSSIP      = Items.register("two_decomposition_gossip", () -> new DecompositionItem(new Item.Properties().fireResistant().stacksTo(1),2));
    public static final RegistryObject<Item> THREE_DECOMPOSITION_GOSSIP      = Items.register("three_decomposition_gossip", () -> new DecompositionItem(new Item.Properties().fireResistant().stacksTo(1),3));
    public static final RegistryObject<Item> FOUR_DECOMPOSITION_GOSSIP      = Items.register("four_decomposition_gossip", () -> new DecompositionItem(new Item.Properties().fireResistant().stacksTo(1),4));
    public static final RegistryObject<Item> FIVE_DECOMPOSITION_GOSSIP      = Items.register("five_decomposition_gossip", () -> new DecompositionItem(new Item.Properties().fireResistant().stacksTo(1),5));

    public static ArrayList<RegistryObject<Item>> decompositiongossip = new ArrayList<>();
    static {
        decompositiongossip.add(DECOMPOSITION_GOSSIP);
        decompositiongossip.add(FIRST_DECOMPOSITION_GOSSIP);
        decompositiongossip.add(TWO_DECOMPOSITION_GOSSIP);
        decompositiongossip.add(THREE_DECOMPOSITION_GOSSIP);
        decompositiongossip.add(FOUR_DECOMPOSITION_GOSSIP);
        decompositiongossip.add(FIVE_DECOMPOSITION_GOSSIP);
    }

    public static final RegistryObject<Item> SHENCIHUNHUAN_SHIWAN = Items.register("shencihunhuan_shiwan", () -> new GodGiveHunhuan(new Item.Properties(), 708888){
        public void appendHoverText(@NotNull ItemStack itemStack, @Nullable Level level, @NotNull List<Component> list, @NotNull TooltipFlag tooltipFlag) {
            list.add(Component.literal("Nhẫn linh hồn do Thượng đế ban tặng - 100.000 năm").withStyle(ChatFormatting.GRAY));
            list.add(Component.literal("Nhấp chuột phải để đặt nó xuống đất, và bạn có thể hấp thụ nó bằng cách ngồi lên, giống như một chiếc nhẫn linh hồn thông thường.").withStyle(ChatFormatting.GRAY));
            list.add(Component.literal("Những chiếc nhẫn linh hồn được ban tặng bởi thần thánh không tiêu hao năng lượng linh hồn của chúng.").withStyle(ChatFormatting.GRAY));
            list.add(Component.literal("Tuy nhiên, nó sẽ tiêu hao năng lượng tâm linh đã sử dụng khi kích hoạt Võ Hồn.").withStyle(ChatFormatting.GRAY));
            list.add(Component.literal("Do đó, hãy chuẩn bị một số viên thuốc trước khi hấp thụ để tránh trường hợp hấp thụ thất bại do thiếu năng lượng tinh thần duy trì võ hồn.").withStyle(ChatFormatting.GRAY));
            list.add(Component.literal("Chú ý! Nhẫn linh hồn được ban tặng có thể tháo rời! Vui lòng không tự ý tháo rời; chúng tôi sẽ không chịu trách nhiệm cho bất kỳ tổn thất nào phát sinh sau khi tháo rời!").withStyle(ChatFormatting.DARK_RED));
            list.add(Component.literal("Chú ý! Nhẫn Linh Hồn Thần Thánh sẽ tự động phân hủy sau một khoảng thời gian nhất định, vì vậy hãy hấp thụ chúng càng sớm càng tốt!").withStyle(ChatFormatting.DARK_RED));
            list.add(Component.literal("Phương thức nhận quà: Mua các gói quà tặng (chỉ dành riêng cho máy chủ) vật phẩm sự kiện").withStyle(ChatFormatting.GRAY));
        }
    });

    public static final RegistryObject<Item> SHENCIHUNHUAN_WAN = Items.register("shencihunhuan_wan", () -> new GodGiveHunhuan(new Item.Properties(), 78888){
        public void appendHoverText(@NotNull ItemStack itemStack, @Nullable Level level, @NotNull List<Component> list, @NotNull TooltipFlag tooltipFlag) {
            list.add(Component.literal("Nhẫn Linh Hồn Thần Thánh - Vạn Năm").withStyle(ChatFormatting.GRAY));
            list.add(Component.literal("Nhấp chuột phải để đặt nó xuống đất, và bạn có thể hấp thụ nó bằng cách ngồi lên, giống như một chiếc nhẫn linh hồn thông thường.").withStyle(ChatFormatting.GRAY));
            list.add(Component.literal("Những chiếc nhẫn linh hồn được ban tặng bởi thần thánh không tiêu hao năng lượng linh hồn của chúng.").withStyle(ChatFormatting.GRAY));
            list.add(Component.literal("Tuy nhiên, nó sẽ tiêu hao năng lượng tâm linh đã sử dụng khi kích hoạt Võ Hồn.").withStyle(ChatFormatting.GRAY));
            list.add(Component.literal("Do đó, hãy chuẩn bị một số viên thuốc trước khi hấp thụ để tránh trường hợp hấp thụ thất bại do thiếu năng lượng tinh thần duy trì võ hồn.").withStyle(ChatFormatting.GRAY));
            list.add(Component.literal("Chú ý! Nhẫn linh hồn được ban tặng có thể tháo rời! Vui lòng không tự ý tháo rời; chúng tôi sẽ không chịu trách nhiệm cho bất kỳ tổn thất nào phát sinh sau khi tháo rời!").withStyle(ChatFormatting.DARK_RED));
            list.add(Component.literal("Chú ý! Nhẫn Linh Hồn Thần Thánh sẽ tự động phân hủy sau một khoảng thời gian nhất định, vì vậy hãy hấp thụ chúng càng sớm càng tốt!").withStyle(ChatFormatting.DARK_RED));
            list.add(Component.literal("Phương thức thu thập: Vật phẩm sự kiện (chỉ dành riêng cho máy chủ)").withStyle(ChatFormatting.GRAY));
        }
    });

    public static final RegistryObject<Item> SHENCIHUNHUAN_QIAN = Items.register("shencihunhuan_qian", () -> new GodGiveHunhuan(new Item.Properties(), 8888){
        public void appendHoverText(@NotNull ItemStack itemStack, @Nullable Level level, @NotNull List<Component> list, @NotNull TooltipFlag tooltipFlag) {
            list.add(Component.literal("神赐魂环 - 千年").withStyle(ChatFormatting.GRAY));
            list.add(Component.literal("Nhấp chuột phải để đặt nó xuống đất, và bạn có thể hấp thụ nó bằng cách ngồi lên, giống như một chiếc nhẫn linh hồn thông thường.").withStyle(ChatFormatting.GRAY));
            list.add(Component.literal("Những chiếc nhẫn linh hồn được ban tặng bởi thần thánh không tiêu hao năng lượng linh hồn của chúng.").withStyle(ChatFormatting.GRAY));
            list.add(Component.literal("Tuy nhiên, nó sẽ tiêu hao năng lượng tâm linh đã sử dụng khi kích hoạt Võ Hồn.").withStyle(ChatFormatting.GRAY));
            list.add(Component.literal("Do đó, hãy chuẩn bị một số viên thuốc trước khi hấp thụ để tránh trường hợp hấp thụ thất bại do thiếu năng lượng tinh thần duy trì võ hồn.").withStyle(ChatFormatting.GRAY));
            list.add(Component.literal("Chú ý! Nhẫn linh hồn được ban tặng có thể tháo rời! Vui lòng không tự ý tháo rời; chúng tôi sẽ không chịu trách nhiệm cho bất kỳ tổn thất nào phát sinh sau khi tháo rời!").withStyle(ChatFormatting.DARK_RED));
            list.add(Component.literal("Chú ý! Nhẫn Linh Hồn Thần Thánh sẽ tự động phân hủy sau một khoảng thời gian nhất định, vì vậy hãy hấp thụ chúng càng sớm càng tốt!").withStyle(ChatFormatting.DARK_RED));
            list.add(Component.literal("Phương thức thu thập: Vật phẩm sự kiện (chỉ dành riêng cho máy chủ)").withStyle(ChatFormatting.GRAY));
        }
    });

    public static ArrayList<RegistryObject<Item>> shencihunhuan = new ArrayList<>();
    static {
        shencihunhuan.add(SHENCIHUNHUAN_QIAN);
        shencihunhuan.add(SHENCIHUNHUAN_WAN);
        shencihunhuan.add(SHENCIHUNHUAN_SHIWAN);
        shencihunhuan.add(shencihunhuan_baiwan);
        shencihunhuan.add(shencihunhuan_qianwan);
    }

}