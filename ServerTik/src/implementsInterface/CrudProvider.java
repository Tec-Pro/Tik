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
import models.Provider;
import models.Providercategory;
import org.javalite.activejdbc.Base;
import utils.Utils;

/**
 *
 * @author nico
 */
public class CrudProvider extends UnicastRemoteObject implements interfaces.providers.InterfaceProvider {

    /**
     *
     * @throws RemoteException
     */
    public CrudProvider() throws RemoteException {
        super();

    }

    /**
     * Crea un proveedor en la base de datos.
     *
     * @param name nombre del proveedor.
     * @param cuit cuit del proveedor.
     * @param address dirección del proveedor.
     * @param description descripción del proveedor.
     * @param phones teléfonos del proveedor.
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
     * Función que modifica un proveedor existente de la DB.
     *
     * @param id id del proveedor a modificar.
     * @param name nuevo nombre del proveedor.
     * @param cuit nuevo cuit del proveedor.
     * @param address nueva dirección del proveedor.
     * @param description nueva descripción del proveedor.
     * @param phones nuevos teléfonos del proveedor.
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
     * Función que elimina un proveedor si existe en la DB.
     *
     * @param id id del proveedor a eliminar.
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
     * Función que devuelve el proveedor correspondiente con el id pasado.
     *
     * @param id id del proveedor pedido.
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
     * Función que lista todos los proveedores existentes en la DB.
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
     * Función que devuelve las categorías de un proveedor específico.
     *
     * @param id id del proveedor del que se quieren obtener las categorías.
     * @return una lista de Maps que representan las categorías de un proveedor.
     * @throws RemoteException
     */
    @Override
    public List<Map> getCategoriesFromProvider(int id) throws RemoteException {
        Utils.abrirBase();
        Base.openTransaction();
        List<Map> result;
        //busco el proveedor corespondiente al id
        Provider prov = Provider.findById(id);
        /*if (prov != null) {
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
         }*/
        result = prov.getAll(Providercategory.class).toMaps();
        Base.commitTransaction();
        return result;
    }

    /**
     * Función que guarda las nuevas categorías de un proveedor.
     *
     * @param provider_id id del proveedor al que se le guardan las categorías.
     * @param categoriesToAdd categorías para agregar al proveedor especificado.
     * @param categoriesToRemove categorías para remover del proveedor
     * especificado.
     * @return un map representando al proveedor especificado.
     * @throws RemoteException
     */
    @Override
    public Map<String, Object> saveCategoriesOfProvider(int provider_id, LinkedList categoriesToAdd, LinkedList categoriesToRemove) throws RemoteException {
        Utils.abrirBase();
        Base.openTransaction();
        Provider p = Provider.findById(provider_id);
        List<Providercategory> categoriesFromProvider = p.getAll(Providercategory.class);
        for (Iterator it = categoriesToRemove.iterator(); it.hasNext();) {
            int i = (int) it.next();
            Providercategory pc = Providercategory.findById(i);
            for (Providercategory providerCategory : categoriesFromProvider) {
                if (providerCategory.getId().equals(i)) {
                    p.remove(pc);
                    categoriesFromProvider = p.getAll(Providercategory.class);
                }
            }
        }
        for (Iterator it = categoriesToAdd.iterator(); it.hasNext();) {
            int j = (int) it.next();
            Providercategory pc = Providercategory.findById(j);
            boolean add = true;
            for (Providercategory providerCategory : categoriesFromProvider) {
                add = !providerCategory.getId().equals(j);
            }
            if (add) {
                p.add(pc);
            }
        }
        Base.commitTransaction();
        return getProvider(provider_id);
    }

}
