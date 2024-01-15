package org.example.map;

import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonValue;
import org.example.item.Trap;
import org.example.snake.Direction;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Tile extends MapEntity implements Json.Serializable {
    private TileEntity entity;

    private GameMap map;

    private final Lock lock = new ReentrantLock();


    public boolean tryLock() {
        return lock.tryLock();
    }

    public void unlock() {
        lock.unlock();
    }

    public Tile() {
        super();
    }

    public Tile(int x, int y) {
        super();
        setPosition(x, y);
    }

    public Tile getNeighbour(Direction direction) {
        int maxHeight = map.getHeight();
        int maxWidth = map.getWidth();

        int x = getX();
        int y = getY();

        switch (direction)  {
            case UP -> y = (y + 1) % maxHeight;
            case DOWN -> y = (y - 1 + maxHeight) % maxHeight;
            case LEFT -> x = (x - 1 + maxWidth) % maxWidth;
            case RIGHT -> x = (x + 1) % maxWidth;
        }

        return map.getTile(x, y);
    }

    public void setEntity(TileEntity entity) {
        this.entity = entity;
    }

    public void setMap(GameMap map) {
        this.map = map;
    }

    public int getIndex() {
        return getX() * map.getHeight() + getY();
    }

    public TileEntity getEntity() {
        return entity;
    }

    public EntityType getType() {
        if (entity == null) {
            return EntityType.Blank;
        }
        return entity.getType();
    }

    public TileState getTileState() {
        return new TileState(this);
    }

    public GameMap getMap() {
        return map;
    }

    public void clear() {
        entity = null;
    }


    public static class TileState {
        private int x;
        private int y;

        private EntityType type;

        TileState() {

        }

        TileState(Tile tile) {
            this.x = tile.getX();
            this.y = tile.getY();
            this.type = tile.getType();
        }

        void applyToTile(Tile tile) {
            tile.setPosition(x, y);
            if (type == EntityType.Trap) {
                Trap trap = new Trap();
                trap.setTile(tile);
            }
        }

        public int getX() {
            return x;
        }

        public int getY() {
            return y;
        }
    }

    @Override
    public void write(Json json) {
        json.writeValue("state", new TileState(this));
    }

    @Override
    public void read(Json json, JsonValue jsonValue) {
        TileState state = json.readValue(TileState.class, jsonValue.get("state"));
        state.applyToTile(this);
    }
}
