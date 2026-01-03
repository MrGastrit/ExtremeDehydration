package org.stereometric.extremedehydration.item;

import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import org.stereometric.extremedehydration.Extremedehydration;

public class ModItemsGroups {

    public static final ItemGroup HARMLESS_ANIMALS_ITEMS_GROUP = Registry.register(Registries.ITEM_GROUP,
            Identifier.of(Extremedehydration.MOD_ID, "extremedehydration_items"),
            FabricItemGroup.builder().icon(() -> new ItemStack(ModItems.BOTTLE))
                    .displayName(Text.translatable("itemgroup.extremedehydration.extremedehydration_items"))
                    .entries(((displayContext, entries) -> {
                        entries.add(new ItemStack(ModItems.BOTTLE));
                        entries.add(new ItemStack(ModItems.HALF_BOTTLE));
                        entries.add(new ItemStack(ModItems.EMPTY_BOTTLE));
                        entries.add(new ItemStack(ModItems.CLEAN_WATER_BOTTLE));
                        entries.add(new ItemStack(ModItems.CLEAN_HALF_WATER_BOTTLE));
                        entries.add(new ItemStack(ModItems.FILTER));
                    }))
                    .build());

    public static void registerItemGroups() {
        Extremedehydration.LOGGER.info("Registering ItemGroups for" + Extremedehydration.MOD_ID);
    }
}
