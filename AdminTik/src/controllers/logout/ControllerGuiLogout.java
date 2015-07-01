/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers.logout;

import gui.logout.GuiLogout;
import interfaces.InterfacePresence;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
public class ControllerGuiLogout implements ActionListener {

    int userId;
    GuiLogout guiLogout;
    private InterfacePresence crudPresence;
    private Set<Map> online;

    public ControllerGuiLogout(GuiLogout guiLogout) throws RemoteException, NotBoundException {
        this.guiLogout = guiLogout;
        crudPresence = (InterfacePresence) InterfaceName.registry.lookup(InterfaceName.CRUDPresence);
        updateOnline();
        userId = -1;
        guiLogout.clear();
        guiLogout.getcBoxEmployers().addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                changeEmployed();
            }
        });
        guiLogout.getTxtDelivery().addKeyListener(new java.awt.event.KeyAdapter() {
            @Override
            public void keyReleased(java.awt.event.KeyEvent evt) {
                calculateDif();
            }
        });
        guiLogout.setActionListener(this);
    }

     private void calculateDif(){
        float d = ParserFloat.stringToFloat(guiLogout.getTxtDelivery().getText());
        float e = ParserFloat.stringToFloat(guiLogout.getLblEarn().getText());
        guiLogout.getLblDif().setText(ParserFloat.floatToString(d-e));
     }
    
    private void changeEmployed() {
        if (guiLogout.getcBoxEmployers().getSelectedIndex() > -1) {
            String name = (String) guiLogout.getcBoxEmployers().getSelectedItem();
            String split[] = name.split("-");
            userId = Integer.parseInt(split[0]);
            String pos = split[2];
            if (pos.equals("Mozo")) {
                guiLogout.clear();
                guiLogout.getTxtDelivery().setEnabled(true);
                //calculo de cuanto recuado
                //ParserFloat
            } else {
                guiLogout.clear();
            }
        } else {
            userId = -1;
            guiLogout.clear();
        }
    }

    public void updateOnline() throws RemoteException {
        online = new HashSet<>();
        online.addAll(crudPresence.getWaiters());
        online.addAll(crudPresence.getCooks());
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        if (ae.getSource() == guiLogout.getBtnUpdate()) {
            try {
                updateOnline();
            } catch (RemoteException ex) {
                Logger.getLogger(ControllerGuiLogout.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        if (ae.getSource() == guiLogout.getBtnClose()) {
            try {
                if (userId > -1) {
                    crudPresence.logout(userId);
                    updateOnline();
                    guiLogout.clear();
                }
            } catch (RemoteException ex) {
                Logger.getLogger(ControllerGuiLogout.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        if (ae.getSource() == guiLogout.getBtnCloseAllKitchen()) {
            try {
                crudPresence.logoutAllCooks();
                JOptionPane.showMessageDialog(guiLogout, "Las sesiones se cerraron exitosamente");
                updateOnline();
                guiLogout.clear();
            } catch (RemoteException ex) {
                Logger.getLogger(ControllerGuiLogout.class.getName()).log(Level.SEVERE, null, ex);
            }

        }

    }
}
