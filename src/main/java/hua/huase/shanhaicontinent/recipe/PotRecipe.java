package hua.huase.shanhaicontinent.recipe;

import com.google.common.collect.Maps;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import hua.huase.shanhaicontinent.SHMainBus;
import net.minecraft.core.NonNullList;
import net.minecraft.core.RegistryAccess;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

import java.util.Map;


public class PotRecipe implements Recipe<SimpleContainer> {
    private final NonNullList<Ingredient> inputItems;
    private final ItemStack output;
    private final ResourceLocation id;
    private final int nengliang;

    public PotRecipe(NonNullList<Ingredient> inputItems, ItemStack output, ResourceLocation id, int nengliang) {
        this.inputItems = inputItems;
        this.output = output;
        this.id = id;
        this.nengliang = nengliang;
    }

    @Override
    public boolean matches(SimpleContainer pContainer, Level pLevel) {
        if(pLevel.isClientSide()) {
            return false;
        }
        int index = 0;
        for (Ingredient inputItem : inputItems) {
            if(!inputItem.test(pContainer.getItem(index))){
                return false;
            }
            index++;
        }


        return true;
    }

    @Override
    public NonNullList<Ingredient> getIngredients() {
        return inputItems;
    }
    public int getnengliang() {
        return nengliang;
    }

    @Override
    public ItemStack assemble(SimpleContainer pContainer, RegistryAccess pRegistryAccess) {
        return output.copy();
    }

    @Override
    public boolean canCraftInDimensions(int pWidth, int pHeight) {
        return true;
    }

    @Override
    public ItemStack getResultItem(RegistryAccess pRegistryAccess) {
        return output.copy();
    }

    @Override
    public ResourceLocation getId() {
        return id;
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return Serializer.INSTANCE;
//        return POT_TEXT.get();
    }

    @Override
    public RecipeType<?> getType() {
        return Type.INSTANCE;
    }

    public static class Type implements RecipeType<PotRecipe> {
        public static final Type INSTANCE = new Type();
        public static final String ID = "pot_recipe";
    }

    public static class Serializer implements RecipeSerializer<PotRecipe> {
        public static final Serializer INSTANCE = new Serializer();
        public static final ResourceLocation ID = new ResourceLocation(SHMainBus.MOD_ID, "pot_recipe");


        /**
         * Returns a key json object as a Java HashMap.
         */
        static Map<String, Ingredient> keyFromJson(JsonObject pKeyEntry) {
            Map<String, Ingredient> map = Maps.newHashMap();

            for(Map.Entry<String, JsonElement> entry : pKeyEntry.entrySet()) {
//                if (entry.getKey().length() != 1) {
//                    throw new JsonSyntaxException("Invalid key entry: '" + (String)entry.getKey() + "' is an invalid symbol (must be 1 character only).");
//                }
//
//                if (" ".equals(entry.getKey())) {
//                    throw new JsonSyntaxException("Invalid key entry: ' ' is a reserved symbol.");
//                }

                map.put(entry.getKey(), Ingredient.fromJson(entry.getValue(), true));
            }

//            map.put(" ", Ingredient.EMPTY);
            return map;
        }


        @Override
        public PotRecipe fromJson(ResourceLocation pRecipeId, JsonObject pSerializedRecipe) {
            Map<String, Ingredient> map = keyFromJson(GsonHelper.getAsJsonObject(pSerializedRecipe, "key"));

            ItemStack itemstack = ShapedRecipe.itemStackFromJson(GsonHelper.getAsJsonObject(pSerializedRecipe, "result"));

            NonNullList<Ingredient> inputs = NonNullList.withSize(7, Ingredient.EMPTY);
            inputs.set(0,map.get("PEIFANG"));
            inputs.set(1,map.get("RANLIAO"));
            inputs.set(2,map.get("MU"));
            inputs.set(3,map.get("SHUI"));
            inputs.set(4,map.get("HUO"));
            inputs.set(5,map.get("JIN"));
            inputs.set(6,map.get("TU"));

            int nengliang = GsonHelper.getAsInt(pSerializedRecipe, "nengliang");

            return new PotRecipe(inputs, itemstack, pRecipeId,nengliang);
        }

        @Override
        public @Nullable PotRecipe fromNetwork(ResourceLocation pRecipeId, FriendlyByteBuf pBuffer) {
            NonNullList<Ingredient> inputs = NonNullList.withSize(pBuffer.readInt(), Ingredient.EMPTY);

            for(int i = 0; i < inputs.size(); i++) {
                inputs.set(i, Ingredient.fromNetwork(pBuffer));
            }

            ItemStack output = pBuffer.readItem();
            int i = pBuffer.readVarInt();
            return new PotRecipe(inputs, output, pRecipeId, i);
//            return new PotRecipe(inputs, output, pRecipeId, 0);
        }

        @Override
        public void toNetwork(FriendlyByteBuf pBuffer, PotRecipe pRecipe) {
            pBuffer.writeInt(pRecipe.inputItems.size());

            for (Ingredient ingredient : pRecipe.getIngredients()) {
                ingredient.toNetwork(pBuffer);
            }
            pBuffer.writeItemStack(pRecipe.getResultItem(null), false);
            pBuffer.writeVarInt(pRecipe.getnengliang());
        }
    }
}
