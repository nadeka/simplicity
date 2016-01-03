package com.annihuuh.simplicity.view;

import com.annihuuh.simplicity.model.TileType;
import java.awt.Color;
import java.util.HashMap;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JPanel;

/**
 * Displays all tile buttons and a destroy button. 
 *
 * @author annihuuh
 */
public class TilePanel extends JPanel {
    private HashMap<JButton, TileType> tileButtons; 
    
    public TilePanel(Resources icons) {
        this.tileButtons = new HashMap<>();
        this.setLayout();
        this.createButtons(icons);
    }
    
    private void setLayout() {
        super.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        super.setBackground(Color.WHITE);
        super.setBorder(BorderFactory.createLineBorder(Color.WHITE, 5));        
    }
          
    private void createButtons(Resources icons) {        
        for ( TileType tileType : icons.getTileIcons().keySet() ) {
            if ( tileType == TileType.GRASS ) {
                continue;
            }
            
            JButton button = new JButton(icons.getTileIcons().get(tileType));
            this.setToolTipText(button, tileType);
            button.setBorder(BorderFactory.createLineBorder(Color.WHITE, 1));
            button.setContentAreaFilled(false);                       
            button.setName(tileType.getName());   
            this.add(button);
            this.tileButtons.put(button, tileType);
        }
        
        JButton destroyButton = new JButton(icons.getToolIcons().get(ToolType.DESTROY));
        destroyButton.setToolTipText(ToolType.DESTROY.getName());
        destroyButton.setBorder(BorderFactory.createLineBorder(Color.WHITE, 1));
        destroyButton.setContentAreaFilled(false);
        destroyButton.setName(ToolType.DESTROY.getName());
        this.add(destroyButton);
        this.tileButtons.put(destroyButton, null);               
    }
    
    private void setToolTipText(JButton button, TileType tileType) {
        if ( tileType.isRoad() ) {
            button.setToolTipText("<html>" + tileType.getName() + 
            "<br>Price: " + tileType.getCost() + 
            "<br>Monthly revenue: " + tileType.getRevenue() +                   
            "<br>Capacity: " + tileType.getCapacity() + "</html>");                        
        } else if ( tileType.isResidential() ) { 
            button.setToolTipText("<html>" + tileType.getName() + 
            "<br>Price: " + tileType.getCost() + 
            "<br>Monthly revenue: " + tileType.getRevenue() + 
            "<br>Pollution: " + tileType.getPollution() +                    
            "<br>Maximum amount of residents: " + tileType.getCapacity() + "</html>");            
        } else {
            button.setToolTipText("<html>" + tileType.getName() + 
            "<br>Price: " + tileType.getCost() + 
            "<br>Monthly revenue: " + tileType.getRevenue() + 
            "<br>Happiness impact: " + tileType.getHappiness() + 
            "<br>Health impact: " + tileType.getHealth() +
            "<br>Impact radius: " + tileType.getRadius() + 
            "<br>Pollution: " + tileType.getPollution() +
            "<br>Workplaces: " + tileType.getWorkplaces() + 
            "<br>Salary: " + tileType.getSalary() + "</html>");           
        }
    }
    
    public HashMap<JButton, TileType> getTileButtons() {
        return this.tileButtons;
    }    
}
