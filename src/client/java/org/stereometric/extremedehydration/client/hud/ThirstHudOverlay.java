package org.stereometric.extremedehydration.client.hud;

import com.mojang.blaze3d.systems.RenderSystem;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gl.RenderPipelines;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.client.render.RenderTickCounter;
import net.minecraft.util.Identifier;
import net.minecraft.world.GameMode;
import org.jetbrains.annotations.NotNull;
import org.stereometric.extremedehydration.Extremedehydration;
import org.stereometric.extremedehydration.client.thirst.ClientThirstData;
import org.stereometric.extremedehydration.effect.ModEffects;

public class ThirstHudOverlay implements HudRenderCallback {
    public static final Identifier WATER = Identifier.of(Extremedehydration.MOD_ID, "textures/hud/water.png");
    public static final Identifier HALF_WATER = Identifier.of(Extremedehydration.MOD_ID, "textures/hud/half_water.png");
    public static final Identifier THIRST = Identifier.of(Extremedehydration.MOD_ID, "textures/hud/thirst.png");
    public static final Identifier HALF_THIRST = Identifier.of(Extremedehydration.MOD_ID, "textures/hud/half_thirst.png");
    public static final Identifier EMPTY = Identifier.of(Extremedehydration.MOD_ID, "textures/hud/empty_water.png");

    @Override
    public void onHudRender(@NotNull DrawContext drawContext, @NotNull RenderTickCounter renderTickCounter) {
        MinecraftClient client = MinecraftClient.getInstance();
        if (client.player == null || client.options.hudHidden) return;

        GameMode mode = client.player.getGameMode();
        if (mode == GameMode.CREATIVE || mode == GameMode.SPECTATOR) return;


        int x = 0;
        int y = 0;

        int screenWidth = client.getWindow().getScaledWidth();
        int screenHeight = client.getWindow().getScaledHeight();

        int thirst = ClientThirstData.get();
        int maxThirst = 20;

        x = screenWidth / 2 + 91;
        y = screenHeight - 49;

        for (int i = 0; i < 10; i++) {
            int value = i * 2 + 1;

            Identifier texture;
            if (client.player.getActiveStatusEffects().containsKey(ModEffects.THIRST)) {
                if (value < thirst) {
                    texture = THIRST;
                } else if (value == thirst) {
                    texture = HALF_THIRST;
                } else {
                    texture = EMPTY;
                }
            } else {
                if (value < thirst) {
                    texture = WATER;
                } else if (value == thirst) {
                    texture = HALF_WATER;
                } else {
                    texture = EMPTY;
                }
            }

            drawContext.drawTexture(
                    RenderPipelines.GUI_TEXTURED,
                    texture,
                    x - (i * 8) - 9, y, 0, 0, 9, 9, 9, 9
            );
        }
    }
}
