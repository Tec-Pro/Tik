/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers.cashbox;

import gui.cashbox.GuiSummaryCashbox;
import interfaces.InterfaceAdmin;
import interfaces.InterfaceTurn;
import interfaces.InterfaceUser;
import interfaces.cashbox.InterfaceCashbox;
import interfaces.cashbox.expenses.InterfaceExpenses;
import interfaces.deposits.InterfaceDeposit;
import interfaces.withdrawals.InterfaceWithdrawal;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import utils.InterfaceName;
import utils.ParserFloat;

/**
 *
 * @author Alan
 */
public class ControllerGuiSummaryCashbox {

    private GuiSummaryCashbox guiSummaryCashbox;
    private static InterfaceCashbox cashbox;
    private static InterfaceExpenses expenses;
    private static InterfaceWithdrawal withdrawal;
    private static InterfaceDeposit deposit;
    private static InterfaceAdmin admin;
    private static InterfaceUser user;

    public ControllerGuiSummaryCashbox(GuiSummaryCashbox guiSC) throws RemoteException, NotBoundException {
        guiSummaryCashbox = guiSC;
        withdrawal = (InterfaceWithdrawal) InterfaceName.registry.lookup(InterfaceName.CRUDWithdrawal);
        deposit = (InterfaceDeposit) InterfaceName.registry.lookup(InterfaceName.CRUDDeposit);
        admin = (InterfaceAdmin) InterfaceName.registry.lookup(InterfaceName.CRUDAdmin);
        user = (InterfaceUser) InterfaceName.registry.lookup(InterfaceName.CRUDUser);
        cashbox = (InterfaceCashbox) InterfaceName.registry.lookup(InterfaceName.CRUDCashbox);
        expenses = (InterfaceExpenses) InterfaceName.registry.lookup(InterfaceName.CRUDExpenses);
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

    private Float loadBalance() throws RemoteException {
        Float initialBalance = loadInitialBalance();
        Float adminDeposits = loadAdminDeposits();
        Float waiterDeposits = loadWaiterDeposits();
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
    private Float loadWaiterDeposits() throws RemoteException {
        String date = new java.sql.Date(System.currentTimeMillis()).toString();
        Float waiterDeposits = deposit.getWaitersDepositsTotal(date, "M") + deposit.getWaitersDepositsTotal(date, "T");
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

    public void loadData() throws RemoteException{
        loadBalance();
        loadExpenses();
        loadInitialBalance();
    }
    
}
