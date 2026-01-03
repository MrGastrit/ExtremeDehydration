package org.stereometric.extremedehydration.client.thirst;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;

@Environment(EnvType.CLIENT)
public class ClientThirstData {

    private static int thirst = 20;

    public static int get() {
        return thirst;
    }

    public static void set(int value) {
        thirst = value;
    }
}