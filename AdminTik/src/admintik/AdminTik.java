/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package admintik;



import controllers.ControllerGuiAdminLogin;

import interfaces.InterfaceServer;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RMISecurityManager;
import java.rmi.RemoteException;

/**
 *
 * @author agustin
 */
public class AdminTik{

    
    
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws NotBoundException, MalformedURLException, RemoteException {

        
        if (System.getSecurityManager() == null){
            System.setSecurityManager(new RMISecurityManager());
        }
        
        
        InterfaceServer server = (InterfaceServer) Naming.lookup("//192.168.1.26/Server");
        
       // InterfaceAdmin crudAdmin = (InterfaceAdmin)   Naming.lookup("//192.168.1.26/crudAdmin");
        ControllerGuiAdminLogin controllerLogin = new ControllerGuiAdminLogin();
       //ControllerGuiAdmin mainController = new ControllerGuiAdmin();
     
    }

}
