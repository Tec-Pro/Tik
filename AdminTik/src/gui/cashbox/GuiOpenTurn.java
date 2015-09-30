/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gui.cashbox;

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

    public void setActionListener(ActionListener lis) {
        this.btnSeeAfternoon.addActionListener(lis);
        this.btnCloseAfternoon.addActionListener(lis);
        this.btnOpenAfternoon.addActionListener(lis);
        this.btnOpenMorning.addActionListener(lis);
        this.btnSeeMorning.addActionListener(lis);
        this.btnCloseMorning.addActionListener(lis);
        this.btnResume.addActionListener(lis);
        this.btnUpdate.addActionListener(lis);
        this.btnCloseSession.addActionListener(lis);
    }

    public JButton getBtnUpdate() {
        return btnUpdate;
    }

    public JButton getBtnCloseSession() {
        return btnCloseSession;
    }
    
    

    public JButton getBtnCloseAfternoon() {
        return btnCloseAfternoon;
    }

    public JButton getBtnCloseMorning() {
        return btnCloseMorning;
    }

    public JButton getBtnOpenAfternoon() {
        return btnOpenAfternoon;
    }

    public JButton getBtnOpenMorning() {
        return btnOpenMorning;
    }

    public JButton getBtnResume() {
        return btnResume;
    }

    public JButton getBtnSeeAfternoon() {
        return btnSeeAfternoon;
    }

    public JButton getBtnSeeMorning() {
        return btnSeeMorning;
    }

    public JLabel getLblAGain() {
        return lblAGain;
    }

    public JLabel getLblBalance() {
        return lblBalance;
    }

    public JLabel getLblMGain() {
        return lblMGain;
    }

    public JLabel getLblWA1() {
        return lblWA1;
    }

    public JLabel getLblWA10() {
        return lblWA10;
    }

    public JLabel getLblWA2() {
        return lblWA2;
    }

    public JLabel getLblWA3() {
        return lblWA3;
    }

    public JLabel getLblWA4() {
        return lblWA4;
    }

    public JLabel getLblWA5() {
        return lblWA5;
    }

    public JLabel getLblWA6() {
        return lblWA6;
    }

    public JLabel getLblWA7() {
        return lblWA7;
    }

    public JLabel getLblWA8() {
        return lblWA8;
    }

    public JLabel getLblWA9() {
        return lblWA9;
    }

    public JLabel getLblWAGain1() {
        return lblWAGain1;
    }

    public JLabel getLblWAGain10() {
        return lblWAGain10;
    }

    public JLabel getLblWAGain2() {
        return lblWAGain2;
    }

    public JLabel getLblWAGain3() {
        return lblWAGain3;
    }

    public JLabel getLblWAGain4() {
        return lblWAGain4;
    }

    public JLabel getLblWAGain5() {
        return lblWAGain5;
    }

    public JLabel getLblWAGain6() {
        return lblWAGain6;
    }

    public JLabel getLblWAGain7() {
        return lblWAGain7;
    }

    public JLabel getLblWAGain8() {
        return lblWAGain8;
    }

    public JLabel getLblWAGain9() {
        return lblWAGain9;
    }

    public JLabel getLblWM1() {
        return lblWM1;
    }

    public JLabel getLblWM10() {
        return lblWM10;
    }

    public JLabel getLblWM2() {
        return lblWM2;
    }

    public JLabel getLblWM3() {
        return lblWM3;
    }

    public JLabel getLblWM4() {
        return lblWM4;
    }

    public JLabel getLblWM5() {
        return lblWM5;
    }

    public JLabel getLblWM6() {
        return lblWM6;
    }

    public JLabel getLblWM7() {
        return lblWM7;
    }

    public JLabel getLblWM8() {
        return lblWM8;
    }

    public JLabel getLblWM9() {
        return lblWM9;
    }

    public JLabel getLblWMGain1() {
        return lblWMGain1;
    }

    public JLabel getLblWMGain10() {
        return lblWMGain10;
    }

    public JLabel getLblWMGain2() {
        return lblWMGain2;
    }

    public JLabel getLblWMGain3() {
        return lblWMGain3;
    }

    public JLabel getLblWMGain4() {
        return lblWMGain4;
    }

    public JLabel getLblWMGain5() {
        return lblWMGain5;
    }

    public JLabel getLblWMGain6() {
        return lblWMGain6;
    }

    public JLabel getLblWMGain7() {
        return lblWMGain7;
    }

    public JLabel getLblWMGain8() {
        return lblWMGain8;
    }

    public JLabel getLblWMGain9() {
        return lblWMGain9;
    }

    /**
     * limpia la parte de la gui correspondiente a la mañana
     */
    public void clearMorning() {
        lblMGain.setText("");
        lblWM1.setVisible(false);
        lblWM10.setVisible(false);
        lblWM2.setVisible(false);
        lblWM3.setVisible(false);
        lblWM4.setVisible(false);
        lblWM5.setVisible(false);
        lblWM6.setVisible(false);
        lblWM7.setVisible(false);
        lblWM8.setVisible(false);
        lblWM9.setVisible(false);
        lblWMGain1.setVisible(false);
        lblWMGain10.setVisible(false);
        lblWMGain2.setVisible(false);
        lblWMGain3.setVisible(false);
        lblWMGain4.setVisible(false);
        lblWMGain5.setVisible(false);
        lblWMGain6.setVisible(false);
        lblWMGain7.setVisible(false);
        lblWMGain8.setVisible(false);
        lblWMGain9.setVisible(false);
    }

    /**
     * limpia la parte de la gui correspondiente a la tarde
     */
    public void clearAfternoon() {
        lblAGain.setText("");
        lblWA1.setVisible(false);
        lblWA10.setVisible(false);
        lblWA2.setVisible(false);
        lblWA3.setVisible(false);
        lblWA4.setVisible(false);
        lblWA5.setVisible(false);
        lblWA6.setVisible(false);
        lblWA7.setVisible(false);
        lblWA8.setVisible(false);
        lblWA9.setVisible(false);
        lblWAGain1.setVisible(false);
        lblWAGain10.setVisible(false);
        lblWAGain2.setVisible(false);
        lblWAGain3.setVisible(false);
        lblWAGain4.setVisible(false);
        lblWAGain5.setVisible(false);
        lblWAGain6.setVisible(false);
        lblWAGain7.setVisible(false);
        lblWAGain8.setVisible(false);
        lblWAGain9.setVisible(false);
    }

    /**
     * devuelve los lbl para el nombre correspondiente al indice y el turno
     */
    public JLabel getLblNameByIndex(String turn, int index) {
        if (turn.endsWith("M")) {
            switch (index) {
                case 1:
                    return lblWM1;
                case 2:
                    return lblWM2;
                case 3:
                    return lblWM3;
                case 4:
                    return lblWM4;
                case 5:
                    return lblWM5;
                case 6:
                    return lblWM6;
                case 7:
                    return lblWM7;
                case 8:
                    return lblWM8;
                case 9:
                    return lblWM9;
                case 10:
                    return lblWM10;
            }
        } else {
            switch (index) {
                case 1:
                    return lblWA1;
                case 2:
                    return lblWA2;
                case 3:
                    return lblWA3;
                case 4:
                    return lblWA4;
                case 5:
                    return lblWA5;
                case 6:
                    return lblWA6;
                case 7:
                    return lblWA7;
                case 8:
                    return lblWA8;
                case 9:
                    return lblWA9;
                case 10:
                    return lblWA10;
            }
        }
        return null;
    }

    /**
     * Devuelve el lbl gain correspondiente al indice y el turno
     */
    public JLabel getLblGainByIndex(String turn, int index) {
        if (turn.endsWith("M")) {
            switch (index) {
                case 1:
                    return lblWMGain1;
                case 2:
                    return lblWMGain2;
                case 3:
                    return lblWMGain3;
                case 4:
                    return lblWMGain4;
                case 5:
                    return lblWMGain5;
                case 6:
                    return lblWMGain6;
                case 7:
                    return lblWMGain7;
                case 8:
                    return lblWMGain8;
                case 9:
                    return lblWMGain9;
                case 10:
                    return lblWMGain10;
            }
        } else {
            switch (index) {
                case 1:
                    return lblWAGain1;
                case 2:
                    return lblWAGain2;
                case 3:
                    return lblWAGain3;
                case 4:
                    return lblWAGain4;
                case 5:
                    return lblWAGain5;
                case 6:
                    return lblWAGain6;
                case 7:
                    return lblWAGain7;
                case 8:
                    return lblWAGain8;
                case 9:
                    return lblWAGain9;
                case 10:
                    return lblWAGain10;
            }
        }
        return null;
    }

    /**
     * limpia la gui
     */
    public void clear() {
        clearMorning();
        clearAfternoon();
        lblBalance.setText("");
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        panelImage1 = new org.edisoncor.gui.panel.PanelImage();
        lblWM9 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        lblWAGain7 = new javax.swing.JLabel();
        lblWA3 = new javax.swing.JLabel();
        lblWMGain5 = new javax.swing.JLabel();
        btnCloseAfternoon = new javax.swing.JButton();
        lblWMGain8 = new javax.swing.JLabel();
        lblWAGain10 = new javax.swing.JLabel();
        lblWA9 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        lblWM7 = new javax.swing.JLabel();
        btnCloseSession = new javax.swing.JButton();
        lblWAGain1 = new javax.swing.JLabel();
        lblWM1 = new javax.swing.JLabel();
        btnOpenMorning = new javax.swing.JButton();
        lblWM6 = new javax.swing.JLabel();
        lblWMGain6 = new javax.swing.JLabel();
        btnOpenAfternoon = new javax.swing.JButton();
        btnSeeMorning = new javax.swing.JButton();
        lblMGain = new javax.swing.JLabel();
        lblWAGain8 = new javax.swing.JLabel();
        lblWA7 = new javax.swing.JLabel();
        lblWA10 = new javax.swing.JLabel();
        btnCloseMorning = new javax.swing.JButton();
        lblWMGain1 = new javax.swing.JLabel();
        btnSeeAfternoon = new javax.swing.JButton();
        lblWMGain4 = new javax.swing.JLabel();
        lblWM10 = new javax.swing.JLabel();
        lblAGain = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        lblWAGain6 = new javax.swing.JLabel();
        lblWM2 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        lblWM3 = new javax.swing.JLabel();
        lblWMGain3 = new javax.swing.JLabel();
        lblWMGain9 = new javax.swing.JLabel();
        btnResume = new javax.swing.JButton();
        lblBalance = new javax.swing.JLabel();
        lblWM5 = new javax.swing.JLabel();
        lblWA8 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        lblWAGain5 = new javax.swing.JLabel();
        btnUpdate = new javax.swing.JButton();
        lblWA4 = new javax.swing.JLabel();
        lblWAGain2 = new javax.swing.JLabel();
        lblWMGain2 = new javax.swing.JLabel();
        lblWM8 = new javax.swing.JLabel();
        lblWAGain9 = new javax.swing.JLabel();
        lblWA6 = new javax.swing.JLabel();
        lblWA5 = new javax.swing.JLabel();
        lblWMGain7 = new javax.swing.JLabel();
        lblWA1 = new javax.swing.JLabel();
        lblWAGain4 = new javax.swing.JLabel();
        lblWMGain10 = new javax.swing.JLabel();
        lblWAGain3 = new javax.swing.JLabel();
        lblWM4 = new javax.swing.JLabel();
        lblWA2 = new javax.swing.JLabel();

        setClosable(true);
        setDefaultCloseOperation(javax.swing.WindowConstants.HIDE_ON_CLOSE);
        setTitle("Caja");
        setToolTipText("");

        panelImage1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/fondo gris.png"))); // NOI18N

        lblWM9.setForeground(new java.awt.Color(255, 255, 255));
        lblWM9.setText("nombre");

        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setText("Saldo:");

        lblWAGain7.setForeground(new java.awt.Color(255, 255, 255));
        lblWAGain7.setText("20000");

        lblWA3.setForeground(new java.awt.Color(255, 255, 255));
        lblWA3.setText("nombre");

        lblWMGain5.setForeground(new java.awt.Color(255, 255, 255));
        lblWMGain5.setText("20000");

        btnCloseAfternoon.setText("Cerrar");

        lblWMGain8.setForeground(new java.awt.Color(255, 255, 255));
        lblWMGain8.setText("20000");

        lblWAGain10.setForeground(new java.awt.Color(255, 255, 255));
        lblWAGain10.setText("20000");

        lblWA9.setForeground(new java.awt.Color(255, 255, 255));
        lblWA9.setText("nombre");

        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("Recaudado:");

        lblWM7.setForeground(new java.awt.Color(255, 255, 255));
        lblWM7.setText("nombre:");

        btnCloseSession.setText("Cerrar empleados");

        lblWAGain1.setForeground(new java.awt.Color(255, 255, 255));
        lblWAGain1.setText("20000");

        lblWM1.setForeground(new java.awt.Color(255, 255, 255));
        lblWM1.setText("nombre");

        btnOpenMorning.setText("Abrir");

        lblWM6.setForeground(new java.awt.Color(255, 255, 255));
        lblWM6.setText("nombre");

        lblWMGain6.setForeground(new java.awt.Color(255, 255, 255));
        lblWMGain6.setText("20000");

        btnOpenAfternoon.setText("Abrir");

        btnSeeMorning.setText("Ver");

        lblMGain.setForeground(new java.awt.Color(255, 255, 255));
        lblMGain.setText("20000");

        lblWAGain8.setForeground(new java.awt.Color(255, 255, 255));
        lblWAGain8.setText("20000");

        lblWA7.setForeground(new java.awt.Color(255, 255, 255));
        lblWA7.setText("nombre");

        lblWA10.setForeground(new java.awt.Color(255, 255, 255));
        lblWA10.setText("nombre");

        btnCloseMorning.setText("Cerrar");

        lblWMGain1.setForeground(new java.awt.Color(255, 255, 255));
        lblWMGain1.setText("20000");

        btnSeeAfternoon.setText("Ver");

        lblWMGain4.setForeground(new java.awt.Color(255, 255, 255));
        lblWMGain4.setText("20000");

        lblWM10.setForeground(new java.awt.Color(255, 255, 255));
        lblWM10.setText("nombre");

        lblAGain.setForeground(new java.awt.Color(255, 255, 255));
        lblAGain.setText("20000");

        jLabel4.setFont(new java.awt.Font("Cantarell", 0, 36)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(255, 255, 255));
        jLabel4.setText("Tarde");

        lblWAGain6.setForeground(new java.awt.Color(255, 255, 255));
        lblWAGain6.setText("20000");

        lblWM2.setForeground(new java.awt.Color(255, 255, 255));
        lblWM2.setText("nombre");

        jLabel7.setForeground(new java.awt.Color(255, 255, 255));
        jLabel7.setText("Recaudado:");

        lblWM3.setForeground(new java.awt.Color(255, 255, 255));
        lblWM3.setText("nombre");

        lblWMGain3.setForeground(new java.awt.Color(255, 255, 255));
        lblWMGain3.setText("20000");

        lblWMGain9.setForeground(new java.awt.Color(255, 255, 255));
        lblWMGain9.setText("20000");

        btnResume.setText("Resumen");

        lblBalance.setForeground(new java.awt.Color(255, 255, 255));
        lblBalance.setText("20000");

        lblWM5.setForeground(new java.awt.Color(255, 255, 255));
        lblWM5.setText("nombre");

        lblWA8.setForeground(new java.awt.Color(255, 255, 255));
        lblWA8.setText("nombre");

        jLabel3.setFont(new java.awt.Font("Cantarell", 0, 36)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setText("Mañana");

        lblWAGain5.setForeground(new java.awt.Color(255, 255, 255));
        lblWAGain5.setText("20000");

        btnUpdate.setText("Actualizar");

        lblWA4.setForeground(new java.awt.Color(255, 255, 255));
        lblWA4.setText("nombre");

        lblWAGain2.setForeground(new java.awt.Color(255, 255, 255));
        lblWAGain2.setText("20000");

        lblWMGain2.setForeground(new java.awt.Color(255, 255, 255));
        lblWMGain2.setText("20000");

        lblWM8.setForeground(new java.awt.Color(255, 255, 255));
        lblWM8.setText("nombre");

        lblWAGain9.setForeground(new java.awt.Color(255, 255, 255));
        lblWAGain9.setText("20000");

        lblWA6.setForeground(new java.awt.Color(255, 255, 255));
        lblWA6.setText("nombre");

        lblWA5.setForeground(new java.awt.Color(255, 255, 255));
        lblWA5.setText("nombre");

        lblWMGain7.setForeground(new java.awt.Color(255, 255, 255));
        lblWMGain7.setText("20000");

        lblWA1.setForeground(new java.awt.Color(255, 255, 255));
        lblWA1.setText("nombre");

        lblWAGain4.setForeground(new java.awt.Color(255, 255, 255));
        lblWAGain4.setText("20000");

        lblWMGain10.setForeground(new java.awt.Color(255, 255, 255));
        lblWMGain10.setText("20000");

        lblWAGain3.setForeground(new java.awt.Color(255, 255, 255));
        lblWAGain3.setText("20000");

        lblWM4.setForeground(new java.awt.Color(255, 255, 255));
        lblWM4.setText("nombre");

        lblWA2.setForeground(new java.awt.Color(255, 255, 255));
        lblWA2.setText("nombre");

        javax.swing.GroupLayout panelImage1Layout = new javax.swing.GroupLayout(panelImage1);
        panelImage1.setLayout(panelImage1Layout);
        panelImage1Layout.setHorizontalGroup(
            panelImage1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelImage1Layout.createSequentialGroup()
                .addGap(33, 33, 33)
                .addGroup(panelImage1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelImage1Layout.createSequentialGroup()
                        .addGroup(panelImage1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(panelImage1Layout.createSequentialGroup()
                                .addComponent(btnCloseSession, javax.swing.GroupLayout.PREFERRED_SIZE, 151, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelImage1Layout.createSequentialGroup()
                                .addGap(12, 12, 12)
                                .addGroup(panelImage1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(btnOpenMorning, javax.swing.GroupLayout.PREFERRED_SIZE, 78, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(btnSeeMorning, javax.swing.GroupLayout.PREFERRED_SIZE, 78, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(btnCloseMorning, javax.swing.GroupLayout.PREFERRED_SIZE, 78, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(18, 18, 18)
                                .addGroup(panelImage1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(panelImage1Layout.createSequentialGroup()
                                        .addGap(0, 74, Short.MAX_VALUE)
                                        .addComponent(btnResume))
                                    .addGroup(panelImage1Layout.createSequentialGroup()
                                        .addComponent(jLabel2)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(lblBalance)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))))
                        .addGroup(panelImage1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(panelImage1Layout.createSequentialGroup()
                                .addGroup(panelImage1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(btnOpenAfternoon, javax.swing.GroupLayout.PREFERRED_SIZE, 78, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(btnCloseAfternoon, javax.swing.GroupLayout.PREFERRED_SIZE, 78, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(btnSeeAfternoon, javax.swing.GroupLayout.PREFERRED_SIZE, 78, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(64, 64, 64))
                            .addComponent(btnUpdate, javax.swing.GroupLayout.Alignment.TRAILING)))
                    .addGroup(panelImage1Layout.createSequentialGroup()
                        .addGroup(panelImage1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(lblWM1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(lblWM2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(lblWM3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(lblWM4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(lblWM5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(lblWM6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(lblWM7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(lblWM8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(lblWM9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(lblWM10, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(panelImage1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(lblMGain)
                            .addComponent(lblWMGain4)
                            .addComponent(lblWMGain3)
                            .addComponent(lblWMGain2)
                            .addComponent(lblWMGain1)
                            .addGroup(panelImage1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(lblWMGain7)
                                .addComponent(lblWMGain6)
                                .addComponent(lblWMGain5)
                                .addComponent(lblWMGain8)
                                .addComponent(lblWMGain9)
                                .addComponent(lblWMGain10)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(panelImage1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(lblWA1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(lblWA2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(lblWA3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(lblWA4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(lblWA5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(lblWA6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(lblWA7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(lblWA8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(lblWA9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(lblWA10, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(panelImage1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(panelImage1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(lblWAGain1, javax.swing.GroupLayout.Alignment.TRAILING)
                                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelImage1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(lblWAGain3)
                                    .addComponent(lblWAGain2))
                                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelImage1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(lblWAGain5)
                                    .addComponent(lblWAGain4))
                                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelImage1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(lblWAGain7)
                                    .addComponent(lblWAGain6))
                                .addComponent(lblWAGain8, javax.swing.GroupLayout.Alignment.TRAILING)
                                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelImage1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(lblWAGain10)
                                    .addComponent(lblWAGain9)))
                            .addComponent(lblAGain))
                        .addGap(25, 25, 25))
                    .addGroup(panelImage1Layout.createSequentialGroup()
                        .addComponent(jLabel3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel4)
                        .addGap(54, 54, 54))))
        );
        panelImage1Layout.setVerticalGroup(
            panelImage1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelImage1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelImage1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel4, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel3))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(panelImage1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelImage1Layout.createSequentialGroup()
                        .addComponent(btnOpenMorning)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnCloseMorning))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelImage1Layout.createSequentialGroup()
                        .addComponent(btnOpenAfternoon)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnCloseAfternoon))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelImage1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jLabel2)
                        .addComponent(lblBalance)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelImage1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnSeeAfternoon)
                    .addComponent(btnSeeMorning))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(panelImage1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1)
                    .addComponent(lblMGain)
                    .addComponent(jLabel7)
                    .addComponent(lblAGain))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelImage1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelImage1Layout.createSequentialGroup()
                        .addComponent(lblWM1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lblWM2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lblWM3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lblWM4)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lblWM5)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lblWM6)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lblWM7)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lblWM8)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lblWM9)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lblWM10))
                    .addGroup(panelImage1Layout.createSequentialGroup()
                        .addComponent(lblWAGain1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lblWAGain2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lblWAGain3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lblWAGain4)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lblWAGain5)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lblWAGain6)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lblWAGain7)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lblWAGain8)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lblWAGain9)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lblWAGain10))
                    .addGroup(panelImage1Layout.createSequentialGroup()
                        .addComponent(lblWA1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lblWA2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lblWA3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lblWA4)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lblWA5)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lblWA6)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lblWA7)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lblWA8)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lblWA9)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lblWA10))
                    .addGroup(panelImage1Layout.createSequentialGroup()
                        .addComponent(lblWMGain1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lblWMGain2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lblWMGain3, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGroup(panelImage1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(panelImage1Layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(lblWMGain4)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(lblWMGain5))
                            .addGroup(panelImage1Layout.createSequentialGroup()
                                .addGap(50, 50, 50)
                                .addComponent(lblWMGain6)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(lblWMGain7)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(lblWMGain8)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(lblWMGain9)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(lblWMGain10)))))
                .addGap(18, 18, 18)
                .addGroup(panelImage1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelImage1Layout.createSequentialGroup()
                        .addComponent(btnResume, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(33, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelImage1Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addGroup(panelImage1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btnUpdate)
                            .addComponent(btnCloseSession)))))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(panelImage1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(panelImage1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCloseAfternoon;
    private javax.swing.JButton btnCloseMorning;
    private javax.swing.JButton btnCloseSession;
    private javax.swing.JButton btnOpenAfternoon;
    private javax.swing.JButton btnOpenMorning;
    private javax.swing.JButton btnResume;
    private javax.swing.JButton btnSeeAfternoon;
    private javax.swing.JButton btnSeeMorning;
    private javax.swing.JButton btnUpdate;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel lblAGain;
    private javax.swing.JLabel lblBalance;
    private javax.swing.JLabel lblMGain;
    private javax.swing.JLabel lblWA1;
    private javax.swing.JLabel lblWA10;
    private javax.swing.JLabel lblWA2;
    private javax.swing.JLabel lblWA3;
    private javax.swing.JLabel lblWA4;
    private javax.swing.JLabel lblWA5;
    private javax.swing.JLabel lblWA6;
    private javax.swing.JLabel lblWA7;
    private javax.swing.JLabel lblWA8;
    private javax.swing.JLabel lblWA9;
    private javax.swing.JLabel lblWAGain1;
    private javax.swing.JLabel lblWAGain10;
    private javax.swing.JLabel lblWAGain2;
    private javax.swing.JLabel lblWAGain3;
    private javax.swing.JLabel lblWAGain4;
    private javax.swing.JLabel lblWAGain5;
    private javax.swing.JLabel lblWAGain6;
    private javax.swing.JLabel lblWAGain7;
    private javax.swing.JLabel lblWAGain8;
    private javax.swing.JLabel lblWAGain9;
    private javax.swing.JLabel lblWM1;
    private javax.swing.JLabel lblWM10;
    private javax.swing.JLabel lblWM2;
    private javax.swing.JLabel lblWM3;
    private javax.swing.JLabel lblWM4;
    private javax.swing.JLabel lblWM5;
    private javax.swing.JLabel lblWM6;
    private javax.swing.JLabel lblWM7;
    private javax.swing.JLabel lblWM8;
    private javax.swing.JLabel lblWM9;
    private javax.swing.JLabel lblWMGain1;
    private javax.swing.JLabel lblWMGain10;
    private javax.swing.JLabel lblWMGain2;
    private javax.swing.JLabel lblWMGain3;
    private javax.swing.JLabel lblWMGain4;
    private javax.swing.JLabel lblWMGain5;
    private javax.swing.JLabel lblWMGain6;
    private javax.swing.JLabel lblWMGain7;
    private javax.swing.JLabel lblWMGain8;
    private javax.swing.JLabel lblWMGain9;
    private org.edisoncor.gui.panel.PanelImage panelImage1;
    // End of variables declaration//GEN-END:variables
}
