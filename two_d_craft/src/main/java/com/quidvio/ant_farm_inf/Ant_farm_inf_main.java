package com.quidvio.ant_farm_inf;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
import net.minecraft.registry.*;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.Identifier;

import java.util.ArrayList;

public class Ant_farm_inf_main implements ModInitializer {
    public static ArrayList<ServerWorld> world = new ArrayList<>();
    public static boolean isExiteGateway = false;

    @Override
    public void onInitialize() {
        Registry.register(Registries.CHUNK_GENERATOR, new Identifier("ant_farm_inf", "ant_farm_gen"), ChunkGenerator2D.CODEC);

        ServerLifecycleEvents.SERVER_STARTED.register(server -> {
            ArrayList<ServerWorld> serverWorlds = new ArrayList<>();
            server.getWorlds().forEach(serverWorlds::add);
            setWorld(serverWorlds);
        });
    }

    public static void setWorld(ArrayList<ServerWorld> value) {
        world = value;
    }

    public static boolean getIsAntFarmInf() {
        return world.stream()
            .allMatch(
                world -> world.getChunkManager().getChunkGenerator() instanceof ChunkGenerator2D
            );
    }

    public static void setIsExitGateway(boolean value) {
        isExiteGateway = value;
    }

    public static boolean getIsExitGateway() {
        return isExiteGateway;
    }
}
