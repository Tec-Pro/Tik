/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package implementsInterface.providers.payments;

import interfaces.providers.payments.InterfacePayments;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;
import java.util.Map;
import models.Payment;
import org.javalite.activejdbc.Base;
import utils.Utils;

/**
 *
 * @author jacinto
 */
public class CRUDPayments extends UnicastRemoteObject implements InterfacePayments {

    public CRUDPayments() throws RemoteException {
        super();
    }

    @Override
    public boolean createPayment(int provider_id, String detail, float amount, int purchase_id, String date,String nameAdmin) throws RemoteException {
        Utils.abrirBase();
        Base.openTransaction();
        Payment payment = null;
        if (purchase_id == -1) {
            payment = Payment.createIt(
                    "provider_id", provider_id,
                    "detail", detail,
                    "amount", amount,
                    "date", date,
                    "name_admin", nameAdmin
            );
        } else {
            payment = Payment.createIt(
                    "provider_id", provider_id,
                    "detail", detail,
                    "amount", amount,
                    "date", date,
                    "purchase_id", purchase_id,
                    "name_admin", nameAdmin

            );
        }
        Base.commitTransaction();
        return (payment != null);
    }

    @Override
    public Map<String, Object> getPayment(int id) throws RemoteException {
        Utils.abrirBase();
        Payment payment = Payment.findById(id);
        Map<String, Object> ret = payment.toMap();
        return ret;
    }

    @Override
    public  List<Map> getPayments(String datefrom, String dateTo) throws RemoteException {
        Utils.abrirBase();
        List<Map> payments = Payment.where("date>= ? AND date <= ?", datefrom, dateTo).toMaps();
        return payments;
    }

    @Override
    public List<Map> getPaymentsProvider(int provider_id) throws RemoteException {
        Utils.abrirBase();
        List<Map> payments = Payment.where("provider_id = ?",provider_id).toMaps();
        return payments;    
    }

}
