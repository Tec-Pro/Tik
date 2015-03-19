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
import models.Phone;
import models.Provider;
import org.javalite.activejdbc.Base;
import utils.Utils;

/**
 *
 * @author nico
 */
public class CrudProvider extends UnicastRemoteObject implements interfaces.InterfaceProvider {

    public CrudProvider() throws RemoteException {
        super();

    }

    public boolean create(String name, String cuit, String address, String description) throws java.rmi.RemoteException {
        Utils.abrirBase();
        Base.openTransaction();
        Provider provider = new Provider();
        provider.setString("name", name);
        provider.setString("cuit", cuit);
        provider.setString("address", address);
        provider.setString("description", description);
        boolean res = provider.saveIt();
        Base.commitTransaction();
        Utils.cerrarBase();
        return res;
    }

    public boolean modify(int id, String name, String cuit, String address, String description) throws java.rmi.RemoteException {
        Utils.abrirBase();
        Provider provider = Provider.findById(id);
        boolean res = false;
        if (provider != null) {
            provider.setString("name", name);
            provider.setString("cuit", cuit);
            provider.setString("address", address);
            provider.setString("description", description);
            Base.openTransaction();
            res = provider.saveIt();
            Base.commitTransaction();
            Utils.cerrarBase();
        }
        return res;
    }

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

     public Map<String,Object> getProvider(int id) throws java.rmi.RemoteException{
         Utils.abrirBase();
         Map<String,Object> ret= Provider.findById(id).toMap();
         Utils.cerrarBase();
         return ret;
     }    
    public List<Map> getProviders() throws java.rmi.RemoteException {
        Utils.abrirBase();
        List<Map> ret = Provider.findAll().toMaps();
        Utils.cerrarBase();
        return ret;
    }
    
         public boolean addPhone(int id,String number)throws java.rmi.RemoteException{
             Utils.abrirBase();
             Base.openTransaction();
             Provider provider= Provider.findById(id);
             boolean ret= false;
             if(provider!=null){
                Phone phone = Phone.create("number",number);
                provider.add(phone);
                ret= true;
             }
             Base.commitTransaction();
             Utils.cerrarBase();
             return ret;
         }

         public boolean deletePhone(int id) throws java.rmi.RemoteException{
             Utils.abrirBase();
             Base.openTransaction();
             boolean ret= false;
             Phone phone = Phone.findById(id);
             if(phone!=null){
                 ret=phone.delete();
             }
             Base.commitTransaction();
             Utils.cerrarBase();
             return ret;
         }
         
         public boolean modifyPhone(int id, String number)throws java.rmi.RemoteException{
             Utils.abrirBase();
             Base.openTransaction();
             boolean ret= false;
             Phone phone = Phone.findById(id);
             if(phone!=null){
                 phone.setString("number", number);
                 ret=phone.saveIt();
             }
             Base.commitTransaction();
             Utils.cerrarBase();
             return ret;
         }

         
         public List<Map> getPhonesProvider(int id)throws java.rmi.RemoteException{
             Utils.abrirBase();
             Provider provider = Provider.findById(id);
             List<Map> ret = new LinkedList<>();
             if(provider!=null){
                 ret=provider.getAll(Phone.class).toMaps();
             }
             Utils.cerrarBase();
             return ret;
         }
         
         public Map<String,Object> getPhone(int id)throws java.rmi.RemoteException{
             Utils.abrirBase();
             Phone phone = Phone.findById(id);
             Map<String,Object> ret = null ;
             if(phone!=null){
                 ret=phone.toMap();
             }
             Utils.cerrarBase();
             return ret;
         }
}
