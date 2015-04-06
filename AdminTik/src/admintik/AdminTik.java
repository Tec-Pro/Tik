/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package admintik;

import controllers.providers.ControllerGuiCRUDProviders;
import gui.providers.*;
import implementsInterface.Client;
import interfaces.providers.InterfaceProvider;
import interfaces.providers.InterfaceProviderCategory;
import interfaces.InterfaceServer;
import interfaces.providers.InterfaceProvidersSearch;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RMISecurityManager;
import java.rmi.RemoteException;
import javax.swing.JFrame;

/**
 *
 * @author agustin
 */
public class AdminTik{

    /**
     * @param args the command line arguments
     * @throws java.rmi.NotBoundException
     * @throws java.net.MalformedURLException
     * @throws java.rmi.RemoteException
     */
    public static void main(String[] args) throws NotBoundException, MalformedURLException, RemoteException {

        
        if (System.getSecurityManager() == null){
            System.setSecurityManager(new RMISecurityManager());
        }
        
        
        InterfaceServer server = (InterfaceServer) Naming.lookup("//localhost/Server");
        Client client= new Client();
        server.registerClient(client,"AdminTik");
        
        InterfaceProvider provider = (InterfaceProvider) Naming.lookup("//localhost/crudProvider");
        InterfaceProviderCategory providerCategory = (InterfaceProviderCategory ) Naming.lookup("//localhost/crudProviderCategory");
        InterfaceProvidersSearch providersSearch = (InterfaceProvidersSearch) Naming.lookup("//localhost/providersSearch");
        
        //gui contenedora para testear
        GuiTest guiPrincipal = new GuiTest();
        
        //guis internas
        GuiCRUDProviders guiCRUDProviders = new GuiCRUDProviders();
        GuiNewCategory guiNewCategory = new GuiNewCategory();
        GuiNewProvider guiNewProvider = new GuiNewProvider();
        
        
        new ControllerGuiCRUDProviders(guiCRUDProviders, guiNewCategory, guiNewProvider, provider, providerCategory, providersSearch);
        
        //configuraciones de la gui test
        guiPrincipal.getjDesktopPane1().add(guiCRUDProviders);
        guiPrincipal.getjDesktopPane1().add(guiNewCategory);
        guiPrincipal.getjDesktopPane1().add(guiNewProvider);
        guiPrincipal.setExtendedState(JFrame.MAXIMIZED_BOTH);
        guiPrincipal.setVisible(true);
        guiCRUDProviders.cleanComponents();
        guiCRUDProviders.setVisible(true);
    }

}
