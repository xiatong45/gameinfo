package com.cxk.gameinfo.config;

import com.cxk.gameinfo.GameinfoClient;
import com.cxk.gameinfo.util.HexUtil;
import com.google.gson.Gson;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;
import net.minecraft.SharedConstants;


public class GameInfoConfig {
    public boolean showFPS = true; // 是否显示FPS
    public boolean showTimeAndDays = true; // 是否显示时间
    public boolean showRealTime = false; // 是否显示现实时间
    public boolean showCoordinates = true; // 是否显示坐标
    public boolean showNetherCoordinates = true; // 是否显示下届坐标
    public boolean showBiome = false; // 是否显示群系
    public int color = 0xFF55FFFF; // 文字颜色（默认蓝色）
    public Integer xPos = 3; // x 坐标
    public Integer yPos = 3; // y 坐标
    public boolean remark = true; // 是否显示备注
    public double scale = 0.5; // 文字
    public String version = SharedConstants.getCurrentVersion().getName(); // 默认使用当前游戏版本
    public boolean showEquipment = true;
    private static final String CONFIG_FILE = "config" + File.separator + "gameinfo.properties";

    public boolean enabled = true; // 是否启用游戏信息显示

    public GameInfoConfig() {
        loadConfig();
    }

    /**
     * @param flag true代表关闭，false代表开启
     */
    public void closeGameInfo(Boolean flag) {
        if (flag) {
            showFPS = false;
            showTimeAndDays = false;
            showRealTime = false;
            showCoordinates = false;
            showNetherCoordinates = false;
            showBiome = false;
            remark = false;
            showEquipment = false;
        } else {
            loadConfig();
            GameinfoClient.logger("加载到配置文件"+ new Gson().toJson(this));
        }
    }


    public void loadConfig() {
        File configFile = new File(CONFIG_FILE);
        if (!configFile.exists()) {
            saveConfig(); // 如果文件不存在，保存默认配置
            return;
        }
        Properties properties = new Properties();
        try (FileInputStream input = new FileInputStream(configFile)) {
            properties.load(input);
            showFPS = Boolean.parseBoolean(properties.getProperty("showFPS", "true"));
            showTimeAndDays = Boolean.parseBoolean(properties.getProperty("showTimeAndDays", "true"));
            showRealTime = Boolean.parseBoolean(properties.getProperty("showRealTime", "false"));
            showCoordinates = Boolean.parseBoolean(properties.getProperty("showCoordinates", "true"));
            showNetherCoordinates = Boolean.parseBoolean(properties.getProperty("showNetherCoordinates", "true"));
            showBiome = Boolean.parseBoolean(properties.getProperty("showBiome", "false"));
            color = (int) Long.parseLong(properties.getProperty("color", "0xFF55FFFF").replace("0x", ""), 16);
            xPos = Integer.parseInt(properties.getProperty("xPos", "3"));
            yPos = Integer.parseInt(properties.getProperty("yPos", "3"));
            remark = Boolean.parseBoolean(properties.getProperty("remark", "true"));
            scale = Double.parseDouble(properties.getProperty("scale", "0.5"));
            // 如果配置文件中没有version，则使用当前游戏版本
            version = properties.getProperty("version", SharedConstants.getCurrentVersion().getName());
            showEquipment = Boolean.parseBoolean(properties.getProperty("showEquipment", "true"));
            enabled = Boolean.parseBoolean(properties.getProperty("enabled", "true"));

            // 检查是否有缺失的配置项，补上默认值并写回文件
            boolean missing = false;
            if (!properties.containsKey("showFPS")) { properties.setProperty("showFPS", "true"); missing = true; }
            if (!properties.containsKey("showTimeAndDays")) { properties.setProperty("showTimeAndDays", "true"); missing = true; }
            if (!properties.containsKey("showRealTime")) { properties.setProperty("showRealTime", "false"); missing = true; }
            if (!properties.containsKey("showCoordinates")) { properties.setProperty("showCoordinates", "true"); missing = true; }
            if (!properties.containsKey("showNetherCoordinates")) { properties.setProperty("showNetherCoordinates", "true"); missing = true; }
            if (!properties.containsKey("showBiome")) { properties.setProperty("showBiome", "false"); missing = true; }
            if (!properties.containsKey("color")) { properties.setProperty("color", "0xFF55FFFF"); missing = true; }
            if (!properties.containsKey("xPos")) { properties.setProperty("xPos", "3"); missing = true; }
            if (!properties.containsKey("yPos")) { properties.setProperty("yPos", "3"); missing = true; }
            if (!properties.containsKey("remark")) { properties.setProperty("remark", "true"); missing = true; }
            if (!properties.containsKey("scale")) { properties.setProperty("scale", "0.5"); missing = true; }
            if (!properties.containsKey("version")) { properties.setProperty("version", SharedConstants.getCurrentVersion().getName()); missing = true; }
            if (!properties.containsKey("showEquipment")) { properties.setProperty("showEquipment", "true"); missing = true; }
            if (!properties.containsKey("enabled")) { properties.setProperty("enabled", "true"); missing = true; }

            if (missing) {
                File parentDir = configFile.getParentFile();
                if (parentDir != null && !parentDir.exists()) {
                    parentDir.mkdirs();
                }
                try (FileOutputStream output = new FileOutputStream(configFile)) {
                    properties.store(output, null);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void saveConfig() {
        Properties properties = new Properties();
        properties.setProperty("showFPS", Boolean.toString(showFPS));
        properties.setProperty("showTimeAndDays", Boolean.toString(showTimeAndDays));
        properties.setProperty("showRealTime", Boolean.toString(showRealTime));
        properties.setProperty("showCoordinates", Boolean.toString(showCoordinates));
        properties.setProperty("showNetherCoordinates", Boolean.toString(showNetherCoordinates));
        properties.setProperty("showBiome", Boolean.toString(showBiome));
        properties.setProperty("color", HexUtil.toHex(color));
        properties.setProperty("xPos", xPos.toString());
        properties.setProperty("yPos", yPos.toString());
        properties.setProperty("remark", Boolean.toString(remark));
        properties.setProperty("scale", Double.toString(scale));
        properties.setProperty("version", version);
        properties.setProperty("showEquipment", Boolean.toString(showEquipment));
        properties.setProperty("enabled", Boolean.toString(enabled));

        // 确保 config 目录存在
        File configFile = new File(CONFIG_FILE);
        File parentDir = configFile.getParentFile();
        if (parentDir != null && !parentDir.exists()) {
            parentDir.mkdirs();
        }

        try (FileOutputStream output = new FileOutputStream(CONFIG_FILE)) {
            properties.store(output, null);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
