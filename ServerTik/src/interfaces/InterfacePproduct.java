/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package interfaces;

import java.rmi.Remote;
import java.util.List;
import java.util.Map;

/**
 *
 * @author nico
 */
public interface InterfacePproduct extends Remote {
    
    /**
     * Crea un producto primario.
     * @param name
     * @param stock
     * @param measureUnit
     * @param unitPrice
     * @return Map<String,Object> 
     * @throws java.rmi.RemoteException
     */
    public Map<String,Object> create(String name, float stock, String measureUnit, float unitPrice) throws java.rmi.RemoteException;
         /**
     * Modifica un producto primario.
     * @param id
     * @param name
     * @param stock
     * @param measureUnit
     * @param unitPrice
     * @return Map<String,Object>
     * @throws java.rmi.RemoteException
     */
     public Map<String,Object> modify(int id,String name, float stock, String measureUnit, float unitPrice) throws java.rmi.RemoteException;
         /**
     * Elimina un producto primario de manera logica, setea al atributo removed en 1 como asi tambien a todos sus relacionados.
     * @param id
     * @return boolean
     * @throws java.rmi.RemoteException
     */
     public boolean delete(int id) throws java.rmi.RemoteException;
      /**
     * Retorna un produto primario
     * @param id
     * @return Map<String,Object>
     * @throws java.rmi.RemoteException
     */
     public Map<String,Object> getPproduct(int id) throws java.rmi.RemoteException;     
     /**
     * Retorna todos los productos primarios.
     * @return List<Map>
     * @throws java.rmi.RemoteException
     */
     public  List<Map> getPproducts() throws java.rmi.RemoteException;    
}
