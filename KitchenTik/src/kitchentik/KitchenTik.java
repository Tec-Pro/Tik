/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kitchentik;

import implementsInterface.ClientKitchen;
import interfaces.InterfaceServer;
import java.io.IOException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import utils.Config;

/**
 *
 * @author eze
 */
public class KitchenTik {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws RemoteException, IOException, NotBoundException {
        try {
            JFrame.setDefaultLookAndFeelDecorated(true);
            com.jtattoo.plaf.aero.AeroLookAndFeel.setTheme("Default");

            UIManager.setLookAndFeel("com.jtattoo.plaf.aero.AeroLookAndFeel");
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException e) {
        }
        
        
              
              
                            ClientKitchen client= new ClientKitchen();//creo el cliente este

                      Config config = new Config(new javax.swing.JFrame(), true);
        try {
            config.loadProperties();
        } catch (IOException ex) {
            Logger.getLogger(KitchenTik.class.getName()).log(Level.SEVERE, null, ex);
        }
        boolean connected = false;
        while (!connected) {
            try {
             ( (InterfaceServer) Naming.lookup("//" + Config.ip + "/Server")).registerClientKitchen(client, "kitchen");//le digo al server que me conecto y soy un mozo
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
 
        //Aca poner el controlador principal y con la gui principal      
    }
        
    
    
}
