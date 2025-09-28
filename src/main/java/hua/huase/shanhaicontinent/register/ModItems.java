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
            list.add(Component.literal("使用九个九花雨露丸丹方可合成").withStyle(ChatFormatting.GRAY));
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
            list.add(Component.literal("副手令牌时击杀敌对生物掉落").withStyle(ChatFormatting.GRAY));
            list.add(Component.literal("铸造海神神器的必须品").withStyle(ChatFormatting.GRAY));
        }
    });

    public static final RegistryObject<Item> haiyang_zhixin      = Items.register("haiyang_zhixin", () -> new Item(new Item.Properties()){
        public void appendHoverText(@NotNull ItemStack itemStack, @Nullable Level level, @NotNull List<Component> list, @NotNull TooltipFlag tooltipFlag) {
            list.add(Component.literal("铸造神器的必要材料之一").withStyle(ChatFormatting.GRAY));
        }
    });

    public static final RegistryObject<Item> zixiaoxianjin_ingot = Items.register("zixiaoxianjin_ingot", () -> new Item(new Item.Properties()));

    public static final RegistryObject<Item> tianshi_suipian      = Items.register("tianshi_suipian", () -> new Item(new Item.Properties()){
        public void appendHoverText(@NotNull ItemStack itemStack, @Nullable Level level, @NotNull List<Component> list, @NotNull TooltipFlag tooltipFlag) {
            list.add(Component.literal("副手令牌时击杀敌对生物掉落").withStyle(ChatFormatting.GRAY));
            list.add(Component.literal("铸造天使神神器的必须品").withStyle(ChatFormatting.GRAY));
        }
    });

    public static final RegistryObject<Item> tianshi_zhiguan      = Items.register("tianshi_zhiguan", () -> new Item(new Item.Properties()){
        public void appendHoverText(@NotNull ItemStack itemStack, @Nullable Level level, @NotNull List<Component> list, @NotNull TooltipFlag tooltipFlag) {
            list.add(Component.literal("铸造神器的必要材料之一").withStyle(ChatFormatting.GRAY));
        }
    });

    public static final RegistryObject<Item> luocha_suipian      = Items.register("luocha_suipian", () -> new Item(new Item.Properties()){
        public void appendHoverText(@NotNull ItemStack itemStack, @Nullable Level level, @NotNull List<Component> list, @NotNull TooltipFlag tooltipFlag) {
            list.add(Component.literal("副手令牌时击杀敌对生物掉落").withStyle(ChatFormatting.GRAY));
            list.add(Component.literal("铸造罗刹神神器的必须品").withStyle(ChatFormatting.GRAY));
        }
    });

    public static final RegistryObject<Item> luocha_zhitong      = Items.register("luocha_zhitong", () -> new Item(new Item.Properties()){
        public void appendHoverText(@NotNull ItemStack itemStack, @Nullable Level level, @NotNull List<Component> list, @NotNull TooltipFlag tooltipFlag) {
            list.add(Component.literal("铸造神器的必要材料之一").withStyle(ChatFormatting.GRAY));
        }
    });

    public static final RegistryObject<Item> xiuluo_suipian      = Items.register("xiuluo_suipian", () -> new Item(new Item.Properties()){
        public void appendHoverText(@NotNull ItemStack itemStack, @Nullable Level level, @NotNull List<Component> list, @NotNull TooltipFlag tooltipFlag) {
            list.add(Component.literal("副手令牌时击杀敌对生物掉落").withStyle(ChatFormatting.GRAY));
            list.add(Component.literal("铸造修罗神神器的必须品").withStyle(ChatFormatting.GRAY));
        }
    });

    public static final RegistryObject<Item> xiuluo_zhiyin      = Items.register("xiuluo_zhiyin", () -> new Item(new Item.Properties()){
        public void appendHoverText(@NotNull ItemStack itemStack, @Nullable Level level, @NotNull List<Component> list, @NotNull TooltipFlag tooltipFlag) {
            list.add(Component.literal("铸造神器的必要材料之一").withStyle(ChatFormatting.GRAY));
        }
    });

    public static final RegistryObject<Item> shenqi_eye      = Items.register("shenqi_eye", () -> new ShenEye(new Item.Properties()){
        public void appendHoverText(@NotNull ItemStack itemStack, @Nullable Level level, @NotNull List<Component> list, @NotNull TooltipFlag tooltipFlag) {
            list.add(Component.literal("右键寻找当初神王们留下的神铸工坊").withStyle(ChatFormatting.GRAY));
        }
    });

    public static final RegistryObject<Item> hanjing_suipian      = Items.register("hanjing_suipian", () -> new Item(new Item.Properties()){
        public void appendHoverText(@NotNull ItemStack itemStack, @Nullable Level level, @NotNull List<Component> list, @NotNull TooltipFlag tooltipFlag) {
            list.add(Component.literal("蕴含强大能量，但特别常见").withStyle(ChatFormatting.GRAY));
            list.add(Component.literal("可用于制作寒晶基石材料的替代品").withStyle(ChatFormatting.GRAY));
        }
    });

    public static final RegistryObject<Item> hungu_fenjie      = Items.register("hungu_fenjie", () -> new BlockItem(ModBlock.HUNGU_FENJIE.get(), new Item.Properties()){
        public void appendHoverText(@NotNull ItemStack itemStack, @Nullable Level level, @NotNull List<Component> list, @NotNull TooltipFlag tooltipFlag) {
            list.add(Component.translatable("魂骨分解1").withStyle(ChatFormatting.GRAY));
        }
    });

    public static final RegistryObject<Item> hungu_jinghua      = Items.register("hungu_jinghua", () -> new Hungufen(new Item.Properties()));

    public static final RegistryObject<Item> shencihunhuan_baiwan = Items.register("shencihunhuan_baiwan", () -> new GodGiveHunhuan(new Item.Properties(), 5201314){
        public void appendHoverText(@NotNull ItemStack itemStack, @Nullable Level level, @NotNull List<Component> list, @NotNull TooltipFlag tooltipFlag) {
            list.add(Component.literal("神赐魂环 - 百万年").withStyle(ChatFormatting.GRAY));
            list.add(Component.literal("右键放在地上，和普通魂环一样坐上去即可吸收").withStyle(ChatFormatting.GRAY));
            list.add(Component.literal("神赐魂环不会消耗魂环精神力").withStyle(ChatFormatting.GRAY));
            list.add(Component.literal("但是会消耗开启武魂时消耗的精神力").withStyle(ChatFormatting.GRAY));
            list.add(Component.literal("所以吸收前先准备好一些丹药防止没有精神力维持武魂导致吸收失败").withStyle(ChatFormatting.GRAY));
            list.add(Component.literal("神赐魂环拥有普通魂环不一样的特殊加成").withStyle(ChatFormatting.GRAY));
            list.add(Component.literal("注意！神赐魂环可被分解！不要试图分解它，分解后造成的损失概不负责！").withStyle(ChatFormatting.DARK_RED));
            list.add(Component.literal("注意！神赐魂环到达一定时间会自动分解请尽快吸收！").withStyle(ChatFormatting.DARK_RED));
            list.add(Component.literal("获取途径：购买礼包（服务器专属）").withStyle(ChatFormatting.GRAY));
        }});

    public static final RegistryObject<Item> shencihunhuan_qianwan = Items.register("shencihunhuan_qianwan", () -> new GodGiveHunhuan(new Item.Properties(), 15201314){
        public void appendHoverText(@NotNull ItemStack itemStack, @Nullable Level level, @NotNull List<Component> list, @NotNull TooltipFlag tooltipFlag) {
            list.add(Component.literal("神赐魂环 - 千万年").withStyle(ChatFormatting.GRAY));
            list.add(Component.literal("右键放在地上，和普通魂环一样坐上去即可吸收").withStyle(ChatFormatting.GRAY));
            list.add(Component.literal("神赐魂环不会消耗魂环精神力").withStyle(ChatFormatting.GRAY));
            list.add(Component.literal("但是会消耗开启武魂时消耗的精神力").withStyle(ChatFormatting.GRAY));
            list.add(Component.literal("所以吸收前先准备好一些丹药防止没有精神力维持武魂导致吸收失败").withStyle(ChatFormatting.GRAY));
            list.add(Component.literal("神赐魂环拥有普通魂环不一样的特殊加成").withStyle(ChatFormatting.GRAY));
            list.add(Component.literal("注意！神赐魂环可被分解！不要试图分解它，分解后造成的损失概不负责！").withStyle(ChatFormatting.DARK_RED));
            list.add(Component.literal("注意！神赐魂环到达一定时间会自动分解请尽快吸收！").withStyle(ChatFormatting.DARK_RED));
            list.add(Component.literal("获取途径：购买礼包（服务器专属）").withStyle(ChatFormatting.GRAY));
        }
    });

    public static final RegistryObject<Item> COLDICE_BUCKET      = Items.register("coldice_bucket", ColdiceItem::new);

    public static final RegistryObject<Item> HOTYANGQUAN_BUCKET = Items.register("hotyangquan_bucket", HotyangquanItem::new);

    public static final RegistryObject<Item> ICECOLD_BUCKET      = Items.register("icecold_bucket", () -> new Item(new Item.Properties().stacksTo(1)){
        public void appendHoverText(@NotNull ItemStack itemStack, @Nullable Level level, @NotNull List<Component> list, @NotNull TooltipFlag tooltipFlag) {
            list.add(Component.literal("被寒极冰泉冰封的桶").withStyle(ChatFormatting.GRAY));
            list.add(Component.literal("在高炉中烧炼可解冻").withStyle(ChatFormatting.GRAY));
        }
    });

    public static final RegistryObject<Item> HOT_BUCKET      = Items.register("hot_bucket", () -> new Item(new Item.Properties().stacksTo(1)){
        public void appendHoverText(@NotNull ItemStack itemStack, @Nullable Level level, @NotNull List<Component> list, @NotNull TooltipFlag tooltipFlag) {
            list.add(Component.literal("被炽热阳泉烧漏的桶").withStyle(ChatFormatting.GRAY));
            list.add(Component.literal("在高炉中烧炼可获得铁粒").withStyle(ChatFormatting.GRAY));
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
            list.add(Component.literal("转生胶囊,吃下后会在10秒后转生").withStyle(ChatFormatting.GRAY));
            list.add(Component.literal("10秒内死亡可停止转生！").withStyle(ChatFormatting.GRAY));
            list.add(Component.literal("不可合成,服务器道具。").withStyle(ChatFormatting.GRAY));
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
                    list.add(Component.literal("使用空间石做的戒指").withStyle(ChatFormatting.GRAY));
                    list.add(Component.literal("但本身并没有什么实际作用").withStyle(ChatFormatting.GRAY));
                    list.add(Component.literal("可用来合成某种空间收纳魂导器").withStyle(ChatFormatting.GRAY));
                }
            });

    public static final RegistryObject<Item> SHIWAN_CHALLENGE_TICKET = Items.register("shiwan_challenge_ticket", () -> new Item(new Item.Properties().stacksTo(16)){
        public void appendHoverText(@NotNull ItemStack itemStack, @Nullable Level level, @NotNull List<Component> list, @NotNull TooltipFlag tooltipFlag) {
            list.add(Component.literal("十万年生物挑战券").withStyle(ChatFormatting.GRAY));
            list.add(Component.literal("挑战的十万年生物的魂骨爆率*13倍").withStyle(ChatFormatting.GRAY));
            list.add(Component.literal("掉落物品类十万年魂环（未实装）").withStyle(ChatFormatting.GRAY));
            list.add(Component.literal("服务器道具（不可正常获得）").withStyle(ChatFormatting.GRAY));
        }
    });

    public static final RegistryObject<Item> BAIWAN_CHALLENGE_TICKET = Items.register("baiwan_challenge_ticket", () -> new Item(new Item.Properties().stacksTo(16)){
        public void appendHoverText(@NotNull ItemStack itemStack, @Nullable Level level, @NotNull List<Component> list, @NotNull TooltipFlag tooltipFlag) {
            list.add(Component.literal("百万年生物挑战券").withStyle(ChatFormatting.GRAY));
            list.add(Component.literal("挑战的百万年生物的魂骨爆率*10倍").withStyle(ChatFormatting.GRAY));
            list.add(Component.literal("掉落物品类百万年魂环（未实装）").withStyle(ChatFormatting.GRAY));
            list.add(Component.literal("服务器道具（不可正常获得）").withStyle(ChatFormatting.GRAY));
        }
    });

    public static final RegistryObject<Item> QIANWAN_CHALLENGE_TICKET = Items.register("qianwan_challenge_ticket", () -> new Item(new Item.Properties().stacksTo(16)){
        public void appendHoverText(@NotNull ItemStack itemStack, @Nullable Level level, @NotNull List<Component> list, @NotNull TooltipFlag tooltipFlag) {
            list.add(Component.literal("千万年生物挑战券").withStyle(ChatFormatting.GRAY));
            list.add(Component.literal("挑战的千万年生物的魂骨爆率*2倍").withStyle(ChatFormatting.GRAY));
            list.add(Component.literal("掉落物品类千万年魂环（未实装）").withStyle(ChatFormatting.GRAY));
            list.add(Component.literal("服务器道具（不可正常获得）").withStyle(ChatFormatting.GRAY));
        }
    });

    public static final RegistryObject<Item> SPECIAL_CHALLENGE_TICKET = Items.register("special_challenge_ticket", () -> new Item(new Item.Properties().stacksTo(16)){
        public void appendHoverText(@NotNull ItemStack itemStack, @Nullable Level level, @NotNull List<Component> list, @NotNull TooltipFlag tooltipFlag) {
            list.add(Component.literal("特殊年限生物挑战券").withStyle(ChatFormatting.GRAY));
            list.add(Component.literal("挑战的生物的魂骨爆率*2倍").withStyle(ChatFormatting.GRAY));
            list.add(Component.literal("掉落物品类特殊魂环（未实装）").withStyle(ChatFormatting.GRAY));
            list.add(Component.literal("服务器道具（不可正常获得）").withStyle(ChatFormatting.GRAY));
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
                    list.add(Component.literal("当前魂环年限: " + nianxian + "年")
                            .withStyle(ChatFormatting.GRAY));
                }
            }
            list.add(Component.literal("击杀副本内十万年生物获得的魂环").withStyle(ChatFormatting.GRAY));
            list.add(Component.literal("右键放在地上吸收").withStyle(ChatFormatting.GRAY));
            list.add(Component.literal("放置后尽快吸收否则会变成魂核").withStyle(ChatFormatting.GRAY));
            list.add(Component.literal("服务器道具（不可正常获得）").withStyle(ChatFormatting.GRAY));
        }
    });

    public static final RegistryObject<Item> HUNHUAN_BAIWAN = Items.register("hunhuan_baiwan", () -> new HunhuanItem(new Item.Properties().stacksTo(1)){
        public void appendHoverText(@NotNull ItemStack itemStack, @Nullable Level level, @NotNull List<Component> list, @NotNull TooltipFlag tooltipFlag) {
            if (itemStack.hasTag() && itemStack.getTag().contains("monster_data")) {
                CompoundTag monsterData = itemStack.getTag().getCompound("monster_data");
                if (monsterData.contains("nianxian")) {
                    int nianxian = monsterData.getInt("nianxian");
                    list.add(Component.literal("当前魂环年限: " + nianxian + "年")
                            .withStyle(ChatFormatting.GRAY));
                }
            }
            list.add(Component.literal("击杀副本内百万年生物获得的魂环").withStyle(ChatFormatting.GRAY));
            list.add(Component.literal("右键放在地上吸收").withStyle(ChatFormatting.GRAY));
            list.add(Component.literal("放置后尽快吸收否则会变成魂核").withStyle(ChatFormatting.GRAY));
            list.add(Component.literal("服务器道具（不可正常获得）").withStyle(ChatFormatting.GRAY));
        }
    });

    public static final RegistryObject<Item> HUNHUAN_QIANWAN = Items.register("hunhuan_qianwan", () -> new HunhuanItem(new Item.Properties().stacksTo(1)){
        public void appendHoverText(@NotNull ItemStack itemStack, @Nullable Level level, @NotNull List<Component> list, @NotNull TooltipFlag tooltipFlag) {
            if (itemStack.hasTag() && itemStack.getTag().contains("monster_data")) {
                CompoundTag monsterData = itemStack.getTag().getCompound("monster_data");
                if (monsterData.contains("nianxian")) {
                    int nianxian = monsterData.getInt("nianxian");
                    list.add(Component.literal("当前魂环年限: " + nianxian + "年")
                            .withStyle(ChatFormatting.GRAY));
                }
            }
            list.add(Component.literal("击杀副本内千万年生物获得的魂环").withStyle(ChatFormatting.GRAY));
            list.add(Component.literal("右键放在地上吸收").withStyle(ChatFormatting.GRAY));
            list.add(Component.literal("放置后尽快吸收否则会变成魂核").withStyle(ChatFormatting.GRAY));
            list.add(Component.literal("服务器道具（不可正常获得）").withStyle(ChatFormatting.GRAY));
        }
    });

    public static final RegistryObject<Item> HUNHUAN_SPECIAL = Items.register("hunhuan_special", () -> new HunhuanItem(new Item.Properties().stacksTo(1)){
        public void appendHoverText(@NotNull ItemStack itemStack, @Nullable Level level, @NotNull List<Component> list, @NotNull TooltipFlag tooltipFlag) {
            if (itemStack.hasTag() && itemStack.getTag().contains("monster_data")) {
                CompoundTag monsterData = itemStack.getTag().getCompound("monster_data");
                if (monsterData.contains("nianxian")) {
                    int nianxian = monsterData.getInt("nianxian");
                    list.add(Component.literal("当前魂环年限: " + nianxian + "年")
                            .withStyle(ChatFormatting.GRAY));
                }
            }
            list.add(Component.literal("击杀副本内特殊生物获得的魂环").withStyle(ChatFormatting.GRAY));
            list.add(Component.literal("右键放在地上吸收").withStyle(ChatFormatting.GRAY));
            list.add(Component.literal("放置后尽快吸收否则会变成魂核").withStyle(ChatFormatting.GRAY));
            list.add(Component.literal("服务器道具（不可正常获得）").withStyle(ChatFormatting.GRAY));
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
            list.add(Component.literal("强化神器的必需品").withStyle(ChatFormatting.GRAY));
        }
    });

    public static final RegistryObject<Item> STRENGTHENING_PROTECT      = Items.register("strengthening_protect", () -> new Item(new Item.Properties().fireResistant()){
        public void appendHoverText(@NotNull ItemStack itemStack, @Nullable Level level, @NotNull List<Component> list, @NotNull TooltipFlag tooltipFlag) {
            list.add(Component.literal("强化保护卷").withStyle(ChatFormatting.GRAY));
            list.add(Component.literal("强化失败,扣除一张,保护一级").withStyle(ChatFormatting.GRAY));
            list.add(Component.literal("强化成功也会扣除一张").withStyle(ChatFormatting.GRAY));
        }
    });

    public static final RegistryObject<Item> STRENGTHENING_SUCCEED      = Items.register("strengthening_succeed", () -> new StrengtheningSucceedItem(new Item.Properties().fireResistant()){
        public void appendHoverText(@NotNull ItemStack itemStack, @Nullable Level level, @NotNull List<Component> list, @NotNull TooltipFlag tooltipFlag) {
            list.add(Component.literal("强化成功率卷").withStyle(ChatFormatting.GRAY));
            list.add(Component.literal("增加强化的成功率").withStyle(ChatFormatting.GRAY));
            list.add(Component.literal("无论强化成功或失败，都会消耗一张").withStyle(ChatFormatting.GRAY));
        }
    });

    public static final RegistryObject<Item> STRENGTHENING_DOUBLE      = Items.register("strengthening_double", () -> new Item(new Item.Properties().fireResistant()){
        public void appendHoverText(@NotNull ItemStack itemStack, @Nullable Level level, @NotNull List<Component> list, @NotNull TooltipFlag tooltipFlag) {
            list.add(Component.literal("强化双倍卷").withStyle(ChatFormatting.GRAY));
            list.add(Component.literal("强化成功将额外提升一等级").withStyle(ChatFormatting.GRAY));
            list.add(Component.literal("无论强化成功或失败，都会消耗一张").withStyle(ChatFormatting.GRAY));
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
            list.add(Component.literal("某位冰神陨落人间自制信物").withStyle(ChatFormatting.GRAY));
            list.add(Component.literal("放入古冥圣坛后蹲下右键召唤霜狱").withStyle(ChatFormatting.GRAY));
        }
    });

    public static final RegistryObject<Item> TYRANT_HEART      = Items.register("tyrant_heart", () -> new Item(new Item.Properties().fireResistant().stacksTo(1)){
        public void appendHoverText(@NotNull ItemStack itemStack, @Nullable Level level, @NotNull List<Component> list, @NotNull TooltipFlag tooltipFlag) {
            list.add(Component.literal("击杀邪眼暴君所掉落的心脏").withStyle(ChatFormatting.GRAY));
            list.add(Component.literal("用做媒介可合成为某种信物").withStyle(ChatFormatting.GRAY));
        }
    });

    public static final RegistryObject<Item> DECOMPOSITION_GOSSIP      = Items.register("decomposition_gossip", () -> new Item(new Item.Properties().fireResistant().stacksTo(1)){
        public void appendHoverText(@NotNull ItemStack itemStack, @Nullable Level level, @NotNull List<Component> list, @NotNull TooltipFlag tooltipFlag) {
            list.add(Component.literal("用于合成范围型魂环分解器").withStyle(ChatFormatting.GRAY));
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
            list.add(Component.literal("神赐魂环 - 十万年").withStyle(ChatFormatting.GRAY));
            list.add(Component.literal("右键放在地上，和普通魂环一样坐上去即可吸收").withStyle(ChatFormatting.GRAY));
            list.add(Component.literal("神赐魂环不会消耗魂环精神力").withStyle(ChatFormatting.GRAY));
            list.add(Component.literal("但是会消耗开启武魂时消耗的精神力").withStyle(ChatFormatting.GRAY));
            list.add(Component.literal("所以吸收前先准备好一些丹药防止没有精神力维持武魂导致吸收失败").withStyle(ChatFormatting.GRAY));
            list.add(Component.literal("注意！神赐魂环可被分解！不要试图分解它，分解后造成的损失概不负责！").withStyle(ChatFormatting.DARK_RED));
            list.add(Component.literal("注意！神赐魂环到达一定时间会自动分解请尽快吸收！").withStyle(ChatFormatting.DARK_RED));
            list.add(Component.literal("获取途径：购买礼包（服务器专属）活动道具").withStyle(ChatFormatting.GRAY));
        }
    });

    public static final RegistryObject<Item> SHENCIHUNHUAN_WAN = Items.register("shencihunhuan_wan", () -> new GodGiveHunhuan(new Item.Properties(), 78888){
        public void appendHoverText(@NotNull ItemStack itemStack, @Nullable Level level, @NotNull List<Component> list, @NotNull TooltipFlag tooltipFlag) {
            list.add(Component.literal("神赐魂环 - 万年").withStyle(ChatFormatting.GRAY));
            list.add(Component.literal("右键放在地上，和普通魂环一样坐上去即可吸收").withStyle(ChatFormatting.GRAY));
            list.add(Component.literal("神赐魂环不会消耗魂环精神力").withStyle(ChatFormatting.GRAY));
            list.add(Component.literal("但是会消耗开启武魂时消耗的精神力").withStyle(ChatFormatting.GRAY));
            list.add(Component.literal("所以吸收前先准备好一些丹药防止没有精神力维持武魂导致吸收失败").withStyle(ChatFormatting.GRAY));
            list.add(Component.literal("注意！神赐魂环可被分解！不要试图分解它，分解后造成的损失概不负责！").withStyle(ChatFormatting.DARK_RED));
            list.add(Component.literal("注意！神赐魂环到达一定时间会自动分解请尽快吸收！").withStyle(ChatFormatting.DARK_RED));
            list.add(Component.literal("获取途径：活动道具（服务器专属）").withStyle(ChatFormatting.GRAY));
        }
    });

    public static final RegistryObject<Item> SHENCIHUNHUAN_QIAN = Items.register("shencihunhuan_qian", () -> new GodGiveHunhuan(new Item.Properties(), 8888){
        public void appendHoverText(@NotNull ItemStack itemStack, @Nullable Level level, @NotNull List<Component> list, @NotNull TooltipFlag tooltipFlag) {
            list.add(Component.literal("神赐魂环 - 千年").withStyle(ChatFormatting.GRAY));
            list.add(Component.literal("右键放在地上，和普通魂环一样坐上去即可吸收").withStyle(ChatFormatting.GRAY));
            list.add(Component.literal("神赐魂环不会消耗魂环精神力").withStyle(ChatFormatting.GRAY));
            list.add(Component.literal("但是会消耗开启武魂时消耗的精神力").withStyle(ChatFormatting.GRAY));
            list.add(Component.literal("所以吸收前先准备好一些丹药防止没有精神力维持武魂导致吸收失败").withStyle(ChatFormatting.GRAY));
            list.add(Component.literal("注意！神赐魂环可被分解！不要试图分解它，分解后造成的损失概不负责！").withStyle(ChatFormatting.DARK_RED));
            list.add(Component.literal("注意！神赐魂环到达一定时间会自动分解请尽快吸收！").withStyle(ChatFormatting.DARK_RED));
            list.add(Component.literal("获取途径：活动道具（服务器专属）").withStyle(ChatFormatting.GRAY));
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