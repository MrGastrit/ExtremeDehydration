package org.stereometric.extremedehydration.effect;

import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.util.Identifier;
import org.stereometric.extremedehydration.Extremedehydration;

public class ModEffects {
    public static final RegistryEntry<StatusEffect> THIRST = registerStatusEffect("thirst",
            new ThirstEffect(StatusEffectCategory.NEUTRAL, 0x32CD32)
                    .addAttributeModifier(EntityAttributes.MOVEMENT_SPEED,
                            Identifier.of(Extremedehydration.MOD_ID, "thirst"), 0.1f,
                            EntityAttributeModifier.Operation.ADD_MULTIPLIED_TOTAL));

    private static RegistryEntry<StatusEffect> registerStatusEffect(String name, StatusEffect effect) {
        return Registry.registerReference(Registries.STATUS_EFFECT, Identifier.of(Extremedehydration.MOD_ID, name), effect);
    }

    public static void registerEffects() {
        Extremedehydration.LOGGER.info("Registering StatusEffects for" +  Extremedehydration.MOD_ID);
    }
}
