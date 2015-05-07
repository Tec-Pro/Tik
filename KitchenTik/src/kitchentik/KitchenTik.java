/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kitchentik;

import javax.swing.JFrame;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

/**
 *
 * @author eze
 */
public class KitchenTik {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        try {
            JFrame.setDefaultLookAndFeelDecorated(true);
            com.jtattoo.plaf.aero.AeroLookAndFeel.setTheme("Default");

            UIManager.setLookAndFeel("com.jtattoo.plaf.aero.AeroLookAndFeel");
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException e) {
        }
    }
    
}
