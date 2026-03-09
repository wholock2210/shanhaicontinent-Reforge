package hua.huase.shanhaicontinent.screen;

import com.mojang.blaze3d.systems.RenderSystem;
import hua.huase.shanhaicontinent.SHMainBus;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;

public class PotScreen extends AbstractContainerScreen<PotMenu> {
    private static final ResourceLocation TEXTURE =
            new ResourceLocation(SHMainBus.MOD_ID, "textures/gui/pot/potdisplayer.png");
    private static final ResourceLocation FIRE =
            new ResourceLocation(SHMainBus.MOD_ID, "textures/gui/pot/fire.png");

    public PotScreen(PotMenu pMenu, Inventory pPlayerInventory, Component pTitle) {
        super(pMenu, pPlayerInventory, pTitle);
    }

    @Override
    protected void init() {
        this.imageWidth = 384;
        this.imageHeight = 216;
        super.init();
        this.inventoryLabelY = 10000;
        this.titleLabelY = 10000;
    }

    @Override
    protected void renderBg(GuiGraphics guiGraphics, float pPartialTick, int pMouseX, int pMouseY) {
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.setShaderTexture(0, TEXTURE);
        int x = (width - imageWidth) / 2;
        int y = (height - imageHeight) / 2;
//渲染背景
        guiGraphics.blit(TEXTURE, x, y,  0, 0, this.imageWidth, this.imageHeight, this.imageWidth, this.imageHeight);

        renderProgressArrow(guiGraphics, x, y);


//
        if( this.menu.getNengliang()>0){

            guiGraphics.drawString(this.font,Component.translatable("所需能量",this.menu.getNengliang())
            ,x+24,y+78,0xFF00ff00
            );
        }

    }




//menu.getScaledProgress()
    private void renderProgressArrow(GuiGraphics guiGraphics, int x, int y) {
        if(menu.isCrafting()) {
            guiGraphics.blit(FIRE, x + 211, y + 15+menu.getScaledProgress(), 0, +menu.getScaledProgress(),93,102-+menu.getScaledProgress(),93,102);
        }
    }

    @Override
    public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float delta) {
        renderBackground(guiGraphics);
        super.render(guiGraphics, mouseX, mouseY, delta);
        renderTooltip(guiGraphics, mouseX, mouseY);
    }
}
