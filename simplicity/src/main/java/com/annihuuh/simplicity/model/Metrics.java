package com.annihuuh.simplicity.model;

import java.io.Serializable;
import java.util.Set;
import java.util.TreeMap;

/**
 * Contains a map with different statistics and methods for accessing it. 
 * An attribute of City.
 * 
 * @see    City
 *
 * @author annihuuh
 */
public class Metrics implements Serializable {
    private TreeMap<String, Double> metrics;   
    
    public Metrics() {
        this.metrics = new TreeMap<>();
        
        this.metrics.put("Rating", 0.0);
        this.metrics.put("Population size", 0.0);
        this.metrics.put("Pollution", 0.0);
        this.metrics.put("Safety", 0.0); 
        this.metrics.put("Health", 0.0); 
        
        this.metrics.put("Children", 0.0);
        this.metrics.put("Young adults", 0.0);
        this.metrics.put("Elderly", 0.0);
        
        this.metrics.put("Homeless", 0.0);
        this.metrics.put("Employed", 0.0); 
        
        this.metrics.put("Residences", 0.0);
        this.metrics.put("Commerces", 0.0);
        this.metrics.put("Industries", 0.0);
        this.metrics.put("Public services", 0.0);        
    }
    
    public int getSize() {
        return this.metrics.size();
    }    
    
    public Set<String> getKeys() {
        return this.metrics.keySet();
    }     
    
    /**
     * Retrieves a statistic.
     *
     * @param   key    the string key, e.g. "Pollution"
     * 
     * @return the statistic if it exists, 0 otherwise
     */    
    public double getValue(String key) {
        if ( this.metrics.containsKey(key) ) {
            return this.metrics.get(key);
        }
        
        return 0;
    }
    
    /**
     * Increases a statistic by a certain value.
     *
     * @param   key    the string key, e.g. "Pollution"
     * @param   amount the increase amount as a double
     */      
    public void incValue(String key, double amount) {
        if ( this.metrics.containsKey(key) ) {
            this.metrics.put(key, this.metrics.get(key) + amount);
        }
    }
    
    /**
     * Sets all statistics to 0.
     */     
    public void reset() {
        for ( String key : this.metrics.keySet() ) {
            this.metrics.put(key, 0.0);
        }
    }   
}
