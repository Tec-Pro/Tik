/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import java.awt.event.ActionListener;
import java.rmi.RemoteException;
import java.util.Map;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFormattedTextField;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author A
 */
public class GuiCRUDPProduct extends javax.swing.JInternalFrame {

    private DefaultTableModel tableProductsDefault; //Tabla Default para tener las opciones de insertar y eliminar filas

    /**
     * Creates new form GuiCRUDProductCategory
     */
    public GuiCRUDPProduct() {
        initComponents();
        tableProductsDefault = (DefaultTableModel) tableProducts.getModel(); //convierto la tabla
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
    }

    /**
     * habilita y desabilita los campos y botones como sea necesario cuando haga
     * clic en nuevo
     *
     * @throws RemoteException
     */
    public void clicNewProduct() throws RemoteException {
        clear();
        txtPrice.setEnabled(true);
        txtName.setEnabled(true);
        txtStock.setEnabled(true);
        btnSave.setEnabled(true);
        btnCancel.setEnabled(true);
        btnPurchase.setEnabled(false);
        btnDelete.setEnabled(false);
        btnModify.setEnabled(false);
        cboxMeasureUnit.setEnabled(true);
        txtPrice.setText("0");
        txtStock.setText("0");
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
        cboxMeasureUnit.setEnabled(false);
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
        cboxMeasureUnit.setEnabled(false);
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
        cboxMeasureUnit.setEditable(true);
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
        txtStock.setText(prod.get("stock").toString());
        txtPrice.setText(String.valueOf(prod.get("unit_price").toString()));
        txtName.setText(prod.get("name").toString());
        cboxMeasureUnit.setSelectedItem(prod.get("measure_unit").toString());
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
            JOptionPane.showMessageDialog(this, "Cantidad vacia, por favor complete el campo");
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
        jPanel1 = new javax.swing.JPanel();
        jPanel9 = new javax.swing.JPanel();
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
        jPanel11 = new javax.swing.JPanel();
        btnDelete = new javax.swing.JButton();
        btnModify = new javax.swing.JButton();
        btnSave = new javax.swing.JButton();
        btnNew = new javax.swing.JButton();
        btnPurchase = new javax.swing.JButton();
        btnCancel = new javax.swing.JButton();
        jScrollPane4 = new javax.swing.JScrollPane();
        tableProducts = new javax.swing.JTable();
        txtSearch = new javax.swing.JTextField();

        setClosable(true);
        setDefaultCloseOperation(javax.swing.WindowConstants.HIDE_ON_CLOSE);
        setIconifiable(true);
        setMaximizable(true);
        setTitle("Gestion Productos Primarios");
        setPreferredSize(new java.awt.Dimension(1095, 664));
        setVerifyInputWhenFocusTarget(false);

        jPanel9.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Datos del producto", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Century Schoolbook L", 1, 14))); // NOI18N

        jLabel2.setText("ID");

        txtId.setEditable(false);
        txtId.setToolTipText("Código del artículo");
        txtId.setDisabledTextColor(new java.awt.Color(16, 2, 245));
        txtId.setEnabled(false);

        jLabel4.setText("Unidad de medida");

        txtName.setToolTipText("Nombre");
        txtName.setDisabledTextColor(new java.awt.Color(16, 2, 245));
        txtName.setEnabled(false);

        jLabel6.setText("Nombre");

        jLabel8.setText("Precio Unitario");

        txtPrice.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#0.00"))));
        txtPrice.setEnabled(false);
        txtPrice.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtPriceActionPerformed(evt);
            }
        });

        jLabel9.setText("Stock");

        txtStock.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#0.00"))));
        txtStock.setEnabled(false);

        cboxMeasureUnit.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "gr", "ml", "unitario" }));
        cboxMeasureUnit.setSelectedIndex(-1);
        cboxMeasureUnit.setEnabled(false);

        jPanel11.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

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

        javax.swing.GroupLayout jPanel11Layout = new javax.swing.GroupLayout(jPanel11);
        jPanel11.setLayout(jPanel11Layout);
        jPanel11Layout.setHorizontalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel11Layout.createSequentialGroup()
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
                .addComponent(btnCancel, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        jPanel11Layout.setVerticalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel11Layout.createSequentialGroup()
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnDelete, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnModify, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnSave, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnNew, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnPurchase, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnCancel, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(0, 0, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel9Layout = new javax.swing.GroupLayout(jPanel9);
        jPanel9.setLayout(jPanel9Layout);
        jPanel9Layout.setHorizontalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                .addGroup(jPanel9Layout.createSequentialGroup()
                    .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jLabel4)
                        .addComponent(jLabel8))
                    .addGap(6, 6, 6)
                    .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(txtPrice)
                        .addComponent(cboxMeasureUnit, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addGroup(jPanel9Layout.createSequentialGroup()
                    .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 103, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel2))
                    .addGap(17, 17, 17)
                    .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel9Layout.createSequentialGroup()
                            .addComponent(txtId, javax.swing.GroupLayout.PREFERRED_SIZE, 113, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel9)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(txtStock, javax.swing.GroupLayout.PREFERRED_SIZE, 121, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addComponent(txtName))))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel9Layout.createSequentialGroup()
                .addContainerGap(47, Short.MAX_VALUE)
                .addComponent(jPanel11, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(47, Short.MAX_VALUE))
        );
        jPanel9Layout.setVerticalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
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
                    .addComponent(jLabel4)
                    .addComponent(cboxMeasureUnit, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(4, 4, 4)
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8)
                    .addComponent(txtPrice, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jPanel11, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        tableProducts.setAutoCreateRowSorter(true);
        tableProducts.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Id", "Nombre", "Stock", "Unidad de medida", "Precio por unidad"
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
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(txtSearch)
                        .addGap(433, 433, 433))
                    .addComponent(jScrollPane4)
                    .addComponent(jPanel9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addComponent(txtSearch, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane4, javax.swing.GroupLayout.DEFAULT_SIZE, 263, Short.MAX_VALUE)
                .addGap(0, 0, 0)
                .addComponent(jPanel9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(10, 10, 10))
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

    private void txtSearchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtSearchActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtSearchActionPerformed

    private void btnModifyActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnModifyActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnModifyActionPerformed

    private void txtPriceActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtPriceActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtPriceActionPerformed

    private void btnCancelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnCancelActionPerformed
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCancel;
    private javax.swing.JButton btnDelete;
    private javax.swing.JButton btnModify;
    private javax.swing.JButton btnNew;
    private javax.swing.JButton btnPurchase;
    private javax.swing.JButton btnSave;
    private javax.swing.JComboBox cboxMeasureUnit;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel11;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JTable tableProducts;
    private javax.swing.JTextField txtId;
    private javax.swing.JTextField txtName;
    private javax.swing.JFormattedTextField txtPrice;
    private javax.swing.JTextField txtSearch;
    private javax.swing.JFormattedTextField txtStock;
    // End of variables declaration//GEN-END:variables
}
