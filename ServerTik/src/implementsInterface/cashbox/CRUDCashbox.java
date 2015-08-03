/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package implementsInterface.cashbox;

import interfaces.cashbox.InterfaceCashbox;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import models.cashbox.Cashbox;
import org.javalite.activejdbc.Base;
import utils.Utils;

/**
 *
 * @author jacinto
 */
public class CRUDCashbox extends UnicastRemoteObject implements InterfaceCashbox {

    public CRUDCashbox() throws RemoteException {
        super();
    }

    @Override
    public Map<String, Object> create(String turn, float balance, float collect, float entryCash, float spend, float withdrawal, float deliveryCash, float deliveryWaiter) throws RemoteException {
        Utils.abrirBase();
        Base.openTransaction();
        Date now = new Date(System.currentTimeMillis());
        SimpleDateFormat date = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat hour = new SimpleDateFormat("HH:mm:ss");
        Cashbox ret = Cashbox.createIt("day", date.format(now), "turn", turn, "balance", balance, "collect", collect, "entry_cash", entryCash, "spend", spend, "withdrawal", withdrawal, "delivery_cash", deliveryCash, "delivery_waiter", deliveryWaiter);
        Base.commitTransaction();
        return ret.toMap();
    }

    @Override
    public float getPastBalance() throws RemoteException {
        Utils.abrirBase();
        float ret = 0;
        List<Map> last = Cashbox.findAll().orderBy("id desc").limit(1).toMaps();
        Map m = last.get(0);
        ret += (float) m.get("balance");
        return ret;
    }

    @Override
    public Map<String, Object> getLast() throws RemoteException {
        Utils.abrirBase();
        List<Map> last = Cashbox.findAll().orderBy("id desc").limit(1).toMaps();
        Map m = last.get(0);
        return m;
    }

    /**
     * si el turno es TT retorna el penultimo turno tarde, NO EL ULTIMO
     *
     * @param turn
     * @return
     * @throws RemoteException
     */
    @Override
    public Map<String, Object> getLast(String turn) throws RemoteException {
        Utils.abrirBase();
        List<Map> last;
        Map m;
        if (turn.equals("TT")) {
            last = Cashbox.where("turn = ?", "T").orderBy("id desc").limit(2).toMaps();
            if(last.size()==2)
                m = last.get(1);
            else
                m = last.get(0);
        } else {
            last = Cashbox.where("turn = ?", turn).orderBy("id desc").limit(1).toMaps();
            m = last.get(0);
        }
        return m;
    }
}
