package com.quidvio.ant_farm_inf.mixin;

import net.minecraft.util.math.BlockPos;
import net.minecraft.world.gen.feature.EndPortalFeature;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import static com.quidvio.ant_farm_inf.Ant_farm_inf_main.getIsAntFarmInf;

@Mixin(EndPortalFeature.class)
public class EndPortalFeatureMixin {
    @Inject(method = "offsetOrigin", at = @At("HEAD"), cancellable = true)
    private static void fixEndPortalOffsetPos(
        BlockPos pos,
        CallbackInfoReturnable<BlockPos> cir
    ) {
        if (getIsAntFarmInf()) {
            BlockPos newBlockPos = new BlockPos(8, 0, 0).add(pos);
            cir.setReturnValue(newBlockPos);
        }
    }
}
