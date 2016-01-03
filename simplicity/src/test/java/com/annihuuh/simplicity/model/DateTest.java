package com.annihuuh.simplicity.model;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author annihuuh
 */
public class DateTest {
    Date date;

    @Before
    public void setUp() {
        this.date = new Date();
    }
    
    @Test
    public void constructor() {
        assertEquals(1, this.date.getDay());
        assertEquals(0, this.date.getMonth());
        assertEquals(2000, this.date.getYear());
    }
    
    @Test
    public void tick() {
        this.date.tick();
        
        assertEquals(2, this.date.getDay());
        assertEquals(0, this.date.getMonth());
        assertEquals(2000, this.date.getYear());
        
        for ( int i = 1; i <= 28; i++ ) {
            this.date.tick();
        }
        
        assertEquals(30, this.date.getDay());
        assertEquals(0, this.date.getMonth());
        assertEquals(2000, this.date.getYear());        
        
        this.date.tick();
        
        assertEquals(1, this.date.getDay());
        assertEquals(1, this.date.getMonth());
        assertEquals(2000, this.date.getYear());
        
        for ( int i = 1; i <= 24 * 30; i++ ) {
            this.date.tick();
        }
        
        assertEquals(1, this.date.getDay());
        assertEquals(1, this.date.getMonth());
        assertEquals(2002, this.date.getYear()); 
        
        for ( int i = 1; i <= 24 * 30 * 12; i++ ) {
            this.date.tick();
        }
        
        assertEquals(1, this.date.getDay());
        assertEquals(1, this.date.getMonth());
        assertEquals(2026, this.date.getYear());         
    }
    
    @Test
    public void string() {
        assertEquals("Jan 1, 2000", this.date.toString());
    }
}