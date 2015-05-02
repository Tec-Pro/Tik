/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package interfaces;

import java.rmi.Remote;
import java.util.Map;

/**
 *
 * @author jacinto
 */
public interface  InterfacePresence extends Remote {
    
     /**
     * registra la entrada de un empleado
     * @param userId
     * @return
     * @throws java.rmi.RemoteException
     */
    public Map<String,Object> create(int userId) throws java.rmi.RemoteException;
     
     /**
     * registra la salida del empleado
     * @param userId
     * @return
     * @throws java.rmi.RemoteException
     */
    public Map<String,Object> logout(int userId) throws java.rmi.RemoteException;
    
}
