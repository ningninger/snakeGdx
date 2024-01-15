package org.example.screen;

import org.example.MyInputProcessor;
import org.example.map.GameMap;
import org.example.utils.MockWorld;
import org.example.world.World;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class PlayerScreenTest {

    private PlayerScreen playerScreen;

    @BeforeEach
    void setUp() {
        playerScreen = new PlayerScreen(null);
    }

    @Test
    void togglePauseGame() {

        playerScreen.togglePauseGame();
    }

    @Test
    void render() {
        World world = new MockWorld();
        World.setWorld(world);
        GameMap map = new GameMap(world);
        world.setScreen(playerScreen);
        world.initWorld(2);
        playerScreen.setWorld(world);
        playerScreen.render(123);
    }

    @Test
    void addInputProcessor() {
        playerScreen.addInputProcessor(new MyInputProcessor());
    }

    @Test
    void removeInputProcessor() {
        playerScreen.addInputProcessor(new MyInputProcessor());
    }

    @Test
    void dispose() {
        playerScreen.dispose();
    }
}