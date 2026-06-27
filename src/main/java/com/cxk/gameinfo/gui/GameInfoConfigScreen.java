package com.cxk.gameinfo.gui;

import com.cxk.gameinfo.GameinfoClient;
import com.cxk.gameinfo.config.GameInfoConfig;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;

public class GameInfoConfigScreen extends Screen {
    private final Screen parent;
    private GameInfoConfig config;
    // 配置快照，用于取消时恢复
    private boolean snapshotShowFPS, snapshotShowTimeAndDays, snapshotShowRealTime;
    private boolean snapshotShowCoordinates, snapshotShowNetherCoordinates, snapshotShowBiome;
    private boolean snapshotRemark, snapshotShowEquipment;
    private boolean snapshotEnabled;
    private String snapshotVersion;
    private int snapshotColor, snapshotXPos, snapshotYPos;

    // Tab相关
    private int currentTab = 0;
    private final List<TabButton> tabButtons = new ArrayList<>();
    private final String[] tabNames = {"信息开关", "基础设置"};
    
    // 信息开关Tab的组件
    private Button fpsButton;
    private Button timeButton;
    private Button realTimeButton;
    private Button coordButton;
    private Button netherButton;
    private Button biomeButton;
    private Button remarkButton;
    private Button showEquipmentButton;

    private Button enableButton;
    
    // 颜色设置Tab的组件
    private EditBox xPosField;
    private EditBox yPosField;
    private EditBox versionField;
    private Button colorButton;

    public GameInfoConfigScreen(Screen parent) {
        super(Component.literal("游戏信息配置"));
        this.parent = parent;
        this.config = GameinfoClient.config;
        saveSnapshot();
    }

    private void saveSnapshot() {
        snapshotShowFPS = config.showFPS;
        snapshotShowTimeAndDays = config.showTimeAndDays;
        snapshotShowRealTime = config.showRealTime;
        snapshotShowCoordinates = config.showCoordinates;
        snapshotShowNetherCoordinates = config.showNetherCoordinates;
        snapshotShowBiome = config.showBiome;
        snapshotRemark = config.remark;
        snapshotShowEquipment = config.showEquipment;
        snapshotEnabled = config.enabled;
        snapshotColor = config.color;
        snapshotXPos = config.xPos;
        snapshotYPos = config.yPos;
        snapshotVersion = config.version;
    }

    private void restoreSnapshot() {
        config.showFPS = snapshotShowFPS;
        config.showTimeAndDays = snapshotShowTimeAndDays;
        config.showRealTime = snapshotShowRealTime;
        config.showCoordinates = snapshotShowCoordinates;
        config.showNetherCoordinates = snapshotShowNetherCoordinates;
        config.showBiome = snapshotShowBiome;
        config.remark = snapshotRemark;
        config.showEquipment = snapshotShowEquipment;
        config.enabled = snapshotEnabled;
        config.color = snapshotColor;
        config.xPos = snapshotXPos;
        config.yPos = snapshotYPos;
        config.version = snapshotVersion;
    }

    @Override
    protected void init() {
        this.clearWidgets();
        
        // 创建Tab按钮
        createTabButtons();
        
        // 根据当前Tab创建内容
        if (currentTab == 0) {
            createInfoToggleTab();
        } else if (currentTab == 1) {
            createColorSettingTab();
        }
        
        // 创建底部按钮
        createBottomButtons();
    }
    
    private void createTabButtons() {
        tabButtons.clear();
        int centerX = this.width / 2;
        int tabWidth = 80;
        int tabHeight = 20;
        int startX = centerX - (tabNames.length * tabWidth) / 2;
        
        for (int i = 0; i < tabNames.length; i++) {
            final int tabIndex = i;
            TabButton tabButton = new TabButton(
                startX + i * tabWidth, 35, 
                tabWidth, tabHeight, 
                tabNames[i], 
                tabIndex == currentTab,
                () -> switchTab(tabIndex)
            );
            tabButtons.add(tabButton);
            this.addRenderableWidget(tabButton);
        }
    }
    
    private void switchTab(int newTab) {
        if (newTab != currentTab) {
            currentTab = newTab;
            this.init();
        }
    }
    
    private void createInfoToggleTab() {
        int centerX = this.width / 2;
        int startY = 80;
        int buttonWidth = 100;
        int buttonHeight = 20;
        int spacing = 25;
        int sideMargin = 40;
        
        // 左列
        int leftX = centerX - buttonWidth - sideMargin;
        this.fpsButton = this.addRenderableWidget(GuiHelper.createToggleButton(
                "帧数显示", () -> config.showFPS, value -> config.showFPS = value,
                leftX, startY, buttonWidth, buttonHeight));

        this.timeButton = this.addRenderableWidget(GuiHelper.createToggleButton(
                "时间显示", () -> config.showTimeAndDays, value -> config.showTimeAndDays = value,
                leftX, startY + spacing, buttonWidth, buttonHeight));

        this.coordButton = this.addRenderableWidget(GuiHelper.createToggleButton(
                "坐标显示", () -> config.showCoordinates, value -> config.showCoordinates = value,
                leftX, startY + spacing * 2, buttonWidth, buttonHeight));

        this.netherButton = this.addRenderableWidget(GuiHelper.createToggleButton(
                "下界坐标", () -> config.showNetherCoordinates, value -> config.showNetherCoordinates = value,
                leftX, startY + spacing * 3, buttonWidth, buttonHeight));

        this.enableButton = this.addRenderableWidget(GuiHelper.createToggleButton(
                "全局显示", () -> config.enabled, value -> {
                    config.enabled = value;
                    this.init();
                    GameinfoClient.logger("全局显示: " + value);
                },
                leftX, startY + spacing * 4, buttonWidth, buttonHeight));

        // 右列
        int rightX = centerX + sideMargin;
        this.biomeButton = this.addRenderableWidget(GuiHelper.createToggleButton(
                "群系显示", () -> config.showBiome, value -> config.showBiome = value,
                rightX, startY, buttonWidth, buttonHeight));

        this.showEquipmentButton = this.addRenderableWidget(GuiHelper.createToggleButton(
                "装备显示", () -> config.showEquipment, value -> config.showEquipment = value,
                rightX, startY + spacing, buttonWidth, buttonHeight));

        this.remarkButton = this.addRenderableWidget(GuiHelper.createToggleButton(
                "版本显示", () -> config.remark, value -> config.remark = value,
                rightX, startY + spacing * 2, buttonWidth, buttonHeight));

        this.realTimeButton = this.addRenderableWidget(GuiHelper.createToggleButton(
                "现实时间", () -> config.showRealTime, value -> config.showRealTime = value,
                rightX, startY + spacing * 3, buttonWidth, buttonHeight));
    }
    
    private void createColorSettingTab() {
        int centerX = this.width / 2;
        int startY = 100;
        int spacing = 30;
        
        // 位置设置
        this.xPosField = this.addRenderableWidget(GuiHelper.createNumberField(
                this.font, "X位置", () -> config.xPos, value -> config.xPos = value,
                centerX - 65, startY, 50, 20));

        this.yPosField = this.addRenderableWidget(GuiHelper.createNumberField(
                this.font, "Y位置", () -> config.yPos, value -> config.yPos = value,
                centerX + 15, startY, 50, 20));

        // 版本备注输入框
        this.versionField = new EditBox(this.font, centerX - 65, startY + spacing, 130, 20,
                Component.literal("版本备注"));
        this.versionField.setValue(config.version);
        this.versionField.setResponder(value -> config.version = value);
        this.addRenderableWidget(this.versionField);

        // 颜色选择按钮
        this.colorButton = this.addRenderableWidget(Button.builder(
                Component.literal("选择颜色"),
                button -> this.minecraft.setScreen(new ColorPickerScreen(this, (color, colorName) -> {
                    config.color = color;
                })))
                .bounds(centerX - 50, startY + spacing * 2, 100, 20)
                .build());
    }
    
    private void createBottomButtons() {
        int centerX = this.width / 2;
        int bottomY = this.height - 40;
        
        this.addRenderableWidget(GuiHelper.createButton("保存", () -> {
            config.saveConfig();
            this.onClose();
        }, centerX - 55, bottomY, 50, 20));

        this.addRenderableWidget(GuiHelper.createButton("取消", () -> {
            // 取消时从快照恢复之前的设置
            restoreSnapshot();
            this.onClose();
        }, centerX + 5, bottomY, 50, 20));
    }

    @Override
    public void render(GuiGraphics context, int mouseX, int mouseY, float delta) {
        // 绘制半透明背景
        context.fill(0, 0, this.width, this.height, 0x50000000);
        
        super.render(context, mouseX, mouseY, delta);

        // 绘制标题
        context.drawString(this.font, this.title, this.width / 2 - this.font.width(this.title) / 2, 20, 0xFFFFD700, false);
        
        // 根据当前Tab绘制特定内容
        // 绘制Tab内容区域背景
        int tabContentY = 60;
        int tabContentHeight = this.height - 110;
        context.fill(20, tabContentY, this.width - 20, tabContentY + tabContentHeight, 0x20000000);
        if (currentTab == 0) {
            renderInfoToggleTab(context);
        } else if (currentTab == 1) {
            renderColorSettingTab(context);
        }
    }
    
    private void renderInfoToggleTab(GuiGraphics context) {
        int centerX = this.width / 2;
        context.drawString(this.font, "选择要显示的信息", centerX - this.font.width("选择要显示的信息") / 2, 70, 0xFFAAFFAA, false);
    }
    
    private void renderColorSettingTab(GuiGraphics context) {
        int centerX = this.width / 2;
        int startY = 100;
        int spacing = 30;
        
        // 绘制标题
        context.drawString(this.font, "自定义显示位置、版本、颜色", centerX - this.font.width("自定义显示位置、版本、颜色") / 2, 70, 0xFFAAFFAA, false);
        
        // 绘制X和Y标签
        context.drawString(this.font, "X:", centerX - 85, startY + 4, 0xFFFFFFFF);
        context.drawString(this.font, "Y:", centerX - 5, startY + 4, 0xFFFFFFFF);
        
        // 绘制版本标签
        context.drawString(this.font, "版本:", centerX - 95, startY + spacing + 6, 0xFFFFFFFF);

        // 绘制颜色预览
        int colorSize = 24;
        int colorX = centerX + 65;
        int colorY = startY + spacing * 2 - 2;
        
        context.fill(colorX - 1, colorY - 1, colorX + colorSize + 1, colorY + colorSize + 1, 0xFF000000);
        context.fill(colorX, colorY, colorX + colorSize, colorY + colorSize, config.color);
    }

    @Override
    public void onClose() {
        this.minecraft.setScreen(this.parent);
    }

    @Override
    public boolean isPauseScreen() {
        return true;
    }
    
    // Tab按钮类
    @Override
    public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
        if (keyCode == 256) { // ESC
            restoreSnapshot();
            this.onClose();
            return true;
        }
        return super.keyPressed(keyCode, scanCode, modifiers);
    }

    private static class TabButton extends Button {
        private final boolean isActive;
        
        public TabButton(int x, int y, int width, int height, String text, boolean isActive, Runnable onPress) {
            super(x, y, width, height, net.minecraft.network.chat.Component.literal(text), button -> onPress.run(), DEFAULT_NARRATION);
            this.isActive = isActive;
        }
        

        @Override
        protected void renderWidget(GuiGraphics context, int mouseX, int mouseY, float deltaTicks) {
            // 根据状态选择颜色
            int bgColor = isActive ? 0xFF4A4A4A : 0xFF2A2A2A;
            int borderColor = isActive ? 0xFFFFFFFF : 0xFF666666;
            int textColor = isActive ? 0xFFFFD700 : 0xFFCCCCCC;

            if (this.isHovered() && !isActive) {
                bgColor = 0xFF3A3A3A;
            }

            // 绘制Tab背景
            context.fill(this.getX(), this.getY(), this.getX() + this.width, this.getY() + this.height, bgColor);

            // 绘制边框（活跃Tab不绘制底边）
//            context.drawBorder(this.getX(), this.getY(), this.width, this.height, borderColor);
            if (isActive) {
                context.fill(this.getX() + 1, this.getY() + this.height - 1, this.getX() + this.width - 1, this.getY() + this.height, bgColor);
            }

            // 绘制文本
            int textX = this.getX() + this.width / 2;
            int textY = this.getY() + (this.height - 8) / 2;
            context.drawString(Minecraft.getInstance().font, this.getMessage(), textX - Minecraft.getInstance().font.width(this.getMessage()) / 2, textY, textColor, false);
        }
    }
}
