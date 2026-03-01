package com.milkytools.materials;

import net.minecraft.item.ItemStack;

public class MaterialEntry {
    private final ItemStack stack;
    private final int needed;
    private int missing;
    private int available;
    
    public MaterialEntry(ItemStack stack, int needed, int missing, int available) {
        this.stack = stack.copy();
        this.needed = needed;
        this.missing = missing;
        this.available = available;
    }
    
    public ItemStack getStack() {
        return stack;
    }
    
    public int getNeeded() {
        return needed;
    }
    
    public int getMissing() {
        return missing;
    }
    
    public void setMissing(int missing) {
        this.missing = missing;
    }
    
    public int getAvailable() {
        return available;
    }
    
    public void setAvailable(int available) {
        this.available = available;
    }
}
