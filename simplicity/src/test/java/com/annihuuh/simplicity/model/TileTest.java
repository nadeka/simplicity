package com.annihuuh.simplicity.model;

import java.util.PriorityQueue;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author annihuuh
 */
public class TileTest {
    Tile piece;
    
    @Before
    public void setUp() {
        piece = new Tile(TileType.FACTORY, new Location(5, 6));
    }

    @Test
    public void constructor() {
        assertEquals(TileType.FACTORY, piece.getType());  
        assertEquals(5, piece.getLocation().getX());
        assertEquals(6, piece.getLocation().getY());         
    }
    
    @Test
    public void compareTo() {
        Tile piece2 = new Tile(TileType.HOUSE, new Location(3, 4));        
        piece2.setSum(3);
        piece.setSum(5);      
        PriorityQueue<Tile> queue = new PriorityQueue<>();        
        queue.add(piece);
        queue.add(piece2);
        
        assertEquals(piece2, queue.peek());
        
        queue.remove(piece2);        
        piece2.setSum(40);
        queue.add(piece2);
        
        assertEquals(piece, queue.peek());          
    }
}
