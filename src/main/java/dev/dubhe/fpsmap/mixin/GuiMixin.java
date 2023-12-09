package dev.dubhe.fpsmap.mixin;

import com.mojang.blaze3d.vertex.PoseStack;
import dev.dubhe.fpsmap.screens.MapScreen;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.client.gui.ForgeIngameGui;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ForgeIngameGui.class)
public abstract class GuiMixin extends Gui {
    private GuiMixin(Minecraft pMinecraft) {
        super(pMinecraft);
    }

    @Unique
    private @Nullable Player fPSMap$getCameraPlayer() {
        return !(this.minecraft.getCameraEntity() instanceof Player) ? null : (Player) this.minecraft.getCameraEntity();
    }

    @Inject(method = "render", at = @At(value = "RETURN"))
    private void render(PoseStack pPoseStack, float pPartialTick, CallbackInfo ci) {
        Player player = this.fPSMap$getCameraPlayer();
        if (null == player) return;
        MapScreen map = new MapScreen(player);
        map.render(pPoseStack);
    }
}
