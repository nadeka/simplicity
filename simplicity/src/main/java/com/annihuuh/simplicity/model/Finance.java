package com.annihuuh.simplicity.model;

import java.io.Serializable;

/**
 * Functions as a tax collector and bank.
 *
 * @author annihuuh
 */
public class Finance implements Serializable {
    private double cash;
    private double cashFlow;
    
    private double workerTaxRate;
    private double corporateTaxRate;
    
    public Finance() {
        this.cash = 1000.0;
        this.cashFlow = 0;
        
        this.workerTaxRate = 0.12;
        this.corporateTaxRate = 0.12;
    }

    public double getCash() {
        return this.cash;
    }

    public double getCashFlow() {
        return this.cashFlow;
    }

    public void resetCashFlow() {
        this.cashFlow = 0;
    }

    public void addCash(double amount) {
        this.cash += amount;
    }

    public void setCash(double cash) {
        this.cash = cash;
    }

    public void setCashFlow(double cashFlow) {
        this.cashFlow = cashFlow;
    }
    
    /**
     * Adds the accumulated cash flow to the funds.
     */     
    public void addCashFlow() {
        this.cash += this.cashFlow;
        this.cashFlow = 0;
    }
    
    public void reduceCash(double amount) {
        this.cash -= amount;
    }      

    public double getWorkerTaxRate() {
        return this.workerTaxRate;
    }

    public void setWorkerTaxRate(double workerTaxRate) {
        this.workerTaxRate = workerTaxRate;
    }

    public double getCorporateTaxRate() {
        return this.corporateTaxRate;
    }

    public void setCorporateTaxRate(double corporateTaxRate) {
        this.corporateTaxRate = corporateTaxRate;
    }
    
    /**
     * Taxes the revenue of a business according 
     * to the current corporate tax rate.
     * 
     * @see     Tile
     * @see     TileType
     * 
     * @param   business    the business to tax
     * @param   citizens    the current population size
     */  
    public void collectTax(Tile business, double citizens) {
        this.cashFlow += business.getType().getRevenue() * citizens *
                         this.corporateTaxRate;
    }
    
    /**
     * Taxes the salary of an individual citizen according 
     * to the current worker tax rate.
     * 
     * @see     Citizen
     * 
     * @param   worker     the worker to tax
     */       
    public void collectTax(Citizen worker) {
        this.cashFlow += worker.getSalary() * this.workerTaxRate;
    }   
    
    /**
     * Checks if there is enough cash to build a certain tile.
     * 
     * @see     TileType
     * 
     * @param   tileType    the type of the tile
     * 
     * @return true if the tile type is affordable, false otherwise
     */       
    public boolean isAffordable(TileType tileType) {
        return tileType.getCost() <= this.cash;
    }
}
