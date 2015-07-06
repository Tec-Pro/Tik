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
    public Map<String, Object> createWaiterDeposit(int waiter_id, Float amount) throws RemoteException {
        Utils.abrirBase();
        Base.openTransaction();
        CRUDTurn turn = new CRUDTurn();
        Deposit deposit = Deposit.createIt("waiter_id", waiter_id, "amount", amount, "turn", turn.getTurn());
        Base.commitTransaction();
        return deposit.toMap();
    }

    @Override
    public Map<String, Object> updateWaiterDeposit(int deposit_id, Float amount) throws RemoteException {
        Utils.abrirBase();
        Base.openTransaction();
        Deposit d = Deposit.findById(deposit_id);
        d.set("amount", amount).saveIt();
        Base.commitTransaction();
        return d.toMap();
    }

    @Override
    public boolean deleteWaiterDeposit(int deposit_id) throws RemoteException {
        Utils.abrirBase();
        Base.openTransaction();
        Deposit d = Deposit.findById(deposit_id);
        boolean res = d.delete();
        Base.commitTransaction();
        return res;
    }

    @Override
    public List<Map> getDepositsOfWaiter(int waiter_id) throws RemoteException {
        Utils.abrirBase();
        return Deposit.where("waiter_id= ?", waiter_id).toMaps();
    }

    @Override
    public List<Map> getWaitersDeposits() throws RemoteException {
        Utils.abrirBase();
        return Deposit.where("admin_id = ?", 0).toMaps();
    }

    @Override
    public Map<String, Object> createAdminDeposit(int admin_id, Float amount) throws RemoteException {
        Utils.abrirBase();
        Base.openTransaction();
        CRUDTurn turn = new CRUDTurn();
        Deposit deposit = Deposit.createIt("admin_id", admin_id, "amount", amount, "turn", turn.getTurn());
        Base.commitTransaction();
        return deposit.toMap();
    }

    @Override
    public Map<String, Object> updateAdminDeposit(int deposit_id, Float amount) throws RemoteException {
        Utils.abrirBase();
        Base.openTransaction();
        Deposit w = Deposit.findById(deposit_id);
        w.set("amount", amount).saveIt();
        Base.commitTransaction();
        return w.toMap();
    }

    @Override
    public boolean deleteAdminDeposit(int deposit_id) throws RemoteException {
        Utils.abrirBase();
        Base.openTransaction();
        Deposit w = Deposit.findById(deposit_id);
        boolean res = w.delete();
        Base.commitTransaction();
        return res;
    }

    @Override
    public List<Map> getDepositsOfAdmin(int admin_id) throws RemoteException {
        Utils.abrirBase();
        return Deposit.where("admin_id= ?", admin_id).toMaps();
    }

    @Override
    public List<Map> getAdminsDeposits() throws RemoteException {
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
    public Double getWaitersDepositsTotal() throws RemoteException {
        Utils.abrirBase();
        String date = new java.sql.Date(System.currentTimeMillis()).toString();
        List<Map> results = Base.findAll("SELECT SUM(amount) as total FROM tik.deposits WHERE created_at >= ? AND admin_id = 0", date);
        if (!(results.get(0).get("total") == null)) {
            return (double) results.get(0).get("total");
        }
        return 0.00;
    }

    @Override
    public Double getWaiterDepositsTotal(int id) throws RemoteException {
        Utils.abrirBase();
        String date = new java.sql.Date(System.currentTimeMillis()).toString();
        List<Map> results = Base.findAll("SELECT SUM(amount) as total FROM tik.deposits WHERE created_at >= ? AND waiter_id = ?", date, id);
        if (!(results.get(0).get("total") == null)) {
            return (double) results.get(0).get("total");
        }
        return 0.00;
    }

    @Override
    public Double getAdminsDepositsTotal() throws RemoteException {
        Utils.abrirBase();
        String date = new java.sql.Date(System.currentTimeMillis()).toString();
        List<Map> results = Base.findAll("SELECT SUM(amount) as total FROM tik.deposits WHERE created_at >= ? AND waiter_id = 0", date);
        if (!(results.get(0).get("total") == null)) {
            return (double) results.get(0).get("total");
        }
        return 0.00;
    }

    @Override
    public Double getAdminDepositsTotal(int id) throws RemoteException {
        Utils.abrirBase();
        String date = new java.sql.Date(System.currentTimeMillis()).toString();
        List<Map> results = Base.findAll("SELECT SUM(amount) as total FROM tik.deposits WHERE created_at >= ? AND admin_id = ?", date, id);
        if (!(results.get(0).get("total") == null)) {
            return (double) results.get(0).get("total");
        }
        return 0.00;
    }

    @Override
    public boolean eraseAdminDeposits() throws RemoteException {
        Utils.abrirBase();
        return Deposit.delete("waiter_id = ?", 0) > 0;
    }

    @Override
    public boolean eraseWaiterDeposits() throws RemoteException {
        Utils.abrirBase();
        return Deposit.delete("admin_id = ?", 0) > 0;
    }

    @Override
    public List<Map> getDepositsOfWaiterOnDate(int waiter_id, String date) throws RemoteException {
        Utils.abrirBase();
        return Deposit.where("waiter_id = ? AND created_at >= ?", waiter_id, date).toMaps();
    }

    @Override
    public List<Map> getDepositsOfWaiterOnTurn(int waiter_id, String turn) throws RemoteException {
        Utils.abrirBase();
        return Deposit.where("waiter_id = ? AND turn = ?", waiter_id, turn).toMaps();
    }

    @Override
    public List<Map> getDepositsOfWaiter(int waiter_id, String date, String turn) throws RemoteException {
        Utils.abrirBase();
        return Deposit.where("waiter_id = ? AND created_at >= ? AND turn = ?", waiter_id, date, turn).toMaps();
    }

    @Override
    public List<Map> getWaitersDepositsForTurn(String turn) throws RemoteException {
        Utils.abrirBase();
        return Deposit.where("admin_id = 0 AND turn = ?", turn).toMaps();
    }

    @Override
    public List<Map> getWaitersDeposits(String date, String turn) throws RemoteException {
        Utils.abrirBase();
        return Deposit.where("admin_id = 0 AND turn = ? AND created_at >= ?", turn, date).toMaps();
    }

    @Override
    public List<Map> getDepositsOfAdminOnDate(int admin_id, String date) throws RemoteException {
        Utils.abrirBase();
        return Deposit.where("admin_id = ? AND created_at >= ?", admin_id, date).toMaps();
    }

    @Override
    public List<Map> getDepositsOfAdminOnTurn(int admin_id, String turn) throws RemoteException {
        Utils.abrirBase();
        return Deposit.where("admin_id = ? AND turn = ?", admin_id, turn).toMaps();
    }

    @Override
    public List<Map> getDepositsOfAdmin(int admin_id, String date, String turn) throws RemoteException {
        Utils.abrirBase();
        return Deposit.where("admin_id = ? AND created_at = ? AND turn = ?", admin_id, date, turn).toMaps();
    }

    @Override
    public List<Map> getAdminsDepositsForTurn(String turn) throws RemoteException {
        Utils.abrirBase();
        return Deposit.where("turn = ? AND waiter_id = 0", turn).toMaps();
    }

    @Override
    public List<Map> getAdminsDeposits(String date, String turn) throws RemoteException {
        Utils.abrirBase();
        return Deposit.where("created_at = ? AND turn = ? AND waiter_id =0", date, turn).toMaps();
    }

    @Override
    public Double getWaitersDepositsTotal(String date, String turn) throws RemoteException {
        Utils.abrirBase();
        List<Map> results = Base.findAll("SELECT SUM(amount) as total FROM tik.deposits WHERE created_at >= ? AND turn = ? AND admin_id = 0", date, turn);
        if (!(results.get(0).get("total") == null)) {
            return (double) results.get(0).get("total");
        }
        return 0.00;
    }

    @Override
    public Double getWaitersDepositsTotalOnDate(String date) throws RemoteException {
        Utils.abrirBase();
        List<Map> results = Base.findAll("SELECT SUM(amount) as total FROM tik.deposits WHERE created_at >= ? AND admin_id = 0", date);
        if (!(results.get(0).get("total") == null)) {
            return (double) results.get(0).get("total");
        }
        return 0.00;
    }

    @Override
    public Double getWaitersDepositsTotalOnTurn(String turn) throws RemoteException {
        Utils.abrirBase();
        List<Map> results = Base.findAll("SELECT SUM(amount) as total FROM tik.deposits WHERE turn = ? AND admin_id = 0", turn);
        if (!(results.get(0).get("total") == null)) {
            return (double) results.get(0).get("total");
        }
        return 0.00;
    }

    @Override
    public Double getWaiterDepositsTotal(int id, String date, String turn) throws RemoteException {
        Utils.abrirBase();
        List<Map> results = Base.findAll("SELECT SUM(amount) as total FROM tik.deposits WHERE created_at >= ? AND turn = ? AND waiter_id = ?", date, turn, id);
        if (!(results.get(0).get("total") == null)) {
            return (double) results.get(0).get("total");
        }
        return 0.00;
    }

    @Override
    public Double getWaiterDepositsTotalOnDate(int id, String date) throws RemoteException {
        Utils.abrirBase();
        List<Map> results = Base.findAll("SELECT SUM(amount) as total FROM tik.deposits WHERE created_at >= ? AND waiter_id = ?", date, id);
        if (!(results.get(0).get("total") == null)) {
            return (double) results.get(0).get("total");
        }
        return 0.00;
    }

    @Override
    public Double getWaiterDepositsTotalOnTurn(int id, String turn) throws RemoteException {
        Utils.abrirBase();
        List<Map> results = Base.findAll("SELECT SUM(amount) as total FROM tik.deposits WHERE turn = ? AND waiter_id = ?", turn, id);
        if (!(results.get(0).get("total") == null)) {
            return (double) results.get(0).get("total");
        }
        return 0.00;
    }

    @Override
    public Double getAdminsDepositsTotal(String date, String turn) throws RemoteException {
        Utils.abrirBase();
        List<Map> results = Base.findAll("SELECT SUM(amount) as total FROM tik.deposits WHERE created_at >= ? AND turn = ? AND waiter_id = 0", date, turn);
        if (!(results.get(0).get("total") == null)) {
            return (double) results.get(0).get("total");
        }
        return 0.00;
    }

    @Override
    public Double getAdminsDepositsTotalOnDate(String date) throws RemoteException {
        Utils.abrirBase();
        List<Map> results = Base.findAll("SELECT SUM(amount) as total FROM tik.deposits WHERE created_at >= ? AND waiter_id = 0", date);
        if (!(results.get(0).get("total") == null)) {
            return (double) results.get(0).get("total");
        }
        return 0.00;
    }

    @Override
    public Double getAdminsDepositsTotalOnTurn(String turn) throws RemoteException {
        Utils.abrirBase();
        List<Map> results = Base.findAll("SELECT SUM(amount) as total FROM tik.deposits WHERE turn = ? AND waiter_id = 0", turn);
        if (!(results.get(0).get("total") == null)) {
            return (double) results.get(0).get("total");
        }
        return 0.00;
    }

    @Override
    public Double getAdminDepositsTotal(int id, String date, String turn) throws RemoteException {
        Utils.abrirBase();
        List<Map> results = Base.findAll("SELECT SUM(amount) as total FROM tik.deposits WHERE created_at >= ? AND turn = ? AND admin_id = ?", date, turn, id);
        if (!(results.get(0).get("total") == null)) {
            return (double) results.get(0).get("total");
        }
        return 0.00;
    }

    @Override
    public Double getAdminDepositsTotalOnDate(int id, String date) throws RemoteException {
        Utils.abrirBase();
        List<Map> results = Base.findAll("SELECT SUM(amount) as total FROM tik.deposits WHERE created_at >= ? AND admin_id = ?", date, id);
        if (!(results.get(0).get("total") == null)) {
            return (double) results.get(0).get("total");
        }
        return 0.00;
    }

    @Override
    public Double getAdminDepositsTotalOnTurn(int id, String turn) throws RemoteException {
        Utils.abrirBase();
        List<Map> results = Base.findAll("SELECT SUM(amount) as total FROM tik.deposits WHERE turn = ? AND admin_id = ?", turn, id);
        if (!(results.get(0).get("total") == null)) {
            return (double) results.get(0).get("total");
        }
        return 0.00;
    }
}
