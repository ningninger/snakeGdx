package org.example.item;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonValue;
import org.example.map.EntityType;
import org.example.map.GameMap;
import org.example.map.Tile;
import org.example.map.TileEntity;
import org.example.world.World;

public class Cookie extends TileEntity implements Json.Serializable {

    public static final EntityType type = EntityType.Cookie;

    private boolean isEaten = false;

    private World world;

    public Cookie() {
        super(type);
        setEaten(false);
    }

    public void init() {
        tile = null;
        entityState = null;
        setEaten(false);
    }

    public void setWorld(World world) {
        this.world = world;
    }

    public boolean isEaten() {
        return isEaten;
    }

    public void setEaten(boolean isEaten) {
        this.isEaten = isEaten;
    }

    public void update() {
        if (isEaten) {
            setTile(null);
            world.freeCookie(this);
        }
    }

    public void render(SpriteBatch batch) {
        Tile tile = getTile();
        if (tile != null && batch != null) {
            batch.draw(world.getTextureManager().getCookieTexture(), tile.getX() * GameMap.DEFAULT_TILE_SIZE, tile.getY() * GameMap.DEFAULT_TILE_SIZE, 40, 40);
        }
    }

    public void setInitialPosition(int x, int y) {
        x = x % world.getMap().getWidth();
        y = y % world.getMap().getHeight();

        Tile tile = world.getMap().getTile(x, y);
        assert tile != null;
        setTile(tile);
    }

    public void dispose() {

    }

    @Override
    public void write(Json json) {
        json.writeValue("tileEntityState", entityState);
        json.writeValue("isEaten", isEaten());
    }

    @Override
    public void read(Json json, JsonValue jsonValue) {
        entityState = json.readValue(TileEntity.TileEntityState.class, jsonValue.get("tileEntityState"));
        setEaten(jsonValue.getBoolean("isEaten"));
    }


}
