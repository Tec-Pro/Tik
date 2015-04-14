/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import gui.GuiCRUDPProduct;
import gui.GuiLoadPurchase;
import interfaces.InterfaceCategory;
import interfaces.InterfacePproduct;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import utils.Config;

/**
 *
 * @author jacinto
 */
public class ControllerGuiCRUDPproduct implements ActionListener {

    private GuiCRUDPProduct guiCRUDPProduct;
    private GuiLoadPurchase guiLoadPurchase;
    private JTable tableProducts;
    private DefaultTableModel tableProductsDefault;
    private Boolean isNew;
    private Boolean editingInformation;
    private List<Map> productList;
    InterfacePproduct pproduct;
    InterfaceCategory category;
    Map<String, Object> product;
    //private CRUDPproduct crudPproduct;

    /**
     *
     * @param guiCRUDPProduct
     * @throws NotBoundException
     * @throws MalformedURLException
     * @throws RemoteException
     */
    public ControllerGuiCRUDPproduct(GuiCRUDPProduct guiCRUDPProduct, GuiLoadPurchase guiLoadPurchase) throws NotBoundException, MalformedURLException, RemoteException {
        this.guiCRUDPProduct = guiCRUDPProduct;
        this.guiCRUDPProduct.setActionListener(this);
        this.guiLoadPurchase = guiLoadPurchase;
        this.guiLoadPurchase.setActionListener(this);
        tableProducts = guiCRUDPProduct.getTableProducts();
        this.guiCRUDPProduct.getTxtSearch().addKeyListener(new java.awt.event.KeyAdapter() {
            @Override
            public void keyReleased(java.awt.event.KeyEvent evt) {
                try {
                    searchKeyReleased(evt);
                } catch (RemoteException ex) {
                    Logger.getLogger(ControllerGuiCRUDPproduct.class.getName()).log(Level.SEVERE, null, ex);

                }
            }
        });
//        this.guiCRUDPProduct.getTableProducts().addMouseListener(new java.awt.event.MouseAdapter() {
//            @Override
//            public void mouseClicked(java.awt.event.MouseEvent evt) {
//                try {
//                    tableMouseClicked(evt);
//                } catch (RemoteException ex) {
//                    Logger.getLogger(ControllerGuiCRUDPproduct.class.getName()).log(Level.SEVERE, null, ex);
//                    ex.getMessage();
//                }
//            }
//        });
        this.guiCRUDPProduct.getTableProducts().getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                try {
                    tableValueChanged();
                } catch (RemoteException ex) {
                    Logger.getLogger(ControllerGuiCRUDPproduct.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });

        tableProductsDefault = guiCRUDPProduct.getTableProductsDefault();
        pproduct = (InterfacePproduct) Naming.lookup("//" + Config.ip + "/CRUDPproduct");
        productList = pproduct.getPproducts();
        refreshList();
    }

    /**
     * key listener para el buscador
     *
     * @param evt
     * @throws RemoteException
     */
    public void searchKeyReleased(java.awt.event.KeyEvent evt) throws RemoteException {
        search();
    }

    /**
     * busca los productos con el parametro de la barra de busqueda
     *
     * @param evt
     * @throws RemoteException
     */
    private void search() throws RemoteException {
        if (guiCRUDPProduct.getTxtSearch().getText().equals("") || guiCRUDPProduct.getTxtSearch().getText().equals(" ")) {
            productList = pproduct.getPproducts();
        } else {
            productList = pproduct.getPproducts(guiCRUDPProduct.getTxtSearch().getText());
        }
        refreshList();
    }

    /**
     * refresca la lista de productos
     */
    private void refreshList() throws RemoteException {
        tableProductsDefault.setRowCount(0);
        Iterator<Map> it = productList.iterator();
        while (it.hasNext()) {
            Map<String, Object> prod = it.next();
            Object row[] = new String[5];
            row[0] = prod.get("id").toString();
            row[1] = prod.get("name").toString(); //NOMBRE
            row[2] = prod.get("stock").toString(); // STOCK 
            row[3] = prod.get("measure_unit").toString(); // UNIDAD DE MEDIDA
            row[4] = prod.get("unit_price").toString(); // PRECIO UNITARIO
            tableProductsDefault.addRow(row);
        }
    }

    /**
     * ListSelectionListener en la tabla para cargar el producto en los txt
     *
     * @throws RemoteException
     */
    public void tableValueChanged() throws RemoteException {
        if (guiCRUDPProduct.getTableProducts().getSelectedRow() != -1) {
            guiCRUDPProduct.clicTableProducts();
            product = pproduct.getPproduct(Integer.parseInt((String) tableProducts.getValueAt(tableProducts.getSelectedRow(), 0)));
            guiCRUDPProduct.loadProduct(product);
        }

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == guiCRUDPProduct.getBtnNew()) { //Boton nuevo
            try {
                guiCRUDPProduct.clicNewProduct();
            } catch (RemoteException ex) {
                Logger.getLogger(ControllerGuiCRUDPproduct.class.getName()).log(Level.SEVERE, null, ex);
            }
            isNew = true;
            editingInformation = true;
        }
        if (e.getSource() == guiCRUDPProduct.getBtnModify()) { //boton modificar
            try {
                guiCRUDPProduct.clicModifyProduct();
            } catch (RemoteException ex) {
                Logger.getLogger(ControllerGuiCRUDPproduct.class.getName()).log(Level.SEVERE, null, ex);
            }
            isNew = false;
            editingInformation = true;
        }
        if (e.getSource() == guiCRUDPProduct.getBtnDelete()) { //boton eliminar
            Integer resp = JOptionPane.showConfirmDialog(guiCRUDPProduct, "¿Desea borrar el producto " + guiCRUDPProduct.getTxtName().getText(), "Confirmar borrado", JOptionPane.YES_NO_OPTION);
            if (resp == JOptionPane.YES_OPTION) {
                int id = Integer.parseInt(guiCRUDPProduct.getTxtId().getText());
                try {
                    if (pproduct.delete(id)) {
                        JOptionPane.showMessageDialog(guiCRUDPProduct, "¡Producto borrado exitosamente!");
                        guiCRUDPProduct.clicDeleteProduct();
                        productList = pproduct.getPproducts();
                        refreshList();
                    } else {
                        JOptionPane.showMessageDialog(guiCRUDPProduct, "Ocurrió un error, no se borró el producto", "Error!", JOptionPane.ERROR_MESSAGE);
                    }
                } catch (RemoteException ex) {
                    Logger.getLogger(ControllerGuiCRUDPproduct.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        if (e.getSource() == guiCRUDPProduct.getBtnSave() && editingInformation && isNew) { //guardo un producto nuevo, boton guardar
            String name = guiCRUDPProduct.getTxtName().getText();
            float stock = Float.parseFloat(guiCRUDPProduct.getTxtStock().getText());
            float unitPrice = Float.parseFloat(guiCRUDPProduct.getTxtPrice().getText());
            String measureUnit = "";
            if (guiCRUDPProduct.getCboxMeasureUnit().getSelectedItem() != -1) {
                measureUnit = guiCRUDPProduct.getCboxMeasureUnit().getSelectedItem().toString();
            }
            try {
                pproduct.create(name, stock, measureUnit, unitPrice);
                JOptionPane.showMessageDialog(guiCRUDPProduct, "¡Producto creado exitosamente!");
                guiCRUDPProduct.clicSaveProduct();
                productList = pproduct.getPproducts();
                refreshList();
            } catch (RemoteException ex) {
                Logger.getLogger(ControllerGuiCRUDPproduct.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        if (e.getSource() == guiCRUDPProduct.getBtnSave() && editingInformation && !isNew) { //modifico un producto, boton guardar
            String name = guiCRUDPProduct.getTxtName().getText();
            float stock = Float.parseFloat(guiCRUDPProduct.getTxtStock().getText());
            float unitPrice = Float.parseFloat(guiCRUDPProduct.getTxtPrice().getText());
            String measureUnit = guiCRUDPProduct.getCboxMeasureUnit().getSelectedItem().toString();
            int id = Integer.parseInt(guiCRUDPProduct.getTxtId().getText());
            try {
                pproduct.modify(id, name, stock, measureUnit, unitPrice);
                JOptionPane.showMessageDialog(guiCRUDPProduct, "¡Producto modificado exitosamente!");
                guiCRUDPProduct.clicSaveProduct();
                productList = pproduct.getPproducts();
                refreshList();
            } catch (RemoteException ex) {
                Logger.getLogger(ControllerGuiCRUDPproduct.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        if (e.getSource() == guiCRUDPProduct.getBtnCancel() && editingInformation && !isNew) {  //modifico un producto pero cancelo 
            try {
                guiCRUDPProduct.clicSaveProduct();
                guiCRUDPProduct.clicTableProducts();
                guiCRUDPProduct.loadProduct(product);
            } catch (RemoteException ex) {
                Logger.getLogger(ControllerGuiCRUDPproduct.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        if (e.getSource() == guiCRUDPProduct.getBtnCancel() && editingInformation && isNew) { //creo un producto pero cancel0          
            guiCRUDPProduct.clicSaveProduct();
        }
        if (e.getSource() == guiCRUDPProduct.getBtnPurchase()) {
            guiLoadPurchase.getTxtId().setText(guiCRUDPProduct.getTxtId().getText());
            guiLoadPurchase.setVisible(true);
            guiLoadPurchase.show();
            guiCRUDPProduct.setVisible(false);
        }
        if (e.getSource() == guiLoadPurchase.getBtnCancel()) { //cancelo la carga de una compra  
            guiLoadPurchase.setVisible(false);
            guiLoadPurchase.clear();
            guiCRUDPProduct.setVisible(true);
            guiCRUDPProduct.show();
            guiCRUDPProduct.clicSaveProduct();
            try {
                guiCRUDPProduct.clicTableProducts();
                guiCRUDPProduct.loadProduct(product);
            } catch (RemoteException ex) {
                Logger.getLogger(ControllerGuiCRUDPproduct.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        if (e.getSource() == guiLoadPurchase.getBtnSave()) { //guardo la compra
            int idx = Integer.parseInt(guiLoadPurchase.getTxtId().getText());
            String measureU = guiLoadPurchase.getcBoxMeasureUnit().getSelectedItem().toString();
            float price = Float.parseFloat(guiLoadPurchase.getTxtPrice().getText());
            float amount = Float.parseFloat(guiLoadPurchase.getTxtAmount().getText());
            if (guiLoadPurchase.getCheckBoxOnlyCalculate().isSelected()) { //me fijo si solo hay que calcular el precio
                try {
                    pproduct.loadPurchase(idx, measureU, price, amount);
                } catch (RemoteException ex) {
                    Logger.getLogger(ControllerGuiCRUDPproduct.class.getName()).log(Level.SEVERE, null, ex);
                }
                JOptionPane.showMessageDialog(guiLoadPurchase, "¡Compra registrada exitosamente!");
            } else {
                try {
                    pproduct.calculateUnitPrice(idx, measureU, price, amount);
                } catch (RemoteException ex) {
                    Logger.getLogger(ControllerGuiCRUDPproduct.class.getName()).log(Level.SEVERE, null, ex);
                }
                JOptionPane.showMessageDialog(guiLoadPurchase, "¡Precio calculado exitosamente!");
            }
            guiLoadPurchase.setVisible(false);
            guiLoadPurchase.clear();
            guiCRUDPProduct.setVisible(true);
            guiCRUDPProduct.show();
            guiCRUDPProduct.clicSaveProduct();
        }
    }
}
