package org.stereometric.extremedehydration.network.thirst;

import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.packet.CustomPayload;
import net.minecraft.util.Identifier;
import org.stereometric.extremedehydration.Extremedehydration;

public record ThirstSyncPayload(int thirst) implements CustomPayload {

    public static final Id<ThirstSyncPayload> ID =
            new Id<>(Identifier.of(Extremedehydration.MOD_ID, "thirst"));

    public static final PacketCodec<RegistryByteBuf, ThirstSyncPayload> CODEC = PacketCodec.of((payload, buf) -> buf.writeInt(payload.thirst), buf -> new ThirstSyncPayload(buf.readInt()));

    @Override
    public Id<? extends CustomPayload> getId() {
        return ID;
    }
}
