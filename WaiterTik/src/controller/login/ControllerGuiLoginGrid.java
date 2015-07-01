/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package controller.login;

import controller.ControllerGuiMain;
import gui.login.ComponentUserLoginBtn;
import gui.login.GuiLogin;
import gui.login.GuiLoginGrid;
import interfaces.InterfacePresence;
import interfaces.InterfaceTurn;
import interfaces.InterfaceUser;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.MalformedURLException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import utils.InterfaceName;

/**
 *
 * @author jacinto
 */
public class ControllerGuiLoginGrid implements ActionListener {

    private GuiLoginGrid guiLoginGrid;
    private GuiLogin guiLogin;
    private InterfaceUser crudUser;
    private InterfacePresence crudPresence;
    private Set<Map> online;
    private Map buttons; //Nos sirve para almacenar a los objetos creados
    private ControllerGuiMain controllerGuiMain;
    private boolean newLog;
    private InterfaceTurn crudTurn;

    public ControllerGuiLoginGrid(GuiLoginGrid guiLoginGrid, ControllerGuiMain controllerGuiMain) throws NotBoundException, MalformedURLException, RemoteException {
        this.guiLoginGrid = guiLoginGrid;
        this.controllerGuiMain = controllerGuiMain;
        buttons = new HashMap();
        crudUser = (InterfaceUser) InterfaceName.registry.lookup(InterfaceName.CRUDUser);
        crudTurn = (InterfaceTurn) InterfaceName.registry.lookup(InterfaceName.CRUDTurn);
        crudPresence = (InterfacePresence) InterfaceName.registry.lookup(InterfaceName.CRUDPresence);
        online = new HashSet<Map>();
        guiLogin = new GuiLogin(guiLoginGrid, true);
        guiLogin.setActionListener(this);
        for (Map m : crudPresence.getWaiters()) {
            online.add(m);
            String usr = (int) m.get("id") + "-" + (String) m.get("name") + " " + (String) m.get("surname");
            addMyComponent(usr);
        }
        this.guiLoginGrid.setActionListener(this);
        newLog = false;
    }

    /**
     * Agrega un boton con el nombre del usuario
     *
     * @param user (id-nombre-apellido)
     */
    public void addMyComponent(String user) {
        //instancia nueva a componente
        ComponentUserLoginBtn cULBtn = new ComponentUserLoginBtn(user);
        cULBtn.btn.addActionListener(this);//escucha eventos
        cULBtn.setSize(guiLoginGrid.getBtnLogin().getSize());
        guiLoginGrid.getPanelLogin().add(cULBtn);//se añade al jpanel 
        guiLoginGrid.getPanelLogin().revalidate();
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
                Set<Map> usr = new HashSet<Map>();
                try {
                    usr.add(crudUser.getUser(userId));
                } catch (RemoteException ex) {
                    Logger.getLogger(ControllerGuiLoginGrid.class.getName()).log(Level.SEVERE, null, ex);
                }
                newLog = false;
                guiLogin.loadCBoxUsers(usr);
                guiLogin.setLocationRelativeTo(null);
                guiLogin.setVisible(true);
            }
        }
        if (e.getSource() == guiLoginGrid.getBtnLogin()) {
            try {
                if (crudTurn.isTurnOpen()) {
                    Set<Map> offline = new HashSet<Map>();
                    try {
                        offline.addAll(crudUser.getWaiters());
                    } catch (RemoteException ex) {
                        Logger.getLogger(ControllerGuiLoginGrid.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    offline.removeAll(online);
                    if (offline.isEmpty()) {
                        JOptionPane.showMessageDialog(guiLoginGrid, "Ocurrió un error, ya estan todos los usuarios logueados", "Error!", JOptionPane.ERROR_MESSAGE);
                    } else {
                        newLog = true;
                        guiLogin.loadCBoxUsers(offline);
                        guiLogin.setLocationRelativeTo(null);
                        guiLogin.setVisible(true);
                    }
                } else {
                    JOptionPane.showMessageDialog(guiLoginGrid, "Ocurrió un error, no hay ningun turno abierto", "Error!", JOptionPane.ERROR_MESSAGE);
                }
            } catch (RemoteException ex) {
                Logger.getLogger(ControllerGuiLoginGrid.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        if (e.getSource() == guiLogin.getBtnAccept()) {
            String user = guiLogin.getcBoxUsers().getItemAt(guiLogin.getcBoxUsers().getSelectedIndex()).toString();
            String split[] = user.split("-");
            int userId = Integer.parseInt(split[0]);
            if (true) {   // crudUser.validatePass(userId, guiLogin.getTxtPass().getText())
                if (newLog) {
                    addMyComponent(user);
                    try {
                        crudPresence.create(userId);
                        guiLogin.setVisible(false);
                        online.add(crudUser.getUser(userId));
                    } catch (RemoteException ex) {
                        Logger.getLogger(ControllerGuiLoginGrid.class.getName()).log(Level.SEVERE, null, ex);
                    }
                } else {
                    try {
                        guiLogin.setVisible(false);
                        guiLoginGrid.setVisible(false);
                        controllerGuiMain.waiterInit(userId);
                    } catch (RemoteException ex) {
                        Logger.getLogger(ControllerGuiLoginGrid.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            } else {
                JOptionPane.showMessageDialog(guiLoginGrid, "Ocurrió un error, contraseña incorrecta", "Error!", JOptionPane.ERROR_MESSAGE);
            }
        }
        if (e.getSource() == guiLogin.getBtnCancel()) {
            guiLogin.setVisible(false);
        }
    }
}
