package org.example.map;

import com.badlogic.gdx.ai.pfa.Connection;
import com.badlogic.gdx.ai.pfa.indexed.IndexedGraph;
import com.badlogic.gdx.utils.Array;

public class MapGraph implements IndexedGraph<Tile> {

    private final GameMap map;

    public MapGraph(GameMap map) {
        this.map = map;
    }

    @Override
    public int getIndex(Tile tile) {
        assert tile != null;
        return tile.getIndex();
    }

    @Override
    public int getNodeCount() {
        return map.getHeight() * map.getWidth();
    }

    @Override
    public Array<Connection<Tile>> getConnections(Tile tile) {
        Array<Connection<Tile>> connections = new Array<>();

        int x = tile.getX();
        int y = tile.getY();

        int maxWidth = map.getWidth();
        int maxHeight = map.getHeight();

        addConnectionIfValid(connections, (x - 1 + maxWidth)%maxWidth, y, tile);
        addConnectionIfValid(connections, (x + 1)%maxWidth, y, tile);
        addConnectionIfValid(connections, x, (y - 1 + maxHeight)%maxHeight, tile);
        addConnectionIfValid(connections, x, (y + 1)%maxHeight, tile);

        return connections;
    }

    private void addConnectionIfValid(Array<Connection<Tile>> connections, int x, int y, Tile sourceTile) {
        if (isValidTile(x, y)) {
            Tile targetTile = map.getTile(x, y);
            if (map.isPassable(sourceTile, targetTile)) {
                float cost = computeCost(sourceTile, targetTile);
                Connection<Tile> connection = new CustomConnection(sourceTile, targetTile, cost);
                connections.add(connection);
            }
        }
    }

    private float computeCost(Tile ignoredSourceTile, Tile ignoredTargetTile) {
        return 1f;
    }

    private boolean isValidTile(int x, int y) {
        return x >= 0 && x < map.getWidth() && y >= 0 && y < map.getHeight()
                && map.getTileType(x, y) != EntityType.Trap;
    }
}
