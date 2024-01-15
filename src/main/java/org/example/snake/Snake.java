package org.example.snake;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonValue;
import org.example.item.Cookie;
import org.example.map.EntityType;
import org.example.map.Tile;
import org.example.player.Player;
import org.example.world.World;

public class Snake implements Json.Serializable {

    boolean isAlive;

    boolean needsToGrow = false;

    int length;

    int bodyLimit = 32;

    int speed;

    private SnakeHead head;

    private Array<SnakeBody> bodies;

    private Player player;

    private World world;

    private boolean toCookieItem = false;

    public Snake() {
        setAlive(true);
        setNeedsToGrow(false);
        this.world = World.getWorld();
        length = 0;
        head = world.obtainSnakeHead();
        bodies = new Array<>(bodyLimit);
        speed = 0;
    }

    public void setWorld(World world) {
        this.world = world;
    }

    public void init() {
        head.init();
        bodies.clear();
        setAlive(true);
        setNeedsToGrow(false);
        setToCookieItem(false);
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public void setToCookieItem(boolean state) {
        toCookieItem = false;
    }

    public boolean isAlive() {
        return isAlive;
    }

    public void setAlive(boolean state) {
        isAlive = state;
    }

    public boolean isNeedsToGrow() {
        return needsToGrow;
    }

    void setNeedsToGrow(boolean state) {
        needsToGrow = state;
    }

    public SnakeHead getHead() {
        return head;
    }

    public Array<SnakeBody> getBodies() {
        return bodies;
    }

    public void update() {
        if (isAlive) {
            if (head.getDirection() != Direction.STAY) {
                if (!checkCollisions()) {
                    setAlive(false);
                }
            }
        } else {
            if (!toCookieItem) {
                head.toCookie();
                bodies.forEach(SnakeBody::toCookie);
                bodies.clear();
                world.freePlayer(player);
                toCookieItem = true;
            }
        }
    }

    private boolean checkCollisions() {
        Tile nextTile = head.getTile().getNeighbour(head.getDirection());

        if (nextTile.getType() == EntityType.Trap
                || nextTile.getType() == EntityType.SnakeBody
                || nextTile.getType() == EntityType.SnakeHead) {
            return false;
        }

        if (!nextTile.tryLock()) {
            return false;
        }

        move();

        nextTile.unlock();
        return true;
    }

    public void move() {
        Direction direction = head.direction;
        if (direction != Direction.STAY) {
            Tile currTile = head.getTile();
            Tile nextTile = currTile.getNeighbour(head.getDirection());
            if (nextTile.getType() == EntityType.Cookie) {
                Cookie cookie = (Cookie) nextTile.getEntity();
                cookie.setEaten(true);
                setNeedsToGrow(true);
                player.increaseScore();
            }
            head.move(nextTile);

            if (needsToGrow) {
                SnakeBody newBody = world.obtainSnakeBody();
                newBody.setTile(currTile);
                bodies.add(newBody);
            }

            moveBody(currTile);
        }

    }

    public void render(SpriteBatch batch) {
        head.render(batch, player.getType());
        for (SnakeBody body : bodies) {
            body.render(batch, player.getType());
        }
    }

    public void moveBody(Tile bodyNextTile) {
        if (!needsToGrow) {
            Tile nextTile = bodyNextTile;

            for (int i = bodies.size - 1; i >= 0; i--) {
                SnakeBody currentBody = bodies.get(i);
                Tile curTile = currentBody.getTile();
                currentBody.setTile(nextTile);
                nextTile = curTile;
                if (i == 0) {
                    if (curTile != null) {
                        curTile.setEntity(null);
                    } else {
                        throw new RuntimeException("move body error!!!");
                    }
                }
            }
            if (bodies.isEmpty()) {
                bodyNextTile.setEntity(null);
            }
        } else {
            setNeedsToGrow(false);
        }
    }

    public void handleInput(int key) {

        switch (key) {
            case Input.Keys.UP -> {
                if (head.getDirection() != Direction.DOWN) {
                    head.setDirection(Direction.UP);
                }
            }
            case Input.Keys.DOWN -> {
                if (head.getDirection() != Direction.UP) {
                    head.setDirection(Direction.DOWN);
                }
            }
            case Input.Keys.LEFT -> {
                if (head.getDirection() != Direction.RIGHT) {
                    head.setDirection(Direction.LEFT);
                }
            }
            case Input.Keys.RIGHT -> {
                if (head.getDirection() != Direction.LEFT) {
                    head.setDirection(Direction.RIGHT);
                }
            }
        }
    }


    public void dispose() {
        world.freeSnakeHead(head);
        for (SnakeBody body : bodies) {
            world.freeSnakeBody(body);
        }
    }

    public void setInitialPosition(int x, int y) {
        x = x % world.getMap().getWidth();
        y = y % world.getMap().getHeight();

        Tile tile = world.getMap().getTile(x, y);
        head.setTile(tile);
    }

    @Override
    public void write(Json json) {
        json.writeValue("head", head);
        json.writeValue("bodies", bodies);
    }

    @Override
    public void read(Json json, JsonValue jsonValue) {
        head = json.readValue("head", SnakeHead.class, jsonValue);
        bodies = json.readValue("bodies", Array.class, SnakeBody.class, jsonValue);
    }
}
