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
import java.awt.Frame;
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
    private final InterfaceProviderCategory providerCategory;
    private final InterfaceProvider provider;
    private final InterfaceProvidersSearch providersSearch;
    private static Integer iDCurrentlySelectedProvider;

    public ControllerGuiCRUDProviders(GuiCRUDProviders guiCProv) throws RemoteException, NotBoundException, MalformedURLException {
        this.provider = (InterfaceProvider) Naming.lookup("//" + Config.ip + "/crudProvider");
        this.providersSearch = (InterfaceProvidersSearch) Naming.lookup("//" + Config.ip + "/providersSearch");
        this.providerCategory = (InterfaceProviderCategory) Naming.lookup("//" + Config.ip + "/crudProviderCategory");
        this.guiCRUDProviders = guiCProv;
        iDCurrentlySelectedProvider = -1;
        this.guiCRUDProviders.setActionListener(this);
        loadProviderCategories();
        updateSearchProviderTable(provider.getProviders());
        //escucho en el txtFindProvider lo que se va ingresando para buscar un proveedor
        this.guiCRUDProviders.getTxtFindProvider().addKeyListener(new java.awt.event.KeyAdapter() {
            @Override
            public void keyReleased(java.awt.event.KeyEvent evt) {
                //saco el texto ingresado en txtFindProvider
                String txtFindProvider = guiCRUDProviders.getTxtFindProvider().getText();
                if (txtFindProvider != null) {
                    List<Map> providersList = null;
                    int row = guiCRUDProviders.getTableProviderCategories().getSelectedRow();
                    if (row != -1){
                        try {
                            providersList = providersSearch.searchProviders(txtFindProvider, (int) guiCRUDProviders.getTableProviderCategories().getModel().getValueAt(row, 0));
                            updateSearchProviderTable(providersList);
                        } catch (RemoteException ex) {
                            Logger.getLogger(ControllerGuiCRUDProviders.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    } else {
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
                        GuiNewProvider guiNewProvider = new GuiNewProvider(null, true);
                        ControllerGuiNewProvider controllerGuiNewProvider = new ControllerGuiNewProvider(guiNewProvider);
                        controllerGuiNewProvider.loadFindCategoryTable();
                        controllerGuiNewProvider.loadGUIWithData((int) target.getValueAt(row, 0));
                        guiNewProvider.setVisible(true);
                    } catch (RemoteException | NotBoundException | MalformedURLException ex) {
                        Logger.getLogger(ControllerGuiCRUDProviders.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    //hago una busqueda nueva de proveedores y lo cargo en la tabla, para actualizar posibles modificaciones
                    String txtFindProvider = guiCRUDProviders.getTxtFindProvider().getText();
                    //if (txtFindProvider != null) {
                        List<Map> providersList;
                        try {
                            //hago la busqueda de proveedores en base a ese texto
                            providersList = providersSearch.searchProviders(txtFindProvider);
                            //cargo los proveedores en la tabla
                            updateSearchProviderTable(providersList);
                        } catch (RemoteException ex) {
                            Logger.getLogger(ControllerGuiCRUDProviders.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    //}
                }
            }

        });

        //reviso si se clickea alguna fila de la tabla categorias
        this.guiCRUDProviders.getTableProviderCategories().addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
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
                if (row == -1) {
                    guiCRUDProviders.getBtnRemoveProvider().setEnabled(false);
                    iDCurrentlySelectedProvider = -1;
                } else {
                    guiCRUDProviders.getBtnRemoveProvider().setEnabled(true);
                    guiCRUDProviders.getBtnTicketsPaid().setEnabled(true);
                    guiCRUDProviders.getBtnPayments().setEnabled(true);
                    int selectedRow = guiCRUDProviders.getTableProviders().getSelectedRow();
                    iDCurrentlySelectedProvider = Integer.parseInt(((DefaultTableModel) guiCRUDProviders.getTableProviders().getModel()).getValueAt(selectedRow, 0).toString());
                }
            }
        });

        guiCRUDProviders.getTableProviderCategories().getSelectionModel().addListSelectionListener(new ListSelectionListener() {

            @Override
            public void valueChanged(ListSelectionEvent e) {
                int row = guiCRUDProviders.getTableProviderCategories().getSelectedRow();
                if (row != -1) {
                    iDCurrentlySelectedProvider = -1;
                    guiCRUDProviders.getBtnRemoveCategory().setEnabled(false);
                    guiCRUDProviders.getBtnTicketsPaid().setEnabled(false);
                    guiCRUDProviders.getBtnPayments().setEnabled(false);
                    try {
                    updateSearchProviderTable(providerCategory.getProvidersFromCategory((int) guiCRUDProviders.getTableProviderCategories().getModel().getValueAt(row, 0)));
                } catch (RemoteException ex) {
                    Logger.getLogger(ControllerGuiCRUDProviders.class.getName()).log(Level.SEVERE, null, ex);
                }
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
        if(providersList != null){
        //si hay proveedores los cargo en la gui
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
        //si presiono el boton LISTADO DE PAGOS REALIZADOS
        if (e.getSource().equals(this.guiCRUDProviders.getBtnPayments())) {
            GuiPaymentsToProviders guiPaymentsToProviders = new GuiPaymentsToProviders(null, true);
            new ControllerGuiPaymentsToProviders(guiPaymentsToProviders, iDCurrentlySelectedProvider);
            guiPaymentsToProviders.setVisible(true);
        }
        //si presiono el boton LISTADO DE FACTURACIÓN
        if (e.getSource().equals(this.guiCRUDProviders.getBtnTicketsPaid())) {

            GuiTicketsPaid guiTicketsPaid = new GuiTicketsPaid(null, true);
            try {
                new ControllerGuiTicketsPaid(guiTicketsPaid, iDCurrentlySelectedProvider);
            } catch (NotBoundException | MalformedURLException | RemoteException ex) {
                Logger.getLogger(ControllerGuiNewProvider.class.getName()).log(Level.SEVERE, null, ex);
            }
            guiTicketsPaid.setVisible(true);
        }
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
            try {
                //cargo las categorias disponibles en la tabla correspondiente de la guiNewProvider
                GuiNewProvider guiNewProvider = new GuiNewProvider(null, true);
                ControllerGuiNewProvider controllerGuiNewProvider = new ControllerGuiNewProvider(guiNewProvider);
                controllerGuiNewProvider.setModify(false);
                controllerGuiNewProvider.loadFindCategoryTable();
                guiNewProvider.setVisible(true);
            } catch (RemoteException | NotBoundException | MalformedURLException ex) {
                Logger.getLogger(ControllerGuiCRUDProviders.class.getName()).log(Level.SEVERE, null, ex);
            }
            //hago una busqueda nueva de proveedores y lo cargo en la tabla, para actualizar posibles modificaciones
            String txtFindProvider = guiCRUDProviders.getTxtFindProvider().getText();
            int rowNewProvider = guiCRUDProviders.getTableProviderCategories().getSelectedRow();
            if (txtFindProvider != null || rowNewProvider != -1) {
                List<Map> providersList;
                try {
                    //hago la busqueda de proveedores en base a ese texto
                    providersList = providersSearch.searchProviders(txtFindProvider, (int) guiCRUDProviders.getTableProviderCategories().getModel().getValueAt(rowNewProvider, 0));
                    //cargo los proveedores en la tabla
                    updateSearchProviderTable(providersList);
                } catch (RemoteException ex) {
                    Logger.getLogger(ControllerGuiCRUDProviders.class.getName()).log(Level.SEVERE, null, ex);
                }
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
}
