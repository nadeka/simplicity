package com.annihuuh.simplicity.model;

import java.io.Serializable;

/**
 * Defines all tile types and their constant properties. An attribute of Tile.
 * 
 * @see     Tile
 *
 * @author annihuuh
 */
public enum TileType implements Serializable {  
    
//        name                      cost   cap    rad    rev  poll   happ  hea     wp    sal    road   serv    res    in     comm    
    GRASS("Grass",                     0,    0,    0,     0,    0,    5,    0,     0,     0,    false, false, false, false, false),
    ROAD("Road",                      10,   30,    0,    -10,    0,    0,    0,     0,     0,     true, false, false, false, false),   
    HOUSE("House",                   100,   10,    0,    -5,   10,    0,    0,     0,     0,    false, false, true,  false, false),   
    DRUGST("Drugstore",             400,    0,     5,    20,   10,    20,   20,    15,    15,   false, false, false, false, true),
    HOSPITAL("Hospital",            800,   0,     10,    -30,   10,   30,    40,    20,   20,    false, true, false, false, false), 
    ELEMSC("Elementary school",     500,    50,    10,   -15,   10,   30,    0,     20,    20,   false, true, false, false, false),
    UNIVERSITY("University",       2000,   500,    20,   -30,   50,   30,    0,     30,    30,   false, true, false, false, false),
    LIBRARY("Library",              300,    0,     10,   -10,   10,   20,    0,    20,    10,   false, true,  false,  false, false),
    MUSEUM("Museum",                500,    0,     10,     0,   15,   20,    0,    20,    10,   false, true,  false,  false, false),
    MARKET("Market",                200,    0,      0,    30,    5,   30,    0,     5,    10,   false, false, false,  false, true),
    POLICEST("Police station",      500,    0,     10,   -15,    5,   20,    0,    10,    25,   false, true,  false,  false, false),     
    FIREST("Fire station",          500,    0,     10,   -15,    5,   20,    0,    10,    25,   false, true,  false,  false, false),  
    FARM("Farm",                    200,    0,      0,    20,   10,   -5,   -5,     20,    10,  false, false, false,  true,  false),
    FACTORY("Factory",              600,    0,      10,    40,   35,  -10,   -10,    20,   15,   false, false, false,  true,  false),
    WATERTOWER("Water tower",       800,    0,     20,   -20,   20,   50,    30,    15,    15,  false, true,  false,  true,  false),
    POWERST("Power station",        800,    0,     20,   -20,   20,   50,    30,    15,    15,  false, true,  false,  true,  false);

    private String name;
    private int cost;
    private int capacity;
    private int radius;
    private int revenue;
    private double pollution;
    private double happiness;
    private double health;
    private int workplaces;
    private int salary;
    private boolean isRoad;
    private boolean isService;
    private boolean isResidential;
    private boolean isIndustrial;
    private boolean isCommercial;
            
    private TileType(String name, int cost, int capacity, int radius, 
                    int revenue, double pollution, double happiness, 
                    double health, int workplaces, int salary, boolean isRoad,
                    boolean isService, boolean isResidential, 
                    boolean isIndustrial, boolean isCommercial) {
        
        this.name = name;
        this.cost = cost;
        this.capacity = capacity;
        this.radius = radius;
        this.revenue = revenue;
        this.pollution = pollution;
        this.happiness = happiness;
        this.health = health;
        this.workplaces = workplaces;
        this.salary = salary;
        this.isRoad = isRoad;
        this.isService = isService;
        this.isResidential = isResidential;
        this.isIndustrial = isIndustrial;
        this.isCommercial = isCommercial;
    }

    public String getName() {
        return this.name;
    }

    public int getCost() {
        return this.cost;
    }

    public int getCapacity() {
        return this.capacity;
    } 

    public int getRadius() {
        return this.radius;
    }

    public int getRevenue() {
        return this.revenue;
    }

    public double getPollution() {
        return this.pollution;
    }

    public double getHappiness() {
        return this.happiness;
    }

    public double getHealth() {
        return this.health;
    }

    public int getWorkplaces() {
        return this.workplaces;
    }

    public int getSalary() {
        return this.salary;
    }

    public boolean isRoad() {
        return this.isRoad;
    }
    public boolean isService() {
        return this.isService;
    }

    public boolean isResidential() {
        return this.isResidential;
    }

    public boolean isIndustrial() {
        return this.isIndustrial;
    }

    public boolean isCommercial() {
        return this.isCommercial;
    }
    }
