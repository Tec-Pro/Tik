/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package interfaces.cashbox.existant;

import java.util.List;
import java.util.Map;

/**
 *
 * @author joako
 */
public interface InterfaceExistantCashbox {
    
    public Map<String,Object> create() throws java.rmi.RemoteException;
    
    public Map<String,Object> update() throws java.rmi.RemoteException;
    
    public boolean delete() throws java.rmi.RemoteException;
    
    public Map<String,Object> get() throws java.rmi.RemoteException;
    
    public List<Map> getAll() throws java.rmi.RemoteException;
    
    
}
