/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import gui.GuiCRUDAdmin;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import interfaces.InterfaceAdmin;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.Map;
import javax.swing.JOptionPane;
import utils.Config;
import utils.InterfaceName;

/**
 *
 * @author agustin
 */
public class ControllerGuiCRUDAdmin implements ActionListener {

    private final GuiCRUDAdmin guiAdmin;
    private final InterfaceAdmin crudAdmin;
    private Map<String, Object> currentAdmin;
    private List<Map> admins = null;
 

    public ControllerGuiCRUDAdmin(Map<String, Object> admin, GuiCRUDAdmin gui) throws NotBoundException, MalformedURLException, RemoteException {
        currentAdmin = admin;
        crudAdmin = (InterfaceAdmin) InterfaceName.registry.lookup(InterfaceName.CRUDAdmin);
        guiAdmin = gui;
        guiAdmin.getTxtName().setEditable(false);
        guiAdmin.getPassField().setEditable(false);
        guiAdmin.setActionListener(this);
        fillAdminList();
        guiAdmin.cleanFields();
        checkPermissions();

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource().equals(guiAdmin.getBtnConfirm()) && guiAdmin.getBtnCreate().isSelected()) { //crear nuevo administrador
            if (!dataIsValid()) {
                JOptionPane.showMessageDialog(guiAdmin, "Los campos no pueden estar vacios!", "Error!", JOptionPane.ERROR_MESSAGE);
            } else {
                try {
                    Map<String, Object> newUser;
                    newUser = crudAdmin.create(guiAdmin.getTxtName().getText(), guiAdmin.getPassField().getText(), guiAdmin.getCheckBoxIsAdmin().isSelected());
                    if (newUser != null) {
                        setDefault();
                        fillAdminList();
                        JOptionPane.showMessageDialog(guiAdmin, "Nuevo administrador creado exitosamente!", "Administrador creado!", JOptionPane.INFORMATION_MESSAGE);
                    } else {
                        JOptionPane.showMessageDialog(guiAdmin, "El nombre ya existe!", "Error!", JOptionPane.ERROR_MESSAGE);
                    }
                } catch (RemoteException ex) {
                    Logger.getLogger(ControllerGuiCRUDAdmin.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        if (e.getSource().equals(guiAdmin.getBtnConfirm()) && guiAdmin.getBtnModify().isSelected()) {    //modificar el administrador actual 
            if (!dataIsValid()) {
                JOptionPane.showMessageDialog(guiAdmin, "Los campos no pueden estar vacios!", "Error!", JOptionPane.ERROR_MESSAGE);
            } else {
                try {
                    Map<String, Object> adminToModify = null;
                    if(admins!=null){
                        for (Map admin : admins) {
                             if(guiAdmin.getComboBoxAdmins().getSelectedItem().toString().equals(admin.get("name"))) //busco el nombre en la lista de administradores
                                 adminToModify = admin;
                        }
                    }
                    if(adminToModify==null){
                        JOptionPane.showMessageDialog(guiAdmin, "No se pudo modificar!", "Error!", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                    Map<String, Object> modifiedUser;
                    modifiedUser = crudAdmin.modify((int) adminToModify.get("id"), guiAdmin.getTxtName().getText(), guiAdmin.getPassField().getText());   
                    if (modifiedUser != null) {
                        fillAdminList();
                        setDefault();
                        if((int)currentAdmin.get("id") == (int)adminToModify.get("id")) //cambio al usuario actual solo si se modifico el usuario que estal logueado
                            currentAdmin = modifiedUser;
                        JOptionPane.showMessageDialog(guiAdmin, "Datos modificados correctamente!", "Modificacion exitosa!", JOptionPane.INFORMATION_MESSAGE);
                    } else {
                        setDefault();
                        JOptionPane.showMessageDialog(guiAdmin, "No se pudo modificar! \n Ese nombre ya existe", "Error!", JOptionPane.ERROR_MESSAGE);
                    }
                } catch (RemoteException ex) {
                    Logger.getLogger(ControllerGuiCRUDAdmin.class.getName()).log(Level.SEVERE, null, ex);
                }
            }

        }
        if (e.getSource().equals(guiAdmin.getBtnConfirm()) && guiAdmin.getBtnDelete().isSelected()) {    //borrar el administrador    
            try {
                Map<String, Object> adminToDelete = null;
                if(admins!=null){
                     for (Map admin : admins) {
                          if(guiAdmin.getComboBoxAdmins().getSelectedItem().toString().equals(admin.get("name"))) //busco el nombre en la lista de administradores
                              adminToDelete = admin;
                     }
                }
                if(adminToDelete==null){
                     JOptionPane.showMessageDialog(guiAdmin, "No se pudo eliminar!", "Error!", JOptionPane.ERROR_MESSAGE);
                     return;
                } 
                boolean currentuserDeleted = false;
                if((int)currentAdmin.get("id") == (int)adminToDelete.get("id")){ //si esta por borrar su propia cuenta
                    Object[] options = {"Borrar","Cancelar"};
                    int n = JOptionPane.showOptionDialog(guiAdmin,
                        "Está por borrar su cuenta de administrador",
                        "Atención!",
                        JOptionPane.YES_NO_OPTION,
                        JOptionPane.QUESTION_MESSAGE,
                        null,     //do not use a custom Icon
                        options,  //the titles of buttons
                        options[0]); //default button title
                    if(n==JOptionPane.YES_OPTION){
                        currentuserDeleted = crudAdmin.delete((int) adminToDelete.get("id"));
                        if (currentuserDeleted) {
                            JOptionPane.showMessageDialog(guiAdmin, "Administrador eliminado, se cerrará sesión", "Aministrador Eliminado!", JOptionPane.INFORMATION_MESSAGE);
                            fillAdminList();
                            setDefault();  
                            ControllerMain.closeSession();
                            // guiAdmin.dispose();
                            //new ControllerGuiAdminLogin();
                        } else {
                            JOptionPane.showMessageDialog(guiAdmin, "No se pudo eliminar!", "Error!", JOptionPane.ERROR_MESSAGE);
                        }
                    }else
                        return;
                }
                else{ //borra una cuenta comun
                    currentuserDeleted = crudAdmin.delete((int) adminToDelete.get("id"));
                     if (currentuserDeleted) {
                        JOptionPane.showMessageDialog(guiAdmin, "Administrador eliminado exitosamente", "Aministrador Eliminado!", JOptionPane.INFORMATION_MESSAGE);
                        fillAdminList();
                        setDefault();  
                        //ControllerMain.closeSession();
                        // guiAdmin.dispose();
                        //new ControllerGuiAdminLogin();
                    } else {
                        JOptionPane.showMessageDialog(guiAdmin, "No se pudo eliminar!", "Error!", JOptionPane.ERROR_MESSAGE);
                    }
                    
                }         
            } catch (RemoteException ex) {
                Logger.getLogger(ControllerGuiCRUDAdmin.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        if (e.getSource().equals(guiAdmin.getBtnCreate())) { //modifica la gui para crear un nuevo administrador
            guiAdmin.getBtnDelete().setSelected(false);
            guiAdmin.getBtnModify().setSelected(false);
            guiAdmin.getBtnCreate().setSelected(true);
            guiAdmin.cleanFields();
            guiAdmin.setLblMessage("Crear nuevo administrador");
            guiAdmin.getTxtName().setEditable(true);
            guiAdmin.getPassField().setEditable(true);
            guiAdmin.getCheckBoxIsAdmin().setEnabled(true);
            guiAdmin.getComboBoxAdmins().setVisible(false);
            checkPermissions();
        }
        if (e.getSource().equals(guiAdmin.getBtnDelete())) { //modifica gui para eliminar
            guiAdmin.getBtnDelete().setSelected(true);
            guiAdmin.getBtnModify().setSelected(false);
            guiAdmin.getBtnCreate().setSelected(false);
            guiAdmin.setLblMessage("Desea borrar este administrador?");
            guiAdmin.cleanFields();
            guiAdmin.getTxtName().setText(guiAdmin.getComboBoxAdmins().getSelectedItem().toString());
            guiAdmin.getTxtName().setEditable(false);
            guiAdmin.getPassField().setEditable(false);
            guiAdmin.getCheckBoxIsAdmin().setEnabled(false);
            guiAdmin.getComboBoxAdmins().setVisible(true); 
            checkPermissions();
        }
        if (e.getSource().equals(guiAdmin.getBtnModify())) { //modifica gui para modificar administrador
            guiAdmin.getBtnDelete().setSelected(false);
            guiAdmin.getBtnModify().setSelected(true);
            guiAdmin.getBtnCreate().setSelected(false);
            guiAdmin.setLblMessage("Modificar: Nuevo nombre y contraseña");
            guiAdmin.cleanFields();
            guiAdmin.getTxtName().setText(guiAdmin.getComboBoxAdmins().getSelectedItem().toString());
            //guiAdmin.getTxtName().setText(currentAdmin.get("name").toString());
            guiAdmin.getTxtName().setEditable(true);
            guiAdmin.getPassField().setEditable(true);
            guiAdmin.getCheckBoxIsAdmin().setEnabled(false);
            guiAdmin.getComboBoxAdmins().setVisible(true);
            checkPermissions();
        }
    }
     
    
    private void fillAdminList(){
        guiAdmin.getComboBoxAdmins().removeAllItems();
        try {
            admins = crudAdmin.getAdmins();
        } catch (RemoteException ex) {
            Logger.getLogger(ControllerGuiCRUDAdmin.class.getName()).log(Level.SEVERE, null, ex);
        }
        if(admins == null)
            return;
        for (Map admin : admins) { //busco todos los admins para listarlos en el jcombobox
            guiAdmin.getComboBoxAdmins().addItem((String)admin.get("name"));
        }
    }
    
    private void checkPermissions(){
        if(!ControllerMain.isAdmin()){
            guiAdmin.getTxtName().setEditable(false);
            guiAdmin.getPassField().setEditable(false);
            guiAdmin.getCheckBoxIsAdmin().setEnabled(false);
            guiAdmin.getBtnConfirm().setEnabled(false);
            guiAdmin.getComboBoxAdmins().setEnabled(false);
            guiAdmin.getBtnCreate().setEnabled(false);
            guiAdmin.getBtnModify().setEnabled(false);
            guiAdmin.getBtnDelete().setEnabled(false);
        }
    }
    
    private void setDefault() {
        guiAdmin.getBtnDelete().setSelected(false);
        guiAdmin.getBtnModify().setSelected(false);
        guiAdmin.getBtnCreate().setSelected(false);
        guiAdmin.setLblMessage("Seleccione una opcion: Crear, modificar o borrar");
        guiAdmin.getTxtName().setEditable(false);
        guiAdmin.getPassField().setEditable(false);
        guiAdmin.getCheckBoxIsAdmin().setEnabled(false);
        guiAdmin.getComboBoxAdmins().setVisible(false);
        guiAdmin.cleanFields();
    }

    private boolean dataIsValid() { //retorna true si los campos no estan vacios

        return !guiAdmin.getTxtName().getText().equals("") && !(guiAdmin.getPassField().getPassword().length == 0);
    }

}
