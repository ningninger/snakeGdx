package org.example.map;

import org.example.utils.MockTileEntity;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class TileEntityTest {


    @Test
    void setAndGetTile() {
        TileEntity tileEntity = new MockTileEntity();
        Tile tile = new Tile(2, 3);

        tileEntity.setTile(tile);

        assertEquals(tile, tileEntity.getTile());
        assertNotNull(tileEntity.getEntityState());
    }


    @Test
    void setAndGetType() {
        TileEntity tileEntity = new MockTileEntity();
        EntityType newType = EntityType.SnakeBody;
        tileEntity.setType(newType);

        assertEquals(newType, tileEntity.getType());
    }

}