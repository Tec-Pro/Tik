/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import gui.GuiCRUDUser;
import gui.GuiFrameUser;
import interfaces.InterfaceUser;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.util.Map;
import java.util.Iterator;
import java.util.Date;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.net.MalformedURLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.table.DefaultTableModel;
import javax.swing.JOptionPane;
import javax.swing.event.ListSelectionListener;
import java.util.Calendar;
import javax.swing.event.ListSelectionEvent;

/**
 *
 * @author xen
 */
public class ControllerGuiCRUDUser implements ActionListener{
    
    private final GuiFrameUser guiFrame;
    private final GuiCRUDUser guiUser;
    private final InterfaceUser crudUser;
    private final DefaultTableModel dtmUsers;
    
   public ControllerGuiCRUDUser(GuiFrameUser frame, GuiCRUDUser gui) throws NotBoundException, MalformedURLException, RemoteException{
        crudUser = (InterfaceUser) Naming.lookup("//localhost/crudUser");
        guiFrame = frame;
        guiUser = gui;
        guiUser.getTableUsers().getSelectionModel().addListSelectionListener(new ListSelectionListener(){
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if (guiUser.getTableUsers().getSelectedRow() != -1) {

                    try {
                        tableUserMouseClicked(null);
                    } catch (RemoteException ex) {
                        Logger.getLogger(ControllerGuiCRUDUser.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (Exception ex) {
                        Logger.getLogger(ControllerGuiCRUDUser.class.getName()).log(Level.SEVERE, null, ex);
                    }
                } else {
                    guiUser.cleanFields();
                    guiUser.modifyMode(false);
                }  
            }
        });
        dtmUsers = guiUser.getDtmUsers();
        updateDtmUsers();
        
        guiUser.setVisible(true);
        guiFrame.getjDesktopPane1().add(guiUser);
        guiFrame.setVisible(true);
        guiUser.setActionListener(this);
        guiUser.cleanFields();
        guiUser.modifyMode(false);
    }
    
    private void tableUserMouseClicked(MouseEvent evt) throws RemoteException, Exception {
        int r = guiUser.getTableUsers().getSelectedRow();
        loadSelectedUser((int) guiUser.getDtmUsers().getValueAt(r, 0));
        guiUser.modifyMode(true);
    }
    
    public void loadSelectedUser(int id) throws RemoteException, Exception {
        Map<String,Object> user;
        user = crudUser.getUser(id);
        String p = (String) user.get("pass");
        byte[] pass = p.getBytes("UTF-8");
        if (user != null) {
            guiUser.updateFields(
                (String) user.get("name"),
                (String) user.get("surname"),
                (String) "******", 
                (Date) user.get("entry_date"),
                (Date) user.get("exit_date"),
                (String) user.get("turn"),
                (Date) user.get("date_of_birth"),
                (String) user.get("place_of_birth"),
                (String) user.get("id_type"),
                (String) user.get("id_number"),
                (String) user.get("address"),
                (String) user.get("home_phone"),
                (String) user.get("emergency_phone"),
                (String) user.get("mobile_phone"),
                (String) user.get("marital_status"),
                (String) user.get("blood_type"),
                (String) user.get("position"));
        } else {
            JOptionPane.showMessageDialog(guiUser, "Ups! Error! Intente de nuevo!", "Error", JOptionPane.ERROR_MESSAGE);
        }        
    }
        
    private boolean dataIsValid() {
        return !(guiUser.getTxtName().getText().equals("") || guiUser.getTxtSurname().getText().equals("") || guiUser.getTxtPass().getText().equals("") || guiUser.getBoxPosition().getSelectedItem().equals(""));
        // Name, Surname, Pass and Position cannot be empty
    }
    
    private void updateDtmUsers() throws RemoteException {
        dtmUsers.setRowCount(0);
        Iterator<Map> it = crudUser.getUsers().iterator();
        while (it.hasNext()){
            Map<String,Object> user = it.next();
            Object rowDtm[] = new Object[4];
            rowDtm[0] = user.get("id");
            rowDtm[1] = user.get("name");
            rowDtm[2] = user.get("surname");
            rowDtm[3] = user.get("position");            
            dtmUsers.addRow(rowDtm);                       
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource().equals(guiUser.getBtnCreate())){ //If the button pressed is Create
            if(!dataIsValid()){
                JOptionPane.showMessageDialog(guiUser, "Nombre, Apellido, Contrasena y Posicion no pueden eestar vacios!", "Error!", JOptionPane.ERROR_MESSAGE);
            } else{
                try {
                    Map<String,Object> user;
                    user = crudUser.create(guiUser.getTxtName().getText(),
                            guiUser.getTxtSurname().getText(),
                            guiUser.getTxtPass().getText(),
                            Calendar.getInstance().getTime(),
                            Calendar.getInstance().getTime(),
                            (String) guiUser.getBoxTurn().getSelectedItem(),
                            Calendar.getInstance().getTime(),
                            guiUser.getTxtPlaceOfBirth().getText(),
                            (String) guiUser.getBoxIdType().getSelectedItem(),
                            guiUser.getTxtIdNumber().getText(),
                            guiUser.getTxtAddress().getText(),
                            guiUser.getTxtHomePhone().getText(),
                            guiUser.getTxtEmergencyPhone().getText(),
                            guiUser.getTxtMobilePhone().getText(),
                            (String) guiUser.getBoxMaritalStatus().getSelectedItem(),
                            (String) guiUser.getBoxBloodType().getSelectedItem(),
                            (String) guiUser.getBoxPosition().getSelectedItem());
                    if(user != null){
                        JOptionPane.showMessageDialog(guiUser, "Nuevo usuario creado exitosamente!", "Usuario creado!", JOptionPane.INFORMATION_MESSAGE);
                        updateDtmUsers();
                        guiUser.cleanFields();
                    }else{
                        JOptionPane.showMessageDialog(guiUser, "Problemas! No se pudo crear!", "Error!", JOptionPane.ERROR_MESSAGE);
                    }
                } catch (RemoteException ex){
                    Logger.getLogger(ControllerGuiCRUDUser.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        if(e.getSource().equals(guiUser.getBtnModify())){ //If the button pressed is Modify
            if(!dataIsValid()){
                JOptionPane.showMessageDialog(guiUser, "Nombre, Apellido, Contrasena y Posicion no pueden estar vacios!", "Error!", JOptionPane.ERROR_MESSAGE);
            } else{
                try {
                    int row = guiUser.getTableUsers().getSelectedRow();
                    int id = (int) dtmUsers.getValueAt(row, 0);
                    Map<String,Object> user;
                    user = crudUser.modify(id,
                            guiUser.getTxtName().getText(),
                            guiUser.getTxtSurname().getText(),
                            guiUser.getTxtPass().getText(),
                            Calendar.getInstance().getTime(),
                            Calendar.getInstance().getTime(),
                            (String) guiUser.getBoxTurn().getSelectedItem(),
                            Calendar.getInstance().getTime(),
                            guiUser.getTxtPlaceOfBirth().getText(),
                            (String) guiUser.getBoxIdType().getSelectedItem(),
                            guiUser.getTxtIdNumber().getText(),
                            guiUser.getTxtAddress().getText(),
                            guiUser.getTxtHomePhone().getText(),
                            guiUser.getTxtEmergencyPhone().getText(),
                            guiUser.getTxtMobilePhone().getText(),
                            (String) guiUser.getBoxMaritalStatus().getSelectedItem(),
                            (String) guiUser.getBoxBloodType().getSelectedItem(),
                            (String) guiUser.getBoxPosition().getSelectedItem());
                    if(user != null){
                        JOptionPane.showMessageDialog(guiUser, "Usuario modificado con exito!", "Usuario creado!", JOptionPane.INFORMATION_MESSAGE);
                        updateDtmUsers();
                    }else{
                        JOptionPane.showMessageDialog(guiUser, "Problemas! No se pudo modificar!", "Error!", JOptionPane.ERROR_MESSAGE);
                    }
                } catch (RemoteException ex){
                    Logger.getLogger(ControllerGuiCRUDUser.class.getName()).log(Level.SEVERE, null, ex);
                }
            }       
        }
        if(e.getSource().equals(guiUser.getBtnDelete())){ //If the button pressed is Delete
            if(!dataIsValid()){
                JOptionPane.showMessageDialog(guiUser, "Nombre, Apellido, Contrasena y Posicion no pueden eestar vacios!", "Error!", JOptionPane.ERROR_MESSAGE);
            } else{
                try {
                    int row = guiUser.getTableUsers().getSelectedRow();
                    boolean user;
                    user = crudUser.delete( (int) dtmUsers.getValueAt(row, 0));
                    if(user == true){
                        JOptionPane.showMessageDialog(guiUser, "Usuario borrado exitosamente!", "Usuario borrado!", JOptionPane.INFORMATION_MESSAGE);
                        updateDtmUsers();
                    }else{
                        JOptionPane.showMessageDialog(guiUser, "Problemas! No se pudo borrar!", "Error!", JOptionPane.ERROR_MESSAGE);
                    }
                } catch (RemoteException ex){
                    Logger.getLogger(ControllerGuiCRUDUser.class.getName()).log(Level.SEVERE, null, ex);
                }
            }       
        }
    }
    
}
