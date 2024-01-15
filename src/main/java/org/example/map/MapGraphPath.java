package org.example.map;

import com.badlogic.gdx.ai.pfa.GraphPath;
import com.badlogic.gdx.utils.Array;
import org.jetbrains.annotations.NotNull;

import java.util.Iterator;

public class MapGraphPath implements GraphPath<Tile> {

    private final Array<Tile> nodes = new Array<>();

    @Override
    public int getCount() {
        return nodes.size;
    }

    @Override
    public Tile get(int index) {
        return nodes.get(index);
    }

    @Override
    public void add(Tile tile) {
        nodes.add(tile);
    }

    @Override
    public void clear() {
        nodes.clear();
    }

    @Override
    public void reverse() {

    }

    @Override
    public @NotNull Iterator<Tile> iterator() {
        return new PathIterator();
    }


    private class PathIterator implements Iterator<Tile> {
        private int currentIndex = 0;

        @Override
        public boolean hasNext() {
            return currentIndex < getCount();
        }

        @Override
        public Tile next() {
            return get(currentIndex++);
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException("Remove operation is not supported.");
        }
    }
}
