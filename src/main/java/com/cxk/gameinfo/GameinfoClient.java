package com.cxk.gameinfo;

import com.cxk.gameinfo.config.GameInfoConfig;
import com.cxk.gameinfo.hud.HudOverlay;
import com.cxk.gameinfo.keybind.KeybindHandler;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;


public class GameinfoClient implements ClientModInitializer {
    public static String modName = "gameinfo";
    public static GameInfoConfig config = new GameInfoConfig(); // 初始化配置
    public static HudOverlay hudOverlay = new HudOverlay(); // 创建HUD覆盖层

    @Override
    public void onInitializeClient() {
        KeybindHandler.register(); // 注册按键绑定
        // 注册HUD渲染回调（1.21.4 使用 HudRenderCallback 替代 HudElement）
        HudRenderCallback.EVENT.register(hudOverlay::onHudRender);
    }

    public static void logger(String message) {
        System.out.println("[DEBUG]： " + message);
    }

}
