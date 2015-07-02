/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package interfaces.providers.payments;

import java.rmi.Remote;
import java.util.List;
import java.util.Map;
import java.util.SortedMap;

/**
 *
 * @author nico
 */
public interface InterfacePayments extends Remote {

  /**
   * crea un nuevo pago a un proveedor
   * @param provider_id
   * @param detail
   * @param amount
   * @param purchase_id debe ser -1 si no se quiere asociar una compra
   * @param date
   * @return si se creo o no
   * @throws java.rmi.RemoteException 
   */
    public boolean createPayment(int provider_id, String detail, float amount, int purchase_id, String date,String nameAdmin) throws java.rmi.RemoteException;
    
    /**
     * obtiene un pago mediante el id
     * @param id
     * @return
     * @throws java.rmi.RemoteException 
     */
    public Map<String,Object> getPayment(int id) throws java.rmi.RemoteException;
    
    /**
     * obtiene todos los pagos que existen entre dos fechas dadas
     * @param datefrom
     * @param dateTo
     * @return
     * @throws java.rmi.RemoteException 
     */
    public  List<Map> getPayments(String datefrom, String dateTo) throws java.rmi.RemoteException;

    /**
     * obtiene todos los pagos de un proveedor dado
     * @param provider_id
     * @return
     * @throws java.rmi.RemoteException 
     */
    public List<Map> getPaymentsProvider(int provider_id) throws java.rmi.RemoteException;
}
