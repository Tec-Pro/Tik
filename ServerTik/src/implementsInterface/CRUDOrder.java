/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package implementsInterface;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;
import java.util.Map;
import models.Order;
import models.OrdersFproducts;
import org.javalite.activejdbc.Base;
import utils.Utils;

/**
 *
 * @author agustin
 */
public class CRUDOrder extends UnicastRemoteObject implements interfaces.InterfaceOrder{

    
    
    public CRUDOrder() throws RemoteException {
        super();
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
        Order newOrder = Order.createIt("user_id", userId,"description", description,"closed",false);
        for (Map<String, Object> prod : fproducts) {   
           OrdersFproducts.create("order_id", newOrder.getId(), "fproduct_id", (int)prod.get("fproductId"), "quantity", (float)prod.get("quantity"), "done", (boolean)prod.get("done"), "commited", (boolean)prod.get("commited"), "issued", (boolean)prod.get("issued")).saveIt();
        }
        Base.commitTransaction();
        Server.notifyWaitersOrderReady(newOrder.getInteger("id"));
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
        Server.notifyWaitersOrderReady(orderId);
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
        order.set("closed", true);
        Base.commitTransaction();
        return true;
    }

    @Override
    public List<Map> getOrderProducts(int orderId) throws RemoteException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    
    
}
