/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import controllers.cashbox.ControllerGUICashbox;
import controllers.providers.ControllerGuiCRUDProviders;
import controllers.providers.purchase.ControllerGuiPurchase;
import controllers.statistics.ControllerGuiProductList;
import controllers.statistics.ControllerGuiProductStatistics;
import controllers.statistics.ControllerGuiSalesStatistics;
//import controllers.withdrawals.ControllerGUICRUDWithdrawal;
import gui.GuiAdminLogin;
import gui.GuiCRUDAdmin;
import gui.GuiCRUDEProduct;
import gui.GuiCRUDFProduct;
import gui.GuiCRUDPProduct;
import gui.GuiCRUDProductCategory;
import gui.GuiCRUDUser;
import gui.GuiMenu;
import gui.GuiLoadPurchase;
import gui.cashbox.GUICashbox;
import gui.main.GuiConfig;
import gui.main.GuiMain;
import gui.providers.GuiCRUDProviders;
import gui.providers.purchases.GuiPurchase;
import gui.statistics.GuiProductList;
import gui.statistics.GuiProductStatistics;
import gui.statistics.GuiSalesStatistics;
//import gui.withdrawal.GUICRUDWithdrawal;
import interfaces.InterfaceGeneralConfig;
import interfaces.InterfacePresence;
import interfaces.InterfaceTurn;
import interfaces.providers.InterfaceProvider;
import interfaces.providers.InterfaceProviderCategory;
import interfaces.providers.InterfaceProvidersSearch;
import java.awt.Cursor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyVetoException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import utils.GeneralConfig;
import utils.InterfaceName;

/**
 *
 * @author nico
 */
public class ControllerMain implements ActionListener {

    public static GuiMain guiMain; //interfaz principal con el desktop, publico para los Dialog.
    private static Map<String, Object> userLogged; //usuario logeado
    //interface configuracion de propiedades en el servidor
    private static InterfaceGeneralConfig generalConfig;
    //guis
    private static GuiAdminLogin guiAdminLogin; //para poder cerrar sesión
    private static GuiCRUDAdmin guiCRUDAdmin; //gui del crud de admin
    private static GuiCRUDEProduct guiCRUDEProduct; //gui productos elaborados
    private static GuiCRUDPProduct guiCRUDPProduct; //gui productos primarios
    private static GuiCRUDFProduct guiCRUDFProduct; //gui productos finales
    private static GuiCRUDProductCategory guiCRUDProductCategory; //gui categoria productos
    private static GuiCRUDProviders guiCRUDProviders;
    private static GuiCRUDUser guiCRUDUser; //gui usuarios
    private static GuiLoadPurchase guiLoadPurchase;
//    private static GUICRUDWithdrawal guiCRUDWithdrawal;
    private static GUICashbox guiCashbox;
    private static GuiMenu guiMenu;
    private static GuiPurchase guiPurchase;
    private static GuiSalesStatistics guiSalesStatistics;
    private static GuiProductList guiProductList;
    private static GuiProductStatistics guiProductStatistics;
    //controladores
    private static ControllerGuiCRUDAdmin controllerCRUDAdmin; //controlador de la gui para admin
    private ControllerGuiCRUDEproduct controllerCRUDEProduct; //controlador productos elaborados
    private ControllerGuiCRUDPproduct controllerCRUDPProduct; //controlador productos primarios
    private ControllerGuiCRUDFproduct controllerCRUDFProduct; //controlador productos finales
    private ControllerGuiProductCategory controllerCRUDProductCategory; //controlador categorias de productos
    private ControllerGuiCRUDProviders controllerCRUDProviders;
    private ControllerGuiCRUDUser controllerCRUDUser;
    private ControllerGuiMenu controllerGuiMenu;
    private ControllerGuiPurchase controllerGuiPurchase;
    private ControllerGUICashbox controllerGuiCashbox;
    private ControllerGuiProductList controllerGuiProductList;
    private ControllerGuiProductStatistics controllerGuiProductStatistics;
    private ControllerGuiSalesStatistics controllerGuiSalesStatistics;
//    private ControllerGUICRUDWithdrawal controllerGuiCRUDWithdrawal;
    private InterfacePresence crudPresence;
    private InterfaceTurn crudTurn;

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
        guiLoadPurchase = new GuiLoadPurchase();
        guiMenu = new GuiMenu();
        guiPurchase = new GuiPurchase();
        guiCashbox = new GUICashbox();
        guiSalesStatistics = new GuiSalesStatistics();
        guiProductList = new GuiProductList();
        guiProductStatistics = new GuiProductStatistics();
//        guiCRUDWithdrawal = new GUICRUDWithdrawal();

        //agrego las gui al desktop
        guiMain.getDesktop().add(guiCRUDAdmin);
        guiMain.getDesktop().add(guiCRUDEProduct);
        guiMain.getDesktop().add(guiCRUDFProduct);
        guiMain.getDesktop().add(guiCRUDPProduct);
        guiMain.getDesktop().add(guiCRUDProductCategory);
        guiMain.getDesktop().add(guiCRUDProviders);
        guiMain.getDesktop().add(guiCRUDUser);
        guiMain.getDesktop().add(guiLoadPurchase);
        guiMain.getDesktop().add(guiMenu);
        guiMain.getDesktop().add(guiPurchase);
//        guiMain.getDesktop().add(guiCRUDWithdrawal);
        guiMain.getDesktop().add(guiCashbox);
        guiMain.getDesktop().add(guiProductList);
        guiMain.getDesktop().add(guiProductStatistics);
        guiMain.getDesktop().add(guiSalesStatistics);

        InterfaceProvider provider = (InterfaceProvider) InterfaceName.registry.lookup(InterfaceName.CRUDProvider);
        InterfaceProviderCategory providerCategory = (InterfaceProviderCategory) InterfaceName.registry.lookup(InterfaceName.CRUDProviderCategory);
        InterfaceProvidersSearch providersSearch = (InterfaceProvidersSearch) InterfaceName.registry.lookup(InterfaceName.providersSearch);
        generalConfig = (InterfaceGeneralConfig) InterfaceName.registry.lookup(InterfaceName.GeneralConfig);

        //creo los controladores 
        controllerCRUDAdmin = new ControllerGuiCRUDAdmin(userLogged, guiCRUDAdmin);
        controllerCRUDEProduct = new ControllerGuiCRUDEproduct(guiCRUDEProduct);
        controllerCRUDFProduct = new ControllerGuiCRUDFproduct(guiCRUDFProduct);
        controllerCRUDProductCategory = new ControllerGuiProductCategory(guiCRUDProductCategory);
        controllerCRUDProviders = new ControllerGuiCRUDProviders(guiCRUDProviders);
        controllerCRUDUser = new ControllerGuiCRUDUser(guiCRUDUser);
        controllerCRUDPProduct = new ControllerGuiCRUDPproduct(guiCRUDPProduct, guiLoadPurchase);
        controllerGuiMenu = new ControllerGuiMenu(guiMenu, guiMain);
        controllerGuiPurchase = new ControllerGuiPurchase(guiPurchase);
//        controllerGuiCRUDWithdrawal = new ControllerGUICRUDWithdrawal(guiCRUDWithdrawal);
        controllerGuiCashbox = new ControllerGUICashbox(guiCashbox);
        controllerGuiSalesStatistics = new ControllerGuiSalesStatistics(guiSalesStatistics);
        controllerGuiProductList = new ControllerGuiProductList(guiProductList);
        controllerGuiProductStatistics = new ControllerGuiProductStatistics(controllerGuiProductStatistics);
        //restauro el puntero asi ya se que termino de cargar todo
        guiMain.setCursor(Cursor.DEFAULT_CURSOR);

        crudPresence = (InterfacePresence) InterfaceName.registry.lookup(InterfaceName.CRUDPresence);
        crudTurn = (InterfaceTurn) InterfaceName.registry.lookup(InterfaceName.CRUDTurn);

    }

    public static void closeSession() {
        guiMain.setVisible(false); //finalizo la ventana entera
        guiAdminLogin.setVisible(true);//hago visible la del login
        //debo finaliar todas las INTERNALFRAME!
        //para evitar que quede abierta y el otro admin vea que hizo
        guiAdminLogin.clearFields();
        ControllerGuiAdminLogin.getAllAdmins();
        guiCRUDAdmin.dispose();
        guiCRUDEProduct.dispose();
        guiCRUDFProduct.dispose();
        guiCRUDPProduct.dispose();
        guiCRUDProductCategory.dispose();
        guiCRUDProviders.dispose();
        guiCRUDUser.dispose();
        guiLoadPurchase.dispose();
        guiPurchase.dispose();
        guiSalesStatistics.dispose();
        guiProductList.dispose();
        guiProductStatistics.dispose();
//        guiCRUDWithdrawal.dispose();
        guiCashbox.dispose();
    }

    public static boolean isAdmin() {
        return (boolean) userLogged.get("is_admin");
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
                controllerCRUDProviders.loadProviderTable();
                guiCRUDProviders.setMaximum(true);
            } catch (RemoteException | PropertyVetoException ex) {
                Logger.getLogger(ControllerMain.class.getName()).log(Level.SEVERE, null, ex);

            }
            guiCRUDProviders.setVisible(true);
            guiCRUDProviders.toFront();
        }
        //boton empleados
        if (ae.getSource() == guiMain.getBtnEmployes()) {
            try {
                guiCRUDUser.setMaximum(true);
            } catch (PropertyVetoException ex) {
                Logger.getLogger(ControllerMain.class.getName()).log(Level.SEVERE, null, ex);
            }
            guiCRUDUser.setVisible(true);
            guiCRUDUser.toFront();
        }
        //boton menu
        if (ae.getSource() == guiMain.getBtnMenu()) {
            guiMenu.setVisible(true);
            guiMenu.cleanFields();
            try {
                controllerGuiMenu.CreateTree();
            } catch (RemoteException ex) {
                Logger.getLogger(ControllerMain.class.getName()).log(Level.SEVERE, null, ex);
            }
            guiMenu.toFront();
        }
        //boton compras
        if (ae.getSource() == guiMain.getBtnPurchase()) {
            try {
                guiPurchase.setMaximum(true);
            } catch (PropertyVetoException ex) {
                Logger.getLogger(ControllerMain.class.getName()).log(Level.SEVERE, null, ex);
            }
            guiPurchase.setVisible(true);
            guiPurchase.toFront();
        }
        //boton configuracion
        if (ae.getSource() == guiMain.getBtnConfig()) {
            GuiConfig guiConfig = new GuiConfig(guiMain, true);
            try {
                guiConfig.setTxtDelayTime(generalConfig.getDelayTime());
            } catch (RemoteException ex) {
                Logger.getLogger(ControllerMain.class.getName()).log(Level.SEVERE, null, ex);
            }
            guiConfig.setLocationRelativeTo(guiMain);
            guiConfig.setVisible(true);
            if (guiConfig.getStatus() == 1) {
                try {
                    generalConfig.saveProperties(guiConfig.getTxtDelayTime());
                    GeneralConfig.saveProperties(guiConfig.getPercent());
                } catch (IOException ex) {
                    JOptionPane.showMessageDialog(guiMain, "Error al guardar configuracion: " + ex.getMessage());
                }
            }
        }
        if (ae.getSource() == guiMain.getBtnDailyCashbox()) {
            try {
                guiCashbox.setMaximum(true);
            } catch (PropertyVetoException ex) {
                Logger.getLogger(ControllerMain.class.getName()).log(Level.SEVERE, null, ex);
            }
            guiCashbox.setVisible(true);
            guiCashbox.toFront();
        }
        if (ae.getSource() == guiMain.getBtnCloseCashbox()) {
            try {
                if (crudPresence.isSomeoneLogin()) {
                    JOptionPane.showMessageDialog(guiMain, "Aun hay empleados logeados, por favor deslogee todos los empleados antes de cerrar la caja");
                } else {
                    if (crudTurn.changeTurn("N")) {
                        JOptionPane.showMessageDialog(guiMain, "El tunro se cerro exitosamente");

                    }
                    //BLOQUEAR BOTONES CAJA.
                }
            } catch (RemoteException ex) {
                Logger.getLogger(ControllerMain.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        if (ae.getSource() == guiMain.getBtnOpenTM()) {
            try {
                System.out.print("CLICK TURNO MAÑANA WACHIN");
                if (crudTurn.isTurnOpen()) {
                    if (crudTurn.getTurn().endsWith("M")) {
                        JOptionPane.showMessageDialog(guiMain, "El turno mañana ya esta abierto");
                    } else {
                        JOptionPane.showMessageDialog(guiMain, "Debe cerrar el turno anterior, antes de abrir uno nuevo");
                    }
                } else {
                    if (crudTurn.changeTurn("M")) {
                        JOptionPane.showMessageDialog(guiMain, "Turno mañana abierto");
                    }
                }
            } catch (RemoteException ex) {
                Logger.getLogger(ControllerMain.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        if (ae.getSource() == guiMain.getBntOpenTA()) {
            try {
                if (crudTurn.isTurnOpen()) {
                    if (crudTurn.getTurn().endsWith("T")) {
                        JOptionPane.showMessageDialog(guiMain, "El turno tarde ya esta abierto");
                    } else {
                        JOptionPane.showMessageDialog(guiMain, "Debe cerrar el turno anterior, antes de abrir uno nuevo");
                    }
                } else {
                    if (crudTurn.changeTurn("T")) {
                        JOptionPane.showMessageDialog(guiMain, "Turno tarde abierto");
                    }
                }
            } catch (RemoteException ex) {
                Logger.getLogger(ControllerMain.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        if (ae.getSource() == guiMain.getBtnProductList()) {
            try {
                guiProductList.setMaximum(true);
                guiProductList.setVisible(true);
                guiProductList.toFront();
            } catch (PropertyVetoException ex) {
                Logger.getLogger(ControllerMain.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        if (ae.getSource() == guiMain.getBtnProductStatistics()) {
            try {
                guiProductStatistics.setMaximum(true);
                guiProductStatistics.setVisible(true);
                guiProductStatistics.toFront();
            } catch (PropertyVetoException ex) {
                Logger.getLogger(ControllerMain.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        if (ae.getSource() == guiMain.getBtnSalesStatistics()) {
            try {
                guiSalesStatistics.setMaximum(true);
                guiSalesStatistics.setVisible(true);
                guiSalesStatistics.toFront();
            } catch (PropertyVetoException ex) {
                Logger.getLogger(ControllerMain.class.getName()).log(Level.SEVERE, null, ex);
            }

        }
    }
}
