/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui.statistics;

import com.toedter.calendar.JDateChooser;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author eze
 */
public class GuiProductStatistics extends javax.swing.JInternalFrame {

    /**
     * Creates new form GuiProductStatistics
     */
    public GuiProductStatistics() {
        initComponents();
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

    public JDateChooser getDateSince() {
        return dateSince;
    }

    public JDateChooser getDateUntil() {
        return dateUntil;
    }

    public JTable getTableProductStatistics() {
        return tableProductStatistics;
    }
    
    public DefaultTableModel getModelTableProductStatistics() {
        return (DefaultTableModel) tableProductStatistics.getModel();
    }

    //limpia la gui completa y setea valores por defecto
    public void cleanComponents(){
        getModelTableProductStatistics().setRowCount(0);
        checkAnnual.setSelected(false);
        checkMonthly.setSelected(false);
        checkDaily.setSelected(false);
    }
    
    /**
     *
     * @param lis
     */
    public void setActionListener(ActionListener lis) {
        this.checkAnnual.addActionListener(lis);
        this.checkDaily.addActionListener(lis);
        this.checkMonthly.addActionListener(lis);
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
        jScrollPane1 = new javax.swing.JScrollPane();
        tableProductStatistics = new javax.swing.JTable();
        jLabel1 = new javax.swing.JLabel();
        dateUntil = new com.toedter.calendar.JDateChooser();
        checkMonthly = new javax.swing.JCheckBox();
        checkAnnual = new javax.swing.JCheckBox();
        jLabel2 = new javax.swing.JLabel();
        dateSince = new com.toedter.calendar.JDateChooser();
        checkDaily = new javax.swing.JCheckBox();
        btnPrintReport = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();

        setClosable(true);
        setDefaultCloseOperation(javax.swing.WindowConstants.HIDE_ON_CLOSE);
        setIconifiable(true);
        setMaximizable(true);
        setTitle("Estadisticas de Productos");
        setPreferredSize(new java.awt.Dimension(1300, 700));

        panelImage1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/fondo gris.png"))); // NOI18N

        tableProductStatistics.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Producto", "Cant.", "Turno", "Fecha"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tableProductStatistics.setColumnSelectionAllowed(true);
        tableProductStatistics.getTableHeader().setReorderingAllowed(false);
        jScrollPane1.setViewportView(tableProductStatistics);
        tableProductStatistics.getColumnModel().getSelectionModel().setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        if (tableProductStatistics.getColumnModel().getColumnCount() > 0) {
            tableProductStatistics.getColumnModel().getColumn(1).setMinWidth(120);
            tableProductStatistics.getColumnModel().getColumn(1).setPreferredWidth(120);
            tableProductStatistics.getColumnModel().getColumn(2).setMinWidth(120);
            tableProductStatistics.getColumnModel().getColumn(2).setPreferredWidth(120);
            tableProductStatistics.getColumnModel().getColumn(3).setMinWidth(120);
            tableProductStatistics.getColumnModel().getColumn(3).setPreferredWidth(120);
        }

        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("Hasta:");

        checkMonthly.setBackground(new java.awt.Color(0, 0, 0));
        checkMonthly.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);
        checkMonthly.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                checkMonthlyActionPerformed(evt);
            }
        });

        checkAnnual.setBackground(new java.awt.Color(0, 0, 0));
        checkAnnual.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);

        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setText("Desde:");

        checkDaily.setBackground(new java.awt.Color(0, 0, 0));
        checkDaily.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);

        btnPrintReport.setText("IMPRIMIR REPORTE");

        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setText("Diario");

        jLabel4.setForeground(new java.awt.Color(255, 255, 255));
        jLabel4.setText("Mensual");

        jLabel5.setForeground(new java.awt.Color(255, 255, 255));
        jLabel5.setText("Anual");

        javax.swing.GroupLayout panelImage1Layout = new javax.swing.GroupLayout(panelImage1);
        panelImage1.setLayout(panelImage1Layout);
        panelImage1Layout.setHorizontalGroup(
            panelImage1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelImage1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelImage1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 1265, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelImage1Layout.createSequentialGroup()
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
                        .addGap(38, 38, 38)
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(dateSince, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(dateUntil, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnPrintReport, javax.swing.GroupLayout.PREFERRED_SIZE, 229, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        panelImage1Layout.setVerticalGroup(
            panelImage1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelImage1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelImage1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelImage1Layout.createSequentialGroup()
                        .addGap(2, 2, 2)
                        .addComponent(btnPrintReport)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(panelImage1Layout.createSequentialGroup()
                        .addGroup(panelImage1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(dateUntil, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel2)
                            .addComponent(jLabel3)
                            .addComponent(checkDaily)
                            .addComponent(jLabel4)
                            .addComponent(checkMonthly)
                            .addComponent(jLabel5)
                            .addComponent(checkAnnual)
                            .addComponent(dateSince, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel1))
                        .addGap(12, 12, 12)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 606, Short.MAX_VALUE)))
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


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnPrintReport;
    private javax.swing.JCheckBox checkAnnual;
    private javax.swing.JCheckBox checkDaily;
    private javax.swing.JCheckBox checkMonthly;
    private com.toedter.calendar.JDateChooser dateSince;
    private com.toedter.calendar.JDateChooser dateUntil;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JScrollPane jScrollPane1;
    private org.edisoncor.gui.panel.PanelImage panelImage1;
    private javax.swing.JTable tableProductStatistics;
    // End of variables declaration//GEN-END:variables
}
