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
    
    
    
    public Map<String,Object> create(String name) throws java.rmi.RemoteException;
     
     public Map<String,Object> modify(int id,String name) throws java.rmi.RemoteException;
     
     public boolean delete(int id) throws java.rmi.RemoteException;
     
      public boolean categoryExists(String name);

     public Map<String,Object> getCategory(int id) throws java.rmi.RemoteException;
     
     public Map<String, Object> getCategoryByName(String name) throws java.rmi.RemoteException;
     
     public  List<Map> getCategories() throws java.rmi.RemoteException;    
     
     public Map<String,Object> addSubcategory(int id,String name)throws java.rmi.RemoteException;
     
     public boolean deleteSubcategory(int id) throws java.rmi.RemoteException;
     
     public Map<String,Object> modifySubcategory(int id, String number)throws java.rmi.RemoteException;
     
     public List<Map> getSubcategoriesCategory(int id)throws java.rmi.RemoteException;
     
     public List<Map> getSubcategoriesCategory()throws java.rmi.RemoteException;
             
     public Map<String,Object> getSubcategory(int id)throws java.rmi.RemoteException;
     
     public Map<String,Object> getSubcategory(String name)throws java.rmi.RemoteException;
}
