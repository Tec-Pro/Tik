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
import java.util.Map;
import java.util.Iterator;
import java.util.List;
import java.util.Date;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.net.MalformedURLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.JOptionPane;
import java.text.SimpleDateFormat;

/**
 *
 * @author xen
 */
public class ControllerGuiCRUDUser implements ActionListener{
    
    private final GuiCRUDUser guiUser;
    private final InterfaceUser crudUser;
    private final Map<String,Object> currentAdmin;
    private final JTable tableUsers;
    private final DefaultTableModel dtmUsers;
    private final List<Map> listUsers;
    
    public ControllerGuiCRUDUser(Map<String,Object> admin, Map<String,Object> user, GuiCRUDUser gui) throws NotBoundException, MalformedURLException, RemoteException{
        currentAdmin = admin;
        crudUser = (InterfaceUser) Naming.lookup("//192.168.1.103/crudUser");
        
        guiUser = gui;
        guiUser.cleanFields();
        tableUsers = guiUser.getTableUsers();
        tableUsers.addMouseListener(new java.awt.event.MouseAdapter(){
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tablaMouseClicked(evt);
            }
        });
        
        dtmUsers = guiUser.getDtmUsers();
        listUsers = crudUser.getUsers();
        updateDtmUsers();
        guiUser.setActionListener(this);
        guiUser.setVisible(true);
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource().equals(guiUser.getBtnCreate())){ //If the button pressed is Create
            if(!dataIsValid()){
                JOptionPane.showMessageDialog(guiUser, "Nombre, Apellido, Contrasena y Posicion no pueden eestar vacios!", "Error!", JOptionPane.ERROR_MESSAGE);
            } else
                try {
                    
                    Map<String,Object> user;
                    user = crudUser.create(guiUser.getTxtName().getText(),
                            guiUser.getTxtSurname().getText(),
                            guiUser.getTxtPass().getText(),
                            stringToDate(guiUser.getTxtEntryDate().getText()),
                            stringToDate(guiUser.getTxtExitDate().getText()),
                            guiUser.getTxtTurn().getText(),
                            stringToDate(guiUser.getTxtDateOfBirth().getText()),
                            guiUser.getTxtPlaceOfBirth().getText(),
                            guiUser.getTxtIdType().getText(),
                            guiUser.getTxtIdNumber().getText(),
                            guiUser.getTxtAddress().getText(),
                            guiUser.getTxtHomePhone().getText(),
                            guiUser.getTxtEmergencyPhone().getText(),
                            guiUser.getTxtMobilePhone().getText(),
                            guiUser.getTxtMaritalStatus().getText(),
                            guiUser.getTxtBloodType().getText(),
                            guiUser.getTxtPosition().getText());
                    if(user != null){
                        JOptionPane.showMessageDialog(guiUser, "Nuevo usuario creado exitosamente!", "Usuario creado!", JOptionPane.INFORMATION_MESSAGE);
                    }else{
                        JOptionPane.showMessageDialog(guiUser, "Problemas! No se pudo crear!", "Error!", JOptionPane.ERROR_MESSAGE);
                    }
                } catch (RemoteException ex){
                    Logger.getLogger(ControllerGuiCRUDAdmin.class.getName()).log(Level.SEVERE, null, ex);
                } catch (java.text.ParseException ex) {
                Logger.getLogger(ControllerGuiCRUDUser.class.getName()).log(Level.SEVERE, null, ex);
                
            }
        }
    }
    
    public void tablaMouseClicked(java.awt.event.MouseEvent evt){ // I update the fields in GuiCRUDUser
        int selectedRow = tableUsers.getSelectedRow();
        int userID = (int) tableUsers.getValueAt(selectedRow,0); // IdNumber of the selected User
        try {
            Map<String,Object> user;
            user = crudUser.getUser(userID);
            if(user != null){
                guiUser.updateFields(
                (String) user.get("name"),
                (String) user.get("surname"),
                (String) user.get("pass"),
                (String) user.get("entry_date").toString(),
                (String) user.get("exit_date").toString(),
                (String) user.get("turn"),
                (String) user.get("date_of_birth").toString(),
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
            }
        } catch (RemoteException ex) {
            Logger.getLogger(ControllerGuiCRUDUser.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private void updateDtmUsers() {
        dtmUsers.setRowCount(0);
        Iterator<Map> it = listUsers.iterator();
        while (it.hasNext()){
            Map<String,Object> user = it.next();
            Object rowDtm[] = new Object[18];
            rowDtm[0] = user.get("id");
            rowDtm[1] = user.get("name");
            rowDtm[2] = user.get("surname");
            rowDtm[3] = user.get("pass");
            rowDtm[4] = user.get("entry_date").toString();
            rowDtm[5] = user.get("exit_date").toString();
            rowDtm[6] = user.get("turn");
            rowDtm[7] = user.get("date_of_birth").toString();
            rowDtm[8] = user.get("place_of_birth");
            rowDtm[9] = user.get("id_type");
            rowDtm[10] = user.get("id_number");
            rowDtm[11] = user.get("address");
            rowDtm[12] = user.get("home_phone");
            rowDtm[13] = user.get("emergency_phone");
            rowDtm[14] = user.get("mobile_phone");
            rowDtm[15] = user.get("marital_status");
            rowDtm[16] = user.get("blood_type");
            rowDtm[17] = user.get("position");
            
            dtmUsers.addRow(rowDtm);
            
            //Object rowTable[] = new Object[5];
            //rowTable[0] = user.get("id");
            //rowTable[1] = user.get("name");
            //rowTable[2] = user.get("surname");
            //rowTable[3] = user.get("position");
            
        }
    }
    private Date stringToDate(String stringDate) throws java.text.ParseException{
        SimpleDateFormat ft = new SimpleDateFormat ("yyyy-MM-dd");   
        
        Date date = new Date();
        date = ft.parse(stringDate); //I parse the string to the established date format
        
        return date;
    }

    private boolean dataIsValid() {
        return !(guiUser.getTxtName().getText().equals("") || guiUser.getTxtSurname().getText().equals("") || guiUser.getTxtPass().getText().equals("") || guiUser.getTxtPosition().getText().equals(""));
        // Name, Surname, Pass and Position cannot be empty
    }
    
}
