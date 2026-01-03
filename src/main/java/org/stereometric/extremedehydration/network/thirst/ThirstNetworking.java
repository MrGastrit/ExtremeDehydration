package org.stereometric.extremedehydration.network.thirst;

import net.minecraft.network.packet.s2c.common.CustomPayloadS2CPacket;
import net.minecraft.server.network.ServerPlayerEntity;
import org.stereometric.extremedehydration.util.thirst.ThirstHolder;

public class ThirstNetworking {

    public static void sync(ServerPlayerEntity player) {
        int thirst = ((ThirstHolder) player).getThirst();
        player.networkHandler.sendPacket(new CustomPayloadS2CPacket(new ThirstSyncPayload(thirst)));
    }
}
