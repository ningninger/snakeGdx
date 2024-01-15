package org.example;

import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;

public class DesktopLauncher {
    public static void main(String[] args) {
        Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();
        config.setTitle("my-gdx-game");
        config.setWindowedMode(1280, 720);
        //config.setWindowedMode(1920, 1080);
        new Lwjgl3Application(new MyGdxGame(), config);
    }
}
