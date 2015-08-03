/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package implementsInterface.deposits;

import implementsInterface.CRUDTurn;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import models.deposit.Deposit;
import models.deposit.Income;
import org.javalite.activejdbc.Base;
import utils.Utils;

/**
 *
 * @author joako
 */
public class CRUDDeposit extends UnicastRemoteObject implements interfaces.deposits.InterfaceDeposit {

    private Connection conn;

    public CRUDDeposit() throws RemoteException {
        super();
    }

    @Override
    public Map<String, Object> createIncome(int admin_id, Float amount) throws RemoteException {
        Utils.abrirBase();
        Base.openTransaction();
        CRUDTurn turn = new CRUDTurn();
        Income income = Income.createIt("admin_id", admin_id, "amount",amount,"turn",turn.getTurn());
        Base.commitTransaction();
        return income.toMap();
    }
    
    @Override
    public List<Map> getIncomes(String date, String turn) throws RemoteException{
        Utils.abrirBase();
        return Income.where("created_at >= ? and turn = ?", date,turn).toMaps();
    }
    
    /**
     * si turno es N retorna todos
     * @param date
     * @param turn
     * @return
     * @throws RemoteException 
     */
    @Override
    public float getIncomesTotal(String date, String turn) throws RemoteException {
        openBase();
        String sql;
        if(!"N".equals(turn))
             sql = "SELECT SUM(amount) as amount FROM incomes WHERE created_at >='"+date+"' AND turn = '"+turn+"';";
        else
             sql = "SELECT SUM(amount) as amount FROM incomes ;";

        float ret = 0;
        try {
            Statement stmt = conn.createStatement();
            java.sql.ResultSet rs = stmt.executeQuery(sql);
            if (rs.next()) {
                ret = rs.getFloat("amount");
                rs.close();
                conn.close();
            }
        } catch (SQLException ex) {
            Logger.getLogger(CRUDDeposit.class.getName()).log(Level.SEVERE, null, ex);
        }
        return ret;
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
    public float getWaitersDepositsTotal() throws RemoteException {
        openBase();
        String sql = "SELECT SUM(amount) as amount FROM deposits WHERE admin_id = '0';";
        float ret = 0;
        try {
            Statement stmt = conn.createStatement();
            java.sql.ResultSet rs = stmt.executeQuery(sql);
            if (rs.next()) {
                ret = rs.getFloat("amount");
                rs.close();
                conn.close();
            }
        } catch (SQLException ex) {
            Logger.getLogger(CRUDDeposit.class.getName()).log(Level.SEVERE, null, ex);
        }
        return ret;
    }

    @Override
    public float getWaiterDepositsTotal(int id) throws RemoteException {
        openBase();
        String sql = "SELECT SUM(amount) as amount FROM deposits WHERE waiter_id = '" + id + "';";
        float ret = 0;
        try {
            Statement stmt = conn.createStatement();
            java.sql.ResultSet rs = stmt.executeQuery(sql);
            if (rs.next()) {
                ret = rs.getFloat("amount");
                rs.close();
                conn.close();
            }
        } catch (SQLException ex) {
            Logger.getLogger(CRUDDeposit.class.getName()).log(Level.SEVERE, null, ex);
        }
        return ret;
    }

    @Override
    public float getAdminsDepositsTotal() throws RemoteException {
        openBase();
        String sql = "SELECT SUM(amount) as amount FROM deposits WHERE waiter_id = '0';";
        float ret = 0;
        try {
            Statement stmt = conn.createStatement();
            java.sql.ResultSet rs = stmt.executeQuery(sql);
            if (rs.next()) {
                ret = rs.getFloat("amount");
                rs.close();
                conn.close();
            }
        } catch (SQLException ex) {
            Logger.getLogger(CRUDDeposit.class.getName()).log(Level.SEVERE, null, ex);
        }
        return ret;
    }

    @Override
    public float getAdminDepositsTotal(int id) throws RemoteException {
        openBase();
        String sql = "SELECT SUM(amount) as amount FROM deposits WHERE admin_id = '" + id + "';";
        float ret = 0;
        try {
            Statement stmt = conn.createStatement();
            java.sql.ResultSet rs = stmt.executeQuery(sql);
            if (rs.next()) {
                ret = rs.getFloat("amount");
                rs.close();
                conn.close();
            }
        } catch (SQLException ex) {
            Logger.getLogger(CRUDDeposit.class.getName()).log(Level.SEVERE, null, ex);
        }
        return ret;
    }

    @Override
    public boolean deleteAdminDeposits() throws RemoteException {
        Utils.abrirBase();
        Base.openTransaction();
        boolean res = Deposit.delete("waiter_id = 0") > 0;
        Base.commitTransaction();
        return res;
    }

    @Override
    public boolean deleteWaiterDeposits() throws RemoteException {
        Utils.abrirBase();
        Base.openTransaction();
        boolean res = Deposit.delete("admin_id = 0") > 0;
        Base.commitTransaction();
        return res;
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
        return Deposit.where("admin_id = ? AND created_at >= ? AND turn = ?", admin_id, date, turn).toMaps();
    }

    @Override
    public List<Map> getAdminsDepositsForTurn(String turn) throws RemoteException {
        Utils.abrirBase();
        return Deposit.where("turn = ? AND waiter_id = 0", turn).toMaps();
    }

    @Override
    public List<Map> getAdminsDeposits(String date, String turn) throws RemoteException {
        Utils.abrirBase();
        return Deposit.where("created_at >= ? AND turn = ? AND waiter_id =0", date, turn).toMaps();
    }

    @Override
    public float getWaitersDepositsTotal(String date, String turn) throws RemoteException {
        openBase();
        String sql = "SELECT SUM(amount) as amount FROM deposits WHERE turn = '" + turn + "' AND created_at >= '" + date + "' AND admin_id = '0';";
        float ret = 0;
        try {
            Statement stmt = conn.createStatement();
            java.sql.ResultSet rs = stmt.executeQuery(sql);
            if (rs.next()) {
                ret = rs.getFloat("amount");
                rs.close();
                conn.close();
            }
        } catch (SQLException ex) {
            Logger.getLogger(CRUDDeposit.class.getName()).log(Level.SEVERE, null, ex);
        }
        return ret;
    }

    @Override
    public float getWaitersDepositsTotalOnDate(String date) throws RemoteException {
        openBase();
        String sql = "SELECT SUM(amount) as amount FROM deposits WHERE created_at >= '" + date + "' AND admin_id = '0';";
        float ret = 0;
        try {
            Statement stmt = conn.createStatement();
            java.sql.ResultSet rs = stmt.executeQuery(sql);
            if (rs.next()) {
                ret = rs.getFloat("amount");
                rs.close();
                conn.close();
            }
        } catch (SQLException ex) {
            Logger.getLogger(CRUDDeposit.class.getName()).log(Level.SEVERE, null, ex);
        }
        return ret;
    }

    @Override
    public float getWaitersDepositsTotalOnTurn(String turn) throws RemoteException {
        openBase();
        String sql = "SELECT SUM(amount) as amount FROM deposits WHERE turn = '" + turn + "' AND admin_id = '0';";
        float ret = 0;
        try {
            Statement stmt = conn.createStatement();
            java.sql.ResultSet rs = stmt.executeQuery(sql);
            if (rs.next()) {
                ret = rs.getFloat("amount");
                rs.close();
                conn.close();
            }
        } catch (SQLException ex) {
            Logger.getLogger(CRUDDeposit.class.getName()).log(Level.SEVERE, null, ex);
        }
        return ret;
    }

    @Override
    public float getWaiterDepositsTotal(int id, String date, String turn) throws RemoteException {
        openBase();
        String sql = "SELECT SUM(amount) as amount FROM deposits WHERE turn = '" + turn + "' AND created_at >= '" + date + "' AND waiter_id = '" + id + "';";
        float ret = 0;
        try {
            Statement stmt = conn.createStatement();
            java.sql.ResultSet rs = stmt.executeQuery(sql);
            if (rs.next()) {
                ret = rs.getFloat("amount");
                rs.close();
                conn.close();
            }
        } catch (SQLException ex) {
            Logger.getLogger(CRUDDeposit.class.getName()).log(Level.SEVERE, null, ex);
        }
        return ret;
    }

    @Override
    public float getWaiterDepositsTotalOnDate(int id, String date) throws RemoteException {
        openBase();
        String sql = "SELECT SUM(amount) as amount FROM deposits WHERE created_at >= '" + date + "' AND waiter_id = '" + id + "';";
        float ret = 0;
        try {
            Statement stmt = conn.createStatement();
            java.sql.ResultSet rs = stmt.executeQuery(sql);
            if (rs.next()) {
                ret = rs.getFloat("amount");
                rs.close();
                conn.close();
            }
        } catch (SQLException ex) {
            Logger.getLogger(CRUDDeposit.class.getName()).log(Level.SEVERE, null, ex);
        }
        return ret;
    }

    @Override
    public float getWaiterDepositsTotalOnTurn(int id, String turn) throws RemoteException {
        openBase();
        String sql = "SELECT SUM(amount) as amount FROM deposits WHERE turn = '" + turn + "' AND waiter_id = '" + id + "';";
        float ret = 0;
        try {
            Statement stmt = conn.createStatement();
            java.sql.ResultSet rs = stmt.executeQuery(sql);
            if (rs.next()) {
                ret = rs.getFloat("amount");
                rs.close();
                conn.close();
            }
        } catch (SQLException ex) {
            Logger.getLogger(CRUDDeposit.class.getName()).log(Level.SEVERE, null, ex);
        }
        return ret;
    }

    @Override
    public float getAdminsDepositsTotal(String date, String turn) throws RemoteException {
        openBase();
        String sql = "SELECT SUM(amount) as amount FROM deposits WHERE turn = '" + turn + "' AND created_at >= '" + date + "' AND waiter_id = '0';";
        float ret = 0;
        try {
            Statement stmt = conn.createStatement();
            java.sql.ResultSet rs = stmt.executeQuery(sql);
            if (rs.next()) {
                ret = rs.getFloat("amount");
                rs.close();
                conn.close();
            }
        } catch (SQLException ex) {
            Logger.getLogger(CRUDDeposit.class.getName()).log(Level.SEVERE, null, ex);
        }
        return ret;
    }

    @Override
    public float getAdminsDepositsTotalOnDate(String date) throws RemoteException {
        openBase();
        String sql = "SELECT SUM(amount) as amount FROM deposits WHERE created_at >= '" + date + "' AND waiter_id = '0';";
        float ret = 0;
        try {
            Statement stmt = conn.createStatement();
            java.sql.ResultSet rs = stmt.executeQuery(sql);
            if (rs.next()) {
                ret = rs.getFloat("amount");
                rs.close();
                conn.close();
            }
        } catch (SQLException ex) {
            Logger.getLogger(CRUDDeposit.class.getName()).log(Level.SEVERE, null, ex);
        }
        return ret;
    }

    @Override
    public float getAdminsDepositsTotalOnTurn(String turn) throws RemoteException {
        openBase();
        String sql = "SELECT SUM(amount) as amount FROM deposits WHERE turn = '" + turn + "' AND waiter_id = '0';";
        float ret = 0;
        try {
            Statement stmt = conn.createStatement();
            java.sql.ResultSet rs = stmt.executeQuery(sql);
            if (rs.next()) {
                ret = rs.getFloat("amount");
                rs.close();
                conn.close();
            }
        } catch (SQLException ex) {
            Logger.getLogger(CRUDDeposit.class.getName()).log(Level.SEVERE, null, ex);
        }
        return ret;
    }

    @Override
    public float getAdminDepositsTotal(int id, String date, String turn) throws RemoteException {
        openBase();
        String sql = "SELECT SUM(amount) as amount FROM deposits WHERE turn = '" + turn + "' AND created_at >= '" + date + "' AND admin_id = '" + id + "';";
        float ret = 0;
        try {
            Statement stmt = conn.createStatement();
            java.sql.ResultSet rs = stmt.executeQuery(sql);
            if (rs.next()) {
                ret = rs.getFloat("amount");
                rs.close();
                conn.close();
            }
        } catch (SQLException ex) {
            Logger.getLogger(CRUDDeposit.class.getName()).log(Level.SEVERE, null, ex);
        }
        return ret;
    }

    @Override
    public float getAdminDepositsTotalOnDate(int id, String date) throws RemoteException {
        openBase();
        String sql = "SELECT SUM(amount) as amount FROM deposits WHERE created_at >= '" + date + "' AND admin_id = '" + id + "';";
        float ret = 0;
        try {
            Statement stmt = conn.createStatement();
            java.sql.ResultSet rs = stmt.executeQuery(sql);
            if (rs.next()) {
                ret = rs.getFloat("amount");
                rs.close();
                conn.close();
            }
        } catch (SQLException ex) {
            Logger.getLogger(CRUDDeposit.class.getName()).log(Level.SEVERE, null, ex);
        }
        return ret;
    }

    @Override
    public float getAdminDepositsTotalOnTurn(int id, String turn) throws RemoteException {
        openBase();
        String sql = "SELECT SUM(amount) as amount FROM deposits WHERE turn = '" + turn + "' AND admin_id = '" + id + "';";
        float ret = 0;
        try {
            Statement stmt = conn.createStatement();
            java.sql.ResultSet rs = stmt.executeQuery(sql);
            if (rs.next()) {
                ret = rs.getFloat("amount");
                rs.close();
                conn.close();
            }
        } catch (SQLException ex) {
            Logger.getLogger(CRUDDeposit.class.getName()).log(Level.SEVERE, null, ex);
        }
        return ret;
    }

    private void openBase() {
        try {
            try {
                Class.forName("com.mysql.jdbc.Driver");
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(CRUDDeposit.class.getName()).log(Level.SEVERE, null, ex);
            }
            if (conn == null || conn.isClosed()) {
                conn = DriverManager.getConnection("jdbc:mysql://localhost/tik", "root", "root");
            }
        } catch (SQLException ex) {
            Logger.getLogger(CRUDDeposit.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    @Override
    public boolean deleteIncomes() throws RemoteException {
        Utils.abrirBase();
        Base.openTransaction();
        boolean res = Income.deleteAll()> 0;
        Base.commitTransaction();
        return res;
    }
}
