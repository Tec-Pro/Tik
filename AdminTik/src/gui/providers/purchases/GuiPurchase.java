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

        jPanel1 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        fondoImagen = new javax.swing.JPanel();
        jSplitPane1 = new javax.swing.JSplitPane();
        jPanel2 = new javax.swing.JPanel();
        panelControlFactura = new javax.swing.JPanel();
        btnNew = new javax.swing.JButton();
        btnPurchase = new javax.swing.JButton();
        btnCancel = new javax.swing.JButton();
        panelCompra = new javax.swing.JPanel();
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
        panelClientesAarticulos = new javax.swing.JPanel();
        jSplitPane2 = new javax.swing.JSplitPane();
        panelArticulos = new javax.swing.JPanel();
        jScrollPane6 = new javax.swing.JScrollPane();
        tblProduct = new javax.swing.JTable();
        labelNombre1 = new javax.swing.JLabel();
        txtSearchProduct = new javax.swing.JTextField();
        panelProveedores = new javax.swing.JPanel();
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

        jPanel1.setPreferredSize(new java.awt.Dimension(825, 448));

        jScrollPane1.setPreferredSize(new java.awt.Dimension(822, 448));

        fondoImagen.setPreferredSize(new java.awt.Dimension(820, 400));

        jSplitPane1.setDividerSize(10);

        panelControlFactura.setLayout(new java.awt.GridLayout(1, 0));

        btnNew.setText("COMPRA NUEVA");
        btnNew.setToolTipText("Realizar una nueva compra");
        panelControlFactura.add(btnNew);

        btnPurchase.setText("REALIZAR COMPRA");
        btnPurchase.setToolTipText("Registrar la compra");
        panelControlFactura.add(btnPurchase);

        btnCancel.setText("CANCELAR");
        panelControlFactura.add(btnCancel);

        panelCompra.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Compra", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Century Schoolbook L", 3, 18))); // NOI18N

        labelCliente.setFont(new java.awt.Font("Century Schoolbook L", 0, 14)); // NOI18N
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
        if (tblPurchase.getColumnModel().getColumnCount() > 0) {
            tblPurchase.getColumnModel().getColumn(0).setResizable(false);
            tblPurchase.getColumnModel().getColumn(0).setPreferredWidth(15);
            tblPurchase.getColumnModel().getColumn(5).setResizable(false);
            tblPurchase.getColumnModel().getColumn(5).setPreferredWidth(30);
        }

        labelTotal.setFont(new java.awt.Font("Century Schoolbook L", 0, 14)); // NOI18N
        labelTotal.setText("Total");

        jLabel3.setFont(new java.awt.Font("Century Schoolbook L", 0, 14)); // NOI18N
        jLabel3.setText("Fecha");

        datePurchase.setDateFormatString("dd/MM/yyyy");

        jLabel5.setFont(new java.awt.Font("Century Schoolbook L", 0, 14)); // NOI18N
        jLabel5.setText("Abona");

        boxPay.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                boxPayActionPerformed(evt);
            }
        });

        txtCost.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#0.00"))));
        txtCost.setEnabled(false);

        javax.swing.GroupLayout panelCompraLayout = new javax.swing.GroupLayout(panelCompra);
        panelCompra.setLayout(panelCompraLayout);
        panelCompraLayout.setHorizontalGroup(
            panelCompraLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane8, javax.swing.GroupLayout.Alignment.TRAILING)
            .addGroup(panelCompraLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel5)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(boxPay)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(labelTotal)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtCost, javax.swing.GroupLayout.PREFERRED_SIZE, 106, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelCompraLayout.createSequentialGroup()
                .addComponent(labelCliente)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblProvider, javax.swing.GroupLayout.PREFERRED_SIZE, 211, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblIdProvider, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel3)
                .addGap(4, 4, 4)
                .addComponent(datePurchase, javax.swing.GroupLayout.PREFERRED_SIZE, 164, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        panelCompraLayout.setVerticalGroup(
            panelCompraLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelCompraLayout.createSequentialGroup()
                .addGroup(panelCompraLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(labelCliente, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(panelCompraLayout.createSequentialGroup()
                        .addGroup(panelCompraLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblProvider, javax.swing.GroupLayout.PREFERRED_SIZE, 19, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel3)
                            .addComponent(datePurchase, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblIdProvider, javax.swing.GroupLayout.PREFERRED_SIZE, 18, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane8, javax.swing.GroupLayout.DEFAULT_SIZE, 254, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(panelCompraLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelCompraLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addComponent(jLabel5)
                        .addComponent(boxPay))
                    .addComponent(txtCost, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(labelTotal)))
        );

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(panelCompra, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(panelControlFactura, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(0, 0, 0))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(panelCompra, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(panelControlFactura, javax.swing.GroupLayout.PREFERRED_SIZE, 64, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0))
        );

        jSplitPane1.setLeftComponent(jPanel2);

        jSplitPane2.setDividerLocation(200);
        jSplitPane2.setDividerSize(10);
        jSplitPane2.setOrientation(javax.swing.JSplitPane.VERTICAL_SPLIT);

        panelArticulos.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Productos primarios", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Century Schoolbook L", 3, 18))); // NOI18N

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
        if (tblProduct.getColumnModel().getColumnCount() > 0) {
            tblProduct.getColumnModel().getColumn(0).setResizable(false);
            tblProduct.getColumnModel().getColumn(0).setPreferredWidth(15);
        }

        labelNombre1.setFont(new java.awt.Font("Century Schoolbook L", 0, 14)); // NOI18N
        labelNombre1.setText("Codigo");

        txtSearchProduct.setToolTipText("Filtrar busqueda por codigo");

        javax.swing.GroupLayout panelArticulosLayout = new javax.swing.GroupLayout(panelArticulos);
        panelArticulos.setLayout(panelArticulosLayout);
        panelArticulosLayout.setHorizontalGroup(
            panelArticulosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane6, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
            .addGroup(panelArticulosLayout.createSequentialGroup()
                .addComponent(labelNombre1)
                .addGap(17, 17, 17)
                .addComponent(txtSearchProduct))
        );
        panelArticulosLayout.setVerticalGroup(
            panelArticulosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelArticulosLayout.createSequentialGroup()
                .addGroup(panelArticulosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtSearchProduct, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(labelNombre1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane6, javax.swing.GroupLayout.DEFAULT_SIZE, 137, Short.MAX_VALUE))
        );

        jSplitPane2.setLeftComponent(panelArticulos);

        panelProveedores.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Proveedores", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Century Schoolbook L", 3, 18))); // NOI18N

        txtSearchProvider.setToolTipText("Filtrar busqueda por ID");

        labelNombre.setFont(new java.awt.Font("Century Schoolbook L", 0, 14)); // NOI18N
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
        if (tblProvider.getColumnModel().getColumnCount() > 0) {
            tblProvider.getColumnModel().getColumn(0).setResizable(false);
            tblProvider.getColumnModel().getColumn(0).setPreferredWidth(15);
        }

        javax.swing.GroupLayout panelProveedoresLayout = new javax.swing.GroupLayout(panelProveedores);
        panelProveedores.setLayout(panelProveedoresLayout);
        panelProveedoresLayout.setHorizontalGroup(
            panelProveedoresLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelProveedoresLayout.createSequentialGroup()
                .addComponent(labelNombre)
                .addGap(17, 17, 17)
                .addComponent(txtSearchProvider, javax.swing.GroupLayout.DEFAULT_SIZE, 161, Short.MAX_VALUE))
            .addComponent(jScrollPane7, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
        );
        panelProveedoresLayout.setVerticalGroup(
            panelProveedoresLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelProveedoresLayout.createSequentialGroup()
                .addGroup(panelProveedoresLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtSearchProvider, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(labelNombre))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane7, javax.swing.GroupLayout.DEFAULT_SIZE, 167, Short.MAX_VALUE))
        );

        jSplitPane2.setRightComponent(panelProveedores);

        javax.swing.GroupLayout panelClientesAarticulosLayout = new javax.swing.GroupLayout(panelClientesAarticulos);
        panelClientesAarticulos.setLayout(panelClientesAarticulosLayout);
        panelClientesAarticulosLayout.setHorizontalGroup(
            panelClientesAarticulosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jSplitPane2, javax.swing.GroupLayout.Alignment.TRAILING)
        );
        panelClientesAarticulosLayout.setVerticalGroup(
            panelClientesAarticulosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jSplitPane2, javax.swing.GroupLayout.Alignment.TRAILING)
        );

        jSplitPane1.setRightComponent(panelClientesAarticulos);

        javax.swing.GroupLayout fondoImagenLayout = new javax.swing.GroupLayout(fondoImagen);
        fondoImagen.setLayout(fondoImagenLayout);
        fondoImagenLayout.setHorizontalGroup(
            fondoImagenLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(fondoImagenLayout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addComponent(jSplitPane1)
                .addGap(0, 0, 0))
        );
        fondoImagenLayout.setVerticalGroup(
            fondoImagenLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(fondoImagenLayout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addComponent(jSplitPane1))
        );

        jScrollPane1.setViewportView(fondoImagen);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(3, 3, 3))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        getContentPane().add(jPanel1, java.awt.BorderLayout.CENTER);

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
    private javax.swing.JPanel fondoImagen;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
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
    private javax.swing.JPanel panelArticulos;
    private javax.swing.JPanel panelClientesAarticulos;
    private javax.swing.JPanel panelCompra;
    private javax.swing.JPanel panelControlFactura;
    private javax.swing.JPanel panelProveedores;
    private javax.swing.JTable tblProduct;
    private javax.swing.JTable tblProvider;
    private javax.swing.JTable tblPurchase;
    private javax.swing.JFormattedTextField txtCost;
    private javax.swing.JTextField txtSearchProduct;
    private javax.swing.JTextField txtSearchProvider;
    // End of variables declaration//GEN-END:variables
}
