/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package implementsInterface.withdrawal;

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
import models.withdrawals.Withdrawal;
import org.javalite.activejdbc.Base;
import utils.Utils;

/**
 *
 * @author joako
 */
public class CRUDWithdrawal extends UnicastRemoteObject implements interfaces.withdrawals.InterfaceWithdrawal {
    private Connection conn;

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
    public float getWithdrawalsTotal() throws RemoteException {
        openBase();
        String sql = "SELECT SUM(amount) as amount FROM withdrawal;";
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
            Logger.getLogger(CRUDWithdrawal.class.getName()).log(Level.SEVERE, null, ex);
        }
        return ret;
    }

    @Override
    public float getAdminWithdrawalsTotal(int id) throws RemoteException {
        openBase();
        String sql = "SELECT SUM(amount) as amount FROM withdrawals WHERE admin_id = '"+ id +"';";
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
            Logger.getLogger(CRUDWithdrawal.class.getName()).log(Level.SEVERE, null, ex);
        }
        return ret;
    }

    @Override
    public boolean eraseWithdrawals() throws RemoteException {
        Utils.abrirBase();
        Base.openTransaction();
        boolean res = Withdrawal.deleteAll()>0;
        Base.commitTransaction();
        return res;
    }

    @Override
    public List<Map> getWithdrawalsOfAdminOnTurn(int admin_id, String turn) throws RemoteException {
        Utils.abrirBase();
        return Withdrawal.where("admin_id = ? AND turn = ?", admin_id, turn).toMaps();
    }

    @Override
    public List<Map> getWithdrawalsOfAdminOnDate(int admin_id, String date) throws RemoteException {
        Utils.abrirBase();
        return Withdrawal.where("admin_id = ? AND created_at >= ?", admin_id, date).toMaps();
    }

    @Override
    public List<Map> getWithdrawalsOfAdmin(int admin_id, String turn, String date) throws RemoteException {
        Utils.abrirBase();
        return Withdrawal.where("admin_id = ? AND turn = ? AND created_at >= ?", admin_id, turn, date).toMaps();
    }

    @Override
    public List<Map> getWithdrawalsOnTurn(String turn) throws RemoteException {
        Utils.abrirBase();
        return Withdrawal.where("turn = ?", turn).toMaps();
    }

    @Override
    public List<Map> getWithdrawals(String date, String turn) throws RemoteException {
        Utils.abrirBase();
        return Withdrawal.where("created_at >= ? AND turn = ?", date, turn).toMaps() ;
    }

    @Override
    public float getWithdrawalsTotalOnDate(String date) throws RemoteException {
        openBase();
        String sql = "SELECT SUM(amount) as amount FROM withdrawals WHERE  created_at >= '"+date+"';";
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
            Logger.getLogger(CRUDWithdrawal.class.getName()).log(Level.SEVERE, null, ex);
        }
        return ret;
    }

    @Override
    public float getWithdrawalsTotalOnTurn(String turn) throws RemoteException {
        openBase();
        String sql = "SELECT SUM(amount) as amount FROM withdrawals WHERE turn = '" + turn + "';";
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
            Logger.getLogger(CRUDWithdrawal.class.getName()).log(Level.SEVERE, null, ex);
        }
        return ret;
    }

    @Override
    public float getWithdrawalsTotal(String date, String turn) throws RemoteException {
        openBase();
        String sql = "SELECT SUM(amount) as amount FROM withdrawals WHERE turn = '" + turn + "' AND created_at >= '"+date+"';";
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
            Logger.getLogger(CRUDWithdrawal.class.getName()).log(Level.SEVERE, null, ex);
        }
        return ret;
    }

    @Override
    public float getAdminWithdrawalsTotalOnDate(int admin_id, String date) throws RemoteException {
        openBase();
        String sql = "SELECT SUM(amount) as amount FROM withdrawals WHERE created_at >= '"+date+"' AND admin_id = '"+ admin_id +"';";
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
            Logger.getLogger(CRUDWithdrawal.class.getName()).log(Level.SEVERE, null, ex);
        }
        return ret;
    }

    @Override
    public float getAdminWithdrawalsTotalOnTurn(int admin_id, String turn) throws RemoteException {
        openBase();
        String sql = "SELECT SUM(amount) as amount FROM withdrawals WHERE turn = '" + turn + "' AND admin_id = '"+ admin_id +"';";
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
            Logger.getLogger(CRUDWithdrawal.class.getName()).log(Level.SEVERE, null, ex);
        }
        return ret;
    }

    @Override
    public float getAdminWithdrawalsTotal(int admin_id, String date, String turn) throws RemoteException {
       openBase();
        String sql = "SELECT SUM(amount) as amount FROM withdrawals WHERE turn = '" + turn + "' AND created_at >= '"+date+"' AND admin_id = '"+ admin_id +"';";
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
            Logger.getLogger(CRUDWithdrawal.class.getName()).log(Level.SEVERE, null, ex);
        }
        return ret;
    }

     private void openBase() {
        try {
            try {
                Class.forName("com.mysql.jdbc.Driver");
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(CRUDWithdrawal.class.getName()).log(Level.SEVERE, null, ex);
            }
            if (conn == null || conn.isClosed()) {
                conn = DriverManager.getConnection("jdbc:mysql://localhost/tik", "root", "root");
            }
        } catch (SQLException ex) {
            Logger.getLogger(CRUDWithdrawal.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
}
