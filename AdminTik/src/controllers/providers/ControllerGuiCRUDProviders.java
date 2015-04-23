/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers.providers;

import gui.providers.*;
import interfaces.providers.InterfaceProvider;
import interfaces.providers.InterfaceProviderCategory;
import interfaces.providers.InterfaceProvidersSearch;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import utils.Config;

/**
 *
 * @author eze
 */
public class ControllerGuiCRUDProviders implements ActionListener {

    private final GuiCRUDProviders guiCRUDProviders;
    private final GuiNewProvider guiNewProvider;
    private final ControllerGuiNewProvider controllerGuiNewProvider;
    private final InterfaceProviderCategory providerCategory;
    private final InterfaceProvider provider;
    private final InterfaceProvidersSearch providersSearch;

    public ControllerGuiCRUDProviders(GuiCRUDProviders guiCProv, GuiNewProvider guiNProv, GuiPaymentsToProviders guiPTP, GuiTicketsPaid guiIP) throws RemoteException, NotBoundException, MalformedURLException {
        this.provider = (InterfaceProvider) Naming.lookup("//" + Config.ip + "/crudProvider");
        this.providersSearch = (InterfaceProvidersSearch) Naming.lookup("//" + Config.ip + "/providersSearch");
        this.providerCategory = (InterfaceProviderCategory) Naming.lookup("//" + Config.ip + "/crudProviderCategory");
        
        this.guiCRUDProviders = guiCProv;
        this.guiNewProvider = guiNProv;
        this.controllerGuiNewProvider = new ControllerGuiNewProvider(this.guiNewProvider, guiPTP, guiIP);
        
        this.guiCRUDProviders.setActionListener(this);
        loadProviderCategories();
        //escucho en el txtFindProvider lo que se va ingresando para buscar un proveedor
        this.guiCRUDProviders.getTxtFindProvider().addKeyListener(new java.awt.event.KeyAdapter() {
            @Override
            public void keyReleased(java.awt.event.KeyEvent evt) {
                //saco el texto ingresado en txtFindProvider
                String txtFindProvider = guiCRUDProviders.getTxtFindProvider().getText();
                if (txtFindProvider != null) {
                    List<Map> providersList;
                    try {
                        //hago la busqueda de proveedores en base a ese texto
                        providersList = providersSearch.searchProviders(txtFindProvider);
                        //cargo los proveedores en la tabla
                        updateSearchProviderTable(providersList);
                    } catch (RemoteException ex) {
                        Logger.getLogger(ControllerGuiCRUDProviders.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        });
        //Si hace doble click en una fila de la tabla de proveedores, abre la edicion de los datos del mismo
        this.guiCRUDProviders.getTableProviders().addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                if (evt.getClickCount() == 2) {
                    JTable target = (JTable) evt.getSource();
                    int row = target.getSelectedRow();
                    try {
                        guiNewProvider.cleanComponents();
                        controllerGuiNewProvider.loadFindCategoryTable();
                        controllerGuiNewProvider.loadGUIWithData((int) target.getValueAt(row, 0));
                    } catch (RemoteException ex) {
                        Logger.getLogger(ControllerGuiCRUDProviders.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    guiCRUDProviders.cleanComponents();
                    guiNewProvider.setVisible(true);
                }
            }

        });
        //reviso si se clickea alguna fila de la tabla proveedores
        
        //reviso si se clickea alguna fila de la tabla categorias
        this.guiCRUDProviders.getTableProviderCategories().addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                //limpio la pantalla antes de cargar los datos nuevos
                guiCRUDProviders.cleanComponents();
                //saco el id de la categoria, de la fila seleccionada
                DefaultTableModel categoryModel = ((DefaultTableModel) guiCRUDProviders.getTableProviderCategories().getModel());
                int selectedRow = guiCRUDProviders.getTableProviderCategories().getSelectedRow();
                int categoryId = Integer.parseInt(categoryModel.getValueAt(selectedRow, 0).toString());
                List<Map> providersList = null;
                try {
                    //busco la lista de proveedores de la categoria seleccionada
                    providersList = providerCategory.getProvidersFromCategory(categoryId);
                } catch (RemoteException ex) {
                    Logger.getLogger(ControllerGuiCRUDProviders.class.getName()).log(Level.SEVERE, null, ex);
                }
                //si hay proveedores en la lista
                if (providersList != null && !providersList.isEmpty()) {
                    try {
                        //cargo los proveedores en la tabla
                        updateSearchProviderTable(providersList);
                    } catch (RemoteException ex) {
                        Logger.getLogger(ControllerGuiCRUDProviders.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                //guiCRUDProviders.getBtnRemoveCategory().setEnabled(true);
                //Si hago doble click en la tabla de categorías muestro un diálogo de edición.
                if (evt.getClickCount() == 2) {
                    JTable target = (JTable) evt.getSource();
                    int row = target.getSelectedRow();
                    String categoryName = JOptionPane.showInputDialog(guiCRUDProviders, "Ingrese el nuevo nombre de la categoría.", "Modificar categoría", JOptionPane.PLAIN_MESSAGE);
                    if (categoryName != null) {
                        if (categoryName.trim().isEmpty()) {
                            JOptionPane.showMessageDialog(guiCRUDProviders, "Error: Falta especificar un nombre.", "Error", JOptionPane.ERROR_MESSAGE);
                        } else {
                            try {
                                providerCategory.modify((int) target.getValueAt(row, 0), categoryName);
                                loadProviderCategories();
                            } catch (RemoteException ex) {
                                Logger.getLogger(ControllerGuiCRUDProviders.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        }
                    }
                }
            }
        });
        guiCRUDProviders.getTableProviders().getSelectionModel().addListSelectionListener(new ListSelectionListener() {

            @Override
            public void valueChanged(ListSelectionEvent e) {
                int row = guiCRUDProviders.getTableProviders().getSelectedRow();
                if (row == -1){
                    guiCRUDProviders.getBtnRemoveProvider().setEnabled(false);
                } else {
                    guiCRUDProviders.getBtnRemoveProvider().setEnabled(true);
                }
            }
        });
        
        guiCRUDProviders.getTableProviderCategories().getSelectionModel().addListSelectionListener(new ListSelectionListener() {

            @Override
            public void valueChanged(ListSelectionEvent e) {
                int row = guiCRUDProviders.getTableProviderCategories().getSelectedRow();
                if (row != -1){
                    guiCRUDProviders.getBtnRemoveCategory().setEnabled(false);
                }
            }
        });
    }

    private void loadProviderCategories() throws RemoteException {
        List<Map> providerCategoriesList = this.providerCategory.getProviderCategories();
        DefaultTableModel providerCategoriesTableModel = (DefaultTableModel) this.guiCRUDProviders.getTableProviderCategories().getModel();
        providerCategoriesTableModel.setRowCount(0);
        Object[] o = new Object[2];
        for (Map provCat : providerCategoriesList) {
            o[0] = provCat.get("id");
            o[1] = provCat.get("name");
            providerCategoriesTableModel.addRow(o);
        }
    }

    private void updateSearchProviderTable(List<Map> providersList) throws RemoteException {
        if (providersList != null) { //si hay proveedores los cargo en la gui
            DefaultTableModel providerModel = ((DefaultTableModel) this.guiCRUDProviders.getTableProviders().getModel());
            providerModel.setRowCount(0);//limpio la tabla antes de cargarla nuevamente
            Map<String, Object> providerMap;
            Object[] o = new Object[7];
            Iterator<Map> providerMapItr = providersList.iterator();
            while (providerMapItr.hasNext()) {
                providerMap = providerMapItr.next();
                o[0] = (providerMap.get("id"));
                o[1] = (providerMap.get("name"));
                o[2] = (providerMap.get("cuit"));
                o[3] = (providerMap.get("address"));
                o[4] = (providerMap.get("description"));
                o[5] = (providerMap.get("phones"));
                //busco las categorias de cada proveedor y las cargo en la tabla
                List<Map> categoriesList = this.provider.getCategoriesFromProvider(Integer.parseInt(providerMap.get("id").toString()));
                Iterator<Map> categoriesItr = categoriesList.iterator();
                String categories = "";
                while (categoriesItr.hasNext()) {
                    Map<String, Object> categoryMap = categoriesItr.next();
                    categories += categoryMap.get("name") + " ";
                }
                o[6] = (categories);
                providerModel.addRow(o);
            }
        }

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        //Si presiono el boton de agregar una nueva categoria
        if (e.getSource().equals(this.guiCRUDProviders.getBtnNewCategory())) {
            String categoryName = JOptionPane.showInputDialog(guiCRUDProviders, "Ingrese el nombre de la categoría.", "Modificar categoría", JOptionPane.PLAIN_MESSAGE);
            if (categoryName != null) {
                if (categoryName.trim().isEmpty()) {
                    JOptionPane.showMessageDialog(guiCRUDProviders, "Error: El nombre no puede ser vacío.", "Error", JOptionPane.ERROR_MESSAGE);
                } else {
                    try {
                        providerCategory.create(categoryName);
                    } catch (RemoteException ex) {
                        Logger.getLogger(ControllerGuiCRUDProviders.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        }
        if (e.getSource().equals(this.guiCRUDProviders.getBtnRemoveCategory())) {
            Integer resp = JOptionPane.showConfirmDialog(guiCRUDProviders, "¿Desea borrar la categoría seleccionada?", "Confirmar borrado", JOptionPane.YES_NO_OPTION);
            if (resp == JOptionPane.YES_OPTION) {
                int row = guiCRUDProviders.getTableProviderCategories().getSelectedRow();
                try {
                    if (providerCategory.delete((int) guiCRUDProviders.getDefaultTableProviderCategories().getValueAt(row, 0))) {
                        JOptionPane.showMessageDialog(guiCRUDProviders, "Categoría eliminada correctamente", "Operacion exitosa", JOptionPane.INFORMATION_MESSAGE);
                    } else {
                        JOptionPane.showMessageDialog(guiCRUDProviders, "Ocurrio un error", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                } catch (RemoteException ex) {
                    Logger.getLogger(ControllerGuiCRUDProviders.class.getName()).log(Level.SEVERE, null, ex);
                }
            }

        }
        //Si presiono el boton de agregar un nuevo proveedor
        if (e.getSource().equals(this.guiCRUDProviders.getBtnNewProvider())) {
            this.guiNewProvider.cleanComponents();
            this.guiCRUDProviders.cleanComponents();
            try {
                //cargo las categorias disponibles en la tabla correspondiente de la guiNewProvider
                this.controllerGuiNewProvider.setModify(false);
                this.controllerGuiNewProvider.loadFindCategoryTable();
            } catch (RemoteException ex) {
                Logger.getLogger(ControllerGuiCRUDProviders.class.getName()).log(Level.SEVERE, null, ex);
            }
            this.guiNewProvider.setVisible(true);
        }
        /*Si presiono el boton de eliminar un proveedor(el cual debera ser seleccionado previamente
         * de la tabala de proveedores.
         */
        if (e.getSource().equals(this.guiCRUDProviders.getBtnRemoveProvider())) {
            Integer resp = JOptionPane.showConfirmDialog(guiCRUDProviders, "¿Desea borrar el proveedor seleccionada?", "Confirmar borrado", JOptionPane.YES_NO_OPTION);
            if (resp == JOptionPane.YES_OPTION) {
                int row = guiCRUDProviders.getTableProviders().getSelectedRow();
                try {
                    if (provider.delete((int) guiCRUDProviders.getDefaultTableProviders().getValueAt(row, 0))) {
                        JOptionPane.showMessageDialog(guiCRUDProviders, "Proveedor eliminado correctamente", "Operacion exitosa", JOptionPane.INFORMATION_MESSAGE);
                        List<Map> providersList = Collections.EMPTY_LIST;
                        //hago la busqueda de proveedores en base a ese texto
                        providersList = providersSearch.searchProviders(guiCRUDProviders.getTxtFindProvider().getText());
                        //cargo los proveedores en la tabla
                        updateSearchProviderTable(providersList);
                    } else {
                        JOptionPane.showMessageDialog(guiCRUDProviders, "Ocurrio un error", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                } catch (RemoteException ex) {
                    Logger.getLogger(ControllerGuiCRUDProviders.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        try {
            loadProviderCategories();
        } catch (RemoteException ex) {
            Logger.getLogger(ControllerGuiCRUDProviders.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
