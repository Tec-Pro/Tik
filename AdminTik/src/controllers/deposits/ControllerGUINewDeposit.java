/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers.deposits;

import gui.deposit.GUINewDeposit;
import interfaces.InterfaceAdmin;
import interfaces.InterfaceUser;
import interfaces.deposits.InterfaceDeposit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
 * @author joako
 */
public class ControllerGUINewDeposit implements ActionListener {

    private final GUINewDeposit guiNewDeposit;
    private final InterfaceDeposit deposit;
    private final InterfaceAdmin admin;
    private final InterfaceUser user;

    public ControllerGUINewDeposit(GUINewDeposit gui) throws RemoteException, NotBoundException {
        this.guiNewDeposit = gui;
        this.deposit = (InterfaceDeposit) InterfaceName.registry.lookup(InterfaceName.CRUDDeposit);
        System.out.println(deposit.toString());
        this.admin = (InterfaceAdmin) InterfaceName.registry.lookup(InterfaceName.CRUDAdmin);
        this.user = (InterfaceUser) InterfaceName.registry.lookup(InterfaceName.CRUDUser);

        guiNewDeposit.setActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        switch (e.getActionCommand()) {
            case "OK MOZO":
                int waiter_id = Integer.parseInt(guiNewDeposit.getDepositComboBox().getSelectedItem().toString().split(" - ")[0]);
                try {
                    deposit.createWaiterDeposit(waiter_id, ParserFloat.stringToFloat(guiNewDeposit.getAmountTxtField().getText()));
                    controllers.cashbox.ControllerGUICashbox.reloadWaiterDeposits();
                } catch (RemoteException ex) {
                    Logger.getLogger(ControllerGUINewDeposit.class.getName()).log(Level.SEVERE, null, ex);
                }
                break;
            case "OK ADMIN":
                int admin_id = Integer.parseInt(guiNewDeposit.getDepositComboBox().getSelectedItem().toString().split(" - ")[0]);
                try {
                    deposit.createAdminDeposit(admin_id, ParserFloat.stringToFloat(guiNewDeposit.getAmountTxtField().getText()));
                    controllers.cashbox.ControllerGUICashbox.reloadAdminDeposits();
                } catch (RemoteException ex) {
                    Logger.getLogger(ControllerGUINewDeposit.class.getName()).log(Level.SEVERE, null, ex);
                }
                break;
        }
        Integer resp = JOptionPane.showConfirmDialog(guiNewDeposit, "¿Desea cargar otro deposito?", null, JOptionPane.YES_NO_OPTION);
        if (resp == JOptionPane.NO_OPTION){
            guiNewDeposit.dispose();
        } else {
            guiNewDeposit.getAmountTxtField().setText("");
            guiNewDeposit.getDepositComboBox().setSelectedIndex(0);
        }
        try {
            controllers.cashbox.ControllerGUICashbox.ECReloadBalance();
        } catch (RemoteException ex) {
            Logger.getLogger(ControllerGUINewDeposit.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void loadComboBoxWaiters() throws RemoteException {
        for (Map w : user.getWaiters()) {
            guiNewDeposit.getDepositComboBox().addItem(w.get("id") + " - " + w.get("name"));
        }
    }

    public void loadComboBoxAdmins() throws RemoteException {
        for (Map a : admin.getAdmins()) {
            guiNewDeposit.getDepositComboBox().addItem(a.get("id") + " - " + a.get("name"));
        }
    }
}