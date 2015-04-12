/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import gui.GuiAdminLogin;
import gui.GuiCRUDAdmin;
import gui.GuiCRUDEProduct;
import gui.GuiCRUDFProduct;
import gui.GuiCRUDPProduct;
import gui.GuiCRUDProductCategory;
import gui.main.GuiMain;
import java.awt.Cursor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.MalformedURLException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.Map;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

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

    //controladores
    private static ControllerGuiCRUDAdmin controllerCRUDAdmin; //controlador de la gui para admin
    private ControllerGuiCRUDEproduct controllerCRUDEProduct; //controlador productos elaborados
    private ControllerGuiCRUDPproduct controllerCRUDPProduct; //controlador productos primarios
    private ControllerGuiCRUDFproduct controllerCRUDFProduct; //controlador productos finales
    private ControllerGuiProductCategory controllerCRUDProductCategory; //controlador categorias de productos

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
  //      guiCRUDProductCategory = new GuiCRUDProductCategory();

        //agrego las gui al desktop
        guiMain.getDesktop().add(guiCRUDAdmin);
        guiMain.getDesktop().add(guiCRUDEProduct);
        guiMain.getDesktop().add(guiCRUDFProduct);
        guiMain.getDesktop().add(guiCRUDPProduct);
     //   guiMain.getDesktop().add(guiCRUDProductCategory);

        //creo los controladores 
        controllerCRUDAdmin = new ControllerGuiCRUDAdmin(userLogged, guiCRUDAdmin);
        controllerCRUDEProduct = new ControllerGuiCRUDEproduct(guiCRUDEProduct);
        controllerCRUDFProduct = new ControllerGuiCRUDFproduct(guiCRUDFProduct);
        controllerCRUDPProduct = new ControllerGuiCRUDPproduct(guiCRUDPProduct);
      //  controllerCRUDProductCategory = new ControllerGuiProductCategory(guiCRUDProductCategory);

        //restauro el puntero asi ya se que termino de cargar todo
        guiMain.setCursor(Cursor.DEFAULT_CURSOR);

    }

    public static void closeSession() {
        guiMain.setVisible(false); //finalizo la ventana entera
        guiAdminLogin.setVisible(true);//hago visible la del login
        //debo finaliar todas las INTERNALFRAME!
        //para evitar que quede abierta y el otro admin vea que  hizo
        ControllerGuiAdminLogin.getAllAdmins();
        guiCRUDAdmin.dispose();
        guiCRUDEProduct.dispose();
        guiCRUDFProduct.dispose();
        guiCRUDPProduct.dispose();
     //   guiCRUDProductCategory.dispose();
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
            guiCRUDEProduct.setVisible(true);
            guiCRUDEProduct.toFront();
        }
        //boton producto final
        if (ae.getSource() == guiMain.getBtnFProduct()) {
            guiCRUDFProduct.setVisible(true);
            guiCRUDFProduct.toFront();
        }
        //boton producto primario
        if (ae.getSource() == guiMain.getBtnPProduct()) {
            guiCRUDPProduct.setVisible(true);
            guiCRUDPProduct.toFront();
        }
        //boton categoria producto
        if (ae.getSource() == guiMain.getBtnProductCategory()) {
            guiCRUDProductCategory.setVisible(true);
            guiCRUDProductCategory.toFront();
        }
    }

}
