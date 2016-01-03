package com.annihuuh.simplicity.view;

import com.annihuuh.simplicity.model.City;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import javax.swing.JFrame;
import javax.swing.JScrollPane;

/**
 * The main gui class.
 * 
 * @author annihuuh
 */
public class Gui implements Runnable {   
    private JFrame frame;
    
    private Dialogs dialogs;
    private City city; 

    private boolean isDisplayable;
    
    private MenuBar menuBar;
    private CanvasPanel canvasPanel;
    private TilePanel tilePanel;  
    private ToolPanel toolPanel;   
    private AlertPanel alertPanel;
    
    private InfoPanel infoPanel; 
    
    public Gui(City city) {
        this.city = city;
        this.isDisplayable = false;
        this.dialogs = new Dialogs();
    }

    public boolean isDisplayable() {
        return this.isDisplayable;
    }
    
    public JFrame getFrame() {
        return this.frame;
    }

    public Dialogs getDialogs() {
        return this.dialogs;
    }
    
    public MenuBar getMenuBar() {
        return this.menuBar;
    }

    public CanvasPanel getCanvasPanel() {
        return this.canvasPanel;
    }

    public TilePanel getTilePanel() {
        return this.tilePanel;
    }

    public ToolPanel getToolPanel() {
        return this.toolPanel;
    }

    public InfoPanel getInfoPanel() {
        return this.infoPanel;
    }

    public AlertPanel getAlertPanel() {
        return this.alertPanel;
    }

    @Override
    public void run() { 
        this.frame = new JFrame("Simplicity");
        
        this.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.frame.setPreferredSize(new Dimension(1300, 720));
        this.frame.setBackground(Color.WHITE);      
        
        this.createComponents();
 
        this.frame.pack();
        this.isDisplayable = true;
        this.frame.setVisible(true);
    }
    
    private void createComponents() {
        Resources resources = new Resources();
        resources.load(this);
        
        this.menuBar = new MenuBar();   
        this.canvasPanel = new CanvasPanel(this.city.getMap(), resources);
        this.tilePanel = new TilePanel(resources);
        this.toolPanel = new ToolPanel(resources);
        this.infoPanel = new InfoPanel(this.city.getMetrics());    
        this.alertPanel = new AlertPanel(this.city);
        
        this.addComponents();
    } 
    
    private void addComponents() {
        this.frame.add(this.canvasPanel, BorderLayout.CENTER);
        this.frame.add(this.tilePanel, BorderLayout.WEST);
        this.frame.add(this.toolPanel, BorderLayout.PAGE_END);
        this.frame.add(this.alertPanel, BorderLayout.PAGE_START);
        this.frame.add(this.infoPanel, BorderLayout.EAST);
        
        this.frame.setJMenuBar(this.menuBar);
        
        JScrollPane scrollPane = new JScrollPane(this.canvasPanel);
        scrollPane.setViewportView(this.canvasPanel);
        scrollPane.getVerticalScrollBar().setUnitIncrement(10);   
        this.frame.add(scrollPane, BorderLayout.CENTER);   
        
        JScrollPane scrollPane2 = new JScrollPane(this.tilePanel);
        scrollPane2.setViewportView(this.tilePanel);
        scrollPane2.setPreferredSize(new Dimension(80, this.frame.getHeight()));
        scrollPane2.getVerticalScrollBar().setUnitIncrement(10);         
        this.frame.add(scrollPane2, BorderLayout.WEST);           
    }
}
