/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package interfaces;

import java.rmi.Remote;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 *
 * @author nico
 */
public interface InterfaceUser extends Remote {

    public Map<String,Object> create(String name, String pass,Date entryDate, Date exitDate, String turn) throws java.rmi.RemoteException;
     
     public Map<String,Object> modify(int id,String name, String pass,Date entryDate, Date exitDate, String turn) throws java.rmi.RemoteException;
     
     public boolean delete(int id) throws java.rmi.RemoteException;
     
     public Map<String,Object> getUser(int id) throws java.rmi.RemoteException;
     
     public  List<Map> getUsers() throws java.rmi.RemoteException;    
}