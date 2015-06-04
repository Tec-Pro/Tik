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
 * @author nico
 */
public interface InterfaceServer extends Remote {

    public void registerClientAdmin(InterfaceClientAdmin clientm) throws RemoteException;

    public void registerClientWaiter(InterfaceClientWaiter clientm) throws RemoteException;

    public void registerClientKitchen(InterfaceClientKitchen client) throws RemoteException;

    public void registerClientBar(InterfaceClientBar client) throws RemoteException;
    
    public void notifyWaitersOrderDelayed(int order) throws RemoteException;
}
