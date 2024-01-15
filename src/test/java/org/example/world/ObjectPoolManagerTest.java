package org.example.world;

import org.example.item.Cookie;
import org.example.player.AIPlayer;
import org.example.player.CommonPlayer;
import org.example.snake.Snake;
import org.example.snake.SnakeBody;
import org.example.snake.SnakeHead;
import org.example.utils.MockWorld;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;

class ObjectPoolManagerTest {


    private ObjectPoolManager objectPoolManager;

    private World world;

    @BeforeEach
    void setUp() {
        objectPoolManager = new ObjectPoolManager();
        world = new MockWorld();
        World.setWorld(world);
    }

    @Test
    void obtainAndFreeCommonPlayerTest() {
        CommonPlayer commonPlayer = objectPoolManager.obtainCommonPlayer();

        assertNotNull(commonPlayer);

        objectPoolManager.freeCommonPlayer(commonPlayer);

    }

    @Test
    void obtainAndFreeAIPlayerTest() {
        AIPlayer aiPlayer = objectPoolManager.obtainAIPlayer();

        assertNotNull(aiPlayer);

        objectPoolManager.freeAIPlayer(aiPlayer);

    }

    @Test
    void obtainAndFreeSnakeTest() {
        Snake snake = objectPoolManager.obtainSnake();

        assertNotNull(snake);

        objectPoolManager.freeSnake(snake);

    }

    @Test
    void obtainAndFreeSnakeHeadTest() {
        SnakeHead snakeHead = objectPoolManager.obtainSnakeHead();

        assertNotNull(snakeHead);

        objectPoolManager.freeSnakeHead(snakeHead);

    }

    @Test
    void obtainAndFreeSnakeBodyTest() {
        SnakeBody snakeBody = objectPoolManager.obtainSnakeBody();

        assertNotNull(snakeBody);

        objectPoolManager.freeSnakeBody(snakeBody);

    }

    @Test
    void obtainAndFreeCookieTest() {
        Cookie cookie = objectPoolManager.obtainCookie();

        assertNotNull(cookie);

        objectPoolManager.freeCookie(cookie);

    }
}