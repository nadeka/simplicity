package com.annihuuh.simplicity.controller;

import javax.swing.JSpinner;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

/**
 * Listens to spinners on the gui.
 *
 * @author annihuuh
 */
public class SpinnerListener implements ChangeListener {
    private Game game;
    
    public SpinnerListener(Game game) {
        this.game = game;
    }

    @Override
    public void stateChanged(ChangeEvent e) {
        JSpinner spinner = (JSpinner) e.getSource();

        switch ( spinner.getName() ) {
            case "Worker tax rate spinner":
                this.game.getCity().getFinance().setWorkerTaxRate(
                                                (double) spinner.getValue());
                break;
            case "Corporate tax rate spinner":      
                this.game.getCity().getFinance().setCorporateTaxRate(
                                                (double) spinner.getValue());
                break;
        }
    }   
}
