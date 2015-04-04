/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import gui.AplicacionGui;
import gui.GuiCRUDPProduct;

import java.awt.Cursor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyVetoException;
import java.io.File;
import java.net.MalformedURLException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

/**
 *
 * @author nico
 */
public class ControladorApliacion implements ActionListener {

    private final AplicacionGui aplicacionGui;
    GuiCRUDPProduct guiCRUDPProduct;
    ControllerGuiCRUDPproduct controllerGuiCRUDPproduct;
 

    public ControladorApliacion() throws ClassNotFoundException, SQLException, PropertyVetoException, NotBoundException, MalformedURLException, RemoteException {
        try {
            UIManager.setLookAndFeel("com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel");
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException e) {
        }
        aplicacionGui = new AplicacionGui();
        aplicacionGui.setCursor(Cursor.WAIT_CURSOR);
        aplicacionGui.setActionListener(this);
        aplicacionGui.setExtendedState(JFrame.MAXIMIZED_BOTH);
        guiCRUDPProduct = new GuiCRUDPProduct();
        controllerGuiCRUDPproduct = new ControllerGuiCRUDPproduct(guiCRUDPProduct);
        aplicacionGui.getContenedor().add(guiCRUDPProduct);
        aplicacionGui.setCursor(Cursor.DEFAULT_CURSOR);
        aplicacionGui.setVisible(true);
    }

    public static void main(String[] args) throws InterruptedException, ClassNotFoundException, SQLException, PropertyVetoException, NotBoundException, MalformedURLException, RemoteException {
        ControladorApliacion controladorAplicacion = new ControladorApliacion();
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        if (ae.getSource() == aplicacionGui.getPROBAR()) {
            guiCRUDPProduct.setVisible(true);
            guiCRUDPProduct.show();
        }
    }
}
