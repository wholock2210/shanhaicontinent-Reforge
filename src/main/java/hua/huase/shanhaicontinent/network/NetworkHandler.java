/*
 * Copyright (c) 2018-2020 C4
 *
 * This file is part of Curios, a mod made for Minecraft.
 *
 * Curios is free software: you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as published
 * by the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Curios is distributed in the hope that it will be useful, but
 * WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with Curios.  If not, see <https://www.gnu.org/licenses/>.
 */

package hua.huase.shanhaicontinent.network;

import java.util.function.BiConsumer;
import java.util.function.Function;
import java.util.function.Supplier;

import hua.huase.shanhaicontinent.SHMainBus;
import hua.huase.shanhaicontinent.block.grower.TribulationCompletePacket;
import hua.huase.shanhaicontinent.command.CPacketUseSkill;
import hua.huase.shanhaicontinent.event.client.RequestStartAnimationPacket;
import hua.huase.shanhaicontinent.event.client.SyncShenciAttributesPacket;
import hua.huase.shanhaicontinent.network.client.*;
import hua.huase.shanhaicontinent.network.server.*;
import hua.huase.shanhaicontinent.util.artifact.StartCraftingPacket;
import hua.huase.shanhaicontinent.util.hungufenjie.EnhanceSoulBonePacket;
import hua.huase.shanhaicontinent.util.hungufenjie.FenjieSoulbonePacket;
import hua.huase.shanhaicontinent.util.procedures.CraftingPacket;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkDirection;
import net.minecraftforge.network.NetworkEvent;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.PacketDistributor;
import net.minecraftforge.network.simple.SimpleChannel;

public class NetworkHandler {

  private static final String PTC_VERSION = "1";

  public static SimpleChannel INSTANCE;

  private static int id = 0;

  private static boolean isRegistered = false; // 添加一个标志位

  public static void register() {
    if (!isRegistered) { // 确保只注册一次
      INSTANCE = NetworkRegistry.ChannelBuilder.named(new ResourceLocation(SHMainBus.MOD_ID, "main"))
              .networkProtocolVersion(() -> PTC_VERSION).clientAcceptedVersions(PTC_VERSION::equals)
              .serverAcceptedVersions(PTC_VERSION::equals).simpleChannel();

      // 注册 CraftingPacket
      register(CraftingPacket.class, CraftingPacket::encode, CraftingPacket::decode, CraftingPacket::handle);

      // 其他数据包
      register(CPacketOpenAttrGUI.class, CPacketOpenAttrGUI::encode, CPacketOpenAttrGUI::decode, CPacketOpenAttrGUI::handle);
      register(CPacketQiehuanWuhun.class, CPacketQiehuanWuhun::encode, CPacketQiehuanWuhun::decode, CPacketQiehuanWuhun::handle);
      register(CPacketHunji.class, CPacketHunji::encode, CPacketHunji::decode, CPacketHunji::handle);
      register(CPacketUseSkill.class, CPacketUseSkill::encode, CPacketUseSkill::new, CPacketUseSkill::handle);
      register(CPacketOpenSkillGUI.class,
              CPacketOpenSkillGUI::encode,
              CPacketOpenSkillGUI::new,
              CPacketOpenSkillGUI::handle);

      register(CPacketStrengthen.class, CPacketStrengthen::encode, CPacketStrengthen::decode, CPacketStrengthen::handle);

      register(StartCraftingPacket.class,StartCraftingPacket::encode,StartCraftingPacket::decode,StartCraftingPacket::handle);

      register(SyncWuhunDataPacket.class,SyncWuhunDataPacket::encode,SyncWuhunDataPacket::decode,SyncWuhunDataPacket::handle);
      register(RequestStartAnimationPacket.class,RequestStartAnimationPacket::encode,RequestStartAnimationPacket::decode,RequestStartAnimationPacket::handle);

      register(TribulationCompletePacket.class,TribulationCompletePacket::encode,TribulationCompletePacket::decode,TribulationCompletePacket::handle);

      register(FenjieSoulbonePacket.class,FenjieSoulbonePacket::encode,FenjieSoulbonePacket::decode,FenjieSoulbonePacket::handle);
      register(EnhanceSoulBonePacket.class,EnhanceSoulBonePacket::encode,EnhanceSoulBonePacket::decode,EnhanceSoulBonePacket::handle);

      register(SyncShenciAttributesPacket.class,SyncShenciAttributesPacket::encode,SyncShenciAttributesPacket::decode,SyncShenciAttributesPacket::handle);

      register(SPacketPlayerAttribute.class, SPacketPlayerAttribute::encode, SPacketPlayerAttribute::decode, SPacketPlayerAttribute::handle);
      register(SPacketEntityAttribute.class, SPacketEntityAttribute::encode, SPacketEntityAttribute::decode, SPacketEntityAttribute::handle);
      register(AreaProtectionPacket.class, AreaProtectionPacket::encode, AreaProtectionPacket::new, AreaProtectionPacket.Handler::onMessage);
      isRegistered = true; // 标记为已注册
    }
  }

  private static <M> void register(Class<M> messageType, BiConsumer<M, FriendlyByteBuf> encoder,
                                   Function<FriendlyByteBuf, M> decoder,
                                   BiConsumer<M, Supplier<NetworkEvent.Context>> messageConsumer) {
    INSTANCE.registerMessage(id++, messageType, encoder, decoder, messageConsumer);
  }

  public static void sendToServer(Object packet) {
    INSTANCE.sendToServer(packet);
  }
  public static void sendToClient(Object packet, ServerPlayer player) {
    INSTANCE.send(PacketDistributor.PLAYER.with(() -> player), packet);
  }
}