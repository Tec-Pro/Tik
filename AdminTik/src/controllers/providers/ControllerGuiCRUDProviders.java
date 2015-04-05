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
import java.util.logging.Level;
import java.util.logging.Logger;

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
    
    public ControllerGuiCRUDProviders(GuiCRUDProviders guiCProv, GuiNewCategory guiNCat, GuiNewProvider guiNProv,
    InterfaceProvider prov, InterfaceProviderCategory provCategory) throws RemoteException{
        this.guiCRUDProviders = guiCProv;
        this.guiNewCategory = guiNCat;
        this.guiNewProvider = guiNProv;
        this.provider = prov;
        this.providerCategory = provCategory;
        this.controllerGuiNewCategory = new ControllerGuiNewCategory(this.guiNewCategory,this.providerCategory);
        this.controllerGuiNewProvider = new ControllerGuiNewProvider(this.guiNewProvider,this.provider, this.providerCategory);
        this.guiCRUDProviders.setActionListener(this);
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
            //this.guiNewProvider.cleanComponents();
            try {
                //cargo las categorias disponibles en la tabla correspondiente de la guiNewProvider
                this.controllerGuiNewProvider.loadCategoryList();
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
