package com.example.thermalcompass;

import net.fabricmc.api.ModInitializer;

public class ThermalCompass implements ModInitializer {
    @Override
    public void onInitialize() {
        ThermalCompassItems.register();
    }
}