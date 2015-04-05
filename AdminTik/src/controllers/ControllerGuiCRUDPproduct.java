/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import gui.GuiCRUDPProduct;
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
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author jacinto
 */
public class ControllerGuiCRUDPproduct implements ActionListener {

    private GuiCRUDPProduct guiCRUDPProduct;
    private JTable tableProducts;
    private DefaultTableModel tableProductsDefault;
    private Boolean isNew;
    private Boolean editingInformation;
    private List<Map> productList;
    InterfacePproduct pproduct;
    InterfaceCategory category;
    private String ip;
    Map<String, Object> product;
    //private CRUDPproduct crudPproduct;

    /**
     *
     * @param guiCRUDPProduct
     * @throws NotBoundException
     * @throws MalformedURLException
     * @throws RemoteException
     */
    public ControllerGuiCRUDPproduct(GuiCRUDPProduct guiCRUDPProduct) throws NotBoundException, MalformedURLException, RemoteException {
        this.guiCRUDPProduct = guiCRUDPProduct;
        ip = "//192.168.1.16/";
        this.guiCRUDPProduct.setActionListener(this);
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
        this.guiCRUDPProduct.getTableProducts().addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                try {
                    tableMouseClicked(evt);
                } catch (RemoteException ex) {
                    Logger.getLogger(ControllerGuiCRUDPproduct.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
        tableProductsDefault = guiCRUDPProduct.getTableProductsDefault();
        pproduct = (InterfacePproduct) Naming.lookup(ip + "CRUDPproduct");
        //  category = (InterfaceCategory) Naming.lookup(ip + "CRUDCategory");
        productList = pproduct.getPproducts();
        //   guiCRUDPProduct.setCRUDCategory(category);
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
        productList = pproduct.getPproducts(guiCRUDPProduct.getTxtSearch().getText());
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
            Object row[] = new String[6];
            row[0] = prod.get("id").toString();
            row[1] = prod.get("name").toString(); //NOMBRE
            row[2] = prod.get("stock").toString(); // STOCK 
            Map<String, Object> subC = (Map<String, Object>) category.getSubcategoriesCategory(Integer.parseInt(prod.get("subcategory_id").toString()));
            row[3] = subC.get("name").toString(); //CATEGORIA
            row[4] = prod.get("measure_unit").toString(); // UNIDAD DE MEDIDA
            row[5] = prod.get("unit_price").toString(); // PRECIO UNITARIO
            tableProductsDefault.addRow(row);
        }
    }

    /**
     * Mouse Clicked listener en la tabla para cargar el producto en los txt
     *
     * @param evt
     * @throws RemoteException
     */
    public void tableMouseClicked(java.awt.event.MouseEvent evt) throws RemoteException {
        if (evt.getClickCount() == 2) {
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
                    } else {
                        JOptionPane.showMessageDialog(guiCRUDPProduct, "Ocurrió un error, no se borró el producto", "Error!", JOptionPane.ERROR_MESSAGE);
                    }
                } catch (RemoteException ex) {
                    Logger.getLogger(ControllerGuiCRUDPproduct.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        if (e.getSource() == guiCRUDPProduct.getBtnSave() && editingInformation && isNew) { //guardo un producto nuevo, boton guardar
            guiCRUDPProduct.clicSaveProduct();
            try {
                Map subC = category.getSubcategory(guiCRUDPProduct.getCategory().getSelectedItem().toString());
                int subcategory_id = Integer.parseInt(subC.get("id").toString());//obtener categoria
                String name = guiCRUDPProduct.getTxtName().getText();
                float stock = Float.parseFloat(guiCRUDPProduct.getTxtStock().getText());
                float amount = Float.parseFloat(guiCRUDPProduct.getTxtQuantity().getText());
                String measureUnit = guiCRUDPProduct.getTxtMeasureUnit().getText();
                String purchasePricea = guiCRUDPProduct.getTxtPurchasePrice().getText();
                float unitPrice = amount / Float.parseFloat(purchasePricea);
                try {
                    pproduct.create(name, stock, measureUnit, unitPrice, subcategory_id, amount);
                    JOptionPane.showMessageDialog(guiCRUDPProduct, "¡Producto creado exitosamente!");

                } catch (RemoteException ex) {
                    Logger.getLogger(ControllerGuiCRUDPproduct.class.getName()).log(Level.SEVERE, null, ex);
                }
            } catch (RemoteException ex) {
                Logger.getLogger(ControllerGuiCRUDPproduct.class.getName()).log(Level.SEVERE, null, ex);
            }

        }
        if (e.getSource() == guiCRUDPProduct.getBtnSave() && editingInformation && !isNew) { //modifico un producto, boton guardar
            guiCRUDPProduct.clicSaveProduct();
            String name = guiCRUDPProduct.getTxtName().getText();
            float stock = Float.parseFloat(guiCRUDPProduct.getTxtStock().getText());
            float amount = Float.parseFloat(guiCRUDPProduct.getTxtQuantity().getText());
            try {
                Map subC = category.getSubcategory(guiCRUDPProduct.getCategory().getSelectedItem().toString());
                int subcategory_id = Integer.parseInt(subC.get("id").toString());//obtener categoria
                String measureUnit = guiCRUDPProduct.getTxtMeasureUnit().getText();
                String purchasePricea = guiCRUDPProduct.getTxtPurchasePrice().getText();
                int id = Integer.parseInt(guiCRUDPProduct.getTxtId().getText());
                float unitPrice = amount / Float.parseFloat(purchasePricea);
                try {
                    pproduct.modify(id, name, stock, measureUnit, unitPrice, subcategory_id, amount);
                    JOptionPane.showMessageDialog(guiCRUDPProduct, "¡Producto modificado exitosamente!");
                } catch (RemoteException ex) {
                    Logger.getLogger(ControllerGuiCRUDPproduct.class.getName()).log(Level.SEVERE, null, ex);
                }
            } catch (RemoteException ex) {
                Logger.getLogger(ControllerGuiCRUDPproduct.class.getName()).log(Level.SEVERE, null, ex);
            }

        }
    }
}
