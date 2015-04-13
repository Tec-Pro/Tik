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
        Fproduct ret = Fproduct.createIt("name", name, "subcategory_id", subcategory_id);
        Iterator it = pProducts.iterator();
        for (Pair<Integer, Float> prod : pProducts) {
            String amount = prod.second().toString().replace(',', '.');
            FproductsPproducts.create("fproduct_id", ret.getId(), "pproduct_id", prod.first(), "amount", amount).saveIt();
        }
        for (Pair<Integer, Float> prod : eProducts) {
            String amount = prod.second().toString().replace(',', '.');
            FproductsEproducts.create("fproduct_id", ret.getId(), "eproduct_id", prod.first(), "amount", amount).saveIt();
        }
        Base.commitTransaction();
         
        return ret.toMap();
    }

    @Override
    public Map<String, Object> modify(int id, String name, int subcategory_id, List<Pair> pProducts, List<Pair> eProducts) throws RemoteException {
        Utils.abrirBase();
        Base.openTransaction();
        Fproduct ret = Fproduct.findById(id);
        if (ret != null) {
            ret.setString("name", name);
            ret.set("subcategory_id", subcategory_id);
            for (FproductsPproducts fp : ret.getAll(FproductsPproducts.class)) {
                fp.delete();
            }
            for (FproductsEproducts fe : ret.getAll(FproductsEproducts.class)) {
                fe.delete();
            }
            for (Pair<Integer, Float> prod : pProducts) {
                String amount = prod.second().toString().replace(',', '.');
                FproductsPproducts.create("fproduct_id", ret.getId(), "pproduct_id", prod.first(), "amount", amount).saveIt();
            }
            for (Pair<Integer, Float> prod : eProducts) {
                String amount = prod.second().toString().replace(',', '.');
                FproductsEproducts.create("fproduct_id", ret.getId(), "eproduct_id", prod.first(), "amount", amount).saveIt();
            }
            ret.saveIt();
            Base.commitTransaction();
             
            return ret.toMap();
        } else {
            Base.commitTransaction();
             
            return null;
        }
    }

    @Override
    public boolean delete(int id) throws RemoteException {
        Utils.abrirBase();
        Fproduct product = Fproduct.findById(id);
        boolean res = true;
        if (product != null) {
            Base.openTransaction();
            product.setInteger("removed", 1);
            res = res && product.saveIt();
            Base.commitTransaction();
        }
         
        return res;
    }

    @Override
    public Map<String, Object> getFproduct(int id) throws RemoteException {
        Utils.abrirBase();
        Map<String, Object> ret = null;
        Fproduct product = Fproduct.findById(id);
        if (product != null) {
            ret = product.toMap();
        }
         
        return ret;
    }

    @Override
    public List<Map> getFproducts() throws RemoteException {
        Utils.abrirBase();
        List<Map> ret = Fproduct.where("removed = ?", 0).toMaps();
         
        return ret;
    }

    @Override
    public List<Map> getPproducts(int id) throws java.rmi.RemoteException {
        Utils.abrirBase();
        Fproduct fProd = Fproduct.findById(id);
        List<Map> ret = null;
        if (fProd != null) {
            ret = fProd.getAll(Pproduct.class).toMaps();
        }
         
        return ret;
    }

    @Override
    public List<Map> getEproducts(int id) throws java.rmi.RemoteException {
        Utils.abrirBase();
        Fproduct fProd = Fproduct.findById(id);
        List<Map> ret = null;
        if (fProd != null) {
            ret = fProd.getAll(Eproduct.class).toMaps();
        }
         
        return ret;
    }

    @Override
    public List<Map> getFproducts(String name) throws java.rmi.RemoteException {
        Utils.abrirBase();
        List<Map> ret = Fproduct.where("removed = ? and (id = ? or name = ?)", 0, name, name).toMaps();
         
        return ret;
    }

    @Override
    public List<Map> getFproductPproduts(int idFproduct) throws java.rmi.RemoteException {
        Utils.abrirBase();
        Fproduct fProd = Fproduct.findById(idFproduct);
        List<Map> ret = null;
        if (fProd != null) {
            ret = fProd.getAll(FproductsPproducts.class).toMaps();
        }
         
        return ret;
    }

    @Override
    public List<Map> getFproductEproduts(int idFproduct) throws java.rmi.RemoteException {
        Utils.abrirBase();
        Fproduct fProd = Fproduct.findById(idFproduct);
        List<Map> ret = null;
        if (fProd != null) {
            ret = fProd.getAll(FproductsEproducts.class).toMaps();
        }
         
        return ret;
    }
}
