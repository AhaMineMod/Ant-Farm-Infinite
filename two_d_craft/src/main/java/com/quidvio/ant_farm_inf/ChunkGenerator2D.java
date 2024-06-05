//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.quidvio.ant_farm_inf;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.block.Blocks;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.*;
import net.minecraft.world.biome.source.BiomeAccess;
import net.minecraft.world.biome.source.BiomeSource;
import net.minecraft.world.chunk.*;
import net.minecraft.world.gen.GenerationStep;
import net.minecraft.world.gen.StructureAccessor;
import net.minecraft.world.gen.chunk.*;
import net.minecraft.world.gen.noise.NoiseConfig;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;

public class ChunkGenerator2D extends NoiseChunkGenerator  {

    public static final Codec<ChunkGenerator2D> CODEC = RecordCodecBuilder.create((instance) -> instance.group(
            BiomeSource.CODEC.fieldOf("biome_source").forGetter(ChunkGenerator2D::getBiomeSource),
            ChunkGeneratorSettings.REGISTRY_CODEC.fieldOf("settings").forGetter(ChunkGenerator2D::getSettings))
            .apply(instance, instance.stable(ChunkGenerator2D::new)));


    private static final int maxChunkDistFromXAxis = 0; // The max distance, in chunks, the world will generate away from the x-axis.
    private static final int structureChunkDistanceFlexibility = 2; // The max distance, in chunks, the world will generate structures outside of the region.

    /**
     * Constructor
     * Added defaultGen setter and field for utility.
     *
     * @param biomeSource
     * @param settings
     */
    public ChunkGenerator2D(BiomeSource biomeSource, RegistryEntry<ChunkGeneratorSettings> settings) {
        super(biomeSource, settings);
    }

    /**
     * Needed for Inheritance.
     *
     * @return
     */
    @Override
    protected Codec<? extends ChunkGenerator> getCodec() {
        return CODEC;
    }

    /**
     * Uses horizontal limiting (chunkPos < maxDist)
     *
     * @param region
     * @param structures
     * @param noiseConfig
     * @param chunk
     */
    @Override
    public void buildSurface(ChunkRegion region, StructureAccessor structures, NoiseConfig noiseConfig, Chunk chunk) {
        if (Math.abs(chunk.getPos().x) <= maxChunkDistFromXAxis)
            super.buildSurface(region, structures, noiseConfig, chunk);
//        else if (Math.abs(chunk.getPos().x) <= maxChunkDistFromXAxis + 1) {
//            fillChunkWithBARRIER(chunk);
//        }
    }

    private static void fillChunkWithBARRIER(Chunk chunk) {
        final BlockPos.Mutable mutable = new BlockPos.Mutable();
        for (int x = 0; x < 16; x++) {
            for (int z = 0; z < 16; z++) {
                for (int y = chunk.getBottomY(); y < chunk.getHeight() ; y++) {
                    chunk.setBlockState(mutable.set(x, y, z), Blocks.BARRIER.getDefaultState(), false);
                }
            }
        }
    }

    @Override
    public void carve(ChunkRegion chunkRegion, long seed, NoiseConfig noiseConfig, BiomeAccess biomeAccess, StructureAccessor structureAccessor, Chunk chunk, GenerationStep.Carver carverStep) {
        if (Math.abs(chunk.getPos().x) <= maxChunkDistFromXAxis)
            super.carve(chunkRegion, seed, noiseConfig, biomeAccess, structureAccessor, chunk, carverStep);
    }


    @Override
    public CompletableFuture<Chunk> populateNoise(Executor executor, Blender blender, NoiseConfig noiseConfig, StructureAccessor structureAccessor, Chunk chunk) {
        if (Math.abs(chunk.getPos().x) <= maxChunkDistFromXAxis)
            return super.populateNoise(executor, blender, noiseConfig, structureAccessor, chunk);
        return CompletableFuture.completedFuture(chunk);
    }

    @Override
    public void generateFeatures(StructureWorldAccess world, Chunk chunk, StructureAccessor structureAccessor) {
        if(Math.abs(chunk.getPos().x) <= maxChunkDistFromXAxis)
            super.generateFeatures(world, chunk, structureAccessor);
        else if (Math.abs(chunk.getPos().x) <= maxChunkDistFromXAxis + 1) {
            fillChunkWithBARRIER(chunk);
        }
    }
}