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
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author eze
 */
public class CRUDStatistics extends UnicastRemoteObject implements InterfaceStatistics {

    private Connection conn;
    private CRUDFproduct crudFProduct;
    
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
     * @return List<Map<String, Object>> con:
     * Nombre(name) de fproduct,
     * Precio de Venta(sell_price),
     * Subcategoría(subcategory),
     * Categoría(category),
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
            sql2 = "SELECT ALL sql1.fp_id, sql1.name, sql1.sell_price, cat.name AS category, sql1.subcategory FROM ("+sql1+") sql1 INNER JOIN categories cat WHERE sql1.category_id = cat.id;"
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
                m.put("elaboration_price", calculateProductionPrice );
                ret.add(m);
            }
            rs.close();
            stmt.close();
        } catch (SQLException ex) {
            Logger.getLogger(CRUDStatistics.class.getName()).log(Level.SEVERE, null, ex);
        }
        return ret;
    
    }
    
    
}
