/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers.providers;

import controllers.ControllerMain;
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
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import utils.Config;
import utils.InterfaceName;

/**
 *
 * @author eze Controlador de la interfaz de proveedores.
 */
public class ControllerGuiCRUDProviders implements ActionListener {

    private final GuiCRUDProviders guiCRUDProviders;
    private final InterfaceProviderCategory providerCategory;
    private final InterfaceProvider provider;
    private final InterfaceProvidersSearch providersSearch;
    private static Integer iDCurrentlySelectedProvider;

    /**
     * Constructor del controlador encargado de la interacción entre la GUI CRUD
     * Providers y CRUD Providers.
     *
     * @param guiCProv GUI de CRUD Providers
     * @throws RemoteException
     * @throws NotBoundException
     * @throws MalformedURLException
     */
    public ControllerGuiCRUDProviders(GuiCRUDProviders guiCProv) throws RemoteException, NotBoundException, MalformedURLException {
        //Busco las clases en el server.
        this.provider = (InterfaceProvider) InterfaceName.registry.lookup(InterfaceName.CRUDProvider);
        this.providersSearch = (InterfaceProvidersSearch) InterfaceName.registry.lookup(InterfaceName.providersSearch);
        this.providerCategory = (InterfaceProviderCategory) InterfaceName.registry.lookup(InterfaceName.CRUDProviderCategory);

        this.guiCRUDProviders = guiCProv;
        iDCurrentlySelectedProvider = -1;
        if(ControllerMain.isAdmin())
            this.guiCRUDProviders.setActionListener(this);

        //Cargo la tabla de categorías.
        loadProviderCategories();

        //escucho en el txtFindProvider lo que se va ingresando para buscar un proveedor
        this.guiCRUDProviders.getTxtFindProvider().addKeyListener(new java.awt.event.KeyAdapter() {
            @Override
            public void keyReleased(java.awt.event.KeyEvent evt) {
                //saco el texto ingresado en txtFindProvider
                String txtFindProvider = guiCRUDProviders.getTxtFindProvider().getText();
                List<Map> providersList;
                //Obtengo la fila seleccionada. Si hay una fila seleccionada, la sumo al filtro de búsqueda.
                int row = guiCRUDProviders.getTableProviderCategories().getSelectedRow();
                if (row != -1) {
                    try {
                        providersList = providersSearch.searchProviders(txtFindProvider, (int) guiCRUDProviders.getTableProviderCategories().getModel().getValueAt(row, 0));
                        //Actualizo la tabla de proveedores con la lista resultante de la búsqueda.
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
        });
        //Si hace doble click en una fila de la tabla de proveedores, abre la edicion de los datos del mismo
        this.guiCRUDProviders.getTableProviders().addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                if (evt.getClickCount() == 2) {
                    JTable target = (JTable) evt.getSource();
                    int row = target.getSelectedRow();
                    if (row != -1) {
                        try {
                            //Creo el nuevo JDialog.
                            GuiNewProvider guiNewProvider = new GuiNewProvider(ControllerMain.guiMain, true);
                            //Creo el controlador.
                            ControllerGuiNewProvider controllerGuiNewProvider = new ControllerGuiNewProvider(guiNewProvider);
                            //Cargo las categorías en la nueva GUI.
                            controllerGuiNewProvider.loadFindCategoryTable();
                            //Cargo los datos del proveedor seleccionado.
                            controllerGuiNewProvider.loadGUIWithData((int) target.getValueAt(row, 0));
                            //Hago visible el Dialog.
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
            }

        });

        //reviso si se clickea alguna fila de la tabla categorias
        this.guiCRUDProviders.getTableProviderCategories().addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                //Si hago doble click en la tabla de categorías muestro un diálogo de edición.
                if (evt.getClickCount() == 2) {
                    JTable target = (JTable) evt.getSource();
                    int row = target.getSelectedRow();
                    if (row != -1) {
                        String categoryName = JOptionPane.showInputDialog(guiCRUDProviders, "Ingrese el nuevo nombre de la categoría.", "Modificar categoría", JOptionPane.PLAIN_MESSAGE);
                        //La categoría debe tener un nombre.
                        if (categoryName != null) {
                            //Ese nombre no pueden ser espacios en blanco.
                            if (categoryName.trim().isEmpty()) {
                                JOptionPane.showMessageDialog(guiCRUDProviders, "Error: Falta especificar un nombre.", "Error", JOptionPane.ERROR_MESSAGE);
                            } else {
                                try {
                                //Modifico la categoría correspondiente 
                                    //Cargo nuevamente la tabla de categorías.
                                    providerCategory.modify((int) target.getValueAt(row, 0), categoryName);
                                    loadProviderCategories();
                                } catch (RemoteException ex) {
                                    Logger.getLogger(ControllerGuiCRUDProviders.class.getName()).log(Level.SEVERE, null, ex);
                                }
                            }
                        }
                    }
                }
            }
        });
        guiCRUDProviders.getTableProviders().getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            //Si cambia el valor de selección de la tabla de proveedores.
            @Override
            public void valueChanged(ListSelectionEvent e) {
                int row = guiCRUDProviders.getTableProviders().getSelectedRow();
                //Si no hay una fila seleccionada, inhabilito los botones y pongo como ID corriente -1.
                if (row == -1) {
                    guiCRUDProviders.getBtnRemoveProvider().setEnabled(false);
                    guiCRUDProviders.getBtnPayments().setEnabled(false);
                    guiCRUDProviders.getBtnTicketsPaid().setEnabled(false);
                    iDCurrentlySelectedProvider = -1;
                    //Si hay una fila seleccionada, habilito los botones y pongo la ID corriente que corresponde.
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
                //Obtengo el valor de la fila seleccionada.
                int row = guiCRUDProviders.getTableProviderCategories().getSelectedRow();
                //Si hay una fila seleccionada, habilito los botones correspondientes.
                if (row != -1) {
                    iDCurrentlySelectedProvider = -1;
                    guiCRUDProviders.getBtnRemoveCategory().setEnabled(true);
                    guiCRUDProviders.getBtnTicketsPaid().setEnabled(false);
                    guiCRUDProviders.getBtnPayments().setEnabled(false);
                    try {
                        //Actualizo la tabla de proveedores usando la categoría en el filtro de búsqueda.
                        updateSearchProviderTable(providersSearch.searchProviders(guiCRUDProviders.getTxtFindProvider().getText(), (int) guiCRUDProviders.getTableProviderCategories().getModel().getValueAt(row, 0)));
                    } catch (RemoteException ex) {
                        Logger.getLogger(ControllerGuiCRUDProviders.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    //Si no hay una fila seleccionada.
                } else {
                    //Inhabilito el botón para eliminar categorías.
                    guiCRUDProviders.getBtnRemoveCategory().setEnabled(false);
                    try {
                        //Actualizo la búsqueda sacando la categoría del filtro.
                        updateSearchProviderTable(providersSearch.searchProviders(guiCRUDProviders.getTxtFindProvider().getText()));
                    } catch (RemoteException ex) {
                        Logger.getLogger(ControllerGuiCRUDProviders.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        });
    }

    /**
     * Función que carga las categorías de proveedor en la tabla.
     *
     * @throws RemoteException
     */
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

    /**
     * Función que carga la tabla de proveedores.
     *
     * @throws RemoteException
     */
    public void loadProviderTable() throws RemoteException {
        List<Map> providers = provider.getProviders();
        DefaultTableModel providerModel = ((DefaultTableModel) this.guiCRUDProviders.getTableProviders().getModel());
        providerModel.setRowCount(0);//limpio la tabla antes de cargarla nuevamente

        Object[] o = new Object[8];
        for (Map p : providers) {
            o[0] = (p.get("id"));
            o[1] = (p.get("name"));
            o[2] = (p.get("cuit"));
            o[3] = (p.get("address"));
            o[4] = (p.get("description"));
            o[5] = (p.get("phones"));
            //busco las categorias de cada proveedor y las cargo en la tabla
            List<Map> categoriesList = this.provider.getCategoriesFromProvider(Integer.parseInt(p.get("id").toString()));
            Iterator<Map> categoriesItr = categoriesList.iterator();
            String categories = "";
            while (categoriesItr.hasNext()) {
                Map<String, Object> categoryMap = categoriesItr.next();
                categories += categoryMap.get("name") + " ";
            }
            o[6] = (categories);
            o[7] = p.get("current_account");
            providerModel.addRow(o);
        }

    }

    /**
     * Función que carga la tabla de proveedores de acuerdo a una lista pasada
     * como parámetro.
     *
     * @param providersList lista de proveedores que quieran cargarse.
     * @throws RemoteException
     */
    private void updateSearchProviderTable(List<Map> providersList) throws RemoteException {
        if (providersList != null) {
            //si hay proveedores los cargo en la gui
            DefaultTableModel providerModel = ((DefaultTableModel) this.guiCRUDProviders.getTableProviders().getModel());
            providerModel.setRowCount(0);//limpio la tabla antes de cargarla nuevamente
            Map<String, Object> providerMap;
            Object[] o = new Object[8];
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
                o[7] = providerMap.get("current_account");
                providerModel.addRow(o);
            }
        }

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        //si presiono el boton LISTADO DE PAGOS REALIZADOS
        if (e.getSource().equals(this.guiCRUDProviders.getBtnPayments())) {
            //Creo el JDialog, creo el controlador y seteo el JDialog visible.
            GuiPaymentsToProviders guiPaymentsToProviders = new GuiPaymentsToProviders(ControllerMain.guiMain, true);
            try {
                new ControllerGuiPaymentsToProviders(guiPaymentsToProviders, iDCurrentlySelectedProvider);
            } catch (RemoteException ex) {
                Logger.getLogger(ControllerGuiCRUDProviders.class.getName()).log(Level.SEVERE, null, ex);
            } catch (NotBoundException ex) {
                Logger.getLogger(ControllerGuiCRUDProviders.class.getName()).log(Level.SEVERE, null, ex);
            }
            guiPaymentsToProviders.setVisible(true);
        }
        //si presiono el boton LISTADO DE FACTURACIÓN
        if (e.getSource().equals(this.guiCRUDProviders.getBtnTicketsPaid())) {
            //Creo el JDialog, creo el controlador y seteo el JDialog visible.
            GuiTicketsPaid guiTicketsPaid = new GuiTicketsPaid(ControllerMain.guiMain, true);
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
                        //Si el nombre no es vacío y no contiene solo espacios en blanco, creo la categoría.
                        providerCategory.create(categoryName);
                        //Actualizo la tabla de categorías.
                        loadProviderCategories();
                    } catch (RemoteException ex) {
                        Logger.getLogger(ControllerGuiCRUDProviders.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        }
        //Si presiono el botón ELIMINAR CATEGORÍA
        if (e.getSource().equals(this.guiCRUDProviders.getBtnRemoveCategory())) {
            Integer resp = JOptionPane.showConfirmDialog(guiCRUDProviders, "¿Desea borrar la categoría seleccionada?", "Confirmar borrado", JOptionPane.YES_NO_OPTION);
            if (resp == JOptionPane.YES_OPTION) {
                int row = guiCRUDProviders.getTableProviderCategories().getSelectedRow();
                try {
                    //Si la eliminación se realizo de manera correcta, actualizo la lista de categorías.
                    if (providerCategory.delete((int) guiCRUDProviders.getDefaultTableProviderCategories().getValueAt(row, 0))) {
                        JOptionPane.showMessageDialog(guiCRUDProviders, "Categoría eliminada correctamente", "Operacion exitosa", JOptionPane.INFORMATION_MESSAGE);
                        loadProviderCategories();
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
                //Cargo las categorias disponibles en la tabla correspondiente de la guiNewProvider
                //Creo la GUI, el controlador, seteo el modify en falso y pongo la GUI visible.
                GuiNewProvider guiNewProvider = new GuiNewProvider(ControllerMain.guiMain, true);
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
                    if (rowNewProvider != -1) {
                        providersList = providersSearch.searchProviders(txtFindProvider, (int) guiCRUDProviders.getTableProviderCategories().getModel().getValueAt(rowNewProvider, 0));
                    } else {
                        providersList = providersSearch.searchProviders(txtFindProvider);
                    }
                    //cargo los proveedores en la tabla
                    updateSearchProviderTable(providersList);
                } catch (RemoteException ex) {
                    Logger.getLogger(ControllerGuiCRUDProviders.class.getName()).log(Level.SEVERE, null, ex);
                }
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

    }
}
