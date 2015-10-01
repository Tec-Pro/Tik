/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import controllers.statistics.ControllerGuiProductList;
import gui.GuiCRUDUser;
import interfaces.InterfacePresence;
import interfaces.InterfaceUser;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
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
import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.view.JasperViewer;
import reports.purchaseStatistics.PproductStatistic;
import reports.userReport.UserData;
import utils.InterfaceName;
import utils.SerializableBufferedImage;

/**
 *
 * @author xen
 */
public class ControllerGuiCRUDUser implements ActionListener {

    private final GuiCRUDUser guiUser;
    private final InterfacePresence crudPresence;
    private final InterfaceUser crudUser;
    private final DefaultTableModel dtmUsers;
    private final DefaultTableModel presencesTbl;

    private boolean modifyMode = false;
    private boolean createMode = false;

    public ControllerGuiCRUDUser(GuiCRUDUser gui) throws NotBoundException, MalformedURLException, RemoteException {
        crudUser = (InterfaceUser) InterfaceName.registry.lookup(InterfaceName.CRUDUser);
        crudPresence = (InterfacePresence) InterfaceName.registry.lookup(InterfaceName.CRUDPresence);

        guiUser = gui;
        guiUser.getTableUsers().getSelectionModel().addListSelectionListener(new ListSelectionListener() { // Listener for moving through the tableUsers and refreshing the gui
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if (guiUser.getTableUsers().getSelectedRow() != -1) {
                    try {
                        tableUserMouseClicked(null);
                        guiUser.getBtnPrintReport().setEnabled(true);
                    } catch (RemoteException ex) {
                        Logger.getLogger(ControllerGuiCRUDUser.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (Exception ex) {
                        Logger.getLogger(ControllerGuiCRUDUser.class.getName()).log(Level.SEVERE, null, ex);
                    }
                } else {
                    guiUser.getBtnPrintReport().setEnabled(false);
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
        dtmUsers = guiUser.getDtmUsers();
        presencesTbl= guiUser.getPresencesTbl();
        updateDtmUsers();
        if(ControllerMain.isAdmin())
            guiUser.setActionListener(this);
        guiUser.initialMode(true);
        guiUser.getDateFrom().addPropertyChangeListener(new PropertyChangeListener() {
            @Override
            public void propertyChange(PropertyChangeEvent evt) {
                int r = guiUser.getTableUsers().getSelectedRow();
                try {
                    loadPresences((int) guiUser.getDtmUsers().getValueAt(r, 0), utils.Dates.dateToMySQLDate(guiUser.getDateFrom().getDate(), false), utils.Dates.dateToMySQLDate(guiUser.getDateTo().getDate(), false));
                } catch (RemoteException ex) {
                    Logger.getLogger(ControllerGuiCRUDUser.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
        guiUser.getDateTo().addPropertyChangeListener(new PropertyChangeListener() {
            @Override
            public void propertyChange(PropertyChangeEvent evt) {
                int r = guiUser.getTableUsers().getSelectedRow();
                try {
                    loadPresences((int) guiUser.getDtmUsers().getValueAt(r, 0), utils.Dates.dateToMySQLDate(guiUser.getDateFrom().getDate(), false), utils.Dates.dateToMySQLDate(guiUser.getDateTo().getDate(), false));
                } catch (RemoteException ex) {
                    Logger.getLogger(ControllerGuiCRUDUser.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
    }

    private void addPhoto() throws IOException, Exception {
        if (createMode || modifyMode) { //If I'm either creating a new user or modifying an old one, I can change the photo
            final JFileChooser fc = new JFileChooser();
            fc.setAcceptAllFileFilterUsed(false);
            fc.addChoosableFileFilter(new ImageFilter());
            int returnVal = fc.showOpenDialog(guiUser); // user chooses the image
            if (returnVal == JFileChooser.APPROVE_OPTION) {
                File file = fc.getSelectedFile(); //I get the selected File

                BufferedImage image = ImageIO.read(file); // I take the file and convert it to a BufferedImage
                guiUser.setPicture(image);
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
            Calendar cal = Calendar.getInstance();
            cal.set(Calendar.DAY_OF_MONTH, 1);
            guiUser.getDateFrom().setCalendar(cal);
            cal.set(Calendar.DAY_OF_MONTH, cal.getActualMaximum(Calendar.DAY_OF_MONTH));
            guiUser.getDateTo().setCalendar(cal);
        }
    }

    public void loadSelectedUser(int id) throws RemoteException, Exception {
        Map<String, Object> user;
        user = crudUser.getUser(id);
        SerializableBufferedImage imgSerializable = crudUser.getPhoto(id);
        BufferedImage img = null;
        if (imgSerializable != null) {
            img = imgSerializable.getBImage();
        }
        String p = (String) user.get("pass");
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
                    (String) user.get("position")
            );
            guiUser.setPicture(img);

        } else {
            JOptionPane.showMessageDialog(guiUser, "Ups! Error! Intente de nuevo!", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void loadPresences(int id, String from, String to) throws RemoteException {
        List<Map> presences = crudPresence.getPresences(id, from, to);
        Iterator<Map> it = presences.iterator();
        presencesTbl.setRowCount(0);
        while (it.hasNext()) {
            Map<String, Object> presence = it.next();
            Object rowDtm[] = new Object[6];
            rowDtm[0] = presence.get("id");
            rowDtm[1] = utils.Dates.dateToMySQLDate((Date) presence.get("day"), true);
            rowDtm[2] = presence.get("entry_time");
            if (!presence.get("departure_time").toString().equals("00:00:00")) {
                rowDtm[3] = utils.Dates.dateToMySQLDate((Date) presence.get("departure_day"), true);
                rowDtm[4] = presence.get("departure_time");
                rowDtm[5]=diferenciaFechas(presence.get("day")+" "+presence.get("entry_time"), presence.get("departure_day")+" "+presence.get("departure_time"));
            } else {
                rowDtm[3] = "";
                rowDtm[4] = "";
            }
            presencesTbl.addRow(rowDtm);
            
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
                    SerializableBufferedImage bufser = null;
                    if (guiUser.getImage() != null) {
                        bufser = new SerializableBufferedImage(guiUser.getImage());
                    }
                    Map<String, Object> user;
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
                            bufser
                    );
                    if (user != null) {
                        JOptionPane.showMessageDialog(guiUser, "Nuevo usuario creado exitosamente!", "Usuario creado!", JOptionPane.INFORMATION_MESSAGE);
                        updateDtmUsers();
                        guiUser.cleanFields();
                    } else {
                        JOptionPane.showMessageDialog(guiUser, "Problemas! No se pudo crear!", "Error!", JOptionPane.ERROR_MESSAGE);
                    }
                } catch (RemoteException ex) {
                    Logger.getLogger(ControllerGuiCRUDUser.class.getName()).log(Level.SEVERE, null, ex);
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
                    SerializableBufferedImage bufser = null;
                    if (guiUser.getImage() != null) {
                        bufser = new SerializableBufferedImage(guiUser.getImage());
                    }

                    Map<String, Object> user;
                    user = crudUser.modify(
                            id,
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
                            (String) guiUser.getBoxPosition().getSelectedItem(),
                            bufser
                    );
                    if (user != null) {
                        JOptionPane.showMessageDialog(guiUser, "Usuario modificado con exito!", "Usuario creado!", JOptionPane.INFORMATION_MESSAGE);
                        updateDtmUsers();
                    } else {
                        JOptionPane.showMessageDialog(guiUser, "Problemas! No se pudo modificar!", "Error!", JOptionPane.ERROR_MESSAGE);
                    }
                } catch (RemoteException ex) {
                    Logger.getLogger(ControllerGuiCRUDUser.class.getName()).log(Level.SEVERE, null, ex);
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
            if (guiUser.getDateDischargedDate().getDate() == null) {
                guiUser.enableDischarged(false);
            } else {
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
                Logger.getLogger(ControllerGuiCRUDUser.class.getName()).log(Level.SEVERE, null, ex);

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

        if (e.getSource().equals(guiUser.getCheckBoxDischarged())) {
            if (guiUser.getCheckBoxDischarged().isSelected()) {
                guiUser.enableDischarged(true);
            } else {
                guiUser.enableDischarged(false);
            }
        }

        if (e.getSource().equals(guiUser.getBtnAddPhoto())) {
            try {
                addPhoto();
            } catch (Exception ex) {
                Logger.getLogger(ControllerGuiCRUDUser.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        if (e.getSource().equals(guiUser.getBtnDeletePhoto())) {
            guiUser.setPictureDefault();
        }
        if (e.getSource().equals(guiUser.getBtnPrintReport())) {
            List<UserData> listA = new ArrayList();
            for (int i = 0; i < guiUser.getTableEmployeeSchedule().getRowCount(); i++) {
                UserData pp = new UserData(guiUser.getTableEmployeeSchedule().getValueAt(i, 1),
                        guiUser.getTableEmployeeSchedule().getValueAt(i, 2),
                        guiUser.getTableEmployeeSchedule().getValueAt(i, 3),
                guiUser.getTableEmployeeSchedule().getValueAt(i, 4),
                guiUser.getTableEmployeeSchedule().getValueAt(i, 5));
                listA.add(pp);
            }

            try {
                JasperReport reporte = (JasperReport) JRLoader.loadObject(getClass().getResource("/reports/userReport/userReport.jasper"));//cargo el reporte
                JasperPrint jasperPrint;
                Map<String, Object> parametros = new HashMap<String, Object>();
                parametros.put("nombre", guiUser.getTxtName().getText()+" "+guiUser.getTxtSurname().getText());
                jasperPrint = JasperFillManager.fillReport(reporte, parametros, new JRBeanCollectionDataSource(listA));
                JasperViewer.viewReport(jasperPrint, false);
            } catch (JRException ex) {
                Logger.getLogger(ControllerGuiProductList.class.getName()).log(Level.SEVERE, null, ex);
            }
            
        }
    }
    
     public String diferenciaFechas(String inicio, String llegada){

        Date fechaInicio = null;
        Date fechaLlegada = null;

        // configuramos el formato en el que esta guardada la fecha en 
        //  los strings que nos pasan
        SimpleDateFormat formato = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        try {
            // aca realizamos el parse, para obtener objetos de tipo Date de 
            // las Strings
            fechaInicio = formato.parse(inicio);
            fechaLlegada = formato.parse(llegada);

        } catch (ParseException e) {
           // Log.e(TAG, "Funcion diferenciaFechas: Error Parse " + e);
        } catch (Exception e){
            // Log.e(TAG, "Funcion diferenciaFechas: Error " + e);
        }

        // tomamos la instancia del tipo de calendario
        Calendar calendarInicio = Calendar.getInstance();
        Calendar calendarFinal = Calendar.getInstance();

        // Configramos la fecha del calendatio, tomando los valores del date que 
        // generamos en el parse
        calendarInicio.setTime(fechaInicio);
        calendarFinal.setTime(fechaLlegada);

        // obtenemos el valor de las fechas en milisegundos
        long milisegundos1 = calendarInicio.getTimeInMillis();
        long milisegundos2 = calendarFinal.getTimeInMillis();

        // tomamos la diferencia
        long diferenciaMilisegundos = milisegundos2 - milisegundos1;

        // Despues va a depender en que formato queremos  mostrar esa 
        // diferencia, minutos, segundo horas, dias, etc, aca van algunos 
        // ejemplos de conversion

        // calcular la diferencia en segundos
        long diffSegundos =  Math.abs (diferenciaMilisegundos / 1000);

        // calcular la diferencia en minutos
        long diffMinutos =  Math.abs (diferenciaMilisegundos / (60 * 1000));
        long restominutos = diffMinutos%60;

        // calcular la diferencia en horas
        long diffHoras =   (diferenciaMilisegundos / (60 * 60 * 1000));

        // calcular la diferencia en dias
        long diffdias = Math.abs ( diferenciaMilisegundos / (24 * 60 * 60 * 1000) );

        // devolvemos el resultado en un string
        return String.valueOf(diffHoras + " hs " + restominutos + " min");
    }
}
