package org.example.snake;

import org.example.player.Player;
import org.example.screen.PlayerScreen;
import org.example.utils.MockWorld;
import org.example.world.World;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class SnakeTest {

    private Snake snake;
    private World world;

    @BeforeEach
    void setUp() {
        world = new MockWorld();
        World.setWorld(world);
        PlayerScreen playerScreen = new PlayerScreen(null);
        world.setScreen(playerScreen);
        snake = world.obtainSnake();
        Player player = world.obtainPlayer(Player.PlayerType.AI);
        snake.setWorld(world);
        snake.setPlayer(player);
    }

    @Test
    void setAlive() {
        snake.update();
        snake.setAlive(false);
        snake.update();
    }

    @Test
    void setToCookieItem() {
        snake.setToCookieItem(false);
        snake.update();
    }

    @Test
    void setNeedsToGrow() {
        snake.setNeedsToGrow(true);
        snake.update();
        snake.dispose();
    }


}