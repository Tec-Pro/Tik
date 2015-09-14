/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package implementsInterface.statistics;

import implementsInterface.CRUDPproduct;
import interfaces.statistics.InterfacePurchaseStatistics;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import models.Pproductcategory;
import models.Pproductsubcategory;
import models.Provider;
import models.statistics.Purchasestatistic;
import org.javalite.activejdbc.Base;
import org.javalite.activejdbc.Model;
import static utils.InterfaceName.CRUDPproduct;
import utils.Utils;

/**
 *
 * @author eze
 */
public class CRUDPurchaseStatistics extends UnicastRemoteObject implements InterfacePurchaseStatistics {

    private Connection conn;
    
    public CRUDPurchaseStatistics() throws RemoteException {
        super();
        openBase();
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
    
    public static Purchasestatistic savePurchaseStatistics(int pproductsubcategory_id, String day, int pproduct_id, String ppname,
            String measure_unit, float quantity, float total_price, int provider_id,
            float unit_price) {
        Utils.abrirBase();
        Base.openTransaction();
        Pproductsubcategory pproductsubcategory = Pproductsubcategory.findById(pproductsubcategory_id);
        Pproductcategory pproductcategory = Pproductcategory.findById(pproductsubcategory.getInteger("pproductcategory_id"));
        Purchasestatistic purchasestatistic = Purchasestatistic.createIt(
                "pproductcategory_id", pproductsubcategory.getInteger("pproductcategory_id"),
                "pproductsubcategory_id", pproductsubcategory_id,
                "day", day,
                "pproduct_id", pproduct_id,
                "pproduct_name", ppname,
                "measure_unit", measure_unit,
                "quantity", quantity,
                "total_price", total_price,
                "provider_id", provider_id,
                "provider_name", (Provider.findById(provider_id)).getString("name"),
                "unit_price", unit_price,
                "pproductcategory_name", pproductcategory.getString("name"),
                "pproductsubcategory_name", pproductsubcategory.getString("name"));
        Base.commitTransaction();
        return purchasestatistic;
    }

    @Override
    public List<Map> findPurchaseStatisticsBetweenDays(Date since, Date until) throws RemoteException {
        openBase();
        List<Map> ret = new LinkedList<>();
        try {
            String sql = "SELECT DISTINCT id, pproduct_name, SUM(quantity) AS quantity, measure_unit, SUM(total_price) AS total_price, "
                    + "provider_name, unit_price, pproductcategory_name, pproductsubcategory_name, day  "
                    + "FROM purchasestatistics "
                    + "WHERE day >= '" + since.toString() + "' AND day <= '" + until.toString() + "' "
                    + "GROUP BY day, pproduct_id";
            try (Statement stmt = conn.createStatement();
                   java.sql.ResultSet rs = stmt.executeQuery(sql)) {
                while (rs.next()) {
                    Map m = new HashMap();
                    m.put("id", rs.getObject("id"));
                    m.put("pproduct_name", rs.getObject("pproduct_name"));
                    m.put("measure_unit", rs.getObject("measure_unit"));
                    m.put("total_price", rs.getObject("total_price"));
                    m.put("provider_name", rs.getObject("provider_name"));
                    m.put("unit_price", rs.getObject("unit_price"));
                    m.put("pproductcategory_name", rs.getObject("pproductcategory_name"));
                    m.put("pproductsubcategory_name", rs.getObject("pproductsubcategory_name"));
                    m.put("quantity", rs.getObject("quantity"));
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
    public List<Map> findPurchaseStatisticsBetweenMonths(Date since, Date until) throws RemoteException {
        openBase();
        List<Map> ret = new LinkedList<>();
        try {
            String sql = "SELECT DISTINCT id, pproduct_name, SUM(quantity) AS quantity, measure_unit, SUM(total_price) AS total_price, "
                    + "provider_name, unit_price, pproductcategory_name, pproductsubcategory_name, day  "
                    + "FROM purchasestatistics "
                    + "WHERE day >= '" + since.toString() + "' AND day <= '" + until.toString() + "' "
                    + "GROUP BY year(day), month(day), pproduct_id";
            try (Statement stmt = conn.createStatement();
                   java.sql.ResultSet rs = stmt.executeQuery(sql)) {
                while (rs.next()) {
                    Map m = new HashMap();
                    m.put("id", rs.getObject("id"));
                    m.put("pproduct_name", rs.getObject("pproduct_name"));
                    m.put("measure_unit", rs.getObject("measure_unit"));
                    m.put("total_price", rs.getObject("total_price"));
                    m.put("provider_name", rs.getObject("provider_name"));
                    m.put("unit_price", rs.getObject("unit_price"));
                    m.put("pproductcategory_name", rs.getObject("pproductcategory_name"));
                    m.put("pproductsubcategory_name", rs.getObject("pproductsubcategory_name"));
                    m.put("quantity", rs.getObject("quantity"));
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
    public List<Map> findPurchaseStatisticsBetweenYears(Date since, Date until) throws RemoteException {
        openBase();
        List<Map> ret = new LinkedList<>();
        try {
            String sql = "SELECT DISTINCT id, pproduct_name, SUM(quantity) AS quantity, measure_unit, SUM(total_price) AS total_price, "
                    + "provider_name, unit_price, pproductcategory_name, pproductsubcategory_name, day  "
                    + "FROM purchasestatistics "
                    + "WHERE day >= '" + since.toString() + "' AND day <= '" + until.toString() + "' "
                    + "GROUP BY year(day), pproduct_id";
            try (Statement stmt = conn.createStatement();
                   java.sql.ResultSet rs = stmt.executeQuery(sql)) {
                while (rs.next()) {
                    Map m = new HashMap();
                    m.put("id", rs.getObject("id"));
                    m.put("pproduct_name", rs.getObject("pproduct_name"));
                    m.put("measure_unit", rs.getObject("measure_unit"));
                    m.put("total_price", rs.getObject("total_price"));
                    m.put("provider_name", rs.getObject("provider_name"));
                    m.put("unit_price", rs.getObject("unit_price"));
                    m.put("pproductcategory_name", rs.getObject("pproductcategory_name"));
                    m.put("pproductsubcategory_name", rs.getObject("pproductsubcategory_name"));
                    m.put("quantity", rs.getObject("quantity"));
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
    public List<Map> findAllPurchaseStatisticsBetweenDates(Date since, Date until) throws RemoteException {
        openBase();
        List<Map> ret = new LinkedList<>();
        try {
            String sql = "SELECT DISTINCT id, pproduct_name, SUM(quantity) AS quantity, measure_unit, SUM(total_price) AS total_price, "
                    + "provider_name, unit_price, pproductcategory_name, pproductsubcategory_name, day  "
                    + "FROM purchasestatistics "
                    + "WHERE day >= '" + since.toString() + "' AND day <= '" + until.toString() + "' "
                    + "GROUP BY pproduct_id";
            try (Statement stmt = conn.createStatement();
                   java.sql.ResultSet rs = stmt.executeQuery(sql)) {
                while (rs.next()) {
                    Map m = new HashMap();
                    m.put("id", rs.getObject("id"));
                    m.put("pproduct_name", rs.getObject("pproduct_name"));
                    m.put("measure_unit", rs.getObject("measure_unit"));
                    m.put("total_price", rs.getObject("total_price"));
                    m.put("provider_name", rs.getObject("provider_name"));
                    m.put("unit_price", rs.getObject("unit_price"));
                    m.put("pproductcategory_name", rs.getObject("pproductcategory_name"));
                    m.put("pproductsubcategory_name", rs.getObject("pproductsubcategory_name"));
                    m.put("quantity", rs.getObject("quantity"));
                    m.put("day", rs.getObject("day"));
                    ret.add(m);
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(CRUDStatistics.class.getName()).log(Level.SEVERE, null, ex);
        }
        return ret;
    }
    
    
}
