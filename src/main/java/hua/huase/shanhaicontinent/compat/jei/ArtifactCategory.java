package hua.huase.shanhaicontinent.compat.jei;

import hua.huase.shanhaicontinent.SHMainBus;
import hua.huase.shanhaicontinent.recipe.ArtifactRecipe;
import hua.huase.shanhaicontinent.register.ModBlock;
import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.gui.drawable.IDrawableAnimated;
import mezz.jei.api.gui.drawable.IDrawableBuilder;
import mezz.jei.api.gui.ingredient.IRecipeSlotsView;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeIngredientRole;
import mezz.jei.api.recipe.RecipeType;
import mezz.jei.api.recipe.category.IRecipeCategory;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.core.NonNullList;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;

public class ArtifactCategory implements IRecipeCategory<ArtifactRecipe> {

    public static final ResourceLocation UID = new ResourceLocation(SHMainBus.MOD_ID,"artifact");
    public static final ResourceLocation TEXTURE = new ResourceLocation(SHMainBus.MOD_ID,"textures/gui/artifact.png");

    private final ResourceLocation PROGRESS_EMPTY = new ResourceLocation("shanhaicontinent:textures/screens/jindutiao.png");
    private final ResourceLocation PROGRESS_FULL = new ResourceLocation("shanhaicontinent:textures/screens/maxjingdutiao.png");

    public static final RecipeType<ArtifactRecipe> ARTIFACT_TYPE = new RecipeType<>(UID,ArtifactRecipe.class);

    private final IDrawable background;
    private final IDrawable icon;

    private final IDrawableAnimated progressBar;


    public ArtifactCategory(IGuiHelper helper) {
        IDrawableBuilder iDrawableBuilder = helper.drawableBuilder(TEXTURE, 0, 0, 181, 87);
        this.background = iDrawableBuilder.setTextureSize(181,87).build();
        this.icon = helper.createDrawableIngredient(VanillaTypes.ITEM_STACK,new ItemStack(ModBlock.artifact_workbench.get()));

        this.progressBar = helper.createAnimatedDrawable(
                helper.drawableBuilder(PROGRESS_EMPTY, 0, 0, 32, 16)
                        .setTextureSize(32, 16)
                        .build(),
                100, // 100 ticks = 5秒 (20 ticks/秒 × 5秒)
                IDrawableAnimated.StartDirection.LEFT,
                true // 循环播放
        );
    }

    @Override
    public void draw(ArtifactRecipe recipe, IRecipeSlotsView slots, GuiGraphics gui,
                     double mouseX, double mouseY) {
        gui.drawString(Minecraft.getInstance().font,
                Component.literal("制作所需材料"),
                10, 48, 0xFF00ff00, false);
        gui.blit(PROGRESS_FULL, 74, 8, 0, 0, 32, 16, 32, 16);
        progressBar.draw(gui, 74, 8);
    }

    @Override
    public RecipeType<ArtifactRecipe> getRecipeType() {
        return ARTIFACT_TYPE;
    }

    @Override
    public Component getTitle() {
        return Component.literal("神器锻造").withStyle(ChatFormatting.BLUE);
    }

    @Override
    public IDrawable getBackground() {
        return this.background;
    }

    @Override
    public IDrawable getIcon() {
        return this.icon;
    }

    @Override
    public void setRecipe(IRecipeLayoutBuilder builder, ArtifactRecipe recipe, IFocusGroup focusGroup) {
        builder.addSlot(RecipeIngredientRole.INPUT, 51, 8)
                .addIngredients(recipe.token);
        builder.addSlot(RecipeIngredientRole.OUTPUT, 105, 8)
                .addItemStack(recipe.getResultItem(null));
        NonNullList<Ingredient> materials = recipe.getIngredients();
        int[][] materialPositions = {
                {11, 62},  // 第1个材料位置
                {46, 62},  // 第2个材料位置
                {81, 62},  // 第3个材料位置
                {118, 62}   // 第4个材料位置
        };
        int materialCount = Math.min(materials.size(), 4);
        for (int i = 0; i < materialCount; i++) {
            builder.addSlot(RecipeIngredientRole.CATALYST,
                            materialPositions[i][0],
                            materialPositions[i][1])
                    .addIngredients(materials.get(i));
        }
    }

}
