/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package implementsInterface;

import interfaces.InterfaceClient;
import interfaces.InterfaceClientKitchen;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Iterator;
import java.util.LinkedList;

/**
 *
 * @author nico
 */
public class Server extends UnicastRemoteObject implements interfaces.InterfaceServer {

    
    
    public static volatile LinkedList<Object[]> clients;
    @Override
    public void registerClient(InterfaceClient client, String name) throws RemoteException {
        Object[] clientArr = new Object[2];
        clientArr[0]= client;
        clientArr[1] = name;
        clients.add(clientArr);
        
    }
    
    
    @Override
    public void registerClientKitchen(InterfaceClientKitchen client, String name) throws RemoteException {
        Object[] clientArr = new Object[2];
        clientArr[0]= client;
        clientArr[1] = name;
        clients.add(clientArr);
    }
    
    
    public Server()throws RemoteException{
        super();
        clients= new LinkedList<>();
    }
    
    //le aviso a los mozos que el pedido con id está listo
    public static void notifyWaitersOrderReady(int id) throws RemoteException{
        Iterator<Object[]> it= clients.iterator();
        int i=0;
        while(it.hasNext()){
            Object[] client= it.next();
            if(client[1].equals("waiter")){
                try{
                ((InterfaceClient)client[0]).readyOrder(id);
                }catch(java.rmi.ConnectException e){
                    System.err.println("se rompió porque se cerro un programa seguro"+e);
                    clients.remove(i);
                    //despues voy a eliminar este tipo porque la conexión se rechazó por desconectarses
                }
            }
            i++;
        }
    }

    

    //avisa a la cocina que hay un nuevo pedido o se actualizo uno
    public static void notifyKitchenNewOrder(int id) throws RemoteException{
        Iterator<Object[]> it= clients.iterator();
        int i=0;
        while(it.hasNext()){
            Object[] client= it.next();
            if(client[1].equals("kitchen")){
                try{
                ((InterfaceClientKitchen)client[0]).newOrder(id);
                }catch(java.rmi.ConnectException e){
                    System.err.println("Se rompió porque se cerro el programa cocina seguramente."+e);
                    clients.remove(i);
                    //despues voy a eliminar este tipo porque la conexión se rechazó por desconectarses
                }
            }
            i++;
        }
    }
   
}
