/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package implementsInterface;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import models.Subcategory;
import models.Category;
import models.Fproduct;
import org.javalite.activejdbc.Base;
import org.javalite.activejdbc.LazyList;
import utils.Utils;

/**
 *
 * @author nico
 */
public class CRUDCategory extends UnicastRemoteObject implements interfaces.InterfaceCategory {

    public CRUDCategory() throws RemoteException {
        super();

    }

    public boolean categoryExists(String name) throws java.rmi.RemoteException{
        Utils.abrirBase();
        boolean res = false;
        Category c = Category.first("name = ?", name);
        if(c != null){
            res = true;
        }
         
        return res;
    }
    
    public boolean subCategoryExists(String name) throws java.rmi.RemoteException{
        Utils.abrirBase();
        boolean res = false;
        Subcategory c = Subcategory.first("name = ?", name);
        if(c != null){
            res = true;
        }
         
        return res;
    }
    
    public Map<String, Object> create(String name) throws java.rmi.RemoteException {
        Utils.abrirBase();
        Base.openTransaction();
        Map<String, Object> res = null;
        if(!categoryExists(name))
            res = Category.createIt("name", name).toMap();
        Base.commitTransaction();
         
        return res;
    }

    public Map<String, Object> modify(int id, String name) throws java.rmi.RemoteException {
        Utils.abrirBase();
        Category category = Category.findById(id);
        if (category != null) {
            category.setString("name", name);
            Base.openTransaction();
            category.saveIt();
            Base.commitTransaction();
             
        }
        return category.toMap();
    }

    public boolean delete(int id) throws java.rmi.RemoteException {
        Utils.abrirBase();
        Category category = Category.findById(id);
        Subcategory subcategoryDefault = Subcategory.findById(1);
        boolean res = true;
        if (category != null) {
            Base.openTransaction();
           for(Subcategory s : category.getAll(Subcategory.class)){
               LazyList<Fproduct> fproducts = s.getAll(Fproduct.class);
               for(Fproduct fp : fproducts){
                   subcategoryDefault.add(fp);
               }
               res = res && s.delete();
           }
           res = res && category.delete();
            Base.commitTransaction();
        }
        return res;
    }

    public List<Map> getCategories() throws java.rmi.RemoteException {
        Utils.abrirBase();
        List<Map> ret = Category.findAll().toMaps();
         
        return ret;
    }

    public Map<String, Object> getCategory(int id) throws java.rmi.RemoteException {
        Utils.abrirBase();
        Map<String, Object> ret = Category.findById(id).toMap();
         
        return ret;
    }
    
    public Map<String, Object> getCategoryByName(String name) throws java.rmi.RemoteException {
        Utils.abrirBase();
        Map<String, Object> ret = Category.first("name = ?",name).toMap();
         
        return ret;
    }

    public Map<String, Object> addSubcategory(int id, String name) throws java.rmi.RemoteException {
        Utils.abrirBase();
        Base.openTransaction();
        Category category = Category.findById(id);
        Map<String, Object> ret = null;
        if (category != null && !subCategoryExists(name)) {
            Subcategory subcategory = Subcategory.createIt("name", name);
            category.add(subcategory);
            ret = subcategory.toMap();
        }
        Base.commitTransaction();
         
        return ret;
    }

    public boolean deleteSubcategory(int id) throws java.rmi.RemoteException {
        Utils.abrirBase();
        Base.openTransaction();
        boolean ret = false;
        Subcategory subcategory = Subcategory.findById(id);
        Subcategory subcategoryDefault = Subcategory.findById(1);
        if (subcategory != null) {
            LazyList<Fproduct> fproducts = subcategory.getAll(Fproduct.class);
            for(Fproduct fp : fproducts){
                subcategoryDefault.add(fp);
            }
            ret = subcategory.delete();
        }
        Base.commitTransaction();
         
        return ret;
    }

    public Map<String, Object> modifySubcategory(int id, String name) throws java.rmi.RemoteException {
        Utils.abrirBase();
        Base.openTransaction();
        Map<String, Object> ret = null;
        Subcategory subcategory = Subcategory.findById(id);
        if (subcategory != null) {
            subcategory.setString("name", name);
            subcategory.saveIt();
            ret = subcategory.toMap();
        }
        Base.commitTransaction();
         
        return ret;
    }

    public List<Map> getSubcategoriesCategory(int id) throws java.rmi.RemoteException {
        Utils.abrirBase();
        Category category = Category.findById(id);
        List<Map> ret = new LinkedList<>();
        if (category != null) {
            ret = category.getAll(Subcategory.class).toMaps();
        }
         
        return ret;
    }

    public List<Map> getSubcategoriesCategory() throws java.rmi.RemoteException {
        Utils.abrirBase();
        List<Map> ret = Subcategory.findAll().toMaps();
         
        return ret;
    }

    public Map<String, Object> getSubcategory(int id) throws java.rmi.RemoteException {
        Utils.abrirBase();
        Subcategory subcategory = Subcategory.findById(id);
        Map<String, Object> ret = null;
        if (subcategory != null) {
            ret = subcategory.toMap();
        }
         
        return ret;
    }

    public Map<String, Object> getSubcategory(String name) throws java.rmi.RemoteException {
        Utils.abrirBase();
        Map<String, Object> ret = Subcategory.findFirst("name = ?", name).toMap();
         
        return ret;
    }

    @Override
    public List<Map> getFProductsSubcategory(int id) throws RemoteException {
        Utils.abrirBase();
        Subcategory subcategory = Subcategory.findById(id);
        List<Map> ret =  subcategory.getAll(Fproduct.class).toMaps();
        return ret;
    }

	public Map<String, Object> getCategoryOfSubcategory(int id) throws java.rmi.RemoteException {
        Utils.abrirBase();
        Subcategory subcategory = Subcategory.findById(id);
        Map<String, Object> ret = null;
        if (subcategory != null) {
            ret = subcategory.parent(Category.class).toMap();
        }
        return ret;
    }
}
