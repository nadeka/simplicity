package com.annihuuh.simplicity.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Set;

/**
 * Contains the city map as a two-dimensional array and 
 * methods for accessing it.
 * 
 * @see    Tile
 * 
 * @author annihuuh
 */
public class Map implements Serializable {
    private Tile[][] map;
    
    public Map(int width, int height) {
        this.map = new Tile[height][width];
    }
    
    /**
     * Fills the array with grass tiles.
     * 
     * @return  a map with all the tiles and their locations
     */    
    public HashMap<Location, Tile> initializeMap() {
        HashMap<Location, Tile> tiles = new HashMap<>();
        
        for ( int i = 0; i < this.map.length; i++ ) {
            for ( int j = 0; j < this.map[0].length; j++ ) {
                Tile tile = new Tile(TileType.GRASS, new Location(j, i));
                this.map[i][j] = tile;
                tiles.put(tile.getLocation(), tile);
            }
        }     
        
        return tiles;
    }

    public Tile[][] getMap() {
        return this.map;
    }

    public int getWidth() {
        return this.map[0].length;
    }

    public int getHeight() {
        return this.map.length;
    }
    
    /**
     * Retrieves a tile from a certain location if the location is valid.
     *
     * @see Tile
     * 
     * @return      Tile    the retrieved tile
     * 
     * @param       x       the x coordinate
     * @param       y       the y coordinate
     */    
    public Tile getTile(int x, int y) { 
        if ( isValidLocation(x, y) ) {
            return this.map[y][x];
        }
        
        return null;
    }
    
    /**
     * Adds a tile to the map.
     *
     * @see Tile
     * 
     * @param       tile    the tile to add
     */        
    public void setTile(Tile tile) {
        if ( this.isValidLocation(tile.getLocation().getX(), 
                tile.getLocation().getY()) ) {
            
            this.map[tile.getLocation().getY()]
                    [tile.getLocation().getX()] = tile;
        }
    }
    
    /**
     * Checks if a location is valid.
     * 
     * @return      false if the location is out of bounds, true otherwise
     * 
     * @param       x    the x coordinate
     * @param       y    the y coordinate
     */        
    public boolean isValidLocation(int x, int y) {
        return y >= 0 && y < getHeight() && x >= 0 && x < getWidth();
    }
       
    /**
     * Checks if a tile with a certain type is in the radius of another tile.
     * 
     * @return      false if the tile type is not in the radius, true otherwise
     * 
     * @param       tile        the center tile
     * @param       tileType    the tile type to search for
     * @param       radius      the radius as an integer
     */     
    public boolean isInRadius(Tile tile, TileType tileType, int radius) {
        for ( int x = -radius; x <= radius; x++ ) {
            for ( int y = -radius; y <= radius; y++ ) {
                if ( x * x + y * y <= radius * radius ) {
                    int currentX = x + tile.getLocation().getX();
                    int currentY = y + tile.getLocation().getY();
                    
                    if ( this.isValidLocation(currentX, currentY) && 
                         this.getTile(currentX, currentY).getType() == 
                            tileType ) {
                        
                        return true;
                    }
                }   
            }
        }
        
        return false;   
    }
    
    /**
     * An implementation of the A* algorithm that finds the quickest path 
     * between two tiles. Grass tiles weigh more than road tiles.
     * 
     * @see         Tile
     * 
     * @return      the goal tile that can be used for retrieving the path 
     *              and its weight 
     * 
     * @param       source    the source tile
     * @param       goal      the goal tile
     */    
    public List<Tile> quickestPath(Tile source, Tile goal) {
        PriorityQueue<Tile> set = new PriorityQueue<>(); 
        
        this.initializeTiles(source, goal);
        set.add(source);
        
        Set<Tile> explored = new HashSet<>();
        
        while ( !set.isEmpty() ){
            Tile current = set.poll();
       
            if ( current == goal ) {
                return constructPath(goal);
            }
            
            explored.add(current);

            for ( Tile adj : adjList(current) ) {
                if ( adj == null || explored.contains(adj) ) {
                    continue;
                }
                
                int cost;

                if ( adj.getType() == TileType.GRASS ) {
                    cost = 5;
                } else if ( adj.getType().isRoad() ) {
                    cost = 1;
                } else {
                    if ( adj != goal ) {
                        continue;
                    }
                    
                    cost = 0;
                }

                if ( !set.contains(adj) || adj.getToStart() > 
                        current.getToStart() + cost ) {
                    
                    if ( set.contains(adj) ) {
                        set.remove(adj);
                    }

                    adj.setToStart(current.getToStart() + cost);
                    adj.setSum(adj.getToStart() + adj.getToEnd());
                    adj.setParent(current);
                    set.add(adj);
                }
                
            }
        }

        return this.constructPath(goal);           
    }
    
    private void initializeTiles(Tile source, Tile goal) {
        for ( int i = 0; i < this.getHeight(); i++ ) {
            for ( int j = 0; j < this.getWidth(); j++ ) {
                Tile tile = this.getTile(j, i);
                tile.setToEnd(Math.abs(i - goal.getLocation().getY()) + 
                              Math.abs(j - goal.getLocation().getX()));
                tile.setToStart(Integer.MAX_VALUE);
                tile.setSum(tile.getToEnd() + tile.getToStart());
                tile.setParent(null);
            }
        }    
        
        source.setToStart(0);
        source.setSum(source.getToEnd() + source.getToStart());
    }
     
    private Tile[] adjList(Tile tile) {
        Tile[] adjList = tile.getAdjList();
        
        adjList[0] = null; adjList[1] = null; 
        adjList[2] = null; adjList[3] = null;

        if ( isValidLocation(tile.getLocation().getX(), 
                             tile.getLocation().getY() + 1) ) {
            
            adjList[0] = this.getTile(tile.getLocation().getX(), 
                                     tile.getLocation().getY() + 1);
        }

        if ( isValidLocation(tile.getLocation().getX() + 1, 
                             tile.getLocation().getY()) ) {
            
            adjList[1] = this.getTile(tile.getLocation().getX() + 1, 
                                      tile.getLocation().getY());
        }   

        if ( isValidLocation(tile.getLocation().getX() - 1, 
                             tile.getLocation().getY()) ) {
            
            adjList[2] = this.getTile(tile.getLocation().getX() - 1, 
                                      tile.getLocation().getY());
        }  

        if ( isValidLocation(tile.getLocation().getX(), 
                             tile.getLocation().getY() - 1) ) {
            
            adjList[3] = this.getTile(tile.getLocation().getX(), 
                                      tile.getLocation().getY() - 1);
        }  
        
        return adjList;
    }
    
    private List<Tile> constructPath(Tile goal) {
        List<Tile> path = new ArrayList<>();
        
        for ( Tile tile = goal; tile != null; tile = tile.getParent() ) {
            path.add(tile);
        }         
 
        return path;          
    }
}
