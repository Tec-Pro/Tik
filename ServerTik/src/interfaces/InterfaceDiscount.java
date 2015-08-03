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
public interface InterfaceDiscount extends Remote {

    /**
     * Crea un descuento
     * @param fproduct_id
     * @param user_id
     * @param order_id
     * @return Map<String,Object>
     * @throws java.rmi.RemoteException
     */
    public Map<String, Object> create(int fproduct_id, int user_id, int order_id) throws java.rmi.RemoteException;

        /**
     * devuelve los descuentos entre dos fechas de un mozo, si es -1 el id devuelve todos
     *
     * @param user_id
     * @param dateFrom
     * @param dateTo
     * 
     * @return List<Map>
     * @throws java.rmi.RemoteException
     */
    public List<Map> getDiscounts(int user_id, String dateFrom, String dateTo) throws java.rmi.RemoteException;
    
 
}
