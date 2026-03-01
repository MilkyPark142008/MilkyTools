package com.milkytools.config;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.milkytools.ModCompat;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class MilkyToolsConfig {
    private static final File CONFIG_FILE = new File("config/milkytools.json");
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    
    public boolean enabled = true;
    public boolean renderHud = true;
    public boolean hideAvailable = false;
    public double hudScale = 1.0;
    public int maxLines = 15;
    public long updateIntervalMs = 2000;
    
    public void load() {
        if (CONFIG_FILE.exists()) {
            try (FileReader reader = new FileReader(CONFIG_FILE)) {
                JsonObject json = GSON.fromJson(reader, JsonObject.class);
                
                if (json != null) {
                    if (json.has("enabled")) enabled = json.get("enabled").getAsBoolean();
                    if (json.has("renderHud")) renderHud = json.get("renderHud").getAsBoolean();
                    if (json.has("hideAvailable")) hideAvailable = json.get("hideAvailable").getAsBoolean();
                    if (json.has("hudScale")) hudScale = json.get("hudScale").getAsDouble();
                    if (json.has("maxLines")) maxLines = json.get("maxLines").getAsInt();
                    if (json.has("updateIntervalMs")) updateIntervalMs = json.get("updateIntervalMs").getAsLong();
                }
                ModCompat.LOGGER.info("Config loaded successfully");
            } catch (IOException e) {
                ModCompat.LOGGER.error("Failed to load config", e);
            }
        } else {
            save();
        }
    }
    
    public void save() {
        try (FileWriter writer = new FileWriter(CONFIG_FILE)) {
            JsonObject json = new JsonObject();
            json.addProperty("enabled", enabled);
            json.addProperty("renderHud", renderHud);
            json.addProperty("hideAvailable", hideAvailable);
            json.addProperty("hudScale", hudScale);
            json.addProperty("maxLines", maxLines);
            json.addProperty("updateIntervalMs", updateIntervalMs);
            
            GSON.toJson(json, writer);
            ModCompat.LOGGER.info("Config saved successfully");
        } catch (IOException e) {
            ModCompat.LOGGER.error("Failed to save config", e);
        }
    }
}
