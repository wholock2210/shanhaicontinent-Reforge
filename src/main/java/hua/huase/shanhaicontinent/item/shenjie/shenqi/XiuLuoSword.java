package hua.huase.shanhaicontinent.item.shenjie.shenqi;

import hua.huase.shanhaicontinent.init.SHModMobEffectsinit;
import hua.huase.shanhaicontinent.item.jineng.WuqiBase;
import net.minecraft.ChatFormatting;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
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

import java.util.List;

public class XiuLuoSword extends WuqiBase {

    private static final float BASE_WUGONG = 1.3000f;
    private static final float BASE_WUFANG = 1.0050f;
    private static final float BASE_MAX_SHENGMING = 1.0500f;
    private static final float BASE_BAOJILV = 80f;
    private static final float BASE_BAOJISHANGHAI = 150f;
    private static final float BASE_SHENGMINGHUIFU = 1.0500f;
    private static final float BASE_SHANBI = 50f;
    private static final float BASE_MINGHZHONG = 60f;

    private static final float UPGRADE_MULTIPLIER = 0.005f; // 乘法属性每级增加
    private static final float UPGRADE_ADDITIVE = 10f;       // 加法属性每级增加

    private static final String UPGRADE_TAG = "UpgradeLevel";

    public XiuLuoSword() {
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
        player.getCooldowns().addCooldown(this, 30 * 20);
        if (!level.isClientSide) {
            performSkill(player, stack);
        }
        if (level.isClientSide) {
            spawnSphericalParticles(level, player.position(), 8.0);
        }

        return InteractionResultHolder.success(stack);
    }

    private void performSkill(Player player, ItemStack stack) {
        Level level = player.level();
        Vec3 playerPos = player.position();
        double radius = 8.0;
        AABB area = new AABB(playerPos, playerPos).inflate(radius);
        for (Entity entity : level.getEntities(player, area)) {
            if (entity instanceof LivingEntity livingEntity) {
                float damage = 1000.0f;
                livingEntity.hurt(level.damageSources().playerAttack(player), damage);
                livingEntity.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 20 * 20, 1)); // 缓慢2
                livingEntity.addEffect(new MobEffectInstance(MobEffects.BLINDNESS, 20 * 20, 0)); // 失明1
            }
        }
        MobEffectInstance currentBuff = player.getEffect(SHModMobEffectsinit.xiuluo_shixue.get());
        if (currentBuff != null) {
            player.addEffect(new MobEffectInstance(SHModMobEffectsinit.xiuluo_shixue.get(), currentBuff.getDuration() + 15 * 20, currentBuff.getAmplifier()));
        } else {
            player.addEffect(new MobEffectInstance(SHModMobEffectsinit.xiuluo_shixue.get(), 35 * 20, 0));
        }
    }
    private void spawnSphericalParticles(Level level, Vec3 center, double radius) {
        ParticleOptions particle = ParticleTypes.FLAME;
        int segments = 16;
        double angleStep = Math.PI / segments;
        for (double theta = 0; theta <= Math.PI; theta += angleStep) { // 纬度
            for (double phi = 0; phi <= 2 * Math.PI; phi += angleStep) { // 经度
                double x = center.x + radius * Math.sin(theta) * Math.cos(phi);
                double y = center.y + radius * Math.cos(theta);
                double z = center.z + radius * Math.sin(theta) * Math.sin(phi);
                level.addParticle(particle, x, y, z, 0.0, 0.0, 0.0);
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


        list.add(Component.literal("右键释放修罗神力，技能冷却时间：30秒").withStyle(ChatFormatting.GOLD));
        list.add(Component.literal("攻击距离玩家8格以内的所有生物").withStyle(ChatFormatting.GRAY));
        list.add(Component.literal("玩家获得嗜血Buff，持续1分钟，期间击杀一个实体物攻增强3%，最大增幅为765%").withStyle(ChatFormatting.GRAY));
        list.add(Component.literal("若玩家身上拥有嗜血buff则给buff增加15秒的时长").withStyle(ChatFormatting.GRAY));

        CompoundTag nbt = itemStack.getTag();
        if (nbt != null && nbt.contains("sh_playername")) {
            String ownerName = nbt.getString("sh_playername");
            ownerName = ownerName.replace("literal{", "").replace("}", "");
            list.add(Component.translatable("拥有者: " + ownerName).withStyle(ChatFormatting.DARK_GRAY));
        } else {
            list.add(Component.translatable("拥有者: 未知").withStyle(ChatFormatting.DARK_GRAY));
        }
    }
}