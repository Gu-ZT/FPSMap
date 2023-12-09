package dev.dubhe.fpsmap.configs;

import com.google.gson.annotations.SerializedName;

public class FPSMapClientConfig {
    // 默认敌人颜色
    @SerializedName("default_enemy_color")
    public String defaultEnemyColor = "red";
    // 默认队友颜色
    @SerializedName("default_teammate_color")
    public String defaultTeammateColor = "green";
    // 是否按照队伍颜色显示
    @SerializedName("team_color")
    public boolean teamColor = true;
    // 地图尺寸
    @SerializedName("map_size")
    public double mapSize = 1.0;
}
