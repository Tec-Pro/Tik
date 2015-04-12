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
import implementsInterface.CrudProdCategory;
import implementsInterface.CrudProduct;
import implementsInterface.CrudProvider;
import implementsInterface.CrudUser;
import implementsInterface.Server;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.RMISecurityManager;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;

/**
 *
 * @author nico
 */
public class ServerTik {

    /**
     * @param args the command line arguments
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
           CrudAdmin crudAdmin = new CrudAdmin();
           Server server= new Server();
           CRUDEproduct CRUDEproduct = new CRUDEproduct();
           CRUDPproduct CRUDPproduct = new CRUDPproduct();
           CRUDFproduct CRUDFproduct = new CRUDFproduct();
                      CRUDCategory crudCategory = new CRUDCategory();
           CrudProdCategory crudProdCategory = new CrudProdCategory();
           //Asocio el objeto remoto 's' a la direccion de mi host seguida de un /nombreAsociado
           Naming.rebind("crudAdmin", crudAdmin); 
           Naming.rebind("CRUDPproduct", CRUDPproduct);
           Naming.rebind("CRUDEproduct", CRUDEproduct);
           Naming.rebind("CRUDFproduct", CRUDFproduct); 
           Naming.rebind("CRUDCategory", crudCategory);
           //Naming.rebind("crudProdCategory", crudProdCategory);  
           Naming.rebind("Server", server);  
           
        //   System.out.println("hice un usuario" + crudAdmin.create("agu", "aguasdasd"));
    }
    
}
