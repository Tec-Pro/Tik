/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui.waiter;

import javax.swing.JLabel;



/**
 *
 * @author nico
 */
public class GuiPanelNew extends javax.swing.JPanel {



    /**
     * Creates new form GuiMenuDetail
     */
    public GuiPanelNew() {
        initComponents();
    }

    public JLabel getLblNew() {
        return lblNew;
    }

    

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        lblNew = new javax.swing.JLabel();

        setBackground(java.awt.Color.white);
        setBorder(null);
        setMaximumSize(new java.awt.Dimension(230, 230));
        setMinimumSize(new java.awt.Dimension(230, 230));
        setOpaque(false);
        setPreferredSize(new java.awt.Dimension(230, 230));

        lblNew.setFont(new java.awt.Font("Cantarell", 1, 300)); // NOI18N
        lblNew.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblNew.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/add_icon.png"))); // NOI18N
        lblNew.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(lblNew, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(lblNew, javax.swing.GroupLayout.DEFAULT_SIZE, 230, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel lblNew;
    // End of variables declaration//GEN-END:variables
}
