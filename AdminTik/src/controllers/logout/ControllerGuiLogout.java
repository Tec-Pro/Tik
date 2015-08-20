/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers.logout;

import controllers.ControllerMain;
import controllers.cashbox.ControllerGUICashbox;
import gui.cashbox.GUIUserDiscounts;
import gui.logout.GuiLogout;
import interfaces.InterfaceOrder;
import interfaces.InterfacePresence;
import interfaces.InterfaceTurn;
import interfaces.deposits.InterfaceDeposit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.HashSet;
import java.util.List;
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
    private InterfaceOrder crudOrder;
    private InterfaceDeposit crudDeposit;
    private InterfaceTurn crudTurn;

    public ControllerGuiLogout(GuiLogout guiLogout) throws RemoteException, NotBoundException {
        this.guiLogout = guiLogout;
        crudPresence = (InterfacePresence) InterfaceName.registry.lookup(InterfaceName.CRUDPresence);
        crudOrder = (InterfaceOrder) InterfaceName.registry.lookup(InterfaceName.CRUDOrder);
        crudDeposit = (InterfaceDeposit) InterfaceName.registry.lookup(InterfaceName.CRUDDeposit);
        crudTurn = (InterfaceTurn) InterfaceName.registry.lookup(InterfaceName.CRUDTurn);
        updateOnline();
        userId = -1;
        guiLogout.clear();
        guiLogout.getcBoxEmployers().addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    changeEmployed();
                } catch (RemoteException ex) {
                    Logger.getLogger(ControllerGuiLogout.class.getName()).log(Level.SEVERE, null, ex);
                }
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

    /**
     * calcula la diferencia entre lo que vendio y lo que entrego
     */
    private void calculateDif() {
        float pd = ParserFloat.stringToFloat(guiLogout.getLblDelivery().getText());
        float d = ParserFloat.stringToFloat(guiLogout.getTxtDelivery().getText());
        float e = ParserFloat.stringToFloat(guiLogout.getLblEarn().getText());
        float ex = ParserFloat.stringToFloat(guiLogout.getLblException().getText());
        float desc = ParserFloat.stringToFloat(guiLogout.getLblDiscount().getText());
        guiLogout.getLblDif().setText(ParserFloat.floatToString(pd + d  - e - ex));
        guiLogout.getLblUndelivered().setText(ParserFloat.floatToString(e + ex  - pd));
    }

    /**
     * change selection listener
     */
    private void changeEmployed() throws RemoteException {
        if (guiLogout.getcBoxEmployers().getSelectedIndex() > -1) {
            String name = (String) guiLogout.getcBoxEmployers().getSelectedItem();
            String split[] = name.split("-");
            userId = Integer.parseInt(split[0]);
            String pos = split[2];
            if (pos.equals("Mozo")) {
                guiLogout.clear();
                guiLogout.getTxtDelivery().setText("0");
                guiLogout.getLblEarn().setText(ParserFloat.floatToString(crudOrder.EarnByUser(userId)));
                guiLogout.getLblException().setText(ParserFloat.floatToString(crudOrder.getExceptions(userId)));
                guiLogout.getLblDelivery().setText(ParserFloat.floatToString(crudDeposit.getWaiterDepositsTotalOnTurn(userId, crudTurn.getTurn())));

                guiLogout.getTxtDelivery().setEnabled(true);
                List<Map> prods = crudOrder.getCurrentDiscounts(userId);
                List<Map> efec = crudOrder.getCurrentDiscountsInEfective(userId);
                float totalProd = 0;
                for (Map p : prods) {
                    totalProd = totalProd + ((float) p.get("sell_price") * (float) p.get("quantity"));
                }
                float totalEfec = 0;
                for (Map p : efec) {
                    totalEfec = totalProd + ((float) p.get("discount"));
                }
                guiLogout.getLblDiscount().setText(ParserFloat.floatToString(totalEfec + totalProd));
                calculateDif();

            } else {
                guiLogout.clear();
            }
        } else {
            userId = -1;
            guiLogout.clear();
        }
    }

    /**
     * actualiza la lista de usuario online
     *
     * @throws RemoteException
     */
    public void updateOnline() throws RemoteException {
        online = new HashSet<>();
        online.addAll(crudPresence.getWaiters());
        online.addAll(crudPresence.getCooks());
        guiLogout.loadOnlineEmployers(online);
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
                    if(ParserFloat.stringToFloat(guiLogout.getTxtDelivery().getText())>0){
                        String name = (String) guiLogout.getcBoxEmployers().getSelectedItem();
                        String split[] = name.split("-");
                        userId = Integer.parseInt(split[0]);
                        String pos = split[2];
                        if (pos.equals("Mozo")) {
                            crudDeposit.createWaiterDeposit(userId, ParserFloat.stringToFloat(guiLogout.getTxtDelivery().getText()));
                        }
                        crudPresence.logout(userId);
                        updateOnline();
                        guiLogout.clear();
                    }else{
                        JOptionPane.showMessageDialog(guiLogout, "Debe ingresar un valor en la entrega", "La entrega no puede ser 0 ni negativo", JOptionPane.ERROR_MESSAGE);
                    }
                }
            } catch (RemoteException ex) {
                Logger.getLogger(ControllerGuiLogout.class.getName()).log(Level.SEVERE, null, ex);
            }
            try {
                ControllerGUICashbox.reloadDialyCashbox();
            } catch (RemoteException ex) {
                Logger.getLogger(ControllerGuiLogout.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        if (ae.getSource() == guiLogout.getBtnDiscounts()) {

            try {
                List<Map> prods = crudOrder.getCurrentDiscounts(userId);
                List<Map> efec = crudOrder.getCurrentDiscountsInEfective(userId);
                GUIUserDiscounts guiDiscounts = new GUIUserDiscounts(ControllerMain.guiMain, true, prods, efec);
                guiDiscounts.setLocationRelativeTo(guiLogout);
                guiDiscounts.setVisible(true);
            } catch (RemoteException ex) {
                Logger.getLogger(ControllerGuiLogout.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        /* if (ae.getSource() == guiLogout.getBtnCloseAllKitchen()) {
         try {
         crudPresence.logoutAllCooks();
         JOptionPane.showMessageDialog(guiLogout, "Las sesiones se cerraron exitosamente");
         updateOnline();
         guiLogout.clear();
         } catch (RemoteException ex) {
         Logger.getLogger(ControllerGuiLogout.class.getName()).log(Level.SEVERE, null, ex);
         }

         }*/

    }
}
