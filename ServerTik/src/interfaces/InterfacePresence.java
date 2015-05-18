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
}
