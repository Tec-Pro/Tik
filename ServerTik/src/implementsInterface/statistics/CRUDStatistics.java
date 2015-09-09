/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package implementsInterface.statistics;

import implementsInterface.CRUDFproduct;
import implementsInterface.CRUDTurn;
import interfaces.statistics.InterfaceStatistics;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import models.statistics.Productstatistic;
import models.statistics.Salesstatistic;
import org.javalite.activejdbc.Base;
import utils.Utils;

/**
 *
 * @author eze
 */
public class CRUDStatistics extends UnicastRemoteObject implements InterfaceStatistics {

    private Connection conn;
    private final CRUDFproduct crudFProduct;

    /**
     *
     * @throws RemoteException
     */
    public CRUDStatistics() throws RemoteException {
        super();
        openBase();
        crudFProduct = new CRUDFproduct();
    }

    private void openBase() {
        try {
            try {
                Class.forName("com.mysql.jdbc.Driver");
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(CRUDStatistics.class.getName()).log(Level.SEVERE, null, ex);
            }
            if (conn == null || conn.isClosed()) {
                conn = DriverManager.getConnection("jdbc:mysql://localhost/tik", "root", "root");
            }
        } catch (SQLException ex) {
            Logger.getLogger(CRUDStatistics.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    /**
     * Retorna una lista de productos finales con detalles agregados
     *
     * @return List<Map<String, Object>> con: Nombre(name) de fproduct, Precio
     * de Venta(sell_price), Subcategoría(subcategory), Categoría(category),
     * Precio de elaboracion(elaboration_price)
     * @throws RemoteException
     */
    @Override
    public List<Map<String, Object>> getProductList() throws RemoteException {
        openBase();
        String sql1, sql2;
        List<Map<String, Object>> ret = new LinkedList<>();
        try {
            //saco todos los productos finales junto a sus categorias
            sql1 = "SELECT ALL fp.id AS fp_id, fp.name, fp.sell_price, sub.name AS subcategory,sub.category_id FROM fproducts fp INNER JOIN "
                    + "subcategories sub WHERE fp.subcategory_id = sub.id";
            //saco las categorias de las subcategorias y productos finales calculados anteriormente
            sql2 = "SELECT ALL sql1.fp_id, sql1.name, sql1.sell_price, cat.name AS category, sql1.subcategory FROM (" + sql1 + ") sql1 INNER JOIN categories cat WHERE sql1.category_id = cat.id;"
                    + "";
            Statement stmt = conn.createStatement();
            java.sql.ResultSet rs = stmt.executeQuery(sql2);

            while (rs.next()) {
                Map m = new HashMap();
                m.put("name", rs.getObject("name"));
                m.put("sell_price", rs.getObject("sell_price"));
                m.put("subcategory", rs.getObject("subcategory"));
                m.put("category", rs.getObject("category"));
                float productionPrice = crudFProduct.calculateProductionPrice(Integer.parseInt(rs.getObject("fp_id").toString()));
                float calculateProductionPrice = productionPrice;
                m.put("elaboration_price", calculateProductionPrice);
                ret.add(m);
            }
            rs.close();
            stmt.close();
        } catch (SQLException ex) {
            Logger.getLogger(CRUDStatistics.class.getName()).log(Level.SEVERE, null, ex);
        }
        return ret;

    }

    /**
     * Crea las estadisticas de ventas de un turno, en la base de datos
     *
     * @param waiterName nombre del mozo
     * @param userId id del mozo
     * @param saleAmount monto total de las ventas del mozo
     * @param tables cantidad de mesas atendidas por el mozo
     * @param customers cantidad de clientes atendidos por el mozo
     * @param products cantidad de productos vendidos por el mozo
     * @param avgTables promedio de mesas atendidas por el mozo
     * @param avgCustomers promedio
     * @param avgProducts promedio
     * @param discounts descuentos realizados
     * @param exceptions excepciones
     * @param turn turno del dia correspondiente a este estadistico
     * @param day fecha
     * @return
     * @throws RemoteException
     */
    @Override
    public Map<String, Object> saveSalesStatistics(String waiterName, int userId, float saleAmount, int tables,
            int customers, int products, float avgTables, float avgCustomers, float avgProducts,
            float discounts, float exceptions, String turn, java.sql.Date day) throws RemoteException {
        Utils.abrirBase();
        Base.openTransaction();
        Salesstatistic ret = Salesstatistic.createIt("waiter_name", waiterName, "user_id", userId, "sale_amount",
                String.valueOf(saleAmount), "tables", tables, "customers", customers, "products", products, "average_tables", avgTables,
                "average_customers", avgCustomers, "average_products", avgProducts, "discounts", discounts,
                "exceptions", exceptions, "turn", turn, "day", day);
        Base.commitTransaction();
        return ret.toMap();
    }

    /**
     * Retorna una lista de estadisticas de ventas de todos los productos, en
     * todos los turnos
     *
     * @return
     * @throws java.rmi.RemoteException
     */
    @Override
    public List<Map> getAllProductStatistics() throws RemoteException {
        Utils.abrirBase();
        List<Map> ret = Productstatistic.findAll().toMaps();
        return ret;
    }

    /**
     * Calcula automaticamente y Crea las estadisticas de ventas de productos
     * del turno actual, en la base de datos
     *
     * @return
     * @throws RemoteException
     */
    @Override
    public List<Map> saveStatisticsCurrentProductShift() throws RemoteException {
        openBase();
        List<Map> ret = new LinkedList<>();
        try {
            String sql = "SELECT SUM(ofp.quantity) AS quantity, ofp.fproduct_id AS fproduct_id, fp.name AS name, ofp.created_at AS day"
                    + "  FROM orders_fproducts ofp INNER JOIN fproducts fp ON fp.id= ofp.fproduct_id GROUP BY fproduct_id";
            try (Statement stmt = conn.createStatement();
                    java.sql.ResultSet rs = stmt.executeQuery(sql)) {
                while (rs.next()) {
                    Map m = new HashMap();
                    m.put("id", rs.getObject("fproduct_id"));
                    m.put("name", rs.getObject("name"));
                    m.put("quantity", rs.getObject("quantity"));
                    m.put("turn", "turn");
                    m.put("day", rs.getObject("day"));
                    ret.add(m);
                    Statement stmtInsert = conn.createStatement();
                    stmtInsert.executeUpdate("INSERT INTO productstatistics (fproduct_id, name, quantity, turn, day) "
                            + "VALUES ('" + rs.getObject("fproduct_id").toString() + "' , '" + rs.getObject("name").toString() + "' , '" + rs.getObject("quantity").toString() + "' , '" + (new CRUDTurn()).getTurn() + "' , '" + rs.getObject("day").toString() + "' )");
                }
            }

        } catch (SQLException ex) {
            Logger.getLogger(CRUDStatistics.class.getName()).log(Level.SEVERE, null, ex);
        }
        return ret;
    }

    @Override
    public List<Map> findProductStatisticsBetweenDays(java.sql.Date since, java.sql.Date until) throws RemoteException {
        openBase();
        List<Map> ret = new LinkedList<>();
        try {
            String sql = "SELECT DISTINCT id, name, SUM(quantity) AS quantity, turn, day  FROM productstatistics WHERE day >= '" + since.toString() + "' AND day <= '" + until.toString() + "' GROUP BY day, fproduct_id";
            try (Statement stmt = conn.createStatement();
                    java.sql.ResultSet rs = stmt.executeQuery(sql)) {
                while (rs.next()) {
                    Map m = new HashMap();
                    m.put("id", rs.getObject("id"));
                    m.put("name", rs.getObject("name"));
                    m.put("quantity", rs.getObject("quantity"));
                    m.put("turn", "M y T");
                    m.put("day", rs.getObject("day"));
                    ret.add(m);
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(CRUDStatistics.class.getName()).log(Level.SEVERE, null, ex);
        }
        return ret;
    }

    @Override
    public List<Map> findProductStatisticsBetweenMonths(java.sql.Date since, java.sql.Date until) throws RemoteException {
        openBase();
        List<Map> ret = new LinkedList<>();
        try {
            String sql = "SELECT DISTINCT id, name, SUM(quantity) AS quantity, turn, day  FROM productstatistics "
                    + "WHERE day >= '" + since.toString() + "' and day <= '" + until.toString() + "' "
                    + "GROUP BY fproduct_id, year(day), month(day)";
            try (Statement stmt = conn.createStatement();
                    java.sql.ResultSet rs = stmt.executeQuery(sql)) {
                while (rs.next()) {
                    Map m = new HashMap();
                    m.put("id", rs.getObject("id"));
                    m.put("name", rs.getObject("name"));
                    m.put("quantity", rs.getObject("quantity"));
                    m.put("turn", "M y T");
                    m.put("day", rs.getObject("day"));
                    ret.add(m);
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(CRUDStatistics.class.getName()).log(Level.SEVERE, null, ex);
        }
        return ret;
    }

    @Override
    public List<Map> findProductStatisticsBetweenYears(java.sql.Date since, java.sql.Date until) throws RemoteException {
        openBase();
        List<Map> ret = new LinkedList<>();
        try {
            String sql = "SELECT DISTINCT id, name, SUM(quantity) AS quantity, turn, day  FROM productstatistics "
                    + "WHERE day >= '" + since.toString() + "' and day <= '" + until.toString() + "' "
                    + "GROUP BY fproduct_id, year(day)";
            try (Statement stmt = conn.createStatement();
                    java.sql.ResultSet rs = stmt.executeQuery(sql)) {
                while (rs.next()) {
                    Map m = new HashMap();
                    m.put("id", rs.getObject("id"));
                    m.put("name", rs.getObject("name"));
                    m.put("quantity", rs.getObject("quantity"));
                    m.put("turn", "M y T");
                    m.put("day", rs.getObject("day"));
                    ret.add(m);
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(CRUDStatistics.class.getName()).log(Level.SEVERE, null, ex);
        }
        return ret;
    }

    @Override
    public List<Map> findAllProductStatisticsBetweenDates(java.sql.Date since, java.sql.Date until) throws RemoteException {
        openBase();
        List<Map> ret = new LinkedList<>();
        try {
            String sql = "SELECT DISTINCT id, name, SUM(quantity) AS quantity, turn, day  FROM productstatistics "
                    + "WHERE day >= '" + since.toString() + "' and day <= '" + until.toString() + "' "
                    + "GROUP BY fproduct_id";
            try (Statement stmt = conn.createStatement();
                    java.sql.ResultSet rs = stmt.executeQuery(sql)) {
                while (rs.next()) {
                    Map m = new HashMap();
                    m.put("id", rs.getObject("id"));
                    m.put("name", rs.getObject("name"));
                    m.put("quantity", rs.getObject("quantity"));
                    m.put("turn", "M y T");
                    m.put("day", rs.getObject("day"));
                    ret.add(m);
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(CRUDStatistics.class.getName()).log(Level.SEVERE, null, ex);
        }
        return ret;
    }

    @Override
    public List<Map> findAllProductStatisticsBetweenDatesWithTurn(java.sql.Date since, java.sql.Date until) throws RemoteException {
        openBase();
        List<Map> ret = new LinkedList<>();
        try {
            String sql = "SELECT DISTINCT id, name, SUM(quantity) AS quantity, turn, day  FROM productstatistics"
                    + " WHERE day >= '" + since.toString() + "' and day <= '" + until.toString() + "' "
                    + "GROUP BY turn, fproduct_id";
            try (Statement stmt = conn.createStatement();
                    java.sql.ResultSet rs = stmt.executeQuery(sql)) {
                while (rs.next()) {
                    Map m = new HashMap();
                    m.put("id", rs.getObject("id"));
                    m.put("name", rs.getObject("name"));
                    m.put("quantity", rs.getObject("quantity"));
                    m.put("turn", rs.getObject("turn"));
                    m.put("day", rs.getObject("day"));
                    ret.add(m);
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(CRUDStatistics.class.getName()).log(Level.SEVERE, null, ex);
        }
        return ret;
    }

    @Override
    public List<Map> findProductStatisticsBetweenDaysWithTurn(java.sql.Date since, java.sql.Date until) throws RemoteException {
        openBase();
        List<Map> ret = new LinkedList<>();
        try {
            String sql = "SELECT DISTINCT id, name, quantity, turn, day  FROM productstatistics "
                    + "WHERE day >= '" + since.toString() + "' AND day <= '" + until.toString() + "'";
            try (Statement stmt = conn.createStatement();
                    java.sql.ResultSet rs = stmt.executeQuery(sql)) {
                while (rs.next()) {
                    Map m = new HashMap();
                    m.put("id", rs.getObject("id"));
                    m.put("name", rs.getObject("name"));
                    m.put("quantity", rs.getObject("quantity"));
                    m.put("turn", rs.getObject("turn"));
                    m.put("day", rs.getObject("day"));
                    ret.add(m);
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(CRUDStatistics.class.getName()).log(Level.SEVERE, null, ex);
        }
        return ret;
    }

    @Override
    public List<Map> findProductStatisticsBetweenMonthsWithTurn(java.sql.Date since, java.sql.Date until) throws RemoteException {
        openBase();
        List<Map> ret = new LinkedList<>();
        try {
            String sql = "SELECT DISTINCT id, name, SUM(quantity) AS quantity, turn, day  FROM productstatistics"
                    + " WHERE day >= '" + since.toString() + "' and day <= '" + until.toString() + "' "
                    + "GROUP BY turn, fproduct_id, year(day), month(day)";
            try (Statement stmt = conn.createStatement();
                    java.sql.ResultSet rs = stmt.executeQuery(sql)) {
                while (rs.next()) {
                    Map m = new HashMap();
                    m.put("id", rs.getObject("id"));
                    m.put("name", rs.getObject("name"));
                    m.put("quantity", rs.getObject("quantity"));
                    m.put("turn", rs.getObject("turn"));
                    m.put("day", rs.getObject("day"));
                    ret.add(m);
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(CRUDStatistics.class.getName()).log(Level.SEVERE, null, ex);
        }
        return ret;
    }

    @Override
    public List<Map> findProductStatisticsBetweenYearsWithTurn(java.sql.Date since, java.sql.Date until) throws RemoteException {
        openBase();
        List<Map> ret = new LinkedList<>();
        try {
            String sql = "SELECT DISTINCT id, name, SUM(quantity) AS quantity, turn, day  FROM productstatistics "
                    + "WHERE day >= '" + since.toString() + "' and day <= '" + until.toString() + "' "
                    + "GROUP BY turn, fproduct_id, year(day)";
            try (Statement stmt = conn.createStatement();
                    java.sql.ResultSet rs = stmt.executeQuery(sql)) {
                while (rs.next()) {
                    Map m = new HashMap();
                    m.put("id", rs.getObject("id"));
                    m.put("name", rs.getObject("name"));
                    m.put("quantity", rs.getObject("quantity"));
                    m.put("turn", rs.getObject("turn"));
                    m.put("day", rs.getObject("day"));
                    ret.add(m);
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(CRUDStatistics.class.getName()).log(Level.SEVERE, null, ex);
        }
        return ret;
    }

    @Override
    public List<Map> findSalesStatisticsBetweenDaysWithTurn(java.sql.Date since, java.sql.Date until) throws RemoteException {
        openBase();
        List<Map> ret = new LinkedList<>();
        try {
            String sql = "SELECT DISTINCT waiter_name, user_id, sale_amount, tables, "
                    + "customers, products, average_tables AS avg_tables, "
                    + "average_customers AS avg_customers, average_products AS avg_products, discounts AS discounts,"
                    + " exceptions, turn, day FROM salesstatistics "
                    + "WHERE day >= '" + since.toString() + "' AND day <= '" + until.toString() + "'";
            try (Statement stmt = conn.createStatement();
                    java.sql.ResultSet rs = stmt.executeQuery(sql)) {
                while (rs.next()) {
                    Map m = new HashMap();
                    m.put("waiter_name", rs.getObject("waiter_name"));
                    m.put("user_id", rs.getObject("user_id"));
                    m.put("sale_amount", rs.getObject("sale_amount"));
                    m.put("tables", rs.getObject("tables"));
                    m.put("customers", rs.getObject("customers"));
                    m.put("products", rs.getObject("products"));
                    m.put("average_tables", rs.getObject("avg_tables"));
                    m.put("average_customers", rs.getObject("avg_customers"));
                    m.put("average_products", rs.getObject("avg_products"));
                    m.put("discounts", rs.getObject("discounts"));
                    m.put("exceptions", rs.getObject("exceptions"));
                    m.put("turn", rs.getObject("turn"));
                    m.put("day", rs.getObject("day"));
                    ret.add(m);
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(CRUDStatistics.class.getName()).log(Level.SEVERE, null, ex);
        }
        return ret;
    }

    @Override
    public List<Map> findSalesStatisticsBetweenMonthsWithTurn(java.sql.Date since, java.sql.Date until) throws RemoteException {
        openBase();
        List<Map> ret = new LinkedList<>();
        try {
            String sql = "SELECT DISTINCT waiter_name, user_id, SUM(sale_amount) AS sale_amount, SUM(tables) AS tables, "
                    + "SUM(customers) AS customers, SUM(products) AS products, AVG(average_tables) AS avg_tables, "
                    + "AVG(average_customers) AS avg_customers, AVG(average_products) AS avg_products, SUM(discounts) AS discounts,"
                    + " SUM(exceptions) AS exceptions, turn, day FROM salesstatistics "
                    + "WHERE day >= '" + since.toString() + "' and day <= '" + until.toString() + "' "
                    + "GROUP BY turn, user_id, year(day), month(day)";
            try (Statement stmt = conn.createStatement();
                    java.sql.ResultSet rs = stmt.executeQuery(sql)) {
                while (rs.next()) {
                    Map m = new HashMap();
                    m.put("waiter_name", rs.getObject("waiter_name"));
                    m.put("user_id", rs.getObject("user_id"));
                    m.put("sale_amount", rs.getObject("sale_amount"));
                    m.put("tables", rs.getObject("tables"));
                    m.put("customers", rs.getObject("customers"));
                    m.put("products", rs.getObject("products"));
                    m.put("average_tables", rs.getObject("avg_tables"));
                    m.put("average_customers", rs.getObject("avg_customers"));
                    m.put("average_products", rs.getObject("avg_products"));
                    m.put("discounts", rs.getObject("discounts"));
                    m.put("exceptions", rs.getObject("exceptions"));
                    m.put("turn", rs.getObject("turn"));
                    m.put("day", rs.getObject("day"));
                    ret.add(m);
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(CRUDStatistics.class.getName()).log(Level.SEVERE, null, ex);
        }
        return ret;
    }

    @Override
    public List<Map> findSalesStatisticsBetweenYearsWithTurn(java.sql.Date since, java.sql.Date until) throws RemoteException {
        openBase();
        List<Map> ret = new LinkedList<>();
        try {
            String sql = "SELECT DISTINCT waiter_name, user_id, SUM(sale_amount) AS sale_amount, SUM(tables) AS tables, "
                    + "SUM(customers) AS customers, SUM(products) AS products, AVG(average_tables) AS avg_tables, "
                    + "AVG(average_customers) AS avg_customers, AVG(average_products) AS avg_products, SUM(discounts) AS discounts,"
                    + " SUM(exceptions) AS exceptions, turn, day FROM salesstatistics "
                    + "WHERE day >= '" + since.toString() + "' and day <= '" + until.toString() + "' "
                    + "GROUP BY turn, user_id, year(day)";
            try (Statement stmt = conn.createStatement();
                    java.sql.ResultSet rs = stmt.executeQuery(sql)) {
                while (rs.next()) {
                    Map m = new HashMap();
                    m.put("waiter_name", rs.getObject("waiter_name"));
                    m.put("user_id", rs.getObject("user_id"));
                    m.put("sale_amount", rs.getObject("sale_amount"));
                    m.put("tables", rs.getObject("tables"));
                    m.put("customers", rs.getObject("customers"));
                    m.put("products", rs.getObject("products"));
                    m.put("average_tables", rs.getObject("avg_tables"));
                    m.put("average_customers", rs.getObject("avg_customers"));
                    m.put("average_products", rs.getObject("avg_products"));
                    m.put("discounts", rs.getObject("discounts"));
                    m.put("exceptions", rs.getObject("exceptions"));
                    m.put("turn", rs.getObject("turn"));
                    m.put("day", rs.getObject("day"));
                    ret.add(m);
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(CRUDStatistics.class.getName()).log(Level.SEVERE, null, ex);
        }
        return ret;
    }

    @Override
    public List<Map> findAllSalesStatisticsBetweenDatesWithTurn(java.sql.Date since, java.sql.Date until) throws RemoteException {
        openBase();
        List<Map> ret = new LinkedList<>();
        try {
            String sql = "SELECT DISTINCT waiter_name, user_id, SUM(sale_amount) AS sale_amount, SUM(tables) AS tables, "
                    + "SUM(customers) AS customers, SUM(products) AS products, AVG(average_tables) AS avg_tables, "
                    + "AVG(average_customers) AS avg_customers, AVG(average_products) AS avg_products, SUM(discounts) AS discounts,"
                    + " SUM(exceptions) AS exceptions, turn, day FROM salesstatistics "
                    + "WHERE day >= '" + since.toString() + "' and day <= '" + until.toString() + "' "
                    + "GROUP BY turn, user_id";
            try (Statement stmt = conn.createStatement();
                    java.sql.ResultSet rs = stmt.executeQuery(sql)) {
                while (rs.next()) {
                    Map m = new HashMap();
                    m.put("waiter_name", rs.getObject("waiter_name"));
                    m.put("user_id", rs.getObject("user_id"));
                    m.put("sale_amount", rs.getObject("sale_amount"));
                    m.put("tables", rs.getObject("tables"));
                    m.put("customers", rs.getObject("customers"));
                    m.put("products", rs.getObject("products"));
                    m.put("average_tables", rs.getObject("avg_tables"));
                    m.put("average_customers", rs.getObject("avg_customers"));
                    m.put("average_products", rs.getObject("avg_products"));
                    m.put("discounts", rs.getObject("discounts"));
                    m.put("exceptions", rs.getObject("exceptions"));
                    m.put("turn", rs.getObject("turn"));
                    m.put("day", rs.getObject("day"));
                    ret.add(m);
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(CRUDStatistics.class.getName()).log(Level.SEVERE, null, ex);
        }
        return ret;
    }

    @Override
    public List<Map> findTotalSalesStatisticsBetweenDatesWithTurn(java.sql.Date since, java.sql.Date until) throws RemoteException {
        openBase();
        List<Map> ret = new LinkedList<>();
        try {
            String sql = "SELECT DISTINCT SUM(sale_amount) AS sale_amount, SUM(tables) AS tables, "
                    + "SUM(customers) AS customers, SUM(products) AS products, AVG(average_tables) AS avg_tables, "
                    + "AVG(average_customers) AS avg_customers, AVG(average_products) AS avg_products, SUM(discounts) AS discounts,"
                    + " SUM(exceptions) AS exceptions, turn FROM salesstatistics "
                    + "WHERE day >= '" + since.toString() + "' and day <= '" + until.toString() + "' "
                    + "GROUP BY turn";
            try (Statement stmt = conn.createStatement();
                    java.sql.ResultSet rs = stmt.executeQuery(sql)) {
                while (rs.next()) {
                    Map m = new HashMap();
                    m.put("sale_amount", rs.getObject("sale_amount"));
                    m.put("tables", rs.getObject("tables"));
                    m.put("customers", rs.getObject("customers"));
                    m.put("products", rs.getObject("products"));
                    m.put("average_tables", rs.getObject("avg_tables"));
                    m.put("average_customers", rs.getObject("avg_customers"));
                    m.put("average_products", rs.getObject("avg_products"));
                    m.put("discounts", rs.getObject("discounts"));
                    m.put("exceptions", rs.getObject("exceptions"));
                    m.put("turn", rs.getObject("turn"));
                    ret.add(m);
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(CRUDStatistics.class.getName()).log(Level.SEVERE, null, ex);
        }
        return ret;
    }

    @Override
    public List<Map> findTotalSalesStatisticsBetweenDates(java.sql.Date since, java.sql.Date until) throws RemoteException {
        openBase();
        List<Map> ret = new LinkedList<>();
        try {
            String sql = "SELECT DISTINCT SUM(sale_amount) AS sale_amount, SUM(tables) AS tables, "
                    + "SUM(customers) AS customers, SUM(products) AS products, AVG(average_tables) AS avg_tables, "
                    + "AVG(average_customers) AS avg_customers, AVG(average_products) AS avg_products, SUM(discounts) AS discounts,"
                    + " SUM(exceptions) AS exceptions FROM salesstatistics "
                    + "WHERE day >= '" + since.toString() + "' and day <= '" + until.toString() + "'";
            try (Statement stmt = conn.createStatement();
                    java.sql.ResultSet rs = stmt.executeQuery(sql)) {
                while (rs.next()) {
                    Map m = new HashMap();
                    m.put("sale_amount", rs.getObject("sale_amount"));
                    m.put("tables", rs.getObject("tables"));
                    m.put("customers", rs.getObject("customers"));
                    m.put("products", rs.getObject("products"));
                    m.put("average_tables", rs.getObject("avg_tables"));
                    m.put("average_customers", rs.getObject("avg_customers"));
                    m.put("average_products", rs.getObject("avg_products"));
                    m.put("discounts", rs.getObject("discounts"));
                    m.put("exceptions", rs.getObject("exceptions"));
                    m.put("turn", "M y T");
                    ret.add(m);
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(CRUDStatistics.class.getName()).log(Level.SEVERE, null, ex);
        }
        return ret;
    }
    
    @Override
    public List<Map> findSalesStatisticsBetweenDays(java.sql.Date since, java.sql.Date until) throws RemoteException {
        openBase();
        List<Map> ret = new LinkedList<>();
        try {
            String sql = "SELECT DISTINCT waiter_name, user_id, SUM(sale_amount) AS sale_amount, SUM(tables) AS tables, "
                    + "SUM(customers) AS customers, SUM(products) AS products, AVG(average_tables) AS avg_tables, "
                    + "AVG(average_customers) AS avg_customers, AVG(average_products) AS avg_products, SUM(discounts) AS discounts,"
                    + " SUM(exceptions) AS exceptions, turn, day FROM salesstatistics "
                    + "WHERE day >= '" + since.toString() + "' AND day <= '" + until.toString() + "' "
                    + "GROUP BY day, user_id";
            try (Statement stmt = conn.createStatement();
                    java.sql.ResultSet rs = stmt.executeQuery(sql)) {
                while (rs.next()) {
                    Map m = new HashMap();
                    m.put("waiter_name", rs.getObject("waiter_name"));
                    m.put("user_id", rs.getObject("user_id"));
                    m.put("sale_amount", rs.getObject("sale_amount"));
                    m.put("tables", rs.getObject("tables"));
                    m.put("customers", rs.getObject("customers"));
                    m.put("products", rs.getObject("products"));
                    m.put("average_tables", rs.getObject("avg_tables"));
                    m.put("average_customers", rs.getObject("avg_customers"));
                    m.put("average_products", rs.getObject("avg_products"));
                    m.put("discounts", rs.getObject("discounts"));
                    m.put("exceptions", rs.getObject("exceptions"));
                    m.put("turn", "M y T");
                    m.put("day", rs.getObject("day"));
                    ret.add(m);
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(CRUDStatistics.class.getName()).log(Level.SEVERE, null, ex);
        }
        return ret;
    }

    @Override
    public List<Map> findSalesStatisticsBetweenMonths(java.sql.Date since, java.sql.Date until) throws RemoteException {
        openBase();
        List<Map> ret = new LinkedList<>();
        try {
            String sql = "SELECT DISTINCT waiter_name, user_id, SUM(sale_amount) AS sale_amount, SUM(tables) AS tables, "
                    + "SUM(customers) AS customers, SUM(products) AS products, AVG(average_tables) AS avg_tables, "
                    + "AVG(average_customers) AS avg_customers, AVG(average_products) AS avg_products, SUM(discounts) AS discounts,"
                    + " SUM(exceptions) AS exceptions, turn, day FROM salesstatistics "
                    + "WHERE day >= '" + since.toString() + "' and day <= '" + until.toString() + "' "
                    + "GROUP BY user_id, year(day), month(day)";
            try (Statement stmt = conn.createStatement();
                    java.sql.ResultSet rs = stmt.executeQuery(sql)) {
                while (rs.next()) {
                    Map m = new HashMap();
                    m.put("waiter_name", rs.getObject("waiter_name"));
                    m.put("user_id", rs.getObject("user_id"));
                    m.put("sale_amount", rs.getObject("sale_amount"));
                    m.put("tables", rs.getObject("tables"));
                    m.put("customers", rs.getObject("customers"));
                    m.put("products", rs.getObject("products"));
                    m.put("average_tables", rs.getObject("avg_tables"));
                    m.put("average_customers", rs.getObject("avg_customers"));
                    m.put("average_products", rs.getObject("avg_products"));
                    m.put("discounts", rs.getObject("discounts"));
                    m.put("exceptions", rs.getObject("exceptions"));
                    m.put("turn", "M y T");
                    m.put("day", rs.getObject("day"));
                    ret.add(m);
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(CRUDStatistics.class.getName()).log(Level.SEVERE, null, ex);
        }
        return ret;
    }

    @Override
    public List<Map> findSalesStatisticsBetweenYears(java.sql.Date since, java.sql.Date until) throws RemoteException {
        openBase();
        List<Map> ret = new LinkedList<>();
        try {
            String sql = "SELECT DISTINCT waiter_name, user_id, SUM(sale_amount) AS sale_amount, SUM(tables) AS tables, "
                    + "SUM(customers) AS customers, SUM(products) AS products, AVG(average_tables) AS avg_tables, "
                    + "AVG(average_customers) AS avg_customers, AVG(average_products) AS avg_products, SUM(discounts) AS discounts,"
                    + " SUM(exceptions) AS exceptions, turn, day FROM salesstatistics "
                    + "WHERE day >= '" + since.toString() + "' and day <= '" + until.toString() + "' "
                    + "GROUP BY user_id, year(day)";
            try (Statement stmt = conn.createStatement();
                    java.sql.ResultSet rs = stmt.executeQuery(sql)) {
                while (rs.next()) {
                    Map m = new HashMap();
                    m.put("waiter_name", rs.getObject("waiter_name"));
                    m.put("user_id", rs.getObject("user_id"));
                    m.put("sale_amount", rs.getObject("sale_amount"));
                    m.put("tables", rs.getObject("tables"));
                    m.put("customers", rs.getObject("customers"));
                    m.put("products", rs.getObject("products"));
                    m.put("average_tables", rs.getObject("avg_tables"));
                    m.put("average_customers", rs.getObject("avg_customers"));
                    m.put("average_products", rs.getObject("avg_products"));
                    m.put("discounts", rs.getObject("discounts"));
                    m.put("exceptions", rs.getObject("exceptions"));
                    m.put("turn", "M y T");
                    m.put("day", rs.getObject("day"));
                    ret.add(m);
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(CRUDStatistics.class.getName()).log(Level.SEVERE, null, ex);
        }
        return ret;
    }

    @Override
    public List<Map> findAllSalesStatisticsBetweenDates(java.sql.Date since, java.sql.Date until) throws RemoteException {
        openBase();
        List<Map> ret = new LinkedList<>();
        try {
            String sql = "SELECT DISTINCT waiter_name, user_id, SUM(sale_amount) AS sale_amount, SUM(tables) AS tables, "
                    + "SUM(customers) AS customers, SUM(products) AS products, AVG(average_tables) AS avg_tables, "
                    + "AVG(average_customers) AS avg_customers, AVG(average_products) AS avg_products, SUM(discounts) AS discounts,"
                    + " SUM(exceptions) AS exceptions, turn, day FROM salesstatistics "
                    + "WHERE day >= '" + since.toString() + "' and day <= '" + until.toString() + "' "
                    + "GROUP BY user_id";
            try (Statement stmt = conn.createStatement();
                    java.sql.ResultSet rs = stmt.executeQuery(sql)) {
                while (rs.next()) {
                    Map m = new HashMap();
                    m.put("waiter_name", rs.getObject("waiter_name"));
                    m.put("user_id", rs.getObject("user_id"));
                    m.put("sale_amount", rs.getObject("sale_amount"));
                    m.put("tables", rs.getObject("tables"));
                    m.put("customers", rs.getObject("customers"));
                    m.put("products", rs.getObject("products"));
                    m.put("average_tables", rs.getObject("avg_tables"));
                    m.put("average_customers", rs.getObject("avg_customers"));
                    m.put("average_products", rs.getObject("avg_products"));
                    m.put("discounts", rs.getObject("discounts"));
                    m.put("exceptions", rs.getObject("exceptions"));
                    m.put("turn", "M y T");
                    m.put("day", rs.getObject("day"));
                    ret.add(m);
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(CRUDStatistics.class.getName()).log(Level.SEVERE, null, ex);
        }
        return ret;
    }

    @Override
    public List<Map> getTotalSalesWaiterBetweenDays(java.sql.Date since, java.sql.Date until) throws RemoteException {
        openBase();
        List<Map> ret = new LinkedList<>();
        try {
            String sql = "SELECT DISTINCT waiter_name, user_id, SUM(sale_amount) AS sale_amount, SUM(tables) AS tables"
                    + " FROM salesstatistics "
                    + "WHERE day >= '" + since.toString() + "' and day <= '" + until.toString() + "' "
                    + "GROUP BY user_id";
            try (Statement stmt = conn.createStatement();
                    java.sql.ResultSet rs = stmt.executeQuery(sql)) {
                while (rs.next()) {
                    Map m = new HashMap();
                    m.put("waiter_name", rs.getObject("waiter_name"));
                    m.put("user_id", rs.getObject("user_id"));
                    m.put("sale_amount", rs.getObject("sale_amount"));
                    m.put("tables", rs.getObject("tables"));
                    ret.add(m);
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(CRUDStatistics.class.getName()).log(Level.SEVERE, null, ex);
        }
        return ret;
    }

}
