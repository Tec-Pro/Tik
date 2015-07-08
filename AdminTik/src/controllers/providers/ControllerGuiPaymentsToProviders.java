/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers.providers;

import controllers.ControllerGuiCRUDUser;
import gui.providers.GuiPaymentsToProviders;
import interfaces.providers.InterfaceProviderCategory;
import interfaces.providers.payments.InterfacePayments;
import interfaces.providers.purchases.InterfacePurchase;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.table.DefaultTableModel;
import utils.InterfaceName;

/**
 *
 * @author nico
 */
public class ControllerGuiPaymentsToProviders {

    private GuiPaymentsToProviders guiPTP;
    private final InterfacePayments interfacePayments;
    private int current_id;

    public ControllerGuiPaymentsToProviders(GuiPaymentsToProviders guiPTP, int current_id) throws RemoteException, NotBoundException {
        this.guiPTP = guiPTP;
        this.interfacePayments = (InterfacePayments) InterfaceName.registry.lookup(InterfaceName.CRUDpayments);
       this.current_id= current_id;
        guiPTP.getDateFrom().addPropertyChangeListener(new PropertyChangeListener() {
            @Override
            public void propertyChange(PropertyChangeEvent evt) {
                try {
                    loadPayments(current_id, utils.Dates.dateToMySQLDate(guiPTP.getDateFrom().getDate(), false), utils.Dates.dateToMySQLDate(guiPTP.getDateTo().getDate(), false));
                } catch (RemoteException ex) {
                    Logger.getLogger(ControllerGuiCRUDUser.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
        guiPTP.getDateTo().addPropertyChangeListener(new PropertyChangeListener() {
            @Override
            public void propertyChange(PropertyChangeEvent evt) {
                try {
                    loadPayments(current_id, utils.Dates.dateToMySQLDate(guiPTP.getDateFrom().getDate(), false), utils.Dates.dateToMySQLDate(guiPTP.getDateTo().getDate(), false));
                } catch (RemoteException ex) {
                    Logger.getLogger(ControllerGuiCRUDUser.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
        loadPayments(current_id, utils.Dates.dateToMySQLDate(guiPTP.getDateFrom().getDate(), false), utils.Dates.dateToMySQLDate(guiPTP.getDateTo().getDate(), false));
    }

    private void loadPayments(int id, String from, String to) throws RemoteException {
        List<Map> payments = interfacePayments.getPayments(id, from, to);
        Iterator<Map> it = payments.iterator();
        DefaultTableModel tblMdl = (DefaultTableModel) guiPTP.getTablePaymentsToProviders().getModel();
        tblMdl.setRowCount(0);
        while (it.hasNext()) {
            Map<String, Object> payment = it.next();
            Object rowDtm[] = new Object[5];
            rowDtm[0] = utils.Dates.dateToMySQLDate((Date) payment.get("date"), true);
            rowDtm[1] = utils.ParserFloat.floatToString((float) payment.get("amount"));
            rowDtm[2] = payment.get("detail");
            if (payment.get("purchase_id") == null) {
                rowDtm[3] = "";
            } else {
                rowDtm[3] = payment.get("purchase_id");
            }
            tblMdl.addRow(rowDtm);
        }
    }
}
