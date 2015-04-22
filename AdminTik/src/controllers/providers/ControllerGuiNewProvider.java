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
import java.rmi.RemoteException;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

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
    private final GuiTicketsPaid guiInvoicesPaid;
    private final GuiPaymentsToProviders guiPaymentsToProviders;

    /**
     *
     * @param guiNProv
     * @param guiPTP
     * @param guiIP
     * @param prov
     * @param provCategory
     * @throws RemoteException
     */
    public ControllerGuiNewProvider(GuiNewProvider guiNProv, GuiPaymentsToProviders guiPTP, GuiTicketsPaid guiIP, InterfaceProvider prov, InterfaceProviderCategory provCategory) throws RemoteException {
        this.modify = false;
        this.guiPaymentsToProviders = guiPTP;
        this.guiInvoicesPaid = guiIP;
        this.guiNewProvider = guiNProv;
        this.provider = prov;
        this.providerCategory = provCategory;
        this.guiNewProvider.setActionListener(this);
        //Escucho si se clickea alguna fila de la tabla FindProviderCategories
        this.guiNewProvider.getTableFindProviderCategories().addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                //Agrego la categoria clickeada en la tabla CategoriesProviders
                addCategoryToProvider();
            }
        });
        //Escucho si se clickea alguna fila de la tabla de categorias del proveedor (Doble click sobre la categoria la elimina de la tabla)
        this.guiNewProvider.getTableCategoriesProviders().addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                if (evt.getClickCount() == 2) {
                    removeRowCategoryTable();
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

    private void removeRowCategoryTable() {
        int selectedRow = guiNewProvider.getTableCategoriesProviders().getSelectedRow();
        DefaultTableModel categoryModel = ((DefaultTableModel) guiNewProvider.getTableCategoriesProviders().getModel());
        System.out.println("selecRow: "+selectedRow);
        categoryModel.removeRow(selectedRow);
    }

    private void addCategoryToProvider() {
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
    }

    private void saveProviderCategories(int id) throws RemoteException {
        DefaultTableModel categoryModel = ((DefaultTableModel) guiNewProvider.getTableCategoriesProviders().getModel());
        int rowCount = categoryModel.getRowCount(), i = 0;
        Map<String, Object> providerMap = Collections.EMPTY_MAP;
        List<Map> categoriesFromProvider = provider.getCategoriesFromProvider(id);
        while (i < rowCount) {
            if (!categoriesFromProvider.contains(providerCategory.getProviderCategory((int) categoryModel.getValueAt(i, 0)))) {
                providerMap = provider.addCategoryToProvider(id, (int) categoryModel.getValueAt(i, 0));
            }
            i++;
        }
    }

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
            int rowCount = categoryModel.getRowCount(), i = 0;
            int providerId = (Integer.parseInt(providerMap.get("id").toString()));
            while (i < rowCount) {
                provider.addCategoryToProvider(providerId, (int) categoryModel.getValueAt(i, 0));
                i++;
            }
            //Retorno si se creó o no el proveedor
            return !providerMap.isEmpty();
        } else { //si no tiene nombre, no lo creo
            return false;
        }

    }

    public void loadGUIWithData(int id) throws RemoteException {
        Map<String, Object> p = provider.getProvider(id);
        this.guiNewProvider.getTxtProviderAddress().setText((String) p.get("address"));
        this.guiNewProvider.getTxtProviderCuit().setText((String) p.get("cuit"));
        this.guiNewProvider.getTxtProviderDescription().setText((String) p.get("description"));
        this.guiNewProvider.getTxtProviderName().setText((String) p.get("name"));
        this.guiNewProvider.getTxtProviderPhone().setText((String) p.get("phones"));
        setModify(true);
        loadCategoriesOfProvider(id);
        setCurrentProviderId(id);
    }

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
                
                try {
                    boolean result = provider.modify(getCurrentProviderId(), this.guiNewProvider.getTxtProviderName().getText(), this.guiNewProvider.getTxtProviderCuit().getText(), this.guiNewProvider.getTxtProviderAddress().getText(),
                            this.guiNewProvider.getTxtProviderDescription().getText(), this.guiNewProvider.getTxtProviderPhone().getText()) != null;
                    if (result) {
                        saveProviderCategories(getCurrentProviderId());
                        JOptionPane.showMessageDialog(this.guiNewProvider, "Proveedor modificado con éxito!", "", JOptionPane.INFORMATION_MESSAGE);
                    } else {
                        JOptionPane.showMessageDialog(this.guiNewProvider, "Debe ingresar nombre de Proveedor como requisito mínimo.", "Error!", JOptionPane.ERROR_MESSAGE);
                    }
                } catch (RemoteException ex) {
                    Logger.getLogger(ControllerGuiNewProvider.class.getName()).log(Level.SEVERE, null, ex);
                }
                setModify(false);
                this.guiNewProvider.hide();
            }
        }
        //Si preciono el boton CANCELAR
        if (e.getSource().equals(this.guiNewProvider.getBtnCancelProvider())) {
            this.guiNewProvider.hide();
            setModify(false);
        }
        //si presiono el boton LISTADO DE PAGOS REALIZADOS
        if(e.getSource().equals(this.guiNewProvider.getBtnPayments())){
            guiPaymentsToProviders.setVisible(true);
        }
        //si presiono el boton LISTADO DE FACTURACIÓN
        if(e.getSource().equals(this.guiNewProvider.getBtnInvoicesPaid())){
            guiInvoicesPaid.setVisible(true);
        }
    }

}
