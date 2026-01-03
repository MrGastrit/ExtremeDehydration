package org.stereometric.extremedehydration;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.stereometric.extremedehydration.effect.ModEffects;
import org.stereometric.extremedehydration.item.ModItems;
import org.stereometric.extremedehydration.item.ModItemsGroups;
import org.stereometric.extremedehydration.network.thirst.ThirstSyncPayload;

public class Extremedehydration implements ModInitializer {
    public static final String MOD_ID = "extremedehydration";
    public static Logger LOGGER = LogManager.getLogger(MOD_ID);

    @Override
    public void onInitialize() {
        ModItems.registerItems();
        ModItemsGroups.registerItemGroups();
        ModEffects.registerEffects();

        PayloadTypeRegistry.playS2C().register(
                ThirstSyncPayload.ID,
                ThirstSyncPayload.CODEC
        );
    }
}
