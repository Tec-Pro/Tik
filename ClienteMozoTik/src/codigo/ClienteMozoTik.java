/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clientemozotik;

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
import java.rmi.RemoteException;

/**
 *
 * @author nico
 */
public class ClienteMozoTik {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws NotBoundException, MalformedURLException, RemoteException {

        InterfaceServer server = (InterfaceServer) Naming.lookup("//192.168.0.111/Server");
        
        InterfaceAdmin crudAdmin = (InterfaceAdmin)   Naming.lookup("//192.168.0.110/crudAdmin");
        InterfaceCategory category = (InterfaceCategory)   Naming.lookup("//192.168.0.110/crudCategory");
        InterfaceProdCategory prodCategory = (InterfaceProdCategory)   Naming.lookup("//192.168.0.110/crudProdCategory");
        InterfaceProduct product = (InterfaceProduct)   Naming.lookup("//192.168.0.110/crudProduct");
        InterfaceProvider provider = (InterfaceProvider)   Naming.lookup("//192.168.0.110/crudProvider");
        InterfaceUser user = (InterfaceUser)   Naming.lookup("//192.168.0.110/crudUser");  

        Client client= new Client();
        server.registerClient(client,"mozo");

        System.out.println("Crear mozo, TRUE = "+crudAdmin.create("nico", "nico"));
        System.out.println("Modificar mozo, TRUE = "+ crudAdmin.modify(1, "nicoUpdate", "nicoUpdate"));
        System.out.println("Obtener mozo:" +crudAdmin.getAdmin(1).toString());
        System.out.println("Obtener todos los mozos:" +crudAdmin.getAdmins().toString());
        System.out.println("Borrar mozo, TRUE = " +crudAdmin.delete(3));
    }

}
