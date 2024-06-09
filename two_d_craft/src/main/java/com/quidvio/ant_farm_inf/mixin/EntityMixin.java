package com.quidvio.ant_farm_inf.mixin;

import com.quidvio.ant_farm_inf.ChunkGenerator2D;
import net.minecraft.entity.Entity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.TeleportTarget;
import net.minecraft.world.dimension.DimensionTypes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Entity.class)
public class EntityMixin {
    @Shadow
    public Vec3d getVelocity() {
        return null;
    }

    @Shadow
    public float getYaw() {
        return 0;
    }

    @Shadow
    public float getPitch() {
        return 0;
    }


    @Inject(method = "getTeleportTarget", at = @At("RETURN"), cancellable = true)
    private void fixEndSpawnPos(ServerWorld destination, CallbackInfoReturnable<TeleportTarget> cir) {
        if (destination.getChunkManager().getChunkGenerator() instanceof ChunkGenerator2D
            && destination.getDimensionKey().getValue().equals(DimensionTypes.THE_END_ID)
        ) {
            TeleportTarget teleportTarget = new TeleportTarget(
                new Vec3d(8.5, 50, 100.5), this.getVelocity(), this.getYaw(), this.getPitch());
            cir.setReturnValue(teleportTarget);
        }
    }
}
