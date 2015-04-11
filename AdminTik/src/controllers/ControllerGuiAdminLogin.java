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
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author agustin
 */
public class ControllerGuiAdminLogin implements ActionListener {
    
    private final GuiAdminLogin guiAdminLogin;
    private final InterfaceAdmin crudAdmin;
    private ControllerGuiCRUDAdmin controllerGuiAdmin;
    
    public ControllerGuiAdminLogin() throws NotBoundException, MalformedURLException, RemoteException {
        guiAdminLogin = new GuiAdminLogin();
        crudAdmin = (InterfaceAdmin)   Naming.lookup("//192.168.1.26/crudAdmin");              
        guiAdminLogin.setActionListener(this);
        guiAdminLogin.setVisible(true);
        if(crudAdmin.getAdmins().isEmpty()){
            crudAdmin.create("admin", "admin");
            JOptionPane.showMessageDialog(guiAdminLogin, "Se creó un un administrador por defecto \n Nombre: admin - Contraseña:admin", "NO HAY ADMINISTRADORES!", JOptionPane.INFORMATION_MESSAGE);
        }
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource().equals(guiAdminLogin.getBtnConfirm())){
            try {
                Map<String,Object> newUser;
                newUser = crudAdmin.loginAdmin(guiAdminLogin.getTxtName().getText(), guiAdminLogin.getTxtPassword().getText());                
                if(newUser != null){
                    guiAdminLogin.dispose();
                    
                    new ControllerTest(newUser); //aca deberia ir el controlador main.
                   // controllerGuiAdmin = new ControllerGuiCRUDAdmin(newUser);
                }
                else
                    JOptionPane.showMessageDialog(guiAdminLogin, "Nombre de usuario o contraseña no valida!", "Error!", JOptionPane.ERROR_MESSAGE);
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
