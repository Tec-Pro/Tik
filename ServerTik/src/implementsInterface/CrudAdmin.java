/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package implementsInterface;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;
import java.util.Map;
import models.Admin;
import org.javalite.activejdbc.Base;
import utils.Utils;

/**
 *
 * @author nico
 */
public class CrudAdmin extends UnicastRemoteObject implements interfaces.InterfaceAdmin {
 
    public CrudAdmin() throws RemoteException{
        super();
        
    }
    
    public Map<String,Object> create(String name, String pass) throws java.rmi.RemoteException{
        Utils.abrirBase();
        Base.openTransaction();
        Map<String,Object> res =Admin.createIt("name",name,"pass",pass);
        Base.commitTransaction();
        Utils.cerrarBase();        
        return res;
    }
     
     public Map<String,Object> modify(int id,String name, String pass) throws java.rmi.RemoteException{
        Utils.abrirBase();
        Admin admin= Admin.findById(id);
        boolean res= false;
        if(admin!=null){
            admin.setString("name",name);
            admin.setString("pass",pass);
            Base.openTransaction();
            res = admin.saveIt();
            Base.commitTransaction();
            Utils.cerrarBase();
        }
        return admin.toMap();
     }
     
     public boolean delete(int id) throws java.rmi.RemoteException{
        Utils.abrirBase();
        Admin admin= Admin.findById(id);
        boolean res= false;
        if(admin != null){
            Base.openTransaction();
            res= admin.delete();
            Base.commitTransaction();
        }
        return res;
     }
     
     public Map<String,Object> getAdmin(int id) throws java.rmi.RemoteException{
         Utils.abrirBase();
         Map<String,Object> ret= Admin.findById(id).toMap();
         Utils.cerrarBase();
         return ret;
     }
     
     
     public  List<Map> getAdmins() throws java.rmi.RemoteException{
         Utils.abrirBase();
         List<Map> ret= Admin.findAll().toMaps();
         Utils.cerrarBase();
         return ret;
     }


}
