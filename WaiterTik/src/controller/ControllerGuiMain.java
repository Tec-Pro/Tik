/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import controller.controllerGuiOrder.ControllerGuiOrder;
import gui.login.GuiLogin;
import gui.GuiMain;
import gui.login.ComponentUserLoginBtn;
import gui.order.GuiOrder;
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
import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author jacinto
 */
public class ControllerGuiMain implements ActionListener {

    private static GuiMain guiMain;
    private GuiLogin guiLogin;
    private InterfaceUser crudUser;
    private InterfacePresence crudPresence;
    private Map buttons; //Nos sirve para almacenar a los objetos creados
    private Set<Map> online;
    private GuiOrder guiOrder;
    ControllerGuiOrder controllerGuiOrder;
    private boolean isNewOrder;  //variable de control, para saber que accion se ejecuta.
    private static boolean ordersUpdated; //variable estatica para saber cuando la cocina actualizo un pedido 
    private static InterfaceOrder crudOrder;

    public ControllerGuiMain() throws NotBoundException, MalformedURLException, RemoteException {
        guiMain = new GuiMain();
        guiMain.setActionListener(this);
        buttons = new HashMap();
        guiMain.setVisible(true);
        guiMain.setExtendedState(JFrame.MAXIMIZED_BOTH);
        crudUser = (InterfaceUser) Naming.lookup("//" + Config.ip + "/" + InterfaceName.CRUDUser);
        crudPresence = (InterfacePresence) Naming.lookup("//" + Config.ip + "/" + InterfaceName.CRUDPresence);
        online = new HashSet<Map>();
        guiOrder = new GuiOrder(guiMain, true);
        controllerGuiOrder = new ControllerGuiOrder(guiOrder, guiMain);
        guiLogin = null;
        crudOrder = (InterfaceOrder) Naming.lookup("//" + Config.ip + "/" + InterfaceName.CRUDOrder);
        for (Map m : crudPresence.getWaiters()) {
            online.add(m);
            String usr = (int) m.get("id") + "-" + (String) m.get("name") + " " + (String) m.get("surname");
            addMyComponent(usr);
        }
    }

    /**
     * Metodo para avisarle al controlador que se actualizaron los pedidos
     *
     * @param orderId
     */
    public static void UpdateOrder(int orderId) {
        List<Map> products = new LinkedList();
        Map order = null;
        try {
            order = crudOrder.getOrder(orderId); //aca esta guardado el numero del pedido y el usuario que lo hizo, lo puse por si hace falta
            products = crudOrder.getOrderProducts(orderId);
        } catch (RemoteException ex) {
            Logger.getLogger(ControllerGuiMain.class.getName()).log(Level.SEVERE, null, ex);
        }
        if (order == null) {
            return;
        }
        if ((boolean) order.get("closed")) {
            //poner en blanco, perdido cerrado
            return;
        }
        int productsReady = 0;
        for (Map prod : products) {
            if ((boolean) prod.get("done")) {
                productsReady++;
            }
        }
        if (productsReady == 0) {
            //poner el pedido en rojo, ningun producto esta listo
        } else {
            if (productsReady < products.size()) {
                //poner el pedido en amarillo , hay producto listos pero no todos
            } else {
                //poner pedido en verde , todos los productos estan listos
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
                //FILTRAR 
            }
        }
        if (e.getSource() == guiMain.getBtnLogin()) {
            try {
                guiLogin = new GuiLogin(guiMain, true);
                Set<Map> offline = new HashSet<Map>();
                offline.addAll(crudUser.getUsers());
                offline.removeAll(online);
                guiLogin.loadCBoxUsers(offline);
                guiLogin.setActionListener(this);
                guiLogin.setVisible(true);
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
                            guiOrder.setVisible(true);
                            try {
                                controllerGuiOrder.CreateTree();
                                controllerGuiOrder.setIds(null, userId);
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
                    guiLogin.setVisible(true);
                } catch (RemoteException ex) {
                    Logger.getLogger(ControllerGuiMain.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }
}
