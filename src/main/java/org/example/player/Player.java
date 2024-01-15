package org.example.player;

import org.example.MyInputProcessor;
import org.example.snake.Snake;
import org.example.world.World;

public abstract class Player {

    protected final MyInputProcessor inputProcessor;
    protected PlayerType type;

    protected Snake snake;
    protected int score;
    protected int lives;
    protected boolean isProtagonist = false;

    protected World world;

    public Player(PlayerType type) {
        this.type = type;
        this.world = World.getWorld();
        this.snake = world.obtainSnake();
        this.snake.setAlive(true);
        this.snake.setPlayer(this);
        this.score = 0;
        this.lives = 3;
        this.inputProcessor = new MyInputProcessor();
        this.inputProcessor.setSnake(snake);
    }

    public void setWorld(World world) {
        this.world = world;
    }

    public void increaseScore() {
        score++;
    }

    public int getScore() {
        return score;
    }

    public Snake getSnake() {
        return snake;
    }

    public void init() {
        snake.init();
        score = 0;
    }

    public PlayerType getType() {
        return type;
    }

    public MyInputProcessor getInputProcessor() {
        return inputProcessor;
    }

    public void setInputProcessor(Snake snake) {
        inputProcessor.setSnake(snake);
    }

    public void handleInput(int keycode) {
        snake.handleInput(keycode);
    }

    public void update() {
        snake.update();
    }

    public void dispose() {
        snake.dispose();
    }

    public void setInitialPosition(int x, int y) {
        snake.setInitialPosition(x, y);
    }

    public boolean isProtagonist() {
        return isProtagonist;
    }

    public void setProtagonist(boolean isProtagonist) {
        this.isProtagonist = isProtagonist;
    }

    public enum PlayerType {
        AI, COMMON
    }

}
