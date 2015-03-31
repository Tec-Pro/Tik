/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package interfaces;

import java.rmi.Remote;
import java.util.List;
import java.util.Map;
import utils.Pair;

/**
 *
 * @author jacinto
 */
public interface InterfaceEproduct extends Remote {
    
    /**
     * Crea un producto elaborado.
     * @param name
     * @param stock
     * @param measureUnit
     * @param subcategory_id
     * @param pProducts es una List<Pair<Integer,Float> con todos los id de productos primarios y cantidades que forman al producto elaborado
     * @return Map<String,Object> 
     * @throws java.rmi.RemoteException
     */
    public Map<String,Object> create(String name, float stock, String measureUnit, int subcategory_id, List<Pair> pProducts) throws java.rmi.RemoteException;
     
    /**
     * Modifica un producto elaborado.
     * @param id
     * @param name
     * @param stock
     * @param measureUnit
     * @param subcategory_id
     * @param pProducts es una List<Pair<Integer,Float> con todos los id de productos primarios y cantidades que forman al producto elaborado
     * @return Map<String,Object>
     * @throws java.rmi.RemoteException
     */
     public Map<String,Object> modify(int id,String name, float stock, String measureUnit, int subcategory_id, List<Pair> pProducts) throws java.rmi.RemoteException;
         /**
     * Elimina un producto elaborado de manera logica, setea al atributo removed en 1 como asi tambien a todos sus relacionados.
     * @param id
     * @return boolean
     * @throws java.rmi.RemoteException
     */
     public boolean delete(int id) throws java.rmi.RemoteException;
      /**
     * Retorna un produto elaborado
     * @param id
     * @return Map<String,Object>
     * @throws java.rmi.RemoteException
     */
     public Map<String,Object> getEproduct(int id) throws java.rmi.RemoteException;     
     /**
     * Retorna todos los productos elaborados.
     * @return List<Map>
     * @throws java.rmi.RemoteException
     */
     public  List<Map> getEproducts() throws java.rmi.RemoteException;   
     
     /**
     * Dado un producto elaborado retonra todos sus productos primarios.
     * @param id
     * @return List<Map>
     * @throws java.rmi.RemoteException
     */
     public  List<Map> getPproducts(int id) throws java.rmi.RemoteException;    

}
