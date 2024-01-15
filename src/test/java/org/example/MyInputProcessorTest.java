package org.example;

import com.badlogic.gdx.Input;
import org.example.snake.Snake;
import org.example.utils.MockWorld;
import org.example.world.World;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class MyInputProcessorTest {

    private MyInputProcessor myInputProcessor;

    private Snake snake;

    @BeforeEach
    void setUp() {
        myInputProcessor = new MyInputProcessor();
        World world = new MockWorld();
        World.setWorld(world);
        snake = world.obtainSnake();
        myInputProcessor.setSnake(snake);

    }

    @Test
    void keyDown() {
        myInputProcessor.keyDown(Input.Keys.RIGHT);
        myInputProcessor.keyDown(Input.Keys.UP);
        myInputProcessor.keyDown(Input.Keys.LEFT);
        myInputProcessor.keyDown(Input.Keys.DOWN);

    }

    @Test
    void keyUp() {
        myInputProcessor.keyDown(0);
    }
}