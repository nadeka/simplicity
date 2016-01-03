package com.annihuuh.simplicity.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Citizens occupy residences as families with sizes between 1 and 10.
 * 
 * @see    Housing
 *
 * @author annihuuh
 */
public class Family implements Serializable {
    private List<Citizen> citizens;
    private Tile house;
    
    public Family() {
        this.citizens = new ArrayList<>();
        this.house = null;
    }

    public List<Citizen> getCitizens() {
        return this.citizens;
    }
    
    public int size() {
        return this.citizens.size();
    }
    
    public void addCitizen(Citizen citizen) {
        this.citizens.add(citizen);
    } 
    
    public void addCitizens(List<Citizen> citizens) {
        this.citizens.addAll(citizens);
    } 
    
    public void removeCitizen(Citizen citizen) {
        this.citizens.remove(citizen);
    }
    
    public void removeCitizens(List<Citizen> citizens) {
        this.citizens.removeAll(citizens);
    }    

    public Tile getHouse() {
        return this.house;
    }
    
    /**
     * Sets a house for the family.
     * 
     * @see     Citizen#moveOut()
     * @see     Citizen#moveIn(Tile)
     *
     * @param   house   the house to set
     */    
    public void setHouse(Tile house) {
        this.house = house;
        
        if ( this.house == null ) {
            for ( Citizen citizen : this.citizens ) {
                citizen.moveOut();
            }
        } else {
            for ( Citizen citizen : this.citizens ) {
                citizen.moveIn(house);
            }            
        }
    }   
}
