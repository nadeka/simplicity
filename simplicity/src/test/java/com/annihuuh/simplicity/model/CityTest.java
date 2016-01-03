package com.annihuuh.simplicity.model;

import java.util.ArrayList;
import java.util.List;
import static org.junit.Assert.assertEquals;
import org.junit.Before;
import org.junit.Test;

/**
 *
 * @author annihuuh
 */
public class CityTest {
    City city;
    Citizen citizen1;
    Citizen citizen2;

    @Before
    public void setUp() {
        this.city = new City(20, 20);
        this.city.getFinance().addCash(9000);
        this.citizen1 = new Citizen(43);
        this.citizen2 = new Citizen(30);
    }       
    
    @Test
    public void build() {
        city.build(TileType.HOUSE, 5, 6); 
        
        assertEquals(1, city.getHousing().getHouses().size());
        assertEquals(TileType.HOUSE, city.getMap().getTile(5, 6).getType());
        assertEquals(9900, city.getFinance().getCash(), 0.01);
        
        city.build(TileType.ROAD, 0, 0);
        
        assertEquals(TileType.ROAD, city.getMap().getTile(0, 0).getType());
        assertEquals(9890, city.getFinance().getCash(), 0.01);
        
        city.build(TileType.MARKET, 19, 19);
        
        assertEquals(1, city.getEmployment().getWorkplacesAndEmployees().size());
        assertEquals(TileType.MARKET, city.getMap().getTile(19, 19).getType());
        assertEquals(9690, city.getFinance().getCash(), 0.01);
    }
    
    @Test
    public void destroy() {
        city.build(TileType.HOUSE, 5, 6);
        city.destroy(5, 6);
        
        assertEquals(0, city.getHousing().getHouses().size());
        assertEquals(TileType.GRASS, city.getMap().getTile(5, 6).getType());
        assertEquals(9950, city.getFinance().getCash(), 0.01);
        
        city.build(TileType.ROAD, 0, 0);
        city.destroy(0, 0); 
        
        assertEquals(TileType.GRASS, city.getMap().getTile(0, 0).getType()); 
        assertEquals(9945, city.getFinance().getCash(), 0.01);
        
        city.build(TileType.HOSPITAL, 19, 19);
        city.destroy(19, 19);
        
        assertEquals(0, city.getEmployment().getWorkplacesAndEmployees().size());
        assertEquals(TileType.GRASS, city.getMap().getTile(19, 19).getType());
        assertEquals(9545, city.getFinance().getCash(), 0.01);
    }   
    
    @Test
    public void removeFamily() {
        Family family = new Family();
        family.addCitizen(citizen2);
        city.getFamilies().add(family);
 
        city.build(TileType.HOSPITAL, 4, 3);
        city.build(TileType.HOUSE, 5, 2);
        
        Tile workplace = city.getMap().getTile(4, 3);
        Tile house = city.getMap().getTile(5, 2);
        
        city.getEmployment().findWorkplaceFor(citizen2);
        city.getHousing().findHouseFor(family);
        
        assertEquals(1, city.getFamilies().size());
        assertEquals(family, city.getHousing().getResidentsOf(house));
        assertEquals(1, city.getEmployment().getEmployeesOf(workplace).size());       
        
        city.removeFamily(family);
        
        assertEquals(0, city.getFamilies().size());
        assertEquals(0, city.getEmployment().getEmployeesOf(workplace).size());
        assertEquals(null, city.getHousing().getResidentsOf(house));
    }
    
    @Test
    public void createFamily() {
        assertEquals(0, city.getFamilies().size());
        
        city.createFamily(4);
        
        assertEquals(1, city.getFamilies().size());
        assertEquals(4, city.getFamilies().get(0).size());
        
        city.createFamily(new ArrayList<Citizen>());
        
        assertEquals(2, city.getFamilies().size());
        assertEquals(0, city.getFamilies().get(1).size());
        
        List<Citizen> citizens = new ArrayList<>();
        citizens.add(citizen1);
        citizens.add(citizen2);
        
        city.createFamily(citizens);
        
        assertEquals(3, city.getFamilies().size());
        assertEquals(2, city.getFamilies().get(2).size());        
    }
    
    @Test
    public void update() {
        city.build(TileType.FACTORY, 3, 3);
        city.build(TileType.HOUSE, 1, 1);
        
        Family family = new Family();
        
        for ( int i = 17; i <= 22; i++ ) {
            family.addCitizen(new Citizen(i));
        }
        
        family.addCitizen(new Citizen(66));
        city.getFamilies().add(family);
        city.update();
        
        assertEquals(5, city.getMetrics().getValue("Young adults"), 0.01);
        assertEquals(1, city.getMetrics().getValue("Children"), 0.01);
        assertEquals(1, city.getMetrics().getValue("Elderly"), 0.01);
        assertEquals(7, city.getMetrics().getValue("Population size"), 0.01);
        assertEquals(0, city.getMetrics().getValue("Unemployed"), 0.01);
        assertEquals(0, city.getMetrics().getValue("Employed"), 0.01);
        assertEquals(0, city.getMetrics().getValue("Homeless"), 0.01);
        assertEquals(1, city.getMetrics().getValue("Industries"), 0.01);
        assertEquals(1, city.getMetrics().getValue("Residences"), 0.01);
        assertEquals(0, city.getMetrics().getValue("Commerces"), 0.01);        
        
        city.update();
        
        assertEquals(5, city.getMetrics().getValue("Young adults"), 0.01);
        assertEquals(1, city.getMetrics().getValue("Children"), 0.01);
        assertEquals(1, city.getMetrics().getValue("Elderly"), 0.01);
        assertEquals(7, city.getMetrics().getValue("Population size"), 0.01);
        assertEquals(0, city.getMetrics().getValue("Unemployed"), 0.01);
        assertEquals(5, city.getMetrics().getValue("Employed"), 0.01);
        assertEquals(0, city.getMetrics().getValue("Homeless"), 0.01);  
        assertEquals(1, city.getMetrics().getValue("Industries"), 0.01);
        assertEquals(1, city.getMetrics().getValue("Residences"), 0.01);
        assertEquals(0, city.getMetrics().getValue("Commerces"), 0.01);
        
        family.removeCitizen(family.getCitizens().get(0));
        
        city.update();
        
        assertEquals(0, city.getMetrics().getValue("Children"), 0.01);
        assertEquals(6, city.getMetrics().getValue("Population size"), 0.01);
        
        family.removeCitizen(family.getCitizens().get(0));
        
        city.update();
        
        assertEquals(4, city.getMetrics().getValue("Young adults"), 0.01);
        assertEquals(5, city.getMetrics().getValue("Population size"), 0.01);
        assertEquals(4, city.getMetrics().getValue("Employed"), 0.01);
        
        family.removeCitizen(family.getCitizens().get(4));
        
        city.update();
        
        assertEquals(0, city.getMetrics().getValue("Elderly"), 0.01);
        assertEquals(4, city.getMetrics().getValue("Population size"), 0.01); 
        assertEquals(4, city.getMetrics().getValue("Employed"), 0.01);
        
        city.build(TileType.MARKET, 10, 3);
        city.destroy(3, 3);
        
        city.update();
        
        assertEquals(1, city.getMetrics().getValue("Commerces"), 0.01);
        assertEquals(0, city.getMetrics().getValue("Industries"), 0.01);
        assertEquals(0, city.getMetrics().getValue("Employed"), 0.01);
        assertEquals(0, city.getMetrics().getValue("Unemployed"), 0.01);
        
        city.update();
        
        assertEquals(4, city.getMetrics().getValue("Employed"), 0.01);
        assertEquals(0, city.getMetrics().getValue("Unemployed"), 0.01);        
    }
    
}
