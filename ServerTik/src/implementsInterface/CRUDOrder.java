/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package implementsInterface;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import models.Order;
import models.OrdersFproducts;
import models.Fproduct;
import org.javalite.activejdbc.Base;
import utils.Pair;
import utils.Utils;

/**
 *
 * @author agustin
 */
public class CRUDOrder extends UnicastRemoteObject implements interfaces.InterfaceOrder {

    private Connection conn;
    private String sql = "";

    public CRUDOrder() throws RemoteException {
        super();
        openBase();

    }

    private long getOrdersCount(int userId) {
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
    public Map<String, Object> sendOrder(int userId, String description, int persons, List<Map<String, Object>> fproducts) throws RemoteException {
        // campos que deberia tener el map: ("fproductId","name","quantity","done","commited","issued")
        Utils.abrirBase();
        Base.openTransaction();
        Order newOrder = Order.createIt("user_id", userId, "order_number", getOrdersCount(userId) + 1, "description", description, "closed", false, "persons", persons);
        
        List<Map> listBar = new LinkedList();
        List<Map> listCook = new LinkedList();
        for (Map<String, Object> prod : fproducts) {
            OrdersFproducts.create("order_id", newOrder.getId(), "fproduct_id", (int) prod.get("fproductId"), "quantity", (float) prod.get("quantity"), "done", (boolean) prod.get("done"), "commited", (boolean) prod.get("commited"), "issued", (boolean) prod.get("issued")).saveIt();
            
            Map<String, Object> addProd = new HashMap(); //Create a map with the product
            addProd.put("fproduct_id", (int) prod.get("fproductId"));
            addProd.put("name", prod.get("name"));
            addProd.put("quantity", (float) prod.get("quantity"));
            addProd.put("done", (boolean) prod.get("done"));
            addProd.put("commited", (boolean) prod.get("commited"));
            addProd.put("issued", (boolean) prod.get("issued"));
            
            Fproduct finalProd = Fproduct.findById((int) prod.get("fproductId")); //get the final product to verify where it belongs
            if (finalProd.get("belong") == "Cocina") {
                listCook.add(addProd);
            } else { // belongs to bar
                listBar.add(addProd);
            }
        }
        Base.commitTransaction();

        final Map<String, Object> orderMap = getOrder(newOrder.getInteger("id"));
        final Pair<Map<String, Object>, List<Map>> pair = new Pair(orderMap, getOrderProducts(newOrder.getInteger("id")));
        final Pair<Map<String, Object>, List<Map>> pairCook = new Pair(orderMap, listCook);
        final Pair<Map<String, Object>, List<Map>> pairBar = new Pair(orderMap, listBar);
        Thread thread = new Thread() {
            public void run() {
                try {
                    Server.notifyKitchenNewOrder(pairCook);
                    Server.notifyBarNewOrder(pairBar);
                    Server.notifyWaitersOrderReady(pair);
                } catch (RemoteException ex) {
                    Logger.getLogger(CRUDOrder.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        };
        thread.start();
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
    public boolean updateOrder(int orderId, String description, int persons, List<Map<String, Object>> fproducts) throws RemoteException {
        // campos que deberia tener el map: ("fproductId","name","quantity","done","commited","issued")
        Utils.abrirBase();
        Base.openTransaction();
        Order order = Order.findById(orderId);
        if (order == null) {
            System.err.println("order not found!");
            return false;
        }
        if ((boolean) order.get("closed")) {
            return false;
        }
        order.set("description", description);
        order.set("persons", persons);
        order.saveIt();
        
        List<Map> listBar = new LinkedList();
        List<Map> listCook = new LinkedList();
        
        for (Map<String, Object> prod : fproducts) {
            OrdersFproducts.create("order_id", orderId, "fproduct_id", (int) prod.get("fproductId"), "quantity", (float) prod.get("quantity"), "done", (boolean) prod.get("done"), "commited", (boolean) prod.get("commited"), "issued", (boolean) prod.get("issued")).saveIt();
            if (!((boolean) prod.get("done"))) { // If not done

                Map<String, Object> addProd = new HashMap(); //Create a map with the product
                addProd.put("fproduct_id", (int) prod.get("fproductId"));
                addProd.put("name", prod.get("name"));
                addProd.put("quantity", (float) prod.get("quantity"));
                addProd.put("done", (boolean) prod.get("done"));
                addProd.put("commited", (boolean) prod.get("commited"));
                addProd.put("issued", (boolean) prod.get("issued"));
                
                Fproduct finalProd = Fproduct.findById((int) prod.get("fproductId")); //get the final product to verify where it belongs
                if (finalProd.get("belong") == "Cocina") {
                    listCook.add(addProd);
                } else { // belongs to bar
                    listBar.add(addProd);
                }
            }
        }
        Base.commitTransaction();
        Map<String, Object> orderMap = order.toMap();
        Pair<Map<String, Object>, List<Map>> pair = new Pair<>(orderMap, order.getAll(OrdersFproducts.class).toMaps());
        Server.notifyWaitersOrderReady(pair);

        if (!(listCook.isEmpty())) { //if there are products to update for the Kitchen
            pair = new Pair<>(orderMap, listCook);
            Server.notifyKitchenUpdatedOrder(pair);
        }
        if (!(listBar.isEmpty())) { //if there are products to update for the Bar
            pair = new Pair<>(orderMap, listBar);
            Server.notifyBarUpdatedOrder(pair);
        }
        return true;
    }

    /**
     *
     * @return Todos los pedidos
     * @throws RemoteException
     */
    @Override
    public List<Map> getAllOrders() throws RemoteException {
        openBase();
        Map m = new HashMap();
        LinkedList<Map> ret = new LinkedList<>();
        try {
            Statement stmt = conn.createStatement();
            sql = "select * from orders;";
            java.sql.ResultSet rs = stmt.executeQuery(sql);

            while (rs.next() != false) {
                m = new HashMap();
                m.put("id", rs.getObject("id"));
                m.put("order_number", rs.getObject("order_number"));
                m.put("user_id", rs.getObject("user_id"));
                m.put("description", rs.getObject("description"));
                m.put("closed", rs.getObject("closed"));
                ret.add(m);
            }
            rs.close();
            stmt.close();
        } catch (SQLException ex) {
            Logger.getLogger(CRUDOrder.class.getName()).log(Level.SEVERE, null, ex);
        }
        return ret;
    }

    /**
     *
     * @param userId
     * @return Todos los pedidos que pertenecen a un usuario dado
     * @throws RemoteException
     */
    @Override
    public List<Map> getOrdersByUser(int userId) throws RemoteException {
        openBase();
        Map m = new HashMap();
        LinkedList<Map> ret = new LinkedList<>();
        try {
            Statement stmt = conn.createStatement();
            sql = "select * from orders where user_id = '" + userId + "';";
            java.sql.ResultSet rs = stmt.executeQuery(sql);

            if (rs.next() != false) {
                m = new HashMap();
                m.put("id", rs.getObject("id"));
                m.put("order_number", rs.getObject("order_number"));
                m.put("user_id", rs.getObject("user_id"));
                m.put("description", rs.getObject("description"));
                m.put("closed", rs.getObject("closed"));
                m.put("persons", rs.getObject("persons"));
                ret.add(m);
            }
            rs.close();
            stmt.close();
        } catch (SQLException ex) {
            Logger.getLogger(CRUDOrder.class.getName()).log(Level.SEVERE, null, ex);
        }
        return ret;
    }

    /**
     *
     * @param idOrder
     * @return
     * @throws RemoteException
     */
    @Override
    public boolean closeOrder(int idOrder) throws RemoteException {
        openBase();
        sql = "UPDATE orders SET closed='1' WHERE id= '" + idOrder + "' ;";
        try {
            Statement stmt = conn.createStatement();
            stmt.executeUpdate(sql);
            stmt.close();
        } catch (SQLException ex) {
            Logger.getLogger(CRUDOrder.class.getName()).log(Level.SEVERE, null, ex);
        }

        List<Map> ret = getOrderProducts(idOrder);
        Map<String, Object> ord = getOrder(idOrder);
        Server.notifyWaitersOrderReady(new Pair(ord, ret));
        return true;
    }

    @Override
    public void commitProducts(int orderId) throws RemoteException {
        openBase();
        sql = "UPDATE orders_fproducts SET commited='1' WHERE order_id= '" + orderId + "' AND done = '1';";
        try {
            Statement stmt = conn.createStatement();
            stmt.executeUpdate(sql);
            stmt.close();
        } catch (SQLException ex) {
            Logger.getLogger(CRUDOrder.class.getName()).log(Level.SEVERE, null, ex);
        }

        List<Map> ret = getOrderProducts(orderId);
        Map<String, Object> ord = getOrder(orderId);
        Server.notifyWaitersOrderReady(new Pair(ord, ret));
    }

    @Override
    public List<Map> getOrderProducts(int orderId) throws RemoteException {
        openBase();
        List<Map> ret = new LinkedList<>();
        try {
            sql = "SELECT * FROM orders_fproducts WHERE order_id = '" + orderId + "';";
            Statement stmt = conn.createStatement();
            java.sql.ResultSet rs = stmt.executeQuery(sql);

            while (rs.next()) {
                Map m = new HashMap();
                m.put("id", rs.getObject("id"));
                m.put("order_id", rs.getObject("order_id"));
                m.put("fproduct_id", rs.getObject("fproduct_id"));
                m.put("quantity", rs.getObject("quantity"));
                m.put("done", rs.getObject("done"));
                m.put("commited", rs.getObject("commited"));
                m.put("issued", rs.getObject("issued"));
                m.put("created_at", rs.getObject("created_at"));
                m.put("updated_at", rs.getObject("updated_at"));
                ret.add(m);
            }
            rs.close();
            stmt.close();
        } catch (SQLException ex) {
            Logger.getLogger(CRUDOrder.class.getName()).log(Level.SEVERE, null, ex);
        }
        return ret;
    }

    @Override
    public Map<String, Object> getOrder(int orderId) throws RemoteException {
        openBase();
        Map m = new HashMap();
        try {
            sql = "select * from orders where id = '" + orderId + "';";
            Statement stmt = conn.createStatement();
            java.sql.ResultSet rs;
            rs = stmt.executeQuery(sql);

            if (rs.next() != false) {
                m = new HashMap();
                m.put("id", rs.getObject("id"));
                m.put("order_number", rs.getObject("order_number"));
                m.put("user_id", rs.getObject("user_id"));
                m.put("description", rs.getObject("description"));
                m.put("closed", rs.getObject("closed"));
                m.put("persons", rs.getObject("persons"));
            }
            rs.close();
            stmt.close();
        } catch (SQLException ex) {
            Logger.getLogger(CRUDOrder.class.getName()).log(Level.SEVERE, null, ex);
        }
        return m;

    }

    @Override
    public List<Map> updateOrdersReadyProducts(Integer idOrder, List<Integer> productsList) throws RemoteException {
        openBase();
        Iterator<Integer> itr = productsList.iterator();
        while (itr.hasNext()) {
            sql = "UPDATE orders_fproducts SET done='1' WHERE id= '" + itr.next() + "';";
            try {
                Statement stmt = conn.createStatement();
                stmt.executeUpdate(sql);
                stmt.close();
            } catch (SQLException ex) {
                Logger.getLogger(CRUDOrder.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        List<Map> ret = getOrderProducts(idOrder);
        Map<String, Object> ord = getOrder(idOrder);
        Server.notifyWaitersOrderReady(new Pair(ord, ret));
        return ret;

    }

    @Override
    public List<Map> getActiveOrdersByUser(int userId) throws RemoteException {
        openBase();
        List<Map> ret = new LinkedList<>();
        try {
            if (userId != -1) {
                sql = "select * from orders where closed =0 AND user_id = '" + userId + "';";
            } else {
                sql = "select * from orders where closed = 0 ;";
            }
            Statement stmt = conn.createStatement();
            java.sql.ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                Map m = new HashMap();
                m.put("id", rs.getObject("id"));
                m.put("order_number", rs.getObject("order_number"));
                m.put("user_id", rs.getObject("user_id"));
                m.put("description", rs.getObject("description"));
                m.put("closed", rs.getObject("closed"));
                m.put("persons", rs.getObject("persons"));
                ret.add(m);
            }
            rs.close();
            stmt.close();
        } catch (SQLException ex) {
            Logger.getLogger(CRUDOrder.class.getName()).log(Level.SEVERE, null, ex);
        }
        return ret;
    }

    private void openBase() {
        try {
            try {
                Class.forName("com.mysql.jdbc.Driver");
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(CRUDOrder.class.getName()).log(Level.SEVERE, null, ex);
            }
            if (conn ==null || conn.isClosed())
                conn = DriverManager.getConnection("jdbc:mysql://localhost/tik", "root", "root");
        } catch (SQLException ex) {
            Logger.getLogger(CRUDOrder.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
}
