/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package implementsInterface;

import interfaces.InterfaceClientBar;
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
public class ClientBar extends UnicastRemoteObject implements InterfaceClientBar {

    public ClientBar() throws RemoteException{
        super();
    }

    @Override
    public void newOrder(int id) throws RemoteException {
        //ControllerGuiBarMain.addOrder(id);
        System.out.println("Llego el pedido: "+id);
    }

    @Override
    public void updatedOrder(int id) throws RemoteException {
        //ControllerGuiBarMain.updatedOrder(id);
        System.out.println("Se actualizo el pedido"+id);
    }
    
    
    
}
