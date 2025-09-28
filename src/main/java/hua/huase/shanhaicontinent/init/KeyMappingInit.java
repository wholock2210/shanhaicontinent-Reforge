package hua.huase.shanhaicontinent.init;

import com.mojang.blaze3d.platform.InputConstants;
import hua.huase.shanhaicontinent.SHMainBus;
import hua.huase.shanhaicontinent.command.CPacketUseSkill;
import hua.huase.shanhaicontinent.network.NetworkHandler;
import hua.huase.shanhaicontinent.network.client.CPacketHunji;
import hua.huase.shanhaicontinent.network.client.CPacketOpenAttrGUI;
import hua.huase.shanhaicontinent.network.client.CPacketQiehuanWuhun;
import net.minecraft.client.KeyMapping;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RegisterKeyMappingsEvent;
import net.minecraftforge.common.util.Lazy;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.lwjgl.glfw.GLFW;

public class KeyMappingInit {
    // 原有按键定义保持不变
    public static final Lazy<KeyMapping> HUNJI = Lazy.of(() -> new KeyMapping(
            "技能开关", InputConstants.Type.KEYSYM, GLFW.GLFW_KEY_R, SHMainBus.MOD_ID));

    public static final Lazy<KeyMapping> ATTRIBUTE_MAPPING = Lazy.of(() -> new KeyMapping(
            "属性面板", InputConstants.Type.KEYSYM, GLFW.GLFW_KEY_O, SHMainBus.MOD_ID));

    public static final Lazy<KeyMapping> KAIGUAN_MAPPING = Lazy.of(() -> new KeyMapping(
            "武魂开关", InputConstants.Type.KEYSYM, GLFW.GLFW_KEY_K, SHMainBus.MOD_ID));

    // 新增三个技能槽位按键
    public static final Lazy<KeyMapping> SKILL_SLOT_1 = Lazy.of(() -> new KeyMapping(
            "技能槽位1", InputConstants.Type.KEYSYM, GLFW.GLFW_KEY_Z, SHMainBus.MOD_ID));

    public static final Lazy<KeyMapping> SKILL_SLOT_2 = Lazy.of(() -> new KeyMapping(
            "技能槽位2", InputConstants.Type.KEYSYM, GLFW.GLFW_KEY_X, SHMainBus.MOD_ID));

    public static final Lazy<KeyMapping> SKILL_SLOT_3 = Lazy.of(() -> new KeyMapping(
            "技能槽位3", InputConstants.Type.KEYSYM, GLFW.GLFW_KEY_C, SHMainBus.MOD_ID));

    @Mod.EventBusSubscriber(modid = SHMainBus.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    public static class RegisterBindings {
        @SubscribeEvent
        public static void registerBindings(RegisterKeyMappingsEvent event) {
            // 注册原有按键
            event.register(ATTRIBUTE_MAPPING.get());
            event.register(KAIGUAN_MAPPING.get());
            event.register(HUNJI.get());

            // 注册新增技能槽位按键
            event.register(SKILL_SLOT_1.get());
            event.register(SKILL_SLOT_2.get());
            event.register(SKILL_SLOT_3.get());
        }
    }

    @Mod.EventBusSubscriber(modid = SHMainBus.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE, value = Dist.CLIENT)
    public static class OnClientTick {
        @SubscribeEvent
        public static void onClientTick(TickEvent.ClientTickEvent event) {
            if (event.phase == TickEvent.Phase.END) {
                // 原有按键逻辑
                if (ATTRIBUTE_MAPPING.get().consumeClick()) {
                    NetworkHandler.INSTANCE.sendToServer(new CPacketOpenAttrGUI());
                }
                if (KAIGUAN_MAPPING.get().consumeClick()) {
                    NetworkHandler.INSTANCE.sendToServer(new CPacketQiehuanWuhun());
                }
                if (HUNJI.get().consumeClick()) {
                    NetworkHandler.INSTANCE.sendToServer(new CPacketHunji());
                }

                // 新增技能槽位按键逻辑
                if (SKILL_SLOT_1.get().consumeClick()) {
                    NetworkHandler.INSTANCE.sendToServer(new CPacketUseSkill(0)); // 0表示第一个槽位
                }
                if (SKILL_SLOT_2.get().consumeClick()) {
                    NetworkHandler.INSTANCE.sendToServer(new CPacketUseSkill(1)); // 1表示第二个槽位
                }
                if (SKILL_SLOT_3.get().consumeClick()) {
                    NetworkHandler.INSTANCE.sendToServer(new CPacketUseSkill(2)); // 2表示第三个槽位
                }
            }
        }
    }
}
