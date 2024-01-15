package org.example.screen;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.ProgressBar;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Timer;

public class LoadingScreen extends ScreenAdapter {
    private final Game game;
    private Stage stage;
    private ProgressBar progressBar;
    private Image image1, image2, image3;

    private Label screenTitle;

    private boolean isOld = false;

    private Label gameMethod;

    private int currentImageIndex = 0;

    public LoadingScreen(Game game, boolean isOld) {
        this.game = game;
        this.isOld = isOld;
    }

    @Override
    public void show() {
        stage = new Stage();
        Gdx.input.setInputProcessor(stage);

        Skin skin = new Skin(Gdx.files.internal("assets/comic/skin/comic-ui.json"));

        Label.LabelStyle labelStyle = skin.get("title", Label.LabelStyle.class);
        if (!isOld) {
            screenTitle = new Label("Game Loading...", labelStyle);
            screenTitle.setFontScale(1.75f);
            screenTitle.setAlignment(Align.top);
            screenTitle.setPosition(Gdx.graphics.getWidth() / 2f - screenTitle.getWidth() / 2f, Gdx.graphics.getHeight() * 0.75f);
        } else {
            screenTitle = new Label("Resuming Old Game...", labelStyle);
            screenTitle.setFontScale(1.75f);
            screenTitle.setAlignment(Align.top);
            screenTitle.setPosition(Gdx.graphics.getWidth() / 2f - screenTitle.getWidth() / 2f, Gdx.graphics.getHeight() * 0.75f);
        }


        labelStyle = skin.get("half-tone", Label.LabelStyle.class);
        gameMethod = new Label("Tips: Guide your snake with arrow keys, avoid threats, and grow by eating.", labelStyle);
        gameMethod.setFontScale(1f);
        gameMethod.setWidth(Gdx.graphics.getWidth() * 0.9f);
        gameMethod.setHeight(Gdx.graphics.getHeight() * 0.05f);
        gameMethod.setAlignment(Align.bottom);
        gameMethod.setPosition(Gdx.graphics.getWidth() / 2f - gameMethod.getWidth() / 2f, Gdx.graphics.getHeight() * 0.05f);


        progressBar = new ProgressBar(0f, 1f, 0.05f, false, skin);
        progressBar.setSize(Gdx.graphics.getWidth() * 0.5f, Gdx.graphics.getHeight() * 0.05f);
        progressBar.setPosition(Gdx.graphics.getWidth() / 2f - progressBar.getWidth() / 2, Gdx.graphics.getHeight() * 0.5f);
        progressBar.setAnimateDuration(0.25f);

        stage.addActor(progressBar);

        image1 = new Image(new Texture(Gdx.files.internal("img/loading/loading1.png")));
        image2 = new Image(new Texture(Gdx.files.internal("img/loading/loading2.png")));
        image3 = new Image(new Texture(Gdx.files.internal("img/loading/loading3.png")));

        float imageWidth = Gdx.graphics.getWidth() * 0.2f;
        float imageHeight = Gdx.graphics.getHeight() * 0.05f;
        image1.setSize(imageWidth * 1.12f, imageHeight);
        image2.setSize(imageWidth, imageHeight);
        image3.setSize(imageWidth, imageHeight);
        float imageX = Gdx.graphics.getWidth() * 0.5f - imageWidth * 1.5f;
        float imageY = Gdx.graphics.getHeight() * 0.2f;
        image1.setPosition(imageX + imageWidth, imageY);
        image2.setPosition(imageX + imageWidth, imageY);
        image3.setPosition(imageX + imageWidth, imageY);

        Timer.schedule(new Timer.Task() {
            @Override
            public void run() {
                currentImageIndex = (currentImageIndex + 1) % 3;
                updateImage();
            }
        }, 0.1f, 0.5f);


        Timer.schedule(new Timer.Task() {
            @Override
            public void run() {
                float progress = progressBar.getValue();
                progress += 0.05f;
                progressBar.setValue(progress);
                if (progress >= 1.0f) {

                    Timer.schedule(new Timer.Task() {
                        @Override
                        public void run() {
                            if (!isOld) {
                                game.setScreen(new PlayerScreen(game, 2));
                            } else {
                                game.setScreen(new PlayerScreen(game));
                            }
                        }
                    }, 2);
                    this.cancel();
                }
            }
        }, 0.1f, 0.3f);
        Gdx.app.log("LoadingScreen", "show 方法执行完成");
    }

    private void updateImage() {
        stage.clear();
        stage.addActor(progressBar);
        stage.addActor(screenTitle);
        stage.addActor(gameMethod);
        switch (currentImageIndex) {
            case 0 -> stage.addActor(image1);
            case 1 -> stage.addActor(image2);
            case 2 -> stage.addActor(image3);
        }
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        stage.act(delta);
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

        if (image1 != null && image1.getDrawable() instanceof TextureRegionDrawable) {
            TextureRegion region = ((TextureRegionDrawable) image1.getDrawable()).getRegion();
            region.getTexture().dispose();
        }

        if (image2 != null && image2.getDrawable() instanceof TextureRegionDrawable) {
            TextureRegion region = ((TextureRegionDrawable) image2.getDrawable()).getRegion();
            region.getTexture().dispose();
        }

        if (image3 != null && image3.getDrawable() instanceof TextureRegionDrawable) {
            TextureRegion region = ((TextureRegionDrawable) image3.getDrawable()).getRegion();
            region.getTexture().dispose();
        }
    }


}
