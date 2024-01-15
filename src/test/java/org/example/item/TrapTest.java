package org.example.item;

import org.example.map.EntityType;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class TrapTest {
    @Test
    void testTrapCreation() {
        Trap trap = new Trap();

        assertNotNull(trap);

        assertEquals(EntityType.Trap, trap.getType());


    }

}