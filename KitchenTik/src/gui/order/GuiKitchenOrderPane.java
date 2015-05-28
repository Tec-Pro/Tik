/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui.order;

import java.awt.Color;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.Map;
import javax.swing.JTextArea;
import javax.swing.Timer;
import utils.Pair;

/**
 *
 * @author nico
 */
public class GuiKitchenOrderPane extends javax.swing.JPanel {

    private Pair<Map<String, Object>, List<Map>> order;
    boolean modified = false;
    private Timer timer;
    private boolean activeTimer;

    /**
     * Creates new form orderPane
     *
     */
    public GuiKitchenOrderPane() {
        initComponents();
        activeTimer = false;
    }

    /**
     *
     * @param orderName nombre del pedido
     * @param desc descripcion del pedido
     * @param date hora del pedido
     * @param ordId id del pedido
     */
    public GuiKitchenOrderPane(String orderName, String desc, String date, Pair<Map<String, Object>, List<Map>> order) {
        initComponents();
        lblOrderNumber.setText(orderName);
        lblTimeOrderArrival.setText(date);
        txtOrderDescription.setText(desc);
        this.order = order;
        activeTimer = false;
    }

    /**
     * setea el color del panel 0 blanco, 1 verde, 2 amarillo, 3 rojo
     *
     * @param color
     */
    public void setColor(int color) {
        switch (color) {
            case 0:
                setBackground(Color.WHITE);
                txtOrderDescription.setBackground(Color.WHITE);
                break;
            case 1:
                setBackground(Color.GREEN);
                txtOrderDescription.setBackground(Color.GREEN);
                break;
            case 2:
                setBackground(Color.YELLOW);
                txtOrderDescription.setBackground(Color.YELLOW);
                break;
            case 3:
                setBackground(Color.RED);
                txtOrderDescription.setBackground(Color.RED);
                break;
        }
    }
    
    /**
     *
     * @return 0 si el color es Blanco,
     *         1 si el color es Verde,
     *         2 si el color es Amarillo,
     *         3 si el color es Rojo,
     *         -1 si no es ninguno de los anteriores.
     */
    public int getColor() {
        Color background = getBackground();
        if(background.equals(Color.WHITE)){
            return 0;
        }else{
            if(background.equals(Color.GREEN)){
                return 1;
            }else{
                if(background.equals(Color.YELLOW)){
                    return 2;
                }else{
                    if(background.equals(Color.RED)){
                        return 3;
                    }else{
                        return -1;
                    }
                }
            }
        }   
    }
    
    
    /**
     * Metodo que inicia un timer para ejecutar una acción cada cierto tiempo.
     * @param lis listener que invoca la acción a ejecutar
     * @param start tiempo de comienzo medido en milisegundos
     * @param delay tiempo entre eventos.
     */
    public void setTimer(ActionListener lis, int start,int delay){
        timer = new Timer(start,lis);
        timer.setDelay(delay);
        activeTimer = true;
        timer.start();
    }

    /**
     * Metodo que finaliza la accion ejecutada por el timer
     */
    public void stopTimer(){
        if (timer != null){
            timer.stop();
            activeTimer = false;
        }
    }
    
    /**
     * Devuelve el label que debe mostrar el número del pedido.
     *
     * @return the orderNumber
     */
    public javax.swing.JLabel getLblOrderNumber() {
        return lblOrderNumber;
    }

    /**
     * Devuelve el label que debe mostrar el tiempo de llegada del pedido.
     *
     * @return the timeOrderArrival
     */
    public javax.swing.JLabel getLblTimeOrderArrival() {
        return lblTimeOrderArrival;
    }

    /**
     * Devuelve el text area que contiene la descripción del pedido.
     *
     * @return the txtOrderDescription
     */
    public JTextArea getTxtOrderDescription() {
        return txtOrderDescription;
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
        txtOrderDescription = new javax.swing.JTextArea();
        jLabel1 = new javax.swing.JLabel();
        lblOrderNumber = new javax.swing.JLabel();
        lblTimeOrderArrival = new javax.swing.JLabel();
        btnOrderReady = new javax.swing.JButton();
        btnPostpone = new javax.swing.JButton();

        setBackground(new java.awt.Color(253, 216, 47));
        setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        setMaximumSize(new java.awt.Dimension(230, 230));
        setMinimumSize(new java.awt.Dimension(230, 230));
        setPreferredSize(new java.awt.Dimension(230, 230));

        txtOrderDescription.setEditable(false);
        txtOrderDescription.setBackground(new java.awt.Color(253, 216, 47));
        txtOrderDescription.setColumns(14);
        txtOrderDescription.setRows(5);
        jScrollPane1.setViewportView(txtOrderDescription);

        jLabel1.setFont(new java.awt.Font("Cantarell", 0, 24)); // NOI18N
        jLabel1.setText("N°");

        lblOrderNumber.setFont(new java.awt.Font("Cantarell", 0, 18)); // NOI18N
        lblOrderNumber.setText("n° de pedido");

        lblTimeOrderArrival.setFont(new java.awt.Font("Cantarell", 0, 15)); // NOI18N
        lblTimeOrderArrival.setText("Hora de llegada del pedido");

        btnOrderReady.setFont(new java.awt.Font("Cantarell", 0, 14)); // NOI18N
        btnOrderReady.setText("Pedido Listo");

        btnPostpone.setFont(new java.awt.Font("Cantarell", 0, 14)); // NOI18N
        btnPostpone.setText("Posponer");
        btnPostpone.setEnabled(false);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jScrollPane1)
                    .addComponent(lblTimeOrderArrival, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lblOrderNumber, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                        .addComponent(btnOrderReady, javax.swing.GroupLayout.PREFERRED_SIZE, 103, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnPostpone, javax.swing.GroupLayout.PREFERRED_SIZE, 95, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(lblOrderNumber))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblTimeOrderArrival)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 111, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(btnPostpone, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnOrderReady, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(9, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnOrderReady;
    private javax.swing.JButton btnPostpone;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblOrderNumber;
    private javax.swing.JLabel lblTimeOrderArrival;
    private javax.swing.JTextArea txtOrderDescription;
    // End of variables declaration//GEN-END:variables

    /**
     *
     * @return true si el timer esta activo
     */
    public boolean isActiveTimer() {
        return activeTimer;
    }

    /**
     * @return the orderId
     */
    public Integer getOrderId() {
        return (Integer) getOrder().first().get("user_id");
    }

    /**
     * @return the btnOrderReady
     */
    public javax.swing.JButton getBtnOrderReady() {
        return btnOrderReady;
    }

    /**
     * @return the btnPostpone
     */
    public javax.swing.JButton getBtnPostpone() {
        return btnPostpone;
    }
    
    /**
     *
     * @param lis
     */
    public void setActionListener(ActionListener lis){
        this.btnPostpone.addActionListener(lis);
        this.btnOrderReady.addActionListener(lis);
    }

    /**
     * @return the modified
     */
    public boolean isModified() {
        return modified;
    }

    /**
     * @param modified the modified to set
     */
    public void setModified(boolean modified) {
        this.modified = modified;
        btnOrderReady.setEnabled(modified);
    }

    /**
     * @return the order
     */
    public Pair<Map<String, Object>, List<Map>> getOrder() {
        return order;
    }

    /**
     * @param order the order to set
     */
    public void setOrder(Pair<Map<String, Object>, List<Map>> order) {
        this.order = order;
    }
}
