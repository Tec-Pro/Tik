/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package interfaces;

import java.rmi.Remote;
import java.util.List;
import java.util.Map;

/**
 *
 * @author jacinto
 */
public interface InterfacePresence extends Remote {

    /**
     * registra la entrada de un empleado
     *
     * @param userId
     * @return
     * @throws java.rmi.RemoteException
     */
    public Map<String, Object> create(int userId) throws java.rmi.RemoteException;

    /**
     * registra la salida del empleado
     *
     * @param userId
     * @return
     * @throws java.rmi.RemoteException
     */
    public Map<String, Object> logout(int userId) throws java.rmi.RemoteException;

    /**
     * devuelve todos los mozos online
     *
     * @return
     * @throws java.rmi.RemoteException
     */
    public List<Map> getWaiters() throws java.rmi.RemoteException;

    /**
     * devuelve todos los cocineros online
     *
     * @return
     * @throws java.rmi.RemoteException
     */
    public List<Map> getCooks() throws java.rmi.RemoteException;
    
    /**
     * desloguea a todos los mozos
     *
     * @throws java.rmi.RemoteException
     */
    public void logoutAllWaiters() throws java.rmi.RemoteException;
    
    /**
     * devuelve las asistencias entre dos fechas de un user
     *
     * @param userId
     * @param dateFrom
     * @param dateTo
     * 
     * @return List<Map>
     * @throws java.rmi.RemoteException
     */
    public List<Map> getPresences(int id, String dateFrom, String dateTo) throws java.rmi.RemoteException;
    
     /**
     * devuelve las asistencias de un dia de un user
     *
     * @param userId
     * @param date
     * 
     * @return List<Map>
     * @throws java.rmi.RemoteException
     */
    public List<Map> getPresences(int id, String date) throws java.rmi.RemoteException;
    
    
    /**
     * desloguea a todos
     *
     * @throws java.rmi.RemoteException
     */
    public void logoutAll() throws java.rmi.RemoteException;
    
    /**
     * desloguea a todos los cocineros
     *
     * @throws java.rmi.RemoteException
     */
    public void logoutAllCooks() throws java.rmi.RemoteException;
    
     /**
     * devuelve true si hay alguien logeado
     *
     * @throws java.rmi.RemoteException
     */
    public boolean isSomeoneLogin() throws java.rmi.RemoteException;
    
      /**
     * devuelve todos los mozos que estubieron online pero ahora no lo estan en el turno actual
     *
     * @return
     * @throws java.rmi.RemoteException
     */
    public List<Map> getWaitersWereOnline() throws java.rmi.RemoteException;
    
}
