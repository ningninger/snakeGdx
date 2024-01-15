package org.example.map;

import org.example.utils.MockWorld;
import org.example.world.World;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class GameMapTest {

    private World world;
    private GameMap gameMap;

    @BeforeEach
    void setUp() {
        world = new MockWorld();
        gameMap = new GameMap(world);
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void setWorld() {
        gameMap.setWorld(world);
    }

    @Test
    void render() {
        gameMap.render(null);
    }

    @Test
    void getWidth() {
        assertNotEquals(0, gameMap.getWidth());
    }

    @Test
    void getHeight() {
        assertNotEquals(0, gameMap.getHeight());
    }

    @Test
    void getTileType() {
        assertEquals(EntityType.Blank, gameMap.getTileType(0, 0));
    }

    @Test
    void getTile() {
        assertNotEquals(null, gameMap.getTile(0, 0));
    }

    @Test
    void isPassable() {
        assertTrue(gameMap.isPassable(gameMap.getTile(0, 0), gameMap.getTile(0, 0)));
    }

    @Test
    void getBlankTile() {
        assertNotEquals(null, gameMap.getBlankTile());
    }

}