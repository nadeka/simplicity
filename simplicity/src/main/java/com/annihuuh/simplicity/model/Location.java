package com.annihuuh.simplicity.model;

import java.io.Serializable;

/**
 * Represents a position in a two-dimensional array. An attribute of Tile.
 * 
 * @see    Tile
 *
 * @author annihuuh
 */
public class Location implements Serializable {
    private int x;
    private int y;
    
    public Location(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return this.x;
    }

    public int getY() {
        return this.y;
    }
}
