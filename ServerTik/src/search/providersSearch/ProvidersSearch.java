/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package search.providersSearch;

import interfaces.providers.InterfaceProvidersSearch;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import models.Provider;
import models.Providercategory;
import models.ProvidersProvidercategory;
import org.javalite.activejdbc.Base;
import org.javalite.activejdbc.Model;
import utils.Utils;

/**
 *
 * @author eze
 */
public class ProvidersSearch extends UnicastRemoteObject implements InterfaceProvidersSearch {

    public ProvidersSearch()throws RemoteException{
        super();
    }
    
    @Override
    public List<Map> searchProviders(String search) throws RemoteException {
        Utils.abrirBase();
        Base.openTransaction();
        List<Map> result = Provider.where("id like ? or name like ? or cuit like ? or address like ? or description like ? or phones like ?","%"+ search +"%","%" + search + "%","%" + search + "%","%" + search + "%","%" + search + "%","%" + search + "%").toMaps();
        Base.commitTransaction();
         
        return result;
    
    }

    @Override
    public List<Map> searchProviders(String search, int category_id) throws RemoteException {
        Utils.abrirBase();
        Base.openTransaction();
        LinkedList<Map> result = new LinkedList<>();
        //Providercategory providerCategory = Providercategory.findById(category_id);
        //List<Map> providers  = providerCategory.get(Provider.class, "", params)
        List<Map> providers = Provider.where("id like ? or name like ? or cuit like ? or address like ? or description like ? or phones like ?","%"+ search +"%", "%" + search + "%","%" + search + "%","%" + search + "%","%" + search + "%","%" + search + "%").toMaps();
        for (Map provider : providers){
            List<Map> providerCategories = ProvidersProvidercategory.where("provider_id = ?", provider.get("id")).toMaps();
            for (Map providerCategory : providerCategories){
                if (providerCategory.get("providercategory_id").equals(category_id)){
                    result.add(provider);
                    break;
                }
            }

        }
        Base.commitTransaction();
        return result;
    }
    
    

}
