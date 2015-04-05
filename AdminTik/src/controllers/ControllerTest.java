/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import javax.swing.JFrame;
import javax.swing.JDesktopPane;
import gui.GuiCRUDAdmin;
import java.net.MalformedURLException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.Map;

/**
 *
 * @author agustin
 */
public class ControllerTest extends JFrame {

    JDesktopPane desktop = new JDesktopPane();
    GuiCRUDAdmin intFrame = new GuiCRUDAdmin();
    
    public ControllerTest(Map<String,Object> admin) throws NotBoundException, MalformedURLException, RemoteException{ // esta clase es para testear nomas, despues se borra
        //intFrame.setSize(320,240);
        intFrame.setVisible(true);
        desktop.add(intFrame);
        add(desktop);
        this.setSize(800,600);
        this.setVisible(true);
        new ControllerGuiCRUDAdmin(admin, intFrame);
    }
    
}
