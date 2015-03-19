/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package interfaces;

import java.rmi.Remote;
import java.util.List;
import java.util.Map;

/**
 *
 * @author nico
 */
public interface InterfaceProvider extends Remote {

    public boolean create(String name, String cuit,String address,String description) throws java.rmi.RemoteException;
     
     public boolean modify(int id,String name, String cuit,String address,String description) throws java.rmi.RemoteException;
     
     public boolean delete(int id) throws java.rmi.RemoteException;
     
     public Map<String,Object> getProvider(int id) throws java.rmi.RemoteException;
     
     public  List<Map> getProviders() throws java.rmi.RemoteException;    
     
     public boolean addPhone(int id,String number)throws java.rmi.RemoteException;
     
     public boolean deletePhone(int id) throws java.rmi.RemoteException;
     
     public boolean modifyPhone(int id, String number)throws java.rmi.RemoteException;
     
     public List<Map> getPhonesProvider(int id)throws java.rmi.RemoteException;
             
     public Map<String,Object> getPhone(int id)throws java.rmi.RemoteException;
}
