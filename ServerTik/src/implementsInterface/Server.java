/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package implementsInterface;

import interfaces.InterfaceClientAdmin;
import interfaces.InterfaceClientBar;
import interfaces.InterfaceClientWaiter;
import interfaces.InterfaceClientKitchen;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.concurrent.CopyOnWriteArrayList;
import utils.Pair;

/**
 *
 * @author nico
 */
public class Server extends UnicastRemoteObject implements interfaces.InterfaceServer {

    public static  CopyOnWriteArrayList<InterfaceClientAdmin> admins;
    public static  CopyOnWriteArrayList<InterfaceClientWaiter> waiters;
    public static  CopyOnWriteArrayList<InterfaceClientKitchen> chefs;
    public static  CopyOnWriteArrayList<InterfaceClientBar> bartenders;


    
    public Server() throws RemoteException {
        super();
        admins = new CopyOnWriteArrayList<>();
        chefs = new CopyOnWriteArrayList<>();
        waiters = new CopyOnWriteArrayList<>();
        bartenders = new CopyOnWriteArrayList<>();
    }
    
    @Override
    public void registerClientAdmin(InterfaceClientAdmin client) throws RemoteException {
        admins.add(client);

    }
    
    @Override
    public void registerClientBar(InterfaceClientBar client) throws RemoteException {
        bartenders.add(client);
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
    
    //avisa al Bar que un pedido fue modificado
    public static void notifyBarUpdatedOrder(int id) throws RemoteException {
        Iterator<InterfaceClientBar> it = bartenders.iterator();
        int i = 0;
        while (it.hasNext()) {
            InterfaceClientBar client = it.next();
            try {
                client.updatedOrder(id); 
            } catch (java.rmi.ConnectException e) {
                System.err.println("se rompió porque se cerro un programa seguro" + e);
                bartenders.remove(i);
                //despues voy a eliminar este tipo porque la conexión se rechazó por desconectarses
            }

            i++;
        }
    }
    
    //avisa al Bar que hay un nuevo pedido
    public static void notifyBarNewOrder(int id) throws RemoteException {
        Iterator<InterfaceClientBar> it = bartenders.iterator();
        int i = 0;
        while (it.hasNext()) {
            InterfaceClientBar client = it.next();
            try {
                client.newOrder(id);
            } catch (java.rmi.ConnectException e) {
                System.err.println("se rompió porque se cerro un programa seguro" + e);
                bartenders.remove(i);
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
