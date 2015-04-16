/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import controllers.providers.ControllerGuiCRUDProviders;
import controllers.providers.ControllerGuiNewProvider;
import gui.GuiAdminLogin;
import gui.GuiCRUDAdmin;
import gui.GuiCRUDEProduct;
import gui.GuiCRUDFProduct;
import gui.GuiCRUDPProduct;
import gui.GuiCRUDProductCategory;
import gui.GuiCRUDUser;
import gui.GuiJTree;
import gui.GuiLoadPurchase;
import gui.main.GuiMain;
import gui.providers.GuiCRUDProviders;
import gui.providers.GuiNewProvider;
import interfaces.providers.InterfaceProvider;
import interfaces.providers.InterfaceProviderCategory;
import interfaces.providers.InterfaceProvidersSearch;
import java.awt.Cursor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyVetoException;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import utils.Config;

/**
 *
 * @author nico
 */
public class ControllerMain implements ActionListener {

    private static GuiMain guiMain; //interfaz principal con el desktpp
    private Map<String, Object> userLogged; //usuario logeado

    //guis
    private static GuiAdminLogin guiAdminLogin; //para poder cerrar sesión
    private static GuiCRUDAdmin guiCRUDAdmin; //gui del crud de admin
    private static GuiCRUDEProduct guiCRUDEProduct; //gui productos elaborados
    private static GuiCRUDPProduct guiCRUDPProduct; //gui productos primarios
    private static GuiCRUDFProduct guiCRUDFProduct; //gui productos finales
    private static GuiCRUDProductCategory guiCRUDProductCategory; //gui categoria productos
    private static GuiCRUDProviders guiCRUDProviders; 
    private static GuiCRUDUser guiCRUDUser; //gui usuarios
    private static GuiNewProvider guiNewProvider;
    private static GuiLoadPurchase guiLoadPurchase;
    private static GuiJTree guiJTree;

    //controladores
    private static ControllerGuiCRUDAdmin controllerCRUDAdmin; //controlador de la gui para admin
    private ControllerGuiCRUDEproduct controllerCRUDEProduct; //controlador productos elaborados
    private ControllerGuiCRUDPproduct controllerCRUDPProduct; //controlador productos primarios
    private ControllerGuiCRUDFproduct controllerCRUDFProduct; //controlador productos finales
    private ControllerGuiProductCategory controllerCRUDProductCategory; //controlador categorias de productos
    private ControllerGuiCRUDProviders controllerCRUDProviders;
    private ControllerGuiCRUDUser controllerCRUDUser;
    private ControllerGuiJTree controllerGuiJTree;
    
    public ControllerMain(GuiAdminLogin guiAdminLogin) throws NotBoundException, MalformedURLException, RemoteException {
        this.guiAdminLogin = guiAdminLogin; //hago esto, así si cierra sesión pongo en visible la ventana
        guiMain = new GuiMain();
        guiMain.setExtendedState(JFrame.MAXIMIZED_BOTH);
        guiMain.setActionListener(this);

    }

    public void initSession(Map<String, Object> userLogged) throws NotBoundException, MalformedURLException, RemoteException {
        this.userLogged = userLogged;
        guiMain.setVisible(true); // hago visible la ventana pero seteo el puntero para que carge todo
        guiMain.setCursor(Cursor.WAIT_CURSOR); //cambio el cursor por si se inicia sesión antes de cargar las cosas
        /*aca va todo, entre el seteo del puntero, para que aparezca que esta cargando todo
         */
        //creo las gui
        guiCRUDAdmin = new GuiCRUDAdmin();
        guiCRUDEProduct = new GuiCRUDEProduct();
        guiCRUDFProduct = new GuiCRUDFProduct();
        guiCRUDPProduct = new GuiCRUDPProduct();
        guiCRUDProductCategory = new GuiCRUDProductCategory();
        guiCRUDProviders = new GuiCRUDProviders();
        guiCRUDUser = new GuiCRUDUser();
        guiNewProvider = new GuiNewProvider();
        guiLoadPurchase = new GuiLoadPurchase();
        guiJTree = new GuiJTree();
        
        //agrego las gui al desktop
        guiMain.getDesktop().add(guiCRUDAdmin);
        guiMain.getDesktop().add(guiCRUDEProduct);
        guiMain.getDesktop().add(guiCRUDFProduct);
        guiMain.getDesktop().add(guiCRUDPProduct);
        guiMain.getDesktop().add(guiCRUDProductCategory);
        guiMain.getDesktop().add(guiCRUDProviders);
        guiMain.getDesktop().add(guiCRUDUser);        
        guiMain.getDesktop().add(guiNewProvider);
        guiMain.getDesktop().add(guiLoadPurchase);
        guiMain.getDesktop().add(guiJTree);
        
        InterfaceProvider provider = (InterfaceProvider) Naming.lookup("//" + Config.ip + "/crudProvider");
        InterfaceProviderCategory providerCategory = (InterfaceProviderCategory ) Naming.lookup("//" + Config.ip + "/crudProviderCategory");
        InterfaceProvidersSearch providersSearch = (InterfaceProvidersSearch) Naming.lookup("//" + Config.ip + "/providersSearch");

        
        //creo los controladores 
        controllerCRUDAdmin = new ControllerGuiCRUDAdmin(userLogged, guiCRUDAdmin);
        controllerCRUDEProduct = new ControllerGuiCRUDEproduct(guiCRUDEProduct);
        controllerCRUDFProduct = new ControllerGuiCRUDFproduct(guiCRUDFProduct);
        controllerCRUDProductCategory = new ControllerGuiProductCategory(guiCRUDProductCategory);
        controllerCRUDProviders = new ControllerGuiCRUDProviders(guiCRUDProviders, guiNewProvider, provider, providerCategory, providersSearch);
        controllerCRUDUser = new ControllerGuiCRUDUser(guiCRUDUser);
        controllerCRUDPProduct = new ControllerGuiCRUDPproduct(guiCRUDPProduct,guiLoadPurchase);
        controllerGuiJTree = new ControllerGuiJTree(guiJTree);
        //restauro el puntero asi ya se que termino de cargar todo
        guiMain.setCursor(Cursor.DEFAULT_CURSOR);

    }

    public static void closeSession() {
        guiMain.setVisible(false); //finalizo la ventana entera
        guiAdminLogin.setVisible(true);//hago visible la del login
        //debo finaliar todas las INTERNALFRAME!
        //para evitar que quede abierta y el otro admin vea que hizo
        guiAdminLogin.clearFields();
        ControllerGuiAdminLogin.getAllAdmins();
        guiCRUDAdmin.dispose();
      //  guiCRUDEProduct.dispose();
       // guiCRUDFProduct.dispose();
       // guiCRUDPProduct.dispose();
      //  guiCRUDProductCategory.dispose();
       // guiCRUDProviders.dispose();
      //  guiNewProvider.dispose();
        guiCRUDUser.dispose();
      //  guiLoadPurchase.dispose();
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        if (ae.getSource() == guiMain.getBtnDisconnect()) {//cerrar sesion
            int r = JOptionPane.showConfirmDialog(guiMain, "¿Desea cerrar la sesion?", "Cerrar sesion", JOptionPane.YES_NO_OPTION);
            if (r == JOptionPane.YES_OPTION) {
                closeSession();
            }

        }
        if (ae.getSource() == guiMain.getBtnExit()) {
            int r = JOptionPane.showConfirmDialog(guiMain, "¿Desea cerrar la aplicación?", "Salir", JOptionPane.YES_NO_OPTION);
            if (r == JOptionPane.YES_OPTION) {
                System.exit(0);
            }
        }
        //boton admin
        if (ae.getSource() == guiMain.getBtnAdmins()) {
            guiCRUDAdmin.setVisible(true);
            guiCRUDAdmin.toFront();
        }
        //boton producto elaborado
        if (ae.getSource() == guiMain.getBtnEProduct()) {
            try {
                guiCRUDEProduct.setMaximum(true);
            } catch (PropertyVetoException ex) {
                Logger.getLogger(ControllerMain.class.getName()).log(Level.SEVERE, null, ex);
            }
            guiCRUDEProduct.setVisible(true);
            guiCRUDEProduct.toFront();
        }
        //boton producto final
        if (ae.getSource() == guiMain.getBtnFProduct()) {
            try {
                guiCRUDFProduct.setMaximum(true);
            } catch (PropertyVetoException ex) {
                Logger.getLogger(ControllerMain.class.getName()).log(Level.SEVERE, null, ex);
            }
            guiCRUDFProduct.setVisible(true);
            guiCRUDFProduct.toFront();
        }
        //boton producto primario
        if (ae.getSource() == guiMain.getBtnPProduct()) {
            try {
                guiCRUDPProduct.setMaximum(true);
            } catch (PropertyVetoException ex) {
                Logger.getLogger(ControllerMain.class.getName()).log(Level.SEVERE, null, ex);
            }
            guiCRUDPProduct.setVisible(true);
            guiCRUDPProduct.toFront();
        }
        //boton categoria producto
        if (ae.getSource() == guiMain.getBtnProductCategory()) {
            try {
                guiCRUDProductCategory.setMaximum(true);
            } catch (PropertyVetoException ex) {
                Logger.getLogger(ControllerMain.class.getName()).log(Level.SEVERE, null, ex);
            }
            guiCRUDProductCategory.setVisible(true);
            guiCRUDProductCategory.toFront();
        }
        //boton proveedores
        if (ae.getSource() == guiMain.getBtnProviders()) {
            guiCRUDProviders.cleanComponents();
            try {
                guiCRUDProviders.setMaximum(true);
            } catch (PropertyVetoException ex) {
                Logger.getLogger(ControllerMain.class.getName()).log(Level.SEVERE, null, ex);
            }
            guiCRUDProviders.setVisible(true);
            guiCRUDProviders.toFront();
        }
        //boton empleados
        if(ae.getSource() == guiMain.getBtnEmployes()){
            try {
                guiCRUDUser.setMaximum(true);
            } catch (PropertyVetoException ex) {
                Logger.getLogger(ControllerMain.class.getName()).log(Level.SEVERE, null, ex);
            }
            guiCRUDUser.setVisible(true);
            guiCRUDUser.toFront();
        }
        //boton jtree
        if(ae.getSource() == guiMain.getBtnJTree()){
            guiJTree.setVisible(true);
            guiJTree.toFront();
        }
    }

}
