package org.example.serialization;

import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonWriter;
import org.example.map.GameMap;
import org.example.utils.MockWorld;
import org.example.world.World;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.StringWriter;

class MapDataTest {

    private MapData mapData;

    @BeforeEach
    void setUp() {
        mapData = new MapData();
    }


    @Test
    void writeAndRead() {
        World world = new MockWorld();
        GameMap map = new GameMap(world);
        mapData = new MapData(map.getTiles(), map.getWallSize());

        Json json = new Json();

        json.setOutputType(JsonWriter.OutputType.json);
        json.setWriter(new JsonWriter(new StringWriter()));

        String jsonString = json.toJson(mapData);

        MapData oldMapData = json.fromJson(MapData.class, jsonString);
    }


}