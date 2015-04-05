/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package interfaces.providers;

import java.rmi.Remote;
import java.util.List;
import java.util.Map;

/**
 *
 * @author nico
 */
public interface InterfaceProviderCategory extends Remote {

    public Map<String,Object> create(String name) throws java.rmi.RemoteException;
     
     public Map<String,Object> modify(int id,String name) throws java.rmi.RemoteException;
     
     public boolean delete(int id) throws java.rmi.RemoteException;
     
     public Map<String,Object> getProviderCategory(int id) throws java.rmi.RemoteException;     
     
     public  List<Map> getProviderCategories() throws java.rmi.RemoteException;    
}
