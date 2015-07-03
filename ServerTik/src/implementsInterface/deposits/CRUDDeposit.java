/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package implementsInterface.deposits;

import implementsInterface.CRUDTurn;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;
import java.util.Map;
import models.deposit.Deposit;
import org.javalite.activejdbc.Base;
import utils.Utils;

/**
 *
 * @author joako
 */
public class CRUDDeposit extends UnicastRemoteObject implements interfaces.deposits.InterfaceDeposit {

    public CRUDDeposit() throws RemoteException {
        super();
    }

    @Override
    public Map<String, Object> createWaiterDeposit(int waiter_id, Float amount) throws RemoteException{
        Utils.abrirBase();
        Base.openTransaction();
        CRUDTurn turn = new CRUDTurn();
        Deposit deposit = Deposit.createIt("waiter_id",waiter_id,"amount",amount/*,"turn",turn.getTurn()*/);
        Base.commitTransaction();
        return deposit.toMap();
    }

    @Override
    public Map<String, Object> updateWaiterDeposit(int deposit_id, Float amount) throws RemoteException{
        Utils.abrirBase();
        Base.openTransaction();
        Deposit d = Deposit.findById(deposit_id);
        d.set("amount", amount).saveIt();
        Base.commitTransaction();
        return d.toMap();
    }

    @Override
    public boolean deleteWaiterDeposit(int deposit_id) throws RemoteException{
        Utils.abrirBase();
        Base.openTransaction();
        Deposit d = Deposit.findById(deposit_id);
        boolean res = d.delete();
        Base.commitTransaction();
        return res;
    }


    @Override
    public List<Map> getDepositsOfWaiter(int waiter_id) throws RemoteException{
        Utils.abrirBase();
        return Deposit.where("waiter_id= ?", waiter_id).toMaps();
    }

    @Override
    public List<Map> getAllWaiterDeposits() throws RemoteException{
        Utils.abrirBase();
        return Deposit.where("admin_id = ?", 0).toMaps();
    }

    @Override
    public Map<String, Object> createAdminDeposit(int admin_id, Float amount) throws RemoteException{
        Utils.abrirBase();
        Base.openTransaction();
        CRUDTurn turn = new CRUDTurn();
        Deposit deposit = Deposit.createIt("admin_id", admin_id, "amount", amount/*,"turn",turn.getTurn()*/);
        Base.commitTransaction();
        return deposit.toMap();
    }

    @Override
    public Map<String, Object> updateAdminDeposit(int deposit_id, Float amount) throws RemoteException{
        Utils.abrirBase();
        Base.openTransaction();
        Deposit w = Deposit.findById(deposit_id);
        w.set("amount", amount).saveIt();
        Base.commitTransaction();
        return w.toMap();
    }

    @Override
    public boolean deleteAdminDeposit(int deposit_id) throws RemoteException{
        Utils.abrirBase();
        Base.openTransaction();
        Deposit w = Deposit.findById(deposit_id);
        boolean res = w.delete();
        Base.commitTransaction();
        return res;
    }

    @Override
    public List<Map> getDepositsOfAdmin(int admin_id) throws RemoteException{
        Utils.abrirBase();
        return Deposit.where("admin_id= ?", admin_id).toMaps();
    }

    @Override
    public List<Map> getAllAdminDeposits() throws RemoteException{
        Utils.abrirBase();
        return Deposit.where("waiter_id = ?", 0).toMaps();
    }

    @Override
    public List<Map> getWaitersDepositsForDate(String date) throws RemoteException {
        Utils.abrirBase();
        return Deposit.where("admin_id = ? and created_at >= ?", 0, date).toMaps();
    }

    @Override
    public List<Map> getAdminsDepositsForDate(String date) throws RemoteException {
        Utils.abrirBase();
        return Deposit.where("waiter_id = ? and created_at >= ?", 0, date).toMaps();   
    }

    @Override
    public Map getDeposit(int deposit_id) throws RemoteException {
        Utils.abrirBase();
        return Deposit.findById(deposit_id).toMap();
    }

    @Override
    public Double getWaiterDepositsTotal() throws RemoteException {
        Utils.abrirBase();
        String date = new java.sql.Date(System.currentTimeMillis()).toString();
        List<Map> results = Base.findAll("SELECT SUM(amount) as total FROM tik.deposits WHERE created_at >= ? AND admin_id = 0", date);
        return (double) results.get(0).get("total");
    }

    @Override
    public Double getWaiterDepositsTotal(int id) throws RemoteException{
        Utils.abrirBase();
        String date = new java.sql.Date(System.currentTimeMillis()).toString();
        List<Map> results = Base.findAll("SELECT SUM(amount) as total FROM tik.deposits WHERE created_at >= ? AND waiter_id = ?", date,id);
        return (double) results.get(0).get("total");
    }

    @Override
    public Double getAdminDepositsTotal() throws RemoteException {
        Utils.abrirBase();
        String date = new java.sql.Date(System.currentTimeMillis()).toString();
        List<Map> results = Base.findAll("SELECT SUM(amount) as total FROM tik.deposits WHERE created_at >= ? AND waiter_id = 0", date);
        return (double) results.get(0).get("total");    }

    @Override
    public Double getAdminDepositsTotal(int id) throws RemoteException {
        Utils.abrirBase();
        String date = new java.sql.Date(System.currentTimeMillis()).toString();
        List<Map> results = Base.findAll("SELECT SUM(amount) as total FROM tik.deposits WHERE created_at >= ? AND admin_id = ?", date,id);
        return (double) results.get(0).get("total");
    }

    @Override
    public boolean eraseAdminDeposits() throws RemoteException {
        Utils.abrirBase();
        return Deposit.delete("waiter_id = ?", 0)>0;
    }

    @Override
    public boolean eraseWaiterDeposits() throws RemoteException {
        Utils.abrirBase();
        return Deposit.delete("admin_id = ?", 0)>0;
    }
}
