/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package implementsInterface.box.expenses;

import interfaces.cashbox.expenses.InterfaceExpenses;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;
import java.util.Map;
import models.cashbox.expenses.Expense;
import org.javalite.activejdbc.Base;
import utils.Utils;

/**
 *
 * @author jacinto
 */
public class CRUDExpenses extends UnicastRemoteObject implements InterfaceExpenses {

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

}
