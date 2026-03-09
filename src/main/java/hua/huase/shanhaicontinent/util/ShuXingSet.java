package hua.huase.shanhaicontinent.util;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import hua.huase.shanhaicontinent.capability.playerattribute.PlayerAttributeCapabilityProvider;
import hua.huase.shanhaicontinent.command.ChangeAttributeCommand;
import hua.huase.shanhaicontinent.network.SynsAPI;
import net.minecraft.ChatFormatting;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.arguments.EntityArgument;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.Entity;

public class ShuXingSet extends ChangeAttributeCommand {
    public static LiteralArgumentBuilder<CommandSourceStack> register() {
        return Commands.literal("attribute")
                .requires(cs -> cs.hasPermission(2))
                .then(Commands.argument("target", EntityArgument.entity())
                        .then(Commands.literal("setmaxshengming")
                                .then(Commands.argument("amount", IntegerArgumentType.integer())
                                        .executes(ctx -> setMaxshengming(EntityArgument.getEntity(ctx, "target"), IntegerArgumentType.getInteger(ctx, "amount")))
                                ))
                        .then(Commands.literal("setmaxjingshenli")
                                .then(Commands.argument("amount", IntegerArgumentType.integer())
                                        .executes(ctx -> setMaxjingshenli(EntityArgument.getEntity(ctx, "target"), IntegerArgumentType.getInteger(ctx, "amount")))
                                ))
                        .then(Commands.literal("setWugong")
                                .then(Commands.argument("amount", IntegerArgumentType.integer())
                                        .executes(ctx -> setWugong(EntityArgument.getEntity(ctx, "target"), IntegerArgumentType.getInteger(ctx, "amount")))
                                ))
                        .then(Commands.literal("setWufang")
                                .then(Commands.argument("amount", IntegerArgumentType.integer())
                                        .executes(ctx -> setWufang(EntityArgument.getEntity(ctx, "target"), IntegerArgumentType.getInteger(ctx, "amount")))
                                ))
                        .then(Commands.literal("setBaojishanghai")
                                .then(Commands.argument("amount", IntegerArgumentType.integer())
                                        .executes(ctx -> setBaojishanghai(EntityArgument.getEntity(ctx, "target"), IntegerArgumentType.getInteger(ctx, "amount")))
                                ))
                        .then(Commands.literal("setBaojilv")
                                .then(Commands.argument("amount", IntegerArgumentType.integer())
                                        .executes(ctx -> setBaojilv(EntityArgument.getEntity(ctx, "target"), IntegerArgumentType.getInteger(ctx, "amount")))
                                ))
                        .then(Commands.literal("setWuchuan")
                                .then(Commands.argument("amount", IntegerArgumentType.integer())
                                        .executes(ctx -> setWuchuan(EntityArgument.getEntity(ctx, "target"), IntegerArgumentType.getInteger(ctx, "amount")))
                                ))
                        .then(Commands.literal("setKangbao")
                                .then(Commands.argument("amount", IntegerArgumentType.integer())
                                        .executes(ctx -> setKangbao(EntityArgument.getEntity(ctx, "target"), IntegerArgumentType.getInteger(ctx, "amount")))
                                ))
                        .then(Commands.literal("setXixue")
                                .then(Commands.argument("amount", IntegerArgumentType.integer())
                                        .executes(ctx -> setXixue(EntityArgument.getEntity(ctx, "target"), IntegerArgumentType.getInteger(ctx, "amount")))
                                ))
                        .then(Commands.literal("setShengminghuifu")
                                .then(Commands.argument("amount", IntegerArgumentType.integer())
                                        .executes(ctx -> setShengminghuifu(EntityArgument.getEntity(ctx, "target"), IntegerArgumentType.getInteger(ctx, "amount")))
                                ))
                        .then(Commands.literal("setMingzhong")
                                .then(Commands.argument("amount", IntegerArgumentType.integer())
                                        .executes(ctx -> setMinghzhong(EntityArgument.getEntity(ctx, "target"), IntegerArgumentType.getInteger(ctx, "amount")))
                                ))
                        .then(Commands.literal("setShanbi")
                                .then(Commands.argument("amount", IntegerArgumentType.integer())
                                        .executes(ctx -> setShanbi(EntityArgument.getEntity(ctx, "target"), IntegerArgumentType.getInteger(ctx, "amount")))
                                ))
                        .then(Commands.literal("setDengji")
                                .then(Commands.argument("amount", IntegerArgumentType.integer())
                                        .executes(ctx -> setDengji(EntityArgument.getEntity(ctx, "target"), IntegerArgumentType.getInteger(ctx, "amount")))
                                ))
                        .then(Commands.literal("setMaxjingyan")
                                .then(Commands.argument("amount", IntegerArgumentType.integer())
                                        .executes(ctx -> setDengji(EntityArgument.getEntity(ctx, "target"), IntegerArgumentType.getInteger(ctx, "amount")))
                                ))
                );
    }

    public static int setMaxshengming(Entity player, int num) {
        player.getCapability(PlayerAttributeCapabilityProvider.CAPABILITY).ifPresent(capability -> {
            capability.setMaxshengming(Math.max(1, num));
        });
        SynsAPI.synsPlayerAttribute(player);
        return Command.SINGLE_SUCCESS;
    }

    public static int setMaxjingshenli(Entity player, int num) {
        player.getCapability(PlayerAttributeCapabilityProvider.CAPABILITY).ifPresent(capability -> {
            capability.setMaxjingshenli(Math.max(0, num));
        });
        SynsAPI.synsPlayerAttribute(player);
        return Command.SINGLE_SUCCESS;
    }

    public static int setWugong(Entity player, int num) {
        player.getCapability(PlayerAttributeCapabilityProvider.CAPABILITY).ifPresent(capability -> {
            capability.setWugong(Math.max(0, num));
        });
        SynsAPI.synsPlayerAttribute(player);
        return Command.SINGLE_SUCCESS;
    }

    public static int setWufang(Entity player, int num) {
        player.getCapability(PlayerAttributeCapabilityProvider.CAPABILITY).ifPresent(capability -> {
            capability.setWufang(Math.max(0, num));
        });
        SynsAPI.synsPlayerAttribute(player);
        return Command.SINGLE_SUCCESS;
    }

    public static int setBaojishanghai(Entity player, int num) {
        player.getCapability(PlayerAttributeCapabilityProvider.CAPABILITY).ifPresent(capability -> {
            capability.setBaojishanghai(Math.max(0, num));
        });
        SynsAPI.synsPlayerAttribute(player);
        return Command.SINGLE_SUCCESS;
    }

    public static int setBaojilv(Entity player, int num) {
        player.getCapability(PlayerAttributeCapabilityProvider.CAPABILITY).ifPresent(capability -> {
            capability.setBaojilv(Math.max(0, num));
        });
        SynsAPI.synsPlayerAttribute(player);
        return Command.SINGLE_SUCCESS;
    }

    public static int setWuchuan(Entity player, int num) {
        player.getCapability(PlayerAttributeCapabilityProvider.CAPABILITY).ifPresent(capability -> {
            capability.setWuchuan(Math.max(0, num));
        });
        SynsAPI.synsPlayerAttribute(player);
        return Command.SINGLE_SUCCESS;
    }

    public static int setKangbao(Entity player, int num) {
        player.getCapability(PlayerAttributeCapabilityProvider.CAPABILITY).ifPresent(capability -> {
            capability.setKangbao(Math.max(0, num));
        });
        SynsAPI.synsPlayerAttribute(player);
        return Command.SINGLE_SUCCESS;
    }

    public static int setXixue(Entity player, int num) {
        player.getCapability(PlayerAttributeCapabilityProvider.CAPABILITY).ifPresent(capability -> {
            capability.setXixue(Math.max(0, num));
        });
        SynsAPI.synsPlayerAttribute(player);
        return Command.SINGLE_SUCCESS;
    }

    public static int setShengminghuifu(Entity player, int num) {
        player.getCapability(PlayerAttributeCapabilityProvider.CAPABILITY).ifPresent(capability -> {
            capability.setShengminghuifu(Math.max(0, num));
        });
        SynsAPI.synsPlayerAttribute(player);
        return Command.SINGLE_SUCCESS;
    }

    public static int setMinghzhong(Entity player, int num) {
        player.getCapability(PlayerAttributeCapabilityProvider.CAPABILITY).ifPresent(capability -> {
            capability.setMingzhong(Math.max(0, num));
        });
        SynsAPI.synsPlayerAttribute(player);
        return Command.SINGLE_SUCCESS;
    }

    public static int setShanbi(Entity player, int num) {
        player.getCapability(PlayerAttributeCapabilityProvider.CAPABILITY).ifPresent(capability -> {
            capability.setShanbi(Math.max(0, num));
        });
        SynsAPI.synsPlayerAttribute(player);
        return Command.SINGLE_SUCCESS;
    }

    public static int setDengji(Entity player, int num) {
        player.getCapability(PlayerAttributeCapabilityProvider.CAPABILITY).ifPresent(capability -> {
            capability.setDengji(Math.max(0, num));
        });
        SynsAPI.synsPlayerAttribute(player);
        return Command.SINGLE_SUCCESS;
    }

    public static int setMaxjingyan(Entity player, int num) {
        player.getCapability(PlayerAttributeCapabilityProvider.CAPABILITY).ifPresent(capability -> {
            capability.setMaxjingyan(Math.max(0, num));
        });
        SynsAPI.synsPlayerAttribute(player);
        return Command.SINGLE_SUCCESS;
    }
}