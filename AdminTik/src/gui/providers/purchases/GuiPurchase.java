/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gui.providers.purchases;

import com.toedter.calendar.JDateChooser;
import java.awt.event.ActionListener;
import java.sql.Date;
import java.util.Calendar;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author jacinto
 */
public class GuiPurchase extends javax.swing.JInternalFrame {
    
    private DefaultTableModel tblDefaultProvider;
    private DefaultTableModel tblDefaultProduct;
    private DefaultTableModel tblDefaultPurchase;

    /**
     * Creates new form CompraGui
     */
    public GuiPurchase() {
        initComponents();
        tblDefaultProduct = (DefaultTableModel) tblProduct.getModel();
        tblDefaultPurchase = (DefaultTableModel) tblPurchase.getModel();
        tblDefaultProvider = (DefaultTableModel) tblProvider.getModel();
        datePurchase.setDate(Calendar.getInstance().getTime());
        clearFields();
    }

    /**
     * Seteo el actionListener para los botones,
     * , compraNueva, realizarCompra, cancelar
     * @param lis     */
    public void setActionListener(ActionListener lis) {
        this.btnNew.addActionListener(lis);
        this.btnPurchase.addActionListener(lis);
        this.btnCancel.addActionListener(lis);
    }
    
    /**
     *limpia la ventana
     */
    public void clearFields() {
        datePurchase.setDate(Calendar.getInstance().getTime());
        txtCost.setText(String.valueOf((float)0));
        txtSearchProduct.setText("");
        txtSearchProvider.setText("");
        lblProvider.setText("");
        tblDefaultPurchase.setRowCount(0);
        tblDefaultProduct.setRowCount(0);
        tblDefaultProvider.setRowCount(0);
        lblIdProvider.setText("");
        boxPay.setSelected(false);
        
    }
    
    /**
     *configura la interfaz del modo cuando hace click en nuevo
     */
    public void clickNew() {
        clearFields();
        btnCancel.setEnabled(true);
        btnPurchase.setEnabled(true);
        btnNew.setEnabled(false);
        tblProduct.setEnabled(true);
        tblProvider.setEnabled(true);
        tblPurchase.setEnabled(true);
        txtSearchProduct.setEnabled(true);
        txtSearchProvider.setEnabled(true);
                datePurchase.setEnabled(true);
                boxPay.setEnabled(true);

    }
    
    
    /**
     *configura la interfaz del modo cuando hace click en cancelar
     */
    public void clickCancel() {
        clearFields();
        btnCancel.setEnabled(false);
        btnPurchase.setEnabled(false);
        btnNew.setEnabled(true);
        tblProduct.setEnabled(false);
        tblProvider.setEnabled(false);
        tblPurchase.setEnabled(false);
        txtSearchProduct.setEnabled(false);
        txtSearchProvider.setEnabled(false);
        datePurchase.setEnabled(false);
        boxPay.setEnabled(false);
        
    }

    /**
     *retorna el lable idProvider
     * @return
     */
    public JLabel getLblIdProvider() {
        return lblIdProvider;
    }

    /**
     *retorna la tabla defaultProvider
     * @return
     */
    public DefaultTableModel getTblDefaultProvider() {
        return tblDefaultProvider;
    }

    /**
     *
     * @return
     */
    public DefaultTableModel getTblDefaultProduct() {
        return tblDefaultProduct;
    }

    /**
     *
     * @return
     */
    public DefaultTableModel getTblDefaultPurchase() {
        return tblDefaultPurchase;
    }

    /**
     *
     * @return
     */
    public JCheckBox getBoxPay() {
        return boxPay;
    }

    /**
     *
     * @return
     */
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

    /**
     *
     * @return
     */
    public JButton getBtnPurchase() {
        return btnPurchase;
    }

    /**
     *
     * @return
     */
    public JDateChooser getDatePurchase() {
        return datePurchase;
    }

    /**
     *
     * @return
     */
    public JLabel getLblProvider() {
        return lblProvider;
    }

    /**
     *
     * @return
     */
    public JTable getTblProduct() {
        return tblProduct;
    }

    /**
     *
     * @return
     */
    public JTable getTblProvider() {
        return tblProvider;
    }

    /**
     *
     * @return
     */
    public JTable getTblPurchase() {
        return tblPurchase;
    }

    /**
     *
     * @return
     */
    public JTextField getTxtCost() {
        return txtCost;
    }

    /**
     *
     * @return
     */
    public JTextField getTxtSearchProduct() {
        return txtSearchProduct;
    }

    /**
     *
     * @return
     */
    public JTextField getTxtSearchProvider() {
        return txtSearchProvider;
    }
    
    

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        panelImage1 = new org.edisoncor.gui.panel.PanelImage();
        jScrollPane1 = new javax.swing.JScrollPane();
        panelImage2 = new org.edisoncor.gui.panel.PanelImage();
        jSplitPane1 = new javax.swing.JSplitPane();
        panelImage3 = new org.edisoncor.gui.panel.PanelImage();
        panelImage4 = new org.edisoncor.gui.panel.PanelImage();
        labelCliente = new javax.swing.JLabel();
        jScrollPane8 = new javax.swing.JScrollPane();
        tblPurchase = new javax.swing.JTable();
        labelTotal = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        datePurchase = new com.toedter.calendar.JDateChooser();
        jLabel5 = new javax.swing.JLabel();
        boxPay = new javax.swing.JCheckBox();
        lblProvider = new javax.swing.JLabel();
        lblIdProvider = new javax.swing.JLabel();
        txtCost = new javax.swing.JFormattedTextField();
        panelImage5 = new org.edisoncor.gui.panel.PanelImage();
        btnNew = new javax.swing.JButton();
        btnPurchase = new javax.swing.JButton();
        btnCancel = new javax.swing.JButton();
        panelImage6 = new org.edisoncor.gui.panel.PanelImage();
        jSplitPane2 = new javax.swing.JSplitPane();
        panelImage7 = new org.edisoncor.gui.panel.PanelImage();
        jScrollPane6 = new javax.swing.JScrollPane();
        tblProduct = new javax.swing.JTable();
        labelNombre1 = new javax.swing.JLabel();
        txtSearchProduct = new javax.swing.JTextField();
        panelImage8 = new org.edisoncor.gui.panel.PanelImage();
        txtSearchProvider = new javax.swing.JTextField();
        labelNombre = new javax.swing.JLabel();
        jScrollPane7 = new javax.swing.JScrollPane();
        tblProvider = new javax.swing.JTable();

        setClosable(true);
        setDefaultCloseOperation(javax.swing.WindowConstants.HIDE_ON_CLOSE);
        setIconifiable(true);
        setMaximizable(true);
        setResizable(true);
        setTitle("Registrar Compra");
        setPreferredSize(new java.awt.Dimension(836, 478));

        panelImage1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/fondo gris.png"))); // NOI18N

        jScrollPane1.setPreferredSize(new java.awt.Dimension(822, 448));

        panelImage2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/fondo gris.png"))); // NOI18N

        jSplitPane1.setDividerSize(10);

        panelImage3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/fondo gris.png"))); // NOI18N

        panelImage4.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Compra", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Dialog", 3, 15), new java.awt.Color(255, 255, 255))); // NOI18N
        panelImage4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/fondo gris.png"))); // NOI18N

        labelCliente.setForeground(new java.awt.Color(255, 255, 255));
        labelCliente.setText("Proveedor");

        tblPurchase.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "Nombre", "Cantidad", "Unidad de medida(cant)", "Precio/unidad de medida)", "Total"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, true, false, true, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblPurchase.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        tblPurchase.getTableHeader().setReorderingAllowed(false);
        jScrollPane8.setViewportView(tblPurchase);

        labelTotal.setForeground(new java.awt.Color(255, 255, 255));
        labelTotal.setText("Total");

        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setText("Fecha");

        jLabel5.setForeground(new java.awt.Color(255, 255, 255));
        jLabel5.setText("Abona");

        boxPay.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                boxPayActionPerformed(evt);
            }
        });

        lblProvider.setForeground(new java.awt.Color(255, 255, 255));

        lblIdProvider.setForeground(new java.awt.Color(255, 255, 255));

        txtCost.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#0.00"))));
        txtCost.setEnabled(false);

        javax.swing.GroupLayout panelImage4Layout = new javax.swing.GroupLayout(panelImage4);
        panelImage4.setLayout(panelImage4Layout);
        panelImage4Layout.setHorizontalGroup(
            panelImage4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane8, javax.swing.GroupLayout.Alignment.TRAILING)
            .addGroup(panelImage4Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel5)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(boxPay)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(labelTotal)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtCost, javax.swing.GroupLayout.PREFERRED_SIZE, 106, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelImage4Layout.createSequentialGroup()
                .addComponent(labelCliente)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblProvider, javax.swing.GroupLayout.PREFERRED_SIZE, 211, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(lblIdProvider, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel3)
                .addGap(4, 4, 4)
                .addComponent(datePurchase, javax.swing.GroupLayout.PREFERRED_SIZE, 164, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        panelImage4Layout.setVerticalGroup(
            panelImage4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelImage4Layout.createSequentialGroup()
                .addGroup(panelImage4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(labelCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3)
                    .addComponent(datePurchase, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblIdProvider, javax.swing.GroupLayout.PREFERRED_SIZE, 18, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblProvider, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(5, 5, 5)
                .addComponent(jScrollPane8, javax.swing.GroupLayout.DEFAULT_SIZE, 285, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(panelImage4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelImage4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addComponent(jLabel5)
                        .addComponent(boxPay))
                    .addComponent(txtCost, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(labelTotal)))
        );

        panelImage5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/fondo gris.png"))); // NOI18N
        panelImage5.setLayout(new java.awt.GridLayout(1, 0));

        btnNew.setText("COMPRA NUEVA");
        btnNew.setToolTipText("Realizar una nueva compra");
        panelImage5.add(btnNew);

        btnPurchase.setText("REALIZAR COMPRA");
        btnPurchase.setToolTipText("Registrar la compra");
        panelImage5.add(btnPurchase);

        btnCancel.setText("CANCELAR");
        panelImage5.add(btnCancel);

        javax.swing.GroupLayout panelImage3Layout = new javax.swing.GroupLayout(panelImage3);
        panelImage3.setLayout(panelImage3Layout);
        panelImage3Layout.setHorizontalGroup(
            panelImage3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(panelImage4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(panelImage5, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        panelImage3Layout.setVerticalGroup(
            panelImage3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelImage3Layout.createSequentialGroup()
                .addComponent(panelImage4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(panelImage5, javax.swing.GroupLayout.PREFERRED_SIZE, 56, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        jSplitPane1.setLeftComponent(panelImage3);

        panelImage6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/fondo gris.png"))); // NOI18N

        jSplitPane2.setDividerLocation(200);
        jSplitPane2.setDividerSize(10);
        jSplitPane2.setOrientation(javax.swing.JSplitPane.VERTICAL_SPLIT);

        panelImage7.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Productos Primarios", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Dialog", 3, 14), new java.awt.Color(255, 255, 255))); // NOI18N
        panelImage7.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/fondo gris.png"))); // NOI18N

        tblProduct.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Id", "Nombre"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblProduct.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_ALL_COLUMNS);
        tblProduct.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        tblProduct.getTableHeader().setReorderingAllowed(false);
        jScrollPane6.setViewportView(tblProduct);

        labelNombre1.setForeground(new java.awt.Color(255, 255, 255));
        labelNombre1.setText("Codigo");

        txtSearchProduct.setToolTipText("Filtrar busqueda por codigo");

        javax.swing.GroupLayout panelImage7Layout = new javax.swing.GroupLayout(panelImage7);
        panelImage7.setLayout(panelImage7Layout);
        panelImage7Layout.setHorizontalGroup(
            panelImage7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane6, javax.swing.GroupLayout.DEFAULT_SIZE, 189, Short.MAX_VALUE)
            .addGroup(panelImage7Layout.createSequentialGroup()
                .addComponent(labelNombre1)
                .addGap(17, 17, 17)
                .addComponent(txtSearchProduct))
        );
        panelImage7Layout.setVerticalGroup(
            panelImage7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelImage7Layout.createSequentialGroup()
                .addGroup(panelImage7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtSearchProduct, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(labelNombre1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane6, javax.swing.GroupLayout.DEFAULT_SIZE, 23, Short.MAX_VALUE))
        );

        jSplitPane2.setLeftComponent(panelImage7);

        panelImage8.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Proveedores", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Dialog", 3, 14), new java.awt.Color(255, 255, 255))); // NOI18N
        panelImage8.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/fondo gris.png"))); // NOI18N

        txtSearchProvider.setToolTipText("Filtrar busqueda por ID");

        labelNombre.setForeground(new java.awt.Color(255, 255, 255));
        labelNombre.setText("Nombre");

        tblProvider.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "Nombre"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblProvider.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_ALL_COLUMNS);
        tblProvider.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        tblProvider.getTableHeader().setReorderingAllowed(false);
        jScrollPane7.setViewportView(tblProvider);

        javax.swing.GroupLayout panelImage8Layout = new javax.swing.GroupLayout(panelImage8);
        panelImage8.setLayout(panelImage8Layout);
        panelImage8Layout.setHorizontalGroup(
            panelImage8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelImage8Layout.createSequentialGroup()
                .addComponent(labelNombre)
                .addGap(17, 17, 17)
                .addComponent(txtSearchProvider))
            .addComponent(jScrollPane7, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
        );
        panelImage8Layout.setVerticalGroup(
            panelImage8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelImage8Layout.createSequentialGroup()
                .addGroup(panelImage8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtSearchProvider, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(labelNombre))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane7, javax.swing.GroupLayout.DEFAULT_SIZE, 297, Short.MAX_VALUE))
        );

        jSplitPane2.setRightComponent(panelImage8);

        javax.swing.GroupLayout panelImage6Layout = new javax.swing.GroupLayout(panelImage6);
        panelImage6.setLayout(panelImage6Layout);
        panelImage6Layout.setHorizontalGroup(
            panelImage6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jSplitPane2, javax.swing.GroupLayout.Alignment.TRAILING)
        );
        panelImage6Layout.setVerticalGroup(
            panelImage6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jSplitPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 448, Short.MAX_VALUE)
        );

        jSplitPane1.setRightComponent(panelImage6);

        javax.swing.GroupLayout panelImage2Layout = new javax.swing.GroupLayout(panelImage2);
        panelImage2.setLayout(panelImage2Layout);
        panelImage2Layout.setHorizontalGroup(
            panelImage2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelImage2Layout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addComponent(jSplitPane1)
                .addGap(0, 0, 0))
        );
        panelImage2Layout.setVerticalGroup(
            panelImage2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelImage2Layout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addComponent(jSplitPane1))
        );

        jScrollPane1.setViewportView(panelImage2);

        javax.swing.GroupLayout panelImage1Layout = new javax.swing.GroupLayout(panelImage1);
        panelImage1.setLayout(panelImage1Layout);
        panelImage1Layout.setHorizontalGroup(
            panelImage1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelImage1Layout.createSequentialGroup()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(3, 3, 3))
        );
        panelImage1Layout.setVerticalGroup(
            panelImage1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        getContentPane().add(panelImage1, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void boxPayActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_boxPayActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_boxPayActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JCheckBox boxPay;
    private javax.swing.JButton btnCancel;
    private javax.swing.JButton btnNew;
    private javax.swing.JButton btnPurchase;
    private com.toedter.calendar.JDateChooser datePurchase;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane6;
    private javax.swing.JScrollPane jScrollPane7;
    private javax.swing.JScrollPane jScrollPane8;
    private javax.swing.JSplitPane jSplitPane1;
    private javax.swing.JSplitPane jSplitPane2;
    private javax.swing.JLabel labelCliente;
    private javax.swing.JLabel labelNombre;
    private javax.swing.JLabel labelNombre1;
    private javax.swing.JLabel labelTotal;
    private javax.swing.JLabel lblIdProvider;
    private javax.swing.JLabel lblProvider;
    private org.edisoncor.gui.panel.PanelImage panelImage1;
    private org.edisoncor.gui.panel.PanelImage panelImage2;
    private org.edisoncor.gui.panel.PanelImage panelImage3;
    private org.edisoncor.gui.panel.PanelImage panelImage4;
    private org.edisoncor.gui.panel.PanelImage panelImage5;
    private org.edisoncor.gui.panel.PanelImage panelImage6;
    private org.edisoncor.gui.panel.PanelImage panelImage7;
    private org.edisoncor.gui.panel.PanelImage panelImage8;
    private javax.swing.JTable tblProduct;
    private javax.swing.JTable tblProvider;
    private javax.swing.JTable tblPurchase;
    private javax.swing.JFormattedTextField txtCost;
    private javax.swing.JTextField txtSearchProduct;
    private javax.swing.JTextField txtSearchProvider;
    // End of variables declaration//GEN-END:variables
}
