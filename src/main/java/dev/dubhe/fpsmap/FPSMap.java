package dev.dubhe.fpsmap;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.mojang.logging.LogUtils;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.loading.FMLConfig;
import org.slf4j.Logger;

import javax.annotation.Nonnull;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;

@Mod(FPSMap.ID)
public class FPSMap {
    public static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    public static final String ID = "fpsmap";
    public static final Logger LOGGER = LogUtils.getLogger();
    public static final Path configPath = Path.of(FMLConfig.defaultConfigPath());

    /**
     * 构造函数
     */
    public FPSMap() {
        MinecraftForge.EVENT_BUS.addListener(this::onClientSetup);
    }

    /**
     * 客户端初始化
     *
     * @param event 事件
     */
    @SubscribeEvent
    public void onClientSetup(FMLClientSetupEvent event) {
        FPSMapClient.init(configPath);
    }

    /**
     * 加载配置文件
     *
     * @param fileName 文件名
     * @param classOfT 类型
     * @param <T>      类型
     * @return 配置文件
     */
    public static <T> T loadConfig(String fileName, Class<T> classOfT) {
        File configFile = configPath.resolve(fileName).toFile();
        try (FileReader reader = new FileReader(configFile)) {
            return FPSMap.GSON.fromJson(reader, classOfT);
        } catch (IOException e) {
            FPSMap.printExceptionStackTrace(e);
        }
        return null;
    }

    /**
     * 保存配置文件
     *
     * @param fileName 文件名
     * @param object   配置文件
     * @param <T>      类型
     */
    public static <T> void saveConfig(String fileName, T object) {
        File configFile = FPSMapClient.getInstance().configPath.resolve(fileName).toFile();
        try (FileWriter writer = new FileWriter(configFile)) {
            FPSMap.GSON.toJson(object, writer);
        } catch (IOException e) {
            FPSMap.printExceptionStackTrace(e);
        }
    }

    /**
     * 打印异常堆栈
     *
     * @param e 异常
     */
    public static void printExceptionStackTrace(Exception e) {
        LOGGER.error(e.toString());
        for (StackTraceElement stackTrace : e.getStackTrace())
            LOGGER.error("\tat " + stackTrace);
    }

    /**
     * 获取资源路径
     *
     * @param path 路径
     * @return 资源路径
     */
    public static @Nonnull ResourceLocation of(@Nonnull String path) {
        return new ResourceLocation(FPSMap.ID, path);
    }
}
