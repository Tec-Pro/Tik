/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package implementsInterface;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import models.Eproduct;
import models.Fproduct;
import models.FproductsEproducts;
import models.FproductsPproducts;
import models.Pproduct;
import models.Subcategory;
import org.javalite.activejdbc.Base;
import utils.Pair;
import utils.Utils;

/**
 *
 * @author jacinto
 */
public class CRUDFproduct extends UnicastRemoteObject implements interfaces.InterfaceFproduct {

    /**
     * Constructor
     *
     * @throws RemoteException
     */
    public CRUDFproduct() throws RemoteException {
        super();
    }

    @Override
    public Map<String, Object> create(String name, int subcategory_id, List<Pair> pProducts, List<Pair> eProducts) throws RemoteException {
        Utils.abrirBase();
        Base.openTransaction();
        Fproduct ret = Fproduct.createIt("name", name);
        Iterator it = pProducts.iterator();
        while (it.hasNext()) { //creo la relacion Fproduct Eproduct
            Pair<Integer, Float> prod = (Pair<Integer, Float>) it.next();
            String amount = prod.second().toString().replace(',', '.');
            FproductsPproducts.create("fproduct_id", ret.getId(), "pproduct_id", prod.first(), "amount", amount);
        }
        it = eProducts.iterator();
        while (it.hasNext()) { //creo la relacion Pproduct Fproduct
            Pair<Integer, Float> prod = (Pair<Integer, Float>) it.next();
            String amount = prod.second().toString().replace(',', '.');
            FproductsEproducts.create("fproduct_id", ret.getId(), "eproduct_id", prod.first(), "amount", amount);
        }
        ret.add(Subcategory.findById(subcategory_id));
        ret.saveIt();
        ret = Fproduct.findById(ret.getId());
        Base.commitTransaction();
        Utils.cerrarBase();
        return ret.toMap();
    }

    @Override
    public Map<String, Object> modify(int id, String name, int subcategory_id, List<Pair> pProducts, List<Pair> eProducts) throws RemoteException {
        Utils.abrirBase();
        Base.openTransaction();
        Eproduct ret = Eproduct.findById(id);
        if (ret != null) {
            ret.setString("name", name);
            ret.set("subcategory_id", null);
            Iterator it = FproductsPproducts.find("fproduct_id = ?", id).iterator();
            while (it.hasNext()) {//elimino relaciones pre existenes
                ((FproductsPproducts) it.next()).delete();
            }
            it = pProducts.iterator();
            while (it.hasNext()) {  //creo relacion entre primarios y finales nuevamente
                Pair<Integer, Float> prod = (Pair<Integer, Float>) it.next();
                String amount = prod.second().toString().replace(',', '.');
                FproductsPproducts.create("fproduct_id", ret.getId(), "pproduct_id", prod.first(), "amount", amount);
            }
            it = FproductsEproducts.find("fproduct_id = ?", id).iterator();
            while (it.hasNext()) {//elimino relaciones pre existenes
                ((FproductsEproducts) it.next()).delete();
            }
            it = pProducts.iterator();
            while (it.hasNext()) {  //creo relacion entre elaborados y finales nuevamente
                Pair<Integer, Float> prod = (Pair<Integer, Float>) it.next();
                String amount = prod.second().toString().replace(',', '.');
                FproductsEproducts.create("fproduct_id", ret.getId(), "eproduct_id", prod.first(), "amount", amount);
            }
            ret.add(Subcategory.findById(subcategory_id));
            ret.saveIt();
            ret = Eproduct.findById(ret.getId());
            Base.commitTransaction();
            Utils.cerrarBase();
            return ret.toMap();
        } else {
            Base.commitTransaction();
            Utils.cerrarBase();
            return null;
        }
    }

    @Override //FALTA ELIMINAR LAS MOVIDAS DONDE PERTENECE  
    public boolean delete(int id) throws RemoteException {
        Utils.abrirBase();
        Eproduct product = Fproduct.findById(id);
        boolean res = false;
        if (product != null) {
            Base.openTransaction();
            res = product.delete();
            Base.commitTransaction();
        }
        Utils.cerrarBase();
        return res;
    }

    @Override
    public Map<String, Object> getFproduct(int id) throws RemoteException {
        Utils.abrirBase();
        Map<String, Object> ret = Fproduct.findById(id).toMap();
        Utils.cerrarBase();
        return ret;
    }

    @Override
    public List<Map> getFproducts() throws RemoteException {
        Utils.abrirBase();
        List<Map> ret = Fproduct.findAll().toMaps();
        Utils.cerrarBase();
        return ret;
    }

    public List<Map> getPproducts(int id) throws java.rmi.RemoteException {
        Utils.abrirBase();
        Fproduct fProd = Fproduct.findById(id);
        List<Map> ret = fProd.getAll(Pproduct.class).toMaps();
        Utils.cerrarBase();
        return ret;
    }

    public List<Map> getEproducts(int id) throws java.rmi.RemoteException {
        Utils.abrirBase();
        Fproduct fProd = Fproduct.findById(id);
        List<Map> ret = fProd.getAll(Eproduct.class).toMaps();
        Utils.cerrarBase();
        return ret;
    }
}
