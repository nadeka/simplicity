package com.annihuuh.simplicity.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * People arrive to the city under certain conditions. They have happiness and 
 * health levels and the city tries to find homes and workplaces for them.
 * Citizens leave the city when they are too unhappy, old or sick, or when they 
 * have been homeless and unemployed for a while. Working and homeowning 
 * citizens have a commute and its duration affects their happiness.
 *
 * @author annihuuh
 */
public class Citizen implements Serializable {
    private Tile workplace;
    private Tile house;
    
    private List<Tile> commute;
    private int commuteTime;
    
    private int salary;
    private int age;   
    
    private double happiness;
    private double health;
    
    private int monthsUnemployed;
    private int monthsHomeless;

    public Citizen(int age) {
        this.workplace = null;
        this.house = null;
        
        this.commute = new ArrayList<>();      
        this.commuteTime = 0;
        
        this.salary = 0;
        this.age = age;
        
        this.happiness = 0;
        this.health = 100;
        
        this.monthsUnemployed = 0;
        this.monthsHomeless = 0;
    }
    
    public void age() {
        this.age++;
    }
    
    public boolean hasJob() {
        return this.workplace != null;
    }

    public boolean hasHouse() {
        return this.house != null;
    }

    public int getSalary() {
        return this.salary;
    }

    public int getAge() {
        return this.age;
    }

    public Tile getWorkplace() {
        return this.workplace;
    }
    
    public Tile getHouse() {
        return this.house;
    }
    
    public void setHouse(Tile house) {
        this.house = house;
    }
    
    public List<Tile> getCommute() {
        return this.commute;
    }
      
    /**
     * Sets a commute for the citizen and increases the traffic level of each 
     * tile on the new path. Decreases traffic on the previous commute path.
     * 
     * @param   commute    the commute to set
     */ 
    public void setCommute(List<Tile> commute) {
        if ( this.commute != null ) {
            for ( Tile tile : this.commute ) {
                tile.decTraffic();
            }            
        }
        
        this.commute = commute;

        for ( Tile tile : this.commute ) {
            tile.incTraffic();
        }
    } 

    public int getCommuteTime() {
        return this.commuteTime;
    }
    
    public double getHappiness() {
        return this.happiness;
    }
    
    public void setHappiness(double happiness) {
        this.happiness = happiness;
    }
    
    public void incHappiness(double amount) {
        this.happiness += amount;
    }  

    public double getHealth() {
        return this.health;
    }

    public void setHealth(double health) {
        this.health = health;
    }

    public void incHealth(double amount) {
        this.health += amount;
    }
    
    public void resetStats() {
        this.happiness = 0;
        this.health = 0;
    }
    
    public void incMonthsUnemployed(double amount) {
        this.monthsUnemployed += amount;
    }
    
    public void incMonthsHomeless(double amount) {
        this.monthsHomeless += amount;
    }
    
    /**
     * Sets a workplace for the citizen.
     * 
     * @param   workplace   the new workplace
     */     
    public void startJob(Tile workplace) {
        this.workplace = workplace;
        this.salary = workplace.getType().getSalary();
        this.monthsUnemployed = 0;
    }

    /**
     * Sets the workplace and commute of the citizen to null.
     * 
     * @see     #setCommute(List)
     */     
    public void quitJob() {
        this.workplace = null;
        this.salary = 0;     
        this.setCommute(new ArrayList<Tile>());
        this.commuteTime = 0;        
    }
    
    /**
     * Sets the house and commute of the citizen to null.
     * 
     * @see     #setCommute(List)
     */     
    public void moveOut() {
        this.house = null;    
        this.setCommute(new ArrayList<Tile>());
        this.commuteTime = 0;
    }   
    
    /**
     * Sets a house for the citizen.
     * 
     * @param   house   the new house
     */      
    public void moveIn(Tile house) {
        this.house = house;   
        this.monthsHomeless = 0;
    }
    
    /**
     * Checks if the citizen is leaving the city.
     * 
     * @return  true if the citizen is too unhappy, dies or has been unemployed 
     *          and homeless for a while, false otherwise
     */        
    public boolean leaves() {
        return this.age >= 90 || this.health < -100 || this.happiness < -100 || 
               (this.monthsHomeless >= 2 && this.monthsUnemployed >= 2);
    }
   
    /**
     * Calculates the commute and its duration if the citizen has a job and house.
     * 
     * @see     Map#quickestPath(Tile, Tile)
     * @see     #setCommute(List)
     * 
     * @param   map                 the city map
     */         
    public void calculateCommute(Map map) {
        if ( this.hasJob() && this.hasHouse() ) {
            List<Tile> path = map.quickestPath(this.workplace, this.house);
            this.setCommute(path);
            this.commuteTime = path.get(0).getToStart();          
        } else {
            this.setCommute(new ArrayList<Tile>());
            this.commuteTime = 0;
        }      
    }
}
