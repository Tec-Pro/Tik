/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import gui.GuiCRUDProductCategory;
import interfaces.InterfaceCategory;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author Alan Gonzalez
 */
public class ControllerGuiProductCategory implements ActionListener {

    private final GuiCRUDProductCategory guiProductCategory;
    private final InterfaceCategory crudProductCategory;

    public ControllerGuiProductCategory(GuiCRUDProductCategory gui) throws NotBoundException, MalformedURLException, RemoteException {
        crudProductCategory = (InterfaceCategory) Naming.lookup("//192.168.0.15/crudProductCategory");
        guiProductCategory = gui;
        guiProductCategory.setActionListener(this);
    }

    private boolean dataIsValid() {
        return !guiProductCategory.getTxtCategory().getText().equals("");
    }

    private void refreshListCategory() throws RemoteException {
        guiProductCategory.getTableCategoryDefault().setRowCount(0);
        List<Map> list = crudProductCategory.getCategories();
        Iterator<Map> it = list.iterator();
        while (it.hasNext()) {
            Map<String, Object> category = it.next();
            Object row[] = new String[2];
            row[0] = category.get("id").toString();
            row[1] = category.get("name").toString(); //NOMBRE
            guiProductCategory.getTableCategoryDefault().addRow(row);
        }
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        if (ae.getSource().equals(guiProductCategory.getBtnNewCategory())) {
            if (guiProductCategory.getBtnNewCategory().getText().equals("Nueva")) {
                guiProductCategory.stateNewAndUpdateCategory();
                guiProductCategory.cleanFieldsCategoryPanel();
            }
            if (guiProductCategory.getBtnNewCategory().getText().equals("Guardar") && !guiProductCategory.isBtnUpdateCategorySelected()) {
                //se crea una nueva categoria
                if (!dataIsValid()) {
                    JOptionPane.showMessageDialog(guiProductCategory, "Los campos no pueden estar vacios!", "Error!", JOptionPane.ERROR_MESSAGE);
                } else {
                    try {
                        Map<String, Object> newCategory;
                        newCategory = crudProductCategory.create(guiProductCategory.getTxtCategory().getText());
                        if (newCategory != null) {
                            JOptionPane.showMessageDialog(guiProductCategory, "Nueva categoria creada exitosamente!", "Categoria creada!", JOptionPane.INFORMATION_MESSAGE);
                            guiProductCategory.stateInitialCategoryPanel();
                            guiProductCategory.cleanFieldsCategoryPanel();
                            refreshListCategory();
                        } else {
                            JOptionPane.showMessageDialog(guiProductCategory, "El nombre ya existe!", "Error!", JOptionPane.ERROR_MESSAGE);
                        }
                    } catch (RemoteException ex) {
                        Logger.getLogger(ControllerGuiCRUDAdmin.class.getName()).log(Level.SEVERE, null, ex);
                    }

                }
            }
            if (guiProductCategory.getBtnNewCategory().getText().equals("Guardar") && guiProductCategory.isBtnUpdateCategorySelected()) {
                //se modifica una categoria
                if (!dataIsValid()) {
                    JOptionPane.showMessageDialog(guiProductCategory, "Los campos no pueden estar vacios!", "Error!", JOptionPane.ERROR_MESSAGE);
                } else {
                    int row = guiProductCategory.getTableCategory().getSelectedRow();
                    if (!String.valueOf(guiProductCategory.getTableCategoryDefault().getValueAt(row, 1)).equals(guiProductCategory.getTxtCategory().getText())) {
                        if (crudProductCategory.categoryExists(guiProductCategory.getTxtCategory().getText())) {
                            JOptionPane.showMessageDialog(guiProductCategory, "El nombre ya existe!", "Error!", JOptionPane.ERROR_MESSAGE);
                        } else {
                            try {
                                Map<String, Object> modifiedCategory;
                                modifiedCategory = crudProductCategory.modify((int) guiProductCategory.getTableCategoryDefault().getValueAt(row, 1), guiProductCategory.getTxtCategory().getText());
                                if (modifiedCategory != null) {
                                    JOptionPane.showMessageDialog(guiProductCategory, "Datos modificados correctamente!", "Modificacion exitosa!", JOptionPane.INFORMATION_MESSAGE);
                                    guiProductCategory.stateAfterUpdateCategory();
                                    refreshListCategory();
                                } else {
                                    JOptionPane.showMessageDialog(guiProductCategory, "No se pudo modificar!", "Error!", JOptionPane.ERROR_MESSAGE);
                                }
                            } catch (RemoteException ex) {
                                Logger.getLogger(ControllerGuiCRUDAdmin.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        }
                    }
                }

            }
        }
        if (ae.getSource().equals(guiProductCategory.getBtnUpdateCategory())) {
            guiProductCategory.setBtnUpdateCategorySelected(true);
            guiProductCategory.stateNewAndUpdateCategory();
        }
    }
}
