/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package implementsInterface;

import interfaces.InterfaceClient;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Iterator;
import java.util.LinkedList;

/**
 *
 * @author nico
 */
public class Server extends UnicastRemoteObject implements interfaces.InterfaceServer {

    
    
    public static LinkedList<Object[]> clients;
    @Override
    public void registerClient(InterfaceClient client, String name) throws RemoteException {
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
        while(it.hasNext()){
            Object[] client= it.next();
            if(client[1].equals("waiter")){
                ((InterfaceClient)client[0]).readyOrder(id);
            }
            
        }
    }
    
}
