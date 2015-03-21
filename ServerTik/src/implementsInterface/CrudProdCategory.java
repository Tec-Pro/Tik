package implementsInterface;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;
import java.util.Map;
import models.PCategory;
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
public class CrudProdCategory extends UnicastRemoteObject implements interfaces.InterfaceProdCategory {

    public CrudProdCategory() throws RemoteException {
        super();

    }

    public Map<String, Object> create(String name) throws java.rmi.RemoteException {
        Utils.abrirBase();
        Base.openTransaction();
        Map<String, Object> ret = PCategory.createIt("name", name);
        Base.commitTransaction();
        Utils.cerrarBase();
        return ret;
    }

    public Map<String, Object> modify(int id, String name) throws java.rmi.RemoteException {
        Utils.abrirBase();
        PCategory pCategory = PCategory.findById(id);
        Map<String, Object> res = null;
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

    public boolean delete(int id) throws java.rmi.RemoteException {
        Utils.abrirBase();
        PCategory pCategory = PCategory.findById(id);
        boolean res = false;
        if (pCategory != null) {
            Base.openTransaction();
            res = pCategory.delete();
            Base.commitTransaction();
        }
        return res;
    }

    public Map<String, Object> getProdCategory(int id) throws java.rmi.RemoteException {
        Utils.abrirBase();
        Map<String, Object> ret = PCategory.findById(id).toMap();
        Utils.cerrarBase();
        return ret;
    }

    public List<Map> getPCategories() throws java.rmi.RemoteException {
        Utils.abrirBase();
        List<Map> ret = PCategory.findAll().toMaps();
        Utils.cerrarBase();
        return ret;
    }

}
