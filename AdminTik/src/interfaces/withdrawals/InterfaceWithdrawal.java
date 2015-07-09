/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package interfaces.withdrawals;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;
import java.util.Map;

/**
 *
 * @author joako
 */
public interface InterfaceWithdrawal extends Remote {

    /**
     * Crea un retiro de dinero.
     *
     * @param admin_id id del admin que hace el retiro
     * @param detail detalle del retiro
     * @param amount cantidad a retirar
     * @return Map con el retiro.
     * @throws java.rmi.RemoteException
     */
    public Map<String, Object> create(int admin_id, String detail, Float amount) throws java.rmi.RemoteException;

    /**
     * Modifica un retiro existente
     *
     * @param id del retiro a modificar
     * @param detail detalle modificado
     * @param amount cantidad modificada
     * @return Map del retiro modificado
     * @throws java.rmi.RemoteException
     */
    public Map<String, Object> modify(int id, String detail, Float amount) throws java.rmi.RemoteException;

    /**
     * Elimina un retiro existente
     *
     * @param id del retiro a eliminar
     * @return true si el retiro fue eliminado
     * @throws java.rmi.RemoteException
     */
    public boolean delete(int id) throws java.rmi.RemoteException;

    /**
     * Busca un retiro existente en la base de datos y lo devuelve
     *
     * @param id del retiro a buscar
     * @return Map con el retiro especificado
     * @throws java.rmi.RemoteException
     */
    public Map<String, Object> get(int id) throws java.rmi.RemoteException;

    /**
     * Devuelve todos los retiros en la base de datos
     *
     * @return lista con todos los retiros
     * @throws java.rmi.RemoteException
     */

    public List<Map> getWithdrawals() throws java.rmi.RemoteException;

    /**
     * Devuelve una lista con todos los retiros de un admin.
     *     
     * @param admin_id id del admin.
     * @return lista con todos los retiros.
     * @throws java.rmi.RemoteException
     */
    public List<Map> getWithdrawalsOfAdmin(int admin_id) throws java.rmi.RemoteException;

    /**
     * Devuelve una lista de los retiros de un admin en un turno.
     * @param admin_id id del admin
     * @param turn turno
     * @return lista de retiros
     * @throws RemoteException error de conexión.
     */
    public List<Map> getWithdrawalsOfAdminOnTurn(int admin_id, String turn) throws java.rmi.RemoteException;
    
    /**
     * Devuelve los retiros de un admin en una fecha.
     * @param admin_id id del admin
     * @param date fecha
     * @return lista de retiros
     * @throws RemoteException error de conexión.
     */
    public List<Map> getWithdrawalsOfAdminOnDate(int admin_id, String date) throws java.rmi.RemoteException;

    /**
     * Devuelve los retiros de un admin en una fecha y un turno.
     * @param admin_id id del admin
     * @param turn turno
     * @param date fecha
     * @return lista de retiros
     * @throws RemoteException error de conexión.
     */
    public List<Map> getWithdrawalsOfAdmin(int admin_id, String turn, String date) throws java.rmi.RemoteException;
    
    /**
     * Devuelve los retiros en un turno.
     * @param turn turno
     * @return lista de retiros
     * @throws RemoteException error de conexión.
     */
    public List<Map> getWithdrawalsOnTurn(String turn) throws java.rmi.RemoteException;
    
    /**
     * Devuelve los retiros hechos en una fecha.
     * @param date fecha 
     * @return lista de retiros
     * @throws RemoteException error de conexión.
     */
    public List<Map> getWithdrawalsOnDate(String date) throws java.rmi.RemoteException;
    
    /**
     * Devuelve los retiros hechos en una fecha y en un turno.
     * @param date fecha
     * @param turn turno
     * @return lista de retiros
     * @throws RemoteException error de conexión.
     */
    public List<Map> getWithdrawals(String date, String turn) throws java.rmi.RemoteException;
    
    /**
     * Devuelve el total de los retiros.
     * @return total de retiros
     * @throws RemoteException error de conexión.
     */
    public float getWithdrawalsTotal() throws java.rmi.RemoteException;

    /**
     * Devuelve el total de los retiros en una fecha.
     * @param date fecha
     * @return total de retiros
     * @throws RemoteException error de conexión.
     */
    public float getWithdrawalsTotalOnDate(String date) throws java.rmi.RemoteException;
    
    /**
     * Devuelve el total de los retiros en un turno.
     * @param turn turno
     * @return total de retiros
     * @throws RemoteException error de conexión.
     */
    public float getWithdrawalsTotalOnTurn(String turn) throws java.rmi.RemoteException;
    
    /**
     * Devuelve el total de los retiros en un turno y una fecha.
     * @param date fecha
     * @param turn turno
     * @return total de retiros
     * @throws RemoteException error de conexión.
     */
    public float getWithdrawalsTotal(String date, String turn) throws java.rmi.RemoteException;
    
    /**
     * Devuelve el total de los retiros de un admin.
     * @param id id del admin
     * @return total de retiros
     * @throws RemoteException error de conexión.
     */
    public float getAdminWithdrawalsTotal(int id) throws java.rmi.RemoteException;

    /**
     * Devuelve el total de los retiros de un admin en una fecha.
     * @param admin_id id del admin
     * @param date fecha
     * @return total de retiros
     * @throws RemoteException error de conexión.
     */
    public float getAdminWithdrawalsTotalOnDate(int admin_id, String date) throws java.rmi.RemoteException;
    
    /**
     * Devuelve el total de los retiros de un admin en un turno.
     * @param admin_id id del admin
     * @param turn turno
     * @return total de retiros
     * @throws RemoteException error de conexión.
     */
    public float getAdminWithdrawalsTotalOnTurn(int admin_id, String turn) throws java.rmi.RemoteException;
    
    /**
     * Devuelve el total de los retiros de un admin en una fecha y un turno.
     * @param admin_id id del admin
     * @param date fecha
     * @param turn turno
     * @return total de retiros
     * @throws RemoteException error de conexión.
     */
    public float getAdminWithdrawalsTotal(int admin_id, String date, String turn) throws java.rmi.RemoteException;
   
    /**
     * Borra todos los retiros de la base de datos.
     * @return true si se borra al menos un registro
     * @throws RemoteException error de conexión.
     */
    public boolean eraseWithdrawals() throws java.rmi.RemoteException;
}
