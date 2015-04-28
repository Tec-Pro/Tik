/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package interfaces.providers;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;
import java.util.Map;

/**
 *
 * @author eze
 */
public interface InterfaceProvidersSearch extends Remote {

    /**
     *
     * @param search
     * @return
     * @throws RemoteException
     */
    public List<Map> searchProviders(String search) throws java.rmi.RemoteException;

    /**
     *
     * @param search
     * @param category_id
     * @return
     * @throws RemoteException
     */
    public List<Map> searchProviders(String search, int category_id) throws java.rmi.RemoteException;
    
}
