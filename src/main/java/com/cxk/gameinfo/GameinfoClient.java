 package com.cxk.gameinfo;
 
 import com.cxk.gameinfo.config.GameInfoConfig;
 import com.cxk.gameinfo.hud.HudOverlay;
 import com.cxk.gameinfo.keybind.KeybindHandler;
 import net.fabricmc.api.ClientModInitializer;
 import net.fabricmc.fabric.api.client.rendering.v1.hud.HudElementRegistry;
 import net.fabricmc.fabric.api.client.rendering.v1.hud.VanillaHudElements;
 import net.minecraft.resources.Identifier;


 public class GameinfoClient implements ClientModInitializer {
     public static String modName = "gameinfo";
     public static GameInfoConfig config = new GameInfoConfig(); // 初始化配置
     public static HudOverlay hudOverlay = new HudOverlay(); // 创建HUD覆盖层

     @Override
     public void onInitializeClient() {
         KeybindHandler.register(); // 注册按键绑定
         HudElementRegistry.attachElementBefore(VanillaHudElements.CHAT, Identifier.fromNamespaceAndPath(modName, "custom_text"), hudOverlay); // 注册HUD元素
     }

    public static void logger(String message) {
        System.out.println("[DEBUG]： " + message);
    }

}
