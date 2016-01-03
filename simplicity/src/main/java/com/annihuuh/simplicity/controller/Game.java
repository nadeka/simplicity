package com.annihuuh.simplicity.controller;

import com.annihuuh.simplicity.io.FileHandler;
import com.annihuuh.simplicity.model.City;
import com.annihuuh.simplicity.model.TileType;
import com.annihuuh.simplicity.view.Dialogs;
import com.annihuuh.simplicity.view.Gui;
import java.awt.Color;
import java.awt.Cursor;
import javax.swing.JButton;
import javax.swing.JMenu;
import javax.swing.JMenuItem;

/**
 * The main controller class. Contains the game loop and handles user input.
 * 
 * @see    Gui
 * @see    City
 * @see    FileHandler
 *
 * @author annihuuh
 */
public class Game {  
    private Gui gui;   
    private City city;  

    private boolean destroyModeOn;
    private boolean pauseModeOn; 
    private TileType selectedTileType;
    
    private FileHandler fileHandler;

    public Game(Gui gui, City city) {        
        this.gui = gui;
        this.city = city;         
        
        this.destroyModeOn = false;
        this.pauseModeOn = false;   
        this.selectedTileType = null;
        
        this.fileHandler = new FileHandler();      
    }

    public Gui getGui() {
        return this.gui;
    }

    public City getCity() {
        return this.city;
    }

    public boolean destroyModeOn() {
        return this.destroyModeOn;
    }
    
    /**
     * Sets the destroy mode on or off. Changes the message in the alert panel 
     * and the cursor type.
     *
     * @param   destroyModeOn    true to set the destroy mode on, false 
     *                           otherwise
     */
    public void setDestroyMode(boolean destroyModeOn) {
        if ( destroyModeOn ) {
            this.gui.getAlertPanel().showMessage("Destroy mode on");
            this.gui.getFrame().setCursor(new Cursor(Cursor.CROSSHAIR_CURSOR));
        } else {
            this.gui.getAlertPanel().showMessage("");
            this.gui.getFrame().setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
        }        
              
        this.destroyModeOn = destroyModeOn;
    }

    public boolean pauseModeOn() {
        return this.pauseModeOn;
    }
    
    /**
     * Sets the pause mode on or off. Changes the color of the date label.
     *
     * @param   pauseModeOn    true to set the pause mode on, false otherwise
     */
    public void setPauseMode(boolean pauseModeOn) {
        if ( pauseModeOn ) {
            this.setBuildMode(null);
            this.gui.getAlertPanel().getDateLabel().setForeground(Color.RED);
        } else {
            this.gui.getAlertPanel().getDateLabel().setForeground(Color.BLACK);            
        }
        
        this.pauseModeOn = pauseModeOn;
    }

    public TileType getSelectedTileType() {
        return this.selectedTileType;
    }   
    
    /**
     * Sets the build mode on or off and saves the selected tile type. 
     * Changes the message in the alert panel and the cursor type.
     *
     * @param   selectedTileType    the tile type to set
     */
    public void setBuildMode(TileType selectedTileType) {             
        if ( selectedTileType != null ) {
            this.setDestroyMode(false);
            this.gui.getAlertPanel().showMessage("Build mode on");
            this.gui.getFrame().setCursor(new Cursor(Cursor.MOVE_CURSOR));
        } else {
            this.gui.getAlertPanel().showMessage("");
            this.gui.getFrame().setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
        }
        
        this.selectedTileType = selectedTileType;
    } 

    private void createListeners() { 
        this.createSpinnerListener();
        this.createButtonListener();
        this.createMenuItemListener();
        this.createCanvasListener();
    } 
    
    private void createSpinnerListener() {
        SpinnerListener spinnerListener = new SpinnerListener(this);
        this.gui.getToolPanel().getCorporateTaxRateSpinner()
                .addChangeListener(spinnerListener);
        
        this.gui.getToolPanel().getWorkerTaxRateSpinner()
                .addChangeListener(spinnerListener);   
    }
    
    private void createButtonListener() {
        ButtonListener buttonListener = new ButtonListener(this); 
        
        for ( JButton tileButton : 
                this.gui.getTilePanel().getTileButtons().keySet() ) {
            tileButton.addActionListener(buttonListener);
        }
        
        for ( JButton toolButton : 
                this.gui.getToolPanel().getToolButtons() ) {
            toolButton.addActionListener(buttonListener);
        } 
    }
    
    private void createMenuItemListener() {
        MenuItemListener menuListener = new MenuItemListener(this);
        
        for ( JMenu menu : this.gui.getMenuBar().getMenus().keySet() ) {
            for ( JMenuItem menuItem : 
                  this.gui.getMenuBar().getMenus().get(menu) ) { 

                menuItem.addActionListener(menuListener);
            }
        }        
    }
    
    private void createCanvasListener() {
        CanvasListener canvasListener = new CanvasListener(this);       
        this.gui.getCanvasPanel().addMouseListener(canvasListener);    
    }
    
    private void addObservers() {
        this.city.addObserver(this.gui.getCanvasPanel());
        this.city.addObserver(this.gui.getInfoPanel());
        this.city.addObserver(this.gui.getAlertPanel());
        this.city.addObserver(this.gui.getToolPanel());  
    }
    
    /**
    * Creates event listeners, adds observers to the city and starts the 
    * game loop where the city updates at regular intervals.
    * 
    */     
    public void start() {
        this.init();
        
        while ( true ) {
            while ( this.pauseModeOn ) {
                pause(10);
            }            

            if ( this.city.isDoomed() ) {
                this.doGameOverAction();              
            }

            this.city.update();       

            this.pause(1000);         
        }
    }
    
    private void init() {
        while ( !this.gui.isDisplayable() ) {
            pause(100);
        }    
        
        this.createListeners();
        this.addObservers();        
    }
  
    private void pause(int time) {
        try {
            Thread.sleep(time);
        } catch (InterruptedException exception) {
            Dialogs.showExceptionErrorDialog(exception, this.gui.getFrame());
            Thread.currentThread().interrupt();
        }        
    }
    
    private void doGameOverAction() {
        switch ( Dialogs.getGameOverOption(this.gui.getFrame()) ) {
            case 0: this.startNewGame(); break;
            case 1: System.exit(0);
        }         
    }
    
    /**
    * Creates a new city and registers observers to it.
    * 
    * @see     City
    * 
    * @see     #setPauseMode(boolean)
    * @see     #addObservers()
    */    
    public void startNewGame() {
        this.setPauseMode(true);
        
        switch ( Dialogs.getNewGameOption(this.gui.getFrame()) ) {
            case 1: return;               
        }         
        
        this.city = new City(this.city.getMap().getWidth(), 
                             this.city.getMap().getHeight());
        this.addObservers();
        this.setPauseMode(false);
    }   
    
    /**
    * Saves the citys's current state to a file.
    * 
    * @see     #setPauseMode(boolean)
    * @see     FileHandler#save(City, Gui)
    */     
    public void saveGame() {
        this.setPauseMode(true);
        this.fileHandler.save(this.city, this.gui);
        this.setPauseMode(false);
    }
    
    /**
    * Loads a saved city and registers observers to it.
    * 
    * @see     #setPauseMode(boolean)
    * @see     FileHandler#load(Gui)
    * @see     #addObservers()
    */        
    public void loadGame() {
        this.setPauseMode(true);
        City loadedCity = this.fileHandler.load(this.gui);

        if ( loadedCity != null ) {
            this.city = loadedCity;
            this.addObservers();           
        }   
        
        this.setPauseMode(false);
    }
    
    /**
    * Quits the game. Prompts the player to save the current game before 
    * quitting.
    * 
    * @see     #setPauseMode(boolean)
    * @see     Dialogs#getQuitOption(JFrame)
    * @see     FileHandler#save(City, Gui)
    */     
    public void quitGame() {
        this.setPauseMode(true);
        
        switch ( Dialogs.getQuitOption(this.gui.getFrame()) ) {
            case 1: return;
            case 0: this.fileHandler.save(this.city, this.gui);               
        } 
        
        System.exit(0);
    } 
}
