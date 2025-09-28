package hua.huase.shanhaicontinent.init;

import hua.huase.shanhaicontinent.SHMainBus;
import hua.huase.shanhaicontinent.recipe.ArtifactRecipe;
import hua.huase.shanhaicontinent.recipe.PotRecipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
public class ModRecipesInit {
    public static final DeferredRegister<RecipeSerializer<?>> SERIALIZERS =
            DeferredRegister.create(ForgeRegistries.RECIPE_SERIALIZERS, SHMainBus.MOD_ID);

    public static final DeferredRegister<RecipeType<?>> RECIPE_TYPES =
            DeferredRegister.create(ForgeRegistries.RECIPE_TYPES, SHMainBus.MOD_ID); // 使用MOD_ID保持一致

    public static final RegistryObject<RecipeSerializer<PotRecipe>> POT_TEXT =
            SERIALIZERS.register("pot_recipe", () -> PotRecipe.Serializer.INSTANCE);

    public static final RegistryObject<RecipeSerializer<ArtifactRecipe>> ARTIFACT_SERIALIZER =
            SERIALIZERS.register("artifact", () -> ArtifactRecipe.Serializer.INSTANCE);
    public static final RegistryObject<RecipeType<ArtifactRecipe>> ARTIFACT_RECIPE =
            RECIPE_TYPES.register("artifact", () -> ArtifactRecipe.Type.INSTANCE);

    public static void register(IEventBus eventBus) {
        SERIALIZERS.register(eventBus);
        RECIPE_TYPES.register(eventBus);
    }
}