/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import gui.GuiLogin;
import gui.GuiMain;
import gui.ComponentUserLoginBtn;
import interfaces.InterfaceUser;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import utils.Config;

/**
 *
 * @author jacinto
 */
public class ControllerGuiMain implements ActionListener {
    

    private GuiMain guiMain;
    private GuiLogin guiLogin;
    private InterfaceUser crudUser;
    private Map buttons; //Nos sirve para almacenar a los objetos creados

    public ControllerGuiMain() throws NotBoundException, MalformedURLException, RemoteException {
        guiMain = new GuiMain();
        guiMain.setActionListener(this);
        buttons = new HashMap();
        guiMain.setVisible(true);
        crudUser = (InterfaceUser) Naming.lookup("//" + Config.ip + "/crudUser");
    }
    
    

    public void addMyComponent(String user) {
        //instancia nueva a componente
        ComponentUserLoginBtn cULBtn = new ComponentUserLoginBtn(user);
        cULBtn.btn.addActionListener(this);//escucha eventos
        cULBtn.setVisible(true);
        guiMain.getPanelLogin().add(cULBtn);//se añade al jpanel
        guiMain.getPanelLogin().validate();
        guiMain.validate();
        //se añade al MAP
        this.buttons.put("key_" + user, cULBtn);
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
                guiLogin.loadCBoxUsers(crudUser.getUsers());
                guiLogin.setActionListener(this);
                guiLogin.setVisible(true);
                
            } catch (RemoteException ex) {
                Logger.getLogger(ControllerGuiMain.class.getName()).log(Level.SEVERE, null, ex);
            }
            
        }

        if (e.getSource() == guiLogin.getBtnAccept()) {
            
            //String user = guiLogin.getcBoxUsers().getItemAt(guiLogin.getcBoxUsers().getSelectedIndex()).toString();
            //String split[] = user.split("-");
            boolean validate = true;
            //boolean validate = crudUser.validatePass(Integer.parseInt(split[0]),guiLogin.getTxtPass().getText());
            if (validate) {
                addMyComponent("1-ramiro");
                guiLogin.dispose();
            } else {
                 JOptionPane.showMessageDialog(guiMain, "Ocurrió un error, contraseña incorrecta", "Error!", JOptionPane.ERROR_MESSAGE);
            }
        }
        if (e.getSource() == guiLogin.getBtnCancel()) {
        }

    }    
    
    
}
