package hua.huase.shanhaicontinent.util.artifact;

import hua.huase.shanhaicontinent.network.NetworkHandler;
import net.minecraft.ChatFormatting;
import net.minecraft.world.level.Level;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.network.chat.Component;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.GuiGraphics;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;

public class ArtifactScreen extends AbstractContainerScreen<ArtifactMenu> {
    private final static HashMap<String, Object> guistate = ArtifactMenu.guistate;
    private final Level world;
    private final Player entity;
    private Button button_kai_shi_duan_zao;

    private static final int TOTAL_CRAFTING_TICKS = 100;
    private final ResourceLocation TEXTURE = new ResourceLocation("shanhaicontinent:textures/screens/artifact.png");
    private final ResourceLocation PROGRESS_EMPTY = new ResourceLocation("shanhaicontinent:textures/screens/jindutiao.png");
    private final ResourceLocation PROGRESS_FULL = new ResourceLocation("shanhaicontinent:textures/screens/maxjingdutiao.png");

    public ArtifactScreen(ArtifactMenu container, Inventory inventory, Component text) {
        super(container, inventory, text);
        this.world = container.world;
        this.entity = container.entity;
        this.imageWidth = 176;
        this.imageHeight = 166;
    }

    @Override
    public void render(@NotNull GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTicks) {
        this.renderBackground(guiGraphics);
        try {
            super.render(guiGraphics, mouseX, mouseY, partialTicks);
        } catch (NullPointerException e) {
            guiGraphics.drawString(font, "界面初始化异常，请联系服主", leftPos + 10, topPos + 10, 0xFF0000, false);
            return;
        }
        this.renderTooltip(guiGraphics, mouseX, mouseY);
    }

    @Override
    protected void renderBg(GuiGraphics guiGraphics, float partialTicks, int mouseX, int mouseY) {
        guiGraphics.blit(TEXTURE, this.leftPos, this.topPos, 0, 0, this.imageWidth, this.imageHeight, this.imageWidth, this.imageHeight);
        float progressPercent = menu.getProgress() / (float)TOTAL_CRAFTING_TICKS;
        int progressWidth = (int)(32 * progressPercent);
        guiGraphics.blit(PROGRESS_EMPTY, leftPos + 71, topPos + 9, 0, 0, 32, 16, 32, 16);
        guiGraphics.blit(PROGRESS_FULL, leftPos + 71, topPos + 9, 0, 0, progressWidth, 16, 32, 16);
    }

    @Override
    protected void renderLabels(GuiGraphics guiGraphics, int mouseX, int mouseY) {
        int remainingTicks = TOTAL_CRAFTING_TICKS - menu.getProgress();
        int seconds = remainingTicks / 20;
        int minutes = seconds / 60;
        seconds = seconds % 60;

        guiGraphics.drawString(font,
                String.format("剩余时间: %02d:%02d", minutes, seconds),
                50, 56, 0xFFAA00, false);
        guiGraphics.drawString(this.font, Component.literal("开始锻造时令牌不可取出").withStyle(ChatFormatting.RED), 7, 69, -12829636, false);
    }

    @Override
    public void init() {
        super.init();

        button_kai_shi_duan_zao = Button.builder(Component.literal("开始锻造"), e -> {
            if (!menu.isCrafting()) {
                NetworkHandler.INSTANCE.sendToServer(new StartCraftingPacket(menu.pos, minecraft.player.getUUID()));
            }
        }).bounds(this.leftPos + 56, this.topPos + 34, 72, 20).build();

        guistate.put("button:button_kai_shi_duan_zao", button_kai_shi_duan_zao);
        this.addRenderableWidget(button_kai_shi_duan_zao);
        updateButtonState();
    }

    private void updateButtonState() {
        if (button_kai_shi_duan_zao != null) {
            button_kai_shi_duan_zao.active = !menu.isCrafting();
            button_kai_shi_duan_zao.setMessage(menu.isCrafting() ?
                    Component.literal("锻造中...") :
                    Component.literal("开始锻造"));
        }
    }

}