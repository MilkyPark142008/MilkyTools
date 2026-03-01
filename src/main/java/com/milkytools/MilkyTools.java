package com.milkytools;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.minecraft.client.MinecraftClient;
import net.minecraft.text.Text;
import com.milkytools.config.MilkyToolsConfig;
import com.milkytools.materials.MaterialListManager;

public class MilkyTools implements ClientModInitializer {
    public static final String MOD_ID = "milkytools";
    public static final MinecraftClient mc = MinecraftClient.getInstance();
    
    public static MilkyToolsConfig config;
    public static MaterialListManager materialListManager;
    
    @Override
    public void onInitializeClient() {
        ModCompat.LOGGER.info("Initializing MilkyTools...");
        
        config = new MilkyToolsConfig();
        config.load();
        
        materialListManager = new MaterialListManager();
        
        // Register events
        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            if (client.player != null) {
                materialListManager.onClientTick(client);
            }
        });
        
        HudRenderCallback.EVENT.register((drawContext, tickDelta) -> {
            if (mc.player != null) {
                materialListManager.onHudRender(drawContext, tickDelta);
            }
        });
        
        ModCompat.LOGGER.info("MilkyTools initialized successfully!");
    }
}
