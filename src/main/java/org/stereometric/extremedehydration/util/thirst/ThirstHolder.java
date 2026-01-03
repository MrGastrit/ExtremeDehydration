package org.stereometric.extremedehydration.util.thirst;

import net.minecraft.server.network.ServerPlayerEntity;

public interface ThirstHolder {
    int getThirst();

    void setThirst(int value);

    float getThirstExhaustion();

    void addThirstExhaustion(float value);
}
