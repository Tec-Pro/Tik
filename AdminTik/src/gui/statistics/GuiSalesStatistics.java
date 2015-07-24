/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui.statistics;

import com.toedter.calendar.JDateChooser;
import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.Map;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PiePlot;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.data.general.PieDataset;
import utils.Pair;

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
     *
     * @param listMap
     */
    public void drawingSalesChartWaiter(List<Map> listMap) {
        DefaultPieDataset dataset = new DefaultPieDataset();
        for (Map m : listMap) {
            dataset.setValue(m.get("waiter_name").toString(), Double.parseDouble(m.get("sale_amount").toString()));
        }

        JFreeChart chart = ChartFactory.createPieChart(
                "Gráfica de Ventas por Mozo", //Titulo del grafico 
                dataset, //Data
                true, true, true);

        PiePlot plot = (PiePlot) chart.getPlot();
        plot.setLabelFont(new Font("SansSerif", Font.PLAIN, 12));
        plot.setNoDataMessage("No data available");
        plot.setCircular(false);
        plot.setLabelGap(0.02);

        ChartPanel chartPanel = new ChartPanel(chart);

        panelSalesChartWaiter.removeAll();
        panelSalesChartWaiter.add(chartPanel, BorderLayout.CENTER);
        panelSalesChartWaiter.validate();

    }
    
    /**
     *
     * @param listMap
     */
    public void drawingTablesChartWaiter(List<Map> listMap) {
        DefaultPieDataset dataset = new DefaultPieDataset();
        for (Map m : listMap) {
            dataset.setValue(m.get("waiter_name").toString(), Double.parseDouble(m.get("tables").toString()));
        }

        JFreeChart chart = ChartFactory.createPieChart(
                "Gráfica de Mesas Atendidas por Mozo", //Titulo del grafico 
                dataset, //Data
                true, true, true);

        PiePlot plot = (PiePlot) chart.getPlot();
        plot.setLabelFont(new Font("SansSerif", Font.PLAIN, 12));
        plot.setNoDataMessage("No data available");
        plot.setCircular(false);
        plot.setLabelGap(0.02);

        ChartPanel chartPanel = new ChartPanel(chart);

        panelTablesChartWaiter.removeAll();
        panelTablesChartWaiter.add(chartPanel, BorderLayout.CENTER);
        panelTablesChartWaiter.validate();

    }

    public JButton getBtnPrintReport() {
        return btnPrintReport;
    }

    public JCheckBox getCheckAnnual() {
        return checkAnnual;
    }

    public JCheckBox getCheckDaily() {
        return checkDaily;
    }

    public JCheckBox getCheckMonthly() {
        return checkMonthly;
    }

    public JCheckBox getCheckAll() {
        return checkAll;
    }

    public JCheckBox getCheckTurn() {
        return checkTurn;
    }

    public JDateChooser getDateSince() {
        return dateSince;
    }

    public JDateChooser getDateUntil() {
        return dateUntil;
    }

    public JTable getTableSalesStatisticsWaiter() {
        return tableSalesStatisticsWaiter;
    }

    public JTable getTableTotalSalesStatistics() {
        return tableTotalSalesStatistics;
    }

    public DefaultTableModel getModelTableSalesStatisticsWaiter() {
        return (DefaultTableModel) tableSalesStatisticsWaiter.getModel();
    }

    public DefaultTableModel getModelTableTotalSalesStatistics() {
        return (DefaultTableModel) tableTotalSalesStatistics.getModel();
    }

    //limpia la gui completa y setea valores por defecto
    public void cleanComponents() {
        getModelTableSalesStatisticsWaiter().setRowCount(0);
        getModelTableTotalSalesStatistics().setRowCount(0);
        checkAnnual.setSelected(false);
        checkMonthly.setSelected(false);
        checkDaily.setSelected(false);
        checkAll.setSelected(false);
        checkTurn.setSelected(true);
        panelSalesChartWaiter.removeAll();
        panelTablesChartWaiter.removeAll();
        panelTablesChartWaiter.validate();
        panelSalesChartWaiter.validate();
    }

    /**
     *
     * @param lis
     */
    public void setActionListener(ActionListener lis) {
        this.checkAnnual.addActionListener(lis);
        this.checkDaily.addActionListener(lis);
        this.checkMonthly.addActionListener(lis);
        this.checkAll.addActionListener(lis);
        this.checkTurn.addActionListener(lis);
        this.btnPrintReport.addActionListener(lis);
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
        jScrollPane2 = new javax.swing.JScrollPane();
        tableTotalSalesStatistics = new javax.swing.JTable();
        jScrollPane1 = new javax.swing.JScrollPane();
        tableSalesStatisticsWaiter = new javax.swing.JTable();
        jLabel3 = new javax.swing.JLabel();
        checkDaily = new javax.swing.JCheckBox();
        jLabel4 = new javax.swing.JLabel();
        checkMonthly = new javax.swing.JCheckBox();
        jLabel5 = new javax.swing.JLabel();
        checkAnnual = new javax.swing.JCheckBox();
        jLabel2 = new javax.swing.JLabel();
        dateSince = new com.toedter.calendar.JDateChooser();
        jLabel1 = new javax.swing.JLabel();
        dateUntil = new com.toedter.calendar.JDateChooser();
        btnPrintReport = new javax.swing.JButton();
        jLabel6 = new javax.swing.JLabel();
        panelSalesChartWaiter = new javax.swing.JPanel();
        panelTablesChartWaiter = new javax.swing.JPanel();
        jLabel7 = new javax.swing.JLabel();
        checkAll = new javax.swing.JCheckBox();
        jLabel8 = new javax.swing.JLabel();
        checkTurn = new javax.swing.JCheckBox();

        setClosable(true);
        setDefaultCloseOperation(javax.swing.WindowConstants.HIDE_ON_CLOSE);
        setIconifiable(true);
        setMaximizable(true);
        setTitle("Estadisticas de Ventas");
        setPreferredSize(new java.awt.Dimension(1300, 700));

        panelImage1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/fondo gris.png"))); // NOI18N

        jScrollPane2.setEnabled(false);

        tableTotalSalesStatistics.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Ventas", "Mesas", "Personas", "Productos", "Prom. Mesa", "Prom. Persona", "Prom. Producto", "Descuentos", "Excepciones", "Turno"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tableTotalSalesStatistics.setToolTipText("");
        tableTotalSalesStatistics.setAutoscrolls(false);
        tableTotalSalesStatistics.setColumnSelectionAllowed(true);
        tableTotalSalesStatistics.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        tableTotalSalesStatistics.getTableHeader().setReorderingAllowed(false);
        jScrollPane2.setViewportView(tableTotalSalesStatistics);
        tableTotalSalesStatistics.getColumnModel().getSelectionModel().setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);

        tableSalesStatisticsWaiter.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Mozo", "Fecha", "Turno", "Ventas", "Mesas", "Personas", "Productos", "Prom. Mesa", "Prom. Persona", "Prom. Producto", "Descuentos", "Excepciones"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false, false, false, false, false
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
            tableSalesStatisticsWaiter.getColumnModel().getColumn(8).setMinWidth(100);
            tableSalesStatisticsWaiter.getColumnModel().getColumn(8).setPreferredWidth(100);
            tableSalesStatisticsWaiter.getColumnModel().getColumn(9).setMinWidth(100);
            tableSalesStatisticsWaiter.getColumnModel().getColumn(9).setPreferredWidth(100);
            tableSalesStatisticsWaiter.getColumnModel().getColumn(10).setMinWidth(100);
            tableSalesStatisticsWaiter.getColumnModel().getColumn(10).setPreferredWidth(100);
            tableSalesStatisticsWaiter.getColumnModel().getColumn(11).setMinWidth(100);
            tableSalesStatisticsWaiter.getColumnModel().getColumn(11).setPreferredWidth(100);
        }

        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setText("Diario");

        checkDaily.setBackground(new java.awt.Color(0, 0, 0));
        checkDaily.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);

        jLabel4.setForeground(new java.awt.Color(255, 255, 255));
        jLabel4.setText("Mensual");

        checkMonthly.setBackground(new java.awt.Color(0, 0, 0));
        checkMonthly.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);
        checkMonthly.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                checkMonthlyActionPerformed(evt);
            }
        });

        jLabel5.setForeground(new java.awt.Color(255, 255, 255));
        jLabel5.setText("Anual");

        checkAnnual.setBackground(new java.awt.Color(0, 0, 0));
        checkAnnual.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);

        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setText("Desde:");

        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("Hasta:");

        btnPrintReport.setText("IMPRIMIR REPORTE");

        jLabel6.setForeground(new java.awt.Color(255, 255, 255));
        jLabel6.setText("TOTAL:");

        panelSalesChartWaiter.setLayout(new java.awt.BorderLayout());

        panelTablesChartWaiter.setLayout(new java.awt.BorderLayout());

        jLabel7.setForeground(new java.awt.Color(255, 255, 255));
        jLabel7.setText("Todo");

        checkAll.setBackground(new java.awt.Color(0, 0, 0));
        checkAll.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);
        checkAll.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                checkAllActionPerformed(evt);
            }
        });

        jLabel8.setForeground(new java.awt.Color(255, 255, 255));
        jLabel8.setText("Separar por Turnos");

        checkTurn.setBackground(new java.awt.Color(0, 0, 0));
        checkTurn.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);

        javax.swing.GroupLayout panelImage1Layout = new javax.swing.GroupLayout(panelImage1);
        panelImage1.setLayout(panelImage1Layout);
        panelImage1Layout.setHorizontalGroup(
            panelImage1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelImage1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelImage1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(panelImage1Layout.createSequentialGroup()
                        .addComponent(jLabel3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(checkDaily)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel4)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(checkMonthly)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel5)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(checkAnnual)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel7)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(checkAll)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel8)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(checkTurn)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(dateSince, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(dateUntil, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnPrintReport, javax.swing.GroupLayout.PREFERRED_SIZE, 229, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(panelImage1Layout.createSequentialGroup()
                        .addComponent(jLabel6)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 1202, Short.MAX_VALUE))
                    .addGroup(panelImage1Layout.createSequentialGroup()
                        .addGap(104, 104, 104)
                        .addComponent(panelSalesChartWaiter, javax.swing.GroupLayout.PREFERRED_SIZE, 474, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(58, 58, 58)
                        .addComponent(panelTablesChartWaiter, javax.swing.GroupLayout.PREFERRED_SIZE, 474, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        panelImage1Layout.setVerticalGroup(
            panelImage1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelImage1Layout.createSequentialGroup()
                .addGroup(panelImage1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelImage1Layout.createSequentialGroup()
                        .addGap(14, 14, 14)
                        .addComponent(btnPrintReport))
                    .addGroup(panelImage1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(panelImage1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(dateUntil, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel2)
                            .addComponent(dateSince, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel1)
                            .addComponent(jLabel3)
                            .addComponent(checkDaily)
                            .addComponent(jLabel4)
                            .addComponent(checkMonthly)
                            .addComponent(jLabel5)
                            .addComponent(checkAnnual)
                            .addComponent(jLabel7)
                            .addComponent(checkAll)
                            .addComponent(jLabel8)
                            .addComponent(checkTurn))))
                .addGap(10, 10, 10)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 272, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelImage1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel6)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelImage1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(panelSalesChartWaiter, javax.swing.GroupLayout.PREFERRED_SIZE, 272, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(panelTablesChartWaiter, javax.swing.GroupLayout.PREFERRED_SIZE, 272, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(panelImage1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(panelImage1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void checkMonthlyActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_checkMonthlyActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_checkMonthlyActionPerformed

    private void checkAllActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_checkAllActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_checkAllActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnPrintReport;
    private javax.swing.JCheckBox checkAll;
    private javax.swing.JCheckBox checkAnnual;
    private javax.swing.JCheckBox checkDaily;
    private javax.swing.JCheckBox checkMonthly;
    private javax.swing.JCheckBox checkTurn;
    private com.toedter.calendar.JDateChooser dateSince;
    private com.toedter.calendar.JDateChooser dateUntil;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private org.edisoncor.gui.panel.PanelImage panelImage1;
    private javax.swing.JPanel panelSalesChartWaiter;
    private javax.swing.JPanel panelTablesChartWaiter;
    private javax.swing.JTable tableSalesStatisticsWaiter;
    private javax.swing.JTable tableTotalSalesStatistics;
    // End of variables declaration//GEN-END:variables
}
