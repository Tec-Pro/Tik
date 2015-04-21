/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package implementsInterface.providers.purchase;

import interfaces.providers.purchases.InterfacePurchase;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import models.Pproduct;
import models.providers.purchase.PproductsPurchases;
import models.providers.purchase.Purchase;
import org.javalite.activejdbc.Base;
import org.javalite.activejdbc.LazyList;
import utils.Pair;
import utils.Utils;

/**
 *
 * @author jacinto
 */
public class CRUDPurchase extends UnicastRemoteObject implements InterfacePurchase{

    public CRUDPurchase() throws RemoteException {
        super();
    }

    public Integer create(Float cost, Float paid, String date, Integer providerId, String datePaid, LinkedList<Pair<Integer, Pair<Float, Float>>> products) {
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
                        "final_price", pair.second().second()
                );
                if (pproductPurchase != null) {
                    Pproduct pproduct = Pproduct.findById(pair.first());
                    pproduct.setFloat("stock", pair.second().first());
                    pproduct.setFloat("amount", pair.second().second());
                    pproduct.saveIt();
                }
            }
        }
        Base.commitTransaction();
        return purchase.getInteger("id");
    }

    //no se actualiza el stock, supongo que se borran por ser muy viejas
   public boolean delete(Integer idPurchase){
       Utils.abrirBase();
       boolean result=false;
       Base.openTransaction();
       int delAmount=Purchase.delete("id = ?", idPurchase);
       if(delAmount==1 && delAmount>0){//se borro una compra, el id es unico entonces debe borrarse solo uno
           PproductsPurchases.delete("purchase_id = ?", idPurchase);
           result=true;
       }
       Base.commitTransaction();
       return result;
   }
   
  public Pair<Map<String,Object>,List<Map>> getPurchase(Integer idPurchase){
      Utils.abrirBase();
      Base.openTransaction();
      Purchase purchase=Purchase.findById(idPurchase);
      Map<String,Object> resultPurchase=purchase.toMap();
      List<Map> resultProducts=purchase.get(PproductsPurchases.class, "purchase_id = ?", idPurchase).toMaps();
      Base.commitTransaction();
      return new Pair<>(resultPurchase, resultProducts);
  }
  
    public List<Pair<Map<String,Object>,List<Map>>> getPurchasesProvider(Integer idProvider){
      Utils.abrirBase();
      Base.openTransaction();
      LinkedList<Pair<Map<String,Object>,List<Map>>> result= new LinkedList<>();
      LazyList<Purchase> purchases=Purchase.where("provider_id", idProvider);
      Iterator<Purchase> it= purchases.iterator();
      while(it.hasNext()){
          Purchase p= it.next();
          Pair<Map<String,Object>,List<Map>> pair= new Pair<>(p.toMap(),p.get(PproductsPurchases.class, "purchase_id = ?", p.getId()).toMaps());
          result.add(pair);
      }
      return result;
  }
   
}
