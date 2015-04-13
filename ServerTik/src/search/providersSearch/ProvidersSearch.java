/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package search.providersSearch;

import interfaces.providers.InterfaceProvidersSearch;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;
import java.util.Map;
import models.Provider;
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
        List<Map> result = Provider.where("name like ? or cuit like ? or address like ? or description like ? or phones like ?","%" + search + "%","%" + search + "%","%" + search + "%","%" + search + "%","%" + search + "%").toMaps();
        Base.commitTransaction();
         
        return result;
    
    }

}
