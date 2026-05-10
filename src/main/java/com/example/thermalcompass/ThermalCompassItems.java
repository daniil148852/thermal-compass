package com.example.thermalcompass;

import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class ThermalCompassItems {
    public static final Item THERMAL_COMPASS = new ThermalCompassItem(new Item.Settings().maxCount(1));

    public static void register() {
        Registry.register(Registries.ITEM, new Identifier("thermalcompass", "thermal_compass"), THERMAL_COMPASS);
    }
}