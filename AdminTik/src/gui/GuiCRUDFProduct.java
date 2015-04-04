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
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

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
    }

    public void setActionListener(ActionListener lis) {
        this.btnDelete.addActionListener(lis);
        this.btnModify.addActionListener(lis);
        this.btnNew.addActionListener(lis);
        this.btnSave.addActionListener(lis);
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
        txtId.setText("");
        txtName.setText("");
        category.setSelectedIndex(-1);
        category.removeAllItems();
        tableProductsDefault.setRowCount(0);
    }

    /**
     * carga las subcategorias en el select
     */
    public void loadCategory() throws RemoteException {
        for (Map subC : CRUDCategory.getCategories()) {
            category.addItem(subC.get("name"));
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
        btnSave.setEnabled(true);
        btnDelete.setEnabled(false);
        btnModify.setEnabled(false);
        tableReciper.setEnabled(true);
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
    }

     /**
     * habilita y desabilita los campos y botones como sea necesario cuando haga
     * clic en la tabla producto
     *
     * @throws RemoteException
     */
    public void clicTableProducts() {
        clear();
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
    }

     /**
     * habilita y desabilita los campos y botones como sea necesario cuando haga
     * clic en eliminar
     */
    public void clicModifyProduct() {
        clear();
        txtName.setEnabled(true);
        category.setEnabled(true);
        btnSave.setEnabled(true);
        btnDelete.setEnabled(false);
        btnModify.setEnabled(false);
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

     /**
     * cargae el producto final en los txt
     * @param prod
     * @throws RemoteException
     */
    public void loadProduct(Map<String, Object> prod) throws RemoteException {
        category.setSelectedIndex(-1);
        category.removeAllItems();
        loadCategory();
        txtId.setText(prod.get("id").toString());
        txtName.setText(prod.get("name").toString());
        Map<String, Object> subC = (Map<String, Object>) CRUDCategory.getSubcategoriesCategory(Integer.parseInt(prod.get("subcategory_id").toString()));
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
        panelImage1 = new org.edisoncor.gui.panel.PanelImage();
        panelCurves1 = new org.edisoncor.gui.panel.PanelCurves();
        panelImage2 = new org.edisoncor.gui.panel.PanelImage();
        txtSearch = new javax.swing.JTextField();
        jScrollPane2 = new javax.swing.JScrollPane();
        tableProducts = new javax.swing.JTable();
        jPanel7 = new javax.swing.JPanel();
        jPanel8 = new javax.swing.JPanel();
        btnDelete = new javax.swing.JButton();
        btnNew = new javax.swing.JButton();
        btnModify = new javax.swing.JButton();
        btnSave = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        txtId = new javax.swing.JTextField();
        txtName = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        category = new javax.swing.JComboBox();
        jLabel10 = new javax.swing.JLabel();
        jScrollPane3 = new javax.swing.JScrollPane();
        tableReciper = new javax.swing.JTable();

        setClosable(true);
        setDefaultCloseOperation(javax.swing.WindowConstants.HIDE_ON_CLOSE);
        setIconifiable(true);
        setMaximizable(true);
        setTitle("Gestion Productos Elaborados");
        setPreferredSize(new java.awt.Dimension(1095, 627));

        panelImage1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/gui/images/red.jpg"))); // NOI18N

        panelImage2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/gui/images/black.jpg"))); // NOI18N

        txtSearch.setForeground(new java.awt.Color(130, 125, 120));
        txtSearch.setText("Buscar producto...");
        txtSearch.setToolTipText("Búsqueda personalizada");

        tableProducts.setAutoCreateRowSorter(true);
        tableProducts.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Id", "Nombre", "Stock", "Categoria", "Unidad de medida", "Tipo"
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
        jScrollPane2.setViewportView(tableProducts);
        tableProducts.getColumnModel().getColumn(3).setHeaderValue("Categoria");
        tableProducts.getColumnModel().getColumn(4).setHeaderValue("Unidad de medida");

        jPanel7.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Datos del producto elaborado", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Century Schoolbook L", 1, 14))); // NOI18N

        jPanel8.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

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

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(btnDelete, javax.swing.GroupLayout.DEFAULT_SIZE, 100, Short.MAX_VALUE)
                    .addComponent(btnNew, javax.swing.GroupLayout.DEFAULT_SIZE, 100, Short.MAX_VALUE)
                    .addComponent(btnSave, javax.swing.GroupLayout.DEFAULT_SIZE, 100, Short.MAX_VALUE)
                    .addComponent(btnModify, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(0, 0, 0))
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addComponent(btnDelete, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(btnNew, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(btnModify, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(btnSave, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        jLabel1.setText("ID");

        txtId.setEditable(false);
        txtId.setToolTipText("Código del artículo");
        txtId.setDisabledTextColor(new java.awt.Color(16, 2, 245));
        txtId.setEnabled(false);

        txtName.setToolTipText("Precio de compra");
        txtName.setDisabledTextColor(new java.awt.Color(16, 2, 245));
        txtName.setEnabled(false);
        txtName.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNameActionPerformed(evt);
            }
        });

        jLabel5.setText("Nombre");

        category.setEnabled(false);

        jLabel10.setText("Categoria");

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 103, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel10, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addComponent(jLabel1))
                .addGap(18, 18, 18)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addComponent(txtId, javax.swing.GroupLayout.PREFERRED_SIZE, 113, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 615, Short.MAX_VALUE))
                    .addComponent(category, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(txtName))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(txtId, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel5))
                .addGap(18, 18, 18)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(category, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel10)))
        );

        tableReciper.setAutoCreateRowSorter(true);
        tableReciper.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Id", "Nombre", "Cantidad", "Tipo"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, true, true
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tableReciper.setEnabled(false);
        jScrollPane3.setViewportView(tableReciper);

        javax.swing.GroupLayout panelImage2Layout = new javax.swing.GroupLayout(panelImage2);
        panelImage2.setLayout(panelImage2Layout);
        panelImage2Layout.setHorizontalGroup(
            panelImage2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelImage2Layout.createSequentialGroup()
                .addGap(580, 580, 580)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 477, Short.MAX_VALUE)
                .addGap(25, 25, 25))
            .addGroup(panelImage2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(panelImage2Layout.createSequentialGroup()
                    .addGap(86, 86, 86)
                    .addGroup(panelImage2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(panelImage2Layout.createSequentialGroup()
                            .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addContainerGap(25, Short.MAX_VALUE))
                        .addGroup(panelImage2Layout.createSequentialGroup()
                            .addGroup(panelImage2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                .addComponent(jScrollPane2, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 477, Short.MAX_VALUE)
                                .addComponent(txtSearch))
                            .addGap(519, 519, 519)))))
        );
        panelImage2Layout.setVerticalGroup(
            panelImage2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelImage2Layout.createSequentialGroup()
                .addGap(44, 44, 44)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 209, Short.MAX_VALUE)
                .addGap(227, 227, 227))
            .addGroup(panelImage2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(panelImage2Layout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(txtSearch, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 209, Short.MAX_VALUE)
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                    .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(38, 38, 38)))
        );

        javax.swing.GroupLayout panelImage1Layout = new javax.swing.GroupLayout(panelImage1);
        panelImage1.setLayout(panelImage1Layout);
        panelImage1Layout.setHorizontalGroup(
            panelImage1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(panelCurves1, javax.swing.GroupLayout.DEFAULT_SIZE, 1082, Short.MAX_VALUE)
            .addGroup(panelImage1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(panelImage2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        panelImage1Layout.setVerticalGroup(
            panelImage1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(panelCurves1, javax.swing.GroupLayout.DEFAULT_SIZE, 597, Short.MAX_VALUE)
            .addGroup(panelImage1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelImage1Layout.createSequentialGroup()
                    .addGap(56, 56, 56)
                    .addComponent(panelImage2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGap(61, 61, 61)))
        );

        jScrollPane1.setViewportView(panelImage1);

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

    private void txtNameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNameActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtNameActionPerformed
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnDelete;
    private javax.swing.JButton btnModify;
    private javax.swing.JButton btnNew;
    private javax.swing.JButton btnSave;
    private javax.swing.JComboBox category;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private org.edisoncor.gui.panel.PanelCurves panelCurves1;
    private org.edisoncor.gui.panel.PanelImage panelImage1;
    private org.edisoncor.gui.panel.PanelImage panelImage2;
    private javax.swing.JTable tableProducts;
    private javax.swing.JTable tableReciper;
    private javax.swing.JTextField txtId;
    private javax.swing.JTextField txtName;
    private javax.swing.JTextField txtSearch;
    // End of variables declaration//GEN-END:variables
}
