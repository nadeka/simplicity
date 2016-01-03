package com.annihuuh.simplicity.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

/**
 * Maintains a map of workplaces and their employees and 
 * finds jobs for citizens.
 * 
 * @see     Tile
 * @see     Citizen
 *
 * @author annihuuh
 */
public class Employment implements Serializable {
    private HashMap<Tile, List<Citizen>> workplacesAndEmployees;
    
    public Employment() {
        this.workplacesAndEmployees = new HashMap<>();
    }

    public HashMap<Tile, List<Citizen>> getWorkplacesAndEmployees() {
        return this.workplacesAndEmployees;
    }
    
    public Set<Tile> getWorkplaces() {
        return this.workplacesAndEmployees.keySet();
    }

    /**
     * Retrieves employees of a workplace.
     * 
     * @param   workplace   the map key
     *
     * @return  a list of the employees if the keyset contains the workplace, 
     *          null otherwise
     */        
    public List<Citizen> getEmployeesOf(Tile workplace) {
        if ( this.workplacesAndEmployees.containsKey(workplace) ) {
            return this.workplacesAndEmployees.get(workplace);
        }
        
        return new ArrayList<>();
    }
    
    /**
     * Adds a new workplace to the keyset.
     * 
     * @param   workplace   the workplace to add
     */     
    public void addWorkplace(Tile workplace) {
        this.workplacesAndEmployees.put(workplace, new ArrayList<Citizen>());
    }
    
    /**
     * Adds an employee to a workplace.
     * 
     * @see     Citizen#startJob(Tile)
     * 
     * @param   workplace   the new workplace of the employee
     * @param   employee    the employee to add
     */        
    public void addEmployee(Tile workplace, Citizen employee) {
        if ( this.workplacesAndEmployees.containsKey(workplace) ) {
            this.getEmployeesOf(workplace).add(employee);
            employee.startJob(workplace);
        }
    }

    /**
     * Removes a workplace from the keyset.
     * 
     * @see     Citizen#quitJob()
     * @see     #removeWorkplace(Tile)
     * 
     * @param   workplace   the workplace to remove
     */    
    public void removeWorkplace(Tile workplace) {
        if ( this.workplacesAndEmployees.containsKey(workplace) ) {
            for ( Citizen employee : this.getEmployeesOf(workplace) ) {
                employee.quitJob();
            }

            this.workplacesAndEmployees.remove(workplace);            
        }
    }
    
    /**
     * Removes all employees from a workplace.
     * 
     * @see     Citizen#quitJob()
     * @see     #addWorkplace(Tile)
     * 
     * @param   workplace   the workplace of the employees
     */    
    public void removeEmployees(Tile workplace) {
        if ( this.workplacesAndEmployees.containsKey(workplace) ) {
            for ( Citizen employee : this.getEmployeesOf(workplace) ) {
                employee.quitJob();
            }

            this.addWorkplace(workplace);            
        }
    }       
    
    /**
     * Removes an employee from a workplace.
     * 
     * @see     Citizen#quitJob()
     * 
     * @param   workplace   the workplace of the employee
     * @param   employee    the employee to remove
     */    
    public void removeEmployee(Tile workplace, Citizen employee) {
        if ( this.workplacesAndEmployees.containsKey(workplace) && 
             this.getEmployeesOf(workplace).contains(employee) ) {
            
            employee.quitJob();
            this.workplacesAndEmployees.get(workplace).remove(employee);
        }
    } 
    
    /**
     * Tries to find a suitable workplace for a citizen.
     * 
     * @param   citizen  the citizen to find a workplace for
     * 
     * @return  true if a workplace was found, false otherwise
     */     
    public boolean findWorkplaceFor(Citizen citizen) {
        for ( Tile workplace : this.getWorkplaces() ) {
            if ( isSuitable(workplace) ) {
                this.addEmployee(workplace, citizen);
                return true;
            }
        }
        
        return false;
    }
    
    private boolean isSuitable(Tile workplace) {
        return this.getEmployeesOf(workplace).size() < 
               workplace.getType().getWorkplaces();
    }    
}
