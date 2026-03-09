package hua.huase.shanhaicontinent.recipe;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import net.minecraft.core.NonNullList;
import net.minecraft.core.RegistryAccess;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.Level;
import net.minecraftforge.registries.ForgeRegistries;
import org.jetbrains.annotations.Nullable;

public class ArtifactRecipe implements Recipe<SimpleContainer> {
    public final Ingredient token;
    private final NonNullList<Ingredient> materials;
    private final ItemStack output;
    private final ResourceLocation id;

    public ArtifactRecipe(ResourceLocation id, Ingredient token, NonNullList<Ingredient> materials, ItemStack output) {
        this.id = id;
        this.token = token;
        this.materials = materials;
        this.output = output;
    }

    @Override
    public NonNullList<Ingredient> getIngredients() {
        return this.materials;
    }

    @Override
    public boolean matches(SimpleContainer container, Level level) {
        // 检查令牌是否匹配
        return token.test(container.getItem(0));
    }

    @Override
    public ItemStack assemble(SimpleContainer container, RegistryAccess access) {
        return output.copy();
    }

    public boolean hasEnoughMaterials(Player player) {
        Inventory inventory = player.getInventory();
        boolean allOK = true;

        for (Ingredient ingredient : materials) {
            int required = 0;
            int found = 0;

            // 获取所需数量和物品
            ItemStack[] stacks = ingredient.getItems();
            if (stacks.length > 0) {
                required = stacks[0].getCount();
            }

            // 计算背包中已有数量
            for (int i = 0; i < inventory.getContainerSize(); i++) {
                ItemStack stack = inventory.getItem(i);
                if (ingredient.test(stack)) {
                    found += stack.getCount();
                    if (found >= required) break;
                }
            }

            if (found < required) {
                allOK = false;
                break;
            }
        }
        return allOK;
    }
    public void consumeMaterials(Player player) {
        Inventory inventory = player.getInventory();

        for (Ingredient ingredient : materials) {
            int remaining = 0;
            ItemStack[] stacks = ingredient.getItems();
            if (stacks.length > 0) {
                remaining = stacks[0].getCount();
            }

            // 从背包中扣除材料
            for (int i = 0; i < inventory.getContainerSize() && remaining > 0; i++) {
                ItemStack stack = inventory.getItem(i);
                if (ingredient.test(stack)) {
                    int deduct = Math.min(stack.getCount(), remaining);
                    stack.shrink(deduct);
                    remaining -= deduct;
                }
            }
        }
    }

    @Override
    public boolean canCraftInDimensions(int width, int height) {
        return true;
    }

    @Override
    public ItemStack getResultItem(RegistryAccess access) {
        return output.copy();
    }

    @Override
    public ResourceLocation getId() {
        return id;
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return Serializer.INSTANCE;
    }

    @Override
    public RecipeType<?> getType() {
        return Type.INSTANCE;
    }

    public static class Type implements RecipeType<ArtifactRecipe> {
        public static final Type INSTANCE = new Type();
        public static final String ID = "artifact";
    }

    public static class Serializer implements RecipeSerializer<ArtifactRecipe> {
        public static final Serializer INSTANCE = new Serializer();
        public static final ResourceLocation ID = new ResourceLocation("shanhaicontinent", "artifact");



        @Override
        public ArtifactRecipe fromJson(ResourceLocation id, JsonObject json) {
            Ingredient token = Ingredient.fromJson(json.get("token"));
            JsonArray materialsArray = json.getAsJsonArray("materials");
            NonNullList<Ingredient> materials = NonNullList.create();

            for (JsonElement elem : materialsArray) {
                JsonObject obj = elem.getAsJsonObject();
                ItemStack stack = new ItemStack(ForgeRegistries.ITEMS.getValue(new ResourceLocation(obj.get("item").getAsString())),
                        obj.has("count") ? obj.get("count").getAsInt() : 1);
                materials.add(Ingredient.of(stack));
            }

            JsonObject outputJson = json.getAsJsonObject("output");
            ItemStack output = new ItemStack(
                    ForgeRegistries.ITEMS.getValue(new ResourceLocation(outputJson.get("item").getAsString())),
                    outputJson.has("count") ? outputJson.get("count").getAsInt() : 1
            );

            return new ArtifactRecipe(id, token, materials, output);
        }


        @Override
        public @Nullable ArtifactRecipe fromNetwork(ResourceLocation recipeId, FriendlyByteBuf buffer) {
            Ingredient token = Ingredient.fromNetwork(buffer);
            int materialCount = buffer.readVarInt();
            NonNullList<Ingredient> materials = NonNullList.withSize(materialCount, Ingredient.EMPTY);
            for (int i = 0; i < materialCount; i++) {
                materials.set(i, Ingredient.fromNetwork(buffer));
            }
            ItemStack output = buffer.readItem();

            return new ArtifactRecipe(recipeId, token, materials, output);
        }

        @Override
        public void toNetwork(FriendlyByteBuf buffer, ArtifactRecipe recipe) {
            recipe.token.toNetwork(buffer);
            buffer.writeVarInt(recipe.materials.size());
            for (Ingredient ingredient : recipe.materials) {
                ingredient.toNetwork(buffer);
            }
            buffer.writeItem(recipe.output);
        }
    }
}