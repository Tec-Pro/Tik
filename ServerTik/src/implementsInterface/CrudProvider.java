/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package implementsInterface;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import models.Provider;
import models.ProvidersProvidercategory;
import org.javalite.activejdbc.Base;
import utils.Utils;

/**
 *
 * @author nico
 */
public class CrudProvider extends UnicastRemoteObject implements interfaces.providers.InterfaceProvider {

    public CrudProvider() throws RemoteException {
        super();

    }

    /**
     *
     * @param name
     * @param cuit
     * @param address
     * @param description
     * @param phones
     * @return map representing the created provider.
     * @throws RemoteException
     */
    @Override
    public Map<String, Object> create(String name, String cuit, String address, String description, String phones) throws java.rmi.RemoteException {
        Utils.abrirBase();
        Base.openTransaction();
        Provider ret = Provider.createIt("name", name, "cuit", cuit, "address", address, "description", description, "phones", phones);
        Base.commitTransaction();
        Utils.cerrarBase();
        return ret.toMap();
    }

    /**
     *
     * @param id
     * @param name
     * @param cuit
     * @param address
     * @param description
     * @param phones
     * @return a map representing the modified provider if exists, empty map otherwise.
     * @throws RemoteException
     */
    @Override
    public Map<String, Object> modify(int id, String name, String cuit, String address, String description, String phones) throws java.rmi.RemoteException {
        Utils.abrirBase();
        Provider provider = Provider.findById(id);
        Map<String, Object> res = Collections.EMPTY_MAP;
        if (provider != null) {
            res = provider.set("name", name, "cuit", cuit, "address", address, "description", description, "phones", phones).toMap();
            Base.openTransaction();
            provider.saveIt();
            Base.commitTransaction();
            Utils.cerrarBase();
        }
        return res;
    }

    /**
     *
     * @param id
     * @return true if the provider was deleted from the database.
     * @throws RemoteException
     */
    @Override
    public boolean delete(int id) throws java.rmi.RemoteException {
        Utils.abrirBase();
        Provider provider = Provider.findById(id);
        boolean res = false;
        if (provider != null) {
            Base.openTransaction();
            res = provider.delete();
            Base.commitTransaction();
        }
        return res;
    }

    /**
     *
     * @param id
     * @return map representing the requested provider.
     * @throws RemoteException
     */
    @Override
    public Map<String, Object> getProvider(int id) throws java.rmi.RemoteException {
        Utils.abrirBase();
        Map<String, Object> ret = Provider.findById(id).toMap();
        Utils.cerrarBase();
        return ret;
    }

    /**
     *
     * @return a list of maps representing the providers.
     * @throws RemoteException
     */
    @Override
    public List<Map> getProviders() throws java.rmi.RemoteException {
        Utils.abrirBase();
        List<Map> ret = Provider.findAll().toMaps();
        Utils.cerrarBase();
        return ret;
    }

    /**
     *
     * @param provider_id
     * @param category_id
     * @return
     * @throws RemoteException
     */
    @Override
    public Map<String, Object> addCategoryToProvider(int provider_id, int category_id) throws java.rmi.RemoteException {
        Utils.abrirBase();
        Base.openTransaction();
        ProvidersProvidercategory.createIt("provider_id",provider_id,"providercategory_id",category_id);
        Base.commitTransaction();
        return getProvider(provider_id);
    }

}
