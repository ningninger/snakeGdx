package org.example.map;

public abstract class TileEntity {
    protected Tile tile;

    protected EntityType type;

    protected TileEntityState entityState;

    public TileEntity(EntityType type) {
        this.type = type;
    }

    public void setTile(Tile tile) {
        this.tile = tile;
        if (this.tile != null) {
            entityState = new TileEntityState(tile);
            tile.setEntity(this);
        }
    }

    public Tile getTile() {
        return tile;
    }

    public TileEntityState getEntityState() {return entityState;}

    public void setType(EntityType type) {
        this.type = type;
    }

    public EntityType getType() {
        return type;
    }

    public static class TileEntityState {
        private int x;
        private int y;

        TileEntityState() {

        }

        TileEntityState(Tile tile) {
            this.x = tile.getX();
            this.y = tile.getY();
        }

        public int getX() {
            return x;
        }

        public int getY() {
            return y;
        }
    }
}
