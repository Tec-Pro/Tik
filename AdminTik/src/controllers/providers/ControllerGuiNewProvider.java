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
public class ControllerGuiNewProvider implements ActionListener{

    private final InterfaceProvider provider;
    private final InterfaceProviderCategory providerCategory;
    private final GuiNewProvider guiNewProvider;
    
    /**
     *
     * @param guiNProv
     * @param prov
     * @param provCategory
     * @throws RemoteException
     */
    public ControllerGuiNewProvider(GuiNewProvider guiNProv, InterfaceProvider prov, InterfaceProviderCategory provCategory) throws RemoteException {
        this.guiNewProvider = guiNProv;
        this.provider = prov;
        this.providerCategory = provCategory;
        this.guiNewProvider.setActionListener(this);
        //Escucho si se clickea alguna fila de la tabla FindProviderCategories
        this.guiNewProvider.getTableFindProviderCategories().addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                //Agrego la categoria clickeada en la tabla CategoriesProviders
                addCategorieToProvider();
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
     *
     * @throws RemoteException
     */
    public void loadFindCategoryTable() throws RemoteException{
        List<Map> categoryList = this.providerCategory.getProviderCategories();
        if(categoryList != null){
            DefaultTableModel categoryModel = (DefaultTableModel) this.guiNewProvider.getTableFindProviderCategories().getModel();
            Iterator<Map> categoryItr = categoryList.iterator();
            while (categoryItr.hasNext()){
                Map<String,Object> categoryMap = categoryItr.next();
                Object[] row = new Object[2];
                row[0] = categoryMap.get("id");
                row[1] = categoryMap.get("name");
                categoryModel.addRow(row);
            }
        }
    }
    
    private void removeRowCategoryTable(){
        int selectedRow = guiNewProvider.getTableCategoriesProviders().getSelectedRow();
        DefaultTableModel categoryModel = ((DefaultTableModel) guiNewProvider.getTableCategoriesProviders().getModel());
        categoryModel.removeRow(selectedRow);
    }
    
    private void addCategorieToProvider(){
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
        row[0] = ((DefaultTableModel) this.guiNewProvider.getTableFindProviderCategories().getModel()).getValueAt(selectedRow,0);
        row[1] = ((DefaultTableModel) this.guiNewProvider.getTableFindProviderCategories().getModel()).getValueAt(selectedRow,1);
        categoryModel.addRow(row);
        }
    }
    
    private boolean saveProvider() throws RemoteException{
        String name = this.guiNewProvider.getTxtProviderName().getText();
        String address = this.guiNewProvider.getTxtProviderAddress().getText();
        String cuit = this.guiNewProvider.getTxtProviderCuit().getText();
        String phone = this.guiNewProvider.getTxtProviderPhone().getText();
        String description = this.guiNewProvider.getTxtProviderDescription().getText();
        DefaultTableModel categoryModel = ((DefaultTableModel) guiNewProvider.getTableCategoriesProviders().getModel());
        //requisito minimo para crear el proveedor, que tenga nombre
        if(!"".equals(name)){
            //doy de alta el proveedor en la base de datos
            Map<String, Object> providerMap = this.provider.create(name, cuit, address, description, phone);
            //agrego las categorias a las cuales pertenece dicho proveedor
            int rowCount = categoryModel.getRowCount(), i = 0;
            int providerId = (Integer.parseInt(providerMap.get("id").toString()));
            while(i < rowCount){
                provider.addCategoryToProvider(providerId, (int) categoryModel.getValueAt(i, 0));
                i++;
            }
            //Retorno si se creó o no el proveedor
            return providerMap != null && !providerMap.isEmpty() ;
        }else{ //si no tiene nombre, no lo creo
            return false;
        }
    
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        //Si presiono el boton GUARDAR
        if(e.getSource().equals(this.guiNewProvider.getBtnSaveProvider())){
            //Guardo el proveedor en la base de datos
            boolean result = false;//por defecto el proveedor no se creó aún
            try {
                result = saveProvider();
            } catch (RemoteException ex) {
                Logger.getLogger(ControllerGuiNewProvider.class.getName()).log(Level.SEVERE, null, ex);
            }
            if(result){//si se guardo el proveedor
                JOptionPane.showMessageDialog(this.guiNewProvider, "Proveedor almacenado con éxito!", "", JOptionPane.INFORMATION_MESSAGE);
                this.guiNewProvider.cleanComponents();
                try {
                    loadFindCategoryTable();
                } catch (RemoteException ex) {
                    Logger.getLogger(ControllerGuiNewProvider.class.getName()).log(Level.SEVERE, null, ex);
                }
            }else{//sino
                JOptionPane.showMessageDialog(this.guiNewProvider, "Debe ingresar nombre de Proveedor como requisito mínimo.", "Error!", JOptionPane.ERROR_MESSAGE);
            }
        }
        //Si preciono el boton CANCELAR
        if(e.getSource().equals(this.guiNewProvider.getBtnCancelProvider())){
            this.guiNewProvider.hide();
        }
    }
    
}