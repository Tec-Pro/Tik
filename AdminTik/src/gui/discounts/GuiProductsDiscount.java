/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui.discounts;

import controllers.ControllerGuiCRUDUser;
import interfaces.InterfaceDiscount;
import interfaces.InterfacePresence;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.table.DefaultTableModel;
import utils.InterfaceName;

/**
 *
 * @author nico
 */
public class GuiProductsDiscount extends javax.swing.JDialog {

    private final InterfaceDiscount discounts;

    /**
     * Creates new form GuiProductsDiscount
     */
    public GuiProductsDiscount(java.awt.Frame parent, boolean modal, List<Map> waiters) throws RemoteException, NotBoundException {
        super(parent, modal);
        initComponents();
        discounts = (InterfaceDiscount) InterfaceName.registry.lookup(InterfaceName.CRUDDiscounts);
        boxWaiters.removeAllItems();
        boxWaiters.addItem("Todos los descuentos");
        for (Map s : waiters) {
            boxWaiters.addItem(s.get("id") + "-" + s.get("name"));
        }
        boxWaiters.setSelectedIndex(0);
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.DAY_OF_MONTH, 1);
        dateFrom.setCalendar(cal);
        cal.set(Calendar.DAY_OF_MONTH, cal.getActualMaximum(Calendar.DAY_OF_MONTH));
        dateTo.setCalendar(cal);
        dateFrom.addPropertyChangeListener(new PropertyChangeListener() {
            @Override
            public void propertyChange(PropertyChangeEvent evt) {
                try {
                    Date dateAuxTo = dateTo.getDate();
                    Calendar calAux = Calendar.getInstance();
                    calAux.setTime(dateAuxTo);
                    calAux.add(Calendar.DAY_OF_MONTH, 1);
                    loadDiscounts(getIdUser(), utils.Dates.dateToMySQLDate(dateFrom.getDate(), false), utils.Dates.dateToMySQLDate(calAux.getTime(), false));
                } catch (RemoteException ex) {
                    Logger.getLogger(ControllerGuiCRUDUser.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
        dateTo.addPropertyChangeListener(new PropertyChangeListener() {
            @Override
            public void propertyChange(PropertyChangeEvent evt) {
                try {
                    Date dateAuxTo = dateTo.getDate();
                    Calendar calAux = Calendar.getInstance();
                    calAux.setTime(dateAuxTo);
                    calAux.add(Calendar.DAY_OF_MONTH, 1);
                    loadDiscounts(getIdUser(), utils.Dates.dateToMySQLDate(dateFrom.getDate(), false), utils.Dates.dateToMySQLDate(calAux.getTime(), false));
                } catch (RemoteException ex) {
                    Logger.getLogger(ControllerGuiCRUDUser.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
        try {
            Date dateAuxTo = dateTo.getDate();
            Calendar calAux = Calendar.getInstance();
            calAux.setTime(dateAuxTo);
            calAux.add(Calendar.DAY_OF_MONTH, 1);
            loadDiscounts(getIdUser(), utils.Dates.dateToMySQLDate(dateFrom.getDate(), false), utils.Dates.dateToMySQLDate(calAux.getTime(), false));
        } catch (RemoteException ex) {
            Logger.getLogger(ControllerGuiCRUDUser.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void loadDiscounts(int user_id, String from, String to) throws RemoteException {
        List<Map> presences = discounts.getDiscounts(user_id, from, to);
        Iterator<Map> it = presences.iterator();
        DefaultTableModel dftmodel = (DefaultTableModel) tableDiscounts.getModel();
        dftmodel.setRowCount(0);
        while (it.hasNext()) {
            Map<String, Object> discount = it.next();
            String createdAt = (String) discount.get("created_at");
            String name = (String) discount.get("name");
            int i = 0;
            while (i < tableDiscounts.getRowCount() && !(name.equals((String) tableDiscounts.getValueAt(i, 0)) && createdAt.equals((String) tableDiscounts.getValueAt(i, 1)))) {
                //ciclo hasta encontrar uno o salir del ciclo
                i++;
            }
            //encontré uno que es el mismo producto y fecha, le sumo la cantidad
            if (i < tableDiscounts.getRowCount()) {
                tableDiscounts.setValueAt((int) tableDiscounts.getValueAt(i, 2) + 1, i, 2);
            } else {//debo crearlo
                Object rowDtm[] = new Object[3];
                rowDtm[0] = name;
                rowDtm[1] = createdAt;
                rowDtm[2] = 1;
                dftmodel.addRow(rowDtm);
            }
        }
    }

    private int getIdUser() {
        if (boxWaiters.getSelectedIndex() > 0) {
            return Integer.valueOf(((String) boxWaiters.getSelectedItem()).split("-")[0]);
        } else {
            return -1;
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

        dateFrom = new com.toedter.calendar.JDateChooser();
        jLabel1 = new javax.swing.JLabel();
        dateTo = new com.toedter.calendar.JDateChooser();
        jLabel2 = new javax.swing.JLabel();
        boxWaiters = new javax.swing.JComboBox();
        jScrollPane1 = new javax.swing.JScrollPane();
        tableDiscounts = new javax.swing.JTable();
        jLabel3 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        dateFrom.setToolTipText("");
        dateFrom.setDateFormatString("dd-MM-yyyy");

        jLabel1.setText("Desde");

        dateTo.setDateFormatString("dd-MM-yyyy");

        jLabel2.setText("Hasta");

        boxWaiters.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                boxWaitersActionPerformed(evt);
            }
        });
        boxWaiters.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                boxWaitersPropertyChange(evt);
            }
        });

        tableDiscounts.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null}
            },
            new String [] {
                "Producto", "Fecha", "Cantidad"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.Integer.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane1.setViewportView(tableDiscounts);

        jLabel3.setText("Mozo");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1)
            .addGroup(layout.createSequentialGroup()
                .addGap(119, 119, 119)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1)
                    .addComponent(jLabel3))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(dateFrom, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(40, 40, 40)
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(dateTo, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(boxWaiters, javax.swing.GroupLayout.PREFERRED_SIZE, 375, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(174, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(dateFrom, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1)
                    .addComponent(dateTo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(boxWaiters, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3))
                .addGap(42, 42, 42)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 201, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void boxWaitersPropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_boxWaitersPropertyChange

    }//GEN-LAST:event_boxWaitersPropertyChange

    private void boxWaitersActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_boxWaitersActionPerformed
        try {
            if (dateFrom.getDate() != null && dateTo.getDate() != null) {
                Date dateAuxTo = dateTo.getDate();
                Calendar calAux = Calendar.getInstance();
                calAux.setTime(dateAuxTo);
                calAux.add(Calendar.DAY_OF_MONTH, 1);
                loadDiscounts(getIdUser(), utils.Dates.dateToMySQLDate(dateFrom.getDate(), false), utils.Dates.dateToMySQLDate(calAux.getTime(), false));
            }
        } catch (RemoteException ex) {
            Logger.getLogger(GuiProductsDiscount.class.getName()).log(Level.SEVERE, null, ex);
        }
     }//GEN-LAST:event_boxWaitersActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox boxWaiters;
    private com.toedter.calendar.JDateChooser dateFrom;
    private com.toedter.calendar.JDateChooser dateTo;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tableDiscounts;
    // End of variables declaration//GEN-END:variables
}
