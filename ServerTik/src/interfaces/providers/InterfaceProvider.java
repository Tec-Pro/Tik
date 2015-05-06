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
     * Crea un proveedor en la base de datos.
     *
     * @param name nombre del proveedor.
     * @param cuit cuit del proveedor.
     * @param address dirección del proveedor.
     * @param description descripción del proveedor.
     * @param phones teléfonos del proveedor.
     * @return map representing the created provider.
     * @throws RemoteException
     */
    public Map<String, Object> create(String name, String cuit, String address, String description, String phones) throws java.rmi.RemoteException;

    /**
     * Función que modifica un proveedor existente de la DB.
     *
     * @param id id del proveedor a modificar.
     * @param name nuevo nombre del proveedor.
     * @param cuit nuevo cuit del proveedor.
     * @param address nueva dirección del proveedor.
     * @param description nueva descripción del proveedor.
     * @param phones nuevos teléfonos del proveedor.
     * @return a map representing the modified provider if exists, empty map
     * otherwise.
     * @throws RemoteException
     */
    public Map<String, Object> modify(int id, String name, String cuit, String address, String description, String phones) throws java.rmi.RemoteException;

    /**
     * Función que elimina un proveedor si existe en la DB.
     *
     * @param id id del proveedor a eliminar.
     * @return true if the provider was deleted from the database.
     * @throws RemoteException
     */
    public boolean delete(int id) throws java.rmi.RemoteException;

    /**
     * Función que devuelve el proveedor correspondiente con el id pasado.
     *
     * @param id id del proveedor pedido.
     * @return map representing the requested provider.
     * @throws RemoteException
     */
    public Map<String, Object> getProvider(int id) throws java.rmi.RemoteException;

    /**
     * Función que lista todos los proveedores existentes en la DB.
     *
     * @return a list of maps representing the providers.
     * @throws RemoteException
     */
    public List<Map> getProviders() throws java.rmi.RemoteException;

    /**
     * Función que devuelve las categorías de un proveedor específico.
     *
     * @param id id del proveedor del que se quieren obtener las categorías.
     * @return una lista de Maps que representan las categorías de un proveedor.
     * @throws RemoteException
     */
    public List<Map> getCategoriesFromProvider(int id) throws java.rmi.RemoteException;

    /**
     * Función que guarda las nuevas categorías de un proveedor.
     *
     * @param provider_id id del proveedor al que se le guardan las categorías.
     * @param categoriesToAdd categorías para agregar al proveedor especificado.
     * @param categoriesToRemove categorías para remover del proveedor
     * especificado.
     * @return un map representando al proveedor especificado.
     * @throws RemoteException
     */
    public Map<String, Object> saveCategoriesOfProvider(int provider_id, LinkedList categoriesToAdd, LinkedList categoriesToRemove) throws java.rmi.RemoteException;
}
