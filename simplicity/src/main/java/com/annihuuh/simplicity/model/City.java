package com.annihuuh.simplicity.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Observable;
import java.util.Random;

/**
 * The main logic class. A city consists of a map, tiles, citizens and 
 * subsystems such as employment and housing. At each update, the city directs 
 * the citizens, calculates statistics and taxes the citizens and businesses. 
 * The city also has a date for timing events.
 * 
 * @author annihuuh
 */
public class City extends Observable implements Serializable { 
    private Map map;
    private HashMap<Location, Tile> tiles;   
    private List<Family> families;
    
    private Random random;
    
    private Housing housing;
    private Employment employment;
    private Finance finance; 
    
    private Date date;
    private Metrics metrics;
    
    public City(int width, int height) {
        this.map = new Map(width, height);
        this.tiles = this.map.initializeMap();     
        this.families = new ArrayList<>();
        
        this.random = new Random();
        
        this.housing = new Housing();
        this.employment = new Employment();
        this.finance = new Finance();
        
        this.date = new Date();
        this.metrics = new Metrics();
    }

    public HashMap<Location, Tile> getTiles() {
        return this.tiles;
    }

    public List<Family> getFamilies() {
        return this.families;
    }

    public Map getMap() {
        return this.map;
    }

    public Date getDate() {
        return this.date;
    }

    public Housing getHousing() {
        return this.housing;
    }

    public Employment getEmployment() {
        return this.employment;
    }
    
    public Finance getFinance() {
        return this.finance;
    }

    public Metrics getMetrics() {
        return this.metrics;
    }
    
    private void registerChange() {
        this.setChanged();
        this.notifyObservers();         
    }
    
    /**
    * Changes a grass tile's type to another type, adds it to the right 
    * subsystems and notifies observers. The tile's cost is removed 
    * from the funds.
    * 
    * @see      Map#getTile(int, int)
    * @see      Finance#reduceCash(double)
    * 
    * @param    newType     the type of the new tile, e.g. house
    * @param    x           the x coordinate of the tile to replace
    * @param    y           the y coordinate of the tile to replace
    */   
    public void build(TileType newType, int x, int y) {
        Tile tile = this.map.getTile(x, y);
        
        if ( isBuildable(tile, newType) ) {
            this.finance.reduceCash(newType.getCost());
            this.changeTypeTo(newType, tile.getLocation());           
            this.addToSystems(tile);
            
            this.registerChange();
        }        
    } 
       
    /**
    * Changes a tile's type to grass, removes it from the subsystems it no 
    * longer belongs to and notifies observers. Half of the tile's original 
    * is refunded.
    *           
    * @see      Map#getTile(int, int) 
    * @see      Finance#addCash(double)
    * 
    * @param    x   the x coordinate of the tile to replace
    * @param    y   the y coordinate of the tile to replace
    */       
    public void destroy(int x, int y) {
        Tile tile = this.map.getTile(x, y);
        
        if ( isDestroyable(tile) ) {
            this.finance.addCash(tile.getType().getCost() / 2);            
            this.removeFromSystems(tile);           
            this.changeTypeTo(TileType.GRASS, tile.getLocation());
            
            this.registerChange();
        }        
    }
    
    private boolean isBuildable(Tile tile, TileType newType) {
        return tile != null && tile.getType() == TileType.GRASS && 
               this.finance.isAffordable(newType);
    }    
     
    private boolean isDestroyable(Tile tile) {
        return tile != null && tile.getType() != TileType.GRASS;
    }
          
    private void changeTypeTo(TileType newType, Location location) {
        this.tiles.get(location).changeType(newType);
        this.map.getTile(location.getX(), location.getY()).changeType(newType);
    } 
    
    private void addToSystems(Tile tile) {
        if ( tile.getType().isResidential() ) {
            this.housing.addHouse(tile);       
        } else if ( isWorkplace(tile) ) {
            this.employment.addWorkplace(tile);
        }
    }
    
    private void removeFromSystems(Tile tile) {
        if ( tile.getType().isResidential() ) {
            this.housing.removeHouse(tile);       
        } else if ( isWorkplace(tile) ) {
            this.employment.removeWorkplace(tile);
        } else if ( tile.getType().isRoad() ) {
            tile.resetTraffic();
        }
    }
    
    private boolean isWorkplace(Tile tile) {
        return tile.getType().isCommercial() || tile.getType().isIndustrial() 
               || tile.getType().isService();
    }
    
    /**
    * Increments the date and checks each tile and citizen. Calculates 
    * the statistics and cash flow. Notifies observers in the end. 
    * 
    * @see      Date#tick()
    * @see      Metrics#reset()
    * @see      Finance#addCash(double)
    */         
    public void update() {
        this.date.tick();  
        
        this.metrics.reset();
        
        updatePieces();
        updateCitizens(); 
        
        this.finance.addCashFlow();
        
        this.registerChange();
    }   
    
    /**
    * Checks if the game is over.
    * 
    * @return   true if the accumulated debt or pollution level is over 1000, 
    *           false otherwise
    */        
    public boolean isDoomed() {
        return this.finance.getCash() < -1000 || 
               this.metrics.getValue("Pollution") < -1000;
    }
     
    private void updatePieces() { 
        for ( Tile tile : this.tiles.values() ) {
            this.finance.collectTax(tile, this.metrics.getValue("Population size"));
            
            this.metrics.incValue("Pollution", tile.getType().getPollution());

            if ( tile.getType().isResidential() ) {        
                this.metrics.incValue("Residences", 1); 
                
                if ( this.housing.getResidentsOf(tile) == null ) {
                    for ( Family family : this.families ) {
                        if ( family.getHouse() == null && this.housing.isSuitable(tile, family) ) {
                            this.housing.addResidents(tile, family);
                            return;
                        }
                    }
                    
                    Family family = this.createFamily(this.random.nextInt(
                                      tile.getType().getCapacity()));
                    this.housing.addResidents(tile, family);
                    break;                                
                }        
                
            } else if ( tile.getType().isCommercial() ) {
                this.metrics.incValue("Commerces", 1);
                
            } else if ( tile.getType().isIndustrial() ) {
                this.metrics.incValue("Industries", 1);
                
            } else if ( tile.getType().isService() ) {
                if ( tile.getType() == TileType.POLICEST || 
                     tile.getType() == TileType.FIREST ) {
                    
                    this.metrics.incValue("Safety", 30);
                }
                
                this.metrics.incValue("Public services", 1);
                
            } else if ( tile.getType().isRoad() ) {
                if ( tile.getTraffic() > tile.getType().getCapacity() ) {
                    this.destroy(tile.getLocation().getX(), 
                                 tile.getLocation().getY());
                }  
            }
        }
    }
    
    private void updateCitizens() {
        HashMap<Family, List<Citizen>> leavingCitizens = new HashMap<>();
        List<Family> emptyFamilies = new ArrayList<>();
        
        double totalHappiness = 0;
        double totalHealth = 0;
            
        for ( Family family : this.families ) {
            if ( family.size() == 0 ) {
                emptyFamilies.add(family);
                continue;
            }
            
            leavingCitizens.put(family, new ArrayList<Citizen>());
            
            if ( family.getHouse() == null ) {              
                this.housing.findHouseFor(family);
            }
            
            for ( Citizen citizen : family.getCitizens() ) {                     
                citizen.resetStats();           
                
                this.finance.collectTax(citizen);
                
                this.checkAge(citizen);                           
                this.updateJobSituation(citizen);
                this.updateHouseSituation(citizen);
                
                citizen.calculateCommute(this.map);
                
                if ( citizen.getCommuteTime() >= 10 ) {
                    citizen.incHappiness(-30);
                }
                
                if ( citizen.leaves() ) {  
                    leavingCitizens.get(family).add(citizen);
                    continue;
                } else {
                    this.metrics.incValue("Population size", 1); 
                }
                
                totalHappiness += citizen.getHappiness();
                totalHealth += citizen.getHealth();            
            }           
        }
        
        for ( Family family : leavingCitizens.keySet() ) {
            family.removeCitizens(leavingCitizens.get(family));
        }
        
        for ( Family family : emptyFamilies ) {
            this.removeFamily(family);
        }
        
        this.metrics.incValue("Health", totalHealth 
                              / this.metrics.getValue("Population size"));
        this.metrics.incValue("Rating", totalHappiness  
                              / this.metrics.getValue("Population size"));       
    }
    
    private void checkAge(Citizen citizen) {
        if ( this.date.getYear() == 1 ) {
            citizen.age();           
        }        
        
        if ( citizen.getAge() < 18 ) {
            this.metrics.incValue("Children", 1);
        } else if ( citizen.getAge() >= 18 && citizen.getAge() < 65 ) {
            this.metrics.incValue("Young adults", 1);
        } else {
            this.metrics.incValue("Elderly", 1);
        }      
    }
    
    private void updateHouseSituation(Citizen citizen) {
        if ( citizen.hasHouse() ) {
            for ( TileType tileType : TileType.values() ) {
                if ( this.map.isInRadius(citizen.getHouse(), tileType, 
                        tileType.getRadius()) )  {
                    
                    citizen.incHappiness(tileType.getHappiness());
                    citizen.incHealth(tileType.getHealth());
                    
                    if ( tileType.isIndustrial() && this.map.isInRadius
                         (citizen.getHouse(), tileType, 5) ) {
                        
                        citizen.incHappiness(-20);
                    }
                    
                    
                }
            }                 
        } else {
            if ( this.date.getDay() == 30 ) {
                citizen.incMonthsHomeless(1);
            }
            
            citizen.incHappiness(-50);
            this.metrics.incValue("Homeless", 1);
        }        
    }
    
    private void updateJobSituation(Citizen citizen) {
        if ( citizen.hasJob() ) {
            for ( double d = 0.2; 
                    d <= this.finance.getWorkerTaxRate(); d += 0.2 ) {
                citizen.incHappiness(-10);
            }                        
            
            citizen.incHappiness(50);
            this.metrics.incValue("Employed", 1);
        } else {
            if ( isOfWorkingAge(citizen) ) {
                if ( !this.employment.findWorkplaceFor(citizen) ) {
                    citizen.incHappiness(-50);
                    
                    if ( this.date.getDay() == 30 ) {
                        citizen.incMonthsUnemployed(1);
                    }
                }                        
            } else {
                
            }
        }     
    }
    
    private boolean isOfWorkingAge(Citizen citizen) {
        return citizen.getAge() >= 18 && citizen.getAge() < 66;
    }
    
    /**
    * Removes a family from the city.
    * 
    * @see      Housing#unhouseFamily(Tile)
    * @see      Employment#removeEmployee(Tile, Citizen) 
    * 
    * @param    family  the family to remove
    */         
    public void removeFamily(Family family) {
        this.families.remove(family);
        
        if ( family.getHouse() != null ) {
            this.housing.unhouseFamily(family.getHouse());
        }
        
        for ( Citizen citizen : family.getCitizens() ) {
            if ( citizen.hasJob() ) {
                this.employment.removeEmployee(citizen.getWorkplace(), citizen);
            }
        }
    }
    
    /**
    * Creates a new family of a certain size and adds it to the city.  
    * 
    * @param    size    the size of the new family
    * 
    * @return   the created family 
    */       
    public Family createFamily(int size) {
        Family family = new Family();
        
        for ( int i = 0; i < size; i++ ) {
            Citizen citizen = new Citizen(this.random.nextInt(100));
            family.addCitizen(citizen);
        }
        
        this.families.add(family);
        
        return family;
    }
    
    /**
    * Creates a new family consisting of certain citizens and adds 
    * it to the city.  
    * 
    * @param    citizens    the members of the new family
    * 
    * @return   the created family 
    */     
    public Family createFamily(List<Citizen> citizens) {
        Family family = new Family();
        
        family.addCitizens(citizens);  
        
        this.families.add(family);
        
        return family;
    }    
}
