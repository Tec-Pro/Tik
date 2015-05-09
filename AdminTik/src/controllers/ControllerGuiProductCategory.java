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
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import utils.Config;
import utils.InterfaceName;

/**
 *
 * @author Alan Gonzalez
 */
public class ControllerGuiProductCategory implements ActionListener {

    private final GuiCRUDProductCategory guiProductCategory;
    private final InterfaceCategory crudProductCategory;

    public ControllerGuiProductCategory(GuiCRUDProductCategory gui) throws NotBoundException, MalformedURLException, RemoteException {
        crudProductCategory = (InterfaceCategory) Naming.lookup("//" + Config.ip + "/"+InterfaceName.CRUDCategory);
        guiProductCategory = gui;
        guiProductCategory.setActionListener(this);
        refreshListCategory();
        guiProductCategory.getTableCategory().getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                int r = guiProductCategory.getTableCategory().getSelectedRow();
                if (r != -1) {
                    try {
                        categorySelected(r);
                    } catch (RemoteException ex) {
                        Logger.getLogger(ControllerGuiProductCategory.class.getName()).log(Level.SEVERE, null, ex);
                    }
                } else {
                    categoryNotSelected();
                }
            }
        });

        guiProductCategory.getTableCategory().addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                int r = guiProductCategory.getTableCategory().getSelectedRow();
                if (r != -1) {
                    try {
                        categorySelected(r);
                    } catch (RemoteException ex) {
                        Logger.getLogger(ControllerGuiProductCategory.class.getName()).log(Level.SEVERE, null, ex);
                    }
                } else {
                    categoryNotSelected();
                }
            }
        });

        guiProductCategory.getTableSubCategory().getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                int r = guiProductCategory.getTableSubCategory().getSelectedRow();
                if (r != -1) {
                    subcategorySelected(r);
                } else {
                    subcategoryNotSelected();
                }
            }
        });

        guiProductCategory.getTableSubCategory().addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                int r = guiProductCategory.getTableSubCategory().getSelectedRow();
                if (r != -1) {
                    subcategorySelected(r);
                } else {
                    subcategoryNotSelected();
                }
            }
        });
    }
    
    /**
     * Acomoda la interfaz acorde a la categoria seleccionada.
     * @param r : numero de fila de la categoria seleccionada
     * @throws RemoteException
     */
    private void categorySelected(int r) throws RemoteException {
        guiProductCategory.getTxtCategory().setText(guiProductCategory.getTableCategoryDefault().getValueAt(r, 1).toString());
        guiProductCategory.stateCategorySelected();
        guiProductCategory.setBtnUpdateCategorySelected(false);
        guiProductCategory.getTableSubCategoryDefault().setRowCount(0);
        guiProductCategory.getBtnNewSubCategory().setEnabled(true);
        int id = Integer.parseInt(guiProductCategory.getTableCategory().getValueAt(r, 0).toString());
        List<Map> listSubCategories = crudProductCategory.getSubcategoriesCategory(id);
        Iterator<Map> it = listSubCategories.iterator();
        while (it.hasNext()) {
            Map<String, Object> subCategory = it.next();
            Object row[] = new String[2];
            row[0] = subCategory.get("id").toString();
            row[1] = subCategory.get("name").toString();
            guiProductCategory.getTableSubCategoryDefault().addRow(row);
        }
        if(guiProductCategory.getTxtCategory().getText().equals("CATEGORIA POR DEFECTO")){
            guiProductCategory.getBtnUpdateCategory().setEnabled(false);
            guiProductCategory.getBtnDeleteCategory().setEnabled(false);
            guiProductCategory.getBtnDeleteSubCategory().setEnabled(false);
            guiProductCategory.getBtnNewSubCategory().setEnabled(false);
            guiProductCategory.getBtnUpdateSubCategory().setEnabled(false);
        }
    }
    
    /**
     * Acomoda la interfaz en el caso en que no haya una categoria seleccionada.
     *
     */
    private void categoryNotSelected() {
        guiProductCategory.initialStateCategoryPanel();
        guiProductCategory.cleanFieldsCategoryPanel();
        guiProductCategory.getTableSubCategoryDefault().setRowCount(0);
        guiProductCategory.getBtnNewSubCategory().setEnabled(false);
        guiProductCategory.setBtnUpdateCategorySelected(false);
        guiProductCategory.cleanFieldsSubCategoryPanel();
        guiProductCategory.initialStateSubCategoryPanel();
        guiProductCategory.setBtnUpdateSubCategorySelected(false);
    }
    /**
     * Acomoda la interfaz en el caso en que se seleccione una subcategoria
     *
     */
    private void subcategorySelected(int r) {
        guiProductCategory.stateSubCategorySelected();
        guiProductCategory.getTxtSubCategory().setText(guiProductCategory.getTableSubCategoryDefault().getValueAt(r, 1).toString());
        guiProductCategory.setBtnUpdateSubCategorySelected(false);
        guiProductCategory.getBtnNewSubCategory().setEnabled(true);
        if(guiProductCategory.getTxtSubCategory().getText().equals("SUBCATEGORIA POR DEFECTO")){
            guiProductCategory.getBtnUpdateCategory().setEnabled(false);
            guiProductCategory.getBtnDeleteCategory().setEnabled(false);
            guiProductCategory.getBtnDeleteSubCategory().setEnabled(false);
            guiProductCategory.getBtnNewSubCategory().setEnabled(false);
            guiProductCategory.getBtnUpdateSubCategory().setEnabled(false);
        }
    }

    /**
     * Acomoda la interfaz en el caso en que no haya una subcategoria seleccionada.
     *
     */
    private void subcategoryNotSelected() {
        guiProductCategory.initialStateSubCategoryPanel();
        guiProductCategory.cleanFieldsSubCategoryPanel();
        guiProductCategory.setBtnUpdateSubCategorySelected(false);
        guiProductCategory.getBtnNewSubCategory().setEnabled(true);
    }

    /**
     * Verifica que el campo del nombre dela categoria no sea vacio.
     * @return boolean
     */
    private boolean dataCategoryIsValid() {
        return !guiProductCategory.getTxtCategory().getText().equals("");
    }

    /**
     * Verifica que el campo del nombre dela categoria no sea vacio.
     * @return boolean
     */
    private boolean dataSubcategoryIsValid() {
        return !guiProductCategory.getTxtSubCategory().getText().equals("");
    }

    /**
     * Actualiza la lista de categorias.
     *
     */
    private void refreshListCategory() throws RemoteException {
        guiProductCategory.getTableCategoryDefault().setRowCount(0);
        List<Map> list = crudProductCategory.getCategories();
        Iterator<Map> it = list.iterator();
        while (it.hasNext()) {
            Map<String, Object> category = it.next();
            Object row[] = new String[2];
            row[0] = category.get("id").toString();
            row[1] = category.get("name").toString();
            guiProductCategory.getTableCategoryDefault().addRow(row);
        }
    }

    /**
     * Actualiza la lista de subcategorias.
     *
     */
    private void refreshListSubCategory() throws RemoteException {
        guiProductCategory.getTableSubCategoryDefault().setRowCount(0);
        int rowSelected = guiProductCategory.getTableCategory().getSelectedRow();
        int id = Integer.parseInt(guiProductCategory.getTableCategory().getValueAt(rowSelected, 0).toString());
        List<Map> list = crudProductCategory.getSubcategoriesCategory(id);
        Iterator<Map> it = list.iterator();
        while (it.hasNext()) {
            Map<String, Object> subcategory = it.next();
            Object row[] = new String[2];
            row[0] = subcategory.get("id").toString();
            row[1] = subcategory.get("name").toString();
            guiProductCategory.getTableSubCategoryDefault().addRow(row);
        }
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        if (ae.getSource().equals(guiProductCategory.getBtnNewCategory())) {
            if (guiProductCategory.getBtnNewCategory().getText().equals("Nueva")) {
                guiProductCategory.stateNewAndUpdateCategory();
                guiProductCategory.cleanFieldsCategoryPanel();
            } else {
                if (guiProductCategory.getBtnNewCategory().getText().equals("Guardar") && !guiProductCategory.isBtnUpdateCategorySelected()) {
                    //se crea una nueva categoria
                    if (!dataCategoryIsValid()) {
                        JOptionPane.showMessageDialog(guiProductCategory, "Los campos no pueden estar vacios!", "Error!", JOptionPane.ERROR_MESSAGE);
                    } else {
                        try {
                            Map<String, Object> newCategory;
                            newCategory = crudProductCategory.create(guiProductCategory.getTxtCategory().getText());
                            if (newCategory != null) {
                                JOptionPane.showMessageDialog(guiProductCategory, "Nueva categoria creada exitosamente!", "Categoria creada!", JOptionPane.INFORMATION_MESSAGE);
                                guiProductCategory.initialStateCategoryPanel();
                                guiProductCategory.cleanFieldsCategoryPanel();
                                refreshListCategory();
                            } else {
                                JOptionPane.showMessageDialog(guiProductCategory, "El nombre ya existe!", "Error!", JOptionPane.ERROR_MESSAGE);
                            }
                        } catch (RemoteException ex) {
                            Logger.getLogger(ControllerGuiCRUDAdmin.class.getName()).log(Level.SEVERE, null, ex);
                        }

                    }
                } else {
                    if (guiProductCategory.getBtnNewCategory().getText().equals("Guardar") && guiProductCategory.isBtnUpdateCategorySelected()) {
                        //se modifica una categoria
                        if (!dataCategoryIsValid()) {
                            JOptionPane.showMessageDialog(guiProductCategory, "Los campos no pueden estar vacios!", "Error!", JOptionPane.ERROR_MESSAGE);
                        } else {
                            int row = guiProductCategory.getTableCategory().getSelectedRow();
                            if (!String.valueOf(guiProductCategory.getTableCategoryDefault().getValueAt(row, 1)).equals(guiProductCategory.getTxtCategory().getText())) {
                                try {
                                    if (crudProductCategory.categoryExists(guiProductCategory.getTxtCategory().getText())) {
                                        JOptionPane.showMessageDialog(guiProductCategory, "El nombre ya existe!", "Error!", JOptionPane.ERROR_MESSAGE);
                                    } else {
                                        try {
                                            Map<String, Object> modifiedCategory;
                                            int id = Integer.parseInt(guiProductCategory.getTableCategory().getValueAt(row, 0).toString());
                                            modifiedCategory = crudProductCategory.modify(id, guiProductCategory.getTxtCategory().getText());
                                            if (modifiedCategory != null) {
                                                JOptionPane.showMessageDialog(guiProductCategory, "Datos modificados correctamente!", "Modificacion exitosa!", JOptionPane.INFORMATION_MESSAGE);
                                                guiProductCategory.stateAfterUpdateCategory();
                                                guiProductCategory.setBtnUpdateCategorySelected(false);
                                                refreshListCategory();
                                            } else {
                                                JOptionPane.showMessageDialog(guiProductCategory, "No se pudo modificar!", "Error!", JOptionPane.ERROR_MESSAGE);
                                            }
                                        } catch (RemoteException ex) {
                                            Logger.getLogger(ControllerGuiCRUDAdmin.class.getName()).log(Level.SEVERE, null, ex);
                                        }
                                    }
                                } catch (RemoteException ex) {
                                    Logger.getLogger(ControllerGuiProductCategory.class.getName()).log(Level.SEVERE, null, ex);
                                }
                            } else {
                                JOptionPane.showMessageDialog(guiProductCategory, "No se detecto cambio en el nombre de la categoria.", "Informacion", JOptionPane.INFORMATION_MESSAGE);
                                guiProductCategory.stateAfterUpdateCategory();
                                guiProductCategory.setBtnUpdateCategorySelected(false);
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
        if (ae.getSource().equals(guiProductCategory.getBtnDeleteCategory())) {
            if (guiProductCategory.getBtnDeleteCategory().getText().equals("Eliminar")) {
                Integer resp = JOptionPane.showConfirmDialog(guiProductCategory, "¿Desea borrar la categoria " + guiProductCategory.getTxtCategory().getText() + "? Se borraran todas las subcategorias.", "Confirmar borrado", JOptionPane.YES_NO_OPTION);
                if (resp == JOptionPane.YES_OPTION) {
                    int row = guiProductCategory.getTableCategory().getSelectedRow();
                    try {
                        int id = Integer.parseInt(guiProductCategory.getTableCategory().getValueAt(row, 0).toString());
                        if (crudProductCategory.delete(id)) {
                            JOptionPane.showMessageDialog(guiProductCategory, "Categoria eliminada.", "Categorio eliminada", JOptionPane.INFORMATION_MESSAGE);
                            refreshListCategory();
                            guiProductCategory.initialStateCategoryPanel();
                            guiProductCategory.cleanFieldsCategoryPanel();
                        } else {
                            JOptionPane.showMessageDialog(guiProductCategory, "No se pudo eliminar!", "Error!", JOptionPane.ERROR_MESSAGE);
                        }
                    } catch (RemoteException ex) {
                        Logger.getLogger(ControllerGuiProductCategory.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
            if (guiProductCategory.getBtnDeleteCategory().getText().equals("Cancelar")) {
                guiProductCategory.initialStateCategoryPanel();
                guiProductCategory.cleanFieldsCategoryPanel();
                guiProductCategory.setBtnUpdateCategorySelected(false);
            }
        }
        /*
         ******Controlador correspondiente al panel de subcategorias
         */
        if (ae.getSource().equals(guiProductCategory.getBtnNewSubCategory())) {
            if (guiProductCategory.getBtnNewSubCategory().getText().equals("Nueva")) {
                guiProductCategory.stateNewAndUpdateSubCategory();
                guiProductCategory.cleanFieldsSubCategoryPanel();
            } else {
                if (guiProductCategory.getBtnNewSubCategory().getText().equals("Guardar") && !guiProductCategory.isBtnUpdateSubCategorySelected()) {
                    //se crea una nueva categoria
                    if (!dataSubcategoryIsValid()) {
                        JOptionPane.showMessageDialog(guiProductCategory, "Los campos no pueden estar vacios!", "Error!", JOptionPane.ERROR_MESSAGE);
                    } else {
                        try {
                            Map<String, Object> newSubCategory;
                            int r = guiProductCategory.getTableCategory().getSelectedRow();
                            int id = Integer.parseInt(guiProductCategory.getTableCategory().getValueAt(r, 0).toString());
                            newSubCategory = crudProductCategory.addSubcategory(id, guiProductCategory.getTxtSubCategory().getText());
                            if (newSubCategory != null) {
                                JOptionPane.showMessageDialog(guiProductCategory, "Nueva subcategoria creada exitosamente!", "SubCategoria creada!", JOptionPane.INFORMATION_MESSAGE);
                                guiProductCategory.initialStateSubCategoryPanel();
                                guiProductCategory.cleanFieldsSubCategoryPanel();
                                refreshListSubCategory();
                            } else {
                                JOptionPane.showMessageDialog(guiProductCategory, "Subcategoria ya existente", "Error!", JOptionPane.ERROR_MESSAGE);
                            }
                        } catch (RemoteException ex) {
                            Logger.getLogger(ControllerGuiCRUDAdmin.class.getName()).log(Level.SEVERE, null, ex);
                        }

                    }
                } else {
                    if (guiProductCategory.getBtnNewSubCategory().getText().equals("Guardar") && guiProductCategory.isBtnUpdateSubCategorySelected()) {
                        //se modifica una categoria
                        if (!dataSubcategoryIsValid()) {
                            JOptionPane.showMessageDialog(guiProductCategory, "Los campos no pueden estar vacios!", "Error!", JOptionPane.ERROR_MESSAGE);
                        } else {
                            int row = guiProductCategory.getTableSubCategory().getSelectedRow();
                            if (!String.valueOf(guiProductCategory.getTableSubCategoryDefault().getValueAt(row, 1)).equals(guiProductCategory.getTxtSubCategory().getText())) {
                                try {
                                    if (crudProductCategory.subCategoryExists(guiProductCategory.getTxtSubCategory().getText())) {
                                        JOptionPane.showMessageDialog(guiProductCategory, "El nombre ya existe!", "Error!", JOptionPane.ERROR_MESSAGE);
                                    } else {
                                        try {
                                            Map<String, Object> modifiedSubCategory;
                                            int id = Integer.parseInt(guiProductCategory.getTableSubCategory().getValueAt(row, 0).toString());
                                            modifiedSubCategory = crudProductCategory.modifySubcategory(id, guiProductCategory.getTxtSubCategory().getText());
                                            if (modifiedSubCategory != null) {
                                                JOptionPane.showMessageDialog(guiProductCategory, "Datos modificados correctamente!", "Modificacion exitosa!", JOptionPane.INFORMATION_MESSAGE);
                                                guiProductCategory.stateAfterUpdateSubCategory();
                                                guiProductCategory.setBtnUpdateSubCategorySelected(false);
                                                refreshListSubCategory();
                                            } else {
                                                JOptionPane.showMessageDialog(guiProductCategory, "No se pudo modificar!", "Error!", JOptionPane.ERROR_MESSAGE);
                                            }
                                        } catch (RemoteException ex) {
                                            Logger.getLogger(ControllerGuiCRUDAdmin.class.getName()).log(Level.SEVERE, null, ex);
                                        }
                                    }
                                } catch (RemoteException ex) {
                                    Logger.getLogger(ControllerGuiProductCategory.class.getName()).log(Level.SEVERE, null, ex);
                                }
                            } else {

                                JOptionPane.showMessageDialog(guiProductCategory, "No se detecto cambio en el nombre de la subcategoria.", "Informacion", JOptionPane.INFORMATION_MESSAGE);
                                guiProductCategory.stateAfterUpdateSubCategory();
                                guiProductCategory.setBtnUpdateSubCategorySelected(false);

                            }
                        }
                    }
                }
            }
        }
        if (ae.getSource().equals(guiProductCategory.getBtnUpdateSubCategory())) {
            guiProductCategory.setBtnUpdateSubCategorySelected(true);
            guiProductCategory.stateNewAndUpdateSubCategory();
        }
        if (ae.getSource().equals(guiProductCategory.getBtnDeleteSubCategory())) {
            if (guiProductCategory.getBtnDeleteSubCategory().getText().equals("Eliminar")) {
                Integer resp = JOptionPane.showConfirmDialog(guiProductCategory, "¿Desea borrar la subcategoria " + guiProductCategory.getTxtSubCategory().getText() + "?", "Confirmar borrado", JOptionPane.YES_NO_OPTION);
                if (resp == JOptionPane.YES_OPTION) {
                    int row = guiProductCategory.getTableSubCategory().getSelectedRow();
                    try {
                        int id = Integer.parseInt(guiProductCategory.getTableSubCategory().getValueAt(row, 0).toString());
                        if (crudProductCategory.deleteSubcategory(id)) {
                            JOptionPane.showMessageDialog(guiProductCategory, "SubCategoria eliminada.", "SubCategoria eliminada", JOptionPane.INFORMATION_MESSAGE);
                            refreshListSubCategory();
                            guiProductCategory.initialStateSubCategoryPanel();
                            guiProductCategory.cleanFieldsSubCategoryPanel();
                        } else {
                            JOptionPane.showMessageDialog(guiProductCategory, "No se pudo eliminar!", "Error!", JOptionPane.ERROR_MESSAGE);
                        }
                    } catch (RemoteException ex) {
                        Logger.getLogger(ControllerGuiProductCategory.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
            if (guiProductCategory.getBtnDeleteSubCategory().getText().equals("Cancelar")) {
                guiProductCategory.initialStateSubCategoryPanel();
                guiProductCategory.cleanFieldsSubCategoryPanel();
                guiProductCategory.setBtnUpdateSubCategorySelected(false);
            }
        }
    }
}
