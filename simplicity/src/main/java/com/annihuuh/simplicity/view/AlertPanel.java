
package com.annihuuh.simplicity.view;

import com.annihuuh.simplicity.model.City;
import java.awt.Color;
import java.awt.Font;
import java.util.Observable;
import java.util.Observer;
import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * Contains a label that tells the player if the build, destroy or pause mode 
 * is on. Also displays the city's date and cash amount.
 *
 * @author annihuuh
 */
public class AlertPanel extends JPanel implements Observer {
    private JLabel alertLabel;  
    private JLabel cashLabel;
    private JLabel dateLabel;
    
    public AlertPanel(City city) {
        this.setLayout();
        this.createLabels();
    } 
    
    private void setLayout() {
        super.setBackground(Color.WHITE);
    }
    
    private void createLabels() {
        Font font = new Font("Dialog", Font.PLAIN, 16);
        
        this.cashLabel = new JLabel();
        this.cashLabel.setBorder(BorderFactory.createLineBorder
                                (Color.WHITE, 15));
        
        this.cashLabel.setFont(font);
        
        this.dateLabel = new JLabel(); 
        this.dateLabel.setBorder(BorderFactory.createLineBorder
                                (Color.WHITE, 15));
        
        this.dateLabel.setFont(font);
        
        this.alertLabel = new JLabel();
        this.alertLabel.setForeground(Color.RED);      
        this.alertLabel.setFont(font);      
                      
        this.addLabels();
    }
    
    private void addLabels() {
        this.add(this.cashLabel);
        this.add(this.dateLabel); 
        this.add(this.alertLabel);
    }

    public JLabel getAlertLabel() {
        return this.alertLabel;
    }

    public JLabel getCashLabel() {
        return this.cashLabel;
    }

    public JLabel getDateLabel() {
        return this.dateLabel;
    }   
    
    /**
     * Sets a message to the alert label.
     * 
     * @param   message    the text to set
     */     
    public void showMessage(String message) {
        this.alertLabel.setText(message);
    }            

    @Override
    public void update(Observable o, Object arg) {
        City city = (City) o;
        
        this.cashLabel.setText("Cash: " + 
                                Math.round(city.getFinance().getCash()));
        
        this.dateLabel.setText(city.getDate().toString());       
    }
}
