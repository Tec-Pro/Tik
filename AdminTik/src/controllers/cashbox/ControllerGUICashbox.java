/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers.cashbox;

import controllers.ControllerMain;
//import controllers.withdrawals.ControllerGUINewWithdrawal;
import gui.cashbox.GUICashbox;
//import gui.withdrawal.GUINewWithdrawal;
import interfaces.InterfaceAdmin;
import interfaces.InterfaceUser;
//import interfaces.deposits.InterfaceDeposits;
//import interfaces.withdrawals.InterfaceWithdrawal;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import utils.InterfaceName;

/**
 *
 * @author joako
 */
public class ControllerGUICashbox implements ActionListener{
    private static GUICashbox gui;
//    private static InterfaceWithdrawal withdrawal;
//    private static InterfaceDeposits deposits;
    private static InterfaceAdmin admin;
    private static InterfaceUser user;
    
    public ControllerGUICashbox(GUICashbox guiCashbox) throws RemoteException, NotBoundException{
        gui = guiCashbox;
//        withdrawal = (InterfaceWithdrawal) InterfaceName.registry.lookup(InterfaceName.CRUDWithdrawal);
//        deposits = (InterfaceDeposits) InterfaceName.registry.lookup(InterfaceName.CRUDDeposits);
        admin = (InterfaceAdmin) InterfaceName.registry.lookup(InterfaceName.CRUDAdmin);
        user = (InterfaceUser) InterfaceName.registry.lookup(InterfaceName.CRUDUser);
        gui.setActionListener(this);
        
        //loadWithdrawals();
        //loadWaiterDeposits();
        //loadAdminDeposits();
    }
    @Override
    public void actionPerformed(ActionEvent e) {
/*        if (e.getActionCommand().equals("NUEVO RETIRO")){
            GUINewWithdrawal guiNewWithdrawal = new GUINewWithdrawal(ControllerMain.guiMain, true);
            try {
                ControllerGUINewWithdrawal controller = new ControllerGUINewWithdrawal(guiNewWithdrawal);
                guiNewWithdrawal.setVisible(true);
                guiNewWithdrawal.toFront();
            } catch (RemoteException | NotBoundException ex) {
                Logger.getLogger(ControllerGUICashbox.class.getName()).log(Level.SEVERE, null, ex);
            }
            
        }*/
    }

    public static void reloadWithdrawals() throws RemoteException{
        loadWithdrawals();
    }
    
    private static void loadWithdrawals() throws RemoteException {
       /* List<Map> withdrawalList = withdrawal.getWithdrawalsForDate("'"+java.time.LocalDate.now().toString()+"'");
        gui.getWithdrawalsTableModel().setRowCount(0);
        Object[] o = new Object[3];
        for (Map w : withdrawalList){
            o[0] = admin.getAdmin((int) w.get("admin_id")).get("name");
            o[1] = w.get("created_at");
            o[2] = w.get("amount");
            gui.getWithdrawalsTableModel().addRow(o);
        } */
    }

    private static void loadWaiterDeposits() throws RemoteException {
       /* List<Map> waiterDepositsList = deposits.getAllWaiterDeposits();
        gui.getWaiterDepositsTableModel().setRowCount(0);
        Object[] o = new Object[3];
        for (Map d : waiterDepositsList){
            o[0] = user.getUser((int) d.get("waiter_id"));
            o[1] = d.get("created_at");
            o[2] = d.get("amount");
            gui.getWaiterDepositsTableModel().addRow(o);
        }*/
    }

    private void loadAdminDeposits() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
