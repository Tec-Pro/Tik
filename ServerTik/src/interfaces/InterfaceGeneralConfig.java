/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package interfaces;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 *
 * @author eze
 */
public interface InterfaceGeneralConfig extends Remote{
    
    public void saveProperties(String delayT) throws RemoteException;
    
    public void loadProperties() throws RemoteException;
            
    public void createDefaultProperties() throws RemoteException;
}
