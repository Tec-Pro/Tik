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
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author A
 */
public class GuiCRUDEProduct extends javax.swing.JInternalFrame {

    private DefaultTableModel tableProductsDefault; //Tabla Default para tener las opciones de insertar y eliminar filas
    private DefaultTableModel tableReciperDefault;

    /**
     * Creates new form GuiCRUDProductCategory
     */
    public GuiCRUDEProduct() {
        initComponents();
        tableProductsDefault = (DefaultTableModel) tableProducts.getModel(); //convierto la tabla
        tableReciperDefault = (DefaultTableModel) tableReciper.getModel();
    }

    public void setActionListener(ActionListener lis) {
        this.btnDelete.addActionListener(lis);
        this.btnModify.addActionListener(lis);
        this.btnNew.addActionListener(lis);
        this.btnSave.addActionListener(lis);
        this.btnCancel.addActionListener(lis);
    }

    /**
     * Limpia todos los campos.
     */
    public void clear() {
        txtSearch.setText("");
        txtId.setText("");
        txtName.setText("");
        tableProductsDefault.setRowCount(0);
        tableReciperDefault.setRowCount(0);
    }

    /**
     * habilita y desabilita los campos y botones como sea necesario cuando haga
     * clic en nuevo
     *
     * @throws RemoteException
     */
    public void clicNewProduct() throws RemoteException {
        clear();
        txtName.setEnabled(true);
        btnSave.setEnabled(true);
        btnDelete.setEnabled(false);
        btnModify.setEnabled(false);
        tableReciper.setEnabled(true);
        btnCancel.setEnabled(true);
    }

    /**
     * habilita y desabilita los campos y botones como sea necesario cuando haga
     * clic en guardar
     */
    public void clicSaveProduct() {
        clear();
        txtName.setEnabled(false);
        btnSave.setEnabled(false);
        btnDelete.setEnabled(false);
        btnModify.setEnabled(false);
        tableReciper.setEnabled(false);
        btnCancel.setEnabled(false);
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

    /**
     * habilita y desabilita los campos y botones como sea necesario cuando haga
     * clic en eliminar
     */
    public void clicDeleteProduct() {
        clear();
        txtName.setEnabled(false);
        btnSave.setEnabled(false);
        btnDelete.setEnabled(false);
        btnModify.setEnabled(false);
        btnCancel.setEnabled(false);
    }

    /**
     * habilita y desabilita los campos y botones como sea necesario cuando haga
     * clic en modificar
     *
     */
    public void clicModifyProduct() {
        txtName.setEnabled(true);
        btnSave.setEnabled(true);
        btnDelete.setEnabled(false);
        btnModify.setEnabled(false);
        tableReciper.setEnabled(true);
        btnCancel.setEnabled(true);
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

    public DefaultTableModel getTableProductsDefault() {
        return tableProductsDefault;
    }

    public DefaultTableModel getTableReciperDefault() {
        return tableReciperDefault;
    }

    public JTable getTableReciper() {
        return tableReciper;
    }

    public JButton getBtnCancel() {
        return btnCancel;
    }

    /**
     * cargae el producto elaborado en los txt
     *
     * @param prod
     * @throws RemoteException
     */
    public void loadProduct(Map<String, Object> prod) throws RemoteException {
        txtId.setText(prod.get("id").toString());
        txtName.setText(prod.get("name").toString());
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
        jPanel7 = new javax.swing.JPanel();
        jPanel8 = new javax.swing.JPanel();
        btnDelete = new javax.swing.JButton();
        btnNew = new javax.swing.JButton();
        btnModify = new javax.swing.JButton();
        btnSave = new javax.swing.JButton();
        btnCancel = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        txtId = new javax.swing.JTextField();
        txtName = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        txtSearch = new javax.swing.JTextField();
        jScrollPane5 = new javax.swing.JScrollPane();
        tableProducts = new javax.swing.JTable();
        jScrollPane4 = new javax.swing.JScrollPane();
        tableReciper = new javax.swing.JTable();

        setClosable(true);
        setDefaultCloseOperation(javax.swing.WindowConstants.HIDE_ON_CLOSE);
        setIconifiable(true);
        setMaximizable(true);
        setTitle("Gestion Productos Elaborados");
        setPreferredSize(new java.awt.Dimension(576, 600));

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
        btnCancel.setToolTipText("Cancelar");
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

        jLabel1.setText("ID");

        txtId.setEditable(false);
        txtId.setToolTipText("Código del artículo");
        txtId.setDisabledTextColor(new java.awt.Color(16, 2, 245));
        txtId.setEnabled(false);

        txtName.setToolTipText("Nombre");
        txtName.setDisabledTextColor(new java.awt.Color(16, 2, 245));
        txtName.setEnabled(false);

        jLabel5.setText("Nombre");

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1)
                    .addComponent(jLabel5))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addComponent(txtName)
                        .addContainerGap())
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addComponent(txtId, javax.swing.GroupLayout.PREFERRED_SIZE, 113, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))))
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(txtId, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel5))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 18, Short.MAX_VALUE)
                .addComponent(jPanel8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        txtSearch.setForeground(new java.awt.Color(130, 125, 120));
        txtSearch.setToolTipText("Búsqueda personalizada");

        tableProducts.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null}
            },
            new String [] {
                "Id", "Nombre", "Tipo"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane5.setViewportView(tableProducts);

        tableReciper.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null}
            },
            new String [] {
                "Id", "Nombre", "Cantidad"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, true
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane4.setViewportView(tableReciper);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(txtSearch, javax.swing.GroupLayout.DEFAULT_SIZE, 523, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                        .addGap(0, 0, 0)
                        .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 261, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(13, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addComponent(txtSearch, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 320, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 320, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
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
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 572, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnModifyActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnModifyActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnModifyActionPerformed
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCancel;
    private javax.swing.JButton btnDelete;
    private javax.swing.JButton btnModify;
    private javax.swing.JButton btnNew;
    private javax.swing.JButton btnSave;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JTable tableProducts;
    private javax.swing.JTable tableReciper;
    private javax.swing.JTextField txtId;
    private javax.swing.JTextField txtName;
    private javax.swing.JTextField txtSearch;
    // End of variables declaration//GEN-END:variables
}
