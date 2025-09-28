package hua.huase.shanhaicontinent.capability.monsterattribute;

import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraftforge.common.capabilities.*;
import net.minecraftforge.common.util.LazyOptional;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class MonsterAttributeCapabilityProvider<T extends MonsterAttributeCapability> implements ICapabilityProvider,ICapabilitySerializable<CompoundTag>
{
    public static Capability<MonsterAttributeCapability> CAPABILITY = CapabilityManager.get(new CapabilityToken<MonsterAttributeCapability>(){});
    private MonsterAttributeCapability capability = null;
    private final LazyOptional<MonsterAttributeCapability> CapabilityLazyOptional;


    public MonsterAttributeCapabilityProvider(){
        CapabilityLazyOptional = LazyOptional.of(this::createPlayerCapability);
    }


    private MonsterAttributeCapability createPlayerCapability(){
        if(capability ==null){
            this.capability =  new MonsterAttributeCapability();
        }
        return capability;
    }

    @Override
    public @NotNull <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
        if(cap== CAPABILITY){
            return CapabilityLazyOptional.cast();
        }
        return LazyOptional.empty();
    }

    @Override
    public CompoundTag serializeNBT() {
        return createPlayerCapability().serializeNBT();
    }

    @Override
    public void deserializeNBT(CompoundTag nbt) {
        createPlayerCapability().deserializeNBT(nbt);
    }
}
