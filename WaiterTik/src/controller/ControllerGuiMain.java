/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import controller.controllerGuiOrder.ControllerGuiOrder;
import gui.login.GuiLogin;
import gui.GuiMain;
import gui.GuiMenuDetail;
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
    private GuiOrder guiOrder;
    ControllerGuiOrder controllerGuiOrder;
    private boolean isNewOrder;  //variable de control, para saber que accion se ejecuta.
    private static boolean ordersUpdated; //variable estatica para saber cuando la cocina actualizo un pedido 
    private static InterfaceOrder crudOrder;
    private static InterfaceFproduct crudFproduct;
    private static Map orders; //Nos sirve para almacenar a los objetos creados
    private static GuiMenuDetail menuDetail; //para trabajar más facil
    private static boolean seeOrder;  //variable de control, para saber que accion se ejecuta.
    private static int orderClic;

    public ControllerGuiMain() throws NotBoundException, MalformedURLException, RemoteException {
        guiMain = new GuiMain();
        buttons = new HashMap();
        orders = new HashMap();
        buttonsOrder = new HashMap();
        guiMain.setVisible(true);
        guiMain.setExtendedState(JFrame.MAXIMIZED_BOTH);
        crudUser = (InterfaceUser) Naming.lookup("//" + Config.ip + "/" + InterfaceName.CRUDUser);
        crudPresence = (InterfacePresence) Naming.lookup("//" + Config.ip + "/" + InterfaceName.CRUDPresence);
        online = new HashSet<Map>();
        guiOrder = new GuiOrder(guiMain, true);
        controllerGuiOrder = new ControllerGuiOrder(guiOrder);
        guiLogin = new GuiLogin(guiMain, true);
        guiLogin.setActionListener(this);
        crudOrder = (InterfaceOrder) Naming.lookup("//" + Config.ip + "/" + InterfaceName.CRUDOrder);
        crudFproduct = (InterfaceFproduct) Naming.lookup("//" + Config.ip + "/" + InterfaceName.CRUDFproduct);
        for (Map m : crudPresence.getWaiters()) {
            online.add(m);
            String usr = (int) m.get("id") + "-" + (String) m.get("name") + " " + (String) m.get("surname");
            addMyComponent(usr);
        }
        loadOrders(-1);
        guiMain.setActionListener(this);
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
                    loadOrders(-1);
                } catch (RemoteException ex) {
                    Logger.getLogger(ControllerGuiMain.class.getName()).log(Level.SEVERE, null, ex);
                }

            }
        };

        thread.start();
    }

    public void addMyComponent(String user) {
        //instancia nueva a componente
        ComponentUserLoginBtn cULBtn = new ComponentUserLoginBtn(user);
        cULBtn.btn.addActionListener(this);//escucha eventos
        cULBtn.setSize(guiMain.getBtnLogin().getSize());
        guiMain.getPanelLogin().add(cULBtn);//se añade al jpanel 
        guiMain.getPanelLogin().revalidate();
        cULBtn.setVisible(true);
        //se añade al MAP
        this.buttons.put(user, cULBtn);
    }

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

        if (e.getSource() == guiMain.getBtnLogin()) {
            try {
                seeOrder = false;
                Set<Map> offline = new HashSet<Map>();
                offline.addAll(crudUser.getWaiters());
                offline.removeAll(online);
                if (offline.isEmpty()) {
                    JOptionPane.showMessageDialog(guiMain, "Ocurrió un error, ya estan todos los usuarios logueados", "Error!", JOptionPane.ERROR_MESSAGE);
                } else {
                    guiLogin.loadCBoxUsers(offline);
                    guiLogin.setLocationRelativeTo(null);
                    guiLogin.setVisible(true);
                }
            } catch (RemoteException ex) {
                Logger.getLogger(ControllerGuiMain.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        if (e.getSource() == guiMain.getBtnSeeAll()) {
            try {
                loadOrders(-1);
            } catch (RemoteException ex) {
                Logger.getLogger(ControllerGuiMain.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        if (e.getSource() == guiLogin.getBtnAccept()) {
            String user = guiLogin.getcBoxUsers().getItemAt(guiLogin.getcBoxUsers().getSelectedIndex()).toString();
            String split[] = user.split("-");
            int userId = Integer.parseInt(split[0]);
            if (seeOrder) {
                guiLogin.setVisible(false);
                guiOrder.setLocationRelativeTo(null);
                controllerGuiOrder.setIds(orderClic, userId);
                guiOrder.setVisible(true);
                try {
                    controllerGuiOrder.CreateTree();
                } catch (RemoteException ex) {
                    Logger.getLogger(ControllerGuiMain.class.getName()).log(Level.SEVERE, null, ex);
                }
            } else {
                try {
                    //crudUser.validatePass(userId, guiLogin.getTxtPass().getText())
                    if (true) {
                        if (isNewOrder) {
                            guiLogin.setVisible(false);
                            guiOrder.setLocationRelativeTo(null);
                            controllerGuiOrder.setIds(null, userId);
                            guiOrder.setVisible(true);
                            try {
                                controllerGuiOrder.CreateTree();
                            } catch (RemoteException ex) {
                                Logger.getLogger(ControllerGuiMain.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        } else {
                            addMyComponent(user);
                            crudPresence.create(userId);
                            guiLogin.setVisible(false);
                            online.add(crudUser.getUser(userId));
                        }
                    } else {
                        JOptionPane.showMessageDialog(guiMain, "Ocurrió un error, contraseña incorrecta", "Error!", JOptionPane.ERROR_MESSAGE);
                    }
                } catch (RemoteException ex) {
                    Logger.getLogger(ControllerGuiMain.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        if (e.getSource() == guiLogin.getBtnCancel()) {
            guiLogin.setVisible(false);
        }
        if (e.getSource().equals(guiMain.getBtnNew())) {
            if (online.isEmpty()) {
                JOptionPane.showMessageDialog(guiMain, "Ocurrió un error, no hay mozos logueado", "Error!", JOptionPane.ERROR_MESSAGE);
            } else {
                try {
                    isNewOrder = true;
                    seeOrder = false;
                    Set<Map> offline = new HashSet<Map>();
                    offline.addAll(crudUser.getWaiters());
                    guiLogin.loadCBoxUsers(offline);
                    guiLogin.getcBoxUsers().setEnabled(true);
                    guiLogin.setLocationRelativeTo(null);
                    guiLogin.setVisible(true);
                } catch (RemoteException ex) {
                    Logger.getLogger(ControllerGuiMain.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
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
        List<Map> ordersNotClosed = crudOrder.getActiveOrdersByUser(idUser);
        for (Map ord : ordersNotClosed) {
            String details = "";
            String nameWaiter = (String) (crudUser.getUser((int) ord.get("user_id"))).get("name");

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
                        try {
                            if (online.contains(crudUser.getUser(newOrder.getIdWaiter()))) {
                                seeOrder = true;
                                orderClic = newOrder.getIdOrder();
                                Set<Map> usr = new HashSet<Map>();
                                usr.add(crudUser.getUser(newOrder.getIdWaiter()));
                                guiLogin.loadCBoxUsers(usr);
                                guiLogin.getcBoxUsers().setSelectedIndex(0);
                                guiLogin.getcBoxUsers().setEnabled(false);
                                guiLogin.setLocationRelativeTo(null);
                                guiLogin.setVisible(true);
                            } else {
                                JOptionPane.showMessageDialog(guiMain, "Ocurrió un error, usuario no logeado", "Error!", JOptionPane.ERROR_MESSAGE);
                            }
                        } catch (RemoteException ex) {
                            Logger.getLogger(ControllerGuiMain.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                }
            });
            menuDetail = newOrder;
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
                    menuDetail.setColor(0);
                    menuDetail.setOrder(ord, details, nameWaiter);
                    guiMain.addPausedOrder(menuDetail);
                    break;

                case 3:
                    //poner el pedido en rojo, ningun producto esta listo
                    menuDetail.setColor(3);
                    menuDetail.setOrder(ord, details, nameWaiter);
                    guiMain.addActiveOrder(menuDetail);
                    break;

                case 2:
                    //poner el pedido en amarillo , hay producto listos pero no todos
                    menuDetail.setColor(2);
                    menuDetail.setOrder(ord, details, nameWaiter);
                    guiMain.addActiveOrder(menuDetail);
                    break;
                case 1:
                    //poner pedido en verde , todos los productos estan listos
                    menuDetail.setColor(1);
                    menuDetail.setOrder(ord, details, nameWaiter);
                    guiMain.addActiveOrder(menuDetail);
                    break;

            }
        }
        guiMain.validateAll();
    }

    private static void clearAllOrders() {
        guiMain.clearAllOrders();
        orders.clear();
    }
}
