package hua.huase.shanhaicontinent.screen;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import hua.huase.shanhaicontinent.SHMainBus;
import hua.huase.shanhaicontinent.capability.AttrubuteAPI;
import hua.huase.shanhaicontinent.capability.monsterattribute.MonsterAttributeCapability;
import hua.huase.shanhaicontinent.capability.playerattribute.PlayerAttributeCapabilityProvider;
import hua.huase.shanhaicontinent.capability.playerattribute.PlayerAttrubuteAPI;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import org.joml.Matrix4f;

import java.util.LinkedList;
import java.util.List;

import static net.minecraft.client.gui.screens.inventory.InventoryScreen.renderEntityInInventoryFollowsMouse;

public class WanfajieshaoScreen extends Screen {
    private static final ResourceLocation TEXTURE =
            new ResourceLocation(SHMainBus.MOD_ID, "textures/gui/wanfajieshao.png");

    protected int imageWidth = 384;
    protected int imageHeight = 216;
    private float xMouse;
    private float yMouse;

    public WanfajieshaoScreen(Component p_96550_) {
        super(p_96550_);
    }

    @Override
    protected void init() {
        super.init();

        this.imageWidth = 384;
        this.imageHeight = 216;
    }


    @Override
    public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float delta) {
//        renderBackground(guiGraphics);
        super.render(guiGraphics, mouseX, mouseY, delta);
        this.xMouse = (float)mouseX;
        this.yMouse = (float)mouseY;
        this.renderBg(guiGraphics, mouseX, mouseY, delta);
    }

    protected void renderBg(GuiGraphics guiGraphics, int pMouseX, int pMouseY, float pPartialTick) {
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.setShaderTexture(0, TEXTURE);
        int x = (width - imageWidth) / 2;
        int y = (height - imageHeight) / 2;
//渲染背景
        guiGraphics.blit(TEXTURE, x, y,  0, 0, this.imageWidth, this.imageHeight, this.imageWidth, this.imageHeight);


        int i = 51+140;
        int j = 210;

//        renderEntityInInventoryFollowsMouse(guiGraphics, x + i, y + j, 30, (float)(x + i) - this.xMouse, (float)(y + j ) - this.yMouse, this.minecraft.player);

    }


    public boolean keyPressed(int key, int i, int j) {

        return super.keyPressed(key,i,j);
    }

}
