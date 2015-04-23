/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers.providers;

import gui.providers.GuiTicketsPaid;
import interfaces.providers.purchases.InterfacePurchase;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.table.DefaultTableModel;
import utils.Config;
import utils.Pair;

/**
 *
 * @author eze
 */
public class ControllerGuiTicketsPaid {

    private final GuiTicketsPaid guiTicketsPaid;
    private final InterfacePurchase interfacePurchase;
    private int currentProviderId;

    public ControllerGuiTicketsPaid(GuiTicketsPaid guiTP, int current_id) throws NotBoundException, MalformedURLException, RemoteException {
        this.guiTicketsPaid = guiTP;

        this.interfacePurchase = (InterfacePurchase) Naming.lookup("//" + Config.ip + "/CRUDPurchase");

        this.currentProviderId = current_id;

        List<Pair<Map<String, Object>, List<Map>>> purchasesProvider = this.interfacePurchase.getPurchasesProvider(currentProviderId);
        loadTicketsTable(purchasesProvider);

        this.guiTicketsPaid.getDateFrom().addPropertyChangeListener(new PropertyChangeListener() {

            @Override
            public void propertyChange(PropertyChangeEvent evt) {
                try {
                    if(guiTicketsPaid.getStringDateUntil() != null){
                    guiTicketsPaid.cleanComponents();
                    List<Pair<Map<String, Object>, List<Map>>> purchasesProvider = interfacePurchase.getProviderPurchasesBetweenDates(currentProviderId,guiTicketsPaid.getStringDateFrom(),guiTicketsPaid.getStringDateUntil());
                    loadTicketsTable(purchasesProvider);
                    }
                } catch (RemoteException ex) {
                    Logger.getLogger(ControllerGuiTicketsPaid.class.getName()).log(Level.SEVERE, null, ex);
                }
            }

        });

        this.guiTicketsPaid.getDateUntil().addPropertyChangeListener(new PropertyChangeListener() {

            @Override
            public void propertyChange(PropertyChangeEvent evt) {
                try {
                    if(guiTicketsPaid.getStringDateFrom() != null){
                    guiTicketsPaid.cleanComponents();
                    List<Pair<Map<String, Object>, List<Map>>> purchasesProvider = interfacePurchase.getProviderPurchasesBetweenDates(currentProviderId,guiTicketsPaid.getStringDateFrom(),guiTicketsPaid.getStringDateUntil());
                    loadTicketsTable(purchasesProvider);
                    }
                } catch (RemoteException ex) {
                    Logger.getLogger(ControllerGuiTicketsPaid.class.getName()).log(Level.SEVERE, null, ex);
                }
            }

        });
    }

    private void loadTicketsTable(List<Pair<Map<String, Object>, List<Map>>> purchasesProvider) throws RemoteException {
        if (!purchasesProvider.isEmpty()) {
            DefaultTableModel dtmPurchasesProvider = (DefaultTableModel) this.guiTicketsPaid.getTableProviderCurrentAccount().getModel();
            dtmPurchasesProvider.setRowCount(0);
            Object[] row = new Object[7];
            for (Pair<Map<String, Object>, List<Map>> purchase : purchasesProvider) {
                row[0] = purchase.first().get("id");
                row[1] = purchase.first().get("date");
                row[2] = purchase.first().get("date_paid");
                row[3] = purchase.first().get("cost");
                row[4] = purchase.first().get("paid");
                row[5] = (float) row[3] - (float) row[4];
                row[6] = (float) row[3] == (float) row[4];
                dtmPurchasesProvider.addRow(row);
            }
        }
    }

    /**
     * @param currentProviderId the currentProviderId to set
     */
    public void setCurrentProviderId(int currentProviderId) {
        this.currentProviderId = currentProviderId;
    }
}
