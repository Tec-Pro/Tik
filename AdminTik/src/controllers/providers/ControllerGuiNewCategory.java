/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers.providers;

import gui.providers.GuiNewCategory;
import interfaces.providers.InterfaceProviderCategory;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.rmi.RemoteException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author eze
 */
public class ControllerGuiNewCategory implements ActionListener {

    private final GuiNewCategory guiNewCategory;
    private final InterfaceProviderCategory providerCategory;
    
    /**
     *
     * @param guiNCat
     * @param provCategory
     */
    public ControllerGuiNewCategory(GuiNewCategory guiNCat, InterfaceProviderCategory provCategory){
        this.guiNewCategory = guiNCat;
        this.providerCategory = provCategory;
        this.guiNewCategory.setActionListener(this);
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        //Si presiono el boton de guardar la nueva categoria
        if(e.getSource().equals(this.guiNewCategory.getBtnSaveCategory())){
            String categoryName = this.guiNewCategory.getTxtCategoryName().getText();
            if( categoryName != null && !"".equals(categoryName)){
                try {
                    providerCategory.create(categoryName);
                    JOptionPane.showMessageDialog(this.guiNewCategory, "Categoría creada con éxito!", "", JOptionPane.INFORMATION_MESSAGE);
                    guiNewCategory.cleanComponents();
                } catch (RemoteException ex) {
                    Logger.getLogger(ControllerGuiNewCategory.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            else{
                    JOptionPane.showMessageDialog(this.guiNewCategory, "Ingrese el nombre de la categoría", "Error!", JOptionPane.ERROR_MESSAGE);
            }
        }
        //Si presiono el boton de cancelar
        if(e.getSource().equals(this.guiNewCategory.getBtnCancelCategory())){
            this.guiNewCategory.hide();
        }
    }
    
}
