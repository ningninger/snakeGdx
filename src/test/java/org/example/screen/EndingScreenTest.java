package org.example.screen;

import com.badlogic.gdx.Game;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class EndingScreenTest {

    private EndingScreen endingScreen;

    private Game mockGame;

    @BeforeEach
    void setUp() {
        endingScreen = new EndingScreen(null);
    }

    @Test
    void updateLoadingText() {
        endingScreen.updateLoadingText();
    }

    @Test
    void dispose() {
        endingScreen.dispose();
    }
}