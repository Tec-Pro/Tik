/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package interfaces.deposits;

import java.rmi.Remote;
import java.util.List;
import java.util.Map;

/**
 *
 * @author joako
 */
public interface InterfaceDeposit extends Remote {
    
    public Map<String,Object> createWaiterDeposit(int waiter_id, Float amount) throws java.rmi.RemoteException;
    
    public Map<String,Object> updateWaiterDeposit(int deposit_id, Float amount) throws java.rmi.RemoteException;
    
    public boolean deleteWaiterDeposit(int deposit_id) throws java.rmi.RemoteException;
        
    public List<Map> getDepositsOfWaiter(int waiter_id) throws java.rmi.RemoteException;
    
    public List<Map> getAllWaiterDeposits() throws java.rmi.RemoteException;
    
    public List<Map> getWaitersDepositsForDate(String date) throws java.rmi.RemoteException;
    
    public Map<String,Object> createAdminDeposit(int admin_id, Float amount) throws java.rmi.RemoteException;
    
    public Map<String,Object> updateAdminDeposit(int deposit_id, Float amount) throws java.rmi.RemoteException;
    
    public boolean deleteAdminDeposit(int deposit_id) throws java.rmi.RemoteException;
        
    public List<Map> getDepositsOfAdmin(int admin_id) throws java.rmi.RemoteException;
    
    public List<Map> getAllAdminDeposits() throws java.rmi.RemoteException;
    
    public List<Map> getAdminsDepositsForDate(String date) throws java.rmi.RemoteException;
    
    public Map getDeposit(int deposit_id) throws java.rmi.RemoteException;
    
    public Double getWaiterDepositsTotal() throws java.rmi.RemoteException;
    
    public Double getWaiterDepositsTotal(int id) throws java.rmi.RemoteException;

    public Double getAdminDepositsTotal() throws java.rmi.RemoteException;
    
    public Double getAdminDepositsTotal(int id) throws java.rmi.RemoteException;
    
    public boolean eraseAdminDeposits() throws java.rmi.RemoteException;
    
    public boolean eraseWaiterDeposits() throws java.rmi.RemoteException;
}
