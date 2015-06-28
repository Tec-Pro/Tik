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
public class GuiProductList extends javax.swing.JInternalFrame {

    /**
     * Creates new form GuiProductList
     */
    public GuiProductList() {
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
        tableProductList = new javax.swing.JTable();

        setTitle("Listado de Productos");
        setPreferredSize(new java.awt.Dimension(1300, 700));

        tableProductList.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Producto", "Costo Elab.", "Precio Sugerido", "Precio Venta", "% Ganancia", "$ Ganancia"
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
        tableProductList.setColumnSelectionAllowed(true);
        tableProductList.getTableHeader().setReorderingAllowed(false);
        jScrollPane1.setViewportView(tableProductList);
        tableProductList.getColumnModel().getSelectionModel().setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        if (tableProductList.getColumnModel().getColumnCount() > 0) {
            tableProductList.getColumnModel().getColumn(1).setMinWidth(120);
            tableProductList.getColumnModel().getColumn(1).setPreferredWidth(120);
            tableProductList.getColumnModel().getColumn(1).setMaxWidth(120);
            tableProductList.getColumnModel().getColumn(2).setMinWidth(120);
            tableProductList.getColumnModel().getColumn(2).setPreferredWidth(120);
            tableProductList.getColumnModel().getColumn(2).setMaxWidth(120);
            tableProductList.getColumnModel().getColumn(3).setMinWidth(120);
            tableProductList.getColumnModel().getColumn(3).setPreferredWidth(120);
            tableProductList.getColumnModel().getColumn(3).setMaxWidth(120);
            tableProductList.getColumnModel().getColumn(4).setMinWidth(120);
            tableProductList.getColumnModel().getColumn(4).setPreferredWidth(120);
            tableProductList.getColumnModel().getColumn(4).setMaxWidth(120);
            tableProductList.getColumnModel().getColumn(5).setMinWidth(120);
            tableProductList.getColumnModel().getColumn(5).setPreferredWidth(120);
            tableProductList.getColumnModel().getColumn(5).setMaxWidth(120);
        }

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 1265, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 646, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tableProductList;
    // End of variables declaration//GEN-END:variables
}
