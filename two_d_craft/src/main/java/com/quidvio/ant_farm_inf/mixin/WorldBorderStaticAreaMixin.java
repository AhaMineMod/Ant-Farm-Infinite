package com.quidvio.ant_farm_inf.mixin;

import net.minecraft.world.border.WorldBorder;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(WorldBorder.StaticArea.class)
public class WorldBorderStaticAreaMixin {
    @Inject(method = "getBoundEast",at = @At("RETURN"),cancellable = true)
    private void getBoundEast(CallbackInfoReturnable<Double> cir) {
        cir.setReturnValue(16.0);
    }

    @Inject(method = "getBoundWest",at = @At("RETURN"),cancellable = true)
    private void getBoundWest(CallbackInfoReturnable<Double> cir) {
        cir.setReturnValue(0.0);
    }
}
