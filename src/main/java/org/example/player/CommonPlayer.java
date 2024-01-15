package org.example.player;

import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonValue;
import org.example.snake.Snake;

public class CommonPlayer extends Player implements Json.Serializable {
    public CommonPlayer() {
        super(PlayerType.COMMON);
    }

    @Override
    public void write(Json json) {
        json.writeValue("type", type);
        json.writeValue("snake", snake);
        json.writeValue("isProtagonist", isProtagonist);
        json.writeValue("score", score);
    }

    @Override
    public void read(Json json, JsonValue jsonValue) {
        type = json.readValue("type", PlayerType.class, jsonValue);
        snake = json.readValue("snake", Snake.class, jsonValue);
        isProtagonist = jsonValue.getBoolean("isProtagonist");
        score = jsonValue.getInt("score");

        snake.setPlayer(this);
    }
}
