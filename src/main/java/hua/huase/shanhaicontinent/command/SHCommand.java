package hua.huase.shanhaicontinent.command;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;
import com.mojang.brigadier.tree.LiteralCommandNode;
import net.minecraft.ChatFormatting;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.Component;

public class SHCommand {
    public static final SimpleCommandExceptionType NOT_IN_TF = new SimpleCommandExceptionType(Component.translatable("commands.shanhaicontinent.not_in_twilight_forest").withStyle(ChatFormatting.RED));

    public static void register(CommandDispatcher<CommandSourceStack> dispatcher) {
        LiteralArgumentBuilder<CommandSourceStack> builder = Commands.literal("shanhaicontinent")
                .executes(SHCommand::run)
                .then(ChangeAttributeCommand.register())
                ;
        LiteralCommandNode<CommandSourceStack> node = dispatcher.register(builder);
        dispatcher.register(Commands.literal("sh").executes(SHCommand::run).redirect(node));
        dispatcher.register(Commands.literal("shanhaicontinent").executes(SHCommand::run).redirect(node));
    }

    private static int run(CommandContext<CommandSourceStack> ctx) throws CommandSyntaxException {
        throw new SimpleCommandExceptionType(Component.translatable("commands.shanhaicontinent.usage", ctx.getInput())).create();
    }
}
