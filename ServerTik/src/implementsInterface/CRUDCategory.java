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
import models.Fproduct;
import models.Pproduct;
import models.Pproductcategory;
import models.Pproductsubcategory;
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

    @Override
    public boolean categoryExists(String name) throws java.rmi.RemoteException {
        Utils.abrirBase();
        boolean res = false;
        Category c = Category.first("name = ?", name);
        if (c != null) {
            res = true;
        }

        return res;
    }

    @Override
    public boolean subCategoryExists(String name) throws java.rmi.RemoteException {
        Utils.abrirBase();
        boolean res = false;
        Subcategory c = Subcategory.first("name = ?", name);
        if (c != null) {
            res = true;
        }

        return res;
    }

    @Override
    public Map<String, Object> create(String name) throws java.rmi.RemoteException {
        Utils.abrirBase();
        Base.openTransaction();
        Map<String, Object> res = null;
        if (!categoryExists(name)) {
            res = Category.createIt("name", name).toMap();
        }
        Base.commitTransaction();

        return res;
    }

    @Override
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

    @Override
    public boolean delete(int id) throws java.rmi.RemoteException {
        Utils.abrirBase();
        Category category = Category.findById(id);
        Subcategory subcategoryDefault = Subcategory.findById(1);
        boolean res = true;
        if (category != null) {
            Base.openTransaction();
            for (Subcategory s : category.getAll(Subcategory.class)) {
                LazyList<Fproduct> fproducts = s.getAll(Fproduct.class);
                for (Fproduct fp : fproducts) {
                    subcategoryDefault.add(fp);
                }
                res = res && s.delete();
            }
            res = res && category.delete();
            Base.commitTransaction();
        }
        return res;
    }

    @Override
    public List<Map> getCategories() throws java.rmi.RemoteException {
        Utils.abrirBase();
        List<Map> ret = Category.findAll().toMaps();

        return ret;
    }

    @Override
    public Map<String, Object> getCategory(int id) throws java.rmi.RemoteException {
        Utils.abrirBase();
        Map<String, Object> ret = Category.findById(id).toMap();

        return ret;
    }

    @Override
    public Map<String, Object> getCategoryByName(String name) throws java.rmi.RemoteException {
        Utils.abrirBase();
        Map<String, Object> ret = Category.first("name = ?", name).toMap();

        return ret;
    }

    @Override
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

    @Override
    public boolean deleteSubcategory(int id) throws java.rmi.RemoteException {
        Utils.abrirBase();
        Base.openTransaction();
        boolean ret = false;
        Subcategory subcategory = Subcategory.findById(id);
        Subcategory subcategoryDefault = Subcategory.findById(1);
        if (subcategory != null) {
            LazyList<Fproduct> fproducts = subcategory.getAll(Fproduct.class);
            for (Fproduct fp : fproducts) {
                subcategoryDefault.add(fp);
            }
            ret = subcategory.delete();
        }
        Base.commitTransaction();

        return ret;
    }

    @Override
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

    @Override
    public List<Map> getSubcategoriesCategory(int id) throws java.rmi.RemoteException {
        Utils.abrirBase();
        Category category = Category.findById(id);
        List<Map> ret = new LinkedList<>();
        if (category != null) {
            ret = category.getAll(Subcategory.class).toMaps();
        }

        return ret;
    }

    @Override
    public List<Map> getSubcategoriesCategory() throws java.rmi.RemoteException {
        Utils.abrirBase();
        List<Map> ret = Subcategory.findAll().toMaps();

        return ret;
    }

    @Override
    public Map<String, Object> getSubcategory(int id) throws java.rmi.RemoteException {
        Utils.abrirBase();
        Subcategory subcategory = Subcategory.findById(id);
        Map<String, Object> ret = null;
        if (subcategory != null) {
            ret = subcategory.toMap();
        }

        return ret;
    }

    @Override
    public Map<String, Object> getSubcategory(String name) throws java.rmi.RemoteException {
        Utils.abrirBase();
        Map<String, Object> ret = Subcategory.findFirst("name = ?", name).toMap();

        return ret;
    }

    @Override
    public List<Map> getFProductsSubcategory(int id) throws RemoteException {
        Utils.abrirBase();
        Subcategory subcategory = Subcategory.findById(id);
        List<Map> ret = subcategory.getAll(Fproduct.class).toMaps();
        return ret;
    }

    @Override
    public Map<String, Object> getCategoryOfSubcategory(int id) throws java.rmi.RemoteException {
        Utils.abrirBase();
        Subcategory subcategory = Subcategory.findById(id);
        Map<String, Object> ret = null;
        if (subcategory != null) {
            ret = subcategory.parent(Category.class).toMap();
        }
        return ret;
    }

    @Override
    public List<Map> searchCategories(String txt) throws java.rmi.RemoteException {
        Utils.abrirBase();
        return Category.where("name like ?", "%" + txt + "%").toMaps();
    }

    @Override
    public List<Map> searchSubcategories(String txt) throws java.rmi.RemoteException {
        Utils.abrirBase();
        return Subcategory.where("name like ?", "%" + txt + "%").toMaps();
    }
    
    
    /*
    *
    * Categorias y subcategorias de productos primarios
    *
    */
    
    @Override
    public boolean pProductCategoryExists(String name) throws java.rmi.RemoteException {
        Utils.abrirBase();
        boolean res = false;
        Pproductcategory c = Pproductcategory.first("name = ?", name);
        if (c != null) {
            res = true;
        }

        return res;
    }
     @Override
    public boolean pProductSubCategoryExists(String name) throws java.rmi.RemoteException {
        Utils.abrirBase();
        boolean res = false;
        Pproductsubcategory c = Pproductsubcategory.first("name = ?", name);
        if (c != null) {
            res = true;
        }

        return res;
    }

    @Override
    public Map<String, Object> createPproductCategory(String name) throws java.rmi.RemoteException {
        Utils.abrirBase();
        Base.openTransaction();
        Map<String, Object> res = null;
        if (!pProductCategoryExists(name)) {
            res = Pproductcategory.createIt("name", name).toMap();
        }
        Base.commitTransaction();

        return res;
    }

    @Override
    public Map<String, Object> modifyPproductCategory(int id, String name) throws java.rmi.RemoteException {
        Utils.abrirBase();
        Pproductcategory category = Pproductcategory.findById(id);
        if (category != null) {
            category.setString("name", name);
            Base.openTransaction();
            category.saveIt();
            Base.commitTransaction();

        }
        return category.toMap();
    }

    @Override
    public boolean deletePproductCategory(int id) throws java.rmi.RemoteException {
        Utils.abrirBase();
        Pproductcategory category = Pproductcategory.findById(id);
        Pproductsubcategory subcategoryDefault = Pproductsubcategory.findById(1);
        boolean res = true;
        if (category != null) {
            Base.openTransaction();
            for (Pproductsubcategory s : category.getAll(Pproductsubcategory.class)) {
                LazyList<Pproduct> fproducts = s.getAll(Pproduct.class);
                for (Pproduct fp : fproducts) {
                    subcategoryDefault.add(fp);
                }
                res = res && s.delete();
            }
            res = res && category.delete();
            Base.commitTransaction();
        }
        return res;
    }

    @Override
    public List<Map> getPproductCategories() throws java.rmi.RemoteException {
        Utils.abrirBase();
        List<Map> ret = Pproductcategory.findAll().toMaps();

        return ret;
    }

    @Override
    public Map<String, Object> getPproductCategory(int id) throws java.rmi.RemoteException {
        Utils.abrirBase();
        Map<String, Object> ret = Pproductcategory.findById(id).toMap();

        return ret;
    }

    @Override
    public Map<String, Object> getPproductCategoryByName(String name) throws java.rmi.RemoteException {
        Utils.abrirBase();
        Map<String, Object> ret = Pproductcategory.first("name = ?", name).toMap();

        return ret;
    }

    @Override
    public Map<String, Object> addPproductSubcategory(int id, String name) throws java.rmi.RemoteException {
        Utils.abrirBase();
        Base.openTransaction();
        Pproductcategory category = Pproductcategory.findById(id);
        Map<String, Object> ret = null;
        if (category != null && !pProductSubCategoryExists(name)) {
            Pproductsubcategory subcategory = Pproductsubcategory.createIt("name", name);
            category.add(subcategory);
            ret = subcategory.toMap();
        }
        Base.commitTransaction();

        return ret;
    }

    @Override
    public boolean deletePproductSubcategory(int id) throws java.rmi.RemoteException {
        Utils.abrirBase();
        Base.openTransaction();
        boolean ret = false;
        Pproductsubcategory subcategory = Pproductsubcategory.findById(id);
        Pproductsubcategory subcategoryDefault = Pproductsubcategory.findById(1);
        if (subcategory != null) {
            LazyList<Pproduct> pproducts = subcategory.getAll(Pproduct.class);
            for (Pproduct fp : pproducts) {
                subcategoryDefault.add(fp);
            }
            ret = subcategory.delete();
        }
        Base.commitTransaction();

        return ret;
    }

    @Override
    public Map<String, Object> modifyPproductSubcategory(int id, String name) throws java.rmi.RemoteException {
        Utils.abrirBase();
        Base.openTransaction();
        Map<String, Object> ret = null;
        Pproductsubcategory subcategory = Pproductsubcategory.findById(id);
        if (subcategory != null) {
            subcategory.setString("name", name);
            subcategory.saveIt();
            ret = subcategory.toMap();
        }
        Base.commitTransaction();

        return ret;
    }

    @Override
    public List<Map> getPproducSubcategoriesCategory(int id) throws java.rmi.RemoteException {
        Utils.abrirBase();
        Pproductcategory category = Pproductcategory.findById(id);
        List<Map> ret = new LinkedList<>();
        if (category != null) {
            ret = category.getAll(Pproductsubcategory.class).toMaps();
        }

        return ret;
    }

    @Override
    public List<Map> getPproducSubcategories() throws java.rmi.RemoteException {
        Utils.abrirBase();
        List<Map> ret = Pproductsubcategory.findAll().toMaps();

        return ret;
    }

    @Override
    public Map<String, Object> getPproductSubcategory(int id) throws java.rmi.RemoteException {
        Utils.abrirBase();
        Pproductsubcategory subcategory = Pproductsubcategory.findById(id);
        Map<String, Object> ret = null;
        if (subcategory != null) {
            ret = subcategory.toMap();
        }

        return ret;
    }

    @Override
    public Map<String, Object> getPproductSubcategoryByName(String name) throws java.rmi.RemoteException {
        Utils.abrirBase();
        Map<String, Object> ret = Pproductsubcategory.findFirst("name = ?", name).toMap();

        return ret;
    }

    @Override
    public List<Map> getPProductsSubcategory(int id) throws RemoteException {
        Utils.abrirBase();
        Pproductsubcategory subcategory = Pproductsubcategory.findById(id);
        List<Map> ret = subcategory.getAll(Pproduct.class).toMaps();
        return ret;
    }

    @Override
    public Map<String, Object> getCategoryOfPproductSubcategory(int id) throws java.rmi.RemoteException {
        Utils.abrirBase();
        Pproductsubcategory subcategory = Pproductsubcategory.findById(id);
        Map<String, Object> ret = null;
        if (subcategory != null) {
            ret = subcategory.parent(Pproductcategory.class).toMap();
        }
        return ret;
    }

    @Override
    public List<Map> searchPproductCategories(String txt) throws java.rmi.RemoteException {
        Utils.abrirBase();
        return Pproductcategory.where("name like ?", "%" + txt + "%").toMaps();
    }

    @Override
    public List<Map> searchPproductSubcategories(String txt) throws java.rmi.RemoteException {
        Utils.abrirBase();
        return Pproductsubcategory.where("name like ?", "%" + txt + "%").toMaps();
    }

}
