/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui.main;

import controllers.ControllerGuiKitchenMain;
import gui.order.GuiKitchenOrderPane;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.rmi.RemoteException;
import java.util.logging.Level;
import java.util.logging.Logger;
import utils.Watch;

/**
 *
 * @author joako
 */
public class GuiKitchenMain extends javax.swing.JFrame {

    /**
     * Creates new form GuiKitchenMain
     */
    public GuiKitchenMain() {
        initComponents();
        Watch watch = new Watch(0, 0, 0, 0);
        watch.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        watch.setFont(new java.awt.Font("Arial", 1, 25));
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
        jPanel2 = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();
        ordersPanel = new javax.swing.JPanel();
        watchPanel = new javax.swing.JPanel();
        jMenuBar1 = new javax.swing.JMenuBar();
        menuLogin = new javax.swing.JMenu();
        menuItemNewLogin = new javax.swing.JMenuItem();
        menuItemLoggedUsers = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel4.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        ordersPanel.setLayout(new java.awt.GridLayout(0, 5));

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(ordersPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addComponent(ordersPanel, javax.swing.GroupLayout.PREFERRED_SIZE, 280, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        watchPanel.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        watchPanel.setLayout(new java.awt.BorderLayout());

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(watchPanel, javax.swing.GroupLayout.DEFAULT_SIZE, 729, Short.MAX_VALUE)
                    .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(31, 31, 31)
                .addComponent(watchPanel, javax.swing.GroupLayout.PREFERRED_SIZE, 92, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(22, Short.MAX_VALUE))
        );

        jScrollPane1.setViewportView(jPanel2);

        menuLogin.setText("Login");

        menuItemNewLogin.setText("Nuevo");
        menuItemNewLogin.setActionCommand("MenuItemNuevo");
        menuLogin.add(menuItemNewLogin);

        menuItemLoggedUsers.setText("Usuarios Logueados");
        menuLogin.add(menuItemLoggedUsers);

        jMenuBar1.add(menuLogin);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 755, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents


    public void addElementToOrdersGrid(final String orderId, final String orderDescription, final String orderArrivalTime, java.awt.event.MouseAdapter mAdapt) {
        GuiKitchenOrderPane newOrder = new GuiKitchenOrderPane(orderId, orderDescription, orderArrivalTime);
        newOrder.addMouseListener(mAdapt); 
        getOrdersPanel().add(newOrder);
        getOrdersPanel().revalidate();
    }
    
    public void removeElementOfOrdersGrid(int index){
        getOrdersPanel().remove(index);
        getOrdersPanel().revalidate();
    }
    
    public void updateElementOfOrdersGrid(int index, String orderDescription){
        GuiKitchenOrderPane order = (GuiKitchenOrderPane) getOrdersPanel().getComponent(index);
        order.getTxtOrderDescription().setText(orderDescription);
        order.revalidate();
        getOrdersPanel().revalidate();
    }

    public void setOrderColor(int index, Color color){
        GuiKitchenOrderPane order = (GuiKitchenOrderPane) getOrdersPanel().getComponent(index);
        order.setBackground(color);
        order.revalidate();
        getOrdersPanel().revalidate();
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
            java.util.logging.Logger.getLogger(GuiKitchenMain.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(GuiKitchenMain.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(GuiKitchenMain.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(GuiKitchenMain.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new GuiKitchenMain().setVisible(true);
            }
        });

    }

    public void setActionListener(ActionListener lis) {
        this.getMenuItemNewLogin().addActionListener(lis);
    }

    public void setMouseListener(MouseListener lis) {
        this.getOrdersPanel().addMouseListener(lis);
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JMenuItem menuItemLoggedUsers;
    private javax.swing.JMenuItem menuItemNewLogin;
    private javax.swing.JMenu menuLogin;
    private javax.swing.JPanel ordersPanel;
    private javax.swing.JPanel watchPanel;
    // End of variables declaration//GEN-END:variables

    /**
     * Devuelve la barra de menú.
     * @return the jMenuBar1
     */
    public javax.swing.JMenuBar getjMenuBar1() {
        return jMenuBar1;
    }

    /**
     * Devuelve el botón de usuarios logueados.
     * @return the menuItemLoggedUsers
     */
    public javax.swing.JMenuItem getMenuItemLoggedUsers() {
        return menuItemLoggedUsers;
    }

    /**
     * Devuelve el botón para loguear un nuevo usuario.
     * @return the menuItemNewLogin
     */
    public javax.swing.JMenuItem getMenuItemNewLogin() {
        return menuItemNewLogin;
    }

    /**
     * Devuelve el menú que contiene las opciones de login y ver usuarios logueados.
     * @return the menuLogin
     */
    public javax.swing.JMenu getMenuLogin() {
        return menuLogin;
    }

    /**
     * Devuelve el JPanel donde deben ordenarse los pedidos.
     * @return the ordersPanel
     */
    public javax.swing.JPanel getOrdersPanel() {
        return ordersPanel;
    }

    /**
     * Devuelve el panel del reloj.
     * @return the watchPanel
     */
    public javax.swing.JPanel getWatchPanel() {
        return watchPanel;
    }

}
