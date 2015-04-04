package implementsInterface;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import models.Providercategory;
import org.javalite.activejdbc.Base;
import utils.Utils;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author nico
 */
public class CrudProviderCategory extends UnicastRemoteObject implements interfaces.InterfaceProviderCategory {

    public CrudProviderCategory() throws RemoteException {
        super();

    }

    /**
     *
     * @param name
     * @return map representing the new category created.
     * @throws RemoteException
     */
    @Override
    public Map<String, Object> create(String name) throws java.rmi.RemoteException {
        Utils.abrirBase();
        Base.openTransaction();
        Map<String, Object> ret = Providercategory.createIt("name", name).toMap();
        Base.commitTransaction();
        Utils.cerrarBase();
        return ret;
    }

    /**
     *
     * @param id
     * @param name
     * @return the modified category if exists, empty map otherwise.
     * @throws RemoteException
     */
    @Override
    public Map<String, Object> modify(int id, String name) throws java.rmi.RemoteException {
        Utils.abrirBase();
        Providercategory pCategory = Providercategory.findById(id);
        Map<String, Object> res = Collections.EMPTY_MAP;
        if (pCategory != null) {
            pCategory.setString("name", name);
            Base.openTransaction();
            pCategory.saveIt();
            res = pCategory.toMap();
            Base.commitTransaction();
            Utils.cerrarBase();
        }
        return res;
    }

    /**
     *
     * @param id
     * @return True if the requested category was deleted from the database.
     * @throws RemoteException
     */
    @Override
    public boolean delete(int id) throws java.rmi.RemoteException {
        Utils.abrirBase();
        Providercategory pCategory = Providercategory.findById(id);
        boolean res = false;
        if (pCategory != null) {
            Base.openTransaction();
            res = pCategory.delete();
            Base.commitTransaction();
        }
        return res;
    }

    /**
     *
     * @param id
     * @return Map representing the requested category.
     * @throws RemoteException
     */
    @Override
    public Map<String, Object> getProviderCategory(int id) throws RemoteException {
        Utils.abrirBase();
        Map<String, Object> ret = Providercategory.findById(id).toMap();
        Utils.cerrarBase();
        return ret;
    }

    /**
     *
     * @return A list of all the provider categories.
     * @throws RemoteException
     */
    @Override
    public List<Map> getProviderCategories() throws RemoteException {
        Utils.abrirBase();
        List<Map> ret = Providercategory.findAll().toMaps();
        Utils.cerrarBase();
        return ret;
    }

}