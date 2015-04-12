/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package admintik;

import implementsInterface.Client;
import interfaces.InterfaceAdmin;
import interfaces.InterfaceCategory;
import interfaces.InterfaceProdCategory;
import interfaces.InterfaceProduct;
import interfaces.InterfaceProvider;
import interfaces.InterfaceServer;
import interfaces.InterfaceUser;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RMISecurityManager;
import java.rmi.RemoteException;
import utils.Config;

/**
 *
 * @author agustin
 */
public class AdminTik{

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws NotBoundException, MalformedURLException, RemoteException {

        
        if (System.getSecurityManager() == null){
            System.setSecurityManager(new RMISecurityManager());
        }
        
        
        InterfaceServer server = (InterfaceServer) Naming.lookup("//"+Config.ip+"/Server");
        
        InterfaceAdmin crudAdmin = (InterfaceAdmin)   Naming.lookup("//"+Config.ip+"/crudAdmin");
        InterfaceCategory category = (InterfaceCategory)   Naming.lookup("//"+Config.ip+"/crudCategory");
        InterfaceProdCategory prodCategory = (InterfaceProdCategory)   Naming.lookup("//"+Config.ip+"/crudProdCategory");
        InterfaceProduct product = (InterfaceProduct)   Naming.lookup("//"+Config.ip+"/crudProduct");
        InterfaceProvider provider = (InterfaceProvider)   Naming.lookup("//"+Config.ip+"/crudProvider");
        InterfaceUser user = (InterfaceUser)   Naming.lookup("//"+Config.ip+"/crudUser");  
       // System.setProperty("java.security.policy","/home/agustin/Documentos/ProyectosGithub/Tik/ServerTik/src/java.policy");
        Client client= new Client();
        server.registerClient(client,"mozo");

        System.out.println("Crear mozo, TRUE = "+crudAdmin.create("agu", "azucar"));
       /* System.out.println("Modificar mozo, TRUE = "+ crudAdmin.modify(1, "nicoUpdate", "nicoUpdate"));
        System.out.println("Obtener mozo:" +crudAdmin.getAdmin(1).toString());
        System.out.println("Obtener todos los mozos:" +crudAdmin.getAdmins().toString());
        System.out.println("Borrar mozo, TRUE = " +crudAdmin.delete(3));*/
    }

}
