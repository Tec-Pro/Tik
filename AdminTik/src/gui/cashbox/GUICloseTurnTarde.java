/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui.cashbox;

import static gui.cashbox.GuiPayProvider.RET_CANCEL;
import static gui.cashbox.GuiPayProvider.RET_OK;
import gui.withdrawal.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.util.List;
import java.util.Map;
import javax.swing.AbstractAction;
import javax.swing.ActionMap;
import javax.swing.InputMap;
import javax.swing.JComponent;
import javax.swing.JOptionPane;
import javax.swing.KeyStroke;
import utils.ParserFloat;

/**
 *
 * @author nico
 */
public class GUICloseTurnTarde extends javax.swing.JDialog {

    /**
     * Creates new form GUINewWithdrawal
     *
     * @param parent frame padre
     * @param modal true si debe ir adelante
     */
    private float currentAmount;

    /**
     * A return status code - returned if Cancel button has been pressed
     */
    public static final int RET_CANCEL = 0;
    /**
     * A return status code - returned if OK button has been pressed
     */
    public static final int RET_OK = 1;
    /**
     * Creates new form GuiPayPurchase
     */
    private int returnStatus = RET_CANCEL;

    public GUICloseTurnTarde(java.awt.Frame parent, boolean modal, List<Map> admins, float currentAmount) {
        super(parent, modal);
        this.currentAmount = currentAmount;
        initComponents();
        boxAdmins.removeAllItems();
        boxAdmins.addItem("Seleccione administrador");
        for (Map s : admins) {
            boxAdmins.addItem(s.get("id")+"-"+s.get("name"));
        }
        boxAdmins.setSelectedIndex(0);
        lblCurrentAmount.setText(ParserFloat.floatToString(currentAmount));
        txtWithdrawal.setText(ParserFloat.floatToString(currentAmount));
        // Close the dialog when Esc is pressed
        String cancelName = "cancel";
        InputMap inputMap = getRootPane().getInputMap(JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
        inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), cancelName);
        ActionMap actionMap = getRootPane().getActionMap();
        actionMap.put(cancelName, new AbstractAction() {
            public void actionPerformed(ActionEvent e) {
                doClose(RET_CANCEL);
            }
        });
    }

    /**
     * @return the return status of this dialog - one of RET_OK or RET_CANCEL
     */
    public int getReturnStatus() {
        return returnStatus;
    }

    /**
     * Closes the dialog
     */
    private void closeDialog(java.awt.event.WindowEvent evt) {
        doClose(RET_CANCEL);
    }

    private void doClose(int retStatus) {
        returnStatus = retStatus;
        setVisible(false);
        dispose();
    }

    public float getAmountWithdrawal() {
        return ParserFloat.stringToFloat(txtWithdrawal.getText());
    }

    public float getAmountNextTurn() {
        return ParserFloat.stringToFloat(txtAmountNextTurn.getText());
    }

    public int getIdAdminSelected(){
        return Integer.valueOf(((String)boxAdmins.getSelectedItem()).split("-")[0]);
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
        panelImage1 = new org.edisoncor.gui.panel.PanelImage();
        txtWithdrawal = new javax.swing.JFormattedTextField();
        jLabel2 = new javax.swing.JLabel();
        boxAdmins = new javax.swing.JComboBox();
        lblCurrentAmount = new javax.swing.JLabel();
        btnOk = new javax.swing.JButton();
        jLabel4 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        txtAmountNextTurn = new javax.swing.JFormattedTextField();
        jLabel1 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        panelImage1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/fondo gris.png"))); // NOI18N

        txtWithdrawal.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#0.00"))));
        txtWithdrawal.setText("0");
        txtWithdrawal.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtWithdrawalActionPerformed(evt);
            }
        });

        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel2.setText("Monto a retirar");

        lblCurrentAmount.setForeground(new java.awt.Color(255, 255, 255));
        lblCurrentAmount.setText("100.00");

        btnOk.setText("OK");
        btnOk.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnOkActionPerformed(evt);
            }
        });

        jLabel4.setForeground(new java.awt.Color(255, 255, 255));
        jLabel4.setText("Saldo en caja:");

        jLabel6.setForeground(new java.awt.Color(255, 255, 255));
        jLabel6.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel6.setText("Monto para turno siguiente:");

        txtAmountNextTurn.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#0.00"))));
        txtAmountNextTurn.setText("0");
        txtAmountNextTurn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtAmountNextTurnActionPerformed(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("DejaVu Sans", 0, 24)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Admins");

        javax.swing.GroupLayout panelImage1Layout = new javax.swing.GroupLayout(panelImage1);
        panelImage1.setLayout(panelImage1Layout);
        panelImage1Layout.setHorizontalGroup(
            panelImage1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelImage1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(panelImage1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelImage1Layout.createSequentialGroup()
                        .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 128, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtWithdrawal, javax.swing.GroupLayout.PREFERRED_SIZE, 299, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(panelImage1Layout.createSequentialGroup()
                        .addComponent(jLabel6)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtAmountNextTurn, javax.swing.GroupLayout.PREFERRED_SIZE, 240, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(15, 15, 15))
            .addComponent(jLabel1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(panelImage1Layout.createSequentialGroup()
                .addGroup(panelImage1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelImage1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel4)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lblCurrentAmount))
                    .addGroup(panelImage1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(boxAdmins, javax.swing.GroupLayout.PREFERRED_SIZE, 460, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(panelImage1Layout.createSequentialGroup()
                        .addGap(186, 186, 186)
                        .addComponent(btnOk, javax.swing.GroupLayout.PREFERRED_SIZE, 107, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        panelImage1Layout.setVerticalGroup(
            panelImage1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelImage1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addGap(12, 12, 12)
                .addComponent(boxAdmins, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelImage1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(lblCurrentAmount))
                .addGap(10, 10, 10)
                .addGroup(panelImage1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(txtWithdrawal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(panelImage1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(txtAmountNextTurn, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(28, 28, 28)
                .addComponent(btnOk)
                .addContainerGap())
        );

        jScrollPane1.setViewportView(panelImage1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 288, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void txtWithdrawalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtWithdrawalActionPerformed
        if (ParserFloat.stringToFloat(txtWithdrawal.getText()) > currentAmount) {
            //lo que quiere retirar es mayor a lo que hay en caja, seteo lo que hay en caja
            txtWithdrawal.setText(ParserFloat.floatToString(currentAmount));
            txtAmountNextTurn.setText(ParserFloat.floatToString(new Float("0")));
        } else {
            //seteo el turno siguiente como la diferencia
            txtAmountNextTurn.setText(ParserFloat.floatToString(currentAmount - ParserFloat.stringToFloat(txtWithdrawal.getText())));
        }
    }//GEN-LAST:event_txtWithdrawalActionPerformed

    private void btnOkActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnOkActionPerformed
        if (boxAdmins.getSelectedIndex() > 0) {
            if (getAmountNextTurn() > -999 || getAmountWithdrawal() > -999) {
                int result = JOptionPane.showConfirmDialog(this, "Usted va a a realizar:"
                    + "\n -Un retiro de: " + ParserFloat.floatToString(getAmountWithdrawal())
                    + "\n-Deja en caja para mañana: " + ParserFloat.floatToString(getAmountNextTurn())
                    + "\n¿Está seguro?", "Retiro de dinero y cierre de dia", JOptionPane.OK_CANCEL_OPTION);
                if (result == JOptionPane.OK_OPTION) {
                    doClose(RET_OK);
                }
            } else {
                JOptionPane.showConfirmDialog(this, "Ingrese un valor valido", "aviso", JOptionPane.OK_OPTION);
            }
        } else {
            JOptionPane.showConfirmDialog(this, "Seleccione un administrador", "aviso", JOptionPane.OK_OPTION);
        }
    }//GEN-LAST:event_btnOkActionPerformed

    private void txtAmountNextTurnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtAmountNextTurnActionPerformed
        if (ParserFloat.stringToFloat(txtAmountNextTurn.getText()) > currentAmount) {
            //lo que quiere retirar es mayor a lo que hay en caja, seteo lo que hay en caja
            txtAmountNextTurn.setText(ParserFloat.floatToString(currentAmount));
            txtWithdrawal.setText(ParserFloat.floatToString(new Float("0")));
        } else {
            //seteo el turno siguiente como la diferencia
            txtWithdrawal.setText(ParserFloat.floatToString(currentAmount - ParserFloat.stringToFloat(txtAmountNextTurn.getText())));
        }
    }//GEN-LAST:event_txtAmountNextTurnActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox boxAdmins;
    private javax.swing.JButton btnOk;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblCurrentAmount;
    private org.edisoncor.gui.panel.PanelImage panelImage1;
    private javax.swing.JFormattedTextField txtAmountNextTurn;
    private javax.swing.JFormattedTextField txtWithdrawal;
    // End of variables declaration//GEN-END:variables

}
