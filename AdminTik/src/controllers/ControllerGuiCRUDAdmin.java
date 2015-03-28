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
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.Map;
import javax.swing.JOptionPane;


/**
 *
 * @author agustin
 */
public class ControllerGuiCRUDAdmin implements ActionListener {
    
    private final GuiCRUDAdmin guiAdmin;
    private final InterfaceAdmin crudAdmin;
    private final Map<String,Object> currentAdmin;
    
    public ControllerGuiCRUDAdmin(Map<String,Object> admin) throws NotBoundException, MalformedURLException, RemoteException{
        currentAdmin = admin;
        crudAdmin = (InterfaceAdmin)   Naming.lookup("//192.168.1.26/crudAdmin");
        guiAdmin = new GuiCRUDAdmin();
        guiAdmin.setActionListener(this);
        guiAdmin.setVisible(true);
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource().equals(guiAdmin.getBtnConfirm()) && guiAdmin.getBtnCreate().isSelected()){
            if(!dataIsValid()){
                JOptionPane.showMessageDialog(guiAdmin, "Los campos no pueden estar vacios!", "Error!", JOptionPane.ERROR_MESSAGE);
            } else {
                try {
                    Map<String,Object> newUser;
                    newUser = crudAdmin.create(guiAdmin.getTxtName().getText(),guiAdmin.getPassField().getText());                 
                    if(newUser != null)
                        JOptionPane.showMessageDialog(guiAdmin, "Nuevo administrador creado exitosamente!", "Administrador creado!", JOptionPane.INFORMATION_MESSAGE);
                    else
                        JOptionPane.showMessageDialog(guiAdmin, "El nombre ya existe!", "Error!", JOptionPane.ERROR_MESSAGE);
                } catch (RemoteException ex) {
                    Logger.getLogger(ControllerGuiCRUDAdmin.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        if(e.getSource().equals(guiAdmin.getBtnConfirm()) && guiAdmin.getBtnModify().isSelected()){     
            if(!dataIsValid()) {
                JOptionPane.showMessageDialog(guiAdmin, "Los campos no pueden estar vacios!", "Error!", JOptionPane.ERROR_MESSAGE);
            } else {
                try {
                    Map<String,Object> modifiedUser;           
                    modifiedUser = crudAdmin.modify((int)currentAdmin.get("id"), guiAdmin.getTxtName().getText(),guiAdmin.getPassField().getText());

                    if(modifiedUser != null)
                        JOptionPane.showMessageDialog(guiAdmin, "Datos modificados correctamente!", "Modificacion exitosa!", JOptionPane.INFORMATION_MESSAGE);
                    else
                        JOptionPane.showMessageDialog(guiAdmin, "No se pudo modificar!", "Error!", JOptionPane.ERROR_MESSAGE);
                } catch (RemoteException ex) {
                    Logger.getLogger(ControllerGuiCRUDAdmin.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
          
        }
        if(e.getSource().equals(guiAdmin.getBtnConfirm()) && guiAdmin.getBtnDelete().isSelected()){          
            try {          
                boolean userDeleted = crudAdmin.delete((int)currentAdmin.get("id"));
                
                if(userDeleted){
                    JOptionPane.showMessageDialog(guiAdmin, "Administrador eliminado, se cerrará sesión", "Aministrador Eliminado!", JOptionPane.INFORMATION_MESSAGE);
                    guiAdmin.dispose();
                    new ControllerGuiAdminLogin();
                }else
                    JOptionPane.showMessageDialog(guiAdmin, "No se pudo eliminar!", "Error!", JOptionPane.ERROR_MESSAGE);
            } catch (RemoteException ex) {
                 Logger.getLogger(ControllerGuiCRUDAdmin.class.getName()).log(Level.SEVERE, null, ex);
            } catch (NotBoundException ex) {
                Logger.getLogger(ControllerGuiCRUDAdmin.class.getName()).log(Level.SEVERE, null, ex);
            } catch (MalformedURLException ex) {
                Logger.getLogger(ControllerGuiCRUDAdmin.class.getName()).log(Level.SEVERE, null, ex);
            }
          
        }
        if(e.getSource().equals(guiAdmin.getBtnCreate())){
            guiAdmin.getBtnDelete().setSelected(false);
            guiAdmin.getBtnModify().setSelected(false);
            guiAdmin.getBtnCreate().setSelected(true);
            guiAdmin.cleanFields();
            guiAdmin.setLblMessage("Crear nuevo administrador");
            guiAdmin.getTxtName().setEditable(true);
        }
        if(e.getSource().equals(guiAdmin.getBtnDelete())){
            guiAdmin.getBtnDelete().setSelected(true);
            guiAdmin.getBtnModify().setSelected(false);
            guiAdmin.getBtnCreate().setSelected(false);
            guiAdmin.setLblMessage("Desea borrar este administrador?");
            guiAdmin.cleanFields();
            guiAdmin.getTxtName().setText(currentAdmin.get("name").toString());
            guiAdmin.getTxtName().setEditable(false);
        }
        if(e.getSource().equals(guiAdmin.getBtnModify())){
            guiAdmin.getBtnDelete().setSelected(false);
            guiAdmin.getBtnModify().setSelected(true);
            guiAdmin.getBtnCreate().setSelected(false);       
            guiAdmin.setLblMessage("Modificar: Nuevo nombre y contraseña");
            guiAdmin.cleanFields();
            guiAdmin.getTxtName().setText(currentAdmin.get("name").toString());
            guiAdmin.getTxtName().setEditable(true);
        }
    }
    
    private boolean dataIsValid(){
        
        return !guiAdmin.getTxtName().getText().equals("") && !(guiAdmin.getPassField().getPassword().length == 0);
    }
    
}
