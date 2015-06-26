/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package implementsInterface.deposits;

import interfaces.deposits.InterfaceDeposits;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;
import java.util.Map;
import models.deposit.AdminDeposit;
import models.deposit.WaiterDeposit;
import org.javalite.activejdbc.Base;
import utils.Utils;

/**
 *
 * @author joako
 */
public class CRUDDeposits extends UnicastRemoteObject implements InterfaceDeposits {

    public CRUDDeposits() throws RemoteException{
        super();
    }
    
    @Override
    public Map<String, Object> createWaiterDeposit(int waiter_id, Float amount) throws RemoteException {
        Utils.abrirBase();
        Base.openTransaction();
        WaiterDeposit deposit = WaiterDeposit.createIt("waiter_id",waiter_id,"amount",amount);
        Base.commitTransaction();
        return deposit.toMap();
    }

    @Override
    public Map<String, Object> modifyWaiterDeposit(int deposit_id, Float amount) throws RemoteException {
        Utils.abrirBase();
        Base.openTransaction();
        WaiterDeposit deposit = WaiterDeposit.findById(deposit_id);
        deposit.set("amount",amount);
        Base.commitTransaction();
        return deposit.toMap();
    }

    @Override
    public boolean removeWaiterDeposit(int deposit_id) throws RemoteException {
        Utils.abrirBase();
        Base.openTransaction();
        WaiterDeposit deposit = WaiterDeposit.findById(deposit_id);
        boolean res = deposit.delete();
        Base.commitTransaction();
        return res;
    }

    @Override
    public Map<String, Object> getWaiterDeposit(int deposit_id) throws RemoteException {
        Utils.abrirBase();
        return WaiterDeposit.findById(deposit_id).toMap();
    }

    @Override
    public List<Map> getWaiterDeposits(int waiter_id) throws RemoteException {
        Utils.abrirBase();
        return WaiterDeposit.find("waiter_id = ?", waiter_id).toMaps();
    }

    @Override
    public List<Map> getAllWaiterDeposits() throws RemoteException {
        Utils.abrirBase();
        return WaiterDeposit.findAll().toMaps();
    }

    @Override
    public Map<String, Object> createAdminDeposit(int admin_id, Float amount) throws RemoteException {
        Utils.abrirBase();
        Base.openTransaction();
        AdminDeposit deposit = AdminDeposit.createIt("admin_id", admin_id,"amount",amount);
        Base.commitTransaction();
        return deposit.toMap();
    }

    @Override
    public Map<String, Object> modifyAdminDeposit(int deposit_id, Float amount) throws RemoteException {
        Utils.abrirBase();
        Base.openTransaction();
        AdminDeposit deposit = AdminDeposit.findById(deposit_id);
        deposit.set("amount",amount);
        Base.commitTransaction();
        return deposit.toMap();    }

    @Override
    public boolean removeAdminDeposit(int deposit_id) throws RemoteException {
        Utils.abrirBase();
        Base.openTransaction();
        AdminDeposit deposit = AdminDeposit.findById(deposit_id);
        boolean res = deposit.delete();
        Base.commitTransaction();
        return res;
    }

    @Override
    public Map getAdminDeposit(int deposit_id) throws RemoteException {
        Utils.abrirBase();
        return AdminDeposit.findById(deposit_id).toMap();
    }

    @Override
    public List<Map> getAdminDeposits(int admin_id) throws RemoteException {
        Utils.abrirBase();
        return AdminDeposit.where("admin_id = ?", admin_id).toMaps();
    }

    @Override
    public List<Map> getAllAdminDeposits() throws RemoteException {
        Utils.abrirBase();
        return AdminDeposit.findAll().toMaps();
    }
    
}
