package com.annihuuh.simplicity.view;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

/**
 * Contains methods for notifying the player about errors and asking for input. 
 *
 * @author annihuuh
 */
public class Dialogs {
    
    public static int getQuitOption(JFrame frame) {
        Object[] options = {"Yes", "Cancel", "No, quit"};
        
        return JOptionPane.showOptionDialog(frame,
            "Would you like to save the game before quitting?",
            "Quit",
            JOptionPane.YES_NO_CANCEL_OPTION,
            JOptionPane.QUESTION_MESSAGE,
            null,
            options,
            options[0]);
    }
    
    public static int getGameOverOption(JFrame frame) {
        Object[] options = {"Yes",
                            "No, quit"};
        
        return JOptionPane.showOptionDialog(frame,
            "Would you like to start a new game?",
            "Game over",
            JOptionPane.YES_NO_OPTION,
            JOptionPane.INFORMATION_MESSAGE,
            null,
            options,
            options[0]); 
    }  
    
    public static int getNewGameOption(JFrame frame) {
        Object[] options = {"Yes",
                            "Cancel"};
        
        return JOptionPane.showOptionDialog(frame,
            "Are you sure you want to start a new game?",
            "New game",
            JOptionPane.YES_NO_OPTION,
            JOptionPane.INFORMATION_MESSAGE,
            null,
            options,
            options[0]); 
    }      
    
    public static void showExceptionErrorDialog(Exception exception, 
                                                JFrame frame) {
        JOptionPane.showMessageDialog(frame,
        "An error occurred. " + "\n" + "Error message: " + '"' 
         + exception.getMessage() + '"',
        "",
        JOptionPane.ERROR_MESSAGE,
        null);          
    }
    
    public static void showSaveSuccessDialog() {
        JOptionPane.showMessageDialog(null, "Game saved successfully.");
    }      
}
