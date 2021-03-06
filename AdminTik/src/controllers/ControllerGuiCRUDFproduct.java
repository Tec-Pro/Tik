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
import javax.swing.event.CellEditorListener;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import utils.GeneralConfig;
import utils.InterfaceName;
import utils.Pair;
import utils.ParserFloat;

/**
 *
 * @author jacinto
 */
public class ControllerGuiCRUDFproduct implements ActionListener, CellEditorListener {

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
    private List<Map> fproductFproductList;
    InterfacePproduct crudPproduct;
    InterfaceEproduct crudEproduct;
    InterfaceFproduct crudFproduct;
    InterfaceCategory category;
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
        tableProducts = guiCRUDFProduct.getTableProducts();
        tableReciper = guiCRUDFProduct.getTableReciper();
        tableProductsDefault = guiCRUDFProduct.getTableProductsDefault();
        tableReciperDefault = guiCRUDFProduct.getTableReciperDefault();
        crudPproduct = (InterfacePproduct) InterfaceName.registry.lookup(InterfaceName.CRUDPproduct);
        crudEproduct = (InterfaceEproduct) InterfaceName.registry.lookup(InterfaceName.CRUDEproduct);
        crudFproduct = (InterfaceFproduct) InterfaceName.registry.lookup(InterfaceName.CRUDFproduct);
        category = (InterfaceCategory) InterfaceName.registry.lookup(InterfaceName.CRUDCategory);
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
        this.guiCRUDFProduct.getTableProducts().getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                try {
                    tableProductsValueChanged();
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
        guiCRUDFProduct.loadCategorySearch();
        if(ControllerMain.isAdmin())
            guiCRUDFProduct.setActionListener(this);
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
     * busca los productos con el parametro de la barra de busqueda, si no se
     * esta creando o ediando un producto final solo econtrara productos finales
     *
     * @param evt
     * @throws RemoteException
     */
    public void search() throws RemoteException {
        if (guiCRUDFProduct.getChboxSearchByCategory().isSelected() && guiCRUDFProduct.getCategorySearch().getSelectedIndex() > -1) {
            String nameCat = guiCRUDFProduct.getCategorySearch().getSelectedItem().toString();
            String catId = category.getCategoryByName(nameCat).get("id").toString(); 
            fproductList = crudFproduct.getFproductsByCategory(guiCRUDFProduct.getTxtSearch().getText(),Integer.valueOf(catId));
       } else {
            if (editingInformation) {
                pproductList = crudPproduct.getPproducts(guiCRUDFProduct.getTxtSearch().getText());
                eproductList = crudEproduct.getEproducts(guiCRUDFProduct.getTxtSearch().getText());
                fproductList = crudFproduct.getFproducts(guiCRUDFProduct.getTxtSearch().getText());
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
    public void refreshList() throws RemoteException {
        tableProductsDefault.setRowCount(0);
        if (guiCRUDFProduct.getChboxSearchByCategory().isSelected() && guiCRUDFProduct.getCategorySearch().getSelectedIndex() > -1) {
            Iterator<Map> it = fproductList.iterator();
            while (it.hasNext()) {
                Map<String, Object> prod = it.next();
                Object row[] = new String[5];
                row[0] = prod.get("id").toString();
                row[1] = prod.get("name").toString(); //NOMBRE
                Map<String, Object> subC = category.getSubcategory(Integer.parseInt(prod.get("subcategory_id").toString()));
                Map<String, Object> cat = category.getCategoryOfSubcategory(Integer.parseInt(prod.get("subcategory_id").toString()));
                row[2] = cat.get("name").toString(); //CATEGORIA
                row[3] = subC.get("name").toString(); //SUBCAT
                row[4] = "Final"; // TIPO
                tableProductsDefault.addRow(row);
            }
        } else {
            if (editingInformation) {
                Iterator<Map> it = pproductList.iterator();
                while (it.hasNext()) {
                    Map<String, Object> prod = it.next();
                    Object row[] = new String[5];
                    row[0] = prod.get("id").toString();
                    row[1] = prod.get("name").toString(); //NOMBRE
                    row[2] = "-"; //CATEGORIA
                    row[3] = "-"; //SUBCAT
                    row[4] = "Primario"; // TIPO
                    tableProductsDefault.addRow(row);
                }
                it = eproductList.iterator();
                while (it.hasNext()) {
                    Map<String, Object> prod = it.next();
                    Object row[] = new String[5];
                    row[0] = prod.get("id").toString();
                    row[1] = prod.get("name").toString(); //NOMBRE
                    row[2] = "-"; //CATEGORIA
                    row[3] = "-"; //SUBCAT
                    row[4] = "Elaborado"; // TIPO
                    tableProductsDefault.addRow(row);
                }
                it = fproductList.iterator();
                while (it.hasNext()) {
                    Map<String, Object> prod = it.next();
                    if (!(prod.get("id").toString().equals(guiCRUDFProduct.getTxtId().getText()))) {
                        Object row[] = new String[5];
                        row[0] = prod.get("id").toString();
                        row[1] = prod.get("name").toString(); //NOMBRE
                        Map<String, Object> subC = category.getSubcategory(Integer.parseInt(prod.get("subcategory_id").toString()));
                        Map<String, Object> cat = category.getCategoryOfSubcategory(Integer.parseInt(prod.get("subcategory_id").toString()));
                        row[2] = cat.get("name").toString(); //CATEGORIA
                        row[3] = subC.get("name").toString(); //SUBCAT
                        row[4] = "Final"; // TIPO
                        tableProductsDefault.addRow(row);
                    }
                }
            } else {
                Iterator<Map> it = fproductList.iterator();
                while (it.hasNext()) {
                    Map<String, Object> prod = it.next();
                    Object row[] = new String[5];
                    row[0] = prod.get("id").toString();
                    row[1] = prod.get("name").toString(); //NOMBRE
                    Map<String, Object> subC = category.getSubcategory(Integer.parseInt(prod.get("subcategory_id").toString()));
                    Map<String, Object> cat = category.getCategoryOfSubcategory(Integer.parseInt(prod.get("subcategory_id").toString()));
                    row[2] = cat.get("name").toString(); //CATEGORIA
                    row[3] = subC.get("name").toString(); //SUBCAT
                    row[4] = "Final"; // TIPO
                    tableProductsDefault.addRow(row);
                }
            }
        }
    }

    /**
     * ListSelectionListener en la tabla para cargar el producto en los txt, y
     * los productos correspondientes en la receta (si es que se esta
     * modificando o creando un producto final)
     *
     * @param evt
     * @throws RemoteException
     */
    public void tableProductsValueChanged() throws RemoteException {
        if (tableProducts.getSelectedRow() != -1) {
            if (!editingInformation) {
                guiCRUDFProduct.clicTableProducts();
                int id = Integer.parseInt((String) tableProducts.getValueAt(tableProducts.getSelectedRow(), 0));
                fproduct = crudFproduct.getFproduct(id);
                fproductPproductList = crudFproduct.getFproductPproduts(id);
                fproductEproductList = crudFproduct.getFproductEproduts(id);
                fproductFproductList = crudFproduct.getFproductFproduts(id);
                guiCRUDFProduct.loadProduct(fproduct);
                float productionPrice = crudFproduct.calculateProductionPrice(id);
                guiCRUDFProduct.getTxtProductionPrice().setText(ParserFloat.floatToString(productionPrice));
                guiCRUDFProduct.getTxtSuggestedPrice().setText(ParserFloat.floatToString(productionPrice + productionPrice * GeneralConfig.percent / 100));
                refreshReciperList();
            } else {
                if (!(isRepeatedOnTableReciper(tableProducts.getValueAt(tableProducts.getSelectedRow(), 1)))) {
                    Object row[] = new String[5];
                    row[0] = tableProducts.getValueAt(tableProducts.getSelectedRow(), 0); //id
                    row[1] = tableProducts.getValueAt(tableProducts.getSelectedRow(), 1); //NOMBRE                    
                    String idxs = tableProducts.getValueAt(tableProducts.getSelectedRow(), 0).toString();
                    int idx = Integer.valueOf(idxs);
                    if (((String) tableProducts.getValueAt(tableProducts.getSelectedRow(), 4)).equals("Primario")) {
                        String measureU = crudPproduct.getPproduct(idx).get("measure_unit").toString();
                        String quantity = JOptionPane.showInputDialog(guiCRUDFProduct, "Cantidad en " + measureU);
                        if (quantity != null) {
                            if (!quantity.isEmpty()) {
                                row[2] = quantity; // Cantidad  
                                row[3] = measureU; //unidad de medida
                                row[4] = "Primario"; // Tipo 
                                tableReciperDefault.addRow(row);
                                dinamicProductionPrice();
                                setCellEditor();
                            }
                        }
                    } else {
                        String quantity = JOptionPane.showInputDialog(guiCRUDFProduct, "Cantidad en unidades");
                        if (quantity != null) {
                            if (!quantity.isEmpty()) {
                                row[2] = quantity; // Cantidad  
                                row[3] = "unitario"; //unidad de medida
                                if (((String) tableProducts.getValueAt(tableProducts.getSelectedRow(), 4)).equals("Elaborado")) {
                                    row[4] = "Elaborado";// Tipo  
                                } else {
                                    row[4] = "Final";// Tipo  
                                }
                                tableReciperDefault.addRow(row);
                                dinamicProductionPrice();
                                setCellEditor();
                            }
                        }
                    }
                }
            }
        } else {
            if (!editingInformation) {
                tableReciperDefault.setRowCount(0);
                guiCRUDFProduct.getTxtId().setText("");
                guiCRUDFProduct.getTxtName().setText("");
                guiCRUDFProduct.getTxtProductionPrice().setText("");
                guiCRUDFProduct.getTxtSellPrice().setText("");
                guiCRUDFProduct.getTxtSuggestedPrice().setText("");
                guiCRUDFProduct.getBelong().setSelectedIndex(-1);
                guiCRUDFProduct.getCategory().setSelectedIndex(-1);
                guiCRUDFProduct.getCategory().removeAllItems();
                guiCRUDFProduct.getSubcategory().setSelectedIndex(-1);
                guiCRUDFProduct.getSubcategory().removeAllItems();
                guiCRUDFProduct.getTxtId().setEnabled(false);
                guiCRUDFProduct.getTxtName().setEnabled(false);
                guiCRUDFProduct.getTxtProductionPrice().setEnabled(false);
                guiCRUDFProduct.getTxtSellPrice().setEnabled(false);
                guiCRUDFProduct.getTxtSuggestedPrice().setEnabled(false);
                guiCRUDFProduct.getBelong().setEnabled(false);
                guiCRUDFProduct.getCategory().setEnabled(false);
                guiCRUDFProduct.getSubcategory().setEnabled(false);
                guiCRUDFProduct.getBtnNew().setEnabled(true);
                guiCRUDFProduct.getBtnCancel().setEnabled(false);
                guiCRUDFProduct.getBtnDelete().setEnabled(false);
                guiCRUDFProduct.getBtnModify().setEnabled(false);
                guiCRUDFProduct.getBtnSave().setEnabled(false);
                guiCRUDFProduct.getBtnAddCategory().setEnabled(false);
                guiCRUDFProduct.getBtnAddSubcategory().setEnabled(false);
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
     * Setea en txtRealGain la ganancia real
     *
     */
    public void calculateRealGain() {
        if (!guiCRUDFProduct.getTxtSellPrice().getText().isEmpty() && !guiCRUDFProduct.getTxtProductionPrice().getText().isEmpty()) {
            float sell = ParserFloat.stringToFloat(guiCRUDFProduct.getTxtSellPrice().getText());
            float production = ParserFloat.stringToFloat(guiCRUDFProduct.getTxtProductionPrice().getText());
            float dif = sell - production;
            float x = (dif * 100) / production;
            guiCRUDFProduct.getTxtRealGain().setText(ParserFloat.floatToString(x) + "%");
        }
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
                dinamicProductionPrice();
            }
        }
    }

    /**
     * actualiza el precio de costo cuando se edita una celda
     *
     * @param evt
     * @throws RemoteException
     */
    @Override
    public void editingStopped(ChangeEvent e) {
        try {
            dinamicProductionPrice();
        } catch (RemoteException ex) {
            Logger.getLogger(ControllerGuiCRUDFproduct.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Setea el CellEditorListener a las celdas de la receta.
     *
     */
    public void setCellEditor() {
        for (int i = 0; i < tableReciper.getRowCount(); i++) {
            tableReciper.getCellEditor(i, 2).addCellEditorListener(this);
        }
    }

    @Override
    public void editingCanceled(ChangeEvent e) {
        //  throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
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
            Object row[] = new String[5];
            row[0] = fpPp.get("pproduct_id").toString();
            row[1] = crudPproduct.getPproduct(Integer.parseInt(fpPp.get("pproduct_id").toString())).get("name").toString(); //NOMBRE
            row[2] = fpPp.get("amount").toString(); // Cantidad     
            row[3] = crudPproduct.getPproduct(Integer.parseInt(fpPp.get("pproduct_id").toString())).get("measure_unit").toString(); //UNidad de medida
            row[4] = "Primario"; // Tipo  
            tableReciperDefault.addRow(row);
        }
        for (Map fpEp : fproductEproductList) {
            Object row[] = new String[5];
            row[0] = fpEp.get("eproduct_id").toString();
            row[1] = crudEproduct.getEproduct(Integer.parseInt(fpEp.get("eproduct_id").toString())).get("name").toString(); //NOMBRE
            row[2] = fpEp.get("amount").toString(); // Cantidad     
            row[3] = "unitario"; //Unidad de medida
            row[4] = "Elaborado"; // Tipo  
            tableReciperDefault.addRow(row);
        }
        for (Map fpFp : fproductFproductList) {
            Object row[] = new String[5];
            row[0] = fpFp.get("fproduct_id").toString();
            row[1] = crudFproduct.getFproduct(Integer.parseInt(fpFp.get("fproduct_id").toString())).get("name").toString(); //NOMBRE
            row[2] = fpFp.get("amount").toString(); // Cantidad     
            row[3] = "unitario"; //Unidad de medida
            row[4] = "Final"; // Tipo  
            tableReciperDefault.addRow(row);
        }
        setCellEditor();
    }

    /**
     * Calcula dinamicamente el precio de produccion del producto;
     *
     */
    public void dinamicProductionPrice() throws RemoteException {
        int i = tableReciper.getRowCount();
        float price = 0;
        if (i > 0) {
            for (int j = 0; j < i; j++) {
                if (tableReciper.getValueAt(j, 4).toString().equals("Primario")) {
                    Map pp = crudPproduct.getPproduct(Integer.parseInt(tableReciper.getValueAt(j, 0).toString()));
                    price += ParserFloat.stringToFloat(pp.get("unit_price").toString()) * ParserFloat.stringToFloat(tableReciper.getValueAt(j, 2).toString());
                } else {
                    if (tableReciper.getValueAt(j, 4).toString().equals("Elaborado")) {
                        List<Map> leppp = crudEproduct.getEproductPproduts(Integer.parseInt(tableReciper.getValueAt(j, 0).toString()));
                        float priceEp = 0;
                        for (Map m : leppp) {
                            Map pp2 = crudPproduct.getPproduct(Integer.parseInt(m.get("pproduct_id").toString()));
                            priceEp += ParserFloat.stringToFloat(pp2.get("unit_price").toString()) * ParserFloat.stringToFloat(m.get("amount").toString());
                        }
                        price += priceEp * ParserFloat.stringToFloat(tableReciper.getValueAt(j, 2).toString());
                    } else {
                        try {
                            price += crudFproduct.calculateProductionPrice(Integer.parseInt(tableReciper.getValueAt(j, 0).toString())) * ParserFloat.stringToFloat(tableReciper.getValueAt(j, 2).toString());
                        } catch (RemoteException ex) {
                            Logger.getLogger(ControllerGuiCRUDFproduct.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                }
            }
        }
        guiCRUDFProduct.getTxtProductionPrice().setText(ParserFloat.floatToString(price));
        guiCRUDFProduct.getTxtSuggestedPrice().setText(ParserFloat.floatToString(price + price * GeneralConfig.percent / 100));
        calculateRealGain();
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
                fproductList = crudFproduct.getFproducts();
                refreshList();
            } catch (RemoteException ex) {
                Logger.getLogger(ControllerGuiCRUDFproduct.class.getName()).log(Level.SEVERE, null, ex);
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
                fproductList = crudFproduct.getFproducts();
                refreshList();
            } catch (RemoteException ex) {
                Logger.getLogger(ControllerGuiCRUDFproduct.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        if (e.getSource() == guiCRUDFProduct.getBtnSave() && editingInformation && isNew) {  //guardo un producto nuevo, boton guardar
            if (guiCRUDFProduct.checkFields()) {
                try {
                    Map subC = category.getSubcategory(guiCRUDFProduct.getSubcategory().getSelectedItem().toString());
                    int subcategory_id = Integer.parseInt(subC.get("id").toString());//obtener categoria
                    List<Pair> listP = new LinkedList<Pair>();
                    List<Pair> listE = new LinkedList<Pair>();
                    List<Pair> listF = new LinkedList<Pair>();
                    for (int i = 0; i < tableReciper.getRowCount(); i++) { //cargo la lista de productos
                        Pair p = new Pair(Integer.parseInt((String) tableReciper.getValueAt(i, 0)), ParserFloat.stringToFloat((String) tableReciper.getValueAt(i, 2)));
                        if (((String) tableReciper.getValueAt(i, 4)).equals("Primario")) {
                            listP.add(p);
                        } else {
                            if (((String) tableReciper.getValueAt(i, 4)).equals("Elaborado")) {
                                listE.add(p);
                            } else {
                                listF.add(p);
                            }
                        }
                    }
                    String name = guiCRUDFProduct.getTxtName().getText();
                    String belong = guiCRUDFProduct.getBelong().getSelectedItem().toString();
                    float sellPrice = ParserFloat.stringToFloat(guiCRUDFProduct.getTxtSellPrice().getText());
                    try {
                        crudFproduct.create(name, subcategory_id, listP, listE, sellPrice, belong, listF);
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
        }
        if (e.getSource() == guiCRUDFProduct.getBtnSave() && editingInformation && !isNew) {  //modifico un producto, boton guardar
            if (guiCRUDFProduct.checkFields()) {
                try {
                    Map subC = category.getSubcategory(guiCRUDFProduct.getSubcategory().getSelectedItem().toString());
                    int subcategory_id = Integer.parseInt(subC.get("id").toString());//obtener categoria
                    List<Pair> listP = new LinkedList<Pair>();
                    List<Pair> listE = new LinkedList<Pair>();
                    List<Pair> listF = new LinkedList<Pair>();
                    for (int i = 0; i < tableReciper.getRowCount(); i++) { //cargo la lista de productos
                        Pair p = new Pair(Integer.parseInt((String) tableReciper.getValueAt(i, 0)), ParserFloat.stringToFloat((String) tableReciper.getValueAt(i, 2)));
                        if (((String) tableReciper.getValueAt(i, 4)).equals("Primario")) {
                            listP.add(p);
                        } else {
                            if (((String) tableReciper.getValueAt(i, 4)).equals("Elaborado")) {
                                listE.add(p);
                            } else {
                                listF.add(p);
                            }
                        }
                    }
                    String name = guiCRUDFProduct.getTxtName().getText();
                    String belong = guiCRUDFProduct.getBelong().getSelectedItem().toString();
                    int id = Integer.parseInt(guiCRUDFProduct.getTxtId().getText());
                    float sellPrice = ParserFloat.stringToFloat(guiCRUDFProduct.getTxtSellPrice().getText());
                    try {
                        crudFproduct.modify(id, name, subcategory_id, listP, listE, sellPrice, belong, listF);
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
        if (e.getSource() == guiCRUDFProduct.getBtnCancel()) { //creo un producto pero cancel 
            isNew = false;
            editingInformation = false;
            tableReciperDefault.setRowCount(0);
            guiCRUDFProduct.getTxtId().setText("");
            guiCRUDFProduct.getTxtName().setText("");
            guiCRUDFProduct.getTxtProductionPrice().setText("");
            guiCRUDFProduct.getTxtSellPrice().setText("");
            guiCRUDFProduct.getTxtSuggestedPrice().setText("");
            guiCRUDFProduct.getBelong().setSelectedIndex(-1);
            guiCRUDFProduct.getCategory().setSelectedIndex(-1);
            guiCRUDFProduct.getCategory().removeAllItems();
            guiCRUDFProduct.getSubcategory().setSelectedIndex(-1);
            guiCRUDFProduct.getSubcategory().removeAllItems();
            guiCRUDFProduct.getTxtId().setEnabled(false);
            guiCRUDFProduct.getTxtName().setEnabled(false);
            guiCRUDFProduct.getTxtProductionPrice().setEnabled(false);
            guiCRUDFProduct.getTxtSellPrice().setEnabled(false);
            guiCRUDFProduct.getTxtSuggestedPrice().setEnabled(false);
            guiCRUDFProduct.getBelong().setEnabled(false);
            guiCRUDFProduct.getCategory().setEnabled(false);
            guiCRUDFProduct.getSubcategory().setEnabled(false);
            guiCRUDFProduct.getBtnNew().setEnabled(true);
            guiCRUDFProduct.getBtnCancel().setEnabled(false);
            guiCRUDFProduct.getBtnDelete().setEnabled(false);
            guiCRUDFProduct.getBtnModify().setEnabled(false);
            guiCRUDFProduct.getBtnSave().setEnabled(false);
            guiCRUDFProduct.getBtnAddCategory().setEnabled(false);
            guiCRUDFProduct.getBtnAddSubcategory().setEnabled(false);
            try {
                search();
            } catch (RemoteException ex) {
                Logger.getLogger(ControllerGuiCRUDFproduct.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        if (e.getSource() == guiCRUDFProduct.getBtnAddCategory()) { //crear una categoria nueva  
            String quantity = JOptionPane.showInputDialog(guiCRUDFProduct, "Nombre de la categoria:");
            if (quantity != null) {
                if (!quantity.isEmpty()) {
                    try {
                        category.create(quantity);
                        guiCRUDFProduct.loadCategory();
                        guiCRUDFProduct.loadCategorySearch();
                    } catch (RemoteException ex) {
                        Logger.getLogger(ControllerGuiCRUDFproduct.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        }
        if (e.getSource() == guiCRUDFProduct.getBtnAddSubcategory()) { //crear una subcategoria nueva  
            if (guiCRUDFProduct.getCategory().getSelectedIndex() != -1) {
                String quantity = JOptionPane.showInputDialog(guiCRUDFProduct, "Nombre de la subcategoria:");
                if (quantity != null) {
                    if (!quantity.isEmpty()) {
                        try {
                            int id = Integer.valueOf(category.getCategoryByName(guiCRUDFProduct.getCategory().getSelectedItem().toString()).get("id").toString());
                            category.addSubcategory(id, quantity);
                            guiCRUDFProduct.loadSubCategory(guiCRUDFProduct.getCategory().getSelectedItem().toString());
                        } catch (RemoteException ex) {
                            Logger.getLogger(ControllerGuiCRUDFproduct.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                }
            }
        }

    }
}
