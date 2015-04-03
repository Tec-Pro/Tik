/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package admintik;

import implementsInterface.Client;
import interfaces.InterfaceProvider;
import interfaces.InterfaceProviderCategory;
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
     * @throws java.rmi.NotBoundException
     * @throws java.net.MalformedURLException
     * @throws java.rmi.RemoteException
     */
    public static void main(String[] args) throws NotBoundException, MalformedURLException, RemoteException {

        
        if (System.getSecurityManager() == null){
            System.setSecurityManager(new RMISecurityManager());
        }
        
        
        InterfaceServer server = (InterfaceServer) Naming.lookup("//localhost/Server");
        Client client= new Client();
        server.registerClient(client,"AdminTik");
        
        InterfaceProvider provider = (InterfaceProvider) Naming.lookup("//localhost/crudProvider");
        InterfaceProviderCategory providerCategory = (InterfaceProviderCategory ) Naming.lookup("//localhost/crudProviderCategory");
        
        
    }

}
