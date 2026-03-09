package hua.huase.shanhaicontinent.compat.jei;

import hua.huase.shanhaicontinent.SHMainBus;
import hua.huase.shanhaicontinent.init.BlockInit;
import hua.huase.shanhaicontinent.recipe.PotRecipe;
import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.builder.IRecipeSlotBuilder;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.gui.drawable.IDrawableBuilder;
import mezz.jei.api.gui.drawable.IDrawableStatic;
import mezz.jei.api.gui.ingredient.IRecipeSlotsView;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeIngredientRole;
import mezz.jei.api.recipe.RecipeType;
import mezz.jei.api.recipe.category.IRecipeCategory;
import mezz.jei.api.recipe.vanilla.IJeiBrewingRecipe;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraftforge.items.SlotItemHandler;

public class PotCategory implements IRecipeCategory<PotRecipe> {
    public static final ResourceLocation UID = new ResourceLocation(SHMainBus.MOD_ID, "jei_pot");
    public static final ResourceLocation TEXTURE = new ResourceLocation(SHMainBus.MOD_ID,
            "textures/gui/pot/potdisplayer.png");

    public static final RecipeType<PotRecipe> GEM_POLISHING_TYPE =
            new RecipeType<>(UID, PotRecipe.class);

    private final IDrawable background;
    private final IDrawable icon;

    public PotCategory(IGuiHelper helper) {

        IDrawableBuilder iDrawableBuilder = helper.drawableBuilder(TEXTURE, 0, 0, 384, 130);
        this.background = iDrawableBuilder.setTextureSize(384,216).build();
        this.icon = helper.createDrawableIngredient(VanillaTypes.ITEM_STACK, new ItemStack(BlockInit.POT_BLOCK.get()));
    }

    @Override
    public RecipeType<PotRecipe> getRecipeType() {
        return GEM_POLISHING_TYPE;
    }

    @Override
    public Component getTitle() {
        return Component.translatable("block.tutorialmod.gem_polishing_station");
    }

    @Override
    public IDrawable getBackground() {
        return this.background;
    }

    @Override
    public IDrawable getIcon() {
        return this.icon;
    }

    /*
            this.addSlot(new SlotItemHandler(iItemHandler, 0, 41,  27));
            this.addSlot(new SlotItemHandler(iItemHandler, 1, 41,  91));
            this.addSlot(new SlotItemHandler(iItemHandler, 2, 97,  60));
            this.addSlot(new SlotItemHandler(iItemHandler, 3, 116, 100));
            this.addSlot(new SlotItemHandler(iItemHandler, 4, 146, 17));
            this.addSlot(new SlotItemHandler(iItemHandler, 5, 170, 100));
            this.addSlot(new SlotItemHandler(iItemHandler, 6, 194, 60));
            this.addSlot(new SlotItemHandler(iItemHandler, 7, 323, 60));*/

    @Override
    public void setRecipe(IRecipeLayoutBuilder builder, PotRecipe recipe, IFocusGroup focuses) {


        IRecipeSlotBuilder iRecipeSlotBuilder = builder.addSlot(RecipeIngredientRole.INPUT, 41, 27).addIngredients(recipe.getIngredients().get(0));
        builder.addSlot(RecipeIngredientRole.INPUT, 41,   91).addIngredients(recipe.getIngredients().get(1));
        builder.addSlot(RecipeIngredientRole.INPUT, 97,   60).addIngredients(recipe.getIngredients().get(2));
        builder.addSlot(RecipeIngredientRole.INPUT, 116,  100).addIngredients(recipe.getIngredients().get(3));
        builder.addSlot(RecipeIngredientRole.INPUT, 146,  17).addIngredients(recipe.getIngredients().get(4));
        builder.addSlot(RecipeIngredientRole.INPUT, 170,  100).addIngredients(recipe.getIngredients().get(5));
        builder.addSlot(RecipeIngredientRole.INPUT, 194,  60).addIngredients(recipe.getIngredients().get(6));

        IRecipeSlotBuilder iRecipeSlotBuilder1 = builder.addSlot(RecipeIngredientRole.OUTPUT, 323, 60).addItemStack(recipe.getResultItem(null));



    }


    @Override
    public void draw(PotRecipe recipe, IRecipeSlotsView recipeSlotsView, GuiGraphics guiGraphics, double mouseX, double mouseY) {

        guiGraphics.drawString(Minecraft.getInstance().font,
                Component.translatable("所需能量",recipe.getnengliang())
                , 24, 78, 0xFF00ff00, false);
    }

}
