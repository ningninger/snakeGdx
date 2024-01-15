package org.example.map;

import com.badlogic.gdx.utils.Array;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class MapGraphPathTest {

    private MapGraphPath mapGraphPath;

    @BeforeEach
    void setUp() {
        mapGraphPath = new MapGraphPath();
    }

    @Test
    void getCount() {
        int expectedCount = 0;

        int actualCount = mapGraphPath.getCount();

        assertEquals(expectedCount, actualCount);
    }

    @Test
    void add() {
        Tile tile = new Tile(2, 3);

        mapGraphPath.add(tile);

        int expectedCount = 1;
        int actualCount = mapGraphPath.getCount();
        assertEquals(expectedCount, actualCount);

        Tile actualTile = mapGraphPath.get(0);
        assertEquals(tile, actualTile);
    }

    @Test
    void clear() {
        mapGraphPath.add(new Tile(2, 3));
        mapGraphPath.add(new Tile(4, 5));

        mapGraphPath.clear();

        int expectedCount = 0;
        int actualCount = mapGraphPath.getCount();
        assertEquals(expectedCount, actualCount);
    }

    @Test
    void iterator() {
        mapGraphPath.add(new Tile(2, 3));
        mapGraphPath.add(new Tile(4, 5));

        Array<Tile> tiles = new Array<>();
        for (Tile tile : mapGraphPath) {
            tiles.add(tile);
        }

        assertNotNull(tiles);
        assertEquals(mapGraphPath.getCount(), tiles.size);
    }
}