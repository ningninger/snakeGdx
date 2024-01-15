package org.example;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import org.example.screen.StartScreen;

public class MyGdxGame extends Game {

    @Override
    public void create() {
        Screen screen = new StartScreen(this);
        setScreen(screen);
        Gdx.graphics.setForegroundFPS(8);
    }

    @Override
    public void resize(int width, int height) {
        super.resize(width, height);
    }

    @Override
    public void render() {
        super.render();
    }

    @Override
    public void dispose() {

    }
}
