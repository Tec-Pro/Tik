/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import gui.GuiCRUDUser;
import interfaces.InterfaceUser;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
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
import javax.swing.JFileChooser;
//import javax.swing.ImageIcon;
import java.io.File;
import java.io.IOException;
//import java.io.ByteArrayOutputStream;

import java.io.InputStream;
import java.io.OutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import javax.imageio.ImageIO;
import javax.swing.event.ListSelectionEvent;
import utils.Config;
import utils.ImageExtensions;
import utils.ImageFilter;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import utils.InterfaceName;
import utils.PhotoDialog;

/**
 *
 * @author xen
 */
public class ControllerGuiCRUDUser implements ActionListener {

    private PhotoDialog imgSelect;
    private final GuiCRUDUser guiUser;
    private final InterfaceUser crudUser;
    private final DefaultTableModel dtmUsers;
    private boolean modifyMode = false;
    private boolean createMode = false;

    public ControllerGuiCRUDUser(GuiCRUDUser gui) throws NotBoundException, MalformedURLException, RemoteException {
        crudUser = (InterfaceUser) Naming.lookup("//" + Config.ip + "/" + InterfaceName.CRUDUser);
        guiUser = gui;
        guiUser.getTableUsers().getSelectionModel().addListSelectionListener(new ListSelectionListener() { // Listener for moving through the tableUsers and refreshing the gui
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
                    if (createMode) {
                        //doNothing();
                    } else { //selectionMode or modifyMode
                        modifyMode = false;
                        guiUser.initialMode(true);
                    }
                }
            }
        });
        guiUser.getTableUsers().addMouseListener(new java.awt.event.MouseAdapter() { // Listener for double click and modify on tableUsers
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                if (evt.getClickCount() == 2) {
                    guiUser.getTableUsers().setEnabled(false);
                    modifyMode = true;
                    guiUser.modifyMode(modifyMode);
                    if (guiUser.getDateDischargedDate().getDate() == null) {
                        guiUser.enableDischarged(false);
                    } else {
                        guiUser.enableDischarged(true);
                    }
                }
            }
        });
        guiUser.getPanelPhoto().addMouseListener(new java.awt.event.MouseAdapter() { // Listener for the panelPhoto
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                if (evt.getClickCount() == 2) {
                    try {
                        panelPhotoDobleClicked(null);
                    } catch (IOException ex) {
                        Logger.getLogger(ControllerGuiCRUDUser.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (Exception ex) {
                        Logger.getLogger(ControllerGuiCRUDUser.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        });
        dtmUsers = guiUser.getDtmUsers();
        updateDtmUsers();
        guiUser.setActionListener(this);
        guiUser.initialMode(true);
    }

    private void panelPhotoDobleClicked(MouseEvent evt) throws IOException, Exception {
        if (createMode || modifyMode) { //If I'm either creating a new user or modifying an old one, I can change the photo
            int row = guiUser.getTableUsers().getSelectedRow(); // selected row
            int id = (int) dtmUsers.getValueAt(row, 0); // selected emplyee id
            Map<String, Object> mapPhoto = crudUser.getUser(id); // get emplyee information
            String name = (String) mapPhoto.get("name") + (String) mapPhoto.get("surname") + id; //name of the file
            imgSelect = new PhotoDialog(name);
            name = imgSelect.getName();
            imgSelect.dispose();
            System.out.println(name);
//                BufferedImage image = ImageIO.read(file); // I take the file and convert it to a BufferedImage
//                ImageExtensions imgEx = new ImageExtensions(); // I create an instance of ImageExtensions
//                String ext = imgEx.getExtension(file); // I extract the extension used
            mapPhoto = null; //initalize the map to use later on
            StandardCopyOption copyOption = StandardCopyOption.REPLACE_EXISTING;
            try {
                mapPhoto = crudUser.modifyPhoto(id, name);
            } catch (IOException e) {
                Logger.getLogger(ControllerGuiCRUDUser.class.getName()).log(Level.SEVERE, null, e);
            }
      
            if (mapPhoto == null) {
                JOptionPane.showMessageDialog(guiUser, "Ups! Error! Intente de nuevo!", "Error", JOptionPane.ERROR_MESSAGE);
            } else {
                loadSelectedUser(id);
            }
            
    }
}

private void tableUserMouseClicked(MouseEvent evt) throws RemoteException, Exception {
        if (createMode) {
            //doNothing();
        } else { //selectionMode or modifyMode
            if (!modifyMode) {
                guiUser.selectionMode(true);
            }
            int r = guiUser.getTableUsers().getSelectedRow();
            loadSelectedUser((int) guiUser.getDtmUsers().getValueAt(r, 0));
        }
    }

    public void loadSelectedUser(int id) throws RemoteException, Exception {
        Map<String, Object> user;
        user = crudUser.getUser(id);
        String p = (String) user.get("pass");
        byte[] pass = p.getBytes("UTF-8");
        if (user != null) {
            guiUser.updateFields(
                    (String) user.get("name"),
                    (String) user.get("surname"),
                    (String) "*******",
                    (Date) user.get("date_hired"),
                    (Date) user.get("date_discharged"),
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
                    (String) user.get("position"),
                    (String) user.get("photo"));
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
        while (it.hasNext()) {
            Map<String, Object> user = it.next();
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
        if (e.getSource().equals(guiUser.getBtnCreate())) { //If the button pressed is Create
            createMode = true;
            guiUser.cleanFields();
            guiUser.createMode(createMode);
            guiUser.enableDischarged(false);
        }
        
        if (e.getSource().equals(guiUser.getBtnSave()) && createMode) { // If the button pressed is Save and is in createMode
            if (!dataIsValid()) {
                JOptionPane.showMessageDialog(guiUser, "Nombre, Apellido, Contrasena y Posicion no pueden eestar vacios!", "Error!", JOptionPane.ERROR_MESSAGE);
            } else {
                try {
                    Map<String, Object> user;
                    Object photo = null;
                    if(guiUser.getPhoto() != null){
                        photo = guiUser.getPhoto().toString().getBytes();
                    }
                    user = crudUser.create(guiUser.getTxtName().getText(),
                            guiUser.getTxtSurname().getText(),
                            guiUser.getTxtPass().getText(),
                            guiUser.getDateHiredDate().getDate(),
                            guiUser.getDateDischargedDate().getDate(),
                            (String) guiUser.getBoxTurn().getSelectedItem(),
                            guiUser.getDateBirthDate().getDate(),
                            guiUser.getTxtPlaceOfBirth().getText(),
                            (String) guiUser.getBoxIdType().getSelectedItem(),
                            guiUser.getTxtIdNumber().getText(),
                            guiUser.getTxtAddress().getText(),
                            guiUser.getTxtHomePhone().getText(),
                            guiUser.getTxtEmergencyPhone().getText(),
                            guiUser.getTxtMobilePhone().getText(),
                            (String) guiUser.getBoxMaritalStatus().getSelectedItem(),
                            (String) guiUser.getBoxBloodType().getSelectedItem(),
                            (String) guiUser.getBoxPosition().getSelectedItem(),
                            (byte[]) photo);
                    if (user != null) {
                        JOptionPane.showMessageDialog(guiUser, "Nuevo usuario creado exitosamente!", "Usuario creado!", JOptionPane.INFORMATION_MESSAGE);
                        updateDtmUsers();
                        guiUser.cleanFields();
                    } else {
                        JOptionPane.showMessageDialog(guiUser, "Problemas! No se pudo crear!", "Error!", JOptionPane.ERROR_MESSAGE);
                    

}
                } catch (RemoteException ex) {
                    Logger.getLogger(ControllerGuiCRUDUser.class  

.getName()).log(Level.SEVERE, null, ex);
                }
                createMode = false;
                guiUser.initialMode(true);
                guiUser.enableDischarged(false);
            }
        }
        
        if (e.getSource().equals(guiUser.getBtnSave()) && modifyMode) { // If the button pressed is Save and is in modifyMode
            if (!dataIsValid()) {
                JOptionPane.showMessageDialog(guiUser, "Nombre, Apellido, Contrasena y Posicion no pueden estar vacios!", "Error!", JOptionPane.ERROR_MESSAGE);
            } else {
                try {
                    int row = guiUser.getTableUsers().getSelectedRow();
                    int id = (int) dtmUsers.getValueAt(row, 0);
                    Map<String, Object> user;
                    user = crudUser.modify(id,
                            guiUser.getTxtName().getText(),
                            guiUser.getTxtSurname().getText(),
                            guiUser.getTxtPass().getText(),
                            guiUser.getDateHiredDate().getDate(),
                            guiUser.getDateDischargedDate().getDate(),
                            (String) guiUser.getBoxTurn().getSelectedItem(),
                            guiUser.getDateBirthDate().getDate(),
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
                    if (user != null) {
                        JOptionPane.showMessageDialog(guiUser, "Usuario modificado con exito!", "Usuario creado!", JOptionPane.INFORMATION_MESSAGE);
                        updateDtmUsers();
                    } else {
                        JOptionPane.showMessageDialog(guiUser, "Problemas! No se pudo modificar!", "Error!", JOptionPane.ERROR_MESSAGE);
                    

}
                } catch (RemoteException ex) {
                    Logger.getLogger(ControllerGuiCRUDUser.class  

.getName()).log(Level.SEVERE, null, ex);
                }
                modifyMode = false;
                guiUser.initialMode(true);
                guiUser.enableDischarged(false);
                guiUser.getTableUsers().setEnabled(true);
            }
        }

        if (e.getSource().equals(guiUser.getBtnModify())) { //If the button pressed is Modify
            guiUser.getTableUsers().setEnabled(false);
            modifyMode = true;
            guiUser.modifyMode(modifyMode);
            if(guiUser.getDateDischargedDate().getDate() == null){
                guiUser.enableDischarged(false);             
            }else{
                guiUser.enableDischarged(true);
            }
        }
        
        if (e.getSource().equals(guiUser.getBtnDelete()) && !modifyMode && !createMode) { //If the button pressed is Delete and I'm not in modifyMode or createMode
            try {
                int option = JOptionPane.showConfirmDialog(null, "Estas seguro que desea borrar el empleado: ", "Warning", JOptionPane.YES_NO_OPTION);
                if (option == 0) {
                    int row = guiUser.getTableUsers().getSelectedRow();
                    boolean user;
                    user = crudUser.delete((int) dtmUsers.getValueAt(row, 0));
                    if (user == true) {
                        JOptionPane.showMessageDialog(guiUser, "Usuario borrado exitosamente!", "Usuario borrado!", JOptionPane.INFORMATION_MESSAGE);
                        updateDtmUsers();
                    } else {
                        JOptionPane.showMessageDialog(guiUser, "Problemas! No se pudo borrar!", "Error!", JOptionPane.ERROR_MESSAGE);
                    

}
                }
            } catch (RemoteException ex) {
                Logger.getLogger(ControllerGuiCRUDUser.class  

.getName()).log(Level.SEVERE, null, ex);

            }
        }

        if (e.getSource().equals(guiUser.getBtnDelete()) && createMode) { //If the button pressed is Delete and I'm in createMode (Cancel)
            createMode = false;
            guiUser.initialMode(true);
            guiUser.getCheckBoxDischarged().setSelected(false);
        }

        if (e.getSource().equals(guiUser.getBtnDelete()) && modifyMode) { //If the button pressed is Delete and I'm in modifyMode (Cancel)
            modifyMode = false;
            guiUser.initialMode(true);
            guiUser.getCheckBoxDischarged().setSelected(false);
            guiUser.getTableUsers().setEnabled(true);
        }
        
        if (e.getSource().equals(guiUser.getCheckBoxDischarged())){
            if(guiUser.getCheckBoxDischarged().isSelected()){
                guiUser.enableDischarged(true);
            }else{
                guiUser.enableDischarged(false);
            }
        }
    }
}
