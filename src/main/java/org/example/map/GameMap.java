package org.example.map;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonWriter;
import org.example.item.Trap;
import org.example.serialization.MapData;
import org.example.world.World;

import java.io.StringWriter;
import java.util.Objects;
import java.util.Random;

public class GameMap {

    public static final int DEFAULT_TILE_SIZE = 40;
    public static final int DEFAULT_TOOL_SIZE = 2;
    public static final int MAX_WALL_SIZE = 32;

    private final int width;
    private final int height;
    private final Random r = new Random();
    private final Tile[][] tiles;
    public int wallSize;
    private World world;

    public GameMap(World world) {
        this.world = world;
        this.width = DEFAULT_TILE_SIZE;
        this.height = DEFAULT_TILE_SIZE;
        this.wallSize = 1;
        this.tiles = new Tile[width][height + DEFAULT_TOOL_SIZE];

        generateMap();
    }

    public GameMap(int wallSize, World world) {
        this.world = world;
        this.width = Gdx.graphics.getWidth() / DEFAULT_TILE_SIZE;
        this.height = (Gdx.graphics.getHeight() / DEFAULT_TILE_SIZE) - DEFAULT_TOOL_SIZE;
        this.wallSize = Math.min(wallSize, MAX_WALL_SIZE);
        this.tiles = new Tile[width][height + DEFAULT_TOOL_SIZE];

        generateMap();
    }

    public GameMap(Tile[][] tiles, int wallSize) {
        this.width = Gdx.graphics.getWidth() / DEFAULT_TILE_SIZE;
        this.height = (Gdx.graphics.getHeight() / DEFAULT_TILE_SIZE) - DEFAULT_TOOL_SIZE;
        this.tiles = tiles;
        this.wallSize = wallSize;
        this.world = null;

        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height + DEFAULT_TOOL_SIZE; j++) {
                tiles[i][j].setMap(this);
            }
        }
    }

    public void setWorld(World world) {
        this.world = world;
    }

    void generateMap() {
        initialTiles();

        for (int i = 0; i < wallSize; i++) {
            Tile tile = getBlankTile();
            if (tile != null) {
                Trap wall = new Trap();
                wall.setTile(tile);
            } else {
                break;
            }
        }
    }

    void initialTiles() {
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                tiles[x][y] = new Tile(x, y);
                tiles[x][y].setMap(this);
            }
        }

        for (int x = 0; x < width; x++) {
            for (int y = height; y < height + DEFAULT_TOOL_SIZE; y++) {
                tiles[x][y] = new Tile(x, y);
                tiles[x][y].setMap(this);
            }
        }
    }

    public void render(SpriteBatch batch) {
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                if (Objects.requireNonNull(tiles[x][y].getType()) == EntityType.Trap && batch != null) {
                    batch.draw(world.getTextureManager().getTrapTexture(), x * DEFAULT_TILE_SIZE, y * DEFAULT_TILE_SIZE, DEFAULT_TILE_SIZE, DEFAULT_TILE_SIZE);
                } else if (batch != null) {
                    batch.draw(world.getTextureManager().getBlankTexture(), x * DEFAULT_TILE_SIZE, y * DEFAULT_TILE_SIZE, DEFAULT_TILE_SIZE, DEFAULT_TILE_SIZE);
                }
            }
        }
        for (int x = 0; x < width; x++) {
            for (int y = height; y < height + DEFAULT_TOOL_SIZE; y++) {
                if (batch != null)
                    batch.draw(world.getTextureManager().getToolBoxTexture(), x * DEFAULT_TILE_SIZE, y * DEFAULT_TILE_SIZE, DEFAULT_TILE_SIZE, DEFAULT_TILE_SIZE);
            }
        }
    }

    public int getWidth() {
        return this.width;
    }

    public int getHeight() {
        return this.height;
    }

    public EntityType getTileType(int x, int y) {
        return tiles[x][y].getType();
    }

    public Tile getTile(int x, int y) {
        x = x % width;
        y = y % height;
        return tiles[x][y];
    }

    public boolean isPassable(Tile sourceTile, Tile targetTile) {
        Tile source = getTile(sourceTile.getX(), sourceTile.getY());
        Tile target = getTile(targetTile.getX(), targetTile.getY());

        EntityType sourceType = source.getType();
        EntityType targetType = target.getType();

        boolean isSourceBlank = sourceType == EntityType.Blank;
        boolean isTargetBlank = targetType == EntityType.Blank;
        boolean isSourceSnakeHead = sourceType == EntityType.SnakeHead;
        boolean isTargetSnakeHead = targetType == EntityType.SnakeHead;
        boolean isSourceCookie = sourceType == EntityType.Cookie;
        boolean isTargetCookie = targetType == EntityType.Cookie;


        return (isSourceBlank && isTargetBlank) ||
                (isSourceBlank && isTargetSnakeHead) ||
                (isSourceSnakeHead && isTargetBlank) ||
                (isSourceCookie && isTargetSnakeHead) ||
                (isSourceSnakeHead && isTargetCookie) ||
                (isSourceBlank && isTargetCookie) ||
                (isSourceCookie && isTargetBlank) ||
                (isSourceCookie && isTargetCookie);
    }

    public Tile getBlankTile() {
        int maxAttempts = 100;
        int attempts = 0;
        Tile tile = null;
        while (attempts < maxAttempts) {
            int x = r.nextInt(width);
            int y = r.nextInt(height);

            if (getTileType(x, y) == EntityType.Blank) {
                tile = getTile(x, y);
            }

            attempts++;
        }
        return tile;
    }

    public void saveMap() {
        MapData mapData = new MapData(this.tiles, this.wallSize);
        Json json = new Json();

        json.setOutputType(JsonWriter.OutputType.json);
        json.setWriter(new JsonWriter(new StringWriter()));

        String jsonString = json.toJson(mapData);

        FileHandle fileHandle = Gdx.files.local("serialization/old-map.json");
        fileHandle.writeString(jsonString, false);

    }

    public Tile[][] getTiles() {
        return tiles;
    }

    public int getWallSize() {
        return wallSize;
    }
}
