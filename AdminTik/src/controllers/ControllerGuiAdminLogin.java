/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import gui.GuiAdminLogin;
import implementsInterface.ClientAdmin;
import interfaces.InterfaceAdmin;
import interfaces.InterfaceServer;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.MalformedURLException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import utils.Config;
import utils.InterfaceName;

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
                InterfaceName.registry = LocateRegistry.getRegistry(Config.ip, Registry.REGISTRY_PORT);
                crudAdmin = (InterfaceAdmin) InterfaceName.registry.lookup(InterfaceName.CRUDAdmin);
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
        ClientAdmin client= new ClientAdmin();//creo el cliente este
        ( (InterfaceServer) InterfaceName.registry.lookup(InterfaceName.server)).registerClientAdmin(client);//le digo al server que me conecto y soy un mozo

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
            boolean fullAccessAdmin = false;
            for (Map admin : admins) { //busco todos los admins para listarlos en el jcombobox
                fullAccessAdmin = (boolean)admin.get("is_admin") || fullAccessAdmin;  
                guiAdminLogin.getTxtName().addItem((String)admin.get("name"));
            }
            
            if (!fullAccessAdmin) { //si no hay ningun administrador con acceso total al sistema, creo uno nuevo
                crudAdmin.create("admin", "admin",true);
                guiAdminLogin.getTxtName().addItem("admin"); 
                JOptionPane.showMessageDialog(guiAdminLogin, "Se creó un un administrador por defecto \nNombre: admin - Contraseña:admin", "NO HAY ADMINISTRADORES!", JOptionPane.INFORMATION_MESSAGE);
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
