/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gui.waiter;

import controllers.waiter.ControllerGuiMain;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.LinkedList;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import org.edisoncor.gui.panel.PanelImage;

/**
 *
 * @author jacinto
 */
public class GuiMain extends javax.swing.JFrame {

    private int gridx = 0;
    private int gridy = 0;
    LinkedList<GuiMenuDetail> orders;

    /**
     * Creates new form GuiMain
     */
    public GuiMain() {
        initComponents();
        setVisible(false);
        orders = new LinkedList<GuiMenuDetail>();
        clearAllOrders();
        setDefaultCloseOperation(HIDE_ON_CLOSE);
        this.addWindowListener(new WindowAdapter(){
                @Override
                public void windowClosing(WindowEvent e){
                    ControllerGuiMain.setLoginGridVisible(true);
                }
            });
    }



    public void addActiveOrder(GuiMenuDetail order) {
        if (gridx == 4) {
            gridy++;
            gridx = 0;
        }
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.weightx = 230;
        constraints.weighty = 230;
        constraints.gridx = gridx;
        constraints.gridy = gridy;
        constraints.anchor = GridBagConstraints.NORTHWEST;
        constraints.insets = new Insets(15, 15, 15, 15);
        panelActivedOrders.add(order, constraints);
        gridx += 1;
        panelActivedOrders.validate();
    }

        public void addPanelNewOrder(GuiPanelNew newPanel) {
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.weightx = 230;
        constraints.weighty = 230;
        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.anchor = GridBagConstraints.NORTHWEST;
        constraints.insets = new Insets(15, 15, 15, 15);
        panelActivedOrders.add(newPanel, constraints);
        gridx += 1;
        panelActivedOrders.validate();
    }


    public void clearAllOrders() {
        panelActivedOrders.removeAll();
        gridx = 0;
        gridy = 0;
        validateAll();
    }

    public void validateAll() {
        panelActivedOrders.validate();
        panelActivedOrders.repaint();
        this.validate();
        this.repaint();
    }


    
    public void setActionListener(ActionListener lis){
        chkAllOrders.addActionListener(lis);
    }

    public JCheckBox getChkAllOrders() {
        return chkAllOrders;
    }

    public PanelImage getPanelActivedOrders() {
        return panelActivedOrders;
    }
    
        /**
     *
     * @param index
     */
    public void removeElementOfOrdersGrid(int index) {
        panelActivedOrders.remove(index);
        panelActivedOrders.revalidate();
        panelActivedOrders.repaint();
        gridx = 0;
        gridy = 0;
        for(Component cop : panelActivedOrders.getComponents()){
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.weightx = 230;
        constraints.weighty = 230;
        constraints.gridx = gridx;
        constraints.gridy = gridy;
        constraints.anchor = GridBagConstraints.NORTHWEST;
        constraints.insets = new Insets(15, 15, 15, 15);
        panelActivedOrders.add(cop, constraints);
        panelActivedOrders.revalidate();
        panelActivedOrders.repaint();
        gridx++;
        if (gridx == 4) {
            gridy++;
            gridx = 0;
        }
        }
    }
        /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        jPanel4 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new org.edisoncor.gui.panel.PanelImage();
        pnlWatch = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        lblTurn = new javax.swing.JLabel();
        chkAllOrders = new javax.swing.JCheckBox();
        jScrollPane4 = new javax.swing.JScrollPane();
        panelActivedOrders = new org.edisoncor.gui.panel.PanelImage();
        jPanel15 = new javax.swing.JPanel();

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("SISTEMA MOZOS TIK");

        jScrollPane1.setBorder(null);
        jScrollPane1.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        jScrollPane1.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);

        jPanel2.setBorder(null);
        jPanel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/fondo gris.png"))); // NOI18N

        pnlWatch.setBackground(new Color(0,0,0,127));
        pnlWatch.setLayout(new java.awt.BorderLayout());

        jPanel3.setForeground(java.awt.Color.white);
        jPanel3.setOpaque(false);

        jLabel1.setFont(new java.awt.Font("Arial", 1, 25)); // NOI18N
        jLabel1.setForeground(java.awt.Color.white);
        jLabel1.setText("TURNO:");

        lblTurn.setFont(new java.awt.Font("Arial", 1, 25)); // NOI18N
        lblTurn.setForeground(java.awt.Color.white);
        lblTurn.setText("MAÑANA");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblTurn)
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(lblTurn))
                .addContainerGap())
        );

        pnlWatch.add(jPanel3, java.awt.BorderLayout.LINE_START);

        chkAllOrders.setBackground(java.awt.Color.black);
        chkAllOrders.setFont(new java.awt.Font("Cantarell", 1, 15)); // NOI18N
        chkAllOrders.setForeground(java.awt.Color.white);
        chkAllOrders.setText("VER TODAS");
        chkAllOrders.setToolTipText("Ver todas las ordenes");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(chkAllOrders)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(pnlWatch, javax.swing.GroupLayout.DEFAULT_SIZE, 770, Short.MAX_VALUE)
                .addGap(21, 21, 21))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(pnlWatch, javax.swing.GroupLayout.PREFERRED_SIZE, 30, Short.MAX_VALUE)
                    .addComponent(chkAllOrders, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        jScrollPane4.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        jScrollPane4.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

        panelActivedOrders.setBorder(null);
        panelActivedOrders.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/fondo gris.png"))); // NOI18N
        panelActivedOrders.setLayout(new java.awt.GridBagLayout());

        jPanel15.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jPanel15.setPreferredSize(new java.awt.Dimension(304, 304));

        javax.swing.GroupLayout jPanel15Layout = new javax.swing.GroupLayout(jPanel15);
        jPanel15.setLayout(jPanel15Layout);
        jPanel15Layout.setHorizontalGroup(
            jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 298, Short.MAX_VALUE)
        );
        jPanel15Layout.setVerticalGroup(
            jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 298, Short.MAX_VALUE)
        );

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        panelActivedOrders.add(jPanel15, gridBagConstraints);

        jScrollPane4.setViewportView(panelActivedOrders);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane4)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(0, 0, 0))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(jScrollPane4, javax.swing.GroupLayout.DEFAULT_SIZE, 371, Short.MAX_VALUE)
                .addGap(0, 0, 0))
        );

        jScrollPane1.setViewportView(jPanel1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 421, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

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
            java.util.logging.Logger.getLogger(GuiMain.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(GuiMain.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(GuiMain.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(GuiMain.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new GuiMain().setVisible(false);
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JCheckBox chkAllOrders;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel15;
    private org.edisoncor.gui.panel.PanelImage jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JLabel lblTurn;
    private org.edisoncor.gui.panel.PanelImage panelActivedOrders;
    private javax.swing.JPanel pnlWatch;
    // End of variables declaration//GEN-END:variables
}
