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
public interface InterfaceClientBar extends Remote {

    /**
     * Metodo para avisar desde el Servidor al Bar que un nuevo Pedido fue
     * creado
     *
     * @param id
     * @throws RemoteException
     */
    public void newOrder(Pair<Map<String, Object>, List<Map>> order) throws RemoteException;

    /**
     * Metodo para avisar desde el Servidor al Bar que un Pedido fue modificado
     *
     * @param id
     * @throws RemoteException
     */
    public void updatedOrder(Pair<Map<String, Object>, List<Map>> order) throws RemoteException;

    
    /**Metodo para avisar desde el Servidor al Bar que un pedido de la cocina está listo
     * 
     * @param order 
     * @throws RemoteException 
     */
    public void kitchenOrderReady(Map<String, Object>order) throws RemoteException;
}
