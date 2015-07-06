/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package implementsInterface.withdrawal;

import implementsInterface.CRUDTurn;
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
    public Map<String, Object> create(int admin_id, String detail, Float amount) throws RemoteException {
        Utils.abrirBase();
        Base.openTransaction();
        CRUDTurn turn = new CRUDTurn();
        Withdrawal withdrawal = Withdrawal.createIt("admin_id", admin_id, "detail", detail, "amount", amount, "turn", turn.getTurn());
        Base.commitTransaction();
        return withdrawal.toMap();
    }

    @Override
    public Map<String, Object> modify(int id, String detail, Float amount) throws RemoteException {
        Utils.abrirBase();
        Base.openTransaction();
        Withdrawal w = Withdrawal.findById(id);
        w.set("detail", detail, "amount", amount).saveIt();
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
    public List<Map> getWithdrawals() throws RemoteException {
        Utils.abrirBase();
        return Withdrawal.findAll().toMaps();
    }

    @Override
    public List<Map> getWithdrawalsOfAdmin(int admin_id) throws RemoteException {
        Utils.abrirBase();
        return Withdrawal.where("admin_id = ?", admin_id).toMaps();
    }

    @Override
    public List<Map> getWithdrawalsOnDate(String date) throws RemoteException {
        Utils.abrirBase();
        return Withdrawal.where("created_at >= ?", date).toMaps();
    }

    @Override
    public Double getWithdrawalsTotal() throws RemoteException {
        Utils.abrirBase();
        String date = new java.sql.Date(System.currentTimeMillis()).toString();
        List<Map> results = Base.findAll("SELECT SUM(amount) as total FROM tik.withdrawals WHERE created_at >= ?", date);
        System.out.println(results);
        if (!(results.get(0).get("total") == null)) {
            return (double) results.get(0).get("total");
        }
        return 0.00;
    }

    @Override
    public Double getAdminWithdrawalsTotal(int id) throws RemoteException {
        Utils.abrirBase();
        String date = new java.sql.Date(System.currentTimeMillis()).toString();
        List<Map> results = Base.findAll("SELECT SUM(amount) as total FROM tik.withdrawals WHERE created_at >= ? AND admin_id = ?", date, id);
        if (!(results.get(0).get("total") == null)) {
            return (double) results.get(0).get("total");
        }
        return 0.00;
    }

    @Override
    public boolean eraseWithdrawals() throws RemoteException {
        Utils.abrirBase();
        return Withdrawal.deleteAll() > 0;
    }

    @Override
    public List<Map> getWithdrawalsOfAdminOnTurn(int admin_id, String turn) throws RemoteException {
        Utils.abrirBase();
        return Withdrawal.where("admin_id = ? AND turn = ?", admin_id, turn).toMaps();
    }

    @Override
    public List<Map> getWithdrawalsOfAdminOnDate(int admin_id, String date) throws RemoteException {
        Utils.abrirBase();
        return Withdrawal.where("admin_id = ? AND date = ?", admin_id, date).toMaps();
    }

    @Override
    public List<Map> getWithdrawalsOfAdmin(int admin_id, String turn, String date) throws RemoteException {
        Utils.abrirBase();
        return Withdrawal.where("admin_id = ? AND turn = ? AND date = ?", admin_id, turn, date).toMaps();
    }

    @Override
    public List<Map> getWithdrawalsOnTurn(String turn) throws RemoteException {
        Utils.abrirBase();
        return Withdrawal.where("turn = ?", turn).toMaps();
    }

    @Override
    public List<Map> getWithdrawals(String date, String turn) throws RemoteException {
        Utils.abrirBase();
        return Withdrawal.where("date = ? AND turn = ?", date, turn).toMaps();
    }

    @Override
    public Double getWithdrawalsTotalOnDate(String date) throws RemoteException {
        Utils.abrirBase();
        List<Map> results = Base.findAll("SELECT SUM(amount) as total FROM tik.withdrawals WHERE created_at = ?", date);
        if (!(results.get(0).get("total") == null)) {
            return (double) results.get(0).get("total");
        }
        return 0.00;
    }

    @Override
    public Double getWithdrawalsTotalOnTurn(String turn) throws RemoteException {
        Utils.abrirBase();
        List<Map> results = Base.findAll("SELECT SUM(amount) as total FROM tik.withdrawals WHERE turn = ? ", turn);
        if (!(results.get(0).get("total") == null)) {
            return (double) results.get(0).get("total");
        }
        return 0.00;
    }

    @Override
    public Double getWithdrawalsTotal(String date, String turn) throws RemoteException {
        Utils.abrirBase();
        List<Map> results = Base.findAll("SELECT SUM(amount) as total FROM tik.withdrawals WHERE turn = ? AND created_at = ? ", turn, date);
        if (!(results.get(0).get("total") == null)) {
            return (double) results.get(0).get("total");
        }
        return 0.00;
    }

    @Override
    public Double getAdminWithdrawalsTotalOnDate(int admin_id, String date) throws RemoteException {
        Utils.abrirBase();
        List<Map> results = Base.findAll("SELECT SUM(amount) as total FROM tik.withdrawals WHERE created_at = ? AND admin_id = ?", date, admin_id);
        if (!(results.get(0).get("total") == null)) {
            return (double) results.get(0).get("total");
        }
        return 0.00;
    }

    @Override
    public Double getAdminWithdrawalsTotalOnTurn(int admin_id, String turn) throws RemoteException {
        Utils.abrirBase();
        List<Map> results = Base.findAll("SELECT SUM(amount) as total FROM tik.withdrawals WHERE turn = ? AND admin_id = ?", turn, admin_id);
        if (!(results.get(0).get("total") == null)) {
            return (double) results.get(0).get("total");
        }
        return 0.00;
    }

    @Override
    public Double getAdminWithdrawalsTotal(int admin_id, String date, String turn) throws RemoteException {
        Utils.abrirBase();
        List<Map> results = Base.findAll("SELECT SUM(amount) as total FROM tik.withdrawals WHERE created_at = ? AND turn = ? AND admin_id = ? ", date, turn, admin_id);
        if (!(results.get(0).get("total") == null)) {
            return (double) results.get(0).get("total");
        }
        return 0.00;
    }

}
