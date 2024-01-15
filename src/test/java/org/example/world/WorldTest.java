package org.example.world;

import org.example.item.Cookie;
import org.example.map.EntityType;
import org.example.map.GameMap;
import org.example.map.Tile;
import org.example.player.Player;
import org.example.screen.PlayerScreen;
import org.example.snake.Snake;
import org.example.snake.SnakeBody;
import org.example.snake.SnakeHead;
import org.example.utils.MockWorld;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class WorldTest {

    private World world;

    private GameMap gameMap;

    private PlayerScreen screen;

    private Player protagonist;


    @BeforeEach
    void setUp() {
        PlayerScreen screen = new PlayerScreen(null);
        world = new MockWorld();
        World.setWorld(world);
        world.setScreen(screen);
        world.initWorld(2);
        gameMap = world.getMap();

    }

    @Test
    void scoreAndTime() {
        int score = world.getScore();
        float time = world.getElapsedTime();
        world.updateTime(time);
    }

    @Test
    void clearWorld() {
        World.clearWorld();
    }

    @Test
    void getWorld() {
        World.getWorld();
    }

    @Test
    void testObtainAndFreePlayer() {
        Player commonPlayer = world.obtainPlayer(Player.PlayerType.COMMON);
        world.freePlayer(commonPlayer);

        Player aiPlayer = world.obtainPlayer(Player.PlayerType.AI);
        world.freePlayer(aiPlayer);
    }

    @Test
    void testObtainAndFreeSnake() {
        Snake snake = world.obtainSnake();
        world.freeSnake(snake);
    }

    @Test
    void testObtainAndFreeSnakeHead() {
        SnakeHead snakeHead = world.obtainSnakeHead();
        world.freeSnakeHead(snakeHead);
    }

    @Test
    void testObtainAndFreeSnakeBody() {
        SnakeBody snakeBody = world.obtainSnakeBody();
        world.freeSnakeBody(snakeBody);
    }

    @Test
    void testObtainAndFreeCookie() {
        Cookie cookie = world.obtainCookie();
        world.freeCookie(cookie);
    }

    @Test
    void aiSnakeSize() {
        assertEquals(1, world.aiSnakeSize());
    }

    @Test
    void commonSnakeSize() {
        assertEquals(1, world.commonSnakeSize());
    }

    @Test
    void getMap() {
        assertEquals(gameMap, world.getMap());
    }

    @Test
    void getCookieTile() {
        Tile tile = world.getCookieTile();
        assertEquals(EntityType.Cookie, tile.getType());
    }

    @Test
    void updateWorld() {
        world.updateWorld();
    }

    @Test
    void render() {
        world.render(null);
    }


    @Test
    void dispose() {
        world.dispose();
    }


}