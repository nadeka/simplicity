package com.annihuuh.simplicity.controller;

import com.annihuuh.simplicity.view.CanvasPanel;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.SwingUtilities;

/**
 * Listens to mouse clicks on the canvas.
 *
 * @author annihuuh
 */
public class CanvasListener implements MouseListener {
    private Game game;
    
    public CanvasListener(Game game) {
        this.game = game;
    } 
    
    @Override
    public void mouseClicked(MouseEvent e) {
        CanvasPanel canvas = (CanvasPanel) e.getSource();
        
        int x = (int) Math.floor(e.getX() / (40 * canvas.getScale()));
        int y = (int) Math.floor(e.getY() / (40 * canvas.getScale()));
        
        if ( SwingUtilities.isRightMouseButton(e) ) {
            this.game.setDestroyMode(false);
            this.game.setBuildMode(null);
            return;
        }        

        if ( this.game.destroyModeOn() ) {  
            this.game.getCity().destroy(x, y);
        } else if ( this.game.getSelectedTileType() != null ) {
            this.game.getCity().build(this.game.getSelectedTileType(), x, y);
            
        }          
    }

    @Override
    public void mousePressed(MouseEvent e) {}
    
    @Override
    public void mouseReleased(MouseEvent e) {}

    @Override
    public void mouseEntered(MouseEvent e) {
        CanvasPanel canvas = (CanvasPanel) e.getSource();
        
        int x = (int) Math.floor(e.getX() * (40 * canvas.getScale()));
        int y = (int) Math.floor(e.getY() * (40 * canvas.getScale()));  
    }

    @Override
    public void mouseExited(MouseEvent e) {
        CanvasPanel canvas = (CanvasPanel) e.getSource();
        
        int x = (int) Math.floor(e.getX() / (40 * canvas.getScale()));
        int y = (int) Math.floor(e.getY() / (40 * canvas.getScale())); 
    }      
}
