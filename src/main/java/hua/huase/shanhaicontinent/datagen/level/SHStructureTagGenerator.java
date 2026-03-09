package hua.huase.shanhaicontinent.datagen.level;

import hua.huase.shanhaicontinent.SHMainBus;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.StructureTagsProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.levelgen.structure.Structure;
import net.minecraftforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;

public class SHStructureTagGenerator extends StructureTagsProvider {
    public static final TagKey<Structure> LANDMARK = TagKey.create(Registries.STRUCTURE, new ResourceLocation(SHMainBus.MOD_ID,"shanhaicontistructure"));

    public SHStructureTagGenerator(PackOutput output, CompletableFuture<HolderLookup.Provider> provider, @Nullable ExistingFileHelper helper) {
        super(output, provider, SHMainBus.MOD_ID, helper);
    }

    @Override
    protected void addTags(HolderLookup.Provider p_256087_) {
        this.tag(LANDMARK).add(
                SHStructures.gufengxiaowu01
        );
    }
}
