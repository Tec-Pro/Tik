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
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;
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
public class GuiCRUDPProduct extends javax.swing.JInternalFrame {

    private DefaultTableModel tableProductsDefault; //Tabla Default para tener las opciones de insertar y eliminar filas
    private InterfaceCategory CRUDCategory;
    
    /**
     * Creates new form GuiCRUDProductCategory
     */
    public GuiCRUDPProduct() {
        initComponents();
        tableProductsDefault = (DefaultTableModel) tableProducts.getModel(); //convierto la tabla
    }

    /**
     * setea el CRUDCategoria
     *
     * @param CRUDCategory
     */
    public void setCRUDCategory(InterfaceCategory CRUDCategory) {
        this.CRUDCategory = CRUDCategory;
    }
    
    /**
     *
     * @param lis
     */
    public void setActionListener(ActionListener lis) {
        this.btnDelete.addActionListener(lis);
        this.btnModify.addActionListener(lis);
        this.btnNew.addActionListener(lis);
        this.btnSave.addActionListener(lis);
        this.btnPurchase.addActionListener(lis);
        this.btnCancel.addActionListener(lis);
        this.btnNewCategory.addActionListener(lis);
    }

    /**
     * Limpia todos los campos.
     */
    public void clear() {
        txtSearch.setText("");
        txtId.setText("");
        txtPrice.setText("");
        txtName.setText("");
        txtStock.setText("");
        cboxMeasureUnit.setSelectedIndex(-1);
        lblPriceUnity.setText("");
        lblStockUnity.setText("");
        proveedores.removeAllItems();
        proveedores.addItem("Seleccione un proveedor");
        boxCategory.setSelectedIndex(-1);
        boxSubcategory.setSelectedIndex(-1);
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
        btnNewCategory.setEnabled(true);
        txtPrice.setEnabled(true);
        txtName.setEnabled(true);
        txtStock.setEnabled(true);
        btnSave.setEnabled(true);
        btnCancel.setEnabled(true);
        btnPurchase.setEnabled(false);
        btnDelete.setEnabled(false);
        btnModify.setEnabled(false);
        cboxMeasureUnit.setEnabled(true);
        proveedores.setEnabled(true);
        boxCategory.setEnabled(true);
        boxSubcategory.setEnabled(true);
    }

    /**
     * habilita y desabilita los campos y botones como sea necesario cuando haga
     * clic en guardar
     */
    public void clicSaveProduct() {
        clear();
        txtPrice.setEnabled(false);
        txtName.setEnabled(false);
        txtStock.setEnabled(false);
        btnSave.setEnabled(false);
        btnPurchase.setEnabled(false);
        btnDelete.setEnabled(false);
        btnModify.setEnabled(false);
        btnCancel.setEnabled(false);
        btnNew.setEnabled(true);
        btnNewCategory.setEnabled(false);
        cboxMeasureUnit.setEnabled(false);
        proveedores.setEnabled(false);
        boxCategory.setEnabled(false);
        boxSubcategory.setEnabled(false);
    }

    /**
     * habilita y desabilita los campos y botones como sea necesario cuando haga
     * clic en la tabla producto
     *
     * @throws RemoteException
     */
    public void clicTableProducts() throws RemoteException {
        clear();
        btnNew.setEnabled(true);
        btnDelete.setEnabled(true);
        btnModify.setEnabled(true);
        btnPurchase.setEnabled(true);

    }

    /**
     * habilita y desabilita los campos y botones como sea necesario cuando haga
     * clic en eliminar
     */
    public void clicDeleteProduct() {
        clear();
        txtPrice.setEnabled(false);
        txtName.setEnabled(false);
        txtStock.setEnabled(false);
        btnSave.setEnabled(false);
        btnPurchase.setEnabled(false);
        btnDelete.setEnabled(false);
        btnModify.setEnabled(false);
        btnCancel.setEnabled(false);
        btnNew.setEnabled(true);
        btnNewCategory.setEnabled(false);
        cboxMeasureUnit.setEnabled(false);
        proveedores.setEnabled(false);
        boxCategory.setEnabled(false);
        boxSubcategory.setEnabled(false);
    }

    /**
     * habilita y desabilita los campos y botones como sea necesario cuando haga
     * clic en modificar
     *
     * @throws RemoteException
     */
    public void clicModifyProduct() throws RemoteException {
        txtPrice.setEnabled(true);
        txtName.setEnabled(true);
        txtStock.setEnabled(true);
        btnSave.setEnabled(true);
        btnPurchase.setEnabled(false);
        btnDelete.setEnabled(false);
        btnModify.setEnabled(false);
        btnCancel.setEnabled(true);
        btnNew.setEnabled(false);
        btnNewCategory.setEnabled(false);
        cboxMeasureUnit.setEditable(true);
        proveedores.setEnabled(true);
        boxCategory.setEnabled(true);
        boxSubcategory.setEnabled(true);
    }

    /**
     *
     * @return
     */
    public DefaultTableModel getTableProductsDefault() {
        return tableProductsDefault;
    }

    /**
     *
     * @return
     */
    public JButton getBtnDelete() {
        return btnDelete;
    }

    public JComboBox getBoxCategory() {
        return boxCategory;
    }

    public JComboBox getBoxSubcategory() {
        return boxSubcategory;
    }

    /**
     *
     * @return
     */
    public JButton getBtnModify() {
        return btnModify;
    }

    public JButton getBtnCancel() {
        return btnCancel;
    }

    public JButton getBtnNewCategory() {
        return btnNewCategory;
    }

    /**
     *
     * @return
     */
    public JButton getBtnNew() {
        return btnNew;
    }

    public JButton getBtnPurchase() {
        return btnPurchase;
    }

    /**
     *
     * @return
     */
    public JButton getBtnSave() {
        return btnSave;
    }

    /**
     *
     * @return
     */
    public JTable getTableProducts() {
        return tableProducts;
    }

    /**
     *
     * @return
     */
    public JTextField getTxtId() {
        return txtId;
    }

    public JComboBox getCboxMeasureUnit() {
        return cboxMeasureUnit;
    }

    /**
     *
     * @return
     */
    public JTextField getTxtName() {
        return txtName;
    }

    /**
     *
     * @return
     */
    public JFormattedTextField getTxtPrice() {
        return txtPrice;
    }

    /**
     *
     * @return
     */
    public JTextField getTxtSearch() {
        return txtSearch;
    }

    /**
     *
     * @return
     */
    public JFormattedTextField getTxtStock() {
        return txtStock;
    }

    /**
     * cargae el producto en los txt
     *
     * @param prod
     * @throws RemoteException
     */
    public void loadProduct(Map<String, Object> prod) throws RemoteException {
        txtId.setText(prod.get("id").toString());
        txtStock.setText(ParserFloat.floatToString((float) prod.get("stock")));
        if (prod.get("measure_unit").toString().equals("unitario")) {
            txtPrice.setText(ParserFloat.floatToString((float) prod.get("unit_price")));
        } else {
            txtPrice.setText(ParserFloat.floatToString((float) prod.get("unit_price") * 1000));
        }
        txtName.setText(prod.get("name").toString());
        cboxMeasureUnit.setSelectedItem(prod.get("measure_unit").toString());
        setProviderBox((Integer)prod.get("provider_id"));
        boxCategory.setSelectedIndex(-1);
        boxCategory.removeAllItems();
        loadCategory();
        Map<String, Object> cat = CRUDCategory.getCategoryOfPproductSubcategory(Integer.parseInt(prod.get("pproductsubcategory_id").toString()));
        Map<String, Object> subC = CRUDCategory.getPproductSubcategory(Integer.parseInt(prod.get("pproductsubcategory_id").toString()));
        boxCategory.setSelectedItem(cat.get("name").toString()); //CATEGORIA
        loadSubCategory(cat.get("name").toString());
        boxSubcategory.setSelectedItem(subC.get("name").toString()); //SubCATEGORIA  
    }

    public void loadProviders(List<Map> providers) {

        proveedores.removeAllItems();
        Iterator<Map> it = providers.iterator();
        while (it.hasNext()) {
            Map prov = it.next();
            proveedores.addItem(prov.get("id") + "-" + prov.get("name"));
        }
        proveedores.addItem("Seleccione un proveedor");
        proveedores.setSelectedItem("Seleccione un proveedor");
    }
    
    public void setProviderBox(Integer id){
            boolean notFound=true;
            for(int i=0;i<proveedores.getItemCount()&&notFound;i++){
                String item=(String)proveedores.getItemAt(i);
                if(!"Seleccione un proveedor".equals(item)){
                Integer idItem=Integer.valueOf(item.split("-")[0]);
                if(Integer.compare(id, idItem)==0){
                    notFound=false;
                    proveedores.setSelectedIndex(i);
                }
                }
            }
            if(notFound)
                proveedores.setSelectedItem("Seleccione un proveedor");
    }
    
    public Integer getIdProviderSelected(){
        String prov=(String)proveedores.getSelectedItem();
        if (!prov.equals("Seleccione un proveedor")){
            return Integer.valueOf(proveedores.getSelectedItem().toString().split("-")[0]);
        }
        return 0;
    }

    /**
     * carga las categorias en el select category
     */
    public void loadCategory() throws RemoteException {
        boxCategory.setSelectedIndex(-1);
        boxCategory.removeAllItems();
        for (Map cat : CRUDCategory.getPproductCategories()) {
            boxCategory.addItem(cat.get("name"));
        }
    }

    /**
     * carga las subcategorias en el select
     */
    public void loadSubCategory(String name) throws RemoteException {
        boxSubcategory.setSelectedIndex(-1);
        boxSubcategory.removeAllItems();
        Map cat = CRUDCategory.getPproductCategoryByName(name);
        for (Iterator<Map> it = CRUDCategory.getPproducSubcategoriesCategory(Integer.parseInt(cat.get("id").toString())).iterator(); it.hasNext();) {
            Map subC = it.next();
            boxSubcategory.addItem(subC.get("name"));
        }
    }
    
    
    /**
     * chequea que los campos no esten vacios
     */
    public boolean checkFields() {
        if (txtPrice.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Precio vacio, por favor complete el campo");
            return false;
        }
        if (txtStock.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Stock vacio, por favor complete el campo");
            return false;
        }
        if (txtName.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Nombre vacio, por favor complete el campo");
            return false;
        }
        if (cboxMeasureUnit.getSelectedIndex() == -1) {
            JOptionPane.showMessageDialog(this, "No hay unidad de medida seleccionada, por favor seleccione una");
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
        panelImage1 = new org.edisoncor.gui.panel.PanelImage();
        jScrollPane4 = new javax.swing.JScrollPane();
        tableProducts = new javax.swing.JTable();
        txtSearch = new javax.swing.JTextField();
        panelImage2 = new org.edisoncor.gui.panel.PanelImage();
        jLabel2 = new javax.swing.JLabel();
        txtId = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        txtName = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        txtPrice = new javax.swing.JFormattedTextField();
        jLabel9 = new javax.swing.JLabel();
        txtStock = new javax.swing.JFormattedTextField();
        cboxMeasureUnit = new javax.swing.JComboBox();
        lblPriceUnity = new javax.swing.JLabel();
        lblStockUnity = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        proveedores = new javax.swing.JComboBox();
        jLabel1 = new javax.swing.JLabel();
        boxCategory = new javax.swing.JComboBox();
        jLabel3 = new javax.swing.JLabel();
        boxSubcategory = new javax.swing.JComboBox();
        panelImage3 = new org.edisoncor.gui.panel.PanelImage();
        btnDelete = new javax.swing.JButton();
        btnModify = new javax.swing.JButton();
        btnSave = new javax.swing.JButton();
        btnNew = new javax.swing.JButton();
        btnPurchase = new javax.swing.JButton();
        btnCancel = new javax.swing.JButton();
        btnNewCategory = new javax.swing.JButton();
        jLabel5 = new javax.swing.JLabel();

        setClosable(true);
        setDefaultCloseOperation(javax.swing.WindowConstants.HIDE_ON_CLOSE);
        setIconifiable(true);
        setMaximizable(true);
        setTitle("Gestion Productos Primarios");
        setPreferredSize(new java.awt.Dimension(1095, 664));
        setVerifyInputWhenFocusTarget(false);

        panelImage1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/fondo gris.png"))); // NOI18N

        tableProducts.setAutoCreateRowSorter(true);
        tableProducts.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Id", "Nombre", "Stock", "Precio por unidad", "Unidad de medida"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.Object.class
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
        jScrollPane4.setViewportView(tableProducts);

        txtSearch.setToolTipText("Búsqueda personalizada");
        txtSearch.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtSearchActionPerformed(evt);
            }
        });

        panelImage2.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Datos del Producto Primario", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Dialog", 0, 15), new java.awt.Color(255, 255, 255))); // NOI18N
        panelImage2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/fondo gris.png"))); // NOI18N

        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setText("ID");

        txtId.setEditable(false);
        txtId.setToolTipText("Código del artículo");
        txtId.setDisabledTextColor(new java.awt.Color(16, 2, 245));
        txtId.setEnabled(false);

        jLabel4.setForeground(new java.awt.Color(255, 255, 255));
        jLabel4.setText("Unidad de medida");

        txtName.setToolTipText("Nombre");
        txtName.setDisabledTextColor(new java.awt.Color(16, 2, 245));
        txtName.setEnabled(false);
        txtName.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNameActionPerformed(evt);
            }
        });

        jLabel6.setForeground(new java.awt.Color(255, 255, 255));
        jLabel6.setText("Nombre");

        jLabel8.setForeground(new java.awt.Color(255, 255, 255));
        jLabel8.setText("Precio Unitario");

        txtPrice.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#0.00"))));
        txtPrice.setEnabled(false);
        txtPrice.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtPriceActionPerformed(evt);
            }
        });

        jLabel9.setForeground(new java.awt.Color(255, 255, 255));
        jLabel9.setText("Stock");

        txtStock.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#0.00"))));
        txtStock.setEnabled(false);

        cboxMeasureUnit.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "gr", "ml", "unitario" }));
        cboxMeasureUnit.setSelectedIndex(-1);
        cboxMeasureUnit.setEnabled(false);
        cboxMeasureUnit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cboxMeasureUnitActionPerformed(evt);
            }
        });

        lblPriceUnity.setForeground(new java.awt.Color(255, 255, 255));

        lblStockUnity.setForeground(new java.awt.Color(255, 255, 255));

        jLabel10.setForeground(new java.awt.Color(255, 255, 255));
        jLabel10.setText("Proveedor");

        proveedores.setEnabled(false);

        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("Categoria");

        boxCategory.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        boxCategory.setEnabled(false);
        boxCategory.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                boxCategoryActionPerformed(evt);
            }
        });

        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setText("Subcategoria");

        boxSubcategory.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        boxSubcategory.setEnabled(false);

        panelImage3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/fondo gris.png"))); // NOI18N

        btnDelete.setText("Borrar");
        btnDelete.setToolTipText("Borrar artículo seleccionado");
        btnDelete.setEnabled(false);

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

        btnNew.setText("Nuevo");
        btnNew.setToolTipText("Crear artículo nuevo");

        btnPurchase.setText("Compra");
        btnPurchase.setToolTipText("Registrar Compra");
        btnPurchase.setEnabled(false);

        btnCancel.setText("Cancelar ");
        btnCancel.setToolTipText("Cancelar cambios realizados");
        btnCancel.setEnabled(false);
        btnCancel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout panelImage3Layout = new javax.swing.GroupLayout(panelImage3);
        panelImage3.setLayout(panelImage3Layout);
        panelImage3Layout.setHorizontalGroup(
            panelImage3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelImage3Layout.createSequentialGroup()
                .addComponent(btnDelete, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(btnModify, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(btnNew, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(btnSave, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(btnPurchase, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(btnCancel, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(93, Short.MAX_VALUE))
        );
        panelImage3Layout.setVerticalGroup(
            panelImage3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelImage3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                .addComponent(btnDelete, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(btnModify, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(btnSave, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(btnNew, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(btnPurchase, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(btnCancel, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        btnNewCategory.setText("+ Categoría");
        btnNewCategory.setEnabled(false);

        javax.swing.GroupLayout panelImage2Layout = new javax.swing.GroupLayout(panelImage2);
        panelImage2.setLayout(panelImage2Layout);
        panelImage2Layout.setHorizontalGroup(
            panelImage2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelImage2Layout.createSequentialGroup()
                .addGroup(panelImage2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel4)
                    .addGroup(panelImage2Layout.createSequentialGroup()
                        .addComponent(jLabel8)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lblPriceUnity, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelImage2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtPrice)
                    .addComponent(cboxMeasureUnit, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelImage2Layout.createSequentialGroup()
                .addGroup(panelImage2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 103, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2))
                .addGap(35, 35, 35)
                .addGroup(panelImage2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelImage2Layout.createSequentialGroup()
                        .addComponent(txtId, javax.swing.GroupLayout.PREFERRED_SIZE, 113, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel10)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(proveedores, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(81, 81, 81)
                        .addComponent(jLabel9)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lblStockUnity, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(4, 4, 4)
                        .addComponent(txtStock, javax.swing.GroupLayout.PREFERRED_SIZE, 121, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(txtName)))
            .addGroup(panelImage2Layout.createSequentialGroup()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelImage2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelImage2Layout.createSequentialGroup()
                        .addGap(12, 12, 12)
                        .addComponent(panelImage3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(panelImage2Layout.createSequentialGroup()
                        .addComponent(boxCategory, javax.swing.GroupLayout.PREFERRED_SIZE, 334, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(boxSubcategory, javax.swing.GroupLayout.PREFERRED_SIZE, 338, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnNewCategory)))
                .addGap(0, 119, Short.MAX_VALUE))
        );
        panelImage2Layout.setVerticalGroup(
            panelImage2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelImage2Layout.createSequentialGroup()
                .addGroup(panelImage2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblStockUnity, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(panelImage2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(proveedores, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel10))
                    .addGroup(panelImage2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel2)
                        .addComponent(txtId, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel9)
                        .addComponent(txtStock, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelImage2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel6))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelImage2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(cboxMeasureUnit, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(panelImage2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelImage2Layout.createSequentialGroup()
                        .addGap(4, 4, 4)
                        .addGroup(panelImage2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel8)
                            .addComponent(txtPrice, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelImage2Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lblPriceUnity, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelImage2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(boxCategory, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3)
                    .addComponent(boxSubcategory, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnNewCategory))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(panelImage3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        jLabel5.setForeground(new java.awt.Color(255, 255, 255));
        jLabel5.setText("Buscar:");

        javax.swing.GroupLayout panelImage1Layout = new javax.swing.GroupLayout(panelImage1);
        panelImage1.setLayout(panelImage1Layout);
        panelImage1Layout.setHorizontalGroup(
            panelImage1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelImage1Layout.createSequentialGroup()
                .addGroup(panelImage1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(panelImage2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(panelImage1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(panelImage1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane4)
                            .addGroup(panelImage1Layout.createSequentialGroup()
                                .addComponent(jLabel5)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtSearch)
                                .addGap(83, 83, 83)))))
                .addContainerGap())
        );
        panelImage1Layout.setVerticalGroup(
            panelImage1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelImage1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(panelImage1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtSearch, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel5))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 353, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(panelImage2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(24, 24, 24))
        );

        jScrollPane1.setViewportView(panelImage1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 1229, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 667, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void txtSearchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtSearchActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtSearchActionPerformed

    private void txtNameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNameActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtNameActionPerformed

    private void txtPriceActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtPriceActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtPriceActionPerformed

    private void cboxMeasureUnitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cboxMeasureUnitActionPerformed
        if (cboxMeasureUnit.getSelectedIndex() != -1) {
            if (cboxMeasureUnit.getSelectedItem().toString().equals("gr")) {
                lblPriceUnity.setText("Kg");
                lblStockUnity.setText("gr");
            }
            if (cboxMeasureUnit.getSelectedItem().toString().equals("ml")) {
                lblPriceUnity.setText("L");
                lblStockUnity.setText("ml");
            }
            if (cboxMeasureUnit.getSelectedItem().toString().equals("unitario")) {
                lblPriceUnity.setText("u");
                lblStockUnity.setText("u");
            }

        }
    }//GEN-LAST:event_cboxMeasureUnitActionPerformed

    private void boxCategoryActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_boxCategoryActionPerformed
        try {
            if (boxCategory.getSelectedIndex() != -1) {
                loadSubCategory(boxCategory.getItemAt(boxCategory.getSelectedIndex()).toString());
            }
        } catch (RemoteException ex) {
            Logger.getLogger(GuiCRUDFProduct.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_boxCategoryActionPerformed

    private void btnModifyActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnModifyActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnModifyActionPerformed

    private void btnCancelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnCancelActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox boxCategory;
    private javax.swing.JComboBox boxSubcategory;
    private javax.swing.JButton btnCancel;
    private javax.swing.JButton btnDelete;
    private javax.swing.JButton btnModify;
    private javax.swing.JButton btnNew;
    private javax.swing.JButton btnNewCategory;
    private javax.swing.JButton btnPurchase;
    private javax.swing.JButton btnSave;
    private javax.swing.JComboBox cboxMeasureUnit;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JLabel lblPriceUnity;
    private javax.swing.JLabel lblStockUnity;
    private org.edisoncor.gui.panel.PanelImage panelImage1;
    private org.edisoncor.gui.panel.PanelImage panelImage2;
    private org.edisoncor.gui.panel.PanelImage panelImage3;
    private javax.swing.JComboBox proveedores;
    private javax.swing.JTable tableProducts;
    private javax.swing.JTextField txtId;
    private javax.swing.JTextField txtName;
    private javax.swing.JFormattedTextField txtPrice;
    private javax.swing.JTextField txtSearch;
    private javax.swing.JFormattedTextField txtStock;
    // End of variables declaration//GEN-END:variables
}
