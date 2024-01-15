package org.example.map;

import org.example.utils.MockMapEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class MapEntityTest {

    MockMapEntity mapEntity;

    @BeforeEach
    void setUp() {
        mapEntity = new MockMapEntity();
    }

    @Test
    void setX() {
        int newX = 42;
        mapEntity.setX(newX);

        assertEquals(newX, mapEntity.getX());
    }

    @Test
    void getX() {
        int x = mapEntity.getX();

        assertEquals(0, x);
    }

    @Test
    void setY() {
        int newY = 42;
        mapEntity.setY(newY);

        assertEquals(newY, mapEntity.getY());
    }

    @Test
    void getY() {
        int y = mapEntity.getY();

        assertEquals(0, y);
    }

    @Test
    void setPosition() {
        int newX = 42;
        int newY = 42;
        mapEntity.setPosition(newX, newY);

        assertEquals(newX, mapEntity.getX());
        assertEquals(newY, mapEntity.getY());
    }
}