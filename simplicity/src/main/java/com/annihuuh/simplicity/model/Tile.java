package com.annihuuh.simplicity.model;

import java.io.Serializable;

/**
 * Each tile has a changeable type, a location on the city map 
 * and a traffic level. Tiles also contain information for pathfinding.
 * 
 * @see     TileType
 * @see     Location
 * @see     Map#quickestPath(Tile, Tile)
 * 
 * @author annihuuh
 */
public class Tile implements Comparable<Tile>, Serializable {
    private TileType type;
    private Location location; 
    private int traffic;

    private int toStart;
    private int toEnd;
    private int sum;     
    private Tile parent;   
    private Tile[] adjList;
    
    public Tile(TileType type, Location location) {
        this.type = type;
        this.location = location;
        this.traffic = 0;
        
        this.toStart = Integer.MAX_VALUE;
        this.toEnd = 0;
        this.sum = Integer.MAX_VALUE;
        this.parent = null;
        this.adjList = new Tile[4];
    }
    
    public TileType getType() {
        return this.type;
    } 

    public Location getLocation() {
        return this.location;
    }

    public int getTraffic() {
        return this.traffic;
    }
    
    public void incTraffic() {
        this.traffic++;
    }
    
    public void decTraffic() {
        if ( this.traffic > 0 ) {
            this.traffic--;
        }
    } 
    
    public void resetTraffic() {
        this.traffic = 0;
    }
    
    public void changeType(TileType newType) {
        this.type = newType;
    }

    public int getToStart() {
        return this.toStart;
    }

    public void setToStart(int toStart) {
        this.toStart = toStart;
    }

    public int getToEnd() {
        return this.toEnd;
    }

    public void setToEnd(int toEnd) {
        this.toEnd = toEnd;
    }

    public int getSum() {
        return this.sum;
    }

    public void setSum(int sum) {
        this.sum = sum;
    }

    public Tile getParent() {
        return this.parent;
    }

    public void setParent(Tile parent) {
        this.parent = parent;
    } 

    public Tile[] getAdjList() {
        return this.adjList;
    }

    @Override
    public int compareTo(Tile other){
        if ( this.sum > other.sum ) {
            return 1;
        } else if ( this.sum < other.sum ){
            return -1;
        } else {
            return 0;
        }
    }
}
