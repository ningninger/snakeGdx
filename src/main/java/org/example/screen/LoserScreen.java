package org.example.screen;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.Timer;
import org.example.player.ScoreBroad;

public class LoserScreen extends ScreenAdapter {

    private float CENTER_X;
    private float SCREEN_HEIGHT;
    private float TITLE_Y;
    private float SCORE_Y;
    private float TIME_LEFT_Y;
    private float GOAL_Y;

    private final Game game;
    private SpriteBatch batch;
    private ShapeRenderer shapeRenderer;

    private Music backgroundMusic;

    private BitmapFont font;
    private final ScoreBroad broad;
    private Skin skin;

    private Texture backgroundTexture;

    public LoserScreen(Game game, ScoreBroad broad) {
        this.game = game;
        this.broad = broad;

        if (game != null) {

            this.batch = new SpriteBatch();
            this.shapeRenderer = new ShapeRenderer();
            skin = new Skin(Gdx.files.internal("assets/comic/skin/comic-ui.json"));
            backgroundTexture = new Texture(Gdx.files.internal("img/board.jpg"));


            CENTER_X = Gdx.graphics.getWidth() * 0.375f;
            SCREEN_HEIGHT = Gdx.graphics.getHeight();
            TITLE_Y = SCREEN_HEIGHT * 0.70f;
            SCORE_Y = SCREEN_HEIGHT * 0.55f;
            TIME_LEFT_Y = SCREEN_HEIGHT * 0.45f;
            GOAL_Y = SCREEN_HEIGHT * 0.35f;


            Label.LabelStyle labelStyle = skin.get("title", Label.LabelStyle.class);
            font = labelStyle.font;
            Gdx.app.log("loser screen", "enter loser screen");


            Timer.schedule(new Timer.Task() {
                @Override
                public void run() {
                    backgroundMusic.stop();
                    game.setScreen(new StartScreen(game));
                }
            }, 3);
        }
    }

    @Override
    public void show() {
        backgroundMusic = Gdx.audio.newMusic(Gdx.files.internal("music/lose.mp3"));
        backgroundMusic.setVolume(100f);
        backgroundMusic.setLooping(false);
        backgroundMusic.play();
    }

    @Override
    public void render(float delta) {
        float x = Gdx.graphics.getWidth() * 0.25f;
        float y = Gdx.graphics.getHeight() * 0.25f;
        float width = Gdx.graphics.getWidth() * 0.5f;
        float height = Gdx.graphics.getHeight() * 0.5f;

        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.begin();
        batch.end();

        Gdx.gl.glEnable(GL20.GL_BLEND);
        shapeRenderer.setProjectionMatrix(batch.getProjectionMatrix());
        shapeRenderer.setTransformMatrix(batch.getTransformMatrix());

        Color boxColor = skin.getColor("gray");
        float boxAlpha = 0.75f;

        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(boxColor.r, boxColor.g, boxColor.b, boxAlpha);
        shapeRenderer.rect(x, y, width, height);
        shapeRenderer.end();

        Color borderColor = skin.getColor("black");
        float borderAlpha = 1f;
        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
        shapeRenderer.setColor(borderColor.r, borderColor.g, borderColor.b, borderAlpha);

        float borderWidth = 16.0f;
        float borderX = x - borderWidth / 2;
        float borderY = y - borderWidth / 2;
        float borderWidthWithWidth = width + borderWidth;
        float borderHeightWithHeight = height + borderWidth;

        shapeRenderer.rect(borderX, borderY, borderWidthWithWidth, borderHeightWithHeight);

        shapeRenderer.end();

        Gdx.gl.glDisable(GL20.GL_BLEND);

        batch.begin();

        setupUI();

        batch.end();
    }


    public void setupUI() {
        if (font == null) {
            font = new BitmapFont();
        }
        font.getData().setScale(2);
        font.draw(batch, "Failed!!!", CENTER_X * 0.95f, TITLE_Y);
        font.getData().setScale(1.2f);
        font.draw(batch, "Score: " + broad.getScore(), CENTER_X, SCORE_Y);
        font.getData().setScale(1.2f);
        font.draw(batch, "Time Left: " + broad.getRemainingTime(), CENTER_X, TIME_LEFT_Y);
        font.getData().setScale(1.2f);
        font.draw(batch, "Your Goal: " + broad.getGoal(), CENTER_X, GOAL_Y);
    }

    @Override
    public void dispose() {
        if (skin != null) {
            skin.dispose();
        }

        if (batch != null) {
            batch.dispose();
        }

        if (shapeRenderer != null) {
            shapeRenderer.dispose();
        }

        if (font != null) {
            font.dispose();
        }

        if (backgroundMusic != null) {
            backgroundMusic.stop();
            backgroundMusic.dispose();
        }
    }
}
