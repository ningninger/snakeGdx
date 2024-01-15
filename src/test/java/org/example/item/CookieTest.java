package org.example.item;

import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonWriter;
import org.example.utils.MockWorld;
import org.example.world.World;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.StringWriter;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

class CookieTest {

    private Cookie cookie;

    private World world;

    @BeforeEach
    void setUp() {
        world = new MockWorld();
        cookie = world.obtainCookie();
    }

    @Test
    void init() {
        cookie.init();
    }

    @Test
    void setWorld() {
        cookie.setWorld(null);
    }

    @Test
    void setEaten() {
        cookie.setEaten(true);
    }

    @Test
    void isEaten() {
        boolean isEaten = cookie.isEaten();
    }

    @Test
    void update() {
        cookie.update();
        setEaten();
        cookie.update();
    }

    @Test
    void render() {
        cookie.render(null);
    }

    @Test
    void setInitialPosition() {
        cookie.setInitialPosition(0, 0);
    }

    @Test
    void dispose() {
        cookie.dispose();
        assertDoesNotThrow(() -> cookie.dispose());
    }

    @Test
    void writeAndRead() {
        cookie.update();
        Json json = new Json();
        json.setOutputType(JsonWriter.OutputType.json);
        json.setWriter(new JsonWriter(new StringWriter()));
        String s = json.toJson(cookie);
        Cookie newCookie = json.fromJson(Cookie.class, s);
    }
}