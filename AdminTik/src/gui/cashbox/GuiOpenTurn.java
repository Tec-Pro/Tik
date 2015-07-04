/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gui.cashbox;

import java.awt.Color;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JLabel;

/**
 *
 * @author jacinto
 */
public class GuiOpenTurn extends javax.swing.JInternalFrame {

    /**
     * Creates new form GuiOpenTurn
     */
    public GuiOpenTurn() {
        initComponents();
    }
    
    public void setActionListener(ActionListener lis){
        this.btnAfternoon.addActionListener(lis);
        this.btnMorning.addActionListener(lis);
        this.btnResume.addActionListener(lis);
    }

 
    public JButton getBtnAfternoon() {
        return btnAfternoon;
    }

    public JButton getBtnMorning() {
        return btnMorning;
    }

    public JButton getBtnResume() {
        return btnResume;
    }

    public JLabel getLblABalance() {
        return lblABalance;
    }

    public JLabel getLblAGain() {
        return lblAGain;
    }

    public JLabel getLblMBalance() {
        return lblMBalance;
    }

    public JLabel getLblMGain() {
        return lblMGain;
    }
    
    

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        btnMorning = new javax.swing.JButton();
        btnAfternoon = new javax.swing.JButton();
        btnResume = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        lblMBalance = new javax.swing.JLabel();
        lblMGain = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        lblABalance = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        lblAGain = new javax.swing.JLabel();

        setClosable(true);
        setTitle("Caja");
        setToolTipText("");

        btnMorning.setText("Mañana");

        btnAfternoon.setText("Tarde");

        btnResume.setText("Resumen");

        jLabel1.setText("Recaudado:");

        jLabel2.setText("Saldo:");

        lblMBalance.setText("20000");

        lblMGain.setText("20000");

        jLabel5.setText("Saldo:");

        lblABalance.setText("20000");

        jLabel7.setText("Recaudado:");

        lblAGain.setText("20000");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel2)
                            .addComponent(jLabel1))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(lblMBalance)
                            .addComponent(lblMGain)))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(46, 46, 46)
                        .addComponent(btnMorning, javax.swing.GroupLayout.PREFERRED_SIZE, 78, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel5)
                            .addComponent(jLabel7))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(lblABalance)
                            .addComponent(lblAGain)))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(34, 34, 34)
                        .addComponent(btnAfternoon, javax.swing.GroupLayout.PREFERRED_SIZE, 72, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(35, 35, 35))
            .addGroup(layout.createSequentialGroup()
                .addGap(138, 138, 138)
                .addComponent(btnResume)
                .addContainerGap(138, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(41, 41, 41)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnMorning)
                    .addComponent(btnAfternoon))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel1))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(lblMBalance)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(lblMGain))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel5)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel7))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(lblABalance)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(lblAGain)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnResume, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(24, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAfternoon;
    private javax.swing.JButton btnMorning;
    private javax.swing.JButton btnResume;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel lblABalance;
    private javax.swing.JLabel lblAGain;
    private javax.swing.JLabel lblMBalance;
    private javax.swing.JLabel lblMGain;
    // End of variables declaration//GEN-END:variables
}
