package hua.huase.shanhaicontinent.register;

import hua.huase.shanhaicontinent.SHMainBus;
import hua.huase.shanhaicontinent.block.Zzhaohuantaientity;
import hua.huase.shanhaicontinent.block.shenjie.blockentity.ArtifactBlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;


public class ModBlockEntities {
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES =
            DeferredRegister.create(ForgeRegistries.BLOCK_ENTITY_TYPES, SHMainBus.MOD_ID);

    public static final RegistryObject<BlockEntityType<Zzhaohuantaientity>> ZHAOHUANTAI =
            BLOCK_ENTITIES.register("zhaohuantai",
                    () -> BlockEntityType.Builder.of(Zzhaohuantaientity::new,
                            ModBlock.ZHAOHUANTAI_BLOCK.get()).build(null));

    public static final RegistryObject<BlockEntityType<ArtifactBlockEntity>> ARTIFACT =
            BLOCK_ENTITIES.register("artifact",
                    () -> BlockEntityType.Builder.of((pos, state) -> new ArtifactBlockEntity(ModBlockEntities.ARTIFACT.get(), pos, state),
                            ModBlock.artifact_workbench.get()).build(null));

    // 确保在Mod主类中调用这个方法注册
    public static void register(IEventBus eventBus) {
        BLOCK_ENTITIES.register(eventBus);
    }
}