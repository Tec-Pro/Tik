/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers.cashbox;

import controllers.ControllerMain;
import static controllers.ControllerMain.guiMain;
import gui.cashbox.GUICashbox;
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
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyVetoException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.Map;
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
    GUICashbox guiCashbox;
    private InterfaceTurn crudTurn;
    private InterfaceCashbox crudCashbox;
    private InterfaceWithdrawal crudWithdrawal;
    private InterfaceDeposit crudDeposit;
    private InterfaceExpenses crudExpenses;
    private InterfaceOrder crudOrder;

    public ControllerGuiOpenTurn(GuiOpenTurn guiOpenTurn, GUICashbox guiCashbox) throws RemoteException, NotBoundException {
        this.guiOpenTurn = guiOpenTurn;
        this.guiCashbox = guiCashbox;
        crudExpenses = (InterfaceExpenses) InterfaceName.registry.lookup(InterfaceName.CRUDExpenses);
        crudTurn = (InterfaceTurn) InterfaceName.registry.lookup(InterfaceName.CRUDTurn);
        crudCashbox = (InterfaceCashbox) InterfaceName.registry.lookup(InterfaceName.CRUDCashbox);
        crudOrder = (InterfaceOrder) InterfaceName.registry.lookup(InterfaceName.CRUDOrder);
        crudWithdrawal = (InterfaceWithdrawal) InterfaceName.registry.lookup(InterfaceName.CRUDWithdrawal);
        crudDeposit = (InterfaceDeposit) InterfaceName.registry.lookup(InterfaceName.CRUDDeposit);
        turn();
        guiSummaryCashbox = new GuiSummaryCashbox(guiMain, false);
        guiOpenTurn.setActionListener(this);
        
    }

    /**
     * Funcion para actualizar los txt, lbl y color del boton segun el turno
     *
     * @throws RemoteException
     */
    public void turn() throws RemoteException {
        if (crudTurn.isTurnOpen()) {
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
                guiOpenTurn.getBtnMorning().setBackground(Color.green);
                guiOpenTurn.getBtnAfternoon().setBackground(Color.GRAY);
                guiOpenTurn.getLblMGain().setText(ParserFloat.floatToString(collect));
                guiOpenTurn.getLblMBalance().setText(ParserFloat.floatToString(balance));
                //funcion saldo
                //funcion recaudado
                guiOpenTurn.getLblAGain().setText(ParserFloat.floatToString(lastCollect));
                guiOpenTurn.getLblABalance().setText(ParserFloat.floatToString(pastBalance));
            } else {
                guiOpenTurn.getBtnMorning().setBackground(Color.GRAY);
                guiOpenTurn.getBtnAfternoon().setBackground(Color.green);
                guiOpenTurn.getLblAGain().setText(ParserFloat.floatToString(collect));
                guiOpenTurn.getLblABalance().setText(ParserFloat.floatToString(balance));
                //funcion saldo
                //funcion recaudado
                guiOpenTurn.getLblMGain().setText(ParserFloat.floatToString(lastCollect));
                guiOpenTurn.getLblMBalance().setText(ParserFloat.floatToString(pastBalance));
            }
        } else {
            guiOpenTurn.getBtnMorning().setBackground(Color.GRAY);
            guiOpenTurn.getBtnAfternoon().setBackground(Color.GRAY);
            Map lastM = crudCashbox.getLast("M");
            guiOpenTurn.getLblMGain().setText(ParserFloat.floatToString((float) lastM.get("collect")));
            guiOpenTurn.getLblMBalance().setText(ParserFloat.floatToString((float) lastM.get("balance")));
            Map lastA = crudCashbox.getLast("T");
            guiOpenTurn.getLblAGain().setText(ParserFloat.floatToString((float) lastA.get("collect")));
            guiOpenTurn.getLblABalance().setText(ParserFloat.floatToString((float) lastA.get("balance")));
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == guiOpenTurn.getBtnMorning()) {
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
        if (e.getSource() == guiOpenTurn.getBtnAfternoon()) {
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
        }
    }
}
