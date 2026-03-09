package hua.huase.shanhaicontinent.util.hungufenjie;


import com.mojang.blaze3d.systems.RenderSystem;
import hua.huase.shanhaicontinent.network.NetworkHandler;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;

import java.util.HashMap;

public class FenjieGUIScreen extends AbstractContainerScreen<FenjieGUIMenu> {
    private final static HashMap<String, Object> guistate = FenjieGUIMenu.guistate;
    private final Level world;
    private final int x, y, z;
    private final Player entity;
    Button button_fen_jie;
    Button button_qiang_hua_hun_gu;

    public FenjieGUIScreen(FenjieGUIMenu container, Inventory inventory, Component text) {
        super(container, inventory, text);
        this.world = container.world;
        this.x = container.x;
        this.y = container.y;
        this.z = container.z;
        this.entity = container.entity;
        this.imageWidth = 226;
        this.imageHeight = 166;
    }

    private static final ResourceLocation texture = new ResourceLocation("shanhaicontinent:textures/screens/fenjie_gui.png");

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

        guiGraphics.blit(new ResourceLocation("shanhaicontinent:textures/screens/fenjiejindutiao.png"), this.leftPos + 159, this.topPos + 17, 0, 0, 16, 16, 16, 16);

        guiGraphics.blit(new ResourceLocation("shanhaicontinent:textures/screens/fenjiejia.png"), this.leftPos + 120, this.topPos + 17, 0, 0, 16, 16, 16, 16);

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
        guiGraphics.drawString(this.font, Component.translatable("gui.shanhaicontinent.fenjie_gui.label_duoyuhungu"), 6, 5, -16711732, false);
        guiGraphics.drawString(this.font, Component.translatable("gui.shanhaicontinent.fenjie_gui.label_duoyuhungu1"), 106, 5, -205, false);
        guiGraphics.drawString(this.font, Component.translatable("gui.shanhaicontinent.fenjie_gui.label_duoyuhungu2"), 77, 39, -205, false);
        guiGraphics.drawString(this.font, Component.translatable("gui.shanhaicontinent.fenjie_gui.label_duoyuhungu3"), 77, 51, -205, false);
    }

    @Override
    public void init() {
        super.init();
        button_fen_jie = Button.builder(Component.translatable("gui.shanhaicontinent.fenjie_gui.button_fen_jie"), e -> {
            NetworkHandler.INSTANCE.sendToServer(new FenjieSoulbonePacket());
        }).bounds(this.leftPos + 35, this.topPos + 18, 35, 20).build();
        guistate.put("button:button_fen_jie", button_fen_jie);
        this.addRenderableWidget(button_fen_jie);


        //魂骨强化
        button_qiang_hua_hun_gu = Button.builder(Component.translatable("gui.shanhaicontinent.fenjie_gui.button_qiang_hua_hun_gu"), e -> {
            if (Minecraft.getInstance().level != null && !Minecraft.getInstance().level.isClientSide) return;
            int containerId = this.menu.containerId;
            int slot1 = 1;
            int slot2 = 2;
            NetworkHandler.INSTANCE.sendToServer(new EnhanceSoulBonePacket(containerId, slot1, slot2));
        }).bounds(this.leftPos + 151, this.topPos + 53, 46, 20).build();
        guistate.put("button:button_qiang_hua_hun_gu", button_qiang_hua_hun_gu);
        this.addRenderableWidget(button_qiang_hua_hun_gu);
    }
}
