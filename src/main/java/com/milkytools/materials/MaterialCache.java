package com.milkytools.materials;

import com.milkytools.mixin.AbstractBlockMixin;
import net.minecraft.block.*;
import net.minecraft.block.enums.BedPart;
import net.minecraft.block.enums.DoubleBlockHalf;
import net.minecraft.block.enums.SlabType;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.IdentityHashMap;

public class MaterialCache {
    private final IdentityHashMap<BlockState, ItemStack> buildItemsCache = new IdentityHashMap<>();
    
    public ItemStack getRequiredBuildItemForState(BlockState state) {
        return getRequiredBuildItemForState(state, null, BlockPos.ORIGIN);
    }
    
    public ItemStack getRequiredBuildItemForState(BlockState state, @Nullable World world, BlockPos pos) {
        ItemStack stack = buildItemsCache.get(state);
        
        if (stack == null) {
            stack = getStateToItemOverride(state);
            
            if (stack == null && world != null) {
                world.setBlockState(pos, state, 3);
                stack = AbstractBlockMixin.getPickStack(world, pos, state);
            }
            
            if (stack == null || stack.isEmpty()) {
                stack = ItemStack.EMPTY;
            } else {
                overrideStackSize(state, stack);
            }
            
            buildItemsCache.put(state, stack);
        }
        
        return stack.copy();
    }
    
    @Nullable
    private ItemStack getStateToItemOverride(BlockState state) {
        Block block = state.getBlock();
        
        if (block == Blocks.PISTON_HEAD ||
            block == Blocks.MOVING_PISTON ||
            block == Blocks.NETHER_PORTAL ||
            block == Blocks.END_PORTAL ||
            block == Blocks.END_GATEWAY) {
            return ItemStack.EMPTY;
        } else if (block == Blocks.FARMLAND) {
            return new ItemStack(Blocks.DIRT);
        } else if (block == Blocks.BROWN_MUSHROOM_BLOCK) {
            return new ItemStack(Blocks.BROWN_MUSHROOM_BLOCK);
        } else if (block == Blocks.RED_MUSHROOM_BLOCK) {
            return new ItemStack(Blocks.RED_MUSHROOM_BLOCK);
        } else if (block == Blocks.LAVA) {
            if (state.get(FluidBlock.LEVEL) == 0) {
                return new ItemStack(Items.LAVA_BUCKET);
            } else {
                return ItemStack.EMPTY;
            }
        } else if (block == Blocks.WATER) {
            if (state.get(FluidBlock.LEVEL) == 0) {
                return new ItemStack(Items.WATER_BUCKET);
            } else {
                return ItemStack.EMPTY;
            }
        } else if (block instanceof DoorBlock && state.get(DoorBlock.HALF) == DoubleBlockHalf.UPPER) {
            return ItemStack.EMPTY;
        } else if (block instanceof BedBlock && state.get(BedBlock.PART) == BedPart.HEAD) {
            return ItemStack.EMPTY;
        } else if (block instanceof TallPlantBlock && state.get(TallPlantBlock.HALF) == DoubleBlockHalf.UPPER) {
            return ItemStack.EMPTY;
        }
        
        return null;
    }
    
    private void overrideStackSize(BlockState state, ItemStack stack) {
        Block block = state.getBlock();
        
        if (block instanceof SlabBlock && state.get(SlabBlock.TYPE) == SlabType.DOUBLE) {
            stack.setCount(2);
        } else if (block == Blocks.SNOW && state.contains(SnowBlock.LAYERS)) {
            stack.setCount(state.get(SnowBlock.LAYERS));
        } else if (block instanceof TurtleEggBlock && state.contains(TurtleEggBlock.EGGS)) {
            stack.setCount(state.get(TurtleEggBlock.EGGS));
        } else if (block instanceof SeaPickleBlock && state.contains(SeaPickleBlock.PICKLES)) {
            stack.setCount(state.get(SeaPickleBlock.PICKLES));
        } else if (block instanceof CandleBlock && state.contains(CandleBlock.CANDLES)) {
            stack.setCount(state.get(CandleBlock.CANDLES));
        } else if (block instanceof MultifaceGrowthBlock) {
            stack.setCount(MultifaceGrowthBlock.collectDirections(state).size());
        }
    }
    
    public void clearCache() {
        buildItemsCache.clear();
    }
}
