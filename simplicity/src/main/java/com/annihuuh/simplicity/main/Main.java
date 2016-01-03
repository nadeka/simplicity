package com.annihuuh.simplicity.main;


import com.annihuuh.simplicity.controller.Game;
import com.annihuuh.simplicity.model.City;
import com.annihuuh.simplicity.view.Gui;
import javax.swing.SwingUtilities;

/**
 *
 * @author annihuuh
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        City city = new City(50, 50);
        Gui gui = new Gui(city);
        Game game = new Game(gui, city);

        SwingUtilities.invokeLater(gui);

        game.start();
    }   
}
