/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import com.toedter.calendar.JDateChooser;
import java.awt.BorderLayout;
import java.awt.event.ActionListener;
import java.util.Date;
import javax.swing.JComboBox;
import javax.swing.table.DefaultTableModel;
import javax.swing.ImageIcon;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
//import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.File;
import java.util.Calendar;
import javax.swing.JButton;
import javax.swing.JFrame;
import org.edisoncor.gui.panel.PanelImage;

/**
 *
 * @author xen
 */
public class GuiCRUDUser extends javax.swing.JInternalFrame {

    private final DefaultTableModel dtmUsers;
    private String namePicture = "sin_imagen_disponible.jpg";
    private BufferedImage image;
    private final DefaultTableModel presencesTbl;

    /**
     * Creates new form GuiCRUDUser
     */
    public GuiCRUDUser() {
        initComponents();
        cleanFields();
        dtmUsers = (DefaultTableModel) tableUsers.getModel();
        presencesTbl = (DefaultTableModel) tableEmployeeSchedule.getModel();
        setVisible(false);
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.DAY_OF_MONTH, 1);
        this.getDateFrom().setCalendar(cal);
        cal.set(Calendar.DAY_OF_MONTH, cal.getActualMaximum(Calendar.DAY_OF_MONTH));
        this.getDateTo().setCalendar(cal);
    }

    public DefaultTableModel getPresencesTbl() {
        return presencesTbl;
    }

    
    /**
     * clean all the text fields
     *
     */
    public void cleanFields() {
        txtName.setText("");
        txtSurname.setText("");
        txtAddress.setText("");
        dateHiredDate.setDate(null);
        dateDischargedDate.setDate(null);
        boxTurn.setSelectedIndex(0);
        dateBirthDate.setDate(null);
        txtPlaceOfBirth.setText("");
        boxIdType.setSelectedIndex(0);
        txtIdNumber.setText("");
        txtPass.setText("");
        txtHomePhone.setText("");
        txtEmergencyPhone.setText("");
        txtMobilePhone.setText("");
        txtIdNumber.setText("");
        boxMaritalStatus.setSelectedIndex(0);
        boxBloodType.setSelectedIndex(0);
        boxPosition.setSelectedIndex(0);
        setPictureDefault();

    }

    public void updateFields(String name,
            String surname,
            String pass,
            Date entryDate,
            Date exitDate,
            String turn,
            Date dateOfBirth,
            String placeOfBirth,
            String idType,
            String idNumber,
            String address,
            String homePhone,
            String emergencyPhone,
            String mobilePhone,
            String maritalStatus,
            String bloodType,
            String position
    ) throws IOException {
        txtName.setText(name);
        txtSurname.setText(surname);
        txtAddress.setText(address);
        dateHiredDate.setDate(entryDate);
        dateDischargedDate.setDate(exitDate);
        boxTurn.setSelectedItem(turn);
        dateBirthDate.setDate(dateOfBirth);
        txtPlaceOfBirth.setText(placeOfBirth);
        boxIdType.setSelectedItem(idType);
        txtIdNumber.setText(idNumber);
        txtPass.setText(pass);
        txtHomePhone.setText(homePhone);
        txtEmergencyPhone.setText(emergencyPhone);
        txtMobilePhone.setText(mobilePhone);
        boxMaritalStatus.setSelectedItem(maritalStatus);
        boxBloodType.setSelectedItem(bloodType);
        boxPosition.setSelectedItem(position);

    }

    public void setPictureDefault() {
        image = null;
        pnlImageUser.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/sin_imagen_disponible.jpg")));
        pnlImageUser.repaint();

    }

    public BufferedImage getImage() {
        return image;
    }

    public void setPicture(BufferedImage image) {
        if (image != null) {
            this.image = image;
            this.namePicture = "hay_imagen";
            pnlImageUser.setIcon(new javax.swing.ImageIcon(image));
            pnlImageUser.repaint();
        } else {
            setPictureDefault();
        }
    }

    public void modifyMode(boolean b) {
        if (b) {
            btnDelete.setText("Cancelar");
        } else {
            btnDelete.setText("Borrar");
        }
        btnDelete.setEnabled(b);
        btnSave.setEnabled(b);
        btnModify.setEnabled(!b);
        btnCreate.setEnabled(!b);
        this.btnAddPhoto.setEnabled(b);
        this.btnDeletePhoto.setEnabled(b);
        enableFields(b);
    }

    public void createMode(boolean b) {
        if (b) {
            btnDelete.setText("Cancelar");
        } else {
            btnDelete.setText("Borrar");
        }
        btnDelete.setEnabled(b);
        btnSave.setEnabled(b);
        btnModify.setEnabled(!b);
        btnCreate.setEnabled(!b);
        this.btnAddPhoto.setEnabled(b);
        this.btnDeletePhoto.setEnabled(b);
        enableFields(b);
    }

    public void selectionMode(boolean b) {
        btnDelete.setEnabled(b);
        btnSave.setEnabled(!b);
        btnModify.setEnabled(b);
        btnCreate.setEnabled(b);
        enableFields(!b);
    }

    public void initialMode(boolean b) {
        cleanFields();
        btnDelete.setText("Borrar");
        btnDelete.setEnabled(!b);
        btnSave.setEnabled(!b);
        btnModify.setEnabled(!b);
        btnCreate.setEnabled(b);
        this.btnAddPhoto.setEnabled(!b);
        this.btnDeletePhoto.setEnabled(!b);
        enableFields(!b);
        tableUsers.clearSelection();
    }

    public void setActionListener(ActionListener ac) {
        btnCreate.addActionListener(ac);
        btnModify.addActionListener(ac);
        btnSave.addActionListener(ac);
        btnDelete.addActionListener(ac);
        checkBoxDischarged.addActionListener(ac);
        this.btnAddPhoto.addActionListener(ac);
        this.btnDeletePhoto.addActionListener(ac);
        btnPrintReport.addActionListener(ac);
    }

    private void enableFields(boolean b) {
        txtName.setEnabled(b);
        txtSurname.setEnabled(b);
        txtAddress.setEnabled(b);
        dateHiredDate.setEnabled(b);
        dateDischargedDate.setEnabled(b);
        boxTurn.setEnabled(b);
        dateBirthDate.setEnabled(b);
        txtPlaceOfBirth.setEnabled(b);
        boxIdType.setEnabled(b);
        txtIdNumber.setEnabled(b);
        txtPass.setEnabled(b);
        txtHomePhone.setEnabled(b);
        txtEmergencyPhone.setEnabled(b);
        txtMobilePhone.setEnabled(b);
        txtIdNumber.setEnabled(b);
        boxMaritalStatus.setEnabled(b);
        boxBloodType.setEnabled(b);
        boxPosition.setEnabled(b);
        checkBoxDischarged.setEnabled(b);
    }

    public void enableDischarged(boolean b) {
        lblDischargedDate.setEnabled(b);
        dateDischargedDate.setEnabled(b);
        checkBoxDischarged.setSelected(b);
    }

    public JButton getBtnAddPhoto() {
        return btnAddPhoto;
    }

    public JButton getBtnDeletePhoto() {
        return btnDeletePhoto;
    }

    public JDateChooser getDateFrom() {
        return dateFrom;
    }

    public JDateChooser getDateTo() {
        return dateTo;
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane2 = new javax.swing.JScrollPane();
        panelImage1 = new org.edisoncor.gui.panel.PanelImage();
        panelImage3 = new org.edisoncor.gui.panel.PanelImage();
        lblEntryDate = new javax.swing.JLabel();
        boxTurn = new javax.swing.JComboBox();
        txtMobilePhone = new javax.swing.JTextField();
        txtSurname = new javax.swing.JTextField();
        lblPlaceOfBirth = new javax.swing.JLabel();
        lblTurn = new javax.swing.JLabel();
        lblBloodType = new javax.swing.JLabel();
        lblIdType = new javax.swing.JLabel();
        boxMaritalStatus = new javax.swing.JComboBox();
        boxBloodType = new javax.swing.JComboBox();
        lblEmergencyPhone = new javax.swing.JLabel();
        dateHiredDate = new com.toedter.calendar.JDateChooser();
        lblHiredDate = new javax.swing.JLabel();
        lblPass = new javax.swing.JLabel();
        lblAddress = new javax.swing.JLabel();
        txtIdNumber = new javax.swing.JTextField();
        lblMaritalStatus = new javax.swing.JLabel();
        dateBirthDate = new com.toedter.calendar.JDateChooser();
        lblSurname = new javax.swing.JLabel();
        txtName = new javax.swing.JTextField();
        lblHomePhone = new javax.swing.JLabel();
        txtEmergencyPhone = new javax.swing.JTextField();
        lblPosition = new javax.swing.JLabel();
        boxPosition = new javax.swing.JComboBox();
        lblMobilePhone = new javax.swing.JLabel();
        boxIdType = new javax.swing.JComboBox();
        txtHomePhone = new javax.swing.JTextField();
        txtPlaceOfBirth = new javax.swing.JTextField();
        lblName = new javax.swing.JLabel();
        txtPass = new javax.swing.JTextField();
        lblIdNumber = new javax.swing.JLabel();
        txtAddress = new javax.swing.JTextField();
        dateDischargedDate = new com.toedter.calendar.JDateChooser();
        lblDischargedDate = new javax.swing.JLabel();
        checkBoxDischarged = new javax.swing.JCheckBox();
        panlePhoto = new org.edisoncor.gui.panel.PanelImage();
        btnAddPhoto = new javax.swing.JButton();
        btnDeletePhoto = new javax.swing.JButton();
        pnlImageUser = new org.edisoncor.gui.panel.PanelImage();
        panelImage2 = new org.edisoncor.gui.panel.PanelImage();
        scrollEmployeeSchedule = new javax.swing.JScrollPane();
        tableEmployeeSchedule = new javax.swing.JTable();
        dateFrom = new com.toedter.calendar.JDateChooser();
        jLabel1 = new javax.swing.JLabel();
        dateTo = new com.toedter.calendar.JDateChooser();
        jLabel2 = new javax.swing.JLabel();
        btnPrintReport = new javax.swing.JButton();
        panelImage4 = new org.edisoncor.gui.panel.PanelImage();
        btnCreate = new javax.swing.JButton();
        btnDelete = new javax.swing.JButton();
        btnModify = new javax.swing.JButton();
        btnSave = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tableUsers = new javax.swing.JTable();

        setClosable(true);
        setDefaultCloseOperation(javax.swing.WindowConstants.HIDE_ON_CLOSE);
        setIconifiable(true);
        setMaximizable(true);
        setResizable(true);
        setTitle("Empleados");
        setMinimumSize(new java.awt.Dimension(1300, 700));

        panelImage1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/fondo gris.png"))); // NOI18N

        panelImage3.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Empleado", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Dialog", 2, 15), new java.awt.Color(255, 255, 255))); // NOI18N
        panelImage3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/fondo gris.png"))); // NOI18N

        lblEntryDate.setForeground(new java.awt.Color(255, 255, 255));
        lblEntryDate.setText("Fecha de Nac");

        boxTurn.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "", "Manana", "Tarde" }));

        txtSurname.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtSurnameActionPerformed(evt);
            }
        });

        lblPlaceOfBirth.setForeground(new java.awt.Color(255, 255, 255));
        lblPlaceOfBirth.setText("Lugar de Nac");

        lblTurn.setForeground(new java.awt.Color(255, 255, 255));
        lblTurn.setText("Turno");

        lblBloodType.setForeground(new java.awt.Color(255, 255, 255));
        lblBloodType.setText("Rh");

        lblIdType.setForeground(new java.awt.Color(255, 255, 255));
        lblIdType.setText("Tipo de doc");

        boxMaritalStatus.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "", "Casado", "Soltero", "Divorciado", "Viudo" }));

        boxBloodType.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "", "A+", "A-", "B+", "B-", "AB+", "AB-", "O+", "O-" }));

        lblEmergencyPhone.setForeground(new java.awt.Color(255, 255, 255));
        lblEmergencyPhone.setText("Tel Emergencia");

        dateHiredDate.setDateFormatString("dd-MM-yyyy");

        lblHiredDate.setForeground(new java.awt.Color(255, 255, 255));
        lblHiredDate.setText("Fecha Contratado");

        lblPass.setForeground(new java.awt.Color(255, 255, 255));
        lblPass.setText("Contrasena");

        lblAddress.setForeground(new java.awt.Color(255, 255, 255));
        lblAddress.setText("Direccion");

        txtIdNumber.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtIdNumberActionPerformed(evt);
            }
        });

        lblMaritalStatus.setForeground(new java.awt.Color(255, 255, 255));
        lblMaritalStatus.setText("Estado Civil");

        dateBirthDate.setDateFormatString("dd-MM-yyyy");

        lblSurname.setForeground(new java.awt.Color(255, 255, 255));
        lblSurname.setText("Apellido");

        txtName.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNameActionPerformed(evt);
            }
        });

        lblHomePhone.setForeground(new java.awt.Color(255, 255, 255));
        lblHomePhone.setText("Telefono Casa");

        lblPosition.setForeground(new java.awt.Color(255, 255, 255));
        lblPosition.setText("Posicion");

        boxPosition.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "", "Mozo", "Cocinero" }));
        boxPosition.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                boxPositionActionPerformed(evt);
            }
        });

        lblMobilePhone.setForeground(new java.awt.Color(255, 255, 255));
        lblMobilePhone.setText("Telefono Movil");

        boxIdType.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "", "DNI", "Pasaporte", "Otro" }));

        lblName.setForeground(new java.awt.Color(255, 255, 255));
        lblName.setText("Nombre");

        lblIdNumber.setForeground(new java.awt.Color(255, 255, 255));
        lblIdNumber.setText("Documento");

        txtAddress.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtAddressActionPerformed(evt);
            }
        });

        dateDischargedDate.setDateFormatString("dd-MM-yyyy");

        lblDischargedDate.setForeground(new java.awt.Color(255, 255, 255));
        lblDischargedDate.setText("Fecha Despedido");

        checkBoxDischarged.setForeground(new java.awt.Color(255, 255, 255));
        checkBoxDischarged.setText("Despedido");
        checkBoxDischarged.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                checkBoxDischargedActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout panelImage3Layout = new javax.swing.GroupLayout(panelImage3);
        panelImage3.setLayout(panelImage3Layout);
        panelImage3Layout.setHorizontalGroup(
            panelImage3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelImage3Layout.createSequentialGroup()
                .addContainerGap(48, Short.MAX_VALUE)
                .addGroup(panelImage3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelImage3Layout.createSequentialGroup()
                        .addGroup(panelImage3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(dateBirthDate, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblEntryDate))
                        .addGap(30, 30, 30)
                        .addGroup(panelImage3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(boxPosition, javax.swing.GroupLayout.PREFERRED_SIZE, 111, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblPosition))
                        .addGap(30, 30, 30)
                        .addGroup(panelImage3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(dateHiredDate, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblHiredDate))
                        .addGap(30, 30, 30)
                        .addGroup(panelImage3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(dateDischargedDate, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblDischargedDate)))
                    .addGroup(panelImage3Layout.createSequentialGroup()
                        .addGroup(panelImage3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtName, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtPlaceOfBirth, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblPlaceOfBirth)
                            .addComponent(lblName))
                        .addGap(30, 30, 30)
                        .addGroup(panelImage3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(panelImage3Layout.createSequentialGroup()
                                .addGroup(panelImage3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(txtSurname, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(lblSurname))
                                .addGap(30, 30, 30)
                                .addGroup(panelImage3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(lblTurn)
                                    .addComponent(boxTurn, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(panelImage3Layout.createSequentialGroup()
                                .addGroup(panelImage3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(txtPass, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(lblPass))
                                .addGap(30, 30, 30)
                                .addGroup(panelImage3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(lblAddress)
                                    .addComponent(txtAddress, javax.swing.GroupLayout.PREFERRED_SIZE, 159, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                    .addGroup(panelImage3Layout.createSequentialGroup()
                        .addGroup(panelImage3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(boxIdType, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblIdType))
                        .addGap(30, 30, 30)
                        .addGroup(panelImage3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtIdNumber, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblIdNumber))
                        .addGap(30, 30, 30)
                        .addGroup(panelImage3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(boxMaritalStatus, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblMaritalStatus))
                        .addGap(30, 30, 30)
                        .addGroup(panelImage3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblBloodType)
                            .addComponent(boxBloodType, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(panelImage3Layout.createSequentialGroup()
                        .addGroup(panelImage3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtHomePhone, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblHomePhone))
                        .addGap(30, 30, 30)
                        .addGroup(panelImage3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtEmergencyPhone, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblEmergencyPhone))
                        .addGap(30, 30, 30)
                        .addGroup(panelImage3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblMobilePhone)
                            .addComponent(txtMobilePhone, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(15, 15, 15)
                        .addComponent(checkBoxDischarged)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        panelImage3Layout.setVerticalGroup(
            panelImage3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelImage3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelImage3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblSurname)
                    .addComponent(lblName)
                    .addComponent(lblTurn))
                .addGap(10, 10, 10)
                .addGroup(panelImage3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtSurname, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(boxTurn, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(12, 12, 12)
                .addGroup(panelImage3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelImage3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(lblAddress)
                        .addComponent(lblPass))
                    .addComponent(lblPlaceOfBirth))
                .addGap(10, 10, 10)
                .addGroup(panelImage3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtPass, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(panelImage3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(txtPlaceOfBirth, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(txtAddress, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGroup(panelImage3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelImage3Layout.createSequentialGroup()
                        .addGap(12, 12, 12)
                        .addGroup(panelImage3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblDischargedDate)
                            .addComponent(lblHiredDate)))
                    .addGroup(panelImage3Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(panelImage3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lblEntryDate)
                            .addComponent(lblPosition))))
                .addGroup(panelImage3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelImage3Layout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addGroup(panelImage3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(panelImage3Layout.createSequentialGroup()
                                .addGroup(panelImage3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(dateHiredDate, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(dateBirthDate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(10, 10, 10)
                                .addComponent(lblHomePhone, javax.swing.GroupLayout.PREFERRED_SIZE, 17, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(8, 8, 8)
                                .addGroup(panelImage3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(txtHomePhone, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txtMobilePhone, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txtEmergencyPhone, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(12, 12, 12)
                                .addGroup(panelImage3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(lblIdNumber, javax.swing.GroupLayout.PREFERRED_SIZE, 17, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(lblIdType, javax.swing.GroupLayout.PREFERRED_SIZE, 17, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(lblMaritalStatus, javax.swing.GroupLayout.PREFERRED_SIZE, 17, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(lblBloodType, javax.swing.GroupLayout.PREFERRED_SIZE, 17, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(10, 10, 10)
                                .addGroup(panelImage3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(txtIdNumber, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(boxIdType, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(boxMaritalStatus, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(boxBloodType, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(panelImage3Layout.createSequentialGroup()
                                .addComponent(dateDischargedDate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(checkBoxDischarged))))
                    .addGroup(panelImage3Layout.createSequentialGroup()
                        .addGap(8, 8, 8)
                        .addComponent(boxPosition, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(panelImage3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lblEmergencyPhone)
                            .addComponent(lblMobilePhone))))
                .addContainerGap(34, Short.MAX_VALUE))
        );

        panlePhoto.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/fondo gris.png"))); // NOI18N

        btnAddPhoto.setText("Agregar");

        btnDeletePhoto.setText("Eliminar");

        pnlImageUser.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/sin_imagen_disponible.jpg"))); // NOI18N
        pnlImageUser.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                pnlImageUserMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout pnlImageUserLayout = new javax.swing.GroupLayout(pnlImageUser);
        pnlImageUser.setLayout(pnlImageUserLayout);
        pnlImageUserLayout.setHorizontalGroup(
            pnlImageUserLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        pnlImageUserLayout.setVerticalGroup(
            pnlImageUserLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 270, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout panlePhotoLayout = new javax.swing.GroupLayout(panlePhoto);
        panlePhoto.setLayout(panlePhotoLayout);
        panlePhotoLayout.setHorizontalGroup(
            panlePhotoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panlePhotoLayout.createSequentialGroup()
                .addComponent(btnAddPhoto, javax.swing.GroupLayout.DEFAULT_SIZE, 229, Short.MAX_VALUE)
                .addGap(0, 0, 0)
                .addComponent(btnDeletePhoto, javax.swing.GroupLayout.DEFAULT_SIZE, 227, Short.MAX_VALUE))
            .addComponent(pnlImageUser, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        panlePhotoLayout.setVerticalGroup(
            panlePhotoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panlePhotoLayout.createSequentialGroup()
                .addComponent(pnlImageUser, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panlePhotoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnDeletePhoto, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnAddPhoto, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)))
        );

        panelImage2.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Presentismo", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Dialog", 2, 15), new java.awt.Color(255, 255, 255))); // NOI18N
        panelImage2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/fondo gris.png"))); // NOI18N

        tableEmployeeSchedule.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "Dia Entrada", "Hora Entrada", "Dia Salida", "Hora Salida", "Horas trabajadas"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        scrollEmployeeSchedule.setViewportView(tableEmployeeSchedule);

        dateFrom.setDateFormatString("dd-MM-yyyy");

        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("Desde");

        dateTo.setDateFormatString("dd-MM-yyyy");

        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setText("Hasta");

        btnPrintReport.setText("Imprimir");
        btnPrintReport.setEnabled(false);
        btnPrintReport.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPrintReportActionPerformed(evt);
            }
        });

        panelImage4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/fondo gris.png"))); // NOI18N

        btnCreate.setText("Nuevo");

        btnDelete.setText("Borrar");

        btnModify.setText("Modificar");
        btnModify.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnModify.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnModifyActionPerformed(evt);
            }
        });

        btnSave.setText("Guardar");

        javax.swing.GroupLayout panelImage4Layout = new javax.swing.GroupLayout(panelImage4);
        panelImage4.setLayout(panelImage4Layout);
        panelImage4Layout.setHorizontalGroup(
            panelImage4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
            .addGroup(panelImage4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(panelImage4Layout.createSequentialGroup()
                    .addGap(0, 0, Short.MAX_VALUE)
                    .addGroup(panelImage4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(panelImage4Layout.createSequentialGroup()
                            .addComponent(btnCreate, javax.swing.GroupLayout.PREFERRED_SIZE, 288, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(0, 0, 0)
                            .addComponent(btnDelete, javax.swing.GroupLayout.PREFERRED_SIZE, 288, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(panelImage4Layout.createSequentialGroup()
                            .addComponent(btnModify, javax.swing.GroupLayout.PREFERRED_SIZE, 288, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(0, 0, 0)
                            .addComponent(btnSave, javax.swing.GroupLayout.PREFERRED_SIZE, 288, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGap(0, 0, Short.MAX_VALUE)))
        );
        panelImage4Layout.setVerticalGroup(
            panelImage4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 106, Short.MAX_VALUE)
            .addGroup(panelImage4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(panelImage4Layout.createSequentialGroup()
                    .addGap(0, 0, Short.MAX_VALUE)
                    .addGroup(panelImage4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(btnCreate, javax.swing.GroupLayout.PREFERRED_SIZE, 53, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(btnDelete, javax.swing.GroupLayout.PREFERRED_SIZE, 53, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(panelImage4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(btnModify, javax.swing.GroupLayout.PREFERRED_SIZE, 53, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(btnSave, javax.swing.GroupLayout.PREFERRED_SIZE, 53, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGap(0, 0, Short.MAX_VALUE)))
        );

        javax.swing.GroupLayout panelImage2Layout = new javax.swing.GroupLayout(panelImage2);
        panelImage2.setLayout(panelImage2Layout);
        panelImage2Layout.setHorizontalGroup(
            panelImage2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelImage2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelImage2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(panelImage4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(scrollEmployeeSchedule)
                    .addGroup(panelImage2Layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(dateFrom, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(dateTo, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnPrintReport, javax.swing.GroupLayout.DEFAULT_SIZE, 177, Short.MAX_VALUE)))
                .addContainerGap())
        );
        panelImage2Layout.setVerticalGroup(
            panelImage2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelImage2Layout.createSequentialGroup()
                .addGroup(panelImage2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelImage2Layout.createSequentialGroup()
                        .addGroup(panelImage2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(dateFrom, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel1)
                            .addComponent(dateTo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel2))
                        .addGap(10, 10, 10)
                        .addComponent(scrollEmployeeSchedule, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                    .addComponent(btnPrintReport))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(panelImage4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        tableUsers.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "Nombre", "Apellido", "Posicion"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.String.class, java.lang.String.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tableUsers.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_ALL_COLUMNS);
        tableUsers.setAutoscrolls(false);
        tableUsers.getTableHeader().setReorderingAllowed(false);
        jScrollPane1.setViewportView(tableUsers);

        javax.swing.GroupLayout panelImage1Layout = new javax.swing.GroupLayout(panelImage1);
        panelImage1.setLayout(panelImage1Layout);
        panelImage1Layout.setHorizontalGroup(
            panelImage1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelImage1Layout.createSequentialGroup()
                .addComponent(panelImage3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(panelImage2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelImage1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 824, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(37, 37, 37))
            .addGroup(panelImage1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(panelImage1Layout.createSequentialGroup()
                    .addComponent(panlePhoto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(0, 868, Short.MAX_VALUE)))
        );
        panelImage1Layout.setVerticalGroup(
            panelImage1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelImage1Layout.createSequentialGroup()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 308, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelImage1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(panelImage2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(panelImage3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(panelImage1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(panelImage1Layout.createSequentialGroup()
                    .addComponent(panlePhoto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(0, 401, Short.MAX_VALUE)))
        );

        jScrollPane2.setViewportView(panelImage1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 1289, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void txtSurnameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtSurnameActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtSurnameActionPerformed

    private void txtIdNumberActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtIdNumberActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtIdNumberActionPerformed

    private void txtNameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNameActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtNameActionPerformed

    private void boxPositionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_boxPositionActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_boxPositionActionPerformed

    private void txtAddressActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtAddressActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtAddressActionPerformed

    private void checkBoxDischargedActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_checkBoxDischargedActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_checkBoxDischargedActionPerformed

    private void pnlImageUserMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pnlImageUserMouseClicked
        if (evt.getClickCount() == 2) {
            JFrame k = new JFrame("VER  IMAGEN DEL EMPLEADO");
            k.setResizable(true);
            k.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            k.setLayout(new BorderLayout());
            PanelImage p = new PanelImage();
            p.setIcon(this.pnlImageUser.getIcon());
            k.add(p);
            k.setSize(p.getIcon().getIconWidth(), p.getIcon().getIconHeight());
            k.setLocationRelativeTo(null);
            k.setVisible(true);
            k.toFront();
        }
    }//GEN-LAST:event_pnlImageUserMouseClicked

    private void btnPrintReportActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPrintReportActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnPrintReportActionPerformed

    private void btnModifyActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnModifyActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnModifyActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox boxBloodType;
    private javax.swing.JComboBox boxIdType;
    private javax.swing.JComboBox boxMaritalStatus;
    private javax.swing.JComboBox boxPosition;
    private javax.swing.JComboBox boxTurn;
    private javax.swing.JButton btnAddPhoto;
    private javax.swing.JButton btnCreate;
    private javax.swing.JButton btnDelete;
    private javax.swing.JButton btnDeletePhoto;
    private javax.swing.JButton btnModify;
    private javax.swing.JButton btnPrintReport;
    private javax.swing.JButton btnSave;
    private javax.swing.JCheckBox checkBoxDischarged;
    private com.toedter.calendar.JDateChooser dateBirthDate;
    private com.toedter.calendar.JDateChooser dateDischargedDate;
    private com.toedter.calendar.JDateChooser dateFrom;
    private com.toedter.calendar.JDateChooser dateHiredDate;
    private com.toedter.calendar.JDateChooser dateTo;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JLabel lblAddress;
    private javax.swing.JLabel lblBloodType;
    private javax.swing.JLabel lblDischargedDate;
    private javax.swing.JLabel lblEmergencyPhone;
    private javax.swing.JLabel lblEntryDate;
    private javax.swing.JLabel lblHiredDate;
    private javax.swing.JLabel lblHomePhone;
    private javax.swing.JLabel lblIdNumber;
    private javax.swing.JLabel lblIdType;
    private javax.swing.JLabel lblMaritalStatus;
    private javax.swing.JLabel lblMobilePhone;
    private javax.swing.JLabel lblName;
    private javax.swing.JLabel lblPass;
    private javax.swing.JLabel lblPlaceOfBirth;
    private javax.swing.JLabel lblPosition;
    private javax.swing.JLabel lblSurname;
    private javax.swing.JLabel lblTurn;
    private org.edisoncor.gui.panel.PanelImage panelImage1;
    private org.edisoncor.gui.panel.PanelImage panelImage2;
    private org.edisoncor.gui.panel.PanelImage panelImage3;
    private org.edisoncor.gui.panel.PanelImage panelImage4;
    private org.edisoncor.gui.panel.PanelImage panlePhoto;
    private org.edisoncor.gui.panel.PanelImage pnlImageUser;
    private javax.swing.JScrollPane scrollEmployeeSchedule;
    private javax.swing.JTable tableEmployeeSchedule;
    private javax.swing.JTable tableUsers;
    private javax.swing.JTextField txtAddress;
    private javax.swing.JTextField txtEmergencyPhone;
    private javax.swing.JTextField txtHomePhone;
    private javax.swing.JTextField txtIdNumber;
    private javax.swing.JTextField txtMobilePhone;
    private javax.swing.JTextField txtName;
    private javax.swing.JTextField txtPass;
    private javax.swing.JTextField txtPlaceOfBirth;
    private javax.swing.JTextField txtSurname;
    // End of variables declaration//GEN-END:variables

    /**
     * @return the BtnCreate
     */
    public javax.swing.JButton getBtnCreate() {
        return btnCreate;
    }

    /**
     * @return the BtnDelete
     */
    public javax.swing.JButton getBtnDelete() {
        return btnDelete;
    }

    /**
     * @return the BtnModify
     */
    public javax.swing.JButton getBtnModify() {
        return btnModify;
    }

    /**
     * @return the btnSave
     */
    public javax.swing.JButton getBtnSave() {
        return btnSave;
    }

    /**
     * @return the tableEmployeeSchedule
     */
    public javax.swing.JTable getTableEmployeeSchedule() {
        return tableEmployeeSchedule;
    }

    /**
     * @return the tableUsers
     */
    public javax.swing.JTable getTableUsers() {
        return tableUsers;
    }

    /**
     * @return the txtAdress
     */
    public javax.swing.JTextField getTxtAddress() {
        return txtAddress;
    }

    /**
     * @return the txtEmergencyPhone
     */
    public javax.swing.JTextField getTxtEmergencyPhone() {
        return txtEmergencyPhone;
    }

    /**
     * @return the txtHomePhone
     */
    public javax.swing.JTextField getTxtHomePhone() {
        return txtHomePhone;
    }

    /**
     * @return the txtIdNumber
     */
    public javax.swing.JTextField getTxtIdNumber() {
        return txtIdNumber;
    }

    /**
     * @return the txtMobilePhone
     */
    public javax.swing.JTextField getTxtMobilePhone() {
        return txtMobilePhone;
    }

    /**
     * @return the txtName
     */
    public javax.swing.JTextField getTxtName() {
        return txtName;
    }

    /**
     * @return the txtPass
     */
    public javax.swing.JTextField getTxtPass() {
        return txtPass;
    }

    /**
     * @return the txtPlaceOfBirth
     */
    public javax.swing.JTextField getTxtPlaceOfBirth() {
        return txtPlaceOfBirth;
    }

    /**
     * @return the txtSurname
     */
    public javax.swing.JTextField getTxtSurname() {
        return txtSurname;
    }

    /**
     * @return the boxTurn
     */
    public JComboBox getBoxTurn() {
        return boxTurn;
    }

    /**
     * @return the dtmUsers
     */
    public DefaultTableModel getDtmUsers() {
        return dtmUsers;
    }

    /**
     * @return the dateBirthDate
     */
    public com.toedter.calendar.JDateChooser getDateBirthDate() {
        return dateBirthDate;
    }

    /**
     * @return the dateHiredDate
     */
    public com.toedter.calendar.JDateChooser getDateHiredDate() {
        return dateHiredDate;
    }

    /**
     * @return the dateDischargedtDate
     */
    public com.toedter.calendar.JDateChooser getDateDischargedDate() {
        return dateDischargedDate;
    }

    /**
     * @return the boxBloodType
     */
    public javax.swing.JComboBox getBoxBloodType() {
        return boxBloodType;
    }

    /**
     * @return the boxIdType
     */
    public javax.swing.JComboBox getBoxIdType() {
        return boxIdType;
    }

    /**
     * @return the boxMaritalStatus
     */
    public javax.swing.JComboBox getBoxMaritalStatus() {
        return boxMaritalStatus;
    }

    /**
     * @return the boxPosition
     */
    public javax.swing.JComboBox getBoxPosition() {
        return boxPosition;
    }
    
    public JButton getBtnPrintReport() {
        return btnPrintReport;
    }

    /**
     * @return the checkBoxDischarged
     */
    public javax.swing.JCheckBox getCheckBoxDischarged() {
        return checkBoxDischarged;
    }
}
