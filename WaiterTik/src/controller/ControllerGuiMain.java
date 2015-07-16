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
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import javax.swing.JOptionPane;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperPrintManager;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.view.JasperViewer;
import reports.payments.ImplementsDataSourcePayment;
import reports.payments.Payment;
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

    private static ImplementsDataSourcePayment datasource;

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
        datasource = new ImplementsDataSourcePayment();

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

    /**
     * Metodo para avisarle al controlador que se actualizaron los pedidos
     *
     * @param order
     * @param orderId
     * @throws java.rmi.RemoteException
     */
    public static void UpdateOrder(Pair<Map<String, Object>, List<Map>> or) throws RemoteException {
        final Pair<Map<String, Object>, List<Map>> order = or;
        if (or.first().get("user_id") != null) {
            if (guiMain.isVisible() && (int) order.first().get("user_id") == idWaiter) {
                Thread thread = new Thread() {
                    @Override
                    public void run() {
                        List<Map> products = order.second();
                        Iterator<GuiMenuDetail> itr = listOrdersPanels.iterator();
                        //Recorro todos los paneles de la gridbaglayout
                        while (itr.hasNext()) {
                            GuiMenuDetail orderPane = itr.next();//saco el panel actual
                            if (orderPane.getIdOrder() == (int) order.first().get("id")) {
                                int productsReady = 0;
                                int productsCommited = 0;
                                for (Map prod : order.second()) {
                                    if ((boolean) prod.get("done")) {
                                        productsReady++;
                                    }
                                    if ((boolean) prod.get("commited")) {
                                        productsCommited++;
                                    }
                                }
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
                                        orderPane.setColor(0);
                                        break;

                                    case 3:
                                        //poner el pedido en rojo, ningun producto esta listo
                                        orderPane.setColor(3);
                                        break;

                                    case 2:
                                        //poner el pedido en amarillo , hay producto listos pero no todos
                                        orderPane.setColor(2);
                                        break;
                                    case 1:
                                        //poner pedido en verde , todos los productos estan listos
                                        orderPane.setColor(1);
                                        break;
                                }
                                guiMain.validateAll();
                                return;
                            }
                        }
                        //si estoy aca es porque no lo encontré, así que lo creo
                        addOrderInGui(order.first(), products);
                    }
                ;

                }

                ;
                thread.start();
            }
        }
    }

    /**
     * setea el mensaje "demorado" al pedido con id pasado por parametro
     *
     * @param id
     */
    public static void orderDelayed(int id) {
        Iterator<GuiMenuDetail> itr = listOrdersPanels.iterator();
        while (itr.hasNext()) {
            GuiMenuDetail orderPane = itr.next();//saco el panel actual
            if (orderPane.getIdOrder() == id) {
                orderPane.setBelated(true);
                guiMain.validateAll();
                return;
            }
        }
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
        GuiPanelNew newButton = new GuiPanelNew();
        listOrdersPanels.clear();
        newButton.getLblNew().addMouseListener(new MouseAdapter() {//agrego un mouselistener
            @Override
            public void mousePressed(MouseEvent e) {
                guiOrder.setLocationRelativeTo(null);
                controllerGuiOrder.setIds(null, idWaiter);
                guiOrder.setVisible(true);
                try {
                    controllerGuiOrder.CreateTree();

                } catch (RemoteException ex) {
                    Logger.getLogger(ControllerGuiMain.class
                            .getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
        guiMain.addPanelNewOrder(newButton);
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
                    try {
                        controllerGuiOrder.CreateTree();

                    } catch (RemoteException ex) {
                        Logger.getLogger(ControllerGuiMain.class
                                .getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        });
        newOrder.getLblDone().addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                int r = JOptionPane.showConfirmDialog(null, "Desea cerrar el pedido?");
                if (r == JOptionPane.YES_OPTION) {
                    try {
                        r = JOptionPane.showConfirmDialog(null, "Desea imprimir un comprobante?");
                        if (r == JOptionPane.OK_OPTION) {
                            List<Map> ord = crudOrder.getDataPrinterOrd(newOrder.getIdOrder());
                            for (Map m : ord) {
                                Payment p = new Payment((String) m.get("name"), (float) m.get("quantity"), (float) m.get("sell_price"), (float) m.get("paid_exceptions"));
                                datasource.addPayment(p);
                            }
                            try {
                                JasperReport reporte = (JasperReport) JRLoader.loadObject(getClass().getResource("/reports/payments/ticket.jasper"));//cargo el reporte
                                JasperPrint jasperPrint;
                                jasperPrint = JasperFillManager.fillReport(reporte, null, datasource);
                                JasperPrintManager.printReport(jasperPrint, true);
                            } catch (JRException ex) {
                                Logger.getLogger(ControllerGuiOrder.class.getName()).log(Level.SEVERE, null, ex);
                            }
                            datasource.removeAllFinalProduct();
                        }
                        crudOrder.closeOrder(newOrder.getIdOrder());
                        if (!guiMain.getChkAllOrders().isSelected()) {
                            removeGuiOrderPane(newOrder.getIdOrder());
                        }

                    } catch (RemoteException ex) {
                        Logger.getLogger(ControllerGuiMain.class
                                .getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        });
        newOrder.getLblCommited().addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                try {
                    crudOrder.commitProducts(newOrder.getIdOrder());

                } catch (RemoteException ex) {
                    Logger.getLogger(ControllerGuiMain.class
                            .getName()).log(Level.SEVERE, null, ex);
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

    public static boolean seeAll() {
        return guiMain.getChkAllOrders().isSelected();
    }
}
