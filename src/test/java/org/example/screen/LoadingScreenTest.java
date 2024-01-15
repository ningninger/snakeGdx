package org.example.screen;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class LoadingScreenTest {

    LoadingScreen loadingScreen;
    @BeforeEach
    void setUp() {
        loadingScreen = new LoadingScreen(null, true);
    }

    @Test
    void dispose() {
        loadingScreen.dispose();
    }
}