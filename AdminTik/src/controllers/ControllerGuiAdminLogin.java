/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import gui.GuiAdminLogin;
import interfaces.InterfaceAdmin;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import utils.Config;

/**
 *
 * @author agustin
 */
public class ControllerGuiAdminLogin implements ActionListener {

    private static GuiAdminLogin guiAdminLogin;
    private static InterfaceAdmin crudAdmin;

    private ControllerMain controllerMain; //controlador principal

    public ControllerGuiAdminLogin() throws NotBoundException, MalformedURLException, RemoteException {
        guiAdminLogin = new GuiAdminLogin();
        Config config = new Config(new javax.swing.JFrame(), true);
        try {
            config.loadProperties();
        } catch (IOException ex) {
            Logger.getLogger(ControllerGuiAdminLogin.class.getName()).log(Level.SEVERE, null, ex);
        }
        boolean connected = false;
        while (!connected) {
            try {
                crudAdmin = (InterfaceAdmin) Naming.lookup("//" + Config.ip + "/crudAdmin");
                connected = true;
            } catch (RemoteException e) {
                config = new Config(new javax.swing.JFrame(), true);
                config.setVisible(true);
                if (config.getReturnStatus() != Config.RET_OK) {
                    System.exit(0);
                }
                connected = false;

            }
        }
        guiAdminLogin.clearFields();
        guiAdminLogin.setActionListener(this);
        guiAdminLogin.setLocationRelativeTo(null);
        guiAdminLogin.setVisible(true);
        getAllAdmins();
        controllerMain = new ControllerMain(guiAdminLogin);
       

    }

    public static void getAllAdmins() {
        try {
            List<Map> admins = crudAdmin.getAdmins();
            if (admins.isEmpty()) {
                crudAdmin.create("admin", "admin");
                guiAdminLogin.getTxtName().addItem("admin");
                
                JOptionPane.showMessageDialog(guiAdminLogin, "Se creó un un administrador por defecto \nNombre: admin - Contraseña:admin", "NO HAY ADMINISTRADORES!", JOptionPane.INFORMATION_MESSAGE);
            }else{
                for (Map admin : admins) {
                    System.out.println(admin.get("name"));
                    guiAdminLogin.getTxtName().addItem((String)admin.get("name"));
                }
            }
        } catch (RemoteException ex) {
            Logger.getLogger(ControllerGuiAdminLogin.class.getName()).log(Level.SEVERE, null, ex);
        }
       guiAdminLogin.getTxtPassword().setText("");
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource().equals(guiAdminLogin.getBtnConfirm())) {
            try {
                Map<String, Object> newUser;
                String adminName =  (String) guiAdminLogin.getTxtName().getSelectedItem();
                newUser = crudAdmin.loginAdmin(adminName, guiAdminLogin.getTxtPassword().getText());
                if (newUser != null && controllerMain!=null) {
                    guiAdminLogin.setVisible(false);
                    controllerMain.initSession(newUser);
                } else {
                    JOptionPane.showMessageDialog(guiAdminLogin, "Nombre de usuario o contraseña no valida!", "Error!", JOptionPane.ERROR_MESSAGE);
                }
            } catch (RemoteException ex) {
                Logger.getLogger(ControllerGuiCRUDAdmin.class.getName()).log(Level.SEVERE, null, ex);
            } catch (NotBoundException ex) {
                Logger.getLogger(ControllerGuiAdminLogin.class.getName()).log(Level.SEVERE, null, ex);
            } catch (MalformedURLException ex) {
                Logger.getLogger(ControllerGuiAdminLogin.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

}
