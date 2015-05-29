/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import gui.order.GuiKitchenOrderDetails;
import interfaces.InterfaceOrder;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.sql.Timestamp;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import utils.InterfaceName;
import utils.Pair;

/**
 *
 * @author eze
 */
public class ControllerGuiOrderDetails implements ActionListener {

    private static InterfaceOrder crudOrder;
    private GuiKitchenOrderDetails guiOrderDetails;
    private Pair<Map<String,Object>,List<Map>> order;
    private Boolean removeThisPane;

    public ControllerGuiOrderDetails(GuiKitchenOrderDetails guiOrderD, Pair<Map<String,Object>,List<Map>> order) throws RemoteException, NotBoundException {
        crudOrder = (InterfaceOrder) InterfaceName.registry.lookup(InterfaceName.CRUDOrder);
        guiOrderDetails = guiOrderD;
        guiOrderDetails.setActionListener(this);
        this.order = order;
        this.removeThisPane = null;
        //inicio los componentes de la gui
        initComponents();
        //escucho cambios en la tabla
        guiOrderDetails.setTableModelListener(new TableModelListener() {
            @Override
            public void tableChanged(TableModelEvent e) { // Cuando un valor de la tabla cambie
                boolean modify = false;
                int i = 0;
                while (i < guiOrderDetails.getDefaultTableModelOrderProducts().getRowCount() && !modify) { // Si un valor esta en true, habilito el boton de cerrar el pedido
                    if ((boolean) guiOrderDetails.getDefaultTableModelOrderProducts().getValueAt(i, 3) == true) {
                        modify = true;
                    } else {
                        i++;
                    }
                }
                guiOrderDetails.setModified(modify);
                guiOrderDetails.getBtnSendOrderDone().setEnabled(modify);
            }
        });
        
    }
    
    private void initComponents(){
        //cargo la tabla
        for (Map<String, Object> m : this.order.second()) { // para cada orderProduct
            Object rowDtm[] = new Object[4]; // New row
            rowDtm[0] = m.get("id");
            rowDtm[1] = m.get("name");
            rowDtm[2] = m.get("quantity");
            rowDtm[3] = false;
            guiOrderDetails.getDefaultTableModelOrderProducts().addRow(rowDtm);
        }
        //tiempo del pedido
        Timestamp date = Timestamp.valueOf("1990-01-01 01:01:01");//inicio la fecha con un valor minimo
        for (Map m : order.second()) {
            //calculo la hora del pedido en base al ultimo producto añadido al mismo
            if (date.before(Timestamp.valueOf(m.get("updated_at").toString()))) {
                date = Timestamp.valueOf(m.get("updated_at").toString());
            }
        }
        guiOrderDetails.getLabelOrderArrivalTime().setText(date.toString());
        //descripcion del pedido
        guiOrderDetails.getTxtOrderDescription().setText(order.first().get("description").toString());
        //por defecto seteo el boton de enviar orden en falso
        guiOrderDetails.getBtnSendOrderDone().setEnabled(false);
        //id del pedido
        guiOrderDetails.setOrderID(Integer.parseInt(order.first().get("id").toString()));
    }
    
    public Boolean removeThisPane(){
        return this.removeThisPane;
    }

    public Integer getOrderId() {
        return Integer.parseInt(order.first().get("id").toString());
    }
    
    @Override
    public void actionPerformed(ActionEvent ae) {
        if (ae.getSource().equals(guiOrderDetails.getBtnCheckAll())) { // If one value is false, I set them all with true. If they're all true, I set them all with false.
            boolean modify = false;
            int i = 0;
            while (i < guiOrderDetails.getDefaultTableModelOrderProducts().getRowCount() && !modify) {
                if ((boolean) guiOrderDetails.getDefaultTableModelOrderProducts().getValueAt(i, 3) == false) {
                    modify = true;
                } else {
                    i++;
                }
            }
            for (i = 0; i < guiOrderDetails.getDefaultTableModelOrderProducts().getRowCount(); i++) {
                guiOrderDetails.getDefaultTableModelOrderProducts().setValueAt(modify, i, 3);
            }
        }
        
        if (ae.getSource().equals(guiOrderDetails.getBtnSendOrderDone())) { // Send the Order
            List<Integer> list = new LinkedList();
            int i = 0;
            while (i < guiOrderDetails.getDefaultTableModelOrderProducts().getRowCount()) { // Agrego todos los productos tildados como realizados
                if ((boolean) guiOrderDetails.getDefaultTableModelOrderProducts().getValueAt(i, 3) == true) {
                    int id = (Integer) guiOrderDetails.getDefaultTableModelOrderProducts().getValueAt(i, 0);
                    list.add(id);
                }
                i++;
            }
            try {
                crudOrder.updateOrdersReadyProducts(guiOrderDetails.getOrderID(), list);
            } catch (RemoteException ex) {
                Logger.getLogger(ControllerGuiKitchenMain.class.getName()).log(Level.SEVERE, null, ex);
            }
            if (list.size() == guiOrderDetails.getDefaultTableModelOrderProducts().getRowCount()) { //si la lista de productos hechos es igual a la cantidad de productos en la tabla
                this.removeThisPane = true;
                guiOrderDetails.dispose();
            } else {
                this.removeThisPane = false;
                guiOrderDetails.dispose();
            }
            
        }
        
    }
    
}
