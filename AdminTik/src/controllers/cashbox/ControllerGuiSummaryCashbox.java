/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers.cashbox;

import controllers.ControllerMain;
import static controllers.cashbox.ControllerGUICashbox.gui;
import gui.cashbox.GuiSummaryCashbox;
import gui.cashbox.GuiSummaryCashboxForDate;
import interfaces.InterfaceAdmin;
import interfaces.InterfaceTurn;
import interfaces.InterfaceUser;
import interfaces.cashbox.InterfaceCashbox;
import interfaces.cashbox.expenses.InterfaceExpenses;
import interfaces.deposits.InterfaceDeposit;
import interfaces.withdrawals.InterfaceWithdrawal;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.List;
import java.util.Map;
import org.apache.poi.hpsf.SummaryInformation;
import utils.InterfaceName;
import utils.ParserFloat;

/**
 *
 * @author Alan
 */
public class ControllerGuiSummaryCashbox implements ActionListener{

    public static GuiSummaryCashbox guiSummaryCashbox;
    public static GuiSummaryCashboxForDate guiSummaryCashboxForDate;
    private static InterfaceCashbox cashbox;
    private static InterfaceExpenses expenses;
    public static InterfaceWithdrawal withdrawal;
    public static InterfaceDeposit deposit;
    public static InterfaceAdmin admin;
    private static InterfaceUser user;

    public ControllerGuiSummaryCashbox(GuiSummaryCashbox guiSC) throws RemoteException, NotBoundException {
        guiSummaryCashbox = guiSC;
        withdrawal = (InterfaceWithdrawal) InterfaceName.registry.lookup(InterfaceName.CRUDWithdrawal);
        deposit = (InterfaceDeposit) InterfaceName.registry.lookup(InterfaceName.CRUDDeposit);
        admin = (InterfaceAdmin) InterfaceName.registry.lookup(InterfaceName.CRUDAdmin);
        user = (InterfaceUser) InterfaceName.registry.lookup(InterfaceName.CRUDUser);
        cashbox = (InterfaceCashbox) InterfaceName.registry.lookup(InterfaceName.CRUDCashbox);
        expenses = (InterfaceExpenses) InterfaceName.registry.lookup(InterfaceName.CRUDExpenses);
        guiSummaryCashbox.setActionListner(this);
        guiSummaryCashboxForDate = new GuiSummaryCashboxForDate(ControllerMain.guiMain, true);
    }

    /*
     Método que carga el saldo inicial de la caja.
     */
    private Float loadInitialBalance() throws RemoteException {
        Float initialBalance = cashbox.getPastBalance();
        guiSummaryCashbox.getTxtInitialBalance().setText(ParserFloat.floatToString(initialBalance));
        return initialBalance;
    }

    private Float loadExpenses() throws RemoteException {
        Float exp = expenses.getSumExpenses("M") + expenses.getSumExpenses("T");
        guiSummaryCashbox.getTxtExpenses().setText(ParserFloat.floatToString(exp));
        return exp;
    }

    private static Float loadCashboxIncome() throws RemoteException {
        String date = new java.sql.Date(System.currentTimeMillis()).toString();
        Float incomes = deposit.getIncomesTotal(date, "M") + deposit.getIncomesTotal(date, "T");
        guiSummaryCashbox.getTxtCashboxIncome().setText(ParserFloat.floatToString(incomes));
        return incomes;
    }
    
    private Float loadBalance() throws RemoteException {
        Float initialBalance = loadInitialBalance();
        Float adminDeposits = loadAdminDeposits();
        Float waiterDeposits = loadWaiterTotalDeposits();
        Float withdrawals = loadWithdrawals();
        Float exp = loadExpenses();
        Float balance = initialBalance + adminDeposits + waiterDeposits - withdrawals - exp;
        guiSummaryCashbox.getTxtFinalBalance().setText(ParserFloat.floatToString(balance));
        return balance;
    }

    private Float loadAdminDeposits() throws RemoteException {
        String date = new java.sql.Date(System.currentTimeMillis()).toString();
        Float adminDeposits = deposit.getAdminsDepositsTotal(date, "M") + deposit.getAdminsDepositsTotal(date, "T");
        return adminDeposits;
    }

    /*
     Método que carga las entregas de mozo en la caja existente.
     */
    private Float loadWaiterMorningDeposits() throws RemoteException {
        String date = new java.sql.Date(System.currentTimeMillis()).toString();
        Float waiterDeposits = deposit.getWaitersDepositsTotal(date, "M");
        guiSummaryCashbox.getTxtEarningsMorning().setText(ParserFloat.floatToString(waiterDeposits));
        return waiterDeposits;
    }
    
    /*
     Método que carga las entregas de mozo en la caja existente.
     */
    private Float loadWaiterAfternoonDeposits() throws RemoteException {
        String date = new java.sql.Date(System.currentTimeMillis()).toString();
        Float waiterDeposits = deposit.getWaitersDepositsTotal(date, "T");
        guiSummaryCashbox.getTxtEarningAfternoon().setText(ParserFloat.floatToString(waiterDeposits));
        return waiterDeposits;
    }
    
    /*
     Método que carga las entregas de mozo en la caja existente.
     */
    private Float loadWaiterTotalDeposits() throws RemoteException {
        String date = new java.sql.Date(System.currentTimeMillis()).toString();
        Float waiterDeposits = deposit.getWaitersDepositsTotal(date, "M") + deposit.getWaitersDepositsTotal(date, "T");
        guiSummaryCashbox.getTxtEarnings().setText(ParserFloat.floatToString(waiterDeposits));
        return waiterDeposits;
    }
    /*
     Método que carga los retiros en la caja existente.
     */

    private Float loadWithdrawals() throws RemoteException {
        String date = new java.sql.Date(System.currentTimeMillis()).toString();
        Float withdrawals = withdrawal.getWithdrawalsTotal(date, "M") + withdrawal.getWithdrawalsTotal(date, "T");
        return withdrawals;
    }

    public void loadData() throws RemoteException {
        loadBalance();
        loadExpenses();
        loadInitialBalance();
        loadWaiterMorningDeposits();
        loadWaiterAfternoonDeposits();
        loadWaiterTotalDeposits();
        loadCashboxIncome();
    }

    public static void loadTableOfAdmins() throws RemoteException {
        guiSummaryCashbox.getTableSummaryDefault().setRowCount(0);
        List<Map> admins = admin.getAdmins();
        float totalD = 0;
        float totalW = 0;
        float total = 0;
        for (Map a : admins) {
            float d = deposit.getAdminDepositsTotal((int) a.get("id"));
            totalD = totalD + d;
            float w = withdrawal.getAdminWithdrawalsTotal((int) a.get("id"));
            totalW = totalW + w;
            float t = d - w;
            total = total + t;
            Object[] row = new Object[4];
            row[0] = a.get("name");
            row[1] = d;
            row[2] = w;
            row[3] = t;
            guiSummaryCashbox.getTableSummaryDefault().addRow(row);
        }
        Object[] row = new Object[4];
        row[0] = "Total";
        row[1] = totalD;
        row[2] = totalW;
        row[3] = total;
        guiSummaryCashbox.getTableSummaryDefault().addRow(row);
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        if(ae.getSource().equals(guiSummaryCashbox.getBtnOtherResume())){
            guiSummaryCashboxForDate.setVisible(true);
            guiSummaryCashboxForDate.setLocationRelativeTo(null);
        }
    }

}
