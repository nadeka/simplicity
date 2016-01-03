package com.annihuuh.simplicity.view;

import com.annihuuh.simplicity.model.City;
import com.annihuuh.simplicity.model.Map;
import com.annihuuh.simplicity.model.Tile;
import com.annihuuh.simplicity.model.TileType;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.HashMap;
import java.util.Observable;
import java.util.Observer;
import javax.swing.ImageIcon;
import javax.swing.JPanel;

/**
 * Paints the city map. Tile types are represented by icons.
 *
 * @author annihuuh
 */
public class CanvasPanel extends JPanel implements Observer {
    private Map map;
    private HashMap<TileType, ImageIcon> tileIcons;
    private double scale;
    
    public CanvasPanel(Map map, Resources icons) {
        this.map = map;        
        this.tileIcons = icons.getTileIcons();
        this.scale = 1;
        
        this.initLayout();
    }
    
    private void initLayout() {
        super.setBackground(Color.WHITE);
        super.setPreferredSize(new Dimension(this.map.getWidth() * 40, 
                                             this.map.getHeight() * 40));         
    }
    
    public void setMap(Map map){
        this.map = map;
    }   
    
   public double getScale() {
        return this.scale;
    }   

    public void setScale(double scale) {
        this.scale = scale;
    }
    
    /**
     * Increases or decreases the size of the canvas.
     * 
     * @param   value  the decimal percentage to scale
     */    
    public void scale(double value) {
        if ( ( value < 0 && this.scale > 0.5 ) || 
             ( value > 0 && this.scale < 3 ) ) {
            
            this.scale += value;
        } else {
            return;
        }
        
        Dimension dimension = super.getSize();
        dimension.setSize(this.getWidth()  * this.scale, 
                          this.getHeight() * this.scale);

        this.repaint();
    }
    
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);       
        Graphics2D g2 = (Graphics2D) g;
        
        g2.scale(this.scale, this.scale);      
        
        for ( int y = 0; y < this.map.getHeight() * 40; y += 40 ) {
            for ( int x = 0; x < this.map.getWidth() * 40; x += 40 ) {
                Tile tile = this.map.getTile(x / 40, y / 40);

                g2.setColor(Color.getHSBColor(0.30F, 0.5F, 0.7F));
                g2.drawRect(x, y, 40, 40);
                
                g2.setColor(Color.getHSBColor(0.30F, 0.52F, 0.81F));
                g2.fillRect(x + 1, y + 1, 39, 39);
                
                if ( tile.getType() != TileType.GRASS ) {
                    g2.drawImage(this.tileIcons.get(tile.getType()).getImage(), 
                                                    x + 1, y + 1, this);           
                }
                
                g2.setColor(Color.GRAY);               
                g2.drawString(Integer.toString(tile.getTraffic()), x, y + 40);  
            }
        }
        
        g2.dispose();
    }

    @Override
    public void update(Observable o, Object arg) {
        this.map = ((City) o).getMap();
        this.repaint();
    }
}
