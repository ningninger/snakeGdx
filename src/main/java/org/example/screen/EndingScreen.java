package org.example.screen;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Timer;

public class EndingScreen extends ScreenAdapter {

    private Stage stage;

    private Skin skin;

    private final Game game;

    private Label titleLabel;
    private int numDots;

    private float elapsedTime;

    public EndingScreen(Game game) {
        this.game = game;
        elapsedTime = 0;

        if (game != null) {
            Timer.schedule(new Timer.Task() {
                @Override
                public void run() {
                    updateLoadingText();
                }
            }, 1, 1);
        }
    }

    @Override
    public void show() {
        stage = new Stage();
        Gdx.input.setInputProcessor(stage);

        Texture backgroundTexture = new Texture(Gdx.files.internal("img/ending.png"));
        Image background = new Image(backgroundTexture);
        background.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        stage.addActor(background);

        skin = new Skin(Gdx.files.internal("assets/comic/skin/comic-ui.json"));

        Label.LabelStyle labelStyle = skin.get("big", Label.LabelStyle.class);
        titleLabel = new Label("The game will close in a few seconds", labelStyle);
        titleLabel.setFontScale(1.5f);
        titleLabel.setAlignment(Align.center);
        titleLabel.setPosition(Gdx.graphics.getWidth() / 2f - titleLabel.getWidth() / 2f, Gdx.graphics.getHeight() * 0.3f);
        stage.addActor(titleLabel);

    }

    @Override
    public void render(float v) {

        Gdx.gl.glClearColor(0.2f, 0.2f, 0.2f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
        stage.draw();

        elapsedTime += v;

        if (elapsedTime > 8) {
            Gdx.app.exit();
        }
    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
    }

    @Override
    public void dispose() {
        if (skin != null) {
            skin.dispose();
        }
    }

    public void updateLoadingText() {
        numDots = (numDots + 1) % 4;

        StringBuilder loadingText = new StringBuilder("The game will close in a few seconds");
        for (int i = 0; i < numDots; i++) {
            loadingText.append(".");
        }

        if (titleLabel != null) {
            titleLabel.setText(loadingText);
        }
    }
}
