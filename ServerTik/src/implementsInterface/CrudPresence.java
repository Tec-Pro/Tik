/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package implementsInterface;

import interfaces.InterfacePresence;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import models.Presence;
import models.User;
import org.javalite.activejdbc.Base;
import utils.Utils;

/**
 *
 * @author jacinto
 */
public class CrudPresence extends UnicastRemoteObject implements InterfacePresence {

    public CrudPresence() throws RemoteException {
        super();
    }

    @Override
    public Map<String, Object> create(int userId) throws RemoteException {
        Utils.abrirBase();
        Base.openTransaction();
        Date now = new Date(System.currentTimeMillis());
        SimpleDateFormat date = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat hour = new SimpleDateFormat("HH:mm:ss");
        System.out.println(date.format(now));
        System.out.println(hour.format(now));
        Presence p = Presence.createIt("day", date.format(now), "entry_time", hour.format(now), "user_id", userId);
        User.findById(userId).set("order_count", 0);
        Base.commitTransaction();
        return p.toMap();
    }

    @Override
    public Map<String, Object> logout(int userId) throws RemoteException {
        Utils.abrirBase();
        Base.openTransaction();
        Date now = new Date(System.currentTimeMillis());
        SimpleDateFormat date = new SimpleDateFormat("yyyy-MM-dd");
        List<Presence> listP = Presence.where("user_id = ? and day = '00:00:00'", userId).orderBy("id desc");
        if (listP != null) {
            SimpleDateFormat hour = new SimpleDateFormat("HH:mm:ss");
            Presence p = listP.get(0);
            p.set("departure_time", hour.format(now));
            p.saveIt();
            Base.commitTransaction();
            return p.toMap();
        }
        return null;
    }

    @Override
    public List<Map> getWaiters() throws RemoteException {
        Utils.abrirBase();
        List<Map> listP = new LinkedList<Map>();
        for (Map m : Presence.where("departure_time = ?", "00:00:00").toMaps()) {
            if (User.findById(m.get("user_id")).getString("position").equals("Mozo")) {
                if (!listP.contains(User.findById(m.get("user_id")).toMap())) {
                    listP.add(User.findById(m.get("user_id")).toMap());
                }
            }
        }
        return listP;
    }

    @Override
    public List<Map> getCooks() throws RemoteException {
        Utils.abrirBase();
        List<Map> listP = new LinkedList<Map>();
        for (Map m : Presence.where("departure_time = ?", "00:00:00").toMaps()) {
            if (User.findById(m.get("user_id")).getString("position").equals("Cocinero")) {
                if (!listP.contains(User.findById(m.get("user_id")).toMap())) {
                    listP.add(User.findById(m.get("user_id")).toMap());
                }
            }
        }
        return listP;
    }

    @Override
    public void logoutAllWaiters() throws RemoteException {
         Utils.abrirBase();
        Base.openTransaction();
        Date now = new Date(System.currentTimeMillis());
        SimpleDateFormat date = new SimpleDateFormat("yyyy-MM-dd");
        List<Presence> listP = Presence.where("day = '00:00:00'");
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
        List<Presence> listP = Presence.where("day = '00:00:00'");
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
        List<Presence> listP = Presence.where("day = '00:00:00'");
        for (Presence p : listP) {
            if (User.findById(p.get("user_id")).getString("position").equals("Cocinero")) {
                SimpleDateFormat hour = new SimpleDateFormat("HH:mm:ss");
                p.set("departure_time", hour.format(now));
                p.saveIt();
            }
        }
        Base.commitTransaction();
    }
}
