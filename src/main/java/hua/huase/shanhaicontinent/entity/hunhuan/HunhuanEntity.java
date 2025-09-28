package hua.huase.shanhaicontinent.entity.hunhuan;

import hua.huase.shanhaicontinent.capability.monsterattribute.MonsterAttributeCapabilityProvider;
import hua.huase.shanhaicontinent.capability.playerattribute.PlayerAttributeCapability;
import hua.huase.shanhaicontinent.capability.playerattribute.PlayerAttributeCapabilityProvider;
import hua.huase.shanhaicontinent.capability.playerattribute.PlayerAttrubuteAPI;
import hua.huase.shanhaicontinent.capability.playerattribute.PlayerHunHuanAPI;
import hua.huase.shanhaicontinent.entity.hunhe.HunheEntity;
import hua.huase.shanhaicontinent.event.client.SyncShenciAttributesPacket;
import hua.huase.shanhaicontinent.network.NetworkHandler;
import net.minecraft.ChatFormatting;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

import static hua.huase.shanhaicontinent.register.ModItems.hunhuanstorage;

public class HunhuanEntity extends Entity {
    private int existenceTime;
    public int livetime;
    private Player player;
    public HunhuanEntity(EntityType<?> entityType, Level level) {
        super(entityType, level);
    }

    @Override
    public void tick() {
        super.tick();
        if(this.level().isClientSide)return;
        if(this.level().getGameTime()%20 == 0){
            secondtick();
        }
        if(livetime>=1000){
            this.getCapability(MonsterAttributeCapabilityProvider.CAPABILITY).ifPresent(capability -> {
                HunheEntity.createHunhe(HunheEntity.conversionValue(capability.getNianxian()),level(),this.getOnPos());
            });

            this.discard();
        }
        if(player != null && player.getVehicle()!=null && player.getVehicle() == this){
            return;
        }
        livetime++;
    }

    public void secondtick() {
        if (player == null || player.getVehicle() != this) {
            existenceTime = 0;
            return;
        }
        player.getCapability(PlayerAttributeCapabilityProvider.CAPABILITY).ifPresent(playerCap -> {
            if (playerCap.getWuhunList() == null) {
                player.sendSystemMessage(Component.translatable("打开武魂"));
                return;
            }
            getCapability(MonsterAttributeCapabilityProvider.CAPABILITY).ifPresent(hunhuanCap -> {
                boolean isShenci = hunhuanCap.isShenci();
                int nianxian = hunhuanCap.getNianxian();
                if (!PlayerHunHuanAPI.isXishouHunhuan((ServerPlayer) player, this)) {
                    return;
                }
                boolean absorbed = isShenci
                        ? PlayerHunHuanAPI.forceXishouHunhuan(player, this)
                        : PlayerHunHuanAPI.xishouHunhuan(player, this);
                if (absorbed) {
                    handleAbsorbed(isShenci, nianxian);
                    return;
                }
                existenceTime++;
                handleAbsorptionProgress(isShenci, nianxian);
            });
        });
    }

    private void handleAbsorbed(boolean isShenci, int nianxian) {
        if (!isShenci) {
            this.discard();
            return;
        }
        player.getCapability(PlayerAttributeCapabilityProvider.CAPABILITY).ifPresent(playerAttr -> {
            ShenciBonus bonus = ShenciBonus.getBonus(nianxian);
            bonus.applyBonus(playerAttr, PlayerAttrubuteAPI.getDengji(player));
            NetworkHandler.sendToClient(new SyncShenciAttributesPacket(playerAttr), (ServerPlayer) player);
            player.sendSystemMessage(Component.literal(bonus.getCompletionMessage()).withStyle(bonus.getColor()));
        });
        this.discard();
    }

    private void handleAbsorptionProgress(boolean isShenci, int nianxian) {
        if (existenceTime == 1) {
            player.sendSystemMessage(createStartMessage(isShenci, nianxian));
        }
        if (existenceTime % 3 == 0) {
            int progress = calculateProgress(nianxian);
            player.sendSystemMessage(createProgressMessage(isShenci, nianxian, progress));
        }
    }

    private Component createStartMessage(boolean isShenci, int nianxian) {
        if (isShenci) {
            ShenciBonus bonus = ShenciBonus.getBonus(nianxian);
            return Component.literal("开始吸收" + bonus.getTypeName() + "！年限: ")
                    .append(Component.literal(String.format("%,d", nianxian)).withStyle(ChatFormatting.GOLD))
                    .append(Component.literal(" (无需消耗精神力)"))
                    .withStyle(bonus.getColor());
        } else {
            double jingshenli = calculateRequiredJingshenli(nianxian);
            return Component.literal("开始吸收魂环！当前魂环年限为: ")
                    .append(Component.literal(String.format("%,d", nianxian)).withStyle(ChatFormatting.GOLD))
                    .append(Component.literal(" 最少消耗精神力: "))
                    .append(Component.literal(String.format("%.1f", jingshenli)).withStyle(ChatFormatting.RED))
                    .withStyle(ChatFormatting.YELLOW);
        }
    }

    private Component createProgressMessage(boolean isShenci, int nianxian, int progress) {
        ShenciBonus bonus = isShenci ? ShenciBonus.getBonus(nianxian) : null;
        String hunhuanType = isShenci ? bonus.getTypeName() : "魂环";
        ChatFormatting color = isShenci ? bonus.getColor() : ChatFormatting.YELLOW;

        return Component.literal(hunhuanType + "吸收进度: ")
                .append(Component.literal(progress + "%").withStyle(ChatFormatting.GOLD))
                .withStyle(color);
    }

    private int calculateProgress(int nianxian) {
        double v1 = Math.log10(nianxian);
        double v = v1 * 10 + 10;
        return Math.min(100, Math.max(0, (int) ((existenceTime / v) * 100)));
    }

    private double calculateRequiredJingshenli(int nianxian) {
        double logValue = Math.log10(nianxian);
        return nianxian / (logValue * logValue * 0.5);
    }

    private enum ShenciBonus {
        MILLION(1000001, "神赐魂环", ChatFormatting.GOLD) {
            @Override
            public void applyBonus(PlayerAttributeCapability attr, double playerLevel) {
                if (playerLevel <= 50) {
                    attr.setMaxshengming(attr.getMaxshengming() + 600f);
                    attr.setWugong(attr.getWugong() + 50f);
                    attr.setWufang(attr.getWufang() + 20f);
                    attr.setWuchuan(attr.getWuchuan() + 10f);
                    attr.setBaojilv(attr.getBaojilv() + 10f);
                    attr.setBaojishanghai(attr.getBaojishanghai() + 20f);
                } else {
                    attr.setMaxshengming(attr.getMaxshengming() * 1.01f);
                    attr.setWugong(attr.getWugong() * 1.01f);
                    attr.setWufang(attr.getWufang() * 1.01f);
                    attr.setWuchuan(attr.getWuchuan() + 20);
                    attr.setBaojilv(attr.getBaojilv() + 20f);
                    attr.setBaojishanghai(attr.getBaojishanghai() + 40f);
                }
            }
        },
        TEN_MILLION(10000001, "神赐魂环", ChatFormatting.DARK_AQUA) {
            @Override
            public void applyBonus(PlayerAttributeCapability attr, double playerLevel) {
                if (playerLevel <= 50) {
                    attr.setMaxshengming(attr.getMaxshengming() + 1000f);
                    attr.setWugong(attr.getWugong() + 100f);
                    attr.setWufang(attr.getWufang() + 20f);
                    attr.setWuchuan(attr.getWuchuan() + 30f);
                    attr.setBaojilv(attr.getBaojilv() + 20f);
                    attr.setBaojishanghai(attr.getBaojishanghai() + 40f);
                } else {
                    attr.setMaxshengming(attr.getMaxshengming() * 1.05f);
                    attr.setWugong(attr.getWugong() * 1.05f);
                    attr.setWufang(attr.getWufang() * 1.05f);
                    attr.setWuchuan(attr.getWuchuan() + 50);
                    attr.setBaojilv(attr.getBaojilv() + 30f);
                    attr.setBaojishanghai(attr.getBaojishanghai() + 50f);
                }
            }
        };

        private final int threshold;
        private final String typeName;
        private final ChatFormatting color;

        ShenciBonus(int threshold, String typeName, ChatFormatting color) {
            this.threshold = threshold;
            this.typeName = typeName;
            this.color = color;
        }

        public abstract void applyBonus(PlayerAttributeCapability attr, double playerLevel);

        public String getTypeName() {
            return typeName;
        }

        public ChatFormatting getColor() {
            return color;
        }

        public String getCompletionMessage() {
            return typeName + "吸收完成！获得额外加成！";
        }

        public static ShenciBonus getBonus(int nianxian) {
            return nianxian >= TEN_MILLION.threshold ? TEN_MILLION : MILLION;
        }
    }

    public boolean isAttackable() {
        return true;
    }


    public boolean skipAttackInteraction(@NotNull Entity entity) {
        if(entity instanceof Player){
            return false;
        }
        return true;
    }


    public boolean hurt(DamageSource source, float damage) {
        if (this.getPlayer() != null && this.getPlayer().getVehicle() == this) {
            return false;
        }
        if(source.getEntity() instanceof Player){
            livetime += 40;
        }
        return false;
    }

    @Override
    public boolean isPickable() {
        return true;
    }

    public InteractionResult interact(Player player, InteractionHand hand) {
        ItemStack heldItem = player.getItemInHand(hand);
        boolean isHoldingStorageItem = hunhuanstorage.stream()
                .anyMatch(item -> heldItem.getItem() == item.get());
        if (this.getPlayer() != null && this.getPlayer().getVehicle() == this) {
            return InteractionResult.PASS;
        }

        if (player.isSecondaryUseActive()) {
            livetime += 1000;
            return InteractionResult.SUCCESS;
        } else if (!isHoldingStorageItem) {
            if (!this.level().isClientSide) {
                if(player.startRiding(this)){
                    this.player = player;
                    existenceTime = 0;
                    return InteractionResult.CONSUME;
                }
            }
            return InteractionResult.SUCCESS;
        }
        return InteractionResult.PASS;
    }

    @Override
    protected void defineSynchedData() {
    }

    @Override
    public void readAdditionalSaveData(CompoundTag compoundTag) {

    }

    @Override
    public void addAdditionalSaveData(CompoundTag compoundTag) {

    }

    public int getExistenceTime() {
        return existenceTime;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }
}
