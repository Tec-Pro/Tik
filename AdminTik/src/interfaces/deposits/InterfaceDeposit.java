/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package interfaces.deposits;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;
import java.util.Map;

/**
 *
 * @author joako
 */
public interface InterfaceDeposit extends Remote {
    
    
    //Métodos de entregas de mozos:

    /**
     * Crea una nueva entrega.
     * @param waiter_id id del mozo.
     * @param amount cantidad de la entrega.
     * @return map de la entrega creada.
     * @throws RemoteException problema de conexión.  
     */
    public Map<String,Object> createWaiterDeposit(int waiter_id, Float amount) throws java.rmi.RemoteException;
    
    /**
     * Modifica una entrega específica.
     * @param deposit_id id de la entrega.
     * @param amount nueva cantidad.
     * @return map de la entrega modificada.
     * @throws RemoteException problema de conexión
     */
    public Map<String,Object> updateWaiterDeposit(int deposit_id, Float amount) throws java.rmi.RemoteException;
    
    /**
     * Borra una entrega específica.
     * @param deposit_id id de la entrega.
     * @return true si se eliminó el registro.
     * @throws RemoteException problema de conexión.
     */
    public boolean deleteWaiterDeposit(int deposit_id) throws java.rmi.RemoteException;
        
    /**
     * Encuentra todas las entregas de un mozo.
     * @param waiter_id id del mozo.
     * @return lista de Maps con las entregas de un mozo.
     * @throws RemoteException problema de conexión.
     */
    public List<Map> getDepositsOfWaiter(int waiter_id) throws java.rmi.RemoteException;
    
    /**
     * Encuentra todas las entregas de un mozo en una fecha.
     * @param waiter_id id del mozo.
     * @param date fecha a buscar.
     * @return lista de entregas hechas en el día hechas por el mozo.
     * @throws RemoteException problema de conexión.
     */
    public List<Map> getDepositsOfWaiterOnDate(int waiter_id, String date) throws java.rmi.RemoteException;
    
    /**
     * Devuelve todas las entregas de un mozo en un turno.
     * @param waiter_id id del mozo
     * @param turn turno
     * @return lista de entregas.
     * @throws RemoteException problema de conexión.
     */
    public List<Map> getDepositsOfWaiterOnTurn(int waiter_id, String turn) throws java.rmi.RemoteException;
    
    /**
     * Encuentra todas las entregas del mozo en una fecha, en un turno.
     * @param waiter_id id del mozo.
     * @param date fecha específica.
     * @param turn turno a buscar.
     * @return lista de entregas de un mozo en un turno.
     * @throws RemoteException problema de conexión. 
     */
    public List<Map> getDepositsOfWaiter(int waiter_id, String date, String turn) throws java.rmi.RemoteException;
    
    /**
     * Devuelve todas las entregas de mozos.
     * @return lista con todos las entregas de mozos.
     * @throws RemoteException problema de conexión.
     */
    public List<Map> getWaitersDeposits() throws java.rmi.RemoteException;
    
    /**
     * Devuelve todas las entregas de mozos en una fecha.
     * @param date fecha en la que buscar.
     * @return lista de entregas.
     * @throws RemoteException problema de conexión.
     */
    public List<Map> getWaitersDepositsForDate(String date) throws java.rmi.RemoteException;
    
    /**
     * Devuelve todas las entregas de un mozo en un turno.
     * @param turn turno en el cual buscar.
     * @return lista de entregas.
     * @throws RemoteException problema de conexión.
     */
    public List<Map> getWaitersDepositsForTurn(String turn) throws java.rmi.RemoteException;
    
    /**
     * Devuelve todos las entregas de un mozo en un día y un turno.
     * @param date fecha 
     * @param turn turno
     * @return lista de entregas
     * @throws RemoteException problema de conexión.
     */
    public List<Map> getWaitersDeposits(String date, String turn) throws java.rmi.RemoteException;
    
    /**
     * Devuelve el total de todas las entregas de los mozos
     * @return Total de entregas de los mozos
     * @throws RemoteException problema de conexión. 
     */
    public Double getWaitersDepositsTotal() throws java.rmi.RemoteException;
    
    /**
     * Devuelve el total de las entregas realizadas en una fecha y un turno.
     * @param date fecha
     * @param turn turno
     * @return total de entregas.
     * @throws RemoteException problema de conexión.
     */
    public Double getWaitersDepositsTotal(String date, String turn) throws java.rmi.RemoteException;
    
    /**
     * Devuelve el total de las entregas realizadas en una fecha.
     * @param date fecha
     * @return total de entregas.
     * @throws RemoteException problema de conexión.
     */
    public Double getWaitersDepositsTotalOnDate(String date) throws java.rmi.RemoteException;

    /**
     * Devuelve el total de las entregas realizadas en un turno.
     * @param turn turno
     * @return total de entregas.
     * @throws RemoteException problema de conexión.
     */
    public Double getWaitersDepositsTotalOnTurn(String turn) throws java.rmi.RemoteException;
    
    /**
     * Devuelve el total de las entregas realizadas por un mozo, en un turno, en un día.
     * @param id id del mozo
     * @param date fecha
     * @param turn turno
     * @return total de entregas.
     * @throws RemoteException problema de conexión.
     */
    public Double getWaiterDepositsTotal(int id, String date, String turn) throws java.rmi.RemoteException;
    
    /**
     * Devuelve el total de las entregas de un mozo en una fecha.
     * @param id id del mozo
     * @param date fecha
     * @return total de entregas.
     * @throws RemoteException problema de conexión.
     */
    public Double getWaiterDepositsTotalOnDate(int id, String date) throws java.rmi.RemoteException;
    
    /**
     * Devuelve el total de entregas de un mozo en un turno.
     * @param id id del mozo
     * @param turn turno
     * @return total de entregas.
     * @throws RemoteException problema de conexión.
     */
    public Double getWaiterDepositsTotalOnTurn(int id, String turn) throws java.rmi.RemoteException;
    
    /**
     * Devuelve el total de entregas de un mozo.
     * @param id id del mozo
     * @return total de entregas
     * @throws RemoteException problema de conexión.
     */
    
    public Double getWaiterDepositsTotal(int id) throws java.rmi.RemoteException;
  
    /**
     * Borra todos las entregas de la base de datos.
     * @return true si se borró más de un registro.
     * @throws RemoteException problema de conexión.
     */
    public boolean eraseWaiterDeposits() throws java.rmi.RemoteException;
    
    //Métodos de depósitos de admin:
    
    /**
     * Crea una entrega de admin.
     * @param admin_id id del admin
     * @param amount monto
     * @return Map del depósito.
     * @throws RemoteException problema de conexión.
     */
    public Map<String,Object> createAdminDeposit(int admin_id, Float amount) throws java.rmi.RemoteException;
    
    /**
     * Modifica un depósito de admin.
     * @param deposit_id id del depósito
     * @param amount monto
     * @return Map modificado del depósito.
     * @throws RemoteException problema de conexión.
     */
    public Map<String,Object> updateAdminDeposit(int deposit_id, Float amount) throws java.rmi.RemoteException;
    
    /**
     * Borra un depósito de admin.
     * @param deposit_id id del depósito
     * @return true si elimina el depósito.
     * @throws RemoteException problema de conexión.
     */
    public boolean deleteAdminDeposit(int deposit_id) throws java.rmi.RemoteException;
        
    /**
     * Devuelve los depositos de un admin.
     * @param admin_id id del admin
     * @return lista de depósitos.
     * @throws RemoteException problema de conexión.
     */
    public List<Map> getDepositsOfAdmin(int admin_id) throws java.rmi.RemoteException;
    
    /**
     * Devuelve los depósitos de un admin en un día.
     * @param admin_id id del admin
     * @param date fecha
     * @return lista de depósitos.
     * @throws RemoteException problema de conexión.
     */
    public List<Map> getDepositsOfAdminOnDate(int admin_id, String date) throws java.rmi.RemoteException;
    
    /**
     * Devuelve los depósitos de un admin en un turno.
     * @param admin_id id del admin
     * @param turn turno
     * @return lista de depósitos.
     * @throws RemoteException problema de conexión.
     */
    public List<Map> getDepositsOfAdminOnTurn(int admin_id, String turn) throws java.rmi.RemoteException;
    
    /**
     * Devuelve los depósitos de un admin, en una fecha, en un turno.
     * @param admin_id id del admin
     * @param date fecha
     * @param turn turno
     * @return lista de depósitos.
     * @throws RemoteException problema de conexión.
     */
    public List<Map> getDepositsOfAdmin(int admin_id, String date, String turn) throws java.rmi.RemoteException;
    
    /**
     * Devuelve todos los depósitos de los admins.
     * @return lista de depósitos.
     * @throws RemoteException problema de conexión.
     */
    public List<Map> getAdminsDeposits() throws java.rmi.RemoteException;
    
    /**
     * Devuelve los depósitos de admins en una fecha.
     * @param date fecha
     * @return lista de depósitos.
     * @throws RemoteException problema de conexión.
     */
    public List<Map> getAdminsDepositsForDate(String date) throws java.rmi.RemoteException;
     
    /**
     * Devuelve los depósitos de admins en un turno.
     * @param turn turno
     * @return lista de depósitos.
     * @throws RemoteException problema de conexión.
     */
    public List<Map> getAdminsDepositsForTurn(String turn) throws java.rmi.RemoteException;
    
    /**
     * Devuelve los depósitos de admins en una fecha, en un turno.
     * @param date fecha
     * @param turn turno
     * @return lista de depósitos.
     * @throws RemoteException problema de conexión.
     */
    public List<Map> getAdminsDeposits(String date, String turn) throws java.rmi.RemoteException;
    
    /**
     * Devuelve el total de los depósitos de admins.
     * @return total de los depósitos.
     * @throws RemoteException problema de conexión.
     */
    public Double getAdminsDepositsTotal() throws java.rmi.RemoteException;
    
    /**
     * Devuelve el total de los depósitos de admins en una fecha y un turno.
     * @param date fecha
     * @param turn turno
     * @return total de los depósitos.
     * @throws RemoteException problema de conexión.
     */
    public Double getAdminsDepositsTotal(String date, String turn) throws java.rmi.RemoteException;
    
    /**
     * Devuelve el total de los depósitos de admins en una fecha.
     * @param date fecha
     * @return total de los depósitos.
     * @throws RemoteException problema de conexión.
     */
    public Double getAdminsDepositsTotalOnDate(String date) throws java.rmi.RemoteException;

    /**
     * Devuelve el total de los depósitos de admins en un turno.
     * @param turn turno
     * @return total de los depósitos.
     * @throws RemoteException problema de conexión.
     */
    public Double getAdminsDepositsTotalOnTurn(String turn) throws java.rmi.RemoteException;
    
    /**
     * Devuelve el total de los depósitos de un admin en una fecha, en un turno.
     * @param id id del admin
     * @param date fecha
     * @param turn turno
     * @return total de los depósitos.
     * @throws RemoteException problema de conexión.
     */
    public Double getAdminDepositsTotal(int id, String date, String turn) throws java.rmi.RemoteException;
    
    /**
     * Devuelve el total de los depósitos de un admin en una fecha.
     * @param id id del admin
     * @param date fecha
     * @return total de los depósitos.
     * @throws RemoteException problema de conexión.
     */
    public Double getAdminDepositsTotalOnDate(int id, String date) throws java.rmi.RemoteException;
    
    /**
     * Devuelve el total de los depósitos de un admin en un turno.
     * @param id id del admin.
     * @param turn turno
     * @return total de los depósitos.
     * @throws RemoteException problema de conexión.
     */
    public Double getAdminDepositsTotalOnTurn(int id, String turn) throws java.rmi.RemoteException;
    
    /**
     * Devuelve el total de los depósitos de un admin.
     * @param id id del admin
     * @return total de los depósitos.
     * @throws RemoteException problema de conexión.
     */
    public Double getAdminDepositsTotal(int id) throws java.rmi.RemoteException;
    
    /**
     * Borra todos los depósitos de los admins.
     * @return true si se borra al menos un registro.
     * @throws RemoteException problema de conexión.
     */
    public boolean eraseAdminDeposits() throws java.rmi.RemoteException;
    
    
    //Buscar una entrega específica:
    
    /**
     * Devuelve un depósito específico.
     * @param deposit_id id del depósito
     * @return Map del depósito
     * @throws RemoteException problema de conexión.
     */
    public Map getDeposit(int deposit_id) throws java.rmi.RemoteException;

}
