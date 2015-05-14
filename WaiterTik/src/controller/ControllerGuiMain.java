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
    private GuiLogin guiLogin;
    private static InterfaceUser crudUser;
    private InterfacePresence crudPresence;
    private Map buttons; //Nos sirve para almacenar a los objetos creados
    private Map buttonsOrder;
    private Set<Map> online;
    private GuiOrder guiOrder;
    ControllerGuiOrder controllerGuiOrder;
    private boolean isNewOrder;  //variable de control, para saber que accion se ejecuta.
    private static boolean ordersUpdated; //variable estatica para saber cuando la cocina actualizo un pedido 
    private static InterfaceOrder crudOrder;
    private static InterfaceFproduct crudFproduct;
    private static Map orders; //Nos sirve para almacenar a los objetos creados
    private static GuiMenuDetail menuDetail; //para trabajar más facil

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
        guiLogin = null;
        crudOrder = (InterfaceOrder) Naming.lookup("//" + Config.ip + "/" + InterfaceName.CRUDOrder);
        crudFproduct = (InterfaceFproduct) Naming.lookup("//" + Config.ip + "/" + InterfaceName.CRUDFproduct);
        for (Map m : crudPresence.getWaiters()) {
            online.add(m);
            String usr = (int) m.get("id") + "-" + (String) m.get("name") + " " + (String) m.get("surname");
            addMyComponent(usr);
        }
        loadOrders(-1);
        guiMain.setActionListener(this);

    }

    public Map getButtonsOrder() {
        return buttonsOrder;
    }

    /**
     * Metodo para avisarle al controlador que se actualizaron los pedidos
     *
     * @param orderId
     */
    public static void UpdateOrder(int orderId) throws RemoteException {
        List<Map> products = new LinkedList();
        Map order = null;
        String details = "";
        String nameWaiter = "";
        try {
            order = crudOrder.getOrder(orderId); //aca esta guardado el numero del pedido y el usuario que lo hizo, lo puse por si hace falta
            products = crudOrder.getOrderProducts(orderId);

        } catch (RemoteException ex) {
            Logger.getLogger(ControllerGuiMain.class.getName()).log(Level.SEVERE, null, ex);
        }
        if (order == null) {
            return;
        }
        nameWaiter = (String) (crudUser.getUser((int) order.get("user_id"))).get("name");
        int productsReady = 0;
        for (Map prod : products) {
            if ((boolean) prod.get("done")) {
                productsReady++;
            }
            String name = (String) crudFproduct.getFproduct((int) prod.get("fproduct_id")).get("name");
            details += ParserFloat.floatToString((float) prod.get("quantity")) + " " + name + "\n";
        }
        menuDetail = existsOrder(orderId);
        if (menuDetail == null) {
            final GuiMenuDetail newOrder = new GuiMenuDetail();
            newOrder.getTxtDetail().addMouseListener(new MouseAdapter() {//agrego un mouselistener
                @Override
                public void mousePressed(MouseEvent e) {
                    if (e.getClickCount() == 2) {
                        System.out.println("click en el pedido: " + newOrder.getIdOrder()+" del mozo : "+ newOrder.getIdWaiter());
                        //aca tiene que abrir la ventana del login para ese mozo
                    }
                }
            });
            menuDetail = newOrder;
            guiMain.addActiveOrder(menuDetail);

        }
        if (productsReady == products.size()) {
            //pedido pausado
            guiMain.addPausedOrder(menuDetail);
            menuDetail.setColor(0);
            menuDetail.setOrder(order, details, nameWaiter);
        }
        if (productsReady == 0) {
            //poner el pedido en rojo, ningun producto esta listo
            menuDetail.setColor(3);
            menuDetail.setOrder(order, details, nameWaiter);
        } else {
            if (productsReady < products.size()) {
                //poner el pedido en amarillo , hay producto listos pero no todos
                menuDetail.setColor(2);
                menuDetail.setOrder(order, details, nameWaiter);
            } else {
                //poner pedido en verde , todos los productos estan listos
                menuDetail.setColor(1);
                menuDetail.setOrder(order, details, nameWaiter);
            }
        }
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
                guiLogin = new GuiLogin(guiMain, true);
                Set<Map> offline = new HashSet<Map>();
                offline.addAll(crudUser.getUsers()); //getWaiters();
                offline.removeAll(online);
                if (offline.isEmpty()) {
                    JOptionPane.showMessageDialog(guiMain, "Ocurrió un error, ya estan todos los usuarios logueados", "Error!", JOptionPane.ERROR_MESSAGE);
                } else {
                    guiLogin.loadCBoxUsers(offline);
                    guiLogin.setActionListener(this);
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
        if (guiLogin != null) {
            if (e.getSource() == guiLogin.getBtnAccept()) {
                String user = guiLogin.getcBoxUsers().getItemAt(guiLogin.getcBoxUsers().getSelectedIndex()).toString();
                String split[] = user.split("-");
                int userId = Integer.parseInt(split[0]);
                try {
                    //crudUser.validatePass(userId, guiLogin.getTxtPass().getText())
                    if (true) {
                        if (isNewOrder) {
                            guiLogin.dispose();
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
                            guiLogin.dispose();
                            online.add(crudUser.getUser(userId));
                        }
                    } else {
                        JOptionPane.showMessageDialog(guiMain, "Ocurrió un error, contraseña incorrecta", "Error!", JOptionPane.ERROR_MESSAGE);
                    }
                } catch (RemoteException ex) {
                    Logger.getLogger(ControllerGuiMain.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            if (e.getSource() == guiLogin.getBtnCancel()) {
                guiLogin.dispose();
            }
        }
        if (e.getSource().equals(guiMain.getBtnNew())) {
            if (online.isEmpty()) {
                JOptionPane.showMessageDialog(guiMain, "Ocurrió un error, no hay mozos logueado", "Error!", JOptionPane.ERROR_MESSAGE);
            } else {
                try {
                    isNewOrder = true;
                    guiLogin = new GuiLogin(guiMain, true);
                    Set<Map> offline = new HashSet<Map>();
                    offline.addAll(crudUser.getUsers());
                    guiLogin.loadCBoxUsers(offline);
                    guiLogin.setActionListener(this);
                    guiLogin.setLocationRelativeTo(null);
                    guiLogin.setVisible(true);
                } catch (RemoteException ex) {
                    Logger.getLogger(ControllerGuiMain.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }

    private static GuiMenuDetail existsOrder(int id) {
        Iterator it = orders.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry entry = (Map.Entry) it.next();
            //se obtiene el KEY -> identificador unico
            Integer itm = (Integer) entry.getKey();
            //si comando de componente es igual a comando pulsado
            if (itm.equals(id)) {
                //se recupera el panel
                return ((GuiMenuDetail) entry.getValue());
                //FILTRAR 
            }
        }
        return null;
    }

    /**
     * Cargo en la gui todas las ordenes ACTIVAS de un cliente, si el id es -1
     * trae todos
     *
     * @param idUser
     * @throws RemoteException
     */
    private void loadOrders(int idUser) throws RemoteException {
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
                        System.out.println("click en el pedido: " + newOrder.getIdOrder()+" del mozo : "+ newOrder.getIdWaiter());
                        //aca tiene que abrir la ventana del login para ese mozo
                    }
                }
            });
            menuDetail = newOrder;
            if (productsCommited == products.size()) {
                guiMain.addPausedOrder(menuDetail);
                menuDetail.setColor(0);
                menuDetail.setOrder(ord, details, nameWaiter);
            } else {
                guiMain.addActiveOrder(menuDetail);
            }

            if (productsReady == 0) {
                //poner el pedido en rojo, ningun producto esta listo
                menuDetail.setColor(3);
                menuDetail.setOrder(ord, details, nameWaiter);
            } else {
                if (productsReady < products.size()) {
                    //poner el pedido en amarillo , hay producto listos pero no todos
                    menuDetail.setColor(2);
                    menuDetail.setOrder(ord, details, nameWaiter);
                }
                if (productsReady == products.size() && productsCommited != productsReady) {
                    //poner pedido en verde , todos los productos estan listos
                    menuDetail.setColor(1);
                    menuDetail.setOrder(ord, details, nameWaiter);
                }
            }
        }
        guiMain.revalidate();
    }

    private void clearAllOrders() {
        guiMain.clearAllOrders();
        orders.clear();
    }
}
