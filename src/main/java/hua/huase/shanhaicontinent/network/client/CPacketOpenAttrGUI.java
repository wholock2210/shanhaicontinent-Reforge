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

package hua.huase.shanhaicontinent.network.client;

import hua.huase.shanhaicontinent.network.SynsAPI;
import hua.huase.shanhaicontinent.screen.ModMenuTypes;
import hua.huase.shanhaicontinent.screen.PlayerAttrubuteContainerMenu;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.SimpleMenuProvider;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.inventory.SimpleContainerData;
import net.minecraftforge.network.NetworkEvent;
import net.minecraftforge.network.NetworkHooks;

import java.util.function.Supplier;

public class  CPacketOpenAttrGUI {

  public static void encode(CPacketOpenAttrGUI msg, FriendlyByteBuf buf) {
  }

  public static CPacketOpenAttrGUI decode(FriendlyByteBuf buf) {
    return new CPacketOpenAttrGUI();
  }

  public static void handle(CPacketOpenAttrGUI msg, Supplier<NetworkEvent.Context> ctx) {
    ctx.get().enqueueWork(() -> {
      ServerPlayer sender = ctx.get().getSender();

      if (sender != null) {
        NetworkHooks.openScreen(sender, new SimpleMenuProvider(
                (containerId, playerInventory, player) -> new PlayerAttrubuteContainerMenu(containerId, playerInventory),
                Component.translatable("menu.title.examplemod.mymenu")
        ));

        SynsAPI.synsPlayerAttribute(sender);
      }
    });
    ctx.get().setPacketHandled(true);
  }
}
