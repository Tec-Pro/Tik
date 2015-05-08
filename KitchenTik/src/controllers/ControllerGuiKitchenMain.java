/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import interfaces.InterfaceOrder;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.LinkedList;
import java.util.Map;
import utils.Config;

/**
 *
 * @author eze
 */
public class ControllerGuiKitchenMain  implements ActionListener {

    private static InterfaceOrder crudOrder;
    private static LinkedList<Map> orderList;
    
    public ControllerGuiKitchenMain() throws NotBoundException, MalformedURLException, RemoteException{
        //crudOrder = (InterfaceOrder) Naming.lookup("//" + Config.ip + "/crudOrderKitchen");
        orderList = new LinkedList<>();
    }
    
    public static void addOrder(int id) throws RemoteException{
        //orderList.add(crudOrder.getOrder(id));
        System.out.println("Pedido: "+id);
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    
}
