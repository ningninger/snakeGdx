package org.example.map;

import com.badlogic.gdx.ai.pfa.Heuristic;

public class MapHeuristic implements Heuristic<Tile> {

    //欧几里得距离
    /*@Override
    public float estimate(Tile startNode, Tile endNode) {
        Vector2 startNodePos = new Vector2(startNode.getX(), startNode.getY());
        Vector2 endNodePos = new Vector2(endNode.getX(), endNode.getY());

        return startNodePos.dst(endNodePos);
    }*/

    //曼哈顿距离
    @Override
    public float estimate(Tile startNode, Tile endNode) {
        return Math.abs(startNode.getX() - endNode.getX()) + Math.abs(startNode.getY() - endNode.getY());
    }

    //对角线距离
    /*@Override
    public float estimate(Tile startNode, Tile endNode) {
        Vector2 startNodePos = new Vector2(startNode.getX(), startNode.getY());
        Vector2 endNodePos = new Vector2(endNode.getX(), endNode.getY());

        float dx = Math.abs(startNode.getX() - endNode.getX());
        float dy = Math.abs(startNode.getY() - endNode.getY());

        return Math.max(dx, dy);  // 对角线距离
    }*/


}

