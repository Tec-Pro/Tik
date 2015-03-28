/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package interfaces;

import java.rmi.Remote;
import java.util.List;
import java.util.Map;

/**
 *
 * @author jacinto
 */
public interface InterfaceFproduct extends Remote {
    
     /**
     * Crea un producto final.
     * @param name
     * @param subcategory_id
     * @return Map<String,Object> 
     * @throws java.rmi.RemoteException
     */
    public Map<String,Object> create(String name, int subcategory_id) throws java.rmi.RemoteException;
         /**
     * Modifica un producto final.
     * @param id
     * @param name
     * @param subcategory_id
     * @return Map<String,Object>
     * @throws java.rmi.RemoteException
     */
     public Map<String,Object> modify(int id,String name, int subcategory_id) throws java.rmi.RemoteException;
         /**
     * Elimina un producto final.
     * @param id
     * @return boolean
     * @throws java.rmi.RemoteException
     */
     public boolean delete(int id) throws java.rmi.RemoteException;
      /**
     * Retorna un produto final
     * @param id
     * @return Map<String,Object>
     * @throws java.rmi.RemoteException
     */
     public Map<String,Object> getFproduct(int id) throws java.rmi.RemoteException;     
     /**
     * Retorna todos los productos finales.
     * @return List<Map>
     * @throws java.rmi.RemoteException
     */
     public  List<Map> getFproducts() throws java.rmi.RemoteException;
}
