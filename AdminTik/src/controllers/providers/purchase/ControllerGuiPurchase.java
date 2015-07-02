/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers.providers.purchase;

import controllers.ControllerMain;
import controllers.providers.ControllerGuiCRUDProviders;
import gui.providers.purchases.GuiAddProductToPurchase;
import gui.providers.purchases.GuiPayPurchase;
import gui.providers.purchases.GuiPurchase;
import interfaces.InterfaceAdmin;
import interfaces.InterfacePproduct;
import interfaces.InterfaceTurn;
import interfaces.cashbox.expenses.InterfaceExpenses;
import interfaces.deposits.InterfaceDeposit;
import interfaces.providers.InterfaceProvider;
import interfaces.providers.InterfaceProvidersSearch;
import interfaces.providers.payments.InterfacePayments;
import interfaces.providers.purchases.InterfacePurchase;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.text.DecimalFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.event.CellEditorListener;
import javax.swing.event.ChangeEvent;
import javax.swing.table.DefaultTableModel;
import utils.Config;
import utils.Dates;
import utils.InterfaceName;
import utils.Pair;
import utils.ParserFloat;

/**
 *
 * @author nico
 */
public class ControllerGuiPurchase implements ActionListener, CellEditorListener {

    private final InterfacePurchase interfacePurchase;
    private final GuiPurchase guiPurchase;
    private final InterfacePproduct interfacePproduct;
    private final InterfaceProvidersSearch interfaceProvidersSearch;
    private final InterfaceAdmin interfaceAdmin;
    private final InterfaceProvider interfaceProvider;
    private final InterfacePayments interfacePayments;
    private final InterfaceExpenses interfaceExpenses;
    private final InterfaceDeposit deposit;
    private final InterfaceTurn interfaceTurn;


    private final DefaultTableModel tblDefaultProvider;
    private final DefaultTableModel tblDefaultProduct;
    private final DefaultTableModel tblDefaultPurchase;

    private final JTable tblProvider;
    private final JTable tblProduct;
    private final JTable tblPurchase;

    private List<Map> productsList;
    private List<Map> providersList;

    /**
     *
     * @param guiPurchase
     * @throws NotBoundException
     * @throws MalformedURLException
     * @throws RemoteException
     */
    public ControllerGuiPurchase(final GuiPurchase guiPurchase) throws NotBoundException, MalformedURLException, RemoteException {
        this.interfacePurchase = (InterfacePurchase) InterfaceName.registry.lookup(InterfaceName.CRUDPurchase);
        this.interfacePproduct = (InterfacePproduct) InterfaceName.registry.lookup(InterfaceName.CRUDPproduct);
        this.interfaceProvidersSearch = (InterfaceProvidersSearch) InterfaceName.registry.lookup(InterfaceName.providersSearch);
        this.interfaceAdmin = (InterfaceAdmin) InterfaceName.registry.lookup(InterfaceName.CRUDAdmin);
        this.interfaceProvider = (InterfaceProvider) InterfaceName.registry.lookup(InterfaceName.CRUDProvider);
        this.interfacePayments = (InterfacePayments) InterfaceName.registry.lookup(InterfaceName.CRUDpayments);
        this.interfaceExpenses = (InterfaceExpenses) InterfaceName.registry.lookup(InterfaceName.CRUDExpenses);
        this.deposit = (InterfaceDeposit) InterfaceName.registry.lookup(InterfaceName.CRUDDeposit);
        this.interfaceTurn= (InterfaceTurn) InterfaceName.registry.lookup(InterfaceName.CRUDTurn);
       
        this.guiPurchase = guiPurchase;
        this.tblDefaultProduct = this.guiPurchase.getTblDefaultProduct();
        this.tblDefaultProvider = this.guiPurchase.getTblDefaultProvider();
        this.tblDefaultPurchase = this.guiPurchase.getTblDefaultPurchase();
        this.tblProduct = this.guiPurchase.getTblProduct();
        this.tblProvider = this.guiPurchase.getTblProvider();
        this.tblPurchase = this.guiPurchase.getTblPurchase();

        this.guiPurchase.getTxtSearchProduct().addKeyListener(new java.awt.event.KeyAdapter() {
            @Override
            public void keyReleased(java.awt.event.KeyEvent evt) {
                try {
                    productsList = interfacePproduct.getPproducts(guiPurchase.getTxtSearchProduct().getText());
                    loadProductsTable(productsList);
                } catch (RemoteException ex) {
                    Logger.getLogger(ControllerGuiPurchase.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
        this.guiPurchase.getTxtSearchProvider().addKeyListener(new java.awt.event.KeyAdapter() {
            @Override
            public void keyReleased(java.awt.event.KeyEvent evt) {
                try {
                    //hago la busqueda de proveedores en base a ese texto
                    providersList = interfaceProvidersSearch.searchProviders(guiPurchase.getTxtSearchProvider().getText());
                    //cargo los proveedores en la tabla
                    loadProvidersTable(providersList);
                } catch (RemoteException ex) {
                    Logger.getLogger(ControllerGuiPurchase.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
        tblProvider.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblProviderMouseClicked(evt);
            }
        });
        tblProduct.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                try {
                    tblProductMouseClicked(evt);
                } catch (RemoteException ex) {
                    Logger.getLogger(ControllerGuiPurchase.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
        this.guiPurchase.clickCancel();
        this.guiPurchase.setActionListener(this);
    }

    private void tblProviderMouseClicked(MouseEvent evt) {
        if (evt.getClickCount() == 2) {
            int row = guiPurchase.getTblProvider().getSelectedRow();
            if (row > -1) {
                Integer id = (Integer) tblProvider.getValueAt(row, 0);
                String name = (String) tblProvider.getValueAt(row, 1);
                guiPurchase.getLblProvider().setText(name);
                guiPurchase.getLblIdProvider().setText(id.toString());
            }
        }
    }

    private void tblProductMouseClicked(MouseEvent evt) throws RemoteException {
        if (evt.getClickCount() == 2) {
            int row = guiPurchase.getTblProduct().getSelectedRow();
            if (row > -1) {
                Integer id = (Integer) tblProduct.getValueAt(row, 0);
                if (noExistsInPurchase(id)) {
                    Map<String, Object> product = interfacePproduct.getPproduct(id);
                    if (product != null) {
                        String measureUnit = (String) product.get("measure_unit");
                        Float totalPrice = (float) product.get("unit_price");
                        Float amount = new Float(1);
                        GuiAddProductToPurchase guiAdd = new GuiAddProductToPurchase(ControllerMain.guiMain, true, measureUnit, totalPrice);
                        guiAdd.setLocationRelativeTo(guiPurchase);
                        guiAdd.setVisible(true);
                        if (guiAdd.getReturnStatus() == GuiAddProductToPurchase.RET_OK) {
                            totalPrice = guiAdd.getReturnCost();
                            amount = guiAdd.getReturnAmount();
                            Object[] o = new Object[6];
                            o[0] = (product.get("id"));
                            o[1] = (product.get("name"));
                            o[2] = ParserFloat.floatToString(amount);
                            switch (measureUnit) {
                                case "gr":
                                    o[3] = "Kg";
                                    break;
                                case "ml":
                                    o[3] = "L";
                                    break;
                                case "unitario":
                                    o[3] = "unitario";
                                    break;
                            }
                            o[4] = ParserFloat.floatToString(totalPrice / amount);
                            o[5] = ParserFloat.floatToString(totalPrice);
                            tblDefaultPurchase.addRow(o);
                            guiPurchase.getTxtCost().setText(ParserFloat.floatToString(calculateCost()));
                            setCellEditor();

                        }
                    }
                } else {
                    JOptionPane.showMessageDialog(guiPurchase, "¡Producto ya cargado!", "", JOptionPane.INFORMATION_MESSAGE);
                }
            }
        }
    }

    private void loadAll() throws RemoteException {
        //hago la busqueda de proveedores en base a ese texto
        providersList = interfaceProvidersSearch.searchProviders("");
        //cargo los proveedores en la tabla
        loadProvidersTable(providersList);
        productsList = interfacePproduct.getPproducts("");
        loadProductsTable(productsList);
    }

    /**
     *
     */
    public void setCellEditor() {
        for (int i = 0; i < tblPurchase.getRowCount(); i++) {
            tblPurchase.getCellEditor(i, 2).addCellEditorListener(this);
            tblPurchase.getCellEditor(i, 4).addCellEditorListener(this);
        }
    }

    private void loadProvidersTable(List<Map> providersList) throws RemoteException {
        tblDefaultProvider.setRowCount(0);//limpio la tabla antes de cargarla nuevamente
        if (providersList != null) { //si hay proveedores los cargo en la gui
            Map<String, Object> providerMap;
            Object[] o = new Object[2];
            Iterator<Map> providerMapItr = providersList.iterator();
            while (providerMapItr.hasNext()) {
                providerMap = providerMapItr.next();
                o[0] = (providerMap.get("id"));
                o[1] = (providerMap.get("name"));
                tblDefaultProvider.addRow(o);
            }

        }

    }

    private boolean noExistsInPurchase(Integer id) {
        boolean noExists = true;
        for (int i = 0; i < tblPurchase.getRowCount() && noExists; i++) {
            noExists = !tblPurchase.getValueAt(i, 0).equals(id);
        }
        return noExists;
    }

    private float calculateCost() {
        float result = 0;
        for (int i = 0; i < tblPurchase.getRowCount(); i++) {
            float amount = ParserFloat.stringToFloat((String) tblPurchase.getValueAt(i, 2));
            float cost = ParserFloat.stringToFloat((String) tblPurchase.getValueAt(i, 4));
            tblPurchase.setValueAt(ParserFloat.floatToString(amount), i, 2);
            tblPurchase.setValueAt(ParserFloat.floatToString(cost), i, 4);
            tblPurchase.setValueAt(ParserFloat.floatToString(amount * cost), i, 5);
            result += amount * cost;
        }
        return result;
    }

    private void loadProductsTable(List<Map> productsList) throws RemoteException {
        tblDefaultProduct.setRowCount(0);//limpio la tabla antes de cargarla nuevamente
        if (productsList != null) { //si hay productos los cargo en la gui
            Map<String, Object> productMap;
            Object[] o = new Object[2];
            Iterator<Map> productMapItr = productsList.iterator();
            while (productMapItr.hasNext()) {
                productMap = productMapItr.next();
                o[0] = (productMap.get("id"));
                o[1] = (productMap.get("name"));
                tblDefaultProduct.addRow(o);
            }
        }
    }

    private Integer makePurchase() throws RemoteException {
        float cost = ParserFloat.stringToFloat(guiPurchase.getTxtCost().getText());
        float paid = 0;
        float totalPaid = 0;
        String messageAux = "";
        String datePaid = null;
        String nameAdmin = "";
        Integer providerId = Integer.valueOf(guiPurchase.getLblIdProvider().getText());
        datePaid = Dates.dateToMySQLDate(Calendar.getInstance().getTime(), false);
        String datePurchase = Dates.dateToMySQLDate(guiPurchase.getDatePurchase().getDate(), false);
        LinkedList<Pair<Integer, Pair<Float, Float>>> products = new LinkedList<>();
        for (int i = 0; i < tblPurchase.getRowCount(); i++) {
            float amount = ParserFloat.stringToFloat((String) tblPurchase.getValueAt(i, 2));
            float costProduct = ParserFloat.stringToFloat((String) tblPurchase.getValueAt(i, 4));
            Integer idProduct = (Integer) tblPurchase.getValueAt(i, 0);
            Pair<Float, Float> amountPrice = new Pair(amount, costProduct);
            Pair<Integer, Pair<Float, Float>> pair = new Pair<>(idProduct, amountPrice);
            products.add(pair);
        }
        if (guiPurchase.getBoxPay().isSelected()) {//paga el total de la factura
            List<Map> admins = interfaceAdmin.getAdmins();
            GuiPayPurchase guiPayPurchase = new GuiPayPurchase(ControllerMain.guiMain, true, cost, admins);
            guiPayPurchase.setLocationRelativeTo(guiPurchase);
            guiPayPurchase.setVisible(true);
            if (guiPayPurchase.getReturnStatus() == GuiPayPurchase.RET_OK) {
                totalPaid = guiPayPurchase.getPayAdmin() + guiPayPurchase.getPayBox();
                if (totalPaid <= cost) {
                    paid = totalPaid;
                } else {
                    paid = cost;

                    messageAux = " y con el resto se saldó las " + interfaceProvider.payPurchases(providerId, totalPaid - paid);
                }
                nameAdmin = guiPayPurchase.getNameAdmin();
                if (guiPayPurchase.getPayAdmin() > 0) {
                    
                    /**
                     * ACA DEBO CREAR UNA ENTREGA DEL MOZO
                     * SIEMPRE Y CUANDO LO QUE ENTREGA MOZO ES MAYOR A 0
                     */
                    int idAdmin=-1;
                    Iterator<Map> it= admins.iterator();
                    while(it.hasNext()&& idAdmin==-1){
                        Map a= it.next();
                        if(((String)a.get("name"))==nameAdmin)
                            idAdmin=(Integer)a.get("id");
                    }
                    deposit.createAdminDeposit(idAdmin, guiPayPurchase.getPayAdmin());
                    controllers.cashbox.ControllerGUICashbox.reloadAdminDeposits();
                }
            }
        }
        int idPurchase = interfacePurchase.create(cost, paid, datePurchase, providerId, datePaid, products);
        if (guiPurchase.getBoxPay().isSelected()) {
            interfacePayments.createPayment(providerId, "Se pagó " + ParserFloat.floatToString(paid) + " de la factura con id " + idPurchase + messageAux, totalPaid, idPurchase, datePurchase, nameAdmin);
            interfaceExpenses.createExpense(2, "Se le pagó la compra con id " + idPurchase, totalPaid, idPurchase, providerId, interfaceTurn.getTurn());
        } else {
            interfaceProvider.payPurchases(providerId, 0);
        }
        return idPurchase;//retorna el id

    }

    /**
     *
     * @param ae
     */
    @Override
    public void actionPerformed(ActionEvent ae
    ) {
        if (ae.getSource().equals(guiPurchase.getBtnNew())) {
            guiPurchase.clickNew();
            try {
                loadAll();
            } catch (RemoteException ex) {
                Logger.getLogger(ControllerGuiPurchase.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        if (ae.getSource().equals(guiPurchase.getBtnCancel())) {
            guiPurchase.clickCancel();
        }

        if (ae.getSource().equals(guiPurchase.getBtnPurchase())) {
            if (!guiPurchase.getLblIdProvider().getText().equals("")) {
                try {
                    if (makePurchase() != null) {
                        JOptionPane.showMessageDialog(guiPurchase, "Se ha cargado la compra correctamente", "¡exito!", JOptionPane.INFORMATION_MESSAGE);
                        guiPurchase.clickCancel();
                    } else {
                        JOptionPane.showMessageDialog(guiPurchase, "Ocurrio un error, revise los datos", "¡Error!", JOptionPane.ERROR_MESSAGE);
                    }

                } catch (RemoteException ex) {
                    Logger.getLogger(ControllerGuiPurchase.class.getName()).log(Level.SEVERE, null, ex);
                }
            } else {
                JOptionPane.showMessageDialog(guiPurchase, "Proveedor vacio, seleccione uno", "¡Error!", JOptionPane.ERROR_MESSAGE);
            }
        }

    }

    /**
     *
     * @param ce
     */
    @Override
    public void editingStopped(ChangeEvent ce
    ) {
        guiPurchase.getTxtCost().setText(ParserFloat.floatToString(calculateCost()));
    }

    /**
     *
     * @param ce
     */
    @Override
    public void editingCanceled(ChangeEvent ce
    ) {
    }

}
