/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package implementsInterface.box.expenses;

import implementsInterface.statistics.CRUDStatistics;
import interfaces.cashbox.expenses.InterfaceExpenses;
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
import models.cashbox.expenses.Expense;
import org.javalite.activejdbc.Base;
import utils.Utils;

/**
 *
 * @author jacinto
 */
public class CRUDExpenses extends UnicastRemoteObject implements InterfaceExpenses {

    private Connection conn;

    public CRUDExpenses() throws RemoteException {
        super();
    }

    @Override
    public boolean createExpense(int type, String detail, float amount, int purchase_id, int provider_id, String turn) throws RemoteException {
        Utils.abrirBase();
        Base.openTransaction();
        Expense expense = null;
        switch (type) {
            case 2:// compra
                expense = Expense.createIt(
                        "type", "COMPRA",
                        "detail", detail,
                        "amount", amount,
                        "provider_id", provider_id,
                        "purchase_id", purchase_id,
                        "turn", turn
                );
                break;
            case 1: //pago a proveedor
                expense = Expense.createIt(
                        "type", "PAGO A PROVEEDOR",
                        "detail", detail,
                        "amount", amount,
                        "provider_id", provider_id,
                        "turn", turn
                );
                break;
            case 3:
                expense = Expense.createIt(
                        "type", "OTROS GASTOS",
                        "detail", detail,
                        "amount", amount,
                        "turn", turn
                );
                break;
        }
        Base.commitTransaction();
        return (expense != null);
    }

    @Override
    public List<Map> getExpenses(String turn) throws RemoteException {
        Utils.abrirBase();
        List<Map> expenses = Expense.where("turn = ?", turn).toMaps();
        return expenses;
    }

    @Override
    public float getSumExpenses(String turn) throws RemoteException {
        openBase();
        String sql = "SELECT SUM(amount) as amount FROM expenses WHERE turn = '" + turn + "';";
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
            Logger.getLogger(CRUDExpenses.class.getName()).log(Level.SEVERE, null, ex);
        }
        return ret;
    }

    private void openBase() {
        try {
            try {
                Class.forName("com.mysql.jdbc.Driver");
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(CRUDStatistics.class.getName()).log(Level.SEVERE, null, ex);
            }
            if (conn == null || conn.isClosed()) {
                conn = DriverManager.getConnection("jdbc:mysql://localhost/tik", "root", "root");
            }
        } catch (SQLException ex) {
            Logger.getLogger(CRUDStatistics.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
}
