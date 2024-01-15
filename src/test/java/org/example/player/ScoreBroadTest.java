package org.example.player;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ScoreBroadTest {

    @Test
    void createAndGet() {
        ScoreBroad scoreBroad = new ScoreBroad(100, 120, 200);

        assertEquals(100, scoreBroad.getScore());
        assertEquals(120, scoreBroad.getRemainingTime());
        assertEquals(200, scoreBroad.getGoal());
    }
}