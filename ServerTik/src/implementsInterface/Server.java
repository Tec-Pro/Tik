/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package implementsInterface;

import interfaces.InterfaceClientAdmin;
import interfaces.InterfaceClientWaiter;
import interfaces.InterfaceClientKitchen;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Iterator;
import java.util.LinkedList;
import utils.Pair;

/**
 *
 * @author nico
 */
public class Server extends UnicastRemoteObject implements interfaces.InterfaceServer {

    public static volatile LinkedList<InterfaceClientAdmin> admins;
    public static volatile LinkedList<InterfaceClientWaiter> waiters;
    public static volatile LinkedList<InterfaceClientKitchen> chefs;
    //public static volatile LinkedList<Pair<InterfaceClientBartender,String>> bartenders;

    
    public Server() throws RemoteException {
        super();
        admins = new LinkedList<>();
        chefs = new LinkedList<>();
        waiters = new LinkedList<>();
    }
    
    @Override
    public void registerClientAdmin(InterfaceClientAdmin client) throws RemoteException {
        admins.add(client);

    }

    @Override
    public void registerClientKitchen(InterfaceClientKitchen client) throws RemoteException {
        chefs.add(client);
    }

    @Override
    public void registerClientWaiter(InterfaceClientWaiter client) throws RemoteException {
        waiters.add(client);
    }

    //le aviso a los mozos que el pedido con id está listo
    public static void notifyWaitersOrderReady(int id) throws RemoteException {
        Iterator<InterfaceClientWaiter> it = waiters.iterator();
        int i = 0;
        while (it.hasNext()) {
            InterfaceClientWaiter client = it.next();
            try {
                client.readyOrder(id);
            } catch (java.rmi.ConnectException e) {
                System.err.println("se rompió porque se cerro un programa seguro" + e);
                waiters.remove(i);
                //despues voy a eliminar este tipo porque la conexión se rechazó por desconectarses
            }
            i++;
        }
    }

    //avisa a la cocina que hay un nuevo pedido
    public static void notifyKitchenNewOrder(int id) throws RemoteException {
        Iterator<InterfaceClientKitchen> it = chefs.iterator();
        int i = 0;
        while (it.hasNext()) {
            InterfaceClientKitchen client = it.next();
            try {
                client.newOrder(id);
            } catch (java.rmi.ConnectException e) {
                System.err.println("se rompió porque se cerro un programa seguro" + e);
                chefs.remove(i);
                //despues voy a eliminar este tipo porque la conexión se rechazó por desconectarses
            }

            i++;
        }
    }
    
    //avisa a la cocina que un pedido fue modificado
    public static void notifyKitchenUpdatedOrder(int id) throws RemoteException {
        Iterator<InterfaceClientKitchen> it = chefs.iterator();
        int i = 0;
        while (it.hasNext()) {
            InterfaceClientKitchen client = it.next();
            try {
                client.updatedOrder(id); 
            } catch (java.rmi.ConnectException e) {
                System.err.println("se rompió porque se cerro un programa seguro" + e);
                chefs.remove(i);
                //despues voy a eliminar este tipo porque la conexión se rechazó por desconectarses
            }

            i++;
        }
    }

}
