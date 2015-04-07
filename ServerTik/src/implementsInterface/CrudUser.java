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

    public Map<String,Object> create(
            String name,
            String surname,
            String pass,
            Date entryDate,
            Date exitDate,
            String turn,
            Date dateOfBirth,
            String placeOfBirth,
            String idType,
            String idNumber,
            String adress,
            String homePhone,
            String emergencyPhone,
            String mobilePhone,
            String maritalStatus,
            String bloodType,
            String position
            ) throws java.rmi.RemoteException {
        
        Utils.abrirBase();
        Base.openTransaction();
        Map<String,Object> res= User.createIt(
                "name", name,
                "surname", surname,
                "pass", pass,
                "entry_date", entryDate,
                "exit_date", exitDate,
                "turn", turn,
                "date_of_birth", dateOfBirth,
                "place_of_birth", placeOfBirth,
                "id_type", idType,
                "id_number", idNumber,
                "adress", adress,
                "home_phone", homePhone,
                "emergency_phone", emergencyPhone,
                "mobile_phone", mobilePhone,
                "marital_status", maritalStatus,
                "blood_type", bloodType,
                "position", position
                ).toMap();
        Base.commitTransaction();
        Utils.cerrarBase();
        return res;
    }

    public Map<String,Object> modify(
            int id,
            String name,
            String surname,
            String pass,
            Date entryDate,
            Date exitDate,
            String turn,
            Date dateOfBirth,
            String placeOfBirth,
            String idType,
            String idNumber,
            String adress,
            String homePhone,
            String emergencyPhone,
            String mobilePhone,
            String maritalStatus,
            String bloodType,
            String position
            ) throws java.rmi.RemoteException  {
        Utils.abrirBase();
        User user = User.findById(id);
        Map<String,Object> res = null;
        if (user != null) {
            user.setString("name", name);
            user.setString("surname", surname);
            user.setString("pass", pass);
            user.setDate("entry_date", entryDate);
            user.setDate("exit_date", exitDate);
            user.setString("turn", turn);
            user.setDate("date_of_birth", dateOfBirth);
            user.setString("place_of_birth", placeOfBirth);
            user.setString("id_type", idType);
            user.setString("id_number", idNumber);
            user.setString("adress", adress);
            user.setString("home_phone", homePhone);
            user.setString("emergency_phone", emergencyPhone);
            user.setString("mobile_phone", mobilePhone);
            user.setString("marital_status", maritalStatus);
            user.setString("blood_type", bloodType);
            user.setString("position", position);
            res = user.toMap();
            Base.openTransaction();
            user.saveIt();
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
            res = true;
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
