/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import gui.GuiCRUDEProduct;
import interfaces.InterfaceEproduct;
import interfaces.InterfacePproduct;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.Iterator;
import java.util.LinkedList;
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
import utils.Pair;

/**
 *
 * @author jacinto
 */
public class ControllerGuiCRUDEproduct implements ActionListener {

    private GuiCRUDEProduct guiCRUDEProduct;
    private JTable tableProducts;
    private JTable tableReciper;
    private DefaultTableModel tableProductsDefault;
    private DefaultTableModel tableReciperDefault;
    private Boolean isNew;
    private Boolean editingInformation;
    private List<Map> pproductList;
    private List<Map> eproductList;
    private List<Map> eproductPproductList;
    InterfacePproduct crudPproduct;
    InterfaceEproduct crudEproduct;
    Map<String, Object> pproduct;
    Map<String, Object> eproduct;

    /**
     *
     * @param guiCRUDEProduct
     * @throws NotBoundException
     * @throws MalformedURLException
     * @throws RemoteException
     */
    public ControllerGuiCRUDEproduct(GuiCRUDEProduct guiCRUDEProduct) throws NotBoundException, MalformedURLException, RemoteException {
        this.guiCRUDEProduct = guiCRUDEProduct;
        guiCRUDEProduct.setActionListener(this);
        tableProducts = guiCRUDEProduct.getTableProducts();
        tableReciper = guiCRUDEProduct.getTableReciper();
        tableProductsDefault = guiCRUDEProduct.getTableProductsDefault();
        tableReciperDefault = guiCRUDEProduct.getTableReciperDefault();
        crudPproduct = (InterfacePproduct) Naming.lookup("//" + Config.ip + "/CRUDPproduct");
        crudEproduct = (InterfaceEproduct) Naming.lookup("//" + Config.ip + "/CRUDEproduct");
        pproductList = crudPproduct.getPproducts();
        eproductList = crudEproduct.getEproducts();
        editingInformation = false;
        refreshList();
        this.guiCRUDEProduct.getTxtSearch().addKeyListener(new java.awt.event.KeyAdapter() {
            @Override
            public void keyReleased(java.awt.event.KeyEvent evt) {
                try {
                    searchKeyReleased(evt);
                } catch (RemoteException ex) {
                    Logger.getLogger(ControllerGuiCRUDPproduct.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
        this.guiCRUDEProduct.getTableProducts().getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                try {
                    tableProductsValueChanged();
                } catch (RemoteException ex) {
                    Logger.getLogger(ControllerGuiCRUDPproduct.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
        this.guiCRUDEProduct.getTableReciper().addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                try {
                    tableReciperMouseClicked(evt);
                } catch (RemoteException ex) {
                    Logger.getLogger(ControllerGuiCRUDPproduct.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
    }

    /**
     * Key listener del buscador
     *
     * @param evt
     * @throws RemoteException
     */
    public void searchKeyReleased(java.awt.event.KeyEvent evt) throws RemoteException {
        search();
    }

    /**
     * busca los productos con el parametro de la barra de busqueda, si se esta
     * creando o editando un producto elaborado solo encontrara productos
     * primarios, si no solo econtrara productos elaborados
     *
     * @param evt
     * @throws RemoteException
     */
    private void search() throws RemoteException {
        if (guiCRUDEProduct.getTxtSearch().getText().isEmpty()) {
            if (editingInformation) {
                pproductList = crudPproduct.getPproducts();
            } else {
                eproductList = crudEproduct.getEproducts();
            }
        } else {
            if (editingInformation) {
                pproductList = crudPproduct.getPproducts(guiCRUDEProduct.getTxtSearch().getText());
            } else {
                eproductList = crudEproduct.getEproducts(guiCRUDEProduct.getTxtSearch().getText());
            }
        }
        refreshList();
    }

    /**
     * refresca la lista de productos, diferenciando si son elaborados o
     * primarios
     */
    private void refreshList() throws RemoteException {
        tableProductsDefault.setRowCount(0);
        if (editingInformation) {
            Iterator<Map> it = pproductList.iterator();
            while (it.hasNext()) {
                Map<String, Object> prod = it.next();
                Object row[] = new String[3];
                row[0] = prod.get("id").toString();
                row[1] = prod.get("name").toString(); //NOMBRE
                row[2] = "Primario"; // TIPO
                tableProductsDefault.addRow(row);
            }
        } else {
            Iterator<Map> it = eproductList.iterator();
            while (it.hasNext()) {
                Map<String, Object> prod = it.next();
                Object row[] = new String[3];
                row[0] = prod.get("id").toString();
                row[1] = prod.get("name").toString(); //NOMBRE
                row[2] = "Elaborado"; // TIPO
                tableProductsDefault.addRow(row);
            }
        }
    }

    /**
     * ListSelectionListener en la tabla para cargar el producto en los txt, y
     * los productos correspondientes en la receta (si es que se esta
     * modificando o creando un producto elaborado)
     *
     * @param evt
     * @throws RemoteException
     */
    public void tableProductsValueChanged() throws RemoteException {
        if (tableProducts.getSelectedRow() != -1) {
            if (!editingInformation) {
                guiCRUDEProduct.clicTableProducts();
                int id = Integer.parseInt((String) tableProducts.getValueAt(tableProducts.getSelectedRow(), 0));
                eproduct = crudEproduct.getEproduct(id);
                eproductPproductList = crudEproduct.getEproductPproduts(id);
                guiCRUDEProduct.loadProduct(eproduct);
                refreshReciperList();
            } else {
                if (!(isRepeatedOnTableReciper(tableProducts.getValueAt(tableProducts.getSelectedRow(), 0)))) {
                    Object row[] = new String[4];
                    String idxs = tableProducts.getValueAt(tableProducts.getSelectedRow(), 0).toString();
                    int idx = Integer.valueOf(idxs);
                    String measureU = crudPproduct.getPproduct(idx).get("measure_unit").toString();
                    String quantity = JOptionPane.showInputDialog(guiCRUDEProduct, "Cantidad en " + measureU);
                    if (quantity != null) {
                        if (!quantity.isEmpty()) {
                            row[0] = tableProducts.getValueAt(tableProducts.getSelectedRow(), 0); //id
                            row[1] = tableProducts.getValueAt(tableProducts.getSelectedRow(), 1); //NOMBRE
                            row[2] = quantity; // Cantidad        
                            row[3] = measureU; // Unidad de medida
                            tableReciperDefault.addRow(row);
                        }
                    }
                }
            }
        } else {
            if (!editingInformation) {
                tableReciperDefault.setRowCount(0);
            }
        }
    }

    /**
     * Retorna true si el elemento ya esta en la tabla receta
     *
     * @param id
     * @return boolean
     */
    public boolean isRepeatedOnTableReciper(Object id) {
        for (int i = 0; i < tableReciper.getRowCount(); i++) {
            if (tableReciper.getValueAt(i, 0).equals(id)) {
                return true;
            }
        }
        return false;
    }

    /**
     * borra el elemenot que se cliclea dos veces
     *
     * @param evt
     * @throws RemoteException
     */
    public void tableReciperMouseClicked(java.awt.event.MouseEvent evt) throws RemoteException {
        if (evt.getClickCount() == 2) {
            int row = tableReciper.getSelectedRow();
            if (row > -1 && row < tableReciper.getRowCount()) {
                tableReciperDefault.removeRow(tableReciper.getSelectedRow());
            }
        }
    }

    /**
     * refresca la lista de la reseta
     *
     * @throws RemoteException
     */
    public void refreshReciperList() throws RemoteException {
        tableReciperDefault.setRowCount(0);
        for (Map epPp : eproductPproductList) {
            Object row[] = new String[4];
            String idxs = epPp.get("pproduct_id").toString();
            int idx = Integer.valueOf(idxs);
            String measureU = crudPproduct.getPproduct(idx).get("measure_unit").toString();
            row[0] = idxs;
            row[1] = crudPproduct.getPproduct(Integer.parseInt(epPp.get("pproduct_id").toString())).get("name").toString(); //NOMBRE
            row[2] = epPp.get("amount").toString().replace(',', '.'); // Cantidad    
            row[3] = measureU;
            tableReciperDefault.addRow(row);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == guiCRUDEProduct.getBtnNew()) { //Boton nuevo            
            isNew = true;
            editingInformation = true;
            try {
                guiCRUDEProduct.clicNewProduct();
                pproductList = crudPproduct.getPproducts();
                refreshList();
            } catch (RemoteException ex) {
                Logger.getLogger(ControllerGuiCRUDEproduct.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        if (e.getSource() == guiCRUDEProduct.getBtnDelete()) {  //boton eliminar
            Integer resp = JOptionPane.showConfirmDialog(guiCRUDEProduct, "¿Desea borrar el producto " + guiCRUDEProduct.getTxtName().getText(), "Confirmar borrado", JOptionPane.YES_NO_OPTION);
            if (resp == JOptionPane.YES_OPTION) {
                int id = Integer.parseInt(guiCRUDEProduct.getTxtId().getText());
                try {
                    if (crudEproduct.delete(id)) {
                        JOptionPane.showMessageDialog(guiCRUDEProduct, "¡Producto borrado exitosamente!");
                        guiCRUDEProduct.clicDeleteProduct();
                        editingInformation = false;
                        eproductList = crudEproduct.getEproducts();
                        refreshList();
                    } else {
                        JOptionPane.showMessageDialog(guiCRUDEProduct, "Ocurrió un error, no se borró el producto", "Error!", JOptionPane.ERROR_MESSAGE);
                    }
                } catch (RemoteException ex) {
                    Logger.getLogger(ControllerGuiCRUDPproduct.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        if (e.getSource() == guiCRUDEProduct.getBtnModify()) { //boton modificar
            guiCRUDEProduct.clicModifyProduct();
            isNew = false;
            editingInformation = true;
            try {
                pproductList = crudPproduct.getPproducts();
                refreshList();
            } catch (RemoteException ex) {
                Logger.getLogger(ControllerGuiCRUDEproduct.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        if (e.getSource() == guiCRUDEProduct.getBtnSave() && editingInformation && isNew) {  //guardo un producto nuevo, boton guardar
            if (guiCRUDEProduct.checkFields()) {
                List<Pair> listP = new LinkedList<Pair>();
                for (int i = 0; i < tableReciper.getRowCount(); i++) { //cargo la lista de productos
                    Pair p = new Pair(Integer.parseInt((String) tableReciper.getValueAt(i, 0)), Float.parseFloat((String) tableReciper.getValueAt(i, 2)));
                    listP.add(p);
                }
                String name = guiCRUDEProduct.getTxtName().getText();
                try {
                    crudEproduct.create(name, listP);
                    JOptionPane.showMessageDialog(guiCRUDEProduct, "¡Producto creado exitosamente!");
                    editingInformation = false;
                    guiCRUDEProduct.clicSaveProduct();
                    eproductList = crudEproduct.getEproducts();
                    refreshList();
                } catch (RemoteException ex) {
                    Logger.getLogger(ControllerGuiCRUDPproduct.class.getName()).log(Level.SEVERE, null, ex);
                }

            }
        }
        if (e.getSource() == guiCRUDEProduct.getBtnSave() && editingInformation && !isNew) {  //modifico un producto, boton guardar
            if (guiCRUDEProduct.checkFields()) {
                List<Pair> listP = new LinkedList<Pair>();
                for (int i = 0; i < tableReciper.getRowCount(); i++) { //cargo la lista de productos
                    Pair p = new Pair(Integer.parseInt((String) tableReciper.getValueAt(i, 0)), Float.parseFloat((String) tableReciper.getValueAt(i, 2)));
                    listP.add(p);
                }
                String name = guiCRUDEProduct.getTxtName().getText();
                int id = Integer.parseInt(guiCRUDEProduct.getTxtId().getText());
                try {
                    crudEproduct.modify(id, name, listP);
                    JOptionPane.showMessageDialog(guiCRUDEProduct, "¡Producto modificado exitosamente!");
                    editingInformation = false;
                    guiCRUDEProduct.clicSaveProduct();
                    eproductList = crudEproduct.getEproducts();
                    refreshList();
                } catch (RemoteException ex) {
                    Logger.getLogger(ControllerGuiCRUDPproduct.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        if (e.getSource() == guiCRUDEProduct.getBtnCancel()) { //creo un producto         
            isNew = false;
            editingInformation = false;
            guiCRUDEProduct.clicSaveProduct();
            try {
                eproductList = crudEproduct.getEproducts();
                refreshList();
            } catch (RemoteException ex) {
                Logger.getLogger(ControllerGuiCRUDEproduct.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}
