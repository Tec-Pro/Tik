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
        ControllerGUICashbox.reloadAdminDeposits();
        ControllerGUICashbox.reloadExistantCashbox();
        ControllerGUICashbox.reloadWaiterDeposits();
        ControllerGUICashbox.reloadWithdrawals();
        ControllerGUICashbox.reloadDialyCashbox();
        if (crudTurn.isTurnOpen()) {
            guiOpenTurn.clear();
            String turn = crudTurn.getTurn();
            String date = new java.sql.Date(System.currentTimeMillis()).toString();
            float collect = crudOrder.totalEarn() + crudOrder.getAllExceptions();
            float balance = ControllerGUICashbox.ECLoadBalance();
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
            online.addAll(crudPresence.getWaitersWereOnline());
            int i = 1;
            for (Map m : online) {
                String name = m.get("name") + " " + m.get("surname") + ":";
                int id = (int) m.get("id");
                float earn = crudOrder.EarnByUser(id) + crudOrder.getExceptions(id);
                guiOpenTurn.getLblNameByIndex(turn, i).setText(name);
                guiOpenTurn.getLblGainByIndex(turn, i).setText(ParserFloat.floatToString(earn));
                guiOpenTurn.getLblGainByIndex(turn, i).setVisible(true);
                guiOpenTurn.getLblNameByIndex(turn, i).setVisible(true);
                ++i;
            }
            guiOpenTurn.repaint();
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
            guiOpenTurn.clear();
        }

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == guiOpenTurn.getBtnCloseMorning()) {
            try {
                if (crudPresence.isSomeoneLogin()) {
                    JOptionPane.showMessageDialog(guiMain, "Aun hay empleados logeados, por favor deslogee todos los empleados antes de cerrar la caja");
                } else {
                    ControllerGUICashbox.reloadAdminDeposits();
                    ControllerGUICashbox.reloadExistantCashbox();
                    ControllerGUICashbox.reloadWaiterDeposits();
                    ControllerGUICashbox.reloadWithdrawals();
                    ControllerGUICashbox.reloadDialyCashbox();
                    float collect = crudOrder.totalEarn() + crudOrder.getAllExceptions();
                    String turn = crudTurn.getTurn();
                    float withdrawal = ParserFloat.stringToFloat(ControllerGUICashbox.gui.getECWithdrawalsField().getText());
                    float spend = ParserFloat.stringToFloat(ControllerGUICashbox.gui.getECCashboxExpensesField().getText());
                    float entryCash = ParserFloat.stringToFloat(ControllerGUICashbox.gui.getECCashboxIncomeField().getText());
                    float deliveryCash = ParserFloat.stringToFloat(ControllerGUICashbox.gui.getECAdminDepositsField().getText());
                    float deliveryWaiter = ParserFloat.stringToFloat(ControllerGUICashbox.gui.getECWaiterDepositsField().getText());
                    float balance = ParserFloat.stringToFloat(ControllerGUICashbox.gui.getECBalanceField().getText());
                    crudCashbox.create(turn, balance, collect, entryCash, spend, withdrawal, deliveryCash, deliveryWaiter);
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
                    ControllerGUICashbox.reloadAdminDeposits();
                    ControllerGUICashbox.reloadExistantCashbox();
                    ControllerGUICashbox.reloadWaiterDeposits();
                    ControllerGUICashbox.reloadWithdrawals();
                    ControllerGUICashbox.reloadDialyCashbox();
                    float balance = ParserFloat.stringToFloat(ControllerGUICashbox.gui.getECBalanceField().getText());
                    GUICloseTurnTarde guiCloseTurnTarde = new GUICloseTurnTarde(guiMain, true, crudAdmin.getAdmins(), balance);
                    guiCloseTurnTarde.setLocationRelativeTo(guiMain);
                    guiCloseTurnTarde.setVisible(true);
                    if (guiCloseTurnTarde.getReturnStatus() == guiCloseTurnTarde.RET_OK) {
                        crudWithdrawal.create(guiCloseTurnTarde.getIdAdminSelected(), "cierre de caja", guiCloseTurnTarde.getAmountWithdrawal());
                        //ACA LLAMAR PARA GUARDAR RESUMEN DIARIO.
                        ControllerGuiSummaryCashbox.saveResume();
                    } else {
                        return;// salgo de todo sin cerar el turno si no acepto
                    }                            //HACER RESUMEN ALAN???
                    ControllerGUICashbox.reloadAdminDeposits();
                    ControllerGUICashbox.reloadExistantCashbox();
                    ControllerGUICashbox.reloadWaiterDeposits();
                    ControllerGUICashbox.reloadWithdrawals();
                    ControllerGUICashbox.reloadDialyCashbox();
                    float collect = crudOrder.totalEarn() + crudOrder.getAllExceptions();
                    String turn = crudTurn.getTurn();
                    Map lastTurn = crudCashbox.getLast("M"); //obtengo el turno mañana para restarle todo eso
                    float withdrawal = ParserFloat.stringToFloat(ControllerGUICashbox.gui.getECWithdrawalsField().getText())-(float)lastTurn.get("withdrawal");
                    float spend = ParserFloat.stringToFloat(ControllerGUICashbox.gui.getECCashboxExpensesField().getText())-(float)lastTurn.get("spend");
                    float entryCash = ParserFloat.stringToFloat(ControllerGUICashbox.gui.getECCashboxIncomeField().getText())-(float)lastTurn.get("entry_cash");
                    float deliveryCash = ParserFloat.stringToFloat(ControllerGUICashbox.gui.getECAdminDepositsField().getText())-(float)lastTurn.get("delivery_cash");
                    float deliveryWaiter = ParserFloat.stringToFloat(ControllerGUICashbox.gui.getECWaiterDepositsField().getText())-(float)lastTurn.get("delivery_waiter");
                    balance = ParserFloat.stringToFloat(ControllerGUICashbox.gui.getECBalanceField().getText());
                    
                    
                    crudCashbox.create(turn, balance, collect, entryCash, spend, withdrawal, deliveryCash, deliveryWaiter);

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
                    crudDeposit.deleteWaiterDeposits();
                    crudDeposit.deleteAdminDeposits();
                    crudDeposit.deleteIncomes();
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
                guiCashbox.setMaximum(true);
            } catch (PropertyVetoException ex) {
                Logger.getLogger(ControllerMain.class.getName()).log(Level.SEVERE, null, ex);
            }
            try {
                ControllerGUICashbox.reloadWithdrawals();
                ControllerGUICashbox.reloadWaiterDeposits();
                ControllerGUICashbox.reloadAdminDeposits();
                ControllerGUICashbox.reloadExpenses();
                ControllerGUICashbox.reloadExistantCashbox();
                ControllerGUICashbox.reloadDialyCashbox();
                ControllerGUICashbox.ECReloadBalance();
                ControllerGUICashbox.blockButtons();
            } catch (RemoteException ex) {
                Logger.getLogger(ControllerGuiOpenTurn.class.getName()).log(Level.SEVERE, null, ex);
            }
            guiCashbox.setVisible(true);
            guiCashbox.toFront();

        }
        if (e.getSource() == guiOpenTurn.getBtnSeeAfternoon()) {
            try {
                guiCashbox.setMaximum(true);
            } catch (PropertyVetoException ex) {
                Logger.getLogger(ControllerMain.class.getName()).log(Level.SEVERE, null, ex);
            }
            try {
                ControllerGUICashbox.reloadWithdrawals();
                ControllerGUICashbox.reloadWaiterDeposits();
                ControllerGUICashbox.reloadAdminDeposits();
                ControllerGUICashbox.reloadExpenses();
                ControllerGUICashbox.reloadExistantCashbox();
                ControllerGUICashbox.reloadDialyCashbox();
                ControllerGUICashbox.ECReloadBalance();
                ControllerGUICashbox.blockButtons();
            } catch (RemoteException ex) {
                Logger.getLogger(ControllerGuiOpenTurn.class.getName()).log(Level.SEVERE, null, ex);
            }
            guiCashbox.setVisible(true);
            guiCashbox.toFront();

        }
        if (e.getSource() == guiOpenTurn.getBtnResume()) {
            guiSummaryCashbox.setVisible(true);
            guiSummaryCashbox.setLocationRelativeTo(null);
            try {
                controllerGuiSummaryCashbox.loadData();
                ControllerGuiSummaryCashbox.loadTableOfAdmins();
            } catch (RemoteException ex) {
                Logger.getLogger(ControllerGuiOpenTurn.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}
