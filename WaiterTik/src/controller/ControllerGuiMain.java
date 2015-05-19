/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import controller.controllerGuiOrder.ControllerGuiOrder;
import gui.login.GuiLogin;
import gui.GuiMain;
import gui.GuiMenuDetail;
import gui.GuiPanelNew;
import gui.login.ComponentUserLoginBtn;
import gui.order.GuiOrder;
import interfaces.InterfaceFproduct;
import interfaces.InterfacePresence;
import interfaces.InterfaceUser;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import utils.Config;
import utils.InterfaceName;
import interfaces.InterfaceOrder;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.LinkedList;
import java.util.List;
import utils.ParserFloat;

/**
 *
 * @author jacinto
 */
public class ControllerGuiMain implements ActionListener {

    private static GuiMain guiMain;
    private static GuiLogin guiLogin;
    private static InterfaceUser crudUser;
    private InterfacePresence crudPresence;
    private Map buttons; //Nos sirve para almacenar a los objetos creados
    private Map buttonsOrder;
    private static Set<Map> online;
    private static GuiOrder guiOrder;
    static ControllerGuiOrder controllerGuiOrder;
    private boolean isNewOrder;  //variable de control, para saber que accion se ejecuta.
    private static boolean ordersUpdated; //variable estatica para saber cuando la cocina actualizo un pedido 
    private static InterfaceOrder crudOrder;
    private static InterfaceFproduct crudFproduct;
    private static Map orders; //Nos sirve para almacenar a los objetos creados
    private static boolean seeOrder;  //variable de control, para saber que accion se ejecuta.
    private static int orderClic;
    private static int idWaiter; //id del mozo que esta en esta ventana

    public ControllerGuiMain() throws NotBoundException, MalformedURLException, RemoteException {
        guiMain = new GuiMain();
        buttons = new HashMap();
        orders = new HashMap();
        buttonsOrder = new HashMap();
        crudUser = (InterfaceUser) InterfaceName.registry.lookup(InterfaceName.CRUDUser);
        crudPresence = (InterfacePresence) InterfaceName.registry.lookup(InterfaceName.CRUDPresence);
        online = new HashSet<Map>();
        guiOrder = new GuiOrder(guiMain, true);
        controllerGuiOrder = new ControllerGuiOrder(guiOrder);
        guiLogin = new GuiLogin(guiMain, true);
        guiLogin.setActionListener(this);
        crudOrder = (InterfaceOrder) InterfaceName.registry.lookup(InterfaceName.CRUDOrder);
        crudFproduct = (InterfaceFproduct) InterfaceName.registry.lookup(InterfaceName.CRUDFproduct);
//        for (Map m : crudPresence.getWaiters()) {
//            online.add(m);
//            String usr = (int) m.get("id") + "-" + (String) m.get("name") + " " + (String) m.get("surname");
//            addMyComponent(usr);
//        }
        loadOrders(-1);
//        guiMain.setActionListener(this);
        seeOrder = false;

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

//    public void addMyComponent(String user) {
//        //instancia nueva a componente
//        ComponentUserLoginBtn cULBtn = new ComponentUserLoginBtn(user);
//        cULBtn.btn.addActionListener(this);//escucha eventos
//        cULBtn.setSize(guiMain.getBtnLogin().getSize());
//        guiMain.getPanelLogin().add(cULBtn);//se añade al jpanel 
//        guiMain.getPanelLogin().revalidate();
//        cULBtn.setVisible(true);
//        //se añade al MAP
//        this.buttons.put(user, cULBtn);
//    }

    @Override
    public void actionPerformed(ActionEvent e) {
        //se obtiene el comando ejecutado
        String command = e.getActionCommand();
        //se recorre el MAP
        Iterator it = buttons.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry entry = (Map.Entry) it.next();
            //se obtiene el KEY -> identificador unico
            String itm = entry.getKey().toString();
            //si comando de componente es igual a comando pulsado
            if (itm.equals(command)) {
                //se recupera el contenido del JTextfield
                String name = ((ComponentUserLoginBtn) entry.getValue()).btn.getText();
                String split[] = name.split("-");
                int userId = Integer.parseInt(split[0]);
                try {
                    loadOrders(userId);
                } catch (RemoteException ex) {
                    Logger.getLogger(ControllerGuiMain.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }

//        if (e.getSource() == guiMain.getBtnLogin()) {
//            try {
//                seeOrder = false;
//                Set<Map> offline = new HashSet<Map>();
//                offline.addAll(crudUser.getWaiters());
//                offline.removeAll(online);
//                if (offline.isEmpty()) {
//                    JOptionPane.showMessageDialog(guiMain, "Ocurrió un error, ya estan todos los usuarios logueados", "Error!", JOptionPane.ERROR_MESSAGE);
//                } else {
//                    guiLogin.loadCBoxUsers(offline);
//                    guiLogin.setLocationRelativeTo(null);
//                    guiLogin.setVisible(true);
//                }
//            } catch (RemoteException ex) {
//                Logger.getLogger(ControllerGuiMain.class.getName()).log(Level.SEVERE, null, ex);
//            }
//        }
//        if (e.getSource() == guiMain.getBtnSeeAll()) {
//            try {
//                loadOrders(-1);
//            } catch (RemoteException ex) {
//                Logger.getLogger(ControllerGuiMain.class.getName()).log(Level.SEVERE, null, ex);
//            }
//        }
//
//        if (e.getSource() == guiLogin.getBtnAccept()) {
//            String user = guiLogin.getcBoxUsers().getItemAt(guiLogin.getcBoxUsers().getSelectedIndex()).toString();
//            String split[] = user.split("-");
//            int userId = Integer.parseInt(split[0]);
//            if (seeOrder) {
//                guiLogin.setVisible(false);
//                guiOrder.setLocationRelativeTo(null);
//                controllerGuiOrder.setIds(orderClic, userId);
//                guiOrder.setVisible(true);
//                try {
//                    controllerGuiOrder.CreateTree();
//                } catch (RemoteException ex) {
//                    Logger.getLogger(ControllerGuiMain.class.getName()).log(Level.SEVERE, null, ex);
//                }
//            } else {
//                try {
//                    //crudUser.validatePass(userId, guiLogin.getTxtPass().getText())
//                    if (true) {
//                        if (isNewOrder) {
//                            guiLogin.setVisible(false);
//                            guiOrder.setLocationRelativeTo(null);
//                            controllerGuiOrder.setIds(null, userId);
//                            guiOrder.setVisible(true);
//                            try {
//                                controllerGuiOrder.CreateTree();
//                            } catch (RemoteException ex) {
//                                Logger.getLogger(ControllerGuiMain.class.getName()).log(Level.SEVERE, null, ex);
//                            }
//                        } else {
//                            addMyComponent(user);
//                            crudPresence.create(userId);
//                            guiLogin.setVisible(false);
//                            online.add(crudUser.getUser(userId));
//                        }
//                    } else {
//                        JOptionPane.showMessageDialog(guiMain, "Ocurrió un error, contraseña incorrecta", "Error!", JOptionPane.ERROR_MESSAGE);
//                    }
//                } catch (RemoteException ex) {
//                    Logger.getLogger(ControllerGuiMain.class.getName()).log(Level.SEVERE, null, ex);
//                }
//            }
//        }
//        if (e.getSource() == guiLogin.getBtnCancel()) {
//            guiLogin.setVisible(false);
//        }
//        if (e.getSource().equals(guiMain.getBtnNew())) {
//            if (online.isEmpty()) {
//                JOptionPane.showMessageDialog(guiMain, "Ocurrió un error, no hay mozos logueado", "Error!", JOptionPane.ERROR_MESSAGE);
//            } else {
//                try {
//                    isNewOrder = true;
//                    seeOrder = false;
//                    Set<Map> offline = new HashSet<Map>();
//                    offline.addAll(crudUser.getWaiters());
//                    guiLogin.loadCBoxUsers(offline);
//                    guiLogin.getcBoxUsers().setEnabled(true);
//                    guiLogin.setLocationRelativeTo(null);
//                    guiLogin.setVisible(true);
//                } catch (RemoteException ex) {
//                    Logger.getLogger(ControllerGuiMain.class.getName()).log(Level.SEVERE, null, ex);
//                }
//            }
//        }
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
//                        try {
//                            if (online.contains(crudUser.getUser(newOrder.getIdWaiter()))) {
//                                seeOrder = true;
//                                orderClic = newOrder.getIdOrder();
//                                Set<Map> usr = new HashSet<Map>();
//                                usr.add(crudUser.getUser(newOrder.getIdWaiter()));
//                                guiLogin.loadCBoxUsers(usr);
//                                guiLogin.getcBoxUsers().setSelectedIndex(0);
//                                guiLogin.getcBoxUsers().setEnabled(false);
//                                guiLogin.setLocationRelativeTo(null);
//                                guiLogin.setVisible(true);
//                            } else {
//                                JOptionPane.showMessageDialog(guiMain, "Ocurrió un error, usuario no logeado", "Error!", JOptionPane.ERROR_MESSAGE);
//                            }
//                        } catch (RemoteException ex) {
//                            Logger.getLogger(ControllerGuiMain.class.getName()).log(Level.SEVERE, null, ex);
//                        }
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
