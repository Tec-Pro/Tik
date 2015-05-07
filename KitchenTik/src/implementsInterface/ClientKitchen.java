/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package implementsInterface;

import interfaces.InterfaceClientKitchen;
import interfaces.InterfaceOrder;
import java.net.MalformedURLException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author eze
 * 
 * Implementacion de InterfaceClientKitchen
 * (Metodos que podra invocar el Servidor)
 * 
 */
public class ClientKitchen extends UnicastRemoteObject implements InterfaceClientKitchen {

    public ClientKitchen() throws RemoteException{
        super();
    }

    @Override
    public void newOrder(int id) throws RemoteException {
        System.out.println("Llego el pedido: "+id);
    }

    @Override
    public void updatedOrder(int id) throws RemoteException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    
    
}
