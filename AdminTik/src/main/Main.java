/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package main;

import controllers.ControllerGuiAdminLogin;
import java.net.MalformedURLException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import javax.swing.JFrame;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

/**
 *
 * @author nico
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws NotBoundException, MalformedURLException, RemoteException {

                 try {
            JFrame.setDefaultLookAndFeelDecorated(true);
            com.jtattoo.plaf.aero.AeroLookAndFeel.setTheme("Default");

            UIManager.setLookAndFeel("com.jtattoo.plaf.aero.AeroLookAndFeel");
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException e) {
        }
          
                 ControllerGuiAdminLogin controllerLogin = new ControllerGuiAdminLogin(); //inicio l apantalla del login y desde aca arranca el programa
    }
    
}