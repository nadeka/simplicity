package com.annihuuh.simplicity.view;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

/**
 * The menu bar of the gui. Stores the menus and menu items in a map.
 *
 * @author annihuuh
 */
public class MenuBar extends JMenuBar {
    private HashMap<JMenu, List<JMenuItem>> menus;
    
    public MenuBar() {
        this.menus = new HashMap<>();
        this.createMenus();
    }
    
    private void createMenus() {
        JMenu menu = new JMenu("Menu");
              
        JMenuItem newGame = new JMenuItem("New game"); 
        JMenuItem save = new JMenuItem("Save");
        JMenuItem load = new JMenuItem("Load");
        JMenuItem quit = new JMenuItem("Quit");
        
        menu.add(newGame);
        menu.add(save);
        menu.add(load);
        menu.add(quit);
        
        this.menus.put(menu, new ArrayList<JMenuItem>());
        this.menus.get(menu).add(newGame);
        this.menus.get(menu).add(save);
        this.menus.get(menu).add(load);
        this.menus.get(menu).add(quit);
        
        for ( JMenu m : this.menus.keySet() ) {
            this.add(m);
        }      
    }

    public HashMap<JMenu, List<JMenuItem>> getMenus() {
        return this.menus;
    }
}
