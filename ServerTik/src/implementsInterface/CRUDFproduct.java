/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package implementsInterface;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import models.Category;
import models.Eproduct;
import models.EproductsPproducts;
import models.Fproduct;
import models.FproductsEproducts;
import models.FproductsFproducts;
import models.FproductsPproducts;
import models.Pproduct;
import models.Subcategory;
import org.javalite.activejdbc.Base;
import org.javalite.activejdbc.Model;
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
    public Map<String, Object> create(String name, int subcategory_id, List<Pair> pProducts, List<Pair> eProducts, float sellPrice, String belong, List<Pair> fProducts) throws RemoteException {
        Utils.abrirBase();
        Base.openTransaction();
        Fproduct ret = Fproduct.createIt("name", name, "subcategory_id", subcategory_id, "sell_price", sellPrice, "belong", belong, "removed", 0);
        for (Pair<Integer, Float> prod : pProducts) {
            float amount = Float.parseFloat(prod.second().toString());
            FproductsPproducts.create("fproduct_id", ret.getId(), "pproduct_id", prod.first(), "amount", amount).saveIt();
        }
        for (Pair<Integer, Float> prod : eProducts) {
            float amount = Float.parseFloat(prod.second().toString());
            FproductsEproducts.create("fproduct_id", ret.getId(), "eproduct_id", prod.first(), "amount", amount).saveIt();
        }
        for (Pair<Integer, Float> prod : fProducts) {

            float amount = Float.parseFloat(prod.second().toString());
            FproductsFproducts.create("fproduct_id2", ret.getId(), "fproduct_id", prod.first(), "amount", amount).saveIt();

        }
        Base.commitTransaction();
        return ret.toMap();
    }

    @Override
    public Map<String, Object> modify(int id, String name, int subcategory_id, List<Pair> pProducts, List<Pair> eProducts, float sellPrice, String belong, List<Pair> fProducts) throws RemoteException {
        Utils.abrirBase();
        Base.openTransaction();
        Fproduct ret = Fproduct.findById(id);
        if (ret != null) {
            ret.setString("name", name);
            ret.setString("belong", belong);
            ret.set("subcategory_id", subcategory_id);
            ret.set("sell_price", sellPrice);
            for (FproductsPproducts fp : ret.getAll(FproductsPproducts.class)) {
                fp.delete();
            }
            for (FproductsEproducts fe : ret.getAll(FproductsEproducts.class)) {
                fe.delete();
            }
            for (Model fe : FproductsFproducts.where("fproduct_id2 = ?", id)) {
                fe.delete();
            }
            for (Pair<Integer, Float> prod : pProducts) {
                float amount = Float.parseFloat(prod.second().toString());
                FproductsPproducts.create("fproduct_id", ret.getId(), "pproduct_id", prod.first(), "amount", amount).saveIt();
            }
            for (Pair<Integer, Float> prod : eProducts) {
                float amount = Float.parseFloat(prod.second().toString());
                FproductsEproducts.create("fproduct_id", ret.getId(), "eproduct_id", prod.first(), "amount", amount).saveIt();
            }
            for (Pair<Integer, Float> prod : fProducts) {
                float amount = Float.parseFloat(prod.second().toString());
                FproductsFproducts.create("fproduct_id2", ret.getId(), "fproduct_id", prod.first(), "amount", amount).saveIt();
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
            for (Iterator<Model> it = FproductsFproducts.where("fproduct_id = ?", id).iterator(); it.hasNext();) {
                Model ff = it.next();
                res = res && Fproduct.findById(ff.get("fproduct_id2")).delete();
            }
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
    public List<Map> getPproducts(int id) throws RemoteException {
        Utils.abrirBase();
        Fproduct fProd = Fproduct.findById(id);
        List<Map> ret = null;
        if (fProd != null) {
            ret = fProd.getAll(Pproduct.class).toMaps();
        }

        return ret;
    }

    @Override
    public List<Map> getEproducts(int id) throws RemoteException {
        Utils.abrirBase();
        Fproduct fProd = Fproduct.findById(id);
        List<Map> ret = null;
        if (fProd != null) {
            ret = fProd.getAll(Eproduct.class).toMaps();
        }

        return ret;
    }

    @Override
    public List<Map> getFproducts(String name) throws RemoteException {
        Utils.abrirBase();
        List<Map> ret = Fproduct.where("removed = ? and (id like ? or name like ?)", 0, "%" + name + "%", "%" + name + "%").toMaps();

        return ret;
    }

    @Override
    public List<Map> getFproductPproduts(int idFproduct) throws RemoteException {
        Utils.abrirBase();
        Fproduct fProd = Fproduct.findById(idFproduct);
        List<Map> ret = null;
        if (fProd != null) {
            ret = fProd.getAll(FproductsPproducts.class).toMaps();
        }

        return ret;
    }

    @Override
    public List<Map> getFproductEproduts(int idFproduct) throws RemoteException {
        Utils.abrirBase();
        Fproduct fProd = Fproduct.findById(idFproduct);
        List<Map> ret = null;
        if (fProd != null) {
            ret = fProd.getAll(FproductsEproducts.class).toMaps();
        }
        return ret;
    }

    @Override
    public float calculateProductionPrice(int idFproduct) throws RemoteException {
        Utils.abrirBase();
        Fproduct fProd = Fproduct.findById(idFproduct);
        float ret = 0;
        float epPrice = 0;
        if (fProd != null) {
            for (FproductsPproducts fp : fProd.getAll(FproductsPproducts.class)) {
                Pproduct pp = Pproduct.findById(fp.getInteger("pproduct_id"));
                ret += pp.getFloat("unit_price") * fp.getFloat("amount");
            }
            for (FproductsEproducts fe : fProd.getAll(FproductsEproducts.class)) {
                Eproduct pp = Eproduct.findById(fe.getInteger("eproduct_id"));
                epPrice = 0;
                for (EproductsPproducts ep : pp.getAll(EproductsPproducts.class)) {
                    Pproduct pp2 = Pproduct.findById(ep.getInteger("pproduct_id"));
                    epPrice += pp2.getFloat("unit_price") * ep.getFloat("amount");
                }
                ret += epPrice * fe.getFloat("amount");
            }
            for (Iterator<Model> it = FproductsFproducts.where("fproduct_id2 = ?", idFproduct).iterator(); it.hasNext();) {
                Model ff = it.next();
                ret += calculateProductionPrice(ff.getInteger("fproduct_id")) * ff.getFloat("amount");
            }
        }
        return ret;
    }

    @Override
    public List<Map> getFproducts(int id) throws RemoteException {
        Utils.abrirBase();
        List<Map> ret = new LinkedList();
        for (Iterator<Model> it = FproductsFproducts.where("fproduct_id2 = ?", id).iterator(); it.hasNext();) {
            Model ff = it.next();
            Map fProd = Fproduct.findById(ff.get("fproduct_id")).toMap();
            ret.add(fProd);
        }
        return ret;
    }

    @Override
    public List<Map> getFproductFproduts(int idFproduct) throws RemoteException {
        Utils.abrirBase();
        List<Map> ret = FproductsFproducts.where("fproduct_id2 = ?", idFproduct).toMaps();
        return ret;
    }

    @Override
    public int belongsTo(int idFProduct) throws RemoteException {
        //Devuelve 1 si el producto pertenece a la cocina, 0 si pertenece al bar.
        Utils.abrirBase();
        Fproduct prod = Fproduct.findById(idFProduct);
        if (prod.getString("belong").equals("Cocina")){
            return 1;
        } else {
            return 0;
        }
    }

    @Override
    public List<Map> getFproductsByCategory(String name, int idCategory) throws RemoteException {
        Utils.abrirBase();
        List<Map> ret = Fproduct.where("removed = ? and (id like ? or name like ?)", 0, "%" + name + "%", "%" + name + "%").toMaps();
        List<Map> ret2 = Fproduct.where("removed = ? and (id like ? or name like ?)", 0, "%" + name + "%", "%" + name + "%").toMaps();
        ret2.removeAll(ret);
        for (Map m: ret){
            Subcategory subcategory = Subcategory.findById(m.get("subcategory_id"));
            if (subcategory != null) {
                if (subcategory.parent(Category.class).getId().equals(idCategory)){
                    ret2.add(m);
                }
            }
        }       
        return ret2;
    }
}
