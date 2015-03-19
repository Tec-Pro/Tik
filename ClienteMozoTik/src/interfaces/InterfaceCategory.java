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
public interface InterfaceCategory extends Remote {
    
    
    
    public boolean create(String name) throws java.rmi.RemoteException;
     
     public boolean modify(int id,String name) throws java.rmi.RemoteException;
     
     public boolean delete(int id) throws java.rmi.RemoteException;

     public Map<String,Object> getCategory(int id) throws java.rmi.RemoteException;
     
     
     public  List<Map> getCategories() throws java.rmi.RemoteException;    
     
     public boolean addSubcategory(int id,String name)throws java.rmi.RemoteException;
     
     public boolean deleteSubcategory(int id) throws java.rmi.RemoteException;
     
     public boolean modifySubcategory(int id, String number)throws java.rmi.RemoteException;
     
     public List<Map> getSubcategoriesCategory(int id)throws java.rmi.RemoteException;
             
     public Map<String,Object> getSubcategory(int id)throws java.rmi.RemoteException;
}
