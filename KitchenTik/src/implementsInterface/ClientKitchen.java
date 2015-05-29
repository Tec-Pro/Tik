/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package implementsInterface;

import controllers.ControllerGuiKitchenMain;
import interfaces.InterfaceClientKitchen;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;
import java.util.Map;
import utils.Pair;

/**
 *
 * @author eze
 *
 * Implementacion de InterfaceClientKitchen (Metodos que podra invocar el
 * Servidor)
 *
 */
public class ClientKitchen extends UnicastRemoteObject implements InterfaceClientKitchen {

    public ClientKitchen() throws RemoteException {
        super();
    }

    @Override
    public void newOrder(Pair<Map<String, Object>, List<Map>> order) throws RemoteException {
        ControllerGuiKitchenMain.addOrder(order);
    }

    @Override
    public void updatedOrder(Pair<Map<String, Object>, List<Map>> order) throws RemoteException {
       ControllerGuiKitchenMain.updatedOrder(order);
    }

}
