/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import interfaces.InterfaceCategory;
import java.awt.event.ActionListener;
import java.rmi.RemoteException;
import java.util.Map;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFormattedTextField;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

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
    }

    /**
     * Limpia todos los campos.
     */
    public void clear() {
        txtSearch.setText("");
        txtId.setText("");
        txtMeasureUnit.setText("");
        txtPurchasePrice.setText("");
        txtName.setText("");
        txtQuantity.setText("");
        txtStock.setText("");
        category.setSelectedIndex(-1);
        category.removeAllItems();
    }

    /**
     * Carga todas las subcategorias en el select de categoria
     *
     * @throws RemoteException
     */
    public void loadCategory() throws RemoteException {
        for (Map subC : CRUDCategory.getSubcategoriesCategory()) {
            category.addItem(subC.get("name").toString());
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
        txtMeasureUnit.setEnabled(true);
        txtPurchasePrice.setEnabled(true);
        txtName.setEnabled(true);
        txtQuantity.setEnabled(true);
        txtStock.setEnabled(true);
        category.setEnabled(true);
        btnSave.setEnabled(true);
        btnDelete.setEnabled(false);
        btnModify.setEnabled(false);
    }

    /**
     * habilita y desabilita los campos y botones como sea necesario cuando haga
     * clic en guardar
     */
    public void clicSaveProduct() {
        clear();
        txtMeasureUnit.setEnabled(false);
        txtPurchasePrice.setEnabled(false);
        txtName.setEnabled(false);
        txtQuantity.setEnabled(false);
        txtStock.setEnabled(false);
        category.setEnabled(false);
        btnSave.setEnabled(false);
        btnDelete.setEnabled(false);
        btnModify.setEnabled(false);
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
    }

    /**
     * habilita y desabilita los campos y botones como sea necesario cuando haga
     * clic en eliminar
     */
    public void clicDeleteProduct() {
        clear();
        txtMeasureUnit.setEnabled(false);
        txtPurchasePrice.setEnabled(false);
        txtName.setEnabled(false);
        txtQuantity.setEnabled(false);
        txtStock.setEnabled(false);
        category.setEnabled(false);
        btnSave.setEnabled(false);
        btnDelete.setEnabled(false);
        btnModify.setEnabled(false);
    }

    /**
     * habilita y desabilita los campos y botones como sea necesario cuando haga
     * clic en modificar
     *
     * @throws RemoteException
     */
    public void clicModifyProduct() throws RemoteException {
        txtMeasureUnit.setEnabled(true);
        txtPurchasePrice.setEnabled(true);
        txtName.setEnabled(true);
        txtQuantity.setEnabled(true);
        txtStock.setEnabled(true);
        category.setEnabled(true);
        btnSave.setEnabled(true);
        btnDelete.setEnabled(false);
        btnModify.setEnabled(false);
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
    public JComboBox getCategory() {
        return category;
    }

    /**
     *
     * @return
     */
    public JButton getBtnDelete() {
        return btnDelete;
    }

    /**
     *
     * @return
     */
    public JButton getBtnModify() {
        return btnModify;
    }

    /**
     *
     * @return
     */
    public JButton getBtnNew() {
        return btnNew;
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

    /**
     *
     * @return
     */
    public JTextField getTxtMeasureUnit() {
        return txtMeasureUnit;
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
    public JFormattedTextField getTxtPurchasePrice() {
        return txtPurchasePrice;
    }

    /**
     *
     * @return
     */
    public JTextField getTxtQuantity() {
        return txtQuantity;
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
        category.setSelectedIndex(-1);
        category.removeAllItems();
        loadCategory();
        txtId.setText(prod.get("id").toString());
        txtStock.setText(prod.get("stock").toString());
        float amount = Float.parseFloat(prod.get("amount").toString());
        float unitPrice = Float.parseFloat(prod.get("unit_price").toString());
        float purchasePrice = (amount * unitPrice);
        txtQuantity.setText(prod.get("amount").toString());
        txtPurchasePrice.setText(String.valueOf(purchasePrice));
        txtName.setText(prod.get("name").toString());
        txtMeasureUnit.setText(prod.get("measure_unit").toString());;
        Map<String, Object> subC = CRUDCategory.getSubcategory(Integer.parseInt(prod.get("subcategory_id").toString()));
        category.setSelectedItem(subC.get("name").toString()); //CATEGORIA
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
        jPanel10 = new javax.swing.JPanel();
        btnDelete = new javax.swing.JButton();
        btnNew = new javax.swing.JButton();
        btnModify = new javax.swing.JButton();
        btnSave = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        txtId = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        txtName = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        txtQuantity = new javax.swing.JTextField();
        category = new javax.swing.JComboBox();
        jLabel11 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        txtMeasureUnit = new javax.swing.JTextField();
        txtPurchasePrice = new javax.swing.JFormattedTextField();
        jLabel9 = new javax.swing.JLabel();
        txtStock = new javax.swing.JFormattedTextField();
        jScrollPane4 = new javax.swing.JScrollPane();
        tableProducts = new javax.swing.JTable();
        txtSearch = new javax.swing.JTextField();

        setClosable(true);
        setDefaultCloseOperation(javax.swing.WindowConstants.HIDE_ON_CLOSE);
        setIconifiable(true);
        setMaximizable(true);
        setTitle("Gestion Productos Primarios");
        setPreferredSize(new java.awt.Dimension(1095, 627));

        jPanel9.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Datos del producto", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Century Schoolbook L", 1, 14))); // NOI18N

        jPanel10.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        btnDelete.setText("Borrar");
        btnDelete.setToolTipText("Borrar artículo seleccionado");
        btnDelete.setEnabled(false);

        btnNew.setText("Nuevo");
        btnNew.setToolTipText("Crear artículo nuevo");

        btnModify.setText("Modificar");
        btnModify.setToolTipText("Modificar artículo seleccionado");
        btnModify.setEnabled(false);
        btnModify.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnModifyActionPerformed(evt);
            }
        });

        btnSave.setText("Guardar");
        btnSave.setToolTipText("Guardar cambios realizados");
        btnSave.setEnabled(false);

        javax.swing.GroupLayout jPanel10Layout = new javax.swing.GroupLayout(jPanel10);
        jPanel10.setLayout(jPanel10Layout);
        jPanel10Layout.setHorizontalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(btnDelete, javax.swing.GroupLayout.DEFAULT_SIZE, 100, Short.MAX_VALUE)
                    .addComponent(btnNew, javax.swing.GroupLayout.DEFAULT_SIZE, 100, Short.MAX_VALUE)
                    .addComponent(btnSave, javax.swing.GroupLayout.DEFAULT_SIZE, 100, Short.MAX_VALUE)
                    .addComponent(btnModify, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(0, 0, 0))
        );
        jPanel10Layout.setVerticalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addComponent(btnDelete, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(btnNew, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(btnModify, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(btnSave, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        jLabel2.setText("ID");

        txtId.setEditable(false);
        txtId.setToolTipText("Código del artículo");
        txtId.setDisabledTextColor(new java.awt.Color(16, 2, 245));
        txtId.setEnabled(false);

        jLabel4.setText("Unidad de medida");

        txtName.setToolTipText("Precio de compra");
        txtName.setDisabledTextColor(new java.awt.Color(16, 2, 245));
        txtName.setEnabled(false);

        jLabel6.setText("Nombre");

        jLabel7.setText("Cantidad");

        txtQuantity.setToolTipText("Precio de venta");
        txtQuantity.setDisabledTextColor(new java.awt.Color(16, 2, 245));
        txtQuantity.setEnabled(false);

        category.setEnabled(false);

        jLabel11.setText("Categoria");

        jLabel8.setText("Precio de compra");

        txtMeasureUnit.setToolTipText("Marca del artículo");
        txtMeasureUnit.setDisabledTextColor(new java.awt.Color(16, 2, 245));
        txtMeasureUnit.setEnabled(false);

        txtPurchasePrice.setEnabled(false);

        jLabel9.setText("Stock");

        txtStock.setEnabled(false);

        javax.swing.GroupLayout jPanel9Layout = new javax.swing.GroupLayout(jPanel9);
        jPanel9.setLayout(jPanel9Layout);
        jPanel9Layout.setHorizontalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 103, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel7, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel11, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addComponent(jLabel4)
                    .addComponent(jLabel8)
                    .addComponent(jLabel2))
                .addGap(6, 6, 6)
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel9Layout.createSequentialGroup()
                        .addComponent(txtId, javax.swing.GroupLayout.PREFERRED_SIZE, 113, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 378, Short.MAX_VALUE)
                        .addComponent(jLabel9)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtStock, javax.swing.GroupLayout.PREFERRED_SIZE, 121, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(5, 5, 5))
                    .addComponent(category, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(txtQuantity)
                    .addComponent(txtName)
                    .addComponent(txtMeasureUnit)
                    .addComponent(txtPurchasePrice))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel10, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        jPanel9Layout.setVerticalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel9Layout.createSequentialGroup()
                        .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel2)
                            .addComponent(txtId, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel9)
                            .addComponent(txtStock, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel6))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtQuantity, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel7))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(category, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel11))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel4)
                            .addComponent(txtMeasureUnit, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(jPanel10, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(0, 0, 0)
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8)
                    .addComponent(txtPurchasePrice, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
        );

        tableProducts.setAutoCreateRowSorter(true);
        tableProducts.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Id", "Nombre", "Stock", "Categoria", "Unidad de medida", "Precio por unidad"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane4.setViewportView(tableProducts);

        txtSearch.setForeground(java.awt.Color.black);
        txtSearch.setToolTipText("Búsqueda personalizada");
        txtSearch.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtSearchActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1082, Short.MAX_VALUE)
            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel1Layout.createSequentialGroup()
                    .addGap(86, 86, 86)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jPanel9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanel1Layout.createSequentialGroup()
                            .addComponent(txtSearch)
                            .addGap(433, 433, 433))
                        .addComponent(jScrollPane4))
                    .addGap(86, 86, 86)))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 597, Short.MAX_VALUE)
            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel1Layout.createSequentialGroup()
                    .addGap(70, 70, 70)
                    .addComponent(txtSearch, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                    .addComponent(jScrollPane4, javax.swing.GroupLayout.DEFAULT_SIZE, 209, Short.MAX_VALUE)
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                    .addComponent(jPanel9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(71, 71, 71)))
        );

        jScrollPane1.setViewportView(jPanel1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnModifyActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnModifyActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnModifyActionPerformed

    private void txtSearchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtSearchActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtSearchActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnDelete;
    private javax.swing.JButton btnModify;
    private javax.swing.JButton btnNew;
    private javax.swing.JButton btnSave;
    private javax.swing.JComboBox category;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JTable tableProducts;
    private javax.swing.JTextField txtId;
    private javax.swing.JTextField txtMeasureUnit;
    private javax.swing.JTextField txtName;
    private javax.swing.JFormattedTextField txtPurchasePrice;
    private javax.swing.JTextField txtQuantity;
    private javax.swing.JTextField txtSearch;
    private javax.swing.JFormattedTextField txtStock;
    // End of variables declaration//GEN-END:variables
}
