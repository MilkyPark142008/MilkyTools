package com.milkytools.materials;

import com.milkytools.MilkyTools;
import com.milkytools.config.MilkyToolsConfig;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.BlockPos;
import org.lwjgl.glfw.GLFW;

import java.util.ArrayList;
import java.util.List;

public class MaterialListManager {
    private final MinecraftClient mc = MinecraftClient.getInstance();
    private final MaterialCache materialCache = new MaterialCache();
    private final MaterialRenderer renderer = new MaterialRenderer();
    
    private List<MaterialEntry> currentMaterials = new ArrayList<>();
    private BlockState targetBlockState = Blocks.AIR.getDefaultState();
    private BlockPos targetPos = null;
    private long lastUpdateTime = 0;
    
    public void onClientTick(MinecraftClient client) {
        if (!MilkyTools.config.enabled) return;
        
        // Check for key press to toggle HUD
        if (client.options.useKey.isPressed()) {
            // Key press detected - could be used for other features
        }
        
        // Update target block and materials
        updateTargetBlock();
        
        // Periodically update available counts
        long currentTime = System.currentTimeMillis();
        if (currentTime - lastUpdateTime > MilkyTools.config.updateIntervalMs) {
            updateAvailableCounts();
            lastUpdateTime = currentTime;
        }
    }
    
    public void onHudRender(DrawContext drawContext, float tickDelta) {
        if (!MilkyTools.config.enabled || !MilkyTools.config.renderHud) return;
        
        if (mc.player == null || mc.world == null) return;
        
        renderer.render(drawContext, this);
    }
    
    private void updateTargetBlock() {
        if (mc.player == null || mc.world == null || mc.crosshairTarget == null) {
            targetBlockState = Blocks.AIR.getDefaultState();
            targetPos = null;
            currentMaterials.clear();
            return;
        }
        
        if (mc.crosshairTarget.getType() == HitResult.Type.BLOCK) {
            BlockHitResult hitResult = (BlockHitResult) mc.crosshairTarget;
            targetPos = hitResult.getBlockPos();
            targetBlockState = mc.world.getBlockState(targetPos);
            
            // Get required materials for this block
            ItemStack requiredItem = materialCache.getRequiredBuildItemForState(targetBlockState);
            
            if (!requiredItem.isEmpty()) {
                updateMaterialsList(requiredItem);
            }
        }
    }
    
    private void updateMaterialsList(ItemStack targetItem) {
        currentMaterials.clear();
        
        // Count what we have in inventory
        int available = countItemInInventory(targetItem, mc.player);
        int needed = targetItem.getCount();
        int missing = Math.max(0, needed - available);
        
        MaterialEntry entry = new MaterialEntry(
            targetItem,
            needed,
            missing,
            available
        );
        
        currentMaterials.add(entry);
    }
    
    private void updateAvailableCounts() {
        if (mc.player == null) return;
        
        for (MaterialEntry entry :<think>The response was cut off in the middle of writing MaterialListManager.java. I need to continue from where it left off. Let me complete the file and continue with the remaining files.</think><tool name="write_file">
<param name="path">/storage/emulated/0/1/git/milkytools/src/main/java/com/milkytools/materials/MaterialListManager.java