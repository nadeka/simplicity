package com.annihuuh.simplicity.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;

/**
 * Listens to buttons on the gui.
 *
 * @author annihuuh
 */
public class ButtonListener implements ActionListener {
    private Game game;
    
    public ButtonListener(Game game) {
        this.game = game;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        JButton button = (JButton) e.getSource();
        
        switch ( button.getName() ) {
            case "Destroy":
                this.game.setDestroyMode(true); 
                break;
            case "Pause":
                this.game.setPauseMode(true);
                break;
            case "Continue":
                this.game.setPauseMode(false);
                break;
            case "Zoom in":
                this.game.getGui().getCanvasPanel().scale(0.5);
                break;
            case "Zoom out":
                this.game.getGui().getCanvasPanel().scale(-0.5); 
                break;
            default:
                this.game.setBuildMode(this.game.getGui().getTilePanel()
                                              .getTileButtons().get(button));
                break;
        }
    }    
}
