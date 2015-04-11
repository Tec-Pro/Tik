/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package interfaces.providers;

import java.rmi.Remote;
import java.util.List;
import java.util.Map;

/**
 *
 * @author nico
 */
public interface InterfaceProvider extends Remote {

    public Map<String,Object> create(String name, String cuit,String address,String description, String phones) throws java.rmi.RemoteException;
     
     public Map<String,Object> modify(int id,String name, String cuit,String address,String description, String phones) throws java.rmi.RemoteException;
     
     public boolean delete(int id) throws java.rmi.RemoteException;
     
     public Map<String,Object> getProvider(int id) throws java.rmi.RemoteException;
     
     public  List<Map> getProviders() throws java.rmi.RemoteException; 
     
     public List<Map> getCategoriesFromProvider(int id) throws java.rmi.RemoteException;
     
     /**
     *Función que agrega una categoría a un proveedor. Recibe como parámetros
     * el <code>id_proveedor</code> y el <code>id_categoría</code>. Devuelve un 
     * Map conteniendo el proveedor.
     * 
     * @param provider_id
     * @param category_id
     * @return Un map con el proveedor.
     * @throws java.rmi.RemoteException
     */
     public Map<String,Object> addCategoryToProvider(int provider_id, int category_id) throws java.rmi.RemoteException;
}
