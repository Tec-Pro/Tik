/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers.cashbox;

import controllers.ControllerMain;
import static controllers.ControllerMain.guiMain;
import controllers.statistics.ControllerGuiProductStatistics;
import controllers.statistics.ControllerGuiSalesStatistics;
import gui.cashbox.GUICashbox;
import gui.cashbox.GUICloseTurnTarde;
import gui.cashbox.GuiOpenTurn;
import gui.cashbox.GuiSummaryCashbox;
import interfaces.InterfaceAdmin;
import interfaces.InterfaceOrder;
import interfaces.InterfacePresence;
import interfaces.InterfaceTurn;
import interfaces.cashbox.InterfaceCashbox;
import interfaces.cashbox.expenses.InterfaceExpenses;
import interfaces.deposits.InterfaceDeposit;
import interfaces.withdrawals.InterfaceWithdrawal;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyVetoException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import utils.InterfaceName;
import utils.ParserFloat;

/**
 *
 * @author jacinto
 */
public class ControllerGuiOpenTurn implements ActionListener {

    GuiOpenTurn guiOpenTurn;
    GuiSummaryCashbox guiSummaryCashbox;
    ControllerGuiSummaryCashbox controllerGuiSummaryCashbox;
    GUICashbox guiCashbox;
    private InterfaceTurn crudTurn;
    private InterfaceCashbox crudCashbox;
    private InterfaceWithdrawal crudWithdrawal;
    private InterfaceDeposit crudDeposit;
    private InterfaceExpenses crudExpenses;
    private InterfaceOrder crudOrder;
    private InterfacePresence crudPresence;
    private InterfaceAdmin crudAdmin;
    private Set<Map> online;

    public ControllerGuiOpenTurn(GuiOpenTurn guiOpenTurn, GUICashbox guiCashbox) throws RemoteException, NotBoundException {
        this.guiOpenTurn = guiOpenTurn;
        this.guiCashbox = guiCashbox;
        crudExpenses = (InterfaceExpenses) InterfaceName.registry.lookup(InterfaceName.CRUDExpenses);
        crudTurn = (InterfaceTurn) InterfaceName.registry.lookup(InterfaceName.CRUDTurn);
        crudCashbox = (InterfaceCashbox) InterfaceName.registry.lookup(InterfaceName.CRUDCashbox);
        crudOrder = (InterfaceOrder) InterfaceName.registry.lookup(InterfaceName.CRUDOrder);
        crudWithdrawal = (InterfaceWithdrawal) InterfaceName.registry.lookup(InterfaceName.CRUDWithdrawal);
        crudDeposit = (InterfaceDeposit) InterfaceName.registry.lookup(InterfaceName.CRUDDeposit);
        crudAdmin = (InterfaceAdmin) InterfaceName.registry.lookup(InterfaceName.CRUDAdmin);
        crudPresence = (InterfacePresence) InterfaceName.registry.lookup(InterfaceName.CRUDPresence);
        turn();
        guiSummaryCashbox = new GuiSummaryCashbox(guiMain, false);
        controllerGuiSummaryCashbox = new ControllerGuiSummaryCashbox(guiSummaryCashbox);
        guiOpenTurn.setActionListener(this);

    }

    /**
     * Funcion para actualizar los txt, lbl y botones segun el turno
     *
     * @throws RemoteException
     */
    public void turn() throws RemoteException {
        if (crudTurn.isTurnOpen()) {
            guiOpenTurn.clear();
            float collect = crudOrder.totalEarn() + crudOrder.getAllExceptions();
            String turn = crudTurn.getTurn();
            float withdrawal = crudWithdrawal.getWithdrawalsTotalOnTurn(turn);
            float spend = crudExpenses.getSumExpenses(turn);
            float enrtyCash = 0;
            float delveryCash = crudDeposit.getAdminsDepositsTotalOnTurn(turn);
            float deliveryWaiter = crudDeposit.getWaitersDepositsTotalOnTurn(turn);
            float pastBalance = crudCashbox.getPastBalance();
            float balance = pastBalance + collect + delveryCash + deliveryWaiter - withdrawal - spend;
            float lastCollect = (float) crudCashbox.getLast().get("collect");
            if (turn.equals("M")) {
                guiOpenTurn.getBtnOpenAfternoon().setEnabled(false);
                guiOpenTurn.getBtnOpenMorning().setEnabled(false);
                guiOpenTurn.getBtnSeeAfternoon().setEnabled(true);
                guiOpenTurn.getBtnSeeMorning().setEnabled(true);
                guiOpenTurn.getBtnCloseAfternoon().setEnabled(false);
                guiOpenTurn.getBtnCloseMorning().setEnabled(true);
                guiOpenTurn.getLblMGain().setText(ParserFloat.floatToString(collect));
                guiOpenTurn.getLblBalance().setText(ParserFloat.floatToString(balance));
                guiOpenTurn.getLblAGain().setText(ParserFloat.floatToString(lastCollect));
            } else {
                guiOpenTurn.getBtnOpenAfternoon().setEnabled(false);
                guiOpenTurn.getBtnOpenMorning().setEnabled(false);
                guiOpenTurn.getBtnSeeAfternoon().setEnabled(true);
                guiOpenTurn.getBtnSeeMorning().setEnabled(true);
                guiOpenTurn.getBtnCloseAfternoon().setEnabled(true);
                guiOpenTurn.getBtnCloseMorning().setEnabled(false);
                guiOpenTurn.getLblMGain().setText(ParserFloat.floatToString(lastCollect));
                guiOpenTurn.getLblBalance().setText(ParserFloat.floatToString(balance));
                guiOpenTurn.getLblAGain().setText(ParserFloat.floatToString(collect));

            }
            online = new HashSet<>();
            online.addAll(crudPresence.getWaiters());
            int i = 1;
            for (Map m : online) {
                String name = m.get("name") + " " + m.get("surname") + ":";
                float earn = crudOrder.EarnByUser((int) m.get("id")) + crudOrder.getExceptions((int) m.get("id"));
                guiOpenTurn.getLblNameByIndex(turn, i).setText(name);
                guiOpenTurn.getLblGainByIndex(turn, i).setText(ParserFloat.floatToString(earn));
                ++i;
            }
        } else {
            guiOpenTurn.getBtnOpenAfternoon().setEnabled(true);
            guiOpenTurn.getBtnOpenMorning().setEnabled(true);
            guiOpenTurn.getBtnSeeAfternoon().setEnabled(true);
            guiOpenTurn.getBtnSeeMorning().setEnabled(true);
            guiOpenTurn.getBtnCloseAfternoon().setEnabled(false);
            guiOpenTurn.getBtnCloseMorning().setEnabled(false);

            guiOpenTurn.getLblBalance().setText(ParserFloat.floatToString(crudCashbox.getPastBalance()));
            Map lastM = crudCashbox.getLast("M");
            guiOpenTurn.getLblMGain().setText(ParserFloat.floatToString((float) lastM.get("collect")));
            Map lastA = crudCashbox.getLast("T");
            guiOpenTurn.getLblAGain().setText(ParserFloat.floatToString((float) lastA.get("collect")));
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == guiOpenTurn.getBtnCloseMorning()) {
            try {
                if (crudPresence.isSomeoneLogin()) {
                    JOptionPane.showMessageDialog(guiMain, "Aun hay empleados logeados, por favor deslogee todos los empleados antes de cerrar la caja");
                } else {
                    float collect = crudOrder.totalEarn() + crudOrder.getAllExceptions();
                    String turn = crudTurn.getTurn();
                    float withdrawal = crudWithdrawal.getWithdrawalsTotalOnTurn(turn);
                    float spend = crudExpenses.getSumExpenses(turn);
                    float enrtyCash = 0;
                    float delveryCash = crudDeposit.getAdminsDepositsTotalOnTurn(turn);
                    float deliveryWaiter = crudDeposit.getWaitersDepositsTotalOnTurn(turn);
                    float balance = crudCashbox.getPastBalance() + collect + delveryCash + deliveryWaiter - withdrawal - spend;
                    withdrawal = crudWithdrawal.getWithdrawalsTotalOnTurn(turn);
                    balance = crudCashbox.getPastBalance() + collect + delveryCash + deliveryWaiter - withdrawal - spend;
                    crudCashbox.create(turn, balance, collect, enrtyCash, spend, withdrawal, delveryCash, deliveryWaiter);
                    //estadisticas
                    ControllerGuiSalesStatistics.calculateAndSaveStatistics();
                    ControllerGuiProductStatistics.calculateAndSaveProductStatistics();

                    if (crudTurn.changeTurn("N")) {
                        JOptionPane.showMessageDialog(guiMain, "El turno se cerro exitosamente");
                        turn();
                    }

                }
            } catch (RemoteException ex) {
                Logger.getLogger(ControllerGuiOpenTurn.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        if (e.getSource() == guiOpenTurn.getBtnCloseAfternoon()) {
            try {
                if (crudPresence.isSomeoneLogin()) {
                    JOptionPane.showMessageDialog(guiOpenTurn, "Aun hay empleados logeados, por favor deslogee todos los empleados antes de cerrar la caja");
                } else {
                    float collect = crudOrder.totalEarn() + crudOrder.getAllExceptions();
                    String turn = crudTurn.getTurn();
                    float withdrawal = crudWithdrawal.getWithdrawalsTotalOnTurn(turn);
                    float spend = crudExpenses.getSumExpenses(turn);
                    float enrtyCash = 0;
                    float delveryCash = crudDeposit.getAdminsDepositsTotalOnTurn(turn);
                    float deliveryWaiter = crudDeposit.getWaitersDepositsTotalOnTurn(turn);
                    float balance = crudCashbox.getPastBalance() + collect + delveryCash + deliveryWaiter - withdrawal - spend;

                    //retiro la guita antes de cerrar
                    GUICloseTurnTarde guiCloseTurnTarde = new GUICloseTurnTarde(guiMain, true, crudAdmin.getAdmins(), balance);
                    guiCloseTurnTarde.setLocationRelativeTo(guiMain);
                    guiCloseTurnTarde.setVisible(true);
                    if (guiCloseTurnTarde.getReturnStatus() == guiCloseTurnTarde.RET_OK) {
                        crudWithdrawal.create(guiCloseTurnTarde.getIdAdminSelected(), "cierre de caja", guiCloseTurnTarde.getAmountWithdrawal());

                    } else {
                        return;// salgo de todo sin cerar el turno si no acepto
                    }                            //HACER RESUMEN ALAN???
                    withdrawal = crudWithdrawal.getWithdrawalsTotalOnTurn(turn);
                    balance = crudCashbox.getPastBalance() + collect + delveryCash + deliveryWaiter - withdrawal - spend;
                    crudCashbox.create(turn, balance, collect, enrtyCash, spend, withdrawal, delveryCash, deliveryWaiter);

                    //estadisticas
                    ControllerGuiSalesStatistics.calculateAndSaveStatistics();
                    ControllerGuiProductStatistics.calculateAndSaveProductStatistics();
                    if (crudTurn.changeTurn("N")) {
                        JOptionPane.showMessageDialog(guiMain, "El turno se cerro exitosamente");
                        turn();
                    }

                }
            } catch (RemoteException ex) {
                Logger.getLogger(ControllerGuiOpenTurn.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        if (e.getSource() == guiOpenTurn.getBtnOpenMorning()) {
            try {
                if (crudTurn.changeTurn("M")) {
                    JOptionPane.showMessageDialog(guiMain, "Turno mañana abierto");
                    crudOrder.deleteAll();
                    crudExpenses.removeAllExpenses();
                    crudWithdrawal.eraseWithdrawals();
                    crudDeposit.eraseWaiterDeposits();
                    crudDeposit.eraseAdminDeposits();
                    turn();
                }
            } catch (RemoteException ex) {
                Logger.getLogger(ControllerGuiOpenTurn.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        if (e.getSource() == guiOpenTurn.getBtnOpenAfternoon()) {
            try {
                if (crudTurn.changeTurn("T")) {
                    JOptionPane.showMessageDialog(guiMain, "Turno tarde abierto");
                    crudOrder.deleteAll();
                    turn();
                }
            } catch (RemoteException ex) {
                Logger.getLogger(ControllerGuiOpenTurn.class.getName()).log(Level.SEVERE, null, ex);
            }
        }







        if (e.getSource() == guiOpenTurn.getBtnSeeMorning()) {
            try {
                if (crudTurn.getTurn().equals("T")) {
                    float collect = crudOrder.totalEarn() + crudOrder.getAllExceptions();
                    String turn = crudTurn.getTurn();
                    float withdrawal = crudWithdrawal.getWithdrawalsTotalOnTurn(turn);
                    float spend = crudExpenses.getSumExpenses(turn);
                    float delveryCash = crudDeposit.getAdminsDepositsTotalOnTurn(turn);
                    float deliveryWaiter = crudDeposit.getWaitersDepositsTotalOnTurn(turn);
                    float pastBalance = crudCashbox.getPastBalance();
                    float balance = pastBalance + collect + delveryCash + deliveryWaiter - withdrawal - spend;
                    guiCashbox.getDCBalanceField().setText(ParserFloat.floatToString(pastBalance));
                    guiCashbox.getDCCashboxIncomeField().setText("0");
                    guiCashbox.getDCEarningsField().setText(ParserFloat.floatToString(collect));
                    guiCashbox.getDCExpensesField().setText(ParserFloat.floatToString(spend));
                    guiCashbox.getDCNextTurnField().setText(ParserFloat.floatToString(balance));
                } else {
                    //calculsos, deasbilitar         
                }
                try {
                    guiCashbox.setMaximum(true);
                } catch (PropertyVetoException ex) {
                    Logger.getLogger(ControllerMain.class.getName()).log(Level.SEVERE, null, ex);
                }
                guiCashbox.setVisible(true);
                guiCashbox.toFront();
            } catch (RemoteException ex) {
                Logger.getLogger(ControllerGuiOpenTurn.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        if (e.getSource() == guiOpenTurn.getBtnSeeAfternoon()) {
            try {
                if (crudTurn.getTurn().equals("M")) {
                    float collect = crudOrder.totalEarn() + crudOrder.getAllExceptions();
                    String turn = crudTurn.getTurn();
                    float withdrawal = crudWithdrawal.getWithdrawalsTotalOnTurn(turn);
                    float spend = crudExpenses.getSumExpenses(turn);
                    float delveryCash = crudDeposit.getAdminsDepositsTotalOnTurn(turn);
                    float deliveryWaiter = crudDeposit.getWaitersDepositsTotalOnTurn(turn);
                    float pastBalance = crudCashbox.getPastBalance();
                    float balance = pastBalance + collect + delveryCash + deliveryWaiter - withdrawal - spend;
                    guiCashbox.getDCBalanceField().setText(ParserFloat.floatToString(pastBalance));
                    guiCashbox.getDCCashboxIncomeField().setText("0");
                    guiCashbox.getDCEarningsField().setText(ParserFloat.floatToString(collect));
                    guiCashbox.getDCExpensesField().setText(ParserFloat.floatToString(spend));
                    guiCashbox.getDCNextTurnField().setText(ParserFloat.floatToString(balance));
                    //Desabilitar botones
                } else {
                    //calculsos, deasbilitar            
                }
                try {
                    guiCashbox.setMaximum(true);
                } catch (PropertyVetoException ex) {
                    Logger.getLogger(ControllerMain.class.getName()).log(Level.SEVERE, null, ex);
                }
                guiCashbox.setVisible(true);
                guiCashbox.toFront();
            } catch (RemoteException ex) {
                Logger.getLogger(ControllerGuiOpenTurn.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        if (e.getSource() == guiOpenTurn.getBtnResume()) {
            guiSummaryCashbox.setVisible(true);
            guiSummaryCashbox.setLocationRelativeTo(null);
            try {
                controllerGuiSummaryCashbox.loadData();
            } catch (RemoteException ex) {
                Logger.getLogger(ControllerGuiOpenTurn.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}
