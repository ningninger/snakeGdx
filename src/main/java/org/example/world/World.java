package org.example.world;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonWriter;
import org.example.item.Cookie;
import org.example.map.GameMap;
import org.example.map.Tile;
import org.example.player.AIPlayer;
import org.example.player.CommonPlayer;
import org.example.player.Player;
import org.example.screen.PlayerScreen;
import org.example.serialization.MapData;
import org.example.serialization.WorldData;
import org.example.snake.Snake;
import org.example.snake.SnakeBody;
import org.example.snake.SnakeHead;

import java.io.StringWriter;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class World {

    private static World world;
    private final ObjectPoolManager poolManager;
    private final Array<Snake> activeSnakes;
    private final Array<SnakeBody> activeSnakeBodies;
    private final Array<SnakeHead> activeSnakeHeads;
    private final Array<Cookie> activeCookies;
    private final Array<Player> activePlayers;
    private final ExecutorService executorService = Executors.newFixedThreadPool(2);
    private int difficulty = 0;
    private float elapsedTime = 0;
    private TextureManager textureManager;
    private GameMap map;
    private PlayerScreen screen;

    private Player protagonist;

    public World() {
        this.poolManager = new ObjectPoolManager();

        map = new GameMap(this);

        activeSnakes = new Array<>(1024);
        activeSnakeBodies = new Array<>(1024);
        activeSnakeHeads = new Array<>(1024);
        activeCookies = new Array<>(1024);
        activePlayers = new Array<>(32);
    }

    private World(ObjectPoolManager poolManager) {
        this.poolManager = poolManager;

        this.textureManager = TextureManager.getInstance();

        activeSnakes = new Array<>(1024);
        activeSnakeBodies = new Array<>(1024);
        activeSnakeHeads = new Array<>(1024);
        activeCookies = new Array<>(1024);
        activePlayers = new Array<>(32);
    }

    private World(WorldData worldData, MapData mapData, TextureManager textureManager) {
        map = mapData.toGameMap();
        worldData.loadTileEntities(map);
        map.setWorld(this);

        this.difficulty = worldData.getDifficulty();
        this.elapsedTime = worldData.getElapsedTime();

        this.textureManager = textureManager;
        activeCookies = worldData.getActiveCookies();
        activePlayers = worldData.getActivePlayers();
        activeSnakes = worldData.getActiveSnakes();
        activeSnakeBodies = worldData.getActiveSnakeBodies();
        activeSnakeHeads = worldData.getActiveSnakeHeads();
        poolManager = new ObjectPoolManager();


        boolean onlyOneProtagonist = false;
        for (Player player : activePlayers) {
            player.setWorld(this);
            player.setInputProcessor(player.getSnake());
            if (player.isProtagonist() && !onlyOneProtagonist) {
                onlyOneProtagonist = true;
                protagonist = player;
            } else {
                if (player instanceof AIPlayer aiPlayer) {
                    Tile targetTile = map.getTile(aiPlayer.getTargetTileState().getX(), aiPlayer.getTargetTileState().getY());
                    aiPlayer.setTargetTile(targetTile);
                }
            }
        }

        for (Snake snake : activeSnakes) {
            snake.setWorld(this);
        }

        for (Cookie cookie : activeCookies) {
            cookie.setWorld(this);
        }

        for (SnakeHead snakeHead : activeSnakeHeads) {
            snakeHead.setWorld(this);
        }

        for (SnakeBody snakeBody : activeSnakeBodies) {
            snakeBody.setWorld(this);
        }
    }

    public static void clearWorld() {
        world = null;
    }

    public static World getWorld() {
        if (world == null) {
            world = new World(new ObjectPoolManager());
        }
        return world;
    }

    public static void setWorld(World newWorld) {
        world = newWorld;
    }

    public static void setOldWorld() {
        Json mapJson = new Json();
        FileHandle mapFileHandle = Gdx.files.internal("serialization/old-map.json");
        String mapJsonStr = mapFileHandle.readString();

        MapData mapData = mapJson.fromJson(MapData.class, mapJsonStr);

        Json worldJson = new Json();
        FileHandle worldFileHandle = Gdx.files.internal("serialization/old-world.json");
        String worldJsonStr = worldFileHandle.readString();

        WorldData worldData = worldJson.fromJson(WorldData.class, worldJsonStr);

        world = new World(worldData, mapData, TextureManager.getInstance());
    }

    public int getScore() {
        return protagonist.getScore();
    }

    public float getElapsedTime() {
        return this.elapsedTime;
    }

    public void updateTime(float delta) {
        this.elapsedTime += delta;
    }

    public TextureManager getTextureManager() {
        return textureManager;
    }

    public void setScreen(PlayerScreen screen) {
        this.screen = screen;
    }

    public Player obtainPlayer(Player.PlayerType type) {
        Player player = switch (type) {
            case AI -> poolManager.obtainAIPlayer();
            case COMMON -> poolManager.obtainCommonPlayer();
        };
        activePlayers.add(player);
        player.init();
        screen.addInputProcessor(player.getInputProcessor());
        return player;
    }

    public void freePlayer(Player player) {
        activePlayers.removeValue(player, true);
        screen.removeInputProcessor(player.getInputProcessor());
        switch (player.getType()) {
            case AI -> poolManager.freeAIPlayer((AIPlayer) player);
            case COMMON -> poolManager.freeCommonPlayer((CommonPlayer) player);
        }
    }

    public int aiSnakeSize() {
        int size = 0;
        for (Player player : activePlayers) {
            if (player.getType() == Player.PlayerType.AI) {
                size++;
            }
        }
        return size;
    }

    public int commonSnakeSize() {
        int size = 0;
        for (Player player : activePlayers) {
            if (player.getType() == Player.PlayerType.COMMON) {
                size++;
            }
        }
        return size;
    }

    public Snake obtainSnake() {
        Snake snake = poolManager.obtainSnake();
        activeSnakes.add(snake);
        snake.init();
        snake.setWorld(this);
        return snake;
    }

    public void freeSnake(Snake snake) {
        activeSnakes.removeValue(snake, true);
        poolManager.freeSnake(snake);
    }

    public SnakeBody obtainSnakeBody() {
        SnakeBody snakeBody = poolManager.obtainSnakeBody();
        activeSnakeBodies.add(snakeBody);
        snakeBody.init();
        snakeBody.setWorld(this);
        return snakeBody;
    }

    public void freeSnakeBody(SnakeBody snakeBody) {
        activeSnakeBodies.removeValue(snakeBody, true);
        poolManager.freeSnakeBody(snakeBody);
    }

    public SnakeHead obtainSnakeHead() {
        SnakeHead snakeHead = poolManager.obtainSnakeHead();
        activeSnakeHeads.add(snakeHead);
        snakeHead.init();
        snakeHead.setWorld(this);
        return snakeHead;
    }

    public void freeSnakeHead(SnakeHead snakeHead) {
        activeSnakeHeads.removeValue(snakeHead, true);
        poolManager.freeSnakeHead(snakeHead);
    }

    public Cookie obtainCookie() {
        Cookie cookie = poolManager.obtainCookie();
        activeCookies.add(cookie);
        cookie.init();
        cookie.setWorld(this);
        return cookie;
    }

    public void freeCookie(Cookie cookie) {
        activeCookies.removeValue(cookie, true);
        poolManager.freeCookie(cookie);
    }

    public GameMap getMap() {
        return map;
    }

    public Tile getCookieTile() {
        Random random = new Random();
        int index = random.nextInt(activeCookies.size);
        return activeCookies.get(index).getTile();
    }

    public void updateWorld() {

        List<Callable<Void>> playerUpdateTasks = new LinkedList<>();
        for (Player player : activePlayers) {
            playerUpdateTasks.add(() -> {
                player.update();
                return null;
            });
        }

        try {
            executorService.invokeAll(playerUpdateTasks);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        List<Callable<Void>> cookieUpdateTasks = new LinkedList<>();
        for (Cookie cookie : activeCookies) {
            cookieUpdateTasks.add(() -> {
                cookie.update();
                return null;
            });
        }

        try {
            executorService.invokeAll(cookieUpdateTasks);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        randomCookie();
        randomAiSnake();
    }

    public void render(SpriteBatch batch) {
        map.render(batch);
        activeCookies.forEach(cookie -> {
            if (cookie != null) {
                cookie.render(batch);
            } else {
                Gdx.app.error("World", "Null cookie found in activeCookies");
            }
        });
        activeSnakes.forEach(snake -> {
            if (snake != null) {
                snake.render(batch);
            } else {
                Gdx.app.error("World", "Null snake found in activeSnakes");
            }
        });
    }

    public void initWorld(int goal) {
        difficulty = (int) (Math.log(goal) / Math.log(2));
        assert difficulty != 0;
        if (map == null) {
            map = new GameMap(difficulty, this);
        }
        protagonist = obtainPlayer(Player.PlayerType.COMMON);
        protagonist.setProtagonist(true);
        Tile tile = map.getBlankTile();
        protagonist.setInitialPosition(tile.getX(), tile.getY());

        randomCookie();
        randomAiSnake();
    }

    private void randomCookie() {
        int targetSize = difficulty;

        while (activeCookies.size < targetSize) {
            Tile tile = map.getBlankTile();

            if (tile != null) {
                Cookie cookie = obtainCookie();
                cookie.setInitialPosition(tile.getX(), tile.getY());
            } else {
                break;
            }
        }
    }

    private void randomAiSnake() {
        int targetSize = difficulty;

        int currentSize = aiSnakeSize();

        while (currentSize < targetSize) {
            Tile tile = map.getBlankTile();

            if (tile != null) {
                Player aiPlayer = obtainPlayer(Player.PlayerType.AI);
                aiPlayer.setInitialPosition(tile.getX(), tile.getY());
                currentSize++;
            } else {
                break;
            }
        }
    }

    public void saveWorld() {
        map.saveMap();

        WorldData worldData = new WorldData(this);
        Json json = new Json();

        json.setOutputType(JsonWriter.OutputType.json);
        json.setWriter(new JsonWriter(new StringWriter()));

        String jsonString = json.toJson(worldData);

        FileHandle fileHandle = Gdx.files.local("serialization/old-world.json");
        fileHandle.writeString(jsonString, false);

        Gdx.app.log("WorldData", "WorldData saved successfully!");

    }

    public void dispose() {
        activePlayers.forEach(Player::dispose);
        activePlayers.clear();
        activeCookies.forEach(Cookie::dispose);
        activeCookies.clear();
        activeSnakes.clear();
        activeSnakeBodies.clear();
        activeSnakeHeads.clear();
        world = null;
    }


    public int getDifficulty() {
        return difficulty;
    }

    public Array<Snake> getActiveSnakes() {
        return activeSnakes;
    }

    public Array<SnakeBody> getActiveSnakeBodies() {
        return activeSnakeBodies;
    }

    public Array<SnakeHead> getActiveSnakeHeads() {
        return activeSnakeHeads;
    }

    public Array<Cookie> getActiveCookies() {
        return activeCookies;
    }

    public Array<Player> getActivePlayers() {
        return activePlayers;
    }


}
