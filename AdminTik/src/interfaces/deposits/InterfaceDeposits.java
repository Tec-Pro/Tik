/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package interfaces.deposits;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;
import java.util.Map;

/**
 *
 * @author joako
 */
public interface InterfaceDeposits extends Remote {
    
    /**
     *
     * @param waiter_id
     * @param amount
     * @return
     * @throws RemoteException
     */
    public Map<String, Object> createWaiterDeposit(int waiter_id, Float amount) throws RemoteException;

    /**
     *
     * @param deposit_id
     * @param amount
     * @return
     * @throws RemoteException
     */
    public Map<String, Object> modifyWaiterDeposit(int deposit_id, Float amount) throws RemoteException;
    
    /**
     *
     * @param deposit_id
     * @return
     * @throws RemoteException
     */
    public boolean removeWaiterDeposit(int deposit_id) throws RemoteException;

    /**
     *
     * @param deposit_id
     * @return
     * @throws RemoteException
     */
    public Map<String,Object> getWaiterDeposit(int deposit_id) throws RemoteException;

    /**
     *
     * @param waiter_id
     * @return
     * @throws RemoteException
     */
    public List<Map> getWaiterDeposits(int waiter_id) throws RemoteException;
    
    /**
     *
     * @return
     * @throws RemoteException
     */
    public List<Map> getAllWaiterDeposits() throws RemoteException;
    
    /**
     *
     * @param admin_id
     * @param amount
     * @return
     * @throws RemoteException
     */
    public Map<String, Object> createAdminDeposit(int admin_id, Float amount) throws RemoteException;
    
    /**
     *
     * @param deposit_id
     * @param amount
     * @return
     * @throws RemoteException
     */
    public Map<String, Object> modifyAdminDeposit(int deposit_id, Float amount) throws RemoteException;

    /**
     *
     * @param deposit_id
     * @return
     * @throws RemoteException
     */
    public boolean removeAdminDeposit(int deposit_id) throws RemoteException;
    
    /**
     *
     * @param deposit_id
     * @return
     * @throws RemoteException
     */
    public Map getAdminDeposit(int deposit_id) throws RemoteException;
    
    /**
     *
     * @param waiter_id
     * @return
     * @throws RemoteException
     */
    public List<Map> getAdminDeposits(int waiter_id) throws RemoteException;
    
    /**
     *
     * @return
     * @throws RemoteException
     */
    public List<Map> getAllAdminDeposits() throws RemoteException;

}
