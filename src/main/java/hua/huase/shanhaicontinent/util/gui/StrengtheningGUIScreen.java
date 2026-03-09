package hua.huase.shanhaicontinent.util.gui;

import hua.huase.shanhaicontinent.item.shenjie.severitem.StrengtheningSucceedItem;
import hua.huase.shanhaicontinent.network.NetworkHandler;
import hua.huase.shanhaicontinent.network.client.CPacketStrengthen;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.network.chat.Component;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.GuiGraphics;

import java.util.HashMap;

import com.mojang.blaze3d.systems.RenderSystem;

import static hua.huase.shanhaicontinent.register.ModItems.STRENGTHENING_SUCCEED;

public class StrengtheningGUIScreen extends AbstractContainerScreen<StrengtheningGUIMenu> {
    private final static HashMap<String, Object> guistate = StrengtheningGUIMenu.guistate;
    private final Level world;
    private final int x, y, z;
    private final Player entity;
    Button button_qiang_hua;

    public StrengtheningGUIScreen(StrengtheningGUIMenu container, Inventory inventory, Component text) {
        super(container, inventory, text);
        this.world = container.world;
        this.x = container.x;
        this.y = container.y;
        this.z = container.z;
        this.entity = container.entity;
        this.imageWidth = 176;
        this.imageHeight = 166;
    }

    private static final ResourceLocation texture = new ResourceLocation("shanhaicontinent:textures/screens/strengthening_gui.png");
    private static final ResourceLocation emptyProgressBar = new ResourceLocation("shanhaicontinent:textures/screens/jindutiao.png");
    private static final ResourceLocation fullProgressBar = new ResourceLocation("shanhaicontinent:textures/screens/maxjingdutiao.png");
    private long startTime = 0;
    private boolean isStrengthening = false;
    private static final int STRENGTHEN_DURATION = 3000;


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

        guiGraphics.blit(emptyProgressBar, this.leftPos + 29, this.topPos + 9, 0, 0, 32, 16, 32, 16);

        // 如果正在强化，绘制进度条填充部分
        if (isStrengthening) {
            long elapsed = System.currentTimeMillis() - startTime;
            float progress = Math.min(1.0f, (float)elapsed / STRENGTHEN_DURATION);
            int width = (int)(32 * progress);

            // 绘制填充部分
            guiGraphics.blit(fullProgressBar,
                    this.leftPos + 29, this.topPos + 9,
                    0, 0,
                    width, 16,
                    32, 16);
            if (progress >= 1.0f) {
                isStrengthening = false;
                NetworkHandler.sendToServer(new CPacketStrengthen());
            }
        }
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
        // 原有文本
        guiGraphics.drawString(this.font, Component.literal("强化保护卷："), 97, 12, -2816, false);
        guiGraphics.drawString(this.font, Component.literal("强化成功卷："), 97, 37, -2816, false);
        guiGraphics.drawString(this.font, Component.literal("强化双倍卷："), 97, 62, -2816, false);
        Slot inputSlot = menu.get().get(0);
        Slot succeedSlot = menu.get().get(2);
        if (inputSlot != null && inputSlot.hasItem()) {
            ItemStack stack = inputSlot.getItem();
            int level = ((StrengtheningGUIMenu) menu).getStrengthenLevel(stack);
            float successRate = ((StrengtheningGUIMenu) menu).getSuccessRate(level);
            int requiredStones = ((StrengtheningGUIMenu) menu).calculateRequiredStones(level);
            float baseRate = ((StrengtheningGUIMenu) menu).getSuccessRate(level);
            float totalRate = baseRate;

            if (succeedSlot != null && succeedSlot.hasItem() &&
                    succeedSlot.getItem().getItem() == STRENGTHENING_SUCCEED.get()) {
                int bonus = StrengtheningSucceedItem.getSuccessRate(succeedSlot.getItem());
                totalRate += bonus;
            }

            guiGraphics.drawString(this.font, Component.literal("§e当前等级: " + level), 8, 61, -1, false);
            guiGraphics.drawString(this.font, Component.literal("§e当前成功率: " + String.format("%.1f", totalRate) + "%"), 8, 71, -1, false);
            guiGraphics.drawString(this.font, Component.literal("§5需要强化石: " + requiredStones), 8, 29, -1, false);
        }

        Slot protectSlot = menu.get().get(1);
        if (protectSlot != null && protectSlot.hasItem()) {
            guiGraphics.drawString(this.font,
                    Component.literal("保护卷生效").withStyle(ChatFormatting.BLUE),
                    97, 24, -1, false);
        }

        Slot succeedSloty = menu.get().get(2);
        if (succeedSloty != null && succeedSloty.hasItem()) {
            guiGraphics.drawString(this.font,
                    Component.literal("成功率生效").withStyle(ChatFormatting.BLUE),
                    97, 49, -1, false);
        }

        Slot doubleSlot = menu.get().get(3);
        if (doubleSlot != null && doubleSlot.hasItem()) {
            guiGraphics.drawString(this.font,
                    Component.literal("双倍卷生效").withStyle(ChatFormatting.BLUE),
                    97, 74, -1, false);
        }

    }
    
    @Override
    public void init() {
        super.init();
        button_qiang_hua = Button.builder(Component.literal("强化"), e -> {
            if (!isStrengthening) {
                startStrengthening();
            }
        }).bounds(this.leftPos + 24, this.topPos + 39, 35, 20).build();
        guistate.put("button:button_qiang_hua", button_qiang_hua);
        this.addRenderableWidget(button_qiang_hua);
    }

    private void startStrengthening() {
        isStrengthening = true;
        startTime = System.currentTimeMillis();
        button_qiang_hua.active = false;
        new Thread(() -> {
            try {
                Thread.sleep(STRENGTHEN_DURATION);
                Minecraft.getInstance().execute(() -> {
                    button_qiang_hua.active = true;
                });
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
    }

}
