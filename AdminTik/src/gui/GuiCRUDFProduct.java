/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import interfaces.InterfaceCategory;
import java.awt.event.ActionListener;
import java.rmi.RemoteException;
import java.util.Iterator;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFormattedTextField;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import utils.ParserFloat;

/**
 *
 * @author A
 */
public class GuiCRUDFProduct extends javax.swing.JInternalFrame {

    private DefaultTableModel tableProductsDefault; //Tabla Default para tener las opciones de insertar y eliminar filas
    private DefaultTableModel tableReciperDefault;
    private InterfaceCategory CRUDCategory;

    /**
     * Creates new form GuiCRUDProductCategory
     */
    public GuiCRUDFProduct() {
        initComponents();
        tableProductsDefault = (DefaultTableModel) tableProducts.getModel(); //convierto la tabla
        tableReciperDefault = (DefaultTableModel) tableReciper.getModel();
        this.setVisible(false);
    }

    public void setActionListener(ActionListener lis) {
        this.btnDelete.addActionListener(lis);
        this.btnModify.addActionListener(lis);
        this.btnNew.addActionListener(lis);
        this.btnSave.addActionListener(lis);
        this.btnCancel.addActionListener(lis);
        this.btnAddSubcategory.addActionListener(lis);
        this.btnAddCategory.addActionListener(lis);
    }

    /**
     * setea el CRUDCategoria
     *
     * @param CRUDCategory
     */
    public void setCRUDCategory(InterfaceCategory CRUDCategory) {
        this.CRUDCategory = CRUDCategory;
    }

    public void clear() {
        txtSearch.setText("");
        txtId.setText("");
        txtName.setText("");
        category.setSelectedIndex(-1);
        category.removeAllItems();
        subcategory.setSelectedIndex(-1);
        subcategory.removeAllItems();
        tableProductsDefault.setRowCount(0);
        tableReciperDefault.setRowCount(0);
        txtSellPrice.setText("");
        txtSuggestedPrice.setText("");
        txtProductionPrice.setText("");
        belong.setSelectedIndex(-1);
        txtRealGain.setText("");
        chboxSearchByCategory.setSelected(false);
        categorySearch.setSelectedIndex(-1);
    }
    
     /**
     * carga las categorias en el select categorySearch
     */
    public void loadCategorySearch() throws RemoteException {
        categorySearch.setSelectedIndex(-1);
        categorySearch.removeAllItems();
        for (Map cat : CRUDCategory.getCategories()) {
            categorySearch.addItem(cat.get("name"));
        }
        categorySearch.setSelectedIndex(-1);
    }
    
    

    /**
     * carga las categorias en el select category
     */
    public void loadCategory() throws RemoteException {
        category.setSelectedIndex(-1);
        category.removeAllItems();
        for (Map cat : CRUDCategory.getCategories()) {
            category.addItem(cat.get("name"));
        }
    }

    /**
     * carga las subcategorias en el select
     */
    public void loadSubCategory(String name) throws RemoteException {
        subcategory.setSelectedIndex(-1);
        subcategory.removeAllItems();
        Map cat = CRUDCategory.getCategoryByName(name);
        for (Iterator<Map> it = CRUDCategory.getSubcategoriesCategory(Integer.parseInt(cat.get("id").toString())).iterator(); it.hasNext();) {
            Map subC = it.next();
            subcategory.addItem(subC.get("name"));
        }
    }

    /**
     * habilita y desabilita los campos y botones como sea necesario cuando haga
     * clic en nuevo
     *
     * @throws RemoteException
     */
    public void clicNewProduct() throws RemoteException {
        clear();
        loadCategory();
        txtName.setEnabled(true);
        category.setEnabled(true);
        subcategory.setEnabled(true);
        btnSave.setEnabled(true);
        btnDelete.setEnabled(false);
        btnModify.setEnabled(false);
        tableReciper.setEnabled(true);
        btnCancel.setEnabled(true);
        txtSellPrice.setEnabled(true);
        btnAddCategory.setEnabled(true);
        btnAddSubcategory.setEnabled(true);
        belong.setEnabled(true);
    }

    /**
     * habilita y desabilita los campos y botones como sea necesario cuando haga
     * clic en guardar
     */
    public void clicSaveProduct() {
        clear();
        txtName.setEnabled(false);
        category.setEnabled(false);
        btnSave.setEnabled(false);
        btnDelete.setEnabled(false);
        btnModify.setEnabled(false);
        tableReciper.setEnabled(false);
        subcategory.setEnabled(false);
        btnCancel.setEnabled(false);
        txtSellPrice.setEnabled(false);
        btnAddCategory.setEnabled(false);
        btnAddSubcategory.setEnabled(false);
        belong.setEnabled(false);
    }

    /**
     * habilita y desabilita los campos y botones como sea necesario cuando haga
     * clic en la tabla producto
     *
     * @throws RemoteException
     */
    public void clicTableProducts() {
        tableReciperDefault.setRowCount(0);
        btnNew.setEnabled(true);
        btnDelete.setEnabled(true);
        btnModify.setEnabled(true);
    }

    public void clicDeleteProduct() {
        clear();
        txtName.setEnabled(false);
        category.setEnabled(false);
        btnSave.setEnabled(false);
        btnDelete.setEnabled(false);
        btnModify.setEnabled(false);
        tableReciper.setEnabled(false);
        subcategory.setEnabled(false);
        btnCancel.setEnabled(false);
        txtSellPrice.setEnabled(false);
        btnAddCategory.setEnabled(false);
        btnAddSubcategory.setEnabled(false);
        belong.setEnabled(false);
    }

    /**
     * habilita y desabilita los campos y botones como sea necesario cuando haga
     * clic en eliminar
     */
    public void clicModifyProduct() {
        txtName.setEnabled(true);
        category.setEnabled(true);
        btnSave.setEnabled(true);
        btnDelete.setEnabled(false);
        btnModify.setEnabled(false);
        tableReciper.setEnabled(true);
        btnCancel.setEnabled(true);
        subcategory.setEnabled(true);
        txtSellPrice.setEnabled(true);
        btnAddCategory.setEnabled(true);
        btnAddSubcategory.setEnabled(true);
        belong.setEnabled(true);
    }

    public JComboBox getBelong() {
        return belong;
    }    
    
    public DefaultTableModel getTableProductsDefault() {
        return tableProductsDefault;
    }

    public DefaultTableModel getTableReciperDefault() {
        return tableReciperDefault;
    }

    public JTable getTableReciper() {
        return tableReciper;
    }

    public JComboBox getCategory() {
        return category;
    }

    public JButton getBtnDelete() {
        return btnDelete;
    }

    public JButton getBtnModify() {
        return btnModify;
    }

    public JButton getBtnNew() {
        return btnNew;
    }

    public JButton getBtnSave() {
        return btnSave;
    }

    public JTable getTableProducts() {
        return tableProducts;
    }

    public JTextField getTxtId() {
        return txtId;
    }

    public JTextField getTxtName() {
        return txtName;
    }

    public JTextField getTxtSearch() {
        return txtSearch;
    }

    public JButton getBtnCancel() {
        return btnCancel;
    }

    public JComboBox getSubcategory() {
        return subcategory;
    }

    public JButton getBtnAddCategory() {
        return btnAddCategory;
    }

    public JButton getBtnAddSubcategory() {
        return btnAddSubcategory;
    }

    public JFormattedTextField getTxtProductionPrice() {
        return txtProductionPrice;
    }

    public JFormattedTextField getTxtSellPrice() {
        return txtSellPrice;
    }

    public JFormattedTextField getTxtSuggestedPrice() {
        return txtSuggestedPrice;
    }

    public JTextField getTxtRealGain() {
        return txtRealGain;
    }

    public JComboBox getCategorySearch() {
        return categorySearch;
    }

    public JCheckBox getChboxSearchByCategory() {
        return chboxSearchByCategory;
    }
    
    
    /**
     * Setea en txtRealGain la ganancia real
     *
     */
    public void calculateRealGain() {
        if (!txtSellPrice.getText().isEmpty() && !txtProductionPrice.getText().isEmpty()) {
            float sell = ParserFloat.stringToFloat(txtSellPrice.getText());
            float production = ParserFloat.stringToFloat(txtProductionPrice.getText());
            float dif = sell - production;
            float x = (dif * 100) / production;
            txtRealGain.setText(ParserFloat.floatToString(x) + "%");
        }
    }

    /**
     * cargae el producto final en los txt
     *
     * @param prod
     * @throws RemoteException
     */
    public void loadProduct(Map<String, Object> prod) throws RemoteException {
        category.setSelectedIndex(-1);
        category.removeAllItems();
        loadCategory();
        txtId.setText(prod.get("id").toString());
        txtName.setText(prod.get("name").toString());
        Map<String, Object> cat = CRUDCategory.getCategoryOfSubcategory(Integer.parseInt(prod.get("subcategory_id").toString()));
        Map<String, Object> subC = CRUDCategory.getSubcategory(Integer.parseInt(prod.get("subcategory_id").toString()));
        category.setSelectedItem(cat.get("name").toString()); //CATEGORIA
        loadSubCategory(cat.get("name").toString());
        subcategory.setSelectedItem(subC.get("name").toString()); //SubCATEGORIA  
        txtSellPrice.setText(ParserFloat.floatToString( (float)prod.get("sell_price")));
        belong.setSelectedItem(prod.get("belong").toString());
    }

    /**
     * chequea que los campos no esten vacios
     */
    public boolean checkFields() {
        if (txtName.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Nombre vacio, por favor complete el campo");
            return false;
        }
        if (tableReciper.getRowCount() < 1) {
            JOptionPane.showMessageDialog(this, "No se cargo ningun producto primario");
            return false;
        }
        if (category.getSelectedIndex() == -1) {
            JOptionPane.showMessageDialog(this, "No hay categoria seleccionada, por favor seleccione una");
            return false;
        }
        if (subcategory.getSelectedIndex() == -1) {
            JOptionPane.showMessageDialog(this, "No hay subcategoria seleccionada, por favor seleccione una");
            return false;
        }
        if (txtSellPrice.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Precio de venta vacio, por favor complete el campo");
            return false;
        }
        if (belong.getSelectedIndex() == -1) {
            JOptionPane.showMessageDialog(this, "No hay pertenencia, por favor seleccione una");
            return false;
        }
        return true;
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        jPanel1 = new javax.swing.JPanel();
        jPanel9 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        txtId = new javax.swing.JTextField();
        txtName = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        category = new javax.swing.JComboBox();
        jLabel11 = new javax.swing.JLabel();
        subcategory = new javax.swing.JComboBox();
        jLabel12 = new javax.swing.JLabel();
        jPanel8 = new javax.swing.JPanel();
        btnDelete = new javax.swing.JButton();
        btnNew = new javax.swing.JButton();
        btnModify = new javax.swing.JButton();
        btnSave = new javax.swing.JButton();
        btnCancel = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        txtProductionPrice = new javax.swing.JFormattedTextField();
        txtSuggestedPrice = new javax.swing.JFormattedTextField();
        txtSellPrice = new javax.swing.JFormattedTextField();
        btnAddCategory = new javax.swing.JButton();
        btnAddSubcategory = new javax.swing.JButton();
        belong = new javax.swing.JComboBox();
        jLabel13 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        txtRealGain = new javax.swing.JTextField();
        txtSearch = new javax.swing.JTextField();
        jScrollPane2 = new javax.swing.JScrollPane();
        tableReciper = new javax.swing.JTable();
        jScrollPane3 = new javax.swing.JScrollPane();
        tableProducts = new javax.swing.JTable();
        chboxSearchByCategory = new javax.swing.JCheckBox();
        jLabel14 = new javax.swing.JLabel();
        categorySearch = new javax.swing.JComboBox();

        setClosable(true);
        setDefaultCloseOperation(javax.swing.WindowConstants.HIDE_ON_CLOSE);
        setIconifiable(true);
        setMaximizable(true);
        setTitle("Gestion Productos Finales");
        setPreferredSize(new java.awt.Dimension(1103, 633));
        setVisible(true);

        jScrollPane1.setPreferredSize(new java.awt.Dimension(1095, 627));

        jPanel9.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Datos del producto final", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Century Schoolbook L", 1, 14))); // NOI18N

        jLabel2.setText("ID");

        txtId.setEditable(false);
        txtId.setToolTipText("Código del artículo");
        txtId.setDisabledTextColor(new java.awt.Color(16, 2, 245));
        txtId.setEnabled(false);

        txtName.setToolTipText("Nombre");
        txtName.setDisabledTextColor(new java.awt.Color(16, 2, 245));
        txtName.setEnabled(false);
        txtName.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNameActionPerformed(evt);
            }
        });

        jLabel6.setText("Nombre");

        category.setEnabled(false);
        category.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                categoryActionPerformed(evt);
            }
        });

        jLabel11.setText("Categoria");

        subcategory.setEnabled(false);

        jLabel12.setText("Subcategoria");

        jPanel8.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        btnDelete.setText("Borrar");
        btnDelete.setToolTipText("Borrar artículo seleccionado");
        btnDelete.setEnabled(false);

        btnNew.setText("Nuevo");
        btnNew.setToolTipText("Crear artículo nuevo");

        btnModify.setText("Modificar");
        btnModify.setToolTipText("Modificar artículo seleccionado");
        btnModify.setEnabled(false);
        btnModify.setMaximumSize(new java.awt.Dimension(53, 28));
        btnModify.setMinimumSize(new java.awt.Dimension(53, 28));
        btnModify.setPreferredSize(new java.awt.Dimension(53, 28));
        btnModify.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnModifyActionPerformed(evt);
            }
        });

        btnSave.setText("Guardar");
        btnSave.setToolTipText("Guardar cambios realizados");
        btnSave.setEnabled(false);

        btnCancel.setText("Cancelar");
        btnCancel.setToolTipText("Cancelar cambios realizados");
        btnCancel.setEnabled(false);

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addComponent(btnDelete, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(btnModify, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(btnNew, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(btnSave, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(btnCancel, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0))
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnDelete, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnNew, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnModify, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnSave, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnCancel, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(0, 0, 0))
        );

        jLabel3.setText("Costo de produccion");

        jLabel4.setText("Precio sugerido");

        jLabel5.setText("Precio de venta");

        txtProductionPrice.setEditable(false);
        txtProductionPrice.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#0.00"))));
        txtProductionPrice.setEnabled(false);

        txtSuggestedPrice.setEditable(false);
        txtSuggestedPrice.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#0.00"))));
        txtSuggestedPrice.setEnabled(false);

        txtSellPrice.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#0.00"))));
        txtSellPrice.setEnabled(false);
        txtSellPrice.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtSellPriceKeyReleased(evt);
            }
        });

        btnAddCategory.setText("+");
        btnAddCategory.setEnabled(false);

        btnAddSubcategory.setText("+");
        btnAddSubcategory.setEnabled(false);

        belong.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Cocina", "Bar", "Promo" }));
        belong.setEnabled(false);
        belong.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                belongActionPerformed(evt);
            }
        });

        jLabel13.setText("Pertenece");

        jLabel7.setText("Ganancia real");

        txtRealGain.setEditable(false);
        txtRealGain.setToolTipText("");
        txtRealGain.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        txtRealGain.setEnabled(false);

        javax.swing.GroupLayout jPanel9Layout = new javax.swing.GroupLayout(jPanel9);
        jPanel9.setLayout(jPanel9Layout);
        jPanel9Layout.setHorizontalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 103, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel11, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel12, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addComponent(jLabel2))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel9Layout.createSequentialGroup()
                        .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jPanel9Layout.createSequentialGroup()
                                .addComponent(jPanel8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(199, 199, 199))
                            .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addComponent(subcategory, javax.swing.GroupLayout.Alignment.LEADING, 0, 882, Short.MAX_VALUE)
                                .addComponent(category, javax.swing.GroupLayout.Alignment.LEADING, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(btnAddCategory)
                            .addComponent(btnAddSubcategory)))
                    .addGroup(jPanel9Layout.createSequentialGroup()
                        .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(txtName)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel9Layout.createSequentialGroup()
                                .addComponent(txtId, javax.swing.GroupLayout.PREFERRED_SIZE, 113, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jLabel3)
                                .addGap(3, 3, 3)
                                .addComponent(txtProductionPrice, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel13, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(txtSuggestedPrice, javax.swing.GroupLayout.DEFAULT_SIZE, 125, Short.MAX_VALUE)
                            .addComponent(belong, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel5)
                            .addComponent(jLabel7))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtSellPrice, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtRealGain, javax.swing.GroupLayout.PREFERRED_SIZE, 113, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(54, Short.MAX_VALUE))
        );
        jPanel9Layout.setVerticalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(txtId, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3)
                    .addComponent(jLabel4)
                    .addComponent(jLabel5)
                    .addComponent(txtProductionPrice, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtSuggestedPrice, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtSellPrice, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(belong, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel13)
                        .addComponent(txtRealGain, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel7))
                    .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(txtName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel6)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(category, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel11)
                    .addComponent(btnAddCategory))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(subcategory, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel12)
                    .addComponent(btnAddSubcategory))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jPanel8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        tableReciper.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null}
            },
            new String [] {
                "Id", "Nombre", "Cantidad", "Unidad de medida", "Tipo"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.Object.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, true, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane2.setViewportView(tableReciper);

        tableProducts.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null}
            },
            new String [] {
                "Id", "Nombre", "Categoria", "Subcategoria", "Tipo"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane3.setViewportView(tableProducts);

        chboxSearchByCategory.setText("Buscar por categoria");
        chboxSearchByCategory.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chboxSearchByCategoryActionPerformed(evt);
            }
        });

        jLabel14.setText("Categoria");

        categorySearch.setEnabled(false);
        categorySearch.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                categorySearchActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(txtSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 532, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(chboxSearchByCategory)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel14)
                        .addGap(18, 18, 18)
                        .addComponent(categorySearch, 0, 119, Short.MAX_VALUE)
                        .addGap(168, 168, 168))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jScrollPane3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane2))
                    .addComponent(jPanel9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(0, 0, 0))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(categorySearch, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel14))
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(txtSearch, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(chboxSearchByCategory)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 328, Short.MAX_VALUE)
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 280, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0))
        );

        jScrollPane1.setViewportView(jPanel1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 1092, Short.MAX_VALUE)
                .addGap(0, 0, 0))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(12, 12, 12)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 593, Short.MAX_VALUE)
                .addGap(0, 0, 0))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void txtNameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNameActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtNameActionPerformed

    private void btnModifyActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnModifyActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnModifyActionPerformed

    private void categoryActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_categoryActionPerformed
        try {
            if (category.getSelectedIndex() != -1) {
                loadSubCategory(category.getItemAt(category.getSelectedIndex()).toString());
            }
        } catch (RemoteException ex) {
            Logger.getLogger(GuiCRUDFProduct.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_categoryActionPerformed

    private void belongActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_belongActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_belongActionPerformed

    private void categorySearchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_categorySearchActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_categorySearchActionPerformed

    private void chboxSearchByCategoryActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chboxSearchByCategoryActionPerformed
        if (chboxSearchByCategory.isSelected()) {
            categorySearch.setEnabled(true);
        } else {
            categorySearch.setEnabled(false);
        }
    }//GEN-LAST:event_chboxSearchByCategoryActionPerformed

    private void txtSellPriceKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtSellPriceKeyReleased
        calculateRealGain();        // TODO add your handling code here:
    }//GEN-LAST:event_txtSellPriceKeyReleased

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox belong;
    private javax.swing.JButton btnAddCategory;
    private javax.swing.JButton btnAddSubcategory;
    private javax.swing.JButton btnCancel;
    private javax.swing.JButton btnDelete;
    private javax.swing.JButton btnModify;
    private javax.swing.JButton btnNew;
    private javax.swing.JButton btnSave;
    private javax.swing.JComboBox category;
    private javax.swing.JComboBox categorySearch;
    private javax.swing.JCheckBox chboxSearchByCategory;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JComboBox subcategory;
    private javax.swing.JTable tableProducts;
    private javax.swing.JTable tableReciper;
    private javax.swing.JTextField txtId;
    private javax.swing.JTextField txtName;
    private javax.swing.JFormattedTextField txtProductionPrice;
    private javax.swing.JTextField txtRealGain;
    private javax.swing.JTextField txtSearch;
    private javax.swing.JFormattedTextField txtSellPrice;
    private javax.swing.JFormattedTextField txtSuggestedPrice;
    // End of variables declaration//GEN-END:variables
}
