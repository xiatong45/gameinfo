package com.cxk.gameinfo.keybind;

import com.cxk.gameinfo.GameinfoClient;
import com.cxk.gameinfo.gui.GameInfoConfigScreen;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
import org.lwjgl.glfw.GLFW;

public class KeybindHandler {


    public static void register() {
        registerOpenGui();
    }

    public static void registerOpenGui() {
        KeyMapping openGuiKeyBinding = KeyBindingHelper.registerKeyBinding(new KeyMapping(
                "key.gameinfo.open_gui", // 翻译键
                GLFW.GLFW_KEY_U, // U键
                "gameinfo.keybindings" // 分类名称
        ));
        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            while (openGuiKeyBinding.consumeClick()) {
                Minecraft mc = Minecraft.getInstance();
                if (mc.screen == null) {
                    mc.setScreen(new GameInfoConfigScreen(null));
                }
            }
        });
    }
}
