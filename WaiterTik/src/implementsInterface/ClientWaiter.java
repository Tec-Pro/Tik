/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package implementsInterface;

import controller.ControllerGuiMain;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Map;
import javax.swing.JOptionPane;

/**
 *
 * @author nico
 * 
 * Con esta clase, logramos que el server "avise" al cliente de algun cambio,
 * invocando el metodo doSomething, más adelante lo que dehería hacer es que 
 * cuando la cocina termine el pedido, mande el aviso al server, el server
 * vea que es un pedido terminado de parte de la cocina, autmaticamente invoca
 * dosomething de esta clase y asi los clietes mozos actualizan sus vistas
 */
public class ClientWaiter extends UnicastRemoteObject implements interfaces.InterfaceClientWaiter {
    @Override
    public void doSomething() throws RemoteException {
        System.out.println("Server invoked doSomething()");
    }
    
    public ClientWaiter() throws RemoteException{
        super();
    }

    @Override
    public void readyOrder(Map<String,Object> order) throws RemoteException {
       ControllerGuiMain.UpdateOrder(Integer.parseInt(order.get("id").toString()));
    }
}
