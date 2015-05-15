/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import gui.login.GuiLogin;
import gui.login.GuiOnlineUsers;
import gui.main.GuiKitchenMain;
import gui.order.GuiKitchenOrderDetails;
import interfaces.InterfaceOrder;
import interfaces.InterfacePresence;
import interfaces.InterfaceUser;
import interfaces.InterfaceFproduct;
import java.awt.Color;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import java.awt.event.MouseEvent;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import utils.Config;
import utils.InterfaceName;
import java.util.Iterator;

/**
 *
 * @author eze
 */
public class ControllerGuiKitchenMain implements ActionListener {

    private static InterfaceOrder crudOrder;
    private static LinkedList<Integer> orderList;
    private Set<Map> online; // set con los cocineros online
    private InterfacePresence crudPresence;
    private InterfaceUser crudUser;
    private static InterfaceFproduct crudFProduct;
    private GuiLogin guiLogin;
    private static GuiKitchenOrderDetails guiOrderDetails;
    private static GuiKitchenMain guiKitchenMain;
    private static DefaultTableModel dtmOrderDetails;

    /**
     *
     * @throws NotBoundException
     * @throws MalformedURLException
     * @throws RemoteException
     */
    public ControllerGuiKitchenMain() throws NotBoundException, MalformedURLException, RemoteException {
        orderList = new LinkedList<>();
        crudOrder = (InterfaceOrder) Naming.lookup("//" + Config.ip + "/" + InterfaceName.CRUDOrder);
        crudPresence = (InterfacePresence) Naming.lookup("//" + Config.ip + "/" + InterfaceName.CRUDPresence);
        crudUser = (InterfaceUser) Naming.lookup("//" + Config.ip + "/" + InterfaceName.CRUDUser);
        crudFProduct = (InterfaceFproduct) Naming.lookup("//" + Config.ip + "/" + InterfaceName.CRUDFproduct);
        online = new HashSet<Map>();
        for (Map m : crudPresence.getCooks()) {
            online.add(m);
        }
        guiOrderDetails = new GuiKitchenOrderDetails(guiKitchenMain, false);
        guiOrderDetails.setVisible(false);

        dtmOrderDetails = guiOrderDetails.getDefaultTableModelOrderProducts();

        guiKitchenMain = new GuiKitchenMain();
        guiKitchenMain.setVisible(true);
        guiKitchenMain.setActionListener(this);
        guiOrderDetails.setActionListener(this);

        guiLogin = null;
    }

    private static void loadGuiOrderDetails(int order, String desc, String arrival) throws RemoteException {
        guiOrderDetails.getLabelOrderArrivalTime().setText(arrival);
        guiOrderDetails.getTxtOrderDescription().setText(desc);
        guiOrderDetails.getBtnSendOrderDone().setEnabled(false);
        guiOrderDetails.setOrderID(order);
        dtmOrderDetails.setRowCount(0);
        List<Map> map = crudOrder.getOrderProducts(order); // I obtain all the products
        for (Map<String, Object> m : map) { // For each product
            if ((boolean) m.get("done") == false) { // If the product isn't done
                int prodID = (int) m.get("fproduct_id"); // I obtain the product ID
                Map<String, Object> prod = crudFProduct.getFproduct(prodID); // I obtain the product (importantly, it's name and if it belongs)
                String cook = (String) prod.get("belong");
                if (cook.equals("Cocina")) { // If the product is for the Kitchen
                    Object rowDtm[] = new Object[4]; // New row
                    rowDtm[0] = m.get("id");
                    rowDtm[1] = prod.get("name");
                    rowDtm[2] = m.get("quantity");
                    rowDtm[3] = false;
                    dtmOrderDetails.addRow(rowDtm);
                }
            }
        }
        guiOrderDetails.setTableModelListener(new TableModelListener() {
            public void tableChanged(TableModelEvent e) { // When a value in the table changes, I make configurations
                boolean modify = false;
                int i = 0;
                while (i < dtmOrderDetails.getRowCount() && !modify) { // If one value is true, It's able to send and order and check before closing the diag
                    if ((boolean) dtmOrderDetails.getValueAt(i, 3) == true) {
                        modify = true;
                    } else {
                        i++;
                    }
                }
                guiOrderDetails.setModified(modify);
                guiOrderDetails.getBtnSendOrderDone().setEnabled(modify);
            }
        });
        guiOrderDetails.setVisible(true);
        guiOrderDetails.toFront();
        guiOrderDetails.setModal(true);
    }

    private static boolean itBelongsKitchen(int id) throws RemoteException { // There was either no products to make, or they've all been made allready
        boolean itBelongs = false;
        Map<String, Object> order = crudOrder.getOrder(id);
        List<Map> orderProducts = crudOrder.getOrderProducts(id);
        for (Map<String, Object> m : orderProducts) { // For each product 
            int prodID = (int) m.get("fproduct_id"); // I obtain the product ID
            Map<String, Object> prod = crudFProduct.getFproduct(prodID); // I obtain the product (importantly, where it belongs)
            String cook = (String) prod.get("belong");
            if (cook.equals("Cocina")) { // Does it belong to cocina?
                itBelongs = true;
            }
        }
        return itBelongs;
    }

    private static boolean noMoreToCook(int id) throws RemoteException {
        boolean noMore = true;
        Map<String, Object> order = crudOrder.getOrder(id);
        List<Map> orderProducts = crudOrder.getOrderProducts(id);
        for (Map<String, Object> m : orderProducts) { // For each product 
            if (((boolean) m.get("done") == false)) {
                noMore = false;
            }
        }
        return noMore;
    }

    /**
     * Metodo invocado por el servidor cuando se crea un nuevo Pedido/Orden en
     * el modulo Waiter. Este metodo cargara el pedido creado en la gui
     * correspondiente
     *
     * @param id del pedido
     * @throws RemoteException
     */
    public static void addOrder(final int id) throws RemoteException {
        /* "order" es el Map de un pedido con la siguiente estructura:
         * {order_number, id, user_id, closed=boolean, description}*/
        /* "orderProducts" es una lista de Maps, de los productos finales que
         * contiene el pedido "order", cada Map tiene la siguiente estructura:
         * {id, done=boolean, issued=boolean, fproduct_id, quantity, order_id, commited=boolean}*/
        //Aca debe cargarse el pedido en la gui y/o en la lista de pedidos
        //dependiendo de como sea implementado el controlador
        //RECORDAR QUE EN LA GUI SOLO DEBEN CARGARSE LOS PRODUCTOS CORRESPONDIENTES A COCINA(FILTRAR LA LISTA)
        Map<String, Object> order = crudOrder.getOrder(id);
        if (itBelongsKitchen(id)) {
            final DateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
            final Date date = new Date();
            final String desc;
            if ((String) order.get("description") == null) {
                desc = "";
            } else {
                desc = (String) order.get("description");
            }
            guiKitchenMain.addElementToOrdersGrid(Integer.toString(id), desc, dateFormat.format(date),
                    new java.awt.event.MouseAdapter() {
                        public void mouseClicked(MouseEvent e) {
                            if (e.getClickCount() == 2) {
                                try {
                                    loadGuiOrderDetails(id, desc, dateFormat.format(date));
                                } catch (RemoteException ex) {
                                    Logger.getLogger(ControllerGuiKitchenMain.class.getName()).log(Level.SEVERE, null, ex);
                                }
                            }
                        }
                    });
            guiKitchenMain.setOrderColor(guiKitchenMain.getOrdersPanel().getComponentCount() - 1, new Color(255, 0, 0));
            orderList.add(id);
        }
    }

    /**
     * Metodo invocado por el servidor cuando se actualiza informacion sobre
     * Pedido/Orden en el modulo Waiter. Este metodo actualizara el pedido
     * modificado en la gui correspondiente
     *
     * @param id del pedido
     * @throws RemoteException
     */
    public static void updatedOrder(int id) throws RemoteException {
        /* "order" es el Map de un pedido con la siguiente estructura:
         * {order_number, id, user_id, closed=boolean, description}*/
        /* "orderProducts" es una lista de Maps, de los productos finales que
         * contiene el pedido "order", cada Map tiene la siguiente estructura:
         * {id, done=boolean, issued=boolean, fproduct_id, quantity, order_id, commited=boolean}*/
        //List<Map> orderProducts = crudOrder.getOrderProducts(id);
        //Aca debe actualizarse el pedido en la gui y/o en la lista de pedidos
        //dependiendo de como sea implementado el controlador
        //RECORDAR QUE EN LA GUI SOLO DEBEN CARGARSE LOS PRODUCTOS CORRESPONDIENTES A COCINA(FILTRAR LA LISTA)
        Map<String, Object> order = crudOrder.getOrder(id);
        int size = guiKitchenMain.getOrdersPanel().getComponentCount(); // the amount of orders in the order panel
        int i = 0;
        boolean check = false;
        while (i < size || check) {
            if (orderList.get(i) == id) {
                check = true;
            } else {
                i++;
            }
        }
        guiKitchenMain.updateElementOfOrdersGrid(i, (String) order.get("description"));
        guiKitchenMain.setOrderColor(i, new Color(255, 0, 0));
    }

    /**
     *
     * @param ae
     */
    @Override
    public void actionPerformed(ActionEvent ae) {
        if (ae.getSource().equals(guiOrderDetails.getBtnCheckAll())) { // If one value is false, I set them all with true. If they're all true, I set them all with false.
            boolean modify = false;
            int i = 0;
            while (i < dtmOrderDetails.getRowCount() && !modify) {
                if ((boolean) dtmOrderDetails.getValueAt(i, 3) == false) {
                    modify = true;
                } else {
                    i++;
                }
            }
            for (i = 0; i < dtmOrderDetails.getRowCount(); i++) {
                dtmOrderDetails.setValueAt(modify, i, 3);
            }
        }
        if (ae.getSource().equals(guiOrderDetails.getBtnSendOrderDone())) { // Send the Order
            List<Integer> list = new LinkedList();
            int i = 0;
            while (i < dtmOrderDetails.getRowCount()) { // Add all products that are true
                if ((boolean) dtmOrderDetails.getValueAt(i, 3) == true) {
                    int id = (Integer) dtmOrderDetails.getValueAt(i, 0);
                    list.add(id);
                }
                i++;
            }
            List<Map> updateOrder = new LinkedList<Map>();
            try {
                updateOrder = crudOrder.updateOrdersReadyProducts(guiOrderDetails.getOrderID(), list);
            } catch (RemoteException ex) {
                Logger.getLogger(ControllerGuiKitchenMain.class.getName()).log(Level.SEVERE, null, ex);
            }
            try {
                if (noMoreToCook(guiOrderDetails.getOrderID())) { //if it no longer belongs in the kitchen (no products for the cooks to cook)
                    for (int j = 0; j < orderList.size(); j++) {
                        if (orderList.get(j) == guiOrderDetails.getOrderID()) {
                            orderList.remove(j);
                            guiKitchenMain.removeElementOfOrdersGrid(j);
                        }
                    }
                    guiOrderDetails.closeWindow();
                } else { // Still has products for the cook to cook
                    loadGuiOrderDetails(guiOrderDetails.getOrderID(), guiOrderDetails.getTxtOrderDescription().getText(), guiOrderDetails.getLabelOrderArrivalTime().getText());
                }
            } catch (RemoteException ex) {
                Logger.getLogger(ControllerGuiKitchenMain.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        if (guiLogin != null) {
            if (ae.getSource() == guiLogin.getBtnAccept()) {
                String user = guiLogin.getcBoxUsers().getItemAt(guiLogin.getcBoxUsers().getSelectedIndex()).toString();
                String split[] = user.split("-");
                int userId = Integer.parseInt(split[0]);
                try {
                    crudPresence.create(userId);
                    guiLogin.dispose();
                    online.add(crudUser.getUser(userId));
                } catch (RemoteException ex) {
                    Logger.getLogger(ControllerGuiKitchenMain.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            if (ae.getSource() == guiLogin.getBtnCancel()) {
                guiLogin.dispose();
            }
        }

        if (ae.getSource() == guiKitchenMain.getMenuItemNewLogin()) {
            try {
                guiLogin = new GuiLogin(guiKitchenMain, true);
                Set<Map> offline = new HashSet<Map>();
                offline.addAll(crudUser.getCooks());
                offline.removeAll(online);
                if (offline.isEmpty()) {
                    JOptionPane.showMessageDialog(guiKitchenMain, "Ocurrió un error, ya estan todos los usuarios logueados", "Error!", JOptionPane.ERROR_MESSAGE);
                } else {
                    guiLogin.loadCBoxUsers(offline);
                    guiLogin.setActionListener(this);
                    guiLogin.setVisible(true);
                }
            } catch (RemoteException ex) {
                Logger.getLogger(ControllerGuiKitchenMain.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        if (ae.getSource() == guiKitchenMain.getMenuItemLoggedUsers()) {
            GuiOnlineUsers gulu = new GuiOnlineUsers(guiKitchenMain, true);
            gulu.setVisible(true);
        }
        /* PARA ACTUALIZAR LOS PRODUCTOS LISTOS SE DEBE USAR ESTE METODO
         * SI HAY ALGUNA DUDA LEER SU JAVADOC:
         * crudOrder.updateOrdersReadyProducts(id, list)
         */
    }
}
