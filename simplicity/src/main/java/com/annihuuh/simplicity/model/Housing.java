package com.annihuuh.simplicity.model;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Set;

/**
 * Maintains a map of houses and their residents. Finds homes for families.
 * 
 * @author annihuuh
 */
public class Housing implements Serializable {
    private HashMap<Tile, Family> housesAndFamilies;

    public Housing() {
        this.housesAndFamilies = new HashMap<>();
    }

    public HashMap<Tile, Family> getHousesAndResidents() {
        return this.housesAndFamilies;
    }

    public Set<Tile> getHouses() {
        return this.housesAndFamilies.keySet();
    }
    
    /**
     * Retrieves a house's residents.
     * 
     * @param   house   the map key
     *
     * @return  the family if the keyset contains the house, null otherwise
     */       
    public Family getResidentsOf(Tile house) {
        if ( this.housesAndFamilies.containsKey(house) ) {
            return this.housesAndFamilies.get(house);
        }
        
        return null;
    }
    
    /**
     * Adds a new house to the keyset.
     * 
     * @param   house   the house to add
     */       
    public void addHouse(Tile house) {
        this.housesAndFamilies.put(house, null);
    }   
    
    /**
     * Adds a family to a house.
     * 
     * @see     Family#setHouse(Tile)
     * 
     * @param   house    the selected house
     * @param   family   the family to add
     */     
    public void addResidents(Tile house, Family family) {
        if ( this.housesAndFamilies.containsKey(house) ) {
            this.housesAndFamilies.put(house, family);
            family.setHouse(house);
        }       
    }
    
    /**
     * Removes a house from the keyset.
     * 
     * @param   house   the house to remove
     */     
    public void removeHouse(Tile house) {
        if ( this.housesAndFamilies.containsKey(house) ) {
            if ( this.housesAndFamilies.get(house) != null ) {
                this.housesAndFamilies.get(house).setHouse(null);
            }
            
            this.housesAndFamilies.remove(house);            
        }
    }
    
    /**
     * Removes a family from a house.
     * 
     * @see     Family#setHouse(Tile)
     * 
     * @param   house   the house of the family
     */    
    public void unhouseFamily(Tile house) {
        if ( this.housesAndFamilies.containsKey(house) && 
                this.housesAndFamilies.get(house) != null ) {
            
            this.housesAndFamilies.get(house).setHouse(null);       
            this.housesAndFamilies.put(house, null);             
        }      
    }    
    
    /**
     * Tries to find a suitable house for a family.
     * 
     * @see     #addResidents(Tile, Family)
     * 
     * @param   family  the family to find a house for
     * 
     * @return  true if a house was found, false otherwise
     */     
    public boolean findHouseFor(Family family) {
        for ( Tile house : this.getHouses() ) {
            if ( isSuitable(house, family) ) {
                this.addResidents(house, family);
                return true;
            }
        }
        
        return false;
    }
    
    /**
     * Checks if a house is suitable for a family.
     * 
     * @see     #getResidentsOf(Tile)
     * 
     * @param   house   the house to check
     * @param   family  the family to find a house for
     * 
     * @return  true if the house does not have residents and has enough
     *          capacity, false otherwise
     */         
    public boolean isSuitable(Tile house, Family family) {
        return this.getResidentsOf(house) == null && 
               house.getType().getCapacity() >= family.size();
    }
}
