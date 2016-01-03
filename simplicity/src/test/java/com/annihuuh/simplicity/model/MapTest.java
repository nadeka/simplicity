package com.annihuuh.simplicity.model;

import java.util.List;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author annihuuh
 */
public class MapTest {
    Map map; Tile tile1; Tile tile2;
    
    @Before
    public void setUp() {
        map = new Map(20, 30);
        map.initializeMap();
        tile1 = new Tile(TileType.HOUSE, new Location(19, 29));
        tile2 = new Tile(TileType.FACTORY, new Location(0, 0));        
    }
    
    @Test
    public void constructor() {
        assertEquals(20, map.getWidth());
        assertEquals(30, map.getHeight());
    }
    
    @Test
    public void getAndSetTile() {
        map.setTile(tile1);
        map.setTile(tile2); 
        
        assertEquals(tile1, map.getTile(19, 29));
        assertEquals(tile2, map.getTile(0, 0));
    }   
    
    @Test
    public void isValidLocation() {
        assertEquals(true, map.isValidLocation(19, 29));
        assertEquals(true, map.isValidLocation(0, 0));
        assertEquals(true, map.isValidLocation(10, 10));
        assertEquals(true, map.isValidLocation(3, 19));
        assertEquals(false, map.isValidLocation(20, 29));
        assertEquals(false, map.isValidLocation(19, 30));
        assertEquals(false, map.isValidLocation(-1, 0));
        assertEquals(false, map.isValidLocation(0, -1));
        assertEquals(false, map.isValidLocation(6, -4));        
    }
    
    @Test
    public void shortestPath() {
        tile1 = new Tile(TileType.HOUSE, new Location(4, 5));
        tile2 = new Tile(TileType.FACTORY, new Location(3, 4));     
        
        map.setTile(tile1);
        map.setTile(tile2);
        
        List<Tile> path = map.quickestPath(tile1, tile2);
        
        assertEquals(3, path.size());
        assertEquals(5, path.get(0).getToStart());
    }
    
    @Test
    public void isInRadius() {
        tile1 = new Tile(TileType.HOUSE, new Location(15, 16));
        tile2 = new Tile(TileType.FACTORY, new Location(15, 15));     
        
        map.setTile(tile1);
        map.setTile(tile2);
        
        assertEquals(true, map.isInRadius(tile1, TileType.FACTORY, 1));
        assertEquals(false, map.isInRadius(tile1, TileType.FACTORY, 0));
        
        tile1 = new Tile(TileType.HOUSE, new Location(4, 5));
        tile2 = new Tile(TileType.FACTORY, new Location(5, 6));     
        
        map.setTile(tile1);
        map.setTile(tile2);  
        
        assertEquals(true, map.isInRadius(tile1, TileType.FACTORY, 2));
        assertEquals(false, map.isInRadius(tile1, TileType.FACTORY, 0));
        assertEquals(false, map.isInRadius(tile1, TileType.FACTORY, 1));
        assertEquals(true, map.isInRadius(tile1, TileType.FACTORY, 10));
        
        tile1 = new Tile(TileType.HOUSE, new Location(8, 9));
        tile2 = new Tile(TileType.FACTORY, new Location(10, 11));     
        
        map.setTile(tile1);
        map.setTile(tile2);  
        
        assertEquals(true, map.isInRadius(tile1, TileType.FACTORY, 3));
        assertEquals(false, map.isInRadius(tile1, TileType.FACTORY, 2));
        assertEquals(true, map.isInRadius(tile1, TileType.FACTORY, 4));
        
        tile1 = new Tile(TileType.HOUSE, new Location(0, 0));
        tile2 = new Tile(TileType.FACTORY, new Location(0, 0));     
        
        map.setTile(tile1);
        map.setTile(tile2);  
        
        assertEquals(true, map.isInRadius(tile1, TileType.FACTORY, 0)); 
        assertEquals(false, map.isInRadius(tile1, TileType.FACTORY, -1));
        
        map = new Map(20, 30);
        map.initializeMap();

        tile1 = new Tile(TileType.HOUSE, new Location(17, 29));
        tile2 = new Tile(TileType.FACTORY, new Location(16, 25));     
        
        map.setTile(tile1);
        map.setTile(tile2);  
        
        assertEquals(false, map.isInRadius(tile1, TileType.FACTORY, 0));
        assertEquals(true, map.isInRadius(tile1, TileType.FACTORY, 5));
        
        tile1 = new Tile(TileType.HOUSE, new Location(4, 4));
        tile2 = new Tile(TileType.FACTORY, new Location(5, 5));     
        
        map.setTile(tile1);
        map.setTile(tile2);  
        
        assertEquals(false, map.isInRadius(tile1, TileType.FACTORY, 1));
        assertEquals(true, map.isInRadius(tile1, TileType.FACTORY, 2));
        
        tile1 = new Tile(TileType.HOUSE, new Location(0, 0));
        tile2 = new Tile(TileType.FACTORY, new Location(0, 2));     
        
        map.setTile(tile1);
        map.setTile(tile2);  
        
        assertEquals(false, map.isInRadius(tile1, TileType.FACTORY, 1));
        assertEquals(true, map.isInRadius(tile1, TileType.FACTORY, 3));
    }
}
