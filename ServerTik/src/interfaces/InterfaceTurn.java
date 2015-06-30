/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package interfaces;

import java.rmi.Remote;

/**
 *
 * @author jacinto
 */
public interface InterfaceTurn extends Remote {
    
     /**
     *
     * @return true si hay un turno abierto
     */
    public boolean isTurnOpen()throws java.rmi.RemoteException;
    
    
    /**
     *
     * @return T = tarde, M = mañana, N = ninguno
     */
    public String getTurn()throws java.rmi.RemoteException;
    
    /**
     * cambia el turno
     * @param char T = tarde, M = mañana, N = ninguno
     */
    public boolean changeTurn(String t) throws java.rmi.RemoteException;
    
}
