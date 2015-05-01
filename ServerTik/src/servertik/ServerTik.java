/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package servertik;

import implementsInterface.CRUDCategory;
import implementsInterface.CRUDEproduct;
import implementsInterface.CRUDFproduct;
import implementsInterface.CRUDPproduct;
import implementsInterface.CrudAdmin;
import implementsInterface.CrudProvider;
import implementsInterface.CrudProviderCategory;
import implementsInterface.CrudUser;
import implementsInterface.Server;
import implementsInterface.providers.purchase.CRUDPurchase;
import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.rmi.Naming;
import java.rmi.RMISecurityManager;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.util.Enumeration;
import org.javalite.activejdbc.Base;
import search.providersSearch.ProvidersSearch;

/**
 *
 * @author nico
 */
public class ServerTik {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws RemoteException, MalformedURLException, InterruptedException, UnknownHostException, SocketException {
             
    Enumeration<NetworkInterface> n = NetworkInterface.getNetworkInterfaces();
    for (; n.hasMoreElements();)
    {
        NetworkInterface e = n.nextElement();

        Enumeration<InetAddress> a = e.getInetAddresses();
        for (; a.hasMoreElements();)
        {
            InetAddress addr = a.nextElement();
            if(addr.isSiteLocalAddress())
                System.out.println("The IP of server is: " + addr.getHostAddress());
        }
    }
        

            // arrancar un gestor de seguridad – esto es 
            // necesario si se utiliza stub downloading
            /*if (System.getSecurityManager() == null){
                System.setSecurityManager(new RMISecurityManager());
            }*/
            //System.setProperty("java.rmi.server.codebase",getClass().getResource("java.policy").toString());
            //System.setProperty("java.security.policy","/home/agustin/Documentos/ProyectosGithub/Tik/ServerTik/src/java.policy");

            // Creo el registro de objetos remotos, que acepte llamadas en el puerto 1099
           LocateRegistry.createRegistry(1099);
           //para saber que el servidor esta funcionando 
           System.out.println("<<<<<<<<< Servidor Andando >>>>>>>>>");
           CrudAdmin crudAdmin = new CrudAdmin();
           Server server= new Server();
           CRUDEproduct CRUDEproduct = new CRUDEproduct();
           CRUDPproduct CRUDPproduct = new CRUDPproduct();
           CRUDFproduct CRUDFproduct = new CRUDFproduct();
           CRUDCategory crudCategory = new CRUDCategory();
           CRUDPurchase CRUDPurchase = new CRUDPurchase();
           CrudUser crudUser = new CrudUser();
           CrudProvider crudProvider= new CrudProvider();
           CrudProviderCategory crudProviderCategory= new CrudProviderCategory();
           ProvidersSearch providerSearch = new ProvidersSearch();
           //Asocio el objeto remoto 's' a la direccion de mi host seguida de un /nombreAsociado
           Naming.rebind("crudAdmin", crudAdmin); 
           Naming.rebind("CRUDPproduct", CRUDPproduct);
           Naming.rebind("CRUDEproduct", CRUDEproduct);
           Naming.rebind("CRUDFproduct", CRUDFproduct); 
           Naming.rebind("CRUDCategory", crudCategory);
           Naming.rebind("CRUDPurchase", CRUDPurchase);
           Naming.rebind("crudUser", crudUser);
           Naming.rebind("crudProvider", crudProvider);
           Naming.rebind("crudProviderCategory", crudProviderCategory);
           Naming.rebind("providersSearch", providerSearch);
           Naming.rebind("Server", server);  
           
           //ESTO ES DE LOS PEDIDOS DEL TORTUGA LO COMENTO PORQUE LE PUSO WHILE INFINITO 
//           while(true){ // le aviso infinitamente que tienen pedidos 
  //         Thread.sleep(7000);
    //       Server.notifyWaitersOrderReady(1);
      //     }
        //   System.out.println("hice un usuario" + crudAdmin.create("agu", "aguasdasd"));
    }
    
}
