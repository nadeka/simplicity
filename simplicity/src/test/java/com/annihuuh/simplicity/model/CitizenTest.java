package com.annihuuh.simplicity.model;

import java.util.ArrayList;
import static org.junit.Assert.assertEquals;
import org.junit.Before;
import org.junit.Test;

/**
 *
 * @author annihuuh
 */
public class CitizenTest {
    City city;
    Citizen citizen;
    Tile workplace;
    Tile house;

    @Before
    public void setUp() {
        city = new City(20, 20);
        city.getFinance().addCash(10000);
        citizen = new Citizen(18);
        workplace = new Tile(TileType.FACTORY, new Location(4, 5));
        house = new Tile(TileType.HOUSE, new Location(3, 4));
    }

    @Test
    public void constructor() {
        assertEquals(null, citizen.getHouse());
        assertEquals(null, citizen.getWorkplace());        
        assertEquals(false, citizen.hasJob());
        assertEquals(false, citizen.hasHouse());      
        assertEquals(18, citizen.getAge());
        assertEquals(0, citizen.getHappiness(), 0.01);
        assertEquals(0, citizen.getSalary());       
    }
    
    @Test
    public void age() {        
        citizen.age();
        
        assertEquals(19, citizen.getAge());
    }     
     
    @Test
    public void setJob() {
        city.build(TileType.FACTORY, 4, 5);
        Tile wp = city.getMap().getTile(4, 5);
        city.getEmployment().findWorkplaceFor(citizen);
        
        assertEquals(wp, citizen.getWorkplace());       
        assertEquals(true, citizen.hasJob());
        assertEquals(15, citizen.getSalary());
        assertEquals(new ArrayList<Tile>(), citizen.getCommute());
        assertEquals(0, citizen.getCommuteTime());
     }
     
     @Test
     public void setHouse() {
        city.build(TileType.HOUSE, 3, 4);
        Tile h = city.getMap().getTile(3, 4);
        Family household = new Family();
        household.addCitizen(citizen);
        city.getHousing().findHouseFor(household);
         
        assertEquals(h, citizen.getHouse());
        assertEquals(true, citizen.hasHouse()); 
        assertEquals(new ArrayList<Tile>(), citizen.getCommute());  
        assertEquals(0, citizen.getCommuteTime());
     }
     
     @Test
     public void leaveWhenOld() {     
        assertEquals(false, citizen.leaves());
        assertEquals(18, citizen.getAge());
        
        for ( int i = 0; i < 71; i++ ) {
            citizen.age();
        }
        
        assertEquals(false, citizen.leaves());
        
        citizen.age();
        
        assertEquals(true, citizen.leaves()); 
    }
     
    @Test
    public void leaves() {
        assertEquals(false, citizen.leaves());

        citizen.incMonthsHomeless(1);

        assertEquals(false, citizen.leaves());
        
        citizen.incMonthsHomeless(1);
        
        assertEquals(false, citizen.leaves());
        
        citizen.incMonthsHomeless(1);
        
        assertEquals(false, citizen.leaves());     
        
        citizen.incMonthsUnemployed(1);
        
        assertEquals(false, citizen.leaves());          
        
        citizen.incMonthsUnemployed(1);
        
        assertEquals(true, citizen.leaves());           
    }
}
