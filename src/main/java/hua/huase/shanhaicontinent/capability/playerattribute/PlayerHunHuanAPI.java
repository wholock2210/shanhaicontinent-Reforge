package hua.huase.shanhaicontinent.capability.playerattribute;

import hua.huase.shanhaicontinent.capability.monsterattribute.MonsterAttributeCapability;
import hua.huase.shanhaicontinent.capability.monsterattribute.MonsterAttributeCapabilityProvider;
import hua.huase.shanhaicontinent.entity.hunhuan.HunhuanEntity;
import hua.huase.shanhaicontinent.init.AdvenceInit;
import hua.huase.shanhaicontinent.init.ModConfig;
import hua.huase.shanhaicontinent.network.SynsAPI;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.game.ClientboundSetActionBarTextPacket;
import net.minecraft.network.protocol.game.ClientboundSetTitleTextPacket;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.common.util.LazyOptional;
import org.jetbrains.annotations.NotNull;

import java.util.*;

import static hua.huase.shanhaicontinent.SHMainBus.random;
import static hua.huase.shanhaicontinent.capability.playerattribute.PlayerAttributeCapability.wuhunListsnameall;

public interface PlayerHunHuanAPI {

    static void addWuHun(Player player) {

        LazyOptional<PlayerAttributeCapability> capability1 = player.getCapability(PlayerAttributeCapabilityProvider.CAPABILITY);
        if(capability1.isPresent()){
            PlayerAttributeCapability capability = capability1.orElseThrow(RuntimeException::new);
            Object[] array = wuhunListsnameall.toArray();
            boolean b = true;
            while (b){
                String s = (String) array[random.nextInt(array.length)];
                List<MonsterAttributeCapability> monsterAttributeCapabilities = capability.getMonsterCapabilityLists().get(s);
                if(monsterAttributeCapabilities ==null) {
                    capability.getMonsterCapabilityLists().put(s,new ArrayList<>());
                    capability.getWuhunListsname().add(s);
                    capability.setHunhuankuaiguan(capability.getMonsterCapabilityLists().size()-1);
                    player.sendSystemMessage(Component.translatable("成功觉醒武魂",s));
                    ((ServerPlayer)player).connection.send(new ClientboundSetTitleTextPacket(Component.translatable("成功觉醒武魂",s)));
                    b=!b;
                    if(random.nextInt(5) == 0){
                        juexingShuangsheng(player);
                    }
                    //排序
                    Collections.sort(capability.getWuhunListsname());
                    AdvenceInit.juexingwuhuntrigger.trigger((ServerPlayer) player, capability.getWuhunListsname().size());
                    SynsAPI.synsPlayerAttribute(player);
                }
            }
        }
    }

    static void juexingShuangsheng(Player player) {
        LazyOptional<PlayerAttributeCapability> capability1 = player.getCapability(PlayerAttributeCapabilityProvider.CAPABILITY);
        if(capability1.isPresent()){
            PlayerAttributeCapability capability = capability1.orElseThrow(RuntimeException::new);
            Object[] array = wuhunListsnameall.toArray();
            String s = (String) array[random.nextInt(array.length)];
            addWuHun(player,s);
        }
    }

    static void addWuHun(Player player,String name) {

        LazyOptional<PlayerAttributeCapability> capability1 = player.getCapability(PlayerAttributeCapabilityProvider.CAPABILITY);
        if(capability1.isPresent()){
            PlayerAttributeCapability capability = capability1.orElseThrow(RuntimeException::new);

            List<MonsterAttributeCapability> monsterAttributeCapabilities = capability.getMonsterCapabilityLists().get(name);
            if(monsterAttributeCapabilities ==null) {
                capability.getMonsterCapabilityLists().put(name,new ArrayList<>());
                capability.getWuhunListsname().add(name);
                capability.setHunhuankuaiguan(capability.getMonsterCapabilityLists().size()-1);
                player.sendSystemMessage(Component.translatable("成功觉醒武魂",name));
                ((ServerPlayer)player).connection.send(new ClientboundSetTitleTextPacket(Component.translatable("成功觉醒武魂",name)));
                AdvenceInit.juexingwuhuntrigger.trigger((ServerPlayer) player, capability.getWuhunListsname().size());
                SynsAPI.synsPlayerAttribute(player);
            }else {
                player.sendSystemMessage(Component.literal("觉醒失败，已拥有该武魂").withStyle(ChatFormatting.RED));
                ((ServerPlayer)player).connection.send(new ClientboundSetTitleTextPacket(Component.literal("觉醒失败，已拥有该武魂").withStyle(ChatFormatting.RED)));
            }
        }
    }
//50000
//    年限计算精神力消耗
//     （2*年限* （log10(年限)*10+10） * （log10(年限)*10+10）)/（log10(年限)*log(年限)）
    /*
    1.20.1版精神力消耗对照表
年限     精神力
160     65.8
640     162
1280    265
2560    440
5120    744
10240   1273
20480   2203
40960   3850
81920   6786
163840  12051
327680  21543
655360  38742
1310720 70047
2621440 12726
           */
    static boolean xishouHunhuan(Player player, HunhuanEntity entity) {
        boolean absorbed = false;
        if (entity.getCapability(MonsterAttributeCapabilityProvider.CAPABILITY).isPresent()) {
            MonsterAttributeCapability capability = entity.getCapability(MonsterAttributeCapabilityProvider.CAPABILITY).resolve().get();
            double v1 = Math.log10(capability.getNianxian());
            double v = v1 * 10 + 10;
            if (!capability.isShenci()) {
                player.getCapability(PlayerAttributeCapabilityProvider.CAPABILITY).ifPresent(capability1 -> {
                    float jingshenli = (float) ((capability.getNianxian() / (v1 * v1 * 0.5)) / v);
                    capability1.setJingshenli(capability1.getJingshenli() - jingshenli);
                });
            }
            if (entity.getExistenceTime() >= v) {
                addHunhuan(player, entity);
                entity.discard();
                absorbed = true;
            }
        }
        return absorbed;
    }

    static void addHunhuan(Player player, HunhuanEntity entity) {
        player.getCapability(PlayerAttributeCapabilityProvider.CAPABILITY).ifPresent(capability -> {
            entity.getCapability(MonsterAttributeCapabilityProvider.CAPABILITY).ifPresent(monsterCap -> {
                List<String> wuhunNames = capability.getWuhunListsname();
                if (wuhunNames == null || wuhunNames.isEmpty()) {
                    player.sendSystemMessage(Component.translatable("未开启或觉醒武魂"));
                    return;
                }
                int activeIndex = capability.getHunhuankuaiguan();
                if (activeIndex < 0 || activeIndex >= wuhunNames.size()) {
                    activeIndex = wuhunNames.size() - 1;
                    capability.setHunhuankuaiguan(activeIndex);
                }
                String activeName = wuhunNames.get(activeIndex);
                Map<String, List<MonsterAttributeCapability>> map = capability.getMonsterCapabilityLists();
                if (map == null) {
                    map = new HashMap<>();
                }
                List<MonsterAttributeCapability> listForActive = map.get(activeName);
                if (listForActive == null) {
                    listForActive = new ArrayList<>();
                    map.put(activeName, listForActive);
                }
                listForActive.add(monsterCap);
                AdvenceInit.xishouhunhuantrigger.trigger((ServerPlayer) player, monsterCap.getNianxian());
                player.sendSystemMessage(Component.translatable("成功吸收魂环", monsterCap.getNianxian()));
                SynsAPI.synsPlayerAttribute(player);
            });
        });
    }

    static void tupoDengji(ServerPlayer serverPlayer, @NotNull PlayerAttributeCapability capability) {
        if (isTupoDengji(serverPlayer,capability)){
            if(isTupoChenggong(serverPlayer,capability)){
                capability.setDengji(capability.getDengji()+1);
                capability.setJingyan(0);
                capability.setTupochenggonggailv(5);
                addTupoAttibute(capability.getDengji(),capability);
                AdvenceInit.changedengjitrigger.trigger(serverPlayer,capability.getDengji());
                serverPlayer.connection.send(new ClientboundSetTitleTextPacket(Component.translatable("突破成功",capability.getDengji())));
                if(capability.getDengji()==1){
                    addWuHun(serverPlayer);
                }
            }else {
                capability.setJingyan(capability.getMaxjingyan()/2);
                capability.setTupochenggonggailv(capability.getTupochenggonggailv()+1);
                serverPlayer.connection.send(new ClientboundSetTitleTextPacket(Component.translatable("突破失败")));
            }
            SynsAPI.synsPlayerAttribute(serverPlayer);
        }
    }

    //玩家升级后加入的属性
    static void addTupoAttibute(int dengji, PlayerAttributeCapability capability) {
        int zhuanshengshu = capability.getZhuanshengshu();
        // 精神力上限（削弱10%）
        capability.setMaxjingshenli(capability.getMaxjingshenli() + (dengji * 2f + zhuanshengshu * 5) * 0.9f);
        // 生命上限（削弱10%）
        capability.setMaxshengming(capability.getMaxshengming() + (dengji * 0.6f + zhuanshengshu * 5) * 0.9f);
        // 经验上限（不削弱）
        double currentLevel = capability.getDengji();
        float jingyanMultiplier = ModConfig.jingyanbalance.get() ? 5f : 1f;
        float baseJingyan = dengji * jingyanMultiplier + zhuanshengshu * 8;
        float finalJingyan;
        if (currentLevel <= 100) {
            finalJingyan = baseJingyan;
        } else {
            float growthFactor = calculate199GrowthFactor(currentLevel, capability);
            finalJingyan = baseJingyan * growthFactor;
        }
        capability.setMaxjingyan((int)(capability.getMaxjingyan() + finalJingyan));
        // 攻击力（削弱10%）
        capability.setWugong((int)(capability.getWugong() + (dengji * 0.5f + zhuanshengshu * 2) * 0.9f));
        // 防御力（削弱10%）
        capability.setWufang((int)(capability.getWufang() + (dengji * 0.3f + zhuanshengshu * 2) * 0.9f));
        // 穿透（削弱10%）
        capability.setWuchuan((int)(capability.getWuchuan() + (dengji * 0.8f + zhuanshengshu) * 0.9f));
        // 真实伤害（削弱10%）
        capability.setZhenshang((int)(capability.getZhenshang() + (dengji * 0.2f + zhuanshengshu) * 0.9f));
        // 生命恢复（削弱10%）
        capability.setShengminghuifu((int)(capability.getShengminghuifu() + dengji * 0.1f * 0.9f));
    }

    static boolean isTupoChenggong(ServerPlayer serverPlayer, PlayerAttributeCapability capability) {
        int i = random.nextInt(90);
        float v = 100 - capability.getDengji() + capability.getTupochenggonggailv();
        return v>=i;
    }

    private static float calculate199GrowthFactor(double currentLevel,PlayerAttributeCapability capability) {
        int zhuanshengshu = capability.getZhuanshengshu();
        float base = 1.5f;
        float levelBonus = (float)(currentLevel - 100) * 0.020f;
        return Math.min(base + levelBonus + zhuanshengshu * 10, 3.0f);
    }

    static boolean isTupoDengji(ServerPlayer player, @NotNull PlayerAttributeCapability cap) {
        int level = cap.getDengji();
        if (level >= 199) {
            player.connection.send(new ClientboundSetTitleTextPacket(Component.translatable("已经满级")));
            return false;
        }
        if (level == 99) {
            player.connection.send(new ClientboundSetTitleTextPacket(Component.literal("已经满级，请封神后再突破").withStyle(ChatFormatting.RED)));
            return false;
        }
        if (level >= 100 && level < 199) {
            int rings = getMaxRings(cap);
            int required = level / 10;
            if (rings < required) {
                player.connection.send(new ClientboundSetTitleTextPacket(Component.translatable("不能突破")));
                return false;
            }
            return true;
        }
        int rings = getMaxRings(cap);
        if (level >= rings * 10 + 10) {
            player.connection.send(new ClientboundSetTitleTextPacket(Component.translatable("不能突破")));
            return false;
        }
        return true;
    }

    private static int getMaxRings(PlayerAttributeCapability cap) {
        int max = 0;
        Map<String, List<MonsterAttributeCapability>> map = cap.getMonsterCapabilityLists();
        if (map == null || map.isEmpty()) return 0;
        for (List<MonsterAttributeCapability> list : map.values()) {
            if (list != null && list.size() > max) max = list.size();
        }
        return max;
    }

    static boolean isXishouHunhuan(ServerPlayer player, HunhuanEntity hunhuanEntity) {
        LazyOptional<PlayerAttributeCapability> capability1 = player.getCapability(PlayerAttributeCapabilityProvider.CAPABILITY);
        if(capability1.isPresent()){
            PlayerAttributeCapability capability = capability1.orElseThrow(RuntimeException::new);
            int playerLevel = capability.getDengji();
            // 检查是否已觉醒至少一个武魂
            List<String> wuhunNames = capability.getWuhunListsname();
            if(wuhunNames == null || wuhunNames.isEmpty()){
                player.sendSystemMessage(Component.translatable("未开启或觉醒武魂"));
                return false;
            }
            // 等级检查：必须是阶段等级（10 的倍数，10~190 或者 199）
            if(!((playerLevel % 10 == 0 && playerLevel >= 10 && playerLevel <= 190) || playerLevel == 199)) {
                player.sendSystemMessage(Component.translatable("阶段等级").withStyle(ChatFormatting.RED));
                return false;
            }
            // 获取当前魂环数量
            int currentRings = getMaxRings(capability);
            // 计算理论上可拥有的最大魂环数
            int allowedRings = (playerLevel == 199) ? 20 : playerLevel / 10;
            // 如果魂环数量已经达到上限，则不能再吸收
            if (currentRings >= allowedRings) {
                player.sendSystemMessage(Component.translatable("等级不够").withStyle(ChatFormatting.RED));
                return false;
            }
            return true;
        }
        return false;
    }

    static void addJingyan(ServerPlayer player, float value) {
        player.getCapability(PlayerAttributeCapabilityProvider.CAPABILITY).ifPresent(capability -> {
            capability.setJingyan(capability.getJingyan()+value);
            if(capability.getJingyan()>=capability.getMaxjingyan()){
                tupoDengji(player,capability);
            }
            player.connection.send(new ClientboundSetActionBarTextPacket(Component.translatable("吸收经验成功",value)));
        });
    }

    static void addBaojilv(ServerPlayer player, float value) {
        player.getCapability(PlayerAttributeCapabilityProvider.CAPABILITY).ifPresent(capability -> {
            capability.setBaojilv(capability.getBaojilv()+value);

        });
    }

    static void addBaojishanhai(ServerPlayer player, float value) {
        player.getCapability(PlayerAttributeCapabilityProvider.CAPABILITY).ifPresent(capability -> {
            capability.setBaojishanghai(capability.getBaojishanghai()+value);

        });
    }

    static void addKangbao(ServerPlayer player, float value) {
        player.getCapability(PlayerAttributeCapabilityProvider.CAPABILITY).ifPresent(capability -> {
            capability.setKangbao(capability.getKangbao()+value);
        });
    }

    static void addShanbi(ServerPlayer player, float value) {
        player.getCapability(PlayerAttributeCapabilityProvider.CAPABILITY).ifPresent(capability -> {
            capability.setShanbi(capability.getShanbi()+value);
        });
    }

    static void addJingshenli(ServerPlayer player, float value) {
        player.getCapability(PlayerAttributeCapabilityProvider.CAPABILITY).ifPresent(capability -> {
            capability.setJingshenli(Math.min(capability.getJingshenli()+value,capability.getMaxjingshenli()));
        });
    }

    static void addMaxshengming(ServerPlayer player, float value) {
        player.getCapability(PlayerAttributeCapabilityProvider.CAPABILITY).ifPresent(capability -> {
            capability.setMaxshengming(capability.getMaxshengming()+value);
            player.getAttribute(Attributes.MAX_HEALTH).setBaseValue(capability.getMaxshengming()+value);
        });
    }

    static void addMingzhong(ServerPlayer player, float value) {
        player.getCapability(PlayerAttributeCapabilityProvider.CAPABILITY).ifPresent(capability -> {
            capability.setMingzhong(capability.getMingzhong()+value);
        });
    }

    static void addShengming(ServerPlayer player, float value) {
        player.getCapability(PlayerAttributeCapabilityProvider.CAPABILITY).ifPresent(capability -> {
            float v = player.getHealth() + value;
            float maxHealth = player.getMaxHealth();
            player.setHealth(Math.min(v, maxHealth));
        });

    }

    static void addShengminghuifu(ServerPlayer player, float value) {
        player.getCapability(PlayerAttributeCapabilityProvider.CAPABILITY).ifPresent(capability -> {
            capability.setShengminghuifu(capability.getShengminghuifu()+value);
        });
    }

    static void addTupochenggonggailv(ServerPlayer player, float value) {
        player.getCapability(PlayerAttributeCapabilityProvider.CAPABILITY).ifPresent(capability -> {
            capability.setTupochenggonggailv(capability.getTupochenggonggailv()+value);
        });
    }

    static void addWuchuan(ServerPlayer player, float value) {
        player.getCapability(PlayerAttributeCapabilityProvider.CAPABILITY).ifPresent(capability -> {
            capability.setWuchuan(capability.getWuchuan()+value);
        });
    }

    static void addWufang(ServerPlayer player, float value) {
        player.getCapability(PlayerAttributeCapabilityProvider.CAPABILITY).ifPresent(capability -> {
            capability.setWufang(capability.getWufang()+value);
        });
    }

    static void addWugong(ServerPlayer player, float value) {
        player.getCapability(PlayerAttributeCapabilityProvider.CAPABILITY).ifPresent(capability -> {
            capability.setWugong(capability.getWugong()+value);
        });
    }

    static void addXixue(ServerPlayer player, float value) {
        player.getCapability(PlayerAttributeCapabilityProvider.CAPABILITY).ifPresent(capability -> {
            capability.setXixue(capability.getXixue()+value);
        });
    }

    static void addZhenshang(ServerPlayer player, float value) {
        player.getCapability(PlayerAttributeCapabilityProvider.CAPABILITY).ifPresent(capability -> {
            capability.setZhenshang(capability.getZhenshang()+value);
        });
    }

    static void addMaxJingshenli(ServerPlayer player, float value) {
        player.getCapability(PlayerAttributeCapabilityProvider.CAPABILITY).ifPresent(capability -> {
            capability.setMaxjingshenli(capability.getMaxjingshenli()+value);
        });

    }

    static void addManHunhuanT(Player player) {
        player.getCapability(PlayerAttributeCapabilityProvider.CAPABILITY).ifPresent(capability -> {
                if(capability.getWuhunList() == null){
                    player.sendSystemMessage(Component.translatable("未开启或觉醒武魂"));
                    return;
                }
            capability.getWuhunList().clear();

            for (int i = 0; i < 9; i++) {
                MonsterAttributeCapability monsterAttributeCapability = new MonsterAttributeCapability(60 + i * i * i * i * i * i * i * 10);


                capability.getWuhunList().add(monsterAttributeCapability);
            }
                SynsAPI.synsPlayerAttribute(player);
        });
    }

    static void zhuansheng(PlayerAttributeCapability newplayerCapability, PlayerAttributeCapability oldItemCapability, ServerPlayer player) {
        if(newplayerCapability!=null&&oldItemCapability!=null){
            newplayerCapability.setWugong(newplayerCapability.getWugong()+oldItemCapability.getWugong()/20);
            newplayerCapability.setZhenshang(newplayerCapability.getZhenshang()+oldItemCapability.getZhenshang()/20);
            newplayerCapability.setWufang(newplayerCapability.getWufang()+oldItemCapability.getWufang()/20);
            newplayerCapability.setMaxshengming(newplayerCapability.getMaxshengming()+oldItemCapability.getMaxshengming()/20);
            newplayerCapability.setShengminghuifu(newplayerCapability.getShengminghuifu()+oldItemCapability.getShengminghuifu()/20);
            newplayerCapability.setWuchuan(newplayerCapability.getWuchuan()+oldItemCapability.getWuchuan()/20);
            newplayerCapability.setKangbao(newplayerCapability.getKangbao()+oldItemCapability.getKangbao()/20);
            newplayerCapability.setMaxjingshenli((int) (newplayerCapability.getMaxjingshenli()+oldItemCapability.getMaxjingshenli()/20));
            newplayerCapability.setJingshenli(0);
            newplayerCapability.setZhuanshengshu(newplayerCapability.getZhuanshengshu()+oldItemCapability.getZhuanshengshu()+1);
//            魂骨蓸
            newplayerCapability.getBoneslot().deserializeNBT(oldItemCapability.getBoneslot().serializeNBT());
            player.setHealth(newplayerCapability.getMaxshengming());
        }
    }

    // 强制吸收魂环方法
   static boolean forceXishouHunhuan(Player player, HunhuanEntity entity) {
        boolean absorbed = false;
        if (entity.getCapability(MonsterAttributeCapabilityProvider.CAPABILITY).isPresent()) {
            MonsterAttributeCapability capability = entity.getCapability(MonsterAttributeCapabilityProvider.CAPABILITY).resolve().get();
            double v1 = Math.log10(capability.getNianxian());
            double v = v1 * 10 + 10;

            if (entity.getExistenceTime() >= v) {
                addHunhuan(player, entity);
                entity.discard();
                absorbed = true;
            }
        }
        return absorbed;
    }

}
