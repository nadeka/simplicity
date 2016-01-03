
package com.annihuuh.simplicity.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JMenuItem;

/**
 * Listens to menu items on the gui.
 * 
 * @see     Game
 */
public class MenuItemListener implements ActionListener {
    private Game game;
    
    public MenuItemListener(Game game) {
        this.game = game; 
    }   

    @Override
    public void actionPerformed(ActionEvent e) {
        JMenuItem item = (JMenuItem) e.getSource();

        switch ( item.getText() ) {
            case "Quit":
                this.game.quitGame();
                break;
            case "New game":
                this.game.startNewGame();
                break;
            case "Save":
                this.game.saveGame();
                break;
            case "Load":
                this.game.loadGame();
                break;
        }
    }     
}
