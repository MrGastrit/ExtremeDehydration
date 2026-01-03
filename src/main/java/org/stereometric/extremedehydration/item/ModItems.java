package org.stereometric.extremedehydration.item;

import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Identifier;
import org.stereometric.extremedehydration.Extremedehydration;
import org.stereometric.extremedehydration.item.custom.*;

import java.util.function.Function;

public class ModItems {
    public static final Item EMPTY_BOTTLE = register("empty_bottle", EmptyBottle::new, new Item.Settings());
    public static final Item HALF_BOTTLE = register("half_bottle", HalfBottleItem::new, new Item.Settings().maxCount(1));
    public static final Item BOTTLE = register("bottle", BottleItem::new, new Item.Settings().maxCount(1));
    public static final Item FILTER = register("filter", Item::new, new Item.Settings());
    public static final Item CLEAN_WATER_BOTTLE = register("clean_bottle", CleanBottleItem::new, new Item.Settings().maxCount(1));
    public static final Item CLEAN_HALF_WATER_BOTTLE = register("clean_half_bottle", CleanHalfBottleItem::new, new Item.Settings().maxCount(1));

    public static Item register(String path, Function<Item.Settings, Item> factory, Item.Settings settings) {
        final RegistryKey<Item> registryKey = RegistryKey.of(RegistryKeys.ITEM, Identifier.of(Extremedehydration.MOD_ID, path));
        return Items.register(registryKey, factory, settings);
    }

    public static void registerItems() {
        Extremedehydration.LOGGER.info("Registering Items for " + Extremedehydration.MOD_ID);
    }
}
