/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package implementsInterface;

import interfaces.InterfacePresence;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import models.Presence;
import models.User;
import org.javalite.activejdbc.Base;
import utils.Utils;

/**
 *
 * @author jacinto
 */
public class CrudPresence extends UnicastRemoteObject implements InterfacePresence {

    private Connection conn;
    private String sql = "";

    public CrudPresence() throws RemoteException {
        super();
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
    public Map<String, Object> create(int userId) throws RemoteException {
        Utils.abrirBase();
        Base.openTransaction();
        Date now = new Date(System.currentTimeMillis());
        SimpleDateFormat date = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat hour = new SimpleDateFormat("HH:mm:ss");

        Presence p = Presence.createIt("day", date.format(now), "entry_time", hour.format(now), "user_id", userId);
        User.findById(userId).set("order_count", 0);
        Base.commitTransaction();
        return p.toMap();
    }

    @Override
    public Map<String, Object> logout(int userId) throws RemoteException {
        Utils.abrirBase();
        Date now = new Date(System.currentTimeMillis());
        SimpleDateFormat date = new SimpleDateFormat("yyyy-MM-dd");
        List<Presence> listP = Presence.where("user_id = ? and departure_time = '00:00:00'", userId).orderBy("id desc");
        if (listP != null) {
            Base.openTransaction();
            SimpleDateFormat hour = new SimpleDateFormat("HH:mm:ss");
            Presence p = listP.get(0);
            p.set("departure_time", hour.format(now));
            p.set("departure_day", date.format(now));
            p.saveIt();
            Base.commitTransaction();
            return p.toMap();
        }
        return null;
    }

    @Override
    public List<Map> getWaiters() throws RemoteException {
       openBase();
        List<Map> ret = new LinkedList<>();
        try {
            sql = "SELECT * FROM tik.presences inner join tik.users where departure_time ='00:00:00' and position = 'Mozo' group by user_id ; ";
            Statement stmt = conn.createStatement();
            java.sql.ResultSet rs = stmt.executeQuery(sql);

            while (rs.next()) {
                Map m = new HashMap();

                m.put("id", rs.getObject("user_id"));
                m.put("name", rs.getObject("name"));
                m.put("surname", rs.getObject("surname"));
                m.put("pass", rs.getObject("pass"));
                m.put("date_hired", rs.getObject("date_hired"));
                m.put("date_discharged", rs.getObject("date_discharged"));
                m.put("turn", rs.getObject("turn"));
                m.put("date_of_birth", rs.getObject("date_of_birth"));
                m.put("place_of_birth", rs.getObject("place_of_birth"));
                m.put("id_type", rs.getObject("id_type"));
                m.put("id_number", rs.getObject("id_number"));
                m.put("address", rs.getObject("address"));
                m.put("home_phone", rs.getObject("home_phone"));
                m.put("emergency_phone", rs.getObject("emergency_phone"));
                m.put("mobile_phone", rs.getObject("mobile_phone"));
                m.put("marital_status", rs.getObject("marital_status"));
                m.put("blood_type", rs.getObject("blood_type"));
                m.put("position", rs.getObject("position"));
                m.put("order_count", rs.getObject("order_count"));
                ret.add(m);
            }
            rs.close();
            stmt.close();
            conn.close();

        } catch (SQLException ex) {
            Logger.getLogger(CRUDOrder.class.getName()).log(Level.SEVERE, null, ex);
        }

        return ret;
    }

    @Override
    public List<Map> getCooks() throws RemoteException {
openBase();
        List<Map> ret = new LinkedList<>();
        try {
            sql = "SELECT * FROM tik.presences inner join tik.users where departure_time ='00:00:00' and position = 'Cocinero' group by user_id ; ";
            Statement stmt = conn.createStatement();
            java.sql.ResultSet rs = stmt.executeQuery(sql);

            while (rs.next()) {
                Map m = new HashMap();

                m.put("id", rs.getObject("user_id"));
                m.put("name", rs.getObject("name"));
                m.put("surname", rs.getObject("surname"));
                m.put("pass", rs.getObject("pass"));
                m.put("date_hired", rs.getObject("date_hired"));
                m.put("date_discharged", rs.getObject("date_discharged"));
                m.put("turn", rs.getObject("turn"));
                m.put("date_of_birth", rs.getObject("date_of_birth"));
                m.put("place_of_birth", rs.getObject("place_of_birth"));
                m.put("id_type", rs.getObject("id_type"));
                m.put("id_number", rs.getObject("id_number"));
                m.put("address", rs.getObject("address"));
                m.put("home_phone", rs.getObject("home_phone"));
                m.put("emergency_phone", rs.getObject("emergency_phone"));
                m.put("mobile_phone", rs.getObject("mobile_phone"));
                m.put("marital_status", rs.getObject("marital_status"));
                m.put("blood_type", rs.getObject("blood_type"));
                m.put("position", rs.getObject("position"));
                m.put("order_count", rs.getObject("order_count"));
                ret.add(m);
            }
            rs.close();
            stmt.close();
            conn.close();

        } catch (SQLException ex) {
            Logger.getLogger(CRUDOrder.class.getName()).log(Level.SEVERE, null, ex);
        }

        return ret;
    }

    @Override
    public void logoutAllWaiters() throws RemoteException {
        Utils.abrirBase();
        Base.openTransaction();
        Date now = new Date(System.currentTimeMillis());
        SimpleDateFormat date = new SimpleDateFormat("yyyy-MM-dd");
        List<Presence> listP = Presence.where("departure_time = '00:00:00'");
        for (Presence p : listP) {
            if (User.findById(p.get("user_id")).getString("position").equals("Mozo")) {
                SimpleDateFormat hour = new SimpleDateFormat("HH:mm:ss");
                p.set("departure_time", hour.format(now));
                p.saveIt();
            }
        }
        Base.commitTransaction();
    }

    @Override
    public List<Map> getPresences(int id, String dateFrom, String dateTo) throws RemoteException {
        Utils.abrirBase();
        List<Map> list = Presence.where("user_id = ? and (day BETWEEN ? AND ?)", id, dateFrom, dateTo).toMaps();
        return list;
    }

    @Override
    public List<Map> getPresences(int id, String date) throws RemoteException {
        Utils.abrirBase();
        List<Map> list = Presence.where("user_id = ? and day = ?", id, date).toMaps();
        return list;
    }

    @Override
    public void logoutAll() throws RemoteException {
        Utils.abrirBase();
        Base.openTransaction();
        Date now = new Date(System.currentTimeMillis());
        SimpleDateFormat date = new SimpleDateFormat("yyyy-MM-dd");
        List<Presence> listP = Presence.where("departure_time = '00:00:00'");
        for (Presence p : listP) {
            SimpleDateFormat hour = new SimpleDateFormat("HH:mm:ss");
            p.set("departure_time", hour.format(now));
            p.saveIt();
        }
        Base.commitTransaction();
    }

    @Override
    public void logoutAllCooks() throws RemoteException {
        Utils.abrirBase();
        Base.openTransaction();
        Date now = new Date(System.currentTimeMillis());
        SimpleDateFormat date = new SimpleDateFormat("yyyy-MM-dd");
        List<Presence> listP = Presence.where("departure_time = '00:00:00'");
        for (Presence p : listP) {
            if (User.findById(p.get("user_id")).getString("position").equals("Cocinero")) {
                SimpleDateFormat hour = new SimpleDateFormat("HH:mm:ss");
                p.set("departure_time", hour.format(now));
                p.saveIt();
            }
        }
        Base.commitTransaction();
    }

    @Override
    public boolean isSomeoneLogin() throws RemoteException {
        Utils.abrirBase();
        List<Presence> listP = Presence.where("departure_time = '00:00:00'");
        if (listP == null) {
            return false;
        } else {
            if (!listP.isEmpty()) {
                return true;
            } else {
                return false;
            }
        }
    }

    @Override
    public boolean userIsLogin(int idUser) throws RemoteException {
        openBase();
        boolean ret = false;
        try {
            sql = "SELECT * from presences WHERE departure_time = '00:00:00' and user_id ='" + idUser + "';";
            Statement stmt = conn.createStatement();
            java.sql.ResultSet rs = stmt.executeQuery(sql);
            while (rs.next() && ret == false) {
                ret = true;
            }
            rs.close();
            stmt.close();
            conn.close();

        } catch (SQLException ex) {
            Logger.getLogger(CRUDOrder.class.getName()).log(Level.SEVERE, null, ex);
        }
        return ret;
    }

    @Override
    public List<Map> getWaitersWereOnline() throws RemoteException {
        Utils.abrirBase();
        Date now = new Date(System.currentTimeMillis());
        GregorianCalendar calendar = new GregorianCalendar();
        calendar.setGregorianChange(now);
        calendar.set(GregorianCalendar.DAY_OF_YEAR, calendar.get(GregorianCalendar.DAY_OF_YEAR) - 1);
        Date y = calendar.getTime();
        SimpleDateFormat date = new SimpleDateFormat("yyyy-MM-dd");
        List<Map> list = Presence.where("departure_time <> '00:00:00' and (day = ? or day = ?)", date.format(now), date.format(y)).toMaps();
        List<Map> listp = new LinkedList<Map>();
        for (Map m : list) {
            if (User.findById(m.get("user_id")).getString("position").equals("Mozo")) {
                if (!listp.contains(User.findById(m.get("user_id")).toMap())) {
                    listp.add(User.findById(m.get("user_id")).toMap());
                }
            }
        }
        return listp;
    }
}
