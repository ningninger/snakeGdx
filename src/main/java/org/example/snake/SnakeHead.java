package org.example.snake;

import com.badlogic.gdx.graphics.Texture;
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

public class SnakeHead extends TileEntity implements SnakeBlock, Json.Serializable {

    public static final EntityType type = EntityType.SnakeHead;

    Direction direction;

    private World world;

    public SnakeHead() {
        super(type);

        setDirection(Direction.STAY);

    }

    public void init() {
        setDirection(Direction.STAY);
        tile = null;
        entityState = null;
    }

    public void setWorld(World world) {
        this.world = world;
    }

    public void move(Tile tile) {
        setTile(tile);
    }

    public Direction getDirection() {
        return direction;
    }

    public void setDirection(Direction direction) {
        this.direction = direction;
    }

    public void render(SpriteBatch batch, Player.PlayerType type) {
        Tile tile = getTile();
        if (tile != null && batch != null) {
            Texture texture = world.getTextureManager().getDirectionTexture(direction, type);
            if (texture != null) {
                batch.draw(texture, tile.getX() * GameMap.DEFAULT_TILE_SIZE, tile.getY() * GameMap.DEFAULT_TILE_SIZE, 40, 40);
            }
        }
    }


    @Override
    public void toCookie() {
        Cookie cookie = world.obtainCookie();
        cookie.setTile(getTile());
        setTile(null);
    }


    @Override
    public void write(Json json) {
        json.writeValue("tileEntityState", entityState);
        json.writeValue("direction", direction);
    }

    @Override
    public void read(Json json, JsonValue jsonValue) {
        entityState = json.readValue(TileEntity.TileEntityState.class, jsonValue.get("tileEntityState"));
        direction = json.readValue("direction", Direction.class, jsonValue);
    }
}
