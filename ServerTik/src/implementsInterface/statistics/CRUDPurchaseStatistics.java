/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package implementsInterface.statistics;

import interfaces.statistics.InterfacePurchaseStatistics;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.sql.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import models.Pproductcategory;
import models.Pproductsubcategory;
import models.Provider;
import models.statistics.Purchasestatistic;
import org.javalite.activejdbc.Base;
import org.javalite.activejdbc.Model;
import utils.Utils;

/**
 *
 * @author eze
 */
public class CRUDPurchaseStatistics extends UnicastRemoteObject implements InterfacePurchaseStatistics {

    public CRUDPurchaseStatistics() throws RemoteException {
        super();
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
        System.out.println("Las estadisticas de compra diaria no andan viejita!");
        return new LinkedList<>();
    }

    @Override
    public List<Map> findPurchaseStatisticsBetweenMonths(Date since, Date until) throws RemoteException {
        System.out.println("Las estadisticas de compra mensual no andan viejita!");
        return new LinkedList<>();
    }

    @Override
    public List<Map> findPurchaseStatisticsBetweenYears(Date since, Date until) throws RemoteException {
        System.out.println("Las estadisticas de compra anual no andan viejita!");
        return new LinkedList<>();
    }

    @Override
    public List<Map> findAllPurchaseStatisticsBetweenDates(Date since, Date until) throws RemoteException {
        System.out.println("Las estadisticas de compra entre fechas no andan viejita!");
        return new LinkedList<>();
    }

}
