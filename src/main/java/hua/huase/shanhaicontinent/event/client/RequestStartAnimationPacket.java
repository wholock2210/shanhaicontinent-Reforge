package hua.huase.shanhaicontinent.event.client;

import hua.huase.shanhaicontinent.network.NetworkHandler;
import hua.huase.shanhaicontinent.network.client.SyncWuhunDataPacket;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkEvent;
import net.minecraftforge.network.PacketDistributor;

import java.util.Collections;
import java.util.function.Supplier;

public class RequestStartAnimationPacket {

        public RequestStartAnimationPacket() {}

        public static void encode(RequestStartAnimationPacket pkt, FriendlyByteBuf buf) {
            // 无数据
        }

        public static RequestStartAnimationPacket decode(FriendlyByteBuf buf) {
            return new RequestStartAnimationPacket();
        }

        public static void handle(RequestStartAnimationPacket pkt, Supplier<NetworkEvent.Context> ctx) {
            ctx.get().enqueueWork(() -> {
                ServerPlayer player = ctx.get().getSender();
                if (player != null) {
                    // 构造同步包
                    SyncWuhunDataPacket packet = new SyncWuhunDataPacket(player.getUUID(), Collections.emptyList(), true, System.currentTimeMillis());
                    // 广播给所有客户端
                    NetworkHandler.INSTANCE.send(PacketDistributor.ALL.noArg(), packet);
                }
            });
            ctx.get().setPacketHandled(true);
        }

    }

