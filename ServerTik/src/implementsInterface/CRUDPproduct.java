/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package implementsInterface;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;
import java.util.Map;
import models.Eproduct;
import models.Fproduct;
import models.Pproduct;
import org.javalite.activejdbc.Base;
import utils.Utils;

/**
 *
 * @author jacinto
 */
public class CRUDPproduct extends UnicastRemoteObject implements interfaces.InterfacePproduct {

    /**
     * Constructor
     *
     * @throws RemoteException
     */
    public CRUDPproduct() throws RemoteException {
        super();
    }

    @Override
    public Map<String, Object> create(String name, float stock, String measureUnit, float unitPrice) throws java.rmi.RemoteException {
        Utils.abrirBase();
        Base.openTransaction();
        if (!measureUnit.equals("unitario")) {
            unitPrice = unitPrice / 1000;
        }
        Pproduct ret = Pproduct.createIt("name", name, "stock", stock, "measure_unit", measureUnit, "unit_price", unitPrice);
        Base.commitTransaction();
        return ret.toMap();
    }

    @Override
    public Map<String, Object> modify(int id, String name, float stock, String measureUnit, float unitPrice) throws java.rmi.RemoteException {
        Utils.abrirBase();
        Pproduct product = Pproduct.findById(id);
        Map<String, Object> res = null;
        if (product != null) {
            Base.openTransaction();
            if (measureUnit.equals("unitario")) {
                unitPrice = unitPrice / 1000;
            }
            product.setString("name", name);
            product.setFloat("stock", stock);
            product.setString("measure_unit", measureUnit);
            product.setFloat("unit_price", unitPrice);
            product.saveIt();
            res = product.toMap();
            Base.commitTransaction();
        }
        return res;
    }

    @Override
    public boolean delete(int id) throws java.rmi.RemoteException {
        Utils.abrirBase();
        Pproduct product = Pproduct.findById(id);
        boolean res = true;
        if (product != null) {
            Base.openTransaction();
            product.setInteger("removed", 1);
            res = res && product.saveIt();
            CRUDEproduct crudE = new CRUDEproduct();
            for (Eproduct eProdcut : product.getAll(Eproduct.class)) {
                eProdcut.setInteger("removed", 1);
                res = res && eProdcut.saveIt();
                for (Fproduct fProdcut : eProdcut.getAll(Fproduct.class)) {
                    fProdcut.set("removed", 1);
                    res = res && fProdcut.saveIt();
                }
            }
            for (Fproduct fProdcut : product.getAll(Fproduct.class)) {
                fProdcut.set("removed", 1);
                res = res && fProdcut.saveIt();
            }
            Base.commitTransaction();
        }
        return res;
    }

    @Override
    public Map<String, Object> getPproduct(int id) throws java.rmi.RemoteException {
        Utils.abrirBase();
        Map<String, Object> ret = null;
        Pproduct product = Pproduct.findById(id);
        if (product != null) {
            ret = product.toMap();
        }
        return ret;
    }

    @Override
    public List<Map> getPproducts() throws java.rmi.RemoteException {
        Utils.abrirBase();
        List<Map> ret = Pproduct.where("removed = ?", 0).toMaps();
        return ret;
    }

    @Override
    public List<Map> getPproducts(String searchParams) throws java.rmi.RemoteException {
        Utils.abrirBase();
        List<Map> ret = Pproduct.where("removed = ? and (id like ? or name like ?)", 0, "%" + searchParams + "%", "%" + searchParams + "%").toMaps();
        return ret;
    }

    @Override
    public Map<String, Object> loadPurchase(int id, String measureUnit, float price, float amount) throws java.rmi.RemoteException {
        Utils.abrirBase();
        Pproduct product = Pproduct.findById(id);
        Map<String, Object> res = null;
        if (product != null) {
            Base.openTransaction();
            if (measureUnit.equals("Kg") || measureUnit.equals("L")) {
                amount = amount * 1000;
            }
            float st = product.getFloat("stock") + amount;
            float unitP = price / amount;
            product.setFloat("stock", st);
            product.setFloat("unit_price", unitP);
            product.saveIt();
            res = product.toMap();
            Base.commitTransaction();
        }
        return res;
    }

    @Override
    public Map<String, Object> calculateUnitPrice(int id, String measureUnit, float price, float amount) throws java.rmi.RemoteException {
        Utils.abrirBase();
        Pproduct product = Pproduct.findById(id);
        Map<String, Object> res = null;
        if (product != null) {
            Base.openTransaction();
            if (measureUnit.equals("Kg") || measureUnit.equals("L")) {
                amount = amount * 1000;
            }
            float unitP = price / amount;
            product.setFloat("unit_price", unitP);
            product.saveIt();
            res = product.toMap();
            Base.commitTransaction();
        }
        return res;
    }
}
