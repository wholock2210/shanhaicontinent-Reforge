package hua.huase.shanhaicontinent.util.jinengmianban;


import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;

import java.util.HashMap;

public class JinengmianbanScreen extends AbstractContainerScreen<JinengmianbanMenu> {
    private final static HashMap<String, Object> guistate = JinengmianbanMenu.guistate;
    private final Level world;
    private final int x, y, z;
    private final Player entity;

    public JinengmianbanScreen(JinengmianbanMenu container, Inventory inventory, Component text) {
        super(container, inventory, text);
        this.world = container.world;
        this.x = container.x;
        this.y = container.y;
        this.z = container.z;
        this.entity = container.entity;
        this.imageWidth = 177;
        this.imageHeight = 166;
    }

    private static final ResourceLocation texture = new ResourceLocation("shanhaicontinent:textures/screens/jinengmianban.png");

    @Override
    public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTicks) {
        this.renderBackground(guiGraphics);
        super.render(guiGraphics, mouseX, mouseY, partialTicks);
        this.renderTooltip(guiGraphics, mouseX, mouseY);
    }

    @Override
    protected void renderBg(GuiGraphics guiGraphics, float partialTicks, int gx, int gy) {
        RenderSystem.setShaderColor(1, 1, 1, 1);
        RenderSystem.enableBlend();
        RenderSystem.defaultBlendFunc();
        guiGraphics.blit(texture, this.leftPos, this.topPos, 0, 0, this.imageWidth, this.imageHeight, this.imageWidth, this.imageHeight);
        RenderSystem.disableBlend();
    }

    @Override
    public boolean keyPressed(int key, int b, int c) {
        if (key == 256) {
            this.minecraft.player.closeContainer();
            return true;
        }
        return super.keyPressed(key, b, c);
    }

    @Override
    protected void renderLabels(GuiGraphics guiGraphics, int mouseX, int mouseY) {
        guiGraphics.drawString(this.font, Component.translatable("gui.shanhaicontinent.jinengmianban.label_ji_neng_1"), 7, 6, -12829636, false);
        guiGraphics.drawString(this.font, Component.translatable("gui.shanhaicontinent.jinengmianban.label_ji_neng_2"), 66, 6, -12829636, false);
        guiGraphics.drawString(this.font, Component.translatable("gui.shanhaicontinent.jinengmianban.label_ji_neng_3"), 129, 6, -12829636, false);
        guiGraphics.drawString(this.font, Component.translatable("gui.shanhaicontinent.jinengmianban.label_ji_neng"), 10, 42, -3590859, false);
        guiGraphics.drawString(this.font, Component.translatable("gui.shanhaicontinent.jinengmianban.label_jiang_ji_jieshao"), 9, 55, -39322, false);
        guiGraphics.drawString(this.font, Component.translatable("gui.shanhaicontinent.jinengmianban.label_jinengjieshao"), 9, 68, -39322, false);
        guiGraphics.drawString(this.font, Component.translatable("gui.shanhaicontinent.jinengmianban.label_z"), 31, 25, -10027060, false);
        guiGraphics.drawString(this.font, Component.translatable("gui.shanhaicontinent.jinengmianban.label_x"), 91, 25, -10027060, false);
        guiGraphics.drawString(this.font, Component.translatable("gui.shanhaicontinent.jinengmianban.label_c"), 152, 25, -10027060, false);


    }

    @Override
    public void init() {
        super.init();
    }
}
