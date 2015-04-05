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
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author eze
 */
public class ControllerGuiNewProvider implements ActionListener{

    private final InterfaceProvider provider;
    private final InterfaceProviderCategory providerCategory;
    private final GuiNewProvider guiNewProvider;
    
    public ControllerGuiNewProvider(GuiNewProvider guiNProv, InterfaceProvider prov, InterfaceProviderCategory provCategory) throws RemoteException {
        this.guiNewProvider = guiNProv;
        this.provider = prov;
        this.providerCategory = provCategory;
        //Escucho si se clickea alguna fila de la tabla FindProviderCategories
        this.guiNewProvider.getTableFindProviderCategories().addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                //Agrego la categoria clickeada en la tabla CategoriesProviders
                addCategorieToProvider();
            }
        });
    }

    public void loadCategoryList() throws RemoteException{
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
    
    
    private void addCategorieToProvider(){
        Object[] row = new Object[2];
        int i = this.guiNewProvider.getTableFindProviderCategories().getSelectedRow();
        row[0] = ((DefaultTableModel) this.guiNewProvider.getTableFindProviderCategories().getModel()).getValueAt(i,0);
        row[1] = ((DefaultTableModel) this.guiNewProvider.getTableFindProviderCategories().getModel()).getValueAt(i,1);
        ((DefaultTableModel) this.guiNewProvider.getTableCategoriesProviders().getModel()).addRow(row);
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        
    }
    
}
