package com.annihuuh.simplicity.view;

import com.annihuuh.simplicity.model.City;
import com.annihuuh.simplicity.model.Metrics;
import java.awt.Color;
import java.awt.Font;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * Observes and displays the city's statistics.
 *
 * @author annihuuh
 */
public class InfoPanel extends JPanel implements Observer {      
    private List<JLabel> labels;
    
    public InfoPanel(Metrics metrics) {
        this.labels = new ArrayList<>();
        this.setLayout();
        this.createComponents(metrics);
    }    
    
    private void setLayout() {
        super.setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
        super.setBorder(BorderFactory.createLineBorder(Color.WHITE, 10));
        super.setBackground(Color.WHITE);         
    }
    
    private void createComponents(Metrics metrics) {
        Font font = new Font("Dialog", Font.PLAIN, 14);
        
        for ( String key : metrics.getKeys() ) {
            JLabel label = new JLabel(key + ": " 
                                      + metrics.getValue(key));
            
            label.setName(key);
            label.setBorder(BorderFactory.createLineBorder(Color.WHITE, 2));
            label.setFont(font);
            this.add(label);
            this.labels.add(label);
        }        
    }

    @Override
    public void update(Observable o, Object arg) {
        City city = (City) o;
        
        for ( JLabel label : this.labels ) {
            label.setText(label.getName() + ": " + 
                       Math.round(city.getMetrics().getValue(label.getName())));   
        }        
    }    
} 
