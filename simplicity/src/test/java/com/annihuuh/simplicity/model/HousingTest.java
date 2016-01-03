package com.annihuuh.simplicity.model;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author annihuuh
 */
public class HousingTest {
    Housing housing;
    Map map;
    Tile house1;
    Tile house2;
    Family household;
    Family household2;

    @Before
    public void setUp() {
        housing = new Housing();
        map = new Map(20, 20);
        house1 = new Tile(TileType.HOUSE, new Location(4, 5));
        house2 = new Tile(TileType.HOUSE, new Location(4, 4));
        
        household = new Family();
        household2 = new Family();
        household.addCitizen(new Citizen(18));
        household.addCitizen(new Citizen(200));
    }
    
    @Test
    public void getResidentsOf() {
        assertEquals(null, housing.getResidentsOf(house1));
        
        housing.addHouse(house1);
        
        assertEquals(null, housing.getResidentsOf(house1));
        
        housing.findHouseFor(household);
        
        assertEquals(household, housing.getResidentsOf(house1));
    }

    @Test
    public void addHouse() {
        assertEquals(0, housing.getHouses().size());
        
        housing.addHouse(house1);
        
        assertEquals(1, housing.getHouses().size());
        
        housing.addHouse(house2);
        
        assertEquals(2, housing.getHouses().size());
    }
    
    @Test
    public void removeHouse() {
        housing.addHouse(house1);
        housing.removeHouse(house1);
        
        assertEquals(0, housing.getHousesAndResidents().size());
        
        housing.addHouse(house1);
        housing.findHouseFor(household);
        housing.removeHouse(house1);
        
        assertEquals(null, household.getHouse());       
    }
    
    @Test
    public void addResidents() {
        housing.addHouse(house1);
        housing.addResidents(house1, household);
        
        assertEquals(household, housing.getResidentsOf(house1));
        assertEquals(house1, household.getHouse());
    }
    
    @Test
    public void removeResidents() {
        housing.addHouse(house1); 
        housing.findHouseFor(household);
        housing.unhouseFamily(house1);
        
        assertEquals(null, housing.getResidentsOf(house1));
        assertEquals(null, household.getHouse());
    }
    
    @Test
    public void isSuitable() {
        housing.addHouse(house1);
        housing.findHouseFor(household);     
        housing.findHouseFor(household2);
        
        assertEquals(household, housing.getResidentsOf(house1)); 
        
        housing.unhouseFamily(house1);
        
        for ( int i = 0; i < 11; i++ ) {
            household2.addCitizen(new Citizen(2));
        }
        
        assertEquals(false, housing.findHouseFor(household2)); 
    }   
}
