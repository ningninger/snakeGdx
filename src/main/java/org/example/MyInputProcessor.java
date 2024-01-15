package org.example;

import com.badlogic.gdx.InputAdapter;
import org.example.snake.Snake;

import java.util.Objects;

public class MyInputProcessor extends InputAdapter {

    public Snake snake;

    public void setSnake(Snake snake) {
        this.snake = Objects.requireNonNull(snake, "Snake cannot be null");
    }

    @Override
    public boolean keyDown(int keycode) {
        Objects.requireNonNull(snake, "Snake cannot be null");

        snake.handleInput(keycode);

        return true;
    }

    @Override
    public boolean keyUp(int keycode) {
        return true;
    }
}
