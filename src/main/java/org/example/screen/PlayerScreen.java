package org.example.screen;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.FrameBuffer;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import org.example.player.Player;
import org.example.player.ScoreBroad;
import org.example.world.World;

public class PlayerScreen extends ScreenAdapter {

    private final Game game;
    private final InputMultiplexer inputMultiplexer;
    private World world;
    private OrthographicCamera camera;
    private SpriteBatch batch;
    private FrameBuffer frameBuffer;
    private BitmapFont font;
    private Skin skin;

    private Stage stage;

    private TextButton settingButton;

    private TextureRegion gameScreenTextureRegion;

    private boolean isOld = false;

    private boolean isGamePaused = false;

    private Window settingsWindow;

    private Music backgroundMusic;

    private final float totalTime = 300;
    private int currentScore = 0;

    private int goal;

    public PlayerScreen(Game game) {
        this.game = game;
        this.isOld = true;
        this.inputMultiplexer = new InputMultiplexer();
        if (game != null) {

            World.clearWorld();
            World.setOldWorld();
            this.world = World.getWorld();
            this.world.setScreen(this);
            this.goal = (int) Math.pow(2, world.getDifficulty());

            Gdx.input.setInputProcessor(inputMultiplexer);

            for (Player player : world.getActivePlayers()) {
                addInputProcessor(player.getInputProcessor());
            }

            this.camera = new OrthographicCamera();
            this.camera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        }
    }


    public PlayerScreen(Game game, int goal) {
        this.game = game;
        this.isOld = false;
        this.inputMultiplexer = new InputMultiplexer();

        if (game != null) {
            World.clearWorld();
            this.world = World.getWorld();
            this.world.setScreen(this);
            this.goal = goal;

            Gdx.input.setInputProcessor(inputMultiplexer);

            this.camera = new OrthographicCamera();
            this.camera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        }
    }

    public void setWorld(World world) {
        this.world = world;
    }

    @Override
    public void show() {

        backgroundMusic = Gdx.audio.newMusic(Gdx.files.internal("music/play.mp3"));
        backgroundMusic.setLooping(true);
        backgroundMusic.play();


        this.batch = new SpriteBatch();

        stage = new Stage();
        inputMultiplexer.addProcessor(stage);

        this.skin = new Skin(Gdx.files.internal("assets/comic/skin/comic-ui.json"));

        settingButton = new TextButton("Setting", skin, "default");
        settingButton.setColor(Color.WHITE);
        settingButton.getLabel().setFontScale(1f);
        settingButton.setSize(Gdx.graphics.getWidth() * 0.15f, Gdx.graphics.getHeight() * 0.1f);
        settingButton.setPosition(Gdx.graphics.getWidth() * 0.5f - settingButton.getWidth() / 2f, Gdx.graphics.getHeight() * 0.895f);

        settingButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                togglePauseGame();
                showSettingsDialog();
            }
        });

        stage.addActor(settingButton);

        Label.LabelStyle labelStyle = skin.get("big", Label.LabelStyle.class);
        this.font = labelStyle.font;
        this.font.setColor(Color.WHITE);
        this.font.getData().setScale(1.25f);

        this.frameBuffer = new FrameBuffer(Pixmap.Format.RGBA8888, Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), false);


        if (!isOld) {
            world.initWorld(goal);
        }
    }

    public void togglePauseGame() {
        isGamePaused = !isGamePaused;
    }

    private void showSettingsDialog() {

        if (settingsWindow == null) {
            Window.WindowStyle windowStyle = skin.get("default", Window.WindowStyle.class);
            settingsWindow = new Window("", windowStyle);

            settingsWindow.setSize(Gdx.graphics.getWidth() * 0.25f, Gdx.graphics.getHeight() * 0.35f);
            settingsWindow.setPosition(Gdx.graphics.getWidth() / 2f - settingsWindow.getWidth() / 2f, Gdx.graphics.getHeight() / 2f - settingsWindow.getHeight() / 2);
            settingsWindow.setColor(Color.BLACK);

            Label.LabelStyle labelStyle = skin.get("title", Label.LabelStyle.class);
            Label label = new Label("Settings", labelStyle);
            Table titleTable = settingsWindow.getTitleTable();
            label.setAlignment(Align.topLeft);
            label.setFontScale(1.2f);
            titleTable.clear();
            titleTable.row();
            titleTable.add(label).center().row();

            TextButton closeButton = new TextButton("Close", skin);
            TextButton saveButton = new TextButton("Save", skin);
            TextButton backButton = new TextButton("Back", skin);

            Table buttonTable = new Table();

            buttonTable.defaults().padBottom(5f);

            buttonTable.add(closeButton).center().row();
            buttonTable.add(saveButton).center().row();
            buttonTable.add(backButton).center();

            settingsWindow.add(buttonTable).center().padTop(30);

            closeButton.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    settingsWindow.setVisible(false);
                    isGamePaused = !isGamePaused;
                }
            });

            saveButton.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    world.saveWorld();
                    Label.LabelStyle style = skin.get("title", Label.LabelStyle.class);
                    Label successLabel = new Label("Save Successful!!!", style);
                    successLabel.setAlignment(Align.center);
                    successLabel.setFontScale(2f);
                    successLabel.setSize(Gdx.graphics.getWidth() * 0.4f, Gdx.graphics.getHeight() * 0.2f);
                    successLabel.setPosition(Gdx.graphics.getWidth() / 2f - successLabel.getWidth() / 2f, Gdx.graphics.getHeight() / 2f - successLabel.getHeight() / 2f);

                    stage.addActor(successLabel);

                    successLabel.getColor().a = 0f;

                    successLabel.addAction(Actions.sequence(
                            Actions.fadeIn(0.1f),
                            Actions.delay(0f),
                            Actions.fadeOut(0.2f),
                            Actions.run(successLabel::remove)
                    ));
                }

            });

            backButton.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    backgroundMusic.stop();
                    game.setScreen(new StartScreen(game));
                }
            });

            stage.addActor(settingsWindow);
        }
        assert settingsWindow != null;
        settingsWindow.setVisible(true);

    }


    @Override
    public void render(float delta) {
        currentScore = world.getScore();

        if (!isGamePaused) {
            world.updateTime(delta);
            if (((int) world.getElapsedTime()) % 10 == 0) {
                gameScreenTextureRegion = captureScreenAsTextureRegion();
            }
            world.updateWorld();
        }

        if (game != null) {
            Gdx.gl.glClearColor(0, 0, 0, 1);
            Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

            batch.setProjectionMatrix(camera.combined);
            batch.begin();

            world.render(batch);

            font.draw(batch, "Time: " + (int) (totalTime - world.getElapsedTime()), Gdx.graphics.getWidth() * 0.65f, Gdx.graphics.getHeight() * 0.96f);
            font.draw(batch, "Score: " + currentScore, Gdx.graphics.getWidth() * 0.825f, Gdx.graphics.getHeight() * 0.96f);
            font.draw(batch, "Goal: " + goal, Gdx.graphics.getWidth() * 0.035f, Gdx.graphics.getHeight() * 0.96f);

            batch.end();

            stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
            stage.draw();

            if (world.commonSnakeSize() == 0 || world.getElapsedTime() >= totalTime) {

                backgroundMusic.stop();
                game.setScreen(new LoserScreen(game, new ScoreBroad(currentScore, (int) (totalTime - world.getElapsedTime()), goal)));
            }

            if (currentScore >= goal) {
                backgroundMusic.stop();
                game.setScreen(new WinnerScreen(game, new ScoreBroad(currentScore, (int) (totalTime - world.getElapsedTime()), goal)));
            }
        }
    }

    private TextureRegion captureScreenAsTextureRegion() {
        frameBuffer.begin();
        batch.begin();

        world.render(batch);

        batch.end();
        frameBuffer.end();

        TextureRegion result = new TextureRegion(frameBuffer.getColorBufferTexture());
        result.flip(false, true);
        return result;
    }


    public void addInputProcessor(InputProcessor inputProcessor) {
        inputMultiplexer.addProcessor(inputProcessor);
    }

    public void removeInputProcessor(InputProcessor inputProcessor) {
        inputMultiplexer.removeProcessor(inputProcessor);
    }

    @Override
    public void resize(int width, int height) {
        camera.setToOrtho(false, width, height);
        stage.getViewport().update(width, height, true);

    }

    @Override
    public void dispose() {
        if (batch != null) {
            batch.dispose();
        }

        if (skin != null) {
            skin.dispose();
        }

        if (world != null) {
            world.dispose();
        }

        if (stage != null) {
            stage.dispose();
        }

        if (frameBuffer != null) {
            frameBuffer.dispose();
        }

        if (backgroundMusic != null) {
            backgroundMusic.stop();
            backgroundMusic.dispose();
        }
    }
}