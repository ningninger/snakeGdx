package org.example.player;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.ai.pfa.GraphPath;
import com.badlogic.gdx.ai.pfa.indexed.IndexedAStarPathFinder;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonValue;
import org.example.map.EntityType;
import org.example.map.GameMap;
import org.example.map.MapGraph;
import org.example.map.MapGraphPath;
import org.example.map.MapHeuristic;
import org.example.map.Tile;
import org.example.snake.Snake;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;

public class AIPlayer extends Player implements Json.Serializable {
    private static final int AI_MOVE_INTERVAL = 500;

    private static final int MAX_STEPS = 30;

    private final Random r = new Random();
    private int currentSteps = 0;

    private Tile targetTile;

    private Tile.TileState targetTileState;

    public AIPlayer() {
        super(PlayerType.AI);
    }


    @Override
    public void update() {
        if (snake.isAlive()) {
            performAIMove();
        }
        super.update();
    }

    public void performAIMove() {
        Tile currentTile = snake.getHead().getTile();
        if (targetTile == null
                || currentSteps >= 48
                || currentTile == targetTile
                || (targetTile.getType() != EntityType.Cookie && targetTile.getType() != EntityType.Blank)
        ) {
            if (targetTile != null && targetTile.getType() == EntityType.Cookie) {
                targetTile = findRandomBlankTile();
            } else {
                targetTile = findTargetTile();
            }
            currentSteps = 0;
        }


        GraphPath<Tile> path = findPath(currentTile, targetTile);

        if (path != null && path.getCount() > 1) {
            if (hasDuplicatePath(path)) {
                throw new RuntimeException("duplicate path");
            }
            Tile nextTile = path.get(0);
            currentSteps++;
            int direction = calculateDirection(currentTile, nextTile);
            handleInput(direction);
        }
    }

    private boolean hasDuplicatePath(GraphPath<Tile> path) {
        Set<Tile> visitedTiles = new HashSet<>();
        Tile lastTile = null;

        for (Tile tile : path) {
            if (lastTile != null && visitedTiles.contains(tile)) {
                return true;
            }
            visitedTiles.add(tile);
            lastTile = tile;
        }

        return false;
    }

    private Tile findTargetTile() {
        Tile target = null;
        if (world.getActiveCookies().isEmpty()) {
            return findRandomBlankTile();
        } else {
            target = world.getCookieTile();
        }
        return target;
    }

    private Tile findRandomBlankTile() {
        GameMap map = world.getMap();
        Tile target = null;
        while (target == null || target.getType() != EntityType.Blank) {
            int targetX = (int) (Math.random() % map.getWidth());
            int targetY = (int) (Math.random() % map.getHeight());
            target = map.getTile(targetX, targetY);
        }
        return target;
    }

    private GraphPath<Tile> findPath(Tile start, Tile target) {
        IndexedAStarPathFinder<Tile> pathFinder = new IndexedAStarPathFinder<>(new MapGraph(world.getMap()));
        GraphPath<Tile> path = new MapGraphPath();
        if (target == null) {
            throw new RuntimeException("target is null!!!");
        }
        if (pathFinder.searchNodePath(start, target, new MapHeuristic(), path)) {
            return path;
        } else {
            return null;
        }
    }

    private int calculateDirection(Tile currentTile, Tile nextTile) {
        int deltaX = nextTile.getX() - currentTile.getX();
        int deltaY = nextTile.getY() - currentTile.getY();


        if (deltaX == 0 && deltaY < 0) {
            return Input.Keys.DOWN;
        }

        if (deltaX == 0 && deltaY > 0) {
            return Input.Keys.UP;
        }

        if (deltaX < 0 && deltaY == 0) {
            return Input.Keys.LEFT;
        }

        if (deltaX > 0 && deltaY == 0) {
            return Input.Keys.RIGHT;
        }
        if (deltaX > 0) {
            return Input.Keys.RIGHT;
        } else if (deltaX < 0) {
            return Input.Keys.LEFT;
        } else if (deltaY > 0) {
            return Input.Keys.UP;
        } else if (deltaY < 0) {
            return Input.Keys.DOWN;
        }

        return 0;
    }

    public Tile.TileState getTargetTileState() {
        return targetTileState;
    }

    public Tile getTargetTile() {
        return targetTile;
    }

    public void setTargetTile(Tile tile) {
        this.targetTile = tile;
    }

    @Override
    public void write(Json json) {
        json.writeValue("type", type);
        json.writeValue("snake", snake);
        json.writeValue("isProtagonist", isProtagonist);
        json.writeValue("score", score);
        json.writeValue("targetTile", targetTile.getTileState());
        json.writeValue("currentSteps", currentSteps);
    }

    @Override
    public void read(Json json, JsonValue jsonValue) {
        type = json.readValue("type", PlayerType.class, jsonValue);
        snake = json.readValue("snake", Snake.class, jsonValue);
        isProtagonist = jsonValue.getBoolean("isProtagonist");
        score = jsonValue.getInt("score");
        targetTileState = json.readValue("targetTile", Tile.TileState.class, jsonValue);
        currentSteps = jsonValue.getInt("currentSteps");

        snake.setPlayer(this);
    }
}
