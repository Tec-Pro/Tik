/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package implementsInterface.providers.purchase;

import implementsInterface.statistics.CRUDPurchaseStatistics;
import interfaces.providers.purchases.InterfacePurchase;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.SortedMap;
import models.Pproduct;
import models.Pproductcategory;
import models.providers.purchase.PproductsPurchases;
import models.providers.purchase.Purchase;
import models.statistics.Purchasestatistic;
import org.javalite.activejdbc.Base;
import org.javalite.activejdbc.LazyList;
import utils.Pair;
import utils.Utils;

/**
 *
 * @author jacinto
 */
public class CRUDPurchase extends UnicastRemoteObject implements InterfacePurchase {

    public CRUDPurchase() throws RemoteException {
        super();
    }

    @Override
    public Integer create(Float cost, Float paid, String date, Integer providerId, String datePaid, LinkedList<Pair<Integer, Pair<Float, Float>>> products, HashMap<Integer, Float> ivaProducts) {
        Utils.abrirBase();
        Base.openTransaction();
        Purchase purchase = Purchase.createIt(
                "cost", cost,
                "paid", paid,
                "date", date,
                "provider_id", providerId,
                "date_paid", datePaid
        );
        if (purchase != null) {
            Integer idPurchase = purchase.getInteger("id");
            Iterator<Pair<Integer, Pair<Float, Float>>> it = products.iterator();//primer elemento es el id del articulo, el segundo cantidad, tercero precio
            while (it.hasNext()) {
                Pair<Integer, Pair<Float, Float>> pair = it.next();
                PproductsPurchases pproductPurchase = PproductsPurchases.createIt(
                        "pproduct_id", pair.first(),
                        "purchase_id", purchase.getId(),
                        "amount", pair.second().first(),
                        "final_price", pair.second().second(),
                        "iva", 21
                );
                if (pproductPurchase != null) {
                    Pproduct pproduct = Pproduct.findById(pair.first());
                    float stock = pair.second().first();
                    float unitPrice = pair.second().second();
                    switch (pproduct.getString("measure_unit")) {
                        case "gr":
                            stock *= 1000;
                            unitPrice /= 1000;
                            break;
                        case "ml":
                            stock *= 1000;
                            unitPrice /= 1000;
                            break;

                    }
                    pproduct.setFloat("stock", stock + pproduct.getFloat("stock"));
                    pproduct.setFloat("unit_price", unitPrice);
                    pproduct.setInteger("provider_id", providerId);
                    pproduct.saveIt();
                    //calculo las estadisticas de compras de productos primarios
                    String measureUnit = ""; //Paso la unidad de medida a Litro o Kg segun corresponda
                    if (pproduct.getString("measure_unit").equals("gr")) {
                        measureUnit = "Kg";
                    } else {
                        if (pproduct.getString("measure_unit").equals("ml")) {
                            measureUnit = "L";
                        }
                    }
                    int pproductSubcategoryId = pproduct.getInteger("pproductsubcategory_id");//id de la subcategoria a la que corresponde el pproduct
                    int pproductId = pair.first(); //id del pproduct
                    String pproductName = pproduct.getString("name"); //nombre del pproduct
                    float quantity = pair.second().first(); //cantidad de pproduct que se compró
                    float price = pair.second().second(); //precio del pproduct por Litro, Kg o Unidad
                    float totalPrice = quantity * price; //precio total de la cantidad por el precio unitario de cada pproduct
                    CRUDPurchaseStatistics.savePurchaseStatistics(pproductSubcategoryId, date, pproductId, pproductName,
                            measureUnit, quantity, totalPrice, providerId, price, idPurchase);

                }
            }
        }
        Base.commitTransaction();
        return purchase.getInteger("id");
    }

    //no se actualiza el stock, supongo que se borran por ser muy viejas
    @Override
    public boolean delete(Integer idPurchase) {
        Utils.abrirBase();
        boolean result = false;
        Base.openTransaction();
        int delAmount = Purchase.delete("id = ?", idPurchase);
        if (delAmount == 1 && delAmount > 0) {//se borro una compra, el id es unico entonces debe borrarse solo uno
            PproductsPurchases.delete("purchase_id = ?", idPurchase);
            result = true;
        }
        Base.commitTransaction();
        return result;
    }

    @Override
    public Pair<Map<String, Object>, List<Map>> getPurchase(Integer idPurchase) {
        Utils.abrirBase();
        Base.openTransaction();
        Purchase purchase = Purchase.findById(idPurchase);
        Map<String, Object> resultPurchase = purchase.toMap();
        List<Map> resultProducts = purchase.get(PproductsPurchases.class, "purchase_id = ?", idPurchase).toMaps();
        Base.commitTransaction();
        return new Pair<>(resultPurchase, resultProducts);
    }

    @Override
    public List<Pair<SortedMap<String, Object>, List<Map>>> getPurchasesProvider(Integer idProvider) {
        Utils.abrirBase();
        Base.openTransaction();
        LinkedList<Pair<SortedMap<String, Object>, List<Map>>> result = new LinkedList<>();
        LazyList<Purchase> purchases = Purchase.where("provider_id = ?", idProvider);
        Iterator<Purchase> it = purchases.iterator();
        while (it.hasNext()) {
            Purchase p = it.next();
            Pair<SortedMap<String, Object>, List<Map>> pair = new Pair<>(p.toMap(), p.get(PproductsPurchases.class, "purchase_id = ?", p.getId()).toMaps());
            result.add(pair);
        }
        return result;
    }

    @Override
    public List<Pair<SortedMap<String, Object>, List<Map>>> getProviderPurchasesBetweenDates(Integer idProvider, String from, String until) throws RemoteException {
        Utils.abrirBase();
        Base.openTransaction();
        LinkedList<Pair<SortedMap<String, Object>, List<Map>>> result = new LinkedList<>();
        LazyList<Purchase> purchases = Purchase.where("provider_id = ? AND date>= ? AND date <= ?", idProvider, from, until);
        Iterator<Purchase> it = purchases.iterator();
        while (it.hasNext()) {
            Purchase p = it.next();
            Pair<SortedMap<String, Object>, List<Map>> pair = new Pair<>(p.toMap(), p.get(PproductsPurchases.class, "purchase_id = ?", p.getId()).toMaps());
            result.add(pair);
        }
        return result;
    }

    @Override
    public Integer modify(Float cost, Float paid, String date, Integer providerId, String datePaid, LinkedList<Pair<Integer, Pair<Float, Float>>> products, Integer idPurchase, HashMap<Integer, Float> ivaProducts) throws RemoteException {
        Utils.abrirBase();
        Base.openTransaction();
        Purchase purchase = Purchase.findById(idPurchase);
        purchase.set(
                "cost", cost,
                "paid", paid,
                "date", date,
                "provider_id", providerId,
                "date_paid", datePaid
        );
        purchase.saveIt();
        if (purchase.saveIt()) {
            LazyList<PproductsPurchases> restoreStock = PproductsPurchases.find("purchase_id = ?", idPurchase);
            Iterator<PproductsPurchases> itRestore = restoreStock.iterator();
            while (itRestore.hasNext()) {
                PproductsPurchases p = itRestore.next();
                Pproduct pproduct = Pproduct.findById(p.get("pproduct_id"));
                pproduct.set("stock", pproduct.getFloat("stock") + p.getFloat("amount")); //restauro el stock
                pproduct.saveIt();
            }
            //remuevo las estadisticas de compra
            Purchasestatistic.delete("purchase_id = ?", idPurchase);

            //ahora que restaure el stock elimino todas las relaciones y las vuelvo a crear más abajo
            PproductsPurchases.delete("purchase_id = ?", idPurchase);

            Iterator<Pair<Integer, Pair<Float, Float>>> it = products.iterator();//primer elemento es el id del articulo, el segundo cantidad, tercero precio
            while (it.hasNext()) {
                Pair<Integer, Pair<Float, Float>> pair = it.next();
                PproductsPurchases pproductPurchase = PproductsPurchases.createIt(
                        "pproduct_id", pair.first(),
                        "purchase_id", purchase.getId(),
                        "amount", pair.second().first(),
                        "final_price", pair.second().second(),
                        "iva",21
                );
                if (pproductPurchase != null) {
                    Pproduct pproduct = Pproduct.findById(pair.first());
                    float stock = pair.second().first();
                    float unitPrice = pair.second().second();
                    switch (pproduct.getString("measure_unit")) {
                        case "gr":
                            stock *= 1000;
                            unitPrice /= 1000;
                            break;
                        case "ml":
                            stock *= 1000;
                            unitPrice /= 1000;
                            break;

                    }
                    pproduct.setFloat("stock", stock + pproduct.getFloat("stock"));
                    pproduct.setFloat("unit_price", unitPrice);
                    pproduct.setInteger("provider_id", providerId);
                    pproduct.saveIt();
                    //calculo las estadisticas de compras de productos primarios
                    String measureUnit = ""; //Paso la unidad de medida a Litro o Kg segun corresponda
                    if (pproduct.getString("measure_unit").equals("gr")) {
                        measureUnit = "Kg";
                    } else {
                        if (pproduct.getString("measure_unit").equals("ml")) {
                            measureUnit = "L";
                        }
                    }
                    int pproductSubcategoryId = pproduct.getInteger("pproductsubcategory_id");//id de la subcategoria a la que corresponde el pproduct
                    int pproductId = pair.first(); //id del pproduct
                    String pproductName = pproduct.getString("name"); //nombre del pproduct
                    float quantity = pair.second().first(); //cantidad de pproduct que se compró
                    float price = pair.second().second(); //precio del pproduct por Litro, Kg o Unidad
                    float totalPrice = quantity * price; //precio total de la cantidad por el precio unitario de cada pproduct
                    CRUDPurchaseStatistics.savePurchaseStatistics(pproductSubcategoryId, date, pproductId, pproductName,
                            measureUnit, quantity, totalPrice, providerId, price, idPurchase);

                }
            }
        }
        Base.commitTransaction();
        return idPurchase;
    }
}
