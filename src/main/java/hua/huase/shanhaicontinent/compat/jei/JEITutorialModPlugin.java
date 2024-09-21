package hua.huase.shanhaicontinent.compat.jei;

import hua.huase.shanhaicontinent.SHMainBus;
import hua.huase.shanhaicontinent.init.BlockInit;
import hua.huase.shanhaicontinent.recipe.PotRecipe;
import hua.huase.shanhaicontinent.screen.PotScreen;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.registration.IGuiHandlerRegistration;
import mezz.jei.api.registration.IRecipeCatalystRegistration;
import mezz.jei.api.registration.IRecipeCategoryRegistration;
import mezz.jei.api.registration.IRecipeRegistration;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeManager;

import java.util.List;

@JeiPlugin
public class JEITutorialModPlugin implements IModPlugin {
    @Override
    public ResourceLocation getPluginUid() {
        return new ResourceLocation(SHMainBus.MOD_ID, "jei_plugin");
    }


    @Override
    public void registerRecipeCatalysts(IRecipeCatalystRegistration registration) {
            registration.addRecipeCatalyst(new ItemStack(BlockInit.POT_BLOCK.get()), PotCategory.GEM_POLISHING_TYPE);
    }

    @Override
    public void registerCategories(IRecipeCategoryRegistration registration) {
        registration.addRecipeCategories(new PotCategory(registration.getJeiHelpers().getGuiHelper()));
    }

    @Override
    public void registerRecipes(IRecipeRegistration registration) {
        RecipeManager recipeManager = Minecraft.getInstance().level.getRecipeManager();

        List<PotRecipe> polishingRecipes = recipeManager.getAllRecipesFor(PotRecipe.Type.INSTANCE);
        registration.addRecipes(PotCategory.GEM_POLISHING_TYPE, polishingRecipes);
    }

    @Override
    public void registerGuiHandlers(IGuiHandlerRegistration registration) {
        registration.addRecipeClickArea(PotScreen.class, 60, 30, 20, 30,
                PotCategory.GEM_POLISHING_TYPE);
    }
}
