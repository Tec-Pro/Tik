/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package interfaces.cashbox.expenses;

import java.rmi.Remote;
import java.util.List;
import java.util.Map;

/**
 *
 * @author nico
 */
public interface InterfaceExpenses extends Remote {

    /**
     * crea un nuevo gasto
     * @param type, solo puede ser (1)PAGO A PROVEEDOR, (2)COMPRA, (3)OTROS GASTOS
     * @param detail, detalle de lo que paga, compra etc
     * @param amount
     * @param purchase_id -1 si es un pago a proveedor u otros gastos
     * @param provider_id -1 si es otro gastos, para no asignar proveedor
     * @param turn solo puede ser MAÑANA, TARDE
     * @return
     * @throws java.rmi.RemoteException 
     */
    public boolean createExpense(int type, String detail, float amount, int purchase_id, int provider_id,String turn) throws java.rmi.RemoteException;
  
    
    /**
     * obtiene todos los gastos que existen de un turno dado
     * @param turn,solo puede ser MAÑANA o TARDE
     * @return
     * @throws java.rmi.RemoteException 
     */
    public  List<Map> getExpenses(String turn) throws java.rmi.RemoteException;

}
