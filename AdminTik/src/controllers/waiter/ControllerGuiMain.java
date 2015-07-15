/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers.waiter;

import controllers.waiter.ControllerGuiOrder;
import controllers.waiter.ControllerGuiLoginGrid;
import gui.waiter.GuiMain;
import gui.waiter.GuiMenuDetail;
import gui.waiter.GuiOrder;
import gui.waiter.GuiPanelNew;
import gui.waiter.login.GuiLoginGrid;
import interfaces.InterfaceFproduct;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.MalformedURLException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;
import utils.InterfaceName;
import interfaces.InterfaceOrder;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import javax.swing.JOptionPane;
import utils.Pair;
import utils.ParserFloat;

/**
 *
 * @author jacinto
 */
public class ControllerGuiMain implements ActionListener {

    private static GuiMain guiMain;
    private Map buttonsOrder;
    private static GuiOrder guiOrder;
    static ControllerGuiOrder controllerGuiOrder;
    private static boolean ordersUpdated; //variable estatica para saber cuando la cocina actualizo un pedido 
    private static InterfaceOrder crudOrder;
    private static InterfaceFproduct crudFproduct;
    private static Map orders; //Nos sirve para almacenar a los objetos creados   
    private static int orderClic;
    public static int idWaiter; //id del mozo que esta en esta ventana
    private ControllerGuiLoginGrid controllerGuiLoginGrid;
    private static GuiLoginGrid guiLoginGrid;
    private static LinkedList<GuiMenuDetail> listOrdersPanels;

    public ControllerGuiMain() throws NotBoundException, MalformedURLException, RemoteException {
        guiMain = new GuiMain();
        orders = new HashMap();
        buttonsOrder = new HashMap();
        guiOrder = new GuiOrder(guiMain, true);
        controllerGuiOrder = new ControllerGuiOrder(guiOrder);
        crudOrder = (InterfaceOrder) InterfaceName.registry.lookup(InterfaceName.CRUDOrder);
        crudFproduct = (InterfaceFproduct) InterfaceName.registry.lookup(InterfaceName.CRUDFproduct);
        listOrdersPanels = new LinkedList<>();
        loadOrders(-1, guiMain.getChkAllOrders().isSelected());

        guiLoginGrid = new GuiLoginGrid();
        controllerGuiLoginGrid = new ControllerGuiLoginGrid(guiLoginGrid, this);
        guiMain.setActionListener(this);
    }

    public static void setLoginGridVisible(boolean isVisible) {
        guiLoginGrid.setVisible(isVisible);
    }

    public Map getButtonsOrder() {
        return buttonsOrder;
    }

    
        private static void removeGuiOrderPane(int orderId) {
        boolean stop = false;
        int index = 1;
        while (!stop && index < guiMain.getPanelActivedOrders().getComponentCount()) {
            GuiMenuDetail guiOP = (GuiMenuDetail) guiMain.getPanelActivedOrders().getComponent(index);
            if (Objects.equals(guiOP.getIdOrder(), orderId)) {
                stop = true;
                guiMain.removeElementOfOrdersGrid(index);
                listOrdersPanels.remove(guiOP);
            }
            index++;

        }
        guiMain.validate();
        guiMain.repaint();
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource().equals(guiMain.getChkAllOrders())) {
            Thread thread = new Thread() {
                public void run() {
                    try {
                        loadOrders(idWaiter, guiMain.getChkAllOrders().isSelected());

                    } catch (RemoteException ex) {
                        Logger.getLogger(ControllerGuiMain.class
                                .getName()).log(Level.SEVERE, null, ex);
                    }

                }
            };

            thread.start();
        }
    }

    /**
     * Cargo en la gui todas las ordenes ACTIVAS de un cliente, si el id es -1
     * trae todos
     *
     * @param idUser
     * @throws RemoteException
     */
    public static void loadOrders(int idUser, boolean all) throws RemoteException {
        clearAllOrders();
        listOrdersPanels.clear();

        List<Map> ordersNotClosed;
        if (!all) {
            ordersNotClosed = crudOrder.getActiveOrdersByUser(idUser);
        } else {
            ordersNotClosed = crudOrder.getOrdersByUser(idUser);
        }
        for (Map ord : ordersNotClosed) {
            List<Map> products = crudOrder.getOrderProductsWithName((int) ord.get("id"));
            addOrderInGui(ord, products);
        }
        guiMain.validateAll();
    }

    private static void addOrderInGui(Map ord, List<Map> products) {
        String details = "";
        int productsReady = 0;
        int productsCommited = 0;
        for (Map prod : products) {
            if ((boolean) prod.get("done")) {
                productsReady++;
            }
            if ((boolean) prod.get("commited")) {
                productsCommited++;
            }
            String name = (String) prod.get("name");
            details += ParserFloat.floatToString((float) prod.get("quantity")) + " " + name + "\n";
        }
        final GuiMenuDetail newOrder = new GuiMenuDetail();
        newOrder.getTxtDetail().addMouseListener(new MouseAdapter() {//agrego un mouselistener
            @Override
            public void mousePressed(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    orderClic = newOrder.getIdOrder();
                    guiOrder.setLocationRelativeTo(null);
                    controllerGuiOrder.setIds(orderClic, idWaiter);
                    guiOrder.setVisible(true);
                }
            }
        });
        int status = -1;
        if (productsCommited == products.size() && productsReady == productsCommited) {
            status = 0;
        } else {
            if (productsReady == 0) {
                status = 3;
            } else {
                if (productsReady < products.size()) {
                    status = 2;
                } else {
                    if (productsReady == products.size() && productsCommited != productsReady) {
                        status = 1;
                    }
                }
            }
        }
        if ((boolean) ord.get("closed")) { //si la orden esta cerrada
            status = 4;
        }
        switch (status) {
            case 0:
                newOrder.setColor(0);
                newOrder.setOrder(ord, details);
                guiMain.addActiveOrder(newOrder);
                break;

            case 3:
                //poner el pedido en rojo, ningun producto esta listo
                newOrder.setColor(3);
                newOrder.setOrder(ord, details);
                guiMain.addActiveOrder(newOrder);
                break;

            case 2:
                //poner el pedido en amarillo , hay producto listos pero no todos
                newOrder.setColor(2);
                newOrder.setOrder(ord, details);
                guiMain.addActiveOrder(newOrder);
                break;
            case 1:
                //poner pedido en verde , todos los productos estan listos
                newOrder.setColor(1);
                newOrder.setOrder(ord, details);
                guiMain.addActiveOrder(newOrder);
                break;
            case 4:
                //poner pedido en invisible , la ordene sta cerrada
                newOrder.setColor(4);
                newOrder.setOrder(ord, details);
                guiMain.addActiveOrder(newOrder);
                break;
        }
        listOrdersPanels.add(newOrder);
    }

    private static void clearAllOrders() {
        guiMain.clearAllOrders();
        orders.clear();
    }

    /**
     * carga la ventana para el usuario con el id pasado por parametro Al
     * presionar su nombre en la ventana de login se llamara a este metodo
     *
     * @param id
     */
    public void waiterInit(int id) throws RemoteException {
        idWaiter = id;
        
        loadOrders(id, guiMain.getChkAllOrders().isSelected());
        guiMain.setVisible(true);
        guiMain.setExtendedState(JFrame.MAXIMIZED_BOTH);

    }
    
    public static boolean seeAll(){
        return guiMain.getChkAllOrders().isSelected();
    }
}
