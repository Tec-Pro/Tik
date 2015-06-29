/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui.statistics;

/**
 *
 * @author eze
 */
public class GuiSalesStatistics extends javax.swing.JInternalFrame {

    /**
     * Creates new form GuiStatistics
     */
    public GuiSalesStatistics() {
        initComponents();
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
        tableSalesStatisticsWaiter = new javax.swing.JTable();
        dateSince = new com.toedter.calendar.JDateChooser();
        dateUntil = new com.toedter.calendar.JDateChooser();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        comboDaily = new javax.swing.JCheckBox();
        comboMonthly = new javax.swing.JCheckBox();
        comboAnnual = new javax.swing.JCheckBox();
        jScrollPane2 = new javax.swing.JScrollPane();
        tableTotalSalesStatistics = new javax.swing.JTable();
        jTextField1 = new javax.swing.JTextField();
        btnPrintReport = new javax.swing.JButton();

        setClosable(true);
        setDefaultCloseOperation(javax.swing.WindowConstants.HIDE_ON_CLOSE);
        setIconifiable(true);
        setMaximizable(true);
        setTitle("Estadisticas de Ventas");
        setPreferredSize(new java.awt.Dimension(1300, 700));

        tableSalesStatisticsWaiter.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Mozo", "Ventas", "Mesas", "Personas", "Productos", "Prom. Mesa", "Prom. Persona", "Prom. Producto"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tableSalesStatisticsWaiter.setColumnSelectionAllowed(true);
        tableSalesStatisticsWaiter.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        tableSalesStatisticsWaiter.getTableHeader().setReorderingAllowed(false);
        jScrollPane1.setViewportView(tableSalesStatisticsWaiter);
        tableSalesStatisticsWaiter.getColumnModel().getSelectionModel().setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        if (tableSalesStatisticsWaiter.getColumnModel().getColumnCount() > 0) {
            tableSalesStatisticsWaiter.getColumnModel().getColumn(1).setMinWidth(100);
            tableSalesStatisticsWaiter.getColumnModel().getColumn(1).setPreferredWidth(100);
            tableSalesStatisticsWaiter.getColumnModel().getColumn(2).setMinWidth(100);
            tableSalesStatisticsWaiter.getColumnModel().getColumn(2).setPreferredWidth(100);
            tableSalesStatisticsWaiter.getColumnModel().getColumn(3).setMinWidth(100);
            tableSalesStatisticsWaiter.getColumnModel().getColumn(3).setPreferredWidth(100);
            tableSalesStatisticsWaiter.getColumnModel().getColumn(4).setMinWidth(100);
            tableSalesStatisticsWaiter.getColumnModel().getColumn(4).setPreferredWidth(100);
            tableSalesStatisticsWaiter.getColumnModel().getColumn(5).setMinWidth(100);
            tableSalesStatisticsWaiter.getColumnModel().getColumn(5).setPreferredWidth(100);
            tableSalesStatisticsWaiter.getColumnModel().getColumn(6).setMinWidth(100);
            tableSalesStatisticsWaiter.getColumnModel().getColumn(6).setPreferredWidth(100);
            tableSalesStatisticsWaiter.getColumnModel().getColumn(7).setMinWidth(100);
            tableSalesStatisticsWaiter.getColumnModel().getColumn(7).setPreferredWidth(100);
        }

        jLabel1.setText("Hasta:");

        jLabel2.setText("Desde:");

        comboDaily.setText("Diario");
        comboDaily.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);

        comboMonthly.setText("Mensual");
        comboMonthly.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);
        comboMonthly.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                comboMonthlyActionPerformed(evt);
            }
        });

        comboAnnual.setText("Anual");
        comboAnnual.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);

        jScrollPane2.setEnabled(false);

        tableTotalSalesStatistics.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null, null}
            },
            new String [] {
                "Ventas", "Mesas", "Personas", "Productos", "Prom. Mesa", "Prom. Persona", "Prom. Producto", "Descuentos", "Excepciones"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tableTotalSalesStatistics.setAutoscrolls(false);
        tableTotalSalesStatistics.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        jScrollPane2.setViewportView(tableTotalSalesStatistics);
        tableTotalSalesStatistics.getColumnModel().getSelectionModel().setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        if (tableTotalSalesStatistics.getColumnModel().getColumnCount() > 0) {
            tableTotalSalesStatistics.getColumnModel().getColumn(0).setResizable(false);
            tableTotalSalesStatistics.getColumnModel().getColumn(1).setResizable(false);
            tableTotalSalesStatistics.getColumnModel().getColumn(2).setResizable(false);
            tableTotalSalesStatistics.getColumnModel().getColumn(3).setResizable(false);
            tableTotalSalesStatistics.getColumnModel().getColumn(4).setResizable(false);
            tableTotalSalesStatistics.getColumnModel().getColumn(5).setResizable(false);
            tableTotalSalesStatistics.getColumnModel().getColumn(6).setResizable(false);
            tableTotalSalesStatistics.getColumnModel().getColumn(7).setResizable(false);
            tableTotalSalesStatistics.getColumnModel().getColumn(8).setResizable(false);
        }

        jTextField1.setText("TOTAL:");

        btnPrintReport.setText("IMPRIMIR REPORTE");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jScrollPane1))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(28, 28, 28)
                        .addComponent(comboDaily)
                        .addGap(18, 18, 18)
                        .addComponent(comboMonthly)
                        .addGap(18, 18, 18)
                        .addComponent(comboAnnual)
                        .addGap(49, 49, 49)
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(dateSince, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(44, 44, 44)
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(dateUntil, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 314, Short.MAX_VALUE)
                        .addComponent(btnPrintReport, javax.swing.GroupLayout.PREFERRED_SIZE, 229, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane2)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(comboDaily)
                        .addComponent(comboMonthly)
                        .addComponent(comboAnnual)
                        .addComponent(jLabel2)
                        .addComponent(jLabel1))
                    .addComponent(dateUntil, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnPrintReport)
                    .addComponent(dateSince, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 289, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(266, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void comboMonthlyActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_comboMonthlyActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_comboMonthlyActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnPrintReport;
    private javax.swing.JCheckBox comboAnnual;
    private javax.swing.JCheckBox comboDaily;
    private javax.swing.JCheckBox comboMonthly;
    private com.toedter.calendar.JDateChooser dateSince;
    private com.toedter.calendar.JDateChooser dateUntil;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTable tableSalesStatisticsWaiter;
    private javax.swing.JTable tableTotalSalesStatistics;
    // End of variables declaration//GEN-END:variables
}
