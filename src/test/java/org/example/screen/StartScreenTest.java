package org.example.screen;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class StartScreenTest {

    private StartScreen startScreen;

    @BeforeEach
    void setUp() {
        startScreen = new StartScreen(null);
    }

    @Test
    void dispose() {
        startScreen.dispose();
    }
}