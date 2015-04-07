/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import gui.GuiCRUDFProduct;
import interfaces.InterfaceCategory;
import interfaces.InterfaceEproduct;
import interfaces.InterfaceFproduct;
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
import javax.swing.table.DefaultTableModel;
import utils.Pair;

/**
 *
 * @author jacinto
 */
public class ControllerGuiCRUDFproduct implements ActionListener {

    private GuiCRUDFProduct guiCRUDFProduct;
    private JTable tableProducts;
    private JTable tableReciper;
    private DefaultTableModel tableProductsDefault;
    private DefaultTableModel tableReciperDefault;
    private Boolean isNew;
    private Boolean editingInformation;
    private List<Map> pproductList;
    private List<Map> eproductList;
    private List<Map> fproductList;
    private List<Map> fproductPproductList;
    private List<Map> fproductEproductList;
    InterfacePproduct crudPproduct;
    InterfaceEproduct crudEproduct;
    InterfaceFproduct crudFproduct;
    InterfaceCategory category;
    private String ip;
    Map<String, Object> pproduct;
    Map<String, Object> eproduct;
    Map<String, Object> fproduct;

    /**
     *
     * @param guiCRUDFProduct
     * @throws NotBoundException
     * @throws MalformedURLException
     * @throws RemoteException
     */
    public ControllerGuiCRUDFproduct(GuiCRUDFProduct guiCRUDFProduct) throws NotBoundException, MalformedURLException, RemoteException {
        this.guiCRUDFProduct = guiCRUDFProduct;
        guiCRUDFProduct.setActionListener(this);
        ip = "//192.168.1.16/";
        tableProducts = guiCRUDFProduct.getTableProducts();
        tableReciper = guiCRUDFProduct.getTableReciper();
        tableProductsDefault = guiCRUDFProduct.getTableProductsDefault();
        tableReciperDefault = guiCRUDFProduct.getTableReciperDefault();
        crudPproduct = (InterfacePproduct) Naming.lookup(ip + "CRUDPproduct");
        crudEproduct = (InterfaceEproduct) Naming.lookup(ip + "CRUDEproduct");
        crudFproduct = (InterfaceFproduct) Naming.lookup(ip + "CRUDFproduct");
        category = (InterfaceCategory) Naming.lookup(ip + "CRUDCategory");
        guiCRUDFProduct.setCRUDCategory(category);
        pproductList = crudPproduct.getPproducts();
        eproductList = crudEproduct.getEproducts();
        fproductList = crudFproduct.getFproducts();
        editingInformation = false;
        refreshList();
        this.guiCRUDFProduct.getTxtSearch().addKeyListener(new java.awt.event.KeyAdapter() {
            @Override
            public void keyReleased(java.awt.event.KeyEvent evt) {
                try {
                    searchKeyReleased(evt);
                } catch (RemoteException ex) {
                    Logger.getLogger(ControllerGuiCRUDPproduct.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
        this.guiCRUDFProduct.getTableProducts().addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                try {
                    tableProductsMouseClicked(evt);
                } catch (RemoteException ex) {
                    Logger.getLogger(ControllerGuiCRUDPproduct.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
        this.guiCRUDFProduct.getTableReciper().addMouseListener(new java.awt.event.MouseAdapter() {
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
     * creando o ediando un producto final solo encontrara productos primarios y
     * elaborados, si no solo econtrara productos finales
     *
     * @param evt
     * @throws RemoteException
     */
    private void search() throws RemoteException {
        if (guiCRUDFProduct.getTxtSearch().getText().equals("") || guiCRUDFProduct.getTxtSearch().getText().equals(" ")) {
            if (editingInformation) {
                pproductList = crudPproduct.getPproducts();
                eproductList = crudEproduct.getEproducts();
            } else {
                fproductList = crudFproduct.getFproducts();
            }
        } else {
            if (editingInformation) {
                pproductList = crudPproduct.getPproducts(guiCRUDFProduct.getTxtSearch().getText());
                eproductList = crudEproduct.getEproducts(guiCRUDFProduct.getTxtSearch().getText());
            } else {
                fproductList = crudFproduct.getFproducts(guiCRUDFProduct.getTxtSearch().getText());
            }
        }
        refreshList();
    }

    /**
     * refresca la lista de productos, diferenciando si son finales, elaborados
     * o primarios
     */
    private void refreshList() throws RemoteException {
        tableProductsDefault.setRowCount(0);
        if (editingInformation) {
            Iterator<Map> it = pproductList.iterator();
            while (it.hasNext()) {
                Map<String, Object> prod = it.next();
                Object row[] = new String[6];
                row[0] = prod.get("id").toString();
                row[1] = prod.get("name").toString(); //NOMBRE
                row[2] = prod.get("stock").toString(); // STOCK 
                Map<String, Object> subC = category.getSubcategory(Integer.parseInt(prod.get("subcategory_id").toString()));
                row[3] = subC.get("name").toString(); //CATEGORIA
                row[4] = prod.get("measure_unit").toString(); // UNIDAD DE MEDIDA
                row[5] = "Primario"; // TIPO
                tableProductsDefault.addRow(row);
            }
            it = eproductList.iterator();
            while (it.hasNext()) {
                Map<String, Object> prod = it.next();
                Object row[] = new String[6];
                row[0] = prod.get("id").toString();
                row[1] = prod.get("name").toString(); //NOMBRE
                row[2] = prod.get("stock").toString(); // STOCK 
                Map<String, Object> subC = category.getSubcategory(Integer.parseInt(prod.get("subcategory_id").toString()));
                row[3] = subC.get("name").toString(); //CATEGORIA
                row[4] = prod.get("measure_unit").toString(); // UNIDAD DE MEDIDA
                row[5] = "Elaborado"; // TIPO
                tableProductsDefault.addRow(row);
            }
        } else {
            Iterator<Map> it = fproductList.iterator();
            while (it.hasNext()) {
                Map<String, Object> prod = it.next();
                Object row[] = new String[6];
                row[0] = prod.get("id").toString();
                row[1] = prod.get("name").toString(); //NOMBRE
                row[2] = "-"; // STOCK 
                Map<String, Object> subC = category.getSubcategory(Integer.parseInt(prod.get("subcategory_id").toString()));
                row[3] = subC.get("name").toString(); //CATEGORIA
                row[4] = "-"; // UNIDAD DE MEDIDA
                row[5] = "Final"; // TIPO
                tableProductsDefault.addRow(row);
            }
        }
    }

    /**
     * Mouse Clicked listener en la tabla para cargar el producto en los txt, y
     * los productos correspondientes en la receta (si es que se esta
     * modificando o creando un producto final)
     *
     * @param evt
     * @throws RemoteException
     */
    public void tableProductsMouseClicked(java.awt.event.MouseEvent evt) throws RemoteException {
        if (evt.getClickCount() == 2) {
            if (!editingInformation) {
                guiCRUDFProduct.clicTableProducts();
                int id = Integer.parseInt((String) tableProducts.getValueAt(tableProducts.getSelectedRow(), 0));
                fproduct = crudFproduct.getFproduct(id);
                fproductPproductList = crudFproduct.getFproductPproduts(id);
                fproductEproductList = crudFproduct.getFproductEproduts(id);
                guiCRUDFProduct.loadProduct(fproduct);
                refreshReciperList();
            } else {
                if (!(isRepeatedOnTableReciper(tableProducts.getValueAt(tableProducts.getSelectedRow(), 1)))) {
                    Object row[] = new String[4];
                    row[0] = tableProducts.getValueAt(tableProducts.getSelectedRow(), 0); //id
                    row[1] = tableProducts.getValueAt(tableProducts.getSelectedRow(), 1); //NOMBRE
                    row[2] = "1"; // Cantidad  
                    if (((String) tableProducts.getValueAt(tableProducts.getSelectedRow(), 5)).equals("Primario")) {
                        row[3] = "Primario"; // Tipo  
                    } else {
                        row[3] = "Elaborado";// Tipo  
                    }
                    tableReciperDefault.addRow(row);
                }
            }
        }
    }

    /**
     * Retorna true si el elemento ya esta en la tabla receta
     *
     * @param id
     * @return boolean
     */
    public boolean isRepeatedOnTableReciper(Object name) {
        for (int i = 0; i < tableReciper.getRowCount(); i++) {
            if (tableReciper.getValueAt(i, 1).equals(name)) {
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
     * refresca la lista de la reseta diferenciando si son productos primarios o
     * elaborados
     *
     * @throws RemoteException
     */
    public void refreshReciperList() throws RemoteException {
        tableReciperDefault.setRowCount(0);
        for (Map fpPp : fproductPproductList) {
            Object row[] = new String[4];
            row[0] = fpPp.get("pproduct_id").toString();
            row[1] = crudPproduct.getPproduct(Integer.parseInt(fpPp.get("pproduct_id").toString())).get("name").toString(); //NOMBRE
            row[2] = fpPp.get("amount").toString(); // Cantidad     
            row[3] = "Primario"; // Tipo  
            tableReciperDefault.addRow(row);
        }
        for (Map fpEp : fproductEproductList) {
            Object row[] = new String[4];
            row[0] = fpEp.get("eproduct_id").toString();
            row[1] = crudEproduct.getEproduct(Integer.parseInt(fpEp.get("eproduct_id").toString())).get("name").toString(); //NOMBRE
            row[2] = fpEp.get("amount").toString(); // Cantidad     
            row[3] = "Elaborado"; // Tipo  
            tableReciperDefault.addRow(row);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == guiCRUDFProduct.getBtnNew()) { //Boton nuevo            
            isNew = true;
            editingInformation = true;
            try {
                guiCRUDFProduct.clicNewProduct();
                pproductList = crudPproduct.getPproducts();
                eproductList = crudEproduct.getEproducts();
                refreshList();
            } catch (RemoteException ex) {
                Logger.getLogger(ControllerGuiCRUDEproduct.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        if (e.getSource() == guiCRUDFProduct.getBtnDelete()) {   //boton eliminar
            Integer resp = JOptionPane.showConfirmDialog(guiCRUDFProduct, "¿Desea borrar el producto " + guiCRUDFProduct.getTxtName().getText(), "Confirmar borrado", JOptionPane.YES_NO_OPTION);
            if (resp == JOptionPane.YES_OPTION) {
                int id = Integer.parseInt(guiCRUDFProduct.getTxtId().getText());
                try {
                    if (crudFproduct.delete(id)) {
                        JOptionPane.showMessageDialog(guiCRUDFProduct, "¡Producto borrado exitosamente!");
                        guiCRUDFProduct.clicDeleteProduct();
                        editingInformation = false;
                        fproductList = crudFproduct.getFproducts();
                        refreshList();
                    } else {
                        JOptionPane.showMessageDialog(guiCRUDFProduct, "Ocurrió un error, no se borró el producto", "Error!", JOptionPane.ERROR_MESSAGE);
                    }
                } catch (RemoteException ex) {
                    Logger.getLogger(ControllerGuiCRUDPproduct.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        if (e.getSource() == guiCRUDFProduct.getBtnModify()) {//boton modificar            
            isNew = false;
            editingInformation = true;
            guiCRUDFProduct.clicModifyProduct();
            try {
                pproductList = crudPproduct.getPproducts();
                eproductList = crudEproduct.getEproducts();
                refreshList();
            } catch (RemoteException ex) {
                Logger.getLogger(ControllerGuiCRUDFproduct.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        if (e.getSource() == guiCRUDFProduct.getBtnSave() && editingInformation && isNew) {  //guardo un producto nuevo, boton guardar
            try {
                Map subC = category.getSubcategory(guiCRUDFProduct.getCategory().getSelectedItem().toString());
                int subcategory_id = Integer.parseInt(subC.get("id").toString());//obtener categoria
                List<Pair> listP = new LinkedList<Pair>();
                List<Pair> listE = new LinkedList<Pair>();
                for (int i = 0; i < tableReciper.getRowCount(); i++) { //cargo la lista de productos
                    if (((String) tableReciper.getValueAt(i, 3)).equals("Primario")) {
                        Pair p = new Pair(Integer.parseInt((String) tableReciper.getValueAt(i, 0)), Float.parseFloat((String) tableReciper.getValueAt(i, 2)));
                        listP.add(p);
                    } else {
                        Pair p = new Pair(Integer.parseInt((String) tableReciper.getValueAt(i, 0)), Float.parseFloat((String) tableReciper.getValueAt(i, 2)));
                        listE.add(p);
                    }
                }
                String name = guiCRUDFProduct.getTxtName().getText();
                try {
                    crudFproduct.create(name, subcategory_id, listP, listE);
                    JOptionPane.showMessageDialog(guiCRUDFProduct, "¡Producto creado exitosamente!");
                    editingInformation = false;
                    guiCRUDFProduct.clicSaveProduct();
                    fproductList = crudFproduct.getFproducts();
                    refreshList();
                } catch (RemoteException ex) {
                    Logger.getLogger(ControllerGuiCRUDPproduct.class.getName()).log(Level.SEVERE, null, ex);
                }
            } catch (RemoteException ex) {
                Logger.getLogger(ControllerGuiCRUDPproduct.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        if (e.getSource() == guiCRUDFProduct.getBtnSave() && editingInformation && !isNew) {  //modifico un producto, boton guardar
            try {
                Map subC = category.getSubcategory(guiCRUDFProduct.getCategory().getSelectedItem().toString());
                int subcategory_id = Integer.parseInt(subC.get("id").toString());//obtener categoria
                List<Pair> listP = new LinkedList<Pair>();
                List<Pair> listE = new LinkedList<Pair>();
                for (int i = 0; i < tableReciper.getRowCount(); i++) {
                    if (((String) tableReciper.getValueAt(i, 3)).equals("Primario")) { //cargo la lista de productos
                        Pair p = new Pair(Integer.parseInt((String) tableReciper.getValueAt(i, 0)), Float.parseFloat((String) tableReciper.getValueAt(i, 2)));
                        listP.add(p);
                    } else {
                        Pair p = new Pair(Integer.parseInt((String) tableReciper.getValueAt(i, 0)), Float.parseFloat((String) tableReciper.getValueAt(i, 2)));
                        listE.add(p);
                    }
                }
                String name = guiCRUDFProduct.getTxtName().getText();
                int id = Integer.parseInt(guiCRUDFProduct.getTxtId().getText());
                try {
                    crudFproduct.modify(id, name, subcategory_id, listP, listE);
                    JOptionPane.showMessageDialog(guiCRUDFProduct, "¡Producto modificado exitosamente!");
                    editingInformation = false;
                    guiCRUDFProduct.clicSaveProduct();
                    fproductList = crudFproduct.getFproducts();
                    refreshList();
                } catch (RemoteException ex) {
                    Logger.getLogger(ControllerGuiCRUDPproduct.class.getName()).log(Level.SEVERE, null, ex);
                }
            } catch (RemoteException ex) {
                Logger.getLogger(ControllerGuiCRUDPproduct.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

    }
}
