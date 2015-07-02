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
import gui.deposit.GUINewDeposit;
import gui.withdrawal.GUINewWithdrawal;
import interfaces.InterfaceAdmin;
import interfaces.InterfaceUser;
import interfaces.deposits.InterfaceDeposit;
import interfaces.withdrawals.InterfaceWithdrawal;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import utils.InterfaceName;

/**
 *
 * @author joako
 */
public class ControllerGUICashbox implements ActionListener {

    private static GUICashbox gui;
    private static InterfaceWithdrawal withdrawal;
    private static InterfaceDeposit deposit;
    private static InterfaceAdmin admin;
    private static InterfaceUser user;
    
    public ControllerGUICashbox(GUICashbox guiCashbox) throws RemoteException, NotBoundException {
        gui = guiCashbox;
        withdrawal = (InterfaceWithdrawal) InterfaceName.registry.lookup(InterfaceName.CRUDWithdrawal);
        deposit = (InterfaceDeposit) InterfaceName.registry.lookup(InterfaceName.CRUDDeposit);
        admin = (InterfaceAdmin) InterfaceName.registry.lookup(InterfaceName.CRUDAdmin);
        user = (InterfaceUser) InterfaceName.registry.lookup(InterfaceName.CRUDUser);
        gui.setActionListener(this);
        gui.getECInitialBalanceCheckBox().addItemListener(new ItemListener() {
        
            @Override
            public void itemStateChanged(ItemEvent e) {
                if (e.getStateChange() == ItemEvent.SELECTED) {
                    gui.getECInitialBalance().setEditable(true);
                } else {
                    gui.getECInitialBalance().setEditable(false);
    }
            }
        });
        loadWithdrawals();
        loadWaiterDeposits();
        loadAdminDeposits();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        switch (e.getActionCommand()) {
            case "NUEVO RETIRO":
            GUINewWithdrawal guiNewWithdrawal = new GUINewWithdrawal(ControllerMain.guiMain, true);
            try {
                ControllerGUINewWithdrawal controller = new ControllerGUINewWithdrawal(guiNewWithdrawal);
                guiNewWithdrawal.setVisible(true);
                guiNewWithdrawal.toFront();
            } catch (RemoteException | NotBoundException ex) {
                Logger.getLogger(ControllerGUICashbox.class.getName()).log(Level.SEVERE, null, ex);
            }
                break;
            case "ENTREGA MOZO": {
                GUINewDeposit guiNewDeposit = new GUINewDeposit(ControllerMain.guiMain, true);
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
            case "ENTREGA ADMIN": {
                GUINewDeposit guiNewDeposit = new GUINewDeposit(ControllerMain.guiMain, true);
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
    }

 

    public static void reloadWithdrawals() throws RemoteException {
        loadWithdrawals();
    }
    
    private static void loadWithdrawals() throws RemoteException {
        String date = new java.sql.Date(System.currentTimeMillis()).toString();
        List<Map> withdrawalList = withdrawal.getWithdrawalsForDate(date);
        gui.getWithdrawalsTableModel().setRowCount(0);
        Object[] o = new Object[3];
        Float total = 0.0f;
        for (Map w : withdrawalList) {
            o[0] = admin.getAdmin((int) w.get("admin_id")).get("name");
            o[1] = w.get("created_at");
            o[2] = String.format("%.2f", w.get("amount"));
            gui.getWithdrawalsTableModel().addRow(o);
            total = total + (Float) w.get("amount");
    }
        gui.getWithdrawalTotalField().setText(String.format("%.2f", total));
        gui.getECWithdrawalsField().setText(String.format("%.2f", total));
    }

    public static void reloadWaiterDeposits() throws RemoteException {
        loadWaiterDeposits();
    }

    private static void loadWaiterDeposits() throws RemoteException {
        String date = new java.sql.Date(System.currentTimeMillis()).toString();
        List<Map> depositsList = deposit.getWaitersDepositsForDate(date);
        gui.getWaiterDepositsTableModel().setRowCount(0);
        Object[] o = new Object[3];
        Float total = 0.0f;
        for (Map d : depositsList) {
            o[0] = user.getUser((int) d.get("waiter_id")).get("name");
            o[1] = d.get("created_at");
            o[2] = String.format("%.2f", d.get("amount"));
            gui.getWaiterDepositsTableModel().addRow(o);
            total = total + (Float) d.get("amount");
    }
        gui.getWaiterDepositsTotalField().setText(String.format("%.2f", total));
        gui.getECWaiterDeposits().setText(String.format("%.2f", total));
    }

    public static void reloadAdminDeposits() throws RemoteException {
        loadAdminDeposits();
    }
    
    private static void loadAdminDeposits() throws RemoteException {
        String date = new java.sql.Date(System.currentTimeMillis()).toString();
        List<Map> depositsList = deposit.getAdminsDepositsForDate(date);
        gui.getAdminDepositsTableModel().setRowCount(0);
        Object[] o = new Object[3];
        Float total = 0.0f;
        for (Map d : depositsList) {
            o[0] = admin.getAdmin((int) d.get("admin_id")).get("name");
            o[1] = d.get("created_at");
            o[2] = String.format("%.2f", d.get("amount"));
            gui.getAdminDepositsTableModel().addRow(o);
            total = total + (Float) d.get("amount");
}
        gui.getAdminDepositsTotalField().setText(String.format("%.2f", total));
        gui.getECAdminDeposits().setText(String.format("%.2f", total));
    }

}
