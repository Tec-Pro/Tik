/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import java.awt.Color;
import java.awt.event.MouseListener;
import java.util.List;
import java.util.Map;
import javax.swing.JLabel;
import javax.swing.JTextArea;

/**
 *
 * @author nico
 */
public class GuiMenuDetail extends javax.swing.JPanel {

    private int idOrder;
    private int idWaiter;

    /**
     * Creates new form GuiMenuDetail
     */
    public GuiMenuDetail() {
        initComponents();
    }

    /**
     * setea el color del panel 0 blanco, 1 verde, 2 amarillo, 3 rojo
     *
     * @param color
     */
    public void setColor(int color) {
        switch (color) {
            case 0:
                setBackground(new Color(0,0,0,65));
                txtDetail.setBackground(new Color(0,0,0,65));
                break;
            case 1:
                setBackground(new java.awt.Color(25, 169, 42));
                txtDetail.setBackground(new java.awt.Color(25, 169, 42));
                break;
            case 2:
                setBackground(new java.awt.Color(253, 216, 47));
                txtDetail.setBackground(new java.awt.Color(253, 216, 47));
                break;
            case 3:
                setBackground(new java.awt.Color(251, 28, 4));
                txtDetail.setBackground(new java.awt.Color(251, 28, 4));
                break;
        }
    }

    public void setOrder(Map order, String detail) {
        lblId.setText("N° " + order.get("order_number").toString());
        txtDetail.setText(detail);
        idOrder = (int) order.get("id");
        idWaiter = (int) order.get("user_id");
    }
    
    public void setBelated(boolean b){
        if (b)
            lblBelated.setText("DEMORADO");
        else
            lblBelated.setText("");
    }
    /**
     * retorna el id de la orden que posee este pedido
     *
     * @return idOrder
     */
    public int getIdOrder() {
        return idOrder;
    }

    public JTextArea getTxtDetail() {
        return txtDetail;
    }

    public int getIdWaiter() {
        return idWaiter;
    }

    public JLabel getLblCommited() {
        return lblCommited;
    }

    public JLabel getLblDone() {
        return lblDone;
    }
    
    

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        lblId = new javax.swing.JLabel();
        lblTime = new javax.swing.JLabel();
        lblBelated = new javax.swing.JLabel();
        lblDone = new javax.swing.JLabel();
        lblCommited = new javax.swing.JLabel();
        txtDetail = new javax.swing.JTextArea();

        setBackground(new java.awt.Color(253, 216, 47));
        setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        setMaximumSize(new java.awt.Dimension(230, 230));
        setMinimumSize(new java.awt.Dimension(230, 230));
        setPreferredSize(new java.awt.Dimension(230, 230));

        lblId.setFont(new java.awt.Font("Cantarell", 1, 24)); // NOI18N
        lblId.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        lblId.setText("N° 5 -");

        lblTime.setFont(new java.awt.Font("Cantarell", 1, 24)); // NOI18N
        lblTime.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblTime.setText("19:25");

        lblBelated.setFont(new java.awt.Font("Cantarell", 1, 18)); // NOI18N
        lblBelated.setText("DEMORADO");

        lblDone.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/close_icon.png"))); // NOI18N

        lblCommited.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/done_icon.png"))); // NOI18N

        txtDetail.setEditable(false);
        txtDetail.setBackground(new java.awt.Color(253, 216, 47));
        txtDetail.setColumns(14);
        txtDetail.setRows(5);
        txtDetail.setAutoscrolls(false);
        txtDetail.setOpaque(false);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(lblBelated, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(54, 54, 54)
                .addComponent(lblCommited, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(lblId, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lblTime)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lblDone))
                    .addComponent(txtDetail, javax.swing.GroupLayout.PREFERRED_SIZE, 222, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblDone)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(lblId, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(lblTime, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(2, 2, 2)
                .addComponent(txtDetail, javax.swing.GroupLayout.PREFERRED_SIZE, 145, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblBelated, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lblCommited)))
                .addContainerGap(7, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel lblBelated;
    private javax.swing.JLabel lblCommited;
    private javax.swing.JLabel lblDone;
    private javax.swing.JLabel lblId;
    private javax.swing.JLabel lblTime;
    private javax.swing.JTextArea txtDetail;
    // End of variables declaration//GEN-END:variables
}
