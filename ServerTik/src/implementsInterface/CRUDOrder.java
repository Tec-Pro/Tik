/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package implementsInterface;
import java.rmi.RemoteException;
import interfaces.InterfaceOrder;
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
     * @param description
     * @param fproducts
     * @return
     * @throws RemoteException
     */
    @Override
    public Map<String, Object> sendOrder(String description,List<Map<String,Object>> fproducts) throws RemoteException {
        // campos que deberia tener el map: ("fproductId","quantity","done","commited","issued")
        Utils.abrirBase();
        Base.openTransaction();      
        Order newOrder = Order.createIt("description", description,"closed",false);
        for (Map<String, Object> prod : fproducts) {
            OrdersFproducts.create("order_id", newOrder.getId(), "fproduct_id", (int)prod.get("fproductId"), "quantity", (int)prod.get("quantity"), "done", (boolean)prod.get("done"), "commited", (boolean)prod.get("comitted"), "issued", (boolean)prod.get("issued")).saveIt();
        }
        Base.commitTransaction();
        return newOrder.toMap();
    }
    
    /**
     *
     * @param idOrder
     * @param fproducts
     * @return
     * @throws RemoteException
     */
    @Override
    public boolean addProducts(int idOrder,List<Map<String,Object>> fproducts) throws RemoteException {
        // campos que deberia tener el map: ("fproductId","quantity","done","commited","issued")
        Utils.abrirBase();
        Base.openTransaction();
        Order order = Order.findById(idOrder);
        if(order == null){
            System.err.println("order not found!");
            return false;
        }
        if((boolean)order.get("closed")){
           return false; 
        }
        for (Map<String, Object> prod : fproducts) {
           OrdersFproducts.create("order_id", order.getId(), "fproduct_id", (int)prod.get("fproductId"), "quantity", (int)prod.get("quantity"), "done", (boolean)prod.get("done"), "commited", (boolean)prod.get("comitted"), "issued", (boolean)prod.get("issued")).saveIt();
        }
        Base.commitTransaction();
        return true;
    }

    /**
     *
     * @param idOrder
     * @return
     * @throws RemoteException
     */
    @Override
    public boolean closeOrder(int idOrder) throws RemoteException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    


    
}
