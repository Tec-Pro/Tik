/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui.cashbox;

import gui.providers.purchases.*;
import static gui.providers.purchases.GuiAddProductToPurchase.RET_CANCEL;
import static gui.providers.purchases.GuiAddProductToPurchase.RET_OK;
import interfaces.providers.InterfaceProvider;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.AbstractAction;
import javax.swing.ActionMap;
import javax.swing.InputMap;
import javax.swing.JComponent;
import javax.swing.JOptionPane;
import javax.swing.KeyStroke;
import utils.InterfaceName;
import utils.ParserFloat;

/**
 *
 * @author nico
 */
public class GuiPayProvider extends javax.swing.JDialog {

    private final InterfaceProvider interfaceProvider;

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

    public GuiPayProvider(java.awt.Frame parent, boolean modal , List<Map> admins, List<Map> providers) throws RemoteException, NotBoundException {
        super(parent, modal);
        initComponents();
        this.interfaceProvider = (InterfaceProvider) InterfaceName.registry.lookup(InterfaceName.CRUDProvider);
        txtPayAdmin.setText(ParserFloat.floatToString(new Float("0")));
        txtPayBox.setText(ParserFloat.floatToString(new Float("0")));
        lblAmount.setText(ParserFloat.floatToString(new Float("0")));
        boxAdmins.removeAllItems();
        boxAdmins.addItem("Seleccione administrador");
        for (Map s : admins) {
            boxAdmins.addItem(s.get("name"));
        }
        boxAdmins.setSelectedIndex(0);
        boxProviders.removeAllItems();
        boxProviders.addItem("OTROS GASTOS");
        for (Map p : providers) {
            boxProviders.addItem(p.get("id")+"-"+p.get("name"));
        }
        boxProviders.setSelectedIndex(0);
        lblCurrentAccount.setText("0.00");
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

    /**
     * retorno el nombre del admin que paga
     *
     * @return
     */
    public String getNameAdmin() {
        return (String) boxAdmins.getSelectedItem();
    }

    /**
     * retorno lo que paga la caja
     *
     * @return
     */
    public float getPayBox() {
        return ParserFloat.stringToFloat(txtPayBox.getText());
    }

    /**
     * retorno el saldo que paga el admin de su bolsillo
     *
     * @return
     */
    public float getPayAdmin() {
        return ParserFloat.stringToFloat(txtPayAdmin.getText());
    }

    /**
     * retorno el id del proveedor al que le paga, -1 si es otros gastos
     *
     * @return
     */
    public int getProvider() {
        if (boxProviders.getSelectedIndex() == 0) {
            return -1;
        }
        return Integer.valueOf(((String) boxProviders.getSelectedItem()).split("-")[0]);
    }

    public String getDetail(){
        return txtDetail.getText();
    }
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        lblAmount = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        chkBox = new javax.swing.JCheckBox();
        jLabel2 = new javax.swing.JLabel();
        txtPayBox = new javax.swing.JFormattedTextField();
        jLabel3 = new javax.swing.JLabel();
        txtPayAdmin = new javax.swing.JFormattedTextField();
        boxAdmins = new javax.swing.JComboBox();
        btnPay = new javax.swing.JButton();
        boxProviders = new javax.swing.JComboBox();
        jLabel4 = new javax.swing.JLabel();
        lblCurrentAccount = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        txtDetail = new javax.swing.JTextArea();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jLabel1.setFont(new java.awt.Font("Cantarell", 1, 18)); // NOI18N
        jLabel1.setText("Total a abonar:  $");

        lblAmount.setFont(new java.awt.Font("Cantarell", 1, 18)); // NOI18N
        lblAmount.setText("50.50");

        jPanel1.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));

        chkBox.setText("Paga desde caja");
        chkBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chkBoxActionPerformed(evt);
            }
        });

        jLabel2.setText("Monto a pagar desde caja:");

        txtPayBox.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#0.00"))));
        txtPayBox.setEnabled(false);
        txtPayBox.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtPayBoxFocusLost(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(chkBox)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtPayBox, javax.swing.GroupLayout.DEFAULT_SIZE, 265, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(chkBox)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(txtPayBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        jLabel3.setText("Monto a pagar");

        txtPayAdmin.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#0.00"))));
        txtPayAdmin.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtPayAdminFocusLost(evt);
            }
        });

        boxAdmins.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        btnPay.setText("PAGAR");
        btnPay.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPayActionPerformed(evt);
            }
        });

        boxProviders.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        boxProviders.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                boxProvidersActionPerformed(evt);
            }
        });

        jLabel4.setText("Proveedor:");

        lblCurrentAccount.setText("100.00");

        jLabel6.setText("Cta corriente:");

        jLabel5.setText("Detalle");

        txtDetail.setColumns(20);
        txtDetail.setRows(5);
        jScrollPane1.setViewportView(txtDetail);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(boxAdmins, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtPayAdmin, javax.swing.GroupLayout.PREFERRED_SIZE, 144, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel4)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(boxProviders, javax.swing.GroupLayout.PREFERRED_SIZE, 195, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel6)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lblCurrentAccount, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jLabel1)
                        .addGap(1, 1, 1)
                        .addComponent(lblAmount))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addComponent(jLabel5)
                                .addGap(252, 252, 252)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnPay, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(boxProviders, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblCurrentAccount)
                    .addComponent(jLabel6))
                .addGap(5, 5, 5)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(txtPayAdmin, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(boxAdmins, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(lblAmount))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel5)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(btnPay))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void chkBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chkBoxActionPerformed
        txtPayBox.setEnabled(chkBox.isSelected());
        if (!chkBox.isSelected()) {
            txtPayBox.setText(ParserFloat.floatToString(new Float("0")));
            float amountBox = ParserFloat.stringToFloat(txtPayBox.getText());
            float amountAdmin = ParserFloat.stringToFloat(txtPayAdmin.getText());
            lblAmount.setText(ParserFloat.floatToString(amountAdmin + amountBox));
        }
    }//GEN-LAST:event_chkBoxActionPerformed

    private void txtPayBoxFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtPayBoxFocusLost
        float amountBox = ParserFloat.stringToFloat(txtPayBox.getText());
        float amountAdmin = ParserFloat.stringToFloat(txtPayAdmin.getText());
        lblAmount.setText(ParserFloat.floatToString(amountAdmin + amountBox));
    }//GEN-LAST:event_txtPayBoxFocusLost

    private void btnPayActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPayActionPerformed
        if (boxAdmins.getSelectedIndex() > 0) {
            if (getPayAdmin() > -999 || getPayBox() > -999) {
                System.out.println(getPayBox()+"s"+getPayAdmin());
                doClose(RET_OK);
            } else {
                JOptionPane.showConfirmDialog(this, "Ingrese un valor valido", "aviso", JOptionPane.OK_OPTION);
            }
        } else {
            JOptionPane.showConfirmDialog(this, "Seleccione un administrador", "aviso", JOptionPane.OK_OPTION);
        }
    }//GEN-LAST:event_btnPayActionPerformed

    private void boxProvidersActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_boxProvidersActionPerformed
        if (boxProviders.getSelectedIndex() > 0) {
            try {
                String idProvider = ((String) boxProviders.getSelectedItem()).split("-")[0];
                interfaceProvider.getCurrentAccount(Integer.valueOf(idProvider));
                lblCurrentAccount.setText(ParserFloat.floatToString(interfaceProvider.getCurrentAccount(Integer.valueOf(idProvider))));
            } catch (RemoteException ex) {
                Logger.getLogger(GuiPayProvider.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }//GEN-LAST:event_boxProvidersActionPerformed

    private void txtPayAdminFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtPayAdminFocusLost
        float amountBox = ParserFloat.stringToFloat(txtPayBox.getText());
        float amountAdmin = ParserFloat.stringToFloat(txtPayAdmin.getText());
        lblAmount.setText(ParserFloat.floatToString(amountAdmin + amountBox));
    }//GEN-LAST:event_txtPayAdminFocusLost


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox boxAdmins;
    private javax.swing.JComboBox boxProviders;
    private javax.swing.JButton btnPay;
    private javax.swing.JCheckBox chkBox;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblAmount;
    private javax.swing.JLabel lblCurrentAccount;
    private javax.swing.JTextArea txtDetail;
    private javax.swing.JFormattedTextField txtPayAdmin;
    private javax.swing.JFormattedTextField txtPayBox;
    // End of variables declaration//GEN-END:variables
}
