package com.chompchompdead.teddyware.api.mixin.mixins;

import com.chompchompdead.teddyware.client.module.ModuleManager;
import com.chompchompdead.teddyware.client.module.render.Xray;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Block.class)
public class MixinBlock {

    @Inject(method = "shouldSideBeRendered", at = @At("HEAD"), cancellable = true)
    public void renderBlockPatch(IBlockState blockState, IBlockAccess blockAccess, BlockPos pos, EnumFacing side, CallbackInfoReturnable<Boolean> callbackInfoReturnable) {
        if (ModuleManager.getModule("Xray").isToggled()) {
            if (Xray.xrayBlocks.contains(blockState.getBlock())) {
                callbackInfoReturnable.setReturnValue(true);
            } else {
                callbackInfoReturnable.setReturnValue(false);
                callbackInfoReturnable.cancel();
            }
        }
    }

    @Inject(method = "isFullCube", at = @At("HEAD"), cancellable = true)
    public void isFullCubePatch(IBlockState state, CallbackInfoReturnable<Boolean> callbackInfoReturnable) {
        if (ModuleManager.getModule("Xray").isToggled()) {
            callbackInfoReturnable.setReturnValue(Xray.xrayBlocks.contains(state.getBlock()));
        }
    }

}
