package hua.huase.shanhaicontinent.util.gui;

import hua.huase.shanhaicontinent.SHMainBus;
import hua.huase.shanhaicontinent.network.MianbanButtonMessage;
import hua.huase.shanhaicontinent.world.inventory.MianbanMenu;
import net.minecraft.world.level.Level;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.network.chat.Component;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.Minecraft;

import java.util.HashMap;

import com.mojang.blaze3d.systems.RenderSystem;

public class MianbanScreen extends AbstractContainerScreen<MianbanMenu> {
    protected final static HashMap<String, Object> guistate = MianbanMenu.guistate;
    private final Level world;
    private final int x, y, z;
    private final Player entity;
    EditBox shengming;
    EditBox wugong;
    EditBox wufang;
    EditBox baoshang;
    EditBox zhengshang;
    EditBox wuchuan;
    EditBox baolu;
    EditBox kangbao;
    EditBox xixue;
    EditBox huifu;
    EditBox mingzhong;
    EditBox shanbi;
    EditBox shenwuID;
    EditBox nianxian;
    Button button_sheng_cheng_sheng_wu;

    public MianbanScreen(MianbanMenu container, Inventory inventory, Component text) {
        super(container, inventory, text);
        this.world = container.world;
        this.x = container.x;
        this.y = container.y;
        this.z = container.z;
        this.entity = container.entity;
        this.imageWidth = 283;
        this.imageHeight = 179;
    }

    private static final ResourceLocation texture = new ResourceLocation("shanhaicontinent:textures/screens/mianban.png");

    @Override
    public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTicks) {
        this.renderBackground(guiGraphics);
        super.render(guiGraphics, mouseX, mouseY, partialTicks);
        shengming.render(guiGraphics, mouseX, mouseY, partialTicks);
        wugong.render(guiGraphics, mouseX, mouseY, partialTicks);
        wufang.render(guiGraphics, mouseX, mouseY, partialTicks);
        baoshang.render(guiGraphics, mouseX, mouseY, partialTicks);
        zhengshang.render(guiGraphics, mouseX, mouseY, partialTicks);
        wuchuan.render(guiGraphics, mouseX, mouseY, partialTicks);
        baolu.render(guiGraphics, mouseX, mouseY, partialTicks);
        kangbao.render(guiGraphics, mouseX, mouseY, partialTicks);
        xixue.render(guiGraphics, mouseX, mouseY, partialTicks);
        huifu.render(guiGraphics, mouseX, mouseY, partialTicks);
        mingzhong.render(guiGraphics, mouseX, mouseY, partialTicks);
        shanbi.render(guiGraphics, mouseX, mouseY, partialTicks);
        shenwuID.render(guiGraphics, mouseX, mouseY, partialTicks);
        nianxian.render(guiGraphics, mouseX, mouseY, partialTicks);
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
        if (shengming.isFocused())
            return shengming.keyPressed(key, b, c);
        if (wugong.isFocused())
            return wugong.keyPressed(key, b, c);
        if (wufang.isFocused())
            return wufang.keyPressed(key, b, c);
        if (baoshang.isFocused())
            return baoshang.keyPressed(key, b, c);
        if (zhengshang.isFocused())
            return zhengshang.keyPressed(key, b, c);
        if (wuchuan.isFocused())
            return wuchuan.keyPressed(key, b, c);
        if (baolu.isFocused())
            return baolu.keyPressed(key, b, c);
        if (kangbao.isFocused())
            return kangbao.keyPressed(key, b, c);
        if (xixue.isFocused())
            return xixue.keyPressed(key, b, c);
        if (huifu.isFocused())
            return huifu.keyPressed(key, b, c);
        if (mingzhong.isFocused())
            return mingzhong.keyPressed(key, b, c);
        if (shanbi.isFocused())
            return shanbi.keyPressed(key, b, c);
        if (shenwuID.isFocused())
            return shenwuID.keyPressed(key, b, c);
        if (nianxian.isFocused())
            return nianxian.keyPressed(key, b, c);
        return super.keyPressed(key, b, c);
    }

    @Override
    public void containerTick() {
        super.containerTick();
        shengming.tick();
        wugong.tick();
        wufang.tick();
        baoshang.tick();
        zhengshang.tick();
        wuchuan.tick();
        baolu.tick();
        kangbao.tick();
        xixue.tick();
        huifu.tick();
        mingzhong.tick();
        shanbi.tick();
        shenwuID.tick();
        nianxian.tick();
    }

    @Override
    public void resize(Minecraft minecraft, int width, int height) {
        String shengmingValue = shengming.getValue();
        String wugongValue = wugong.getValue();
        String wufangValue = wufang.getValue();
        String baoshangValue = baoshang.getValue();
        String zhengshangValue = zhengshang.getValue();
        String wuchuanValue = wuchuan.getValue();
        String baoluValue = baolu.getValue();
        String kangbaoValue = kangbao.getValue();
        String xixueValue = xixue.getValue();
        String huifuValue = huifu.getValue();
        String mingzhongValue = mingzhong.getValue();
        String shanbiValue = shanbi.getValue();
        String shenwuIDValue = shenwuID.getValue();
        String nianxianValue = nianxian.getValue();
        super.resize(minecraft, width, height);
        shengming.setValue(shengmingValue);
        wugong.setValue(wugongValue);
        wufang.setValue(wufangValue);
        baoshang.setValue(baoshangValue);
        zhengshang.setValue(zhengshangValue);
        wuchuan.setValue(wuchuanValue);
        baolu.setValue(baoluValue);
        kangbao.setValue(kangbaoValue);
        xixue.setValue(xixueValue);
        huifu.setValue(huifuValue);
        mingzhong.setValue(mingzhongValue);
        shanbi.setValue(shanbiValue);
        shenwuID.setValue(shenwuIDValue);
        nianxian.setValue(nianxianValue);
    }

    @Override
    protected void renderLabels(GuiGraphics guiGraphics, int mouseX, int mouseY) {
        guiGraphics.drawString(this.font, Component.translatable("gui.shanhaicontinent.mianban.label_sheng_ming"), 8, 9, -12829636, false);
        guiGraphics.drawString(this.font, Component.translatable("gui.shanhaicontinent.mianban.label_wu_gong"), 8, 32, -12829636, false);
        guiGraphics.drawString(this.font, Component.translatable("gui.shanhaicontinent.mianban.label_empty"), 8, 59, -12829636, false);
        guiGraphics.drawString(this.font, Component.translatable("gui.shanhaicontinent.mianban.label_bao_shang"), 108, 9, -12829636, false);
        guiGraphics.drawString(this.font, Component.translatable("gui.shanhaicontinent.mianban.label_bao_lu"), 107, 33, -12829636, false);
        guiGraphics.drawString(this.font, Component.translatable("gui.shanhaicontinent.mianban.label_zhen_shang"), 107, 57, -12829636, false);
        guiGraphics.drawString(this.font, Component.translatable("gui.shanhaicontinent.mianban.label_wu_chuan"), 198, 8, -12829636, false);
        guiGraphics.drawString(this.font, Component.translatable("gui.shanhaicontinent.mianban.label_kang_bao"), 198, 34, -12829636, false);
        guiGraphics.drawString(this.font, Component.translatable("gui.shanhaicontinent.mianban.label_xi_xie"), 199, 57, -12829636, false);
        guiGraphics.drawString(this.font, Component.translatable("gui.shanhaicontinent.mianban.label_empty1"), 7, 83, -12829636, false);
        guiGraphics.drawString(this.font, Component.translatable("gui.shanhaicontinent.mianban.label_ming_zhong"), 107, 82, -12829636, false);
        guiGraphics.drawString(this.font, Component.translatable("gui.shanhaicontinent.mianban.label_empty2"), 199, 81, -12829636, false);
        guiGraphics.drawString(this.font, Component.translatable("gui.shanhaicontinent.mianban.label_shu_ru_sheng_wu_de_idming"), 27, 102, -12829636, false);
        guiGraphics.drawString(this.font, Component.translatable("gui.shanhaicontinent.mianban.label_mu_qian_zhi_zhi_chi_yuan_ban_sheng_wu"), 8, 134, -12829636, false);
        guiGraphics.drawString(this.font, Component.translatable("gui.shanhaicontinent.mianban.label_li_zi_pig_zhu"), 9, 148, -12829636, false);
        guiGraphics.drawString(this.font, Component.translatable("gui.shanhaicontinent.mianban.label_zhu_yi"), 102, 134, -65536, false);
        guiGraphics.drawString(this.font, Component.translatable("gui.shanhaicontinent.mianban.label_ben_gong_ju_wei_zuo_bi_gong_ju"), 90, 148, -65536, false);
        guiGraphics.drawString(this.font, Component.translatable("gui.shanhaicontinent.mianban.label_shi_yong_shi_sheng_wu_de_shu_zhi_bu_yi_guo_da"), 11, 163, -65536, false);
        guiGraphics.drawString(this.font, Component.translatable("gui.shanhaicontinent.mianban.label_fou_ze_dao_zhi_cun_dang_sun_pi_hou_guo_zi_fu"), 128, 163, -65536, false);
        guiGraphics.drawString(this.font, Component.translatable("gui.shanhaicontinent.mianban.label_nian_xian"), 185, 115, -12829636, false);
        guiGraphics.drawString(this.font, Component.translatable("gui.shanhaicontinent.mianban.label_ruo_shu_zhi_wei_kong_sheng_cheng_de_sheng_wu_mo_ren_shu_xing_wei_1"), 129, 134, -205, false);
    }

    @Override
    public void init() {
        super.init();
        shengming = new EditBox(this.font, this.leftPos + 29, this.topPos + 6, 70, 18, Component.translatable("gui.shanhaicontinent.mianban.shengming"));
        shengming.setMaxLength(12);
        guistate.put("text:shengming", shengming);
        this.addWidget(this.shengming);

        wugong = new EditBox(this.font, this.leftPos + 29, this.topPos + 30, 70, 18, Component.translatable("gui.shanhaicontinent.mianban.wugong"));
        wugong.setMaxLength(12);
        guistate.put("text:wugong", wugong);
        this.addWidget(this.wugong);

        wufang = new EditBox(this.font, this.leftPos + 28, this.topPos + 56, 70, 18, Component.translatable("gui.shanhaicontinent.mianban.wufang"));
        wufang.setMaxLength(12);
        guistate.put("text:wufang", wufang);
        this.addWidget(this.wufang);

        baoshang = new EditBox(this.font, this.leftPos + 127, this.topPos + 6, 61, 18, Component.translatable("gui.shanhaicontinent.mianban.baoshang"));
        baoshang.setMaxLength(12);
        guistate.put("text:baoshang", baoshang);
        this.addWidget(this.baoshang);

        zhengshang = new EditBox(this.font, this.leftPos + 126, this.topPos + 55, 62, 18, Component.translatable("gui.shanhaicontinent.mianban.zhengshang"));
        zhengshang.setMaxLength(12);
        guistate.put("text:zhengshang", zhengshang);
        this.addWidget(this.zhengshang);

        wuchuan = new EditBox(this.font, this.leftPos + 218, this.topPos + 6, 52, 18, Component.translatable("gui.shanhaicontinent.mianban.wuchuan"));
        wuchuan.setMaxLength(12);
        guistate.put("text:wuchuan", wuchuan);
        this.addWidget(this.wuchuan);

        baolu = new EditBox(this.font, this.leftPos + 127, this.topPos + 30, 60, 18, Component.translatable("gui.shanhaicontinent.mianban.baolu"));
        baolu.setMaxLength(12);
        guistate.put("text:baolu", baolu);
        this.addWidget(this.baolu);

        kangbao = new EditBox(this.font, this.leftPos + 217, this.topPos + 30, 53, 18, Component.translatable("gui.shanhaicontinent.mianban.kangbao"));
        kangbao.setMaxLength(8);
        guistate.put("text:kangbao", kangbao);
        this.addWidget(this.kangbao);

        xixue = new EditBox(this.font, this.leftPos + 217, this.topPos + 54, 52, 18, Component.translatable("gui.shanhaicontinent.mianban.xixue"));
        xixue.setMaxLength(8);
        guistate.put("text:xixue", xixue);
        this.addWidget(this.xixue);

        huifu = new EditBox(this.font, this.leftPos + 28, this.topPos + 80, 70, 18, Component.translatable("gui.shanhaicontinent.mianban.huifu"));
        huifu.setMaxLength(8);
        guistate.put("text:huifu", huifu);
        this.addWidget(this.huifu);

        mingzhong = new EditBox(this.font, this.leftPos + 127, this.topPos + 79, 60, 18, Component.translatable("gui.shanhaicontinent.mianban.mingzhong"));
        mingzhong.setMaxLength(8);
        guistate.put("text:mingzhong", mingzhong);
        this.addWidget(this.mingzhong);

        shanbi = new EditBox(this.font, this.leftPos + 217, this.topPos + 79, 53, 18, Component.translatable("gui.shanhaicontinent.mianban.shanbi"));
        shanbi.setMaxLength(8);
        guistate.put("text:shanbi", shanbi);
        this.addWidget(this.shanbi);

        shenwuID = new EditBox(this.font, this.leftPos + 7, this.topPos + 113, 118, 18, Component.translatable("gui.shanhaicontinent.mianban.shenwuID"));
        shenwuID.setMaxLength(100);
        guistate.put("text:shenwuID", shenwuID);
        this.addWidget(this.shenwuID);

        nianxian = new EditBox(this.font, this.leftPos + 204, this.topPos + 110, 71, 18, Component.translatable("gui.shanhaicontinent.mianban.nianxian"));
        nianxian.setMaxLength(12);
        guistate.put("text:nianxian", nianxian);
        this.addWidget(this.nianxian);

        button_sheng_cheng_sheng_wu = Button.builder(Component.translatable("gui.shanhaicontinent.mianban.button_sheng_cheng_sheng_wu"), e -> {
            if (true) {
                SHMainBus.PACKET_HANDLER.sendToServer(new MianbanButtonMessage(0, x, y, z));
                MianbanButtonMessage.handleButtonAction(entity, 0, x, y, z);
            }
        }).bounds(this.leftPos + 131, this.topPos + 110, 46, 20).build();
        guistate.put("button:button_sheng_cheng_sheng_wu", button_sheng_cheng_sheng_wu);
        this.addRenderableWidget(button_sheng_cheng_sheng_wu);

        shengming.setFilter(input -> input.matches("\\d*"));
        wugong.setFilter(input -> input.matches("\\d*"));
        wufang.setFilter(input -> input.matches("\\d*"));
        baoshang.setFilter(input -> input.matches("\\d*"));
        baolu.setFilter(input -> input.matches("\\d*"));
        zhengshang.setFilter(input -> input.matches("\\d*"));
        wuchuan.setFilter(input -> input.matches("\\d*"));
        kangbao.setFilter(input -> input.matches("\\d*"));
        xixue.setFilter(input -> input.matches("\\d*"));
        huifu.setFilter(input -> input.matches("\\d*"));
        mingzhong.setFilter(input -> input.matches("\\d*"));
        shanbi.setFilter(input -> input.matches("\\d*"));
        nianxian.setFilter(input -> input.matches("\\d*"));
        shenwuID.setFilter(input -> input.matches("[a-zA-Z]*"));

       this.addWidget(shengming);
        this.addWidget(wugong);
        this.addWidget(wufang);
        this.addWidget(baoshang);
        this.addWidget(zhengshang);
        this.addWidget(wuchuan);
        this.addWidget(baolu);
        this.addWidget(kangbao);
        this.addWidget(xixue);
        this.addWidget(huifu);
        this.addWidget(mingzhong);
        this.addWidget(shanbi);
        this.addWidget(shenwuID);
        this.addWidget(nianxian);
    }
}