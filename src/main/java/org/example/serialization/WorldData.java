package org.example.serialization;

import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonValue;
import org.example.item.Cookie;
import org.example.map.GameMap;
import org.example.map.Tile;
import org.example.player.Player;
import org.example.snake.Snake;
import org.example.snake.SnakeBody;
import org.example.snake.SnakeHead;
import org.example.world.World;

public class WorldData implements Json.Serializable {

    private int difficulty;

    private float elapsedTime;

    private Array<Snake> activeSnakes;

    private Array<SnakeBody> activeSnakeBodies;

    private Array<SnakeHead> activeSnakeHeads;

    private Array<Cookie> activeCookies;

    private Array<Player> activePlayers;

    private boolean isLoaded = false;

    public WorldData(World world) {
        this.difficulty = world.getDifficulty();
        this.elapsedTime = world.getElapsedTime();
        this.activeSnakes = world.getActiveSnakes();
        this.activeSnakeBodies = world.getActiveSnakeBodies();
        this.activeSnakeHeads = world.getActiveSnakeHeads();
        this.activeCookies = world.getActiveCookies();
        this.activePlayers = world.getActivePlayers();
    }

    public WorldData() {

    }

    @Override
    public void write(Json json) {
        json.writeValue("difficulty", difficulty);
        json.writeValue("elapsedTime", elapsedTime);
        json.writeValue("activeCookies", activeCookies, Array.class, Cookie.class);
        json.writeValue("activePlayers", activePlayers, Array.class, Player.class);
    }

    @Override
    public void read(Json json, JsonValue jsonValue) {
        difficulty = jsonValue.getInt("difficulty");
        elapsedTime = jsonValue.getFloat("elapsedTime");
        activeCookies = json.readValue("activeCookies", Array.class, Cookie.class, jsonValue);
        activePlayers = json.readValue("activePlayers", Array.class, Player.class, jsonValue);
        parseOtherArray();
    }

    private void parseOtherArray() {
        assert activePlayers != null;
        activeSnakes = new Array<>(1024);
        activeSnakeBodies = new Array<>(1024);
        activeSnakeHeads = new Array<>(1024);
        for (Player player : activePlayers) {
            activeSnakes.add(player.getSnake());
            activeSnakeBodies.addAll(player.getSnake().getBodies());
            activeSnakeHeads.add(player.getSnake().getHead());
        }

    }

    public void loadTileEntities(GameMap gameMap) {
        loadCookies(gameMap);
        loadSnakeBodies(gameMap);
        loadSnakeHeads(gameMap);
        isLoaded = true;
    }

    private void loadCookies(GameMap gameMap) {
        for (Cookie cookie : activeCookies) {
            Tile tile = gameMap.getTile(cookie.getEntityState().getX(), cookie.getEntityState().getY());
            cookie.setTile(tile);
        }
    }

    private void loadSnakeBodies(GameMap gameMap) {
        for (SnakeBody snakeBody : activeSnakeBodies) {
            Tile tile = gameMap.getTile(snakeBody.getEntityState().getX(), snakeBody.getEntityState().getY());
            snakeBody.setTile(tile);
        }
    }

    private void loadSnakeHeads(GameMap gameMap) {
        for (SnakeHead snakeHead : activeSnakeHeads) {
            Tile tile = gameMap.getTile(snakeHead.getEntityState().getX(), snakeHead.getEntityState().getY());
            snakeHead.setTile(tile);
        }
    }


    public boolean isLoaded() {
        return isLoaded;
    }

    public int getDifficulty() {
        return difficulty;
    }

    public float getElapsedTime() {
        return elapsedTime;
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
