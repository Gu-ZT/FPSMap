package dev.dubhe.fpsmap.configs;

import com.google.gson.annotations.SerializedName;

public class FPSMapServerConfig {
    // 是否显示未完全暴露玩家
    @SerializedName("show_hidden_player")
    public boolean showHiddenPlayer = true;
    // 是否共享队友位置
    @SerializedName("share_teammate_position")
    public boolean shareTeammatePosition = true;
    // 是否共享队友视野
    @SerializedName("share_teammate_view")
    public boolean shareTeammateView = true;
}
