package hua.huase.shanhaicontinent.tab;

import hua.huase.shanhaicontinent.init.ItemInit;
import hua.huase.shanhaicontinent.register.ModBlock;
import hua.huase.shanhaicontinent.register.ModItems;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.registries.RegistryObject;

public class ShenJieTab
{
    public CreativeModeTab output()
    {
        return CreativeModeTab.builder().title(Component.translatable("itemGroup.ShenJie.tab"))
                .icon(() -> new ItemStack(ModBlock.GRASSSJ_BLOCK.get()))
                .displayItems((parameters,output) ->
                {
                   output.accept(ModItems.cs_dengji.get());
                   output.accept(ModItems.cs_dengjijia.get());
                   output.accept(ModItems.cs_dengjijian.get());
                   output.accept(ModItems.cs_lingpai.get());
                   output.accept(ModItems.cs_shengwuscq.get());
                   output.accept(ModItems.cs_shenqihuoqu.get());
                   output.accept(ModItems.cs_jiadengji.get());
                   output.accept(ItemInit.cs_danyao_shenwangdan.get());
                   output.accept(ModItems.cs_jinengmianban.get());
                   output.accept(ModItems.CS_DENGJIYI.get());
                   output.accept(ModItems.CS_HEALTH.get());

                   output.accept(ModItems.LINGPAI_HAISHEN.get());
                   output.accept(ModItems.LINGPAI_TIANSHISHEN.get());
                   output.accept(ModItems.LINGPAI_XIULUOSHEN.get());
                   output.accept(ModItems.LINGPAI_LUOCHASHEN.get());
                   output.accept(ModItems.HAISHEN_SANCHAJI.get());
                   output.accept(ModItems.XIULUOSHEN_JIAN.get());
                   output.accept(ModItems.TIANSHISHEN_JIAN.get());
                   output.accept(ModItems.LUOCHASHEN_MOLIAN.get());

                   output.accept(ModItems.DENGXIANTAI_BLOCK.get());

                   for (RegistryObject<Item> itemRegistryObject : ModItems.shencihunhuan) {
                      output.accept(itemRegistryObject.get());
                   }

                   output.accept(ModItems.REINCARNATION_CAPSULE.get());
                   output.accept(ModItems.SHENLAN_BLOCK.get());
                   output.accept(ModItems.SHENJIE_CSM.get());
                   output.accept(ModItems.artifact_workbench.get());

                   output.accept(ModItems.GRASSSJ_BLOCK.get());
                   output.accept(ModItems.FERTILE_DIRT.get());
                   output.accept(ModItems.SHENJIEMU_BLOCK.get());
                   output.accept(ModItems.SHENJIEMUSHUYE_BLOCK.get());
                   output.accept(ModItems.SHENJIESHUSHUMIAO_SAPLING.get());
                   output.accept(ModItems.sunset_log.get());
                   output.accept(ModItems.sunset_leave.get());
                   output.accept(ModItems.sunset_sapling.get());

                   output.accept(ItemInit.XUANBING_JIAN.get());
                   output.accept(ItemInit.XUANBING_GAO.get());
                   output.accept(ItemInit.XUANBING_FU.get());
                   output.accept(ItemInit.XUANBING_CHAN.get());
                   output.accept(ItemInit.XUANBING_CHU.get());

                   output.accept(ItemInit.xuanbing_head.get());
                   output.accept(ItemInit.xuanbing_chest.get());
                   output.accept(ItemInit.xuanbing_legs.get());
                   output.accept(ItemInit.xuanbing_feet.get());

                   output.accept(ModItems.XUANBING_BLOCK.get());
                   output.accept(ModItems.CIXUE_ORE.get());
                   output.accept(ModItems.zixiaoxianjin.get());
                   output.accept(ModItems.SILVER_INGOT.get());
                   output.accept(ModItems.XUANBING_STONE.get());
                   output.accept(ModItems.zixiaoxianjin_ingot.get());
                   output.accept(ModItems.ZIXIAOXIANJIN_BLOCK.get());

                   output.accept(ModItems.XUANBINGBLOCK.get());

                   output.accept(ModItems.HANJING_GLASS.get());
                   output.accept(ModItems.hanjing_suipian.get());

                   output.accept(ModItems.pojingdan.get());
                   output.accept(ItemInit.danyao_zhuji.get());
                   output.accept(ItemInit.danyao_zixiao.get());
                   output.accept(ItemInit.danyao_fengming.get());
                   output.accept(ModItems.danfang_zixiao.get());
                   output.accept(ModItems.danfang_fengming.get());
                   output.accept(ModItems.danfang_yaochen.get());

                   output.accept(ModItems.COLDHOTEYE_BOOK.get());
                   output.accept(ModItems.god_eye.get());
                   output.accept(ModItems.shenqi_eye.get());

                   output.accept(ModItems.ZHAOHUANSHI.get());
                   output.accept(ModItems.HEART_OF_ICE.get());
                   output.accept(ModItems.TYRANT_HEART.get());

                   output.accept(ModItems.BLACKSOIL.get());
                   output.accept(ModItems.JIANYING_BLOCK.get());
                   output.accept(ModItems.JIANYING_QINAG.get());
                   output.accept(ModItems.JIANYING_LOUTI.get());
                   output.accept(ModItems.ZHAOHUANTAI_BLOCK.get());
                   output.accept(ModItems.hungu_fenjie.get());
                   output.accept(ModItems.STRENGTHENING_TABLE.get());

                   for (RegistryObject<Item> itemRegistryObject : ModItems.shengwuegg) {
                      output.accept(itemRegistryObject.get());
                   }

                   output.accept(ModItems.MOWU_XUENANG.get());
                   output.accept(ModItems.MOWU_JINGHUA.get());
                   output.accept(ModItems.CHRYSANTHEMUM_TRUNCATUM.get());
                   output.accept(ModItems.LOVERED.get());
                   output.accept(ModItems.QILUOTULIP.get());

                   output.accept(ModItems.SYNTHETIC_DREAM.get());
                   output.accept(ModItems.SYNTHETIC_DREAM_MUSIC.get());

                   output.accept(ModItems.haiyang_suipian.get());
                   output.accept(ModItems.haiyang_zhixin.get());
                   output.accept(ModItems.tianshi_suipian.get());
                   output.accept(ModItems.tianshi_zhiguan.get());
                   output.accept(ModItems.luocha_suipian.get());
                   output.accept(ModItems.luocha_zhitong.get());
                   output.accept(ModItems.xiuluo_suipian.get());
                   output.accept(ModItems.xiuluo_zhiyin.get());

                   output.accept(ModItems.SPACE_RING.get());

                   for (RegistryObject<Item> itemRegistryObject : ModItems.hunhuanstorage) {
                      output.accept(itemRegistryObject.get());
                   }

                   for (RegistryObject<Item> itemRegistryObject : ModItems.challengeticket) {
                      output.accept(itemRegistryObject.get());
                   }

                   for (RegistryObject<Item> itemRegistryObject : ModItems.hunhuanitem) {
                      output.accept(itemRegistryObject.get());
                   }

                   for (RegistryObject<Item> itemRegistryObject : ModItems.qianghuasanjintao) {
                      output.accept(itemRegistryObject.get());
                   }

                   for (RegistryObject<Item> itemRegistryObject : ModItems.decompositiongossip) {
                      output.accept(itemRegistryObject.get());
                   }

                   output.accept(ModItems.hungu_jinghua.get());

                   output.accept(ModItems.COLDICE_BUCKET.get());
                   output.accept(ModItems.HOTYANGQUAN_BUCKET.get());
                   output.accept(ModItems.ICECOLD_BUCKET.get());
                   output.accept(ModItems.HOT_BUCKET.get());





                })
                .build();
    }
}
