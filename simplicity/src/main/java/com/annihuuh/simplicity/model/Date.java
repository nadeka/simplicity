package com.annihuuh.simplicity.model;

import java.io.Serializable;

/**
 * Represents a date in years, months and days. An attribute of City.
 * 
 * @see     City
 * 
 * @author annihuuh
 */
public class Date implements Serializable {
    private String[] months;
    private int year;
    private int month;
    private int day;

    public Date() {
        this.months = new String[]{"Jan", "Feb", "Mar", "Apr", "May", "Jun", 
                                   "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"};
        this.year = 2000;
        this.month = 0;
        this.day = 1;
    }
    
    public int getYear() {
        return this.year;
    }

    public int getMonth() {
        return this.month;
    }    

    public int getDay() {
        return this.day;
    }

    /**
     * Increments the day by one.
     */    
    public void tick() {
        this.day++;

        if ( this.day == 31 ) {
            this.month++;
            this.day = 1;

            if ( this.month == 12 ) {
                this.year++;
                this.month = 0;
            }
        }                         
    }

    @Override
    public String toString() {
        return this.months[this.month] + " " + this.day + ", " + this.year;
    }
}
