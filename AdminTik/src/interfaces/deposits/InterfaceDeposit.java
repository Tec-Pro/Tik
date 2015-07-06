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
     * @param waiter_id id del mozo. id del mozo.
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
     *
     * @param waiter_id
     * @param turn
     * @return
     * @throws RemoteException
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
     * 
     * @param turn
     * @return
     * @throws RemoteException problema de conexión.
     */
    public List<Map> getWaitersDepositsForTurn(String turn) throws java.rmi.RemoteException;
    
    /**
     *
     * @param date
     * @param turn
     * @return
     * @throws RemoteException problema de conexión.
     */
    public List<Map> getWaitersDeposits(String date, String turn) throws java.rmi.RemoteException;
    
    /**
     *
     * @return
     * @throws RemoteException
     */
    public Double getWaitersDepositsTotal() throws java.rmi.RemoteException;
    
    public Double getWaitersDepositsTotal(String date, String turn) throws java.rmi.RemoteException;
    
    public Double getWaitersDepositsTotalOnDate(String date) throws java.rmi.RemoteException;

    public Double getWaitersDepositsTotalOnTurn(String turn) throws java.rmi.RemoteException;
    
    public Double getWaiterDepositsTotal(int id, String date, String turn) throws java.rmi.RemoteException;
    
    public Double getWaiterDepositsTotalOnDate(int id, String date) throws java.rmi.RemoteException;
    
    public Double getWaiterDepositsTotalOnTurn(int id, String turn) throws java.rmi.RemoteException;
    
    /**
     *
     * @param id
     * @return
     * @throws RemoteException
     */
    
    public Double getWaiterDepositsTotal(int id) throws java.rmi.RemoteException;
  
    /**
     *
     * @return
     * @throws RemoteException
     */
    public boolean eraseWaiterDeposits() throws java.rmi.RemoteException;
    
    //Métodos de depósitos de admin:
    
    /**
     *
     * @param admin_id
     * @param amount
     * @return
     * @throws RemoteException
     */
    public Map<String,Object> createAdminDeposit(int admin_id, Float amount) throws java.rmi.RemoteException;
    
    /**
     *
     * @param deposit_id
     * @param amount
     * @return
     * @throws RemoteException
     */
    public Map<String,Object> updateAdminDeposit(int deposit_id, Float amount) throws java.rmi.RemoteException;
    
    /**
     *
     * @param deposit_id
     * @return
     * @throws RemoteException
     */
    public boolean deleteAdminDeposit(int deposit_id) throws java.rmi.RemoteException;
        
    /**
     *
     * @param admin_id
     * @return
     * @throws RemoteException
     */
    public List<Map> getDepositsOfAdmin(int admin_id) throws java.rmi.RemoteException;
    
    /**
     *
     * @param admin_id
     * @param date
     * @return
     * @throws RemoteException
     */
    public List<Map> getDepositsOfAdminOnDate(int admin_id, String date) throws java.rmi.RemoteException;
    
    /**
     *
     * @param admin_id
     * @param turn
     * @return
     * @throws RemoteException
     */
    public List<Map> getDepositsOfAdminOnTurn(int admin_id, String turn) throws java.rmi.RemoteException;
    
    /**
     *
     * @param admin_id
     * @param date
     * @param turn
     * @return
     * @throws RemoteException
     */
    public List<Map> getDepositsOfAdmin(int admin_id, String date, String turn) throws java.rmi.RemoteException;
    
    /**
     *
     * @return
     * @throws RemoteException
     */
    public List<Map> getAdminsDeposits() throws java.rmi.RemoteException;
    
    /**
     *
     * @param date
     * @return
     * @throws RemoteException
     */
    public List<Map> getAdminsDepositsForDate(String date) throws java.rmi.RemoteException;
     
    /**
     *
     * @param turn
     * @return
     * @throws RemoteException
     */
    public List<Map> getAdminsDepositsForTurn(String turn) throws java.rmi.RemoteException;
    
    /**
     *
     * @param date
     * @param turn
     * @return
     * @throws RemoteException
     */
    public List<Map> getAdminsDeposits(String date, String turn) throws java.rmi.RemoteException;
    
    /**
     *
     * @return
     * @throws RemoteException
     */
    public Double getAdminsDepositsTotal() throws java.rmi.RemoteException;
    
    public Double getAdminsDepositsTotal(String date, String turn) throws java.rmi.RemoteException;
    
    public Double getAdminsDepositsTotalOnDate(String date) throws java.rmi.RemoteException;

    public Double getAdminsDepositsTotalOnTurn(String turn) throws java.rmi.RemoteException;
    
    public Double getAdminDepositsTotal(int id, String date, String turn) throws java.rmi.RemoteException;
    
    public Double getAdminDepositsTotalOnDate(int id, String date) throws java.rmi.RemoteException;
    
    public Double getAdminDepositsTotalOnTurn(int id, String turn) throws java.rmi.RemoteException;
    
    /**
     *
     * @param id
     * @return
     * @throws RemoteException
     */
    public Double getAdminDepositsTotal(int id) throws java.rmi.RemoteException;
    
    /**
     *
     * @return
     * @throws RemoteException
     */
    public boolean eraseAdminDeposits() throws java.rmi.RemoteException;
    
    
    //Buscar una entrega específica:
    
    /**
     *
     * @param deposit_id
     * @return
     * @throws RemoteException
     */
    public Map getDeposit(int deposit_id) throws java.rmi.RemoteException;

}
