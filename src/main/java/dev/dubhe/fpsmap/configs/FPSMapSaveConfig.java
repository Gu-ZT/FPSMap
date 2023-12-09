package dev.dubhe.fpsmap.configs;

import java.util.Vector;

public class FPSMapSaveConfig extends Vector<FPSMapSaveConfig.FPSMapSaveConfigItem> {
    public static class FPSMapSaveConfigItem {
        public String dim = "default"; // 维度名称
        public String image = "fpsmap:gui/map/default"; // 地图图片
        public int[] center = {0, 0}; // 地图中心（x,z）
        public int range = 256; // 地图范围
    }
}

