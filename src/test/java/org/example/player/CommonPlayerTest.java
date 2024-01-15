package org.example.player;

import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonWriter;
import org.example.map.GameMap;
import org.example.screen.PlayerScreen;
import org.example.utils.MockWorld;
import org.example.world.World;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.StringWriter;

class CommonPlayerTest {

    private CommonPlayer commonPlayer;
    private World world;

    private GameMap map;

    @BeforeEach
    void setUp() {
        world = new MockWorld();
        World.setWorld(world);
        PlayerScreen mockScreen = new PlayerScreen(null);
        PlayerScreen mockScreen2 = new PlayerScreen(null, 2);
        world.setScreen(mockScreen);
        world.initWorld(2);
        map = new GameMap(world);
        commonPlayer = (CommonPlayer) world.getActivePlayers().get(0);
    }

    @Test
    void writeAndRead() {
        commonPlayer.update();
        Json json = new Json();
        json.setOutputType(JsonWriter.OutputType.json);
        json.setWriter(new JsonWriter(new StringWriter()));
        String s = json.toJson(commonPlayer);
        CommonPlayer newCommonPlayer = json.fromJson(CommonPlayer.class, s);
    }


}