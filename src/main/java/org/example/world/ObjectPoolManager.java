package org.example.world;

import com.badlogic.gdx.utils.Pool;
import org.example.item.Cookie;
import org.example.player.AIPlayer;
import org.example.player.CommonPlayer;
import org.example.snake.Snake;
import org.example.snake.SnakeBody;
import org.example.snake.SnakeHead;

public class ObjectPoolManager {

    private final Pool<Snake> snakePool = new Pool<Snake>() {
        @Override
        protected Snake newObject() {
            return new Snake();
        }
    };

    private final Pool<SnakeHead> snakeHeadPool = new Pool<SnakeHead>() {
        @Override
        protected SnakeHead newObject() {
            return new SnakeHead();
        }
    };

    private final Pool<SnakeBody> snakeBodyPool = new Pool<SnakeBody>() {
        @Override
        protected SnakeBody newObject() {
            return new SnakeBody();
        }
    };

    private final Pool<Cookie> cookiePool = new Pool<Cookie>() {
        @Override
        protected Cookie newObject() {
            return new Cookie();
        }
    };

    private final Pool<CommonPlayer> commonPlayerPool = new Pool<CommonPlayer>() {
        @Override
        protected CommonPlayer newObject() {
            return new CommonPlayer();
        }
    };

    private final Pool<AIPlayer> aiPlayerPool = new Pool<AIPlayer>() {
        @Override
        protected AIPlayer newObject() {
            return new AIPlayer();
        }
    };

    public CommonPlayer obtainCommonPlayer() {
        return commonPlayerPool.obtain();
    }

    public void freeCommonPlayer(CommonPlayer commonPlayer) {
        commonPlayerPool.free(commonPlayer);
    }

    public AIPlayer obtainAIPlayer() {
        return aiPlayerPool.obtain();
    }

    public void freeAIPlayer(AIPlayer aiPlayer) {
        aiPlayerPool.free(aiPlayer);
    }

    public Snake obtainSnake() {
        return snakePool.obtain();
    }

    public void freeSnake(Snake snake) {
        snakePool.free(snake);
    }

    public SnakeHead obtainSnakeHead() {
        return snakeHeadPool.obtain();
    }

    public void freeSnakeHead(SnakeHead snakeHead) {
        snakeHeadPool.free(snakeHead);
    }

    public SnakeBody obtainSnakeBody() {
        return snakeBodyPool.obtain();
    }

    public void freeSnakeBody(SnakeBody snakeBody) {
        snakeBodyPool.free(snakeBody);
    }

    public Cookie obtainCookie() {
        return cookiePool.obtain();
    }

    public void freeCookie(Cookie cookie) {
        cookiePool.free(cookie);
    }
}