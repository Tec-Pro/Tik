/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package waitertik;

import controller.ControllerGuiMain;
import implementsInterface.Client;
import interfaces.InterfaceServer;
import java.io.IOException;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import utils.Config;

/* prueba cargar orden , borrar esto despues
import interfaces.InterfaceOrder;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;*/

/**
 *
 * @author nico
 */
public class WaiterTik {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws RemoteException, MalformedURLException, NotBoundException {
              try {
            JFrame.setDefaultLookAndFeelDecorated(true);
            com.jtattoo.plaf.aero.AeroLookAndFeel.setTheme("Default");

            UIManager.setLookAndFeel("com.jtattoo.plaf.aero.AeroLookAndFeel");
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException e) {
        }
              
              
              
                            Client client= new Client();//creo el cliente este

                      Config config = new Config(new javax.swing.JFrame(), true);
        try {
            config.loadProperties();
        } catch (IOException ex) {
            Logger.getLogger(WaiterTik.class.getName()).log(Level.SEVERE, null, ex);
        }
        boolean connected = false;
        while (!connected) {
            try {
             ( (InterfaceServer) Naming.lookup("//" + Config.ip + "/Server")).registerClient(client, "waiter");//le digo al server que me conecto y soy un mozo
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
        /*--------Prueba cargar orden-----*/
        /*InterfaceOrder crudOrder = (InterfaceOrder)Naming.lookup("//" + Config.ip + "/crudOrder") ;
        List<Map<String,Object>> products = new LinkedList<>();
        Map<String,Object> product = new HashMap();
        product.put("fproductId", 1);
        product.put("quantity",67);
        product.put("done", false);
        product.put("commited", false);
        product.put("issued", true);
        products.add(product);
        crudOrder.sendOrder("Nueva orden!", products);*/
        ControllerGuiMain c = new ControllerGuiMain();          
    }
    
}
