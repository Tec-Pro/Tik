/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package interfaces;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;
import java.util.Map;
import utils.Pair;

/**
 *
 * @author nico
 *
 * En esta clase van los métodos para que el server pueda invocarlos
 */
public interface InterfaceClientWaiter extends Remote {

    public void doSomething() throws RemoteException;

    /**
     * es un ejemplo de cuando la cocina envia un pedido que esta listo y le
     * pasa el id
     *
     * @param order
     * @param id id del pedido puevo
     * @throws RemoteException
     */
    public void readyOrder(Pair<Map<String, Object>, List<Map>> order) throws RemoteException;

    public void orderDelayed(int idOrder) throws RemoteException;
}
