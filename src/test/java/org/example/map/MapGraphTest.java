package org.example.map;

import com.badlogic.gdx.ai.pfa.Connection;
import com.badlogic.gdx.utils.Array;
import org.example.utils.MockWorld;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class MapGraphTest {

    private GameMap gameMap;
    private MapGraph mapGraph;

    @BeforeEach
    void setUp() {
        gameMap = new GameMap(new MockWorld());
        mapGraph = new MapGraph(gameMap);
    }

    @Test
    void getIndex() {
        Tile tile = gameMap.getTile(2, 3);
        int expectedIndex = tile.getIndex();

        int actualIndex = mapGraph.getIndex(tile);

        assertEquals(expectedIndex, actualIndex);
    }

    @Test
    void getNodeCount() {
        int expectedNodeCount = gameMap.getWidth() * gameMap.getHeight();
        int actualNodeCount = mapGraph.getNodeCount();

        assertEquals(expectedNodeCount, actualNodeCount);
    }

    @Test
    void getConnections() {
        Tile centralTile = gameMap.getTile(2, 3);
        Array<Connection<Tile>> connections = mapGraph.getConnections(centralTile);

        assertNotNull(connections);
    }
}