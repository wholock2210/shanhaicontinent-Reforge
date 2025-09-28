package hua.huase.shanhaicontinent.screen;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.*;
import hua.huase.shanhaicontinent.SHMainBus;
import hua.huase.shanhaicontinent.capability.AttrubuteAPI;
import hua.huase.shanhaicontinent.capability.monsterattribute.MonsterAttributeCapability;
import hua.huase.shanhaicontinent.capability.playerattribute.PlayerAttributeCapability;
import hua.huase.shanhaicontinent.capability.playerattribute.PlayerAttributeCapabilityProvider;
import hua.huase.shanhaicontinent.capability.playerattribute.PlayerAttrubuteAPI;
import hua.huase.shanhaicontinent.network.NetworkHandler;
import hua.huase.shanhaicontinent.network.client.CPacketOpenAttrGUI;
import hua.huase.shanhaicontinent.network.client.CPacketOpenSkillGUI;
import hua.huase.shanhaicontinent.util.jinengmianban.SadhaYouJiKongQiShiShiTiDeWeiZhiProcedure;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.Tooltip;
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

public class PlayerAttrubuteContiainerScreen extends AbstractContainerScreen<PlayerAttrubuteContainerMenu> {
    private static final ResourceLocation TEXTURE =
            new ResourceLocation(SHMainBus.MOD_ID, "textures/gui/playercapabilityx.png");

    private float xMouse;
    private float yMouse;


    List<MutableComponent> mutableComponents = new LinkedList<>();
    public PlayerAttrubuteContiainerScreen(PlayerAttrubuteContainerMenu pMenu, Inventory pPlayerInventory, Component pTitle) {
        super(pMenu, pPlayerInventory, pTitle);
    }

    private int currentPage = 0;
    private Button prevPageButton;
    private Button nextPageButton;

    @Override
    protected void init() {
        this.imageWidth = 384;
        this.imageHeight = 216;
        super.init();
        this.inventoryLabelY = 10000;
        this.titleLabelY = 10000;
        int buttonY = this.topPos + 8;

        int buttonWidth = 12;
        int buttonHeight = 12;
        int gap = 5;
        int prevButtonX = (this.width - buttonWidth * 2 - gap) / 2;

        this.addRenderableWidget(Button.builder(
                        Component.translatable("skill.button"),
                        button -> {
                            NetworkHandler.sendToServer(new CPacketOpenSkillGUI());
                        }
                )
                .bounds(
                        this.leftPos + this.imageWidth - 170,
                        this.topPos + 7,
                        40,
                        20
                )
                .tooltip(Tooltip.create(Component.translatable("tooltip.skill.open")))
                .build());

        int nextButtonX = prevButtonX + buttonWidth + gap;
        prevPageButton = this.addRenderableWidget(Button.builder(
                Component.literal("<"),
                button -> {
                    if (currentPage > 0) currentPage--;
                }
        ).bounds(
                prevButtonX, buttonY, buttonWidth, buttonHeight
        ).build());
        nextPageButton = this.addRenderableWidget(Button.builder(
                Component.literal(">"),
                button -> {
                    int maxPage = (int) Math.ceil((double) totalHunhuanCount / HUNHUAN_PER_PAGE) - 1;
                    if (currentPage < maxPage) currentPage++;
                }
        ).bounds(
                nextButtonX, buttonY, buttonWidth, buttonHeight
        ).build());
    }

    @Override
    public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float delta) {
        renderBackground(guiGraphics);
        super.render(guiGraphics, mouseX, mouseY, delta);
        renderTooltip(guiGraphics, mouseX, mouseY);
        this.xMouse = (float) mouseX;
        this.yMouse = (float) mouseY;
        int maxPage = (int) Math.ceil((double) totalHunhuanCount / HUNHUAN_PER_PAGE) - 1;
        prevPageButton.active = currentPage > 0;
        nextPageButton.active = currentPage < maxPage;
    }

    @Override
    protected void renderBg(GuiGraphics guiGraphics, float pPartialTick, int pMouseX, int pMouseY) {
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.setShaderTexture(0, TEXTURE);
        int x = (width - imageWidth) / 2;
        int y = (height - imageHeight) / 2;
        guiGraphics.blit(TEXTURE, x, y, 0, 0, this.imageWidth, this.imageHeight, this.imageWidth, this.imageHeight);
        Player player = this.menu.player;

        renderShenwei(guiGraphics, player, x, y);

        renderPlayerAttibute(guiGraphics, player, x, y);
        // 渲染魂环
        renderHunhuan(guiGraphics, player, x, y, pPartialTick);

        int i = 51 + 140;
        int j = 110;
        renderEntityInInventoryFollowsMouse(guiGraphics, x + i, y + j, 30, (float) (x + i) - this.xMouse, (float) (y + j) - this.yMouse, this.minecraft.player);
    }

    private void renderShenwei(GuiGraphics guiGraphics, Player player, int x, int y) {
        String shenwei = PlayerAttributeCapability.getShenwei(player);
        MutableComponent shenweiText = switch (shenwei) {
            case "海神" -> Component.literal("神位：海神").withStyle(ChatFormatting.BLUE);
            case "天使神" -> Component.literal("神位：天使神").withStyle(ChatFormatting.YELLOW);
            case "修罗神" -> Component.literal("神位：修罗神").withStyle(ChatFormatting.RED);
            case "罗刹神" -> Component.literal("神位：罗刹神").withStyle(ChatFormatting.DARK_PURPLE);
            default -> Component.literal("神位：无神位").withStyle(ChatFormatting.GRAY);
        };

        int textWidth = font.width(shenweiText);
        int textX = x + this.imageWidth - textWidth - 120; // 调整 X 坐标
        int textY = y + 30; // 调整 Y 坐标

        guiGraphics.drawString(font, shenweiText, textX, textY, 0xFFFFFF, false);
    }

    private static final ResourceLocation HUNHUAN = new ResourceLocation(SHMainBus.MOD_ID, "textures/gui/hunhuan.png");

    private static final int HUNHUAN_PER_PAGE = 10;

    private int totalHunhuanCount = 0;

    private void renderHunhuan(GuiGraphics guiGraphics, Player player, int x, int y, float pPartialTick) {
        player.getCapability(PlayerAttributeCapabilityProvider.CAPABILITY).ifPresent(capability -> {
            if (capability.getWuhunListsname().size() - 1 < capability.getHunhuankuaiguan() || capability.getHunhuankuaiguan() < 0) return;
            String s = capability.getWuhunListsname().get(capability.getHunhuankuaiguan());
            totalHunhuanCount = capability.getMonsterCapabilityLists().get(s).size();
            int startIndex = currentPage * HUNHUAN_PER_PAGE;
            int endIndex = Math.min(startIndex + HUNHUAN_PER_PAGE, capability.getMonsterCapabilityLists().get(s).size());
            for (int i = startIndex; i < endIndex; i++) {
                MonsterAttributeCapability monsterAttributeCapability = capability.getMonsterCapabilityLists().get(s).get(i);
                PoseStack pose = guiGraphics.pose();
                pose.pushPose();
                Matrix4f matrix4f = pose.last().pose();
                int row = (i - startIndex) / 2;
                int col = (i - startIndex) % 2;
                matrix4f.translate(300 + 50 * col + x, 30 + row * 40 + y, 0);
                matrix4f.scale(1.0f, 0.7f, 1.0f);
                matrix4f.rotate((float) (0.02 * (player.level().getGameTime() + pPartialTick)), 0f, 0f, 1f);
                renderGUIHunhuanAttribute(monsterAttributeCapability.getNianxian(), player.level().getGameTime() + pPartialTick);
                guiGraphics.blit(HUNHUAN, -30, -30, 0, 0, 60, 60, 60, 60);
                pose.popPose();
            }
            for (int i = startIndex; i < endIndex; i++) {
                MonsterAttributeCapability monsterAttributeCapability = capability.getMonsterCapabilityLists().get(s).get(i);
                PoseStack pose = guiGraphics.pose();
                pose.pushPose();
                Matrix4f matrix4f = pose.last().pose();

                int row = (i - startIndex) / 2;
                int col = (i - startIndex) % 2;
                matrix4f.translate(300 + 50 * col + x, 28 + row * 40 + y, 0);
                matrix4f.scale(0.8f, 0.8f, 1.0f);

                MutableComponent c = Component.translatable(monsterAttributeCapability.getNianxian() + "年");
                int width1 = font.width(c);
                font.drawInBatch(c, 0 - width1 / 2, 0 + 0f, 0xffff00, false, matrix4f, guiGraphics.bufferSource(), Font.DisplayMode.SEE_THROUGH, 0, 999999);

                pose.popPose();
            }

            RenderSystem.setShaderColor(1, 1f, 1f, 1f);
        });
    }

    private void renderGUIHunhuanAttribute(int nianxian, float partialTick) {

        if (nianxian>=10000000) {
            RenderSystem.setShaderColor(0, 0, (float) Math.sin(partialTick * 0.1f) * 0.25f + 0.75f, 0.8f);

        }else if(nianxian>=1000000){
            RenderSystem.setShaderColor((float) Math.sin(partialTick*0.1f)*0.25f+0.75f,0,0,0.8f);

        }else if(nianxian>=100000){
            RenderSystem.setShaderColor(1.0f, 0, 0,0.8f);

        }else if(nianxian>=10000){
            RenderSystem.setShaderColor(0, 0f, 0,0.8f);

        }else if(nianxian>=1000){
            RenderSystem.setShaderColor(1.0f, 0f, 0.5f,0.8f);

        }else if(nianxian>=100){
            RenderSystem.setShaderColor(1.0f, 1.0f, 0,0.8f);

        }else if(nianxian>=1){
            RenderSystem.setShaderColor(1.0f, 1.0f, 1.0f,0.8f);
        }
    }


    private void renderPlayerAttibute(GuiGraphics guiGraphics,Player player,int x,int y ){

        mutableComponents.clear();
        mutableComponents.add(Component.translatable("生命", (int)player.getHealth(),(int)player.getMaxHealth()));
        mutableComponents.add(Component.translatable("精神力", (int)PlayerAttrubuteAPI.getJingshenli(player),(int)PlayerAttrubuteAPI.getMaxjingshenli(player)));
        mutableComponents.add(Component.translatable("物攻", (int) AttrubuteAPI.getWugong(player)));
        mutableComponents.add(Component.translatable("物防", (int)AttrubuteAPI.getWufang(player)));
        mutableComponents.add(Component.translatable("爆伤", (int)AttrubuteAPI.getBaojishanghai(player)));
        mutableComponents.add(Component.translatable("爆率", (int)AttrubuteAPI.getBaojilv(player)));
        mutableComponents.add(Component.translatable("真伤", (int)AttrubuteAPI.getZhenshang(player)));
        mutableComponents.add(Component.translatable("物穿", (int)AttrubuteAPI.getWuchuan(player)));
        mutableComponents.add(Component.translatable("抗暴", (int)AttrubuteAPI.getKangbao(player)));
        mutableComponents.add(Component.translatable("吸血", (int)AttrubuteAPI.getXixue(player)));
        mutableComponents.add(Component.translatable("回复", (int)AttrubuteAPI.getShengminghuifu(player)));
        mutableComponents.add(Component.translatable("命中", (int)AttrubuteAPI.getMinghzong(player)));
        mutableComponents.add(Component.translatable("闪避", (int)AttrubuteAPI.getShanbi(player)));
        mutableComponents.add(Component.translatable("经验", (int)PlayerAttrubuteAPI.getJingyan(player),(int)PlayerAttrubuteAPI.getMaxJingyan(player)));
        mutableComponents.add(Component.translatable("等级", (int)PlayerAttrubuteAPI.getDengji(player)));
        mutableComponents.add(Component.translatable("转生", (int)PlayerAttrubuteAPI.getZhuansheng(player)));

//        renderListText(guiGraphics,mutableComponents, x+60, y+36);
        renderListText(guiGraphics,mutableComponents, x, y);

//        pose.scale(1/0.8f,1/0.8f,1.0f);
//        pose1.popPose();
    }


    private void renderListText(GuiGraphics guiGraphics, List<MutableComponent> list, int x, int y){

        PoseStack poseStack = guiGraphics.pose();
//        Matrix4f matrix4f = poseStack.last().pose();


        float index = 0;
        for (MutableComponent mutableComponent : list) {
            poseStack.pushPose();

            Matrix4f matrix4f = poseStack.last().pose();
            matrix4f.translate(x+24,y+11 +index,0);
            matrix4f.scale(0.8f,0.8f,1.0f);

            font.drawInBatch(mutableComponent, 0, 0, 0, false, matrix4f, guiGraphics.bufferSource(), Font.DisplayMode.SEE_THROUGH, 100, 000000);
//            var font = net.minecraftforge.client.extensions.common.IClientItemExtensions.of(p_281330_).getFont(p_281330_, net.minecraftforge.client.extensions.common.IClientItemExtensions.FontContext.ITEM_COUNT);
//            guiGraphics.drawString(this.font,mutableComponent, x + 0, y+index,4210752, false);

            index += 12.8f;
            poseStack.popPose();
        }


    }

    public boolean keyPressed(int key, int i, int j) {

        return super.keyPressed(key,i,j);
    }

}
