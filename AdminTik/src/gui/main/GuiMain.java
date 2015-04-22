/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gui.main;

import java.awt.event.ActionListener;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JButton;
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
        jMenu5 = new javax.swing.JMenu();
        btnEmployes = new javax.swing.JMenuItem();
        jMenu2 = new javax.swing.JMenu();
        btnAbout = new javax.swing.JMenuItem();

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

        jMenu5.setText("Empleados");

        btnEmployes.setText("Gestión de empleados");
        jMenu5.add(btnEmployes);

        jMenuBar1.add(jMenu5);

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

    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JMenuItem btnAbout;
    private javax.swing.JMenuItem btnAdmins;
    private javax.swing.JMenuItem btnDisconnect;
    private javax.swing.JMenuItem btnEProduct;
    private javax.swing.JMenuItem btnEmployes;
    private javax.swing.JMenuItem btnExit;
    private javax.swing.JMenuItem btnFProduct;
    private javax.swing.JMenuItem btnMenu;
    private javax.swing.JMenuItem btnPProduct;
    private javax.swing.JMenuItem btnProductCategory;
    private javax.swing.JMenuItem btnProviders;
    private javax.swing.JMenuItem btnPurchase;
    private javax.swing.JMenu jMenu;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JMenu jMenu3;
    private javax.swing.JMenu jMenu4;
    private javax.swing.JMenu jMenu5;
    private javax.swing.JMenuBar jMenuBar1;
    // End of variables declaration//GEN-END:variables
}
