package org.example.item;

import org.example.map.EntityType;
import org.example.map.TileEntity;

public class Trap extends TileEntity {

    public static final EntityType type = EntityType.Trap;

    public Trap() {
        super(type);
    }
}
