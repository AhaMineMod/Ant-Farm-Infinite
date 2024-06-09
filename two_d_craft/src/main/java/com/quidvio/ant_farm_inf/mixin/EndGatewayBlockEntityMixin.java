package com.quidvio.ant_farm_inf.mixin;

import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.entity.EndGatewayBlockEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.BlockView;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import static com.quidvio.ant_farm_inf.Ant_farm_inf_main.getIsAntFarmInf;
import static com.quidvio.ant_farm_inf.Ant_farm_inf_main.setIsExitGateway;

@Mixin(EndGatewayBlockEntity.class)
public class EndGatewayBlockEntityMixin {
    @Inject(method = "findTeleportLocation", at = @At("RETURN"), cancellable = true)
    private static void fixEndGatewayTeleportPos(
        ServerWorld world,
        BlockPos pos,
        CallbackInfoReturnable<Vec3d> cir
    ) {
        if (getIsAntFarmInf()) {
            Vec3d vec3d = cir.getReturnValue();
            cir.setReturnValue(new Vec3d(0, vec3d.y, vec3d.z));
        }
    }

    @Inject(method = "findExitPortalPos", at = @At("RETURN"), cancellable = true)
    private static void fixFindExitPortalPos(
        BlockView world, BlockPos pos,
        int searchRadius, boolean force,
        CallbackInfoReturnable<BlockPos> cir
    ) {
        if (getIsAntFarmInf()) {
            setIsExitGateway(true);
            BlockPos blockPos = cir.getReturnValue();
            for (int k = world.getTopY() - 1; k > world.getBottomY(); --k) {
                BlockPos blockPos2 = new BlockPos(8, k, pos.getZ());
                BlockState blockState = world.getBlockState(blockPos2);
                if (blockState.isFullCube(world, blockPos2) && (force || !blockState.isOf(Blocks.BEDROCK))) {
                    blockPos = blockPos2;
                    break;
                }
            }
            cir.setReturnValue(blockPos);
        }
    }
}
