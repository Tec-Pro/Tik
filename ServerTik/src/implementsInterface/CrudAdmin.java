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
import java.util.logging.Level;
import java.util.logging.Logger;
import models.Admin;
import org.javalite.activejdbc.Base;
import utils.Utils;
import utils.Encryption;

/**
 *
 * @author nico
 */
public class CrudAdmin extends UnicastRemoteObject implements interfaces.InterfaceAdmin {
 
    public CrudAdmin() throws RemoteException{
        super();
        
    }
    
    private boolean adminExists(String name){
        return Admin.first("name = ?", name) != null;
    }
    
    public Map<String,Object> create(String name, String pass) throws java.rmi.RemoteException{
        Utils.abrirBase();
        Base.openTransaction();
        byte[] passEncrypted = {0};
        try {
            passEncrypted = Encryption.encrypt(pass);
        } catch (Exception ex) {
            Logger.getLogger(CrudAdmin.class.getName()).log(Level.SEVERE, null, ex);
        }
        Map<String,Object> res = null;
       
        if(!adminExists(name))
             res = Admin.createIt("name",name,"pass", passEncrypted).toMap();
        Base.commitTransaction();
                 
        return res;

    }
     
    /**
     * 
     * @param id 
     * @param name
     * @param pass
     * @return a map with the user modified or null if the user to be modified doesn't exists
     * @throws RemoteException
     */
    @Override
     public Map<String,Object> modify(int id,String name, String pass) throws java.rmi.RemoteException{
        Utils.abrirBase();
        Admin admin= Admin.findById(id);
        
        byte[] passEncrypted = {0};
        try {
            passEncrypted = Encryption.encrypt(pass);
        } catch (Exception ex) {
            Logger.getLogger(CrudAdmin.class.getName()).log(Level.SEVERE, null, ex);
        }
        if(admin!=null){
            if(!admin.getString("name").equals(name))
                if(adminExists(name))
                    return null;
            admin.setString("name",name);
            admin.set("pass",passEncrypted);
            Base.openTransaction();
            admin.saveIt();
            Base.commitTransaction();
             
            return admin.toMap();
        }else
            return null;
   
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
          
         return ret;
     }
     
     public Map<String,Object> loginAdmin(String name, String pass) throws java.rmi.RemoteException{
        Utils.abrirBase();
        Map<String,Object> ret = null;
        byte[] passEncrypted = {0};
        try {
            passEncrypted = Encryption.encrypt(pass);
        } catch (Exception ex) {
            Logger.getLogger(CrudAdmin.class.getName()).log(Level.SEVERE, null, ex);
        }
         if(Admin.first("name = ? and pass = ?", name,passEncrypted)!=null)
            ret= Admin.first("name = ? and pass = ?", name,passEncrypted).toMap();
          
         return ret;
     }
     
     public  List<Map> getAdmins() throws java.rmi.RemoteException{
         Utils.abrirBase();
         List<Map> ret= Admin.findAll().toMaps();
          
         return ret;
     }


}
