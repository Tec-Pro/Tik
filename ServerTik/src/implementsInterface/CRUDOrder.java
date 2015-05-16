/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package implementsInterface;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import models.Order;
import models.OrdersFproducts;
import org.javalite.activejdbc.Base;
import org.javalite.activejdbc.Model;
import utils.Utils;

/**
 *
 * @author agustin
 */
public class CRUDOrder extends UnicastRemoteObject implements interfaces.InterfaceOrder{

    
    
    public CRUDOrder() throws RemoteException {
        super();
    }

    
    private long getOrdersCount(int userId){
        return Order.count("user_id = ?", userId);
    }
    /**
     *
     * @param userId
     * @param description
     * @param fproducts
     * @return
     * @throws RemoteException
     */
    @Override
    public Map<String, Object> sendOrder(int userId, String description,List<Map<String,Object>> fproducts) throws RemoteException {
        // campos que deberia tener el map: ("fproductId","quantity","done","commited","issued")
        Utils.abrirBase();
        Base.openTransaction();      
        Order newOrder = Order.createIt("user_id", userId,"order_number",getOrdersCount(userId)+1, "description", description,"closed",false);
        for (Map<String, Object> prod : fproducts) {   
           OrdersFproducts.create("order_id", newOrder.getId(), "fproduct_id", (int)prod.get("fproductId"), "quantity", (float)prod.get("quantity"), "done", (boolean)prod.get("done"), "commited", (boolean)prod.get("commited"), "issued", (boolean)prod.get("issued")).saveIt();
        }
        Base.commitTransaction();
        Map<String,Object> orderMap = newOrder.toMap();
        //int orderId=newOrder.getInteger("id");
        Server.notifyKitchenNewOrder(orderMap);
        Server.notifyBarNewOrder(orderMap);
        Server.notifyWaitersOrderReady(orderMap);
        return newOrder.toMap();
    }
    
    /**
     *
     * @param orderId
     * @param fproducts
     * @return
     * @throws RemoteException
     */
    @Override
    public boolean addProducts(int orderId,List<Map<String,Object>> fproducts) throws RemoteException {
        // campos que deberia tener el map: ("fproductId","quantity","done","commited","issued")
        Utils.abrirBase();
        Base.openTransaction();
        Order order = Order.findById(orderId);
        if(order == null){
            System.err.println("order not found!");
            return false;
        }
        if((boolean)order.get("closed")){
           return false; 
        }
        for (Map<String, Object> prod : fproducts) {
             OrdersFproducts.create("order_id", orderId, "fproduct_id", (int)prod.get("fproductId"), "quantity", (float)prod.get("quantity"), "done", (boolean)prod.get("done"), "commited", (boolean)prod.get("commited"), "issued", (boolean)prod.get("issued")).saveIt();
        }
        Base.commitTransaction();
        Map<String,Object> orderMap = order.toMap();
        Server.notifyKitchenUpdatedOrder(orderMap);
        Server.notifyBarUpdatedOrder(orderMap);
        Server.notifyWaitersOrderReady(orderMap);
        return true;
    }

    /**
     *
     * @return Todos los pedidos
     * @throws RemoteException
     */
    @Override
    public List<Map> getAllOrders() throws RemoteException {
        Utils.abrirBase();
        return Order.findAll().toMaps();
    }
    
    /**
     *
     * @param userId
     * @return Todos los pedidos que pertenecen a un usuario dado
     * @throws RemoteException
     */
    @Override
    public List<Map> getOrdersByUser(int userId) throws RemoteException {
        Utils.abrirBase();
        return Order.find("user_id = ?", userId).toMaps();
    }
      
    /**
     *
     * @param idOrder
     * @return
     * @throws RemoteException
     */
    @Override
    public boolean closeOrder(int idOrder) throws RemoteException {
        Utils.abrirBase();
        Base.openTransaction();
        Order order = Order.findById(idOrder);      
        order.set("closed", true).saveIt();
        Base.commitTransaction();
        Server.notifyWaitersOrderReady(order.toMap());
        return true;
    }

    
    @Override
    public void commitProducts(int orderId)throws RemoteException{
        Utils.abrirBase();
        Base.openTransaction();
        List<OrdersFproducts> orderProducts = OrdersFproducts.find("order_id = ?", orderId);
        for(OrdersFproducts prod : orderProducts){
            if((boolean)prod.get("done") == true)
                prod.set("commited",true).saveIt();
        }
      
        Base.commitTransaction();
        Server.notifyWaitersOrderReady(getOrder(orderId));
    }
    
    @Override
    public List<Map> getOrderProducts(int orderId) throws RemoteException {
        Utils.abrirBase();
        List<Map> ordersFProducts = OrdersFproducts.find("order_id = ?", orderId).toMaps();
        return ordersFProducts;
    }       

    
    
    @Override
    public Map<String, Object> getOrder(int orderId) throws RemoteException {
        Utils.abrirBase();
        Order order =  Order.findById(orderId);
        return order.toMap();
    }
    
    @Override
    public List<Map> updateOrdersReadyProducts(Integer idOrder, List<Integer> productsList) throws RemoteException {
        Utils.abrirBase();
        Base.openTransaction();
        List<Map> result = new LinkedList<>();
        Iterator<Integer> itr = productsList.iterator();
        while (itr.hasNext()){
            Integer idOrderProduct = itr.next();
            Model product = OrdersFproducts.findById(idOrderProduct);
            product.set("done", true).saveIt();
            result.add(product.toMap());
        }
        Base.commitTransaction();
        Server.notifyWaitersOrderReady(getOrder(idOrder));
        return result;
    }

    @Override
    public List<Map> getActiveOrdersByUser(int userId) throws RemoteException {
        Utils.abrirBase();
        if(userId != -1)
            return Order.find("closed = ? AND user_id = ?", 0,userId).toMaps();
        else
            return Order.find("closed = ?", 0).toMaps();
    }
    
}
