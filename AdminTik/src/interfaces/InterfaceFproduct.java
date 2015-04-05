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
public interface InterfaceFproduct extends Remote {
    
     /**
     * Crea un producto final.
     * @param name
     * @param subcategory_id
     * @param pProducts es una List<Pair<Integer,Float> con todos los id de productos primarios y cantidades que forman al producto elaborado
     * @param eProducts es una List<Pair<Integer,Float> con todos los id de productos elaborados y cantidades que forman al producto elaborado
     * @return Map<String,Object> 
     * @throws java.rmi.RemoteException
     */
    public Map<String,Object> create(String name, int subcategory_id, List<Pair> pProducts, List<Pair> eProducts) throws java.rmi.RemoteException;
         /**
     * Modifica un producto final.
     * @param id
     * @param name
     * @param subcategory_id
     * @param pProducts es una List<Pair<Integer,Float> con todos los id de productos primarios y cantidades que forman al producto elaborado
     * @param eProducts es una List<Pair<Integer,Float> con todos los id de productos elaborados y cantidades que forman al producto elaborado
     * @return Map<String,Object>
     * @throws java.rmi.RemoteException
     */
     public Map<String,Object> modify(int id,String name, int subcategory_id, List<Pair> pProducts, List<Pair> eProducts) throws java.rmi.RemoteException;
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
     
        /**
     * Dado un producto final retonra todos sus productos primarios.
     * @param id
     * @return List<Map>
     * @throws java.rmi.RemoteException
     */
     public  List<Map> getPproducts(int id) throws java.rmi.RemoteException; 
     
        /**
     * Dado un producto final retonra todos sus productos elaborados.
     * @param id
     * @return List<Map>
     * @throws java.rmi.RemoteException
     */
     public  List<Map> getEproducts(int id) throws java.rmi.RemoteException;
     
     /**
     * Retorna todos los productos fianles.
     * @param name o id
     * @return List<Map>
     * @throws java.rmi.RemoteException
     */
     public List<Map> getFproducts(String name) throws java.rmi.RemoteException; 
     
     /**
     * Retorna la relacion fproducto pproducto
     * @param eProductName
     * @return List<Map>
     * @throws java.rmi.RemoteException
     */
     public  List<Map> getFproductPproduts(int idFproduct) throws java.rmi.RemoteException;
     
      /**
     * Retorna la relacion fproducto eproducto
     * @param eProductName
     * @return List<Map>
     * @throws java.rmi.RemoteException
     */
     public  List<Map> getFproductEproduts(int idFproduct) throws java.rmi.RemoteException;
}
