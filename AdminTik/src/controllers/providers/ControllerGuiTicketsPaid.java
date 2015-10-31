/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers.providers;

import controllers.ControllerMain;
import gui.providers.GuiTicketDetails;
import gui.providers.GuiTicketsPaid;
import interfaces.providers.purchases.InterfacePurchase;
import interfaces.InterfacePproduct;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.List;
import java.util.Map;
import java.util.SortedMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import utils.Config;
import utils.InterfaceName;
import utils.Pair;

/**
 *
 * @author eze
 */
public class ControllerGuiTicketsPaid {

    private final GuiTicketsPaid guiTicketsPaid;
    private final InterfacePurchase interfacePurchase;
    private final InterfacePproduct interfacePProduct;
    private int currentProviderId;

    /**
     * Constructor del controlador encargado de la interacción entre la GUI
     * Tickets Paid y los Tickets de compra.
     *
     * @param guiTP interfaz de Tickets Paid
     * @param current_id id de proveedor.
     * @throws NotBoundException
     * @throws MalformedURLException
     * @throws RemoteException
     */
    public ControllerGuiTicketsPaid(GuiTicketsPaid guiTP, int current_id) throws NotBoundException, MalformedURLException, RemoteException {
        this.guiTicketsPaid = guiTP;

        //Busco los métodos del server.
        this.interfacePurchase = (InterfacePurchase) InterfaceName.registry.lookup(InterfaceName.CRUDPurchase);

        this.interfacePProduct = (InterfacePproduct) InterfaceName.registry.lookup(InterfaceName.CRUDPproduct);

        //Seteo el ID del provedor corriente.
        this.currentProviderId = current_id;

        //Obtengo las compras realizadas al proveedor corriente.
        List<Pair<SortedMap<String, Object>, List<Map>>> purchasesProvider = this.interfacePurchase.getPurchasesProvider(currentProviderId);

        //Cargo las compras realizadas en la tabla.
        loadTicketsTable(purchasesProvider);

        this.guiTicketsPaid.getTableProviderCurrentAccount().addMouseListener(new MouseAdapter() {

            @Override
            public void mouseClicked(MouseEvent evt) {
                //Si hago doble click sobre una compra, puedo ver sus detalles.
                if (evt.getClickCount() == 2) {
                    JTable target = (JTable) evt.getSource();
                    int row = target.getSelectedRow();
                    if (row != -1) {
                        //Muestro los detalles de la compra en un JDialog.
                        GuiTicketDetails guiDetails = new GuiTicketDetails(ControllerMain.guiMain, true);
                        try {
                            //Cargo los detalles de la compra.
                            loadTicketDetails((int) target.getValueAt(row, 0), guiDetails);
                        } catch (RemoteException ex) {
                            Logger.getLogger(ControllerGuiTicketsPaid.class.getName()).log(Level.SEVERE, null, ex);
                        }
                        //Muestro la GUI.
                        guiDetails.setVisible(true);
                    }
                }
            }
        });

        //Observo los cambios en las fechas.
        this.guiTicketsPaid.getDateFrom().addPropertyChangeListener(new PropertyChangeListener() {

            @Override
            public void propertyChange(PropertyChangeEvent evt) {
                try {
                    //Si hay una fecha seleccionada, limpio los componentes y filtro las compras.
                    if (guiTicketsPaid.getStringDateUntil() != null) {
                        guiTicketsPaid.cleanComponents();
                        List<Pair<SortedMap<String, Object>, List<Map>>> purchasesProvider = interfacePurchase.getProviderPurchasesBetweenDates(currentProviderId, guiTicketsPaid.getStringDateFrom(), guiTicketsPaid.getStringDateUntil());
                        //Cargo la tabla con las compras filtradas.
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
                    //Si hay una fecha seleccionada.
                    if (guiTicketsPaid.getStringDateFrom() != null) {
                        guiTicketsPaid.cleanComponents();
                        //Filtro las compras y las cargo en la tabla.
                        List<Pair<SortedMap<String, Object>, List<Map>>> purchasesProvider = interfacePurchase.getProviderPurchasesBetweenDates(currentProviderId, guiTicketsPaid.getStringDateFrom(), guiTicketsPaid.getStringDateUntil());
                        loadTicketsTable(purchasesProvider);
                    }
                } catch (RemoteException ex) {
                    Logger.getLogger(ControllerGuiTicketsPaid.class.getName()).log(Level.SEVERE, null, ex);
                }
            }

        });
    }

    /**
     * Función que carga las compras a un proveedor en la tabla.
     * @param purchasesProvider lista de compras a un proveedor.
     * @throws RemoteException 
     */
    private void loadTicketsTable(List<Pair<SortedMap<String, Object>, List<Map>>> purchasesProvider) throws RemoteException {
        if (!purchasesProvider.isEmpty()) {
            DefaultTableModel dtmPurchasesProvider = (DefaultTableModel) this.guiTicketsPaid.getTableProviderCurrentAccount().getModel();
            dtmPurchasesProvider.setRowCount(0);
            Object[] row = new Object[7];
            for (Pair<SortedMap<String, Object>, List<Map>> purchase : purchasesProvider) {
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
     * Función que carga los detalles de una compra en un JDialog nuevo.
     * @param ticket_id id de la compra de la que se quiere saber los detalles.
     * @param gui JDialog que contiene los detalles de la compra.
     * @throws RemoteException 
     */
    private void loadTicketDetails(int ticket_id, GuiTicketDetails gui) throws RemoteException {
        Pair<Map<String, Object>, List<Map>> purchase = interfacePurchase.getPurchase(ticket_id);
        gui.getTxtDate().setText(purchase.first().get("date").toString());
        gui.getTxtTotal().setText(purchase.first().get("cost").toString());
        gui.getTicketId().setText(purchase.first().get("id").toString());
        DefaultTableModel dtmTableDetails = (DefaultTableModel) gui.getTableTicketDetails().getModel();
        dtmTableDetails.setRowCount(0);
        Object[] row = new Object[6];
        for (Map<String, Object> product : purchase.second()) {
            Map<String, Object> p = interfacePProduct.getPproduct((Integer.parseInt( product.get("pproduct_id").toString())));
            row[0] = product.get("pproduct_id");
            row[1] = p.get("name");
            row[2] = product.get("amount");
            row[3] = p.get("measure_unit");
            if (p.get("measure_unit").equals("gr")) {
                row[4] = (float) p.get("unit_price") * 100;
            } else if (p.get("measure_unit").equals("ml")) {
                row[4] = (float) p.get("unit_price") * 1000;
            } else {
                row[4] = p.get("unit_price");
            }
            row[5] = product.get("final_price");
            dtmTableDetails.addRow(row);
        }
    }

    /**
     * @param currentProviderId the currentProviderId to set
     */
    public void setCurrentProviderId(int currentProviderId) {
        this.currentProviderId = currentProviderId;
    }
}
