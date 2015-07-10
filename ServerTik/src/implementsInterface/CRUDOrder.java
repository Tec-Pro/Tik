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
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import models.Fproduct;
import models.Order;
import models.OrdersFproducts;
import org.javalite.activejdbc.Base;
import org.javalite.activejdbc.LazyList;
import utils.Pair;
import utils.Utils;

/**
 *
 * @author agustin
 */
public class CRUDOrder extends UnicastRemoteObject implements interfaces.InterfaceOrder {

    private Connection conn;
    private String sql = "";

    /**
     *
     * @throws RemoteException
     */
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
        // campos que deberia tener el map: ("fproductId","quantity","done","commited","issued")
        Utils.abrirBase();
        Base.openTransaction();
        final Order newOrder = Order.createIt("user_id", userId, "order_number", getOrdersCount(userId) + 1, "description", description, "closed", false, "persons", persons);
        for (Map<String, Object> prod : fproducts) {
            OrdersFproducts.createIt("order_id", newOrder.getId(), "fproduct_id", (int) prod.get("fproductId"), "quantity", (float) prod.get("quantity"), "done", (boolean) prod.get("done"), "commited", (boolean) prod.get("commited"), "issued", (boolean) prod.get("issued"));
        }
        Base.commitTransaction();
        Thread thread = new Thread() {
            public void run() {
                try {
                    Pair<Map<String, Object>, List<Map>> mapBar = obtainOrdersBar(newOrder.getInteger("id"));
                    Pair<Map<String, Object>, List<Map>> mapKitchen = obtainOrdersKitchen(newOrder.getInteger("id"));
                    if (mapKitchen != null) {
                        Server.notifyKitchenNewOrder(mapKitchen);
                    }
                    if (mapBar != null) {
                        Server.notifyBarNewOrder(mapBar);
                    }
                    Pair<Map<String, Object>, List<Map>> pair = new Pair(getOrder(newOrder.getInteger("id")), getOrderProductsWithName(newOrder.getInteger("id")));
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
    public boolean updateOrder(final int orderId, String description, int persons, List<Map<String, Object>> fproducts) throws RemoteException {
        // campos que deberia tener el map: ("fproductId","quantity","done","commited","issued")
        Utils.abrirBase();
        Base.openTransaction();
        Order order = Order.findById(orderId);
        if (order == null) {
            System.err.println("order not found!");
            return false;
        }
        if ((boolean) order.get("closed")) {// reabro el pedido si esta cerrado
            order.set("closed", 0);
        }
        order.set("description", description);
        order.set("persons", persons);
        order.saveIt();
        for (Map<String, Object> prod : fproducts) {
            OrdersFproducts.create("order_id", orderId, "fproduct_id", (int) prod.get("fproductId"), "quantity", (float) prod.get("quantity"), "done", (boolean) prod.get("done"), "commited", (boolean) prod.get("commited"), "issued", (boolean) prod.get("issued")).saveIt();
        }
        Base.commitTransaction();
        Thread thread = new Thread() {
            public void run() {
                try {
                    Pair<Map<String, Object>, List<Map>> mapBar = obtainOrdersBar(orderId);
                    Pair<Map<String, Object>, List<Map>> mapKitchen = obtainOrdersKitchen(orderId);
                    if (mapKitchen != null) {
                        Server.notifyKitchenUpdatedOrder(mapKitchen);
                    }
                    if (mapBar != null) {
                        Server.notifyBarUpdatedOrder(mapBar);
                    }
                    Pair<Map<String, Object>, List<Map>> pair = new Pair(getOrder(orderId), getOrderProductsWithName(orderId));
                    Server.notifyWaitersOrderReady(pair);
                } catch (RemoteException ex) {
                    Logger.getLogger(CRUDOrder.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        };
        thread.start();
        return true;
    }

    @Override
    public List<Map> getOrderProductsWithName(int orderId) throws RemoteException {
        openBase();
        List<Map> ret = new LinkedList<>();
        try {
            sql = "SELECT fp.name,ofp.quantity,ofp.done,ofp.issued,ofp.commited,ofp.paid "
                    + " FROM tik.orders_fproducts ofp INNER JOIN fproducts fp ON fp.id= ofp.fproduct_id WHERE ofp.order_id = '" + orderId + "';";
            Statement stmt = conn.createStatement();
            java.sql.ResultSet rs = stmt.executeQuery(sql);

            while (rs.next()) {
                Map m = new HashMap();
                m.put("name", rs.getObject("name"));
                m.put("quantity", rs.getObject("quantity"));
                m.put("done", rs.getObject("done"));
                m.put("commited", rs.getObject("commited"));
                m.put("issued", rs.getObject("issued"));
                m.put("paid", rs.getObject("paid"));
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
            sql = "SELECT orders.id,order_number,user_id,description,closed,persons,name FROM orders"
                    + " INNER JOIN users WHERE orders.user_id = users.id;";
            java.sql.ResultSet rs = stmt.executeQuery(sql);

            while (rs.next() != false) {
                m = new HashMap();
                m.put("id", rs.getObject("id"));
                m.put("order_number", rs.getObject("order_number"));
                m.put("user_id", rs.getObject("user_id"));
                m.put("description", rs.getObject("description"));
                m.put("closed", rs.getObject("closed"));
                m.put("persons", rs.getObject("persons"));
                m.put("user_name", rs.getObject("name"));
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

            while (rs.next() != false) {
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
            sql = "UPDATE orders_fproducts SET paid = 1 WHERE order_id = '" + idOrder + "';";
            stmt = conn.createStatement();
            stmt.executeUpdate(sql);
            stmt.close();
            sql = "UPDATE orders SET paid_exceptions= exceptions+paid_exceptions,  exceptions = 0 WHERE id = '" + idOrder + "';";
            stmt = conn.createStatement();
            stmt.executeUpdate(sql);
            stmt.close();
        } catch (SQLException ex) {
            Logger.getLogger(CRUDOrder.class.getName()).log(Level.SEVERE, null, ex);
        }

        List<Map> ret = getOrderProductsWithName(idOrder);
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
            //Notifico al bar que los productos han sido entregados.
            Map ord = getOrder(orderId);
            Server.notifyBarKitchenOrderCommited((int) ord.get("order_number"), (String) ord.get("user_name"));
        } catch (SQLException ex) {
            Logger.getLogger(CRUDOrder.class.getName()).log(Level.SEVERE, null, ex);
        }

        List<Map> ret = getOrderProductsWithName(orderId);
        Map<String, Object> ord = getOrder(orderId);
        Server.notifyWaitersOrderReady(new Pair(ord, ret));
    }

    /**
     * Función que obtiene un producto de una orden.
     *
     * @param orderId id de la orden.
     * @param order_fproductId id del producto dentro de la orden.
     * @return Map representando el producto.
     */
    public Map getProductFromOrder(int orderId, int order_fproductId) {
        openBase();
        HashMap ret = new HashMap();
        try {
            sql = "SELECT fproduct_id FROM orders_fproducts WHERE order_id = '" + orderId + "' AND id ='" + order_fproductId + "';";
            Statement stmt = conn.createStatement();
            java.sql.ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                ret.put("fproduct_id", rs.getObject("fproduct_id"));
            }
            rs.close();
            stmt.close();
        } catch (SQLException ex) {
            Logger.getLogger(CRUDOrder.class.getName()).log(Level.SEVERE, null, ex);
        }
        return ret;
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
                m.put("paid", rs.getObject("paid"));
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
            sql = "SELECT orders.id,order_number,user_id,description,closed,persons,name FROM orders"
                    + " INNER JOIN users WHERE orders.user_id = users.id AND orders.id = '" + orderId + "';";
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
                m.put("user_name", rs.getObject("name"));
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
        CRUDFproduct fproduct = new CRUDFproduct();
        int id;
        while (itr.hasNext()) {
            id = itr.next();
            sql = "UPDATE orders_fproducts SET done='1' WHERE id= '" + id + "';";
            try {
                Statement stmt = conn.createStatement();
                stmt.executeUpdate(sql);
                //Me fijo si el producto pertenece a la cocina, si pertenece al bar no debería avisar al bar de que está listo.
                if (fproduct.belongsTo((int) getProductFromOrder(idOrder, id).get("fproduct_id")) == 1) {

                    Map ord = getOrder(idOrder);

                    Server.notifyBarKitchenOrderReady((int) ord.get("order_number"), (String) ord.get("user_name"));

                }
                stmt.close();
            } catch (SQLException ex) {
                Logger.getLogger(CRUDOrder.class.getName()).log(Level.SEVERE, null, ex);
            }

        }

        List<Map> ret = getOrderProductsWithName(idOrder);
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
                sql = "select * from orders where closed = '0' AND user_id = '" + userId + "';";
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
            if (conn == null || conn.isClosed()) {
                conn = DriverManager.getConnection("jdbc:mysql://localhost/tik", "root", "root");
            }
        } catch (SQLException ex) {
            Logger.getLogger(CRUDOrder.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    private Pair<Map<String, Object>, List<Map>> obtainOrdersKitchen(Integer orderId) throws RemoteException {
        final Map<String, Object> orderMap = getOrder(orderId);
        openBase();
        List<Map> result = new LinkedList<>();
        try {
            sql = "SELECT orders_fproducts.id,order_id,fproduct_id,quantity,done,commited,issued,created_at,updated_at,name"
                    + " FROM orders_fproducts INNER JOIN fproducts WHERE orders_fproducts.fproduct_id = fproducts.id AND"
                    + " fproducts.belong = 'Cocina' AND orders_fproducts.order_id = '" + orderId + "' AND orders_fproducts.done = 0;";
            Statement stmt = conn.createStatement();
            java.sql.ResultSet rs = stmt.executeQuery(sql);

            while (rs.next()) {
                Map m = new HashMap();
                m.put("id", rs.getObject("id"));
                m.put("order_id", rs.getObject("order_id"));
                m.put("fproduct_id", rs.getObject("fproduct_id"));
                m.put("quantity", rs.getObject("quantity"));
                m.put("done", rs.getObject("done"));
                m.put("created_at", rs.getObject("created_at"));
                m.put("updated_at", rs.getObject("updated_at"));
                m.put("name", rs.getObject("name"));
                result.add(m);
            }
            rs.close();
            stmt.close();
        } catch (SQLException ex) {
            Logger.getLogger(CRUDOrder.class.getName()).log(Level.SEVERE, null, ex);
        }
        if (result.isEmpty()) {
            return null;
        } else {
            final Pair<Map<String, Object>, List<Map>> pair = new Pair(orderMap, result);
            return pair;
        }
    }

    private Pair<Map<String, Object>, List<Map>> obtainOrdersBar(Integer orderId) throws RemoteException {
        final Map<String, Object> orderMap = getOrder(orderId);
        openBase();
        List<Map> result = new LinkedList<>();
        try {
            sql = "SELECT orders_fproducts.id,order_id,fproduct_id,quantity,done,commited,issued,created_at,updated_at,fproducts.name"
                    + " FROM orders_fproducts INNER JOIN fproducts WHERE orders_fproducts.fproduct_id = fproducts.id AND"
                    + " fproducts.belong = 'Bar' AND orders_fproducts.order_id = '" + orderId + "' AND orders_fproducts.done = 0;";
            Statement stmt = conn.createStatement();
            java.sql.ResultSet rs = stmt.executeQuery(sql);

            while (rs.next()) {
                Map m = new HashMap();
                m.put("id", rs.getObject("id"));
                m.put("order_id", rs.getObject("order_id"));
                m.put("fproduct_id", rs.getObject("fproduct_id"));
                m.put("quantity", rs.getObject("quantity"));
                m.put("done", rs.getObject("done"));
                m.put("created_at", rs.getObject("created_at"));
                m.put("updated_at", rs.getObject("updated_at"));
                m.put("name", rs.getObject("name"));
                result.add(m);
            }
            rs.close();
            stmt.close();
        } catch (SQLException ex) {
            Logger.getLogger(CRUDOrder.class.getName()).log(Level.SEVERE, null, ex);
        }
        if (result.isEmpty()) {
            return null;
        } else {
            final Pair<Map<String, Object>, List<Map>> pair = new Pair(orderMap, result);
            return pair;
        }
    }

    @Override
    public Pair<List<Map>, List<Map>> getAllOrdersForKitchen() throws RemoteException {
        openBase();
        Map m = new HashMap();
        LinkedList<Map> listOrdersFproducts = new LinkedList<>();
        try {
            Statement stmt = conn.createStatement();
            String sqlOrdersFproducts = "SELECT orders_fproducts.id,order_id,fproduct_id,quantity,done,commited,issued,created_at,updated_at,name"
                    + " FROM orders_fproducts INNER JOIN fproducts WHERE orders_fproducts.fproduct_id = fproducts.id AND"
                    + " fproducts.belong = 'Cocina' AND orders_fproducts.done = 0;";

            java.sql.ResultSet rs = stmt.executeQuery(sqlOrdersFproducts);

            while (rs.next() != false) {
                m = new HashMap();
                m.put("id", rs.getObject("id"));
                m.put("order_id", rs.getObject("order_id"));
                m.put("fproduct_id", rs.getObject("fproduct_id"));
                m.put("quantity", rs.getObject("quantity"));
                m.put("done", rs.getObject("done"));
                m.put("created_at", rs.getObject("created_at"));
                m.put("updated_at", rs.getObject("updated_at"));
                m.put("name", rs.getObject("name"));
                listOrdersFproducts.add(m);
            }
            rs.close();
            stmt.close();
        } catch (SQLException ex) {
            Logger.getLogger(CRUDOrder.class.getName()).log(Level.SEVERE, null, ex);
        }
        final Pair<List<Map>, List<Map>> pair = new Pair(getAllOrders(), listOrdersFproducts);
        return pair;
    }

    @Override
    public Pair<List<Map>, List<Map>> getAllOrdersForBar() throws RemoteException {
        openBase();
        Map m = new HashMap();
        LinkedList<Map> listOrdersFproducts = new LinkedList<>();
        try {
            Statement stmt = conn.createStatement();
            String sqlOrdersFproducts = "SELECT orders_fproducts.id,order_id,fproduct_id,quantity,done,commited,issued,created_at,updated_at,name"
                    + " FROM orders_fproducts INNER JOIN fproducts WHERE orders_fproducts.fproduct_id = fproducts.id AND"
                    + " fproducts.belong = 'Bar' AND orders_fproducts.done = 0;";

            java.sql.ResultSet rs = stmt.executeQuery(sqlOrdersFproducts);

            while (rs.next() != false) {
                m = new HashMap();
                m.put("id", rs.getObject("id"));
                m.put("order_id", rs.getObject("order_id"));
                m.put("fproduct_id", rs.getObject("fproduct_id"));
                m.put("quantity", rs.getObject("quantity"));
                m.put("done", rs.getObject("done"));
                m.put("created_at", rs.getObject("created_at"));
                m.put("updated_at", rs.getObject("updated_at"));
                m.put("name", rs.getObject("name"));
                listOrdersFproducts.add(m);
            }
            rs.close();
            stmt.close();
        } catch (SQLException ex) {
            Logger.getLogger(CRUDOrder.class.getName()).log(Level.SEVERE, null, ex);
        }
        final Pair<List<Map>, List<Map>> pair = new Pair(getAllOrders(), listOrdersFproducts);
        return pair;
    }

    @Override
    public float totalEarn() throws RemoteException {
        Utils.abrirBase();
        LazyList<Order> lo = Order.findAll();
        float total = 0;
        for (Order o : lo) {
            LazyList<OrdersFproducts> lof = OrdersFproducts.where("order_id = ?", o.getId());
            for (OrdersFproducts of : lof) {
                float quantity = of.getFloat("quantity");
                float price = Fproduct.findById(of.getString("fproduct_id")).getFloat("sell_price");
                total += quantity * price;
            }
        }
        return total;
    }

    @Override
    public float EarnByUser(int userId) throws RemoteException {
        Utils.abrirBase();
        LazyList<Order> lo = Order.where("user_id = ?", userId);
        float total = 0;
        for (Order o : lo) {
            LazyList<OrdersFproducts> lof = OrdersFproducts.where("order_id = ?", o.getId());
            for (OrdersFproducts of : lof) {
                float quantity = of.getFloat("quantity");
                float price = Fproduct.findById(of.getString("fproduct_id")).getFloat("sell_price");
                total += quantity * price;
            }
        }
        return total;
    }

    @Override
    public boolean addException(int orderId, float amount) throws RemoteException {
        Utils.abrirBase();
        Order ord = Order.findById(orderId);
        Base.openTransaction();
        ord.set("exceptions", ord.getFloat("exceptions") + amount);
        boolean ret = ord.saveIt();
        return ret;
    }

    @Override
    public float getException(int orderId) throws RemoteException {
        try {
            openBase();
            sql = "SELECT exceptions FROM orders WHERE id = '" + orderId + "';";
            Statement stmt = conn.createStatement();
            java.sql.ResultSet rs = stmt.executeQuery(sql);
            rs.next();
            float ret = rs.getFloat("exceptions");
            rs.close();
            stmt.close();
            return ret;
        } catch (SQLException ex) {
            Logger.getLogger(CRUDOrder.class.getName()).log(Level.SEVERE, null, ex);
        }
        return 0;
    }

    @Override
    public float getExceptions(int userId) throws RemoteException {
        Utils.abrirBase();
        LazyList<Order> lo = Order.where("user_id = ?", userId);
        float total = 0;
        for (Order o : lo) {
            total = total + o.getFloat("paid_exceptions") + o.getFloat("exceptions");
        }
        return total;
    }

    @Override
    public float getPaidException(int orderId) throws RemoteException {
        try {
            openBase();
            sql = "SELECT paid_exceptions FROM orders WHERE id = '" + orderId + "';";
            Statement stmt = conn.createStatement();
            java.sql.ResultSet rs = stmt.executeQuery(sql);
            rs.next();
            float ret = rs.getFloat("paid_exceptions");
            rs.close();
            stmt.close();
            return ret;
        } catch (SQLException ex) {
            Logger.getLogger(CRUDOrder.class.getName()).log(Level.SEVERE, null, ex);
        }
        return 0;
    }

    @Override
    public void deleteAll() throws RemoteException {
        Utils.abrirBase();
        Base.openTransaction();
        OrdersFproducts.deleteAll();
        Order.deleteAll();
        Base.commitTransaction();
    }

    @Override
    public float getAllExceptions() throws RemoteException {
        Utils.abrirBase();
        LazyList<Order> lo = Order.findAll();
        float total = 0;
        for (Order o : lo) {
            total = total + o.getFloat("paid_exceptions") + o.getFloat("exceptions");
        }
        return total;
    }

    public List<Map> getDataPrinterOrd(int id) throws RemoteException {
        try {
            openBase();
            Map m = new HashMap();
            LinkedList<Map> ret = new LinkedList<>();
            sql = "SELECT ord.quantity, pr.name, pr.sell_price, orden.paid_exceptions FROM tik.orders_fproducts ord INNER JOIN tik.fproducts pr ON ord.fproduct_id = pr.id, tik.orders as orden WHERE ord.order_id = '" + id + "' and ord.paid =1 AND orden.id= ord.order_id;";
            Statement stmt = conn.createStatement();
            java.sql.ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                m = new HashMap();
                m.put("quantity", rs.getFloat("quantity"));
                m.put("name", rs.getString("name"));
                m.put("sell_price", rs.getFloat("sell_price"));
                m.put("paid_exceptions", rs.getFloat("paid_exceptions"));
                ret.add(m);
            }
            rs.close();
            stmt.close();
            return ret;
        } catch (SQLException ex) {
            Logger.getLogger(CRUDOrder.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
}
