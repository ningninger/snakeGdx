package org.example.screen;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class LoserScreenTest {

    private LoserScreen loserScreen;

    @BeforeEach
    void setUp() {
        loserScreen = new LoserScreen(null, null);
    }

    @Test
    void dispose() {
        loserScreen.dispose();
    }
}