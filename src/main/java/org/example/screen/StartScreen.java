package org.example.screen;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

public class StartScreen extends ScreenAdapter {

    private final Game game;
    private Stage stage;
    private Skin skin;
    private Music backgroundMusic;

    public StartScreen(Game game) {
        this.game = game;
    }

    @Override
    public void show() {

        backgroundMusic = Gdx.audio.newMusic(Gdx.files.internal("music/start.mp3"));
        backgroundMusic.setLooping(true);
        backgroundMusic.play();

        stage = new Stage();
        Gdx.input.setInputProcessor(stage);

        Texture backgroundTexture = new Texture(Gdx.files.internal("img/screen.jpg"));
        Image background = new Image(backgroundTexture);
        background.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        stage.addActor(background);

        skin = new Skin(Gdx.files.internal("assets/comic/skin/comic-ui.json"));
        TextButton.TextButtonStyle buttonStyle = skin.get("default", TextButton.TextButtonStyle.class);

        TextButton startButton = new TextButton("Start Game", buttonStyle);
        startButton.setColor(Color.WHITE);
        startButton.setWidth(Gdx.graphics.getWidth() * 0.3f);
        startButton.setHeight(Gdx.graphics.getHeight() * 0.15f);
        startButton.getLabel().setFontScale(1.5f);
        startButton.setPosition(Gdx.graphics.getWidth() / 2f - startButton.getWidth() / 2f, Gdx.graphics.getHeight() * 0.4f);
        stage.addActor(startButton);

        TextButton continueButton = new TextButton("Continue Game", buttonStyle);
        continueButton.setColor(Color.WHITE);
        continueButton.setWidth(Gdx.graphics.getWidth() * 0.3f);
        continueButton.setHeight(Gdx.graphics.getHeight() * 0.15f);
        continueButton.getLabel().setFontScale(1.5f);
        continueButton.setPosition(Gdx.graphics.getWidth() / 2f - continueButton.getWidth() / 2f, Gdx.graphics.getHeight() * 0.2310f);
        stage.addActor(continueButton);

        TextButton exitButton = new TextButton("Exit Game", buttonStyle);
        exitButton.setColor(Color.WHITE);
        exitButton.setWidth(Gdx.graphics.getWidth() * 0.3f);
        exitButton.setHeight(Gdx.graphics.getHeight() * 0.15f);
        exitButton.getLabel().setFontScale(1.5f);
        exitButton.setPosition(Gdx.graphics.getWidth() / 2f - exitButton.getWidth() / 2f, Gdx.graphics.getHeight() * 0.0618f);
        stage.addActor(exitButton);

        startButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                backgroundMusic.stop();
                game.setScreen(new LoadingScreen(game, false));
            }
        });

        continueButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                backgroundMusic.stop();
                game.setScreen(new LoadingScreen(game, true));
            }
        });

        exitButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                backgroundMusic.stop();
                game.setScreen(new EndingScreen(game));
            }
        });
    }

    @Override
    public void render(float v) {
        Gdx.gl.glClearColor(0.2f, 0.2f, 0.2f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
    }

    @Override
    public void dispose() {
        if (stage != null) {
            stage.dispose();
        }

        if (skin != null) {
            skin.dispose();
        }

        if (backgroundMusic != null) {
            backgroundMusic.stop();
            backgroundMusic.dispose();
        }
    }
}
