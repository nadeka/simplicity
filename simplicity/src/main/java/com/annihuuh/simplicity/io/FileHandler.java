package com.annihuuh.simplicity.io;

import com.annihuuh.simplicity.model.City;
import com.annihuuh.simplicity.view.Dialogs;
import com.annihuuh.simplicity.view.Gui;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import javax.swing.JFileChooser;

/**
 * Offers methods for saving and loading games.
 *
 * @author annihuuh
 */
public class FileHandler {
    private JFileChooser fileChooser;
    
    public FileHandler() {
        this.fileChooser = new JFileChooser();
    }   
    
    /**
     * Reads a city object from a file the user selects.
     *
     * @param   gui    the gui for showing dialogs
     * 
     * @return  the loaded city object if the loading was successful, 
     *          null otherwise   
     */    
    public City load(Gui gui) {     
        this.fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY); 
        int option = this.fileChooser.showOpenDialog(gui.getFrame());
        
        File file = this.getFile(option);
        
        if ( file == null ) {
            return null;
        }

        try {
            try ( FileInputStream fileInputStream = new FileInputStream(file); 
                  ObjectInputStream objectInputStream = 
                          new ObjectInputStream(fileInputStream) ) {
                
                  return (City) objectInputStream.readObject();
            }
        } catch ( IOException | ClassNotFoundException exception ) {
            Dialogs.showExceptionErrorDialog(exception, gui.getFrame());
            return null;
        }
    } 
    
    /**
     * Writes a city object to a file the user selects.
     *
     * @param   gui     the gui for showing dialogs
     * @param   city    the city to save
     */       
    public void save(City city, Gui gui) {
        int option = this.fileChooser.showSaveDialog(gui.getFrame());
        
        File file = this.getFile(option);
        
        if ( file == null ) {
            return;
        }

        try {
            try ( FileOutputStream fileOutputStream = 
                    new FileOutputStream(file); 
                  ObjectOutputStream objectOutputStream = 
                        new ObjectOutputStream(fileOutputStream) ) {
                
                  objectOutputStream.writeObject(city);
                  Dialogs.showSaveSuccessDialog();
            }
        } catch (IOException exception) {
            Dialogs.showExceptionErrorDialog(exception, gui.getFrame());
        }        
    }
    
    private File getFile(int option) {
        if ( option == JFileChooser.APPROVE_OPTION ) {
            return this.fileChooser.getSelectedFile();
        } else {
            return null;
        }        
    }
}
