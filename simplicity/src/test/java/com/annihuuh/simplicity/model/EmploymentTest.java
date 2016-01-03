package com.annihuuh.simplicity.model;

import java.util.ArrayList;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author annihuuh
 */
public class EmploymentTest {
    Employment employment;
    Map map;
    Tile workplace1;
    Tile workplace2;
    Citizen employee1;
    Citizen employee2;

    @Before
    public void setUp() {
        employment = new Employment();
        map = new Map(20,20);
        workplace1 = new Tile(TileType.MARKET, new Location(4, 5));
        workplace2 = new Tile(TileType.MARKET, new Location(1, 1));
        employee1 = new Citizen(18);
        employee2 = new Citizen(18);
    }

    @Test
    public void addWorkplace() {
        assertEquals(0, employment.getWorkplacesAndEmployees().size());
        
        employment.addWorkplace(workplace1);
        
        assertEquals(1, employment.getWorkplacesAndEmployees().size());
        
        employment.addWorkplace(workplace2);
        
        assertEquals(2, employment.getWorkplacesAndEmployees().size());
    }
    
    @Test
    public void addEmployee() {
        employment.addWorkplace(workplace1);
        employment.addEmployee(workplace1, employee1);
        
        assertEquals(1, employment.getEmployeesOf(workplace1).size());
    }
    
    @Test
    public void removeWorkplace() {
        employment.addWorkplace(workplace1);
        employment.findWorkplaceFor(employee1);
        employment.findWorkplaceFor(employee2);
        
        assertEquals(1, employment.getWorkplacesAndEmployees().size()); 
        assertEquals(true, employee1.hasJob());
        assertEquals(true, employee2.hasJob()); 
        
        employment.removeWorkplace(workplace1);       
        
        assertEquals(0, employment.getWorkplacesAndEmployees().size());  
        assertEquals(false, employee1.hasJob());
        assertEquals(false, employee2.hasJob());
    }
    
    @Test
    public void removeEmployee() {
        employment.addWorkplace(workplace1); 
        employment.findWorkplaceFor(employee1);
        employment.findWorkplaceFor(employee2);
        employment.removeEmployee(workplace1, employee1);
        
        assertEquals(1, employment.getEmployeesOf(workplace1).size());
        assertEquals(false, employee1.hasJob());
        
        employment.removeEmployee(workplace1, employee2);
        
        assertEquals(0, employment.getEmployeesOf(workplace1).size());
        assertEquals(false, employee2.hasJob());
    }
    
    @Test
    public void removeEmployees() {
        employment.addWorkplace(workplace1); 
        employment.findWorkplaceFor(employee1);
        employment.findWorkplaceFor(employee2);
        employment.removeEmployees(workplace1);  
        
        assertEquals(new ArrayList<Citizen>(), employment.getEmployeesOf(workplace1));
        assertEquals(false, employee1.hasJob());
        assertEquals(false, employee2.hasJob());
    }
    
    @Test
    public void getEmployeesOf() {
        assertEquals(new ArrayList<Citizen>(), employment.getEmployeesOf(workplace1));
        
        employment.addWorkplace(workplace1);
        
        assertEquals(0, employment.getEmployeesOf(workplace1).size());
        assertEquals(1, employment.getWorkplacesAndEmployees().size());
    }
}
