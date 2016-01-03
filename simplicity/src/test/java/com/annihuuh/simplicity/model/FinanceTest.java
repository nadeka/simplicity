package com.annihuuh.simplicity.model;

import static org.junit.Assert.assertEquals;
import org.junit.Before;
import org.junit.Test;

/**
 *
 * @author annihuuh
 */
public class FinanceTest {
    Finance finance;
    
    @Before
    public void setUp() {
        finance = new Finance();
    }
    
    @Test
    public void constructor() {
        assertEquals(1000, finance.getCash(), 0.05);
        assertEquals(0, finance.getCashFlow(), 0.05);
        assertEquals(0.12, finance.getWorkerTaxRate(), 0.05);
        assertEquals(0.12, finance.getCorporateTaxRate(), 0.05);
    }
    
    @Test
    public void addCash() {
        finance.addCash(10);
        assertEquals(1010, finance.getCash(), 0.05);
        
        finance.addCash(-10);
        assertEquals(1000, finance.getCash(), 0.05);        
    }
    
    @Test
    public void removeCash() {
        finance.reduceCash(10);
        assertEquals(990, finance.getCash(), 0.05);
        
        finance.reduceCash(-10);
        assertEquals(1000, finance.getCash(), 0.05);        
    }
    
    @Test
    public void setTaxRates() {
        finance.setCorporateTaxRate(1);
        finance.setWorkerTaxRate(0);
        
        assertEquals(1, finance.getCorporateTaxRate(), 0.01);
        assertEquals(0, finance.getWorkerTaxRate(), 0.01);
    }
   
    @Test
    public void addCashFlow() {
        finance.setCashFlow(500);
        finance.addCashFlow();
        
        assertEquals(1500, finance.getCash(), 0.05);
        assertEquals(0, finance.getCashFlow(), 0.05);
    }
    
    @Test
    public void resetCashFlow() {
        finance.setCashFlow(40);
        finance.resetCashFlow();
        
        assertEquals(0, finance.getCashFlow(), 0.05);
    }
    
    @Test
    public void collectPieceTax() {
        Tile piece = new Tile(TileType.HOUSE, new Location(5, 6));
        finance.collectTax(piece, 1);
        
        assertEquals(-0.6, this.finance.getCashFlow(), 0.05);
        
        Tile piece2 = new Tile(TileType.FACTORY, new Location(6, 6));
        finance.collectTax(piece2, 1);
        
        assertEquals(4.2, this.finance.getCashFlow(), 0.05);       
    }
    
    @Test
    public void collectCitizenTax() {
        Citizen citizen = new Citizen(18);
        finance.collectTax(citizen);
        
        assertEquals(0, this.finance.getCashFlow(), 0.05);
        
        City city = new City(20, 20);
        city.getEmployment().addWorkplace(new Tile(TileType.MARKET, new Location(6, 2)));
        city.getEmployment().findWorkplaceFor(citizen);
        finance.collectTax(citizen);
        
        assertEquals(1.2, this.finance.getCashFlow(), 00.5);
    }
    
    @Test
    public void isAffordable() {
        finance.setCash(599);
        
        assertEquals(false, finance.isAffordable(TileType.FACTORY));
        assertEquals(true, finance.isAffordable(TileType.HOUSE));
        
        finance.setCash(100);
        
        assertEquals(true, finance.isAffordable(TileType.HOUSE));
        
        finance.setCash(99);
        
        assertEquals(false, finance.isAffordable(TileType.HOUSE));
    }
}
