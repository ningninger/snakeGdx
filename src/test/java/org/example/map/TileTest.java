package org.example.map;

import org.example.item.Trap;
import org.example.snake.Direction;
import org.example.utils.MockWorld;
import org.example.world.World;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class TileTest {

    private Tile tile;

    private GameMap map;

    private World world;

    @BeforeEach
    void setUp() {
        world = new MockWorld();
        map = new GameMap(world);
    }

    @Test
    void tryLock() {
    }

    @Test
    void unlock() {
    }

    @Test
    void getNeighbour() {

        int x = 2;
        int y = 3;
        tile = map.getTile(x, y);

        Tile neighbourUp = tile.getNeighbour(Direction.UP);
        assertEquals((y + 1) % map.getHeight(), neighbourUp.getY());
        assertEquals(x, neighbourUp.getX());

        Tile neighbourDown = tile.getNeighbour(Direction.DOWN);
        assertEquals((y - 1 + map.getHeight()) % map.getHeight(), neighbourDown.getY());
        assertEquals(x, neighbourDown.getX());

        Tile neighbourLeft = tile.getNeighbour(Direction.LEFT);
        assertEquals(y, neighbourLeft.getY());
        assertEquals((x - 1 + map.getWidth()) % map.getWidth(), neighbourLeft.getX());

        Tile neighbourRight = tile.getNeighbour(Direction.RIGHT);
        assertEquals(y, neighbourRight.getY());
        assertEquals((x + 1) % map.getWidth(), neighbourRight.getX());

    }

    @Test
    void setAndGetEntity() {
        tile = map.getBlankTile();
        TileEntity entity = new Trap();
        tile.setEntity(entity);

        assertEquals(entity, tile.getEntity());
    }

    @Test
    void setAndGetMap() {
        tile = map.getBlankTile();
        tile.setMap(map);

        assertEquals(map, tile.getMap());

    }

    @Test
    void getIndex() {
        int x = 2;
        int y = 3;
        tile = map.getTile(x, y);

        int expectedIndex = x * map.getHeight() + y;
        int actualIndex = tile.getIndex();

        assertEquals(expectedIndex, actualIndex);
    }


    @Test
    void getType() {
        tile = new Tile();
        assertEquals(EntityType.Blank, tile.getType());

        TileEntity entity = new Trap();
        tile.setEntity(entity);
        assertEquals(entity.getType(), tile.getType());

    }

    @Test
    void getTileState() {
        int x = 2;
        int y = 3;
        tile = map.getTile(x, y);

        Tile.TileState state = tile.getTileState();

        assertEquals(x, state.getX());
        assertEquals(y, state.getY());
    }

}