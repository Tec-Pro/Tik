/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui.main;

import gui.order.GuiBarOrderPane;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import static java.awt.Frame.MAXIMIZED_BOTH;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionListener;
import java.awt.event.MouseListener;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.table.DefaultTableModel;
import utils.Pair;
import utils.Watch;

/**
 *
 * @author joako
 */
public class GuiBarMain extends javax.swing.JFrame {

    private int gridX;
    private int gridY;
    private static final int maxGridX = 5;

    /**
     * Creates new form GuiBarMain
     */
    public GuiBarMain() {
        initComponents();
        Watch watch = new Watch(0, 0, 0, 0);
        watch.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        watch.setFont(new java.awt.Font("Arial", 1, 25));
        watch.setForeground(Color.white);
        setExtendedState(MAXIMIZED_BOTH);
        watchPanel.add(watch, BorderLayout.CENTER);
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
        jPanel2 = new org.edisoncor.gui.panel.PanelImage();
        jPanel4 = new javax.swing.JPanel();
        jSplitPane1 = new javax.swing.JSplitPane();
        jScrollPane2 = new javax.swing.JScrollPane();
        ordersPanel = new org.edisoncor.gui.panel.PanelImage();
        jScrollPane3 = new javax.swing.JScrollPane();
        kitchenOrdersJTable = new javax.swing.JTable();
        watchPanel = new javax.swing.JPanel();
        btnRemoveKitchenOrders = new javax.swing.JButton();
        jMenuBar1 = new javax.swing.JMenuBar();
        menuLogin = new javax.swing.JMenu();
        menuItemNewLogin = new javax.swing.JMenuItem();
        menuItemLoggedUsers = new javax.swing.JMenuItem();
        menuConfig = new javax.swing.JMenu();
        menuItemGeneralConfig = new javax.swing.JMenuItem();
        jMenu1 = new javax.swing.JMenu();
        menuItemAbout = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("SISTEMA BAR TIK");

        jScrollPane1.setMaximumSize(new java.awt.Dimension(1024, 768));

        jPanel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/fondo gris.png"))); // NOI18N

        jPanel4.setBorder(null);
        jPanel4.setOpaque(false);

        jSplitPane1.setBorder(null);
        jSplitPane1.setDividerLocation(400);
        jSplitPane1.setDividerSize(3);
        jSplitPane1.setOrientation(javax.swing.JSplitPane.VERTICAL_SPLIT);
        jSplitPane1.setResizeWeight(0.75);
        jSplitPane1.setMaximumSize(new java.awt.Dimension(400, 500));

        jScrollPane2.setBorder(null);
        jScrollPane2.setViewportBorder(javax.swing.BorderFactory.createEtchedBorder());

        ordersPanel.setBorder(null);
        ordersPanel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/fondo gris.png"))); // NOI18N
        ordersPanel.setLayout(new java.awt.GridBagLayout());
        jScrollPane2.setViewportView(ordersPanel);

        jSplitPane1.setTopComponent(jScrollPane2);

        jScrollPane3.setBorder(null);
        jScrollPane3.setViewportBorder(javax.swing.BorderFactory.createEtchedBorder());

        kitchenOrdersJTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID Pedido", "Mozo"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        kitchenOrdersJTable.setName("kitchenOrdersJTable"); // NOI18N
        jScrollPane3.setViewportView(kitchenOrdersJTable);
        if (kitchenOrdersJTable.getColumnModel().getColumnCount() > 0) {
            kitchenOrdersJTable.getColumnModel().getColumn(0).setResizable(false);
            kitchenOrdersJTable.getColumnModel().getColumn(1).setResizable(false);
        }

        jSplitPane1.setBottomComponent(jScrollPane3);

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jSplitPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 682, Short.MAX_VALUE)
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jSplitPane1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 249, Short.MAX_VALUE)
        );

        watchPanel.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        watchPanel.setForeground(new java.awt.Color(254, 254, 254));
        watchPanel.setOpaque(false);
        watchPanel.setLayout(new java.awt.BorderLayout());

        btnRemoveKitchenOrders.setText("Eliminar Pedidos Seleccionados");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(watchPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(231, 231, 231)
                .addComponent(btnRemoveKitchenOrders)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(7, 7, 7)
                .addComponent(watchPanel, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnRemoveKitchenOrders)
                .addContainerGap())
        );

        jScrollPane1.setViewportView(jPanel2);

        menuLogin.setText("Login");

        menuItemNewLogin.setText("Nuevo");
        menuItemNewLogin.setActionCommand("MenuItemNuevo");
        menuLogin.add(menuItemNewLogin);

        menuItemLoggedUsers.setText("Usuarios Logueados");
        menuLogin.add(menuItemLoggedUsers);

        jMenuBar1.add(menuLogin);

        menuConfig.setText("Configuracion");

        menuItemGeneralConfig.setText("Configuraciones Generales");
        menuItemGeneralConfig.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuItemGeneralConfigActionPerformed(evt);
            }
        });
        menuConfig.add(menuItemGeneralConfig);

        jMenuBar1.add(menuConfig);

        jMenu1.setText("Acerca de");

        menuItemAbout.setText("Tec-Pro Software");
        menuItemAbout.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuItemAboutActionPerformed(evt);
            }
        });
        jMenu1.add(menuItemAbout);

        jMenuBar1.add(jMenu1);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(0, 0, 0))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void menuItemGeneralConfigActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuItemGeneralConfigActionPerformed
        try {
            (new GuiConfig(this, true)).setVisible(true);
        } catch (RemoteException ex) {
            Logger.getLogger(GuiBarMain.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NotBoundException ex) {
            Logger.getLogger(GuiBarMain.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_menuItemGeneralConfigActionPerformed

    private void menuItemAboutActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuItemAboutActionPerformed
        (new GuiAbout(this, true)).setVisible(true);
    }//GEN-LAST:event_menuItemAboutActionPerformed

    /**
     *
     * @param newOrder
     */
    public void addElementToOrdersGrid(GuiBarOrderPane newOrder) {
        if (gridX == maxGridX) {
            gridY++;
            gridX = 0;
        }
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.gridx = gridX;
        constraints.gridy = gridY;
        constraints.anchor = GridBagConstraints.NORTHWEST;
        constraints.insets = new Insets(10, 10, 10, 10);
        gridX++;
        ordersPanel.add(newOrder, constraints);
        ordersPanel.revalidate();
    }

    /**
     * Función que agrega un elemento a la lista de pedidos listos de cocina.
     * @param order Orden lista donde first es la orden y second es la lista de productos.
     * order.first es un Map que tiene {persons, user_id, user_name, order_number, description, closed, id}
     * order.second es una lista de Maps donde cada uno tiene 
     * {quantity, updated_at, paid, created_at, id, issued, order_id, fproduct_id, done, commited}
     */
    public void addElementToKitchenOrdersTable(Pair<Map<String, Object>, List<Map>> order) {
        DefaultTableModel tableModel = (DefaultTableModel) getKitchenOrdersJTable().getModel();
        boolean found = false;
        int i = 0;
        String orderId = order.first().get("order_number").toString();
        String orderUser = order.first().get("user_name").toString();
        //Ciclo la tabla para ver si ya está agregado el pedido a la misma.
        while (!found && i < getKitchenOrdersJTable().getRowCount()) {
            found = tableModel.getValueAt(i, 0).equals(orderId) && tableModel.getValueAt(i, 1).equals(orderUser);
            i++;
        }
        //Si no está agregado creo la nueva fila y la agrego.
        if (!found) {
            Object[] row = new Object[2];
            row[0] = orderId;
            row[1] = orderUser;
            tableModel.addRow(row);
        }
    }
    
    /** 
     * Función que permite eliminar manualmente los pedidos seleccionados de la lista.
     */
    public void removeElementOfKitchenOrdersTable() {
        DefaultTableModel tableModel = (DefaultTableModel) getKitchenOrdersJTable().getModel();
        int[] a = getKitchenOrdersJTable().getSelectedRows();
        Arrays.sort(a);
        for (int i = a.length - 1; i >= 0; i--) {
            tableModel.removeRow(a[i]);
        }
    }

    /**
     * Función que elimina un componente y reordena la grilla.
     * @param index índice del componente a eliminar.
     * Elimina el elemento del panel de ordenes, revalida, repinta y luego por
     * cada componente que está dentro del panel, modifica sus restricciones
     * y lo ubica en su nueva posición.
     */
    public void removeElementOfOrdersGrid(int index) {
        ordersPanel.remove(index);
        ordersPanel.revalidate();
        ordersPanel.repaint();
        gridX = 0;
        gridY = 0;
        for (Component cop : ordersPanel.getComponents()) {
            GridBagConstraints constraints = new GridBagConstraints();
            constraints.gridx = gridX;
            constraints.gridy = gridY;
            constraints.anchor = GridBagConstraints.NORTHWEST;
            constraints.insets = new Insets(10, 10, 10, 10);
            ordersPanel.add(cop, constraints);
            ordersPanel.revalidate();
            ordersPanel.repaint();
            gridX++;
            if (gridX == maxGridX) {
                gridY++;
                gridX = 0;
            }
        }
    }

    /**
     * Función que actualiza un elemento de la grilla.
     * @param index índice del elemento a actualizar.
     * @param order información del pedido.
     */
    public void updateElementOfOrdersGrid(int index, Pair<Map<String, Object>, List<Map>> order) {
        GuiBarOrderPane orderPane = (GuiBarOrderPane) getOrdersPanel().getComponent(index);

        final String desc;
        String aux = orderPane.getTxtOrderDescription().getText();
        for (Map m : order.second()) {
            aux = aux + m.get("name") + " x" + m.get("quantity") + "\n";
        }
        orderPane.getTxtOrderDescription().setText(aux);
        orderPane.setOrder(order.first());
        orderPane.setOrderProducts(order.second());
        orderPane.revalidate();
        getOrdersPanel().revalidate();
    }

    /**
     * Función que modifica el color de un JPanel de pedido.
     * @param index índice del panel a modificar
     * @param color color seleccionado.
     */
    public void setOrderColor(int index, Color color) {
        GuiBarOrderPane order = (GuiBarOrderPane) getOrdersPanel().getComponent(index);
        order.setBackground(color);
        order.revalidate();
        getOrdersPanel().revalidate();
    }

    /**
     * Función que elimina todos los paneles de ordersPanel.
     */
    public void cleanAllOrders() {
        ordersPanel.removeAll();
        gridX = 0;
        gridY = 0;
        ordersPanel.revalidate();
        ordersPanel.repaint();
        revalidate();
        repaint();
    }

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
            java.util.logging.Logger.getLogger(GuiBarMain.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(GuiBarMain.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(GuiBarMain.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(GuiBarMain.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new GuiBarMain().setVisible(true);
            }
        });

    }

    public void setActionListener(ActionListener lis) {
        this.getMenuItemNewLogin().addActionListener(lis);
        this.menuItemLoggedUsers.addActionListener(lis);
        this.btnRemoveKitchenOrders.addActionListener(lis);
    }

    public void setMouseListener(MouseListener lis) {
        this.getOrdersPanel().addMouseListener(lis);
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnRemoveKitchenOrders;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenuBar jMenuBar1;
    private org.edisoncor.gui.panel.PanelImage jPanel2;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JSplitPane jSplitPane1;
    private javax.swing.JTable kitchenOrdersJTable;
    private javax.swing.JMenu menuConfig;
    private javax.swing.JMenuItem menuItemAbout;
    private javax.swing.JMenuItem menuItemGeneralConfig;
    private javax.swing.JMenuItem menuItemLoggedUsers;
    private javax.swing.JMenuItem menuItemNewLogin;
    private javax.swing.JMenu menuLogin;
    private org.edisoncor.gui.panel.PanelImage ordersPanel;
    private javax.swing.JPanel watchPanel;
    // End of variables declaration//GEN-END:variables

    /**
     * Devuelve la barra de menú.
     *
     * @return the jMenuBar1
     */
    public javax.swing.JMenuBar getjMenuBar1() {
        return jMenuBar1;
    }

    /**
     * Devuelve el botón de usuarios logueados.
     *
     * @return the menuItemLoggedUsers
     */
    public javax.swing.JMenuItem getMenuItemLoggedUsers() {
        return menuItemLoggedUsers;
    }

    /**
     * Devuelve el botón para loguear un nuevo usuario.
     *
     * @return the menuItemNewLogin
     */
    public javax.swing.JMenuItem getMenuItemNewLogin() {
        return menuItemNewLogin;
    }

    /**
     * Devuelve el menú que contiene las opciones de login y ver usuarios
     * logueados.
     *
     * @return the menuLogin
     */
    public javax.swing.JMenu getMenuLogin() {
        return menuLogin;
    }

    /**
     * Devuelve el JPanel donde deben ordenarse los pedidos.
     *
     * @return the ordersPanel
     */
    public javax.swing.JPanel getOrdersPanel() {
        return ordersPanel;
    }

    /**
     * Devuelve el panel del reloj.
     *
     * @return the watchPanel
     */
    public javax.swing.JPanel getWatchPanel() {
        return watchPanel;
    }

    /**
     *
     * @return indice de la columna en la ultima fila de la grilla contenedora
     * de paneles
     */
    public int getGridX() {
        return gridX;
    }

    /**
     *
     *
     * @return indice de la ultima fila de la grilla contenedora de paneles
     */
    public int getGridY() {
        return gridY;
    }

    /**
     *
     */
    public void setGridX(int x) {
        this.gridX = x;
    }

    /**
     *
     */
    public void setGridY(int y) {
        this.gridY = y;
    }

    /**
     *
     * @return indice del maximo anchor de la grilla contenedora de paneles
     */
    public static int getMaxGridX() {
        return maxGridX;
    }

    /**
     * @return the btnRemoveKitchenOrders
     */
    public javax.swing.JButton getBtnRemoveKitchenOrders() {
        return btnRemoveKitchenOrders;
    }

    /**
     * @return the kitchenOrdersJTable
     */
    public javax.swing.JTable getKitchenOrdersJTable() {
        return kitchenOrdersJTable;
    }

}
