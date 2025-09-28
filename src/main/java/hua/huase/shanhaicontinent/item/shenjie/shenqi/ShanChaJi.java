package hua.huase.shanhaicontinent.item.shenjie.shenqi;

import hua.huase.shanhaicontinent.init.SHModMobEffectsinit;
import hua.huase.shanhaicontinent.item.jineng.WuqiBase;
import net.minecraft.ChatFormatting;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Objects;

public class ShanChaJi extends WuqiBase {
    // 基础属性值
    private static final float BASE_WUGONG = 1.1700f;
    private static final float BASE_WUFANG = 1.2000f;
    private static final float BASE_MAX_SHENGMING = 1.2000f;
    private static final float BASE_BAOJILV = 50f;
    private static final float BASE_BAOJISHANGHAI = 100f;
    private static final float BASE_SHENGMINGHUIFU = 1.1000f;
    private static final float BASE_SHANBI = 40f;
    private static final float BASE_MINGHZHONG = 40f;

    // 每级强化增加的数值
    private static final float UPGRADE_MULTIPLIER = 0.005f; // 乘法属性每级增加
    private static final float UPGRADE_ADDITIVE = 10f;       // 加法属性每级增加

    private static final String UPGRADE_TAG = "UpgradeLevel";

    public ShanChaJi() {
        super(new Tier() {
            @Override public int getUses() { return 0; }
            @Override public float getSpeed() { return 4f; }
            @Override public float getAttackDamageBonus() { return 9996f; }
            @Override public int getLevel() { return 4; }
            @Override public int getEnchantmentValue() { return 2; }
            @Override public @NotNull Ingredient getRepairIngredient() { return Ingredient.of(); }
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
                player.sendSystemMessage(Component.literal("技能冷却中，还剩 " + (player.getCooldowns().getCooldownPercent(this, 0) * 20) + " 秒！"));
            }
            return InteractionResultHolder.fail(stack);
        }
        player.getCooldowns().addCooldown(this, 20 * 20);
        if (level.isClientSide) {
            player.swing(hand);
            level.playSound(player, player.getX(), player.getY(), player.getZ(),
                    SoundEvents.TRIDENT_THROW, SoundSource.PLAYERS, 1.0f, 1.0f);
            Vec3 start = player.position().add(0, player.getEyeHeight(), 0);
            Vec3 end = start.add(player.getLookAngle().scale(50));
            AABB area = new AABB(start, end).inflate(2.5, 2.0, 2.5);
            spawnBlueParticles(level, area);
        }
        if (!level.isClientSide) {
            performSkill(player, stack);
        }
        return InteractionResultHolder.success(stack);
    }

    private void performSkill(Player player, ItemStack stack) {
        Level level = player.level();
        Vec3 lookVec = player.getLookAngle();
        Vec3 start = player.position().add(0, player.getEyeHeight(), 0);
        Vec3 end = start.add(lookVec.scale(50));
        AABB area = new AABB(start, end).inflate(2.5, 2.0, 2.5);
        for (Entity entity : level.getEntities(player, area)) {
            if (entity instanceof LivingEntity livingEntity) {
                float damage = 1000.0f;
                livingEntity.hurt(level.damageSources().playerAttack(player), damage);
                player.addEffect(new MobEffectInstance(SHModMobEffectsinit.haishen_xinyang.get(), 15 * 20, 0));
                livingEntity.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 10 * 20, 254)); // 缓慢255
                livingEntity.addEffect(new MobEffectInstance(MobEffects.WEAKNESS, 30 * 20, 1)); // 虚弱2
                if (level.getRandom().nextFloat() < 0.10f) {
                    livingEntity.kill();
                    if (entity instanceof Player targetPlayer) {
                        player.sendSystemMessage(Component.literal("您触发了海神之力，直接斩杀了 " + targetPlayer.getName().getString() + "！"));
                        targetPlayer.sendSystemMessage(Component.literal("您被海神之力触发了斩杀特效，直接死亡！"));
                        Objects.requireNonNull(level.getServer()).getPlayerList().broadcastSystemMessage(
                                Component.literal(targetPlayer.getName().getString() + " 被 " + player.getName().getString() + " 的海神三叉戟捅穿而死"),
                                false
                        );
                    }
                }
            }
        }
    }

    private void spawnBlueParticles(Level level, AABB area) {
        ParticleOptions particle = ParticleTypes.SOUL_FIRE_FLAME;
        double minX = area.minX;
        double minY = area.minY;
        double minZ = area.minZ;
        double maxX = area.maxX;
        double maxY = area.maxY;
        double maxZ = area.maxZ;
        double step = 0.5; // 粒子间距
        for (double x = minX; x <= maxX; x += step) {
            for (double y = minY; y <= maxY; y += step) {
                level.addParticle(particle, x, y, minZ, 0.0, 0.0, 0.0); // 底面
                level.addParticle(particle, x, y, maxZ, 0.0, 0.0, 0.0); // 顶面
            }
        }
        for (double z = minZ; z <= maxZ; z += step) {
            for (double y = minY; y <= maxY; y += step) {
                level.addParticle(particle, minX, y, z, 0.0, 0.0, 0.0); // 左侧面
                level.addParticle(particle, maxX, y, z, 0.0, 0.0, 0.0); // 右侧面
            }
        }
        for (double x = minX; x <= maxX; x += step) {
            for (double z = minZ; z <= maxZ; z += step) {
                level.addParticle(particle, x, minY, z, 0.0, 0.0, 0.0); // 前面
                level.addParticle(particle, x, maxY, z, 0.0, 0.0, 0.0); // 后面
            }
        }
    }

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

    @Override
    public void appendHoverText(@NotNull ItemStack stack, @Nullable Level level, @NotNull List<Component> list, @NotNull TooltipFlag flag) {
        super.appendHoverText(stack, level, list, flag);

        int upgradeLevel = getUpgradeLevel(stack);

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


        list.add(Component.literal("右键释放海神神力,冷却时间：20秒").withStyle(ChatFormatting.GOLD));
        list.add(Component.literal("对正前方50格范围内的所有生物造成20点伤害").withStyle(ChatFormatting.GRAY));
        list.add(Component.literal("施加缓慢255和虚弱2效果").withStyle(ChatFormatting.GRAY));
        list.add(Component.literal("有10%概率直接斩杀命中的实体").withStyle(ChatFormatting.GRAY));


        CompoundTag nbt = stack.getTag();
        if (nbt != null && nbt.contains("sh_playername")) {
            String ownerName = nbt.getString("sh_playername");
            ownerName = ownerName.replace("literal{", "").replace("}", "");
            list.add(Component.translatable("拥有者: " + ownerName).withStyle(ChatFormatting.DARK_GRAY));
        } else {
            list.add(Component.literal("拥有者: 未知").withStyle(ChatFormatting.DARK_GRAY));
        }
    }
}