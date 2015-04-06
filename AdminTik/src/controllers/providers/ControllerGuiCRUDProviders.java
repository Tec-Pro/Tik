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
public class ControllerGuiCRUDProviders implements ActionListener {

    private final GuiCRUDProviders guiCRUDProviders;
    private final GuiNewCategory guiNewCategory;
    private final GuiNewProvider guiNewProvider;
    private final ControllerGuiNewCategory controllerGuiNewCategory;
    private final ControllerGuiNewProvider controllerGuiNewProvider;
    private final InterfaceProviderCategory providerCategory;
    private final InterfaceProvider provider;
    private final InterfaceProvidersSearch providersSearch;
    
    public ControllerGuiCRUDProviders(GuiCRUDProviders guiCProv, GuiNewCategory guiNCat, GuiNewProvider guiNProv,
    InterfaceProvider prov, InterfaceProviderCategory provCategory, InterfaceProvidersSearch provSearch) throws RemoteException{
        this.guiCRUDProviders = guiCProv;
        this.guiNewCategory = guiNCat;
        this.guiNewProvider = guiNProv;
        this.provider = prov;
        this.providerCategory = provCategory;
        this.providersSearch = provSearch;
        this.controllerGuiNewCategory = new ControllerGuiNewCategory(this.guiNewCategory,this.providerCategory);
        this.controllerGuiNewProvider = new ControllerGuiNewProvider(this.guiNewProvider,this.provider, this.providerCategory);
        this.guiCRUDProviders.setActionListener(this);
        //escucho en el txtFindProvider lo que se va ingresando para buscar un proveedor
        this.guiCRUDProviders.getTxtFindProvider().addKeyListener(new java.awt.event.KeyAdapter() {
            @Override
            public void keyReleased(java.awt.event.KeyEvent evt) {
                try {
                    updateSearchProviderTable();
                } catch (RemoteException ex) {
                    Logger.getLogger(ControllerGuiCRUDProviders.class.getName()).log(Level.SEVERE, null, ex);
                }

            }

        });
    }
    
    private void updateSearchProviderTable() throws RemoteException{
        String txtFindProvider = this.guiCRUDProviders.getTxtFindProvider().getText();
        if (txtFindProvider != null && !"".equals(txtFindProvider)) {
            List<Map> providersList = this.providersSearch.searchProviders(txtFindProvider);
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
                        while(categoriesItr.hasNext()){
                            Map<String,Object> categoryMap = categoriesItr.next();
                            categories += categoryMap.get("name")+" ";
                        }
                        o[6] = (categories);
                        providerModel.addRow(o);
                    }
                }
            }
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        //Si presiono el boton de agregar una nueva categoria
        if(e.getSource().equals(this.guiCRUDProviders.getBtnNewCategory())){
            this.guiNewCategory.cleanComponents();
            this.guiNewCategory.setVisible(true);
        }
        //Si presiono el boton de agregar un nuevo proveedor
        if(e.getSource().equals(this.guiCRUDProviders.getBtnNewProvider())){
            this.guiNewProvider.cleanComponents();
            try {
                //cargo las categorias disponibles en la tabla correspondiente de la guiNewProvider
                this.controllerGuiNewProvider.loadFindCategoryTable();
            } catch (RemoteException ex) {
                Logger.getLogger(ControllerGuiCRUDProviders.class.getName()).log(Level.SEVERE, null, ex);
            }
            this.guiNewProvider.setVisible(true);
        }
        /*Si presiono el boton de eliminar un proveedor(el cual debera ser seleccionado previamente
        * de la tabala de proveedores.
        */
        if(e.getSource().equals(this.guiCRUDProviders.getBtnRemoveProvider())){
            
        }
    }
    
}
