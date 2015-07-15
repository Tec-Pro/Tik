/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers.waiter;

import controllers.waiter.ControllerGuiMain;
import gui.waiter.login.ComponentUserLoginBtn;
import gui.waiter.login.GuiLoginGrid;
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
    private InterfaceUser crudUser;
    private InterfacePresence crudPresence;
    private Set<Map> online;
    private Map buttons; //Nos sirve para almacenar a los objetos creados
    private ControllerGuiMain controllerGuiMain;
    private InterfaceTurn crudTurn;

    public ControllerGuiLoginGrid(GuiLoginGrid guiLoginGrid, ControllerGuiMain controllerGuiMain) throws NotBoundException, MalformedURLException, RemoteException {
        this.guiLoginGrid = guiLoginGrid;
        this.controllerGuiMain = controllerGuiMain;
        buttons = new HashMap();
        crudUser = (InterfaceUser) InterfaceName.registry.lookup(InterfaceName.CRUDUser);
        crudTurn = (InterfaceTurn) InterfaceName.registry.lookup(InterfaceName.CRUDTurn);
        crudPresence = (InterfacePresence) InterfaceName.registry.lookup(InterfaceName.CRUDPresence);
        online = new HashSet<Map>();
        for (Map m : crudPresence.getWaiters()) {
            online.add(m);
            String usr = (int) m.get("id") + "-" + (String) m.get("name") + " " + (String) m.get("surname");
            addMyComponent(usr);
        }
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
        cULBtn.setSize(50, 50);
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
                try {
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
                    guiLoginGrid.setVisible(false);
                    controllerGuiMain.waiterInit(userId);
                    
                } catch (RemoteException ex) {
                    Logger.getLogger(ControllerGuiLoginGrid.class.getName()).log(Level.SEVERE, null, ex);
                }

            }
        }

    }
}
