/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servertik;

import implementsInterface.CRUDCategory;
import implementsInterface.CRUDEproduct;
import implementsInterface.CRUDFproduct;
import implementsInterface.CRUDOrder;
import implementsInterface.CRUDPproduct;
import implementsInterface.CRUDTurn;
import implementsInterface.CrudAdmin;
import implementsInterface.CrudPresence;
import implementsInterface.CrudProvider;
import implementsInterface.CrudProviderCategory;
import implementsInterface.CrudUser;
import implementsInterface.GeneralConfig;
import implementsInterface.Server;
import implementsInterface.box.expenses.CRUDExpenses;
import implementsInterface.cashbox.CRUDCashbox;
import implementsInterface.deposits.CRUDDeposit;
import implementsInterface.providers.payments.CRUDPayments;
import implementsInterface.providers.purchase.CRUDPurchase;
import implementsInterface.statistics.CRUDStatistics;
import implementsInterface.withdrawal.CRUDWithdrawal;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.util.Enumeration;
import search.providersSearch.ProvidersSearch;
import utils.InterfaceName;

/**
 *
 * @author nico
 */
public class ServerTik {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws RemoteException, MalformedURLException, InterruptedException, UnknownHostException, SocketException, IOException {

        Enumeration<NetworkInterface> n = NetworkInterface.getNetworkInterfaces();
        for (; n.hasMoreElements();) {
            NetworkInterface e = n.nextElement();
            Enumeration<InetAddress> a = e.getInetAddresses();
            for (; a.hasMoreElements();) {
                InetAddress addr = a.nextElement();
                if (addr.isSiteLocalAddress()) {
                    System.out.println("The IP of server is: " + addr.getHostAddress());
                }
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
        CrudAdmin CRUDAdmin = new CrudAdmin();
        Server server = new Server();
        CRUDEproduct CRUDEproduct = new CRUDEproduct();
        CRUDPproduct CRUDPproduct = new CRUDPproduct();
        CRUDFproduct CRUDFproduct = new CRUDFproduct();
        CRUDCategory CRUDCategory = new CRUDCategory();
        CRUDPurchase CRUDPurchase = new CRUDPurchase();
        CRUDTurn CRUDTurn = new CRUDTurn();
        CRUDStatistics CRUDStatistics = new CRUDStatistics();
        CRUDOrder CRUDOrder = new CRUDOrder();
        CrudUser CRUDUser = new CrudUser();
        CrudProvider CRUDProvider = new CrudProvider();
        CrudProviderCategory CRUDProviderCategory = new CrudProviderCategory();
        ProvidersSearch providerSearch = new ProvidersSearch();
        CrudPresence CRUDPresence = new CrudPresence();
        GeneralConfig generalConfig = new GeneralConfig();
        CRUDPayments CRUDPayments = new CRUDPayments();
        CRUDWithdrawal CRUDWithdrawal = new CRUDWithdrawal();
        CRUDDeposit CRUDDeposit = new CRUDDeposit();
        CRUDExpenses CRUDExpenses = new CRUDExpenses();
        CRUDCashbox CRUDCashbox = new CRUDCashbox();

        //Cargo las configuraciones generales
        generalConfig.loadProperties();

        //Asocio el objeto remoto 's' a la direccion de mi host seguida de un /nombreAsociado
        Naming.rebind(InterfaceName.CRUDAdmin, CRUDAdmin);
        Naming.rebind(InterfaceName.CRUDPproduct, CRUDPproduct);
        Naming.rebind(InterfaceName.CRUDEproduct, CRUDEproduct);
        Naming.rebind(InterfaceName.CRUDFproduct, CRUDFproduct);
        Naming.rebind(InterfaceName.CRUDCategory, CRUDCategory);
        Naming.rebind(InterfaceName.CRUDPurchase, CRUDPurchase);
        Naming.rebind(InterfaceName.CRUDStatistics, CRUDStatistics);
        Naming.rebind(InterfaceName.CRUDUser, CRUDUser);
        Naming.rebind(InterfaceName.CRUDProvider, CRUDProvider);
        Naming.rebind(InterfaceName.CRUDProviderCategory, CRUDProviderCategory);
        Naming.rebind(InterfaceName.providersSearch, providerSearch);
        Naming.rebind(InterfaceName.CRUDPresence, CRUDPresence);
        Naming.rebind(InterfaceName.CRUDOrder, CRUDOrder);
        Naming.rebind(InterfaceName.server, server);
        Naming.rebind(InterfaceName.GeneralConfig, generalConfig);
        //Naming.rebind(InterfaceName.CRUDTurn, CRUDTurn);
        Naming.rebind(InterfaceName.CRUDpayments, CRUDPayments);
        Naming.rebind(InterfaceName.CRUDWithdrawal, CRUDWithdrawal);
        Naming.rebind(InterfaceName.CRUDDeposit, CRUDDeposit);
        Naming.rebind(InterfaceName.CRUDTurn, CRUDTurn);
        Naming.rebind(InterfaceName.CRUDExpenses, CRUDExpenses);
        Naming.rebind(InterfaceName.CRUDCashbox, CRUDCashbox);
    }

}
