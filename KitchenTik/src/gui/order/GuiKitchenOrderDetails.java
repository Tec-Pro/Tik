/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui.order;

import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowAdapter;
import java.util.Map;
import javax.swing.event.TableModelListener;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author joako
 */
public class GuiKitchenOrderDetails extends javax.swing.JDialog {

    /**
     * Creates new form OrderDetails
     */
    private int orderID;
    private boolean modified = false;
    
    public GuiKitchenOrderDetails(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        this.addWindowListener(new WindowAdapter(){
                public void windowClosing(WindowEvent e){
                    int i = 0;
                    if (modified){ // If there were modifications to the table
                        i=JOptionPane.showConfirmDialog(null, "Seguro que quiere salir?");
                    }
                    if(i==0){
                        closeWindow();
                    }
                }
            });
        initComponents();
    }
    
    public GuiKitchenOrderDetails(java.awt.Frame parent, boolean modal,Map<String,Object> order) {
        super(parent, modal);
        initComponents();
        
    }
    
     public void setActionListener(ActionListener lis) {
        this.btnSendOrderDone.addActionListener(lis);
        this.btnCheckAll.addActionListener(lis);
    }

     public void setTableModelListener(TableModelListener lis){
         this.tableOrderProducts.getModel().addTableModelListener(lis);
     }
     
     public void closeWindow(){
         this.setModal(false);
         this.setVisible(false);
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
        orderTimePanel = new javax.swing.JPanel();
        labelOrderArrivalTime = new javax.swing.JLabel();
        jSplitPane2 = new javax.swing.JSplitPane();
        orderDescriptionPanel = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        txtOrderDescription = new javax.swing.JTextPane();
        jPanel4 = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        tableOrderProducts = new javax.swing.JTable();
        btnSendOrderDone = new javax.swing.JButton();
        btnCheckAll = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();

        setTitle("Detalles de pedido");

        labelOrderArrivalTime.setText("TIEMPO ACA");

        javax.swing.GroupLayout orderTimePanelLayout = new javax.swing.GroupLayout(orderTimePanel);
        orderTimePanel.setLayout(orderTimePanelLayout);
        orderTimePanelLayout.setHorizontalGroup(
            orderTimePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(labelOrderArrivalTime, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        orderTimePanelLayout.setVerticalGroup(
            orderTimePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(labelOrderArrivalTime, javax.swing.GroupLayout.DEFAULT_SIZE, 38, Short.MAX_VALUE)
        );

        jSplitPane2.setOrientation(javax.swing.JSplitPane.VERTICAL_SPLIT);

        jScrollPane2.setViewportView(txtOrderDescription);

        javax.swing.GroupLayout orderDescriptionPanelLayout = new javax.swing.GroupLayout(orderDescriptionPanel);
        orderDescriptionPanel.setLayout(orderDescriptionPanelLayout);
        orderDescriptionPanelLayout.setHorizontalGroup(
            orderDescriptionPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 632, Short.MAX_VALUE)
        );
        orderDescriptionPanelLayout.setVerticalGroup(
            orderDescriptionPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(orderDescriptionPanelLayout.createSequentialGroup()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 88, Short.MAX_VALUE)
                .addContainerGap())
        );

        jSplitPane2.setTopComponent(orderDescriptionPanel);

        tableOrderProducts.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "Producto", "Cantidad", "Hecho"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Boolean.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, true
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane3.setViewportView(tableOrderProducts);

        btnSendOrderDone.setText("Pedido listo");

        btnCheckAll.setText("X");

        jLabel1.setText("Marcar todos");

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane3, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 632, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGap(6, 6, 6)
                        .addComponent(btnCheckAll, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel1))
                    .addComponent(btnSendOrderDone, javax.swing.GroupLayout.PREFERRED_SIZE, 156, javax.swing.GroupLayout.PREFERRED_SIZE)))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 226, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnCheckAll, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 9, Short.MAX_VALUE)
                .addComponent(btnSendOrderDone))
        );

        jSplitPane2.setRightComponent(jPanel4);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jSplitPane2)
                    .addComponent(orderTimePanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(orderTimePanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jSplitPane2)
                .addContainerGap())
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

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(GuiKitchenOrderDetails.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(GuiKitchenOrderDetails.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(GuiKitchenOrderDetails.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(GuiKitchenOrderDetails.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                GuiKitchenOrderDetails dialog = new GuiKitchenOrderDetails(new javax.swing.JFrame(), true);
                dialog.addWindowListener(new java.awt.event.WindowAdapter() {
                    @Override
                    public void windowClosing(java.awt.event.WindowEvent e) {
                        System.exit(0);
                    }
                });
                dialog.setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCheckAll;
    private javax.swing.JButton btnSendOrderDone;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JSplitPane jSplitPane2;
    private javax.swing.JLabel labelOrderArrivalTime;
    private javax.swing.JPanel orderDescriptionPanel;
    private javax.swing.JPanel orderTimePanel;
    private javax.swing.JTable tableOrderProducts;
    private javax.swing.JTextPane txtOrderDescription;
    // End of variables declaration//GEN-END:variables

    /**
     * Devuelve el botón para avisar que el pedido está listo.
     * @return the btnSendOrderDone
     */
    public javax.swing.JButton getBtnSendOrderDone() {
        return btnSendOrderDone;
    }

    /**
     * Devuelve el botón que se utiliza para marcar todos los items del pedido.
     * @return the checkBoxCheckAll
     */
    public javax.swing.JButton getBtnCheckAll() {
        return btnCheckAll;
    }

    /**
     * Devuelve el label que se utiliza para mostrar el tiempo de llegada del pedido.
     * @return the labelOrderArrivalTime
     */
    public javax.swing.JLabel getLabelOrderArrivalTime() {
        return labelOrderArrivalTime;
    }

    /**
     * Devuelve la tabla de los productos del pedido.
     * @return the tableOrderProducts
     */
    public javax.swing.JTable getTableOrderProducts() {
        return tableOrderProducts;
    }

    
    /**
     * Devuelve el dtm para la tabla de productos del pedido.
     * @return the defaultTableModel for tableOrderProducts
     */
    public DefaultTableModel getDefaultTableModelOrderProducts() {
            return (DefaultTableModel) tableOrderProducts.getModel();
    }
    /**
     * Devuelve el panel de texto donde va la descripción del pedido.
     * @return the txtOrderDescription
     */
    public javax.swing.JTextPane getTxtOrderDescription() {
        return txtOrderDescription;
    }

    /**
     * @param orderID the orderID to set
     */
    public void setOrderID(int orderID) {
        this.orderID = orderID;
    }

    /**
     * @param modified the modified to set
     */
    public void setModified(boolean modified) {
        this.modified = modified;
    }

    /**
     * @return the orderID
     */
    public int getOrderID() {
        return orderID;
    }
}