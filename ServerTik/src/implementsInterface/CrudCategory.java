/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package implementsInterface;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import models.Subcategory;
import models.Category;
import org.javalite.activejdbc.Base;
import utils.Utils;

/**
 *
 * @author nico
 */
public class CrudCategory extends UnicastRemoteObject implements interfaces.InterfaceCategory{
 
    public CrudCategory() throws RemoteException{
        super();
        
    }
    public boolean create(String name) throws java.rmi.RemoteException{
        Utils.abrirBase();
        Base.openTransaction();
        Category category= new Category();
        category.setString("name",name);
        boolean res = category.saveIt();
        Base.commitTransaction();
        Utils.cerrarBase();        
        return res;
    }
     
     public boolean modify(int id,String name) throws java.rmi.RemoteException{
        Utils.abrirBase();
        Category category= Category.findById(id);
        boolean res= false;
        if(category!=null){
            category.setString("name",name);
            Base.openTransaction();
            res = category.saveIt();
            Base.commitTransaction();
            Utils.cerrarBase();
        }
        return res;
     }
     
     public boolean delete(int id) throws java.rmi.RemoteException{
        Utils.abrirBase();
        Category category= Category.findById(id);
        boolean res= false;
        if(category != null){
            Base.openTransaction();
            res= category.delete();
            Base.commitTransaction();
        }
        return res;
     }
     
     public  List<Map> getCategories() throws java.rmi.RemoteException{
         Utils.abrirBase();
         List<Map> ret= Category.findAll().toMaps();
         Utils.cerrarBase();
         return ret;
     }

     public Map<String,Object> getCategory(int id) throws java.rmi.RemoteException{
         Utils.abrirBase();
         Map<String,Object> ret= Category.findById(id).toMap();
         Utils.cerrarBase();
         return ret;
     }

             public boolean addSubcategory(int id,String name)throws java.rmi.RemoteException{
             Utils.abrirBase();
             Base.openTransaction();
             Category category= Category.findById(id);
             boolean ret= false;
             if(category!=null){
                Subcategory subcategory = Subcategory.create("name",name);
                category.add(subcategory);
                ret= true;
             }
             Base.commitTransaction();
             Utils.cerrarBase();
             return ret;
         }

         public boolean deleteSubcategory(int id) throws java.rmi.RemoteException{
             Utils.abrirBase();
             Base.openTransaction();
             boolean ret= false;
             Subcategory subcategory = Subcategory.findById(id);
             if(subcategory!=null){
                 ret=subcategory.delete();
             }
             Base.commitTransaction();
             Utils.cerrarBase();
             return ret;
         }
         
         public boolean modifySubcategory(int id, String name)throws java.rmi.RemoteException{
             Utils.abrirBase();
             Base.openTransaction();
             boolean ret= false;
             Subcategory subcategory = Subcategory.findById(id);
             if(subcategory!=null){
                 subcategory.setString("name", name);
                 ret=subcategory.saveIt();
             }
             Base.commitTransaction();
             Utils.cerrarBase();
             return ret;
         }

         
         public List<Map> getSubcategoriesCategory(int id)throws java.rmi.RemoteException{
             Utils.abrirBase();
             Category category = Category.findById(id);
             List<Map> ret = new LinkedList<>();
             if(category!=null){
                 ret=category.getAll(Subcategory.class).toMaps();
             }
             Utils.cerrarBase();
             return ret;
         }
         
         public Map<String,Object> getSubcategory(int id)throws java.rmi.RemoteException{
             Utils.abrirBase();
             Subcategory subcategory = Subcategory.findById(id);
             Map<String,Object> ret = null ;
             if(subcategory!=null){
                 ret=subcategory.toMap();
             }
             Utils.cerrarBase();
             return ret;
         }
}
