package com.quidvio.ant_farm_inf;

import net.fabricmc.api.ModInitializer;
import net.minecraft.registry.*;
import net.minecraft.util.Identifier;

public class Ant_farm_inf_main implements ModInitializer {

    @Override
    public void onInitialize() {
        Registry.register(Registries.CHUNK_GENERATOR, new Identifier("ant_farm_inf", "ant_farm_gen"), ChunkGenerator2D.CODEC);
    }
}
