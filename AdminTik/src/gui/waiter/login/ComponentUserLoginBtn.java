/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gui.waiter.login;

/**
 *
 * @author jacinto
 */
public class ComponentUserLoginBtn extends javax.swing.JPanel {

    private String name;

    /**
     * Creates new form ComponentUserLoginBtn
     */
    public ComponentUserLoginBtn(String name) {
        initComponents();
        btn.setText(name);
        this.name = name;
        this.btn.setActionCommand(name);
        this.btn.setToolTipText(name);
        this.setVisible(true);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        btn = new javax.swing.JButton();

        setOpaque(false);
        setPreferredSize(new java.awt.Dimension(125, 100));

        btn.setMaximumSize(new java.awt.Dimension(100, 100));
        btn.setMinimumSize(new java.awt.Dimension(100, 100));
        btn.setPreferredSize(new java.awt.Dimension(100, 100));
        btn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addComponent(btn, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(btn, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void btnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnActionPerformed
        //FILTRAR
    }//GEN-LAST:event_btnActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    public javax.swing.JButton btn;
    // End of variables declaration//GEN-END:variables
}
