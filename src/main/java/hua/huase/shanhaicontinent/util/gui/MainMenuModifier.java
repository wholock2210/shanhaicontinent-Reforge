package hua.huase.shanhaicontinent.util.gui;

import hua.huase.shanhaicontinent.register.ConfigKeyValidator;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.narration.NarrationElementOutput;
import net.minecraft.client.gui.screens.TitleScreen;
import net.minecraft.network.chat.Component;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.ScreenEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.Arrays;
import java.util.List;

@Mod.EventBusSubscriber(modid = "shanhaicontinent", value = Dist.CLIENT)
public class MainMenuModifier {

    private static final List<Component> WARNING_LINES = Arrays.asList(
            Component.literal("抱歉，本mod为服务器专属所以将单人游戏进行禁止").withStyle(ChatFormatting.RED),
            Component.literal("如若想游玩单人游戏请截图你购买的网易版山海大陆（随意版本）的截图").withStyle(ChatFormatting.RED),
            Component.literal("发给山海大陆QQ频道主或QQ:3315235740").withStyle(ChatFormatting.RED),
            Component.literal("或将截图直接发送到这个邮箱3315265740@qq.com").withStyle(ChatFormatting.RED),
            Component.literal("会在1天内将密钥发放到你的邮箱").withStyle(ChatFormatting.RED),
            Component.literal("获取密钥的方式只有购买网易版山海大陆").withStyle(ChatFormatting.RED),
            Component.literal("在任何地方售卖的山海大陆3服务器版均为诈骗 被骗概不负责").withStyle(ChatFormatting.RED),
            Component.literal("密钥是免费获得的前提是购买网易版山海大陆（版本随意）").withStyle(ChatFormatting.RED),
            Component.literal("不愿购买也没问题可以游玩我们的免费服务器").withStyle(ChatFormatting.RED),
            Component.literal("因为制作mod消耗的精力与收益不成正比 不得已而为之请见谅。").withStyle(ChatFormatting.RED)
    );

    @SubscribeEvent
    public static void onGuiInit(ScreenEvent.Init.Post event) {
        if (!(event.getScreen() instanceof TitleScreen)) return;

        TitleScreen titleScreen = (TitleScreen) event.getScreen();
        boolean keyValid = ConfigKeyValidator.isKeyValid();

        // 处理单人游戏按钮
        event.getScreen().children().forEach(widget -> {
            if (widget instanceof Button button) {
                String text = button.getMessage().getString();
                if (text.equals("单人游戏") || text.equals("Singleplayer")) {
                    button.visible = keyValid;
                    button.active = keyValid;
                }
            }
        });

        // 添加左上角多行文本提示（密钥无效时显示）
        if (!keyValid) {
            event.addListener(new AbstractWidget(
                    10, 10, // 左上角坐标 (x,y)
                    300, 100, // 宽高
                    Component.empty() // 空文本，我们自己渲染
            ) {
                @Override
                public void renderWidget(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTicks) {
                    // 逐行绘制文本
                    int yOffset = this.getY();
                    for (Component line : WARNING_LINES) {
                        guiGraphics.drawString(
                                Minecraft.getInstance().font,
                                line,
                                this.getX(),
                                yOffset,
                                0xFFFFFF,
                                false
                        );
                        yOffset += 10; // 行间距
                    }
                }

                @Override
                protected void updateWidgetNarration(NarrationElementOutput narrationElementOutput) {

                }
            });
        }
    }
}