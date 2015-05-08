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
import java.util.Iterator;
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
        crudOrder = (InterfaceOrder) Naming.lookup("//" + Config.ip + "/crudOrder");
        orderList = new LinkedList<>();
    }
    
    public static void addOrder(int id) throws RemoteException{
        Map<String,Object> order = crudOrder.getOrder(id);
        orderList.add(order);
        System.out.println("Pedido: "+order.toString()+" "+crudOrder.getOrderProducts(Integer.parseInt(order.get("id").toString())));
    }
    
    public static void updatedOrder(int id) throws RemoteException{
        Map<String,Object> updatedOrder = crudOrder.getOrder(id);
        System.out.println("Pedido: "+updatedOrder.toString()+" "+crudOrder.getOrderProducts(Integer.parseInt(updatedOrder.get("id").toString())));
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    
}
