package org.example.serialization;

import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonValue;
import org.example.map.GameMap;
import org.example.map.Tile;

public class MapData implements Json.Serializable {

    private Tile[][] tiles;

    private int wallSize;

    public MapData() {

    }

    public MapData(Tile[][] tiles, int wallSize) {
        this.tiles = tiles;
        this.wallSize = wallSize;
    }

    @Override
    public void write(Json json) {
        json.writeValue("wallSize", wallSize);
        json.writeValue("tiles", tiles, Tile[][].class);
    }

    @Override
    public void read(Json json, JsonValue jsonValue) {
        wallSize = jsonValue.getInt("wallSize");
        tiles = json.readValue("tiles", Tile[][].class, jsonValue);
    }

    public GameMap toGameMap() {
        return new GameMap(tiles, wallSize);
    }
}
