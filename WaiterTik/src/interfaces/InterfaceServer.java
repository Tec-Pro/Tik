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
    
    /**Se pasa como parametro el mismo y se le pasa el nombre
     *que corresponde al area que es el programa ej: mozo, barra, cocina,admin.
     * @param clientm
     * @param name
     * @throws RemoteException
     */
    public void registerClient(InterfaceClient clientm,String name) throws RemoteException;

}
