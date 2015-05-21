/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import controller.controllerGuiOrder.ControllerGuiOrder;
import controller.login.ControllerGuiLoginGrid;
import gui.GuiMain;
import gui.GuiMenuDetail;
import gui.GuiPanelNew;
import gui.login.GuiLoginGrid;
import gui.order.GuiOrder;
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
import java.util.List;
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
    private static int idWaiter; //id del mozo que esta en esta ventana
    private ControllerGuiLoginGrid controllerGuiLoginGrid;
    private GuiLoginGrid guiLoginGrid;

    public ControllerGuiMain() throws NotBoundException, MalformedURLException, RemoteException {
        guiMain = new GuiMain();
        orders = new HashMap();
        buttonsOrder = new HashMap();
        guiOrder = new GuiOrder(guiMain, true);
        controllerGuiOrder = new ControllerGuiOrder(guiOrder);
        crudOrder = (InterfaceOrder) InterfaceName.registry.lookup(InterfaceName.CRUDOrder);
        crudFproduct = (InterfaceFproduct) InterfaceName.registry.lookup(InterfaceName.CRUDFproduct);
        loadOrders(-1);
        
        guiLoginGrid = new GuiLoginGrid();
        controllerGuiLoginGrid = new ControllerGuiLoginGrid(guiLoginGrid, this);        
    }

    public Map getButtonsOrder() {
        return buttonsOrder;
    }

    /**
     * Metodo para avisarle al controlador que se actualizaron los pedidos
     *
     * @param orderId
     */
    public static void UpdateOrder() throws RemoteException {
        Thread thread = new Thread() {
            public void run() {
                try {
                    loadOrders(idWaiter);
                } catch (RemoteException ex) {
                    Logger.getLogger(ControllerGuiMain.class.getName()).log(Level.SEVERE, null, ex);
                }

            }
        };

        thread.start();
    }

    /**
     * setea el mensaje "demorado" al pedido con id pasado por parametro
     *
     * @param id
     */
    public void OrderDelayed(int id) {
        guiMain.OrderDelayed(id);
    }

    @Override
    public void actionPerformed(ActionEvent e) {        
    }

    /**
     * Cargo en la gui todas las ordenes ACTIVAS de un cliente, si el id es -1
     * trae todos
     *
     * @param idUser
     * @throws RemoteException
     */
    private static void loadOrders(int idUser) throws RemoteException {
        clearAllOrders();
        GuiPanelNew newButton = new GuiPanelNew();
        newButton.getLblNew().addMouseListener(new MouseAdapter() {//agrego un mouselistener
            @Override
            public void mousePressed(MouseEvent e) {
                guiOrder.setLocationRelativeTo(null);
                controllerGuiOrder.setIds(null, idWaiter);
                guiOrder.setVisible(true);
                try {
                    controllerGuiOrder.CreateTree();
                } catch (RemoteException ex) {
                    Logger.getLogger(ControllerGuiMain.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
        guiMain.addPanelNewOrder(newButton);
        List<Map> ordersNotClosed = crudOrder.getActiveOrdersByUser(idUser);
        for (Map ord : ordersNotClosed) {
            String details = "";

            List<Map> products = crudOrder.getOrderProducts((int) ord.get("id"));
            int productsReady = 0;
            int productsCommited = 0;
            for (Map prod : products) {
                if ((boolean) prod.get("done")) {
                    productsReady++;
                }
                if ((boolean) prod.get("commited")) {
                    productsCommited++;
                }
                String name = (String) crudFproduct.getFproduct((int) prod.get("fproduct_id")).get("name");
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
                        try {
                            controllerGuiOrder.CreateTree();
                        } catch (RemoteException ex) {
                            Logger.getLogger(ControllerGuiMain.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                }
            });
            newOrder.getLblDone().addMouseListener(new MouseAdapter() {
                @Override
                public void mousePressed(MouseEvent e) {
                    System.out.println("done id=" + newOrder.getIdOrder());
                }
            });
            newOrder.getLblCommited().addMouseListener(new MouseAdapter() {
                @Override
                public void mousePressed(MouseEvent e) {
                    System.out.println("commited id=" + newOrder.getIdOrder());
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

            }
        }
        guiMain.validateAll();
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
        guiMain.setVisible(true);
        guiMain.setExtendedState(JFrame.MAXIMIZED_BOTH);
        loadOrders(id);
    }
}
