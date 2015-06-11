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
import java.util.List;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;
import utils.Pair;

/**
 *
 * @author nico
 */
public class Server extends UnicastRemoteObject implements interfaces.InterfaceServer {

    public static CopyOnWriteArrayList<InterfaceClientAdmin> admins;
    public static CopyOnWriteArrayList<InterfaceClientWaiter> waiters;
    public static CopyOnWriteArrayList<InterfaceClientKitchen> chefs;
    public static CopyOnWriteArrayList<InterfaceClientBar> bartenders;

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

    /**
     * avisa a los mozos que un pedido está atrasado toma como parametro el id
     * del pedido
     *
     * @param order
     * @throws RemoteException
     */
    @Override
    public void notifyWaitersOrderDelayed(int order) throws RemoteException {
        Iterator<InterfaceClientWaiter> it = waiters.iterator();
        int i = 0;
        while (it.hasNext()) {
            InterfaceClientWaiter client = it.next();
            try {
                client.orderDelayed(order);
            } catch (java.rmi.ConnectException e) {
                System.err.println("Se rompió porque se cerro el programa de mozos seguramente.o" + e);
                waiters.remove(i);
                //despues voy a eliminar este tipo porque la conexión se rechazó por desconectarses
            }
            i++;
        }
    }

    //le aviso a los mozos que el pedido con id está listo
    public static void notifyWaitersOrderReady(Pair<Map<String, Object>, List<Map>> order) throws RemoteException {
        Iterator<InterfaceClientWaiter> it = waiters.iterator();
        int i = 0;
        while (it.hasNext()) {
            InterfaceClientWaiter client = it.next();
            try {
                client.readyOrder(order);
            } catch (java.rmi.ConnectException e) {
                System.err.println("Se rompió porque se cerro el programa de mozos seguramente.o" + e);
                waiters.remove(i);
                //despues voy a eliminar este tipo porque la conexión se rechazó por desconectarses
            }
            i++;
        }
        
    }
    
    /**
     * Función que notifica al bar que una orden de la cocina ya está lista.
     * @param order Orden lista donde first es la orden y second es la lista de productos.
     * order.first es un Map que tiene {persons, user_id, user_name, order_number, description, closed, id}
     * order.second es una lista de Maps donde cada uno tiene 
     * {quantity, updated_at, paid, created_at, id, issued, order_id, fproduct_id, done, commited}
     * @throws RemoteException 
     */
    public static void notifyBarKitchenOrderReady(Pair<Map<String, Object>, List<Map>> order) throws RemoteException {
        //Esta parte le avisa al bar que un pedido de la cocina está listo
        Iterator<InterfaceClientBar> itBar = bartenders.iterator();
        int j = 0;
        while (itBar.hasNext()) {
            InterfaceClientBar bar = itBar.next();
            try {
                bar.kitchenOrderReady(order);
            } catch (java.rmi.ConnectException e){
                System.out.println(e.toString());
                bartenders.remove(j);
            }
        }
    }
    
        /**
     * Función que notifica al bar que una orden de la cocina ya está lista.
     * @param order Orden lista donde first es la orden y second es la lista de productos.
     * order.first es un Map que tiene {persons, user_id, user_name, order_number, description, closed, id}
     * order.second es una lista de Maps donde cada uno tiene 
     * {quantity, updated_at, paid, created_at, id, issued, order_id, fproduct_id, done, commited}
     * @throws RemoteException 
     */
    public static void notifyBarKitchenProductsCommited(Pair<Map<String, Object>, List<Map>> order) throws RemoteException {
        //Esta parte le avisa al bar que un pedido de la cocina está listo
        Iterator<InterfaceClientBar> itBar = bartenders.iterator();
        int j = 0;
        while (itBar.hasNext()) {
            InterfaceClientBar bar = itBar.next();
            try {
                bar.kitchenOrderCommited(order);
            } catch (java.rmi.ConnectException e){
                bartenders.remove(j);
            }
        }
    }
    
    //avisa a la cocina que hay un nuevo pedido
    public static void notifyKitchenNewOrder(Pair<Map<String, Object>, List<Map>> order) throws RemoteException {
        Iterator<InterfaceClientKitchen> it = chefs.iterator();
        int i = 0;
        while (it.hasNext()) {
            InterfaceClientKitchen client = it.next();
            try {
                client.newOrder(order);
            } catch (java.rmi.ConnectException e) {
                System.err.println("Se rompió porque se cerro el programa de cocina seguramente." + e);
                chefs.remove(i);
                //despues voy a eliminar este tipo porque la conexión se rechazó por desconectarses
            }

            i++;
        }
    }

    //avisa al Bar que un pedido fue modificado
    public static void notifyBarUpdatedOrder(Pair<Map<String, Object>, List<Map>> order) throws RemoteException {
        Iterator<InterfaceClientBar> it = bartenders.iterator();
        int i = 0;
        while (it.hasNext()) {
            InterfaceClientBar client = it.next();
            try {
                client.updatedOrder(order);
            } catch (java.rmi.ConnectException e) {
                System.err.println("Se rompió porque se cerro el programa de bar seguramente." + e);
                bartenders.remove(i);
                //despues voy a eliminar este tipo porque la conexión se rechazó por desconectarses
            }

            i++;
        }
    }

    //avisa al Bar que hay un nuevo pedido
    public static void notifyBarNewOrder(Pair<Map<String, Object>, List<Map>> order) throws RemoteException {
        Iterator<InterfaceClientBar> it = bartenders.iterator();
        int i = 0;
        while (it.hasNext()) {
            InterfaceClientBar client = it.next();
            try {
                client.newOrder(order);
            } catch (java.rmi.ConnectException e) {
                System.err.println("Se rompió porque se cerro el programa de bar seguramente." + e);
                bartenders.remove(i);
                //despues voy a eliminar este tipo porque la conexión se rechazó por desconectarses
            }

            i++;
        }
    }

    //avisa a la cocina que un pedido fue modificado
    public static void notifyKitchenUpdatedOrder(Pair<Map<String, Object>, List<Map>> order) throws RemoteException {
        Iterator<InterfaceClientKitchen> it = chefs.iterator();
        int i = 0;
        while (it.hasNext()) {
            InterfaceClientKitchen client = it.next();
            try {
                client.updatedOrder(order);
            } catch (java.rmi.ConnectException e) {
                System.err.println("Se rompió porque se cerro el programa de cocina seguramente." + e);
                chefs.remove(i);
                //despues voy a eliminar este tipo porque la conexión se rechazó por desconectarses
            }

            i++;
        }
    }

}
