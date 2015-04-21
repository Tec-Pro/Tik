/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers.providers.purchase;

import controllers.providers.ControllerGuiCRUDProviders;
import gui.providers.purchases.GuiAddProductToPurchase;
import gui.providers.purchases.GuiPurchase;
import interfaces.InterfacePproduct;
import interfaces.providers.InterfaceProvider;
import interfaces.providers.InterfaceProvidersSearch;
import interfaces.providers.purchases.InterfacePurchase;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.text.DecimalFormat;
import java.util.Iterator;
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

    private final DefaultTableModel tblDefaultProvider;
    private final DefaultTableModel tblDefaultProduct;
    private final DefaultTableModel tblDefaultPurchase;

    private final JTable tblProvider;
    private final JTable tblProduct;
    private final JTable tblPurchase;

    private List<Map> productsList;
    private List<Map> providersList;
    

    public ControllerGuiPurchase(final GuiPurchase guiPurchase) throws NotBoundException, MalformedURLException, RemoteException {
        this.interfacePurchase = (InterfacePurchase) Naming.lookup("//" + Config.ip + "/CRUDPurchase");
        this.interfacePproduct = (InterfacePproduct) Naming.lookup("//" + Config.ip + "/CRUDPproduct");
        this.interfaceProvidersSearch = (InterfaceProvidersSearch) Naming.lookup("//" + Config.ip + "/providersSearch");
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
                if(noExistsInPurchase(id)){
                Map<String, Object> product = interfacePproduct.getPproduct(id);
                if (product != null) {
                    String measureUnit = (String) product.get("measure_unit");
                    Float unitPrice = (float) product.get("unit_price");
                    Float amount = new Float(1);
                    GuiAddProductToPurchase guiAdd = new GuiAddProductToPurchase(null, true, measureUnit, unitPrice * 1000);
                    guiAdd.setLocationRelativeTo(guiPurchase);
                    guiAdd.setVisible(true);
                    if (guiAdd.getReturnStatus() == GuiAddProductToPurchase.RET_OK) {
                        unitPrice = guiAdd.getReturnCost();
                        amount = guiAdd.getReturnAmount();
                        Object[] o = new Object[6];
                        o[0] = (product.get("id"));
                        o[1] = (product.get("name"));
                        o[2] = ParserFloat.floatToString(amount);
                        switch(measureUnit){
                            case "gr":
                                 o[3] = "Kg";
                                 break;
                            case "ml":
                                 o[3] = "L";
                                 break;   
                            case "u":
                                 o[3] = "U";
                                 break;                                
                        }
                        o[4] = ParserFloat.floatToString(unitPrice);
                        o[5] = ParserFloat.floatToString(unitPrice*amount);
                        tblDefaultPurchase.addRow(o);
                        guiPurchase.getTxtCost().setText(ParserFloat.floatToString(calculateCost()));
                                            setCellEditor();

                    }
                }
            }
                else{
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
    
    private boolean noExistsInPurchase(Integer id){
        boolean noExists= true;
        for(int i=0 ; i<tblPurchase.getRowCount()&& noExists;i++){
            noExists=!tblPurchase.getValueAt(i, 0).equals(id);
        }
        return noExists;
    }
    
        private float calculateCost(){
            float result=0;
        for(int i=0 ; i<tblPurchase.getRowCount();i++){
            float amount=ParserFloat.stringToFloat((String)tblPurchase.getValueAt(i, 2));
            float cost=ParserFloat.stringToFloat((String)tblPurchase.getValueAt(i, 4));
            tblPurchase.setValueAt(ParserFloat.floatToString(amount),i, 2);
            tblPurchase.setValueAt(ParserFloat.floatToString(cost),i, 4);
            tblPurchase.setValueAt(ParserFloat.floatToString(amount*cost),i, 5);
            result+=amount*cost;
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

    /**
     * busca los productos con el parametro de la barra de busqueda
     *
     * @param evt
     * @throws RemoteException
     */
    private void searchProduct() throws RemoteException {

    }

    @Override
    public void actionPerformed(ActionEvent ae) {
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
    }

    @Override
    public void editingStopped(ChangeEvent ce) {
        guiPurchase.getTxtCost().setText(ParserFloat.floatToString(calculateCost()));
    }

    @Override
    public void editingCanceled(ChangeEvent ce) {
    }

}
