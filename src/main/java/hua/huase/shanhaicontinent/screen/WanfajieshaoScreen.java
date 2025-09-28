package hua.huase.shanhaicontinent.screen;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import hua.huase.shanhaicontinent.SHMainBus;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.FormattedCharSequence;
import org.joml.Matrix4f;

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
    public boolean isPauseScreen() {
        return false;
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
//渲染文字
        rendertext(guiGraphics,x,y);

        int i = 51+140;
        int j = 210;

//        renderEntityInInventoryFollowsMouse(guiGraphics, x + i, y + j, 30, (float)(x + i) - this.xMouse, (float)(y + j ) - this.yMouse, this.minecraft.player);

    }



    MutableComponent translatable1 = Component.translatable(
            "基础操作：\n" +
                    "O键：打开属性界面。\n" +
                    "K键：魂环切换，开启武魂能够飞行，但会消耗精神力\n" +
                    "R键：召唤技能（需打开魂环显示）\n" +
                    "武魂觉醒：魂师等级突破1级时自动觉醒武魂或通过服用武魂果实获得（武魂果实可由转生获得）\n" +
                    "魂环吸收：击杀带有魂环的魂兽会掉落魂环\n" +
                    "魂环分解：攻击魂环可以加速魂环转化成魂核\n" +
                    "魂技：每吸收一个魂环增加一个魂技，通过R键召唤魂魂技再空手右键获得魂技，第一魂技可以通过铁贴绑定其它魂技能但第一魂技本事无主动效果\n" +
                    "升级：吸收魂核和炼制丹药");


    MutableComponent translatable2 = Component.translatable(
            "机制介绍：\n" +
                    "古风小屋：会固定刷新魂民，参考守卫者的刷新机制\n" +
                    "魂民：会掉落丹药和单方，玩家等级越高，掉落的单方等级越高\n" +
                    "魂液瓶：右键可收集魂核的能量，右键耕地可转化为魂土，魂土能够种植草药\n" +
                    "转生：精神力超过3000后再通过床睡觉完成转生，转生后等级属性回归0级，返还武魂果实\n" +
                    "魂兽分布：主世界距离越远，刷新的年限越高，\n" +
                    "造化炉：没有单方可以直接将魂草炼制成精华，放入单方按要求可炼制丹药，\n" +
                    "神界介绍:通过神视之瞳找到登神台建筑，满级后即可右键登神台进行封神");


    MutableComponent translatable3 = Component.translatable(
            "山海大陆1.20.1基础玩法介绍");


    private void rendertext(GuiGraphics guiGraphics, int x, int y) {
        PoseStack poseStack = guiGraphics.pose();
        poseStack.pushPose();
        Matrix4f matrix4f = poseStack.last().pose();
        matrix4f.translate(x+18,y+28 ,0);
        matrix4f.scale(0.7f,0.7f,1.0f);
        this.drawWordWrap(guiGraphics,this.font, Component.translatable("玩法介绍1页"), 0 , 0, 235, 0xff000000);
        poseStack.popPose();

        poseStack.pushPose();
        matrix4f = poseStack.last().pose();
        matrix4f.translate(x+202,y+28 ,0);
        matrix4f.scale(0.7f,0.7f,1.0f);
        this.drawWordWrap(guiGraphics,this.font, Component.translatable("玩法介绍2页"), 0 , 0, 235, 0xff000000);
        poseStack.popPose();

        poseStack.pushPose();
        matrix4f = poseStack.last().pose();
        matrix4f.translate(x+124,y+6 ,0);
        matrix4f.scale(1.2f,1.2f,1.0f);
        this.drawWordWrap(guiGraphics,this.font, Component.translatable("玩法介绍主题"), 0 , 0, 235, 0xff000000);
        poseStack.popPose();

    }

    private void drawWordWrap(GuiGraphics guiGraphics, Font font, MutableComponent translatable1, int i, int i1, int i2, int i3) {

        for(FormattedCharSequence formattedcharsequence : font.split(translatable1, i2)) {
            guiGraphics.drawString(font, formattedcharsequence, i, i1, i3, false);
            i1 += 14;
        }
    }


    public boolean keyPressed(int key, int i, int j) {

        return super.keyPressed(key,i,j);
    }

}
