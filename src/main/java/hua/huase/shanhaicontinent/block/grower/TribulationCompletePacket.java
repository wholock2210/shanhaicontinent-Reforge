package hua.huase.shanhaicontinent.block.grower;

import net.minecraft.client.Minecraft;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.sounds.SoundEvents;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

// 渡劫完成包
// TribulationCompletePacket.java
public class TribulationCompletePacket {
    public static void encode(TribulationCompletePacket msg, FriendlyByteBuf buffer) {
        // 无需写入数据
    }

    public static TribulationCompletePacket decode(FriendlyByteBuf buffer) {
        return new TribulationCompletePacket();
    }

    public static void handle(TribulationCompletePacket msg, Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(() -> {
            // 客户端播放完成特效
            Minecraft.getInstance().player.playSound(
                    SoundEvents.LIGHTNING_BOLT_THUNDER,
                    1.0F, 1.0F
            );
        });
        ctx.get().setPacketHandled(true);
    }
}