/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clientemozotik;

import implementsInterface.Client;
import interfaces.InterfaceCategory;
import interfaces.InterfacePproduct;
import interfaces.InterfaceServer;
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

        InterfaceServer server = (InterfaceServer) Naming.lookup("//192.168.1.16/Server");
        

        InterfacePproduct pproduct = (InterfacePproduct)   Naming.lookup("//192.168.1.16/CRUDPproduct");

        Client client= new Client();
        server.registerClient(client,"mozo");
     
        System.out.println("CREAR Pproducto = " +pproduct.create("arit1", new Float("150"), "Kg",new Float("150") ));
    
       System.out.println("MODIFICAR Pproducto = " + pproduct.modify(1, "arit1MODIFY", new Float("150"), "Kg",new Float("150")));
    
       System.out.println("MODIFICAR Pproducto = " + pproduct.delete(1));    
    }

}
