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
import interfaces.resume.InterfaceResume;
import interfaces.withdrawals.InterfaceWithdrawal;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.poi.hpsf.SummaryInformation;
import utils.InterfaceName;
import utils.ParserFloat;
import utils.Triple;

/**
 *
 * @author Alan
 */
public class ControllerGuiSummaryCashbox implements ActionListener {

    public static GuiSummaryCashbox guiSummaryCashbox;
    public static GuiSummaryCashboxForDate guiSummaryCashboxForDate;
    private static InterfaceCashbox cashbox;
    private static InterfaceExpenses expenses;
    public static InterfaceWithdrawal withdrawal;
    public static InterfaceDeposit deposit;
    public static InterfaceAdmin admin;
    private static InterfaceUser user;
    private static InterfaceResume resume;

    float totalD = 0;
    float totalW = 0;
    float total = 0;

    public ControllerGuiSummaryCashbox(GuiSummaryCashbox guiSC) throws RemoteException, NotBoundException {
        guiSummaryCashbox = guiSC;
        withdrawal = (InterfaceWithdrawal) InterfaceName.registry.lookup(InterfaceName.CRUDWithdrawal);
        deposit = (InterfaceDeposit) InterfaceName.registry.lookup(InterfaceName.CRUDDeposit);
        admin = (InterfaceAdmin) InterfaceName.registry.lookup(InterfaceName.CRUDAdmin);
        user = (InterfaceUser) InterfaceName.registry.lookup(InterfaceName.CRUDUser);
        cashbox = (InterfaceCashbox) InterfaceName.registry.lookup(InterfaceName.CRUDCashbox);
        expenses = (InterfaceExpenses) InterfaceName.registry.lookup(InterfaceName.CRUDExpenses);
        resume = (InterfaceResume) InterfaceName.registry.lookup(InterfaceName.CRUDResume);
        guiSummaryCashbox.setActionListner(this);
        guiSummaryCashboxForDate = new GuiSummaryCashboxForDate(ControllerMain.guiMain, true);
        guiSummaryCashboxForDate.setActionListener(this);

        guiSummaryCashboxForDate.getDateSince().addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            @Override
            public void propertyChange(PropertyChangeEvent evt) {
                try {
                    if (guiSummaryCashboxForDate.getDateUntil().getDate() != null) {
                        if (guiSummaryCashboxForDate.getCheckDaily().isSelected()) {
                            loadResumeForDate(resume.getResumeDaily(dateToMySQLDate(guiSummaryCashboxForDate.getDateSince().getCalendar().getTime(), false),
                                    dateToMySQLDate(guiSummaryCashboxForDate.getDateUntil().getCalendar().getTime(), false)));
                        } else {
                            if (guiSummaryCashboxForDate.getCheckMonthly().isSelected()) {
                                loadResumeForDate(resume.getResumeMonthly(dateToMySQLDate(guiSummaryCashboxForDate.getDateSince().getCalendar().getTime(), false),
                                        dateToMySQLDate(guiSummaryCashboxForDate.getDateUntil().getCalendar().getTime(), false)));
                            } else {
                                if (guiSummaryCashboxForDate.getCheckAnnual().isSelected()) {
                                    loadResumeForDate(resume.getResumeAnnual(dateToMySQLDate(guiSummaryCashboxForDate.getDateSince().getCalendar().getTime(), false),
                                            dateToMySQLDate(guiSummaryCashboxForDate.getDateUntil().getCalendar().getTime(), false)));
                                } else {
                                    if (guiSummaryCashboxForDate.getCheckAll().isSelected()) {
                                        loadResumeForDate(resume.getResumeAll(dateToMySQLDate(guiSummaryCashboxForDate.getDateSince().getCalendar().getTime(), false),
                                                dateToMySQLDate(guiSummaryCashboxForDate.getDateUntil().getCalendar().getTime(), false)));
                                    }
                                }
                            }
                        }
                    }
                } catch (RemoteException ex) {
                    Logger.getLogger(ControllerGuiSummaryCashbox.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });

        guiSummaryCashboxForDate.getDateUntil().addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            @Override
            public void propertyChange(PropertyChangeEvent evt) {
                try {
                    if (guiSummaryCashboxForDate.getDateUntil().getDate() != null) {
                        if (guiSummaryCashboxForDate.getCheckDaily().isSelected()) {
                            loadResumeForDate(resume.getResumeDaily(dateToMySQLDate(guiSummaryCashboxForDate.getDateSince().getCalendar().getTime(), false),
                                    dateToMySQLDate(guiSummaryCashboxForDate.getDateUntil().getCalendar().getTime(), false)));
                        } else {
                            if (guiSummaryCashboxForDate.getCheckMonthly().isSelected()) {
                                loadResumeForDate(resume.getResumeMonthly(dateToMySQLDate(guiSummaryCashboxForDate.getDateSince().getCalendar().getTime(), false),
                                        dateToMySQLDate(guiSummaryCashboxForDate.getDateUntil().getCalendar().getTime(), false)));
                            } else {
                                if (guiSummaryCashboxForDate.getCheckAnnual().isSelected()) {
                                    loadResumeForDate(resume.getResumeAnnual(dateToMySQLDate(guiSummaryCashboxForDate.getDateSince().getCalendar().getTime(), false),
                                            dateToMySQLDate(guiSummaryCashboxForDate.getDateUntil().getCalendar().getTime(), false)));
                                } else {
                                    if (guiSummaryCashboxForDate.getCheckAll().isSelected()) {
                                        loadResumeForDate(resume.getResumeAll(dateToMySQLDate(guiSummaryCashboxForDate.getDateSince().getCalendar().getTime(), false),
                                                dateToMySQLDate(guiSummaryCashboxForDate.getDateUntil().getCalendar().getTime(), false)));
                                    }
                                }
                            }
                        }
                    }
                } catch (RemoteException ex) {
                    Logger.getLogger(ControllerGuiSummaryCashbox.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
    }

    public void loadResumeForDate(List<Map> listResume) throws RemoteException {
        guiSummaryCashboxForDate.getTableResumeDefault().setRowCount(0);
        guiSummaryCashboxForDate.getTableResumeForAdminDefault().setRowCount(0);
        totalD = 0;
        totalW = 0;
        total = 0;
        for (Map r : listResume) {
            if (r.get("resume_date") != null) {
                Object[] rr = new Object[7];
                rr[0] = dateToMySQLDate((Date) r.get("resume_date"), true);
                rr[1] = r.get("income");
                rr[2] = r.get("earning_m");
                rr[3] = r.get("earning_a");
                rr[4] = r.get("earning");
                rr[5] = r.get("expenses");
                rr[6] = r.get("final_balance");
                guiSummaryCashboxForDate.getTableResumeDefault().addRow(rr);
                List<Map> listAdminResume = resume.getAdminResume((int) r.get("id"));

                for (Map m : listAdminResume) {
                    Object[] ra = new Object[5];
                    ra[0] = dateToMySQLDate((Date) r.get("resume_date"), true);
                    ra[1] = m.get("admin");
                    float d = (float) m.get("deposit");
                    totalD = totalD + d;
                    ra[2] = d;
                    float w = (float) m.get("withdrawal");
                    totalW = totalW + w;
                    total = total + (d - w);
                    ra[3] = w;
                    ra[4] = d - w;
                    guiSummaryCashboxForDate.getTableResumeForAdminDefault().addRow(ra);
                }
            }

        }
        Object[] ra = new Object[5];
        ra[1] = "Total";
        ra[2] = totalD;
        ra[3] = totalW;
        ra[4] = total;
        guiSummaryCashboxForDate.getTableResumeForAdminDefault().addRow(ra);
    }

    /*paraMostrar == true: retorna la fecha en formato dd/mm/yyyy (formato pantalla)
     * paraMostrar == false: retorna la fecha en formato yyyy/mm/dd (formato SQL)
     */
    public String dateToMySQLDate(Date fecha, boolean paraMostrar) {
        if (paraMostrar) {
            java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("dd/MM/yyyy");
            return sdf.format(fecha);
        } else {
            java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd");
            return sdf.format(fecha);
        }
    }
    /*
     Método que carga el saldo inicial de la caja.
     */

    private static Float loadInitialBalance() throws RemoteException {
        Float initialBalance;
        if (cashbox.getLast().get("turn").equals("T")) {//si es domingo y ya cerré el turno, tengo que traer el turno del sabado
            initialBalance = (float) cashbox.getLast("TT").get("balance"); // obtengo el balance del ultimo turno abierto
        } else {
            initialBalance = (float) cashbox.getLast("T").get("balance");
        }
        guiSummaryCashbox.getTxtInitialBalance().setText(ParserFloat.floatToString(initialBalance));
        return initialBalance;
    }

    private static Float loadExpenses() throws RemoteException {
        Float exp = expenses.getSumExpenses("N"); // con N traigo todos los gastos
        guiSummaryCashbox.getTxtExpenses().setText(ParserFloat.floatToString(exp));
        return exp;
    }

    private static Float loadCashboxIncome() throws RemoteException {
        String date = new java.sql.Date(System.currentTimeMillis()).toString();
        Float incomes = deposit.getIncomesTotal(date, "N");
        guiSummaryCashbox.getTxtCashboxIncome().setText(ParserFloat.floatToString(incomes));
        return incomes;
    }

    private static Float loadBalance() throws RemoteException {
        Float initialBalance = loadInitialBalance();
        Float adminDeposits = loadAdminDeposits();
        Float waiterDeposits = loadWaiterTotalDeposits();
        Float withdrawals = loadWithdrawals();
        Float exp = loadExpenses();
        Float balance = initialBalance + adminDeposits + waiterDeposits - withdrawals - exp;
        guiSummaryCashbox.getTxtFinalBalance().setText(ParserFloat.floatToString(balance));
        return balance;
    }

    private static Float loadAdminDeposits() throws RemoteException {
        String date = new java.sql.Date(System.currentTimeMillis()).toString();
        Float adminDeposits = deposit.getAdminsDepositsTotal();
        return adminDeposits;
    }

    /*
     Método que carga las entregas de mozo en la caja existente.
     */
    private static Float loadWaiterMorningDeposits() throws RemoteException {
        Float waiterDeposits = deposit.getWaitersDepositsTotalOnTurn("M");
        guiSummaryCashbox.getTxtEarningsMorning().setText(ParserFloat.floatToString(waiterDeposits));
        return waiterDeposits;
    }

    /*
     Método que carga las entregas de mozo en la caja existente.
     */
    private static Float loadWaiterAfternoonDeposits() throws RemoteException {
        Float waiterDeposits = deposit.getWaitersDepositsTotalOnTurn("T");
        guiSummaryCashbox.getTxtEarningAfternoon().setText(ParserFloat.floatToString(waiterDeposits));
        return waiterDeposits;
    }

    /*
     Método que carga las entregas de mozo en la caja existente.
     */
    private static Float loadWaiterTotalDeposits() throws RemoteException {
        Float waiterDeposits = deposit.getWaitersDepositsTotalOnTurn("M") + deposit.getWaitersDepositsTotalOnTurn("T");
        guiSummaryCashbox.getTxtEarnings().setText(ParserFloat.floatToString(waiterDeposits));
        return waiterDeposits;
    }
    /*
     Método que carga los retiros en la caja existente.
     */

    private static Float loadWithdrawals() throws RemoteException {
        String date = new java.sql.Date(System.currentTimeMillis()).toString();
        Float withdrawals = withdrawal.getWithdrawalsTotalOnTurn("M") + withdrawal.getWithdrawalsTotalOnTurn("T");
        return withdrawals;
    }

    public static void loadData() throws RemoteException {
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

    public static void saveResume() throws RemoteException {
        List<Triple> admins = new LinkedList<Triple>();
        List<Map> list = admin.getAdmins();
        for (Map a : list) {
            String n = (String) a.get("name");
            float d = deposit.getAdminDepositsTotal((int) a.get("id"));
            float w = withdrawal.getAdminWithdrawalsTotal((int) a.get("id"));
            admins.add(new Triple(n, d, w));
        }
        resume.create(loadCashboxIncome(), loadWaiterTotalDeposits(), loadExpenses(), loadBalance(), Calendar.getInstance().getTime(), admins, deposit.getWaitersDepositsTotalOnTurn("M"), deposit.getWaitersDepositsTotalOnTurn("T"));
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        if (ae.getSource().equals(guiSummaryCashbox.getBtnOtherResume())) {
            guiSummaryCashboxForDate.getDateSince().setDate(Calendar.getInstance().getTime());
            guiSummaryCashboxForDate.getDateUntil().setDate(Calendar.getInstance().getTime());
            guiSummaryCashboxForDate.setVisible(true);
            guiSummaryCashboxForDate.setLocationRelativeTo(null);
        }
        if (ae.getSource().equals(guiSummaryCashboxForDate.getCheckDaily())) {
            guiSummaryCashboxForDate.getCheckMonthly().setSelected(false);
            guiSummaryCashboxForDate.getCheckAnnual().setSelected(false);
            guiSummaryCashboxForDate.getCheckAll().setSelected(false);
            try {
                if (guiSummaryCashboxForDate.getDateUntil().getDate() != null
                        && guiSummaryCashboxForDate.getDateSince().getDate() != null) {
                    loadResumeForDate(resume.getResumeDaily(dateToMySQLDate(guiSummaryCashboxForDate.getDateSince().getCalendar().getTime(), false),
                            dateToMySQLDate(guiSummaryCashboxForDate.getDateUntil().getCalendar().getTime(), false)));
                }
            } catch (RemoteException ex) {
                Logger.getLogger(ControllerGuiSummaryCashbox.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        if (ae.getSource().equals(guiSummaryCashboxForDate.getCheckMonthly())) {
            guiSummaryCashboxForDate.getCheckDaily().setSelected(false);
            guiSummaryCashboxForDate.getCheckAnnual().setSelected(false);
            guiSummaryCashboxForDate.getCheckAll().setSelected(false);
            try {
                if (guiSummaryCashboxForDate.getDateUntil().getDate() != null
                        && guiSummaryCashboxForDate.getDateSince().getDate() != null) {
                    loadResumeForDate(resume.getResumeMonthly(dateToMySQLDate(guiSummaryCashboxForDate.getDateSince().getCalendar().getTime(), false),
                            dateToMySQLDate(guiSummaryCashboxForDate.getDateUntil().getCalendar().getTime(), false)));
                }
            } catch (RemoteException ex) {
                Logger.getLogger(ControllerGuiSummaryCashbox.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        if (ae.getSource().equals(guiSummaryCashboxForDate.getCheckAnnual())) {
            guiSummaryCashboxForDate.getCheckDaily().setSelected(false);
            guiSummaryCashboxForDate.getCheckMonthly().setSelected(false);
            guiSummaryCashboxForDate.getCheckAll().setSelected(false);
            try {
                if (guiSummaryCashboxForDate.getDateUntil().getDate() != null
                        && guiSummaryCashboxForDate.getDateSince().getDate() != null) {
                    loadResumeForDate(resume.getResumeAnnual(dateToMySQLDate(guiSummaryCashboxForDate.getDateSince().getCalendar().getTime(), false),
                            dateToMySQLDate(guiSummaryCashboxForDate.getDateUntil().getCalendar().getTime(), false)));
                }
            } catch (RemoteException ex) {
                Logger.getLogger(ControllerGuiSummaryCashbox.class.getName()).log(Level.SEVERE, null, ex);
            }

        }
        if (ae.getSource().equals(guiSummaryCashboxForDate.getCheckAll())) {
            guiSummaryCashboxForDate.getCheckDaily().setSelected(false);
            guiSummaryCashboxForDate.getCheckMonthly().setSelected(false);
            guiSummaryCashboxForDate.getCheckAnnual().setSelected(false);
            try {
                if (guiSummaryCashboxForDate.getDateUntil().getDate() != null
                        && guiSummaryCashboxForDate.getDateSince().getDate() != null) {
                    loadResumeForDate(resume.getResumeAll(dateToMySQLDate(guiSummaryCashboxForDate.getDateSince().getCalendar().getTime(), false),
                            dateToMySQLDate(guiSummaryCashboxForDate.getDateUntil().getCalendar().getTime(), false)));
                }
            } catch (RemoteException ex) {
                Logger.getLogger(ControllerGuiSummaryCashbox.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

}
