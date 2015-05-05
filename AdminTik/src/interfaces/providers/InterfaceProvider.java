/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package interfaces.providers;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 *
 * @author nico
 */
public interface InterfaceProvider extends Remote {

    /**
     * Interfaz de la función que crea un proveedor en la base de datos.
     *
     * @param name
     * @param cuit
     * @param address
     * @param description
     * @param phones
     * @return
     * @throws RemoteException
     */
    public Map<String, Object> create(String name, String cuit, String address, String description, String phones) throws java.rmi.RemoteException;

    /**
     *
     * @param id
     * @param name
     * @param cuit
     * @param address
     * @param description
     * @param phones
     * @return
     * @throws RemoteException
     */
    public Map<String, Object> modify(int id, String name, String cuit, String address, String description, String phones) throws java.rmi.RemoteException;

    /**
     *
     * @param id
     * @return
     * @throws RemoteException
     */
    public boolean delete(int id) throws java.rmi.RemoteException;

    /**
     *
     * @param id
     * @return
     * @throws RemoteException
     */
    public Map<String, Object> getProvider(int id) throws java.rmi.RemoteException;

    /**
     *
     * @return @throws RemoteException
     */
    public List<Map> getProviders() throws java.rmi.RemoteException;

    /**
     *
     * @param id
     * @return
     * @throws RemoteException
     */
    public List<Map> getCategoriesFromProvider(int id) throws java.rmi.RemoteException;

    public Map<String, Object> saveCategoriesOfProvider(int provider_id, LinkedList categoriesToAdd, LinkedList categoriesToRemove) throws java.rmi.RemoteException;
}
