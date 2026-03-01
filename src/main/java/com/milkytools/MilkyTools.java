package com.milkytools;

import net.fabricmc.api.ClientModInitializer;

// Temporarily disabled for 1.21.10 compatibility testing
// TODO: Replace Fabric API events with Mixin-based approach
// import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
// import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
// import net.minecraft.client.MinecraftClient;
// import net.minecraft.text.Text;
// import com.milkytools.config.MilkyToolsConfig;
// import com.milkytools.materials.MaterialListManager;

public class MilkyTools implements ClientModInitializer {
    public static final String MOD_ID = "milkytools";
    
    @Override
    public void onInitializeClient() {
        ModCompat.LOGGER.info("Initializing MilkyTools...");
        ModCompat.LOGGER.info("MilkyTools loaded successfully!");
        
        // TODO: Implement Mixin-based event registration
        // config = new MilkyToolsConfig();
        // config.load();
        // materialListManager = new MaterialListManager();
    }
}
