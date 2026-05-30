package com.cxk.gameinfo;

import com.cxk.gameinfo.command.GameInfoCommand;
import com.cxk.gameinfo.config.GameInfoConfig;
import com.cxk.gameinfo.hud.BlockInfoHudRenderer;
import com.cxk.gameinfo.hud.EntityHealthHudRenderer;
import com.cxk.gameinfo.hud.HudOverlay;
import com.cxk.gameinfo.keybind.KeybindHandler;
import com.cxk.gameinfo.renderer.FurnaceEventHandler;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.command.v2.ClientCommandRegistrationCallback;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;


public class GameinfoClient implements ClientModInitializer {
    public static String modName = "gameinfo";
    public static GameInfoConfig config = new GameInfoConfig(); // 初始化配置
    public static GameInfoCommand command = new GameInfoCommand(); // 注册指令
    public static HudOverlay hudOverlay = new HudOverlay(); // 创建HUD覆盖层
    public static BlockInfoHudRenderer blockInfoHudRenderer = new BlockInfoHudRenderer(); // 创建方块信息HUD渲染器
    public static EntityHealthHudRenderer entityHealthHudRenderer = new EntityHealthHudRenderer(); // 创建实体血量HUD渲染器

    @Override
    public void onInitializeClient() {
        ClientCommandRegistrationCallback.EVENT.register(command);
        KeybindHandler.register(); // 注册按键绑定
        // 注册HUD渲染回调（1.21.4 使用 HudRenderCallback 替代 HudElement）
        HudRenderCallback.EVENT.register(hudOverlay::onHudRender);
        HudRenderCallback.EVENT.register(blockInfoHudRenderer::onHudRender);
        HudRenderCallback.EVENT.register(entityHealthHudRenderer::onHudRender);
//        BlockEntityRendererFactories.register(BlockEntityType.FURNACE, FurnaceItemRenderer::new);
        FurnaceEventHandler.registerEvents();
    }

    public static void logger(String message) {
        System.out.println("[DEBUG]： " + message);
    }

}
