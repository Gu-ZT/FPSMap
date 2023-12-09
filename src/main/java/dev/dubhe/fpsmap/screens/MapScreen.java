package dev.dubhe.fpsmap.screens;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import dev.dubhe.fpsmap.FPSMap;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiComponent;
import net.minecraft.client.server.IntegratedServer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.ServerScoreboard;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.players.PlayerList;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.scores.PlayerTeam;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

public class MapScreen extends GuiComponent {
    protected static final ResourceLocation FRAME_LOCATION = FPSMap.of("textures/gui/map/frame.png");
    protected static final ResourceLocation DEFAULT_LOCATION = FPSMap.of("textures/gui/map/default.png");
    @Nonnull
    private final Player player;
    @Nonnull
    private final Minecraft minecraft;
    @Nullable
    private final IntegratedServer server;

    public MapScreen(@Nonnull Player player) {
        this.player = player;
        this.minecraft = Minecraft.getInstance();
        this.server = this.minecraft.getSingleplayerServer();
    }

    /**
     * 渲染
     *
     * @param pPoseStack 画布
     */
    public void render(@Nonnull PoseStack pPoseStack) {
        if (null == server) return;
        PlayerList playerList = server.getPlayerList();
        List<ServerPlayer> players = new ArrayList<>(playerList.getPlayers());
        pPoseStack.pushPose();
        RenderSystem.setShaderTexture(0, FRAME_LOCATION);
        int screenHeight = this.minecraft.getWindow().getGuiScaledHeight();
        int mapSize = screenHeight / 4;
        blit(pPoseStack, 0, 0, mapSize, mapSize, 0, 0, 256, 256, 256, 256);
        for (ServerPlayer player : players) {
            this.renderPlayer(pPoseStack, player);
        }
        pPoseStack.popPose();
    }

    /**
     * 渲染玩家
     *
     * @param pPoseStack 画布
     * @param player     目标玩家
     */
    public void renderPlayer(PoseStack pPoseStack, Player player) {
        if (null == server) return;
        ServerScoreboard scoreboard = server.getScoreboard();
        PlayerTeam selfTeam = scoreboard.getPlayersTeam(this.player.getScoreboardName());
        PlayerTeam targetTeam = scoreboard.getPlayersTeam(player.getScoreboardName());
        if (selfTeam == null || targetTeam == null) return;
        boolean isSelf = this.player.getScoreboardName().equals(player.getScoreboardName());
        boolean isEnemy = selfTeam != targetTeam;
        RenderType type = isEnemy ? getRenderType(player) : RenderType.ALL;
        if (type == RenderType.CANCEL) return;
        Integer color;
        if (isSelf) {
            color = ChatFormatting.WHITE.getColor();
        } else {
            color = targetTeam.getColor().getColor();
        }
        if (color == null) color = isEnemy ? ChatFormatting.RED.getColor() : ChatFormatting.GREEN.getColor();
        if (color == null) return;
        pPoseStack.pushPose();
        switch (type) {
            case ALL -> renderPlayerIcon(pPoseStack, player, color);
            case UNKNOWN -> renderPlayerMark(pPoseStack, player, color);
            default -> {
            }
        }
        pPoseStack.popPose();
    }

    /**
     * 渲染玩家图标
     *
     * @param pPoseStack 画布
     * @param player     目标玩家
     * @param color      颜色
     */
    public void renderPlayerIcon(PoseStack pPoseStack, Player player, int color) {
    }

    /**
     * 渲染玩家标记
     *
     * @param pPoseStack 画布
     * @param player     目标玩家
     * @param color      颜色
     */
    public void renderPlayerMark(PoseStack pPoseStack, Player player, int color) {
    }

    /**
     * 获取渲染类型
     *
     * @param player 目标玩家
     * @return 渲染类型
     */
    public RenderType getRenderType(@Nonnull Player player) {
        Level level = this.player.level;
        Level targetLevel = player.level;
        if (level != targetLevel) return RenderType.CANCEL;
        Vec3 pos = this.player.getEyePosition();
        float yaw = this.player.getXRot();
        double fov = this.minecraft.options.fov;
        int canSee = 0;
        List<Vec3> positions = new ArrayList<>() {{
            this.add(player.position().add(0.3, 0.0, 0.3));
            this.add(player.position().add(-0.3, 0.0, -0.3));
            this.add(player.position().add(0.3, 0.8, 0.3));
            this.add(player.position().add(-0.3, 0.8, -0.3));
            this.add(player.position().add(player.position().add(0.0, player.isShiftKeyDown() ? 1.5 : 1.8, 0.0)));
        }};
        for (Vec3 vec3 : positions) {
            if (canSee(level, pos, yaw, fov, vec3)) canSee++;
        }
        return canSee >= 3 ? RenderType.ALL : canSee >= 1 ? RenderType.UNKNOWN : RenderType.CANCEL;
    }

    /**
     * 判断是否可见
     *
     * @param level 当前世界
     * @param pos   当前玩家位置
     * @param yaw   当前玩家视角
     * @param fov   当前玩家视野
     * @param vec3  目标玩家位置
     * @return 是否可见
     */
    public boolean canSee(Level level, Vec3 pos, float yaw, double fov, Vec3 vec3) {
        return true;
    }

    public enum RenderType {
        ALL,
        UNKNOWN,
        CANCEL
    }
}
