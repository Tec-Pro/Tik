/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import controllers.ControllerGuiAdminLogin;
import java.io.IOException;
import java.net.MalformedURLException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import utils.GeneralConfig;

/**
 *
 * @author nico
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws NotBoundException, MalformedURLException, RemoteException, IOException {
        try {
            JFrame.setDefaultLookAndFeelDecorated(true);
            com.jtattoo.plaf.aero.AeroLookAndFeel.setTheme("Default");
            UIManager.setLookAndFeel("com.jtattoo.plaf.aero.AeroLookAndFeel");
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException e) {
        }
        try {
            GeneralConfig.loadProperties();
        } catch (IOException ex) {
            GeneralConfig.createDefaultProperties();
            JOptionPane.showMessageDialog(null, "El archivo de configuracion no existe, la configuracion por default fue creada.");
        }
        ControllerGuiAdminLogin controllerLogin = new ControllerGuiAdminLogin(); //inicio l apantalla del login y desde aca arranca el programa
    }
}
