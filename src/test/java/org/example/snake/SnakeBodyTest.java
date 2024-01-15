package org.example.snake;

import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonWriter;
import org.example.player.Player;
import org.example.utils.MockWorld;
import org.example.world.World;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.StringWriter;

import static org.junit.jupiter.api.Assertions.assertNull;

class SnakeBodyTest {

    private SnakeBody snakeBody;
    private World world;

    @BeforeEach
    void setUp() {
        snakeBody = new SnakeBody();
        world = new MockWorld();
        World.setWorld(world);
    }

    @Test
    void init() {
        snakeBody.init();
        assertNull(snakeBody.getTile());
        assertNull(snakeBody.getEntityState());
    }

    @Test
    void update() {
        snakeBody.update();
    }

    @Test
    void render() {
        snakeBody.render(null, Player.PlayerType.AI);
    }

    @Test
    void toCookie() {
        snakeBody.setWorld(world);
        snakeBody.toCookie();
    }

    @Test
    void writeAndRead() {
        Json json = new Json();

        json.setOutputType(JsonWriter.OutputType.json);
        json.setWriter(new JsonWriter(new StringWriter()));

        String jsonString = json.toJson(snakeBody);

        SnakeBody oldSnakeBody = json.fromJson(SnakeBody.class, jsonString);
    }


}