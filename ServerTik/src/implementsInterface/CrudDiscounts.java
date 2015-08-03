/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package implementsInterface;

import interfaces.InterfaceDiscount;
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
import models.Discount;
import models.Presence;
import models.User;
import org.javalite.activejdbc.Base;
import org.javalite.activejdbc.Model;
import utils.Utils;

/**
 *
 * @author nico
 */
public class CrudDiscounts extends UnicastRemoteObject implements InterfaceDiscount {
    private Connection conn;
    private String sql = "";
    
    public CrudDiscounts() throws RemoteException {
        super();
        openBase();
    }

    @Override
    public Map<String, Object> create(int fproduct_id, int user_id, int order_id) throws RemoteException {
        Utils.abrirBase();
        Base.openTransaction();
        Discount d = Discount.createIt("fproduct_id", fproduct_id, "user_id", user_id, "order_id", order_id);
        Base.commitTransaction();
        return d.toMap();
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
     
    @Override
    public List<Map> getDiscounts(int user_id, String dateFrom, String dateTo) throws RemoteException {
        try {
            openBase();
            Utils.abrirBase();
            Map m = new HashMap();
            LinkedList<Map> ret = new LinkedList<>();
            if(user_id!=-1)
                sql = "SELECT d.created_at, fp.name, d.user_id FROM tik.discounts d INNER JOIN fproducts fp ON fp.id=d.fproduct_id where d.user_id= '"+user_id+"' and created_at BETWEEN  '"+dateFrom+"'  and  '"+dateTo+"' ;";
            else
                sql = "SELECT d.created_at, fp.name, d.user_id FROM tik.discounts d INNER JOIN fproducts fp ON fp.id=d.fproduct_id where created_at BETWEEN  '"+dateFrom+"'  and  '"+dateTo+"' ;";
            Statement stmt = conn.createStatement();
            java.sql.ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                m = new HashMap();
                m.put("created_at", rs.getString("created_at").split(" ")[0]);
                m.put("name", rs.getString("name"));
                String user_name = User.findById(rs.getInt("user_id")).getString("name");
                m.put("user_name", user_name);
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
