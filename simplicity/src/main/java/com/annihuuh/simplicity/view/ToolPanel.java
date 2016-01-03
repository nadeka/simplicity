package com.annihuuh.simplicity.view;

import com.annihuuh.simplicity.model.City;
import java.awt.Color;
import java.awt.Font;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;

/**
 * Contains tools for pausing the game, zooming on the canvas and changing the tax rates.
 * 
 * @author annihuuh
 */
public class ToolPanel extends JPanel implements Observer {
    private List<JButton> toolButtons;
    
    private JSpinner workerTaxRateSpinner;
    private JSpinner corporateTaxRateSpinner;
    
    public ToolPanel(Resources icons) {
        this.toolButtons = new ArrayList<>(); 
        
        this.initLayout();
        this.createButtons(icons);
        this.createSpinners();
    }
    
    private void initLayout() {
        super.setBackground(Color.WHITE);
    }
    
    private void createButtons(Resources icons) {
        JButton button = new JButton(icons.getToolIcons().get(ToolType.ZOOMIN));
        button.setToolTipText(ToolType.ZOOMIN.getName());
        button.setBorder(BorderFactory.createEmptyBorder());
        button.setContentAreaFilled(false);
        button.setName(ToolType.ZOOMIN.getName());
        this.add(button); 
        this.toolButtons.add(button);
        
        JButton button2 = new JButton(icons.getToolIcons().get(ToolType.ZOOMOUT));
        button2.setToolTipText(ToolType.ZOOMOUT.getName());
        button2.setBorder(BorderFactory.createEmptyBorder());
        button2.setContentAreaFilled(false);
        button2.setName(ToolType.ZOOMOUT.getName());
        this.add(button2); 
        this.toolButtons.add(button2);
        
        JButton button3 = new JButton(icons.getToolIcons().get(ToolType.CONTINUE));
        button3.setToolTipText(ToolType.CONTINUE.getName());
        button3.setBorder(BorderFactory.createEmptyBorder());
        button3.setContentAreaFilled(false);
        button3.setName(ToolType.CONTINUE.getName());
        this.add(button3); 
        this.toolButtons.add(button3);  

        JButton button4 = new JButton(icons.getToolIcons().get(ToolType.PAUSE));
        button4.setToolTipText(ToolType.PAUSE.getName());
        button4.setBorder(BorderFactory.createEmptyBorder());
        button4.setContentAreaFilled(false);
        button4.setName(ToolType.PAUSE.getName());
        this.add(button4); 
        this.toolButtons.add(button4);          
    }
    
    private void createSpinners() {
        Font font = new Font("Dialog", Font.PLAIN, 12);
         
        JLabel workerTaxRateSpinnerLabel = new JLabel("Worker tax rate: ");
        workerTaxRateSpinnerLabel.setFont(font);
        
        JLabel corporateTaxRateSpinnerLabel = new JLabel("Business tax rate: ");
        corporateTaxRateSpinnerLabel.setFont(font);       

        this.workerTaxRateSpinner = new JSpinner
                                    (new SpinnerNumberModel(0, 0.0, 1.0, 0.01)); 
        
        this.workerTaxRateSpinner.setName("Worker tax rate spinner");
        
        this.corporateTaxRateSpinner = new JSpinner
                                    (new SpinnerNumberModel(0, 0.0, 1.0, 0.01));
        
        this.corporateTaxRateSpinner.setName("Corporate tax rate spinner");

        ((JSpinner.DefaultEditor) workerTaxRateSpinner.getEditor()).
                                                   getTextField().setColumns(3);
        
        ((JSpinner.DefaultEditor) workerTaxRateSpinner.getEditor()).
                                                   getTextField().setEditable(false);        
        
        ((JSpinner.DefaultEditor) corporateTaxRateSpinner.getEditor()).
                                                   getTextField().setColumns(3);   
        
        ((JSpinner.DefaultEditor) corporateTaxRateSpinner.getEditor()).
                                                   getTextField().setEditable(false);          
        
        this.add(workerTaxRateSpinnerLabel);
        this.add(this.workerTaxRateSpinner);        
        this.add(corporateTaxRateSpinnerLabel);
        this.add(this.corporateTaxRateSpinner);        
    }
    
    public List<JButton> getToolButtons() {
        return this.toolButtons;
    } 

    public JSpinner getWorkerTaxRateSpinner() {
        return this.workerTaxRateSpinner;
    }

    public JSpinner getCorporateTaxRateSpinner() {
        return this.corporateTaxRateSpinner;
    }  

    @Override
    public void update(Observable o, Object arg) {
        City city = (City) o;
        
        this.workerTaxRateSpinner.getModel().setValue
                                        (city.getFinance().getWorkerTaxRate());
        
        this.corporateTaxRateSpinner.getModel().setValue
                                     (city.getFinance().getCorporateTaxRate());        
    }
}
