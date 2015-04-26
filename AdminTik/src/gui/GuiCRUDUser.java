/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

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

/**
 *
 * @author xen
 */
public class GuiCRUDUser extends javax.swing.JInternalFrame {

    private final DefaultTableModel dtmUsers;
    private ImageIcon photo = null;

    /**
     * Creates new form GuiCRUDUser
     */
    public GuiCRUDUser() {
        initComponents();
        cleanFields();
        dtmUsers = (DefaultTableModel) tableUsers.getModel();
        setVisible(false);
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
        photo = null;
        lblPhoto.setText("Haga doble click sobre el panel");
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
            String position,
            String photo) throws IOException {
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
        txtIdNumber.setText(maritalStatus);
        boxMaritalStatus.setSelectedItem(maritalStatus);
        boxBloodType.setSelectedItem(bloodType);
        boxPosition.setSelectedItem(position);
        if (photo == null) {
            lblPhoto.setText("Haga doble click sobre el panel");
        } else {
                System.out.println(photo);
                File file = new File(String.format(photo, 0));
                BufferedImage image = ImageIO.read(file);
                ImageIcon imgIcon = new ImageIcon(image);
                lblPhoto.setIcon(imgIcon);
                lblPhoto.setText("");        
        }
        lblPhoto.updateUI();
    }

    public String dateToMySQLDate(Date fecha, boolean paraMostrar) {
        if (paraMostrar) {
            java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("dd/MM/yyyy");
            return sdf.format(fecha);
        } else {
            java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd");
            return sdf.format(fecha);
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
        enableFields(!b);
        tableUsers.clearSelection();
    }

    public void setActionListener(ActionListener ac) {
        btnCreate.addActionListener(ac);
        btnModify.addActionListener(ac);
        btnSave.addActionListener(ac);
        btnDelete.addActionListener(ac);
        checkBoxDischarged.addActionListener(ac);
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

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        tableUsers = new javax.swing.JTable();
        panelFields = new javax.swing.JPanel();
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
        panelEmployeeSchedule = new javax.swing.JPanel();
        scrollEmployeeSchedule = new javax.swing.JScrollPane();
        tableEmployeeSchedule = new javax.swing.JTable();
        dateEntryJob = new com.toedter.calendar.JDateChooser();
        jLabel1 = new javax.swing.JLabel();
        dateExitJob = new com.toedter.calendar.JDateChooser();
        jLabel2 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        btnCreate = new javax.swing.JButton();
        btnDelete = new javax.swing.JButton();
        btnModify = new javax.swing.JButton();
        btnSave = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        lblPhoto = new javax.swing.JLabel();

        setClosable(true);
        setDefaultCloseOperation(javax.swing.WindowConstants.HIDE_ON_CLOSE);
        setIconifiable(true);
        setMaximizable(true);
        setResizable(true);
        setTitle("Empleados");
        setMinimumSize(new java.awt.Dimension(1300, 700));
        setPreferredSize(new java.awt.Dimension(1300, 700));

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
        if (tableUsers.getColumnModel().getColumnCount() > 0) {
            tableUsers.getColumnModel().getColumn(0).setMinWidth(20);
            tableUsers.getColumnModel().getColumn(0).setPreferredWidth(20);
            tableUsers.getColumnModel().getColumn(1).setMinWidth(60);
            tableUsers.getColumnModel().getColumn(2).setMinWidth(60);
            tableUsers.getColumnModel().getColumn(3).setMinWidth(60);
        }

        panelFields.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "Empleado", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Ubuntu", 1, 15))); // NOI18N

        lblEntryDate.setText("Fecha de Nac");

        boxTurn.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "", "Manana", "Tarde" }));

        txtSurname.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtSurnameActionPerformed(evt);
            }
        });

        lblPlaceOfBirth.setText("Lugar de Nac");

        lblTurn.setText("Turno");

        lblBloodType.setText("Rh");

        lblIdType.setText("Tipo de doc");

        boxMaritalStatus.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "", "Casado", "Soltero", "Divorciado", "Viudo" }));

        boxBloodType.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "", "A+", "A-", "B+", "B-", "AB+", "AB-", "O+", "O-" }));

        lblEmergencyPhone.setText("Tel Emergencia");

        dateHiredDate.setDateFormatString("dd-MM-yyyy");

        lblHiredDate.setText("Fecha Contratado");

        lblPass.setText("Contrasena");

        lblAddress.setText("Direccion");

        txtIdNumber.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtIdNumberActionPerformed(evt);
            }
        });

        lblMaritalStatus.setText("Estado Civil");

        dateBirthDate.setDateFormatString("dd-MM-yyyy");

        lblSurname.setText("Apellido");

        txtName.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNameActionPerformed(evt);
            }
        });

        lblHomePhone.setText("Telefono Casa");

        lblPosition.setText("Posicion");

        boxPosition.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "", "Mozo", "Cocinero" }));
        boxPosition.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                boxPositionActionPerformed(evt);
            }
        });

        lblMobilePhone.setText("Telefono Movil");

        boxIdType.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "", "DNI", "Pasaporte", "Otro" }));

        lblName.setText("Nombre");

        lblIdNumber.setText("Documento");

        txtAddress.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtAddressActionPerformed(evt);
            }
        });

        dateDischargedDate.setDateFormatString("dd-MM-yyyy");

        lblDischargedDate.setText("Fecha Despedido");

        checkBoxDischarged.setText("Despedido");
        checkBoxDischarged.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                checkBoxDischargedActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout panelFieldsLayout = new javax.swing.GroupLayout(panelFields);
        panelFields.setLayout(panelFieldsLayout);
        panelFieldsLayout.setHorizontalGroup(
            panelFieldsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelFieldsLayout.createSequentialGroup()
                .addContainerGap(41, Short.MAX_VALUE)
                .addGroup(panelFieldsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelFieldsLayout.createSequentialGroup()
                        .addGroup(panelFieldsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(dateBirthDate, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblEntryDate))
                        .addGap(30, 30, 30)
                        .addGroup(panelFieldsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(boxPosition, javax.swing.GroupLayout.PREFERRED_SIZE, 111, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblPosition))
                        .addGap(30, 30, 30)
                        .addGroup(panelFieldsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(dateHiredDate, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblHiredDate))
                        .addGap(30, 30, 30)
                        .addGroup(panelFieldsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(dateDischargedDate, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblDischargedDate)))
                    .addGroup(panelFieldsLayout.createSequentialGroup()
                        .addGroup(panelFieldsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtName, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtPlaceOfBirth, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblPlaceOfBirth)
                            .addComponent(lblName))
                        .addGap(30, 30, 30)
                        .addGroup(panelFieldsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(panelFieldsLayout.createSequentialGroup()
                                .addGroup(panelFieldsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(txtSurname, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(lblSurname))
                                .addGap(30, 30, 30)
                                .addGroup(panelFieldsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(lblTurn)
                                    .addComponent(boxTurn, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(panelFieldsLayout.createSequentialGroup()
                                .addGroup(panelFieldsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(txtPass, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(lblPass))
                                .addGap(30, 30, 30)
                                .addGroup(panelFieldsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(lblAddress)
                                    .addComponent(txtAddress, javax.swing.GroupLayout.PREFERRED_SIZE, 159, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                    .addGroup(panelFieldsLayout.createSequentialGroup()
                        .addGroup(panelFieldsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(boxIdType, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblIdType))
                        .addGap(30, 30, 30)
                        .addGroup(panelFieldsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtIdNumber, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblIdNumber))
                        .addGap(30, 30, 30)
                        .addGroup(panelFieldsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(boxMaritalStatus, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblMaritalStatus))
                        .addGap(30, 30, 30)
                        .addGroup(panelFieldsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblBloodType)
                            .addComponent(boxBloodType, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(panelFieldsLayout.createSequentialGroup()
                        .addGroup(panelFieldsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtHomePhone, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblHomePhone))
                        .addGap(30, 30, 30)
                        .addGroup(panelFieldsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtEmergencyPhone, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblEmergencyPhone))
                        .addGap(30, 30, 30)
                        .addGroup(panelFieldsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblMobilePhone)
                            .addComponent(txtMobilePhone, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(15, 15, 15)
                        .addComponent(checkBoxDischarged)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        panelFieldsLayout.setVerticalGroup(
            panelFieldsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelFieldsLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(panelFieldsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblSurname)
                    .addComponent(lblName)
                    .addComponent(lblTurn))
                .addGap(10, 10, 10)
                .addGroup(panelFieldsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtSurname, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(boxTurn, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(12, 12, 12)
                .addGroup(panelFieldsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelFieldsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(lblAddress)
                        .addComponent(lblPass))
                    .addComponent(lblPlaceOfBirth))
                .addGap(10, 10, 10)
                .addGroup(panelFieldsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtPass, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(panelFieldsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(txtPlaceOfBirth, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(txtAddress, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGroup(panelFieldsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelFieldsLayout.createSequentialGroup()
                        .addGap(12, 12, 12)
                        .addGroup(panelFieldsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblDischargedDate)
                            .addComponent(lblHiredDate)))
                    .addGroup(panelFieldsLayout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(panelFieldsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lblEntryDate)
                            .addComponent(lblPosition))))
                .addGroup(panelFieldsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelFieldsLayout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addGroup(panelFieldsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(panelFieldsLayout.createSequentialGroup()
                                .addGroup(panelFieldsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(dateHiredDate, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(dateBirthDate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(10, 10, 10)
                                .addComponent(lblHomePhone, javax.swing.GroupLayout.PREFERRED_SIZE, 17, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(8, 8, 8)
                                .addGroup(panelFieldsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(txtHomePhone, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txtMobilePhone, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txtEmergencyPhone, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(12, 12, 12)
                                .addGroup(panelFieldsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(lblIdNumber, javax.swing.GroupLayout.PREFERRED_SIZE, 17, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(lblIdType, javax.swing.GroupLayout.PREFERRED_SIZE, 17, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(lblMaritalStatus, javax.swing.GroupLayout.PREFERRED_SIZE, 17, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(lblBloodType, javax.swing.GroupLayout.PREFERRED_SIZE, 17, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(10, 10, 10)
                                .addGroup(panelFieldsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(txtIdNumber, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(boxIdType, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(boxMaritalStatus, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(boxBloodType, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(panelFieldsLayout.createSequentialGroup()
                                .addComponent(dateDischargedDate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(checkBoxDischarged))))
                    .addGroup(panelFieldsLayout.createSequentialGroup()
                        .addGap(8, 8, 8)
                        .addComponent(boxPosition, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(panelFieldsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lblEmergencyPhone)
                            .addComponent(lblMobilePhone))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        panelEmployeeSchedule.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "Horarios del Empleado", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Ubuntu", 1, 15))); // NOI18N

        tableEmployeeSchedule.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "Dia Entrada", "Hora Entrada", "Dia Salida", "Hora Salida"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        scrollEmployeeSchedule.setViewportView(tableEmployeeSchedule);
        if (tableEmployeeSchedule.getColumnModel().getColumnCount() > 0) {
            tableEmployeeSchedule.getColumnModel().getColumn(0).setPreferredWidth(30);
        }

        dateEntryJob.setDateFormatString("dd-MM-yyyy");

        jLabel1.setText("Desde");

        dateExitJob.setDateFormatString("dd-MM-yyyy");

        jLabel2.setText("Hasta");

        javax.swing.GroupLayout panelEmployeeScheduleLayout = new javax.swing.GroupLayout(panelEmployeeSchedule);
        panelEmployeeSchedule.setLayout(panelEmployeeScheduleLayout);
        panelEmployeeScheduleLayout.setHorizontalGroup(
            panelEmployeeScheduleLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelEmployeeScheduleLayout.createSequentialGroup()
                .addGroup(panelEmployeeScheduleLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelEmployeeScheduleLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(scrollEmployeeSchedule, javax.swing.GroupLayout.DEFAULT_SIZE, 527, Short.MAX_VALUE))
                    .addGroup(panelEmployeeScheduleLayout.createSequentialGroup()
                        .addGap(26, 26, 26)
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(dateEntryJob, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(40, 40, 40)
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(dateExitJob, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        panelEmployeeScheduleLayout.setVerticalGroup(
            panelEmployeeScheduleLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelEmployeeScheduleLayout.createSequentialGroup()
                .addGroup(panelEmployeeScheduleLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(dateEntryJob, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1)
                    .addComponent(dateExitJob, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2))
                .addGap(10, 10, 10)
                .addComponent(scrollEmployeeSchedule, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                .addContainerGap())
        );

        jPanel1.setLayout(new java.awt.GridLayout(2, 2));

        btnCreate.setText("Nuevo");
        jPanel1.add(btnCreate);

        btnDelete.setText("Borrar");
        jPanel1.add(btnDelete);

        btnModify.setText("Modificar");
        btnModify.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnModify.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnModifyActionPerformed(evt);
            }
        });
        jPanel1.add(btnModify);

        btnSave.setText("Guardar");
        jPanel1.add(btnSave);

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "Foto", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Ubuntu", 1, 15))); // NOI18N
        jPanel2.setMinimumSize(new java.awt.Dimension(227, 227));
        jPanel2.setPreferredSize(new java.awt.Dimension(227, 227));

        lblPhoto.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblPhoto, javax.swing.GroupLayout.DEFAULT_SIZE, 384, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblPhoto, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, 420, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(10, 10, 10)
                        .addComponent(jScrollPane1))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(panelFields, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(10, 10, 10)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(panelEmployeeSchedule, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 277, Short.MAX_VALUE)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, 277, Short.MAX_VALUE))
                .addGap(10, 10, 10)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(panelEmployeeSchedule, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(10, 10, 10)
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 107, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(panelFields, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(10, 10, 10))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void txtNameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNameActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtNameActionPerformed

    private void txtSurnameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtSurnameActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtSurnameActionPerformed

    private void btnModifyActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnModifyActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnModifyActionPerformed

    private void txtIdNumberActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtIdNumberActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtIdNumberActionPerformed

    private void txtAddressActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtAddressActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtAddressActionPerformed

    private void boxPositionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_boxPositionActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_boxPositionActionPerformed

    private void checkBoxDischargedActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_checkBoxDischargedActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_checkBoxDischargedActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox boxBloodType;
    private javax.swing.JComboBox boxIdType;
    private javax.swing.JComboBox boxMaritalStatus;
    private javax.swing.JComboBox boxPosition;
    private javax.swing.JComboBox boxTurn;
    private javax.swing.JButton btnCreate;
    private javax.swing.JButton btnDelete;
    private javax.swing.JButton btnModify;
    private javax.swing.JButton btnSave;
    private javax.swing.JCheckBox checkBoxDischarged;
    private com.toedter.calendar.JDateChooser dateBirthDate;
    private com.toedter.calendar.JDateChooser dateDischargedDate;
    private com.toedter.calendar.JDateChooser dateEntryJob;
    private com.toedter.calendar.JDateChooser dateExitJob;
    private com.toedter.calendar.JDateChooser dateHiredDate;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
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
    private javax.swing.JLabel lblPhoto;
    private javax.swing.JLabel lblPlaceOfBirth;
    private javax.swing.JLabel lblPosition;
    private javax.swing.JLabel lblSurname;
    private javax.swing.JLabel lblTurn;
    private javax.swing.JPanel panelEmployeeSchedule;
    private javax.swing.JPanel panelFields;
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

    /**
     * @return the panelPhoto
     */
    public javax.swing.JPanel getPanelPhoto() {
        return jPanel2;
    }

    /**
     * @return the lblPhoto
     */
    public javax.swing.JLabel getLblPhoto() {
        return lblPhoto;
    }

    /**
     * @return the photo
     */
    public ImageIcon getPhoto() {
        return photo;
    }

    /**
     * @return the checkBoxDischarged
     */
    public javax.swing.JCheckBox getCheckBoxDischarged() {
        return checkBoxDischarged;
    }
}
