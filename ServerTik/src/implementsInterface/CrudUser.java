/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package implementsInterface;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Date;
import java.util.List;
import java.util.Map;
import models.User;
import org.javalite.activejdbc.Base;
import utils.Utils;

/**
 *
 * @author nico
 */
public class CrudUser extends UnicastRemoteObject implements interfaces.InterfaceUser {

    public CrudUser() throws RemoteException {
        super();

    }

    public boolean create(String name, String pass, Date entryDate, Date exitDate, String turn) throws java.rmi.RemoteException {
        Utils.abrirBase();
        Base.openTransaction();
        User user = new User();
        user.setString("name", name);
        user.setString("pass", pass);
        user.setDate("entry_date", entryDate);
        user.setDate("exit_date", exitDate);
        user.setString("turn", turn);
        boolean res = user.saveIt();
        Base.commitTransaction();
        Utils.cerrarBase();
        return res;
    }

    public boolean modify(int id, String name, String pass, Date entryDate, Date exitDate, String turn) throws java.rmi.RemoteException {
        Utils.abrirBase();
        User user = User.findById(id);
        boolean res = false;
        if (user != null) {
            user.setString("name", name);
            user.setString("pass", pass);
            user.setDate("entry_date", entryDate);
            user.setDate("exit_date", exitDate);
            user.setString("turn", turn);
            Base.openTransaction();
            res = user.saveIt();
            Base.commitTransaction();
            Utils.cerrarBase();
        }
        return res;
    }

    public boolean delete(int id) throws java.rmi.RemoteException {
        Utils.abrirBase();
        User user = User.findById(id);
        boolean res = false;
        if (user != null) {
            Base.openTransaction();
            res = user.delete();
            Base.commitTransaction();
        }
        return res;
    }

     public Map<String,Object> getUser(int id) throws java.rmi.RemoteException{
         Utils.abrirBase();
         Map<String,Object> ret= User.findById(id).toMap();
         Utils.cerrarBase();
         return ret;
     }
    
    public List<Map> getUsers() throws java.rmi.RemoteException {
        Utils.abrirBase();
        List<Map> ret = User.findAll().toMaps();
        Utils.cerrarBase();
        return ret;
    }
    
    

}
