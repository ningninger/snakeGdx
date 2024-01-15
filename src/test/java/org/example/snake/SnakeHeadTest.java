package org.example.snake;

import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonWriter;
import org.example.map.GameMap;
import org.example.map.Tile;
import org.example.player.Player;
import org.example.utils.MockWorld;
import org.example.world.World;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.StringWriter;

class SnakeHeadTest {

    private SnakeHead snakeHead;

    private World world;

    @BeforeEach
    void setUp() {
        world = new MockWorld();
        World.setWorld(world);
        GameMap gameMap = new GameMap(world);

        snakeHead = world.obtainSnakeHead();
        snakeHead.setWorld(world);
    }

    @Test
    void move() {
        Tile tile = world.getMap().getBlankTile();
        snakeHead.move(tile);
    }

    @Test
    void getDirection() {
        Direction direction = snakeHead.getDirection();
    }

    @Test
    void render() {
        snakeHead.render(null, Player.PlayerType.AI);
    }

    @Test
    void toCookie() {
        snakeHead.toCookie();
    }

    @Test
    void writeAndRead() {
        Json json = new Json();

        json.setOutputType(JsonWriter.OutputType.json);
        json.setWriter(new JsonWriter(new StringWriter()));

        String jsonString = json.toJson(snakeHead);

        SnakeHead oldSnakeHead = json.fromJson(SnakeHead.class, jsonString);
    }
}