package com.quidvio.ant_farm_inf.mixin;

import com.quidvio.ant_farm_inf.ChunkGenerator2D;
import net.minecraft.block.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.StructureWorldAccess;
import net.minecraft.world.gen.feature.EndGatewayFeature;
import net.minecraft.world.gen.feature.EndGatewayFeatureConfig;
import net.minecraft.world.gen.feature.util.FeatureContext;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

import static com.quidvio.ant_farm_inf.Ant_farm_inf_main.getIsExitGateway;
import static com.quidvio.ant_farm_inf.Ant_farm_inf_main.setIsExitGateway;

@Mixin(EndGatewayFeature.class)
public class EndGatewayFeatureMixin {
    @ModifyVariable(
        method = "generate",
        at = @At(value = "HEAD"),
        argsOnly = true
    )
    private FeatureContext<EndGatewayFeatureConfig> fixEndPortalGeneratePos(
        FeatureContext<EndGatewayFeatureConfig> context
    ) {
        if (context.getGenerator() instanceof ChunkGenerator2D) {
            BlockPos gatewayPos1 = new BlockPos(8, 75, 73);
            BlockPos gatewayPos2 = new BlockPos(8, 75, -52);
            StructureWorldAccess world = context.getWorld();

            FeatureContext<EndGatewayFeatureConfig> newContext;
            if (getIsExitGateway()) {
                setIsExitGateway(false);
                return context;
            } else if (world.getBlockState(gatewayPos1).getBlock().equals(Blocks.END_GATEWAY)) {
                newContext = new FeatureContext<>(
                    context.getFeature(), context.getWorld(),
                    context.getGenerator(), context.getRandom(),
                    gatewayPos2, EndGatewayFeatureConfig.createConfig()
                );
                return newContext;
            } else if (world.getBlockState(gatewayPos2).getBlock().equals(Blocks.END_GATEWAY)) {
                newContext = new FeatureContext<>(
                    context.getFeature(), context.getWorld(),
                    context.getGenerator(), context.getRandom(),
                    gatewayPos1, EndGatewayFeatureConfig.createConfig()
                );
                return newContext;
            }
        }
        return context;
    }
}
