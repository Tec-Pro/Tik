/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package servertik;

import implementsInterface.CrudProvider;
import implementsInterface.CrudProviderCategory;
import implementsInterface.Server;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.RMISecurityManager;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import search.providersSearch.ProvidersSearch;

/**
 *
 * @author nico
 */
public class ServerTik {

    /**
     * @param args the command line arguments
     * @throws java.rmi.RemoteException
     * @throws java.net.MalformedURLException
     */
    public static void main(String[] args) throws RemoteException, MalformedURLException {
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
           CrudProvider crudProvider = new CrudProvider();
           CrudProviderCategory crudProviderCategory = new CrudProviderCategory();
           ProvidersSearch providersSearch = new ProvidersSearch();
           Server server= new Server();
           //Asocio el objeto remoto 's' a la direccion de mi host seguida de un /nombreAsociado
           Naming.rebind("crudProvider", crudProvider);
           Naming.rebind("crudProviderCategory", crudProviderCategory); 
           Naming.rebind("Server", server);  
           Naming.rebind("providersSearch", providersSearch);
           
    }
    
}
