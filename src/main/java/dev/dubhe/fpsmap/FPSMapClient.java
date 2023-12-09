package dev.dubhe.fpsmap;

import dev.dubhe.fpsmap.configs.FPSMapClientConfig;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.nio.file.Path;

@OnlyIn(Dist.CLIENT)
public class FPSMapClient {
    private static FPSMapClient instance = null;
    public final Path configPath;
    private FPSMapClientConfig clientConfig;

    private FPSMapClient(Path configPath) {
        this.configPath = configPath;
        this.loadConfig();
    }

    /**
     * 初始化
     *
     * @param configPath 配置文件路径
     */
    public static void init(Path configPath) {
        FPSMapClient.instance = new FPSMapClient(configPath);
    }

    /**
     * 加载配置文件
     */
    public void loadConfig() {
        this.clientConfig = FPSMap.loadConfig("fpsmap-client.json", FPSMapClientConfig.class);
    }

    /**
     * 保存配置文件
     */
    public void saveConfig() {
        FPSMap.saveConfig("fpsmap-client.json", this.clientConfig);
    }

    /**
     * 获取实例
     *
     * @return 实例
     */
    public static FPSMapClient getInstance() {
        return instance;
    }

    /**
     * 获取客户端配置
     *
     * @return 客户端配置
     */
    public FPSMapClientConfig getClientConfig() {
        return clientConfig;
    }
}
