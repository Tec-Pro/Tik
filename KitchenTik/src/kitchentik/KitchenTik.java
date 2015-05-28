/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kitchentik;

import controllers.ControllerGuiKitchenMain;
import implementsInterface.ClientKitchen;
import interfaces.InterfaceServer;
import java.io.IOException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import utils.Config;
import utils.InterfaceName;

/**
 *
 * @author eze
 */
public class KitchenTik {

    /**
     * @param args the command line arguments
     * @throws java.rmi.RemoteException
     * @throws java.rmi.NotBoundException
     */
    public static void main(String[] args) throws RemoteException, IOException, NotBoundException {
        ControllerGuiKitchenMain controllerGuiKitchen;
        try {
            JFrame.setDefaultLookAndFeelDecorated(true);
            com.jtattoo.plaf.aero.AeroLookAndFeel.setTheme("Default");

            UIManager.setLookAndFeel("com.jtattoo.plaf.aero.AeroLookAndFeel");
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException e) {
        }

        // Creo el cliente Kitchen
        ClientKitchen client = new ClientKitchen();
        //Creo un nuevo archivo de configuracion
        Config config = new Config(new javax.swing.JFrame(), true);
        try {
            config.loadProperties();
        } catch (IOException ex) {
            Logger.getLogger(KitchenTik.class.getName()).log(Level.SEVERE, null, ex);
        }
        boolean connected = false;
        while (!connected) {
            try {
                InterfaceName.registry = LocateRegistry.getRegistry(Config.ip, Registry.REGISTRY_PORT);
                // le aviso al server que me conecto y que soy Kitchen
                ((InterfaceServer) InterfaceName.registry.lookup(InterfaceName.server)).registerClientKitchen(client);
                connected = true;
            } catch (RemoteException e) {
                config = new Config(new javax.swing.JFrame(), true);
                config.setVisible(true);
                if (config.getReturnStatus() != Config.RET_OK) {
                    System.exit(0);
                }
                connected = false;
            }

        }

        //Creo el controlador principal
        new ControllerGuiKitchenMain();

    }

}
