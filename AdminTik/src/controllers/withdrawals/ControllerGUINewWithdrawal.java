/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers.withdrawals;

import controllers.cashbox.ControllerGUICashbox;
import gui.withdrawal.GUINewWithdrawal;
import interfaces.InterfaceAdmin;
import interfaces.withdrawals.InterfaceWithdrawal;
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
public class ControllerGUINewWithdrawal implements ActionListener{

    private final GUINewWithdrawal guiNewWithdrawal;
    private final InterfaceWithdrawal withdrawal;
    private final InterfaceAdmin admin;
    
    
    public ControllerGUINewWithdrawal(GUINewWithdrawal gui) throws RemoteException, NotBoundException{
        this.guiNewWithdrawal = gui;
        this.withdrawal = (InterfaceWithdrawal) InterfaceName.registry.lookup(InterfaceName.CRUDWithdrawal);
        this.admin = (InterfaceAdmin) InterfaceName.registry.lookup(InterfaceName.CRUDAdmin);
        
        this.guiNewWithdrawal.setActionListener(this);
    
        loadAdmins();
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("OK")){
            try {
                int admin_id = Integer.parseInt(guiNewWithdrawal.getAdminComboBox().getSelectedItem().toString().split(" - ")[0]);
                withdrawal.create(admin_id, guiNewWithdrawal.getDescriptionTxtArea().getText(), Float.parseFloat(guiNewWithdrawal.getAmountTxtField().getText()));
            } catch (RemoteException ex) {
                Logger.getLogger(ControllerGUINewWithdrawal.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        Integer resp = JOptionPane.showConfirmDialog(guiNewWithdrawal, "¿Desea cargar otro retiro?", null, JOptionPane.YES_NO_OPTION);
        if (resp == JOptionPane.NO_OPTION){
            guiNewWithdrawal.dispose();
        } else {
            guiNewWithdrawal.getAdminComboBox().setSelectedIndex(0);
            guiNewWithdrawal.getAmountTxtField().setText("");
            guiNewWithdrawal.getDescriptionTxtArea().setText("");
        }
        try {
            ControllerGUICashbox.reloadWithdrawals();
        } catch (RemoteException ex) {
            Logger.getLogger(ControllerGUINewWithdrawal.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void loadAdmins() throws RemoteException {
        for(Map a : admin.getAdmins()){
            guiNewWithdrawal.getAdminComboBox().addItem(a.get("id") + " - "+a.get("name"));
        }
    }
    
}
