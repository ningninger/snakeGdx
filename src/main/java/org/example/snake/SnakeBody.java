package org.example.snake;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonValue;
import org.example.item.Cookie;
import org.example.map.EntityType;
import org.example.map.GameMap;
import org.example.map.Tile;
import org.example.map.TileEntity;
import org.example.player.Player;
import org.example.world.World;

public class SnakeBody extends TileEntity implements SnakeBlock, Json.Serializable {
    public static final EntityType type = EntityType.SnakeBody;

    private World world;

    public SnakeBody() {
        super(type);
    }

    public void init() {
        tile = null;
        entityState = null;
    }

    public void update() {
    }

    public void setWorld(World world) {
        this.world = world;
    }

    public void render(SpriteBatch batch, Player.PlayerType type) {
        Tile tile = getTile();
        if (tile != null && batch != null) {
            if (type == Player.PlayerType.COMMON) {
                batch.draw(world.getTextureManager().getCommonTexture(), tile.getX() * GameMap.DEFAULT_TILE_SIZE, tile.getY() * GameMap.DEFAULT_TILE_SIZE, 40, 40);
            } else {
                batch.draw(world.getTextureManager().getAITexture(), tile.getX() * GameMap.DEFAULT_TILE_SIZE, tile.getY() * GameMap.DEFAULT_TILE_SIZE, 40, 40);
            }
        }
    }

    @Override
    public void toCookie() {
        Cookie cookie = world.obtainCookie();
        cookie.setTile(getTile());
        setTile(null);
        world.freeSnakeBody(this);
    }

    @Override
    public void write(Json json) {
        json.writeValue("tileEntityState", entityState);
    }

    @Override
    public void read(Json json, JsonValue jsonValue) {
        entityState = json.readValue(TileEntity.TileEntityState.class, jsonValue.get("tileEntityState"));
    }


}
