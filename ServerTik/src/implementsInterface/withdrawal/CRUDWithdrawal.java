/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package implementsInterface.withdrawal;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;
import java.util.Map;
import models.withdrawals.Withdrawal;
import org.javalite.activejdbc.Base;
import utils.Utils;

/**
 *
 * @author joako
 */
public class CRUDWithdrawal extends UnicastRemoteObject implements interfaces.withdrawals.InterfaceWithdrawal {

    public CRUDWithdrawal() throws RemoteException {
        super();
    }
    @Override
    public Map<String, Object> create(int admin_id, String detail, Float amount) throws RemoteException{
        Utils.abrirBase();
        Base.openTransaction();
        Withdrawal withdrawal = Withdrawal.createIt("admin_id",admin_id,"detail",detail,"amount",amount);
        Base.commitTransaction();
        return withdrawal.toMap();
    }

    @Override
    public Map<String, Object> modify(int id, String detail, Float amount) throws RemoteException  {
        Utils.abrirBase();
        Base.openTransaction();
        Withdrawal w = Withdrawal.findById(id);
        w.set("detail", detail, "amount",amount).saveIt();
        Base.commitTransaction();
        return w.toMap();
    }

    @Override
    public boolean delete(int id) throws RemoteException {
        Utils.abrirBase();
        Base.openTransaction();
        Withdrawal w = Withdrawal.findById(id);
        boolean res = w.delete();
        Base.commitTransaction();
        return res;
    }

    @Override
    public Map<String, Object> get(int id) throws RemoteException {
        Utils.abrirBase();
        return Withdrawal.findById(id).toMap();
    }

    @Override
    public List<Map> getAll() throws RemoteException  {
        Utils.abrirBase();
        return Withdrawal.findAll().toMaps();
    }

    @Override
    public List<Map> getWithdrawalsOfAdmin(int admin_id) throws RemoteException  {
        Utils.abrirBase();
        return Withdrawal.find("admin_id = ?", admin_id).toMaps();
    }

    @Override
    public List<Map> getWithdrawalsForDate(String date) throws RemoteException {
        Utils.abrirBase();
        return Withdrawal.where("created_at > ?", date).toMaps();
    }
    
}
