package hua.huase.shanhaicontinent.mixin;

import net.minecraft.world.level.block.entity.StructureBlockEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(StructureBlockEntity.class)
public class MixinStructureBlockEntity {

    @Redirect(
            method = "load",
            at = @At(
                    value ="INVOKE",
                    target = "Lnet/minecraft/util/Mth;clamp(III)I"
            )
    )
    public int load(int num ,int min ,int max) {

        return num;
    }
}
