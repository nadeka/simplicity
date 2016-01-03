package com.annihuuh.simplicity.view;

import com.annihuuh.simplicity.model.TileType;
import java.util.HashMap;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

/**
 * Contains icons for buttons and tiles.
 *
 * @author annihuuh
 */
public class Resources {
    private HashMap<TileType, ImageIcon> tileIcons; 
    private HashMap<ToolType, ImageIcon> toolIcons;
    
    public Resources() {
        this.tileIcons = new HashMap<>();
        this.toolIcons = new HashMap<>();
    }

    public void load(Gui gui) {
        ClassLoader classLoader = getClass().getClassLoader();
        
        try { 
            for ( TileType tileType : TileType.values() ) {
                if ( tileType != TileType.GRASS ) {
                    this.tileIcons.put(tileType, 
                    new ImageIcon(ImageIO.read(classLoader.getResource
                    ("icons/" + tileType.name() + ".png")).
                    getScaledInstance(40, 40, java.awt.Image.SCALE_SMOOTH)));          
                }   
            }

            this.toolIcons.put(ToolType.PAUSE, 
                    new ImageIcon(ImageIO.read(classLoader.getResource
                    ("icons/PAUSE.png")).
                    getScaledInstance(40, 40, java.awt.Image.SCALE_SMOOTH)));          
                
            
            this.toolIcons.put(ToolType.CONTINUE, 
                    new ImageIcon(ImageIO.read(classLoader.getResource
                    ("icons/PLAY.png")).
                    getScaledInstance(40, 40, java.awt.Image.SCALE_SMOOTH)));  
            
            this.toolIcons.put(ToolType.ZOOMIN, 
                    new ImageIcon(ImageIO.read(classLoader.getResource
                    ("icons/ZOOMIN.png")).
                    getScaledInstance(40, 40, java.awt.Image.SCALE_SMOOTH)));  
            
            this.toolIcons.put(ToolType.ZOOMOUT, 
                    new ImageIcon(ImageIO.read(classLoader.getResource
                    ("icons/ZOOMOUT.png")).
                    getScaledInstance(40, 40, java.awt.Image.SCALE_SMOOTH)));  
            
            this.toolIcons.put(ToolType.DESTROY, 
                    new ImageIcon(ImageIO.read(classLoader.getResource
                    ("icons/DESTROY.png")).
                    getScaledInstance(40, 40, java.awt.Image.SCALE_SMOOTH)));  
            
        } catch ( Exception e ) {
            Dialogs.showExceptionErrorDialog(e, gui.getFrame());
            e.printStackTrace();
        } 
    }

    public HashMap<TileType, ImageIcon> getTileIcons() {
        return this.tileIcons;
    }

    public HashMap<ToolType, ImageIcon> getToolIcons() {
        return this.toolIcons;
    }
}
