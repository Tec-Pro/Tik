/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package interfaces.cashbox;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.Map;

/**
 *
 * @author jacinto
 */
public interface InterfaceCashbox extends Remote {
    
    /**
     * crea una entrada en la tabla caja diaria (cashboxes)
     * 
     * @param turn turno    
     * @param balance saldo
     * @param collect recaudado
     * @param entryCash caja entrada
     * @param spend gastos
     * @param withdrawal retiros
     * @param deliveryCash entrega caja
     * @param deliveryWaiter entrega mozo
     * @return map del objeto creado
     * @throws RemoteException 
     */    
    public Map<String, Object> create(String turn, float balance,float collect,float entryCash,float spend,float withdrawal, float deliveryCash,float deliveryWaiter) throws RemoteException;
   
    /**
     * 
     * @return el saldo del ultimo turno
     * @throws RemoteException 
     */
    public float getPastBalance() throws RemoteException;
    
    /**
     *  devuelve la ultima caja diaria
     * @return 
     * @throws RemoteException 
     */    
    public Map<String, Object> getLast() throws RemoteException;
    
    /**
     *  devuelve la ultima caja diaria del turno
     * @param turno
     * @return 
     * @throws RemoteException 
     */    
    public Map<String, Object> getLast(String turn) throws RemoteException;
}
