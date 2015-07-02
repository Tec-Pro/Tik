/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gui.main;

import java.awt.event.ActionListener;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDesktopPane;
import javax.swing.JMenu;
import javax.swing.JMenuItem;

/**
 *
 * @author nico
 */
public class GuiMain extends javax.swing.JFrame {
    /**
     * Creates new form GuiMain
     */
    private DesktopPaneImage desktop;
    
    public GuiMain() {
        initComponents();
        desktop= new DesktopPaneImage();
        this.add(desktop);
        
    }
    

    
    public void setActionListener(ActionListener lis){
        this.btnDisconnect.addActionListener(lis);
        this.btnExit.addActionListener(lis);
        this.btnAdmins.addActionListener(lis);
        this.btnFProduct.addActionListener(lis);
        this.btnPProduct.addActionListener(lis);
        this.btnEProduct.addActionListener(lis);
        this.btnProductCategory.addActionListener(lis);
        this.btnProviders.addActionListener(lis);
        this.btnEmployes.addActionListener(lis);
        this.btnMenu.addActionListener(lis);
        this.btnPurchase.addActionListener(lis);
        this.btnConfig.addActionListener(lis);
        this.btnDailyCashbox.addActionListener(lis);
        this.turn.addActionListener(lis);
        this.btnSalesStatistics.addActionListener(lis);
        this.btnProductStatistics.addActionListener(lis);
        this.btnProductList.addActionListener(lis);
        this.bntOpenTA.addActionListener(lis);
        this.btnCloseCashBox.addActionListener(lis);
        this.btnLogout.addActionListener(lis);
        this.btnOpenTM.addActionListener(lis);
    }

    public JMenuItem getBntOpenTA() {
        return bntOpenTA;
    }

    public JMenuItem getBtnCloseCashBox() {
        return btnCloseCashBox;
    }

    public JMenuItem getBtnLogout() {
        return btnLogout;
    }

    public JMenuItem getBtnOpenTM() {
        return btnOpenTM;
    }     

    public JMenuItem getBtnConfig() {
        return btnConfig;
    }

    public JMenuItem getBtnPurchase() {
        return btnPurchase;
    }

    public JMenuItem getBtnProviders() {
        return btnProviders;
    }

    public JMenuItem getBtnEmployes() {
        return btnEmployes;
    }

    
    public JMenuItem getBtnEProduct() {
        return btnEProduct;
    }

    public JMenuItem getBtnFProduct() {
        return btnFProduct;
    }

    public JMenuItem getBtnPProduct() {
        return btnPProduct;
    }

    public JMenuItem getBtnProductCategory() {
        return btnProductCategory;
    }

    public JMenuItem getBtnMenu() {
        return btnMenu;
    }    

    public JMenuItem getBtnAbout() {
        return btnAbout;
    }

    public JMenuItem getBtnDisconnect() {
        return btnDisconnect;
    }

    public JMenuItem getBtnExit() {
        return btnExit;
    }


    public JMenuItem getBtnAdmins() {
        return btnAdmins;
    }



    public DesktopPaneImage getDesktop() {
        return desktop;
    }

    public JMenuItem getBtnCloseCashbox() {
        return turn;
    }

    public JMenuItem getBtnProductList() {
        return btnProductList;
    }

    public JMenuItem getBtnProductStatistics() {
        return btnProductStatistics;
    }

    public JMenuItem getBtnSalesStatistics() {
        return btnSalesStatistics;
    }


    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        btnDisconnect = new javax.swing.JMenuItem();
        btnExit = new javax.swing.JMenuItem();
        jMenu = new javax.swing.JMenu();
        btnAdmins = new javax.swing.JMenuItem();
        jMenu3 = new javax.swing.JMenu();
        btnEProduct = new javax.swing.JMenuItem();
        btnFProduct = new javax.swing.JMenuItem();
        btnPProduct = new javax.swing.JMenuItem();
        btnProductCategory = new javax.swing.JMenuItem();
        btnMenu = new javax.swing.JMenuItem();
        jMenu4 = new javax.swing.JMenu();
        btnProviders = new javax.swing.JMenuItem();
        btnPurchase = new javax.swing.JMenuItem();
        empleado = new javax.swing.JMenu();
        btnEmployes = new javax.swing.JMenuItem();
        btnLogout = new javax.swing.JMenuItem();
        jMenu7 = new javax.swing.JMenu();
        btnDailyCashbox = new javax.swing.JMenuItem();
        jMenu8 = new javax.swing.JMenu();
        btnSalesStatistics = new javax.swing.JMenuItem();
        btnProductStatistics = new javax.swing.JMenuItem();
        btnProductList = new javax.swing.JMenuItem();
        jMenu6 = new javax.swing.JMenu();
        btnConfig = new javax.swing.JMenuItem();
        jMenu2 = new javax.swing.JMenu();
        btnAbout = new javax.swing.JMenuItem();
        turn = new javax.swing.JMenu();
        btnOpenTM = new javax.swing.JMenuItem();
        bntOpenTA = new javax.swing.JMenuItem();
        btnCloseCashBox = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Tik administrador");
        setIconImage(new ImageIcon(getClass().getResource("/Images/TecPro.png")).getImage());

        jMenu1.setText("Archivo");
        jMenu1.setToolTipText("");

        btnDisconnect.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_L, java.awt.event.InputEvent.CTRL_MASK));
        btnDisconnect.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/cerrar.png"))); // NOI18N
        btnDisconnect.setText("Cerrar sesión");
        jMenu1.add(btnDisconnect);

        btnExit.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_Q, java.awt.event.InputEvent.CTRL_MASK));
        btnExit.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/apagar.png"))); // NOI18N
        btnExit.setText("Salir");
        jMenu1.add(btnExit);

        jMenuBar1.add(jMenu1);

        jMenu.setText("Administradores");

        btnAdmins.setText("Gestión de admins");
        btnAdmins.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAdminsActionPerformed(evt);
            }
        });
        jMenu.add(btnAdmins);

        jMenuBar1.add(jMenu);

        jMenu3.setText("Productos");

        btnEProduct.setText("Productos elaborados");
        jMenu3.add(btnEProduct);

        btnFProduct.setText("Productos finales");
        jMenu3.add(btnFProduct);

        btnPProduct.setText("Productos primarios");
        jMenu3.add(btnPProduct);

        btnProductCategory.setText("Categorias de los productos");
        jMenu3.add(btnProductCategory);

        btnMenu.setText("Menu");
        btnMenu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnMenuActionPerformed(evt);
            }
        });
        jMenu3.add(btnMenu);

        jMenuBar1.add(jMenu3);

        jMenu4.setText("Proveedores");

        btnProviders.setText("Gestión de proveedores");
        jMenu4.add(btnProviders);

        btnPurchase.setText("Realizar compra");
        jMenu4.add(btnPurchase);

        jMenuBar1.add(jMenu4);

        empleado.setText("Empleados");

        btnEmployes.setText("Gestión de empleados");
        empleado.add(btnEmployes);

        btnLogout.setText("Cerrar sesion");
        empleado.add(btnLogout);

        jMenuBar1.add(empleado);

        jMenu7.setText("Caja");

        btnDailyCashbox.setText("Caja diaria");
        btnDailyCashbox.setActionCommand("CAJA");
        jMenu7.add(btnDailyCashbox);

        jMenuBar1.add(jMenu7);

        jMenu8.setText("Estadísticas");

        btnSalesStatistics.setText("Estadisticas de Ventas");
        btnSalesStatistics.setActionCommand("CAJA");
        jMenu8.add(btnSalesStatistics);

        btnProductStatistics.setText("Estadisticas de Productos");
        jMenu8.add(btnProductStatistics);

        btnProductList.setText("Listado de Productos");
        btnProductList.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnProductListActionPerformed(evt);
            }
        });
        jMenu8.add(btnProductList);

        jMenuBar1.add(jMenu8);

        jMenu6.setText("Configuracion");

        btnConfig.setText("Configuracion general");
        jMenu6.add(btnConfig);

        jMenuBar1.add(jMenu6);

        jMenu2.setText("Acerca de");

        btnAbout.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/acerca.png"))); // NOI18N
        btnAbout.setText("Tec-Pro Software");
        btnAbout.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAboutActionPerformed(evt);
            }
        });
        jMenu2.add(btnAbout);

        jMenuBar1.add(jMenu2);

        turn.setText("Turno");

        btnOpenTM.setText("Abrir Turno Mañana");
        btnOpenTM.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnOpenTMActionPerformed(evt);
            }
        });
        turn.add(btnOpenTM);

        bntOpenTA.setText("Abrir Turno Tarde");
        turn.add(bntOpenTA);

        btnCloseCashBox.setText("Cerrar");
        turn.add(btnCloseCashBox);

        jMenuBar1.add(turn);

        setJMenuBar(jMenuBar1);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnAboutActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAboutActionPerformed
        About acercaDe= new About(this, true);
        acercaDe.setLocationRelativeTo(this);
        acercaDe.setVisible(true);
    }//GEN-LAST:event_btnAboutActionPerformed

    private void btnAdminsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAdminsActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnAdminsActionPerformed

    private void btnMenuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnMenuActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnMenuActionPerformed

    private void btnProductListActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnProductListActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnProductListActionPerformed

    private void btnOpenTMActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnOpenTMActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnOpenTMActionPerformed

    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JMenuItem bntOpenTA;
    private javax.swing.JMenuItem btnAbout;
    private javax.swing.JMenuItem btnAdmins;
    private javax.swing.JMenuItem btnCloseCashBox;
    private javax.swing.JMenuItem btnConfig;
    private javax.swing.JMenuItem btnDailyCashbox;
    private javax.swing.JMenuItem btnDisconnect;
    private javax.swing.JMenuItem btnEProduct;
    private javax.swing.JMenuItem btnEmployes;
    private javax.swing.JMenuItem btnExit;
    private javax.swing.JMenuItem btnFProduct;
    private javax.swing.JMenuItem btnLogout;
    private javax.swing.JMenuItem btnMenu;
    private javax.swing.JMenuItem btnOpenTM;
    private javax.swing.JMenuItem btnPProduct;
    private javax.swing.JMenuItem btnProductCategory;
    private javax.swing.JMenuItem btnProductList;
    private javax.swing.JMenuItem btnProductStatistics;
    private javax.swing.JMenuItem btnProviders;
    private javax.swing.JMenuItem btnPurchase;
    private javax.swing.JMenuItem btnSalesStatistics;
    private javax.swing.JMenu empleado;
    private javax.swing.JMenu jMenu;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JMenu jMenu3;
    private javax.swing.JMenu jMenu4;
    private javax.swing.JMenu jMenu6;
    private javax.swing.JMenu jMenu7;
    private javax.swing.JMenu jMenu8;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenu turn;
    // End of variables declaration//GEN-END:variables


    /**
     * @return the btnNewWithdrawal
     */
    public javax.swing.JMenuItem getBtnDailyCashbox() {
        return btnDailyCashbox;
    }
}
