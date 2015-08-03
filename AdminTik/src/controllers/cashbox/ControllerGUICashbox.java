/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers.cashbox;

import controllers.deposits.ControllerGUINewDeposit;
import controllers.ControllerMain;
import controllers.withdrawals.ControllerGUINewWithdrawal;
import gui.cashbox.GUICashbox;
import gui.cashbox.GuiPayProvider;
import gui.deposit.GUINewDeposit;
import gui.providers.purchases.GuiPayPurchase;
import gui.withdrawal.GUINewWithdrawal;
import interfaces.InterfaceAdmin;
import interfaces.InterfaceOrder;
import interfaces.InterfaceTurn;
import interfaces.InterfaceUser;
import interfaces.cashbox.InterfaceCashbox;
import interfaces.cashbox.expenses.InterfaceExpenses;
import interfaces.deposits.InterfaceDeposit;
import interfaces.providers.InterfaceProvider;
import interfaces.providers.payments.InterfacePayments;
import interfaces.withdrawals.InterfaceWithdrawal;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import utils.Dates;
import utils.InterfaceName;
import utils.ParserFloat;

/**
 *
 * @author joako
 */
public class ControllerGUICashbox implements ActionListener {

    public static GUICashbox gui; //DEJAR EN PUBLIC
    private static InterfaceWithdrawal withdrawal;
    private static InterfaceDeposit deposit;
    private static InterfaceAdmin admin;
    private static InterfaceUser user;
    private static InterfaceCashbox cashbox;
    private final InterfaceProvider interfaceProvider;
    private final InterfacePayments interfacePayments;
    private final InterfaceExpenses interfaceExpenses;
    private final InterfaceAdmin interfaceAdmin;
    private final InterfaceTurn interfaceTurn;
    private static InterfaceTurn turn;
    private static InterfaceExpenses expenses;
    private static InterfaceOrder crudOrder;

    public ControllerGUICashbox(GUICashbox guiCashbox) throws RemoteException, NotBoundException {
        gui = guiCashbox;
        withdrawal = (InterfaceWithdrawal) InterfaceName.registry.lookup(InterfaceName.CRUDWithdrawal);
        deposit = (InterfaceDeposit) InterfaceName.registry.lookup(InterfaceName.CRUDDeposit);
        admin = (InterfaceAdmin) InterfaceName.registry.lookup(InterfaceName.CRUDAdmin);
        user = (InterfaceUser) InterfaceName.registry.lookup(InterfaceName.CRUDUser);
        cashbox = (InterfaceCashbox) InterfaceName.registry.lookup(InterfaceName.CRUDCashbox);
        turn = (InterfaceTurn) InterfaceName.registry.lookup(InterfaceName.CRUDTurn);
        expenses = (InterfaceExpenses) InterfaceName.registry.lookup(InterfaceName.CRUDExpenses);
        crudOrder = (InterfaceOrder) InterfaceName.registry.lookup(InterfaceName.CRUDOrder);
        this.interfaceProvider = (InterfaceProvider) InterfaceName.registry.lookup(InterfaceName.CRUDProvider);
        this.interfacePayments = (InterfacePayments) InterfaceName.registry.lookup(InterfaceName.CRUDpayments);
        this.interfaceExpenses = (InterfaceExpenses) InterfaceName.registry.lookup(InterfaceName.CRUDExpenses);
        this.interfaceAdmin = (InterfaceAdmin) InterfaceName.registry.lookup(InterfaceName.CRUDAdmin);
        this.interfaceTurn = (InterfaceTurn) InterfaceName.registry.lookup(InterfaceName.CRUDTurn);
        gui.setActionListener(this);
        gui.getCashboxIncomeLabel().addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2 && e.getButton() == MouseEvent.BUTTON1) {
                    GUINewDeposit guiNewDeposit = new GUINewDeposit(ControllerMain.guiMain, true);
                    guiNewDeposit.getBtnOk().setActionCommand("OK ENTRADA");
                    try {
                        float incomeTotal = ParserFloat.stringToFloat(guiCashbox.getECCashboxIncomeField().getText());
                        ControllerGUINewDeposit controller = new ControllerGUINewDeposit(guiNewDeposit);
                        controller.loadComboBoxAdmins();
                        guiNewDeposit.setVisible(true);
                        guiNewDeposit.toFront();
                    } catch (RemoteException | NotBoundException ex) {
                        Logger.getLogger(ControllerGUICashbox.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        });
        loadWithdrawals();
        loadWaiterDeposits();
        loadAdminDeposits();
        loadExpenses();
        loadExistantCashbox();
        reloadDialyCashbox();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        switch (e.getActionCommand()) {
            //Si quiere cargar un nuevo retiro:
            case "NUEVO RETIRO":
                GUINewWithdrawal guiNewWithdrawal = new GUINewWithdrawal(ControllerMain.guiMain, true);
                try {
                    ControllerGUINewWithdrawal controller = new ControllerGUINewWithdrawal(guiNewWithdrawal);
                    guiNewWithdrawal.setVisible(true);
                    guiNewWithdrawal.toFront();
                    ECLoadWithdrawals();
                } catch (RemoteException | NotBoundException ex) {
                    Logger.getLogger(ControllerGUICashbox.class.getName()).log(Level.SEVERE, null, ex);
                }

                break;
            //Si quiere cargar una entrega de mozo.
            case "ENTREGA MOZO": {
                GUINewDeposit guiNewDeposit = new GUINewDeposit(ControllerMain.guiMain, true);
                //Cambio el action command para que el otro controlador sepa que hacer.
                guiNewDeposit.getBtnOk().setActionCommand("OK MOZO");
                try {
                    ControllerGUINewDeposit controller = new ControllerGUINewDeposit(guiNewDeposit);
                    controller.loadComboBoxWaiters();
                    guiNewDeposit.setVisible(true);
                    guiNewDeposit.toFront();
                } catch (RemoteException | NotBoundException ex) {
                    Logger.getLogger(ControllerGUICashbox.class.getName()).log(Level.SEVERE, null, ex);
                }
                break;
            }
            //Si es una entrega de admin.
            case "ENTREGA ADMIN": {
                GUINewDeposit guiNewDeposit = new GUINewDeposit(ControllerMain.guiMain, true);
                //Cambio el action command así el nuevo controlador sabe que hacer.
                guiNewDeposit.getBtnOk().setActionCommand("OK ADMIN");
                try {
                    ControllerGUINewDeposit controller = new ControllerGUINewDeposit(guiNewDeposit);
                    controller.loadComboBoxAdmins();
                    guiNewDeposit.setVisible(true);
                    guiNewDeposit.toFront();
                } catch (RemoteException | NotBoundException ex) {
                    Logger.getLogger(ControllerGUICashbox.class.getName()).log(Level.SEVERE, null, ex);
                }
                break;
            }
        }
        if (e.getSource() == gui.getBtnNewExpense()) {
            try {
                List<Map> providers;
                List<Map> admins;
                providers = interfaceProvider.getProviders();
                admins = interfaceAdmin.getAdmins();
                try {
                    GuiPayProvider guiPay = new GuiPayProvider(ControllerMain.guiMain, true, admins, providers);
                    guiPay.setLocationRelativeTo(gui);
                    guiPay.setVisible(true);
                    if (guiPay.getReturnStatus() == GuiPayPurchase.RET_OK) {
                        String detail = guiPay.getDetail();
                        String nameAdmin = guiPay.getNameAdmin();
                        float payAdmin = guiPay.getPayAdmin();
                        float payBox = guiPay.getPayBox();
                        int idProvider = guiPay.getProvider();
                        String datePaid = Dates.dateToMySQLDate(Calendar.getInstance().getTime(), false);
                        interfacePayments.createPayment(idProvider, detail, payAdmin + payBox, -1, datePaid, nameAdmin);
                        int auxType;
                        if (idProvider == -1) {
                            auxType = 3;
                        } else {
                            auxType = 1;
                        }
                        interfaceExpenses.createExpense(auxType, detail, payAdmin + payBox, -1, idProvider, interfaceTurn.getTurn());
                        if (idProvider >= 0) {
                            interfaceProvider.payPurchases(idProvider, payAdmin + payBox);
                        }
                        if (guiPay.getPayAdmin() > 0) {

                            /**
                             * ACA DEBO CREAR UNA ENTREGA DEL MOZO SIEMPRE Y
                             * CUANDO LO QUE ENTREGA MOZO ES MAYOR A 0
                             */
                            int idAdmin = -1;
                            Iterator<Map> it = admins.iterator();
                            while (it.hasNext() && idAdmin == -1) {
                                Map a = it.next();
                                if (((String) a.get("name")) == nameAdmin) {
                                    idAdmin = (Integer) a.get("id");
                                }
                            }
                            deposit.createAdminDeposit(idAdmin, guiPay.getPayAdmin());
                            controllers.cashbox.ControllerGUICashbox.reloadAdminDeposits();
                        }

                    }
                } catch (NotBoundException ex) {
                    Logger.getLogger(ControllerGUICashbox.class.getName()).log(Level.SEVERE, null, ex);
                }
                loadExpenses();
                ECLoadExpenses();
            } catch (RemoteException ex) {
                Logger.getLogger(ControllerGUICashbox.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        try {
            ECLoadBalance();
            reloadDialyCashbox();
        } catch (RemoteException ex) {
            Logger.getLogger(ControllerGUICashbox.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Método que actualiza los retiros en la tabla y en los campos
     * correspondientes de caja existente.
     *
     * @throws RemoteException error de conexión.
     */
    public static void reloadWithdrawals() throws RemoteException {
        loadWithdrawals();
    }

    //Método que carga la tabla con los retiros y modifica los campos de caja existente.
    private static void loadWithdrawals() throws RemoteException {
        String date = new java.sql.Date(System.currentTimeMillis()).toString();
        List<Map> withdrawalList = withdrawal.getWithdrawals();
        gui.getWithdrawalsTableModel().setRowCount(0);
        Object[] o = new Object[4];
        Float total = 0.0f;
        String hour;
        for (Map w : withdrawalList) {
            o[0] = admin.getAdmin((int) w.get("admin_id")).get("name");
            o[1] = w.get("turn");
            hour = w.get("created_at").toString();
            o[2] = (hour.split(" "))[1].subSequence(0, 5);
            o[3] = ParserFloat.floatToString((Float) w.get("amount"));
            gui.getWithdrawalsTableModel().addRow(o);
            total = total + (Float) w.get("amount");
        }
        gui.getWithdrawalTotalField().setText(ParserFloat.floatToString(total));
        gui.getECWithdrawalsField().setText(ParserFloat.floatToString(total));
    }

    /**
     * Método que actualiza la lista de entregas de mozo y los campos
     * correspondientes de la caja existente
     *
     * @throws RemoteException error de conexión
     */
    public static void reloadWaiterDeposits() throws RemoteException {
        loadWaiterDeposits();
    }

    /*
     Método que se encarga de cargar la lista de entregas de mozo.
     */
    private static void loadWaiterDeposits() throws RemoteException {
        String date = new java.sql.Date(System.currentTimeMillis()).toString();
        List<Map> depositsList = deposit.getWaitersDeposits();
        gui.getWaiterDepositsTableModel().setRowCount(0);
        Object[] o = new Object[4];
        Float total = 0.0f;
        String hour;
        for (Map d : depositsList) {
            o[0] = user.getUser((int) d.get("waiter_id")).get("name");
            o[1] = d.get("turn");
            hour = d.get("created_at").toString();
            o[2] = (hour.split(" "))[1].subSequence(0, 5);
            o[3] = ParserFloat.floatToString((Float) d.get("amount"));
            gui.getWaiterDepositsTableModel().addRow(o);
            total = total + (Float) d.get("amount");
        }
        gui.getWaiterDepositsTotalField().setText(ParserFloat.floatToString(total));
        gui.getECWaiterDepositsField().setText(ParserFloat.floatToString(total));
    }

    /**
     * Método que actualiza la lista de depósitos de administradores y los
     * campos correspondientes de caja existente.
     *
     * @throws RemoteException
     */
    public static void reloadAdminDeposits() throws RemoteException {
        loadAdminDeposits();
    }
    /*
     Método que se encarga de cargar la lista de depósitos de administradores
     y actualiza los campos de la caja existente.
     */

    private static void loadAdminDeposits() throws RemoteException {
        String date = new java.sql.Date(System.currentTimeMillis()).toString();
        List<Map> depositsList = deposit.getAdminsDeposits();
        gui.getAdminDepositsTableModel().setRowCount(0);
        Object[] o = new Object[4];
        Float total = 0.0f;
        String hour;
        for (Map d : depositsList) {
            o[0] = admin.getAdmin((int) d.get("admin_id")).get("name");
            o[1] = d.get("turn");
            hour = d.get("created_at").toString();
            o[2] = (hour.split(" "))[1].subSequence(0, 5);
            o[3] = ParserFloat.floatToString((Float) d.get("amount"));
            gui.getAdminDepositsTableModel().addRow(o);
            total = total + (Float) d.get("amount");
        }
        gui.getAdminDepositsTotalField().setText(ParserFloat.floatToString(total));
        gui.getECAdminDepositsField().setText(ParserFloat.floatToString(total));
    }

    /**
     * Método que actualiza la lista de gastos y los campos de la caja
     * existente.
     *
     * @throws RemoteException error de conexión.
     */
    public static void reloadExpenses() throws RemoteException {
        loadExpenses();
    }

    /*
     Método que carga los gastos pagados con dinero de la caja.
     */
    private static void loadExpenses() throws RemoteException {
        List<Map> exp = expenses.getExpenses("N");
        gui.getExpensesTableModel().setRowCount(0);
        Object[] o = new Object[4];
        Float total = 0.0f;
        for (Map e : exp) {
            o[0] = e.get("id");
            o[1] = e.get("type");
            o[2] = e.get("detail");
            o[3] = ParserFloat.floatToString((Float) e.get("amount"));
            total = total + (Float) e.get("amount");
            gui.getExpensesTableModel().addRow(o);
        }
        gui.getExpensesTotalField().setText(ParserFloat.floatToString(total));
        gui.getECCashboxExpensesField().setText(ParserFloat.floatToString(total));
    }

    /*
     Método que carga el saldo inicial de la caja.
     */
    private static Float ECLoadInitialBalance() throws RemoteException {
        Map lastTurn = cashbox.getLast();
        Map mm;
        if (lastTurn.get("turn").equals("T")) {
            mm = cashbox.getLast("TT"); // si el ultimo turno es tarde, necesito el otro turno tarde
        } else {
            mm = cashbox.getLast("T");
        }
        //con esto busco el ultimo turno mañana para tener cual es el saldo inicial
        return ((float) mm.get("balance")) + ((float) mm.get("spend")) - ((float) mm.get("entry_cash")) - ((float) mm.get("collect"));
    }

    /*
     Método que carga los depósitos de admin en la caja existente.
     */
    private static Float ECLoadAdminDeposits() throws RemoteException {
        String date = new java.sql.Date(System.currentTimeMillis()).toString();
        Float adminDeposits = deposit.getAdminsDepositsTotal();
        gui.getECAdminDepositsField().setText(ParserFloat.floatToString(adminDeposits));
        return adminDeposits;
    }

    /*
     Método que carga las entregas de mozo en la caja existente.
     */
    private static Float ECLoadWaiterDeposits() throws RemoteException {
        String date = new java.sql.Date(System.currentTimeMillis()).toString();
        Float waiterDeposits = deposit.getWaitersDepositsTotal();
        gui.getECWaiterDepositsField().setText(ParserFloat.floatToString(waiterDeposits));
        return waiterDeposits;
    }
    /*
     Método que carga los retiros en la caja existente.
     */

    private static Float ECLoadWithdrawals() throws RemoteException {
        String date = new java.sql.Date(System.currentTimeMillis()).toString();
        Float withdrawals = withdrawal.getWithdrawalsTotal();
        gui.getECWithdrawalsField().setText(ParserFloat.floatToString(withdrawals));
        return withdrawals;
    }

    private static Float ECLoadCashboxIncome() throws RemoteException {
        String date = new java.sql.Date(System.currentTimeMillis()).toString();
        Float incomes = deposit.getIncomesTotal(date, "N"); // con N retorno todos
        gui.getECCashboxIncomeField().setText(ParserFloat.floatToString(incomes));
        return incomes;
    }

    /*
     Método que carga los gastos en la caja existente.
     */
    private static Float ECLoadExpenses() throws RemoteException {
        Float exp = expenses.getSumExpenses("N");
        gui.getECCashboxExpensesField().setText(ParserFloat.floatToString(exp));
        return exp;
    }

    /**
     * Método que actualiza el saldo de la caja existente.
     *
     * @throws RemoteException error de conexión.
     */
    public static void ECReloadBalance() throws RemoteException {
        ECLoadBalance();
    }
    /*
     Método que carga el balance en la caja existente.
     */

    public static Float ECLoadBalance() throws RemoteException {
        Float initialBalance = ParserFloat.stringToFloat(gui.getECInitialBalanceField().getText());
        Float adminDeposits = ParserFloat.stringToFloat(gui.getECAdminDepositsField().getText());
        Float waiterDeposits = ParserFloat.stringToFloat(gui.getECWaiterDepositsField().getText());
        Float withdrawals = ParserFloat.stringToFloat(gui.getECWithdrawalsField().getText());
        Float exp = ParserFloat.stringToFloat(gui.getECCashboxExpensesField().getText());
        Float cashboxIncome = ParserFloat.stringToFloat(gui.getECCashboxIncomeField().getText());
        Float balance = initialBalance + adminDeposits + waiterDeposits + cashboxIncome - withdrawals - exp;
        gui.getECBalanceField().setText(ParserFloat.floatToString(balance));
        return balance;
    }

    /*
     Método que carga todos los valores de la caja existente.
     */
    private static void loadExistantCashbox() throws RemoteException {
        String date = new java.sql.Date(System.currentTimeMillis()).toString();

        Float initialBalance = ECLoadInitialBalance();

        Float adminDeposits = ECLoadAdminDeposits();

        Float cashboxIncome = ECLoadCashboxIncome();

        Float waiterDeposits = ECLoadWaiterDeposits();

        Float withdrawals = ECLoadWithdrawals();

        Float exp = ECLoadExpenses();

        Float balance = initialBalance + adminDeposits + waiterDeposits + cashboxIncome - withdrawals - exp;
        gui.getECBalanceField().setText(ParserFloat.floatToString(balance));

    }

    /**
     * Método que actualiza todos los valores de la caja existente.
     *
     * @throws RemoteException
     */
    public static void reloadExistantCashbox() throws RemoteException {
        loadExistantCashbox();
    }

    public static void reloadIncome() throws RemoteException {
        ECLoadCashboxIncome();
    }

    /**
     * Método que actualiza todos los valores de la caja diaria.
     *
     * @throws RemoteException
     */
    public static void reloadDialyCashbox() throws RemoteException {
        //cargo siempre el saldo inicial
        Map lastTurn = cashbox.getLast();
        Map mm;
        if (lastTurn.get("turn").equals("T") && !turn.isTurnOpen()) {
            mm = cashbox.getLast("TT"); // si el ultimo turno es tarde, necesito el otro turno tarde
        } else {
            mm = cashbox.getLast("T");
        }
        //con esto busco el ultimo turno mañana para tener cual es el saldo inicial
        float initialBalance = ((float) mm.get("balance"));
        gui.getDCInitialBalanceField().setText(ParserFloat.floatToString(initialBalance));
        gui.getECInitialBalanceField().setText(ParserFloat.floatToString(initialBalance));
        if (turn.isTurnOpen()) {
            if (turn.getTurn().equals("M")) {
                gui.getDCCashboxIncomeField().setText(gui.getECCashboxIncomeField().getText());
                gui.getDCEarningsField().setText(ParserFloat.floatToString(crudOrder.totalEarn() + crudOrder.getAllExceptions()));
                gui.getDCExpensesField().setText(gui.getECCashboxExpensesField().getText());
                gui.getDCNextTurnField().setText("0");
                gui.getLblCurrentTurn().setText("TURNO MAÑANA ABIERTO");
            } else {
                Map m = cashbox.getLast();
                Map mt = cashbox.getLast("T");
                gui.getDCInitialBalanceField().setText(ParserFloat.floatToString((float) mt.get("balance")));
                gui.getDCCashboxIncomeField().setText(ParserFloat.floatToString(((float) m.get("entry_cash")) ));
                float currentEarnes = crudOrder.totalEarn() + crudOrder.getAllExceptions();
                gui.getDCEarningsField().setText(ParserFloat.floatToString(((float) m.get("collect")) + currentEarnes));
                float currentExpenses = ParserFloat.stringToFloat(gui.getECCashboxExpensesField().getText());
                gui.getDCExpensesField().setText(ParserFloat.floatToString(currentExpenses));
                gui.getDCNextTurnField().setText("0");
                gui.getLblCurrentTurn().setText("TURNO TARDE ABIERTO");
            }

        } else {
            gui.getLblCurrentTurn().setText("NO HAY TURNO ABIERTO");
            lastTurn = cashbox.getLast();
            if (lastTurn.get("turn").equals("T")) {
                /**
                 * ahora esta cerrado todos los turnos, si el turno anterior es
                 * T entonces tengo que setear el turno siguiente sino queda
                 * vacio con 0
                 *
                 */
                gui.getDCNextTurnField().setText(ParserFloat.floatToString((float) lastTurn.get("balance")));
            } else// lo dejo en 0, todavía no se definio cuanto deja para el día de mañana
            {
                gui.getDCNextTurnField().setText(ParserFloat.floatToString((float) 0));
            }
            Map m = cashbox.getLast();
            if (m.get("turn").equals("M")) {
                gui.getDCCashboxIncomeField().setText(ParserFloat.floatToString(((float) m.get("entry_cash"))));
                gui.getDCEarningsField().setText(ParserFloat.floatToString((float) m.get("collect")));
                gui.getDCExpensesField().setText(ParserFloat.floatToString(((float) m.get("spend"))));
            }
            if (m.get("turn").equals("T")) {
                mm= cashbox.getLast("M");
                gui.getDCCashboxIncomeField().setText(ParserFloat.floatToString((float) m.get("entry_cash")-(float)mm.get("entry_cash")));
                gui.getDCEarningsField().setText(ParserFloat.floatToString((float) m.get("collect")-(float) mm.get("collect")));
                gui.getDCExpensesField().setText(ParserFloat.floatToString(((float) m.get("spend")-(float) mm.get("spend"))));
            }
        }
        float incomes = ParserFloat.stringToFloat(gui.getDCCashboxIncomeField().getText());
        float earning = ParserFloat.stringToFloat(gui.getDCEarningsField().getText());
        float expenses = ParserFloat.stringToFloat(gui.getDCExpensesField().getText());
        gui.getDCBalanceField().setText(ParserFloat.floatToString(incomes + earning - expenses));
    }
    
    public  static void blockButtons() throws RemoteException{
        if(turn.isTurnOpen()){
            gui.getBtnNewExpense().setEnabled(true);
            gui.getNewWithdrawalButton().setEnabled(true);
            gui.getNewWaiterDepositButton().setEnabled(true);
            gui.getNewAdminDepositButton().setEnabled(true);
        }
        else{
            gui.getBtnNewExpense().setEnabled(false);
            gui.getNewWithdrawalButton().setEnabled(false);
            gui.getNewWaiterDepositButton().setEnabled(false);
            gui.getNewAdminDepositButton().setEnabled(false);
        }
    }
}
