package com.milkytools.materials;

import com.milkytools.MilkyTools;
import com.milkytools.config.MilkyToolsConfig;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.text.Text;
import net.minecraft.util.math.BlockPos;

import java.util.List;

public class MaterialRenderer {
    private final MinecraftClient mc = MinecraftClient.getInstance();
    
    public void render(DrawContext drawContext, MaterialListManager manager) {
        List<MaterialEntry> materials = manager.getMaterials();
        
        if (materials.isEmpty() || manager.getTargetPos() == null) {
            return;
        }
        
        if (MilkyTools.config.hideAvailable) {
            materials = materials.stream()
                .filter(entry -> entry.getMissing() > 0)
                .toList();
        }
        
        if (materials.isEmpty()) {
            return;
        }
        
        TextRenderer font = mc.textRenderer;
        double scale = MilkyTools.config.hudScale;
        int maxLines = MilkyTools.config.maxLines;
        int bgMargin = 2;
        int lineHeight = 16;
        int displayCount = Math.min(materials.size(), maxLines);
        int contentHeight = (displayCount * lineHeight) + bgMargin + 20;
        int maxTextLength = 0;
        int maxCountLength = 0;
        
        for (int i = 0; i < displayCount; i++) {
            MaterialEntry entry = materials.get(i);
            maxTextLength = Math.max(maxTextLength, font.getWidth(entry.getStack().getName()));
            String countStr = formatCount(entry.getMissing(), entry.getStack().getMaxCount());
            maxCountLength = Math.max(maxCountLength, font.getWidth(countStr));
        }
        
        int maxLineLength = maxTextLength + maxCountLength + 30;
        int windowWidth = mc.getWindow().getScaledWidth();
        int windowHeight = mc.getWindow().getScaledHeight();
        
        int x = windowWidth - maxLineLength - bgMargin - 10;
        int y = bgMargin + 10;
        
        if (scale != 1.0 && scale != 0) {
            y = (int) (y / scale);
        }
        
        // Push matrix for scaling
        drawContext.getMatrices().push();
        
        if (scale != 1.0 && scale != 0) {
            drawContext.getMatrices().scale((float) scale, (float) scale);
        }
        
        // Draw background
        int bgColor = 0xA0000000;
        int bgX = x - bgMargin;
        int bgY = y - bgMargin;
        drawContext.fill(bgX, bgY, bgX + maxLineLength + bgMargin * 2, bgY + contentHeight + bgMargin, bgColor);
        
        // Draw title
        BlockPos pos = manager.getTargetPos();
        String titleText = "Block at: " + pos.getX() + ", " + pos.getY() + ", " + pos.getZ();
        drawContext.drawText(font, Text.literal(titleText), x + 2, y + 2, 0xFFFFFFFF, false);
        
        // Draw entries
        int itemX = x;
        int itemY = y + 16;
        
        for (int i = 0; i < displayCount; i++) {
            drawContext.drawItem(materials.get(i).getStack(), itemX, itemY);
        }
        
        // Draw text
        int textX = x + 18;
        int textY = y + 18;
        int countTextColor = 0xFF55FF55;
        
        for (int i = 0; i < displayCount; i++) {
            MaterialEntry entry = materials.get(i);
            String name = entry.getStack().getName().getString();
            String countStr = formatCount(entry.getMissing(), entry.getStack().getMaxCount());
            int countX = x + maxLineLength - font.getWidth(countStr) - 2;
            
            int textColor = entry.getMissing() > 0 ? 0xFFFF5555 : 0xFF55FF55;
            
            drawContext.drawText(font, Text.literal(name), textX, textY, textColor, false);
            drawContext.drawText(font, Text.literal(countStr), countX, textY, textColor, false);
            
            textY += lineHeight;
        }
        
        // Pop matrix
        drawContext.getMatrices().pop();
    }
    
    private String formatCount(int count, int maxStackSize) {
        if (count > maxStackSize) {
            int stacks = count / maxStackSize;
            int remainder = count % maxStackSize;
            
            if (remainder > 0) {
                return String.format("%d (%d×%d+%d)", count, stacks, maxStackSize, remainder);
            } else {
                return String.format("%d (%d×%d)", count, stacks, maxStackSize);
            }
        } else {
            return String.valueOf(count);
        }
    }
}
