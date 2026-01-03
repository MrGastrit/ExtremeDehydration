package org.stereometric.extremedehydration.client;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;
import org.stereometric.extremedehydration.client.hud.ThirstHudOverlay;
import org.stereometric.extremedehydration.client.thirst.ClientThirstData;
import org.stereometric.extremedehydration.network.thirst.ThirstSyncPayload;

public class ExtremedehydrationClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        HudRenderCallback.EVENT.register(new ThirstHudOverlay());

        ClientPlayNetworking.registerGlobalReceiver(
                ThirstSyncPayload.ID,
                (payload, context) -> {
                    context.client().execute(() -> {
                        ClientThirstData.set(payload.thirst());
                    });
                }
        );
    }
}
