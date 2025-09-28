package hua.huase.shanhaicontinent.item.shenjie.shenqi;

import hua.huase.shanhaicontinent.capability.playerattribute.PlayerAttrubuteAPI;
import hua.huase.shanhaicontinent.init.SHModMobEffectsinit;
import hua.huase.shanhaicontinent.item.jineng.WuqiBase;
import hua.huase.shanhaicontinent.item.shenjie.shenqi.Render.TianshiClientEventHandler;
import net.minecraft.ChatFormatting;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.jetbrains.annotations.NotNull;
import net.minecraft.server.MinecraftServer;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Mod.EventBusSubscriber
public class TianShiChangJian extends WuqiBase {
    private static final Map<UUID, SkillData> activeSkills = new HashMap<>(); // 存储正在生效的技能


    private static final float BASE_WUGONG = 1.2000f;
    private static final float BASE_WUFANG = 1.1000f;
    private static final float BASE_MAX_SHENGMING = 1.1500f;
    private static final float BASE_BAOJILV = 60f;
    private static final float BASE_BAOJISHANGHAI = 100f;
    private static final float BASE_SHENGMINGHUIFU = 1.1300f;
    private static final float BASE_SHANBI = 60f;
    private static final float BASE_MINGHZHONG = 35f;

    private static final float UPGRADE_MULTIPLIER = 0.005f; // 乘法属性每级增加
    private static final float UPGRADE_ADDITIVE = 10f;       // 加法属性每级增加

    private static final String UPGRADE_TAG = "UpgradeLevel";


    public static int getUpgradeLevel(ItemStack stack) {
        CompoundTag tag = stack.getOrCreateTag();
        return tag.getInt(UPGRADE_TAG);
    }

    public static void setUpgradeLevel(ItemStack stack, int level) {
        stack.getOrCreateTag().putInt(UPGRADE_TAG, level);
    }

    public float getWugong(Player player, ItemStack itemStack, float value, EquipmentSlot slot) {
        if(slot == EquipmentSlot.MAINHAND){
            int upgradeLevel = getUpgradeLevel(itemStack);
            float upgradedValue = BASE_WUGONG + (upgradeLevel * UPGRADE_MULTIPLIER);
            value += value * upgradedValue;
        }
        return value;
    }

    public float getWufang(Player player, ItemStack itemStack, float value, EquipmentSlot slot) {
        if(slot == EquipmentSlot.MAINHAND){
            int upgradeLevel = getUpgradeLevel(itemStack);
            float upgradedValue = BASE_WUFANG + (upgradeLevel * UPGRADE_MULTIPLIER);
            value += value * upgradedValue;
        }
        return value;
    }

    public float getMaxShengming(Player player, ItemStack itemStack, float value, EquipmentSlot slot) {
        if(slot == EquipmentSlot.MAINHAND){
            int upgradeLevel = getUpgradeLevel(itemStack);
            float upgradedValue = BASE_MAX_SHENGMING + (upgradeLevel * UPGRADE_MULTIPLIER);
            value += value * upgradedValue;
        }
        return value;
    }

    public float getShengminghuifu(Player player, ItemStack itemStack, float value, EquipmentSlot slot) {
        if(slot == EquipmentSlot.MAINHAND){
            int upgradeLevel = getUpgradeLevel(itemStack);
            float upgradedValue = BASE_SHENGMINGHUIFU + (upgradeLevel * UPGRADE_MULTIPLIER);
            value += value * upgradedValue;
        }
        return value;
    }

    // 加法
    public float getBaojilv(Player player, ItemStack itemStack, float value, EquipmentSlot slot) {
        if(slot == EquipmentSlot.MAINHAND){
            int upgradeLevel = getUpgradeLevel(itemStack);
            float upgradedValue = BASE_BAOJILV + (upgradeLevel * UPGRADE_ADDITIVE);
            value += value + upgradedValue;
        }
        return value;
    }

    public float getBaojishanghai(Player player, ItemStack itemStack, float value, EquipmentSlot slot) {
        if(slot == EquipmentSlot.MAINHAND){
            int upgradeLevel = getUpgradeLevel(itemStack);
            float upgradedValue = BASE_BAOJISHANGHAI + (upgradeLevel * UPGRADE_ADDITIVE);
            value += value + upgradedValue;
        }
        return value;
    }

    public float getShanbi(Player player, ItemStack itemStack, float value, EquipmentSlot slot) {
        if(slot == EquipmentSlot.MAINHAND){
            int upgradeLevel = getUpgradeLevel(itemStack);
            float upgradedValue = BASE_SHANBI + (upgradeLevel * UPGRADE_ADDITIVE);
            value += value + upgradedValue;
        }
        return value;
    }

    public float getMinghzong(Player player, ItemStack itemStack, float value, EquipmentSlot slot) {
        if(slot == EquipmentSlot.MAINHAND){
            int upgradeLevel = getUpgradeLevel(itemStack);
            float upgradedValue = BASE_MINGHZHONG + (upgradeLevel * UPGRADE_ADDITIVE);
            value += value + upgradedValue;
        }
        return value;
    }

    @Override
    public @NotNull Component getName(@NotNull ItemStack stack) {
        int upgradeLevel = getUpgradeLevel(stack);
        if (upgradeLevel > 0) {
            return Component.literal(super.getName(stack).getString() + " [+" + upgradeLevel + "]");
        }
        return super.getName(stack);
    }





    public TianShiChangJian() {
        super(new Tier() {
            @Override
            public int getUses() {
                return 0;
            }

            @Override
            public float getSpeed() {
                return 4f;
            }

            @Override
            public float getAttackDamageBonus() {
                return 9996f;
            }

            @Override
            public int getLevel() {
                return 4;
            }

            @Override
            public int getEnchantmentValue() {
                return 2;
            }

            @Override
            public Ingredient getRepairIngredient() {
                return Ingredient.of();
            }
        }, 3, -2f, new Item.Properties().fireResistant());
    }

    @Override
    public @NotNull InteractionResultHolder<ItemStack> use(@NotNull Level level, Player player, @NotNull InteractionHand hand) {
        ItemStack stack = player.getItemInHand(hand);

        CompoundTag nbt = stack.getOrCreateTag();
        String ownerName = nbt.getString("sh_playername");
        if (!ownerName.equals("literal{" + player.getName().getString() + "}")) {
            if (level.isClientSide) {
                player.sendSystemMessage(Component.translatable("神器不是你"));
            }
            return InteractionResultHolder.fail(stack);
        }

        // 检查冷却时间
        if (player.getCooldowns().isOnCooldown(this)) {
            if (level.isClientSide) {
                player.sendSystemMessage(Component.literal("技能冷却中，还剩 " + (player.getCooldowns().getCooldownPercent(this, 0) * 30) + " 秒！"));
            }
            return InteractionResultHolder.fail(stack);
        }

        // 设置冷却时间（30秒）
        player.getCooldowns().addCooldown(this, 30 * 20);

        // 播放技能音效和动作
        if (level.isClientSide) {
            player.swing(hand);
            level.playSound(player, player.getX(), player.getY(), player.getZ(),
                    SoundEvents.ENDER_DRAGON_AMBIENT, SoundSource.PLAYERS, 1.0f, 1.0f);

            // 在客户端触发金色粒子效果
            Vec3 playerPos = player.position();
            double radius = 15.0; // 技能范围
            int duration = 16 * 20; // 技能持续时间（10秒）
            TianshiClientEventHandler.startSkillEffect(playerPos, radius, duration);
        }

        // 执行技能逻辑
        if (!level.isClientSide) {
            performSkill(player, stack);
        }

        return InteractionResultHolder.success(stack);
    }

    private void performSkill(Player player, ItemStack stack) {
        Level level = player.level();

        // 记录技能数据
        SkillData skillData = new SkillData(player.position(), 15.0, 20 * 20); // 技能范围15格，持续时间20秒
        activeSkills.put(player.getUUID(), skillData);

        player.addEffect(new MobEffectInstance(SHModMobEffectsinit.tianshibiyou.get(), 15 * 20, 0)); // 持续时间20秒，等级0
    }

    @SubscribeEvent
    public static void onServerTick(TickEvent.ServerTickEvent event) {
        if (event.phase == TickEvent.Phase.END) {
            // 遍历所有正在生效的技能
            for (Map.Entry<UUID, SkillData> entry : activeSkills.entrySet()) {
                UUID playerId = entry.getKey();
                SkillData skillData = entry.getValue();

                // 获取玩家和技能范围
                MinecraftServer server = event.getServer(); // 获取当前服务器实例
                Player player = server.getPlayerList().getPlayer(playerId); // 通过 UUID 获取玩家
                if (player != null) {
                    Level level = player.level();
                    AABB area = new AABB(skillData.getCenter(), skillData.getCenter()).inflate(skillData.getRadius());

                    // 每 2 秒对普通实体造成伤害，每 3 秒对玩家造成伤害
                    if (skillData.getTicks() % (skillData.isPlayerTarget() ? 60 : 40) == 0) {
                        for (Entity entity : level.getEntities(player, area)) {
                            if (entity instanceof LivingEntity livingEntity) {
                                // 直接造成固定伤害
                                float damage = 1000.0f; // 固定伤害值，可以根据需要调整

                                // 造成伤害，伤害源为玩家自己
                                livingEntity.hurt(level.damageSources().playerAttack(player), damage);
                            }
                        }
                    }

                    // 减少技能持续时间
                    skillData.reduceDuration(1);
                    if (skillData.getDuration() <= 0) {
                        // 技能结束，移除数据
                        activeSkills.remove(playerId);
                    }
                }
            }
        }
    }

    // 技能数据类
    private static class SkillData {
        private final Vec3 center; // 技能中心点
        private final double radius; // 技能范围
        private int duration; // 技能剩余时间
        private int ticks; // 技能已执行 tick 数

        public SkillData(Vec3 center, double radius, int duration) {
            this.center = center;
            this.radius = radius;
            this.duration = duration;
            this.ticks = 0;
        }

        public Vec3 getCenter() {
            return center;
        }

        public double getRadius() {
            return radius;
        }

        public int getDuration() {
            return duration;
        }

        public int getTicks() {
            return ticks;
        }

        public boolean isPlayerTarget() {
            return ticks % 60 == 0; // 每 3 秒一次
        }

        public void reduceDuration(int amount) {
            this.duration -= amount;
            this.ticks += amount;
        }
    }
    @Override
    public void appendHoverText(ItemStack itemStack, Level level, List<Component> list, TooltipFlag tooltipFlag) {
        super.appendHoverText(itemStack, level, list, tooltipFlag);
        int upgradeLevel = getUpgradeLevel(itemStack);

        //乘法
        // 攻击力显示
        float currentWugong = BASE_WUGONG + (upgradeLevel * UPGRADE_MULTIPLIER);
        list.add(Component.literal(String.format("物理攻击: ×%.4f (+%.4f) [×%.4f]",
                BASE_WUGONG,
                (currentWugong - BASE_WUGONG),
                currentWugong)).withStyle(ChatFormatting.YELLOW));

        float currentWufang = BASE_WUFANG + (upgradeLevel * UPGRADE_MULTIPLIER);
        list.add(Component.literal(String.format("物理防御: ×%.4f (+%.4f) [×%.4f]",
                BASE_WUFANG,
                (currentWufang - BASE_WUFANG),
                currentWufang)).withStyle(ChatFormatting.YELLOW));

        float currentMaxshengming = BASE_MAX_SHENGMING + (upgradeLevel * UPGRADE_MULTIPLIER);
        list.add(Component.literal(String.format("最大生命值: ×%.4f (+%.4f) [×%.4f]",
                BASE_MAX_SHENGMING,
                (currentMaxshengming - BASE_MAX_SHENGMING),
                currentMaxshengming)).withStyle(ChatFormatting.YELLOW));

        float currentShengminghuifu = BASE_SHENGMINGHUIFU + (upgradeLevel * UPGRADE_MULTIPLIER);
        list.add(Component.literal(String.format("生命恢复: ×%.4f (+%.4f) [×%.4f]",
                BASE_SHENGMINGHUIFU,
                (currentShengminghuifu - BASE_SHENGMINGHUIFU),
                currentShengminghuifu)).withStyle(ChatFormatting.YELLOW));


        //加法
        // 暴击率显示
        float currentBaojilv = BASE_BAOJILV + (upgradeLevel * UPGRADE_ADDITIVE);
        list.add(Component.literal(String.format("暴击率: %.1f (+%.1f) [%.1f]",
                BASE_BAOJILV,
                (currentBaojilv - BASE_BAOJILV),
                currentBaojilv)).withStyle(ChatFormatting.YELLOW));

        float currentBaojishanghai = BASE_BAOJISHANGHAI + (upgradeLevel * UPGRADE_ADDITIVE);
        list.add(Component.literal(String.format("暴击伤害: %.1f (+%.1f) [%.1f]",
                BASE_BAOJISHANGHAI,
                (currentBaojishanghai - BASE_BAOJISHANGHAI),
                currentBaojishanghai)).withStyle(ChatFormatting.YELLOW));

        float currentShanbi = BASE_SHANBI + (upgradeLevel * UPGRADE_ADDITIVE);
        list.add(Component.literal(String.format("闪避: %.1f (+%.1f) [%.1f]",
                BASE_SHANBI,
                (currentShanbi - BASE_SHANBI),
                currentShanbi)).withStyle(ChatFormatting.YELLOW));

        float currentMingzhong = BASE_MINGHZHONG + (upgradeLevel * UPGRADE_ADDITIVE);
        list.add(Component.literal(String.format("命中: %.1f (+%.1f) [%.1f]",
                BASE_MINGHZHONG,
                (currentMingzhong - BASE_MINGHZHONG),
                currentMingzhong)).withStyle(ChatFormatting.YELLOW));
        list.add(Component.literal("属性解释：例子：物理攻击：基础属性(不变)，(强化属性(单独的强化数值))，[最终属性（基础属性 + 强化属性）]").withStyle(ChatFormatting.GRAY));


        list.add(Component.literal("右键释放天使神力，冷却时间：30秒").withStyle(ChatFormatting.GOLD));
        list.add(Component.literal("以玩家为中心15格范围内生成领域，领域存在时长15秒").withStyle(ChatFormatting.GRAY));
        list.add(Component.literal("实体会每2秒扣除基于你物攻暴击的伤害").withStyle(ChatFormatting.GRAY));
        list.add(Component.literal("玩家会每3秒扣除基于你物攻暴击的伤害").withStyle(ChatFormatting.GRAY));
        list.add(Component.literal("获得天使庇佑BUFF,拥有此buff玩家物防增加70%,物攻20%,吸血:30%").withStyle(ChatFormatting.GRAY));

        CompoundTag nbt = itemStack.getTag();
        if (nbt != null && nbt.contains("sh_playername")) {
            String ownerName = nbt.getString("sh_playername");
            ownerName = ownerName.replace("literal{", "").replace("}", "");
            list.add(Component.translatable("拥有者: " + ownerName).withStyle(ChatFormatting.DARK_GRAY));
        } else {
            list.add(Component.literal("拥有者: 未知").withStyle(ChatFormatting.DARK_GRAY));
        }
    }
}