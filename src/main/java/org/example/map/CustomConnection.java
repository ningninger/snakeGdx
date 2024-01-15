package org.example.map;

import com.badlogic.gdx.ai.pfa.Connection;

public class CustomConnection implements Connection<Tile> {

    private final Tile fromNode;
    private final Tile toNode;
    private final float cost;

    public CustomConnection(Tile fromNode, Tile toNode, float cost) {
        this.fromNode = fromNode;
        this.toNode = toNode;
        this.cost = cost;
    }

    @Override
    public float getCost() {
        return cost;
    }

    @Override
    public Tile getFromNode() {
        return fromNode;
    }

    @Override
    public Tile getToNode() {
        return toNode;
    }
}

