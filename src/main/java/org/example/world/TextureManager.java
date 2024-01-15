package org.example.world;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import org.example.map.GameMap;
import org.example.player.Player;
import org.example.snake.Direction;

public class TextureManager {
    private static TextureManager instance;

    private final Texture commonTexture;
    private final Texture aiTexture;
    private final Texture commonUp;
    private final Texture commonDown;
    private final Texture commonLeft;
    private final Texture commonRight;
    private final Texture aiUp;
    private final Texture aiDown;
    private final Texture aiLeft;
    private final Texture aiRight;

    private final Texture cookieTexture;
    private final Texture trapTexture;

    private final Texture blankTexture;
    private final Texture toolBoxTexture;

    private TextureManager() {
        commonTexture = new Texture(Gdx.files.internal("img/body.png"));
        aiTexture = new Texture(Gdx.files.internal("img/body1.png"));
        commonUp = new Texture(Gdx.files.internal("img/up.png"));
        commonDown = new Texture(Gdx.files.internal("img/down.png"));
        commonLeft = new Texture(Gdx.files.internal("img/left.png"));
        commonRight = new Texture(Gdx.files.internal("img/right.png"));
        aiUp = new Texture(Gdx.files.internal("img/up1.png"));
        aiDown = new Texture(Gdx.files.internal("img/down1.png"));
        aiLeft = new Texture(Gdx.files.internal("img/left1.png"));
        aiRight = new Texture(Gdx.files.internal("img/right1.png"));

        cookieTexture = new Texture(Gdx.files.internal("img/cookie.png"));
        trapTexture = new Texture(Gdx.files.internal("img/trap.png"));

        Pixmap pixmapBlock = new Pixmap(GameMap.DEFAULT_TILE_SIZE, GameMap.DEFAULT_TILE_SIZE, Pixmap.Format.RGBA8888);
        pixmapBlock.setColor(Color.WHITE);
        pixmapBlock.fill();
        blankTexture = new Texture(pixmapBlock);

        pixmapBlock.setColor(Color.BLACK);
        pixmapBlock.fill();
        toolBoxTexture = new Texture(pixmapBlock);

        pixmapBlock.dispose();
    }


    public static TextureManager getInstance() {
        if (instance == null) {
            instance = new TextureManager();
        }
        return instance;
    }

    public Texture getCommonTexture() {
        return commonTexture;
    }

    public Texture getAITexture() {
        return aiTexture;
    }

    public Texture getCookieTexture() {
        return cookieTexture;
    }

    public Texture getTrapTexture() {
        return trapTexture;
    }

    public Texture getBlankTexture() {
        return blankTexture;
    }

    public Texture getToolBoxTexture() {
        return toolBoxTexture;
    }

    public Texture getDirectionTexture(Direction direction, Player.PlayerType type) {
        return switch (direction) {
            case UP, STAY -> (type == Player.PlayerType.COMMON) ? commonUp : aiUp;
            case DOWN -> (type == Player.PlayerType.COMMON) ? commonDown : aiDown;
            case LEFT -> (type == Player.PlayerType.COMMON) ? commonLeft : aiLeft;
            case RIGHT -> (type == Player.PlayerType.COMMON) ? commonRight : aiRight;
        };
    }

    public void dispose() {
        commonTexture.dispose();
        aiTexture.dispose();
        commonUp.dispose();
        commonDown.dispose();
        commonLeft.dispose();
        commonRight.dispose();
        aiUp.dispose();
        aiDown.dispose();
        aiLeft.dispose();
        aiRight.dispose();

        cookieTexture.dispose();
        trapTexture.dispose();

        blankTexture.dispose();
        toolBoxTexture.dispose();
    }
}
