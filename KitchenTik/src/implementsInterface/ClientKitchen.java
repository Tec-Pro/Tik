/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package implementsInterface;

import interfaces.InterfaceClientKitchen;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

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
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void updatedOrder(int id) throws RemoteException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    
    
}
