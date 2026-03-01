package com.milkytools.mixin;

import net.minecraft.block.*;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(AbstractBlock.class)
public class AbstractBlockMixin {
    @Inject(
        method = "getPickStack",
        at = @At("HEAD"),
        cancellable = true
    )
    private void onGetPickStack(
        BlockView world, BlockPos pos, BlockState state, boolean includeData, CallbackInfoReturnable<ItemStack> cir
    ) {
        Block block = state.getBlock();
        
        if (block instanceof BlockEntityProvider) {
            try {
                ItemStack result = block.getPickStack(world, pos, state, includeData);
                if (result != null && !result.isEmpty()) {
                    cir.setReturnValue(result);
                }
            } catch (Exception ignored) {}
        }
    }
    
    public static ItemStack getPickStack(World world, BlockPos pos, BlockState state) {
        Block block = state.getBlock();
        
        if (block instanceof BlockEntityProvider) {
            try {
                ItemStack result = block.getPickStack(world, pos, state, true);
                if (result != null && !result.isEmpty()) {
                    return result;
                }
            } catch (Exception ignored) {}
        }
        
        return new ItemStack(block);
    }
}
