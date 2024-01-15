package org.example.serialization;

import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonWriter;
import org.example.map.GameMap;
import org.example.screen.PlayerScreen;
import org.example.utils.MockWorld;
import org.example.world.World;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.StringWriter;

class WorldDataTest {


    private WorldData worldData;

    @BeforeEach
    void setUp() {
        worldData = new WorldData();
    }


    @Test
    void writeAndRead() {
        World world = new MockWorld();
        World.setWorld(world);
        GameMap gameMap = world.getMap();
        PlayerScreen playerScreen = new PlayerScreen(null);
        world.setScreen(playerScreen);
        world.initWorld(2);
        world.updateWorld();
        worldData = new WorldData(world);

        Json json = new Json();

        json.setOutputType(JsonWriter.OutputType.json);
        json.setWriter(new JsonWriter(new StringWriter()));

        String jsonString = json.toJson(worldData);

        WorldData oldWorldData = json.fromJson(WorldData.class, jsonString);

        oldWorldData.loadTileEntities(gameMap);

        oldWorldData.getActiveCookies();
        oldWorldData.getActivePlayers();
        oldWorldData.getActiveSnakeBodies();
        oldWorldData.getActiveSnakeHeads();
        oldWorldData.getDifficulty();
        oldWorldData.getElapsedTime();
    }


}