/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package interfaces;

import java.rmi.Remote;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 *
 * @author nico
 */
public interface InterfaceUser extends Remote {

    public Map<String,Object> create(
            String name,
            String surname,
            String pass,
            Date hiredDate,
            Date dischargedDate,
            String turn,
            Date dateOfBirth,
            String placeOfBirth,
            String idType,
            String idNumber,
            String address,
            String homePhone,
            String emergencyPhone,
            String mobilePhone,
            String maritalStatus,
            String bloodType,
            String position,
            byte[] photo
            ) throws java.rmi.RemoteException;
     
    public Map<String,Object> modify(
            int id,
            String name,
            String surname,
            String pass,
            Date hiredDate,
            Date dischargedDate,
            String turn,
            Date dateOfBirth,
            String placeOfBirth,
            String idType,
            String idNumber,
            String address,
            String homePhone,
            String emergencyPhone,
            String mobilePhone,
            String maritalStatus,
            String bloodType,
            String position
            ) throws java.rmi.RemoteException ;
    
     public boolean delete(int id) throws java.rmi.RemoteException;
     
     public Map<String,Object> getUser(int id) throws java.rmi.RemoteException;
   
     public  List<Map> getUsers() throws java.rmi.RemoteException;    
}
