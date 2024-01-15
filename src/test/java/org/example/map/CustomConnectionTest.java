package org.example.map;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class CustomConnectionTest {

    @Test
    void testCustomConnectionCreation() {
        Tile fromNode = new Tile(0, 0);
        Tile toNode = new Tile(1, 1);
        float cost = 1.0f;

        CustomConnection customConnection = new CustomConnection(fromNode, toNode, cost);

        assertNotNull(customConnection);

        assertEquals(fromNode, customConnection.getFromNode());
        assertEquals(toNode, customConnection.getToNode());

        assertEquals(cost, customConnection.getCost());


    }

    @Test
    void testGetCost() {
        Tile fromNode = new Tile(0, 0);
        Tile toNode = new Tile(1, 1);
        float cost = 1.0f;

        CustomConnection customConnection = new CustomConnection(fromNode, toNode, cost);

        assertEquals(cost, customConnection.getCost());

    }

    @Test
    void testGetFromNode() {
        Tile fromNode = new Tile(0, 0);
        Tile toNode = new Tile(1, 1);
        float cost = 1.0f;

        CustomConnection customConnection = new CustomConnection(fromNode, toNode, cost);

        assertEquals(fromNode, customConnection.getFromNode());

    }

    @Test
    void testGetToNode() {
        Tile fromNode = new Tile(0, 0);
        Tile toNode = new Tile(1, 1);
        float cost = 1.0f;

        CustomConnection customConnection = new CustomConnection(fromNode, toNode, cost);

        assertEquals(toNode, customConnection.getToNode());

    }


}