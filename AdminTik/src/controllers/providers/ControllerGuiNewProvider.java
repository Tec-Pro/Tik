/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers.providers;

import gui.providers.*;
import interfaces.providers.InterfaceProvider;
import interfaces.providers.InterfaceProviderCategory;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import utils.Config;

/**
 *
 * @author eze
 */
public class ControllerGuiNewProvider implements ActionListener {

    private final InterfaceProvider provider;
    private final InterfaceProviderCategory providerCategory;
    private final GuiNewProvider guiNewProvider;
    private boolean modify;
    private int currentProviderId;
    private final LinkedList categoriesToAdd, categoriesToRemove;

    /**
     * Constructor del controlador encargado de la interacción entre GUI New Provider y CRUD Provider.
     * @param guiNProv
     * @throws RemoteException
     * @throws java.rmi.NotBoundException
     * @throws java.net.MalformedURLException
     */
    public ControllerGuiNewProvider(GuiNewProvider guiNProv) throws RemoteException, NotBoundException, MalformedURLException {
        
        this.modify = false;
        this.guiNewProvider = guiNProv;
        
        //Busco los métodos de CRUD Provider y CRUD Provider Category en el server.
        this.provider = (InterfaceProvider) Naming.lookup("//" + Config.ip + "/crudProvider");
        this.providerCategory = (InterfaceProviderCategory) Naming.lookup("//" + Config.ip + "/crudProviderCategory");
        
        this.guiNewProvider.setActionListener(this);
        
        //Listas utilizadas en el caso de la modificación, representan las categorías para agregar y las categorías para eliminar.
        this.categoriesToAdd = new LinkedList();
        this.categoriesToRemove = new LinkedList();
        
        //Escucho si se clickea alguna fila de la tabla FindProviderCategories
        this.guiNewProvider.getTableFindProviderCategories().addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                //Agrego la categoria clickeada en la tabla CategoriesProviders
                addRowProviderCategoriesTable();
            }
        });
        //Escucho si se clickea alguna fila de la tabla de categorias del proveedor (Doble click sobre la categoria la elimina de la tabla)
        this.guiNewProvider.getTableCategoriesProviders().addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                //Si se hace doble click en la tabla de categorías del proveedor.
                if (evt.getClickCount() == 2) {
                    //Elimino la categoría de la tabla.
                    removeRowProviderCategoriesTable();
                }
            }
        });
    }

    /**
     * Función que carga la tabla de categorías.
     *
     * @throws RemoteException
     */
    public void loadFindCategoryTable() throws RemoteException {
        List<Map> categoryList = this.providerCategory.getProviderCategories();
        if (categoryList != null) {
            DefaultTableModel categoryModel = (DefaultTableModel) this.guiNewProvider.getTableFindProviderCategories().getModel();
            Iterator<Map> categoryItr = categoryList.iterator();
            while (categoryItr.hasNext()) {
                Map<String, Object> categoryMap = categoryItr.next();
                Object[] row = new Object[2];
                row[0] = categoryMap.get("id");
                row[1] = categoryMap.get("name");
                categoryModel.addRow(row);
            }
        }
    }

    /**
     * Método que quita una fila de la tabla de categorías del proveedor.
     * Invocada cuando se hace doble click sobre la categoría.
     */
    private void removeRowProviderCategoriesTable() {
        int selectedRow = guiNewProvider.getTableCategoriesProviders().getSelectedRow();
        DefaultTableModel categoryModel = ((DefaultTableModel) guiNewProvider.getTableCategoriesProviders().getModel());
        //Me fijo el id de la categoría seleccionada.
        int selectedCategoryId = (int) this.guiNewProvider.getTableCategoriesProviders().getModel().getValueAt(selectedRow, 0);
        //Si no está puesto ya en la lista de categorías a remover del proveedor, la agrego a la lista.
        if (!categoriesToRemove.contains(selectedCategoryId)) {
            categoriesToRemove.add(selectedCategoryId);
            //Si estaba por ser agregada, la saco de la lista de categorías a agregar.
            if (categoriesToAdd.contains(selectedCategoryId)) {
                categoriesToAdd.removeFirstOccurrence(selectedCategoryId);
            }
        }
        categoryModel.removeRow(selectedRow);
    }

    /**
     * Función que agrega una fila a la tabla de categorías de proveedor.
     * Esta función es invocada cuando se hace click sobre una de las categorías
     * existentes.
     */
    private void addRowProviderCategoriesTable() {
        boolean existsRow = false;
        DefaultTableModel categoryModel = ((DefaultTableModel) guiNewProvider.getTableCategoriesProviders().getModel());
        DefaultTableModel findProvCatModel = ((DefaultTableModel) guiNewProvider.getTableFindProviderCategories().getModel());
        int selectedRow = this.guiNewProvider.getTableFindProviderCategories().getSelectedRow();
        int i = 0, rowCount = categoryModel.getRowCount();
        //Reviso que la categoria no este ya cargada en la tabla CategoriesProviders
        while (i < rowCount && !existsRow) {
            //Si la categoria(id) ya esta en la tabla de CategoriesProviders
            if (findProvCatModel.getValueAt(selectedRow, 0).equals(categoryModel.getValueAt(i, 0))) {
                existsRow = true;
            }
            i++;
        }
        //Si no esta en la tabla, la cargo
        if (!existsRow) {
            Object[] row = new Object[2];
            row[0] = ((DefaultTableModel) this.guiNewProvider.getTableFindProviderCategories().getModel()).getValueAt(selectedRow, 0);
            row[1] = ((DefaultTableModel) this.guiNewProvider.getTableFindProviderCategories().getModel()).getValueAt(selectedRow, 1);
            categoryModel.addRow(row);
        }
        //Me fijo cual es el id de la categoría seleccionada
        int selectedCategoryId = (int) this.guiNewProvider.getTableFindProviderCategories().getModel().getValueAt(selectedRow, 0);
        //Si ese id no esta ya para agregar, lo agrego a la lista, si está para borrar, lo saco de esa lista.
        if (!categoriesToAdd.contains(selectedCategoryId)) {
            categoriesToAdd.add(selectedCategoryId);
            if (categoriesToRemove.contains(selectedCategoryId)) {
                categoriesToRemove.removeFirstOccurrence(selectedCategoryId);
            }
        }
    }

    /**
     * Función que guarda las categorías de un proveedor.
     * @param id el id del proveedor.
     * @return Map conteniendo al proveedor.
     * @throws RemoteException 
     */
    private Map<String, Object> saveProviderCategories(int id) throws RemoteException {
        Map<String, Object> result;
        result = provider.saveCategoriesOfProvider(id, categoriesToAdd, categoriesToRemove);
        //Limpio las listas de categorías.
        categoriesToAdd.clear();
        categoriesToRemove.clear();
        return result;
    }

    /**
     * Función que guarda el proveedor, ya sea uno creado o modificado.
     * @return True si el proveedor fue creado exitosamente.
     * @throws RemoteException 
     */
    private boolean saveProvider() throws RemoteException {
        String name = this.guiNewProvider.getTxtProviderName().getText();
        String address = this.guiNewProvider.getTxtProviderAddress().getText();
        String cuit = this.guiNewProvider.getTxtProviderCuit().getText();
        String phone = this.guiNewProvider.getTxtProviderPhone().getText();
        String description = this.guiNewProvider.getTxtProviderDescription().getText();
        DefaultTableModel categoryModel = ((DefaultTableModel) guiNewProvider.getTableCategoriesProviders().getModel());
        //requisito minimo para crear el proveedor, que tenga nombre
        if (!"".equals(name)) {
            //doy de alta el proveedor en la base de datos
            Map<String, Object> providerMap = this.provider.create(name, cuit, address, description, phone);
            //agrego las categorias a las cuales pertenece dicho proveedor
            int providerId = (Integer.parseInt(providerMap.get("id").toString()));
            providerMap = saveProviderCategories(providerId);
            categoriesToAdd.clear();
            categoriesToRemove.clear();
            //Retorno si se creó o no el proveedor
            return !providerMap.isEmpty();
        } else { //si no tiene nombre, no lo creo
            return false;
        }

    }
    /**
     * Función que carga la GUINewProvider con los datos del proveedor elegido.
     * @param id el id del proveedor del que se cargaran los datos en la GUI.
     * @throws RemoteException 
     */
    public void loadGUIWithData(int id) throws RemoteException {
        Map<String, Object> p = provider.getProvider(id);
        this.guiNewProvider.getTxtProviderAddress().setText((String) p.get("address"));
        this.guiNewProvider.getTxtProviderCuit().setText((String) p.get("cuit"));
        this.guiNewProvider.getTxtProviderDescription().setText((String) p.get("description"));
        this.guiNewProvider.getTxtProviderName().setText((String) p.get("name"));
        this.guiNewProvider.getTxtProviderPhone().setText((String) p.get("phones"));
        //Seteo que va a ser una modificación, ya que es el único caso en que se carga la GUI con datos.
        setModify(true);
        loadCategoriesOfProvider(id);
        setCurrentProviderId(id);
    }

    /**
     * Función que carga las categorías de un proveedor en la tabla de categorías.
     * @param id Id del proveedor del cual se cargaran las categorías.
     * @throws RemoteException 
     */
    private void loadCategoriesOfProvider(int id) throws RemoteException {
        Object[] o = new Object[2];
        DefaultTableModel categoriesTableModel = (DefaultTableModel) this.guiNewProvider.getTableCategoriesProviders().getModel();
        categoriesTableModel.setRowCount(0);
        for (Map<String, Object> m : provider.getCategoriesFromProvider(id)) {
            o[0] = m.get("id");
            o[1] = m.get("name");
            categoriesTableModel.addRow(o);
        }

    }

    /**
     * @return the modify
     */
    public boolean isModify() {
        return modify;
    }

    /**
     * @return the currentProviderId
     */
    public int getCurrentProviderId() {
        return currentProviderId;
    }

    /**
     * @param modify the modify to set
     */
    public void setModify(boolean modify) {
        this.modify = modify;
    }

    /**
     * @param currentProviderId the currentProviderId to set
     */
    public void setCurrentProviderId(int currentProviderId) {
        this.currentProviderId = currentProviderId;
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        //Si presiono el boton GUARDAR
        if (e.getSource().equals(this.guiNewProvider.getBtnSaveProvider())) {
            //Si no es modificación, es decir es un nuevo proveedor.
            if (!isModify()) {
                //Guardo el proveedor en la base de datos
                boolean result = false;//por defecto el proveedor no se creó aún
                try {
                    result = saveProvider();
                } catch (RemoteException ex) {
                    Logger.getLogger(ControllerGuiNewProvider.class.getName()).log(Level.SEVERE, null, ex);
                }
                if (result) {//si se guardo el proveedor
                    JOptionPane.showMessageDialog(this.guiNewProvider, "Proveedor almacenado con éxito!", "", JOptionPane.INFORMATION_MESSAGE);
                    this.guiNewProvider.cleanComponents();
                    try {
                        loadFindCategoryTable();
                    } catch (RemoteException ex) {
                        Logger.getLogger(ControllerGuiNewProvider.class.getName()).log(Level.SEVERE, null, ex);
                    }
                } else {//sino
                    JOptionPane.showMessageDialog(this.guiNewProvider, "Debe ingresar nombre de Proveedor como requisito mínimo.", "Error!", JOptionPane.ERROR_MESSAGE);
                }
            } else {
                //Si se hace doble click sobre el proveedor en la GUI, se habilita para modificar.
                try {
                    //Almaceno el resultado de la modificación, si el proveedor es distinto de null.
                    boolean result = provider.modify(getCurrentProviderId(), this.guiNewProvider.getTxtProviderName().getText(), this.guiNewProvider.getTxtProviderCuit().getText(), this.guiNewProvider.getTxtProviderAddress().getText(),
                            this.guiNewProvider.getTxtProviderDescription().getText(), this.guiNewProvider.getTxtProviderPhone().getText()) != null;
                    if (result) {
                        //Si el proveedor no es null, guardo sus categorías.
                        saveProviderCategories(getCurrentProviderId());
                        JOptionPane.showMessageDialog(this.guiNewProvider, "Proveedor modificado con éxito!", "", JOptionPane.INFORMATION_MESSAGE);
                    } else {
                        //Sino error.
                        JOptionPane.showMessageDialog(this.guiNewProvider, "Debe ingresar nombre de Proveedor como requisito mínimo.", "Error!", JOptionPane.ERROR_MESSAGE);
                    }
                } catch (RemoteException ex) {
                    Logger.getLogger(ControllerGuiNewProvider.class.getName()).log(Level.SEVERE, null, ex);
                }
                //Una vez realizada la modificación, se resetea el valor.
                setModify(false);
                this.guiNewProvider.setVisible(false);
            }
        }
        //Si presiono el boton CANCELAR
        if (e.getSource().equals(this.guiNewProvider.getBtnCancelProvider())) {
            this.guiNewProvider.setVisible(false);
            setModify(false); //Reseteo el valor.
        }

    }

}
