package hua.huase.shanhaicontinent.capability.playerattribute;

import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.common.capabilities.*;
import net.minecraftforge.common.util.LazyOptional;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class PlayerAttributeCapabilityProvider implements ICapabilityProvider,ICapabilitySerializable<CompoundTag>
{
    public static Capability<PlayerAttributeCapability> CAPABILITY = CapabilityManager.get(new CapabilityToken<PlayerAttributeCapability>(){});
    private PlayerAttributeCapability capability = null;
    private final LazyOptional<PlayerAttributeCapability> CapabilityLazyOptional;


    public PlayerAttributeCapabilityProvider(){
        CapabilityLazyOptional = LazyOptional.of(this::createPlayerCapability);
    }

    private PlayerAttributeCapability createPlayerCapability(){
        if(capability ==null){
            this.capability =  new PlayerAttributeCapability();
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
