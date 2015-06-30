/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package implementsInterface.statistics;

import implementsInterface.CRUDFproduct;
import interfaces.statistics.InterfaceStatistics;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import models.Salesstatistic;
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
                float calculateProductionPrice = crudFProduct.calculateProductionPrice(Integer.parseInt(rs.getObject("fp_id").toString()));
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
     * @throws RemoteException
     */
    @Override
    public Map<String, Object> storeSalesStatistics(String waiterName, int userId, float saleAmount, int tables,
            int customers, int products, float avgTables, float avgCustomers, float avgProducts, float discounts,
            float exceptions, String turn, Timestamp day) throws RemoteException {
        Utils.abrirBase();
        Base.openTransaction();
        Salesstatistic ret = Salesstatistic.createIt("waiter_name", waiterName, "user_id", userId, "sale_amount",
                String.valueOf(saleAmount), "tables", tables, "customers", customers, "products", products, "average_tables", String.valueOf(avgTables),
                "average_customers", String.valueOf(avgCustomers), "average_products", String.valueOf(avgProducts), "discounts", String.valueOf(discounts),
                "exceptions", String.valueOf(exceptions), "turn", turn, "day", day);
        Base.commitTransaction();
        return ret.toMap();
    }

    /**
     * Retorna una lista de estadisticas de ventas de todos los mozos, en todos
     * los turnos
     *
     * @return
     * @throws java.rmi.RemoteException
     */
    @Override
    public List<Map> getAllSalesStatistics() throws RemoteException {
        Utils.abrirBase();
        List<Map> ret = Salesstatistic.findAll().toMaps();
        return ret;
    }

    /**
     * Retorna una lista de estadisticas de ventas de un mozo, en todos los
     * turnos
     *
     * @param userId id del mozo
     * @return
     * @throws RemoteException
     */
    @Override
    public List<Map> getSalesStatisticsFromAWaiter(int userId) throws RemoteException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
