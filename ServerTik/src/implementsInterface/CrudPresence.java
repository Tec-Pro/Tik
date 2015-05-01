/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package implementsInterface;

import interfaces.InterfacePresence;
import java.rmi.RemoteException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import models.Presence;

/**
 *
 * @author jacinto
 */
public class CrudPresence implements InterfacePresence{

    @Override
    public Map<String, Object> create(int userId) throws RemoteException {
       Date now = new Date(System.currentTimeMillis());
       SimpleDateFormat date = new SimpleDateFormat("yyyy-MM-dd");
       SimpleDateFormat hour = new SimpleDateFormat("HH:mm:ss");
        System.out.println(date.format(now));
        System.out.println(hour.format(now));        
       Presence p = Presence.createIt("day",date.format(now),"enrty_time",hour.format(now),"user_id",userId);
       return p.toMap();
    }

    @Override
    public Map<String, Object> logout(int userId) throws RemoteException {
        Date now = new Date(System.currentTimeMillis());
        SimpleDateFormat date = new SimpleDateFormat("yyyy-MM-dd");
        Presence p = Presence.findFirst("user_id = ? and day = ?", userId, date.format(now));
        if (p != null){
            SimpleDateFormat hour = new SimpleDateFormat("HH:mm:ss");
            p.set("departure_time",hour.format(now));
            p.saveIt();
            return p.toMap();
        }
        return null;
    }
    
}
