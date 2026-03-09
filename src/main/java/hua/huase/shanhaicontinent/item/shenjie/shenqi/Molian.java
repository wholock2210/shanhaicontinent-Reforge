package hua.huase.shanhaicontinent.item.shenjie.shenqi;

import hua.huase.shanhaicontinent.capability.playerattribute.PlayerAttrubuteAPI;
import hua.huase.shanhaicontinent.init.SHModMobEffectsinit;
import hua.huase.shanhaicontinent.item.jineng.WuqiBase;
import hua.huase.shanhaicontinent.item.shenjie.shenqi.Render.ClientEventHandler;
import net.minecraft.ChatFormatting;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.level.Level;
import net.minecraft.network.chat.Component;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraftforge.fml.common.Mod;
import org.jetbrains.annotations.NotNull;

import java.util.*;

@Mod.EventBusSubscriber
public class Molian extends WuqiBase {
    private static final Map<UUID, Long> cooldowns = new HashMap<>();

    private static final float BASE_WUGONG = 1.2500f;
    private static final float BASE_WUFANG = 1.0500f;
    private static final float BASE_MAX_SHENGMING = 1.1000f;
    private static final float BASE_BAOJILV = 50f;
    private static final float BASE_BAOJISHANGHAI = 120f;
    private static final float BASE_SHENGMINGHUIFU = 1.1000f;
    private static final float BASE_SHANBI = 30f;
    private static final float BASE_MINGHZHONG = 60f;

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



    public Molian() {
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
            public @NotNull Ingredient getRepairIngredient() {
                return Ingredient.of();
            }
        }, 3, -2f, new Item.Properties().fireResistant());
    }

    @Override
    public @NotNull InteractionResultHolder<ItemStack> use(@NotNull Level level, Player player, @NotNull InteractionHand hand) {
        ItemStack stack = player.getItemInHand(hand);

        // 检查 NBT 归属
        CompoundTag nbt = stack.getOrCreateTag();
        String ownerName = nbt.getString("sh_playername");
        if (!ownerName.equals("literal{" + player.getName().getString() + "}")) {
            if (level.isClientSide) {
                player.sendSystemMessage(Component.translatable("神器不是你"));
            }
            return InteractionResultHolder.fail(stack);
        }

        if (player.getCooldowns().isOnCooldown(this)) {
            if (level.isClientSide) {
                // 获取冷却剩余时间（总冷却时间为 20 秒 = 20 * 20 ticks）
                int totalCooldownTicks = 20 * 20;
                float cooldownPercent = player.getCooldowns().getCooldownPercent(this, 0);
                int remainingTicks = (int) (totalCooldownTicks * cooldownPercent);
                int remainingSeconds = remainingTicks / 20;
                player.sendSystemMessage(Component.literal("技能冷却中还剩 " + remainingSeconds + " 秒！"));
            }
            return InteractionResultHolder.fail(stack);
        }

        // 播放音效和动作（客户端）
        if (level.isClientSide) {
            player.swing(hand);
            level.playSound(player, player.getX(), player.getY(), player.getZ(),
                    SoundEvents.ENDER_DRAGON_AMBIENT, SoundSource.PLAYERS, 1.0f, 1.0f);

            // 在客户端触发粒子扩散
            Vec3 playerPos = player.position();
            ClientEventHandler.startRipple(playerPos);
        }

        // 服务端逻辑
        if (!level.isClientSide) {
            // 设置冷却时间（20 秒 = 20 * 20 ticks）
            player.getCooldowns().addCooldown(this, 20 * 20);

            // 执行技能逻辑
            performSkill(player, stack);
        }

        return InteractionResultHolder.success(stack);
    }

    private void performSkill(Player player, ItemStack stack) {
        Level level = player.level();

        // 以玩家为中心，创建一个20格的球形范围
        Vec3 playerPos = player.position();
        double radius = 20.0;

        // 获取范围内的所有实体
        AABB area = new AABB(playerPos, playerPos).inflate(radius);
        for (Entity entity : level.getEntities(player, area)) {
            if (entity instanceof LivingEntity livingEntity) {

                // 对玩家进行特殊处理
                if (entity instanceof Player targetPlayer) {
                    // 玩家被直接斩杀的概率是10%
                    if (level.getRandom().nextFloat() < 0.10f) {

                        // 自定义死亡信息
                        targetPlayer.kill();
                        level.broadcastEntityEvent(targetPlayer, (byte) 3); // 播放死亡动画
                        player.sendSystemMessage(Component.literal("您触发了罗刹之力，直接斩杀了 " + targetPlayer.getName().getString() + "！"));
                        targetPlayer.sendSystemMessage(Component.literal("您被罗刹之力触发了斩杀特效，直接死亡！"));

                        // 自定义被斩杀的玩家死亡提示
                        level.getServer().getPlayerList().broadcastSystemMessage(
                                Component.literal(targetPlayer.getName().getString() + " 被 " + player.getName().getString() + "的罗刹魔镰斩成了渣渣"),
                                false
                        );
                        continue;
                    }
                }

                if (livingEntity.getHealth() / livingEntity.getMaxHealth() <= 0.15f) {
                    livingEntity.kill();
                    continue;
                }

                // 直接造成固定伤害 20 点
                float damage = 1000.0f;
                livingEntity.hurt(level.damageSources().playerAttack(player), damage);

                player.addEffect(new MobEffectInstance(SHModMobEffectsinit.luocha_tianwei.get(), 15 * 20, 0));

                // 施加负面效果
                livingEntity.addEffect(new MobEffectInstance(MobEffects.BLINDNESS, 60 * 20, 2));
                livingEntity.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 45 * 20, 1));
                livingEntity.addEffect(new MobEffectInstance(MobEffects.WEAKNESS, 20 * 20, 1));
            }
        }
    }

    @Override
    public void appendHoverText(@NotNull ItemStack itemStack, Level level, @NotNull List<Component> list, @NotNull TooltipFlag tooltipFlag) {
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


        list.add(Component.literal("右键释放罗刹神力，冷却时间 20 秒").withStyle(ChatFormatting.GOLD));
        list.add(Component.literal("玩家为中心20格内所有实体发动攻击，").withStyle(ChatFormatting.GRAY));
        list.add(Component.literal("被罗刹神力攻击到的实体血量低于20%则直接斩杀").withStyle(ChatFormatting.GRAY));
        list.add(Component.literal("被罗刹神力攻击到的玩家有10%的概率直接斩杀").withStyle(ChatFormatting.GRAY));
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