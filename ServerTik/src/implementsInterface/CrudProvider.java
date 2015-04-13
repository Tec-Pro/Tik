/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package implementsInterface;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import models.Category;
import models.Provider;
import models.Providercategory;
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
     * @return a map representing the modified provider if exists, empty map
     * otherwise.
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
            provider.deleteCascadeShallow();
            res = true;
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
         
        return ret;
    }

    /**
     *Función que agrega una categoría a un proveedor. Recibe como parámetros
     * el <code>id_proveedor</code> y el <code>id_categoría</code>. Devuelve un 
     * Map conteniendo el proveedor.
     * 
     * @param provider_id
     * @param category_id
     * @return Un map con el proveedor.
     * @throws RemoteException
     */
    @Override
    public Map<String, Object> addCategoryToProvider(int provider_id, int category_id) throws java.rmi.RemoteException {
        Utils.abrirBase();
        Base.openTransaction();
        Provider p = Provider.findById(provider_id);
        Providercategory pc = Providercategory.findById(category_id);
        p.add(pc);
        Base.commitTransaction();
        return getProvider(provider_id);
    }

    @Override
    public List<Map> getCategoriesFromProvider(int id) throws RemoteException {
        Utils.abrirBase();
        Base.openTransaction();
        List<Map> result = new LinkedList<>();
        //busco el proveedor corespondiente al id
        Provider prov = Provider.findById(id);
        if (prov != null) {
            //saco las relaciones con categorias en las cuales se encuentra ese proveedor
            List<ProvidersProvidercategory> provCategoryList = prov.getAll(ProvidersProvidercategory.class);
            //si tiene categorias asociadas
            if (provCategoryList != null) {
                //saco la categoria de cada relacion y la agrego a la lista resultado
                Iterator<ProvidersProvidercategory> provCategoryItr = provCategoryList.iterator();
                while (provCategoryItr.hasNext()) {
                    ProvidersProvidercategory provCategory = provCategoryItr.next();
                    Providercategory category = Providercategory.findById(provCategory.getInteger("providercategory_id"));
                    result.add(category.toMap());
                }
            }
        }
        Base.commitTransaction();
        return result;
    }

}
