/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package interfaces.withdrawals;

import java.rmi.Remote;
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
     * Devuelve una lista con todos los retiros de un admin-
     *     
* @param admin_id id del admin.
     * @return lista con todos los retiros.
     * @throws java.rmi.RemoteException
     */

    public List<Map> getWithdrawalsOfAdmin(int admin_id) throws java.rmi.RemoteException;

    public List<Map> getWithdrawalsOfAdminOnTurn(int admin_id, String turn) throws java.rmi.RemoteException;
    
    public List<Map> getWithdrawalsOfAdminOnDate(int admin_id, String date) throws java.rmi.RemoteException;

    public List<Map> getWithdrawalsOfAdmin(int admin_id, String turn, String date) throws java.rmi.RemoteException;
    
    public List<Map> getWithdrawalsOnTurn(String turn) throws java.rmi.RemoteException;
    
    public List<Map> getWithdrawalsOnDate(String date) throws java.rmi.RemoteException;
    
    public List<Map> getWithdrawals(String date, String turn) throws java.rmi.RemoteException;
    
    public Double getWithdrawalsTotal() throws java.rmi.RemoteException;

    public Double getWithdrawalsTotalOnDate(String date) throws java.rmi.RemoteException;
    
    public Double getWithdrawalsTotalOnTurn(String turn) throws java.rmi.RemoteException;
    
    public Double getWithdrawalsTotal(String date, String turn) throws java.rmi.RemoteException;
    
    public Double getAdminWithdrawalsTotal(int id) throws java.rmi.RemoteException;

    public Double getAdminWithdrawalsTotalOnDate(int admin_id, String date) throws java.rmi.RemoteException;
    
    public Double getAdminWithdrawalsTotalOnTurn(int admin_id, String turn) throws java.rmi.RemoteException;
    
    public Double getAdminWithdrawalsTotal(int admin_id, String date, String turn) throws java.rmi.RemoteException;
   
    public boolean eraseWithdrawals() throws java.rmi.RemoteException;
}
