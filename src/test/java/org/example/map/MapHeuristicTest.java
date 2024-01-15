package org.example.map;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class MapHeuristicTest {

    @Test
    void estimate() {
        MapHeuristic mapHeuristic = new MapHeuristic();

        Tile startNode = new Tile(2, 3);
        Tile endNode = new Tile(5, 7);

        float expectedDistance = Math.abs(startNode.getX() - endNode.getX()) + Math.abs(startNode.getY() - endNode.getY());
        float actualDistance = mapHeuristic.estimate(startNode, endNode);

        assertEquals(expectedDistance, actualDistance, 0.01);
    }
}